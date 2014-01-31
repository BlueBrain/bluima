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
//GNU Lesser General Public License for more details.
// 
//You should have received a copy of the GNU Lesser General Public
//License along with this program; if not, write to the Free Software
//Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
//////////////////////////////////////////////////////////////////////////////
package ch.epfl.bbp.shaded.opennlp.tools.parser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import ch.epfl.bbp.shaded.opennlp.maxent.ContextGenerator;


/**
 * Class for generating predictive context for deciding when a constituent is complete.
 * @author Tom Morton
 *
 */
public class CheckContextGenerator implements ContextGenerator {

  private static final String EOS = "eos";
  
  /**
   * Creates a new context generator for generating predictive context for deciding when a constituent is complete.
   */
  public CheckContextGenerator() {
    super();
  }

  public String[] getContext(Object o) {
    Object[] params = (Object[]) o;
    return getContext((Parse[]) params[0], (String) params[1], ((Integer) params[2]).intValue(), ((Integer) params[3]).intValue());
  }

  private void surround(Parse p, int i, String type, Collection punctSet, List features) {
    StringBuffer feat = new StringBuffer(20);
    feat.append("s").append(i).append("=");
    if (punctSet !=null) {
      for (Iterator pi=punctSet.iterator();pi.hasNext();) {
        Parse punct = (Parse) pi.next();
        if (p != null) {
          feat.append(p.getHead().toString()).append("|").append(type).append("|").append(p.getHead().getType()).append("|").append(punct.getType());
        }
        else {
          feat.append(type).append("|").append(EOS).append("|").append(punct.getType());
        }
        features.add(feat.toString());
        
        feat.setLength(0);
        feat.append("s").append(i).append("*=");
        if (p != null) {
          feat.append(type).append("|").append(p.getHead().getType()).append("|").append(punct.getType());
        }
        else {
          feat.append(type).append("|").append(EOS).append("|").append(punct.getType());
        }
        features.add(feat.toString());

        feat.setLength(0);
        feat.append("s").append(i).append("*=");
        feat.append(type).append("|").append(punct.getType());
        features.add(feat.toString());
      }
    }
    else {
      if (p != null) {
        feat.append(p.getHead().toString()).append("|").append(type).append("|").append(p.getHead().getType());
      }
      else {
        feat.append(type).append("|").append(EOS);
      }
      features.add(feat.toString());
      feat.setLength(0);
      feat.append("s").append(i).append("*=");
      if (p != null) {
        feat.append(type).append("|").append(p.getHead().getType());
      }
      else {
        feat.append(type).append("|").append(EOS);
      }
      features.add(feat.toString());
    }
  }

  private void checkcons(Parse p, String i, String type, List features) {
    StringBuffer feat = new StringBuffer(20);
    feat.append("c").append(i).append("=").append(p.getType()).append("|").append(p.getHead().toString()).append("|").append(type);
    features.add(feat.toString());
    feat.setLength(0);
    feat.append("c").append(i).append("*=").append(p.getType()).append("|").append(type);
    features.add(feat.toString());
  }

  private void checkcons(Parse p1, Parse p2, String type, List features) {
    StringBuffer feat = new StringBuffer(20);
    feat.append("cil=").append(type).append(",").append(p1.getType()).append("|").append(p1.getHead().toString()).append(",").append(p2.getType()).append("|").append(p2.getHead().toString());
    features.add(feat.toString());
    feat.setLength(0);
    feat.append("ci*l=").append(type).append(",").append(p1.getType()).append(",").append(p2.getType()).append("|").append(p2.getHead().toString());
    features.add(feat.toString());
    feat.setLength(0);
    feat.append("cil*=").append(type).append(",").append(p1.getType()).append("|").append(p1.getHead().toString()).append(",").append(p2.getType());
    features.add(feat.toString());
    feat.setLength(0);
    feat.append("ci*l*=").append(type).append(",").append(p1.getType()).append(",").append(p2.getType());
    features.add(feat.toString());
  }
  
  /**
   * Returns predictive context for deciding whether the specified constituents between the specified start and end index 
   * can be combined to form a new constituent of the specified type.  
   * @param constituents The constituents which have yet to be combined into new constituents.
   * @param type The type of the new constituent proposed.
   * @param start The first constituent of the proposed constituent.
   * @param end The last constituent of the proposed constituent.
   * @return The predictive context for deciding whether a new constituent should be created.
   */
  public String[] getContext(Parse[] constituents, String type, int start, int end) {
    int ps = constituents.length;
    List features = new ArrayList(100);

    //default 
    features.add("default");

    Parse pstart = constituents[start];
    Parse pend = constituents[end];
    checkcons(pstart, "begin", type, features);
    checkcons(pend, "last", type, features);
    StringBuffer production = new StringBuffer(20);
    StringBuffer punctProduction = new StringBuffer(20);
    production.append("p=").append(type).append("->");
    punctProduction.append("pp=").append(type).append("->");
    for (int pi = start; pi < end; pi++) {
      Parse p = constituents[pi];
      checkcons(p, pend, type, features);
      production.append(p.getType()).append(",");
      punctProduction.append(p.getType()).append(",");
      Collection nextPunct = p.getNextPunctuationSet();
      if (nextPunct != null) {
        for (Iterator pit=nextPunct.iterator();pit.hasNext();) {
          Parse punct = (Parse) pit.next();
          punctProduction.append(punct.getType()).append(",");
        }
      }
    }
    production.append(pend.getType());
    punctProduction.append(pend.getType());
    features.add(production.toString());
    features.add(punctProduction.toString());
    Parse p_2 = null;
    Parse p_1 = null;
    Parse p1 = null;
    Parse p2 = null;
    Collection p1s = constituents[end].getNextPunctuationSet();
    Collection p2s = null;
    Collection p_1s = constituents[start].getPreviousPunctuationSet();
    Collection p_2s = null;
    if (start - 2 >= 0) {
      p_2 = constituents[start - 2];
    }
    if (start - 1 >= 0) {
      p_1 = constituents[start - 1];
      p_2s = p_1.getPreviousPunctuationSet();
    }
    if (end + 1 < ps) {
      p1 = constituents[end + 1];
      p2s = p1.getNextPunctuationSet();
    }
    if (end + 2 < ps) {
      p2 = constituents[end + 2];
    }
    surround(p_1, -1, type, p_1s, features);
    surround(p_2, -2, type, p_2s, features);
    surround(p1, 1, type, p1s, features);
    surround(p2, 2, type, p2s, features);

    return ((String[]) features.toArray(new String[features.size()]));
  }
}
