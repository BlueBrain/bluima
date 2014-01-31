package ch.epfl.bbp.uima.cr;

import static ch.epfl.bbp.uima.BlueUima.PARAM_FORMAT;
import static ch.epfl.bbp.uima.BlueUima.PARAM_INPUT_FILE;
import static ch.epfl.bbp.uima.BlueUima.PARAM_PATH_PREFIX;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.HEADER;
import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.io.FilenameUtils.removeExtension;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.fit.component.JCasCollectionReader_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Progress;

import ch.epfl.bbp.io.LineReader;
import de.julielab.jules.types.Header;

/**
 * Simply iterates a file list (provided as txt file), and delegates to an
 * {@link AnalysisEngine} to retrieve the actual file and process it.<br>
 * NOTE: NO {@link JCas}.documentText is set yet, the first
 * {@link AnalysisEngine} must do that
 * 
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability(outputs = { HEADER })
public class FromFilelistReader extends JCasCollectionReader_ImplBase {

    @ConfigurationParameter(name = PARAM_INPUT_FILE, //
    description = "path to a txt file containing a list of file paths, one on "
            + "each line. Each line is on the format {doc_id}\t{path}")
    protected String fileList;

    @ConfigurationParameter(name = PARAM_PATH_PREFIX, mandatory = false, //
    description = "prefix (=root path) to all files, leave it out for no prefix")
    protected String pathPrefix;

    @ConfigurationParameter(name = PARAM_FORMAT, mandatory = false, //
    description = "if true, then format is just the path, and the file name is the pubmed id")
    protected boolean formatWithImplicitPmId;

    private Iterator<String> lineIt;

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);
        try {
            checkArgument(new File(fileList).exists(), "no input file at "
                    + fileList);
            lineIt = new LineReader(new FileInputStream(fileList)).iterator();
        } catch (IOException e) {
            throw new ResourceInitializationException(e);
        }
    }

    @Override
    public void getNext(JCas jcas) throws IOException, CollectionException {

        String line = lineIt.next().trim(), docId = null, source = null;

        if (formatWithImplicitPmId) { // format: /a/simple/path/{pmid}.extension
            source = pathPrefix == null ? line : pathPrefix + line;
            docId = removeExtension(new File(line).getName());
        } else {
            String[] nextFile = line.split("\t");
            checkArgument(nextFile.length == 2,
                    "format is {doc_id}\\t{path}, found: '" + line + "'");
            docId = nextFile[0];
            source = pathPrefix == null ? nextFile[1] : pathPrefix
                    + nextFile[1];
        }

        checkArgument(new File(source).exists(), "no file at " + source);

        Header header = new Header(jcas);
        header.setDocId(docId);
        header.setSource(source);
        header.addToIndexes();
    }

    @Override
    public boolean hasNext() throws IOException, CollectionException {
        return lineIt.hasNext();
    }

    @Override
    public Progress[] getProgress() {// nope
        return null;
    }
}