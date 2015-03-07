package ch.epfl.bbp.uima.mongo;

import static ch.epfl.bbp.uima.BlueCasUtil.asList;
import static ch.epfl.bbp.uima.BlueCasUtil.getHeaderIntDocId;
import static ch.epfl.bbp.uima.BlueCasUtil.setDocId;
import static ch.epfl.bbp.uima.BlueUima.PARAM_DB_CONNECTION;
import static ch.epfl.bbp.uima.testutils.UimaTests.getTestCas;
import static java.lang.Thread.sleep;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReader;
import static org.apache.uima.fit.util.JCasUtil.select;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.Collection;
import java.util.List;

import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.junit.Test;
import org.slf4j.Logger;

import ch.epfl.bbp.uima.ae.NaiveSentenceSplitterAnnotator;
import ch.epfl.bbp.uima.ae.WhitespaceTokenizerAnnotator;
import ch.epfl.bbp.uima.uimafit.JcasPipelineBuilder;
import ch.epfl.bbp.uima.uimafit.PipelineBuilder;
import de.julielab.jules.types.Token;

public class MongoCollectionRemoverTest {
	private static Logger LOG = getLogger(MongoCollectionRemoverTest.class);

	@Test
	public void test() throws Exception {

		String[] conn = MongoTest.getTestConn("MongoCollectionRemoverTest");
		LOG.debug("using conn {}", conn);

		LOG.debug("PUT SOME INITIAL DATA, WITH TOKENS");
		JCas jCas = getTestCas();
		setDocId(jCas, 17);
		PipelineBuilder pO = new JcasPipelineBuilder(jCas);
		pO.add(NaiveSentenceSplitterAnnotator.class);
		pO.add(WhitespaceTokenizerAnnotator.class);
		pO.add(MongoWriter.class, PARAM_DB_CONNECTION, conn);
		pO.process();
		sleep(1000);

		LOG.debug("GET AND VERIFIES IT HAS TOKEN ANNOT");
		List<JCas> l = asList(createReader(
				MongoCollectionReader.class,  PARAM_DB_CONNECTION,
				conn));
		assertEquals(1, l.size());
		jCas = l.get(0);
		Collection<Token> t = select(jCas, Token.class);
		assertEquals(24, t.size());

		LOG.debug("REMOVE ALL TOKEN ANNOT");
		MongoCollectionRemover.removeAnnotations(conn, Token.class.getName());

		LOG.debug("GET AGAIN AND TEST IF IT REMOVED ALL TOKENS");
		List<JCas> l2 = asList(createReader(
				MongoCollectionReader.class,  PARAM_DB_CONNECTION,
				conn));
		assertEquals(1, l2.size());
		jCas = l2.get(0);
		assertEquals(17, getHeaderIntDocId(jCas));
		assertFalse(JCasUtil.exists(jCas, Token.class));
	}
}
