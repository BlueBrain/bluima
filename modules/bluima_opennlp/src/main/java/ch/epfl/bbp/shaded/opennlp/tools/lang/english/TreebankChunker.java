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
package ch.epfl.bbp.shaded.opennlp.tools.lang.english;

import java.io.File;
import java.io.IOException;
import java.util.List;

import ch.epfl.bbp.nlp.ModelStream;
import ch.epfl.bbp.shaded.opennlp.maxent.MaxentModel;
import ch.epfl.bbp.shaded.opennlp.maxent.io.SuffixSensitiveGISModelReader;
import ch.epfl.bbp.shaded.opennlp.tools.chunker.ChunkerContextGenerator;
import ch.epfl.bbp.shaded.opennlp.tools.chunker.ChunkerME;
import ch.epfl.bbp.shaded.opennlp.tools.util.Sequence;


/** This is a chunker based on the CONLL chunking task which uses Penn Treebank constituents as the basis for the chunks.
 *   See   http://cnts.uia.ac.be/conll2000/chunking/ for data and task definition.
 * @author Tom Morton
 */
public class TreebankChunker extends ChunkerME {
  
  /**
   * Creates an English Treebank Chunker which uses the specified model file.
   * @param modelFile The name of the maxent model to be used.
   * @throws IOException When the model file can't be open or read.
   */
  public TreebankChunker(String modelFile) throws IOException {
    this(new SuffixSensitiveGISModelReader(new File(modelFile)).getModel());
  }

  /**
   * Creates an English Treebank Chunker which uses the specified model.
   * @param mod The maxent model to be used.
   */
  public TreebankChunker(MaxentModel mod) {
    super(mod);
  }

  /**
   * Creates an English Treebank Chunker which uses the specified model and context generator.
   * @param mod The maxent model to be used.
   * @param cg The context generator to be used.
   */
  public TreebankChunker(MaxentModel mod, ChunkerContextGenerator cg) {
    super(mod, cg);
  }

  /**
   * Creates an English Treebank Chunker which uses the specified model and context generator 
   * which will be decoded using the specified beamSize. 
   * @param mod The maxent model to be used.
   * @param cg The context generator to be used.
   * @param beamSize The size of the beam used for decoding.
   */
  public TreebankChunker(MaxentModel mod, ChunkerContextGenerator cg, int beamSize) {
    super(mod, cg, beamSize);
  }

    /**
     * Creates an English Treebank Chunker which uses the specified model file.
     * 
     * @param modelStream
     *            The input stream of the maxent model to be used.
     * @throws IOException
     *             When the model file can't be open or read.
     */
    public TreebankChunker(ModelStream modelStream) throws IOException {
        this(new SuffixSensitiveGISModelReader(modelStream).getModel());
    }

private boolean validOutcome(String outcome, String prevOutcome) {
    if (outcome.startsWith("I-")) {
      if (prevOutcome == null) {
        return (false);
      }
      else {
        if (prevOutcome.equals("O")) {
          return (false);
        }
        if (!prevOutcome.substring(2).equals(outcome.substring(2))) {
          return (false);
        }
      }
    }
    return (true);
  }
  
  protected boolean validOutcome(String outcome, String[] sequence) {
    String prevOutcome = null;
    if (sequence.length > 0) {
      prevOutcome = sequence[sequence.length-1];
    }
    return validOutcome(outcome,prevOutcome);
  }


  /* inherieted java doc */
  protected boolean validOutcome(String outcome, Sequence sequence) {
    String prevOutcome = null;
    List tagList = sequence.getOutcomes();
    int lti = tagList.size() - 1;
    if (lti >= 0) {
      prevOutcome = (String) tagList.get(lti);
    }
    return validOutcome(outcome,prevOutcome);
  }

  /**
   * Chunks tokenized input from stdin. <br>
   * Usage: java opennlp.tools.chunker.EnglishTreebankChunker model < tokenized_sentences <br>
   */
  public static void main(String[] args) throws IOException {
    if (args.length == 0) {
      System.err.println("Usage: java opennlp.tools.english.TreebankChunker model < tokenized_sentences");
      System.exit(1);
    }
    TreebankChunker chunker = new TreebankChunker(args[0]);
    java.io.BufferedReader inReader = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
    for (String line = inReader.readLine(); line != null; line = inReader.readLine()) {
      if (line.equals("")) {
        System.out.println();
      }
      else {
        String[] tts = line.split(" ");
        String[] tokens = new String[tts.length];
        String[] tags = new String[tts.length];
        for (int ti=0,tn=tts.length;ti<tn;ti++) {
          String[] tt = tts[ti].split("/");
          tokens[ti]=tt[0];
          tags[ti]=tt[1]; 
        }
        String[] chunks = chunker.chunk(tokens,tags);
        //System.err.println(java.util.Arrays.asList(chunks));
        for (int ci=0,cn=chunks.length;ci<cn;ci++) {
          if (ci > 0 && !chunks[ci].startsWith("I-") && !chunks[ci-1].equals("O")) {
            System.out.print(" ]");
          }            
          if (chunks[ci].startsWith("B-")) {
            System.out.print(" ["+chunks[ci].substring(2));
          }
          /*
           else {
           System.out.print(" ");
           }
           */
          System.out.print(" "+tokens[ci]+"/"+tags[ci]);
        }
        if (!chunks[chunks.length-1].equals("O")) {
          System.out.print(" ]");
        }
        System.out.println();
      }
    }
  }
}
