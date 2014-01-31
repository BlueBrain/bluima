package ch.epfl.bbp.range;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

public class RangeBuilderTest {

    // @Test
    public void test() {
	Range<Date> upto = RangeBuilder.from(new Date("1.1.200")).upto(
		new Date("1.12.2000"));
	for (Date date : upto) {
	    System.out.println(date);
	}
    }

    @Test
    public void testInt() throws Exception {

	int v = 0;
	for (int i : RangeBuilder.from(1).upto(10)) {
	    v++;
	}
	assertEquals(10, v);
    }
}
