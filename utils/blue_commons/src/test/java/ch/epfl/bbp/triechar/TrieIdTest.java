package ch.epfl.bbp.triechar;

import static ch.epfl.bbp.io.LineReader.asText;
import static java.lang.System.currentTimeMillis;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.Test;

public class TrieIdTest {

    @Test
    public void testIds() {

        TrieId t = new TrieId();
        t.addWord("he");
        t.addWord("hello");
        t.addWord("hi");
        t.addWord("h");
        t.addWord("hello");
        t.addWord("soup");
        t.addWord("hello");
        t.addWord("hello");

        assertEquals(0, t.getWordId("he"));
        assertEquals(1, t.getWordId("hello"));
        assertEquals(-1, t.getWordId("hg"));
        assertEquals(1, t.getWordId("hello"));
        assertEquals(3, t.getWordId("h"));
        assertEquals(-1, t.getWordId("hw"));
        assertEquals(-1, t.getWordId("g"));
        assertEquals(-1, t.getWordId("hasfdasdf"));
        assertEquals(1, t.getWordId("hello"));

        t.addWord("he");
        assertEquals(0, t.getWordId("he"));
    }

    @Test
    public void testWrite() throws Exception {

        TrieId t = new TrieId();
        t.addWord("four");
        t.addWord("two");
        t.addWord("three");
        t.addWord("one");
        t.addWord("zero");

        String file = "target/trieIdTest_" + currentTimeMillis() + ".txt";
        t.toIdsFile(new FileOutputStream(file));
        assertEquals("four\t0\ntwo\t1\nthree\t2\none\t3\nzero\t4\n",
                asText(new File(file)));
    }
}
