/*
 * Copyright 2005 FBK-irst (http://www.fbk.eu)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.itc.irst.tcc.sre.util;


/**
 * TO DO
 *
 * @author		Claudio Giuliano
 * @version 	%I%, %G%
 * @since			1.0
 */
public interface Stemmer
{
  /** 
   * reset() resets the stemmer so it can stem another word.  If you invoke
   * the stemmer by calling add(char) and then stem(), you must call reset()
   * before starting another word.
   */
  public abstract void reset();

  /**
   * Add a character to the word being stemmed.  When you are finished 
   * adding characters, you can call stem(void) to process the word. 
   */ 
  public abstract void add(char ch);
  
  /**
   * Add a character to the word being stemmed.  When you are finished 
   * adding characters, you can call stem(void) to process the word. 
   */ 
  public abstract void add(char[] ch);
  
  /**
   * Returns the length of the word resulting from the stemming process.
   */
  public abstract int getResultLength();
 
  /**
   * Returns a reference to a character buffer containing the results of
   * the stemming process.  You also need to consult getResultLength()
   * to determine the length of the result.
   */
  public abstract char[] getResultBuffer();


  /** 
   * Stem a word provided as a String.  Returns the result as a String.
   */
  public abstract String stem(String s);

  /** Stem a word contained in a char[].  Returns true if the stemming process
   * resulted in a word different from the input.  You can retrieve the 
   * result with getResultLength()/getResultBuffer() or toString(). 
   */
  public abstract boolean stem(char[] word);

  /** Stem a word contained in a portion of a char[] array.  Returns
   * true if the stemming process resulted in a word different from
   * the input.  You can retrieve the result with
   * getResultLength()/getResultBuffer() or toString().  
   */
  public abstract boolean stem(char[] wordBuffer, int offset, int wordLen);

  /** Stem a word contained in a leading portion of a char[] array.
   * Returns true if the stemming process resulted in a word different
   * from the input.  You can retrieve the result with
   * getResultLength()/getResultBuffer() or toString().  
   */
  public abstract boolean stem(char[] word, int wordLen);
  
  /** Stem the word placed into the Stemmer buffer through calls to add().
   * Returns true if the stemming process resulted in a word different
   * from the input.  You can retrieve the result with
   * getResultLength()/getResultBuffer() or toString().  
   */
  public abstract boolean stem();
  
  public abstract boolean stem(int i0);

} // end interface Stemmer
