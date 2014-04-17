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

import gnu.trove.TLinkableAdapter;

import java.util.Arrays;

/**
 * The context of a decision point during training.  This includes
 * contextual predicates and an outcome.
 *
 * @author      Jason Baldridge
 * @version     $Revision: 1.3 $, $Date: 2003/12/09 23:13:08 $
 */
public class Event extends TLinkableAdapter {
    private String outcome;
    private String[] context;
    
    public Event(String oc, String[] c) {
	outcome = oc;
	context = c;
    }
    
    public String getOutcome() { return outcome; }
    public String[] getContext() { return context; }
    
    public String toString() {
      return outcome+" "+Arrays.asList(context);
    }
    
}
