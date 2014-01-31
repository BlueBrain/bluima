///////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2004 Jason Baldridge, Gann Bierner, and Tom Morton
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

import java.text.DecimalFormat;

/**
 * A maximum entropy model which has been trained using the Generalized
 * Iterative Scaling procedure (implemented in GIS.java).
 *
 * @author      Tom Morton and Jason Baldridge
 * @version     $Revision: 1.14 $, $Date: 2005/10/06 11:03:47 $
 */
public final class GISModel implements MaxentModel {
  	/** Mapping between outcomes and paramater values for each context. 
  	 * The integer representation of the context can be found using <code>pmap</code>.*/
    private final Context[] params;
    /** Maping between predicates/contexts and an integer representing them. */
    private final TObjectIndexHashMap pmap;
    /** The names of the outcomes. */
    private final String[] ocNames;
    private final double correctionConstant;
    private final double correctionParam;
    /** The number of outcomes. */
    private final int numOutcomes;
    private final double iprob;
    private final double fval;
    private DecimalFormat df;

    private int[] numfeats;
    
    public GISModel (Context[] _params,
                     String[] predLabels,
                     String[] _ocNames,
                     int _correctionConstant,
                     double _correctionParam) {

        pmap = new TObjectIndexHashMap(predLabels.length);
        for (int i=0; i<predLabels.length; i++)
            pmap.put(predLabels[i], i);

        params = _params;
        ocNames =  _ocNames;
        correctionConstant = (double)_correctionConstant;
        correctionParam = _correctionParam;
	
        numOutcomes = ocNames.length;
        iprob = Math.log(1.0/numOutcomes);
        fval = 1.0/correctionConstant;
        numfeats = new int[numOutcomes];
    }
    
    /*
    public GISModel (TIntParamHashMap[] _params,
        String[] predLabels,
        String[] _ocNames,
        int _correctionConstant,
        double _correctionParam) {
      this(convertToContexts(_params),predLabels,_ocNames,_correctionConstant,_correctionParam);
    }
    */ 
    
    private static Context[] convertToContexts(TIntParamHashMap[] params) {
      Context[] contexts = new Context[params.length];
      for (int pi=0;pi<params.length;pi++) {
        int[] activeOutcomes = params[pi].keys();
        double[] activeParameters = new double[activeOutcomes.length];
        for (int oi=0;oi<activeParameters.length;oi++) {
          activeParameters[oi] = params[pi].get(activeOutcomes[oi]);
        }
        contexts[pi] = new Context(activeOutcomes,activeParameters);
      }
      return contexts;
    }

    /**
     * Use this model to evaluate a context and return an array of the
     * likelihood of each outcome given that context.
     *
     * @param context The names of the predicates which have been observed at
     *                the present decision point.
     * @return        The normalized probabilities for the outcomes given the
     *                context. The indexes of the double[] are the outcome
     *                ids, and the actual string representation of the
     *                outcomes can be obtained from the method
     *  	      getOutcome(int i).
     */
    public final double[] eval(String[] context) {
      return(eval(context,new double[numOutcomes]));
    }
    
    /**
     * Use this model to evaluate a context and return an array of the
     * likelihood of each outcome given that context.
     *
     * @param context The names of the predicates which have been observed at
     *                the present decision point.
     * @param outsums This is where the distribution is stored.
     * @return        The normalized probabilities for the outcomes given the
     *                context. The indexes of the double[] are the outcome
     *                ids, and the actual string representation of the
     *                outcomes can be obtained from the method
     *                getOutcome(int i).
     */
    public final double[] eval(String[] context, double[] outsums) {
      int[] activeOutcomes;
      double[] activeParameters;
        for (int oid=0; oid<numOutcomes; oid++) {
            outsums[oid] = iprob;
            numfeats[oid] = 0;
        }
        for (int i=0; i<context.length; i++) {
            int contextIndex = pmap.get(context[i]);
            if (contextIndex >= 0) {
                Context predParams = params[contextIndex];
                activeOutcomes = predParams.getOutcomes();
                activeParameters = predParams.getParameters();
                for (int j=0; j<activeOutcomes.length; j++) {
	                int oid = activeOutcomes[j];
	                numfeats[oid]++;
	                outsums[oid] += activeParameters[j];
                }
            }
        }

        double normal = 0.0;
        for (int oid=0; oid<numOutcomes; oid++) {
            outsums[oid] = Math.exp((outsums[oid]*fval)
                                    + ((1.0 -(numfeats[oid]/correctionConstant))
                                       * correctionParam));
            normal += outsums[oid];
        }

        for (int oid=0; oid<numOutcomes; oid++)
            outsums[oid] /= normal;

        return outsums;
    }

    
    /**
     * Return the name of the outcome corresponding to the highest likelihood
     * in the parameter ocs.
     *
     * @param ocs A double[] as returned by the eval(String[] context)
     *            method.
     * @return    The name of the most likely outcome.
     */    
    public final String getBestOutcome(double[] ocs) {
        int best = 0;
        for (int i = 1; i<ocs.length; i++)
            if (ocs[i] > ocs[best]) best = i;
        return ocNames[best];
    }

    
    /**
     * Return a string matching all the outcome names with all the
     * probabilities produced by the <code>eval(String[] context)</code>
     * method.
     *
     * @param ocs A <code>double[]</code> as returned by the
     *            <code>eval(String[] context)</code>
     *            method.
     * @return    String containing outcome names paired with the normalized
     *            probability (contained in the <code>double[] ocs</code>)
     *            for each one.
     */    
    public final String getAllOutcomes (double[] ocs) {
        if (ocs.length != ocNames.length) {
            return "The double array sent as a parameter to GISModel.getAllOutcomes() must not have been produced by this model.";
        }
        else {
            if (df == null) { //lazy initilazation
              df = new DecimalFormat("0.0000");
            }
            StringBuffer sb = new StringBuffer(ocs.length*2);
            sb.append(ocNames[0]).append("[").append(df.format(ocs[0])).append("]");
            for (int i = 1; i<ocs.length; i++) {
                sb.append("  ").append(ocNames[i]).append("[").append(df.format(ocs[i])).append("]");
            }
            return sb.toString();
        }
    }

    
    /**
     * Return the name of an outcome corresponding to an int id.
     *
     * @param i An outcome id.
     * @return  The name of the outcome associated with that id.
     */
    public final String getOutcome(int i) {
        return ocNames[i];
    }

    /**
     * Gets the index associated with the String name of the given outcome.
     *
     * @param outcome the String name of the outcome for which the
     *          index is desired
     * @return the index if the given outcome label exists for this
     * model, -1 if it does not.
     **/
    public int getIndex (String outcome) {
        for (int i=0; i<ocNames.length; i++) {
            if (ocNames[i].equals(outcome))
                return i;
        }
        return -1;
    } 

    public int getNumOutcomes() {
      return(numOutcomes);
    }

    
    /**
     * Provides the fundamental data structures which encode the maxent model
     * information.  This method will usually only be needed by
     * GISModelWriters.  The following values are held in the Object array
     * which is returned by this method:
     *
     * <li>index 0: gnu.trove.TIntDoubleHashMap[] containing the model
     *            parameters  
     * <li>index 1: java.util.Map containing the mapping of model predicates
     *            to unique integers
     * <li>index 2: java.lang.String[] containing the names of the outcomes,
     *            stored in the index of the array which represents their
     * 	          unique ids in the model.
     * <li>index 3: java.lang.Integer containing the value of the models
     *            correction constant
     * <li>index 4: java.lang.Double containing the value of the models
     *            correction parameter
     *
     * @return An Object[] with the values as described above.
     */
    public final Object[] getDataStructures () {
        Object[] data = new Object[5];
        data[0] = params;
        data[1] = pmap;
        data[2] = ocNames;
        data[3] = new Integer((int)correctionConstant);
        data[4] = new Double(correctionParam);
        return data;
    }
}
