package ch.epfl.bbp.shaded.opennlp.tools.util;

import java.util.SortedSet;
import java.util.TreeSet;

/** 
 * An implmention of the Heap interface based on {@link java.util.SortedSet}.
 * This implementation will not allow multiple objects which are equal to be added to the heap.
 * Only use this implementation when object in the heap can be totally ordered (no duplicates). 
 */
public class TreeHeap implements Heap {

  private SortedSet tree;

  /** 
   * Creates a new tree heap.
   */
  public TreeHeap() {
    tree = new TreeSet();
  }

  /**
   * Creates a new tree heap of the specified size.
   * @param size The size of the new tree heap.
   */
  public TreeHeap(int size) {
    tree = new TreeSet();
  }

  public Object extract() {
    Object rv = tree.first();
    tree.remove(rv);
    return rv;
  }

  public Object top() {
    return tree.first();
  }

  public void add(Object o) {
    tree.add(o);
  }

  public int size() {
    return tree.size();
  }

  public void clear() {
    tree.clear();
  }

  public void setSize() {}

  public void setSize(int size) {}
  
  public boolean isEmpty(){
    return this.tree.isEmpty();
  }
  
  public static void main(String[] args) {
    Heap heap = new TreeHeap(5);
    for (int ai=0;ai<args.length;ai++){
      heap.add(new Integer(Integer.parseInt(args[ai])));
    }
    while (!heap.isEmpty()) {
      System.out.print(heap.extract()+" ");
    }
    System.out.println();
   }

}
