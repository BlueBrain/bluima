package ch.epfl.bbp;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility to perform in-place (statically configured) parameter configuration
 * for optimization.
 * 
 * @author renaud.richardet@epfl.ch
 */
public class StaticConfParam {

    private static Map<String, List<Object>> store = new HashMap<String, List<Object>>();
    private static Map<String, Integer> indexes = new HashMap<String, Integer>();

    public static void register(String key, Object... params) {
	store.put(key, Arrays.asList(params));
    }

    public static void configure(String key, int index) {

	if (!store.containsKey(key)) {
	    System.err.println("JUST WARNING YOU: no key " + key + " in store");
	} else {

	    List<Object> params = store.get(key);
	    if (index > params.size()) {
		System.err.println("JUST WARNING YOU: index " + index
			+ " larger than parameter list for key " + key);
	    }
	}

	indexes.put(key, index);
    }

    public static Object get(String key) {

	if (!store.containsKey(key)) {
	    System.err.println("no key " + key + " in store");
	    return null;
	}

	if (!indexes.containsKey(key)) {
	    System.err.println("no key " + key + " selected in current");
	    return null;
	}

	Integer index = indexes.get(key);
	List<Object> params = store.get(key);

	if (index > params.size()) {
	    System.err.println("index " + index
		    + " larger than parameter list for key " + key);
	    return null;
	}

	System.err.println("using param with index " + index + " for key "
		+ key);
	return params.get(index);
    }

    public static void clear(String key) {
	store.remove(key);
	indexes.remove(key);
    }

    public static void clearAll() {
	store = new HashMap<String, List<Object>>();
	indexes = new HashMap<String, Integer>();
    }
}
