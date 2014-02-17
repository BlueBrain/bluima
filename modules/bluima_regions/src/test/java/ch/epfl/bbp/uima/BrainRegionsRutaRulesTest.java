package ch.epfl.bbp.uima;

import static ch.epfl.bbp.uima.BlueUima.PARAM_ANNOTATION_CLASS;
import static ch.epfl.bbp.uima.BlueUima.PARAM_ANNOTATION_CLASSES;
import static ch.epfl.bbp.uima.LexicaHelper.getConceptMapper;
import static ch.epfl.bbp.uima.ae.OpenNlpHelper.getSentenceSplitter;
import static ch.epfl.bbp.uima.ae.OpenNlpHelper.getTokenizer;
import static ch.epfl.bbp.uima.testutils.UimaTests.assertContains;
import static ch.epfl.bbp.uima.testutils.UimaTests.getTestCas;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;
import static org.apache.uima.fit.util.JCasUtil.select;
import static org.slf4j.LoggerFactory.getLogger;

import java.io.IOException;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.junit.Test;
import org.slf4j.Logger;
import org.xml.sax.SAXException;

import ch.epfl.bbp.uima.ae.DeduplicatorAnnotator;
import ch.epfl.bbp.uima.ae.KeepLargestAnnotationAnnotator;
import ch.epfl.bbp.uima.ae.PostprocessRutaEngine;
import ch.epfl.bbp.uima.types.BRCooc;
import ch.epfl.bbp.uima.types.BrainRegionChunk;
import ch.epfl.bbp.uima.types.BrainRegionDictTerm;
import ch.epfl.bbp.uima.types.Cooccurrence;
import ch.epfl.bbp.uima.typesystem.To;

public class BrainRegionsRutaRulesTest {
	private static Logger LOG = getLogger(BrainRegionsRutaRulesTest.class);

	@Test
	public void testChunks() throws Exception {

		JCas jCas = run("the brainstem, subthalamic nucleus and pedunculopontine tegmental nucleus.");

		assertContains(jCas, BrainRegionDictTerm.class, 3);
		assertContains(jCas, BrainRegionChunk.class, 1);
		assertContains(jCas, Cooccurrence.class, 0);
	}

	@Test
	public void testCoocs() throws Exception {

		JCas jCas = run("Magnocellular nucleus of the thalamus projection to neocortex, brainstem and olfactory bulb.");

		assertContains(jCas, BrainRegionDictTerm.class, 5);
		assertContains(jCas, BrainRegionChunk.class, 2);
		assertContains(jCas, Cooccurrence.class, 6);
	}

	private JCas run(String txt) throws AnalysisEngineProcessException,
			ResourceInitializationException, UIMAException, IOException,
			SAXException {

		JCas jCas = getTestCas(txt);
		runPipeline(
				jCas,
				// tokenize
				createEngine(getSentenceSplitter()), //
				createEngine(getTokenizer()),//

				// BR NER
				createEngine(getConceptMapper("brainregions/aba-syn")),//
				
				createEngine(DeduplicatorAnnotator.class,
						PARAM_ANNOTATION_CLASSES,
						BrainRegionDictTerm.class.getName()),//

				// RUTA
				createEngine(BrainRegionsHelper.getBrainregionRules()),//

				// post-process
				createEngine(KeepLargestAnnotationAnnotator.class,
						PARAM_ANNOTATION_CLASS,
						BrainRegionChunk.class.getName()),
				createEngine(KeepLargestAnnotationAnnotator.class,
						PARAM_ANNOTATION_CLASS, BRCooc.class.getName()),
				createEngine(PostprocessRutaEngine.class));

		for (BrainRegionDictTerm b : select(jCas, BrainRegionDictTerm.class)) {
			LOG.debug(To.string(b));
		}
		for (BrainRegionChunk c : select(jCas, BrainRegionChunk.class)) {
			LOG.debug(To.string(c));
		}
		for (Cooccurrence c : select(jCas, Cooccurrence.class)) {
			LOG.debug(To.string(c));
		}
		return jCas;
	}
}
