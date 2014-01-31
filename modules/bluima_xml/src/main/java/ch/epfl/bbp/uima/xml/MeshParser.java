package ch.epfl.bbp.uima.xml;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.epfl.bbp.uima.xml.mesh.DescriptorRecordSet;

/**
 * Parses <a href="http://www.ncbi.nlm.nih.gov/mesh">Mesh</a>
 * 
 * @author renaud.richardet@epfl.ch
 * 
 *         TODO fix validation, use IS instead of files
 * 
 */
public class MeshParser {
    Logger LOG = LoggerFactory.getLogger(MeshParser.class);

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
	    jcSingelton = JAXBContext.newInstance(DescriptorRecordSet.class
		    .getPackage().getName());
	return jcSingelton;
    }

    public MeshParser() throws JAXBException {
	unmarshaller = getSingleton().createUnmarshaller();
	// FIXME http://stackoverflow.com/a/3587647/125617
	// JAXBHelper.getUnmarshaller(unmarshaller).getXMLUnmarshaller().setEntityResolver(new
	// DtdEntityResolver());
	unmarshaller.setSchema(null);// disables validation, IMPORTANT

    }

    public DescriptorRecordSet parse(File f) throws FileNotFoundException,
	    JAXBException {
	LOG.debug("parsing nxml article for file " + f);
	BufferedInputStream fis = new BufferedInputStream(
		new FileInputStream(f));
	return parse(fis);
    }

    public DescriptorRecordSet parse(InputStream is)
	    throws FileNotFoundException, JAXBException {
	try {
	    return (DescriptorRecordSet) unmarshaller.unmarshal(is);
	} finally {
	    IOUtils.closeQuietly(is);
	}
    }
}