///////////////////////////////////////////////////////////////////////////////
//Copyright (C) 2004 Thomas Morton
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

package ch.epfl.bbp.shaded.opennlp.tools.util;

import java.util.Collection;
import java.util.Iterator;

import ch.epfl.bbp.shaded.opennlp.maxent.Event;
import ch.epfl.bbp.shaded.opennlp.maxent.EventStream;


/**
 * Creates an event stream out of a collection of events. 
 */
public class CollectionEventStream implements EventStream {
  
  Iterator ci;
  
  public CollectionEventStream(Collection c) {
    ci = c.iterator();
  }

  public Event nextEvent() {
    return (Event) ci.next();
  }

  public boolean hasNext() {
    return ci.hasNext();
  }

}
