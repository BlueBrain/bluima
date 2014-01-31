package ch.epfl.bbp.uima.ae.serialization;

import static ch.epfl.bbp.uima.BlueUima.PARAM_INPUT_DIRECTORY;
import static ch.epfl.bbp.uima.utils.Preconditions.checkFileExists;
import static org.apache.commons.io.FileUtils.iterateFiles;
import static org.apache.uima.cas.impl.Serialization.deserializeCAS;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.zip.GZIPInputStream;

import org.apache.uima.UimaContext;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.fit.component.JCasCollectionReader_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Progress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.epfl.bbp.uima.utils.StructuredDirectory;

/**
 * Reads CASes from a serialized, compressed binary format, in a
 * {@link StructuredDirectory}, that has been created with
 * {@link BinaryCasWriter}.
 * 
 * @author renaud.richardet@epfl.ch
 */
public class BinaryCasReader extends JCasCollectionReader_ImplBase {
    protected static final Logger LOG = LoggerFactory
            .getLogger(BinaryCasReader.class);

    @ConfigurationParameter(name = PARAM_INPUT_DIRECTORY, //
    description = "path to a structured directory containing serialized CASes")
    protected String inputDir;
    private Iterator<File> files;

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);
        checkFileExists(inputDir);
        files = iterateFiles(new File(inputDir), new String[] { "gz" }, true);
    }

    @Override
    public boolean hasNext() throws IOException, CollectionException {
        return files.hasNext();
    }

    @Override
    public Progress[] getProgress() {// nope
        return null;
    }

    @Override
    public void getNext(JCas jCas) throws IOException, CollectionException {
        File file = files.next();
        try {
            InputStream ois = new GZIPInputStream(new FileInputStream(file));
            deserializeCAS(jCas.getCas(), ois);// , TypeSystem.JULIE_TSD,null);
            ois.close();
        } catch (Exception e) {
            LOG.error(
                    "could not read serialized cas at "
                            + file.getAbsolutePath(), e);
        }

    }
}
