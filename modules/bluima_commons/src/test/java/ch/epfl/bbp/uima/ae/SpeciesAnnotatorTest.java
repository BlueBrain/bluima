package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.testutils.UimaTests.TEST_DEPENDENCY;
import static ch.epfl.bbp.uima.testutils.UimaTests.getTestCas;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;
import static org.apache.uima.fit.util.JCasUtil.selectSingle;
import static org.junit.Assert.assertEquals;

import org.apache.uima.jcas.JCas;
import org.junit.Test;

import ch.epfl.bbp.uima.types.DocumentSpecies;

public class SpeciesAnnotatorTest {

    @Test
    public void test() throws Exception {

        JCas jCas = getTestCas(TEST_DEPENDENCY);

        runPipeline(jCas, LinnaeusAnnotatorTest.createLinnaeusEngine(),
                createEngine(SpeciesAnnotator.class));

        DocumentSpecies ds = selectSingle(jCas, DocumentSpecies.class);
        assertEquals(SpeciesAnnotator.Species.Homo_sapiens.toString(), ds.getFamilyName());
    }
}
