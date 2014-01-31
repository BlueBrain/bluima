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
 * A wrapper to turn EventCollectors created for Maxent 1.0 into EventStreams
 * for Maxent 1.2.  For efficiency, it would be best to convert your
 * EventCollector into a EventStream directly, but this will allow your
 * application to work with Maxent 1.2 with very little recoding.
 *
 * @author      Jason Baldridge
 * @version     $Revision: 1.1.1.1 $, $Date: 2001/10/23 14:06:53 $
 */
public final class EventCollectorAsStream implements EventStream {
    final Event[] events;
    final int numEvents;
    int index = 0;
    
    public EventCollectorAsStream (EventCollector ec) {
	events = ec.getEvents(false);
	numEvents = events.length;
    }
    
    public Event nextEvent () {
	return events[index++];
    }
    
    public boolean hasNext () {
	return (index < numEvents);
    }
 
}
