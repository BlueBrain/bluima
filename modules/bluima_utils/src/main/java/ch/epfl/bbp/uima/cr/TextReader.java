package ch.epfl.bbp.uima.cr;

import static ch.epfl.bbp.uima.BlueUima.PARAM_INPUT;

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
 * CollectionReader that takes a simple text as input. Produces a single
 * {@link JCas}. Useful for testing.
 * 
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability(outputs = { TypeSystem.HEADER })
public class TextReader extends JCasCollectionReader_ImplBase {
    Logger LOG = LoggerFactory.getLogger(TextReader.class);

    public static final String COMPONENT_ID = TextReader.class.getName();

    @ConfigurationParameter(name = PARAM_INPUT, mandatory = true, //
    description = "a string, to be processed")
    private String input;
    boolean done = false;

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);
        LOG.debug("input is {}", input);
    }

    public void getNext(JCas jcas) throws IOException, CollectionException {

        jcas.setDocumentText(input);

        Header header = new Header(jcas);
        header.setDocId("0");
        header.setComponentId(TextReader.COMPONENT_ID);
        header.addToIndexes();

        done = true;
    }

    public boolean hasNext() throws IOException, CollectionException {
        return !done;
    }

    public Progress[] getProgress() {// nope
        return null;
    }

}
