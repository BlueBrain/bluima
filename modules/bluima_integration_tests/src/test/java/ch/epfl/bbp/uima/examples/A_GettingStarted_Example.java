package ch.epfl.bbp.uima.examples;

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.jcas.JCas;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.fit.util.JCasUtil;

import ch.epfl.bbp.uima.ae.NaiveSentenceSplitterAnnotator;
import ch.epfl.bbp.uima.ae.WhitespaceTokenizerAnnotator;
import ch.epfl.bbp.uima.typesystem.TypeSystem;
import de.julielab.jules.types.Token;

public class A_GettingStarted_Example {
    private static Logger LOG = LoggerFactory
	    .getLogger(A_GettingStarted_Example.class);

    @Test
    public void test() throws Exception {

	// 1. Create a CAS from scratch, set some text
	// The same TypeSystem is used across the whole BlueUima codebase
	JCas jCas = JCasFactory.createJCas(TypeSystem.JULIE_TSD);
	jCas.setDocumentText("hello world");

	// 2. Instantiate analysis engines (AEs)
	// FakeSentenceSplitterAE just takes the whole DocumentText and creates
	// a single Sentence with it; We need it because WhitespaceTokenizerAE
	// takes sentences as input
	AnalysisEngine sentenceSplitterAE = AnalysisEngineFactory
		.createEngine(NaiveSentenceSplitterAnnotator.class);
	// WhitespaceTokenizerAE splits words at each empty space
	AnalysisEngine tokenizerAE = AnalysisEngineFactory
		.createEngine(WhitespaceTokenizerAnnotator.class);

	// 3. Run the pipeline. Order of AEs is important!
	SimplePipeline.runPipeline(jCas, sentenceSplitterAE, tokenizerAE);

	// 4. Show some results
	// Select annotations, based on the Annotation class
	Collection<Token> tokens = JCasUtil.select(jCas, Token.class);
	assertEquals(2, tokens.size());
	for (Token token : tokens) {
	    LOG.debug("token: " + token.getCoveredText());
	}
	assertEquals("hello", tokens.iterator().next().getCoveredText());
    }
}
