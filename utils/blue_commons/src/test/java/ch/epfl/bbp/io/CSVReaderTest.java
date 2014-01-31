package ch.epfl.bbp.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import ch.epfl.bbp.ResourceHelper;
import ch.epfl.bbp.io.SVReader.CSVReader;

public class CSVReaderTest {

    @Test
    public void test() throws Exception {

        File csv1 = ResourceHelper.getFile("CSV/test1.csv");
        CSVReader reader = new CSVReader(csv1, true);

        Iterator<List<String>> it = reader.iterator();

        assertTrue(it.hasNext());
        List<String> l1 = it.next();
        assertEquals("1", l1.get(0));
        assertTrue(it.hasNext());
        List<String> l2 = it.next();
        assertEquals("3", l2.get(0));
    }

    @Test
    public void testEscaped() throws Exception {

        File csv2 = ResourceHelper.getFile("CSV/test2.csv");
        CSVReader reader = new CSVReader(csv2, true);

        Iterator<List<String>> it = reader.iterator();

        assertTrue(it.hasNext());
        List<String> l1 = it.next();
        assertEquals("one", l1.get(0));
        assertTrue(it.hasNext());
        assertTrue(it.hasNext());
        @SuppressWarnings("unused")
        List<String> l2 = it.next();
        List<String> l3 = it.next();
        assertEquals("fi,ve", l3.get(0));
        assertEquals("si,,x", l3.get(1));
    }

}
