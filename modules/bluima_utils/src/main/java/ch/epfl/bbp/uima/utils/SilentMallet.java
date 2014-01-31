package ch.epfl.bbp.uima.utils;

import static ch.epfl.bbp.uima.BlueUima.BLUE_UTILS_ROOT;
import static ch.epfl.bbp.uima.utils.Preconditions.checkFileExists;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.logging.LogManager;

/** overwrite Mallet default logs (tooo verbose) */

// TODO move to mallet_utils
public class SilentMallet {

    static {
        try {
            LogManager lm = java.util.logging.LogManager.getLogManager();
            lm.reset();

            File logFile = new File(BLUE_UTILS_ROOT
                    + "src/main/resources/mallet/logging.properties");
            checkFileExists(logFile);
            InputStream in = new FileInputStream(logFile);
            lm.readConfiguration(in);
        } catch (Throwable t) {
            System.err
                    .println("could not reconfigure loggers, " + t.toString());
        }
    }
}
