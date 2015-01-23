package ch.epfl.bbp;

import static ch.epfl.bbp.DateUtils.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class DateUtilsTest {

    @Test
    public void test() {
        assertEquals("0 second", toHuman(123));

        assertEquals("5 seconds", toHuman((5 * ONE_SECOND) + 123));

        assertEquals("1 day, 1 hour", toHuman(ONE_DAY + ONE_HOUR));

        assertEquals("1 day and 2 seconds", toHuman(ONE_DAY + 2 * ONE_SECOND));

        assertEquals("1 day, 1 hour, 2 minutes", toHuman(ONE_DAY + ONE_HOUR
                + (2 * ONE_MINUTE)));

        assertEquals("4 days, 3 hours, 2 minutes and 1 second",
                toHuman((4 * ONE_DAY) + (3 * ONE_HOUR) + (2 * ONE_MINUTE)
                        + ONE_SECOND));

        assertEquals("5 days, 4 hours, 1 minute and 23 seconds",
                toHuman((5 * ONE_DAY) + (4 * ONE_HOUR) + ONE_MINUTE
                        + (23 * ONE_SECOND) + 123));

        assertEquals("42 days", toHuman(42 * ONE_DAY));
    }

}
