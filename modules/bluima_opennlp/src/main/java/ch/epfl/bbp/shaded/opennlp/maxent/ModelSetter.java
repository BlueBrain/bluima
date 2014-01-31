///////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2001 Jason Baldridge and Gann Bierner
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
//////////////////////////////////////////////////////////////////////////////   
package ch.epfl.bbp.shaded.opennlp.maxent;

/**
 * A object to facilitate the resetting of a MaxentModel variable to a
 * new value (model).  In general this will be used anonymously, for example, as
 * follows: 
 * <p>
 *     <pre>
 *     private final ModelReplacementManager replacementManager =
 *	  new ModelReplacementManager(
 *	      new ModelSetter() {
 *		  public void setModel(MaxentModel m) {
 *		      model = m;
 *		  }
 *	      }
 *	  );
 *     </pre>
 * <p>
 * where "model" would be the actual variable name of the model used by your
 * application which you wish to be able to swap (you might have other models
 * which need their own ModelSetters).
 *
 * <p>
 * Basically, this is just a clean way of giving a ModelReplacementManager
 * access to a private variable holding the model.  Nothing complex here.
 *
 * @author      Jason Baldridge
 * @version     $Revision: 1.1.1.1 $, $Date: 2001/10/23 14:06:53 $
 */

public interface ModelSetter {

    /**
     * Assign a new MaxentModel value to a MaxentModel variable.
     *
     * @param m The new model.
     */
    public void setModel (MaxentModel m);
}
