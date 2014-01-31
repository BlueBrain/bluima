package ch.epfl.bbp.nlp.ie.proteinconc.ae;

import static ch.epfl.bbp.nlp.ie.proteinconc.ae.AnnotOverlapFilter.PARAM_FILTERED_ANNOTATIONS;
import static ch.epfl.bbp.nlp.ie.proteinconc.ae.AnnotOverlapFilter.PARAM_PROTECTED_ANNOTATION;
import static ch.epfl.bbp.uima.testutils.UimaTests.createAnnot;
import static ch.epfl.bbp.uima.testutils.UimaTests.getTokenizedTestCas;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;
import static org.apache.uima.fit.util.JCasUtil.select;
import static org.junit.Assert.assertEquals;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.jcas.JCas;
import org.junit.Test;

import ch.epfl.bbp.uima.types.Measure;
import ch.epfl.bbp.uima.types.Protein;

public class AnnotOverlapFilterTest {

    @Test
    public void testOverlap() throws Exception {
        overlap(1, 4, 2, 3);
        overlap(2, 3, 1, 4);
        overlap(0, 3, 2, 4);
    }

    private static void overlap(int s1, int e1, int s2, int e2)
            throws Exception {
        JCas cas = getTokenizedTestCas("a b c");
        createAnnot(cas, Measure.class, s1, e1);
        createAnnot(cas, Protein.class, s2, e2);

        String[] filteredAnnotations = { Measure.class.getName() };
        AnalysisEngine overlapFilterEngine = createEngine(
                AnnotOverlapFilter.class, PARAM_PROTECTED_ANNOTATION,
                Protein.class.getName(), PARAM_FILTERED_ANNOTATIONS,
                filteredAnnotations);
        runPipeline(cas, overlapFilterEngine);

        assertEquals("the protein must be deleted", 0,
                select(cas, Protein.class).size());
    }
}
