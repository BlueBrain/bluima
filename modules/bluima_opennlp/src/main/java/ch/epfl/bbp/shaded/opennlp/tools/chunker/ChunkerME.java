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
package ch.epfl.bbp.shaded.opennlp.tools.chunker;

import java.util.Arrays;
import java.util.List;

import ch.epfl.bbp.shaded.opennlp.maxent.GISModel;
import ch.epfl.bbp.shaded.opennlp.maxent.MaxentModel;
import ch.epfl.bbp.shaded.opennlp.maxent.TwoPassDataIndexer;
import ch.epfl.bbp.shaded.opennlp.tools.util.BeamSearch;
import ch.epfl.bbp.shaded.opennlp.tools.util.Sequence;


/**
 * The class represents a maximum-entropy-based chunker.  Such a chunker can be used to
 * find flat structures based on sequence inputs such as noun phrases or named entities.
 *
 */
public class ChunkerME implements Chunker {

  /** The beam used to search for sequences of chunk tag assignments. */
  protected BeamSearch beam;
  private Sequence bestSequence;
  /** The model used to assign chunk tags to a sequence of tokens. */
  protected MaxentModel model;

  /**
   * Creates a chunker using the specified model.
   * @param mod The maximum entropy model for this chunker.
   */
  public ChunkerME(MaxentModel mod) {
    this(mod, new DefaultChunkerContextGenerator(), 10);
  }

  /**
   * Creates a chunker using the specified model and context generator.
   * @param mod The maximum entropy model for this chunker.
   * @param cg The context generator to be used by the specified model.
   */
  public ChunkerME(MaxentModel mod, ChunkerContextGenerator cg) {
    this(mod, cg, 10);
  }

  /**
   * Creates a chunker using the specified model and context generator and decodes the
   * model using a beam search of the specified size.
   * @param mod The maximum entropy model for this chunker.
   * @param cg The context generator to be used by the specified model.
   * @param beamSize The size of the beam that should be used when decoding sequences.
   */
  public ChunkerME(MaxentModel mod, ChunkerContextGenerator cg, int beamSize) {
    beam = new ChunkBeamSearch(beamSize, cg, mod);
    this.model = mod;
  }

  /* inherieted javadoc */
  public List chunk(List toks, List tags) {
    bestSequence =
        beam.bestSequence(toks, new Object[] { (String[]) tags.toArray(new String[tags.size()]) });
    return bestSequence.getOutcomes();
  }
  
  /* inherieted javadoc */
  public String[] chunk(Object[] toks, String[] tags) {
    bestSequence = beam.bestSequence(Arrays.asList(toks), new Object[] {tags});
    List c = bestSequence.getOutcomes();
    return (String[]) c.toArray(new String[c.size()]);
  }

  /** 
   * This method determines wheter the outcome is valid for the preceeding sequence.  
   * This can be used to implement constraints on what sequences are valid.  
   * @param outcome The outcome.
   * @param sequence The precceding sequence of outcome assignments. 
   * @return true is the outcome is valid for the sequence, false otherwise.
   */
  protected boolean validOutcome(String outcome, Sequence sequence) {
    return (true);
  }
  
  /** 
    * This method determines wheter the outcome is valid for the preceeding sequence.  
    * This can be used to implement constraints on what sequences are valid.  
    * @param outcome The outcome.
    * @param sequence The precceding sequence of outcome assignments. 
    * @return true is the outcome is valid for the sequence, false otherwise.
    */
  protected boolean validOutcome(String outcome, String[] sequence) {
    return (true);
  }

  /**
   * This class implements the abstract BeamSearch class to allow for the chunker to use
   * the common beam search code. 
   *
   */
  class ChunkBeamSearch extends BeamSearch {
    
    public ChunkBeamSearch(int size, ChunkerContextGenerator cg, MaxentModel model) {
      super(size, cg, model);
    }
    
    /* inherieted java doc */
    protected boolean validSequence(int i, List sequence, Sequence s, String outcome) {
      return validOutcome(outcome, s);
    }
    
    protected boolean validSequence(int i, Object[] sequence, String[] s, String outcome) {
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

  
  private static GISModel train(ch.epfl.bbp.shaded.opennlp.maxent.EventStream es, int iterations, int cut) throws java.io.IOException {
    return ch.epfl.bbp.shaded.opennlp.maxent.GIS.trainModel(iterations, new TwoPassDataIndexer(es, cut));
  }

  /**
   * Trains the chunker using the specified parameters. <br>
   * Usage: ChunkerME trainingFile modelFile. <br>
   * Training file should be one word per line where each line consists of a 
   * space-delimited triple of "word pos outcome".  Sentence breaks are indicated by blank lines.
   * @param args The training file and the model file.
   * @throws java.io.IOException When the specifed files can not be read.
   */
  public static void main(String[] args) throws java.io.IOException {
    if (args.length == 0) {
      System.err.println("Usage: ChunkerME trainingFile modelFile");
      System.err.println();
      System.err.println("Training file should be one word per line where each line consists of a ");
      System.err.println("space-delimited triple of \"word pos outcome\".  Sentence breaks are indicated by blank lines.");
      System.exit(1);
    }
    java.io.File inFile = new java.io.File(args[0]);
    java.io.File outFile = new java.io.File(args[1]);
    GISModel mod;
    ch.epfl.bbp.shaded.opennlp.maxent.EventStream es = new ChunkerEventStream(new ch.epfl.bbp.shaded.opennlp.maxent.PlainTextByLineDataStream(new java.io.FileReader(inFile)));
    if (args.length > 3)
      mod = train(es, Integer.parseInt(args[2]), Integer.parseInt(args[3]));
    else
      mod = train(es, 100, 5);

    System.out.println("Saving the model as: " + args[1]);
    new ch.epfl.bbp.shaded.opennlp.maxent.io.SuffixSensitiveGISModelWriter(mod, outFile).persist();
  }
}
