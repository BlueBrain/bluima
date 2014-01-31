package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.BlueUima.PARAM_ANNOTATION_CLASS;
import static ch.epfl.bbp.uima.testutils.UimaTests.getTestCas;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;
import static org.apache.uima.fit.util.JCasUtil.exists;
import static org.apache.uima.fit.util.JCasUtil.select;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.junit.Test;

import ch.epfl.bbp.uima.types.Protein;

public class RemoveAnnotationsAnnotatorTest {

    @Test
    public void test() throws Exception {

        AnalysisEngineDescription removeAnnots = createEngineDescription(
                RemoveAnnotationsAnnotator.class, //
                PARAM_ANNOTATION_CLASS, Protein.class.getName());

        JCas jCas = getTestCas();
        assertEquals("has a DocumentAnnotation at first", 1,
                select(jCas, Annotation.class).size());

        Protein p = new Protein(jCas, 5, 10);
        p.addToIndexes();
        assertTrue(exists(jCas, Protein.class));

        runPipeline(jCas, removeAnnots);
        assertTrue(!exists(jCas, Protein.class));
    }
}
