package ch.epfl.bbp.uima.validation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Utils for Validation
 * 
 * @author renaud.richardet@epfl.ch
 * 
 */
public class ValidationUtils {

    /**
     * @param count
     *            the number of records
     * @param bins
     *            the number of folds
     * @return a random map of {@link Map} [record_id:fold_id]
     */
    public static Map<Integer, Integer> shuffledMap(int count, int bins) {

        Map<Integer, Integer> ids = new HashMap<Integer, Integer>();

        int running = 0;
        for (int i = 0; i < count; i++) {
            ids.put(i, running++ % bins);
        }

        List<Integer> valueList = new ArrayList<Integer>(ids.values());
        Collections.shuffle(valueList);
        Iterator<Integer> valueIt = valueList.iterator();
        for (Map.Entry<Integer, Integer> e : ids.entrySet()) {
            e.setValue(valueIt.next());
        }
        return ids;
    }

}
