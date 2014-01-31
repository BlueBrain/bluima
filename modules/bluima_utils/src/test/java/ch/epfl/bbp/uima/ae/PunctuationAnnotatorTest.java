package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.testutils.UimaTests.getTestCas;
import static ch.epfl.bbp.uima.testutils.UimaTests.getTokenizedTestCas;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;
import static org.apache.uima.fit.util.JCasUtil.select;
import static org.apache.uima.fit.util.JCasUtil.selectSingle;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.uima.jcas.JCas;
import org.junit.Test;

import ch.epfl.bbp.uima.testutils.UimaTests;
import ch.epfl.bbp.uima.types.Punctuation;
import de.julielab.jules.types.Token;

/**
 * @author renaud.richardet@epfl.ch
 */
public class PunctuationAnnotatorTest {

    @Test
    public void test() throws Exception {

        for (String c : "!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~¡¢£¥©®°±·×à̧́̈–—“”⁎●"
                .split(".")) {

            JCas jCas = getTokenizedTestCas(c);

            runPipeline(jCas, createEngine(PunctuationAnnotator.class));

            assertEquals(1, select(jCas, Punctuation.class).size());
        }
    }

    @Test
    public void testMultiple() throws Exception {

        String ps = "!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~¡¢£¥©®°±·×à̧́̈–—“”⁎●";

        JCas jCas = getTestCas(ps);
        UimaTests.createAnnot(jCas, Token.class, 0, ps.length());

        runPipeline(jCas, createEngine(PunctuationAnnotator.class));

        assertNotNull(selectSingle(jCas, Punctuation.class));
    }
}
