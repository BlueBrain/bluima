package ch.epfl.bbp.shaded.opennlp.tools.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Class which keeps a set of values and assigns each value a unique positive index.  The index
 * can be accessed using the #getIndex() method.
 * @author tsmorton
 *
 */
public class NumberedSet implements Set {

  private int max;
  private Map nset;
  
  public NumberedSet() {
    super();
    nset = new HashMap();
    max=1;
  }
  
  public NumberedSet(int size) {
    super();
    nset = new HashMap(size);
    max=1;
  }

  /**
   * Returns the index for the specified key or -1 if specified value is not contain in this set.
   * @param key The key to be checked.
   * @return the index for the specified value or -1 if specified value is not contain in this set
   */
  public int getIndex(Object key) {
    Integer i = (Integer) nset.get(key);
    if (i == null) {
      return -1;
    }
    else {
      return i.intValue();
    }
  }
  
  public void setIndex(Object key, int index) {
    nset.put(key,new Integer(index));
  }

  public int size() {
    return nset.size();
  }

  public boolean isEmpty() {
    return nset.isEmpty();
  }

  public boolean contains(Object o) {
    return nset.containsKey(o);
  }

  public Iterator iterator() {
    return nset.keySet().iterator();
  }

  public Object[] toArray() {
    return nset.keySet().toArray();
  }

  public Object[] toArray(Object[] arg0) {
    return nset.keySet().toArray(arg0);
  }

  public boolean add(Object arg0) {
    if (!nset.containsKey(arg0)) {
      nset.put(arg0,new Integer(max++));
      return true;
    }
    return false;
  }

  public boolean remove(Object o) {
    return (nset.remove(o) != null);
  }

  public boolean containsAll(Collection c) {
    return nset.keySet().containsAll(c);
  }

  public boolean addAll(Collection c) {
    boolean changed =  false;
    for (Iterator ci = c.iterator();ci.hasNext();) {
      changed = changed || add(ci.next());
    }
    return changed;
  }

  public boolean retainAll(Collection c) {
    boolean changed = false;
    for (Iterator ki = nset.keySet().iterator();ki.hasNext();) {
      Object key = ki.next();
      if (!c.contains(key)) {
        nset.remove(key);
        changed = true;
      }
    }
    return changed;
  }

  public boolean removeAll(Collection c) {
    boolean changed =false;
    for (Iterator ki = nset.keySet().iterator();ki.hasNext();) {
      changed = changed || (nset.remove(ki.next()) != null);
    }
    return changed;
  }

  public void clear() {
    nset.clear();
  }

}
