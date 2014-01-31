package ch.epfl.bbp.collections;

import java.util.HashMap;
import java.util.Map;

/** Simulate the behaviour of Python's defaultdict */
public class DefaultHashMap<K, V> extends HashMap<K, V> {
    private static final long serialVersionUID = 1L;

    private final Class<V> cls;
    private final Number defaultValue;

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public DefaultHashMap(Class factory) {
        this.cls = factory;
        this.defaultValue = null;
    }

    public DefaultHashMap(Number defaultValue) {
        this.cls = null;
        this.defaultValue = defaultValue;
    }

    @SuppressWarnings("unchecked")
    @Override
    public V get(Object key) {
        V value = super.get(key);
        if (value == null) {
            if (defaultValue == null) {
                try {
                    value = cls.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                value = (V) defaultValue;
            }
            this.put((K) key, value);
        }
        return value;
    }

    public static <T> Map<T, Integer> intDefaultMap() {
        return new DefaultHashMap<T, Integer>(0);
    }

    public static <T> Map<T, Double> doubleDefaultMap() {
        return new DefaultHashMap<T, Double>(0d);
    }

    public static <T> Map<T, Float> floatDefaultMap() {
        return new DefaultHashMap<T, Float>(0f);
    }

    public static <T> Map<T, String> stringDefaultMap() {
        return new DefaultHashMap<T, String>(String.class);
    }
}