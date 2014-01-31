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

/**
 * A maxent predicate representation which we can use to sort based on the
 * outcomes. This allows us to make the mapping of features to their parameters
 * much more compact.
 *
 * @author      Jason Baldridge
 * @version $Revision: 1.1.1.1 $, $Date: 2001/10/23 14:06:53 $
 */
public class ComparablePredicate implements Comparable {
    public String name;
    public int[] outcomes;
    public double[] params;
    
    public ComparablePredicate(String n, int[] ocs, double[] ps) {
	name = n;
	outcomes = ocs;
	params = ps;
    }

    public int compareTo(Object o) {
	ComparablePredicate cp = (ComparablePredicate)o;

	int smallerLength = (outcomes.length > cp.outcomes.length?
			     cp.outcomes.length : outcomes.length);

	for (int i=0; i<smallerLength; i++) {
	    if (outcomes[i] < cp.outcomes[i]) return -1;
	    else if (outcomes[i] > cp.outcomes[i]) return 1;
	}

	if (outcomes.length < cp.outcomes.length) return -1;
	else if (outcomes.length > cp.outcomes.length) return 1;

	return 0;
    }

    public String toString() {
	String s = "";
	for (int i=0; i<outcomes.length; i++) s+= " "+outcomes[i];
	return s;
    }

}
 
