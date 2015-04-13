package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.testutils.UimaTests.assertResultsContains;
import static ch.epfl.bbp.uima.testutils.UimaTests.getTestCas;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.util.JCasUtil.select;
import static org.junit.Assert.assertNotNull;

import java.io.InputStream;
import java.util.Collection;

import org.apache.commons.io.IOUtils;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.junit.BeforeClass;
import org.junit.Test;

import ch.epfl.bbp.uima.types.LinnaeusSpecies;

public class LinnaeusAnnotatorTest {

    static private String BLUIMA_RESOURCE_DIR;

    @BeforeClass
    public static void setup() {
        BLUIMA_RESOURCE_DIR = System.getProperty("BLUIMA_RESOURCE_DIR");
        assertNotNull("BLUIMA_RESOURCE_DIR system property is not set",
                BLUIMA_RESOURCE_DIR);
    }

    @Test
    public void test() throws Exception {
        String file = "/linnaeus/pmcA1239921.txt";
        InputStream stream = getClass().getResourceAsStream(file);
        String testtext = IOUtils.toString(stream);

        JCas jCas = getTestCas(testtext);

        createLinnaeusEngine().process(jCas);

        Collection<LinnaeusSpecies> sps = select(jCas, LinnaeusSpecies.class);

        assertResultsContains(sps, "mostProbableSpeciesId", "species:ncbi:9606");
        assertResultsContains(sps, "mostProbableSpeciesId", "species:ncbi:9913");
    }

    private AnalysisEngine createLinnaeusEngine()
            throws ResourceInitializationException {
        String config = BLUIMA_RESOURCE_DIR + "/linnaeus/properties.conf";
        return createEngine(LinnaeusAnnotator.class,
                LinnaeusAnnotator.CONFIG_FILE, config);
    }

    @Test
    public void test2() throws Exception {

        // FIXME rodent not found
        // JCas jCas = getTestCas("bla bla rodent bla bla");
        JCas jCas = getTestCas("bla bla rat bla bla");

        createLinnaeusEngine().process(jCas);

        Collection<LinnaeusSpecies> sps = select(jCas, LinnaeusSpecies.class);

        assertResultsContains(sps, "mostProbableSpeciesId",
                "species:ncbi:10116");
    }
}
