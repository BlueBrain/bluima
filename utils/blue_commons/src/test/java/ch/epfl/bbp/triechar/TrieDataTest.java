package ch.epfl.bbp.triechar;

import static ch.epfl.bbp.io.LineReader.asText;
import static java.lang.System.currentTimeMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.Test;

import ch.epfl.bbp.triechar.TrieData;

public class TrieDataTest {

	@Test
	public void test() {

		TrieData<Integer> t = new TrieData<Integer>();
		t.addWord("he", 1);
		t.addWord("hello", 2);
		t.addWord("hi", 3);
		t.addWord("hello", 4);
		t.addWord("soup", 5);
		t.addWord("hello", 14);
		t.addWord("hello", 24);

		assertEquals(1, (int) t.getWordData("he"));
		assertEquals(24, (int) t.getWordData("hello"));
		assertNull(t.getWordData("hellos"));
		assertNull(t.getWordData("h"));
		assertNull(t.getWordData("hasfdasdf"));

		t.addWord("he", 10);
		assertEquals(10, (int) t.getWordData("he"));
	}
	
	@Test
	public void testIterate() {
		
		TrieData<Integer> t = new TrieData<Integer>();
		t.addWord("1", 1);
		t.addWord("2", 2);
		t.addWord("100", 100);
		t.addWord("1000", 1000);
		
		for (TrieNodeData<Integer> trieNodeData : t) {
			System.out.println(trieNodeData);
		}
	}

	@Test
	public void testCaseSensitive() {

		TrieData<Double> t = new TrieData<Double>(true);
		t.addWord("He", 1d);
		t.addWord("hi", 2d);

		assertNull(t.getWordData("he"));
		assertEquals(1d, (double) t.getWordData("He"), 0d);

		assertEquals(2d, (double) t.getWordData("hi"), 0d);
		assertNull(t.getWordData("Hi"));
	}

	@Test
	public void testWrite() throws Exception {

		TrieData<String> t = new TrieData<String>();
		t.addWord("he", "one");
		t.addWord("hello", "two");
		t.addWord("hi", "three");
		t.addWord("awenwioen", "four");

		String filePath = "target/trieTest_" + currentTimeMillis() + ".txt";
		t.toIdFile(new FileOutputStream(filePath));
		assertEquals("awenwioen\tfour\nhe\tone\nhello\ttwo\nhi\tthree\n",
				asText(new File(filePath)));
	}
}
