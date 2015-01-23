package ch.epfl.bbp.uima.ae.relations;

import static ch.epfl.bbp.uima.BlueUima.PARAM_ENCLOSING_SCOPE;
import static ch.epfl.bbp.uima.BlueUima.PARAM_FIRST_ANNOT;
import static ch.epfl.bbp.uima.BlueUima.PARAM_SECOND_ANNOT;
import static ch.epfl.bbp.uima.testutils.UimaTests.createAnnot;
import static ch.epfl.bbp.uima.testutils.UimaTests.getTokenizedTestCas;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;
import static org.apache.uima.fit.util.JCasUtil.exists;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.uima.jcas.JCas;
import org.junit.Test;

import ch.epfl.bbp.uima.types.Cooccurrence;
import ch.epfl.bbp.uima.types.Measure;
import ch.epfl.bbp.uima.types.ProteinDictTerm;
import de.julielab.jules.types.Sentence;

public class FilterCoocurrencesByNotTest {

    @Test
    public void test() throws Exception {

        assertFalse(doTest("The not"));
        assertFalse(doTest("The no"));
        assertFalse(doTest("The nope no"));

        assertTrue(doTest("The nope"));
        assertTrue(doTest("The noway"));
        assertTrue(doTest("The woopsno"));
    }

    public static boolean doTest(String newWord) throws Exception {

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
                createEngine(FilterCoocurrencesByNot.class));

        return exists(jCas, Cooccurrence.class);
    }
}
