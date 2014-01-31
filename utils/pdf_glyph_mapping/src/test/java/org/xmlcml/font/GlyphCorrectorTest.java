package org.xmlcml.font;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Ignore;
import org.junit.Test;
import org.xmlcml.pdf2svg.GlyphCorrector;

public class GlyphCorrectorTest {

    static GlyphCorrector gc = new GlyphCorrector();

    @Test
    public void testArial() {
        assertNotNull(gc.get("Arial"));
    }

    // font wrong code wrong name right code right name pmid
    // AdvP4C4E74 U+00BC VULGAR FRACTION ONE QUARTER U+003D EQUALS SIGN 16988649
    @Test
    public void testOneQuarter() {
        int oneQuarter = 0x00BC; // http://codepoints.net/U+00BC
        int equalSign = 0x003D;
        assertEquals(equalSign,
                gc.codePointConversion("AdvP4C4E74", oneQuarter));
    }

    @Test
    public void testLambda() {
        int lambdaCharpoint = (int) 'λ';
        int lCharpoint = (int) 'l';
        assertEquals(lambdaCharpoint,
                gc.codePointConversion("AdvTT3f84ef53", lCharpoint));
    }

    @Test
    @Ignore
    // FIXME
    public void testSix() {
        int lambdaCharpoint = (int) '±';
        int lCharpoint = (int) '6';
        assertEquals(lambdaCharpoint,
                gc.codePointConversion("Universal-GreekwithMathPi", lCharpoint));
    }
}
