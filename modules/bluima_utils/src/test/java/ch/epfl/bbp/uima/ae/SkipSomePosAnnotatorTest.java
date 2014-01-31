package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.testutils.UimaTests.getTestCas;
import static org.junit.Assert.assertEquals;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;
import static org.apache.uima.fit.util.JCasUtil.select;

import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.jcas.JCas;
import org.junit.Test;

import ch.epfl.bbp.uima.ae.cleanup.SkipSomePosAnnotator2;
import ch.epfl.bbp.uima.types.Protein;
import de.julielab.jules.types.Annotation;
import de.julielab.jules.types.Token;

public class SkipSomePosAnnotatorTest {

	@Test
	public void test() throws Exception {

		AnalysisEngineDescription skip = createEngineDescription(SkipSomePosAnnotator2.class);

		JCas jCas = getTestCas("aaaaabbbbbccccc");

		Token t = new Token(jCas, 5, 10);
		t.setPos("DT"); // <-- will be skipped!
		t.addToIndexes();
		runPipeline(jCas, skip);
		// selecting for julielab.Annotation!
		assertEquals("added first annot", 1, select(jCas, Annotation.class)
				.size());

		Protein p = new Protein(jCas, 5, 10);
		p.addToIndexes();
		runPipeline(jCas, skip);
		assertEquals("should still be one, because of skipping", 1,
				select(jCas, Annotation.class).size());

		// MAYBE
		// Protein p2 = new Protein(jCas, 6, 10);
		// p2.addToIndexes();
		// runPipeline(jCas, skip);
		// assertEquals("again, should still be one, because of skipping", 1,
		// select(jCas, Annotation.class).size());

		Protein p3 = new Protein(jCas, 2, 10);
		p3.addToIndexes();
		runPipeline(jCas, skip);
		assertEquals("now should be 2, since this is a larger annot", 2,
				select(jCas, Annotation.class).size());
	}

}
