package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.BlueUima.PARAM_INPUT;
import static ch.epfl.bbp.uima.testutils.UimaTests.getTokenizedTestCas;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.JULIE_TSD;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.TOKEN;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReader;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.junit.Test;

import ch.epfl.bbp.uima.cr.TextArrayReader;

public class TestAnnotatorTest {

    @Test
    public void test() throws Exception {
        runPipeline(getTokenizedTestCas("a b c"),
                createEngine(TestAnnotator.class, "expects", TOKEN + " 3"));
    }

    @Test(expected = AnalysisEngineProcessException.class)
    public void test2() throws Exception {
        runPipeline(getTokenizedTestCas("a b c"),
                createEngine(TestAnnotator.class, "expects", TOKEN + " 111"));
    }

    @Test
    public void testTwoDocs() throws Exception {

        runPipeline(
                createReader(TextArrayReader.class, JULIE_TSD, PARAM_INPUT,
                        new String[] { "a b c", "d e f g" }),
                createEngine(DotSentenceSplitterAnnotator.class),
                createEngine(WhitespaceTokenizerAnnotator.class),
                createEngine(TestAnnotator.class, "expects", TOKEN + " 3",
                        "expects2", TOKEN + " 4"));
    }

    @Test
    public void testTwoDocsButNotEnoughExpects() throws Exception {

        runPipeline(
                createReader(TextArrayReader.class, JULIE_TSD, PARAM_INPUT,
                        new String[] { "a b c", "d e f g" }),
                createEngine(DotSentenceSplitterAnnotator.class),
                createEngine(WhitespaceTokenizerAnnotator.class),
                createEngine(TestAnnotator.class, "expects", TOKEN + " 3"));
    }
}
