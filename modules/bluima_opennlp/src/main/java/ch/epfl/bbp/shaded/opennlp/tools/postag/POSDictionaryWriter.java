///////////////////////////////////////////////////////////////////////////////
//Copyright (C) 2004 Thomas Morton
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
package ch.epfl.bbp.shaded.opennlp.tools.postag;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import ch.epfl.bbp.shaded.opennlp.tools.util.CountedSet;


/** 
 * Class for writting a pos-tag-dictionary to a file.
 */
public class POSDictionaryWriter {

  private Writer dictFile;
  private Map dictionary;
  private CountedSet wordCounts;
  private String newline = System.getProperty("line.separator");
  
  public POSDictionaryWriter(String file) throws IOException {
    dictFile = new FileWriter(file);
    dictionary = new HashMap();
    wordCounts = new CountedSet();
  }
  
  public void addEntry(String word, String tag) {
    Set tags = (Set) dictionary.get(word);
    if (tags == null) {
      tags = new HashSet();
      dictionary.put(word,tags);
    }
    tags.add(tag);
    wordCounts.add(word);
  }
  
  public void write() throws IOException {
    write(5);
  }
  
  public void write(int cutoff) throws IOException {
    for (Iterator wi=wordCounts.iterator();wi.hasNext();) {
      String word = (String) wi.next();
      if (wordCounts.getCount(word) >= cutoff) {
        dictFile.write(word);
        Set tags = (Set) dictionary.get(word);
        for (Iterator ti=tags.iterator();ti.hasNext();) {
          dictFile.write(" ");
          dictFile.write((String) ti.next());
        }
        dictFile.write(newline);
      }
    }
    dictFile.close();
  }

  public static void main(String[] args) throws IOException {
    if (args.length == 0) {
      System.err.println("Usage: POSDictionaryWriter dictionary tag_files");
      System.exit(1);
    }
    int ai=0;
    String dictionaryFile = args[ai++];
    POSDictionaryWriter dict = new POSDictionaryWriter(dictionaryFile);
    for (int fi=ai;fi<args.length;fi++) {
      BufferedReader in = new BufferedReader(new FileReader(args[fi]));
      for (String line=in.readLine();line != null; line = in.readLine()) {
        String[] parts = line.split("\\s+");
        for (int pi=0;pi<parts.length;pi++) {
          int index = parts[pi].lastIndexOf('_');
          String word = parts[pi].substring(0,index);
          String tag = parts[pi].substring(index+1);
          dict.addEntry(word,tag);
        }
      }
    }
    dict.write();
  }
}
