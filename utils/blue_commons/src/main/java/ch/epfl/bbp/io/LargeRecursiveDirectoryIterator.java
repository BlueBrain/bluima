package ch.epfl.bbp.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

/**
 * Util to iterate very very large directories through low-level system call.<br>
 * ATM only works with UNIXes<br>
 * Supports recursive.
 * 
 * @author renaud.richardet@epfl.ch
 */
public class LargeRecursiveDirectoryIterator implements Iterator<File> {

    private String line;
    private BufferedReader reader;
    private String basePath;

    public LargeRecursiveDirectoryIterator(File directory) throws IOException {
        this(directory, "*");
    }

    /**
     * @param directory
     * @param extensionFilter
     *            e.g. "zip" or null for no filtering
     */
    public LargeRecursiveDirectoryIterator(File directory,
            String extensionFilter) throws IOException {

        if (File.separatorChar != '/') {// only nix
            throw new IOException("only works on nix at the moment");
        }

        if (!directory.exists() || !directory.isDirectory())
            throw new FileNotFoundException("not a dir or not found: "
                    + directory.getAbsolutePath());

        String directoryPath = directory.getAbsolutePath();
        this.basePath = directoryPath;

        // -f flag is important, because this way ls does not sort it output
        String[] params = {
                "/bin/sh",
                "-c",
                "find '" + directoryPath + "' -name '*." + extensionFilter
                        + "' " };
        Process process = Runtime.getRuntime().exec(params);
        reader = new BufferedReader(new InputStreamReader(
                process.getInputStream()));
        line = getNext();
    }

    private String getNext() {
        try {
            String next = reader.readLine();
            if (next == null)
                return null;
            // remove system folders . .. .DS_Store
            if (next.matches("\\.|\\.\\.|\\.DS_Store"))
                return getNext();

            return next;
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public boolean hasNext() {
        return line != null;
    }

    @Override
    public File next() {
        File ret = new File(line);
        if (!ret.exists())
            ret = new File(basePath, line);
        line = getNext();
        return ret;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
