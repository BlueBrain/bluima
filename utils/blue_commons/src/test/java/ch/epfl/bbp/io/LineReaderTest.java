package ch.epfl.bbp.io;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

public class LineReaderTest {

    @Test
    public void testReadLines() throws Exception {

        List<String> theints = LineReader.linesFrom(LineReader.class
                .getResourceAsStream("LineReader_ints.txt"));
        assertEquals(4, theints.size());
        assertEquals("1", theints.get(0));
        assertEquals("2", theints.get(1));
        assertEquals("3", theints.get(2));
        assertEquals("1", theints.get(3));
    }

    @Test
    public void testReadInts() throws Exception {

        int[] theints = LineReader.intsFrom(LineReader.class
                .getResourceAsStream("LineReader_ints.txt"));
        assertEquals(4, theints.length);
        assertEquals(1, theints[0]);
        assertEquals(2, theints[1]);
        assertEquals(3, theints[2]);
        assertEquals(1, theints[3]);
    }

    @Test
    public void testReadIntsQuoted() throws Exception {

        int[] theints = LineReader.intsFrom(LineReader.class
                .getResourceAsStream("LineReader_ints_quoted.txt"));
        assertEquals(4, theints.length);
        assertEquals(1, theints[0]);
        assertEquals(2, theints[1]);
        assertEquals(3, theints[2]);
        assertEquals(1, theints[3]);
    }
}
