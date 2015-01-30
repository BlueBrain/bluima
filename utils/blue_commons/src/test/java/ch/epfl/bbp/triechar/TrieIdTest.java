package ch.epfl.bbp.triechar;

import static ch.epfl.bbp.io.LineReader.asText;
import static java.lang.System.currentTimeMillis;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
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

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyString() {
        TrieId t = new TrieId();
        t.addWord("");
    }

    @Test
    public void testWrite() throws Exception {

        TrieId t = new TrieId();
        t.addWord("zero");
        t.addWord("one");
        t.addWord("two");
        t.addWord("three");
        t.addWord("four");

        String file = "target/trieIdTest_" + currentTimeMillis() + ".txt";
        t.toIdsFile(new FileOutputStream(file));
        assertEquals("four\t4\ntwo\t2\nthree\t3\none\t1\nzero\t0\n",
                asText(new File(file)));

        TrieId tSer = TrieId.fromIdsFile(new FileInputStream(file));
        assertEquals(0, tSer.getWordId("zero"));
        assertEquals(4, tSer.getWordId("four"));
        assertEquals(-1, tSer.getWordId("blah"));
    }

    @Test
    public void testSerDeser() throws Exception {

        for (String testString : new String[] { "simple", "a\tb", "a\tb\t",
                "a\t\tb", "\t" }) {

            TrieId t = new TrieId();
            t.addWord("0");
            t.addWord(testString);
            t.addWord("2");

            String file = "target/trieIdTest_" + currentTimeMillis() + ".txt";
            t.toIdsFile(new FileOutputStream(file));

            TrieId tSer = TrieId.fromIdsFile(new FileInputStream(file));
            assertEquals(0, tSer.getWordId("0"));
            assertEquals("could not find '" + testString + "' back in trie", 1,
                    tSer.getWordId(testString));
            assertEquals(2, tSer.getWordId("2"));
            assertEquals(-1, tSer.getWordId("blah"));
        }
    }
}
