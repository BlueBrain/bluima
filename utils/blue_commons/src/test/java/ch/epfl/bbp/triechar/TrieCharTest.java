package ch.epfl.bbp.triechar;

import static ch.epfl.bbp.io.LineReader.asText;
import static java.lang.System.currentTimeMillis;
import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;

public class TrieCharTest {

    @Test
    public void test() {

        Trie t = new Trie();
        t.addWord("he");
        t.addWord("hello");
        t.addWord("hi");
        t.addWord("hello");
        t.addWord("soup");
        t.addWord("hello");
        t.addWord("hello");

        assertEquals(1, t.getWord("he"));
        assertEquals(4, t.getWord("hello"));
        assertEquals(0, t.getWord("hellos"));
        assertEquals(0, t.getWord("h"));
        assertEquals(0, t.getWord("hasfdasdf"));

        t.addWord("he");
        assertEquals(2, t.getWord("he"));
    }

    @Test
    public void testCaseSensitive() {

        ch.epfl.bbp.triechar.Trie t = new ch.epfl.bbp.triechar.Trie(true);
        t.addWord("He");
        t.addWord("hi");

        assertEquals(0, t.getWord("he"));
        assertEquals(1, t.getWord("He"));

        assertEquals(1, t.getWord("hi"));
        assertEquals(0, t.getWord("Hi"));
    }

    @Test
    public void testWrite() throws Exception {

        ch.epfl.bbp.triechar.Trie t = new ch.epfl.bbp.triechar.Trie();
        t.addWord("he");
        t.addWord("he");
        t.addWord("hello");
        t.addWord("hi");
        t.addWord("hi");
        t.addWord("hi");
        t.addWord("awenwioen");

        String file = "target/trieTest_" + currentTimeMillis() + ".txt";
        t.toFrequencyFile(file);
        assertEquals("awenwioen\t1\nhe\t2\nhello\t1\nhi\t3\n", asText(new File(
                file)));
    }
}
