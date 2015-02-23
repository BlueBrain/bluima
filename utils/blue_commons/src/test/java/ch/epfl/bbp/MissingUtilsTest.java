package ch.epfl.bbp;

import static org.junit.Assert.*;

import org.junit.Test;

public class MissingUtilsTest {

    @Test
    public void testFormat() {

        assertEquals("a1b2c", MissingUtils.format("a{}b{}c", 1, 2));
        assertEquals("1 b 2 c", MissingUtils.format("{} b {} c", 1, 2));

        assertEquals("ok not to have brackets", "",
                MissingUtils.format("", 1, 2));
        assertEquals("ok to have too many arguments", "a1b2c",
                MissingUtils.format("a{}b{}c", 1, 2, 3, 4));
        assertEquals("ok to have too few arguments", "a1b{{MISSING ARG}}c",
                MissingUtils.format("a{}b{}c", 1));
    }

}
