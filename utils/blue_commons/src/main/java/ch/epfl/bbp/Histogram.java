package ch.epfl.bbp;

import static ch.epfl.bbp.MissingUtils.getOrElse;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeSet;

/**
 * Utility to create a Histogram
 * 
 * @author renaud.richardet@epfl.ch
 * 
 * @param <T>
 *            the type of the histogram category
 */
public class Histogram<T> {

    Map<T, Long> map = new HashMap<T, Long>();

    /**
     * Adds an occurence of this value into the Histogram
     * 
     * @param value
     */
    public void add(T value) {
        if (value == null)
            return;

        if (map.containsKey(value)) {
            map.put(value, map.get(value) + 1);
        } else {
            map.put(value, 1l);
        }
    }

    /**
     * Adds times an occurence of this value into the Histogram
     * 
     * @param value
     * @param times
     */
    public void add(T value, int times) {
        if (value == null)
            return;

        if (map.containsKey(value)) {
            map.put(value, map.get(value) + times);
        } else {
            map.put(value, times + 0l);
        }
    }

    /**
     * @param value
     * @return the number of times that 'value' occurs in the Histogram, or 0 if
     *         this value is not in the histogram
     */
    public Long getCount(T value) {
        if (map.containsKey(value))
            return map.get(value);
        else
            return 0L;
    }

    public Map<T, Long> getMap() {
        return map;
    }

    /**
     * @return the number of distinct 'values' in the Histogram
     * @see {@link #totalSize()} as well.
     */
    public int size() {
        return map.size();
    }

    /**
     * @return the total size of the dataset
     * @see {@link #size()} as well.
     */
    public Long totalSize() {
        long total = 0;
        for (Long vals : map.values()) {
            total += vals;
        }
        return total;
    }

    @Override
    public String toString() {
        TreeSet<T> ordered = new TreeSet<T>(map.keySet());
        StringBuilder sb = new StringBuilder();
        sb.append("cnt\tkey\n");
        for (T key : ordered.descendingSet()) {
            sb.append(map.get(key) + "\t" + key + "\n"); // count tab entry
        }
        return sb.toString();
    }

    public void cutAtMin(final int minCount) {
        Iterator<Entry<T, Long>> iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            if (iter.next().getValue() < minCount)
                iter.remove();
        }
    }

    public void add(Histogram<T> anotherHistogram) {
        for (Entry<T, Long> e : anotherHistogram.map.entrySet()) {
            map.put(e.getKey(), getOrElse(map, e.getKey(), 0L) + e.getValue());
        }
    }
}
