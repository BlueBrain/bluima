package ch.epfl.bbp.uima.projects.brainregions.bams;

import static org.apache.commons.lang.StringUtils.join;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;

public class FindSynonyms3Test {

    @Test
    public void test() throws Exception {

        FindSynonyms3 fs3 = new FindSynonyms3();

        Set<String> s = fs3.getSynonyms("dentate gyrus");
        System.out.println(join(s, "; "));
        assertEquals(5, s.size());
        // assertEquals("dentate gyrus", s.get(0));

        s = fs3.getSynonyms("sublingual nucleus");
        System.out.println(join(s, "; "));
        assertTrue(s.contains("nucleus of roller"));
        
        s = fs3.getSynonyms("nucleus of Roller");
        System.out.println(join(s, "; "));
        assertTrue(s.contains("sublingual nucleus"));
    }
}
