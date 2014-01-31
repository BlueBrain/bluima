///////////////////////////////////////////////////////////////////////////////
//Copyright (C) 2005 Thomas Morton
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
package ch.epfl.bbp.shaded.opennlp.tools.ngram;

import java.util.Arrays;
import java.util.List;

import ch.epfl.bbp.shaded.opennlp.tools.util.NumberedSet;


/** This class is used to create NGrams with a particular mapping of words to integers.
 * @author Tom Morton
 *
 */
public class NGramFactory {

  private NumberedSet wordMap;
  
  /**
   * Create a new n-gram factory which uses the specified mapping of words to integers to
   * construct its n-grams.
   * @param wordMap A mapping from words to integers.
   */
  public NGramFactory(NumberedSet wordMap) {
    super();
    this.wordMap = wordMap;
  }
  
  /**
   * Creates an n-gram object for the specified array of words.
   * @param words The words which make up the n-gram.
   * @return an n-gram object for the specified array words.
   */
  public NGram createNGram(String[] words) {
    return createNGram(Arrays.asList(words));
  }
  
  /** 
   * Creates a new n-gram for the specified list of words.
   * @param words The words which are part of this n-gram.
   * @return the newly created object or null if the n-gram contains an unknown word.
   */
  public NGram createNGram(List words) {
    int[] nums = new int[words.size()]; //ngram needs it own copy of this array so keep making a unique one for each instance.
    for (int wi=0;wi<nums.length;wi++) {
      nums[wi] = wordMap.getIndex(words.get(wi));
      if (nums[wi] == -1) {
        return null;
      }
    }
    return new NGram(nums);
  }

}
