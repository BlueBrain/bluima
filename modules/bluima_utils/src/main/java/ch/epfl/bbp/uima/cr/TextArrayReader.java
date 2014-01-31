package ch.epfl.bbp.uima.cr;

import static ch.epfl.bbp.uima.BlueUima.PARAM_INPUT;
import static org.apache.commons.lang.StringUtils.join;

import java.io.IOException;

import org.apache.uima.UimaContext;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.fit.component.JCasCollectionReader_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Progress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.epfl.bbp.uima.typesystem.TypeSystem;
import de.julielab.jules.types.Header;

/**
 * CollectionReader that takes a simple text array as input.
 * 
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability(outputs = { TypeSystem.HEADER })
public class TextArrayReader extends JCasCollectionReader_ImplBase {
    Logger LOG = LoggerFactory.getLogger(TextArrayReader.class);

    public static final String COMPONENT_ID = TextArrayReader.class.getName();

    @ConfigurationParameter(name = PARAM_INPUT, mandatory = true, //
    description = "a array of string, to be processed")
    private String[] inputArray;

    private int docId;

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);
        docId = 0;
        LOG.debug("inputArray is {}", join(inputArray));
    }

    public void getNext(JCas jcas) throws IOException, CollectionException {

        jcas.setDocumentText(inputArray[docId]);

        Header header = new Header(jcas);
        header.setDocId(++docId + "");
        header.setComponentId(COMPONENT_ID);
        header.addToIndexes();
    }

    public boolean hasNext() throws IOException, CollectionException {
        return docId < inputArray.length;
    }

    public Progress[] getProgress() {// nope
        return null;
    }
}
