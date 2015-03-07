package ch.epfl.bbp.uima;

import static ch.epfl.bbp.uima.BlueUima.BLUE_UIMA_ROOT;
import static ch.epfl.bbp.uima.BlueUima.RESOURCES_PATH;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.apache.uima.ruta.engine.RutaEngine.PARAM_MAIN_SCRIPT;
import static org.apache.uima.ruta.engine.RutaEngine.PARAM_RESOURCE_PATHS;
import static org.apache.uima.ruta.engine.RutaEngine.PARAM_SCRIPT_PATHS;

import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.ruta.engine.RutaEngine;

/**
 * @author renaud.richardet@epfl.ch
 */
public class BrainRegionsHelper {

    public static final String BRAIN_REGIONS_HOME = BLUE_UIMA_ROOT
            + "modules/bluima_regions/";

    public static final String LEXICON_HOME = BRAIN_REGIONS_HOME
            + RESOURCES_PATH + "lexicons/";

    public static final String TEST_BASE = BRAIN_REGIONS_HOME
            + "src/test/resources/";

    /** Many pre- and postprocessing needed, see the tests */
    public static AnalysisEngineDescription getBrainregionRules()
            throws ResourceInitializationException {
        return createEngineDescription(RutaEngine.class,//
                PARAM_MAIN_SCRIPT, "Main",//
                PARAM_SCRIPT_PATHS, BRAIN_REGIONS_HOME + RESOURCES_PATH
                        + "ruta",//
                PARAM_RESOURCE_PATHS, LEXICON_HOME);
    }
}
