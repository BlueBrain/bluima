package ch.epfl.bbp.uima;

import java.io.File;

import ch.epfl.bbp.uima.laucher.Launcher;

/**
 * {@link #main}: Lists all available pipelines and asks the user to make a
 * choice. Then launches the pipeline. Useful for testing pipelines within the
 * blue_uima Eclipse module.
 * 
 * @author renaud.richardet@epfl.ch
 */
public class RunPipeline {

    public static void main(String[] args) throws Exception {

        // no pipeline --> list and ask
        if (args == null || args.length == 0) {
            File pipelines = new File(BlueUimaHelper.SCRIPT_ROOT);
            Launcher.listPipelinesAndAsk(pipelines);
        }

        // pipeline provided --> run it
        else if (args.length > 0) {
            Launcher.main(args);
        }
    }
}
