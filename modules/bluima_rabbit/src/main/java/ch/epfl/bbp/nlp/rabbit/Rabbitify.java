package ch.epfl.bbp.nlp.rabbit;

import static ch.epfl.bbp.collections.Create.list;
import static ch.epfl.bbp.io.LineReader.asText;
import static ch.epfl.bbp.nlp.rabbit.RabbitReader.PARAM_TIMEOUT;
import static ch.epfl.bbp.nlp.rabbit.RabbitWriter.*;
import static ch.epfl.bbp.nlp.rabbit.Rabbitify.Mode.receiver;
import static ch.epfl.bbp.nlp.rabbit.Rabbitify.Mode.sender;
import static ch.epfl.bbp.uima.BlueUima.BLUE_UIMA_ROOT;
import static ch.epfl.bbp.uima.utils.Preconditions.checkEquals;
import static ch.epfl.bbp.uima.utils.Preconditions.checkFileExists;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.apache.commons.lang.StringUtils.join;
import static org.slf4j.LoggerFactory.getLogger;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import org.apache.uima.UIMAException;
import org.slf4j.Logger;

import ch.epfl.bbp.uima.laucher.Launcher;
import ch.epfl.bbp.uima.laucher.Pipeline;
import ch.epfl.bbp.uima.laucher.PipelineScriptParser;

/**
 * Allows to distribute a pipeline to Rabbit
 * 
 * @author renaud.richardet@epfl.ch
 */
public class Rabbitify {
    private static Logger LOG = getLogger(Launcher.class);

    public static final String RABBITIFY_HOME = BLUE_UIMA_ROOT
            + "modules/bluima_rabbit/";

    private final static String MASTER_TO_SLAVE_QUEUE = "bluima_master_to_slave_rabbittext_queue";
    private final static String SLAVE_TO_MASTER_QUEUE = "bluima_slave_to_master_rabbittext_queue";

    public static final String BEGIN_RABBITIFY = "##BEGIN_RABBIT_SLAVE";
    public static final String END_RABBITIFY = "##END_RABBIT_SLAVE";

    enum Mode {
        /** the first part of the pipeline. */
        sender, //
        /** the middle part of a pipeline */
        slave, //
        /** the last part of a pipeline */
        receiver, //
    }

    public static final String HELP = "format should be {pipeline_path} {mode} {runId} {replacementVars}?";

    public static void main(String[] args) throws IOException, ParseException,
            UIMAException {

        // pipeline script
        checkArgument(args.length > 2, HELP);
        checkNotNull(args[0], "no script found, " + HELP);
        File scriptFile = new File(args[0]);
        checkFileExists(scriptFile, "no script file found, " + HELP);

        // mode
        checkNotNull(args[1], "no mode found, " + HELP);
        Mode mode = Mode.valueOf(args[1]);

        // run id
        checkNotNull(args[2], "no runId found, " + HELP);
        String runId = args[2];

        // since 1st arg is pipeline script and 2nd is mode
        args = Arrays.copyOfRange(args, 3, args.length);

        run(scriptFile, args, mode, runId, 10);
    }

    /**
     * @param scriptFile
     *            script file, separated with {@link Rabbitify#BEGIN_RABBITIFY}
     *            and {@link Rabbitify#END_RABBITIFY}
     * @param replacementVars
     *            see {@link PipelineScriptParser#parse()}
     * 
     * @param mode
     *            either sender (the first part of the pipeline), slave (the
     *            middle part of a pipeline) or receiver (the last part of a
     *            pipeline)
     * @param runId
     *            gets used to name the rabbit queues, use 'test' for testing
     * @param timeout
     *            how long to wait (in seconds) before the reader exits the
     *            queue
     */
    public static void run(File scriptFile, String[] replacementVars,
            Mode mode, String runId, int timeout) throws IOException,
            ParseException, UIMAException {
        LOG.info("Rabbitifying pipeline script at '{}'",
                scriptFile.getAbsolutePath() + " \n with CLI parameters: "
                        + join(replacementVars, ", "));

        // SPLITTING PIPELINE
        final String pipelineLines = asText(scriptFile);
        checkArgument(pipelineLines.length() > 2);
        // in 3 parts
        final List<String> masterSender, slave, masterReceiver;
        String[] split1 = pipelineLines.split(BEGIN_RABBITIFY);
        checkEquals(2, split1.length);
        masterSender = list(split1[0].split("\n"));
        String[] split2 = split1[1].split(END_RABBITIFY);
        checkEquals(2, split1.length);
        slave = list(split2[0].split("\n"));
        masterReceiver = list(split2[1].split("\n"));

        // preparing script lines
        List<String> lines = list();
        if (mode.equals(sender)) {// MASTER_SENDER PIPELINE

            lines = masterSender;

            // add Rabbit writer
            lines.add("");
            lines.add("ae: " + RabbitWriter.class.getName());
            lines.add(" " + PARAM_QUEUE + ": "
                    + getMasterToSlaveQueue(runId + ""));

        } else if (mode.equals(Mode.slave)) { // SLAVE PIPELINE

            // add Rabbit reader
            lines.add("cr: " + RabbitReader.class.getName());
            lines.add(" " + PARAM_QUEUE + ": "
                    + getMasterToSlaveQueue(runId + ""));
            lines.add(" " + PARAM_TIMEOUT + "__java: " + timeout);

            lines.add("");
            lines.addAll(slave);
            lines.add("");

            // add Rabbit writer
            lines.add("ae: " + RabbitWriter.class.getName());
            lines.add(" " + PARAM_QUEUE + ": "
                    + getSlaveToMasterQueue(runId + ""));

        } else if (mode.equals(receiver)) {// MASTER_RECEIVER PIPELINE

            // add Rabbit reader
            lines.add("cr: " + RabbitReader.class.getName());
            lines.add(" " + PARAM_QUEUE + ": "
                    + getSlaveToMasterQueue(runId + ""));
            lines.add(" " + PARAM_TIMEOUT + "__java: " + timeout);

            lines.add("");
            lines.addAll(masterReceiver);
        }

        // RUN PIPELINE
        try {
            LOG.info("Starting Rabbit " + mode);
            Pipeline p = PipelineScriptParser.parse(lines,
                    scriptFile.getParent(), list(replacementVars));
            p.run();

        } catch (ParseException e) {
            throw new ParseException("\nERROR parsing " + mode + "\n"
                    + e.getMessage()
                    + "\n(see the README.txt for the pipeline script format)",
                    e.getErrorOffset());
        }
        System.out.println(Launcher.OK_MESSAGE);
    }

    public static String getMasterToSlaveQueue(String queueId) {
        return MASTER_TO_SLAVE_QUEUE + queueId;
    }

    public static String getSlaveToMasterQueue(String queueId) {
        return SLAVE_TO_MASTER_QUEUE + queueId;
    }
}
