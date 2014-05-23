package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.BlueUima.PARAM_ANNOTATION_CLASS;
import static ch.epfl.bbp.uima.ae.MeasureRegexAnnotators.getMeasuresAED;
import static ch.epfl.bbp.uima.testutils.UimaTests.getTokenizedTestCas;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.slf4j.LoggerFactory.getLogger;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.util.Collection;

import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.jcas.JCas;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.util.JCasUtil;

import ch.epfl.bbp.uima.testutils.UimaTests;
import ch.epfl.bbp.uima.types.Concentration;
import ch.epfl.bbp.uima.types.Measure;
import ch.epfl.bbp.uima.typesystem.To;

/**
 * @author renaud.richardet@epfl.ch
 * @author joelle.pormann@epfl.ch
 */
public class PruneMeasureAnnotatorTest {
    private static Logger LOG = getLogger(PruneMeasureAnnotatorTest.class);

    @Test
    public void testWithOneJcas() throws Exception {

        AnalysisEngineDescription prune = AnalysisEngineFactory
                .createEngineDescription(KeepLargestAnnotationAnnotator.class,
                        PARAM_ANNOTATION_CLASS, Concentration.class.getName());

        JCas jCas = UimaTests.getTestCas("bla bla 128 +/- 12 copies bli bli");

        // we manually tag the 2 concentrations that the annotator would find
        // (because of the way in which RegExes are defined)
        Concentration concExternal = new Concentration(jCas, 8, 25);
        concExternal.addToIndexes();
        Concentration concInternal = new Concentration(jCas, 16, 25);
        concInternal.addToIndexes();

        // verify that we extracted the right concentrations (the
        // ones we had expected)
        assertEquals("12 copies", concInternal.getCoveredText());
        assertEquals("128 +/- 12 copies", concExternal.getCoveredText());
        assertEquals("Should have 2 Concentrations before pruning", 2, JCasUtil
                .select(jCas, Concentration.class).size());

        runPipeline(jCas, prune);

        // retrieve the result and test whether the bad tag (the inner one)
        // actually has been eliminated
        Collection<Concentration> afterPruning = JCasUtil.select(jCas,
                Concentration.class);
        assertEquals("Should have only 1 Concentration left after pruning", 1,
                afterPruning.size());
        assertEquals("128 +/- 12 copies", afterPruning.iterator().next()
                .getCoveredText());
    }

    @Test
    @Ignore
    // FIXME should work
    public void testWithRegexAnnotator() throws Exception {

        AnalysisEngineDescription regexAnnotator = getMeasuresAED();
        AnalysisEngineDescription prune = createEngineDescription(KeepLargestAnnotationAnnotator.class);

        JCas jCas = getTokenizedTestCas("bla bla 40 - 55 mm bli bli");

        runPipeline(jCas, regexAnnotator);

        Collection<Measure> s = select(jCas, Measure.class);
        LOG.debug("measures found: {}", To.string(s));
        assertTrue("Can have many Measures before pruning, was " + s.size(),
                s.size() > 3);

        // we run the 'Detagger' on the previously annotated text and
        // verify that it ends up with only 1 concentration left
        runPipeline(jCas, prune);
        Collection<Measure> afterPruning = select(jCas, Measure.class);
        assertEquals("Should have only 1 Concentration left after pruning", 1,
                afterPruning.size());
        assertEquals("40 - 55 mm", afterPruning.iterator().next()
                .getCoveredText());
    }
}
