package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.BlueCasUtil.asList;
import static ch.epfl.bbp.uima.BlueUima.PARAM_ANNOTATION_CLASSES;
import static ch.epfl.bbp.uima.LexicaHelper.getConceptMapper;
import static ch.epfl.bbp.uima.ae.OpenNlpHelper.getPosTagger;
import static ch.epfl.bbp.uima.testutils.UimaTests.getTokenizedTestCas;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;
import static org.apache.uima.fit.util.JCasUtil.exists;
import static org.apache.uima.fit.util.JCasUtil.select;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.uima.jcas.JCas;
import org.junit.Test;

import ch.epfl.bbp.uima.filter.AnnotationFilterAnnotator;
import ch.epfl.bbp.uima.filter.LeaveOnlyKeepsEnclosedAnnotationsAnnotator;
import ch.epfl.bbp.uima.types.DictTerm;
import ch.epfl.bbp.uima.types.Keep;
import ch.epfl.bbp.uima.types.POSAdverb;
import ch.epfl.bbp.uima.types.POSSkip;
import ch.epfl.bbp.uima.types.POSVerb;
import ch.epfl.bbp.uima.types.POSWh;
import ch.epfl.bbp.uima.typesystem.Prin;
import de.julielab.jules.types.Sentence;

public class FilteringTest {

    @Test
    public void testPosSkip() throws Exception {

        JCas jCas = getTokenizedTestCas("it is true.");
        runPipeline(
                jCas,
                getPosTagger(),
                createEngineDescription(SkipSomePosAnnotator.class),
                createEngineDescription(ViterbiFilterAnnotator.class),
                createEngineDescription(AnnotationFilterAnnotator.class,
                        PARAM_ANNOTATION_CLASSES,
                        new String[] { POSSkip.class.getName() }),
                createEngineDescription(LeaveOnlyKeepsEnclosedAnnotationsAnnotator.class));
        assertEquals("Sentence is still there", 1,
                asList(select(jCas, Sentence.class)).size());
        assertFalse("no more posSkip", exists(jCas, POSSkip.class));

        // without
        jCas = getTokenizedTestCas("it is true.");
        runPipeline(
                jCas,
                getPosTagger(),
                createEngineDescription(SkipSomePosAnnotator.class),
                createEngineDescription(ViterbiFilterAnnotator.class),
                createEngineDescription(LeaveOnlyKeepsEnclosedAnnotationsAnnotator.class));
        assertEquals("Sentence is still there", 1,
                asList(select(jCas, Sentence.class)).size());
        assertEquals("posSkip is still there", 1,
                asList(select(jCas, POSSkip.class)).size());
    }

    @Test
    public void testAdverb() throws Exception {

        JCas jCas = getTokenizedTestCas("it is not true what you say.");
        runPipeline(jCas, getPosTagger(),
                getConceptMapper("/onto_baseline/protein"),
                createEngineDescription(SkipSomePosAnnotator.class),
                createEngineDescription(ViterbiFilterAnnotator.class)

        );

        List<Keep> out = asList(select(jCas, Keep.class));
        assertTrue(out.get(0).getEnclosedAnnot() instanceof POSSkip);
        assertTrue(out.get(1).getEnclosedAnnot() instanceof POSVerb);
        assertTrue(out.get(2).getEnclosedAnnot() instanceof POSAdverb);
        assertTrue(out.get(4).getEnclosedAnnot() instanceof POSWh);

        for (Keep k : select(jCas, Keep.class)) {
            Prin.t(k);
            assertFalse(k.getEnclosedAnnot() instanceof DictTerm);
        }
    }

}
