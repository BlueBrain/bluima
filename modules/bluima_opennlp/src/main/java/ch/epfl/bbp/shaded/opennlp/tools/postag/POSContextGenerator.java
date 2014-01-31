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

package ch.epfl.bbp.shaded.opennlp.tools.postag;

import ch.epfl.bbp.shaded.opennlp.tools.util.BeamSearchContextGenerator;


/**
 * The interface for a context generator for the POS Tagger.
 *
 * @author      Gann Bierner
 * @version     $Revision: 1.8 $, $Date: 2005/11/20 04:52:19 $
 */

public interface  POSContextGenerator extends BeamSearchContextGenerator { 
  public String[] getContext(int pos, Object[] tokens, String[] prevTags, Object[] ac);
}
