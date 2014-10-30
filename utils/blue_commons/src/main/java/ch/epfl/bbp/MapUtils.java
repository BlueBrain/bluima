package ch.epfl.bbp;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class MapUtils {

    /**
     * @return the key of the highest value of this map. Note: if this map has
     *         multiple values that are the highest, it returns one of its
     *         corresponding keys.
     */
    public static <K, V extends Comparable<V>> K keyOfHighestValue(Map<K, V> map) {
        K bestKey = null;
        V bestValue = null;
        for (Entry<K, V> entry : map.entrySet()) {
            if (bestValue == null || entry.getValue().compareTo(bestValue) > 0) {
                bestKey = entry.getKey();
                bestValue = entry.getValue();
            }
        }
        return bestKey;
    }

    /**
     * @return the keys of the highest values of this map, in decreasing order.
     *         Note: for multiple identical values, they are returned in
     *         undefined order.
     */
    public static <K, V extends Comparable<V>> LinkedHashMap<K, V> keysOfHighestValues(
            Map<K, V> map, int topN) {
        return keysOfHighestValues(map, topN, true);
    }

    public static <K, V extends Comparable<V>> LinkedHashMap<K, V> keysOfHighestValues(
            Map<K, V> map, int topN, final boolean reverse) {

        LinkedHashMap<K, V> best = new LinkedHashMap<K, V>(topN);
        Iterator<Entry<K, V>> sortedByValueIt = sortByValue(map, reverse)
                .entrySet().iterator();

        while (sortedByValueIt.hasNext() && topN > 0) {
            Map.Entry<K, V> entry = sortedByValueIt.next();
            best.put(entry.getKey(), entry.getValue());
            topN--;
        }
        return best;
    }

    // http://stackoverflow.com/a/2581754/125617
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(
            Map<K, V> map) {
        return sortByValue(map, false);
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(
            Map<K, V> map, final boolean reverse) {

        List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(
                map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                if (reverse)
                    return (o2.getValue()).compareTo(o1.getValue());
                else
                    return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        Map<K, V> result = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
}
