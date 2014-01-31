package ch.epfl.bbp.shaded.opennlp.tools.postag;

/** Interface to determine which tags are valid for a particular word based on a tag dictionary.
 * @author Tom Morton
 */
public interface TagDictionary {
  
  /**
   * Returns a list of valid tags for the specified word. 
   * @param word The word.
   * @return A list of valid tags for the specified word or null if no information is available for that word.
   */
  public String[] getTags(String word);
  
}
