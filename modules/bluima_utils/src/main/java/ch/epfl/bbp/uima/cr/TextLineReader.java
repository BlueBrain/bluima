package ch.epfl.bbp.uima.cr;

import static com.google.common.base.Preconditions.checkArgument;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.uima.UimaContext;
import org.apache.uima.cas.CAS;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.fit.component.JCasCollectionReader_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Progress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.epfl.bbp.ResourceHelper;
import ch.epfl.bbp.io.LineReader;
import ch.epfl.bbp.uima.BlueUima;
import ch.epfl.bbp.uima.typesystem.TypeSystem;
import de.julielab.jules.types.Header;

/**
 * CollectionReader that takes a simple text file as input. Each line is turned
 * into a new {@link CAS}. Alternatively, each new empty lines creates a new
 * {@link CAS} (using the {@link TextLineReader#PARAM_SPLIT_MODE} parameter).
 * 
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability(outputs = { TypeSystem.HEADER })
public class TextLineReader extends JCasCollectionReader_ImplBase {
    Logger LOG = LoggerFactory.getLogger(TextLineReader.class);

    public static final String COMPONENT_ID = TextLineReader.class.getName();

    public static final String PARAM_SPLIT_MODE = "splitMode";

    @ConfigurationParameter(name = BlueUima.PARAM_INPUT_FILE, mandatory = true, //
    defaultValue = "pear_resources/sample_file.txt", description = "path to text file")
    private String inputFile;

    @ConfigurationParameter(name = PARAM_SPLIT_MODE, mandatory = true, //
    defaultValue = "1", description = "how to split for new CAS. Values are 1 (split on each new line, default), 2 (split on empty new line)")
    private Integer splitMode;

    private Iterator<String> lineIt;
    private int docId;

    @SuppressWarnings("resource")
    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);
        docId = 0;

        LOG.debug("inputFile is " + inputFile);
        try {
            InputStream inputFileIs = ResourceHelper.getInputStream(inputFile);
            checkArgument(inputFileIs != null);
            lineIt = new LineReader(inputFileIs).iterator();
        } catch (Exception e) {
            throw new ResourceInitializationException(
                    ResourceInitializationException.NO_RESOURCE_FOR_PARAMETERS,
                    new Object[] { "inputFile: " + inputFile });
        }

        if (splitMode == null || splitMode < 1 || splitMode > 2) {
            throw new ResourceInitializationException(
                    ResourceInitializationException.RESOURCE_DATA_NOT_VALID,
                    new Object[] { splitMode, PARAM_SPLIT_MODE });
        }
    }

    public void getNext(JCas jcas) throws IOException, CollectionException {

        if (splitMode == 1) {
            String line = lineIt.next();
            jcas.setDocumentText(line.trim());
        } else { // add text until empty line is found
            String text = "";
            while (lineIt.hasNext()) {
                String line = lineIt.next();
                if (line.length() == 0) {
                    break;
                } else {
                    text += "\n" + line;
                }
            }
            jcas.setDocumentText(text.trim());
        }

        Header header = new Header(jcas);
        header.setDocId(++docId + "");
        header.setComponentId(TextLineReader.COMPONENT_ID);
        header.addToIndexes();
    }

    public boolean hasNext() throws IOException, CollectionException {
        return lineIt.hasNext();
    }

    public Progress[] getProgress() {// nope
        return null;
    }
}
