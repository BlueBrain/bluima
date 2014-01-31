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

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Set which counts the number of times a values are added to it.  
 * This value can be accessed with the #getCount method.
 */
public class CountedSet implements Set {

  private Map cset;
  private static Integer ONE = new Integer(1);
  
  /**
   * Creates a new counted set.
   */
  public CountedSet() {
    cset = new HashMap();
  }

  /** Creates a new counted set of the specified initial size.
   * @param size The initial size of this set.
   */
  public CountedSet(int size) {
    cset = new HashMap(size);

  }

  public boolean add(Object o) {
    Integer count = (Integer) cset.get(o);  
    if ( count == null ) { 
      cset.put(o, ONE);
      return true;
    } 
    else { 
      cset.put(o, new Integer(count.intValue()+1));
      return false;
    }
  }

  /**
   * Reduces the count associated with this object by 1.  If this causes the count
   * to become 0, then the object is removed form the set.
   * @param o The object whose count is being reduced.
   */
  public void subtract(Object o) {
    Integer count = (Integer) cset.get(o);  
    if ( count != null ) { 
      int c = count.intValue()-1;
      if (c == 0) {
        cset.remove(o);
      }
      else {
        cset.put(o, new Integer(c)); 
      }
    }
  }

  /**
   * Assigns the specified object the specified count in the set.
   * @param o The object to be added or updated in the set.
   * @param c The count of the specified object.
   */
  public void setCount(Object o,int c) {
    cset.put(o,new Integer(c));
  }

  /**
   * Return the count of the specified object.
   * @param o the object whose count needs to be determined.
   * @return the count of the specified object.
   */
  public int getCount(Object o) {
    Integer count = (Integer) cset.get(o);   
    if ( count == null ) {
      return(0);
    }
    else {
      return(count.intValue());
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
