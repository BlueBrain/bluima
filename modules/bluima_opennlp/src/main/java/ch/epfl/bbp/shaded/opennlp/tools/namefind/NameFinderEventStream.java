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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.epfl.bbp.shaded.opennlp.maxent.DataStream;
import ch.epfl.bbp.shaded.opennlp.maxent.Event;
import ch.epfl.bbp.shaded.opennlp.maxent.EventStream;


/**
 * Class for creating an event stream out of data files for training an name finder. 
 */
public class NameFinderEventStream implements EventStream {

  private DataStream data;
  private Event[] events;
  private NameContextGenerator cg;
  /** A mapping between tokens and the name tag assigned to them previously. */
  private Map prevTags;
  /** The index into the array of events. */
  private int eventIndex;
  /** The last line read in from the data file. */
  private String line;
  
  /**
   * Creates a new event stream based on the specified data stream.
   * @param d The data stream for this event stream.
   */
  public NameFinderEventStream(DataStream d) {
    this(d, new DefaultNameContextGenerator());
  }

  /**
   * Creates a new event stream based on the specified data stream using the specified context generator.
   * @param d The data stream for this event stream.
   * @param cg The context generator which should be used in the creation of events for this event stream.
   */
  public NameFinderEventStream(DataStream d, NameContextGenerator cg) {
    this.data = d;
    this.cg = cg;
    eventIndex = 0;
    prevTags = new HashMap();
    // prime events with first line of data stream.
    if (data.hasNext()) {
      line = (String) d.nextToken();
      if (line.equals("")) {
        prevTags.clear();
      }
      else {
        addEvents(line);
      }
    }
    else {
      events = new Event[0];
    }
  }

  /** Adds name events for the specified sentence.
   * @param sentence The sentence for which name events should be added.
   */
  private void addEvents(String sentence) {
    String[] parts = sentence.split(" ");
    String outcome = NameFinderME.OTHER;
    List toks = new ArrayList();
    List outcomes = new ArrayList();
    for (int pi = 0, pl = parts.length; pi < pl; pi++) {
      if (parts[pi].equals("<START>")) {
        outcome = NameFinderME.START;
      }
      else if (parts[pi].equals("<END>")) {
        outcome = NameFinderME.OTHER;
      }
      else { //regular token
        toks.add(parts[pi]);
        outcomes.add(outcome);
        if (outcome.equals(NameFinderME.START)) {
          outcome = NameFinderME.CONTINUE;
        }
      }
    }
    events = new Event[toks.size()];
    for (int ti = 0, tl = toks.size(); ti < tl; ti++) {
      events[ti] = new Event((String) outcomes.get(ti), cg.getContext(ti, toks, outcomes, prevTags));
    }
    for (int ti=0,tl=toks.size();ti<tl;ti++) {
      prevTags.put(toks.get(ti),outcomes.get(ti));
    }
  }

  public Event nextEvent() {
    if (eventIndex == events.length) {
      addEvents(line);
      eventIndex = 0;
      line = null;
    }
    return ((Event) events[eventIndex++]);
  }

  public boolean hasNext() {
    if (eventIndex < events.length) {
      return true;
    }
    else if (line != null) { // previous result has not been consumed
      return true;
    }
    //find next non-blank line
    while (data.hasNext()) {
      line = (String) data.nextToken();
      if (line.equals("")) {
        prevTags.clear();
      }
      else {
        return true;
      }
    }
    return false;
  }
  
  public static final void main(String[] args) throws java.io.IOException {
    if (args.length == 0) {
      System.err.println("Usage: NameFinderEventStream trainfiles");
      System.exit(1);
    }
    for (int ai=0,al=args.length;ai<al;ai++) {
      EventStream es = new NameFinderEventStream(new ch.epfl.bbp.shaded.opennlp.maxent.PlainTextByLineDataStream(new java.io.FileReader(args[ai])));
      while(es.hasNext()) {
        System.out.println(es.nextEvent());
      }
    }
  }
}
