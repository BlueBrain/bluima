package ch.epfl.bbp.trie;

import static org.junit.Assert.*;

import org.junit.Test;

@SuppressWarnings("deprecation")
public class TrieTest {

    @Test
    public void test() {

        Trie t = new Trie();
        t.addWord("he");
        t.addWord("hello");
        t.addWord("hi");
        t.addWord("soup");

        assertEquals(1, t.getWord("he"));
        assertEquals(1, t.getWord("hello"));
        assertEquals(0, t.getWord("hellos"));
        assertEquals(0, t.getWord("h"));
        assertEquals(0, t.getWord("hasfdasdf"));

        t.addWord("he");
        assertEquals(2, t.getWord("he"));
    }

    @Test
    public void testCaseSensitive() {

        Trie t = new Trie(true);
        t.addWord("He");
        t.addWord("hi");

        assertEquals(0, t.getWord("he"));
        assertEquals(1, t.getWord("He"));

        assertEquals(1, t.getWord("hi"));
        assertEquals(0, t.getWord("Hi"));
    }
}
