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
import ch.epfl.bbp.shaded.opennlp.tools.ngram.Dictionary;


/**
 * Class to generator predictive contexts for deciding how constituents should be combined together.
 * @author Tom Morton
 */
public class BuildContextGenerator implements ContextGenerator {

  private static final String EOS = "eos";
  private boolean zeroBackOff;
  private Dictionary dict;
  private String[] unigram;
  private String[] bigram;
  private String[] trigram;
  
  /**
   * Creates a new context generator for making decisions about combining constitients togehter.
   *
   */
  public BuildContextGenerator() {
    super();
    zeroBackOff = false;
  }
  
  public BuildContextGenerator(Dictionary dict) {
    this();
    this.dict = dict;
    unigram = new String[1];
    bigram = new String[2];
    trigram = new String[3];
  }

  public String[] getContext(Object o) {
    Object[] params = (Object[]) o;
    return getContext((Parse[]) params[0], ((Integer) params[1]).intValue());
  }

  /**
   * Creates punctuation feature for the specified punctuation at the specfied index.
   * @param punct The punctuation which is in context.
   * @param i The index of the punctuation with relative to the parse.
   * @return Punctuation feature for the specified parse and the specified punctuation at the specfied index.
   */
  private String punct(Parse punct, int i) {
    StringBuffer feat = new StringBuffer(5);
    feat.append(i).append("=");
    feat.append(punct.getType());
    return (feat.toString());
  }


  private String cons(Parse p, int i) {
    StringBuffer feat = new StringBuffer(20);
    feat.append(i).append("=");
    if (p != null) {
      if (i < 0) {
        feat.append(p.getLabel()).append("|");
      }
      feat.append(p.getType()).append("|").append(p.getHead().toString());
    }
    else {
      feat.append(EOS);
    }
    return (feat.toString());
  }

  private String consbo(Parse p, int i) { //cons back-off
    StringBuffer feat = new StringBuffer(20);
    feat.append(i).append("*=");
    if (p != null) {
      if (i < 0) {
        feat.append(p.getLabel()).append("|");
      }
      feat.append(p.getType());
    }
    else {
      feat.append(EOS);
    }
    return (feat.toString());
  }
  
  /**
   * Returns the predictive context used to determine how constituent at the specified index 
   * should be combined with other contisuents. 
   * @param constituents The constituents which have yet to be combined into new constituents.
   * @param index The index of the constituent whcihi is being considered.
   * @return the context for building constituents at the specified index.
   */
  public String[] getContext(Parse[] constituents, int index) {
    List features = new ArrayList(100);
    int ps = constituents.length;

    //default 
    features.add("default");
    // cons(-2), cons(-1), cons(0), cons(1), cons(2)
    // cons(-2)
    Parse p_2 = null;
    Parse p_1 = null;
    Parse p0 = null;
    Parse p1 = null;
    Parse p2 = null;
    
    Collection punct1s = null;
    Collection punct2s = null;
    Collection punct_1s = null;
    Collection punct_2s = null;

    if (index - 2 >= 0) {
      p_2 = constituents[index - 2];
    }
    if (index - 1 >= 0) {
      p_1 = constituents[index - 1];
      punct_2s = p_1.getPreviousPunctuationSet();
    }
    p0 = constituents[index];
    punct_1s=p0.getPreviousPunctuationSet();
    punct1s=p0.getNextPunctuationSet();
    
    if (index + 1 < ps) {
      p1 = constituents[index + 1];
      punct2s = p1.getNextPunctuationSet();
    }
    if (index + 2 < ps) {
      p2 = constituents[index + 2];
    }
    
    boolean u_2 = true;
    boolean u_1 = true;
    boolean u0 = true;
    boolean u1 = true;
    boolean u2 = true;
    boolean b_2_1 = true;
    boolean b_10 = true;
    boolean b01 = true;
    boolean b12 = true;
    boolean t_2_10 = true;
    boolean t_101 = true;
    boolean t012 = true;
    
    if (dict != null) {
      
      if (p_2 != null) {
        unigram[0] = p_2.getHead().toString();
        u_2 = dict.contains(unigram);
      }
      
      if (p2 != null) {
        unigram[0] = p2.getHead().toString();
        u2 = dict.contains(unigram);
      }

      unigram[0] = p0.getHead().toString();
      u0 = dict.contains(unigram);
      
      if (p_2 != null && p_1 != null) {
        bigram[0] = p_2.getHead().toString();
        bigram[1] = p_1.getHead().toString();
        b_2_1 = dict.contains(bigram);
        
        trigram[0] = p_2.getHead().toString();
        trigram[1] = p_1.getHead().toString();
        trigram[2] = p0.getHead().toString();
        t_2_10 = dict.contains(trigram);
      }
      if (p_1 != null && p1 != null) {
        trigram[0] = p_1.getHead().toString();
        trigram[1] = p0.getHead().toString();
        trigram[2] = p1.getHead().toString();
        t_101 = dict.contains(trigram);
      }
      if (p_1 != null) {
        unigram[0] = p_1.getHead().toString();
        u_1 = dict.contains(unigram);
        
        //extra check for 2==null case
        b_2_1 = b_2_1 && u_1; 
        t_2_10 = t_2_10 && u_1;
        t_101 = t_101 && u_1;
        
        bigram[0] = p_1.getHead().toString();
        bigram[1] = p0.getHead().toString();
        b_10 = dict.contains(bigram);
      }
      if (p1 != null && p2 != null) {
        bigram[0] = p1.getHead().toString();
        bigram[1] = p2.getHead().toString();
        b12 = dict.contains(bigram);
        
        trigram[0] = p0.getHead().toString();
        trigram[1] = p1.getHead().toString();
        trigram[2] = p2.getHead().toString();
        t012 = dict.contains(trigram);
      }
      if (p1 != null) {
        unigram[0] = p1.getHead().toString();
        u1 = dict.contains(unigram);
        
        //extra check fpr 2==null case
        b12 = b12 && u1;
        t012 = t012 && u1;
        t_101 = t_101 && u1;
        
        bigram[0] = p0.getHead().toString();
        bigram[1] = p1.getHead().toString();
        b01 = dict.contains(bigram);
      }
    }

    String consp_2 = cons(p_2, -2);
    String consp_1 = cons(p_1, -1);
    String consp0 = cons(p0, 0);
    String consp1 = cons(p1, 1);
    String consp2 = cons(p2, 2);

    String consbop_2 = consbo(p_2, -2);
    String consbop_1 = consbo(p_1, -1);
    String consbop0 = consbo(p0, 0);
    String consbop1 = consbo(p1, 1);
    String consbop2 = consbo(p2, 2);

    //features.add(p_1.getHead()+"=u_1="+u_1);
    //features.add(p0.getHead()+"=u0="+u0);
    //features.add(p1.getHead()+"=u1="+u1);
    
    // features.add("stage=cons(i)");
    // cons(-2), cons(-1), cons(0), cons(1), cons(2)
    if (u0) features.add(consp0);
    features.add(consbop0);

    if (u_2) features.add(consp_2);
    features.add(consbop_2);
    if (u_1) features.add(consp_1);
    features.add(consbop_1);
    if (u1) features.add(consp1);
    features.add(consbop1);
    if (u2) features.add(consp2);
    features.add(consbop2);

    //cons(0),cons(1)
    //features.add("stage=cons(0),cons(1)");
    if (punct1s != null) {
      for (Iterator pi=punct1s.iterator();pi.hasNext();) {
        String punct = punct((Parse) pi.next(),1);
        //punct(1);
        features.add(punct);
        //cons(0)punct(1)
        if (u0) features.add(consp0+","+punct);
        features.add(consbop0+","+punct);
        //cons(0)punct(1)cons(1)
        if (b01) features.add(consp0+","+punct+","+consp1);
        if (u1)  features.add(consbop0+","+punct+","+consp1);
        if (u0)  features.add(consp0+","+punct+","+consbop1);
        features.add(consbop0+","+punct+","+consbop1);
      }
    }
    else {
      //cons(0),cons(1)
      if (b01) features.add(consp0 + "," + consp1);
      if (u1)  features.add(consbop0 + "," + consp1);
      if (u0)  features.add(consp0 + "," + consbop1);
      features.add(consbop0 + "," + consbop1);      
    }
    
    //features.add("stage=cons(-1),cons(0)");
    //cons(-1,0)
    if (punct_1s != null) {
      for (Iterator pi=punct_1s.iterator();pi.hasNext();) {
        String punct = punct((Parse) pi.next(),-1);
        //punct(-1)
        features.add(punct);
        //punct(-1)cons(0)
        if (u0) features.add(punct+","+consp0);
        features.add(punct+","+consbop0);
        //cons(-1)punct(-1)cons(0)
        if (b_10) features.add(consp_1+","+punct+","+consp0);
        if (u0)   features.add(consbop_1+","+punct+","+consp0);
        if (u_1)  features.add(consp_1+","+punct+","+consbop0);
        features.add(consbop_1+","+punct+","+consbop0);
      }
    }
    else {
      // cons(-1,0)
      if (b_10) features.add(consp_1 + "," + consp0);
      if (u0) features.add(consbop_1 + "," + consp0);
      if (u_1) features.add(consp_1 + "," + consbop0);
      features.add(consbop_1 + "," + consbop0);      
    }
    //features.add("stage=cons(0),cons(1),cons(2)");
    if (punct2s != null) {
      for (Iterator pi=punct2s.iterator();pi.hasNext();) {
        String punct = punct((Parse) pi.next(),2);
        //punct(2)
        features.add(punct);
      }
      if (punct1s != null) {
        //cons(0),punct(1),cons(1),punct(2),cons(2)
        for (Iterator pi2=punct2s.iterator();pi2.hasNext();) {
          String punct2 = punct((Parse) pi2.next(),2);
          for (Iterator pi1=punct1s.iterator();pi1.hasNext();) {
            String punct1 = punct((Parse) pi1.next(),1);
            if (t012) features.add(consp0   + "," + punct1+","+consp1   + "," + punct2+","+consp2);
            
            if (b12) features.add(consbop0 + "," + punct1+","+consp1   + "," + punct2+","+consp2);
            if (u0 && u2) features.add(consp0   + "," + punct1+","+consbop1 + "," + punct2+","+consp2);
            if (b01) features.add(consp0   + "," + punct1+","+consp1   + "," + punct2+","+consbop2);
            
            if (u2) features.add(consbop0 + "," + punct1+","+consbop1 + "," + punct2+","+consp2);
            if (u1) features.add(consbop0 + "," + punct1+","+consp1   + "," + punct2+","+consbop2);
            if (u0) features.add(consp0   + "," + punct1+","+consbop1 + "," + punct2+","+consbop2);
            
            features.add(consbop0 + "," + punct1+","+consbop1 + "," + punct2+","+consbop2);
            if (zeroBackOff) {
              if (b01) features.add(consp0   + "," + punct1+","+consp1   + "," + punct2);
              if (u1)  features.add(consbop0 + "," + punct1+","+consp1   + "," + punct2);
              if (u0)  features.add(consp0   + "," + punct1+","+consbop1 + "," + punct2);
              features.add(consbop0 + "," + punct1+","+consbop1 + "," + punct2);
            }
          }
        }
      }
      else {
        //cons(0),cons(1),punct(2),cons(2)
        for (Iterator pi2=punct2s.iterator();pi2.hasNext();) {
          String punct2 = punct((Parse) pi2.next(),2);
          if (t012) features.add(consp0   + "," + consp1   + "," + punct2+","+consp2);
          
          if (b12) features.add(consbop0 + "," + consp1   +","  + punct2+ "," + consp2);
          if (u0 && u2) features.add(consp0   + "," + consbop1 + "," + punct2+","+consp2);
          if (b01) features.add(consp0   + "," + consp1   + "," + punct2+","+consbop2);
          
          if (u2) features.add(consbop0 + "," + consbop1 + "," + punct2+","+consp2);
          if (u1) features.add(consbop0 + "," + consp1   + "," + punct2+","+consbop2);
          if (u0) features.add(consp0   + "," + consbop1 + "," + punct2+","+consbop2);
          
          features.add(consbop0 + "," + consbop1 + "," + punct2+","+consbop2);
          
          if (zeroBackOff) {
            if (b01) features.add(consp0   + "," + consp1   + "," + punct2);
            if (u1)  features.add(consbop0 + "," + consp1   + "," + punct2);
            if (u0)  features.add(consp0   + "," + consbop1 + "," + punct2);
            features.add(consbop0 + "," + consbop1 + "," + punct2);
          }
        }
      }
    }
    else {
      if (punct1s != null) {
        //cons(0),punct(1),cons(1),cons(2)
        for (Iterator pi1=punct1s.iterator();pi1.hasNext();) {
          String punct1 = punct((Parse) pi1.next(),1);
          if (t012) features.add(consp0     + "," + punct1   +","+ consp1   +","+consp2);
          
          if (b12) features.add(consbop0    + "," + punct1   +","+ consp1   +","+consp2);
          if (u0 && u2) features.add(consp0 + "," + punct1   +","+ consbop1 +","+consp2);
          if (b01) features.add(consp0      + "," + punct1   +","+ consp1   +","+consbop2);
          
          if (u2) features.add(consbop0     + "," + punct1   +","+ consbop1 +","+consp2);
          if (u1) features.add(consbop0     + "," + punct1   +","+ consp1 +","+consbop2);
          if (u0) features.add(consp0       + "," + punct1   +","+ consbop1 +","+consbop2);   
          
          features.add(consbop0 + "," + punct1   +","+ consbop1 +","+consbop2);
          
          //zero backoff case covered by cons(0)cons(1)
        }
      }
      else {
        //cons(0),cons(1),cons(2)
        if (t012) features.add(consp0   + "," + consp1   + "," + consp2);
        
        if (b12) features.add(consbop0 + "," + consp1   + "," + consp2);
        if (u0 && u2) features.add(consp0   + "," + consbop1 + "," + consp2);
        if (b01) features.add(consp0   + "," + consp1   + "," + consbop2);
        
        if (u2) features.add(consbop0 + "," + consbop1 + "," + consp2);
        if (u1) features.add(consbop0 + "," + consp1   + "," + consbop2);
        if (u0) features.add(consp0   + "," + consbop1 + "," + consbop2);
        
        features.add(consbop0 + "," + consbop1 + "," + consbop2);
      }
    }
    //features.add("stage=cons(-2),cons(-1),cons(0)");
    if (punct_2s != null) {
      for (Iterator pi=punct_2s.iterator();pi.hasNext();) {
        String punct = punct((Parse) pi.next(),-2);
        //punct(-2)
        features.add(punct);
      }
      if (punct_1s != null) {
        //cons(-2),punct(-2),cons(-1),punct(-1),cons(0)
        for (Iterator pi_2=punct_2s.iterator();pi_2.hasNext();) {
          String punct_2 = punct((Parse) pi_2.next(),-2);
          for (Iterator pi_1=punct_1s.iterator();pi_1.hasNext();) {
            String punct_1 = punct((Parse) pi_1.next(),-1);
            if (t_2_10) features.add(consp_2   + "," + punct_2+","+consp_1   + "," + punct_1+","+consp0);
            
            if (b_10) features.add(consbop_2 + "," + punct_2+","+consp_1   + "," + punct_1+","+consp0);
            if (u_2 && u0) features.add(consp_2   + "," + punct_2+","+consbop_1 + "," + punct_1+","+consp0);
            if (b_2_1) features.add(consp_2   + "," + punct_2+","+consp_1   + "," + punct_1+","+consbop0);
            
            if (u0)  features.add(consbop_2 + "," + punct_2+","+consbop_1 + "," + punct_1+","+consp0);
            if (u_1) features.add(consbop_2 + "," + punct_2+","+consp_1   + "," + punct_1+","+consbop0);
            if (u_2) features.add(consp_2   + "," + punct_2+","+consbop_1 + "," + punct_1+","+consbop0);

            features.add(consbop_2 + "," + punct_2+","+consbop_1 + "," + punct_1+","+consbop0);
            if (zeroBackOff) {
              if (b_10) features.add(punct_2+","+consp_1   + "," + punct_1+","+consp0);
              if (u0)   features.add(punct_2+","+consbop_1 + "," + punct_1+","+consp0);
              if (u_1)  features.add(punct_2+","+consp_1   + "," + punct_1+","+consbop0);
              features.add(punct_2+","+consbop_1 + "," + punct_1+","+consbop0);
            }
          }
        }
      }
      else {
        //cons(-2),punct(-2),cons(-1),cons(0)
        for (Iterator pi_2=punct_2s.iterator();pi_2.hasNext();) {
          String punct_2 = punct((Parse) pi_2.next(),-2);
          if (t_2_10) features.add(consp_2   + "," + punct_2+","+consp_1   + ","+consp0);
          
          if (b_10) features.add(consbop_2 + "," + punct_2+","+consp_1   + ","+consp0);
          if (u_2 && u0) features.add(consp_2   + "," + punct_2+","+consbop_1 + ","+consp0);
          if (b_2_1) features.add(consp_2   + "," + punct_2+","+consp_1   + ","+consbop0);
          
          if (u0)  features.add(consbop_2 + "," + punct_2+","+consbop_1 + ","+consp0);
          if (u_1) features.add(consbop_2 + "," + punct_2+","+consp_1   + ","+consbop0);
          if (u_2) features.add(consp_2   + "," + punct_2+","+consbop_1 + ","+consbop0);
          
          features.add(consbop_2 + "," + punct_2+","+consbop_1 + ","+consbop0);
          
          if (zeroBackOff) {
            if (b_10) features.add(punct_2+","+consp_1   + ","+consp0);
            if (u0)   features.add(punct_2+","+consbop_1 + ","+consp0);
            if (u_1)  features.add(punct_2+","+consp_1   + ","+consbop0);
            features.add(punct_2+","+consbop_1 + ","+consbop0);
          }
        }
      }
    }
    else {
      if (punct_1s != null) {
        //cons(-2),cons(-1),punct(-1),cons(0)
        for (Iterator pi_1=punct_1s.iterator();pi_1.hasNext();) {
          String punct_1 = punct((Parse) pi_1.next(),-1);
          if (t_2_10) features.add(consp_2   + "," + consp_1   + "," + punct_1+","+consp0);
          
          if (b_10) features.add(consbop_2 + "," + consp_1   + "," + punct_1+","+consp0);
          if (u_2 && u0) features.add(consp_2   + "," + consbop_1 + "," + punct_1+","+consp0);
          if (b_2_1) features.add(consp_2   + "," + consp_1   + "," + punct_1+","+consbop0);
          
          if (u0)  features.add(consbop_2 + "," + consbop_1 + "," + punct_1+","+consp0);
          if (u_1) features.add(consbop_2 + "," + consp_1 + "," + punct_1+","+consbop0);
          if (u_2) features.add(consp_2   + "," + consbop_1 + "," + punct_1+","+consbop0);

          
          features.add(consbop_2 + "," + consbop_1 + "," + punct_1+","+consbop0);
          
          //zero backoff case covered by cons(-1)cons(0)
        }        
      }
      else {
        //cons(-2),cons(-1),cons(0)
        if (t_2_10) features.add(consp_2   + "," + consp_1   + "," +consp0);
        
        if (b_10) features.add(consbop_2 + "," + consp_1   + "," +consp0);
        if (u_2 && u0) features.add(consp_2   + "," + consbop_1 + "," +consp0);
        if (b_2_1)features.add(consp_2   + "," + consp_1   + "," +consbop0);
        
        if (u0)  features.add(consbop_2 + "," + consbop_1 + "," +consp0);
        if (u_1) features.add(consbop_2 + "," + consp_1   + "," +consbop0);
        if (u_2) features.add(consp_2   + "," + consbop_1 + "," +consbop0);
        
        features.add(consbop_2 + "," + consbop_1 + "," +consbop0);
      }
    }
    //features.add("stage=cons(-1),cons(0),cons(1)");
    if (punct_1s !=null) {
      if (punct1s != null) {
        //cons(-1),punct(-1),cons(0),punct(1),cons(1)
        for (Iterator pi_1=punct_1s.iterator();pi_1.hasNext();) {
          String punct_1 = punct((Parse) pi_1.next(),-1);
          for (Iterator pi1=punct1s.iterator();pi1.hasNext();) {
            String punct1 = punct((Parse) pi1.next(),1);
            if (t_101) features.add(consp_1   + "," + punct_1+","+consp0   + "," + punct1+","+consp1);
            
            if (b01) features.add(consbop_1 + "," + punct_1+","+consp0   + "," + punct1+","+consp1);
            if (u_1 && u1) features.add(consp_1   + "," + punct_1+","+consbop0 + "," + punct1+","+consp1);
            if (b_10) features.add(consp_1   + "," + punct_1+","+consp0   + "," + punct1+","+consbop1);
            
            if (u1)  features.add(consbop_1 + "," + punct_1+","+consbop0 + "," + punct1+","+consp1);
            if (u0)  features.add(consbop_1 + "," + punct_1+","+consp0   + "," + punct1+","+consbop1);
            if (u_1) features.add(consp_1   + "," + punct_1+","+consbop0 + "," + punct1+","+consbop1);
            
            features.add(consbop_1 + "," + punct_1+","+consbop0 + "," + punct1+","+consbop1);
            
            if (zeroBackOff) {
              if (b_10) features.add(consp_1   + "," + punct_1+","+consp0   + "," + punct1);
              if (u0)   features.add(consbop_1 + "," + punct_1+","+consp0   + "," + punct1);
              if (u_1)  features.add(consp_1   + "," + punct_1+","+consbop0 + "," + punct1);
              features.add(consbop_1 + "," + punct_1+","+consbop0 + "," + punct1);
            
              if (b01) features.add(punct_1+","+consp0   + "," + punct1+","+consp1);
              if (u1)  features.add(punct_1+","+consbop0 + "," + punct1+","+consp1);
              if (u0)  features.add(punct_1+","+consp0   + "," + punct1+","+consbop1);
              features.add(punct_1+","+consbop0 + "," + punct1+","+consbop1);
            }
          }
        }
      }
      else {
        //cons(-1),punct(-1),cons(0),cons(1)
        for (Iterator pi_1=punct_1s.iterator();pi_1.hasNext();) {
          String punct_1 = punct((Parse) pi_1.next(),-1);
          if (t_101) features.add(consp_1   + "," + punct_1+","+consp0   + "," + consp1);
          
          if (b01)features.add(consbop_1 + "," + punct_1+","+consp0   + "," + consp1);
          if (u_1 && u1) features.add(consp_1   + "," + punct_1+","+consbop0 + "," + consp1);
          if (u0) features.add(consp_1   + "," + punct_1+","+consp0   + "," + consbop1);
          
          if (u1)  features.add(consbop_1 + "," + punct_1+","+consbop0 + "," + consp1);
          if (u0)  features.add(consbop_1 + "," + punct_1+","+consp0   + "," + consbop1);
          if (u_1) features.add(consp_1   + "," + punct_1+","+consbop0 + "," + consbop1);
          
          features.add(consbop_1 + "," + punct_1+","+consbop0 + "," + consbop1);
          
          if(zeroBackOff) {
            if (b01) features.add(punct_1+","+consp0   + "," + consp1);
            if (u1)  features.add(punct_1+","+consbop0   + "," + consp1);
            if (u0)  features.add(punct_1+","+consp0   + "," + consbop1);
            features.add(punct_1+","+consbop0   + "," + consbop1);
          }
        }
      }
    }
    else {
      if (punct1s != null) {
        //cons(-1),cons(0),punct(1),cons(1)
        for (Iterator pi1=punct1s.iterator();pi1.hasNext();) {
          String punct1 = punct((Parse) pi1.next(),1);
          if (t_101) features.add(consp_1   + "," + consp0   + "," + punct1+","+consp1);
          
          if (b01) features.add(consbop_1 + "," + consp0   + "," + punct1+","+consp1);
          if (u_1 && u1) features.add(consp_1   + "," + consbop0 + "," + punct1+","+consp1);
          if (b_10) features.add(consp_1   + "," + consp0   + "," + punct1+","+consbop1);
          
          if (u1)  features.add(consbop_1 + "," + consbop0 + "," + punct1+","+consp1);
          if (u0)  features.add(consbop_1 + "," + consp0   + "," + punct1+","+consbop1);
          if (u_1) features.add(consp_1   + "," + consbop0 + "," + punct1+","+consbop1);
          
          features.add(consbop_1 + "," + consbop0 + "," + punct1+","+consbop1);
          
          if (zeroBackOff) {
            if (b_10) features.add(consp_1   + "," + consp0   + "," + punct1);
            if (u0)   features.add(consbop_1 + "," + consp0   + "," + punct1);
            if (u_1)  features.add(consp_1   + "," + consbop0 + "," + punct1);
            features.add(consbop_1 + "," + consbop0 + "," + punct1);
          }
        }
      }
      else {
        //cons(-1),cons(0),cons(1)
        if (t_101) features.add(consp_1   + "," + consp0   + "," +consp1);
        
        if (b01)       features.add(consbop_1 + "," + consp0   + "," +consp1);
        if (u_1 && u1) features.add(consp_1   + "," + consbop0 + "," +consp1);
        if (b_10)      features.add(consp_1   + "," + consp0   + "," +consbop1);
        
        if (u1)  features.add(consbop_1 + "," + consbop0 + "," +consp1);
        if (u0)  features.add(consbop_1   + "," + consp0 + "," +consbop1);
        if (u_1) features.add(consp_1   + "," + consbop0 + "," +consbop1);
        
        features.add(consbop_1 + "," + consbop0 + "," +consbop1);
      }
    }

    //features.add("stage=other");
    String p0Word = p0.toString();
    if (p0Word.equals("-RRB-")) {
      for (int pi = index - 1; pi >= 0; pi--) {
        Parse p = constituents[pi];
        if (p.toString().equals("-LRB-")) {
          features.add("bracketsmatch");
          break;
        }
        if (p.getLabel().startsWith(ParserME.START)) {
          break;
        }
      }
    }
    if (p0Word.equals("-RCB-")) {
      for (int pi = index - 1; pi >= 0; pi--) {
        Parse p = constituents[pi];
        if (p.toString().equals("-LCB-")) {
          features.add("bracketsmatch");
          break;
        }
        if (p.getLabel().startsWith(ParserME.START)) {
          break;
        }
      }
    }
    if (p0Word.equals("''")) {
      for (int pi = index - 1; pi >= 0; pi--) {
        Parse p = constituents[pi];
        if (p.toString().equals("``")) {
          features.add("quotesmatch");
          break;
        }
        if (p.getLabel().startsWith(ParserME.START)) {
          break;
        }
      }
    }
    if (p0Word.equals("'")) {
      for (int pi = index - 1; pi >= 0; pi--) {
        Parse p = constituents[pi];
        if (p.toString().equals("`")) {
          features.add("quotesmatch");
          break;
        }
        if (p.getLabel().startsWith(ParserME.START)) {
          break;
        }
      }
    }
    if (p0Word.equals(",")) {
      for (int pi = index - 1; pi >= 0; pi--) {
        Parse p = constituents[pi];
        if (p.toString().equals(",")) {
          features.add("iscomma");
          break;
        }
        if (p.getLabel().startsWith(ParserME.START)) {
          break;
        }
      }
    }
    if (p0Word.equals(".") && index == ps - 1) {
      for (int pi = index - 1; pi >= 0; pi--) {
        Parse p = constituents[pi];
        if (p.getLabel().startsWith(ParserME.START)) {
          if (pi == 0) {
            features.add("endofsentence");
          }
          break;
        }
      }
    }
    return ((String[]) features.toArray(new String[features.size()]));
  }
}
