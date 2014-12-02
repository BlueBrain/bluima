package ch.epfl.bbp.uima.filter;

import static ch.epfl.bbp.uima.filter.KeepsCleaner.isBalanced;
import static ch.epfl.bbp.uima.filter.KeepsCleaner.isOnlyPunctAndNumbers;
import static ch.epfl.bbp.uima.testutils.UimaTests.createAnnot;
import static ch.epfl.bbp.uima.testutils.UimaTests.getTestCas;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;
import static org.apache.uima.fit.util.JCasUtil.selectSingle;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.uima.jcas.JCas;
import org.junit.Test;

import ch.epfl.bbp.uima.types.Keep;

public class KeepsCleanerTest {

    @Test
    public void testRemovePunctAtEndOrBegin() throws Exception {
        assertEquals("no changes", "dystrophy", cleanup("dystrophy"));
        assertEquals("no changes", "d!yst'rophy", cleanup("d!yst'rophy"));

        assertEquals("dystrophy", cleanup("dystrophy."));
        assertEquals("dystrophy", cleanup("dystrophy\\"));

        assertEquals("dystrophin", cleanup("[dystrophin]"));

        assertEquals("not", cleanup("not?\""));
        assertEquals("what", cleanup("'what"));
        assertEquals("seizures", cleanup("seizures'"));
        assertEquals("impermeant", cleanup("-impermeant"));
    }

    private String cleanup(String s) throws Exception {

        JCas jCas = getTestCas(s);
        createAnnot(jCas, Keep.class, 0, s.length()).setNormalizedText(s);

        runPipeline(jCas, createEngine(KeepsCleaner.class));
        return selectSingle(jCas, Keep.class).getNormalizedText();
    }

    @Test
    public void testBalancedParenth() {
        assertTrue(isBalanced("a{b}c(dd[e]v)g"));
        assertFalse(isBalanced("([}])"));
        assertTrue(isBalanced("([])"));
        assertTrue(isBalanced("()[]{}[][]"));
    }

    @Test
    public void testOnlyPunctAndNumbers() {
        assertFalse(isOnlyPunctAndNumbers("a{b}c(dd[e]v)g"));
        assertTrue(isOnlyPunctAndNumbers("10(-10"));
        assertTrue(isOnlyPunctAndNumbers("1:200,000"));

    }
}
