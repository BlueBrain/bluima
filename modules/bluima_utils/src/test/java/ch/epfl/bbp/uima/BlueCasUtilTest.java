package ch.epfl.bbp.uima;

import static ch.epfl.bbp.uima.BlueCasUtil.isContained;
import static ch.epfl.bbp.uima.testutils.UimaTests.getTestCas;
import static org.junit.Assert.*;

import org.apache.uima.jcas.JCas;
import org.junit.Test;

import ch.epfl.bbp.uima.types.Protein;

/**
 * @author renaud.richardet@epfl.ch
 */
public class BlueCasUtilTest {

    @Test
    public void testIsContained() throws Exception {
        JCas jCas = getTestCas();

        Protein p1 = new Protein(jCas, 7, 10);
        p1.addToIndexes();
        Protein p2 = new Protein(jCas, 6, 10);
        p2.addToIndexes();

        assertTrue(isContained(p1, p2));
        assertFalse(isContained(p2, p1));

        Protein p3 = new Protein(jCas, 7, 10);
        p3.addToIndexes();
        assertTrue("true if has same begin-end", isContained(p1, p3));
    }
}
