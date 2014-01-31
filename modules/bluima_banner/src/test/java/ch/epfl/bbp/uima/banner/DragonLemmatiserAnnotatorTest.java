package ch.epfl.bbp.uima.banner;

import static org.junit.Assert.*;

import java.util.Collection;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.jcas.JCas;
import org.junit.Test;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.util.JCasUtil;

import ch.epfl.bbp.uima.ae.DragonLemmatiserAnnotator;
import ch.epfl.bbp.uima.testutils.UimaTests;
import de.julielab.jules.types.Token;

public class DragonLemmatiserAnnotatorTest {

    @Test
    public void test() throws Exception {
	JCas jcas = UimaTests.getTokenizedTestCas();
	AnalysisEngine ae = AnalysisEngineFactory
		.createEngine(DragonLemmatiserAnnotator.class);
	ae.process(jcas);

	Collection<Token> tokens = JCasUtil.select(jcas, Token.class);
	for (Token t : tokens) {
	    System.out.println(t.getCoveredText() + "\t"
		    + t.getLemma().getValue());
	}
	assertEquals("use", tokens.iterator().next().getLemma().getValue());
    }
}
