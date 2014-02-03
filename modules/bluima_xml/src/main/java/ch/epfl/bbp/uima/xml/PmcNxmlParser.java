package ch.epfl.bbp.uima.xml;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXSource;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

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

	private Unmarshaller unmarshaller;

	private XMLReader xmlReader;

	private static JAXBContext jcSingelton = null;

	/** Use singleton design pattern, since JAXBContext is expensive to create */
	private JAXBContext getSingleton() throws JAXBException {
		if (jcSingelton == null)
			jcSingelton = JAXBContext.newInstance(Article.class.getPackage()
					.getName());
		return jcSingelton;
	}

	public PmcNxmlParser() throws JAXBException, ParserConfigurationException,
			SAXException {

		SAXParserFactory spf = SAXParserFactory.newInstance();
		// no validation error (and missing dtd files)
		spf.setFeature(
				"http://apache.org/xml/features/nonvalidating/load-external-dtd",
				false);
		spf.setFeature("http://xml.org/sax/features/validation", false);

		unmarshaller = getSingleton().createUnmarshaller();
		xmlReader = spf.newSAXParser().getXMLReader();
	}

	public Article parse(InputStream is) throws FileNotFoundException,
			JAXBException {
		try {
			InputSource inputSource = new InputSource(new InputStreamReader(is));
			SAXSource source = new SAXSource(xmlReader, inputSource);

			return (Article) unmarshaller.unmarshal(source);
		} finally {
			IOUtils.closeQuietly(is);
		}
	}

}