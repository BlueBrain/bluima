package ch.epfl.bbp.shaded.opennlp.maxent;

import gnu.trove.TObjectHashingStrategy;
import gnu.trove.TObjectIntHashMap;

/** Data structure storing the mapping between a context and its integer value. */
public class TObjectIndexHashMap extends TObjectIntHashMap {
  
  public TObjectIndexHashMap() {
    super();
  }
  
  public TObjectIndexHashMap(TObjectHashingStrategy arg0) {
    super(arg0);
  }
  
  public TObjectIndexHashMap(int arg0) {
    super(arg0);
  }
  
  public TObjectIndexHashMap(int arg0, float arg1) {
    super(arg0, arg1);
  }
  
  public TObjectIndexHashMap(int arg0, float arg1, TObjectHashingStrategy arg2) {
    super(arg0, arg1, arg2);
  }
  
  public TObjectIndexHashMap(int arg0, TObjectHashingStrategy arg1) {
    super(arg0, arg1);
  }
  
  public int get(Object key) {
    int index = index(key);
    return index < 0 ? (int)-1 : _values[index];
  }
}
