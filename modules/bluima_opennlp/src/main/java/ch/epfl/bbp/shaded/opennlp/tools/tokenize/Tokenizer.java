///////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2002 Jason Baldridge and Gann Bierner
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
//////////////////////////////////////////////////////////////////////////////

package ch.epfl.bbp.shaded.opennlp.tools.tokenize;

import ch.epfl.bbp.shaded.opennlp.tools.util.Span;

/**
 * The interface for tokenizers, which turn messy text into nicely segmented
 * text tokens.
 *
 * @author      Jason Baldridge
 * @version     $Revision: 1.2 $, $Date: 2004/01/26 14:16:37 $
 */

public interface Tokenizer {
    
    /**
     * Tokenize a string.
     *
     * @param s The string to be tokenized.
     * @return  The String[] with the individual tokens as the array
     *          elements.
     */
    public String[] tokenize(String s);

    /**
     * Tokenize a string.
     *
     * @param s The string to be tokenized.
     * @return The Span[] with the spans (offsets into s) for each
     * token as the individuals array elements.
     */
    public Span[] tokenizePos(String s);
    
}
