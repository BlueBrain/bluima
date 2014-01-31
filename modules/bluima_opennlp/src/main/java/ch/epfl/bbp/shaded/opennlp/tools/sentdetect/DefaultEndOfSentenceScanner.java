///////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2002, Eric D. Friedman All Rights Reserved.
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

import java.util.ArrayList;
import java.util.List;

import ch.epfl.bbp.shaded.opennlp.maxent.IntegerPool;

/**
 * The default end of sentence scanner implements all of the
 * EndOfSentenceScanner methods in terms of the getPositions(char[])
 * method.  It scans for <tt>. ? ! ) and "</tt>.
 *
 * Created: Sat Oct 27 11:46:46 2001
 *
 * @author Eric D. Friedman
 * @version $Id: DefaultEndOfSentenceScanner.java,v 1.2 2004/01/27 22:09:16 tsmorton Exp $
 */

public class DefaultEndOfSentenceScanner implements EndOfSentenceScanner {

    protected static final IntegerPool INT_POOL = new IntegerPool(500);
    
    public static final char[] eosCharacters =  {'.','?','!'};
    
    /**
     * Creates a new <code>DefaultEndOfSentenceScanner</code> instance.
     *
     */
    public DefaultEndOfSentenceScanner() {
        super();
    }

    public List getPositions(String s) {
        return getPositions(s.toCharArray());
    }

    public List getPositions(StringBuffer buf) {
        return getPositions(buf.toString().toCharArray());
    }

    public List getPositions(char[] cbuf) {
        List l = new ArrayList();

        for (int i = 0; i < cbuf.length; i++) {
            switch (cbuf[i]) {
            case '.':
            case '?':
            case '!':
            //case '"':
            //case '\'':
            //case ')':
                l.add(INT_POOL.get(i));
                break;
            default:
                break;
            }
        }
        return l;
    }
} // DefaultEndOfSentenceScanner
