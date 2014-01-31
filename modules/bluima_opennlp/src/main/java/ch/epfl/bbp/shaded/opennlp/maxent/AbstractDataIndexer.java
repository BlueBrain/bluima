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
package ch.epfl.bbp.shaded.opennlp.maxent;

import gnu.trove.TObjectIntHashMap;
import gnu.trove.TObjectIntProcedure;

import java.util.Collections;
import java.util.List;

/**
 * Abstract class for collecting event and context counts used in training. 
 *
 */
public abstract class AbstractDataIndexer implements DataIndexer {

  /** The integer contexts associated with each unique event. */ 
  protected int[][] contexts;
  /** The integer outcome associated with each unique event. */ 
  protected int[] outcomeList;
  /** The number of times an event occured in the training data. */
  protected int[] numTimesEventsSeen;
  /** The predicate/context names. */
  protected String[] predLabels;
  /** The names of the outcomes. */
  protected String[] outcomeLabels;

  public int[][] getContexts() {
    return contexts;
  }

  public int[] getNumTimesEventsSeen() {
    return numTimesEventsSeen;
  }

  public int[] getOutcomeList() {
    return outcomeList;
  }

  public String[] getPredLabels() {
    return predLabels;
  }

  public String[] getOutcomeLabels() {
    return outcomeLabels;
  }
  
  

  /**
       * Sorts and uniques the array of comparable events.  This method
       * will alter the eventsToCompare array -- it does an in place
       * sort, followed by an in place edit to remove duplicates.
       *
       * @param eventsToCompare a <code>ComparableEvent[]</code> value
       * @since maxent 1.2.6
       */
  protected void sortAndMerge(List eventsToCompare) {
    Collections.sort(eventsToCompare);
    int numEvents = eventsToCompare.size();
    int numUniqueEvents = 1; // assertion: eventsToCompare.length >= 1

    if (numEvents <= 1) {
      return; // nothing to do; edge case (see assertion)
    }

    ComparableEvent ce = (ComparableEvent) eventsToCompare.get(0);
    for (int i = 1; i < numEvents; i++) {
      ComparableEvent ce2 = (ComparableEvent) eventsToCompare.get(i);

      if (ce.compareTo(ce2) == 0) {
        ce.seen++; // increment the seen count
        eventsToCompare.set(i, null); // kill the duplicate
      }
      else {
        ce = ce2; // a new champion emerges...
        numUniqueEvents++; // increment the # of unique events
      }
    }

    System.out.println("done. Reduced " + numEvents + " events to " + numUniqueEvents + ".");

    contexts = new int[numUniqueEvents][];
    outcomeList = new int[numUniqueEvents];
    numTimesEventsSeen = new int[numUniqueEvents];

    for (int i = 0, j = 0; i < numEvents; i++) {
      ComparableEvent evt = (ComparableEvent) eventsToCompare.get(i);
      if (null == evt) {
        continue; // this was a dupe, skip over it.
      }
      numTimesEventsSeen[j] = evt.seen;
      outcomeList[j] = evt.outcome;
      contexts[j] = evt.predIndexes;
      ++j;
    }
  }

  /**
       * Utility method for creating a String[] array from a map whose
       * keys are labels (Strings) to be stored in the array and whose
       * values are the indices (Integers) at which the corresponding
       * labels should be inserted.
       *
       * @param labelToIndexMap a <code>TObjectIntHashMap</code> value
       * @return a <code>String[]</code> value
       * @since maxent 1.2.6
       */
  protected static String[] toIndexedStringArray(TObjectIntHashMap labelToIndexMap) {
      final String[] array = new String[labelToIndexMap.size()];
      labelToIndexMap.forEachEntry(new TObjectIntProcedure() {
              public boolean execute(Object str, int index) {
                  array[index] = (String)str;
                  return true;
              }
          });
      return array;
  }
}
