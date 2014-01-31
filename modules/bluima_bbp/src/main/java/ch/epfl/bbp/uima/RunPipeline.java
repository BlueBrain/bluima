package ch.epfl.bbp.uima;

import static ch.epfl.bbp.uima.BlueUimaHelper.SCRIPT_ROOT;

import java.io.File;

import ch.epfl.bbp.uima.laucher.Launcher;

/**
 * {@link #main}: Lists all available pipelines and asks the user to make a
 * choice. Then launches the pipeline. Useful for testing pipelines within the
 * bluima Eclipse module.
 * 
 * @author renaud.richardet@epfl.ch
 */
public class RunPipeline {

    public static final File PIPELINES = new File(SCRIPT_ROOT);

    public static void main(String[] args) throws Exception {

        // no pipeline --> list and ask
        if (args == null || args.length == 0) {
            Launcher.listPipelinesAndAsk(PIPELINES);
        }

        // pipeline provided --> run it
        else if (args.length > 0) {
            Launcher.main(args);
        }
    }
}
