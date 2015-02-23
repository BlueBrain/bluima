package ch.epfl.bbp.triechar;

import static org.junit.Assert.*;

import org.junit.Test;

public class VocabularyTest {

    @Test
    public void testHello() {
        Vocabulary v = new Vocabulary(true);
        int hello = v.addWord("hello");
        assertEquals(0, hello);
        assertEquals("hello", v.getWord(hello));
        assertEquals(1, v.getSize());
    }

    @Test
    public void testHello2() {
        Vocabulary v = new Vocabulary(true);
        int he = v.addWord("he");
        assertEquals(1, v.getSize());
        assertEquals(0, he);
        int h = v.addWord("h");
        assertEquals(1, h);
        assertEquals(2, v.getSize());
        int h2 = v.addWord("h");
        assertEquals(2, v.getSize());
        assertEquals("h was already in voc", 1, h2);
        int hello = v.addWord("hello");
        assertEquals(2, hello);
        assertEquals(3, v.getSize());

        assertEquals("he", v.getWord(he));
        assertEquals("h", v.getWord(h));
        assertEquals("hello", v.getWord(hello));
    }
}
