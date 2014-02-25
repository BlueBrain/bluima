package ch.epfl.bbp.uima.ae.serialization;

import static ch.epfl.bbp.uima.typesystem.TypeSystem.HEADER;
import static org.apache.uima.cas.impl.Serialization.deserializeCAS;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.zip.GZIPInputStream;

import org.apache.uima.UimaContext;
import org.apache.uima.cas.impl.CASMgrSerializer;
import org.apache.uima.cas.impl.TypeSystemImpl;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import ch.epfl.bbp.uima.BlueUima;
import ch.epfl.bbp.uima.cr.AbstractFileReader;
import ch.epfl.bbp.uima.utils.StructuredDirectory;

/**
 * Reads CASes from a serialized, compressed binary format, in a
 * {@link StructuredDirectory}, that has been created with
 * {@link BinaryCasWriter}.<br?>
 * 
 * REM: it is recommended to specify LargeDirectoryIterator as
 * {@link BlueUima#PARAM_DIRECTORY_ITERATOR}, e.g.
 * <code> directoryIterator: LargeDirectoryIterator</code>
 * 
 * 
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability(outputs = HEADER)
public class BinaryCasReader extends AbstractFileReader {

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        fileExtensionFilter = "gz"; // overwrite
        isRecursive = true;// overwrite
        super.initialize(context);
    }

    @Override
    public void getNext(JCas jCas) throws IOException, CollectionException {
        File file = fileIterator.next();
        try {
            deserialize(file, jCas);
        } catch (Exception e) {
            LOG.error(
                    "could not read serialized cas at "
                            + file.getAbsolutePath(), e);
        }
    }

    public static void deserialize(File file, JCas jCas) throws IOException,
            ClassNotFoundException, ResourceInitializationException {
        InputStream dis = new GZIPInputStream(new FileInputStream(file));
        TypeSystemImpl ts = null;

        ObjectInputStream ois = new ObjectInputStream(dis);
        CASMgrSerializer casMgrSerializer = (CASMgrSerializer) ois.readObject();
        ts = casMgrSerializer.getTypeSystem();
        ts.commit();

        deserializeCAS(jCas.getCas(), dis, ts, null);
        dis.close();
    }
}
