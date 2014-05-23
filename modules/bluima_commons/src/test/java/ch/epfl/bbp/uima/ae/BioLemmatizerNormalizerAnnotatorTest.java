package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.testutils.UimaTests.createAnnot;
import static org.junit.Assert.assertEquals;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.util.Collection;
import java.util.Iterator;

import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.jcas.JCas;
import org.junit.Test;

import ch.epfl.bbp.uima.filter.Tokens2KeepAnnotator;
import ch.epfl.bbp.uima.testutils.UimaTests;
import ch.epfl.bbp.uima.types.Keep;
import ch.epfl.bbp.uima.types.Measure;
import ch.epfl.bbp.uima.uimafit.JcasPipelineBuilder;
import ch.epfl.bbp.uima.uimafit.PipelineBuilder;
import de.julielab.jules.types.Token;

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
    
    @Test
    public void testNonTokensIncluded() throws Exception{
        JCas jCas = UimaTests.getTestCas("is regulated");

        Measure m = createAnnot(jCas, Measure.class, 0, 1);
        Keep k = createAnnot(jCas, Keep.class, 0, 1);
        k.setEnclosedAnnot(m);
        k.setNormalizedText("is");
        
        Token t = createAnnot(jCas,Token.class, 3, 11);
        createAnnot(jCas, Keep.class, 3, 11).setEnclosedAnnot(t);

        PipelineBuilder builder = new JcasPipelineBuilder(jCas);
        builder.add(BlueBioLemmatizer.class);
        builder.add(BioLemmatizerNormalizerAnnotator.class);
        builder.process();

        Collection<Keep> keeps = select(jCas, Keep.class);
        assertEquals(2, keeps.size());
        Iterator<Keep> it = keeps.iterator();
        assertEquals("i", it.next().getNormalizedText());
        assertEquals("regulate", it.next().getNormalizedText());
    }
    
    @Test
    public void testOnlyTokens() throws Exception{
        JCas jCas = UimaTests.getTestCas("is regulated");

        Measure m = createAnnot(jCas, Measure.class, 0, 1);
        m.setUnit("%");
        m.setValue(20f);
        Keep k = createAnnot(jCas, Keep.class, 0, 1);
        k.setEnclosedAnnot(m);
        k.setNormalizedText("is");
        
        Token t = createAnnot(jCas,Token.class, 3, 11);
        createAnnot(jCas, Keep.class, 3, 11).setEnclosedAnnot(t);

        PipelineBuilder builder = new JcasPipelineBuilder(jCas);
        builder.add(BlueBioLemmatizer.class);
        builder.add(AnalysisEngineFactory.createEngineDescription(BioLemmatizerNormalizerAnnotator.class, "onlyTokens", true));
        builder.process();

        Collection<Keep> keeps = select(jCas, Keep.class);
        assertEquals(2, keeps.size());
        Iterator<Keep> it = keeps.iterator();
        assertEquals("is", it.next().getNormalizedText());
        assertEquals("regulate", it.next().getNormalizedText());
    }
}
