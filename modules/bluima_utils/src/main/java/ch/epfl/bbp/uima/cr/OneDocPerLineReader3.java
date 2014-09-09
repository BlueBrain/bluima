package ch.epfl.bbp.uima.cr;

import static ch.epfl.bbp.uima.BlueUima.PARAM_BETWEEN;
import static ch.epfl.bbp.uima.BlueUima.PARAM_INPUT_DIRECTORY;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.HEADER;
import static ch.epfl.bbp.uima.utils.Preconditions.checkEquals;
import static ch.epfl.bbp.uima.utils.Preconditions.checkFileExists;
import static com.google.common.base.Preconditions.checkArgument;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

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
import ch.epfl.bbp.uima.utils.Preconditions;
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
public class OneDocPerLineReader3 extends JCasCollectionReader_ImplBase {

    @ConfigurationParameter(name = PARAM_INPUT_DIRECTORY, description = "path to directory")
    protected String inputDir;

    @ConfigurationParameter(name = PARAM_BETWEEN, description = "specifies a"
            + " range of pubmed_id, e.g. {13,17} --> 13 <= pubmed_id <= 17. "
            + "It is recommended to keep it under 1M, as these results are "
            + "all stored in the db memory")
    private int[] between;

    private int nextPmid, lastPmid;
    private File nextLine;

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {

        checkFileExists(inputDir);
        checkEquals(2, between.length);
        checkArgument(between[0] < between[1]);
        nextPmid = between[0];
        lastPmid = between[1];
    }

    public void getNext(JCas jcas) throws IOException, CollectionException {
        
        
//        String[] split = nextLine.split("\t");
//        Header header = new Header(jcas);
//        if (split.length == 2) {
//            header.setDocId(split[0]);
//            jcas.setDocumentText(split[1]);
//        } else {
//            header.setDocId(pmId++ + "_generated");
//            jcas.setDocumentText(nextLine);
//        }
//        header.addToIndexes();
    }

    public boolean hasNext() throws IOException, CollectionException {
        return hasNextLine();
    }

 
    private boolean hasNextLine() { // to deal with commented lines

//        if (textFileReader.hasNext()) {
//            nextLine = textFileReader.next();
//            if (nextLine.startsWith("#"))
//                return hasNextLine(); // recurse
//            else
//                return true;
//        }
        return false;
    }

    public Progress[] getProgress() {// nope
        return null;
    }
}
