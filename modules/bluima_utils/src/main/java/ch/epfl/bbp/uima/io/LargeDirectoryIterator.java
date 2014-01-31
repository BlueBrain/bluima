package ch.epfl.bbp.uima.io;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

/**
 * @author renaud.richardet@epfl.ch
 */
public class LargeDirectoryIterator extends DirectoryIterator {

    @Override
    public Iterator<File> iterator() throws IOException {

        if (isRecursive) {

            if (extensionFilter == null)
                return new ch.epfl.bbp.io.LargeRecursiveDirectoryIterator(dir);

            return new ch.epfl.bbp.io.LargeRecursiveDirectoryIterator(dir,
                    extensionFilter);
        } else {

            if (extensionFilter == null)
                return new ch.epfl.bbp.io.LargeDirectoryIterator(dir);

            return new ch.epfl.bbp.io.LargeDirectoryIterator(dir,
                    extensionFilter);
        }
    }
}
