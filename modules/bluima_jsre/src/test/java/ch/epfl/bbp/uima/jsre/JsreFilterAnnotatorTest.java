package ch.epfl.bbp.uima.jsre;

import static ch.epfl.bbp.uima.BlueUima.PARAM_FIRST_ANNOT;
import static ch.epfl.bbp.uima.BlueUima.PARAM_SECOND_ANNOT;
import static ch.epfl.bbp.uima.JsreHelper.TEST_PIPELINES_BASE;
import static ch.epfl.bbp.uima.laucher.PipelineScriptParser.parse;
import static ch.epfl.bbp.uima.testutils.UimaTests.createAnnot;
import static ch.epfl.bbp.uima.testutils.UimaTests.getTokenizedTestCas;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;
import static org.apache.uima.fit.util.JCasUtil.select;
import static org.junit.Assert.assertEquals;

import java.io.File;

import org.apache.uima.jcas.JCas;
import org.junit.Test;

import ch.epfl.bbp.uima.ae.relations.ExtractSameCoocurrences;
import ch.epfl.bbp.uima.types.BrainRegion;
import ch.epfl.bbp.uima.types.Cooccurrence;

/**
 * Tests SVM model with 2 sentences (one "positive", one "negative") from
 * WhiteText corpus.
 * 
 * @author renaud.richardet@epfl.ch
 */
public class JsreFilterAnnotatorTest {

    @Test
    public void testWithCooc() throws Exception {
        // pmid 2341628
        JCas jCas = getTokenizedTestCas("The results of these tracing experiments confirm the general notion of reciprocal connections between the subthalamic nucleus(STh) and pallidal areas.");

        createAnnot(jCas, BrainRegion.class, 106, 125, "subthalamic nucleus");
        createAnnot(jCas, BrainRegion.class, 135, 149, "pallidal areas");

        runPipeline(jCas, createEngine(ExtractSameCoocurrences.class, //
                PARAM_FIRST_ANNOT, BrainRegion.class.getName(),//
                PARAM_SECOND_ANNOT, BrainRegion.class.getName()));
        assertEquals("one cooc", 1, select(jCas, Cooccurrence.class).size());

        runPipeline(jCas, createEngine(JsreFilterAnnotator.class));
        assertEquals("still one cooc, not filtered", 1,
                select(jCas, Cooccurrence.class).size());
    }

    @Test
    public void testWithOut() throws Exception {
        // pmid 1692855
        JCas jCas = getTokenizedTestCas("For this study, we examined the optic (stratum opticum, SO), intermediate gray (stratum griseum intermedium, SGI), intermediate white (stratum album intermedium, SAI), and deep gray (stratum griseum profundum, SGP) layers.");

        createAnnot(jCas, BrainRegion.class, 39, 54, "stratum opticum");
        createAnnot(jCas, BrainRegion.class, 80, 107,
                "stratum griseum intermedium");
        createAnnot(jCas, BrainRegion.class, 135, 160,
                "stratum album intermedium");
        createAnnot(jCas, BrainRegion.class, 183, 208,
                "stratum griseum profundum");

        runPipeline(jCas, createEngine(ExtractSameCoocurrences.class, //
                PARAM_FIRST_ANNOT, BrainRegion.class.getName(),//
                PARAM_SECOND_ANNOT, BrainRegion.class.getName()));
        assertEquals("C24 = 6coocs", 6, select(jCas, Cooccurrence.class).size());

        runPipeline(jCas, createEngine(JsreFilterAnnotator.class));
        assertEquals("all filtered", 0, select(jCas, Cooccurrence.class).size());
    }

    /**
     * Validation and evaluation on whole WT corpus. Achtung: not a
     * cross-validated experiment
     */
    public static void main(String[] args) throws Exception {
        parse(
                new File(TEST_PIPELINES_BASE
                        + "JsreFilterAnnotatorTest.pipeline")).run();
    }
}
