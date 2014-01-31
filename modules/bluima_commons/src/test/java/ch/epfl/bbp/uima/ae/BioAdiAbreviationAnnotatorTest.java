package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.testutils.UimaTests.getTestCas;
import static org.junit.Assert.assertEquals;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.util.Collection;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.jcas.JCas;
import org.junit.Test;

import ch.epfl.bbp.uima.typesystem.Prin;
import de.julielab.jules.types.Abbreviation;

public class BioAdiAbreviationAnnotatorTest {

    @Test
    public void test() throws Exception {

	JCas jCas = getTestCas(//
		"shunting for normal pressure hydrocephalus (NPH) was classified");
	
	AnalysisEngine ae = createEngine(BioAdiAbreviationAnnotator.class);
	ae.process(jCas);

	Collection<Abbreviation> a = select(jCas, Abbreviation.class);
	Prin.t(a);
	assertEquals(1, a.size());
    }
}
