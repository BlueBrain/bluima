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
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.zip.GZIPInputStream;

import ch.epfl.bbp.shaded.opennlp.tools.util.NumberedSet;


/**
 * This class allows for the the loading of n-gram dictionaries to facilitate feature generation 
 * for n-gram based models.
 * @see MutableDictionary
 * @author Tom Morton
 *
 */
public class Dictionary {

  /** The name of the file format used for storing a dictionary. */
  public static String FILE_TYPE = "dict";
  /** Mapping between words and a unique integer assigned to each words. 
   * This structure also stores unigrams. **/
  protected NumberedSet wordMap;
  /** Set which contains all n-grams of size two or more. */
  protected Set gramSet;
  /** Specifies the number of time an n-gram needs to occur to be included when all n-grams are saved to a file. */ 
  protected int cutoff;
  /** Factory for creating n-grams. */
  protected NGramFactory nGramFactory;
  
  /**
   * Creates a new empty dictionary of n-grams.
   *
   */
  protected Dictionary() {}

  /** Constructor used to load a previously created dictionary for the specifed dictionary file.
   * @param dictionaryFile A file storing a dictionary.
   */
  public Dictionary(String dictionaryFile) throws IOException {
    DataInputStream input = new DataInputStream(new GZIPInputStream(new FileInputStream(new File(dictionaryFile))));
    input.readUTF();
    int numWords = input.readInt();
    //System.err.println("Reading: "+numWords+" words");
    wordMap = new NumberedSet(numWords);
    for (int wi=0;wi<numWords;wi++) {
      String word = input.readUTF();
      int index = input.readInt();
      wordMap.setIndex(word,index);
    }
    loadGrams(input);
    nGramFactory = new NGramFactory(wordMap);
  }
  
  /**
   * Loads the contents of the specified input stream into this dictionary. 
   * @param input A dictionary file.
   * @throws IOException If the specified input stream can not be read. 
   */
  protected void loadGrams(DataInputStream input) throws IOException {
    gramSet = new HashSet();
    try {
      while(true) {
        int gramLength=input.readInt();
      
        int[] words = new int[gramLength];
        for (int wi=0;wi<gramLength;wi++) {
          words[wi]=input.readInt();
        }
        gramSet.add(new NGram(words));
      }
    }
    catch(EOFException e) {
      
    }
  }
  
  /**
   * Returns true if this dictionary contains the n-gram consisting of the specified words.
   * @param words The words which make up the n-gram to look up in the dictionary.
   * @return true if this dictionary contains the specified n-gram; false otherwise.
   */
  public boolean contains(String[] words) {
    if (words.length == 1) {
      return wordMap.contains(words[0]);
    }
    else {
      NGram ngram = nGramFactory.createNGram(words);
      if (ngram == null) {
        return false;
      }
      else {
        return gramSet.contains(ngram);
      }
    }
  }
  
  /**
   * Returns an iterator over all n-grams in this dictionary.
   * @return an iterator over all n-grams in this dictionary.
   */
  public Iterator iterator() {
    return new DictionaryIterator(this);
  }
  
  /**
   * Allows a dictionery to be queried for specific n-grams from the command-line from statndard in using space delimited n-grams on a single line.
   * @param args The dictionary file. 
   * @throws IOException The dictionary file can not be read.
   */
  public static void main(String[] args) throws IOException {
    if (args.length == 0) {
      System.err.println("Usage: Dictionary dictionary_file");
      System.exit(0);
    }
    Dictionary dict = new Dictionary(args[0]);
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    String line;
    while (null != (line = in.readLine())) {
      String[] words = line.split(" ");
      if (dict.contains(words)) {
        System.out.println("Dictionary contains: "+line);
      }
      else {
        System.out.println("Dictionary does not contain: "+line);
      }
    }
    /*
    for (Iterator di = dict.iterator();di.hasNext();) {
      System.out.println(di.next());
    }
    */
  }
}

class DictionaryIterator implements Iterator {

  Iterator wordIterator;
  Iterator gramIterator;
  boolean onWords;
  String[] words;
  Dictionary dict;
  
  public DictionaryIterator(Dictionary dict) {
    /*
    words = new String[dict.wordMap.size()+1];
    for (Iterator wi=dict.wordMap.iterator();wi.hasNext();) {
      String word = (String) wi.next();
      words[dict.wordMap.getIndex(word)]=word;
    }
    */
    wordIterator = dict.wordMap.iterator();
    gramIterator = dict.gramSet.iterator();
    onWords = true;
    this.dict = dict;
  }
  public boolean hasNext() {
    if (onWords) {
      if (wordIterator.hasNext()) {
        return true;
      }
      else {
        onWords = false;
      }
    }
    return gramIterator.hasNext();
  }

  public Object next() {
    if (onWords) {
      String word = (String) wordIterator.next(); 
      return word+"="+dict.wordMap.getIndex(word);
    }
    else {
      int[] gramInts = ((NGram) gramIterator.next()).getWords();
      StringBuffer sb = new StringBuffer();
      for (int gi=0;gi<gramInts.length;gi++) {
        //sb.append(words[gramInts[gi]]).append(",");
        sb.append(gramInts[gi]).append(",");
      }
      sb.setLength(sb.length()-1);
      return sb.toString();
    }
  }

  public void remove() {
    throw new UnsupportedOperationException("DictionaryIterator does not allow removal");
  }
  
  
}
