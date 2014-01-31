package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.BlueCasUtil.haveSameBeginEnd;
import static ch.epfl.bbp.uima.ae.CopyAnnotationsAnnotator.DELETE_FROM;
import static ch.epfl.bbp.uima.ae.CopyAnnotationsAnnotator.FROM_ANNOTATION;
import static ch.epfl.bbp.uima.ae.CopyAnnotationsAnnotator.TO_ANNOTATION;
import static ch.epfl.bbp.uima.testutils.UimaTests.getTestCas;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;
import static org.apache.uima.fit.util.JCasUtil.exists;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.util.Collection;

import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.junit.Test;

import ch.epfl.bbp.uima.types.Measure;
import ch.epfl.bbp.uima.types.Protein;

public class CopyAnnotationsAnnotatorTest {

    @Test
    public void test() throws Exception {

        AnalysisEngineDescription copyAnnots = createEngineDescription(
                CopyAnnotationsAnnotator.class, //
                FROM_ANNOTATION, Protein.class.getName(),//
                TO_ANNOTATION, Measure.class.getName());

        JCas jCas = getTestCas();
        assertEquals("has a DocumentAnnotation at first", 1,
                select(jCas, Annotation.class).size());

        Protein p = new Protein(jCas, 5, 10);
        p.addToIndexes();
        runPipeline(jCas, copyAnnots);

        assertTrue(!exists(jCas, Protein.class));
        Collection<Measure> m = select(jCas, Measure.class);
        assertEquals("copied to measure", 1, m.size());
        assertTrue(haveSameBeginEnd(p, m.iterator().next()));

        // copy it back
        AnalysisEngineDescription copyAnnotsBack = createEngineDescription(
                CopyAnnotationsAnnotator.class, //
                FROM_ANNOTATION, Measure.class.getName(), //
                TO_ANNOTATION, Protein.class.getName());
        runPipeline(jCas, copyAnnotsBack);

        assertTrue(!exists(jCas, Measure.class));
        Collection<Protein> pBack = select(jCas, Protein.class);
        assertEquals("copied to protein", 1, pBack.size());
        assertTrue(haveSameBeginEnd(p, pBack.iterator().next()));
    }

    @Test
    public void testDoNotDelete() throws Exception {

        AnalysisEngineDescription copyAnnots = createEngineDescription(
                CopyAnnotationsAnnotator.class, //
                FROM_ANNOTATION, Protein.class.getName(),//
                TO_ANNOTATION, Measure.class.getName(),//
                DELETE_FROM, false);

        JCas jCas = getTestCas();
        assertEquals("has a DocumentAnnotation at first", 1,
                select(jCas, Annotation.class).size());

        Protein p = new Protein(jCas, 5, 10);
        p.addToIndexes();
        runPipeline(jCas, copyAnnots);
        assertTrue(exists(jCas, Protein.class));
    }
}
