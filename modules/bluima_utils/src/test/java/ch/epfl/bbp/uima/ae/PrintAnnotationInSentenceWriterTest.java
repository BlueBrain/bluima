package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.BlueUima.PARAM_ANNOTATION_CLASS;
import static ch.epfl.bbp.uima.testutils.UimaTests.getTokenizedTestCas;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;

import org.apache.uima.jcas.JCas;
import org.junit.Test;

import ch.epfl.bbp.uima.ae.output.PrintAnnotationInSentenceWriter;
import ch.epfl.bbp.uima.types.BrainRegion;

public class PrintAnnotationInSentenceWriterTest {

    @Test
    public void test() throws Exception {

        JCas jcas = getTokenizedTestCas("bla cortex bla brain bla. bli bli brain bli bli");
        new BrainRegion(jcas, 4, 10).addToIndexes();
        new BrainRegion(jcas, 15, 20).addToIndexes();
        new BrainRegion(jcas, 34, 39).addToIndexes();

        runPipeline(
                jcas,
                createEngine(PrintAnnotationInSentenceWriter.class,
                        PARAM_ANNOTATION_CLASS, BrainRegion.class.getName()));
    }
}
