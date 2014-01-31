package ch.epfl.bbp.uima.ae.cleanup;

import static ch.epfl.bbp.uima.laucher.Launcher.runPipeline;
import static com.google.common.collect.Lists.newArrayList;

import java.io.File;

import org.junit.Ignore;
import org.junit.Test;

import ch.epfl.bbp.uima.BlueUimaHelper;

public class TooMuchOOVFilterAnnotatorTest {

    @Test
    @Ignore
    public void test() throws Exception {

        String ROOT = BlueUimaHelper.BLUE_UIMA_MODULE_HOME
                + "src/test/resources/cleanup/";

        for (String file : new String[] { "12834440_verybad.pdf",
                "22125184.pdf", "22139325.pdf", "22219130.pdf",
                "6664492_bad.pdf" }) {

            @SuppressWarnings("rawtypes")
            Class c = TooMuchOOVFilterAnnotatorTest.class;

            String pipelineScript = c.getResource(
                    c.getSimpleName() + ".pipeline").getFile();

            runPipeline(new File(pipelineScript), newArrayList(ROOT + file));
        }
    }
}
