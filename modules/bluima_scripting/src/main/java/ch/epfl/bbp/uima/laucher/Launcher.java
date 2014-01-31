package ch.epfl.bbp.uima.laucher;

import static ch.epfl.bbp.io.TextFileWriter.write;
import static java.io.File.createTempFile;
import static java.lang.Integer.parseInt;
import static org.apache.commons.io.FileUtils.iterateFiles;
import static org.apache.commons.lang.StringUtils.join;
import static org.slf4j.LoggerFactory.getLogger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.uima.UIMAException;
import org.slf4j.Logger;

import com.google.common.collect.Lists;

/**
 * Command line launcher utility to execute UIMA pipeline scripts.
 * 
 * @author renaud.richardet@epfl.ch
 */
public class Launcher {
    private static Logger LOG = getLogger(Launcher.class);

    // used in pipeline tests, do not change
    public static final String OK_MESSAGE = "Pipeline completed successfully  :-)";

    /**
     * @param args
     *            the relative path to a pipeline file. If none provided, lists
     *            all available pipelines and asks the user to make a choice.
     *            Then launches the pipeline.
     */
    public static void main(String[] args) throws IOException, UIMAException,
            ParseException {

        // no pipeline --> list and ask
        if (args == null || args.length == 0) {
            String home = System.getProperty("basedir");
            listPipelinesAndAsk(new File(home + "/pipelines"));
        }

        // pipeline provided --> run it
        else if (args.length > 0) {

            List<String> cliArgs = new ArrayList<String>();
            for (int i = 1; i < args.length; i++) {
                cliArgs.add(args[i]);
            }
            runPipeline(new File(args[0]), cliArgs);
        }

        else {
            System.err.println("Please provide a script file");
        }
    }

    /**
     * List all available pipelines and asks the user to make a choice. Then
     * launches the pipeline.
     */
    public static void listPipelinesAndAsk(File pipelines) throws IOException,
            UIMAException, ParseException {

        // welcome message
        System.out.println("////////////////////////////////////////////////");
        System.out.println("Bluima -- NLP pipeline for biomedical literature ");
        System.out
                .println("////////////////////////////////////////////////\n");

        // check pipeline dir
        if (!pipelines.exists()) {
            System.err.println("no pipeline directory at "
                    + pipelines.getAbsolutePath());
            return;
        }

        // list avail pipelines
        List<File> pipelinesFiles = new ArrayList<File>();
        Iterator<File> it = iterateFiles(pipelines,
                new String[] { "pipeline" }, true);
        System.out.println("Pipelines:");
        while (it.hasNext()) {
            File file = it.next();
            String relName = file.getAbsolutePath().substring(
                    pipelines.getAbsolutePath().length() + 1);
            int pId = pipelinesFiles.size() + 1;
            System.out.println(pId + "\t" + relName + "\t" + pId);
            pipelinesFiles.add(file);
        }

        int lastUsed = -1;
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream(
                    new File("target/last_pipeline")));
            lastUsed = parseInt(properties.getProperty("bluima.lastpipeline"));
            System.out.println("\nLast used:\n" + lastUsed + "\t"
                    + pipelinesFiles.get(lastUsed - 1).getName());
        } catch (Exception e) {// nope
        }

        // prompt the user & validate input
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in,
                "UTF-8"));
        System.out
                .println("\nEnter the pipeline number of your choice (or enter for last used): ");
        int pipelineNr = -17;
        String input = in.readLine();
        if (input == null || input.length() == -1) {
            System.err.println("No nr entered!");
            return;
        }
        input = input.trim();
        if (input.length() == 0) {
            if (lastUsed != -1) {
                pipelineNr = lastUsed;
                System.out.println("Using last pipeline");
            } else {
                System.err.println("No nr entered");
                return;
            }
        }
        if (pipelineNr == -17) { // not the prev pipeline, so parse number
            pipelineNr = parseInt(input);
        }
        if (pipelineNr < 0 || pipelineNr > pipelinesFiles.size()) {
            System.err.println("Invalid nr entered");
            return;
        }
        File pipelineScript = pipelinesFiles.get(pipelineNr - 1);
        System.out.println("--> Using: " + pipelineScript.getName());

        // store
        Properties props = new Properties();
        props.setProperty("bluima.lastpipeline", pipelineNr + "");
        props.store(new FileOutputStream(new File("target/last_pipeline")), "");

        // parse arguments (if any)
        ArrayList<String> args = Lists.newArrayList();
        System.out
                .println("\nEnter arguments (or just hit enter again to continue)!");
        int i = 1;
        while (true) {
            input = in.readLine();
            if (input == null || input.length() == -1)
                break;
            input = input.trim();
            if (input.length() == 0)
                break;
            System.out.println("Argument" + i++ + "='" + input + "'");
            args.add(input);
        }
        runPipeline(pipelineScript, args);
    }

    /**
     * Parse this pipeline and run it
     * 
     * @return true if no error during processing
     * @throws ParseException
     */
    public static void runPipeline(File scriptFile, List<String> cliArgs)
            throws IOException, UIMAException, ParseException {
        if (!scriptFile.exists()) {
            throw new IOException("Script file does not exist ("
                    + scriptFile.getAbsolutePath() + ")");
        }

        LOG.info("Parsing pipeline script at '{}'",
                scriptFile.getAbsolutePath() + " \n with CLI parameters: "
                        + join(cliArgs, ", "));
        Pipeline pipeline = null;
        try {
            pipeline = PipelineScriptParser.parse(scriptFile, cliArgs);
        } catch (ParseException e) {
            throw new ParseException("\nERROR parsing '" + scriptFile.getName()
                    + "'\n" + e.getMessage()
                    + "\n(see the README.txt for the pipeline script format)",
                    e.getErrorOffset());
        }

        File tmpXml = createTempFile(scriptFile.getName(), ".xml");
        write(tmpXml, pipeline.getXml());
        LOG.info("Xml serialization available at " + tmpXml.getAbsolutePath());

        LOG.info("Successfully parsed pipeline script, now starting pipeline...");
        LOG.info("*************************************************************");
        pipeline.run();
        // will be printed if no exception.
        // used in pipeline tests, do not change
        System.out.println(OK_MESSAGE);
    }
}
