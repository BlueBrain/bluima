package ch.epfl.bbp.uima.filter;

import static ch.epfl.bbp.uima.BlueUima.BLUE_UTILS_TEST_BASE;
import static ch.epfl.bbp.uima.BlueUima.PARAM_INPUT_FILE;
import static ch.epfl.bbp.uima.filter.FrequencyFilterAnnotator.MAXIMUM_FREQUENCY;
import static ch.epfl.bbp.uima.filter.FrequencyFilterAnnotator.MINIMUM_FREQUENCY;
import static ch.epfl.bbp.uima.testutils.UimaTests.createAnnot;
import static ch.epfl.bbp.uima.testutils.UimaTests.getTestCas;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.apache.uima.fit.util.JCasUtil.exists;
import static org.apache.uima.fit.util.JCasUtil.select;

import org.apache.uima.jcas.JCas;
import org.junit.Ignore;
import org.junit.Test;

import ch.epfl.bbp.uima.types.Keep;
import ch.epfl.bbp.uima.types.Measure;
import ch.epfl.bbp.uima.types.Punctuation;
import ch.epfl.bbp.uima.typesystem.Prin;
import ch.epfl.bbp.uima.uimafit.JcasPipelineBuilder;
import ch.epfl.bbp.uima.uimafit.PipelineBuilder;

import com.digitalpebble.rasp.Token;

/**
 * Tests for the filtering framework.
 * 
 * @author renaud.richardet@epfl.ch
 */
public class FiltersTest {

    public static final String root = BLUE_UTILS_TEST_BASE + "filter/";

    public static void run(JCas jCas, String freqF, String stopF, int min,
            int max) throws Exception {
        PipelineBuilder builder = new JcasPipelineBuilder(jCas);
        builder.add(SimpleNormalizerAnnotator.class);
        builder.add(MeasureNormalizerAnnotator.class);
        builder.add(StopwordFilterAnnotator.class, PARAM_INPUT_FILE, root
                + stopF);
        builder.add(FrequencyFilterAnnotator.class, PARAM_INPUT_FILE, root
                + freqF, MINIMUM_FREQUENCY, min, MAXIMUM_FREQUENCY, max);
        builder.process();
    }

    @Test
    public void testDryRun() throws Exception {
        JCas jCas = getTestCas("abc");
        run(jCas, "tokenFrequencyFile", "stopwords_empty", 5, 15);
    }

    @Test
    public void testOneAnnot() throws Exception {
        JCas jCas = getTestCas("abc");

        createAnnot(jCas, Keep.class, 0, 1).setEnclosedAnnot(
                createAnnot(jCas, Token.class, 0, 1));

        run(jCas, "tokenFrequencyFile", "stopwords_empty", 0, 5000);

        assertEquals(1, select(jCas, Keep.class).size());
    }

    @Test
    public void testNotFrequentEnough() throws Exception {
        JCas jCas = getTestCas("abc");

        createAnnot(jCas, Keep.class, 0, 1).setEnclosedAnnot(
                createAnnot(jCas, Token.class, 0, 1));

        run(jCas, "tokenFrequencyFile", "stopwords_empty", 5, 5000);

        assertFalse("a is freq 1 -> filtered", exists(jCas, Keep.class));
    }

    @Test
    public void testTooFrequent() throws Exception {
        JCas jCas = getTestCas("abc");

        createAnnot(jCas, Keep.class, 2, 3).setEnclosedAnnot(
                createAnnot(jCas, Token.class, 2, 3));

        run(jCas, "tokenFrequencyFile", "stopwords_empty", 5, 99);

        assertFalse("c is freq 100 -> NOT filtered", exists(jCas, Keep.class));
    }

    @Test
    public void testStopwordGetsRemoved() throws Exception {
        JCas jCas = getTestCas("abc");

        createAnnot(jCas, Keep.class, 0, 1).setEnclosedAnnot(
                createAnnot(jCas, Token.class, 0, 1));

        // -> NOT filtered
        run(jCas, "tokenFrequencyFile", "stopwords_ab", 0, 5000);

        assertFalse("stopword gets removed", exists(jCas, Keep.class));
    }

    @Test
    public void testNonStopwordIsKept() throws Exception {
        JCas jCas = getTestCas("abc");

        createAnnot(jCas, Keep.class, 2, 3).setEnclosedAnnot(
                createAnnot(jCas, Token.class, 2, 3));

        run(jCas, "tokenFrequencyFile", "stopwords_ab", 0, 5000);
        // -> NOT filtered

        assertTrue("non-stopword is kept", exists(jCas, Keep.class));
    }

    @Test
    public void testNormalizeMeasures() throws Exception {
        JCas jCas = getTestCas("ab12 mm");

        Measure m = createAnnot(jCas, Measure.class, 2, 6);
        m.setUnit("mm");
        m.setValue(12f);
        createAnnot(jCas, Keep.class, 2, 6).setEnclosedAnnot(m);

        // -> NOT filtered
        run(jCas, "tokenFrequencyFile", "stopwords_empty", 0, 5000);

        for (Keep select : select(jCas, Keep.class)) {
            Prin.t(select);
        }
        assertTrue(exists(jCas, Keep.class));
        assertEquals(MeasureNormalizerAnnotator.MEASURE_MASK + "mm",
                select(jCas, Keep.class).iterator().next().getNormalizedText());

    }

    @Test
    @Ignore
    // TODO not sure this test makes sense
    public void testPunctuationGetsRemoved() throws Exception {
        JCas jCas = getTestCas("ab.");

        createAnnot(jCas, Keep.class, 2, 3).setEnclosedAnnot(
                createAnnot(jCas, Punctuation.class, 2, 3));

        // -> NOT filtered
        run(jCas, "tokenFrequencyFile", "stopwords_empty", 0, 5000);

        assertFalse("punctuation gets removed", exists(jCas, Keep.class));
    }
}