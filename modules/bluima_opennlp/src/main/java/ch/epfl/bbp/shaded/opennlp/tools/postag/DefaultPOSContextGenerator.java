///////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2002 Jason Baldridge, Gann Bierner and Tom Morton
// 
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
// 
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU Lesser General Public License for more details.
// 
// You should have received a copy of the GNU Lesser General Public
// License along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
//////////////////////////////////////////////////////////////////////////////

package ch.epfl.bbp.shaded.opennlp.tools.postag;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import ch.epfl.bbp.shaded.opennlp.tools.ngram.Dictionary;
import ch.epfl.bbp.shaded.opennlp.tools.util.Cache;


/**
 * A context generator for the POS Tagger.
 *
 * @author      Gann Bierner
 * @author      Tom Morton
 * @version     $Revision: 1.10 $, $Date: 2005/11/06 23:14:58 $
 */

public class DefaultPOSContextGenerator implements POSContextGenerator {

  protected final String SE = "*SE*";
  protected final String SB = "*SB*";
  private static final int PREFIX_LENGTH = 4;
  private static final int SUFFIX_LENGTH = 4;

  private static Pattern hasCap = Pattern.compile("[A-Z]");
  private static Pattern hasNum = Pattern.compile("[0-9]");
  
  private Cache contextsCache;
  private Object wordsKey;
  
  private Dictionary dict;
  private String[] dictGram;

  public DefaultPOSContextGenerator(Dictionary dict) {
    this(0,dict);
  }
  
  public DefaultPOSContextGenerator(int cacheSize,Dictionary dict) {
    this.dict = dict;
    dictGram = new String[1];
    if (cacheSize > 0) {
      contextsCache = new Cache(cacheSize);
    }
  }

  public String[] getContext(Object o) {
    Object[] data = (Object[]) o;
    return getContext(((Integer) data[0]).intValue(), (Object[]) data[1], (String[]) data[2], null);
  }

  protected static String[] getPrefixes(String lex) {
    String[] prefs = new String[4];
    for (int li = 0, ll = PREFIX_LENGTH; li < ll; li++) {
      prefs[li] = lex.substring(0, Math.min(li + 1, lex.length()));
    }
    return prefs;
  }

  protected static String[] getSuffixes(String lex) {
    String[] suffs = new String[4];
    for (int li = 0, ll = SUFFIX_LENGTH; li < ll; li++) {
      suffs[li] = lex.substring(Math.max(lex.length() - li - 1, 0));
    }
    return suffs;
  }

  public String[] getContext(int index, Object[] sequence, String[] priorDecisions, Object[] additionalContext) {
    return getContext(index,sequence,priorDecisions);
  }  

  /**
   * Returns the context for making a pos tag decision at the specified token index given the specified tokens and previous tags.
   * @param index The index of the token for which the context is provided. 
   * @param tokens The tokens in the sentence.
   * @param tags The tags assigned to the previous words in the sentence. 
   * @return The context for making a pos tag decision at the specified token index given the specified tokens and previous tags.
   */
  public String[] getContext(int index, Object[] tokens, String[] tags) {
    String next, nextnext, lex, prev, prevprev;
    String tagprev, tagprevprev;
    tagprev = tagprevprev = null;
    next = nextnext = lex = prev = prevprev = null;

    lex = tokens[index].toString();
    if (tokens.length > index + 1) {
      next = tokens[index + 1].toString();
      if (tokens.length > index + 2)
        nextnext = tokens[index + 2].toString();
      else
        nextnext = SE; // Sentence End

    }
    else {
      next = SE; // Sentence End
    }

    if (index - 1 >= 0) {
      prev =  tokens[index - 1].toString();
      tagprev =  tags[index - 1].toString();

      if (index - 2 >= 0) {
        prevprev = tokens[index - 2].toString();
        tagprevprev = tags[index - 2].toString();
      }
      else {
        prevprev = SB; // Sentence Beginning
      }
    }
    else {
      prev = SB; // Sentence Beginning
    }
    String cacheKey = index+tagprev+tagprevprev;
    if (contextsCache != null) {
      if (wordsKey == tokens){
        String[] cachedContexts = (String[]) contextsCache.get(cacheKey);    
        if (cachedContexts != null) {
          return cachedContexts;
        }
      }
      else {
        contextsCache.clear();
        wordsKey = tokens;
      }
    }
    List e = new ArrayList();
    e.add("def");
    // add the word itself
    e.add("w=" + lex);
    dictGram[0] = lex;
    if (dict == null || !dict.contains(dictGram)) {
      // do some basic suffix analysis
      String[] suffs = getSuffixes(lex);
      for (int i = 0; i < suffs.length; i++) {
        e.add("suf=" + suffs[i]);
      }
      
      String[] prefs = getPrefixes(lex);
      for (int i = 0; i < prefs.length; i++) {
        e.add("pre=" + prefs[i]);
      }
      // see if the word has any special characters
      if (lex.indexOf('-') != -1) {
        e.add("h");
      }
      
      if (hasCap.matcher(lex).find()) {
        e.add("c");
      }
      
      if (hasNum.matcher(lex).find()) {
        e.add("d");
      }
    }
    // add the words and pos's of the surrounding context
    if (prev != null) {
      e.add("p=" + prev);
      if (tagprev != null) {
        e.add("t=" + tagprev);
      }
      if (prevprev != null) {
        e.add("pp=" + prevprev);
        if (tagprevprev != null) {
          e.add("t2=" + tagprevprev+","+tagprev);
        }
      }
    }

    if (next != null) {
      e.add("n=" + next);
      if (nextnext != null) {
        e.add("nn=" + nextnext);
      }
    }
    String[] contexts = (String[]) e.toArray(new String[e.size()]);
    if (contextsCache != null) {
      contextsCache.put(cacheKey,contexts);
    }
    return (contexts);
  }
  
}
