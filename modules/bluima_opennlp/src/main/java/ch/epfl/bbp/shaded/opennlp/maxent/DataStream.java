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
 * A interface for objects which can deliver a stream of training data to be
 * supplied to an EventStream. It is not necessary to use a DataStream in a
 * Maxent application, but it can be used to support a wider variety of formats
 * in which your training data can be held.
 *
 * @author      Jason Baldridge
 * @version     $Revision: 1.1.1.1 $, $Date: 2001/10/23 14:06:53 $
 */
public interface DataStream {

    /**
     * Returns the next slice of data held in this DataStream.
     *
     * @return the Object representing the data which is next in this
     *         DataStream
     */
    public Object nextToken ();

    /**
     * Test whether there are any Events remaining in this EventStream.
     *
     * @return true if this DataStream has more data tokens
     */
    public boolean hasNext ();
}

