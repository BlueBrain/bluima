package ch.epfl.bbp.uima.cr;

import static ch.epfl.bbp.uima.BlueUima.PARAM_INPUT_FILE;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.HEADER;

import java.io.File;
import java.io.IOException;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.util.Progress;
import org.apache.uima.fit.component.JCasCollectionReader_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.TypeCapability;

import de.julielab.jules.types.Header;

/**
 * Simply reads a single file, and delegates to an {@link AnalysisEngine} to
 * retrieve the actual file and process it.<br>
 * NOTE: no {@link JCas}.documentText is set yet, the first
 * {@link AnalysisEngine} must do that
 * 
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability(outputs = { HEADER })
public class SingleFileReader extends JCasCollectionReader_ImplBase {

    @ConfigurationParameter(name = PARAM_INPUT_FILE, description = "path to file", mandatory = true)
    private String inputFile;

    /** only true for one doc, then false */
    private boolean hasNext = true;

    @Override
    public void getNext(JCas jcas) throws IOException, CollectionException {

        hasNext = false;

        File f = new File(inputFile);
        if (!f.exists())
            throw new IOException("no file at " + inputFile);

        Header header = new Header(jcas);
        // .* removes the tmp file
        header.setDocId(f.getName().replaceAll("\\.pdf.*", "")
                .replaceAll("\\.zip.*", ""));
        header.setSource(f.getAbsolutePath());
        header.addToIndexes();
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
