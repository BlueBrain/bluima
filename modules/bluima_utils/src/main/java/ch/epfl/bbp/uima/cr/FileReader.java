package ch.epfl.bbp.uima.cr;

import static ch.epfl.bbp.uima.typesystem.TypeSystem.HEADER;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReader;

import java.io.File;
import java.io.IOException;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import ch.epfl.bbp.uima.BlueUima;
import de.julielab.jules.types.Header;

/**
 * Simply iterates a directory, and delegates to an {@link AnalysisEngine} to
 * retrieve the actual file and process it.<br>
 * NOTE: no {@link JCas}.documentText is set yet, the first
 * {@link AnalysisEngine} must do that
 * 
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability(outputs = { HEADER })
public class FileReader extends AbstractFileReader {

    public static final String COMPONENT_ID = FileReader.class.getName();

    @Override
    public void getNext(JCas jcas) throws IOException, CollectionException {
        File f = fileIterator.next();
        Header header = new Header(jcas);
        // .* removes the tmp file
        header.setDocId(f.getName().replaceAll("\\.pdf.*", "")
                .replaceAll("\\.zip.*", ""));
        header.setSource(f.getAbsolutePath());
        header.addToIndexes();
    }

    public static CollectionReader getCR(String inputDir)
            throws ResourceInitializationException {
        return createReader(FileReader.class, 
                BlueUima.PARAM_INPUT_DIRECTORY, inputDir);
    }
}
