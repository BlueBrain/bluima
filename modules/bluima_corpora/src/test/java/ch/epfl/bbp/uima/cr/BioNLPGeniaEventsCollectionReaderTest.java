package ch.epfl.bbp.uima.cr;

import static org.junit.Assert.assertEquals;

import org.apache.uima.cas.CAS;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.util.CasCreationUtils;
import org.junit.Test;
import org.apache.uima.fit.factory.CollectionReaderFactory;

import ch.epfl.bbp.uima.typesystem.TypeSystem;

/**
 * @author renaud.richardet@epfl.ch
 * 
 */
public class BioNLPGeniaEventsCollectionReaderTest {

    @Test
    public void test() throws Exception {

	CollectionReader cr = CollectionReaderFactory.createReader(
		BioNLPGeniaEventsCollectionReader.class);

	int i = 0;
	while (cr.hasNext()) {
	    CAS cas = CasCreationUtils.createCas(cr
		    .getProcessingResourceMetaData());
	    cr.getNext(cas);

	    // if (createHtml)
	    // viewer.createHtml(cas.getJCas(), cas.getTypeSystem(),
	    // styleMapFile, new File("target/" + i));

	    i++;
	}
	cr.close();
	assertEquals(259, i);

    }

}
