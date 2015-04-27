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
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/** Provides a means of determining which tags are valid for a particular word based on a tag dictionary read from a file.
 * @author Tom Morton
 */
public class POSDictionary implements TagDictionary {

  private Map dictionary;
  private boolean caseSensitive;

  public POSDictionary(String file) throws IOException {
    this(file, true);
  }

  /**
   * Create tag dictionary object with contents of specified file and using specified case to determine how to access entries in the tag dictionary.
   * @param file The file name for the tag dictionary.
   * @param caseSensitive Specifies whether the tag dictionary is case sensitive or not.
   * @throws IOException when the specified file can not be read.
   */
  public POSDictionary(String file, boolean caseSensitive) throws IOException {
    this(new BufferedReader(new FileReader(file)), caseSensitive);
  }

  /**
   * Create tag dictionary object with contents of specified file and using specified case to determine how to access entries in the tag dictionary.
   * @param reader A reader for the tag dictionary.
   * @param caseSensitive Specifies whether the tag dictionary is case sensitive or not.
   * @throws IOException when the specified file can not be read.
   */
  public POSDictionary(BufferedReader reader, boolean caseSensitive) throws IOException {
    dictionary = new HashMap();
    this.caseSensitive = caseSensitive;
    for (String line = reader.readLine(); line != null; line = reader.readLine()) {
      String[] parts = line.split(" ");
      String[] tags = new String[parts.length - 1];
      for (int ti = 0, tl = parts.length - 1; ti < tl; ti++) {
        tags[ti] = parts[ti + 1];
      }
      dictionary.put(parts[0], tags);
    }
  }

  /**
   * Returns a list of valid tags for the specified word. 
   * @param word The word.
   * @return A list of valid tags for the specified word or null if no information is available for that word.
   */
  public String[] getTags(String word) {
    if (caseSensitive) {
      return (String[]) dictionary.get(word);
    }
    else {
      //System.err.println(java.util.Arrays.asList((String[]) dictionary.get(word.toLowerCase())));
      return (String[]) dictionary.get(word.toLowerCase());
    }
  }
  
  public static void main(String[] args) throws IOException {
    POSDictionary dict = new POSDictionary(args[0]);
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    for (String line = in.readLine();line != null;line = in.readLine()) {
      System.out.println(Arrays.asList(dict.getTags(line)));
    }
  }

}
