package ch.epfl.bbp.uima.integration_tests;

import org.apache.uima.cas.CAS;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.util.CasCreationUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.epfl.bbp.uima.pear.ComponentManager;

/**
 * @author renaud.richardet@epfl.ch
 */
public class CorporaIntegrationTest {
    protected static Logger LOG = LoggerFactory
	    .getLogger(AllComponentsIntegrationTest.class);

    @Test
    public void testGeniaCR() throws Exception {

	CollectionReader cr = ComponentManager
		.getCR("GeniaCorpusCollectionReader");

	int i = 0;
	while (cr.hasNext()) {
	    CAS cas = CasCreationUtils.createCas(cr
		    .getProcessingResourceMetaData());
	    cr.getNext(cas);

	    LOG.debug(cas.getJCas().getDocumentText());

	    if (++i > 10)
		break;
	}
	cr.close();
    }

    @Test
    public void testBiocreative2CR() throws Exception {

	CollectionReader cr = ComponentManager
		.getCR("Biocreative2GeneCollectionReader");

	int i = 0;
	while (cr.hasNext()) {
	    CAS cas = CasCreationUtils.createCas(cr
		    .getProcessingResourceMetaData());
	    cr.getNext(cas);

	    LOG.debug(cas.getJCas().getDocumentText());

	    if (++i > 10)
		break;
	}
	cr.close();
    }
}
