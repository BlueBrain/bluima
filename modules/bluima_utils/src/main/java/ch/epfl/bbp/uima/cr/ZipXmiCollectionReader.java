package ch.epfl.bbp.uima.cr;

import static ch.epfl.bbp.ResourceHelper.getFile;
import static ch.epfl.bbp.io.IOUtils.unzipUniqueFileAsStream;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.uima.UimaContext;
import org.apache.uima.cas.impl.XCASDeserializer;
import org.apache.uima.cas.impl.XmiCasDeserializer;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.xml.sax.SAXException;

import ch.epfl.bbp.uima.io.DirectoryIterator;

/**
 * A simple collection reader that reads CASes in as Zipped XMIs from a
 * directory in the filesystem.
 * 
 * @author renaud.richardet@epfl.ch
 */
public class ZipXmiCollectionReader extends AbstractFileReader {

    public static final String XMI = "XMI";
    public static final String XCAS = "XCAS";

    public static final String PARAM_XML_SCHEME = "xmlSchemeName";
    @ConfigurationParameter(name = PARAM_XML_SCHEME, defaultValue = "XMI", //
    description = "specifies the UIMA XML serialization scheme that should be used. "
            + "Valid values for this parameter are 'XMI' and 'XCAS'")
    private String xmlScheme;

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);
        try {
            // we duplicate the code from AbstractFileReader to add "zip"
            // filtering
            File dir = getFile(inputDir);
            fileIterator = DirectoryIterator.get(directoryIterator, dir, "zip",
                    isRecursive);

        } catch (Exception e) {
            throw new ResourceInitializationException(
                    ResourceInitializationException.NO_RESOURCE_FOR_PARAMETERS,
                    new Object[] { inputDir });
        }
    }

    @Override
    public void getNext(JCas jCas) throws IOException, CollectionException {
        File currentZipFile = fileIterator.next();
        deserialize(currentZipFile, jCas, xmlScheme);
    }

    public static void deserialize(File currentZipFile, JCas jCas,
            String _xmlScheme) throws IOException {

        InputStream inputStream = unzipUniqueFileAsStream(currentZipFile);
        try {
            if (_xmlScheme.equals(XMI))
                XmiCasDeserializer.deserialize(inputStream, jCas.getCas());
            else
                XCASDeserializer.deserialize(inputStream, jCas.getCas());
        } catch (SAXException e) {
            throw new IOException(e);
        } finally {
            inputStream.close();
        }
    }
}
