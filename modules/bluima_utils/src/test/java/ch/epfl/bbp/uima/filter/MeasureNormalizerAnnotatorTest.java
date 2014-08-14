package ch.epfl.bbp.uima.filter;

import static ch.epfl.bbp.uima.testutils.UimaTests.createAnnot;
import static ch.epfl.bbp.uima.testutils.UimaTests.getTestCas;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.apache.uima.fit.util.JCasUtil.exists;
import static org.apache.uima.fit.util.JCasUtil.select;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.apache.uima.jcas.JCas;
import org.junit.Test;

import ch.epfl.bbp.uima.types.Keep;
import ch.epfl.bbp.uima.types.Measure;
import ch.epfl.bbp.uima.uimafit.JcasPipelineBuilder;
import ch.epfl.bbp.uima.uimafit.PipelineBuilder;

public class MeasureNormalizerAnnotatorTest {

    @Test
    // Checks a Measure object normalized text is changed to MEASURE_
    public void testMeasuresIsCorrectlyParsed() throws Exception {
        JCas jCas = getTestCas("20%");

        Measure m = createAnnot(jCas, Measure.class, 0, 2);
        m.setUnit("%");
        m.setValue(20f);
        createAnnot(jCas, Keep.class, 0, 2).setEnclosedAnnot(m);

        PipelineBuilder builder = new JcasPipelineBuilder(jCas);
        builder.add(MeasureNormalizerAnnotator.class);
        builder.process();

        assertTrue(exists(jCas, Keep.class));
        assertEquals(MeasureNormalizerAnnotator.MEASURE_MASK + "%",
                select(jCas, Keep.class).iterator().next().getNormalizedText());
    }

    @Test
    // Checks MEASURE_ is removed from keeps if option set to true
    public void testRemoveMeasure_() throws Exception {
        JCas jCas = getTestCas("20");

        Measure m = createAnnot(jCas, Measure.class, 0, 1);
        m.setValue(20f);
        m.setUnit("");
        createAnnot(jCas, Keep.class, 0, 1).setEnclosedAnnot(m);

        PipelineBuilder builder = new JcasPipelineBuilder(jCas);
        builder.add(createEngineDescription(MeasureNormalizerAnnotator.class,
                "removeSimpleMeasure", true));
        builder.process();

        Collection<Keep> keeps = select(jCas, Keep.class);
        assertEquals("Should have no keeps", 0, keeps.size());
    }

}
