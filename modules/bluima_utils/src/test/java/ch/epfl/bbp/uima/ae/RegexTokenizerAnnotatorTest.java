package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.ae.RegexTokenizerAnnotator.patterPunctDigitsCamelcase;
import static ch.epfl.bbp.uima.ae.RegexTokenizerAnnotator.patterPunctuation;
import static ch.epfl.bbp.uima.ae.RegexTokenizerAnnotator.patterPunctuationNoDash;
import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.jcas.JCas;
import org.junit.Test;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.fit.util.JCasUtil;

import ch.epfl.bbp.uima.testutils.UimaTests;
import ch.epfl.bbp.uima.typesystem.Prin;
import de.julielab.jules.types.Token;

public class RegexTokenizerAnnotatorTest {

    @Test
    public void test() throws Exception {

	AnalysisEngine sentenceAE = AnalysisEngineFactory
		.createEngine(NaiveSentenceSplitterAnnotator.class);
	AnalysisEngine tokenizerAE = AnalysisEngineFactory
		.createEngine(RegexTokenizerAnnotator.class);

	JCas jcas = UimaTests.getTestCas("bla,bla calbindin-D28k blaBla");
	SimplePipeline.runPipeline(jcas, sentenceAE, tokenizerAE);

	Collection<Token> tokens = JCasUtil.select(jcas, Token.class);
	Prin.t("tokens", tokens);
	assertEquals(5, tokens.size());
    }

    @Test
    public void testRegex() {

	assertEquals(1, "hello2world".split(patterPunctuation).length);
	assertEquals(7, "hel!lo,wor ld".split(patterPunctuation).length);
    }

    @Test
    public void testRegexCamelCase() {

	assertEquals(1, "helloworld".split(patterPunctDigitsCamelcase).length);
	assertEquals(2, "helloWorld".split(patterPunctDigitsCamelcase).length);
	assertEquals(3, "hello-world".split(patterPunctDigitsCamelcase).length);
	assertEquals(3, "hello2world".split(patterPunctDigitsCamelcase).length);
    }

    @Test
    public void testRegexNoDash() {
	assertEquals(3, "hello,wor-ld".split(patterPunctuationNoDash).length);
    }
}