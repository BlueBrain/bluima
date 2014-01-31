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
//GNU Lesser General Public License for more details.
// 
//You should have received a copy of the GNU Lesser General Public
//License along with this program; if not, write to the Free Software
//Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
//////////////////////////////////////////////////////////////////////////////
package ch.epfl.bbp.shaded.opennlp.tools.util;

import ch.epfl.bbp.shaded.opennlp.maxent.ContextGenerator;

/**
 * Interface for context generators used with a sequence beam search. 
 */
public interface BeamSearchContextGenerator extends ContextGenerator {
    
  /** Returns the context for the specified position in the specified sequence (list).  
     * @param index The index of the sequence.
     * @param sequence  The sequence of items over which the beam seeach is performed.
     * @param priorDecisions The sequence of decisions made prior to the context for which this decision is being made.
     * @param additionalContext Any addition context specific to a class implementing this interface.
     * @return the context for the specified position in the specified sequence.
     */
  public String[] getContext(int index, Object[] sequence, String[] priorDecisions, Object[] additionalContext);
}
