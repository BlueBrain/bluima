package ch.epfl.bbp.shaded.opennlp.tools.lang.spanish;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Collections;

import ch.epfl.bbp.shaded.opennlp.maxent.MaxentModel;
import ch.epfl.bbp.shaded.opennlp.maxent.io.SuffixSensitiveGISModelReader;
import ch.epfl.bbp.shaded.opennlp.tools.namefind.NameContextGenerator;
import ch.epfl.bbp.shaded.opennlp.tools.namefind.NameFinderME;


public class TokenChunker extends NameFinderME {

  
  public TokenChunker(String modelName) throws IOException {
    this(new SuffixSensitiveGISModelReader(new File(modelName)).getModel());
  }
  /**
   * Creates a new token chunker using the specified model.
   * @param model The model to make the chunk decisions.
   */
  public TokenChunker(MaxentModel model) {
    super(model);
  }

  /**
   * Creates a new token chunker using the specified model and context generator.
   * @param model The model to make the chunk decisions.
   * @param cg The context generator to be used with this model.
   */
  public TokenChunker(MaxentModel model, NameContextGenerator cg) {
    super(model, cg);
  }

  /**
   * Creates a new token chunker using the specified model, context generator, 
   * and searches for a chunk sequence using the specified beam size.
   * @param model The model to make the chunk decisions.
   * @param cg The context generator to be used with this model.
   * @param beamSize The size of the beam used for searching for a chunk sequence.
   */
  public TokenChunker(MaxentModel model, NameContextGenerator cg, int beamSize) {
    super(model, cg, beamSize);
  }
  
  public static void main(String[] args) throws IOException {
    if (args.length == 0) {
      System.err.println("Usage: java opennlp.tools.spanish.TokenChunker model < tokenized_sentences");
      System.exit(1);
    }
    TokenChunker chunker = new TokenChunker(args[0]);
    java.io.BufferedReader inReader = new java.io.BufferedReader(new java.io.InputStreamReader(System.in,"ISO-8859-1"));
    PrintStream out = new PrintStream(System.out,true,"ISO-8859-1");
    for (String line = inReader.readLine(); line != null; line = inReader.readLine()) {
      if (line.equals("")) {
        out.println();
      }
      else {
        String[] tokens = line.split(" ");
        String[] chunks = chunker.find(tokens,Collections.EMPTY_MAP);
        //System.err.println(java.util.Arrays.asList(chunks));
        for (int ci=0,cn=chunks.length;ci<cn;ci++) {
          if (ci == 0) {
            out.print(tokens[ci]);
          }
          else if (chunks[ci].equals(NameFinderME.CONTINUE)) {
            out.print("_"+tokens[ci]);
          }
          else {
            out.print(" "+tokens[ci]);
          }
        }
        out.println();
      }
    }
  }
}
