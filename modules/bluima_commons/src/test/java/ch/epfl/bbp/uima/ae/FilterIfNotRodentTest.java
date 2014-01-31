package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.BlueUima.PARAM_ANNOTATION_CLASS;
import static ch.epfl.bbp.uima.testutils.UimaTests.createAnnot;
import static ch.epfl.bbp.uima.testutils.UimaTests.getTestCas;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;
import static org.apache.uima.fit.util.JCasUtil.exists;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.junit.Test;

import ch.epfl.bbp.uima.types.BrainRegion;
import de.julielab.jules.types.Protein;

public class FilterIfNotRodentTest {

    @Test
    public void testRemoved() throws Exception {

        assertTrue("the BR annotation remains, since it's a rat",
                theTest("rat", BrainRegion.class));
        assertFalse("filtering, since it's a cat",
                theTest("cat", BrainRegion.class));
        assertTrue("no filtering, since we remove Protein",
                theTest("rat", Protein.class));

    }

    /** @return whether the BrainRegion still exists */
    private static boolean theTest(String species,
            Class<? extends Annotation> aClass) throws Exception {
        JCas jCas = getTestCas("bla bla " + species + " bla bla");
        createAnnot(jCas, BrainRegion.class, 12, 13);

        runPipeline(
                jCas,//
                createEngine(LinnaeusAnnotator.class),
                createEngine(FilterIfNotRodent.class, //
                        PARAM_ANNOTATION_CLASS, aClass.getName()));

        return exists(jCas, BrainRegion.class);

    }
}
