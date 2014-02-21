package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.BlueCasUtil.haveSameBeginEnd;
import static ch.epfl.bbp.uima.BlueUima.PARAM_ANNOTATION_CLASS;
import static ch.epfl.bbp.uima.ae.CopyAnnotationsAnnotator.DELETE_FROM;
import static ch.epfl.bbp.uima.ae.CopyAnnotationsAnnotator.FROM_VIEW;
import static ch.epfl.bbp.uima.ae.CopyAnnotationsAnnotator2.TO_VIEW;
import static ch.epfl.bbp.uima.testutils.UimaTests.getTestCas;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;
import static org.apache.uima.fit.util.JCasUtil.exists;
import static org.apache.uima.fit.util.JCasUtil.select;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.junit.Test;

import ch.epfl.bbp.uima.BlueUima;
import ch.epfl.bbp.uima.types.Protein;

public class CopyAnnotationsAnnotator2Test {

    @Test
    public void test() throws Exception {

        AnalysisEngineDescription copyAnnots = createEngineDescription(
                CopyAnnotationsAnnotator2.class, //
                TO_VIEW, "blah",//
                PARAM_ANNOTATION_CLASS, Protein.class.getName(),//
                DELETE_FROM, true);

        JCas jCas = getTestCas();
        assertEquals("has a DocumentAnnotation at first", 1,
                select(jCas, Annotation.class).size());

        Protein p = new Protein(jCas, 5, 10);
        p.addToIndexes();
        runPipeline(jCas, copyAnnots);
        assertTrue("no more Protein in initial view",
                !exists(jCas, Protein.class));

        JCas newView = jCas.getView("blah");
        Collection<Protein> pNew = select(newView, Protein.class);
        assertEquals("copied to new view", 1, pNew.size());
        assertTrue(haveSameBeginEnd(p, pNew.iterator().next()));

        // copy it back
        AnalysisEngineDescription copyAnnotsBack = createEngineDescription(
                CopyAnnotationsAnnotator2.class, //
                FROM_VIEW, "blah",//
                TO_VIEW, BlueUima.VIEW_SYSTEM,//
                PARAM_ANNOTATION_CLASS, Protein.class.getName(),//
                DELETE_FROM, true);
        runPipeline(jCas, copyAnnotsBack);

        assertTrue(!exists(newView, Protein.class));
        Collection<Protein> pBack = select(jCas, Protein.class);
        assertEquals("copied to protein", 1, pBack.size());
        assertTrue(haveSameBeginEnd(p, pBack.iterator().next()));
    }
}
