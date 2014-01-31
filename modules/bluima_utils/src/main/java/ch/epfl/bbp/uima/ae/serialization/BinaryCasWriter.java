package ch.epfl.bbp.uima.ae.serialization;

import static ch.epfl.bbp.uima.BlueCasUtil.getHeaderIntDocId;
import static org.apache.commons.io.FileUtils.forceMkdir;
import static org.apache.uima.cas.impl.Serialization.serializeWithCompression;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.epfl.bbp.uima.BlueUima;
import ch.epfl.bbp.uima.utils.StructuredDirectory;

/**
 * Writes CASes into a serialized, compressed binary format, using a
 * {@link StructuredDirectory}. Can be consumed with {@link RangeBinaryCasReader}.
 * 
 * @author renaud.richardet@epfl.ch
 */
public class BinaryCasWriter extends JCasAnnotator_ImplBase {
    private static Logger LOG = LoggerFactory.getLogger(BinaryCasWriter.class);

    public static final String EXTENTION_GZ = "gz";

    @ConfigurationParameter(name = BlueUima.PARAM_OUTPUT_DIR)
    private String outputDir;

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);
    }

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {

        int docId = getHeaderIntDocId(jCas);
        try {
            File file = new File(outputDir+"/"
                    + StructuredDirectory.getFilePath(docId, EXTENTION_GZ));
            // Create parent folders for output file
            if (file.getParentFile() != null) {
                forceMkdir(file.getParentFile());
            }
            OutputStream os = new GZIPOutputStream(new FileOutputStream(file));
            serializeWithCompression(jCas.getCas(), os, jCas.getTypeSystem());
            os.close();
        } catch (Exception e) {
            LOG.error("could not serialize pmId " + docId, e);
        }
    }
}
