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

/** Inteface for interacting with a Heap data structure.  
 * This implementation extract objects from smallest to largest based on either
 * their natural ordering or the comparator provided to an implementation.
 * While this is a typical of a heap it allows this objects natural ordering to
 * match that of other sorted collections.
 * */
public interface Heap  {

  /** 
   * Removes the smallest element from the heap and returns it.
   * @return The smallest element from the heap.
   */  
  public Object extract();
  
  /**
   * Returns the smallest element of the heap.
   * @return The top element of the heap.
   */
  public Object top();
  
  /**
   * Adds the specified object to the heap.
   * @param o The objec tto add to the heap.
   */
  public void add(Object o);
  
  /**
   * Returns the size of the heap.
   * @return The size of the heap.
   */
  public int size();
  
 /**
  * Returns whether the heap is empty.
  * @return true if the heap is empty; false otherwise.
  */
  public boolean isEmpty();

  
  /**
   * Clears the contents of the heap.
   */
  public void clear();
}
