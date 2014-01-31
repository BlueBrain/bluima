package ch.epfl.bbp.uima.ae.relations;

import static ch.epfl.bbp.uima.BlueUima.PARAM_ENCLOSING_SCOPE;
import static ch.epfl.bbp.uima.BlueUima.PARAM_FIRST_ANNOT;
import static ch.epfl.bbp.uima.BlueUima.PARAM_SECOND_ANNOT;
import static ch.epfl.bbp.uima.testutils.UimaTests.createAnnot;
import static ch.epfl.bbp.uima.testutils.UimaTests.getTokenizedTestCas;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;
import static org.apache.uima.fit.util.JCasUtil.select;
import static org.junit.Assert.assertEquals;

import org.apache.uima.jcas.JCas;
import org.junit.Test;

import ch.epfl.bbp.uima.types.BrainRegion;
import ch.epfl.bbp.uima.types.Cooccurrence;
import de.julielab.jules.types.Sentence;

public class FilterCoocurrencesIfTooManyEntitiesTest {

    @Test
    public void test() throws Exception {

        // just checking simple permutations of ExtractSameCoocurrences
        assertEquals("simple permutations", 1, doTest(2, Integer.MAX_VALUE));
        assertEquals("simple permutations", 3, doTest(3, Integer.MAX_VALUE));
        assertEquals("simple permutations", 6, doTest(4, Integer.MAX_VALUE));

        assertEquals("should delete all coocs", 0, doTest(3, 0));
        assertEquals("should delete all coocs", 0, doTest(3, 2));
        assertEquals("should leave all coocs", 3, doTest(3, 3));
        assertEquals("should leave all coocs", 3, doTest(3, 4));
    }

    /**
     * @param nrBrainRegions
     *            to simulate in this sentence
     * @param maximumNrBrainregions
     *            to pass to {@link FilterCoocurrencesIfTooManyEntities}
     *            annotator
     * @return the number of {@link Cooccurrence}s
     */
    public static int doTest(int nrBrainRegions, int maximumNrBrainregions)
            throws Exception {

        JCas jCas = getTokenizedTestCas("a b c d e f g h i j");

        for (int i = 0; i < nrBrainRegions; i++) {
            createAnnot(jCas, BrainRegion.class, i * 2, i * 2 + 1,
                    "abcdefghijklmnopqrstuvwxyz".charAt(i) + "");
        }
        assertEquals(nrBrainRegions, select(jCas, BrainRegion.class).size());

        runPipeline(jCas,
                createEngine(ExtractSameCoocurrences.class,//
                        PARAM_ENCLOSING_SCOPE, Sentence.class.getName(),//
                        PARAM_FIRST_ANNOT, BrainRegion.class.getName(),//
                        PARAM_SECOND_ANNOT, BrainRegion.class.getName()//
                ),
                createEngine(
                        FilterCoocurrencesIfTooManyEntities.class,
                        FilterCoocurrencesIfTooManyEntities.PARAM_MAXIMUM_ENTITIES,
                        maximumNrBrainregions));

        return select(jCas, Cooccurrence.class).size();
    }
}
