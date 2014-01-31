package ch.epfl.bbp.uima.integration_tests;

import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.jcas.JCas;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.fit.util.JCasUtil;

import ch.epfl.bbp.uima.ae.NaiveSentenceSplitterAnnotator;
import ch.epfl.bbp.uima.ae.WhitespaceTokenizerAnnotator;
import ch.epfl.bbp.uima.pear.ComponentManager;
import ch.epfl.bbp.uima.testutils.UimaTests;
import ch.epfl.bbp.uima.types.SurfaceForm;
import ch.epfl.bbp.uima.typesystem.To;

/**
 * Integration Test, uses BioLexicon
 * 
 * @author renaud.richardet@epfl.ch
 */
public class LexiconIntegrationTest {
    protected static Logger LOG = LoggerFactory
	    .getLogger(LexiconIntegrationTest.class);

    protected static AnalysisEngine sentenceAE;
    protected static AnalysisEngine tokenizerAE;
    protected static AnalysisEngine dictAE;

    // FIXME
    // @BeforeClass
    public static void before() throws Exception {
	sentenceAE = AnalysisEngineFactory
		.createEngine(NaiveSentenceSplitterAnnotator.class);
	tokenizerAE = AnalysisEngineFactory
		.createEngine(WhitespaceTokenizerAnnotator.class);

	dictAE = ComponentManager.getAE("BlueDictionaryAE");
    }

    // FIXME
    // @Test
    public void testCalbindin() throws Exception {

	JCas jcas = UimaTests.getTestCas();
	SimplePipeline.runPipeline(jcas, sentenceAE, tokenizerAE, dictAE);

	Collection<SurfaceForm> sf = JCasUtil.select(jcas, SurfaceForm.class);
	LOG.debug(To.string("matched surface forms", sf));
	assertTrue(sf.size() > 1);

	UimaTests.assertResultsCovers(sf, "calbindin-D28k");
    }

}
