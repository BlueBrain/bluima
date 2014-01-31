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
package ch.epfl.bbp.shaded.opennlp.tools.namefind;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import ch.epfl.bbp.shaded.opennlp.tools.util.Cache;
import ch.epfl.bbp.shaded.opennlp.tools.util.Sequence;



/** Class for determining contextual features for a tag/chunk style named-entity recognizer.
 */
public class DefaultNameContextGenerator implements NameContextGenerator {

  /* -------------------- patterns ------------------ */

  private Pattern lowercase;
  private Pattern twoDigits;
  private Pattern fourDigits;
  private Pattern containsNumber;
  private Pattern containsLetter;
  private Pattern containsHyphens;
  private Pattern containsBackslash;
  private Pattern containsComma;
  private Pattern containsPeriod;
  private Pattern allCaps;
  private Pattern capPeriod;
  private Pattern initialCap;
  
  private Cache contextsCache;
  private Object wordsKey;
  private int pi = -1;
  private List prevStaticFeatures;

  /**
   * Creates a name context generator.
   */
  public DefaultNameContextGenerator() {
    this(0);
  }

  /**
   * Creates a name context generator with the specified cach size.
   */
  public DefaultNameContextGenerator(int cacheSize) {
    super();
    initPatterns();
    if (cacheSize > 0) {
      contextsCache = new Cache(cacheSize);
    }
  }

  private void initPatterns() {
    lowercase = Pattern.compile("^[a-z]+$");
    twoDigits = Pattern.compile("^[0-9][0-9]$");
    fourDigits = Pattern.compile("^[0-9][0-9][0-9][0-9]$");
    containsNumber = Pattern.compile("[0-9]");
    containsLetter = Pattern.compile("[a-zA-Z]");
    containsHyphens = Pattern.compile("-");
    containsBackslash = Pattern.compile("/");
    containsComma = Pattern.compile(",");
    containsPeriod = Pattern.compile("\\.");
    allCaps = Pattern.compile("^[A-Z]+$");
    capPeriod = Pattern.compile("^[A-Z]\\.$");
    initialCap = Pattern.compile("^[A-Z]");
  }

  public String[] getContext(Object o) {
    Object[] data = (Object[]) o;
    return (getContext(((Integer) data[0]).intValue(), (List) data[1], (List) data[2], (Map) data[3]));
  }
  
  public String[] getContext(int index, List sequence, Sequence s, Object[] additionalContext) {
    return getContext(index,sequence,s.getOutcomes(),(Map) additionalContext[0]);
  }

  public String[] getContext(int i, List toks, List preds, Map prevTags) {
    return (getContext(i, toks.toArray(), (String[]) preds.toArray(new String[preds.size()]),prevTags));
  }
  
  public String[] getContext(int index, Object[] sequence, String[] priorDecisions, Object[] additionalContext) {
    return getContext(index,sequence,priorDecisions,(Map) additionalContext[0]);
  }

  

  /**
   * Return the context for finding names at the specified index.
   * @param i The index of the token in the specified toks array for which the context should be constructed. 
   * @param toks The tokens of the sentence.  The <code>toString</code> methods of these objects should return the token text.
   * @param preds The previous decisions made in the taging of this sequence.  Only indices less than i will be examined.
   * @param prevTags  A mapping between tokens and the previous outcome for these tokens. 
   * @return the context for finding names at the specified index.
   */
  public String[] getContext(int i, Object[] toks, String[] preds, Map prevTags) {
    String po=NameFinderME.OTHER;
    String ppo=NameFinderME.OTHER;
    if (i > 1){
      ppo = preds[i-2];
    }
    if (i > 0) {
      po = preds[i-1];
    }
    String cacheKey = i+po+ppo;
    if (contextsCache != null) {
      if (wordsKey == toks){
        String[] cachedContexts = (String[]) contextsCache.get(cacheKey);    
        if (cachedContexts != null) {
          return cachedContexts;
        }
      }
      else {
        contextsCache.clear();
        wordsKey = toks;
      }
    }
    List features;
    if (wordsKey == toks && i == pi) {
      features =prevStaticFeatures; 
    }
    else {
      features = getStaticFeatures(toks,i,prevTags);
      pi=i;
      prevStaticFeatures=features;
    }
    
    int fn = features.size();
    String[] contexts = new String[fn+4];
    for (int fi=0;fi<fn;fi++) {
      contexts[fi]=(String) features.get(fi);
    }
    contexts[fn]="po="+po;
    contexts[fn+1]="pow="+po+toks[i];
    contexts[fn+2]="powf="+po+wordFeature(toks[i].toString());
    contexts[fn+3]="ppo="+ppo;
    if (contextsCache != null) {
      contextsCache.put(cacheKey,contexts);
    }
    return contexts;
  }

  /**
    * Returns a list of the features for <code>toks[i]</code> that can
    * be safely cached.  In other words, return a list of all
    * features that do not depend on previous outcome or decision
    * features.  This method is called by <code>search</code>.
    *
    * @param toks The list of tokens being processed.
    * @param i The index of the token whose features should be returned.
    * @return a list of the features for <code>toks[i]</code> that can
    * be safely cached.
    */
  private List getStaticFeatures(Object[] toks, int i, Map prevTags) {
    List feats = new ArrayList();
    feats.add("def");

    //current word
    String w = toks[i].toString().toLowerCase();
    feats.add("w=" + w);
    String wf = wordFeature(toks[i].toString());
    feats.add("wf=" + wf);
    feats.add("w&wf=" + w + "," + wf);
    
    String pt = (String) prevTags.get(toks[i].toString());
    feats.add("pd="+pt);
    if (i == 0) {
      feats.add("df=it");
    }
    // previous previous word
    if (i - 2 >= 0) {
      String ppw = toks[i - 2].toString().toLowerCase();
      feats.add("ppw=" + ppw);
      String ppwf = wordFeature(toks[i - 2].toString());
      feats.add("ppwf=" + ppwf);
      feats.add("ppw&f=" + ppw + "," + ppwf);
    }
    else {
      feats.add("ppw=BOS");
    }
    // previous word
    if (i == 0) {
      feats.add("pw=BOS");
      feats.add("pw=BOS,w=" + w);
      feats.add("pwf=BOS,wf" + wf);
    }
    else {
      String pw = toks[i - 1].toString().toLowerCase();
      feats.add("pw=" + pw);
      String pwf = wordFeature(toks[i - 1].toString());
      feats.add("pwf=" + pwf);
      feats.add("pw&f=" + pw + "," + pwf);
      feats.add("pw=" + pw + ",w=" + w);
      feats.add("pwf=" + pwf + ",wf=" + wf);
    }
    //next word
    if (i + 1 >= toks.length) {
      feats.add("nw=EOS");
      feats.add("w=" + w + ",nw=EOS");
      feats.add("wf=" + wf + ",nw=EOS");
    }
    else {
      String nw = toks[i + 1].toString().toLowerCase();
      feats.add("nw=" + nw);
      String nwf = wordFeature(toks[i + 1].toString());
      feats.add("nwf=" + nwf);
      feats.add("nw&f=" + nw + "," + nwf);
      feats.add("w=" + w + ",nw=" + nw);
      feats.add("wf=" + wf + ",nwf=" + nwf);
    }
    if (i + 2 >= toks.length) {
      feats.add("nnw=EOS");
    }
    else {
      String nnw = toks[i + 2].toString().toLowerCase();
      feats.add("nnw=" + nnw);
      String nnwf = wordFeature(toks[i + 2].toString());
      feats.add("nnwf=" + nnwf);
      feats.add("nnw&f=" + nnw + "," + nnwf);
    }
    //tokenFeatureCache.put(toks[i],Lists.shallowClone(feats));
    return (feats);
  }
  

  /**
    * Returns the most relevant feature for a given word.  This method
    * is called by getCachedFeatures to get the features for words
    * within a window of the word being analyzed.  Typical features
    * are "2d" (2 digits); "4d" (4 digits); and "ac" (all caps).
    * Note that only a single feature is returned.  The default
    * feature is "other".
    *
    * @param word The word whose features should be returned.
    */
  private String wordFeature(String word) {
    //String feat = (String) wordFeatureCache.get(word);
    //if (feat != null) {
    //  return(feat);
    //}
    String feat;
    if (lowercase.matcher(word).find()) {
      feat = "lc";
    }
    else if (twoDigits.matcher(word).find()) {
      feat = "2d";
    }
    else if (fourDigits.matcher(word).find()) {
      feat = "4d";
    }
    else if (containsNumber.matcher(word).find()) {
      if (containsLetter.matcher(word).find()) {
        feat = "an";
      }
      else if (containsHyphens.matcher(word).find()) {
        feat = "dd";
      }
      else if (containsBackslash.matcher(word).find()) {
        feat = "ds";
      }
      else if (containsComma.matcher(word).find()) {
        feat = "dc";
      }
      else if (containsPeriod.matcher(word).find()) {
        feat = "dp";
      }
      else {
        feat = "num";
      }
    }
    else if (allCaps.matcher(word).find() && word.length() == 1) {
      feat = "sc";
    }
    else if (allCaps.matcher(word).find()) {
      feat = "ac";
    }
    else if (capPeriod.matcher(word).find()) {
      feat = "cp";
    }
    else if (initialCap.matcher(word).find()) {
      feat = "ic";
    }
    else {
      feat = "other";
    }
    //wordFeatureCache.put(word,feat);
    //System.err.println("Finder.wordFeature: word="+word+" feat="+feat);
    return (feat);
  }
}
