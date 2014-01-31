package ch.epfl.bbp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.junit.Test;

public class MapUtilsTest {

    @Test
    public void testKeyOfHighestValue() {

        Map<String, Double> mapp = new HashMap<String, Double>();
        mapp.put("s3.5", 3.5);
        mapp.put("s1.5", 1.5);
        mapp.put("s0.5", 0.5);
        mapp.put("s0.6", 0.6);
        mapp.put("2s3.5", 3.5);
        mapp.put("s2.6", 2.6);

        assertEquals("2s3.5", MapUtils.keyOfHighestValue(mapp));
    }

    // http://stackoverflow.com/a/2581754/125617
    @Test
    public void testSortByValue() {
        Random random = new Random(System.currentTimeMillis());
        Map<String, Integer> testMap = new HashMap<String, Integer>(1000);
        for (int i = 0; i < 1000; ++i) {
            testMap.put("SomeString" + random.nextInt(), random.nextInt());
        }

        testMap = MapUtils.sortByValue(testMap);
        assertEquals(1000, testMap.size());

        Integer previous = null;
        for (Map.Entry<String, Integer> entry : testMap.entrySet()) {
            assertNotNull(entry.getValue());
            if (previous != null) {
                assertTrue(entry.getValue() >= previous);
            }
            previous = entry.getValue();
        }
    }

    @Test
    public void testSortByReverseValue() {
        Random random = new Random(System.currentTimeMillis());
        Map<String, Integer> testMap = new HashMap<String, Integer>(1000);
        for (int i = 0; i < 1000; ++i) {
            testMap.put("SomeString" + random.nextInt(), random.nextInt());
        }

        testMap = MapUtils.sortByValue(testMap, true);
        assertEquals(1000, testMap.size());

        Integer previous = null;
        for (Map.Entry<String, Integer> entry : testMap.entrySet()) {
            assertNotNull(entry.getValue());
            if (previous != null) {
                assertTrue(entry.getValue() <= previous);
            }
            previous = entry.getValue();
        }
    }
}
