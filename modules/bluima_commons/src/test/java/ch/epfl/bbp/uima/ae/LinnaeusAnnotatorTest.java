package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.BlueUima.PARAM_CONFIG_FILE;
import static ch.epfl.bbp.uima.testutils.UimaTests.assertResultsContains;
import static ch.epfl.bbp.uima.testutils.UimaTests.getTestCas;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.io.InputStream;
import java.util.Collection;

import org.apache.commons.io.IOUtils;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.junit.Test;

import ch.epfl.bbp.TestWithBluimaResource;
import ch.epfl.bbp.uima.types.LinnaeusSpecies;

public class LinnaeusAnnotatorTest extends TestWithBluimaResource {

    static AnalysisEngineDescription createLinnaeusEngineDescription()
            throws ResourceInitializationException {
        String config = BLUIMA_RESOURCE_DIR + "/linnaeus/properties.conf";
        return createEngineDescription(LinnaeusAnnotator.class,
                PARAM_CONFIG_FILE, config);
    }

    static AnalysisEngine createLinnaeusEngine()
            throws ResourceInitializationException {
        String config = BLUIMA_RESOURCE_DIR + "/linnaeus/properties.conf";
        return createEngine(LinnaeusAnnotator.class, PARAM_CONFIG_FILE, config);
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
