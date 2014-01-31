package ch.epfl.bbp.uima.cr;

import static org.junit.Assert.assertEquals;

import org.apache.uima.cas.CAS;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.util.CasCreationUtils;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author renaud.richardet@epfl.ch
 * 
 */
public class PubmedCentralCollectionReaderTest {

    @Test
    @Ignore
    // FIXME
    public void testCount() throws Exception {

	CollectionReader cr = PubmedCentralCollectionReader.getCR("pmc_test_archive");

	int i = 0;
	while (cr.hasNext()) {
	    CAS cas = CasCreationUtils.createCas(cr
		    .getProcessingResourceMetaData());
	    cr.getNext(cas);
	    i++;
	}
	cr.close();
	assertEquals(6, i);
    }
}
