package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.testutils.UimaTests.getTokenizedTestCas;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.slf4j.LoggerFactory.getLogger;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.jcas.JCas;
import org.junit.Test;
import org.slf4j.Logger;

import de.julielab.jules.types.Token;

public class BlueBioLemmatizerTest {
    private static Logger LOG = getLogger(BlueBioLemmatizerTest.class);

    @Test
    public void test() throws Exception {

        String[] expected = StringUtils.split(
                "long form pressure hydrocephalus (NPH) be classify", " ");

        JCas jCas = getTokenizedTestCas(//
        "longs forms pressure hydrocephalus (NPH) was classifying");

        AnalysisEngine ae = createEngine(BlueBioLemmatizer.class);
        ae.process(jCas);

        Collection<Token> a = select(jCas, Token.class);
        assertEquals(7, a.size());

        int i = 0;
        for (Token t : a) {
            // Lemma lemma = t.getLemma();
            assertNotNull(t.getLemmaStr());
            LOG.debug("{} --> {}", t.getCoveredText(), t.getLemmaStr());
            assertEquals(expected[i++], t.getLemmaStr());
        }
    }

    @Test
    public void testText() throws Exception {
        assertEquals("thalamic", BlueBioLemmatizer.lemmatize("Thalamic", null));
        assertEquals("thalamus", BlueBioLemmatizer.lemmatize("thalamus", null));
    }
}
