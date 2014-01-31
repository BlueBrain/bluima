package ch.epfl.bbp.uima.ae;

import static org.junit.Assert.assertEquals;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.util.Collection;
import java.util.Iterator;

import org.apache.uima.jcas.JCas;
import org.junit.Test;

import ch.epfl.bbp.uima.filter.Tokens2KeepAnnotator;
import ch.epfl.bbp.uima.testutils.UimaTests;
import ch.epfl.bbp.uima.types.Keep;
import ch.epfl.bbp.uima.uimafit.JcasPipelineBuilder;
import ch.epfl.bbp.uima.uimafit.PipelineBuilder;

public class BioLemmatizerNormalizerAnnotatorTest {

    @Test
    public void test() throws Exception {
        JCas jCas = UimaTests.getTokenizedTestCas("AMPAR is Regulated by GABA");

        PipelineBuilder builder = new JcasPipelineBuilder(jCas);
        builder.add(BlueBioLemmatizer.class);
        builder.add(Tokens2KeepAnnotator.class);
        builder.add(BioLemmatizerNormalizerAnnotator.class);
        builder.process();

        Collection<Keep> keeps = select(jCas, Keep.class);
        assertEquals(5, keeps.size());
        Iterator<Keep> it = keeps.iterator();
        it.next();
        assertEquals("be", it.next().getNormalizedText());
        assertEquals("regulate", it.next().getNormalizedText());
    }
}
