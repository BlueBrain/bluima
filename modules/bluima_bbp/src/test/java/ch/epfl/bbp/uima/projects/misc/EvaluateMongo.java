package ch.epfl.bbp.uima.projects.misc;

import static ch.epfl.bbp.uima.BlueUimaHelper.PROJECTS_ROOT;
import static ch.epfl.bbp.uima.laucher.PipelineScriptParser.parse;
import static com.google.common.base.Preconditions.checkArgument;
import static org.slf4j.LoggerFactory.getLogger;

import java.io.File;

import org.slf4j.Logger;

import ch.epfl.bbp.MissingUtils;
import ch.epfl.bbp.uima.RunPipeline;
import ch.epfl.bbp.uima.ae.GarbageCollectorAnnotator;
import ch.epfl.bbp.uima.ae.StatsAnnotatorPlus;
import ch.epfl.bbp.uima.mongo.MongoPipelineBuilder;
import ch.epfl.bbp.uima.types.Stopword;
import ch.epfl.bbp.uima.uimafit.PipelineBuilder;

public class EvaluateMongo {
    private static Logger LOG = getLogger(RunPipeline.class);

    public static void main(String[] args) {
        // write();
        // read();
        increment();
    }

    public static void write() {

        File root = new File(PROJECTS_ROOT + "/evaluate_mongo/write/");

        for (File script : root.listFiles()) {

            if (!script.getName().endsWith("pipeline"))
                continue;

            checkArgument(script.exists());
            LOG.debug(">>>> running script {}", script.getName());

            try {
                parse(script).run();
                
            } catch (Exception e) {
                LOG.error("something wrong running " + script.getName(), e);
            }
        }
    }

    public static void read() {

        File root = new File(PROJECTS_ROOT + "/evaluate_mongo/read/");

        for (File script : root.listFiles()) {

            GarbageCollectorAnnotator.runGC();
            MissingUtils.sleep(60 * 1000);

            if (!script.getName().endsWith("pipeline"))
                continue;

            checkArgument(script.exists());
            LOG.debug(">>>> running script {}", script.getName());

            try {
                parse(script).run();

            } catch (Exception e) {
                LOG.error("something wrong running " + script.getName(), e);
            }
        }
    }

    public static void increment() {

        // MONGO
        try {
            String[] conn = new String[] { "localhost", "writetomongo",
                    "writetomongo_20k", "", "" };
            PipelineBuilder p1 = new MongoPipelineBuilder(1, conn,
                    Stopword.class);

//          FIXME  AnalysisEngineDescription stop = AnalysisEngineFactory
//                    .createEngineDescription(
//                            StopwordsAnnotator.class,
//                            "stopwordsFilePath",
//                            TopicModelsHelper.TOPIC_MODELS_ROOT()
//                                    + "src/main/resources/stoplists/mallet_stopwords_en.txt");
//
//            p1.add(stop);
            p1.add(StatsAnnotatorPlus.class);
            p1.process();
        } catch (Throwable e) {
            e.printStackTrace();
        }

        // OTHERS
        File root = new File(PROJECTS_ROOT + "/evaluate_mongo/increment/");
        for (File script : root.listFiles()) {

            if (!script.getName().endsWith("pipeline"))
                continue;

            checkArgument(script.exists());
            LOG.debug(">>>> running script {}", script.getName());

            try {
                parse(script).run();

            } catch (Throwable e) {
                LOG.error("something wrong running " + script.getName(), e);
            }
        }
    }
}
