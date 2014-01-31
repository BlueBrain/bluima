package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.testutils.UimaTests.getTokenizedTestCas;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.util.Iterator;

import org.apache.uima.jcas.JCas;
import org.junit.Test;

import ch.epfl.bbp.uima.types.GeneralEnglish;

public class GeneralEnglishAnnotatorTest {

	@Test
	public void test() throws Exception {

		JCas jcas = getTokenizedTestCas("he walks around the moon");
		runPipeline(jcas, createEngine(GeneralEnglishAnnotator.class));

		Iterator<GeneralEnglish> it = select(jcas, GeneralEnglish.class)
				.iterator();

		GeneralEnglish next = it.next();
		assertEquals("he", next.getCoveredText());

		next = it.next();
		assertEquals("the", next.getCoveredText());

		next = it.next();
		assertEquals("moon", next.getCoveredText());

		assertFalse(it.hasNext());
	}
}
