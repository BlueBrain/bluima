package ch.epfl.bbp.uima.io;

import java.io.File;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;

public class DefaultDirectoryIterator extends DirectoryIterator {

    @Override
    public Iterator<File> iterator() {

        if (extensionFilter == null)
            return FileUtils.iterateFiles(dir, null, isRecursive);

        return FileUtils.iterateFiles(dir, new String[] { extensionFilter },
                isRecursive);
    }

}
