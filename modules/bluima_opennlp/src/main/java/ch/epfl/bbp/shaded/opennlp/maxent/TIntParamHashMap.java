package ch.epfl.bbp.shaded.opennlp.maxent;

import gnu.trove.TIntDoubleHashMap;

/**
 * Data structure for storing a models parameters for each outcome associated with a specific context. 
 */
public class TIntParamHashMap extends TIntDoubleHashMap {

  public TIntParamHashMap() {
    super();
  }
  
  public TIntParamHashMap(int initialCapacity) {
    super(initialCapacity);
  }
  
  public TIntParamHashMap(int initialCapacity, float loadFactor) {
    super(initialCapacity, loadFactor);
  }
  
  public double get(int key) {
    int hash, probe, index, length;
    int[] set;
    byte[] states;

    states = _states;
    set = _set;
    length = states.length;
    hash = key & 0x7fffffff;
    index = hash % length;

    if (states[index] != FREE &&
        (states[index] == REMOVED || set[index] != key)) {
        // see Knuth, p. 529
        probe = 1 + (hash % (length - 2));

        do {
            index -= probe;
            if (index < 0) {
                index += length;
            }
        } while (states[index] != FREE &&
                 (states[index] == REMOVED || set[index] != key));
    }
    return states[index] == FREE ? 0d : _values[index];
  }
}
