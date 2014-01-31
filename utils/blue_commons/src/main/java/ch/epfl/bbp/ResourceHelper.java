package ch.epfl.bbp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Utility class to get access to Maven resources (files as {@link InputStream})
 * during testing.<br>
 * The way to go is to import it statically like<br>
 * {@code import static ch.epfl.bbp.ResourceHelper.*;}
 * 
 */
@Deprecated
public class ResourceHelper {

    /**
     * Use getInputStream instead, because Files are trouble, when in (maven)
     * JARs
     */
    public static File getFile(String resourceOrFile)
            throws FileNotFoundException {

        try {
            File file = new File(resourceOrFile);
            if (file.exists()) {
                return file;
            }
        } catch (Exception e) {// nope
        }

        return getFile(resourceOrFile, Thread.class, false);
    }

    /**
     * Use getInputStream instead, because Files are trouble, when in (maven)
     * JARs
     */
    public static File getFile(String resourceOrFile, Class<?> cls,
            boolean deleteTmpOnExit) throws FileNotFoundException {
        try {

            // jar:file:/home/.../blue.jar!/path/to/file.xml
            URI uri = getURL(resourceOrFile, cls).toURI();
            String uriStr = uri.toString();
            if (uriStr.startsWith("jar")) {

                if (uriStr.endsWith("/")) {
                    throw new UnsupportedOperationException(
                            "cannot unjar directories, only files");
                }

                String jarPath = uriStr.substring(4, uriStr.indexOf("!"))
                        .replace("file:", "");
                String filePath = uriStr.substring(uriStr.indexOf("!") + 2);

                JarFile jarFile = new JarFile(jarPath);
                assert (jarFile.size() > 0) : "no jarFile at " + jarPath;

                Enumeration<JarEntry> entries = jarFile.entries();

                while (entries.hasMoreElements()) {

                    JarEntry jarEntry = entries.nextElement();
                    if (jarEntry.toString().equals(filePath)) {
                        InputStream input = jarFile.getInputStream(jarEntry);
                        assert (input != null) : "empty is for " + jarEntry;
                        return tmpFileFromStream(input, filePath,
                                deleteTmpOnExit);
                    }
                }
                assert (false) : "file" + filePath + " not found in " + jarPath;
                return null;
            } else {
                return new File(uri);
            }

        } catch (URISyntaxException e) {
            throw new FileNotFoundException(resourceOrFile);
        } catch (IOException e) {
            throw new FileNotFoundException(resourceOrFile);
        }
    }

    private static File tmpFileFromStream(InputStream is, String filePath,
            boolean deleteTmpOnExit) throws IOException {

        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1,
                filePath.lastIndexOf("."));
        assert (fileName != null) : "filename cannot be null for " + filePath;
        String extension = filePath.substring(filePath.lastIndexOf("."));
        assert (extension != null) : "extension cannot be null for " + filePath;

        File tmpFile = File.createTempFile(fileName, extension);
        if (deleteTmpOnExit)
            tmpFile.deleteOnExit();
        assert (tmpFile.exists()) : "could not create tempfile";

        OutputStream out = new FileOutputStream(tmpFile);
        int read = 0;
        byte[] bytes = new byte[1024];
        while ((read = is.read(bytes)) != -1) {
            out.write(bytes, 0, read);
        }
        is.close();
        out.flush();
        out.close();
        assert (tmpFile.length() > 0) : "file empty "
                + tmpFile.getAbsolutePath();
        return tmpFile;
    }

    public static InputStream getInputStream(String resourceOrFile)
            throws FileNotFoundException {
        return getInputStream(resourceOrFile, Thread.class);
    }

    public static InputStream getInputStream(String resourceOrFile, Class<?> cls)
            throws FileNotFoundException {
        try {
            return getURL(resourceOrFile, cls).openStream();
        } catch (Exception e) {
            throw new FileNotFoundException(resourceOrFile);
        }
    }

    public static URL getURL(String resourceOrFile, Class<?> cls)
            throws FileNotFoundException {

        File file = new File(resourceOrFile);
        // System.out.println("checking file ");
        // is file
        if (file.exists()) {
            // System.out.println("file exists");
            try {
                return file.toURI().toURL();
            } catch (MalformedURLException e) {
                throw new FileNotFoundException(resourceOrFile);
            }
        }
        // is resource
        if (!file.exists()) {
            // System.out.println("file resource");
            URL url = cls.getResource(resourceOrFile);
            if (url != null) {
                return url;
            }
            url = cls.getResource("/" + resourceOrFile);
            if (url != null) {
                return url;
            }
        }
        throw new FileNotFoundException(resourceOrFile);
    }
}
