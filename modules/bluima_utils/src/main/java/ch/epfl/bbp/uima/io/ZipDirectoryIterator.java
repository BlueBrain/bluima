package ch.epfl.bbp.uima.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import ch.epfl.bbp.io.ZipFileIterator;

/**
 * Allows to iterate over files in a zip archive 
 * @author renaud.richardet@epfl.ch
 */
public class ZipDirectoryIterator extends DirectoryIterator {

    @Override
    public Iterator<File> iterator() throws IOException {
	try {
	    return new ZipFileIterator(dir);
	} catch (FileNotFoundException e) {
	   throw new IOException(e);
	}
    }
}
