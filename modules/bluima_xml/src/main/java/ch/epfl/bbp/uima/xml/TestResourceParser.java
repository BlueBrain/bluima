package ch.epfl.bbp.uima.xml;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import ch.epfl.bbp.uima.xml.testresources.UnitTests;

/**
 * Parses a General Resources used for Unit Tests
 * 
 * @see examples
 * @author renaud.richardet@epfl.ch
 */
public class TestResourceParser {
    Logger LOG = LoggerFactory.getLogger(TestResourceParser.class);

    private Unmarshaller unmarshaller;

    private static JAXBContext jcSingelton = null;

    /** Use singleton design pattern, since JAXBContext is expensive to create */
    private JAXBContext getSingleton() throws JAXBException {
	if (jcSingelton == null) {
	    ClassLoader classLoader = ch.epfl.bbp.uima.xml.genia.ObjectFactory.class
		    .getClassLoader();// TODO needed?
	    jcSingelton = JAXBContext.newInstance(UnitTests.class.getPackage()
		    .getName(), classLoader);
	}
	return jcSingelton;
    }

    public TestResourceParser() throws JAXBException, SAXException,
	    FileNotFoundException {
	unmarshaller = getSingleton().createUnmarshaller();

	final SchemaFactory schemaFactory = SchemaFactory
		.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
	try {
	    String schemaLocation = "test_resources/resource_unit_test.xsd";
	    URI uri = ch.epfl.bbp.uima.xml.genia.ObjectFactory.class
		    .getClassLoader().getResource(schemaLocation).toURI();
	    LOG.debug("using xsd schema at " + uri.toString());
	    final Schema s = schemaFactory
		    .newSchema(new Source[] { new StreamSource(uri.toString()) });
	    unmarshaller.setSchema(s);
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
    }

    public UnitTests parse(InputStream is) throws FileNotFoundException,
	    JAXBException {
	LOG.debug("parsing unit test");
	try {
	    return (UnitTests) unmarshaller.unmarshal(is);
	} finally {
	    IOUtils.closeQuietly(is);
	}
    }
}