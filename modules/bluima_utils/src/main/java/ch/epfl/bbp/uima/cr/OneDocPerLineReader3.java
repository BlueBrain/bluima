package ch.epfl.bbp.uima.cr;

import static ch.epfl.bbp.io.LineReader.asText;
import static ch.epfl.bbp.uima.BlueUima.PARAM_BETWEEN;
import static ch.epfl.bbp.uima.BlueUima.PARAM_INPUT_DIRECTORY;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.HEADER;
import static ch.epfl.bbp.uima.utils.Preconditions.checkEquals;
import static ch.epfl.bbp.uima.utils.Preconditions.checkFileExists;
import static com.google.common.base.Preconditions.checkArgument;

import java.io.File;
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

import de.julielab.jules.types.Header;

/**
 * {@link CollectionReader} that takes simple text files as input. Each file is
 * turned into a new {@link CAS}. Format is:<br>
 * <code>docId{tab}text</code><br>
 * 
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
    private String nextTxt = "";

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {

        checkFileExists(inputDir);
        checkEquals(2, between.length);
        checkArgument(between[0] < between[1]);
        nextPmid = between[0] - 1; // -1 since we increase it in hasNextDoc()
        lastPmid = between[1];
    }

    public void getNext(JCas jcas) throws IOException, CollectionException {

        Header header = new Header(jcas);
        header.setDocId("" + nextPmid);
        header.addToIndexes();

        jcas.setDocumentText(nextTxt);
    }

    public boolean hasNext() throws IOException, CollectionException {

        while (true) { // file must exist and text not empty

            nextPmid++;
            if (nextPmid > lastPmid)
                return false;

            File nextFile = new File(inputDir + "/" + nextPmid + ".tsv");
            if (!nextFile.exists())
                continue;

            nextTxt = asText(nextFile).split("\t")[1];
            if (nextTxt.trim().length() == 0)
                continue;

            return true;
        }
    }

    public Progress[] getProgress() {// nope
        return null;
    }
}
