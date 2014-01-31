package ch.epfl.bbp.uima.cr;

import java.io.IOException;

import org.apache.uima.collection.CollectionException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.util.Progress;
import org.apache.uima.fit.component.JCasCollectionReader_ImplBase;

import ch.epfl.bbp.uima.testutils.UimaTests;
import de.julielab.jules.types.Header;

/**
 * Reads a single sample abstract, pmId 1957687. Useful for testing.
 * 
 * @author renaud.richardet@epfl.ch
 */
public class SingleAbstractReader extends JCasCollectionReader_ImplBase {

    public static String getText() {
        return UimaTests.TEST_ABSTRACT;
    }

    public static int getPmId() {
        return 1957687;
    }

    /** only true for one doc, then false */
    private boolean hasNext = true;

    @Override
    public void getNext(JCas jcas) throws IOException, CollectionException {

        hasNext = false;

        Header header = new Header(jcas);
        header.setDocId(getPmId() + "");
        header.addToIndexes();
        jcas.setDocumentText(getText());
    }

    @Override
    public boolean hasNext() throws IOException, CollectionException {
        return hasNext;
    }

    @Override
    public Progress[] getProgress() {// nope
        return null;
    }
}
