package ch.epfl.bbp.uima.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract class of all {@link DirectoryIterator}s.
 * 
 * @author renaud.richardet@epfl.ch
 */
public abstract class DirectoryIterator {
    protected static Logger LOG = LoggerFactory
            .getLogger(DirectoryIterator.class);

    public static final String LARGE = LargeDirectoryIterator.class
            .getSimpleName();
    public static final String ZIP = ZipDirectoryIterator.class.getSimpleName();

    protected File dir;
    protected String extensionFilter = null;
    protected boolean isRecursive = false;

    public DirectoryIterator() {
    }

    public DirectoryIterator setDirectory(File dir)
            throws FileNotFoundException {
        if (!dir.exists())
            throw new FileNotFoundException("dir does not exist: " + dir);
        this.dir = dir;
        return this;
    }

    /**
     * @param ext
     *            the file extension to filter on, should be e.g. "pdf" (WITHOUT
     *            dot!)
     */
    public DirectoryIterator setExtensionFilter(String ext) {
        if (ext == null || ext.length() == 0 || ext.startsWith(".")) {
            throw new RuntimeException("invalid extensionFilter '" + ext
                    + "', should be e.g. \"pdf\" (WITHOUT dot!)");
        }
        this.extensionFilter = ext;
        return this;
    }

    public DirectoryIterator setRecursive(boolean isRecursive) {
        this.isRecursive = isRecursive;
        return this;
    }

    public abstract Iterator<File> iterator() throws IOException;

    /**
     * Loads a {@link DirectoryIterator} by reflexion and sets dir and
     * extensionFilter
     * 
     * @throws IOException
     *             if anything goes wrong, including {@link RuntimeException}s
     */
    public static Iterator<File> get(String directoryIteratorType, File dir,
            String extensionFilter, boolean recursive) throws IOException {

        try {
            // add package name to e.g. "DefaultDirectoryIterator"
            if (directoryIteratorType.indexOf('.') == -1) {
                directoryIteratorType = DirectoryIterator.class.getPackage()
                        .getName() + "." + directoryIteratorType;
            }

            DirectoryIterator it = (DirectoryIterator) Class.forName(
                    directoryIteratorType).newInstance();
            it.setDirectory(dir);
            it.setRecursive(recursive);

            if (extensionFilter != null)
                it.setExtensionFilter(extensionFilter);
            return it.iterator();
        } catch (Throwable t) {
            throw new IOException(t);
        }
    }
}
