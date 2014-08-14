package ch.epfl.bbp.uima.filter;

import static ch.epfl.bbp.uima.filter.KeepsCleaner.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class KeepsCleanerTest {

    @Test
    public void testRemovePunctAtEndOrBegin() {
        assertEquals("no changes", "dystrophy", cleanup("dystrophy"));
        assertEquals("no changes", "d!yst'rophy", cleanup("d!yst'rophy"));

        assertEquals("dystrophy", cleanup("dystrophy."));
        assertEquals("dystrophy", cleanup("dystrophy\\"));

        assertEquals("dystrophin", cleanup("[dystrophin"));
        assertEquals("dystrophin", cleanup("[dystrophin]"));
        assertEquals("dys-trophin", cleanup("[dys-trophin;'[-]"));

        assertEquals("not", cleanup("not?\""));
        assertEquals("what", cleanup("'what"));
        assertEquals("seizures", cleanup("seizures'"));
        assertEquals("impermeant", cleanup("-impermeant"));

    }

    @Test
    public void testBalancedParenth() {
        assertTrue(isBalanced("a{b}c(dd[e]v)g"));
        assertFalse(isBalanced("([}])"));
        assertTrue(isBalanced("([])"));
        assertTrue(isBalanced("()[]{}[][]"));
    }

    @Test
    public void testOnlyPunctAndNumbers() {
        assertFalse(isOnlyPunctAndNumbers("a{b}c(dd[e]v)g"));
        assertTrue(isOnlyPunctAndNumbers("10(-10"));
        assertTrue(isOnlyPunctAndNumbers("1:200,000"));

    }
}
