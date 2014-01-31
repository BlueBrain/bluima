package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.testutils.UimaTests.getTokenizedTestCas;
import static org.junit.Assert.assertEquals;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.util.Iterator;

import org.apache.uima.jcas.JCas;
import org.junit.Test;

import de.julielab.jules.types.Token;

public class SnowballAnnotatorTest {

	@Test
	public void test() throws Exception {

		JCas jcas = getTokenizedTestCas("cats eating fishes");

		runPipeline(jcas, createEngine(SnowballAnnotator.class));

		Iterator<Token> tokenIt = select(jcas, Token.class).iterator();

		Token next = tokenIt.next();
		assertEquals("cat", next.getLemma().getValue());

		next = tokenIt.next();
		assertEquals("eat", next.getLemma().getValue());

		next = tokenIt.next();
		assertEquals("fish", next.getLemma().getValue());
	}
}
