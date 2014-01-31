package ch.epfl.bbp.uima.cr;

import static ch.epfl.bbp.ResourceHelper.getFile;
import static ch.epfl.bbp.uima.BlueUima.PARAM_INPUT_DIRECTORY;
import static ch.epfl.bbp.uima.utils.Preconditions.checkFileExists;
import static org.apache.uima.resource.ResourceInitializationException.NO_RESOURCE_FOR_PARAMETERS;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import org.apache.uima.UimaContext;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.fit.component.JCasCollectionReader_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Progress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.epfl.bbp.uima.BlueUima;
import ch.epfl.bbp.uima.io.DirectoryIterator;

/**
 * @author renaud.richardet@epfl.ch
 */
public abstract class AbstractFileReader extends JCasCollectionReader_ImplBase {
    protected static final Logger LOG = LoggerFactory
            .getLogger(AbstractFileReader.class);

    @ConfigurationParameter(name = PARAM_INPUT_DIRECTORY, //
    description = "path to a directory containing pdfs, or path to a zip "
            + "file containing pdfs (make sure it does not contain other file formats)")
    protected String inputDir;

    @ConfigurationParameter(name = BlueUima.PARAM_DIRECTORY_ITERATOR, defaultValue = "DefaultDirectoryIterator", //
    description = "the directoryIterator to use. E.g. DefaultDirectoryIterator, ZipDirectoryIterator, LargeDirectoryIterator")
    protected String directoryIterator;

    @ConfigurationParameter(name = BlueUima.PARAM_IS_RECURSIVE, defaultValue = "false")
    protected boolean isRecursive;

    @ConfigurationParameter(name = BlueUima.PARAM_FILE_EXTENSION_FILTER, defaultValue = "null", //
    description = "a filter on file extension")
    protected String fileExtensionFilter;

    protected Iterator<File> fileIterator;

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);

        try {
            File dir = getFile(inputDir);
            checkFileExists(dir);
            if (fileExtensionFilter.equals("null"))
                fileExtensionFilter = null; // duh
            fileIterator = DirectoryIterator.get(directoryIterator, dir,
                    fileExtensionFilter, isRecursive);

        } catch (FileNotFoundException e) {
            throw new ResourceInitializationException(
                    NO_RESOURCE_FOR_PARAMETERS, new Object[] { inputDir });
        } catch (IOException e) {
            throw new ResourceInitializationException(e);
        }
    }

    @Override
    public boolean hasNext() throws IOException, CollectionException {
        return fileIterator.hasNext();
    }

    @Override
    public Progress[] getProgress() {// nope
        return null;
    }
}
