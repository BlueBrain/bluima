package ch.epfl.bbp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.UUID;

/**
 * @author ren
 * 
 */
public class Clipboard {

    public static final String SER_PATH = "data/clipboarder/";

    private static void init() throws IOException {
        File file = new File(SER_PATH);
        if (!file.exists()) {
            file.mkdirs();
            System.out.println("file://" + file.getAbsolutePath());
        }
    }

    /**
     * @param o
     *            object
     * @return a uuid to later retrieve this object
     */
    public static String put(Object o) {
        String uuid = UUID.randomUUID().toString();
        return put(o, uuid, false);
    }

    /**
     * @param o
     *            object
     * @param name_or_path
     *            a unique name or path for the stored object
     * @return a uuid to later retrieve this object
     */
    public static String put(Object o, String name_or_path) {
        return put(o, name_or_path, false);
    }

    /**
     * @param o
     *            object
     * @param name_or_path
     *            a unique name or path for the stored object
     * @param overwrite
     *            if an object with this name already exists
     * @return a uuid to later retrieve this object
     */
    public static String put(Object o, String name_or_path, boolean overwrite) {

        try {
            init();
            File file = new File(SER_PATH + name_or_path + ".ser");
            if (file.exists() && !overwrite) {
                System.out.println("ERROR, not saving since file already exists: "
                        + file.getAbsolutePath());
                return null;// TODO exception?
            }

            file.getParentFile().mkdirs();

            serialize(o, file);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return name_or_path;
    }

    public static boolean exists(String name_or_path_or_uuid) {
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
        File file = new File(SER_PATH + name_or_path_or_uuid + ".ser");
        return file.exists();
    }

    public static Object get(String name_or_path_or_uuid) {
        File file = new File(SER_PATH + name_or_path_or_uuid + ".ser");
        if (!file.exists()) {
            System.out.println("file does not exists: "
                    + file.getAbsolutePath());
            return null;// TODO exception?
        }

        try {
            return deserialize(file);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static boolean delete(String name_or_path_or_uuid) {
        File file = new File(SER_PATH + name_or_path_or_uuid + ".ser");
        return file.delete();
    }

    public static Object deserialize(String name) throws IOException,
            ClassNotFoundException {
        return deserialize(new File(name));
    }

    public static Object deserialize(File file) throws IOException,
            ClassNotFoundException {

        FileInputStream fileIn = new FileInputStream(file);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        Object o = in.readObject();
        in.close();
        fileIn.close();
        return o;
    }

    public static void serialize(Object o, File file) throws IOException {
        FileOutputStream fileOut = new FileOutputStream(file);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(o);
        out.close();
        fileOut.close();
    }

    public static void serialize(Object o, String name) throws IOException {
        serialize(o, new File(name));
    }
}
