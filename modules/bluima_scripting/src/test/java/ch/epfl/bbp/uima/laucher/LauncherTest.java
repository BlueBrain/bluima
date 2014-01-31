package ch.epfl.bbp.uima.laucher;

import static ch.epfl.bbp.uima.BlueUima.BLUE_UIMA_ROOT;
import static ch.epfl.bbp.uima.BlueUima.TEST_RESOURCES_PATH;
import static com.google.common.collect.Lists.newArrayList;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import org.junit.Test;

public class LauncherTest {

    public static final String LAUNCHER_ROOT = BLUE_UIMA_ROOT
            + "modules/bluima_scripting/";
    public static final String TESTS_ROOT = LAUNCHER_ROOT + TEST_RESOURCES_PATH;

    @Test
    public void testSimple() throws Exception {
        Launcher.main(new String[] { TESTS_ROOT + "laucher/simple.pipeline" });
    }

    @Test
    public void testBeanshell() throws Exception {
        Launcher.main(new String[] { TESTS_ROOT + "laucher/beanshell.pipeline" });
    }

    @Test
    public void testArgs() throws Exception {
        // should not throw an exception
        Launcher.runPipeline(new File(TESTS_ROOT + "laucher/args.pipeline"),
                newArrayList("two words", "2", "10"));
    }

    @Test(expected = ParseException.class)
    public void testArgsFail() throws Exception {

        // "because of: ERROR parsing 'args.pipeline' cannot compile and "
        // + "run '__java' parameter with value '1asfdafsd0",
        Launcher.runPipeline(new File(TESTS_ROOT + "laucher/args.pipeline"),
                newArrayList("two words", "2", "1asfdafsd0"));
    }

    @Test(expected = IOException.class)
    public void testArgsFail2() throws Exception {
        // no file
        Launcher.runPipeline(new File("blablalba"), new ArrayList<String>());
    }
}
