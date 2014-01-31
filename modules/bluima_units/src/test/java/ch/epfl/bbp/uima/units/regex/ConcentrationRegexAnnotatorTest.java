package ch.epfl.bbp.uima.units.regex;

import static ch.epfl.bbp.ResourceHelper.getInputStream;
import static org.apache.uima.annotator.regex.impl.RegExAnnotator.REGEX_CONCEPTS_FILES;

import org.apache.uima.annotator.regex.impl.RegExAnnotator;
import org.junit.Ignore;
import org.junit.Test;
import org.apache.uima.fit.factory.AnalysisEngineFactory;

import ch.epfl.bbp.uima.ae.KeepLargestAnnotationAnnotator;
import ch.epfl.bbp.uima.uimafit.JcasPipelineBuilder;
import ch.epfl.bbp.uima.xml.TestResourceParser;
import ch.epfl.bbp.uima.xml.testresources.Result;
import ch.epfl.bbp.uima.xml.testresources.UnitTests;

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
//		pipeline.add(PruneMeasuresAnnotator.class);
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
