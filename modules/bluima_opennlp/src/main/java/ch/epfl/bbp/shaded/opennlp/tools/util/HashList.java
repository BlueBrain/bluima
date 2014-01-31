///////////////////////////////////////////////////////////////////////////////
//Copyright (C) 2003 Jeremy LaCivita
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

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

/** 
 * Class which creates mapping between keys and a list of values.  
 */
public class HashList extends HashMap {

  public HashList() {
    super();
  }
  
  public Object get(Object key, int index) {
    if (get(key) == null) {
      return get(key);
    }
    else {
      return ((List)get(key)).get(index);
    }
  }

  public Object putAll(Object key, Collection values) {
    List o = (List)get(key);

    if (o == null) {
      o = new ArrayList();
      super.put(key, o);
    }

    o.addAll(values);

    if (o.size() == values.size())
      return null;
    else
      return o;
  }

  public Object put(Object key, Object value) {
    List o = (List)get(key);
    
    if (o == null) {
      o = new ArrayList();
      super.put(key, o);
    } 

    o.add(value);

    if(o.size() == 1)
      return null;
    else
      return o;
  }

  public boolean remove(Object key, Object value) {
    List l = (List) get(key);
    if (l == null) {
      return(false);
    }
    else {
      boolean r = l.remove(value);
      if (l.size() == 0) {
	remove(key);
      }
      return(r);
    }
  }
}

