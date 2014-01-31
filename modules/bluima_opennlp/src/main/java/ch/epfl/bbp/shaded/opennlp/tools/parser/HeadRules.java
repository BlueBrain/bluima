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
package ch.epfl.bbp.shaded.opennlp.tools.parser;

import java.util.Set;

/** Interface for encoding the head rules associated with parsing. 
 * @author Tom Morton
 *
 */
public interface HeadRules {
  
  /**
   * Returns the head constituent for the specified constituents of the specified type.
   * @param constituents The constituents which make up a constituent of the specified type.
   * @param type The type of a constituent which is made up of the specifed constituents.
   * @return The constituent which is the head.
   */
  public Parse getHead(Parse[] constituents, String type);
  
  /**
   * Returns the set of punctuation tags.  Attachment decisions for these tags will not be modeled. 
   * @return the set of punctuation tags.
   */
  public Set getPunctuationTags();
}