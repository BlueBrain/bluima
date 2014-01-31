package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.BlueCasUtil.asList;
import static ch.epfl.bbp.uima.BlueUima.PARAM_ANNOTATION_CLASS;
import static ch.epfl.bbp.uima.testutils.UimaTests.*;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;
import static org.apache.uima.fit.util.JCasUtil.select;
import static org.junit.Assert.assertEquals;

import org.apache.uima.jcas.JCas;
import org.junit.Test;

import ch.epfl.bbp.uima.types.BrainRegion;

public class KeepLargestAnnotationAnnotatorTest {

    @Test
    public void test() throws Exception {

        JCas jCas = getTestCas("bla cortex bla brain bla. bli bli brain bli bli");
        new BrainRegion(jCas, 4, 10).addToIndexes();
        new BrainRegion(jCas, 15, 20).addToIndexes();
        new BrainRegion(jCas, 34, 39).addToIndexes();
        runPipeline(
                jCas,
                createEngine(KeepLargestAnnotationAnnotator.class,
                        PARAM_ANNOTATION_CLASS, BrainRegion.class.getName()));
        assertEquals("no overlap, keeps all annots", 3,
                asList(select(jCas, BrainRegion.class)).size());

        // first annotation is covering other 2.
        jCas = getTestCas("bla cortex bla brain bla. bli bli brain bli bli");
        new BrainRegion(jCas, 4, 10).addToIndexes();
        new BrainRegion(jCas, 5, 10).addToIndexes();
        new BrainRegion(jCas, 6, 9).addToIndexes();
        runPipeline(
                jCas,
                createEngine(KeepLargestAnnotationAnnotator.class,
                        PARAM_ANNOTATION_CLASS, BrainRegion.class.getName()));
        assertEquals("first annotation is covering other 2", 1,
                asList(select(jCas, BrainRegion.class)).size());

        // overlap
        jCas = getTestCas("bla cortex bla brain bla. bli bli brain bli bli");
        new BrainRegion(jCas, 4, 10).addToIndexes();
        new BrainRegion(jCas, 5, 11).addToIndexes();
        runPipeline(
                jCas,
                createEngine(KeepLargestAnnotationAnnotator.class,
                        PARAM_ANNOTATION_CLASS, BrainRegion.class.getName()));
        assertEquals("overlap, keeps all annots", 2,
                asList(select(jCas, BrainRegion.class)).size());
    }
}
