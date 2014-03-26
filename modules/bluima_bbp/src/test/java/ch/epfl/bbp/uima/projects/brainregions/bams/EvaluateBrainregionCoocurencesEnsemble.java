package ch.epfl.bbp.uima.projects.brainregions.bams;

import static ch.epfl.bbp.uima.BlueUimaHelper.PROJECTS_ROOT;
import static ch.epfl.bbp.uima.laucher.PipelineScriptParser.parse;
import static org.python.google.common.collect.Lists.newArrayList;
import static org.slf4j.LoggerFactory.getLogger;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;

import ch.epfl.bbp.uima.RunPipeline;

/**
 * Cross-validation of the ensemble of the 3 extractors. Need to run
 * 20140210_dump_corpus.pipeline first. See extract_brainregions/
 * 20140210_eval_extractors/20140303_eval_ENSEMBLE.pipeline
 * 
 * 
 * @author renaud.richardet@epfl.ch
 */
public class EvaluateBrainregionCoocurencesEnsemble {

    private static Logger LOG = getLogger(RunPipeline.class);

    static String pipeline = PROJECTS_ROOT
            + "extract_brainregions/20140210_eval_extractors/20140303_eval_ENSEMBLE.pipeline";
    static String dumpPipeline = PROJECTS_ROOT
            + "extract_brainregions/20140210_eval_extractors/20140210_dump_corpus.pipeline";

    public static void main(String[] a) throws Exception {

        // dump corpus first
        // parse(new File(dumpPipeline)).run();

        String[] extractorSetups = { "best",// /
                "1 1 1",//
                "1 1 0",//
                "1 0 1",//
                "1 0 0",//
                "0 1 1",//
                "0 1 0",//
                "0 0 1",//
        };

        for (String extractorSetup : extractorSetups) {

            for (int fold = 1; fold < 10; fold++) {
                System.out
                        .println("XXX\tFOLD\t" + fold + "\t" + extractorSetup);
                try {

                    String modelName = "model_" + fold + ".zip";
                    List<String> args = newArrayList("" + fold, modelName);

                    // Done before
                    // // train KERNEL
                    // parse(new File(pipeline + "train_KERNEL.pipeline"), args)
                    // .run();
                    // // copy model
                    // copyFile(new File("target/model.zip"), new
                    // File(modelName));

                    // eval ENSEMBLE
                    System.out.println("XXX\tEVAL ENSEMBLE\t" + fold + "\t"
                            + extractorSetup);
                    args.add(extractorSetup);
                    parse(new File(pipeline), args).run();

                } catch (Exception e1) {
                    LOG.error("", e1);
                }
            }
            System.exit(19);
        }
    }
}
