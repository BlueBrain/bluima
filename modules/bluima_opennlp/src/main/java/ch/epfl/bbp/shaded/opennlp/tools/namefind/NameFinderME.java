///////////////////////////////////////////////////////////////////////////////
//Copyright (C) 2003 Thomas Morton
// 
//This library is free software; you can redistribute it and/or
//modify it under the terms of the GNU Lesser General Public
//License as published by the Free Software Foundation; either
//version 2.1 of the License, or (at your option) any later version.
// 
//This library is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU Lesser General Public License for more details.
// 
//You should have received a copy of the GNU Lesser General Public
//License along with this program; if not, write to the Free Software
//Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
//////////////////////////////////////////////////////////////////////////////
package ch.epfl.bbp.shaded.opennlp.tools.namefind;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import ch.epfl.bbp.shaded.opennlp.maxent.EventStream;
import ch.epfl.bbp.shaded.opennlp.maxent.GIS;
import ch.epfl.bbp.shaded.opennlp.maxent.GISModel;
import ch.epfl.bbp.shaded.opennlp.maxent.MaxentModel;
import ch.epfl.bbp.shaded.opennlp.maxent.PlainTextByLineDataStream;
import ch.epfl.bbp.shaded.opennlp.maxent.TwoPassDataIndexer;
import ch.epfl.bbp.shaded.opennlp.maxent.io.SuffixSensitiveGISModelWriter;
import ch.epfl.bbp.shaded.opennlp.tools.util.BeamSearch;
import ch.epfl.bbp.shaded.opennlp.tools.util.Sequence;


/**
 * Class for creating a maximum-entropy-based name finder.  
 */
public class NameFinderME implements NameFinder {

  protected MaxentModel _npModel;
  protected NameContextGenerator _contextGen;
  private Sequence bestSequence;
  private BeamSearch beam;

  public static final String START = "start";
  public static final String CONTINUE = "cont";
  public static final String OTHER = "other";

  /**
   * Creates a new name finder with the specified model.
   * @param mod The model to be used to find names.
   */
  public NameFinderME(MaxentModel mod) {
    this(mod, new DefaultNameContextGenerator(10), 10);
  }

  /**
   * Creates a new name finder with the specified model and context generator.
   * @param mod The model to be used to find names.
   * @param cg The context generator to be used with this name finder.
   */
  public NameFinderME(MaxentModel mod, NameContextGenerator cg) {
    this(mod, cg, 10);
  }

  /**
   * Creates a new name finder with the specified model and context generator.
   * @param mod The model to be used to find names.
   * @param cg The context generator to be used with this name finder.
   * @param beamSize The size of the beam to be used in decoding this model.
   */
  public NameFinderME(MaxentModel mod, NameContextGenerator cg, int beamSize) {
    _npModel = mod;
    _contextGen = cg;
    beam = new NameBeamSearch(beamSize, cg, mod, beamSize);
  }
  
  /* inherieted javadoc */
  public List find(List toks, Map prevTags) {
    bestSequence = beam.bestSequence(toks, new Object[] { prevTags });
    return bestSequence.getOutcomes();
  }

  /* inherieted javadoc */
  public String[] find(Object[] toks, Map prevTags) {
    bestSequence = beam.bestSequence(toks, new Object[] { prevTags });
    List c = bestSequence.getOutcomes();
    return (String[]) c.toArray(new String[c.size()]);
  }

  /** 
   * This method determines wheter the outcome is valid for the preceeding sequence.  
   * This can be used to implement constraints on what sequences are valid.  
   * @param outcome The outcome.
   * @param sequence The precceding sequence of outcomes assignments. 
   * @return true is the outcome is valid for the sequence, false otherwise.
   */
  protected boolean validOutcome(String outcome, Sequence sequence) {
    if (outcome.equals(CONTINUE)) {
      List tags = sequence.getOutcomes();
      int li = tags.size() - 1;
      if (li == -1) {
        return false;
      }
      else if (((String) tags.get(li)).equals(OTHER)) {
        return false;
      }
    }
    return true;
  }

  /** 
   * Implementation of the abstract beam search to allow the name finder to use the common beam search code. 
   *
   */
  private class NameBeamSearch extends BeamSearch {

    /**
     * Creams a beam seach of the specified size sing the specified model with the specified context generator.
     * @param size The size of the beam.
     * @param cg The context generator used with the specified model.
     * @param model The model used to determine names.
     */
    public NameBeamSearch(int size, NameContextGenerator cg, MaxentModel model, int beamSize) {
      super(size, cg, model, beamSize);
    }

    protected boolean validSequence(int i, List sequence, Sequence s, String outcome) {
      return validOutcome(outcome, s);
    }
  }

  /**
     * Populates the specified array with the probabilities of the last decoded sequence.  The
     * sequence was determined based on the previous call to <code>chunk</code>.  The 
     * specified array should be at least as large as the numbe of tokens in the previous call to <code>chunk</code>.
     * @param probs An array used to hold the probabilities of the last decoded sequence.
     */
  public void probs(double[] probs) {
    bestSequence.getProbs(probs);
  }
  
  /**
    * Returns an array with the probabilities of the last decoded sequence.  The
    * sequence was determined based on the previous call to <code>chunk</code>.
    * @return An array with the same number of probabilities as tokens were sent to <code>chunk</code>
    * when it was last called.   
    */
  public double[] probs() {
    return bestSequence.getProbs();
  }

  private static GISModel train(EventStream es, int iterations, int cut) throws IOException {
    return GIS.trainModel(iterations, new TwoPassDataIndexer(es, cut));
  }
  
  public static void usage(){
    System.err.println("Usage: opennlp.tools.namefind.NameFinderME -encoding encoding training_file model");
    System.exit(1);
  }

  public static void main(String[] args) throws IOException {
    if (args.length == 0) {
      usage();
    }
    try {
      int ai=0;
      String encoding = null;
      while (args[ai].startsWith("-")) {
        if (args[ai].equals("-encoding")) {
          ai++;
          if (ai < args.length) {
            encoding = args[ai];
            ai++;
          }
          else {
            usage();
          }
        }
      }
      File inFile = new File(args[ai++]);
      File outFile = new File(args[ai++]);
      GISModel mod;

      EventStream es = new NameFinderEventStream(new PlainTextByLineDataStream(new InputStreamReader(new FileInputStream(inFile),encoding)));
      if (args.length > ai)
        mod = train(es, Integer.parseInt(args[ai++]), Integer.parseInt(args[ai++]));
      else
        mod = train(es, 100, 5);

      System.out.println("Saving the model as: " + outFile);
      new SuffixSensitiveGISModelWriter(mod, outFile).persist();

    }
    catch (Exception e) {
      e.printStackTrace();
    }

  }

}
