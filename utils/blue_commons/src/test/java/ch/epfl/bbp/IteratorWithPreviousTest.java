package ch.epfl.bbp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

public class IteratorWithPreviousTest {

    @Test
    public void test() {

        List<String> list = new LinkedList<String>();
        list.add("1");
        list.add("2");
        list.add("3");

        IteratorWithPrevious<String> it = new IteratorWithPrevious<String>(
                list.listIterator());

        assertTrue(it.hasNext());
        assertEquals("1", it.next());
        assertTrue(it.hasNext());
        assertEquals("2", it.next());
        assertTrue(it.hasNext());
        assertEquals("3", it.next());
        assertFalse("at end of list", it.hasNext());
        assertEquals("going backwards works", "2", it.previous());
        assertTrue(it.hasNext());
        assertEquals("3", it.next());
        assertFalse("at end of list", it.hasNext());
        assertEquals("going backwards works", "2", it.previous());
        assertTrue(it.hasNext());
        assertEquals("going backwards works", "1", it.previous());
    }
}
