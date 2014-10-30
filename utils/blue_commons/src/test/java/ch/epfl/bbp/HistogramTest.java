package ch.epfl.bbp;

import static org.junit.Assert.*;

import org.junit.Test;

public class HistogramTest {

    @Test
    public void testCut() {

        Histogram<String> h = new Histogram<String>();
        h.add("a", 10);
        h.add("b", 100);
        h.add("c", 1000);

        h.cutAtMin(0);
        assertEquals(10, h.getCount("a").intValue());
        h.cutAtMin(9);
        assertEquals(10, h.getCount("a").intValue());
        h.cutAtMin(10);
        assertEquals(10, h.getCount("a").intValue());
        assertEquals(100, h.getCount("b").intValue());
        h.cutAtMin(11);
        assertEquals(0, h.getCount("a").intValue());
        assertEquals(100, h.getCount("b").intValue());
        h.cutAtMin(111);
        assertEquals(0, h.getCount("b").intValue());
    }

    @Test
    public void testAdd() throws Exception {

        Histogram<String> h = new Histogram<String>();
        h.add("a", 10);
        h.add("b", 100);
        h.add("c", 1000);

        Histogram<String> h2 = new Histogram<String>();
        h2.add("a", 1);
        h2.add("b", 2);
        h2.add("c", 3);

        h.add(h2);
        assertEquals(11, h.getCount("a").intValue());
        assertEquals(102, h.getCount("b").intValue());
    }
}
