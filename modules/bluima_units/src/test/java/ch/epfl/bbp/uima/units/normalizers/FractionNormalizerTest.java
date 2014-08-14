package ch.epfl.bbp.uima.units.normalizers;

import static ch.epfl.bbp.uima.units.normalizers.FractionNormalizer.normalizeFraction;
import static org.junit.Assert.*;

import org.junit.Test;

public class FractionNormalizerTest {

    @Test
    public void test() {
        assertEquals("0.5", normalizeFraction("1/2", null));
        assertEquals(0.12, new Float(normalizeFraction("1.2/10", null)),
                0.00001);
    }

}
