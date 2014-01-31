package ch.epfl.bbp.uima.cr;

import static org.junit.Assert.assertEquals;

import org.apache.uima.cas.CAS;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.util.CasCreationUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.uima.fit.factory.CollectionReaderFactory;

import ch.epfl.bbp.uima.BlueUima;
import ch.epfl.bbp.uima.typesystem.To;
import ch.epfl.bbp.uima.typesystem.TypeSystem;

/**
 * @author renaud.richardet@epfl.ch
 */
public class BioNLPGeniaEventsReaderTest {
    private static Logger LOG = LoggerFactory
	    .getLogger(BioNLPGeniaEventsReaderTest.class);

    final static String TEST_DIR = "BioNLP-ST_2011/BioNLP-ST_2011_genia_testsample/";

    @Test
    public void testCount() throws Exception {

	CollectionReader cr = CollectionReaderFactory.createReader(
		BioNLPGeniaEventsCollectionReader.class, TypeSystem.JULIE_TSD,
		BlueUima.PARAM_INPUT_DIRECTORY, TEST_DIR);

	int i = 0;
	while (cr.hasNext()) {
	    CAS cas = CasCreationUtils.createCas(cr
		    .getProcessingResourceMetaData());
	    cr.getNext(cas);
	    LOG.debug(To.string("cas nr " + i, cas.getJCas()));
	    i++;
	}
	cr.close();
	assertEquals(3, i);
    }
}
