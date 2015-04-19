package ch.epfl.bbp.uima.units.regex;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Compares the text extracted by the measure regexes with a gold standard
 * (specified as a generic input-output testcase xml)
 * 
 * @author renaud.richardet@epfl.ch
 * @author joelle.portmann@epfl.ch
 */
public class ConcentrationRegexAnnotatorTest {

	@Test
	@Ignore
	// FIXME
	public void testWithXmlTestsuite() throws Exception {

//		JcasPipelineBuilder pipeline = new JcasPipelineBuilder();
//
//		pipeline.add(AnalysisEngineFactory
//				.createEngineDescription(
//						RegExAnnotator.class,
//						REGEX_CONCEPTS_FILES,
//						new String[] { "pear_resources/regex_concepts/concentrations.xml" }));
//      pipeline.add(AnalysisEngineFactory
//              .createEngineDescription(KeepLargestAnnotationAnnotator.class,
//                      PARAM_ANNOTATION_CLASS, Measure.class));
//
//		UnitTests tests = new TestResourceParser()
//				.parse(getInputStream("regex_concepts/concentrations_test.xml"));
//
//		for (Result result : tests.getResult()) {
//			MeasureRegexAnnotatorTest.testSingleResult(pipeline, result,
//					"Concentration");
//		}
	}
}
