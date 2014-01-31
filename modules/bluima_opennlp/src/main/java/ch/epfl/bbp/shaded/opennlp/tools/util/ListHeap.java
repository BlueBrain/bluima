///////////////////////////////////////////////////////////////////////////////
//Copyright (C) 2005 Thomas Morton
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

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Comparator;

/**
 * This class implements the heap interface using a {@link java.util.List} as the underlying
 * data structure.  This heap allows values which are equals to be inserted, however
 * the order in which they are extracted is arbitrary.
 */
public class ListHeap implements Heap {
  private List list;

  private Comparator comp;

  private int size;

  private Object max = null;

  /**
   * Creates a new heap with the specified size using the sorted based on the
   * specified comparator.
   * @param sz The size of the heap.
   * @param c The comparator to be used to sort heap elements.
   */
  public ListHeap(int sz, Comparator c) {
    size = sz;
    comp = c;
    list = new ArrayList(sz);
  }

  /**
   * Createa a new heap of the specified size.
   * @param sz The size of the new heap.
   */
  public ListHeap(int sz) {
    this(sz, null);
  }

  private int parent(int i) {
    return (i - 1) / 2;
  }

  private int left(int i) {
    return (i + 1) * 2 - 1;
  }

  private int right(int i) {
    return (i + 1) * 2;
  }

  public int size() {
    return list.size();
  }

  private void swap(int x, int y) {
    Object ox = list.get(x);
    Object oy = list.get(y);

    list.set(y, ox);
    list.set(x, oy);
  }

  private boolean lt(Object o1, Object o2) {
    if (comp != null) {
      return (comp.compare(o1, o2) < 0);
    }
    else {
      return (((Comparable) o1).compareTo(o2) < 0);
    }
  }

  private boolean gt(Object o1, Object o2) {
    if (comp != null) {
      return (comp.compare(o1, o2) > 0);
    }
    else {
      return (((Comparable) o1).compareTo(o2) > 0);
    }
  }

  private void heapify(int i) {
    while (true) {
      int l = left(i);
      int r = right(i);
      int smallest;

      if (l < list.size() && lt(list.get(l), list.get(i)))
        smallest = l;
      else
        smallest = i;

      if (r < list.size() && lt(list.get(r), list.get(smallest)))
        smallest = r;

      if (smallest != i) {
        swap(smallest, i);
        i = smallest;
      }
      else
        break;
    }
  }

  public Object extract() {
    if (list.size() == 0) {
      throw new RuntimeException("Heap Underflow");
    }
    Object top = list.get(0);
    int last = list.size() - 1;
    if (last != 0) {
      list.set(0, list.remove(last));
      heapify(0);
    }
    else {
      list.remove(last);
    }

    return top;
  }

  public void setSize() {
    setSize(size);
  }

  public void setSize(int sz) {
    if (sz > list.size()) {
      return;
    }
    else {
      List t = new ArrayList(sz);
      for (int i = 0; i < sz; i++) {
        t.add(this.extract());
      }
      list = t;
    }
  }

  public Object top() {
    if (list.size() == 0) {
      throw new RuntimeException("Heap Underflow");
    }
    return (list.get(0));
  }

  public void add(Object o) {
    /* keep track of min to prevent unnecessary insertion */
    if (max == null) {
      max = o;
    }
    else if (gt(o, max)) {
      if (list.size() < size) {
        max = o;
      }
      else {
        return;
      }
    }
    list.add(o);

    int i = list.size() - 1;

    //percolate new node to correct position in heap.
    while (i > 0 && gt(list.get(parent(i)), o)) {
      list.set(i, list.get(parent(i)));
      i = parent(i);
    }

    list.set(i, o);
  }

  public void clear() {
    list.clear();
  }

  public Iterator iterator() {
    return (list.iterator());
  }
  
  public boolean isEmpty() {
    return this.list.isEmpty();
  }
  
  public static void main(String[] args) {
   Heap heap = new ListHeap(5);
   for (int ai=0;ai<args.length;ai++){
     heap.add(new Integer(Integer.parseInt(args[ai])));
   }
   while (!heap.isEmpty()) {
     System.out.print(heap.extract()+" ");
   }
   System.out.println();
  }
}

