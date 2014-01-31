///////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2001 Artifactus Ltd. and eTranslate Inc.
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

import java.util.*;

/**
 * A class which stores a mapping from ModelDomain objects to MaxentModels.
 * This permits an application to replace an old model for a domain with a
 * newly trained one in a thread-safe manner.  By calling the getModel()
 * method, the application can create new instances of classes which use the
 * relevant models.
 *
 * @author  Jason Baldridge and Eric Friedman
 * @version $Revision: 1.1.1.1 $, $Date: 2001/10/23 14:06:53 $
 */
public class DomainToModelMap {

    // the underlying object which stores the mapping
    private Map map = Collections.synchronizedMap(new HashMap());

    /**
     * Sets the model for the given domain.
     *
     * @param domain The ModelDomain object which keys to the model.
     * @param model The MaxentModel trained for the domain.
     */
    public void setModelForDomain (ModelDomain domain, MaxentModel model) {
	map.put(domain, model); 
    }


    /**
     * Get the model mapped to by the given ModelDomain key.
     *
     * @param domain The ModelDomain object which keys to the desired model.
     * @return The MaxentModel corresponding to the given domain.
     */
    public MaxentModel getModel (ModelDomain domain) {
	if (map.containsKey(domain)) {
	    return (MaxentModel)map.get(domain); 
	}  else {
	    throw new NoSuchElementException("No model has been created for "+
					     "domain: " + domain);
	}
    }
    

    /**
     * Removes the mapping for this ModelDomain key from this map if present.
     *
     * @param domain The ModelDomain key whose mapping is to be removed from
     *               the map.
     */
    public void removeDomain (ModelDomain domain) {
	map.remove(domain);
    }


    /**
     * A set view of the ModelDomain keys contained in this map.
     *
     * @return a set view of the ModelDomain keys contained in this map
     */
    public Set keySet () {
	return map.keySet();
    }

}
