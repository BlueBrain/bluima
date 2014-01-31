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

import java.io.BufferedReader;
import java.io.InputStreamReader;


/** Class for storing n-grams into a hash-like structure.  
 * You should use the {@link NGramFactory} to create these.
 * Only n-grams of size 32 or smaller are handled.  
 * @author Tom Morton
 */
public class NGram {

  private int[] words;

  /** Creates a new n-gram from the specified words. */
  protected NGram(int[] words) {
    super();
    if (words.length <=32) {
      this.words = words;
    }
    else {
      throw(new IllegalArgumentException("Only n-grams of size 32 or smaller can be created: n-gram length = "+words.length));
    }
  }
  
  /**
   * Returns the size of this n-gram (or the value of n).
   * @return the size of this n-gram.
   */
  public int size() {
    return words.length;
  }
  
  /**
   * Returns the integer values for the words which make up this n-gram.
   * @return the integer values for the words which make up this n-gram.
   */
  protected int[] getWords() {
    return words;
  }

  public boolean equals(Object o) {
    NGram ngram = (NGram) o;
    int wn = this.words.length;
    if (wn == ngram.words.length) {
      for (int wi=0;wi<wn;wi++) {
        if (words[wi] != ngram.words[wi]) {
          return false;
        }
      }
      return true;
    }
    else {
      return false;
    }
  }

  public int hashCode() {
    int numBitsRegular = 32 / words.length;
    int numExtra = 32 % words.length;
    int maskExtra = 0xFFFFFFFF >>> (32-(numBitsRegular+1));
    int maskRegular = 0xFFFFFFFF >>> 32-numBitsRegular;    
    int code = 0x000000000;
    int leftMostBit = 0;
    for (int wi=0;wi<words.length;wi++) {
      int word;
      int mask;
      int numBits;
      if (wi < numExtra){
        mask = maskExtra;
        numBits = numBitsRegular+1;
      }
      else {
        mask = maskRegular;
        numBits = numBitsRegular;
      }
      word = words[wi] & mask; // mask off top bits
      word <<= 32-leftMostBit-numBits; // move to correct position
      leftMostBit+=numBits; //set for next interation
      code |= word;
    }
    return code;
  }
  
  public static void main(String[] args) throws java.io.IOException {
    /*
    NGram g1 = new NGram(new int[]{1,2,3});
    NGram g2 = new NGram(new int[]{2,3,4});
    NGram g3 = new NGram(new int[]{1,2,3});
    assert(g1.equals(g3));
    assert(g3.equals(g1));
    assert(!g1.equals(g2));
    assert(!g2.equals(g1));
    assert(g1.hashCode() == g3.hashCode());
    System.err.println("g1 == g3 "+g1.equals(g3));
    opennlp.tools.util.CountedSet cset = new opennlp.tools.util.CountedSet();
    cset.add(g1);
    cset.add(g2);
    cset.add(g3);
    
    for (java.util.Iterator ci = cset.iterator();ci.hasNext();) {
      Object ng = ci.next();
      System.err.println(ng+" "+cset.getCount(ng));
    }
    */
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    for (String line=in.readLine(); null != line; line=in.readLine()) {
      String[] snums = line.split(" ");
      int[] nums = new int[snums.length];
      for (int ni=0;ni<nums.length;ni++){
        nums[ni] = Integer.parseInt(snums[ni]);
      }
      NGram ngram = new NGram(nums);
      int code = ngram.hashCode();
      System.out.println("hashcode="+code+" size="+ngram.size());
      for (int sh = 31; sh >= 0; sh--) {
        System.out.print((code >> sh) & 1);
      }
      System.out.println();
    }
  }
  
}
