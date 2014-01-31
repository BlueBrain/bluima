package ch.epfl.bbp.uima.ae.relations;

import static ch.epfl.bbp.uima.BlueUima.PARAM_COOCCURRENCE_TYPE;
import static ch.epfl.bbp.uima.BlueUima.PARAM_FIRST_ANNOT_TYPE;
import static ch.epfl.bbp.uima.BlueUima.PARAM_OUTPUT_FILE;
import static ch.epfl.bbp.uima.BlueUima.PARAM_SECOND_ANNOT_TYPE;
import static ch.epfl.bbp.uima.BlueUima.PARAM_VERBOSE;
import static ch.epfl.bbp.uima.ae.relations.WriteCoocurrencesToLoadfile2.snippet;
import static ch.epfl.bbp.uima.testutils.UimaTests.createAnnot;
import static ch.epfl.bbp.uima.testutils.UimaTests.getTokenizedTestCas;
import static java.lang.System.currentTimeMillis;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;
import static org.apache.uima.fit.util.JCasUtil.exists;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.junit.Test;

import ch.epfl.bbp.io.LineReader;
import ch.epfl.bbp.uima.types.Cooccurrence;
import ch.epfl.bbp.uima.types.Measure;
import ch.epfl.bbp.uima.types.Protein;
import ch.epfl.bbp.uima.types.ProteinDictTerm;

public class WriteCoocurrencesToLoadfile2Test {

    @Test
    public void testSnippet() throws Exception {

        JCas jCas = getTokenizedTestCas("Test sentence for snippetting great");
        Protein a1 = createAnnot(jCas, Protein.class, 5, 13);
        Protein a2 = createAnnot(jCas, Protein.class, 18, 29);

        String snippet = snippet(jCas, 2, 32, a1, a2);
        assertEquals(
                "st <strong class=\"Protein\">sentence</strong> for <strong class=\"Protein\">snippetting</strong> gr",
                snippet);

        snippet = snippet(jCas, 2, 32, a2, a1);
        assertEquals(
                "a2 then a1",
                "st <strong class=\"Protein\">sentence</strong> for <strong class=\"Protein\">snippetting</strong> gr",
                snippet);
    }

    @Test
    public void testAE() throws Exception {

        JCas jCas = FilterCoocurrencesByDistanceTest.doTest(5);
        assertTrue("one cooc, distance ok", exists(jCas, Cooccurrence.class));

        String ouputFile = "target/WriteCoocurrencesToLoadfile2Test_"
                + currentTimeMillis() + ".txt";
        AnalysisEngine wc = createEngine(WriteCoocurrencesToLoadfile2.class,//
                PARAM_OUTPUT_FILE, ouputFile,//
                PARAM_FIRST_ANNOT_TYPE, ProteinDictTerm.class.getSimpleName(),//
                PARAM_SECOND_ANNOT_TYPE, Measure.class.getSimpleName());
        runPipeline(jCas, wc);
        wc.collectionProcessComplete();// manual call needed

        // verify file
        List<String> lines = LineReader.linesFrom(ouputFile);
        assertEquals(1, lines.size());
        assertEquals(
                "-1\t0.0\tAMPA\t9\t13\tProteinDictTerm\tmM\t0\t5\tMeasure\t0\t24\t<strong class=\"Measure\">12 mM</strong> of <strong class=\"ProteinDictTerm\">AMPA</strong> was found.",
                lines.get(0));
    }

    @Test
    public void testAEFilter() throws Exception {

        JCas jCas = FilterCoocurrencesByDistanceTest.doTest(5);
        assertTrue("one cooc, distance ok", exists(jCas, Cooccurrence.class));
        Cooccurrence cooc = JCasUtil.selectSingle(jCas, Cooccurrence.class);
        cooc.setCooccurrenceType("bli bli");

        String ouputFile = "target/WriteCoocurrencesToLoadfile2Test_"
                + currentTimeMillis() + ".txt";
        AnalysisEngine wc = createEngine(
                WriteCoocurrencesToLoadfile2.class,//
                PARAM_OUTPUT_FILE,
                ouputFile,//
                PARAM_FIRST_ANNOT_TYPE,
                ProteinDictTerm.class.getSimpleName(),//
                PARAM_SECOND_ANNOT_TYPE, Measure.class.getSimpleName(),
                PARAM_COOCCURRENCE_TYPE, "bli bli", PARAM_VERBOSE, false);
        runPipeline(jCas, wc);
        wc.collectionProcessComplete();// manual call needed

        // verify file
        List<String> lines = LineReader.linesFrom(ouputFile);
        assertEquals(1, lines.size());
        assertEquals(
                "-1\t0.0\tAMPA\t9\t13\tProteinDictTerm\tmM\t0\t5\tMeasure\t0\t24",
                lines.get(0));

        // does not pass filtering
        ouputFile = "target/WriteCoocurrencesToLoadfile2Test_"
                + currentTimeMillis() + ".txt";
        wc = createEngine(
                WriteCoocurrencesToLoadfile2.class,//
                PARAM_OUTPUT_FILE,
                ouputFile,//
                PARAM_FIRST_ANNOT_TYPE,
                ProteinDictTerm.class.getSimpleName(),//
                PARAM_SECOND_ANNOT_TYPE, Measure.class.getSimpleName(),
                PARAM_COOCCURRENCE_TYPE, "bla bla");
        runPipeline(jCas, wc);
        wc.collectionProcessComplete();// manual call needed

        // verify file
        lines = LineReader.linesFrom(ouputFile);
        assertEquals("other files get filtered out", 0,
                lines.size());
    }
}
