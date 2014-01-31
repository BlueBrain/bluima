///////////////////////////////////////////////////////////////////////////////
//Copyright (C) 2003 Thomas Morton
//
//This library is free software; you can redistribute it and/or
//modify it under the terms of the GNU Lesser General Public
//License as published by the Free Software Foundation; either
//version 2.1 of the License, or (at your option) any later version.
//
//This library is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU General Public License for more details.
//
//You should have received a copy of the GNU Lesser General Public
//License along with this program; if not, write to the Free Software
//Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
//////////////////////////////////////////////////////////////////////////////   
package ch.epfl.bbp.shaded.opennlp.tools.lang.english;

import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.StringTokenizer;
import java.io.*;

import ch.epfl.bbp.shaded.opennlp.tools.parser.Parse;
import ch.epfl.bbp.shaded.opennlp.tools.parser.ParserME;


/**
 * Class for storing the English head rules associated with parsing. 
 *
 */
public class HeadRules implements ch.epfl.bbp.shaded.opennlp.tools.parser.HeadRules {
  
  private Map headRules;
  private Set punctSet;
  
  public HeadRules(String ruleDir) throws IOException {
    readHeadRules(ruleDir);
    punctSet = new HashSet();
    punctSet = new HashSet();
    punctSet.add(".");
    punctSet.add(",");
    punctSet.add("``");
    punctSet.add("''");
    //punctSet.add(":");
  }
  
  public HeadRules(Map ruleMap) {
    headRules = ruleMap;
  }
  
  public Set getPunctuationTags() {
    return punctSet;
  }
  
  public Parse getHead(Parse[] constituents, String type) {
    if (constituents[0].getType() == ParserME.TOK_NODE) {
      return null;
    }
    HeadRule hr;
      if (type.equals("NP") || type.equals("NX")) {
        String[] tags1 = { "NN", "NNP", "NNPS", "NNS", "NX", "JJR", "POS" };
        for (int ci = constituents.length - 1; ci >= 0; ci--) {
          for (int ti = tags1.length - 1; ti >= 0; ti--) {
            if (constituents[ci].getType().equals(tags1[ti])) {
              return (constituents[ci].getHead());
            }
          }
        }
        for (int ci = 0; ci < constituents.length; ci++) {
          if (constituents[ci].getType().equals("NP")) {
            return (constituents[ci].getHead());
          }
        }
        String[] tags2 = { "$", "ADJP", "PRN" };
        for (int ci = constituents.length - 1; ci >= 0; ci--) {
          for (int ti = tags2.length - 1; ti >= 0; ti--) {
            if (constituents[ci].getType().equals(tags2[ti])) {
              return (constituents[ci].getHead());
            }
          }
        }
        String[] tags3 = { "JJ", "JJS", "RB", "QP" };
        for (int ci = constituents.length - 1; ci >= 0; ci--) {
          for (int ti = tags3.length - 1; ti >= 0; ti--) {
            if (constituents[ci].getType().equals(tags3[ti])) {
              return (constituents[ci].getHead());
            }
          }
        }
        return (constituents[constituents.length - 1].getHead());
      }
      else if ((hr = (HeadRule) headRules.get(type)) != null) {
        String[] tags = hr.tags;
        int cl = constituents.length;
        int tl = tags.length;
        if (hr.leftToRight) {
          for (int ti = 0; ti < tl; ti++) {
            for (int ci = 0; ci < cl; ci++) {
              if (constituents[ci].getType().equals(tags[ti])) {
                return (constituents[ci].getHead());
              }
            }
          }
          return (constituents[0].getHead());
        }
        else {
          for (int ti = 0; ti < tl; ti++) {
            for (int ci = cl - 1; ci >= 0; ci--) {
              if (constituents[ci].getType().equals(tags[ti])) {
                return (constituents[ci].getHead());
              }
            }
          }
          return (constituents[cl - 1].getHead());
        }
      }
      return (constituents[constituents.length - 1].getHead());
    }
    
  private void readHeadRules(String file) throws IOException {
    BufferedReader str = new BufferedReader(new FileReader(file));
    String line;
    headRules = new HashMap(30);
    while ((line = str.readLine()) != null) {
      StringTokenizer st = new StringTokenizer(line);
      String num = st.nextToken();
      String type = st.nextToken();
      String dir = st.nextToken();
      String[] tags = new String[Integer.parseInt(num)];
      int ti = 0;
      while (st.hasMoreTokens()) {
        tags[ti] = st.nextToken();
        ti++;
      }
      headRules.put(type, new HeadRule(dir.equals("1"), tags));
    }
  }


  private static class HeadRule {
    public boolean leftToRight;
    public String[] tags;
    public HeadRule(boolean l2r, String[] tags) {
      leftToRight = l2r;
      this.tags = tags;
    }
  }
}
