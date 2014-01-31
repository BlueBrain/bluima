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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.GZIPOutputStream;

import ch.epfl.bbp.shaded.opennlp.tools.util.CountedNumberedSet;
import ch.epfl.bbp.shaded.opennlp.tools.util.CountedSet;


/**
 * This class allows for the creation and saving of n-gram dictionaries.
 * @author Tom Morton
 *
 */
public class MutableDictionary extends Dictionary {

  List wordCounts;
  
  /**
   * Creates a new empty dictionary with the specified cut-off.  
   * Only n-grams which occur at least cut-off number of times will be saved.
   * @param cutoff The number of times an n-gram needs to occur to be saved.
   */
  public MutableDictionary(int cutoff) {
    super();
    this.cutoff = cutoff;
    wordMap = new CountedNumberedSet();
    nGramFactory = new NGramFactory(wordMap);
    gramSet = new CountedSet();
    wordCounts = new ArrayList();
  }
  
  /**
   * Creates a new dictionary initalized with the contents of the specified dictionary file and with the specified cut-off.  
   * Only n-grams which occur at least cut-off number of times will be saved.
   * @param dictionaryFile
   * @param cutoff The number of times an n-gram needs to occur to be saved.
   * @throws IOException If the specified dictionary file can not be read.
   */
  public MutableDictionary(String dictionaryFile, int cutoff) throws IOException {
    super(dictionaryFile);
    this.cutoff = cutoff;
  }

  protected void loadGrams(DataInputStream input) throws IOException {
    int numGrams = input.readInt();
    CountedSet cgramSet = new CountedSet(numGrams);
    for (int gi=0;gi<numGrams;gi++) {
      int gramLength=input.readInt();
      int[] words = new int[gramLength];
      for (int wi=0;wi<gramLength;wi++) {
        words[wi]=input.readInt();
      }
      cgramSet.setCount(new NGram(words),cutoff);
    }
    gramSet = cgramSet;
  }
  
  /**
   * Adds n-grams for consisting of the specified words of the specified size (and smaller) to this
   * n-gram dictionary.
   * @param words The words from which n-grams are derived.
   * @param size The size of the n-grams to collect.
   */
  public void add(String[] words,int size, boolean unigrams) {
    List gram = new ArrayList(size);
    //create uni-grams so n-grams can be created.
    if (unigrams) {
      for (int wi=0,wn=words.length;wi<wn;wi++) {
        wordMap.add(words[wi]);
      }
    }
    for (int wi=0,wn=words.length;wi<wn;wi++) {
      //create all n-gram which start with wi
      gram.clear();
      gram.add(words[wi]);
      for (int gi=2;gi<=size;gi++) {
        if (wi+gi-1 < words.length) {
          gram.add(words[wi+gi-1]);
          NGram ngram = nGramFactory.createNGram(gram);
          if (ngram != null) {
            gramSet.add(ngram);
          }
          else {
            throw new NullPointerException();
          }
        }
      }
    }
  }

  /**
   * Save this n-gram dictionary to the specified file.
   * @param file The file to store the n-gram dictionary.
   * @throws IOException If the file can not be written.
   */
  public void persist(File file) throws IOException {
    //System.err.println("Writting "+wordMap.size()+" words and "+gramSet.size()+" n-grams");
    DataOutputStream output = new DataOutputStream(new GZIPOutputStream(new FileOutputStream(file)));
    output.writeUTF(FILE_TYPE);
    System.err.println("pruning from "+wordMap.size());
    for (Iterator ki=wordMap.iterator();ki.hasNext();) {
      String key = (String) ki.next();
      if (((CountedNumberedSet) wordMap).getCount(key) < cutoff) {
        ki.remove();
      }
    }
    System.err.println("pruning to "+wordMap.size());
    output.writeInt(wordMap.size());
    for (Iterator ki=wordMap.iterator();ki.hasNext();) {
      String key = (String) ki.next();
      output.writeUTF(key);
      output.writeInt(wordMap.getIndex(key));
    }
    CountedSet cset = (CountedSet) gramSet;
    int gramCount = 0;
    for (Iterator gi = gramSet.iterator();gi.hasNext();) {
      NGram ngram = (NGram) gi.next();
      if (cset.getCount(ngram) >= cutoff) {
        int[] words = ngram.getWords();
        output.writeInt(words.length);
        for (int wi=0;wi<words.length;wi++) {
          output.writeInt(words[wi]);
        }
        gramCount++;
      }
      else {
        //System.err.println("ngram "+cset.getCount(ngram));
      }
    }
    System.err.println("Wrote out "+gramCount+" n-grams");
    output.close();
  }

}
