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
 * A object which can deliver a stream of training events for the GIS
 * procedure (or others such as IIS if and when they are implemented).
 * EventStreams don't need to use opennlp.maxent.DataStreams, but doing so
 * would provide greater flexibility for producing events from data stored in
 * different formats.
 *
 * @author      Jason Baldridge
 * @version     $Revision: 1.1.1.1 $, $Date: 2001/10/23 14:06:53 $
 *
 */
public interface EventStream {

    /**
     * Returns the next Event object held in this EventStream.
     *
     * @return the Event object which is next in this EventStream
     */
    public Event nextEvent ();
 
    /**
     * Test whether there are any Events remaining in this EventStream.
     *
     * @return true if this EventStream has more Events
     */
    public boolean hasNext ();
}

