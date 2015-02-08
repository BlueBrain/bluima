package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.testutils.UimaTests.getTestCas;
import static com.google.common.collect.Lists.newArrayList;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;
import static org.apache.uima.fit.util.JCasUtil.select;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.jcas.JCas;
import org.junit.Test;

import ch.epfl.bbp.uima.typesystem.Prin;
import de.julielab.jules.types.Token;

public class WhitespaceTokenizerAnnotatorTest {

    @Test
    public void test() throws Exception {

        AnalysisEngine tokenizerAE = createEngine(WhitespaceTokenizerAnnotator.class);

        JCas jCas = getTestCas("bla bla calbindin-D28k bli bla");
        runPipeline(jCas, tokenizerAE);

        List<Token> tokens = newArrayList(select(jCas, Token.class));
        Prin.t("tokens", tokens);
        assertEquals(5, tokens.size());
        assertEquals("bla", tokens.get(0).getCoveredText());
        assertEquals("bla", tokens.get(1).getCoveredText());
        assertEquals("calbindin-D28k", tokens.get(2).getCoveredText());
        assertEquals("bli", tokens.get(3).getCoveredText());
        assertEquals("bla", tokens.get(4).getCoveredText());
    }
}
