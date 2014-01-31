package ch.epfl.bbp.uima.xml;

import java.io.IOException;
import java.io.InputStream;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class DtdEntityResolver implements EntityResolver {

    private String dtdPath;

    public DtdEntityResolver(String dtdPath) {
	this.dtdPath = dtdPath;
    }

    public InputSource resolveEntity(String publicId, String systemId)
	    throws SAXException, IOException {
	InputStream dtd = getClass().getClassLoader().getResourceAsStream(
		dtdPath);
	return new InputSource(dtd);
    }

}