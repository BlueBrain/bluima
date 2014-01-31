package ch.epfl.bbp.uima.xml;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.epfl.bbp.uima.xml.archivearticle3.Article;

/**
 * Parses NXML archived articles from PMC (http://www.ncbi.nlm.nih.gov/pmc/)
 * 
 * @author renaud.richardet@epfl.ch
 * 
 * 
 *         TODO fix validation
 */
public class PmcNxmlParser {
    Logger LOG = LoggerFactory.getLogger(PmcNxmlParser.class);

    static {
	SAXParserFactory spf = SAXParserFactory.newInstance();
	try {
	    spf.setFeature(
		    "http://apache.org/xml/features/nonvalidating/load-external-dtd",
		    false);
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    private Unmarshaller unmarshaller;

    private static JAXBContext jcSingelton = null;

    /** Use singleton design pattern, since JAXBContext is expensive to create */
    private JAXBContext getSingleton() throws JAXBException {
	if (jcSingelton == null)
	    jcSingelton = JAXBContext.newInstance(Article.class.getPackage()
		    .getName());
	return jcSingelton;
    }

    public PmcNxmlParser() throws JAXBException {
	unmarshaller = getSingleton().createUnmarshaller();

//	final SchemaFactory schemaFactory = SchemaFactory
//		.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
//	try {
//	    String schemaLocation = "dummies/archivearticle3.xsd";
//	    URI uri = ch.epfl.bbp.uima.xml.pubmed.ObjectFactory.class
//		    .getClassLoader().getResource(schemaLocation).toURI();
//	    String schemaLocation2 = "dummies/archivearticle3.dtd";
//	    URI uri2 = ch.epfl.bbp.uima.xml.pubmed.ObjectFactory.class
//		    .getClassLoader().getResource(schemaLocation2).toURI();
//	    String schemaLocation3 = "dummies/archivearticle.dtd";
//	    URI uri3 = ch.epfl.bbp.uima.xml.pubmed.ObjectFactory.class
//		    .getClassLoader().getResource(schemaLocation3).toURI();
//	    LOG.debug("using xsd schema at " + uri.toString());
//	    final Schema s = schemaFactory.newSchema(new Source[] {
//	    // new StreamSource(uri.toString()),
//	    // new StreamSource(uri2.toString()),
//	    new StreamSource(uri3.toString()) });
//	    unmarshaller.setSchema(s);
//
//	} catch (Exception e) {
//	    throw new RuntimeException(e);
//	}

	// try { // FIXME
	//
	// Source ss1 = new StreamSource(
	// "http://dtd.nlm.nih.gov/archiving/3.0/xsd/archivearticle3.xsd");
	// Source ss2 = new StreamSource(
	// "http://dtd.nlm.nih.gov/archiving/2.0/archivearticle.dtd");
	// Source ss3 = new StreamSource(
	// "http://dtd.nlm.nih.gov/archiving/3.0/archivearticle3.dtd");
	// SchemaFactory xsdFactory = SchemaFactory
	// .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
	// Schema schema = xsdFactory
	// .newSchema(new Source[] { ss1, ss2, ss3 });
	//
	// unmarshaller.setSchema(schema);
	// } catch (Exception e) {
	// throw new RuntimeException(e);
	// }

	// unmarshaller.setSchema(null);// disables validation, IMPORTANT
	// unmarshaller.setEventHandler(new IgnoringValidationEventHandler());
    }

    public Article parse(InputStream is) throws FileNotFoundException,
	    JAXBException {
	try {
	    return (Article) unmarshaller.unmarshal(is);
	} finally {
	    IOUtils.closeQuietly(is);
	}
    }

    public class IgnoringValidationEventHandler implements
	    ValidationEventHandler {

	public boolean handleEvent(ValidationEvent event) {
	    return true;
	}

    }
}