package ch.epfl.bbp.uima.cr;

import static ch.epfl.bbp.io.LineReader.asText;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.HEADER;

import java.io.File;
import java.io.IOException;

import org.apache.uima.UimaContext;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import de.julielab.jules.types.Header;

/**
 * Iterates the given directory, and parses the text of individual .txt files
 * into {@link JCas}es.
 * 
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability(outputs = { HEADER })
public class TextFileReader extends AbstractFileReader {

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        fileExtensionFilter = "txt";// overwrite filetype filter
        super.initialize(context);
    }

    int docId = 0;

    @Override
    public void getNext(JCas jCas) throws IOException, CollectionException {

        File f = fileIterator.next();
        jCas.setDocumentText(asText(f));

        Header header = new Header(jCas);
        header.setSource(f.getAbsolutePath());
        header.setDocId(docId++ + "");
        header.addToIndexes();
    }
}
