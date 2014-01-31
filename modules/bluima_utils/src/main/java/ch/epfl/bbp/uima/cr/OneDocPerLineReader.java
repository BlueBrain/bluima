package ch.epfl.bbp.uima.cr;

import static ch.epfl.bbp.uima.BlueUima.PARAM_INPUT_FILE;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.HEADER;
import static ch.epfl.bbp.uima.utils.Preconditions.checkFileExists;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.uima.UimaContext;
import org.apache.uima.cas.CAS;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.fit.component.JCasCollectionReader_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Progress;

import ch.epfl.bbp.io.LineReader;
import ch.epfl.bbp.uima.ae.output.SentenceDumpAnnotator;
import de.julielab.jules.types.Header;

/**
 * {@link CollectionReader} that takes a simple text file as input. Each line is
 * turned into a new {@link CAS}. Format is:<br>
 * <code>docId{tab}text</code><br>
 * If not tab is found (= no docId is given), then the document text is the
 * whole line, and an incremental docId is generated.<br/>
 * Lines starting with '#' are ignored.
 * 
 * @see SentenceDumpAnnotator
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability(outputs = { HEADER })
public class OneDocPerLineReader extends JCasCollectionReader_ImplBase {

    @ConfigurationParameter(name = PARAM_INPUT_FILE, description = "path to text file")
    private String inputFile;

    private Iterator<String> textFileReader;
    private String nextLine;
    private int pmId = 1;

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        try {
            checkFileExists(inputFile);
            textFileReader = new LineReader(new FileInputStream(inputFile))
                    .iterator();
        } catch (IOException e) {
            throw new ResourceInitializationException(e);
        }
    }

    public void getNext(JCas jcas) throws IOException, CollectionException {
        String[] split = nextLine.split("\t");
        Header header = new Header(jcas);
        if (split.length == 2) {
            header.setDocId(split[0]);
            jcas.setDocumentText(split[1]);
        } else {
            header.setDocId(pmId++ + "_generated");
            jcas.setDocumentText(nextLine);
        }
        header.addToIndexes();
    }

    public boolean hasNext() throws IOException, CollectionException {
        return hasNextLine();
    }

    private boolean hasNextLine() { // to deal with commented lines
        if (textFileReader.hasNext()) {
            nextLine = textFileReader.next();
            if (nextLine.startsWith("#"))
                return hasNextLine(); // recurse
            else
                return true;
        }
        return false;
    }

    public Progress[] getProgress() {// nope
        return null;
    }
}
