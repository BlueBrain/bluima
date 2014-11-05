package ch.epfl.bbp.uima.utils;

import static org.junit.Assert.*;
import javatools.parsers.PlingStemmer;

import org.junit.Test;

public class PlingStemmerTest {

    @Test
    public void test() throws Exception {
        assertEquals("word", PlingStemmer.stem("word"));
        assertEquals("word", PlingStemmer.stem("words"));
        assertEquals("playing", PlingStemmer.stem("playing"));
        assertEquals("fly", PlingStemmer.stem("flies"));
    }
}
