package ch.epfl.bbp.uima.units.normalizers;

import static ch.epfl.bbp.uima.units.normalizers.WrittenNumbersNormalizer.text2num;
import static org.junit.Assert.*;

import org.junit.Test;

public class WrittenNumbersNormalizerTest {

    @Test
    public void test() {
        assertEquals(new Long(1), text2num("one"));
        assertEquals(new Long(12), text2num("twelve"));
        assertEquals(new Long(72), text2num("seventy two"));
        assertEquals(new Long(72), text2num("seventy-two"));

        assertEquals(new Long(300), text2num("three hundred"));
        assertEquals(new Long(302), text2num("three hundred and two"));

        assertEquals(new Long(1200), text2num("twelve hundred"));
        // FIXME not passing on cli
        // assertEquals (new Long(1200) , text2num("thousand two hundred"));
        assertEquals(new Long(1200), text2num("one thousand two hundred"));

        assertEquals(new Long(12304),
                text2num("twelve thousand three hundred four"));

        assertEquals(new Long(6000000), text2num("six million"));
        assertEquals(new Long(6400005),
                text2num("six million four hundred thousand five"));
        assertEquals(new Long(6400025),
                text2num("six million four hundred thousand and twenty five"));
    }
}
