package ch.epfl.bbp.uima.validation;

import static ch.epfl.bbp.uima.testutils.UimaTests.getTestCas;
import static org.junit.Assert.assertTrue;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.junit.Test;

public class TestEvaluatorTest {

	@Test
	public void test() throws Exception {

		TestEvaluator<Annotation, Annotation> evaluator = TestEvaluator
		        .getAtLeastCoveredEvaluator();

		JCas jCas = getTestCas();
		Annotation expected = new Annotation(jCas, 10, 20);
		Annotation actual = new Annotation(jCas, 10, 20);

		assertTrue(evaluator.getComparator().areTheSame(expected, actual));

		jCas = getTestCas();
		expected = new Annotation(jCas, 10, 20);
		actual = new Annotation(jCas, 5, 20);
		
		assertTrue(evaluator.getComparator().areTheSame(expected, actual));

		jCas = getTestCas();
		expected = new Annotation(jCas, 10, 20);
		actual = new Annotation(jCas, 10, 25);
		
		assertTrue(evaluator.getComparator().areTheSame(expected, actual));
	}
}
