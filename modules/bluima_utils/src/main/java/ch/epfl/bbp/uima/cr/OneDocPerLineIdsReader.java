package ch.epfl.bbp.uima.cr;

import static ch.epfl.bbp.io.LineReader.asText;
import static ch.epfl.bbp.uima.BlueUima.PARAM_INPUT_FILE;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.HEADER;
import static ch.epfl.bbp.uima.utils.Preconditions.checkEquals;
import static ch.epfl.bbp.uima.utils.Preconditions.checkFileExists;
import static com.google.common.base.Preconditions.checkArgument;

import java.io.ByteArrayInputStream;
import java.io.File;
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
import ch.epfl.bbp.triechar.TrieId;
import de.julielab.jules.types.Header;

/**
 * {@link CollectionReader} that takes simple text files as input. Each line is
 * turned into a new {@link CAS}. Format is:<br>
 * <code>{docId}{id_word1}{id_word2}{id_word3}</code><br>
 * 
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability(outputs = { HEADER })
public class OneDocPerLineIdsReader extends JCasCollectionReader_ImplBase {

    @ConfigurationParameter(name = PARAM_INPUT_FILE, description = "path to the corpus file")
    protected String inputDir;

    public static final String PARAM_VOCABULARY_INPUT_FILE = "vocabularyInputFile";
    @ConfigurationParameter(name = PARAM_VOCABULARY_INPUT_FILE, mandatory = true)
    private String vocabularyInputFile;

    private int nextPmid, lastPmid;
    private String nextTxt = "";

    private Iterator<String> textFileReader;
    private TrieId vocab;

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {

        checkFileExists(inputDir);
        checkFileExists(vocabularyInputFile);
        try {
            vocab = TrieId.fromIdsFile(new FileInputStream(new File(
                    vocabularyInputFile)));
            textFileReader = new LineReader(new FileInputStream(inputDir))
                    .iterator();
        } catch (Exception e) {
            throw new ResourceInitializationException(e);
        }
    }

    public void getNext(JCas jcas) throws IOException, CollectionException {

        
        String line = textFileReader.next();
        
        ByteArrayInputStream io=null;
        
        
        Header header = new Header(jcas);
        header.setDocId("" + nextPmid);
        header.addToIndexes();

        jcas.setDocumentText(nextTxt);
    }

    public boolean hasNext() throws IOException, CollectionException {
        return textFileReader.hasNext();
    }

    public Progress[] getProgress() {// nope
        return null;
    }
}
