package ch.epfl.bbp.uima.filter;

import static ch.epfl.bbp.uima.testutils.UimaTests.getTokenizedTestCas;
import static org.junit.Assert.assertEquals;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.util.Collection;

import org.apache.uima.jcas.JCas;
import org.junit.Test;

import ch.epfl.bbp.uima.ae.SnowballAnnotator;
import ch.epfl.bbp.uima.testutils.UimaTests;
import ch.epfl.bbp.uima.types.Keep;
import ch.epfl.bbp.uima.uimafit.JcasPipelineBuilder;
import ch.epfl.bbp.uima.uimafit.PipelineBuilder;

/**
 * Tests for the normalizing framework.
 * 
 * @author renaud.richardet@epfl.ch
 */
public class NormalizersTest {

    @Test
    public void testSimpleNormalizerAnnotator() throws Exception {
        JCas jCas = getTokenizedTestCas("Regulated protein");

        PipelineBuilder builder = new JcasPipelineBuilder(jCas);
        builder.add(Tokens2KeepAnnotator.class);
        builder.add(SimpleNormalizerAnnotator.class);
        builder.process();

        Collection<Keep> keeps = select(jCas, Keep.class);
        assertEquals(2, keeps.size());
        assertEquals("regulated", keeps.iterator().next().getNormalizedText());
    }

    @Test
    public void testSnowball() throws Exception {
        JCas jCas = getTokenizedTestCas("Regulated protein");

        PipelineBuilder builder = new JcasPipelineBuilder(jCas);
        builder.add(Tokens2KeepAnnotator.class);
        builder.add(SnowballStemmerNormalizerAnnotator.class);
        builder.process();

        Collection<Keep> keeps = select(jCas, Keep.class);
        assertEquals(2, keeps.size());
        assertEquals("regul", keeps.iterator().next().getNormalizedText());
    }

    @Test
    /** Using a Snowball lemmatizer (on tokens) and a SimpleNormalizer (on keeps) */
    public void testMixed() throws Exception {
        JCas jCas = UimaTests.getTokenizedTestCas("Regulated protein");

        PipelineBuilder builder = new JcasPipelineBuilder(jCas);
        builder.add(SnowballAnnotator.class);
        builder.add(Tokens2KeepAnnotator.class);
        builder.add(SimpleNormalizerAnnotator.class);
        builder.process();

        Collection<Keep> keeps = select(jCas, Keep.class);
        assertEquals(2, keeps.size());
        assertEquals(
                "it should be 'regul', that comes from Snowball (the Token stemmer)",
                "regul", keeps.iterator().next().getNormalizedText());
    }
}