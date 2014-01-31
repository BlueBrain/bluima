package ch.epfl.bbp.uima.db.cr;

import static ch.epfl.bbp.uima.typesystem.TypeSystem.JULIE_TSD;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReader;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;

import java.util.Collection;

import org.apache.uima.cas.CAS;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.util.CasCreationUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.util.JCasUtil;

import ch.epfl.bbp.uima.BlueUima;
import ch.epfl.bbp.uima.ae.StatsAnnotatorPlus;
import ch.epfl.bbp.uima.cr.PubmedWholeDatabaseCR;
import ch.epfl.bbp.uima.testutils.UimaTests;
import de.julielab.jules.types.pubmed.Header;

public class PubmedWholeDatabaseCRTest {

	@Test
	@Ignore
	public void test() throws Exception {

		CollectionReader cr = CollectionReaderFactory.createReader(
				PubmedWholeDatabaseCR.class, JULIE_TSD);

		CAS cas = CasCreationUtils
				.createCas(cr.getProcessingResourceMetaData());
		cr.getNext(cas);

		for (int i = 0; i < 10000; i++) {
			cr.hasNext();
			cas = CasCreationUtils
					.createCas(cr.getProcessingResourceMetaData());
			cr.getNext(cas);
			System.err.println(cas.getDocumentText());
		}

		// FIXME hangs up cr.close();

		Collection<Header> h = JCasUtil.select(cas.getJCas(), Header.class);
		UimaTests.assertResultsContains(h, "DocId", "1");
	}

	@Test
	@Ignore
	public void test2() throws Exception {

		String[] conn = { "localhost", "bb_pubmed", "root", "" };

		CollectionReader cr = createReader(
				PubmedWholeDatabaseCR.class, JULIE_TSD,
				BlueUima.PARAM_DB_CONNECTION, conn);

		runPipeline(cr, createEngine(StatsAnnotatorPlus.class, StatsAnnotatorPlus.PARAM_PRINT_EVERY, 100000));
	}

}
