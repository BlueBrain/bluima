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
//GNU General Public License for more details.
//
//You should have received a copy of the GNU Lesser General Public
//License along with this program; if not, write to the Free Software
//Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
//////////////////////////////////////////////////////////////////////////////   
package ch.epfl.bbp.shaded.opennlp.tools.parser;

import java.util.List;

import ch.epfl.bbp.shaded.opennlp.tools.chunker.Chunker;
import ch.epfl.bbp.shaded.opennlp.tools.util.Sequence;


/**
 * Interface that a chunker used with the parser should implement. 
 */
public interface ParserChunker extends Chunker {
  /**
   * Returns the top k chunk sequences for the specified sentence with the specified pos-tags   
   * @param sentence The tokens of the sentence.
   * @param tags The pos-tags for the specified sentence.
   * @return the top k chunk sequences for the specified sentence.
   */
  public Sequence[] topKSequences(List sentence, List tags);
  
  /**
   * Returns the top k chunk sequences for the specified sentence with the specified pos-tags   
   * @param sentence The tokens of the sentence.
   * @param tags The pos-tags for the specified sentence.
   * @return the top k chunk sequences for the specified sentence.
   */
  public Sequence[] topKSequences(String[] sentence, String[] tags, double minSequenceScore);
}
