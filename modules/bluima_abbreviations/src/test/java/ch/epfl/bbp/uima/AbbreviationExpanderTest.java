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
                "two definitions, should work",
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

    final static String pmId16196030 = "This paper describes the quantitative areal and laminar distribution of identified neuron populations projecting from areas of prefrontal cortex (PFC) to subcortical autonomic, motor, and limbic sites in the rat. Injections of the retrograde pathway tracer wheat germ agglutinin conjugated with horseradish peroxidase (WGA-HRP) were made into dorsal/ventral striatum (DS/VS), basolateral amygdala (BLA), mediodorsal thalamus (MD), lateral hypothalamus (LH), mediolateral septum, dorsolateral periaqueductal gray, dorsal raphe, ventral tegmental area, parabrachial nucleus, nucleus tractus solitarius, rostral/caudal ventrolateral medulla, or thoracic spinal cord (SC). High-resolution flat-map density distributions of retrogradely labelled neurons indicated that specific PFC regions were differentially involved in the projections studied, with medial (m)PFC divided into dorsal and ventral sectors. The percentages that WGA-HRP retrogradely labelled neurons composed of the projection neurons in individual layers of infralimbic (IL; area 25) prelimbic (PL; area 32), and dorsal anterior cingulate (ACd; area 24b) cortices were calculated. Among layer 5 pyramidal cells, approximately 27.4% in IL/PL/ACd cortices projected to LH, 22.9% in IL/ventral PL to VS, 18.3% in ACd/dorsal PL to DS, and 8.1% in areas IL/PL to BLA; and 37% of layer 6 pyramidal cells in IL/PL/ACd projected to MD. Data for other projection pathways are given. Multiple dual retrograde fluorescent tracing studies indicated that moderate populations (<9%) of layer 5 mPFC neurons projected to LH/VS, LH/SC, or VS/BLA. The data provide new quantitative information concerning the density and distribution of neurons involved in identified projection pathways from defined areas of the rat PFC to specific subcortical targets involved in dynamic goal-directed behavior.";

    @Test
    public void testPmId16196030() throws Exception {

        String txt = pmId16196030;
        assertEquals(4, countMatches(txt, "LH"));

        String txtOut = expand(txt);
        assertEquals("should not contain LH: " + txtOut, 0,
                countMatches(txtOut, "LH"));
    }

}
