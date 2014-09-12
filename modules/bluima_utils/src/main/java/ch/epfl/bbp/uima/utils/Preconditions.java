package ch.epfl.bbp.uima.utils;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.io.File;

/**
 * Extending {@link com.google.common.base.Preconditions}
 * 
 * @author renaud.richardet@epfl.ch
 */
public class Preconditions {

    public static void checkFileExists(String path) {
        checkFileExists(path, "");
    }

    public static void checkFileExists(String path, String msg) {
        checkNotNull(path, "path is null for " + path);
        checkFileExists(new File(path), msg);
    }

    public static void checkFileExists(File f) {
        checkFileExists(f, "");
    }

    public static void checkFileExists(File f, String msg) {
        checkArgument(f.exists(),
                msg + " Could not find file " + f.getAbsolutePath());
    }

    public static <T> void checkEquals(T expected, T actual) {
        checkEquals(expected, actual, "");
    }

    public static <T> void checkEquals(T expected, T actual, String msg) {
        checkArgument(expected.equals(actual), msg + "::"
                + "objects are not equal, expected '" + expected
                + "' but got '" + actual + "'");
    }
}
