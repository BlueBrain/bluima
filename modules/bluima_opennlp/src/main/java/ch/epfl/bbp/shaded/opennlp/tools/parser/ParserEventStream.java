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
import java.util.List;
import java.util.Set;

import ch.epfl.bbp.shaded.opennlp.maxent.DataStream;
import ch.epfl.bbp.shaded.opennlp.maxent.Event;
import ch.epfl.bbp.shaded.opennlp.maxent.EventStream;
import ch.epfl.bbp.shaded.opennlp.tools.chunker.ChunkerContextGenerator;
import ch.epfl.bbp.shaded.opennlp.tools.ngram.Dictionary;
import ch.epfl.bbp.shaded.opennlp.tools.postag.DefaultPOSContextGenerator;
import ch.epfl.bbp.shaded.opennlp.tools.postag.POSContextGenerator;


/**
 * Wrapper class for one of four parser event streams.  The particular event stram is specified 
 * at construction.
 * @author Tom Morton
 *
 */
public class ParserEventStream implements EventStream {

  private BuildContextGenerator bcg;
  private CheckContextGenerator kcg;
  private ChunkerContextGenerator ccg;
  private POSContextGenerator tcg;
  private DataStream data;
  private Event[] events;
  private int ei;
  private HeadRules rules;
  private Set punctSet;
  private EventTypeEnum etype;

  /**
   * Create an event stream based on the specified data stream of the specified type using the specified head rules.
   * @param d A 1-parse-per-line Penn Treebank Style parse. 
   * @param rules The head rules.
   * @param etype The type of events desired (tag, chunk, build, or check).
   * @param dict A tri-gram dictionary to reduce feature generation.
   */
  public ParserEventStream(DataStream d, HeadRules rules, EventTypeEnum etype, Dictionary dict) {
    if (etype == EventTypeEnum.BUILD) {
      this.bcg = new BuildContextGenerator(dict);
    }
    else if (etype == EventTypeEnum.CHECK) {
      this.kcg = new CheckContextGenerator();
    }
    else if (etype == EventTypeEnum.CHUNK) {
      this.ccg = new ChunkContextGenerator();
    }
    else if (etype == EventTypeEnum.TAG) {
      this.tcg = new DefaultPOSContextGenerator(dict);
    }
    this.rules = rules;
    punctSet = rules.getPunctuationTags();
    this.etype = etype;
    data = d;
    ei = 0;
    if (d.hasNext()) {
      addNewEvents();
    }
    else {
      events = new Event[0];
    }
  }
  
  public ParserEventStream(DataStream d, HeadRules rules, EventTypeEnum etype) {
    this (d,rules,etype,null);
  }

  public boolean hasNext() {
    return (ei < events.length || data.hasNext());
  }

  public Event nextEvent() {
    if (ei == events.length) {
      addNewEvents();
      ei = 0;
    }
    return ((Event) events[ei++]);
  }

  private static void getInitialChunks(Parse p, List ichunks) {
    if (p.isPosTag()) {
      ichunks.add(p);
    }
    else {
      Parse[] kids = p.getChildren();
      boolean allKidsAreTags = true;
      for (int ci = 0, cl = kids.length; ci < cl; ci++) {
        if (!kids[ci].isPosTag()) {
          allKidsAreTags = false;
          break;
        }
      }
      if (allKidsAreTags) {
        ichunks.add(p);
      }
      else {
        for (int ci = 0, cl = kids.length; ci < cl; ci++) {
          getInitialChunks(kids[ci], ichunks);
        }
      }
    }
  }

  public static Parse[] getInitialChunks(Parse p) {
    List chunks = new ArrayList();
    getInitialChunks(p, chunks);
    return (Parse[]) chunks.toArray(new Parse[chunks.size()]);
  }

  /**
   * Returns true if the specified child is the first child of the specified parent.
   * @param child The child parse.
   * @param parent The parent parse.
   * @return true if the specified child is the first child of the specified parent; false otherwise.
   */
  private boolean firstChild(Parse child, Parse parent) {
    return ParserME.collapsePunctuation(parent.getChildren(),punctSet)[0] == child;
  }

  /**
   * Returns true if the specified child is the last child of the specified parent.
   * @param child The child parse.
   * @param parent The parent parse.
   * @return true if the specified child is the last child of the specified parent; false otherwise.
   */
  private boolean lastChild(Parse child, Parse parent) {
    Parse[] kids = ParserME.collapsePunctuation(parent.getChildren(),punctSet);
    return (kids[kids.length - 1] == child);
  }

  private void addNewEvents() {
    String parseStr = (String) data.nextToken();
    //System.err.println("ParserEventStream.addNewEvents: "+parseStr);
    List newEvents = new ArrayList();
    Parse p = Parse.parseParse(parseStr);
    p.updateHeads(rules);
    Parse[] chunks = getInitialChunks(p);
    if (etype == EventTypeEnum.TAG) {
      addTagEvents(newEvents, chunks);
    }
    else if (etype == EventTypeEnum.CHUNK) {
      addChunkEvents(newEvents, chunks);
    }
    else {
      addParseEvents(newEvents, ParserME.collapsePunctuation(chunks,punctSet));
    }
    this.events = (Event[]) newEvents.toArray(new Event[newEvents.size()]);
  }
  
  public static  Parse[] reduceChunks(Parse[] chunks, int ci, Parse parent) {
    String type = parent.getType();
    //  perform reduce
    int reduceStart = ci;
    int reduceEnd = ci;
    while (reduceStart >=0 && chunks[reduceStart].getParent() == parent) {
      reduceStart--;
    }
    reduceStart++;
    Parse[] reducedChunks;
    if (!type.equals(ParserME.TOP_NODE)) {
      reducedChunks = new Parse[chunks.length-(reduceEnd-reduceStart+1)+1]; //total - num_removed + 1 (for new node)
      //insert nodes before reduction
      for (int ri=0,rn=reduceStart;ri<rn;ri++) {
        reducedChunks[ri]=chunks[ri];
      }
      //insert reduced node
      reducedChunks[reduceStart]=parent;
      //propagate punctuation sets
      parent.setPrevPunctuation(chunks[reduceStart].getPreviousPunctuationSet());
      parent.setNextPunctuation(chunks[reduceEnd].getNextPunctuationSet());
      //insert nodes after reduction
      int ri=reduceStart+1;
      for (int rci=reduceEnd+1;rci<chunks.length;rci++) {
        reducedChunks[ri]=chunks[rci];
        ri++;
      }
      ci=reduceStart-1; //ci will be incremented at end of loop
    }
    else {
      reducedChunks = new Parse[0];
    }
    return reducedChunks;
  }

  private void addParseEvents(List parseEvents, Parse[] chunks) {
    int ci = 0;
    while (ci < chunks.length) {
      //System.err.println("parserEventStream.addParseEvents: chunks="+Arrays.asList(chunks));
      Parse c = chunks[ci];
      Parse parent = c.getParent();
      if (parent != null) {
        String type = parent.getType();
        String outcome;
        if (firstChild(c, parent)) {
          outcome = ParserME.START + type;
        }
        else {
          outcome = ParserME.CONT + type;
        }
        //System.err.println("parserEventStream.addParseEvents: chunks["+ci+"]="+c+" label="+outcome);
        c.setLabel(outcome);
        if (etype == EventTypeEnum.BUILD) {
          parseEvents.add(new Event(outcome, bcg.getContext(chunks, ci)));
        }
        int start = ci - 1;
        while (start >= 0 && chunks[start].getParent() == parent) {
          start--;
        }
        if (lastChild(c, parent)) {
          if (etype == EventTypeEnum.CHECK) {
            parseEvents.add(new Event(ParserME.COMPLETE, kcg.getContext( chunks, type, start + 1, ci)));
          }
          //perform reduce
          int reduceStart = ci;
          while (reduceStart >=0 && chunks[reduceStart].getParent() == parent) {
            reduceStart--;
          }
          reduceStart++;
          chunks = reduceChunks(chunks,ci,parent);
          ci=reduceStart-1; //ci will be incremented at end of loop
        }
        else {
          if (etype == EventTypeEnum.CHECK) {
            parseEvents.add(new Event(ParserME.INCOMPLETE, kcg.getContext(chunks, type, start + 1, ci)));
          }
        }
      }
      ci++;
    }
  }

  private void addChunkEvents(List chunkEvents, Parse[] chunks) {
    List toks = new ArrayList();
    List tags = new ArrayList();
    List preds = new ArrayList();
    for (int ci = 0, cl = chunks.length; ci < cl; ci++) {
      Parse c = chunks[ci];
      if (c.isPosTag()) {
        toks.add(c.toString());
        tags.add(c.getType());
        preds.add(ParserME.OTHER);
      }
      else {
        boolean start = true;
        String ctype = c.getType();
        Parse[] kids = c.getChildren();
        for (int ti=0,tl=kids.length;ti<tl;ti++) {
          Parse tok = kids[ti];
          toks.add(tok.toString());
          tags.add(tok.getType());
          if (start) {
            preds.add(ParserME.START + ctype);
            start = false;
          }
          else {
            preds.add(ParserME.CONT + ctype);
          }
        }
      }
    }
    for (int ti = 0, tl = toks.size(); ti < tl; ti++) {
      chunkEvents.add(new Event((String) preds.get(ti), ccg.getContext(ti, toks.toArray(), (String[]) tags.toArray(new String[tags.size()]), (String[]) preds.toArray(new String[preds.size()]))));
    }
  }

  private void addTagEvents(List tagEvents, Parse[] chunks) {
    List toks = new ArrayList();
    List preds = new ArrayList();
    for (int ci = 0, cl = chunks.length; ci < cl; ci++) {
      Parse c = (Parse) chunks[ci];
      if (c.isPosTag()) {
        toks.add(c.toString());
        preds.add(c.getType());
      }
      else {
        Parse[] kids = c.getChildren();
        for (int ti=0,tl=kids.length;ti<tl;ti++) {
          Parse tok = kids[ti];
          toks.add(tok.toString());
          preds.add(tok.getType());
        }
      }
    }
    for (int ti = 0, tl = toks.size(); ti < tl; ti++) {
      tagEvents.add(new Event((String) preds.get(ti), tcg.getContext(ti, toks.toArray(), (String[]) preds.toArray(new String[preds.size()]), null)));
    }
  }

  public static void main(String[] args) throws java.io.IOException {
    if (args.length == 0) {
      System.err.println("Usage ParserEventStream -[tag|chunk|build|check|fun] head_rules [dictionary] < parses");
      System.exit(1);
    }
    EventTypeEnum etype = null;
    boolean fun = false;
    int ai = 0;
    while (ai < args.length && args[ai].startsWith("-")) {
      if (args[ai].equals("-build")) {
        etype = EventTypeEnum.BUILD;
      }
      else if (args[ai].equals("-check")) {
        etype = EventTypeEnum.CHECK;
      }
      else if (args[ai].equals("-chunk")) {
        etype = EventTypeEnum.CHUNK;
      }
      else if (args[ai].equals("-tag")) {
        etype = EventTypeEnum.TAG;
      }
      else if (args[ai].equals("-fun")) {
        fun = true;
      }
      else {
        System.err.println("Invalid option " + args[ai]);
        System.exit(1);
      }
      ai++;
    }
    HeadRules rules = new ch.epfl.bbp.shaded.opennlp.tools.lang.english.HeadRules(args[ai++]);
    Dictionary dict = null;
    if (ai < args.length) {
      dict = new Dictionary(args[ai++]);
    }
    if (fun) {
      Parse.useFunctionTags(true);
    }
    ch.epfl.bbp.shaded.opennlp.maxent.EventStream es = new ParserEventStream(new ch.epfl.bbp.shaded.opennlp.maxent.PlainTextByLineDataStream(new java.io.InputStreamReader(System.in)), rules, etype, dict);
    while (es.hasNext()) {
      System.out.println(es.nextEvent());
    }
  }
}

/**
 * Enumerated type of event types for the parser. 
 *
 */
class EventTypeEnum {

  private String name;

  public static final EventTypeEnum BUILD = new EventTypeEnum("build");
  public static final EventTypeEnum CHECK = new EventTypeEnum("check");
  public static final EventTypeEnum CHUNK = new EventTypeEnum("chunk");
  public static final EventTypeEnum TAG = new EventTypeEnum("tag");

  private EventTypeEnum(String name) {
    this.name = name;
  }

  public String toString() {
    return name;
  }
}
