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
package ch.epfl.bbp.shaded.opennlp.tools.parser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import ch.epfl.bbp.shaded.opennlp.maxent.DataStream;
import ch.epfl.bbp.shaded.opennlp.maxent.GISModel;
import ch.epfl.bbp.shaded.opennlp.maxent.MaxentModel;
import ch.epfl.bbp.shaded.opennlp.maxent.TwoPassDataIndexer;
import ch.epfl.bbp.shaded.opennlp.tools.ngram.Dictionary;
import ch.epfl.bbp.shaded.opennlp.tools.ngram.MutableDictionary;
import ch.epfl.bbp.shaded.opennlp.tools.util.Sequence;
import ch.epfl.bbp.shaded.opennlp.tools.util.Span;


/**
 * Class for a shift reduce style parser based on Adwait Ratnaparkhi's 1998 thesis. 
 * 
 */
public class ParserME {

  /** The maximum number of parses advanced from all preceeding parses at each derivation step. */
  private int M;
  /** The maximum number of parses to advance from a single preceeding parse. */
  private int K;
  /** The minimum total probability mass of advanced outcomes.*/
  private double Q;
  /** The default beam size used if no beam size is given. */
  public static final int defaultBeamSize = 20;
  /** The default amount of probability mass required of advanced outcomes. */
  public static final double defaultAdvancePercentage = 0.95;

  /** Completed parses. */
  private SortedSet parses;
  /** Incomplete parses which will be advanced. */
  private SortedSet odh;
  /** Incomplete parses which have been advanced. */
  private SortedSet ndh;
  /** The pos-tagger that the parser uses. */
  protected ParserTagger tagger; //POS tagger
  /** The chunker that the parser uses to chunk non-recursive structures. */
  protected ParserChunker chunker; //Basal Chunker
  
  private MaxentModel buildModel;
  private MaxentModel checkModel;

  private BuildContextGenerator buildContextGenerator;
  private CheckContextGenerator checkContextGenerator;

  private HeadRules headRules;
  private Set punctSet;

  private double[] bprobs;
  private double[] cprobs;

  public static final String TOP_NODE = "TOP";
  public static final String TOK_NODE = "TK";

  public static final Integer ZERO = new Integer(0);

  /** Prefix for outcomes starting a constituent. */
  public static final String START = "S-";
  /** Prefix for outcomes continuing a constituent. */
  public static final String CONT = "C-";
  /** Outcome for token which is not contained in a basal constituent. */
  public static final String OTHER = "O";

  /** Outcome used when a constituent is complete. */
  public static final String COMPLETE = "c";
  /** Outcome used when a constituent is incomplete. */
  public static final String INCOMPLETE = "i";

  private static final String TOP_START = START + TOP_NODE;
  private int topStartIndex;
  private Map startTypeMap;
  private Map contTypeMap;
  
  private int completeIndex;
  private int incompleteIndex;
  
  private boolean createDerivationString = false;
  private boolean debugOn = false;
  
  protected boolean reportFailedParse;
  
  /**
   * Creates a new parser using the specified models and head rules.
   * @param buildModel The model to assign constituent labels.
   * @param checkModel The model to determine a constituent is complete.
   * @param tagger The model to assign pos-tags.
   * @param chunker The model to assign flat constituent labels.
   * @param headRules The head rules for head word perculation.
   */
  public ParserME(MaxentModel buildModel, MaxentModel checkModel, ParserTagger tagger, ParserChunker chunker, HeadRules headRules) {
  	this(buildModel,checkModel,tagger,chunker,headRules,defaultBeamSize,defaultAdvancePercentage);
  }

  /**
   * Creates a new parser using the specified models and head rules using the specified beam size and advance percentage.
   * @param buildModel The model to assign constituent labels.
   * @param checkModel The model to determine a constituent is complete.
   * @param tagger The model to assign pos-tags.
   * @param chunker The model to assign flat constituent labels.
   * @param headRules The head rules for head word perculation.
   * @param beamSize The number of different parses kept during parsing. 
   * @param advancePercentage The minimal amount of probability mass which advanced outcomes must represent.  
   * Only outcomes which contribute to the top "advancePercentage" will be explored.    
   */
  public ParserME(MaxentModel buildModel, MaxentModel checkModel, ParserTagger tagger, ParserChunker chunker, HeadRules headRules, int beamSize, double advancePercentage) {
    this.tagger = tagger; 
    this.chunker = chunker;
    this.buildModel = buildModel;
    this.checkModel = checkModel;
    this.M = beamSize;
    this.K = beamSize;
    this.Q = advancePercentage;
    reportFailedParse = true;
    bprobs = new double[buildModel.getNumOutcomes()];
    cprobs = new double[checkModel.getNumOutcomes()];
    this.buildContextGenerator = new BuildContextGenerator();
    this.checkContextGenerator = new CheckContextGenerator();
    this.headRules = headRules;
    this.punctSet = headRules.getPunctuationTags();
    odh = new TreeSet();
    ndh = new TreeSet();
    parses = new TreeSet();
    startTypeMap = new HashMap();
    contTypeMap = new HashMap();
    for (int boi = 0, bon = buildModel.getNumOutcomes(); boi < bon; boi++) {
      String outcome = buildModel.getOutcome(boi);
      if (outcome.startsWith(START)) {
        //System.err.println("startMap "+outcome+"->"+outcome.substring(START.length()));
        startTypeMap.put(outcome, outcome.substring(START.length()));
      }
      else if (outcome.startsWith(CONT)) {
        //System.err.println("contMap "+outcome+"->"+outcome.substring(CONT.length()));
        contTypeMap.put(outcome, outcome.substring(CONT.length()));
      }
    }
    topStartIndex = buildModel.getIndex(TOP_START);
    completeIndex = checkModel.getIndex(COMPLETE);
    incompleteIndex = checkModel.getIndex(INCOMPLETE);
  }
  
  /**
   * Specifies whether the parser should report when it was unable to find a parse for
   * a particular sentence.
   * @param errorReporting If true then un-parsed sentences are reported, false otherwise.
   */
  public void setErrorReporting(boolean errorReporting) {
    this.reportFailedParse = errorReporting;
  }
  
  /**
   * Returns the specified number of parses or fewer for the specified tokens. <br>
   * <b>Note:</b> The nodes within
   * the returned parses are shared with other parses and therefore their parent node references will not be consistent
   * with their child node reference.  {@link #setParents setParents} can be used to make the parents consistent
   * with a partuicular parse, but subsequent calls to <code>setParents</code> can invalidate the results of earlier
   * calls.<br>  
   * @param tokens A parse containing the tokens with a single parent node.
   * @param numParses The number of parses desired.
   * @return the specified number of parses for the specified tokens.
   */
  public Parse[] parse(Parse tokens, int numParses) {
  	if (createDerivationString) tokens.setDerivation(new StringBuffer(100));
    odh.clear();
    ndh.clear();
    parses.clear();
    int i = 0; //derivation length
    int maxDerivationLength = 2 * tokens.getChildCount() + 3;
    odh.add(tokens);
    Parse guess = null;
    double bestComplete = -100000; //approximating -infinity/0 in ln domain
    while (parses.size() < M && i < maxDerivationLength) {
      ndh = new TreeSet();
      if (odh.size() > 0) {
        int j = 0;
        for (Iterator pi = odh.iterator(); pi.hasNext() && j < K; j++) { // foearch derivation
          Parse tp = (Parse) pi.next();
          if (tp.getProb() < bestComplete) { //this parse and the ones which follow will never win, stop advancing.
            break;
          }
          if (guess == null && i == 2) {
            guess = tp;
          }
          if (debugOn) {
            System.out.print(i + " " + j + " "+tp.getProb());
            tp.show();
            System.out.println();
          }
          Parse[] nd = null;
          if (0 == i) {
            nd = advanceTags(tp);
          }
          else if (1 == i) {
            if (ndh.size() < K) {
              //System.err.println("advancing ts "+j+" "+ndh.size()+" < "+K);
              nd = advanceChunks(tp,bestComplete);
            }
            else {
              //System.err.println("advancing ts "+j+" prob="+((Parse) ndh.last()).getProb());
              nd = advanceChunks(tp,((Parse) ndh.last()).getProb());
            }
          }
          else { // i > 1
            nd = advanceParses(tp, Q);
          }
          if (nd != null) {
            for (int k = 0, kl = nd.length; k < kl; k++) {
              if (nd[k].complete()) {
                advanceTop(nd[k]);
                if (nd[k].getProb() > bestComplete) {
                  bestComplete = nd[k].getProb();
                }
                parses.add(nd[k]);
              }
              else {
                ndh.add(nd[k]);
              }
            }
          }
          else {
            if (reportFailedParse) {
              System.err.println("Couldn't advance parse "+i+" stage "+j+"!\n");
            }
            advanceTop(tp);
            parses.add(tp);
          }
        }
        i++;
        odh = ndh;
      }
      else {
        break;
      }
    }
    if (parses.size() == 0) {
      if (reportFailedParse) System.err.println("Couldn't find parse for: " + tokens);
      //Parse r = (Parse) odh.first();
      //r.show();
      //System.out.println();
      return new Parse[] {guess};
    }
    else if (numParses == 1){
      return new Parse[] {(Parse) parses.first()};
    }
    else {
      List topParses = new ArrayList(numParses);
      while(!parses.isEmpty() && topParses.size() < numParses) {
        Parse tp = (Parse) parses.first();
        topParses.add(tp);
        parses.remove(tp);
      }
      return (Parse[]) topParses.toArray(new Parse[topParses.size()]);
    }
  }
  
  /**
   * Assigns parent references for the specified parse so that they
   * are consistent with the children references.
   * @param p The parse whose parent references need to be assigned.  
   */
  public static void setParents(Parse p) {
    Parse[] children = p.getChildren();
    for (int ci = 0; ci < children.length; ci++) {
      children[ci].setParent(p);
      setParents(children[ci]);
    }
  }

  /**
   * Returns a parse for the specified parse of tokens.
   * @param tokens The root node of a flat parse containing only tokens. 
   * @return A full parse of the specified tokens or the flat chunks of the tokens if a fullparse could not be found.
   */
  public Parse parse(Parse tokens) {
    Parse p = parse(tokens,1)[0];
    setParents(p);
    return p;
  }

  /**
   * Adds the "TOP" node to the specified parse.
   * @param p The complete parse.
   */
  protected void advanceTop(Parse p) {
    buildModel.eval(buildContextGenerator.getContext(p.getChildren(), 0), bprobs);
    p.addProb(Math.log(bprobs[topStartIndex]));
    checkModel.eval(checkContextGenerator.getContext(p.getChildren(), TOP_NODE, 0, 0), cprobs);
    p.addProb(Math.log(cprobs[completeIndex]));
    p.setType(TOP_NODE);
  }

  /**
   * Determines the mapping between the specified index into the specified parses without punctuation to 
   * the coresponding index into the specified parses.
   * @param index An index into the parses without punctuation.
   * @param nonPunctParses The parses without punctuation.
   * @param parses The parses wit punctuation.
   * @return An index into the specified parses which coresponds to the same node the specified index
   * into the parses with punctuation.
   */
  private int mapParseIndex(int index, Parse[] nonPunctParses, Parse[] parses) {
    int parseIndex = index;
    while (parses[parseIndex] != nonPunctParses[index]) {
      parseIndex++;
    }
    return parseIndex;
  }

  /** Advances the specified parse and returns the an array advanced parses whose probability accounts for
   * more than the speicficed amount of probability mass, Q.
   * @param p The parse to advance.
   * @param Q The amount of probability mass that should be accounted for by the advanced parses. 
   */
  protected Parse[] advanceParses(final Parse p, double Q) {
    double q = 1 - Q;
    /** The closest previous node which has been labeled as a start node. */
    Parse lastStartNode = null;
    /** The index of the closest previous node which has been labeled as a start node. */
    int lastStartIndex = -1;
    /** The type of the closest previous node which has been labeled as a start node. */
    String lastStartType = null;
    /** The index of the node which will be labeled in this iteration of advancing the parse. */
    int advanceNodeIndex;
    /** The node which will be labeled in this iteration of advancing the parse. */
    Parse advanceNode=null;
    Parse[] originalChildren = p.getChildren();
    Parse[] children = collapsePunctuation(originalChildren,punctSet);
    int numNodes = children.length;
    if (numNodes == 0) {
      return null;
    }
    //determines which node needs to be labeled and prior labels.
    for (advanceNodeIndex = 0; advanceNodeIndex < numNodes; advanceNodeIndex++) {
      advanceNode = children[advanceNodeIndex];
      if (advanceNode.getLabel() == null) {
        break;
      }
      else if (startTypeMap.containsKey(advanceNode.getLabel())) {
        lastStartType = (String) startTypeMap.get(advanceNode.getLabel());
        lastStartNode = advanceNode;
        lastStartIndex = advanceNodeIndex;
        //System.err.println("lastStart "+i+" "+lastStart.label+" "+lastStart.prob);
      }
    }
    int originalAdvanceIndex = mapParseIndex(advanceNodeIndex,children,originalChildren);
    List newParsesList = new ArrayList(buildModel.getNumOutcomes());
    //call build
    buildModel.eval(buildContextGenerator.getContext(children, advanceNodeIndex), bprobs);
    double bprobSum = 0;
    while (bprobSum < Q) {
      /** The largest unadvanced labeling. */ 
      int max = 0;
      for (int pi = 1; pi < bprobs.length; pi++) { //for each build outcome
        if (bprobs[pi] > bprobs[max]) {
          max = pi;
        }
      }
      if (bprobs[max] == 0) {
        break;
      }
      double bprob = bprobs[max];
      bprobs[max] = 0; //zero out so new max can be found
      bprobSum += bprob;
      String tag = buildModel.getOutcome(max);
      //System.out.println("trying "+tag+" "+bprobSum+" lst="+lst);
      if (max == topStartIndex) { // can't have top until complete
        continue;
      }
      //System.err.println(i+" "+tag+" "+bprob);
      if (startTypeMap.containsKey(tag)) { //update last start
        lastStartIndex = advanceNodeIndex;
        lastStartNode = advanceNode;
        lastStartType = (String) startTypeMap.get(tag);
      }
      else if (contTypeMap.containsKey(tag)) {
        if (lastStartNode == null || !lastStartType.equals(contTypeMap.get(tag))) {
          continue; //Cont must match previous start or continue
        }
      }
      Parse newParse1 = (Parse) p.clone(); //clone parse
      if (createDerivationString) newParse1.getDerivation().append(max).append("-");
      
      newParse1.setChild(originalAdvanceIndex,tag); //replace constituent labeled
      newParse1.addProb(Math.log(bprob));
      //check
      //String[] context = checkContextGenerator.getContext(newParse1.getChildren(), lastStartType, lastStartIndex, advanceNodeIndex);
      checkModel.eval(checkContextGenerator.getContext(collapsePunctuation(newParse1.getChildren(),punctSet), lastStartType, lastStartIndex, advanceNodeIndex), cprobs);
      //System.out.println("check "+lastStartType+" "+cprobs[completeIndex]+" "+cprobs[incompleteIndex]+" "+tag+" "+java.util.Arrays.asList(context));
      Parse newParse2 = newParse1;
      if (cprobs[completeIndex] > q) { //make sure a reduce is likely
        newParse2 = (Parse) newParse1.clone();
        if (createDerivationString) newParse2.getDerivation().append(1).append(".");
        newParse2.addProb(Math.log(cprobs[1]));
        Parse[] cons = new Parse[advanceNodeIndex - lastStartIndex + 1];
        boolean flat = true;
        //first
        cons[0] = lastStartNode;
        if (!cons[0].getType().equals(cons[0].getHead().getType())) {
          flat = false;
        }
        //last
        cons[advanceNodeIndex - lastStartIndex] = advanceNode;
        if (flat && !cons[advanceNodeIndex - lastStartIndex].getType().equals(cons[advanceNodeIndex - lastStartIndex].getHead().getType())) {
          flat = false;
        }
        //middle
        for (int ci = 1; ci < advanceNodeIndex - lastStartIndex; ci++) {
          cons[ci] = children[ci + lastStartIndex];
          if (flat && !cons[ci].getType().equals(cons[ci].getHead().getType())) {
            flat = false;
          }
        }
        if (!flat) { //flat chunks are done by chunker
          if (lastStartIndex == 0 && advanceNodeIndex == numNodes-1) { //check for top node to include end and begining punctuation
            //System.err.println("ParserME.advanceParses: reducing entire span: "+new Span(lastStartNode.getSpan().getStart(), advanceNode.getSpan().getEnd())+" "+lastStartType+" "+java.util.Arrays.asList(children));
            newParse2.insert(new Parse(p.getText(), p.getSpan(), lastStartType, cprobs[1], headRules.getHead(cons, lastStartType)));
          }
          else {
            newParse2.insert(new Parse(p.getText(), new Span(lastStartNode.getSpan().getStart(), advanceNode.getSpan().getEnd()), lastStartType, cprobs[1], headRules.getHead(cons, lastStartType)));
          }
          newParsesList.add(newParse2);
        }
      }
      if (cprobs[incompleteIndex] > q) { //make sure a shift is likly
        if (createDerivationString) newParse1.getDerivation().append(0).append(".");
        if (advanceNodeIndex != numNodes - 1) { //can't shift last element
          newParse1.addProb(Math.log(cprobs[0]));
          newParsesList.add(newParse1);
        }
      }
    }
    Parse[] newParses = new Parse[newParsesList.size()];
    newParsesList.toArray(newParses);
    return newParses;
  }

  /**
   * Reutrns the top chunk sequences for the specified parse.
   * @param p A pos-tag assigned parse.
   * @return The top chunk assignments to the specified parse.
   */
  protected Parse[] advanceChunks(final Parse p, double minChunkScore) {
    // chunk
    Parse[] children = p.getChildren();
    String words[] = new String[children.length];
    String ptags[] = new String[words.length];
    double probs[] = new double[words.length];
    Parse sp = null;
    for (int i = 0, il = children.length; i < il; i++) {
      sp = children[i];
      words[i] = sp.getHead().toString();
      ptags[i] = sp.getType();
    }
    //System.err.println("adjusted mcs = "+(minChunkScore-p.getProb()));
    Sequence[] cs = chunker.topKSequences(words, ptags,minChunkScore-p.getProb());
    Parse[] newParses = new Parse[cs.length];
    for (int si = 0, sl = cs.length; si < sl; si++) {
      newParses[si] = (Parse) p.clone(); //copies top level
      if (createDerivationString) newParses[si].getDerivation().append(si).append(".");
      String[] tags = (String[]) cs[si].getOutcomes().toArray(new String[words.length]);
      cs[si].getProbs(probs);
      int start = -1;
      int end = 0;
      String type = null;
      //System.err.print("sequence "+si+" ");
      for (int j = 0; j <= tags.length; j++) {
        //if (j != tags.length) {System.err.println(words[j]+" "+ptags[j]+" "+tags[j]+" "+probs.get(j));}
        if (j != tags.length) {
          newParses[si].addProb(Math.log(probs[j]));
        }
        if (j != tags.length && tags[j].startsWith(CONT)) { // if continue just update end chunking tag don't use contTypeMap
          end = j;
        }
        else { //make previous constituent if it exists
          if (type != null) {
            //System.err.println("inserting tag "+tags[j]);
            Parse p1 = p.getChildren()[start];
            Parse p2 = p.getChildren()[end];
            //System.err.println("Putting "+type+" at "+start+","+end+" "+newParses[si].prob);
            Parse[] cons = new Parse[end - start + 1];
            cons[0] = p1;
            //cons[0].label="Start-"+type;
            if (end - start != 0) {
              cons[end - start] = p2;
              //cons[end-start].label="Cont-"+type;
              for (int ci = 1; ci < end - start; ci++) {
                cons[ci] = p.getChildren()[ci + start];
                //cons[ci].label="Cont-"+type;
              }
            }
            newParses[si].insert(new Parse(p1.getText(), new Span(p1.getSpan().getStart(), p2.getSpan().getEnd()), type, 1, headRules.getHead(cons, type)));
          }
          if (j != tags.length) { //update for new constituent
            if (tags[j].startsWith(START)) { // don't use startTypeMap these are chunk tags
              type = tags[j].substring(START.length());
              start = j;
              end = j;
            }
            else { // other 
              type = null;
            }
          }
        }
      }
      //newParses[si].show();System.out.println();
    }
    return newParses;
  }

  /**
   * Advances the parse by assigning it POS tags and returns multiple tag sequences.
   * @param p The parse to be tagged.
   * @return Parses with different POS-tag sequence assignments.
   */
  protected Parse[] advanceTags(final Parse p) {
    Parse[] children = p.getChildren();
    String[] words = new String[children.length];
    double[] probs = new double[words.length];
    for (int i = 0,il = children.length; i < il; i++) {
      words[i] = children[i].toString();
    }
    Sequence[] ts = tagger.topKSequences(words);
    if (ts.length == 0) {
      System.err.println("no tag sequence");
    }
    Parse[] newParses = new Parse[ts.length];
    for (int i = 0; i < ts.length; i++) {
      String[] tags = (String[]) ts[i].getOutcomes().toArray(new String[words.length]);
      ts[i].getProbs(probs);
      newParses[i] = (Parse) p.clone(); //copies top level
      if (createDerivationString) newParses[i].getDerivation().append(i).append(".");
      for (int j = 0; j < words.length; j++) {
        Parse word = children[j];
        //System.err.println("inserting tag "+tags[j]);
        double prob = probs[j];
        newParses[i].insert(new Parse(word.getText(), word.getSpan(), tags[j], prob));
        newParses[i].addProb(Math.log(prob));
        //newParses[i].show();
      }
    }
    return newParses;
  }
  
  /**
   * Removes the punctuation from the specified set of chunks, adds it to the parses
   * adjacent to the punctuation is specified, and returns a new array of parses with the punctuation
   * removed.
   * @param chunks A set of parses.
   * @param punctSet The set of punctuation which is to be removed.
   * @return An array of parses which is a subset of chunks with punctuation removed.
   */
  public static Parse[] collapsePunctuation(Parse[] chunks, Set punctSet) {
    List collapsedParses = new ArrayList(chunks.length);
    int lastNonPunct = -1;
    int nextNonPunct = -1;
    for (int ci=0,cn=chunks.length;ci<cn;ci++) {
      if (punctSet.contains(chunks[ci].getType())) {
        if (lastNonPunct >= 0) {
          chunks[lastNonPunct].addNextPunctuation(chunks[ci]);
        }
        for (nextNonPunct=ci+1;nextNonPunct<cn;nextNonPunct++) {
          if (!punctSet.contains(chunks[nextNonPunct].getType())) {
            break;
          }
        }
        if (nextNonPunct < cn) {
          chunks[nextNonPunct].addPreviousPunctuation(chunks[ci]);
        }
      }
      else {
        collapsedParses.add(chunks[ci]);
        lastNonPunct = ci;
      }
    }
    if (collapsedParses.size() == chunks.length) {
      return chunks;
    }
    //System.err.println("collapsedPunctuation: collapsedParses"+collapsedParses);
    return (Parse[]) collapsedParses.toArray(new Parse[collapsedParses.size()]);
  }

  public static GISModel train(ch.epfl.bbp.shaded.opennlp.maxent.EventStream es, int iterations, int cut) throws java.io.IOException {
    return ch.epfl.bbp.shaded.opennlp.maxent.GIS.trainModel(iterations, new TwoPassDataIndexer(es, cut));
  }
  
  private static boolean lastChild(Parse child, Parse parent, Set punctSet) {
    Parse[] kids = collapsePunctuation(parent.getChildren(),punctSet);
    return (kids[kids.length - 1] == child);
  }
  
  private static void usage() {
    System.err.println("Usage: ParserME -[dict|tag|chunk|build|check|fun] trainingFile parserModelDirectory [iterations cutoff]");
    System.err.println();
    System.err.println("Training file should be one sentence per line where each line consists of a Penn Treebank Style parse");
    System.err.println("-dict Just build the dictionaries.");
    System.err.println("-tag Just build the tagging model.");
    System.err.println("-chunk Just build the chunking model.");
    System.err.println("-build Just build the build model");
    System.err.println("-check Just build the check model");
    System.err.println("-fun Predict function tags");
  }

  /**
   * Creates a n-gram dictionary from the specified data stream using the specified head rule and specified cut-off.
   * @param data The data stream of parses.
   * @param rules The head rules for the parses.
   * @param cutoff The minimum number of entries required for the n-gram to be saved as part of the dictionary. 
   * @return A dictionary object.
   */
  private static MutableDictionary buildDictionary(DataStream data, HeadRules rules, int cutoff) {
    MutableDictionary mdict = new MutableDictionary(cutoff);
    while(data.hasNext()) {
      String parseStr = (String) data.nextToken();
      Parse p = Parse.parseParse(parseStr);
      p.updateHeads(rules);
      Parse[] pwords = p.getTagNodes();
      String[] words = new String[pwords.length];
      //add all uni-grams
      for (int wi=0;wi<words.length;wi++) {
        words[wi] = pwords[wi].toString();
      }
      mdict.add(words,1,true);
      //add tri-grams and bi-grams for inital sequence
      Parse[] chunks = collapsePunctuation(ParserEventStream.getInitialChunks(p),rules.getPunctuationTags());
      String[] cwords = new String[chunks.length];
      for (int wi=0;wi<cwords.length;wi++) {
        cwords[wi] = chunks[wi].getHead().toString();
      }
      mdict.add(cwords,3,false);
      //emulate reductions to produce additional n-grams 
      int ci = 0;
      while (ci < chunks.length) {
        //System.err.println("chunks["+ci+"]="+chunks[ci].getHead().toString()+" chunks.length="+chunks.length);
        if (lastChild(chunks[ci], chunks[ci].getParent(),rules.getPunctuationTags())) {
          //perform reduce
          int reduceStart = ci;
          while (reduceStart >=0 && chunks[reduceStart].getParent() == chunks[ci].getParent()) {
            reduceStart--;
          }
          reduceStart++;
          chunks = ParserEventStream.reduceChunks(chunks,ci,chunks[ci].getParent());
          ci = reduceStart;
          if (chunks.length != 0) {
            String[] window = new String[5];
            int wi = 0;
            if (ci-2 >= 0) window[wi++] = chunks[ci-2].getHead().toString();
            if (ci-1 >= 0) window[wi++] = chunks[ci-1].getHead().toString();
            window[wi++] = chunks[ci].getHead().toString();
            if (ci+1 < chunks.length) window[wi++] = chunks[ci+1].getHead().toString();
            if (ci+2 < chunks.length) window[wi++] = chunks[ci+2].getHead().toString();
            if (wi < 5) {
              String[] subWindow = new String[wi];
              for (int swi=0;swi<wi;swi++) {
                subWindow[swi]=window[swi];
              }
              window = subWindow;
            }
            if (window.length >=3) {
              mdict.add(window,3,false);
            }
            else if (window.length == 2) {
              mdict.add(window,2,false);
            }
          }
          ci=reduceStart-1; //ci will be incremented at end of loop
        }
        ci++;
      }
    }
    return mdict;
  }

  public static void main(String[] args) throws java.io.IOException {
    if (args.length < 3) {
      usage();
      System.exit(1);
    }
    boolean dict = false; 
    boolean tag = false;
    boolean chunk = false;
    boolean build = false;
    boolean check = false;
    boolean fun = false;
    boolean all = true;
    int argIndex = 0;
    while (args[argIndex].startsWith("-")) {
      all = false;
      if (args[argIndex].equals("-dict")) {
        dict = true;
      }
      else if (args[argIndex].equals("-tag")) {
        tag = true;
      }
      else if (args[argIndex].equals("-chunk")) {
        chunk = true;
      }
      else if (args[argIndex].equals("-build")) {
        build = true;
      }
      else if (args[argIndex].equals("-check")) {
        check = true;
      }
      else if (args[argIndex].equals("-fun")) {
        fun = true;
      }
      else if (args[argIndex].equals("--")) {
        argIndex++;
        break;
      }
      else {
        System.err.println("Invalid option " + args[argIndex]);
        usage();
        System.exit(1);
      }
      argIndex++;
    }
    java.io.File inFile = new java.io.File(args[argIndex++]);
    String modelDirectory = args[argIndex++];
    HeadRules rules = new ch.epfl.bbp.shaded.opennlp.tools.lang.english.HeadRules(modelDirectory+"/head_rules");
    java.io.File dictFile = new java.io.File(modelDirectory+"/dict.bin.gz");
    java.io.File tagFile = new java.io.File(modelDirectory+"/tag.bin.gz");
    java.io.File chunkFile = new java.io.File(modelDirectory+"/chunk.bin.gz");
    java.io.File buildFile = new java.io.File(modelDirectory+"/build.bin.gz");
    java.io.File checkFile = new java.io.File(modelDirectory+"/check.bin.gz");
    int iterations = 100;
    int cutoff = 5;
    if (args.length > argIndex) {
      iterations = Integer.parseInt(args[argIndex++]);
      cutoff = Integer.parseInt(args[argIndex++]);
    }
    if (fun) {
      Parse.useFunctionTags(true);
    }
    if (dict || all) {
      System.err.println("Building dictionary");
      DataStream data = new ch.epfl.bbp.shaded.opennlp.maxent.PlainTextByLineDataStream(new java.io.FileReader(inFile));
      MutableDictionary mdict = buildDictionary(data, rules, cutoff);
      System.out.println("Saving the dictionary");
      mdict.persist(dictFile);
    }
    if (tag || all) {
      System.err.println("Training tagger");
      System.err.println("Loading Dictionary");
      Dictionary tridict = new Dictionary(dictFile.toString());
      ch.epfl.bbp.shaded.opennlp.maxent.EventStream tes = new ParserEventStream(new ch.epfl.bbp.shaded.opennlp.maxent.PlainTextByLineDataStream(new java.io.FileReader(inFile)), rules, EventTypeEnum.TAG,tridict);
      GISModel tagModel = train(tes, iterations, cutoff);
      System.out.println("Saving the tagger model as: " + tagFile);
      new ch.epfl.bbp.shaded.opennlp.maxent.io.SuffixSensitiveGISModelWriter(tagModel, tagFile).persist();
    }

    if (chunk || all) {
      System.err.println("Training chunker");
      ch.epfl.bbp.shaded.opennlp.maxent.EventStream ces = new ParserEventStream(new ch.epfl.bbp.shaded.opennlp.maxent.PlainTextByLineDataStream(new java.io.FileReader(inFile)), rules, EventTypeEnum.CHUNK);
      GISModel chunkModel = train(ces, iterations, cutoff);
      System.out.println("Saving the chunker model as: " + chunkFile);
      new ch.epfl.bbp.shaded.opennlp.maxent.io.SuffixSensitiveGISModelWriter(chunkModel, chunkFile).persist();
    }

    if (build || all) {
      System.err.println("Loading Dictionary");
      Dictionary tridict = new Dictionary(dictFile.toString());
      System.err.println("Training builder");
      ch.epfl.bbp.shaded.opennlp.maxent.EventStream bes = new ParserEventStream(new ch.epfl.bbp.shaded.opennlp.maxent.PlainTextByLineDataStream(new java.io.FileReader(inFile)), rules, EventTypeEnum.BUILD,tridict);
      GISModel buildModel = train(bes, iterations, cutoff);
      System.out.println("Saving the build model as: " + buildFile);
      new ch.epfl.bbp.shaded.opennlp.maxent.io.SuffixSensitiveGISModelWriter(buildModel, buildFile).persist();
    }

    if (check || all) {
      System.err.println("Training checker");
      ch.epfl.bbp.shaded.opennlp.maxent.EventStream kes = new ParserEventStream(new ch.epfl.bbp.shaded.opennlp.maxent.PlainTextByLineDataStream(new java.io.FileReader(inFile)), rules, EventTypeEnum.CHECK);
      GISModel checkModel = train(kes, iterations, cutoff);
      System.out.println("Saving the check model as: " + checkFile);
      new ch.epfl.bbp.shaded.opennlp.maxent.io.SuffixSensitiveGISModelWriter(checkModel, checkFile).persist();
    }
  }
}
