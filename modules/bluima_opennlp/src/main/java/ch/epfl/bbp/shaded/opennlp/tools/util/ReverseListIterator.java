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
package ch.epfl.bbp.shaded.opennlp.tools.util;

import java.util.Iterator;
import java.util.List;

/**
 * An iterator for a list which returns values in the opposite order as the typical list iterator.
 */
public class ReverseListIterator implements Iterator {
  
  private int index;
  private List list;

  public ReverseListIterator(List list) {
    index = list.size()-1;
    this.list=list;
  }

  public Object next() {
    return(list.get(index--));
  }

  public boolean hasNext() {
    return(index >=0);
  }

  public void remove() {
    throw(new UnsupportedOperationException());
  }

}
