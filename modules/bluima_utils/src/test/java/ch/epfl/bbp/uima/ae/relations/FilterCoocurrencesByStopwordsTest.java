package ch.epfl.bbp.uima.ae.relations;

import static ch.epfl.bbp.uima.BlueCasUtil.asList;
import static ch.epfl.bbp.uima.BlueUima.PARAM_ENCLOSING_SCOPE;
import static ch.epfl.bbp.uima.BlueUima.PARAM_FIRST_ANNOT;
import static ch.epfl.bbp.uima.BlueUima.PARAM_SECOND_ANNOT;
import static ch.epfl.bbp.uima.testutils.UimaTests.createAnnot;
import static ch.epfl.bbp.uima.testutils.UimaTests.getTokenizedTestCas;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;
import static org.apache.uima.fit.util.JCasUtil.exists;
import static org.apache.uima.fit.util.JCasUtil.select;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.uima.jcas.JCas;
import org.junit.Test;

import ch.epfl.bbp.uima.types.Cooccurrence;
import ch.epfl.bbp.uima.types.Measure;
import ch.epfl.bbp.uima.types.ProteinDictTerm;
import de.julielab.jules.types.Sentence;

public class FilterCoocurrencesByStopwordsTest {

    @Test
    public void test() throws Exception {

        JCas jCas = FilterCoocurrencesByTriggerwordTest.doTest("projection");
        List<Cooccurrence> cooc = asList(select(jCas, Cooccurrence.class));
        assertEquals("one cooc, since no stopword", 1, cooc.size());
        assertEquals(0, cooc.get(0).getFirstEntity().getBegin());
        assertEquals(3, cooc.get(0).getSecondEntity().getBegin());

        jCas = FilterCoocurrencesByTriggerwordTest.doTest("abbreviations");
        assertTrue("no cooc, since stopword",
                !exists(jCas, Cooccurrence.class));
    }

    public static JCas doTest(String newWord) throws Exception {

        JCas jCas = getTokenizedTestCas(newWord + " aa bb ");
        int offset = newWord.length() + 1;

        createAnnot(jCas, ProteinDictTerm.class, 0 + offset, 2 + offset, "aa");
        createAnnot(jCas, Measure.class, 3 + offset, 5 + offset, "bb");

        runPipeline(
                jCas, //
                createEngine(ExtractCoocurrences.class,//
                        PARAM_ENCLOSING_SCOPE, Sentence.class.getName(),//
                        PARAM_FIRST_ANNOT, ProteinDictTerm.class.getName(),//
                        PARAM_SECOND_ANNOT, Measure.class.getName()),
                createEngine(FilterCoocurrencesByStopwords.class));
        return jCas;
    }

}
