package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.BlueUima.PARAM_ANNOTATION_CLASSES;
import static ch.epfl.bbp.uima.testutils.UimaTests.getTestCas;
import static org.junit.Assert.assertEquals;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;
import static org.apache.uima.fit.util.JCasUtil.select;

import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.jcas.JCas;
import org.junit.Test;

import de.julielab.jules.types.Token;

public class DeduplicatorAnnotatorTest {

	@Test
	public void test() throws Exception {

		AnalysisEngineDescription dedup = createEngineDescription(
				DeduplicatorAnnotator.class, PARAM_ANNOTATION_CLASSES,
				new String[] { Token.class.getName() });

		JCas jCas = getTestCas("aaaaabbbbbccccc");

		Token t = new Token(jCas, 5, 10);
		t.addToIndexes();
		runPipeline(jCas, dedup);
		assertEquals("added first annot", 1, select(jCas, Token.class).size());

		Token t2 = new Token(jCas, 5, 10);
		t2.addToIndexes();
		Token t3 = new Token(jCas, 5, 10);
		t3.addToIndexes();
		Token t4 = new Token(jCas, 5, 10);
		t4.addToIndexes();
		runPipeline(jCas, dedup);
		assertEquals("should still be one, because of dedupe", 1,
				select(jCas, Token.class).size());

		Token t5 = new Token(jCas, 6, 10);
		runPipeline(jCas, dedup);
		t5.addToIndexes();

		assertEquals("now should be 2, since they do not overlap", 2,
				select(jCas, Token.class).size());
	}
}
