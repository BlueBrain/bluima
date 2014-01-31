package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.testutils.UimaTests.getTestCas;
import static org.junit.Assert.assertEquals;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.util.Collection;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.jcas.JCas;
import org.junit.Test;

import ch.epfl.bbp.uima.typesystem.Prin;
import de.julielab.jules.types.Token;

public class WhitespaceTokenizerAnnotatorTest {

    @Test
    public void test() throws Exception {

        AnalysisEngine sentenceAE = createEngine(NaiveSentenceSplitterAnnotator.class);
        AnalysisEngine tokenizerAE = createEngine(WhitespaceTokenizerAnnotator.class);

        JCas jcas = getTestCas("bla bla calbindin-D28k bla bla");
        runPipeline(jcas, sentenceAE, tokenizerAE);

        Collection<Token> tokens = select(jcas, Token.class);
        Prin.t("tokens", tokens);
        assertEquals(5, tokens.size());
    }
}
