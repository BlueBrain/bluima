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

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ch.epfl.bbp.shaded.opennlp.tools.util.Span;


/** Data structure for holding parse constitents. */
public class Parse implements Cloneable, Comparable {
  /** The text string on which this parse is based.  This object is shared amonung all parses for the same sentence. */
  private String text;
  /** The character offsets into the text for this constituent. */
  private Span span;
  /** The syntactic type of this parse. */
  private String type;
  /** The sub-constituents of this parse. */
  private List parts;
  /** The head parse of this parse. A parse can be its own head.*/
  private Parse head;
  /** The outcome assigned to this parse during cconstruction of its parent parse. */
  private String label;
  /** The parent parse of this parse. */
  private Parse parent;
  /** The probability associated with the syntactic type assigned to this parse. */
  private double prob;
  /** The string buffer used to track the derivation of this parse. */
  private StringBuffer derivation;
  /** The pattern used to find the base constituent label of a Penn Treebank labeled constituent. */
  private static Pattern typePattern = Pattern.compile("^([^ =-]+)");
  /** The pattern used to find the function tags. */ 
  private static Pattern funTypePattern = Pattern.compile("^[^ =-]+-([^ =-]+)");
  /** The patter used to identify tokens in Penn Treebank labeled constituents. */
  private static Pattern tokenPattern = Pattern.compile("^[^ ()]+ ([^ ()]+)\\s*\\)");
  
  /** The set of punctuation parses which are between this parse and the previous parse. */
  private Collection prevPunctSet;
  /** The set of punctuation parses which are between this parse and the subsequent parse. */
  private Collection nextPunctSet;
  
  private static boolean useFunctionTags;
  
  public Object clone() {
    Parse p = new Parse(this.text, this.span, this.type, this.prob, this.head);
    p.parts = (List) ((LinkedList) this.parts).clone();
    if (derivation != null) {
      p.derivation = new StringBuffer(100);
      p.derivation.append(this.derivation.toString());
    }
    p.label = this.label;
    return (p);
  }
  
  
  public static void useFunctionTags(boolean uft) {
    useFunctionTags = uft;
  }

  
  public Parse(String text, Span span, String type, double p) {
    this.text = text;
    this.span = span;
    this.type = type;
    this.prob = p;
    this.head = this;
    this.parts = new LinkedList();
    this.label = null;
    this.parent = null;
  }

  public Parse(String text, Span span, String type, double p, Parse h) {
    this(text, span, type, p);
    this.head = h;
  }

  /**
   * Set the type of this constituent to the specified type.
   * @param type The type of this constituent.
   */
  public void setType(String type) {
    this.type = type;
  }

  /**
   * Returns the constituent label for this node of the parse.
   * @return The constituent label for this node of the parse.
   */
  public String getType() {
    return type;
  }
  
  /**
   * Returns the set of punctuation parses that occur immediately before this parse.
   * @return the set of punctuation parses that occur immediately before this parse.
   */
  public Collection getPreviousPunctuationSet() {
    return prevPunctSet;
  }
  
  /**
   * Designates that the specifed punctuation should is prior to this parse.
   * @param punct The punctuation.
   */
  public void addPreviousPunctuation(Parse punct) {
    if (this.prevPunctSet == null) {
      this.prevPunctSet = new LinkedHashSet();
    }
    prevPunctSet.add(punct);
  }
  
  /**
   * Returns the set of punctuation parses that occur immediately after this parse.
   * @return the set of punctuation parses that occur immediately after this parse.
   */
  public Collection getNextPunctuationSet() {
    return nextPunctSet;
  }
  
  /**
   * Designates that the specifed punctuation follows this parse.
   * @param punct The punctuation set.
   */
  public void addNextPunctuation(Parse punct) {
    if (this.nextPunctSet == null) {
      this.nextPunctSet = new LinkedHashSet();
    }
    nextPunctSet.add(punct);
  }
  
  /**
   * Sets the set of punctuation tags which follow this parse.
   * @param punctSet The set of puncuation tags which follow this parse.
   */
  public void setNextPunctuation(Collection punctSet) {
    this.nextPunctSet = punctSet;
  }
  
  /**
   * Sets the set of punctuation tags which preceed this parse.
   * @param punctSet The set of puncuation tags which preceed this parse.
   */
  public void setPrevPunctuation(Collection punctSet) {
    this.prevPunctSet = punctSet;
  }

  /**
   * Inserts the specified constituent into this parse based on its text span.  This
   * method assumes that the specified constituent can be inserted into this parse.
   * @param constituent The constituent to be inserted.
   */
  public void insert(final Parse constituent) {
    Span ic = constituent.span;
    if (span.contains(ic)) {
      //double oprob=c.prob;
      int pi=0;
      int pn = parts.size();
      for (; pi < pn; pi++) {
        Parse subPart = (Parse) parts.get(pi);
        //System.err.println("Parse.insert:con="+constituent+" sp["+pi+"] "+subPart+" "+subPart.getType());
        Span sp = subPart.span;
        if (sp.getStart() >= ic.getEnd()) {
          break;
        }
        // constituent contains subPart
        else if (ic.contains(sp)) {
          //System.err.println("Parse.insert:con contains subPart");
          parts.remove(pi);
          pi--;
          constituent.parts.add(subPart);
          subPart.setParent(constituent);
          //System.err.println("Parse.insert: "+subPart.hashCode()+" -> "+subPart.getParent().hashCode());
          pn = parts.size();
        }
        else if (sp.contains(ic)) {
          //System.err.println("Parse.insert:subPart contains con");
          subPart.insert(constituent);
          return;
        }
      }
      //System.err.println("Parse.insert:adding con="+constituent+" to "+this);
      parts.add(pi, constituent);
      constituent.setParent(this);
      //System.err.println("Parse.insert: "+constituent.hashCode()+" -> "+constituent.getParent().hashCode());
    }
    else {
      throw (new InternalError("Inserting constituent not contained in the sentence!"));
    }
  }
  
  /**
   * Appends the specified string buffer with a string representation of this parse.
   * @param sb A string buffer into which the parse string can be appended. 
   */
  public void show(StringBuffer sb) {
    int start;
    start = span.getStart();
    if (!type.equals(ParserME.TOK_NODE)) {
      sb.append("(");
      sb.append(type +" ");
      //System.out.print(label+" ");
      //System.out.print(head+" ");
      //System.out.print(df.format(prob)+" ");
    }
    for (Iterator i = parts.iterator(); i.hasNext();) {
      Parse c = (Parse) i.next();
      Span s = c.span;
      if (start < s.getStart()) {
        //System.out.println("pre "+start+" "+s.getStart());
        sb.append(text.substring(start, s.getStart()));
      }
      c.show(sb);
      start = s.getEnd();
    }
    sb.append(text.substring(start, span.getEnd()));
    if (!type.equals(ParserME.TOK_NODE)) {
      sb.append(")");
    }
  }

  /**
   * Displays this parse using Penn Treebank-style formatting. 
   */
  public void show() {
    StringBuffer sb = new StringBuffer(text.length()*4);
    show(sb);
    System.out.println(sb);
  }


  /**
   * Returns the probability associed with the pos-tag sequence assigned to this parse.
   * @return The probability associed with the pos-tag sequence assigned to this parse.
   */
  public double getTagSequenceProb() {
    //System.err.println("Parse.getTagSequenceProb: "+type+" "+this);
    if (parts.size() == 1 && ((Parse) parts.get(0)).type.equals(ParserME.TOK_NODE)) {
      //System.err.println(this+" "+prob);
      return (Math.log(prob));
    }
    else if (parts.size() == 0) {
      System.err.println("Parse.getTagSequenceProb: Wrong base case!");
      return (0.0);
    }
    else {
      double sum = 0.0;
      for (Iterator pi = parts.iterator(); pi.hasNext();) {
        sum += ((Parse) pi.next()).getTagSequenceProb();
      }
      return (sum);
    }
  }

  /** 
   * Returns whether this parse is complete.
   * @return Returns true if the parse contains a single top-most node.
   */
  public boolean complete() {
    return (parts.size() == 1);
  }

  public String toString() {
    return (text.substring(span.getStart(), span.getEnd()));
  }

  /**
   * Returns the text of the sentence over which this parse was formed. 
   * @return The text of the sentence over which this parse was formed.
   */
  public String getText() {
    return text;
  }

  /**
   * Returns the character offsets for this constituent.
   * @return The character offsets for this constituent.
   */
  public Span getSpan() {
    return span;
  }

  /**
   * Returns the log of the product of the probability associated with all the decisions which formed this constituent.
   * @return The log of the product of the probability associated with all the decisions which formed this constituent.
   */
  public double getProb() {
    return prob;
  }
  
  /**
   * Adds the specified probability log to this current log for this parse.
   * @param logProb The probaility of an action performed on this parse.
   */
  public void addProb(double logProb) {
    this.prob+=logProb;
  }

  /**
   * Returns the child constituents of this constiuent. 
   * @return The child constituents of this constiuent.
   */
  public Parse[] getChildren() {
    return (Parse[]) parts.toArray(new Parse[parts.size()]);
  }
  
  /**
   * Replaces the child at the specified index with a new child with the specified label. 
   * @param index The index of the child to be replaced.
   * @param label The label to be assigned to the new child.
   */
  public void setChild(int index, String label) {
    Parse newChild = (Parse) ((Parse) parts.get(index)).clone();
    newChild.setLabel(label);
    parts.set(index,newChild);
  }
  
  /**
   * Returns the number of children for this parse node.
   * @return the number of children for this parse node.
   */
  public int getChildCount() {
    return parts.size();
  }
  
  /**
   * Returns the index of this specified child.
   * @param child A child of this parse.
   * @return the index of this specified child or -1 if the specified child is not a child of this parse.
   */
  public int indexOf(Parse child) {
    return parts.indexOf(child);
  }

  /** Returns the head constituent associated with this constituent.
   * @return The head constituent associated with this constituent.
   */
  public Parse getHead() {
    return head;
  }

  /**
   * Returns the label assigned to this parse node during parsing 
   * which specifies how this node will be formed into a constituent.
   * @return The outcome label assigned to this node during parsing.  
   */
  public String getLabel() {
    return label;
  }

  /**
   * Assigns this parse the specified label.
   * @param label A label indicating the constituent this parse node will become part of.
   */
  public void setLabel(String label) {
    this.label = label;
  }

  
  private static String getType(String rest) {
    if (rest.startsWith("-LCB-")) {
      return "-LCB-";
    }
    else if (rest.startsWith("-RCB-")) {
      return "-RCB-";
    }
    else if (rest.startsWith("-LRB-")) {
      return "-LRB-";
    }
    else if (rest.startsWith("-RRB-")) {
      return "-RRB-";
    }
    else {
      Matcher typeMatcher = typePattern.matcher(rest);
      if (typeMatcher.find()) {
        String type = typeMatcher.group(1); 
        if (useFunctionTags) {
          Matcher funMatcher = funTypePattern.matcher(rest);
          if (funMatcher.find()) {
            String ftag = funMatcher.group(1);
            type = type+"-"+ftag;
          }
        }
        return type;
      }
    }
    return null;
  }

  private static String getToken(String rest) {
    Matcher tokenMatcher = tokenPattern.matcher(rest);
    if (tokenMatcher.find()) {
      return tokenMatcher.group(1);
    }
    return null;
  }
  
  /**
   * Computes the head parses for this parse and its sub-parses and stores this information
   * in the parse data structure. 
   * @param rules The head rules which determine how the head of the parse is computed.
   */
  public void updateHeads(HeadRules rules) {
    if (parts != null && parts.size() != 0) {
      for (int pi=0,pn=parts.size();pi<pn;pi++) {
        Parse c = (Parse) parts.get(pi);
        c.updateHeads(rules);
      }
      this.head = rules.getHead((Parse[]) parts.toArray(new Parse[parts.size()]), type);
      if (head == null) {
        head = this;
      }
    }
    else {
      this.head = this;
    }
  }

  /**
   * Parses the specified tree-bank style parse string and return a Parse structure for that string. 
   * @param parse A tree-bank style parse string.
   * @return a Parse structure for the specified tree-bank style parse string.
   */
  public static Parse parseParse(String parse) {
    StringBuffer text = new StringBuffer();
    int offset = 0;
    Stack stack = new Stack();
    List cons = new LinkedList();
    for (int ci = 0, cl = parse.length(); ci < cl; ci++) {
      char c = parse.charAt(ci);
      if (c == '(') {
        String rest = parse.substring(ci + 1);
        String type = getType(rest);
        if (type == null) {
          System.err.println("null type for: " + rest);
        }
        String token = getToken(rest);
        stack.push(new Object[] { type, new Integer(offset)});
        if (token != null && !type.equals("-NONE-")) {
          cons.add(new Object[] { ParserME.TOK_NODE, new Span(offset, offset + token.length())});
          text.append(token).append(" ");
          offset += token.length() + 1;
        }
      }
      else if (c == ')') {
        Object[] parts = (Object[]) stack.pop();
        String type = (String) parts[0];
        if (!type.equals("-NONE-")) {
          int start = ((Integer) parts[1]).intValue();
          cons.add(new Object[] { parts[0], new Span(start, offset - 1)});
        }
      }
    }
    String txt = text.toString();
    Parse p = new Parse(txt, new Span(0, txt.length()), ParserME.TOP_NODE, 1);
    /*
    for (int ti=0;ti < tokens.size();ti++) {
      Object[] parts = (Object[]) cons.get(ti);
      String type = (String) parts[0];
      if (!type.equals(ParserME.TOP_NODE)) {
        Parse con = new Parse(txt, (Span) parts[1], type, 1);
        System.err.println("insert "+type+" "+con.toString());
        p.insert(con);
      }
    }
    */
    for (int ci=0;ci < cons.size();ci++) {
      Object[] parts = (Object[]) cons.get(ci);
      String type = (String) parts[0];
      if (!type.equals(ParserME.TOP_NODE)) {
        Parse con = new Parse(txt, (Span) parts[1], type, 1);
        //System.err.println("insert["+ci+"] "+type+" "+con.toString()+" "+con.hashCode());
        p.insert(con);
        //codeTree(p);
      }
    }
    return p;
  }

  /**
   * Returns the parent parse node of this constituent.
   * @return The parent parse node of this constituent.
   */
  public Parse getParent() {
    return parent;
  }

  /**
   * Specifies the parent parse node for this constituent.
   * @param parent The parent parse node for this constituent.
   */
  public void setParent(Parse parent) {
    this.parent = parent;
  }

  /**
   * Indicates wether this parse node is a pos-tag.
   * @return true if this node is a pos-tag, false otherwise.
   */
  public boolean isPosTag() {
    return (parts.size() == 1 && ((Parse) parts.get(0)).getType().equals(ParserME.TOK_NODE));
  }
  
  /**
   * Returns the parse nodes which are children of this node and which are pos tags.
   * @return the parse nodes which are children of this node and which are pos tags.
   */
  public Parse[] getTagNodes() {
    List tags = new LinkedList();
    List nodes = new LinkedList();
    nodes.addAll(this.parts);
    while(nodes.size() != 0) {
      Parse p = (Parse) nodes.remove(0);
      if (p.isPosTag()) {
        tags.add(p);
      }
      else {
        nodes.addAll(0,p.parts);
      }
    }
    return (Parse[]) tags.toArray(new Parse[tags.size()]);
  }
  
  /**
   * Returns the deepest shared parent of this node and the specified node. 
   * If the nodes are identical then their parent is returned.  
   * If one node is the parent of the other then the parent node is returned.
   * @param node The node from which parents are compared to this node's parents.
   * @return the deepest shared parent of this node and the specified node.
   */
  public Parse getCommonParent(Parse node) {
    if (this == node) {
      return parent;
    }
    Set parents = new HashSet();
    Parse cparent = this;
    while(cparent != null) {
      parents.add(cparent);
      cparent = cparent.getParent();
    }
    while (node != null) {
      if (parents.contains(node)) {
        return node;
      }
      node = node.getParent();
    }
    return null;
  }

  public int compareTo(Object o) {
    Parse p = (Parse) o;
    if (this.getProb() > p.getProb()) {
      return -1;
    }
    else if (this.getProb() < p.getProb()) {
      return 1;
    }
    return 0;
  }
  
  /**
   * Returns the derivation string for this parse if one has been created.
   * @return the derivation string for this parse or null if no derivation string has been created.
   */
  public StringBuffer getDerivation() {
    return derivation;
  }
  
  /**
   * Specifies the derivation string to be associated with this parse.
   * @param derivation The derivation string to be associated with this parse.
   */
  public void setDerivation(StringBuffer derivation) {
    this.derivation = derivation;
  }
  
  private void codeTree(Parse p,int[] levels) {
    Parse[] kids = p.getChildren();
    StringBuffer levelsBuff = new StringBuffer();
    levelsBuff.append("[");
    int[] nlevels = new int[levels.length+1];
    for (int li=0;li<levels.length;li++) {
      nlevels[li] = levels[li];
      levelsBuff.append(levels[li]).append(".");
    }
    for (int ki=0;ki<kids.length;ki++) {
      nlevels[levels.length] = ki;
      System.out.println(levelsBuff.toString()+ki+"] "+kids[ki].getType()+" "+kids[ki].hashCode()+" -> "+kids[ki].getParent().hashCode()+" "+kids[ki].getParent().getType()+" "+kids[ki].toString());
      codeTree(kids[ki],nlevels);
    }
  }
  
  /**
   * Prints to standard out a representation of the specified parse which contains hash codes so that 
   * parent/child relationships can be explicitly seen.
   */
  public void showCodeTree() {
    codeTree(this,new int[0]);
  }

  /**
   * Reads training parses (one-sentence-per-line) and displays parse structure.
   * @param args The head rules files.
   * @throws java.io.IOException If the head rules file can not be opened and read.
   */
  public static void main(String[] args) throws java.io.IOException {
    if (args.length == 0) {
      System.err.println("Usage: Parse -fun head_rules < train_parses");
      System.err.println("Reads training parses (one-sentence-per-line) and displays parse structure.");
      System.exit(1);
    }
    int ai=0;
    if (args[0].equals("-fun")) {
      Parse.useFunctionTags(true);
      ai++;
    }
    HeadRules rules = new ch.epfl.bbp.shaded.opennlp.tools.lang.english.HeadRules(args[ai]);
    java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
    for (String line = in.readLine(); line != null; line = in.readLine()) {
      Parse p = Parse.parseParse(line);
      p.updateHeads(rules);
      p.show();
      //p.showCodeTree();
    }
  }
}