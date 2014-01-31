package ch.epfl.bbp.uima.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 
 * Uses Guava's {@link com.google.common.io.Resources} utils to load resources
 * on the classpath
 * 
 * @author renaud.richardet@epfl.ch
 */
public class Resources {

    public static File getFile(String path) {
        return new File(com.google.common.io.Resources.getResource(path)
                .getFile());
    }

    public static InputStream getStream(String path) throws IOException {
        return com.google.common.io.Resources.getResource(path).openStream();
    }

    public static String resolvePath(String path) {
        return getFile(path).getAbsolutePath();
    }

}
