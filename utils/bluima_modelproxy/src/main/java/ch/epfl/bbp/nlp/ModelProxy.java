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
     *            a class name that implements ModelResource
     * @return an input stream
     * @throws ModelProxyException
     *             if the class or its resource couldn't be found, or if the
     *             class is not implementing ModelResource or if an instance of
     *             this class couldn't be created
     */
    public static ModelStream getStream(String modelclass)
            throws ModelProxyException {
        try {

            @SuppressWarnings("unchecked")
            Class<ModelResource> res = (Class<ModelResource>) Class
                    .forName(modelclass);
            return getStream(res.newInstance());

        } catch (InstantiationException | IllegalAccessException e) {
            String err = "Unable to instanciate " + modelclass;
            throw new ModelProxyException(err, e);
        } catch (ClassNotFoundException e) {
            String err = "Unable to find model " + modelclass;
            throw new ModelProxyException(err, e);
        }

    }

    /**
     * Open a stream to the given model's resource
     * 
     * @param res
     *            A model resource
     * @return an input stream or null if no resource was found
     * @throws ModelProxyException
     *             if the resource couldn't be opened
     */
    public static ModelStream getStream(ModelResource res)
            throws ModelProxyException {
        return getStream(res.getClass(), res.getResourcePath());
    }

    /**
     * Open a stream to a resource in a given class' jar archive
     * 
     * @param klass
     *            A class tag
     * @param filepath
     *            path to the file with the root of the path (`/`) being the
     *            root of the jar archive
     * @return an input stream
     * @throws ModelProxyException
     *             if the resource couldn't be opened
     */
    public static <T> ModelStream getStream(Class<T> klass, String filepath)
            throws ModelProxyException {

        String name = new File(filepath).getName();
        InputStream stream = klass.getResourceAsStream(filepath);

        if (stream == null) {
            String err = "Unable to open stream to " + filepath;
            throw new ModelProxyException(err);
        }

        return new ModelStream(name, stream);
    }
}
