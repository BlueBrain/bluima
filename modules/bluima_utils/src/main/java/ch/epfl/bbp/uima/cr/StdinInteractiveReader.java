package ch.epfl.bbp.uima.cr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.uima.UimaContext;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Progress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.uima.fit.component.JCasCollectionReader_ImplBase;
import org.apache.uima.fit.descriptor.TypeCapability;

import ch.epfl.bbp.uima.typesystem.TypeSystem;
import de.julielab.jules.types.Header;

/**
 * Interactively reads a sentence from stdin; useful for debugging.
 * 
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability(outputs = { TypeSystem.HEADER })
public class StdinInteractiveReader extends JCasCollectionReader_ImplBase {
    Logger LOG = LoggerFactory.getLogger(StdinInteractiveReader.class);

    String now = new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date());

    private int docId;

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);
        docId = 0;
    }

    public void getNext(JCas jcas) throws IOException, CollectionException {

        // prompt the user & validate input
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in,
                "UTF-8"));
        System.out
                .println("\n------------------------------------------------\n"
                        + "Enter a sentence and type <Enter> (or just <Enter> to exit): ");
        String input = in.readLine();
        if (input == null || input.length() == -1) {
            System.err.println("No sentence entered. Exiting system");
            System.exit(17);
        }
        input = input.trim();
        if (input.length() == 0) {
            System.err.println("No sentence entered. Exiting system");
            System.exit(17);
        }

        jcas.setDocumentText(input);

        Header header = new Header(jcas);
        String docIdStr = "InteractiveSession_" + now + "_" + +docId++;
        header.setDocId(docIdStr);
        header.addToIndexes();
        System.out.println("Processing document, id=" + docIdStr + "\n");
    }

    public boolean hasNext() throws IOException, CollectionException {
        return true;
    }

    public Progress[] getProgress() {// nope
        return null;
    }
}
