package ch.epfl.bbp.uima.projects.brainregions.bams;

import static ch.epfl.bbp.uima.BlueUimaHelper.PROJECTS_ROOT;
import static ch.epfl.bbp.uima.laucher.PipelineScriptParser.parse;
import static org.apache.commons.io.FileUtils.copyFile;
import static org.python.google.common.collect.Lists.newArrayList;
import static org.slf4j.LoggerFactory.getLogger;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;

import ch.epfl.bbp.uima.RunPipeline;

/**
 * Cross-validation of the 3 extractors. Need to run
 * 20140210_dump_corpus.pipeline first
 * 
 * @author renaud.richardet@epfl.ch
 */
public class EvaluateBrainregionCoocurences {

    private static Logger LOG = getLogger(RunPipeline.class);

    public static void main(String[] a) throws Exception {

        String root = PROJECTS_ROOT
                + "/extract_brainregions/20140210_eval_extractors/20140210_";

        parse(new File(root + "dump_corpus.pipeline")).run();

        for (int fold = 1; fold < 10; fold++) {
            System.out.println("XXX\tFOLD\t" + fold + "");
            try {

                String modelName = "model_" + fold + ".zip";
                List<String> args = newArrayList("" + fold, modelName);

                System.out.println("XXX\tRULES\t" + fold + "");
                parse(new File(root + "eval_RULES.pipeline"), args).run();

                System.out.println("XXX\tTOPDOWN\t" + fold + "");
                parse(new File(root + "eval_TOPDOWN.pipeline"), args).run();

                // train KERNEL
                parse(new File(root + "train_KERNEL.pipeline"), args).run();
                // copy model
                copyFile(new File("target/model.zip"), new File(modelName));
                // eval KERNEL
                System.out.println("XXX\tKERNEL\t" + fold + "");
                parse(new File(root + "eval_KERNEL.pipeline"), args).run();

            } catch (Exception e1) {
                LOG.error("", e1);
            }
        }
    }
}
