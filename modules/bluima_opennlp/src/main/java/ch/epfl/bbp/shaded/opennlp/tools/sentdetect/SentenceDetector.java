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

package ch.epfl.bbp.shaded.opennlp.tools.sentdetect;

/**
 * The interface for sentence detectors, which find the sentence boundaries in
 * a text.
 *
 * @author      Jason Baldridge
 * @version     $Revision: 1.1 $, $Date: 2003/11/05 03:31:04 $
 */

public interface SentenceDetector {

    /**
     * Sentence detect a string.
     *
     * @param s The string to be sentence detected.
     * @return  The String[] with the individual sentences as the array
     *          elements.
     */
    public String[] sentDetect(String s);

    /**
     * Sentence detect a string.
     *
     * @param s The string to be sentence detected.
     * @return  An int[] with the starting offset positions of each
     *          detected sentences. 
     */
    public int[] sentPosDetect(String s);
    
}
