package ch.epfl.bbp.uima;

import static ch.epfl.bbp.uima.AbbreviationExpander.expand;
import static ch.epfl.bbp.uima.AbbreviationExpander.getAbbrevs;
import static org.apache.commons.lang3.StringUtils.countMatches;
import static org.junit.Assert.assertEquals;

import java.util.Set;

import org.junit.Test;

import ch.epfl.bbp.uima.AbbreviationExpander.Abbrev;

public class AbbreviationExpanderTest {

    @Test
    public void test() throws Exception {

        String txt = "The subthalamic nucleus (STh) is a brain region. The medial STh is here.";

        String txtOut = expand(txt);
        assertEquals(0, countMatches(txtOut, "STh"));

        Set<Abbrev> abbrevs = getAbbrevs(txt);
        assertEquals(1, abbrevs.size());

        txtOut = expand(txt, abbrevs);
        assertEquals(
                "The subthalamic nucleus is a brain region. The medial subthalamic nucleus is here.",
                txtOut);

        txt = "The subthalamic nucleus (STh) is a brain region. The medial STh";
        assertEquals(
                "should expand, even at end of txt",
                "The subthalamic nucleus is a brain region. The medial subthalamic nucleus",
                expand(txt));
    }

    @Test
    public void testShouldNotExpand() throws Exception {

        String txt = "The subthalamic nucleus (STh) is a brain region. The medialSTh is here.";
        assertEquals(
                "should not expand, since Sth is glued to medial",
                "The subthalamic nucleus is a brain region. The medialSTh is here.",
                expand(txt));
        txt = "The subthalamic nucleus (STh) is a brain region. The medial SThis here.";
        assertEquals(
                "should not expand, since Sth is glued to is",
                "The subthalamic nucleus is a brain region. The medial SThis here.",
                expand(txt));
    }

    @Test
    public void testTwoDefs() throws Exception {

        String txt = "The subthalamic nucleus (STh) is a brain region. The STh (subthalamic nucleus) is here.";
        assertEquals(
                "should not expand, since Sth is glued to medial",
                "The subthalamic nucleus is a brain region. The subthalamic nucleus is here.",
                expand(txt));
    }

    @Test
    public void testLongerText() throws Exception {

        String txt = AbbreviationsAnnotatorTest.SAMPLE_TXT2;
        assertEquals(19, countMatches(txt, "STh"));

        String txtOut = expand(txt);
        assertEquals("should not contain STh: " + txtOut, 0,
                countMatches(txtOut, "STh"));

        Set<Abbrev> abbrevs = getAbbrevs(txt);
        assertEquals(4, abbrevs.size());

        txtOut = expand(txt, abbrevs);
        assertEquals(0, countMatches(txtOut, "STh"));
    }

}
