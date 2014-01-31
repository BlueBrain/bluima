///////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2001 Jason Baldridge and Gann Bierner
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
//////////////////////////////////////////////////////////////////////////////   
package ch.epfl.bbp.shaded.opennlp.maxent;

import gnu.trove.*;
import java.util.*;

/**
 * An indexer for maxent model data which handles cutoffs for uncommon
 * contextual predicates and provides a unique integer index for each of the
 * predicates.  The data structures built in the constructor of this class are
 * used by the GIS trainer.
 *
 * @author      Jason Baldridge
 * @version $Revision: 1.2 $, $Date: 2004/08/29 18:59:12 $
 */
public class OnePassDataIndexer extends AbstractDataIndexer  {

    /**
     * One argument constructor for DataIndexer which calls the two argument
     * constructor assuming no cutoff.
     *
     * @param eventStream An Event[] which contains the a list of all the Events
     *               seen in the training data.
     */     
    public OnePassDataIndexer(EventStream eventStream) {
        this(eventStream, 0);
    }

    /**
     * Two argument constructor for DataIndexer.
     *
     * @param eventStream An Event[] which contains the a list of all the Events
     *               seen in the training data.
     * @param cutoff The minimum number of times a predicate must have been
     *               observed in order to be included in the model.
     */
    public OnePassDataIndexer(EventStream eventStream, int cutoff) {
        TObjectIntHashMap predicateIndex;
        TLinkedList events;
        List eventsToCompare;

        predicateIndex = new TObjectIntHashMap();
        System.out.println("Indexing events using cutoff of " + cutoff + "\n");

        System.out.print("\tComputing event counts...  ");
        events = computeEventCounts(eventStream,predicateIndex,cutoff);
        System.out.println("done. "+events.size()+" events");

        System.out.print("\tIndexing...  ");
        eventsToCompare = index(events,predicateIndex);
        // done with event list
        events = null;
        // done with predicates
        predicateIndex = null;

        System.out.println("done.");

        System.out.print("Sorting and merging events... ");
        sortAndMerge(eventsToCompare);
        System.out.println("Done indexing.");
    }


    
    /**
     * Reads events from <tt>eventStream</tt> into a linked list.  The
     * predicates associated with each event are counted and any which
     * occur at least <tt>cutoff</tt> times are added to the
     * <tt>predicatesInOut</tt> map along with a unique integer index.
     *
     * @param eventStream an <code>EventStream</code> value
     * @param predicatesInOut a <code>TObjectIntHashMap</code> value
     * @param cutoff an <code>int</code> value
     * @return a <code>TLinkedList</code> value
     */
    private TLinkedList computeEventCounts(EventStream eventStream,
                                           TObjectIntHashMap predicatesInOut,
                                           int cutoff) {
        TObjectIntHashMap counter = new TObjectIntHashMap();
        TLinkedList events = new TLinkedList();
        int predicateIndex = 0;
        while (eventStream.hasNext()) {
            Event ev = eventStream.nextEvent();
            events.addLast(ev);
            String[] ec = ev.getContext();
            for (int j=0; j<ec.length; j++) {
                if (! predicatesInOut.containsKey(ec[j])) {
		    if (counter.increment(ec[j])) {
		    } else {
                        counter.put(ec[j], 1);
                    }
		    if (counter.get(ec[j]) >= cutoff) {
		      predicatesInOut.put(ec[j], predicateIndex++);
		      counter.remove(ec[j]);
                      //if (predicateIndex %1000 == 0) {
                      //  System.err.println(predicateIndex+ " predicates"+ "event.length="+ec.length+" "+Arrays.asList(ec));                        
                      //  counter.trimToSize();
                      //}
		    }
                }
            }
        }
        predicatesInOut.trimToSize();
        return events;
    }

    private List index(TLinkedList events,
                       TObjectIntHashMap predicateIndex) {
        TObjectIntHashMap omap = new TObjectIntHashMap();

        int numEvents = events.size();
        int outcomeCount = 0;
        int predCount = 0;
        List eventsToCompare = new ArrayList(numEvents);
        TIntArrayList indexedContext = new TIntArrayList();

        for (int eventIndex=0; eventIndex<numEvents; eventIndex++) {
            Event ev = (Event)events.removeFirst();
            String[] econtext = ev.getContext();
            ComparableEvent ce;
	    
            int predID, ocID;
            String oc = ev.getOutcome();
	    
            if (omap.containsKey(oc)) {
                ocID = omap.get(oc);
            } else {
                ocID = outcomeCount++;
                omap.put(oc, ocID);
            }

            for (int i=0; i<econtext.length; i++) {
                String pred = econtext[i];
                if (predicateIndex.containsKey(pred)) {
                    indexedContext.add(predicateIndex.get(pred));
                }
            }

            // drop events with no active features
            if (indexedContext.size() > 0) {
                ce = new ComparableEvent(ocID, indexedContext.toNativeArray());
                eventsToCompare.add(ce);
            }
	    else {
	      System.err.println("Dropped event "+ev.getOutcome()+":"+Arrays.asList(ev.getContext()));
	    }
            // recycle the TIntArrayList
            indexedContext.resetQuick();
        }
        outcomeLabels = toIndexedStringArray(omap);
        predLabels = toIndexedStringArray(predicateIndex);
        return eventsToCompare;
    }
}
