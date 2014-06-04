package ch.epfl.bbp.uima.cr;

import static com.google.common.base.Preconditions.checkArgument;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.uima.UimaContext;
import org.apache.uima.cas.impl.XCASDeserializer;
import org.apache.uima.cas.impl.XmiCasDeserializer;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.xml.sax.SAXException;

/**
 * A simple collection reader that reads CASes in as XMIs from a directory in
 * the filesystem.
 * 
 * @author renaud.richardet@epfl.ch
 */
public class XCollectionReader extends AbstractFileReader {

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
        checkArgument(xmlScheme.equals(XMI) || xmlScheme.equals(XCAS));
        // overwrite filetype filter
        fileExtensionFilter = xmlScheme.toLowerCase();
        super.initialize(context);
    }

    @Override
    public void getNext(JCas jCas) throws IOException, CollectionException {
        File currentFile = fileIterator.next();
        FileInputStream inputStream = new FileInputStream(currentFile);
        try {
            if (xmlScheme.equals(XMI))
                XmiCasDeserializer.deserialize(inputStream, jCas.getCas());
            else
                XCASDeserializer.deserialize(inputStream, jCas.getCas());
        } catch (SAXException e) {
            throw new CollectionException(e);
        } finally {
            inputStream.close();
        }
    }
}
