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
// GNU General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
///////////////////////////////////////////////////////////////////////////////

package ch.epfl.bbp.shaded.opennlp.maxent;

/**
 * A pool of read-only, unsigned Integer objects within a fixed,
 * non-sparse range.  Use this class for operations in which a large
 * number of Integer wrapper objects will be created.
 *
 * Created: Sat Oct 27 10:59:11 2001
 *
 * @author Eric Friedman
 * @version $Id: IntegerPool.java,v 1.1 2001/10/28 03:17:03 ericdf Exp $
 */

public class IntegerPool {
    private Integer[] _table;
    
    /**
     * Creates an IntegerPool with 0..size Integer objects.
     *
     * @param size the size of the pool.
     */
    public IntegerPool (int size) {
        _table = new Integer[size];
        for (int i = 0; i < size; i++) {
            _table[i] = new Integer(i);
        } // end of for (int i = 0; i < size; i++)
    }

    /**
     * Returns the shared Integer wrapper for <tt>value</tt> if it is
     * inside the range managed by this pool.  if <tt>value</tt> is
     * outside the range, a new Integer instance is returned.
     *
     * @param value an <code>int</code> value
     * @return an <code>Integer</code> value
     */
    public Integer get(int value) {
        if (value < _table.length && value >= 0) {
            return _table[value];
        } else {
            return new Integer(value);
        }
    }
}// IntegerPool
