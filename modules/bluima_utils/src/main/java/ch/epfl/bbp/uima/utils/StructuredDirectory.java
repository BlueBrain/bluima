package ch.epfl.bbp.uima.utils;

import static java.lang.Integer.parseInt;
import static org.apache.commons.io.FileUtils.copyFile;
import static org.apache.commons.io.FileUtils.forceMkdir;
import static org.apache.commons.io.FilenameUtils.getExtension;
import static org.apache.commons.io.FilenameUtils.removeExtension;

import java.io.File;
import java.io.IOException;

import com.google.common.base.Preconditions;

/**
 * Util to get and create paths for storing millions of files in an organized,
 * structured folder structure.
 * 
 * @author renaud.richardet@epfl.ch
 */
public class StructuredDirectory {

    /**
     * @param docId
     * @return an array with 3 elements, the last 2 elements have 3 digits,
     *         e.g.:
     *         <ul>
     *         <li>2345678 -> {2, 345, 678}</li>
     *         <li>78 -> {0, 0, 78} (no leading 0es, just 78!)</li>
     *         </ul>
     * 
     */
    public static int[] getSplit(int docId) {
        if (docId <= 0) {
            throw new IllegalArgumentException("docId must be positive");
        }
        int[] ret = new int[3];
        ret[0] = docId / 1000000;
        ret[1] = (docId % 1000000) / 1000;
        ret[2] = docId % 1000;
        return ret;
    }

    /**
     * @param inputFile
     *            will be copied to an organized directory-structure, e.g
     *            2345678.txt to {outdir}/2/345/678.txt
     * @param outDir
     * @return the newly created file
     */
    public static File storeFile(File outDir, File inputFile)
            throws IOException {

        int docId = parseInt(removeExtension(inputFile.getName()));
        int[] paths = getSplit(docId);

        forceMkdir(outDir);
        File dir1 = new File(outDir, paths[0] + "");
        forceMkdir(dir1);
        File dir2 = new File(dir1, paths[1] + "");
        forceMkdir(dir2);

        String newFileName = paths[2] + "." + getExtension(inputFile.getName());
        File newFile = new File(dir2, newFileName);
        copyFile(inputFile, newFile);
        return newFile;
    }

    public static int decodeFileName(File inputFile) throws IOException {
        try {
            int ret = parseInt(removeExtension(inputFile.getName()));
            ret += 1000 * parseInt(inputFile.getParentFile().getName());
            ret += 1000000 * parseInt(inputFile.getParentFile().getParentFile()
                    .getName());
            return ret;
        } catch (Throwable t) {
            throw new IOException("could not decode filename, make sure"
                    + " it looks like somepath/12/345/678.txt", t);
        }
    }

    public static int decodeFileName(String inputFile) throws IOException {

        String[] split = inputFile.split("/");
        Preconditions.checkArgument(split.length == 3);
        try {
            int ret = parseInt(removeExtension(split[2]));
            ret += 1000 * parseInt(split[1]);
            ret += 1000000 * parseInt(split[0]);
            return ret;
        } catch (Throwable t) {
            throw new IOException("could not decode filename, make sure"
                    + " it looks like somepath/12/345/678.txt", t);
        }
    }

    /**
     * @param docId
     *            usually PubMed id
     * @param extention
     *            e.g. "gz", or "pdf" (WITHOUT dot!)
     * @return a path, e.g.:
     *         <ul>
     *         <li>2345678, "gz" -> "2/345/678.gz"</li>
     *         <li>78, "pdf" -> "0/0/78.pdf"</li>
     *         </ul>
     * 
     */
    public static String getFilePath(int docId, String extention) {
        int[] split = getSplit(docId);
        return split[0] + "/" + split[1] + "/" + split[2] + "." + extention;
    }
}
