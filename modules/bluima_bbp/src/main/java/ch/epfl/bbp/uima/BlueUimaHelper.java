package ch.epfl.bbp.uima;

import static ch.epfl.bbp.uima.BlueUima.*;

/**
 * @author renaud.richardet@epfl.ch
 */
public class BlueUimaHelper {

    public static final String BLUE_UIMA_MODULE_HOME = BlueUima.BLUE_UIMA_ROOT
            + "modules/bluima_bbp/";

    // FIXME rename to pipelines
    public static final String SCRIPT_ROOT = BLUE_UIMA_MODULE_HOME
            + TEST_RESOURCES_PATH + "copy_to_release/";

    public static final String PROJECTS_ROOT = SCRIPT_ROOT + "projects/";
    public static final String EXAMPLE_ROOT = SCRIPT_ROOT + "examples/";
}
