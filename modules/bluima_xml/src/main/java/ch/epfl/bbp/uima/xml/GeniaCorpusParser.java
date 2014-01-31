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

import ch.epfl.bbp.uima.xml.genia.Set;

/**
 * Parses the GENIA Corpus (GENIAcorpus3.02), available from
 * http://www-tsujii.is.s.u-tokyo.ac.jp/~genia/topics/Corpus/
 * 
 * @author renaud.richardet@epfl.ch
 * 
 */
public class GeniaCorpusParser {
    Logger LOG = LoggerFactory.getLogger(GeniaCorpusParser.class);

    private Unmarshaller unmarshaller;

    private static JAXBContext jcSingelton = null;

    /** Use singleton design pattern, since JAXBContext is expensive to create */
    private JAXBContext getSingleton() throws JAXBException {
	if (jcSingelton == null) {
	    ClassLoader classLoader = ch.epfl.bbp.uima.xml.genia.ObjectFactory.class
		    .getClassLoader();// TODO needed?
	    jcSingelton = JAXBContext.newInstance(Set.class.getPackage()
		    .getName(), classLoader);
	}
	return jcSingelton;
    }

    public GeniaCorpusParser() throws JAXBException, SAXException,
	    FileNotFoundException {
	unmarshaller = getSingleton().createUnmarshaller();

	final SchemaFactory schemaFactory = SchemaFactory
		.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
	try {
	    String schemaLocation = "GENIAcorpus3.02/gpml.xsd";
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

    public Set parse(InputStream is) throws FileNotFoundException,
	    JAXBException {
	LOG.debug("parsing genia");
	try {
	    return (Set) unmarshaller.unmarshal(is);
	} finally {
	    IOUtils.closeQuietly(is);
	}
    }
}