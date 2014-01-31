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

import java.util.List;

/**
 * Scans Strings, StringBuffers, and char[] arrays for the offsets of
 * sentence ending characters.
 *
 * <p>Implementations of this interface can use regular expressions,
 * hand-coded DFAs, and other scanning techniques to locate end of
 * sentence offsets.</p>
 *
 * Created: Sat Oct 27 11:42:07 2001
 *
 * @author Eric D. Friedman
 * @version $Id: EndOfSentenceScanner.java,v 1.1 2003/11/05 03:31:04 tsmorton Exp $
 */

public interface EndOfSentenceScanner {

    /**
     * The receiver scans `s' for sentence ending characters and
     * returns their offsets.
     *
     * @param s a <code>String</code> value
     * @return a <code>List</code> of Integer objects.
     */
    public List getPositions(String s);

    /**
     * The receiver scans `buf' for sentence ending characters and
     * returns their offsets.
     *
     * @param buf a <code>StringBuffer</code> value
     * @return a <code>List</code> of Integer objects.
     */
    public List getPositions(StringBuffer buf);

    /**
     * The receiver scans `cbuf' for sentence ending characters and
     * returns their offsets.
     *
     * @param cbuf a <code>char[]</code> value
     * @return a <code>List</code> of Integer objects.
     */
    public List getPositions(char[] cbuf);
} // EndOfSentenceScanner
