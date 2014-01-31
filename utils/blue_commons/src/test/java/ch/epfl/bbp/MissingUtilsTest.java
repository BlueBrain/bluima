package ch.epfl.bbp;

import static org.junit.Assert.*;

import org.junit.Test;

public class MissingUtilsTest {

    @Test
    public void testFormat() {

        assertEquals("a1b2c", MissingUtils.format("a{}b{}c", 1, 2));
        assertEquals("1 b 2 c", MissingUtils.format("{} b {} c", 1, 2));
        
        assertEquals("", MissingUtils.format("", 1, 2));
        assertEquals("a1b2c", MissingUtils.format("a{}b{}c", 1, 2,3,4));
    }

}
