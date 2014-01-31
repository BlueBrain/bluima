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

import ch.epfl.bbp.shaded.opennlp.tools.util.BeamSearchContextGenerator;


/**
 * Interface for the context generator used in name finding.
 */
public interface NameContextGenerator extends BeamSearchContextGenerator {
  
  /**
   * Returns the contexts for chunking of the specified index.
   * @param i The index of the token in the specified toks array for which the context should be constructed. 
   * @param toks The tokens of the sentence.  The <code>toString</code> methods of these objects should return the token text.
   * @param preds The previous decisions made in the taging of this sequence.  Only indices less than i will be examined.
   * @param prevTags A mapping between tokens and the previous outcome for these tokens. 
   * @return An array of predictive contexts on which a model basis its decisions.
   */
  public abstract String[] getContext(int i, List toks,List preds, Map prevTags);
}