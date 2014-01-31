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

package ch.epfl.bbp.shaded.opennlp.tools.chunker;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.bbp.shaded.opennlp.tools.util.Sequence;


/** Features based on chunking model described in Fei Sha and Fernando Pereira. Shallow 
 *  parsing with conditional random fields. In Proceedings of HLT-NAACL 2003. Association 
 *  for Computational Linguistics, 2003.
 * @author Tom Morton
  */
public class DefaultChunkerContextGenerator implements ChunkerContextGenerator {

  /**
   * Creates the default context generator a chunker.
   */
  public DefaultChunkerContextGenerator() {
    super();
  }
  
  /* inherieted java doc */
  public String[] getContext(Object o) {
    Object[] data = (Object[]) o;
    List outcomes = ((Sequence) data[2]).getOutcomes();
    return (getContext(((Integer) data[0]).intValue(), (Object[]) data[1], (String[]) data[3], outcomes.toArray(new String[outcomes.size()])));
  }
  
  public String[] getContext(int index, Object[] sequence, String[] priorDecisions, Object[] additionalContext) {
    return getContext(index,sequence,(String[]) additionalContext[0],priorDecisions); 
  }  
  
  public String[] getContext(int i, Object[] toks, String[] tags, String[] preds) {
    List features = new ArrayList(45);
    /** Words in a 5-word window **/
    String w_2, w_1, w0, w1, w2;
    /** Tags in a 5-word window **/
    String t_2, t_1, t0, t1, t2;
    /** Previous predictions **/
    String p_2, p_1;
    if (i < 2) {
      w_2 = "w_2=bos";
      t_2 = "t_2=bos";
      p_2 = "p_2=bos";
    }
    else {
      w_2 = "w_2=" + toks[i - 2];
      t_2 = "t_2=" + tags[i - 2];
      p_2 = "p_2" + preds[i - 2];
    }
    if (i < 1) {
      w_1 = "w_1=bos";
      t_1 = "t_1=bos";
      p_1 = "p_1=bos";
    }
    else {
      w_1 = "w_1=" + toks[i - 1];
      t_1 = "t_1=" + tags[i - 1];
      p_1 = "p_1=" + preds[i - 1];
    }
    w0 = "w0=" + toks[i];
    t0 = "t0=" + tags[i];
    if (i + 1 >= toks.length) {
      w1 = "w1=eos";
      t1 = "t1=eos";
    }
    else {
      w1 = "w1=" + toks[i + 1];
      t1 = "t1=" + tags[i + 1];
    }
    if (i + 2 >= toks.length) {
      w2 = "w2=eos";
      t2 = "t2=eos";
    }
    else {
      w2 = "w2=" + toks[i + 2];
      t2 = "t2=" + tags[i + 2];
    }
    //add word features
    features.add(w_2);
    features.add(w_1);
    features.add(w0);
    features.add(w1);
    features.add(w2);
    features.add(w_1 + w0);
    features.add(w0 + w1);
    //add tag features
    features.add(t_2);
    features.add(t_1);
    features.add(t0);
    features.add(t1);
    features.add(t2);
    features.add(t_2 + t_1);
    features.add(t_1 + t0);
    features.add(t0 + t1);
    features.add(t1 + t2);
    features.add(t_2 + t_1 + t0);
    features.add(t_1 + t0 + t1);
    features.add(t0 + t1 + t2);
    //add pred tags
    features.add(p_2);
    features.add(p_1);
    features.add(p_2 + p_1);
    //add pred and tag
    features.add(p_1 + t_2);
    features.add(p_1 + t_1);
    features.add(p_1 + t0);
    features.add(p_1 + t1);
    features.add(p_1 + t2);
    features.add(p_1 + t_2 + t_1);
    features.add(p_1 + t_1 + t0);
    features.add(p_1 + t0 + t1);
    features.add(p_1 + t1 + t2);
    features.add(p_1 + t_2 + t_1 + t0);
    features.add(p_1 + t_1 + t0 + t1);
    features.add(p_1 + t0 + t1 + t2);
    //add pred and word
    features.add(p_1 + w_2);
    features.add(p_1 + w_1);
    features.add(p_1 + w0);
    features.add(p_1 + w1);
    features.add(p_1 + w2);
    features.add(p_1 + w_1 + w0);
    features.add(p_1 + w0 + w1);
    return ((String[]) features.toArray(new String[features.size()]));
  }

}
