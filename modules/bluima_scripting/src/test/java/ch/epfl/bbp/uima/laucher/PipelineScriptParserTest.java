package ch.epfl.bbp.uima.laucher;

import static ch.epfl.bbp.uima.laucher.PipelineScriptParser.parse;
import static com.google.common.collect.Lists.newArrayList;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.util.Pair;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.resource.ResourceInitializationException;
import org.junit.Test;

import ch.epfl.bbp.IteratorWithPrevious;
import ch.epfl.bbp.uima.ae.EnsureDocHasHeader;
import ch.epfl.bbp.uima.ae.WhitespaceTokenizerAnnotator;
import ch.epfl.bbp.uima.ae.output.BartWriter;

public class PipelineScriptParserTest {

    static final IteratorWithPrevious<String> EMPTY_ARGS = new IteratorWithPrevious<String>(
            new ArrayList<String>());

    @Test
    public void testWrongIndentation() throws Exception {
        Pair<List<Object>, String> params = PipelineScriptParser
                .parseParams(new IteratorWithPrevious<String>(newArrayList(
                        " a: bla", "b: bli")));
        assertEquals(
                "only one pair of params, the second is considered the next ae definition",
                2, params.getKey().size());
    }

    @Test
    public void testNoCOLUMN() throws Exception {
        Pair<List<Object>, String> params = PipelineScriptParser
                .parseParams(new IteratorWithPrevious<String>(newArrayList(
                        " a: bla", "bbbbbla")));
        assertEquals(
                "only one pair of params, the second is considered the next ae definition",
                2, params.getKey().size());
    }

    @Test
    public void testParseParams() throws Exception {
        Pair<List<Object>, String> params = PipelineScriptParser
                .parseParams(new IteratorWithPrevious<String>(
                        newArrayList(" a: bla")));
        assertEquals(2, params.getKey().size());
        assertEquals("xml works", "<a>bla</a>", params.getValue());
    }

    @Test
    public void testParseAe() throws Exception {
        Pipeline p = new Pipeline();
        PipelineScriptParser.parseAE("ae: "
                + WhitespaceTokenizerAnnotator.class.getName(), EMPTY_ARGS, p);
    }

    @Test
    public void testParseAe2() throws Exception {
        PipelineScriptParser.parseAE("ae: "
                + WhitespaceTokenizerAnnotator.class.getSimpleName(),
                EMPTY_ARGS, new Pipeline());
    }

    @Test
    public void testParseAe_fromCleanup() throws Exception {
        PipelineScriptParser.parseAE("ae: " + BartWriter.class.getName(),
                EMPTY_ARGS, new Pipeline());
    }

    @Test
    public void testParseAe_fromCleanup_simpleName() throws Exception {
        PipelineScriptParser.parseAE("ae: " + BartWriter.class.getSimpleName(),
                EMPTY_ARGS, new Pipeline());
    }

    @Test
    public void testMultiline() throws Exception {
        Pair<List<Object>, String> params = PipelineScriptParser
                .parseParams(new IteratorWithPrevious<String>(newArrayList(
                        " a: bla\\", "   bli   ")));
        assertEquals(2, params.getKey().size());
        assertEquals("bla bli", params.getKey().get(1));

        params = PipelineScriptParser
                .parseParams(new IteratorWithPrevious<String>(newArrayList(
                        " a: bla\\", "   bli\\", "boo ").listIterator()));
        assertEquals(2, params.getKey().size());
        assertEquals("bla bli boo", params.getKey().get(1));
    }

    final static String TEST_PARSE_ROOT = LauncherTest.TESTS_ROOT
            + "pipeline_parser/";

    @Test
    public void testParseValidPipelines() throws Exception {

        for (String validPipeline : newArrayList(//
                "valid1_simple.pipeline", //
                "valid2_arguments.pipeline", //
                "valid3_complex.pipeline", //
                "valid4_collapsed.pipeline")) {
            parse(new File(TEST_PARSE_ROOT + validPipeline));
        }
    }

    // helper to test ae_java:
    public static AnalysisEngineDescription getTestAE()
            throws ResourceInitializationException {
        return createEngineDescription(EnsureDocHasHeader.class);
    }

    @Test
    public void testParseCorrpted() throws Exception {

        for (int i = 1; i < 10; i++) {
            boolean hasEx = false;
            try {
                parse(new File(TEST_PARSE_ROOT + "corrupted" + i + ".pipeline"));
            } catch (ParseException p) {
                hasEx = true;
            }
            assertTrue("corrupted" + i + ".pipeline should not parse ", hasEx);
        }
    }
}
