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

import java.util.*;

/**
 * A maxent event representation which we can use to sort based on the
 * predicates indexes contained in the events.
 *
 * @author      Jason Baldridge
 * @version $Revision: 1.2 $, $Date: 2001/12/27 19:20:26 $
 */
public class ComparableEvent implements Comparable {
    public int outcome;
    public int[] predIndexes;
    public int seen = 1;            // the number of times this event
                                    // has been seen.

    public ComparableEvent(int oc, int[] pids) {
        outcome = oc;
        Arrays.sort(pids);
        predIndexes = pids;
    }

    public int compareTo(Object o) {
        ComparableEvent ce = (ComparableEvent)o;

        if (outcome < ce.outcome) return -1;
        else if (outcome > ce.outcome) return 1;
	
        int smallerLength = (predIndexes.length > ce.predIndexes.length?
                             ce.predIndexes.length : predIndexes.length);

        for (int i=0; i<smallerLength; i++) {
            if (predIndexes[i] < ce.predIndexes[i]) return -1;
            else if (predIndexes[i] > ce.predIndexes[i]) return 1;
        }


        if (predIndexes.length < ce.predIndexes.length) return -1;
        else if (predIndexes.length > ce.predIndexes.length) return 1;

        return 0;
    }

    public String toString() {
        String s = "";
        for (int i=0; i<predIndexes.length; i++) s+= " "+predIndexes[i];
        return s;
    }
}
 
