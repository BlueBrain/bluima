package ch.epfl.bbp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Utility to de/serialize object to byte/files. I use it when passing object
 * to/from RabbitMQ clients
 * 
 * @author renaud.richardet@epfl.ch
 */
public class ObjectSerializer {

    public static void toFile(Object obj, File f) throws IOException {
	FileOutputStream fout = new FileOutputStream(f);
	ObjectOutputStream oos = new ObjectOutputStream(fout);
	oos.writeObject(obj);
	oos.close();
    }

    public static Object fromFile(File f) throws IOException,
	    ClassNotFoundException {
	FileInputStream fin = new FileInputStream(f);
	ObjectInputStream ois = new ObjectInputStream(fin);
	Object obj = ois.readObject();
	ois.close();
	return obj;
    }

    public static byte[] toByteArray(Object obj) {
	byte[] bytes = null;
	ByteArrayOutputStream bos = new ByteArrayOutputStream();
	try {
	    ObjectOutputStream oos = new ObjectOutputStream(bos);
	    oos.writeObject(obj);
	    oos.flush();
	    oos.close();
	    bos.close();
	    bytes = bos.toByteArray();
	} catch (IOException ex) {
	    ex.fillInStackTrace();
	}
	return bytes;
    }

    public static Object fromByteArray(byte[] bytes) {
	Object obj = null;
	try {
	    ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
	    ObjectInputStream ois = new ObjectInputStream(bis);
	    obj = ois.readObject();
	} catch (Exception ex) {
	    ex.fillInStackTrace();
	}
	return obj;
    }
}
