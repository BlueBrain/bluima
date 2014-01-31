package ch.epfl.bbp.uima.mongo;

import static ch.epfl.bbp.uima.BlueCasUtil.asList;
import static ch.epfl.bbp.uima.BlueUima.PARAM_DB_CONNECTION;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.JULIE_TSD;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReader;
import static org.junit.Assert.assertEquals;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.Collection;
import java.util.List;

import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.junit.Test;
import org.slf4j.Logger;

import ch.epfl.bbp.uima.BlueCasUtil;
import ch.epfl.bbp.uima.BlueUima;
import ch.epfl.bbp.uima.ae.NaiveSentenceSplitterAnnotator;
import ch.epfl.bbp.uima.ae.WhitespaceTokenizerAnnotator;
import ch.epfl.bbp.uima.testutils.UimaTests;
import ch.epfl.bbp.uima.uimafit.JcasPipelineBuilder;
import ch.epfl.bbp.uima.uimafit.PipelineBuilder;
import de.julielab.jules.types.Sentence;
import de.julielab.jules.types.Token;

public class MongoPipelineBuilderTest {
	private static Logger LOG = getLogger(MongoPipelineBuilderTest.class);

	@SuppressWarnings("unchecked")
	@Test
	public void test() throws Exception {

		String[] conn = MongoTest.getTestConn("MongoPipelineBuilderTest");
		LOG.debug("using conn {}", conn);

		LOG.debug("PUT SOME INITIAL DATA");
		JCas jCas = UimaTests.getTestCas();
		BlueCasUtil.setDocId(jCas, 17);

		PipelineBuilder pO = new JcasPipelineBuilder(jCas);
		pO.add(MongoWriter.class, BlueUima.PARAM_DB_CONNECTION, conn);
		pO.process();

		Thread.sleep(1000);

		LOG.debug("ADD MORE INCREMETAL DATA: Sentence");
		PipelineBuilder p1 = new MongoPipelineBuilder(2, conn, Sentence.class);
		p1.add(NaiveSentenceSplitterAnnotator.class);
		p1.process();

		Thread.sleep(2000);

		LOG.debug("ADD MORE INCREMETAL DATA: Tokens");
		PipelineBuilder p2 = new MongoPipelineBuilder(2, conn, Token.class);
		p2.add(WhitespaceTokenizerAnnotator.class);
		p2.process();

		Thread.sleep(2000);

		LOG.debug("GET AGAIN AND SEE IF INCREMENTAL WORKED");
		List<JCas> l = asList(createReader(
				MongoCollectionReader.class, JULIE_TSD, PARAM_DB_CONNECTION,
				conn));
		assertEquals(1, l.size());

		jCas = l.get(0);
		assertEquals(17, BlueCasUtil.getHeaderIntDocId(jCas));

		Collection<Token> t = JCasUtil.select(jCas, Token.class);
		assertEquals(24, t.size());
	}
}
