package ch.epfl.bbp.nlp.ie.proteinconc.ae;

import static ch.epfl.bbp.uima.testutils.UimaTests.getTokenizedTestCas;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;
import static org.apache.uima.fit.util.JCasUtil.select;
import static org.junit.Assert.assertEquals;

import org.apache.uima.jcas.JCas;
import org.junit.Test;

import ch.epfl.bbp.uima.types.Figure;

public class FigureTitleAnnotatorTest {

	private void test(String sentence, String coveredText) throws Exception {

		JCas cas = getTokenizedTestCas(sentence);
		runPipeline(cas, createEngine(FigureTitleAnnotator.class));

		assertEquals("one figure must be annotated", 1,
				select(cas, Figure.class).size());
		assertEquals("bad covered text", select(cas, Figure.class).iterator()
				.next().getCoveredText(), coveredText);
	}

	@Test
	public void testWithAbr() throws Exception {
		test("As one can see on fig. 1, the horse has four legs.", "fig. 1");
		test("Fig. 2: a graph representing the correlation between the amount of chocolate consumed and the hour spent practising NLP.",
				"Fig. 2");
	}

	@Test
	public void testWithFull() throws Exception {
		test("Figure 3.4: hell yeah", "Figure 3.4");
		test("I like figure 5.6.4.33 because it is a picture.",
				"figure 5.6.4.33");
	}

}
