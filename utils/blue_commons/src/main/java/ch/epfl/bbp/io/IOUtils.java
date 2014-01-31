package ch.epfl.bbp.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipInputStream;

/**
 * For IO utils not covered in Apache commons or others
 * 
 * @author renaud.richardet@epfl.ch
 */
public class IOUtils {

    /** Maximum loop count when creating temp directories. */
    private static final int TEMP_DIR_ATTEMPTS = 10000;

    /**
     * Atomically creates a new directory somewhere beneath the system's
     * temporary directory (as defined by the {@code java.io.tmpdir} system
     * property), and returns its name.
     * 
     * <p>
     * Use this method instead of {@link File#createTempFile(String, String)}
     * when you wish to create a directory, not a regular file. A common pitfall
     * is to call {@code createTempFile}, delete the file and create a directory
     * in its place, but this leads a race condition which can be exploited to
     * create security vulnerabilities, especially when executable files are to
     * be written into the directory.
     * 
     * <p>
     * This method assumes that the temporary volume is writable, has free
     * inodes and free blocks, and that it will not be called thousands of times
     * per second.
     * 
     * @return the newly-created directory
     * @throws IllegalStateException
     *             if the directory could not be created
     */
    public static File createTempDir() {
        File baseDir = new File(System.getProperty("java.io.tmpdir"));
        String baseName = System.currentTimeMillis() + "-";

        for (int counter = 0; counter < TEMP_DIR_ATTEMPTS; counter++) {
            File tempDir = new File(baseDir, baseName + counter);
            if (tempDir.mkdir()) {
                return tempDir;
            }
        }
        throw new IllegalStateException("Failed to create directory within "
                + TEMP_DIR_ATTEMPTS + " attempts (tried " + baseName + "0 to "
                + baseName + (TEMP_DIR_ATTEMPTS - 1) + ')');
    }

    /**
     * It's better NOT TO USE THIS METHOD, since it creates a temporary file.
     * Use {@link IOUtils#unzipUniqueFileAsStream()} instead
     * 
     * @param inFile
     *            a zip file containing a unique file
     * @return the unique file through a temporary file
     */
    public static File unzipUniqueFile(File inFile) throws IOException {
        File outFile = File.createTempFile(inFile.getName(), "tmp");
        try {
            BufferedOutputStream out = null;
            ZipInputStream in = new ZipInputStream(new BufferedInputStream(
                    new FileInputStream(inFile)));
            in.getNextEntry();
            int count;
            byte data[] = new byte[1024];
            out = new BufferedOutputStream(new FileOutputStream(outFile), 1024);
            while ((count = in.read(data, 0, 1024)) != -1) {
                out.write(data, 0, count);
            }
            out.flush();
            out.close();
            in.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return outFile;
    }

    /**
     * @param inFile
     *            a zip file containing a unique file
     */
    public static InputStream unzipUniqueFileAsStream(File inFile)
            throws IOException {

        try {
            ZipInputStream in = new ZipInputStream(new BufferedInputStream(
                    new FileInputStream(inFile)));
            in.getNextEntry();
            int count;
            byte data[] = new byte[1024];
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            while ((count = in.read(data, 0, 1024)) != -1) {
                out.write(data, 0, count);
            }
            out.flush();

            InputStream inputStream = new BufferedInputStream(
                    new ByteArrayInputStream(out.toByteArray()));

            out.close();
            in.close();
            return inputStream;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
