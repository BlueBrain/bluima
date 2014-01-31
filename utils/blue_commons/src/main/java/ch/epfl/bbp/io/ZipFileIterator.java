package ch.epfl.bbp.io;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * 
 * Allows to iterate over files in a zip archive. Note that the original name of
 * the file is not corresponding, e.g. {filename}.896149808022753684.tmp (since
 * we return a tmp file)
 * 
 * @author renaud.richardet@epfl.ch
 */
public class ZipFileIterator implements Iterator<File> {

    private byte[] buffer = new byte[1024];

    private FileInputStream is;
    private ZipInputStream zis;
    private ZipEntry ze;

    public ZipFileIterator(File file) throws FileNotFoundException {
	is = new FileInputStream(file);
	zis = new ZipInputStream(new BufferedInputStream(is));
    }

    @Override
    public boolean hasNext() {
	try {
	    return (ze = zis.getNextEntry()) != null;
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }

    @Override
    public File next() {
	try {
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    int count;

	    String filename = ze.getName();
	    File tmpFile = File.createTempFile(filename + ".", ".tmp");
	    tmpFile.deleteOnExit();// TODO make it configurable
	    FileOutputStream fout = new FileOutputStream(tmpFile);

	    while ((count = zis.read(buffer)) != -1) {
		baos.write(buffer, 0, count);
		byte[] bytes = baos.toByteArray();
		fout.write(bytes);
		baos.reset();
	    }
	    fout.close();
	    zis.closeEntry();

	    return tmpFile;

	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
    }

    @Override
    public void remove() {
	throw new RuntimeException("not implemented");
    }

    public void close() {
	try {
	    zis.close();
	    is.close();
	} catch (IOException e) {// nope
	}
    }
}
