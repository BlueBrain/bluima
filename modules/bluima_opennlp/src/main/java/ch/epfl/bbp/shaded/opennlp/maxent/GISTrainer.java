/////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2001 Jason Baldridge and Gann Bierner
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
//////////////////////////////////////////////////////////////////////////////   
package ch.epfl.bbp.shaded.opennlp.maxent;

import gnu.trove.*;

/**
 * An implementation of Generalized Iterative Scaling.  The reference paper
 * for this implementation was Adwait Ratnaparkhi's tech report at the
 * University of Pennsylvania's Institute for Research in Cognitive Science,
 * and is available at <a href ="ftp://ftp.cis.upenn.edu/pub/ircs/tr/97-08.ps.Z"><code>ftp://ftp.cis.upenn.edu/pub/ircs/tr/97-08.ps.Z</code></a>. 
 *
 * @author  Jason Baldridge
 * @version $Revision: 1.18 $, $Date: 2005/10/24 12:29:01 $
 */
class GISTrainer {

  // This can improve model accuracy, though training will potentially take
  // longer and use more memory.  Model size will also be larger.  Initial
  // testing indicates improvements for models built on small data sets and
  // few outcomes, but performance degradation for those with large data
  // sets and lots of outcomes.
  private boolean _simpleSmoothing = false;

  private boolean _useSlackParameter = false;
  private double sigma = 2.0;

  // If we are using smoothing, this is used as the "number" of
  // times we want the trainer to imagine that it saw a feature that it
  // actually didn't see.  Defaulted to 0.1.
  private double _smoothingObservation = 0.1;

  private boolean printMessages = false;

  private int numTokens; // # of event tokens
  private int numPreds; // # of predicates
  private int numOutcomes; // # of outcomes
  /** A global index variable for Events. */
  private int TID;
  /** A global index variable for Predicates. */
  private int PID;
  /** A global index variable for Outcomes. */
  private int OID;

  /** Records the array of predicates seen in each event. */
  private int[][] contexts;

  /** Records the array of outcomes seen in each event. */
  private int[] outcomes;

  private int[] outcomeList;

  // records the num of times an event has been seen, paired to
  // int[][] contexts
  private int[] numTimesEventsSeen;

  /** Stores the String names of the outcomes.  The GIS only tracks outcomes
   as ints, and so this array is needed to save the model to disk and
   thereby allow users to know what the outcome was in human
   understandable terms. */
  private String[] outcomeLabels;

  /** Stores the String names of the predicates. The GIS only tracks
   predicates as ints, and so this array is needed to save the model to
   disk and thereby allow users to know what the outcome was in human
   understandable terms. */
  private String[] predLabels;

  /** Stores the observed expected values of the features based on training data. */
  private MutableContext[] observedExpects;

  /** Stores the estimated parameter value of each predicate during iteration */
  private MutableContext[] params;

  /** Stores the expected values of the features based on the current models */
  private MutableContext[] modelExpects;


  /** The maximum number of feattures fired in an event. Usually refered to a C.*/
  private int constant;
  /**  Stores inverse of constant, 1/C. */
  private double constantInverse;
  /** The correction parameter of the model. */
  private double correctionParam;
  /** Observed expectation of correction feature. */
  private double cfObservedExpect;
  /** A global variable for the models expected value of the correction feature. */
  private double CFMOD;

  private final double NEAR_ZERO = 0.01;
  private final double LLThreshold = 0.0001;

  /** Stores the output of the current model on a single event durring
   *  training.  This we be reset for every event for every itteration.  */
  double[] modelDistribution;
  /** Stores the number of features that get fired per event. */
  int[] numfeats;
  /** Initial probability for all outcomes. */
  double iprob;
  
  /**
   * Creates a new <code>GISTrainer</code> instance which does
   * not print progress messages about training to STDOUT.
   *
   */
  GISTrainer() {
    super();
  }

  /**
   * Creates a new <code>GISTrainer</code> instance.
   *
   * @param printMessages sends progress messages about training to
   *                      STDOUT when true; trains silently otherwise.
   */
  GISTrainer(boolean printMessages) {
    this();
    this.printMessages = printMessages;
  }

  /**
   * Sets whether this trainer will use smoothing while training the model.
   * This can improve model accuracy, though training will potentially take
   * longer and use more memory.  Model size will also be larger.
   *
   * @param smooth true if smoothing is desired, false if not
   */
  public void setSmoothing(boolean smooth) {
    _simpleSmoothing = smooth;
  }

  /**
   * Sets whether this trainer will use smoothing while training the model.
   * This can improve model accuracy, though training will potentially take
   * longer and use more memory.  Model size will also be larger.
   *
   * @param timesSeen the "number" of times we want the trainer to imagine
   *                  it saw a feature that it actually didn't see
   */
  public void setSmoothingObservation(double timesSeen) {
    _smoothingObservation = timesSeen;
  }

  public GISModel trainModel(EventStream eventStream, int iterations, int cutoff) {
    return trainModel(iterations, new OnePassDataIndexer(eventStream, cutoff));
  }

  /**
   * Train a model using the GIS algorithm.
   *
   * @param iterations  The number of GIS iterations to perform.
   * @param di The data indexer used to compress events in memory.
   * @return The newly trained model, which can be used immediately or saved
   *         to disk using an opennlp.maxent.io.GISModelWriter object.
   */
  public GISModel trainModel(int iterations, DataIndexer di) {
    /************** Incorporate all of the needed info ******************/
    display("Incorporating indexed data for training...  \n");
    contexts = di.getContexts();
    outcomes = di.getOutcomeList();
    numTimesEventsSeen = di.getNumTimesEventsSeen();
    numTokens = contexts.length;

    //printTable(contexts);

    // determine the correction constant and its inverse
    constant = contexts[0].length;
    for (TID = 1; TID < contexts.length; TID++) {
      if (contexts[TID].length > constant) {
        constant = contexts[TID].length;
      }
    }
    constantInverse = 1.0 / constant;

    display("done.\n");

    outcomeLabels = di.getOutcomeLabels();
    outcomeList = di.getOutcomeList();
    numOutcomes = outcomeLabels.length;
    iprob = Math.log(1.0 / numOutcomes);

    predLabels = di.getPredLabels();
    numPreds = predLabels.length;

    display("\tNumber of Event Tokens: " + numTokens + "\n");
    display("\t    Number of Outcomes: " + numOutcomes + "\n");
    display("\t  Number of Predicates: " + numPreds + "\n");

    // set up feature arrays
    int[][] predCount = new int[numPreds][numOutcomes];
    for (TID = 0; TID < numTokens; TID++)
      for (int j = 0; j < contexts[TID].length; j++)
        predCount[contexts[TID][j]][outcomeList[TID]] += numTimesEventsSeen[TID];

    //printTable(predCount);
    di = null; // don't need it anymore

    // A fake "observation" to cover features which are not detected in
    // the data.  The default is to assume that we observed "1/10th" of a
    // feature during training.
    final double smoothingObservation = _smoothingObservation;

    // Get the observed expectations of the features. Strictly speaking,
    // we should divide the counts by the number of Tokens, but because of
    // the way the model's expectations are approximated in the
    // implementation, this is cancelled out when we compute the next
    // iteration of a parameter, making the extra divisions wasteful.
    params = new MutableContext[numPreds];
    modelExpects = new MutableContext[numPreds];
    observedExpects = new MutableContext[numPreds];
    
    int[] activeOutcomes = new int[numOutcomes];
    int[] outcomePattern;
    int[] allOutcomesPattern= new int[numOutcomes];
    for (OID = 0; OID < numOutcomes; OID++) {
      allOutcomesPattern[OID] = OID;
    }
    int numActiveOutcomes = 0;
    for (PID = 0; PID < numPreds; PID++) {
      numActiveOutcomes = 0;
      if (_simpleSmoothing) {
        numActiveOutcomes = numOutcomes;
        outcomePattern = allOutcomesPattern;
      }
      else { //determine active outcomes
        for (OID = 0; OID < numOutcomes; OID++) {
          if (predCount[PID][OID] > 0) {
            activeOutcomes[numActiveOutcomes] = OID;
            numActiveOutcomes++;
          }
        }
        if (numActiveOutcomes == numOutcomes) {
          outcomePattern = allOutcomesPattern;
        }
        else {
          outcomePattern = new int[numActiveOutcomes];
          for (int aoi=0;aoi<numActiveOutcomes;aoi++) {
            outcomePattern[aoi] = activeOutcomes[aoi];
          }
        }
      }
      params[PID] = new MutableContext(outcomePattern,new double[numActiveOutcomes]);
      modelExpects[PID] = new MutableContext(outcomePattern,new double[numActiveOutcomes]);
      observedExpects[PID] = new MutableContext(outcomePattern,new double[numActiveOutcomes]);
      for (int aoi=0;aoi<numActiveOutcomes;aoi++) {
        OID = outcomePattern[aoi];
        params[PID].setParameter(aoi, 0.0);
        modelExpects[PID].setParameter(aoi, 0.0);
        if (predCount[PID][OID] > 0) {
          observedExpects[PID].setParameter(aoi, predCount[PID][OID]);
        }
        else if (_simpleSmoothing) { 
          observedExpects[PID].setParameter(aoi,smoothingObservation);
        }
      }
    }

    // compute the expected value of correction
    if (_useSlackParameter) {
      int cfvalSum = 0;
      for (TID = 0; TID < numTokens; TID++) {
        for (int j = 0; j < contexts[TID].length; j++) {
          PID = contexts[TID][j];
          if (!modelExpects[PID].contains(outcomes[TID])) {
            cfvalSum += numTimesEventsSeen[TID];
          }
        }
        cfvalSum += (constant - contexts[TID].length) * numTimesEventsSeen[TID];
      }
      if (cfvalSum == 0) {
        cfObservedExpect = Math.log(NEAR_ZERO); //nearly zero so log is defined
      }
      else {
        cfObservedExpect = Math.log(cfvalSum);
      }

      correctionParam = 0.0;
    }
    predCount = null; // don't need it anymore

    display("...done.\n");

    modelDistribution = new double[numOutcomes];
    numfeats = new int[numOutcomes];

    /***************** Find the parameters ************************/
    display("Computing model parameters...\n");
    findParameters(iterations);

    /*************** Create and return the model ******************/
    return new GISModel(params, predLabels, outcomeLabels, constant, correctionParam);

  }

  /* Estimate and return the model parameters. */
  private void findParameters(int iterations) {
    double prevLL = 0.0;
    double currLL = 0.0;
    display("Performing " + iterations + " iterations.\n");
    for (int i = 1; i <= iterations; i++) {
      if (i < 10)
        display("  " + i + ":  ");
      else if (i < 100)
        display(" " + i + ":  ");
      else
        display(i + ":  ");
      currLL = nextIteration();
      if (i > 1) {
        if (prevLL > currLL) {
          System.err.println("Model Diverging: loglikelihood decreased");
          break;
        }
        if (currLL - prevLL < LLThreshold) {
          break;
        }
      }
      prevLL = currLL;
    }

    // kill a bunch of these big objects now that we don't need them
    observedExpects = null;
    modelExpects = null;
    numTimesEventsSeen = null;
    contexts = null;
  }

  /**
   * Use this model to evaluate a context and populate the specified outsums array with the
   * likelihood of each outcome given that context.
   *
   * @param context The integers of the predicates which have been
   *                observed at the present decision point.
   */
  public void eval(int[] context, double[] outsums) {
    for (int oid = 0; oid < numOutcomes; oid++) {
      outsums[oid] = iprob;
      numfeats[oid] = 0;
    }
    int[] activeOutcomes;
    double[] activeParameters; 
    for (int i = 0; i < context.length; i++) {
      Context predParams = params[context[i]];
      activeOutcomes = predParams.getOutcomes();
      activeParameters = predParams.getParameters();
      for (int j = 0; j < activeOutcomes.length; j++) {
        int oid = activeOutcomes[j];
        numfeats[oid]++;
        outsums[oid] += constantInverse * activeParameters[j];
      }
    }

    double SUM = 0.0;
    for (int oid = 0; oid < numOutcomes; oid++) {
      outsums[oid] = Math.exp(outsums[oid]);
      if (_useSlackParameter) {
        outsums[oid] += ((1.0 - ((double) numfeats[oid] / constant)) * correctionParam);
      }
      SUM += outsums[oid];
    }

    for (int oid = 0; oid < numOutcomes; oid++)
      outsums[oid] /= SUM;

  }
  
  

  /* Compute one iteration of GIS and retutn log-likelihood.*/
  private double nextIteration() {
    // compute contribution of p(a|b_i) for each feature and the new
    // correction parameter
    double loglikelihood = 0.0;
    CFMOD = 0.0;
    int numEvents = 0;
    int numCorrect = 0;
    for (TID = 0; TID < numTokens; TID++) {
      // TID, modeldistribution and PID are globals used in 
      // the updateModelExpects procedure.  They need to be set.
      eval(contexts[TID], modelDistribution);
      for (int j = 0; j < contexts[TID].length; j++) {
        PID = contexts[TID][j];
        int[] activeOutcomes = modelExpects[PID].getOutcomes();
        for (int aoi=0;aoi<activeOutcomes.length;aoi++) {
          OID = activeOutcomes[aoi];
          modelExpects[PID].updateParameter(aoi,modelDistribution[OID] * numTimesEventsSeen[TID]);
        }
        if (_useSlackParameter) {
          for (OID = 0; OID < numOutcomes; OID++) {
            if (!modelExpects[PID].contains(OID)) {
              CFMOD += modelDistribution[OID] * numTimesEventsSeen[TID];
            }
          }
        }
      }
      if (_useSlackParameter)
        CFMOD += (constant - contexts[TID].length) * numTimesEventsSeen[TID];

      loglikelihood += Math.log(modelDistribution[outcomes[TID]]) * numTimesEventsSeen[TID];
      numEvents += numTimesEventsSeen[TID];
      if (printMessages) {
        int max = 0;
        for (OID = 1; OID < numOutcomes; OID++) {
          if (modelDistribution[OID] > modelDistribution[max]) {
            max = OID;
          }
        }
        if (max == outcomes[TID]) {
          numCorrect += numTimesEventsSeen[TID];
        }
      }

    }
    display(".");

    // compute the new parameter values
    for (PID = 0; PID < numPreds; PID++) {
      double[] observed = observedExpects[PID].getParameters();
      double[] model = modelExpects[PID].getParameters();
      int[] activeOutcomes = params[PID].getOutcomes();
      for (int aoi=0;aoi<activeOutcomes.length;aoi++) {
        params[PID].updateParameter(aoi,(Math.log(observed[aoi])) - Math.log(model[aoi]));
        modelExpects[PID].setParameter(aoi,0.0); // re-initialize to 0.0's
      }
    }
    if (CFMOD > 0.0 && _useSlackParameter)
      correctionParam += (cfObservedExpect - Math.log(CFMOD));

    display(". loglikelihood=" + loglikelihood + "\t" + ((double) numCorrect / numEvents) + "\n");
    return (loglikelihood);
  }

  private void display(String s) {
    if (printMessages)
      System.out.print(s);
  }

}
