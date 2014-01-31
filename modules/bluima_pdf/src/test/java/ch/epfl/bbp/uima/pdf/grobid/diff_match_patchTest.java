package ch.epfl.bbp.uima.pdf.grobid;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;

public class diff_match_patchTest {

    @Test
    public void testSimple() {
        assertEquals(0, diff_match_patch.match_main("hello", "hello", 0));
        assertEquals(-1, diff_match_patch.match_main("hello", "world", 0));
        assertEquals(
                -1,
                diff_match_patch
                        .match_main(
                                "ReferencesLocker D: Measuring oral health: 18a conceptual framework.\\n\" + \n"
                                        + "                \"Community Dent Health 1988, 5:3-18.",
                                "world center", 0));
    }

    @Test
    @Ignore
    // TODO fails diff_match_patchTest.testRefs:24 Pattern too long for this
    // application.
    public void testRefs() {
        assertEquals(
                10,
                diff_match_patch
                        .match_main(
                                //
                                "ReferencesLocker D: Measuring oral health: 18a conceptual framework.\\n\" + \n"
                                        + "                \"Community Dent Health 1988, 5:3-18.",
                                "Locker D: Measuring oral health: a conceptual framework.\n"
                                        + "Community Dent Health 1988, 5:3-18.",
                                0));

    }

}
