package ch.epfl.bbp.uima.validation;
import static ch.epfl.bbp.uima.BlueUima.PARAM_CORPUS_NAME;
import static ch.epfl.bbp.uima.BlueUima.PARAM_INPUT;
import static ch.epfl.bbp.uima.BlueUima.PARAM_MAX_NR_RESULTS;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.JULIE_TSD;
import static ch.epfl.bbp.uima.validation.CrossvalidationReader.PARAM_MODE_EVAL;
import static ch.epfl.bbp.uima.validation.CrossvalidationReader.PARAM_SLICE;
import static com.google.common.collect.Lists.newArrayList;
import static java.lang.System.currentTimeMillis;
import static org.apache.commons.lang.StringUtils.join;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReader;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.OperationalProperties;
import org.apache.uima.jcas.JCas;
import org.junit.Test;

import ch.epfl.bbp.uima.cr.TextArrayReader;

// because of instance fields:
@OperationalProperties(multipleDeploymentAllowed = false)
public class CrossvalidationTest extends JCasAnnotator_ImplBase {

	static List<String> testCollected = newArrayList();

	@Test
	public void test() throws Exception {

		// write corpus
		String corpusName = "CrossvalidationReaderTest_" + currentTimeMillis();

		List<String> testInput = newArrayList();
		for (int i = 0; i < 100; i++) {
			testInput.add(i + "_");
		}

		CollectionReader cr = createReader(TextArrayReader.class, JULIE_TSD,
				PARAM_INPUT, testInput.toArray(new String[testInput.size()]));

		runPipeline(cr,//
				createEngine(CrossvalidationWriter.class, //
						PARAM_CORPUS_NAME, corpusName));

		// retrieve
		CollectionReader cr2 = createReader(CrossvalidationReader.class,//
				JULIE_TSD,//
				PARAM_CORPUS_NAME, corpusName,//
				PARAM_MAX_NR_RESULTS, 100,//
				PARAM_SLICE, 1,//
				PARAM_MODE_EVAL, true);
		runPipeline(cr2, createEngine(CrossvalidationTest.class));
		assertEquals("should have 10 documents",
				"95_89_77_36_35_18_25_4_87_37_", join(testCollected, ""));
		testCollected.clear();

		// retrieve2
		CollectionReader cr3 = createReader(CrossvalidationReader.class,//
				JULIE_TSD,//
				PARAM_CORPUS_NAME, corpusName,//
				PARAM_MAX_NR_RESULTS, 100,//
				PARAM_SLICE, 1,//
				PARAM_MODE_EVAL, false);
		runPipeline(cr3, createEngine(CrossvalidationTest.class));
		assertEquals("should have 90 documents", 90, testCollected.size());
	}

	@Override
	public void process(JCas jCas) throws AnalysisEngineProcessException {
		testCollected.add(jCas.getDocumentText());
	}
}
