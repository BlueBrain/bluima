package ch.epfl.bbp.uima.projects.misc;

import static ch.epfl.bbp.uima.testutils.UimaTests.getTestCas;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;

import org.apache.uima.jcas.JCas;
import org.junit.Test;

public class ExploreCellsAnnotatorTest {

    @Test
    public void testExtractor() throws Exception {
        JCas jCas = getTestCas("we have two beautiful neurons of snow here! oh boy, show me here what a nice nice cell");
        runPipeline(jCas, createEngine(ExploreCellsAnnotator.class));
    }
}
