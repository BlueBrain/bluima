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
import java.util.List;

import ch.epfl.bbp.shaded.opennlp.tools.chunker.ChunkerContextGenerator;
import ch.epfl.bbp.shaded.opennlp.tools.util.Cache;


/**
 * Creates predivtive context for the pre-chunking phases of parsing.
 */
public class ChunkContextGenerator implements ChunkerContextGenerator {

  private static final String EOS = "eos";
  private Cache contextsCache;
  private Object wordsKey;

  
  public ChunkContextGenerator() {
    this(0);
  }
  
  public ChunkContextGenerator(int cacheSize) {
    super();
    if (cacheSize > 0) {
      contextsCache = new Cache(cacheSize);
    }
  }
  
  public String[] getContext(Object o) {
    Object[] data = (Object[]) o;
    return (getContext(((Integer) data[0]).intValue(), (String[]) data[1], (String[]) data[2], (String[]) data[3]));
  }
    
  public String[] getContext(int i, Object[] words, String[] prevDecisions, Object[] ac) {
    return(getContext(i,words,(String[]) ac[0],prevDecisions));
  }

  public String[] getContext(int i, Object[] words, String[] tags, String[] preds) {
    List features = new ArrayList(19);
    int x0 = i;
    int x_2 = x0 - 2;
    int x_1 = x0 - 1;
    int x2 = x0 + 2;
    int x1 = x0 + 1;
    
    String w_2,w_1,w0,w1,w2;
    String t_2,t_1,t0,t1,t2;
    String p_2,p_1;

    // chunkandpostag(-2)
    if (x_2 >= 0) {
      t_2=tags[x_2];
      p_2=preds[x_2];
      w_2=words[x_2].toString();
    }
    else {
      t_2=EOS;
      p_2=EOS;
      w_2=EOS;
    }

    // chunkandpostag(-1)
    if (x_1 >= 0) {
      t_1=tags[x_1];
      p_1=preds[x_1];
      w_1=words[x_1].toString();
    }
    else {
      t_1=EOS;
      p_1=EOS;
      w_1=EOS;
    }

    // chunkandpostag(0)
    t0=tags[x0];
    w0=words[x0].toString();

    // chunkandpostag(1)
    if (x1 < tags.length) {
      t1=tags[x1];
      w1=words[x1].toString();
    }
    else {
      t1=EOS;
      w1=EOS;
    }

    // chunkandpostag(2)
    if (x2 < tags.length) {
      t2=tags[x2];
      w2=words[x2].toString();
    }
    else {
      t2=EOS;
      w2=EOS;
    }
    
    String cacheKey = x0+t_2+t1+t0+t1+t2+p_2+p_1;
    if (contextsCache!= null) {
      if (wordsKey == words) {
        String[] contexts = (String[]) contextsCache.get(cacheKey);
        if (contexts != null) {
          return contexts;
        }
      }
      else {
        contextsCache.clear();
        wordsKey = words;
      }
    }
    
    String ct_2 = chunkandpostag(-2, w_2, t_2, p_2);
    String ctbo_2 = chunkandpostagbo(-2, t_2, p_2);
    String ct_1 = chunkandpostag(-1, w_1, t_1, p_1);
    String ctbo_1 = chunkandpostagbo(-1, t_1, p_1);
    String ct0 = chunkandpostag(0, w0, t0, null);
    String ctbo0 = chunkandpostagbo(0, t0, null);
    String ct1 = chunkandpostag(1, w1, t1, null);
    String ctbo1 = chunkandpostagbo(1, t1, null);
    String ct2 = chunkandpostag(2, w2, t2, null);
    String ctbo2 = chunkandpostagbo(2, t2, null);
    
    features.add("default");
    features.add(ct_2);
    features.add(ctbo_2);
    features.add(ct_1);
    features.add(ctbo_1);
    features.add(ct0);
    features.add(ctbo0);
    features.add(ct1);
    features.add(ctbo1);
    features.add(ct2);
    features.add(ctbo2);

    //chunkandpostag(-1,0)
    features.add(ct_1 + "," + ct0);
    features.add(ctbo_1 + "," + ct0);
    features.add(ct_1 + "," + ctbo0);
    features.add(ctbo_1 + "," + ctbo0);

    //chunkandpostag(0,1)
    features.add(ct0 + "," + ct1);
    features.add(ctbo0 + "," + ct1);
    features.add(ct0 + "," + ctbo1);
    features.add(ctbo0 + "," + ctbo1);
    String contexts[] = (String[]) features.toArray(new String[features.size()]);
    if (contextsCache != null) {
      contextsCache.put(cacheKey,contexts);
    }
    return (contexts);
  }

  private String chunkandpostag(int i, String tok, String tag, String chunk) {
    StringBuffer feat = new StringBuffer(20);
    feat.append(i).append("=").append(tok).append("|").append(tag);
    if (i < 0) {
      feat.append("|").append(chunk);
    }
    return (feat.toString());
  }

  private String chunkandpostagbo(int i, String tag, String chunk) {
    StringBuffer feat = new StringBuffer(20);
    feat.append(i).append("*=").append(tag);
    if (i < 0) {
      feat.append("|").append(chunk);
    }
    return (feat.toString());
  }
}