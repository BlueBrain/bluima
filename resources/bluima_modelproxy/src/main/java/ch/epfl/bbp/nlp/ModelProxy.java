package ch.epfl.bbp.nlp;

import java.io.File;
import java.io.InputStream;

/**
 * Utility to load resources from jar archives given.
 */
public class ModelProxy {

    /**
     * Open a stream to a given model's resource
     * 
     * @param modelclass
     *            a ModelResource class
     * @return an input stream or null if no resource was found
     * @throws ModelProxyException
     *             if the class couldn't be found, is not a ModelResource or if
     *             an instance of this class couldn't be created
     */
    public static ModelStream getStream(String modelclass)
            throws ModelProxyException {
        try {

            @SuppressWarnings("unchecked")
            Class<ModelResource> res = (Class<ModelResource>) Class
                    .forName(modelclass);
            return getStream(res.newInstance());

        } catch (Exception e) {
            throw new ModelProxyException("Unable to load resource from "
                    + modelclass, e);
        }
    }

    /**
     * Open a stream to the given model's resource
     * 
     * @param res
     *            A model resource
     * @return an input stream or null if no resource was found
     */
    public static ModelStream getStream(ModelResource res) {
        return getStream(res.getClass(), res.getResourceName());
    }

    /**
     * Open a stream to a resource in a given class' jar archive
     * 
     * @param klass
     *            A class tag
     * @param filepath
     *            path to the file with the root of the path (`/`) being the
     *            root of the jar archive
     * @return an input stream or null if no resource was found
     */
    public static <T> ModelStream getStream(Class<T> klass, String filepath) {
        String name = new File(filepath).getName();
        ClassLoader loader = klass.getClassLoader();
        InputStream stream = loader.getResourceAsStream(filepath);
        return new ModelStream(name, stream);
    }
}
