package org.apache.uima.ruta.engine;

import static ch.epfl.bbp.uima.BlueUima.BLUE_UTILS_TEST_BASE;
import static ch.epfl.bbp.uima.testutils.UimaTests.assertContains;
import static ch.epfl.bbp.uima.testutils.UimaTests.getTestCas;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;
import static org.apache.uima.ruta.engine.RutaEngine.PARAM_MAIN_SCRIPT;
import static org.apache.uima.ruta.engine.RutaEngine.PARAM_RESOURCE_PATHS;
import static org.apache.uima.ruta.engine.RutaEngine.PARAM_SCRIPT_PATHS;

import org.apache.uima.jcas.JCas;
import org.junit.Test;

import de.julielab.jules.types.Token;

public class RutaEngineTest {

	@Test
	public void testInline() throws Exception {

		JCas jCas = getTestCas("hello world");
		Ruta.apply(jCas.getCas(), "\"hello\"-> Token;");
		assertContains(jCas, Token.class, 1);
	}

	@Test
	public void testRutaEngine() throws Exception {

		JCas jCas = getTestCas("hello world");

		runPipeline(jCas, createEngine(RutaEngine.class, //
				PARAM_MAIN_SCRIPT, "RutaEngineTest",//
				PARAM_SCRIPT_PATHS, BLUE_UTILS_TEST_BASE + "ruta/"));

		assertContains(jCas, Token.class, 1);
	}

	@Test
	public void testResources() throws Exception {

		JCas jCas = getTestCas("the cat jumps in the river");

		runPipeline(
				jCas,
				createEngine(RutaEngine.class, PARAM_MAIN_SCRIPT,
						"RutaResourcesTest", PARAM_SCRIPT_PATHS,
						BLUE_UTILS_TEST_BASE + "ruta/resourcesTest",
						PARAM_RESOURCE_PATHS, BLUE_UTILS_TEST_BASE
								+ "ruta/resourcesTest"));

		assertContains(jCas, Token.class, 3);
	}
}
