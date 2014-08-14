package ch.epfl.bbp;

import static ch.epfl.bbp.FileParseUtils.parseToFloatList;
import static ch.epfl.bbp.FileParseUtils.parseToIntegerList;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class FileParseUtilsTest {

    @Test
    public void testParseToList() {
        List<Integer> lInt = new ArrayList<Integer>();
        lInt.add(1);
        assertEquals(lInt, parseToIntegerList("1", " "));
        lInt.add(2);
        assertEquals(lInt, parseToIntegerList("1 2", " "));
        assertEquals(lInt, parseToIntegerList("1,2", ","));
        assertEquals(lInt, parseToIntegerList("1\t2", "\t"));
        assertEquals(lInt, parseToIntegerList("1t2", "t"));
        lInt.add(33);
        assertEquals(lInt, parseToIntegerList("1 2 33", " "));

        List<Float> lFloat = new ArrayList<Float>();
        lFloat.add(1.2f);
        assertEquals(lFloat, parseToFloatList("1.2", " "));

    }

    @Test(expected = NumberFormatException.class)
    public void testWrongInput() {
        parseToIntegerList("1.1", " ");
    }

    @Test(expected = NumberFormatException.class)
    public void testWrongInput2() {
        parseToIntegerList("1", "");
    }

    @Test(expected = NumberFormatException.class)
    public void testWrongInput3() {
        parseToIntegerList("d", " ");
    }

    @Test
    public void testParseIntDouble() throws Exception {
        String f = FileParseUtilsTest.class.getResource(
                "FileParseUtilsTest_intdouble.txt").getFile();
        Map<Integer, Double> i_d = FileParseUtils.parseIntDouble(f, " ");
        assertEquals(3, i_d.size());
        assertEquals(2.3, i_d.get(1), 0.00001);
    }

}
