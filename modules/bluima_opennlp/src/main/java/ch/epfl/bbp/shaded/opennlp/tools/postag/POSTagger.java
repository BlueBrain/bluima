package ch.epfl.bbp.shaded.opennlp.tools.postag;

import java.util.List;

/**
 * The interface for part of speech taggers.
 *
 * @author      Jason Baldridge
 * @version $Revision: 1.2 $, $Date: 2003/12/06 18:25:37 $ */

public interface POSTagger {

  /** Assigns the sentence of tokens pos tags.
   * @param sentence The sentece of tokens to be tagged.
   * @return a list of pos tags for each token provided in sentence.
   */
  public List tag(List sentence);

  /** Assigns the sentence of tokens pos tags.
   * @param sentence The sentece of tokens to be tagged.
   * @return an array of pos tags for each token provided in sentence.
   */
  public String[] tag(String[] sentence);

  /** Assigns the sentence of space-delimied tokens pos tags.
   * @param sentence The sentece of space-delimited tokens to be tagged.
   * @return a string of space-delimited pos tags for each token provided in sentence.
   */
  public String tag(String sentence);

}
