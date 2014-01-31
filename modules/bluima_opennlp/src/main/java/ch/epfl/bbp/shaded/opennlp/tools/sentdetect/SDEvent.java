///////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2001, Eric D. Friedman All Rights Reserved.
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
///////////////////////////////////////////////////////////////////////////////


package ch.epfl.bbp.shaded.opennlp.tools.sentdetect;

import ch.epfl.bbp.shaded.opennlp.maxent.Event;

/**
 * An Event which can hold a pointer to another Event for use in a
 * linked list.
 *
 * Created: Sat Oct 27 11:53:55 2001
 *
 * @author Eric D. Friedman
 * @version $Id: SDEvent.java,v 1.1 2003/11/05 03:31:04 tsmorton Exp $
 */

class SDEvent extends Event  {
    SDEvent next;
    
    /**
     * package access only
     *
     */
    SDEvent(String oc, String[] c) {
        super(oc,c);
    }
    
} // SDEvent
