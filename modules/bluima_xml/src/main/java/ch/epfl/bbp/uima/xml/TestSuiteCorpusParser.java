package ch.epfl.bbp.uima.xml;

import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.IOUtils;

import ch.epfl.bbp.uima.xml.testcase.TestSuiteCorpus;

public class TestSuiteCorpusParser {

    private JAXBContext jc;
    private Unmarshaller unmarshaller;

    private static JAXBContext jcSing = null;

    private JAXBContext getSingleton() throws JAXBException {
	if (jcSing == null)
	    jcSing = JAXBContext.newInstance(TestSuiteCorpus.class.getPackage()
		    .getName());
	return jcSing;
    }

    public TestSuiteCorpusParser() throws JAXBException {
	jc = getSingleton();
	unmarshaller = jc.createUnmarshaller();
    }

    public TestSuiteCorpus parse(InputStream is) throws JAXBException {
	try {
	    return (TestSuiteCorpus) unmarshaller.unmarshal(is);
	} finally {
	    IOUtils.closeQuietly(is);
	}
    }
}
