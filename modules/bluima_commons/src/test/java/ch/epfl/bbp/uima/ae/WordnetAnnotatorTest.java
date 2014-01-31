package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.testutils.UimaTests.getTokenizedTestCas;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.util.JCasUtil.select;
import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.jcas.JCas;
import org.junit.Test;

import ch.epfl.bbp.uima.types.WordnetDictTerm;
import ch.epfl.bbp.uima.typesystem.Prin;

public class WordnetAnnotatorTest {

    @Test
    public void test() throws Exception {

        JCas jcas = getTokenizedTestCas("rhinoceroses");

        AnalysisEngine ae = createEngine(WordnetAnnotator.class);
        ae.process(jcas);

        Collection<WordnetDictTerm> w = select(jcas, WordnetDictTerm.class);
        Prin.t(w);
        assertEquals(1, w.size());
    }
}
