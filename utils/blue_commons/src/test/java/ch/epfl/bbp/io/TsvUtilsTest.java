package ch.epfl.bbp.io;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;

public class TsvUtilsTest {

    @Test
    public void test() throws Exception {

        Map<Integer, String> map = TsvUtils.loadIntString(
                TsvUtils.class.getResourceAsStream("TsvUtils_int_string.tsv"),
                false);
        assertEquals(3, map.size());
        assertEquals("bb", map.get(2));
        assertEquals("dd", map.get(1));
    }
}
