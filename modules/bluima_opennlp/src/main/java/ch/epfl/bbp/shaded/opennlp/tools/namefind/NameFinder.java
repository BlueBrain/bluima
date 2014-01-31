///////////////////////////////////////////////////////////////////////////////
//Copyright (C) 2003 Thomas Morton
// 
//This library is free software; you can redistribute it and/or
//modify it under the terms of the GNU Lesser General Public
//License as published by the Free Software Foundation; either
//version 2.1 of the License, or (at your option) any later version.
// 
//This library is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU Lesser General Public License for more details.
// 
//You should have received a copy of the GNU Lesser General Public
//License along with this program; if not, write to the Free Software
//Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
//////////////////////////////////////////////////////////////////////////////
package ch.epfl.bbp.shaded.opennlp.tools.namefind;

import java.util.List;
import java.util.Map;

/**
 * The interface for name finders which provide name tags for a sequence of tokens.
 * @author      Thomas Morton
 */
public interface NameFinder {
  
  /** Generates name tags for the given sequence returning the result in a list.
   * @param toks a list of the tokens or words of the sequence.
   * @param prevTags a mapping between tokens and outcomes from previous sentences. 
   * @return a list of chunk tags for each token in the sequence.
   */
  public List find(List toks, Map prevTags);
  
  /** Generates name tags for the given sequence returning the result in an array.
   * @param toks an array of the tokens or words of the sequence.
   * @param prevTags a mapping between tokens and outcomes from previous sentences. 
   * @return an array of chunk tags for each token in the sequence.
   */
  public String[] find(Object[] toks, Map prevTags);
}
