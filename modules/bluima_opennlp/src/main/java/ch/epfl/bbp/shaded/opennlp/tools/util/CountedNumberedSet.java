package ch.epfl.bbp.shaded.opennlp.tools.util;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Set which counts the number of times a values are added to it and assigns them a unique positive index.  
 * These value can be accessed with the #getCount() or #getIndex() method.
 */

public class CountedNumberedSet extends NumberedSet {
  private Map cset;
  private int max;
  
  /**
   * Creates a new counted set.
   */
  public CountedNumberedSet() {
    cset = new HashMap();
    max=1;
  }
  
  /** Creates a new counted set of the specified initial size.
   * @param size The initial size of this set.
   */
  public CountedNumberedSet(int size) {
    cset = new HashMap(size);
    max = 1;
  }
  
  public boolean add(Object o) {
    int[] nums = (int[]) cset.get(o);  
    if ( nums == null ) { 
      cset.put(o, new int[] {1,max++});
      return true;
    } 
    else { 
      nums[0]++;
      return false;
    }
  }
  
  /**
   * Reduces the count associated with this object by 1.  If this causes the count
   * to become 0, then the object is removed form the set.
   * @param o The object whose count is being reduced.
   */
  public void subtract(Object o) {
    int[] count = (int[]) cset.get(o);  
    if ( count != null ) { 
      count[0]--;
      if (count[0] <= 0) {
        cset.remove(o);
      }
    }
  }
  
  /**
   * Assigns the specified object the specified count in the set.
   * @param o The object to be added or updated in the set.
   * @param c The count of the specified object.
   */
  public void setCount(Object o,int c) {
    int[] nums = (int[]) cset.get(o);
    if (nums != null) {
      nums[0] = c;
    }
    else {
      cset.put(o,new int[]{c,1});
    }
  }
  
  /**
   * Return the count of the specified object.
   * @param o the object whose count needs to be determined.
   * @return the count of the specified object.
   */
  public int getCount(Object o) {
    int[] nums = (int[]) cset.get(o);   
    if (nums == null ) {
      return(0);
    }
    else {
      return(nums[0]);
    }
  }
  
  /**
   * Returns the index for the specified key or -1 if specified value is not contain in this set.
   * @param key The key to be checked.
   * @return the index for the specified value or -1 if specified value is not contain in this set
   */
  public int getIndex(Object key) {
    int[] nums = (int[]) cset.get(key);
    if (nums == null) {
      return -1;
    }
    else {
      return nums[1];
    }
  }
  
  public void write(String fileName,int countCutoff) {
    write(fileName,countCutoff," ");
  }
  
  public void write(String fileName,int countCutoff,String delim) {
    write(fileName,countCutoff,delim,null); 
  }
  
  
  public void write(String fileName,int countCutoff,String delim,String encoding) {
    PrintWriter out = null;
    try{  
      if (encoding != null) {
        out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(fileName),encoding));  
      }
      else {
        out = new PrintWriter(new FileWriter(fileName));  
      }
      
      for (Iterator e = cset.keySet().iterator();  e.hasNext();) {  
        Object key = e.next();  
        int count = this.getCount(key);
        if ( count >= countCutoff ) {
          out.println(count + delim + key);  
        }
      }  
      out.close();  
    }  
    catch (IOException e) {  
      System.err.println(e);  
    }
  }
  
  public boolean addAll(Collection c) {
    boolean changed =  false;
    for (Iterator ci = c.iterator();ci.hasNext();) {
      changed = changed || add(ci.next());
    }
    return changed;
  }
  
  public void clear() {
    cset.clear();
  }
  
  public boolean contains(Object o) {
    return cset.keySet().contains(o);
  }
  
  public boolean containsAll(Collection c) {
    return cset.keySet().containsAll(c);
  }
  
  public boolean isEmpty() {
    return cset.isEmpty();
  }
  
  public Iterator iterator() {
    return cset.keySet().iterator();
  }
  
  public boolean remove(Object o) {
    return (cset.remove(o) != null);
  }
  
  public boolean removeAll(Collection c) {
    boolean changed =false;
    for (Iterator ki = cset.keySet().iterator();ki.hasNext();) {
      changed = changed || (cset.remove(ki.next()) != null);
    }
    return changed;
  }
  
  public boolean retainAll(Collection c) {
    boolean changed = false;
    for (Iterator ki = cset.keySet().iterator();ki.hasNext();) {
      Object key = ki.next();
      if (!c.contains(key)) {
        cset.remove(key);
        changed = true;
      }
    }
    return changed;
  }
  
  public int size() {
    return cset.size();
  }
  
  public Object[] toArray() {
    return cset.keySet().toArray();
  }
  
  public Object[] toArray(Object[] arg0) {
    return cset.keySet().toArray(arg0);
  }
}