package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.ae.EvaluationPreprocessorAnnotator.PARAM_GOLD_ANNOTATION;
import static ch.epfl.bbp.uima.ae.EvaluationPreprocessorAnnotator.PARAM_SYSTEM_ANNOTATION;
import static ch.epfl.bbp.uima.testutils.UimaTests.getTestCas;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;

import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.jcas.JCas;
import org.junit.Test;
import org.apache.uima.fit.pipeline.SimplePipeline;

import ch.epfl.bbp.uima.testutils.UimaTests;
import ch.epfl.bbp.uima.types.DictTerm;
import ch.epfl.bbp.uima.types.Protein;

public class EvaluationAnnotatorTest {

	@Test
	public void test() throws Exception {

		JCas jCas = getTestCas("a b c d");

		// gold
		Protein p = new Protein(jCas, 0, 1);
		p.addToIndexes();
		Protein p2 = new Protein(jCas, 1, 3);
		p2.addToIndexes();

		// system
		DictTerm d = new DictTerm(jCas, 0, 1);
		d.addToIndexes();
		DictTerm d2 = new DictTerm(jCas, 2, 3);
		d2.addToIndexes();

		AnalysisEngineDescription ep = createEngineDescription(
				EvaluationPreprocessorAnnotator.class, PARAM_GOLD_ANNOTATION,
				Protein.class.getName());
		AnalysisEngineDescription e = createEngineDescription(
				EvaluationAnnotator.class, PARAM_GOLD_ANNOTATION,
				Protein.class.getName(), PARAM_SYSTEM_ANNOTATION,
				DictTerm.class.getName());

		runPipeline(jCas, ep, e);
	}
}
