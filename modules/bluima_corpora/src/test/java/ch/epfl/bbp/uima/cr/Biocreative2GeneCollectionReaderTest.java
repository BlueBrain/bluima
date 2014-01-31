package ch.epfl.bbp.uima.cr;

import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.Iterator;

import org.apache.uima.cas.CAS;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.util.CasCreationUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.util.JCasUtil;

import ch.epfl.bbp.Progress;
import ch.epfl.bbp.uima.BlueCasUtil;
import ch.epfl.bbp.uima.BlueUima;
import ch.epfl.bbp.uima.typesystem.To;
import ch.epfl.bbp.uima.typesystem.TypeSystem;
import de.julielab.jules.types.BioEntityMention;
import de.julielab.jules.types.Header;

/**
 * @author renaud.richardet@epfl.ch
 */
public class Biocreative2GeneCollectionReaderTest {
    Logger LOG = LoggerFactory
	    .getLogger(Biocreative2GeneCollectionReaderTest.class);

    /**
     * <pre>
     * GENE.eval
     * P00001606T0076|14 33|alkaline phosphatases
     * P00001606T0076|37 50|5-nucleotidase
     * 
     * train.in:
     * P00001606T0076 Comparison with alkaline phosphatases and 5-nucleotidase
     * </pre>
     */
    @Test
    public void testTrainCorpus() throws Exception {

	CollectionReader cr = CollectionReaderFactory.createReader(
		Biocreative2GeneCollectionReader.class, TypeSystem.JULIE_TSD,
		BlueUima.PARAM_MODE, "train");

	CAS cas = CasCreationUtils
		.createCas(cr.getProcessingResourceMetaData());
	cr.getNext(cas);

	Collection<BioEntityMention> genes = JCasUtil.select(cas.getJCas(),
		BioEntityMention.class);
	assertEquals(2, genes.size());
	Iterator<BioEntityMention> iterator = genes.iterator();
	BioEntityMention gene = iterator.next();
	assertEquals("alkaline phosphatases", gene.getCoveredText());
	gene = iterator.next();
	assertEquals("5-nucleotidase", gene.getCoveredText());

	// move to 'P00027739T0000 Serum gamma glutamyltransferase in the
	// diagnosis of liver disease in cattle.' to test ALTGENE annotations
	for (int i = 0; i < 11; i++) {
	    cas = CasCreationUtils
		    .createCas(cr.getProcessingResourceMetaData());
	    cr.hasNext();
	    cr.getNext(cas);
	    Header header = JCasUtil.selectSingle(cas.getJCas(), Header.class);
	    LOG.debug("docid:{}, text:{}", header.getDocId(),
		    cas.getDocumentText());
	}

	genes = JCasUtil.select(cas.getJCas(), BioEntityMention.class);
	iterator = genes.iterator();

	// check the 2 alternate forms
	assertEquals(2, genes.size());
	gene = iterator.next();
	LOG.debug(gene.getCoveredText());
	assertEquals("Serum gamma glutamyltransferase", gene.getCoveredText());
	gene = iterator.next();
	LOG.debug(gene.getCoveredText());
	assertEquals("gamma glutamyltransferase", gene.getCoveredText());
    }

    @Test
    @Ignore
    // takes way too long
    public void testAnnotations() throws Exception {

	CollectionReader cr = CollectionReaderFactory.createReader(
		Biocreative2GeneCollectionReader.class, TypeSystem.JULIE_TSD,
		BlueUima.PARAM_MODE, "train");

	Progress.reinit();
	while (cr.hasNext()) {
	    CAS cas = CasCreationUtils.createCas(cr
		    .getProcessingResourceMetaData());
	    cr.getNext(cas);
	    Progress.tick();
	    if (BlueCasUtil.getHeaderDocId(cas.getJCas()).equals(
		    "P09398332A0732")) {

		Collection<BioEntityMention> genes = JCasUtil.select(
			cas.getJCas(), BioEntityMention.class);
		LOG.debug(To.string(genes));
		assertEquals(5, genes.size());
	    }
	    if (BlueCasUtil.getHeaderDocId(cas.getJCas()).equals(
		    "P09417870A0105")) {

		Collection<BioEntityMention> genes = JCasUtil.select(
			cas.getJCas(), BioEntityMention.class);
		LOG.debug(To.string(genes));
		assertEquals(1, genes.size());
	    }
	}
	cr.close();
    }

    @Test
    public void testTestCorpus() throws Exception {

	CollectionReader cr = CollectionReaderFactory.createReader(
		Biocreative2GeneCollectionReader.class, TypeSystem.JULIE_TSD,
		BlueUima.PARAM_MODE, "test");

	CAS cas = CasCreationUtils
		.createCas(cr.getProcessingResourceMetaData());
	cr.getNext(cas);

	Collection<BioEntityMention> genes = JCasUtil.select(cas.getJCas(),
		BioEntityMention.class);
	assertEquals(2, genes.size());

	cr.close();
    }
}
