package ch.epfl.bbp.uima.banner;

import static ch.epfl.bbp.uima.BlueUima.PARAM_VIEW;
import static ch.epfl.bbp.uima.testutils.UimaTests.TEST_SENTENCE;
import static ch.epfl.bbp.uima.testutils.UimaTests.createAnnot;
import static ch.epfl.bbp.uima.testutils.UimaTests.getTestCas;
import static org.apache.uima.fit.util.JCasUtil.select;
import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.junit.Test;

import ch.epfl.bbp.uima.ae.BannerAnnotator;
import ch.epfl.bbp.uima.ae.NaiveSentenceSplitterAnnotator;
import ch.epfl.bbp.uima.testutils.UimaTests;
import ch.epfl.bbp.uima.types.Protein;
import ch.epfl.bbp.uima.typesystem.Prin;
import de.julielab.jules.types.Sentence;

/**
 * @author renaud.richardet@epfl.ch
 */
public class BannerAETest {

    @Test
    public void testText() throws Exception {

        JCas jcas = UimaTests.getTestCas();

        AnalysisEngine ss = AnalysisEngineFactory
                .createEngine(NaiveSentenceSplitterAnnotator.class);

        AnalysisEngine banner = AnalysisEngineFactory
                .createEngine(BannerAnnotator.class);

        SimplePipeline.runPipeline(jcas, ss, banner);

        Collection<Protein> prots = JCasUtil.select(jcas, Protein.class);
        assertEquals(1, prots.size());

        Protein prot = prots.iterator().next();
        // TODO calbindin-D28k
        assertEquals("D28k", prot.getCoveredText());
        Prin.t(prots);
    }

    @Test
    public void testView() throws Exception {

        JCas jcas = getTestCas("empty!");

        JCas newView = jcas.createView("bla");
        newView.setDocumentText(TEST_SENTENCE);
        createAnnot(newView, Sentence.class, 0, TEST_SENTENCE.length());

        AnalysisEngine ss = AnalysisEngineFactory
                .createEngine(NaiveSentenceSplitterAnnotator.class);

        AnalysisEngine banner = AnalysisEngineFactory.createEngine(
                BannerAnnotator.class, PARAM_VIEW, "bla");

        SimplePipeline.runPipeline(jcas, ss, banner);

        Collection<Protein> prots = select(jcas, Protein.class);
        assertEquals("nothin in system view", 0, prots.size());

        prots = select(jcas.getView("bla"), Protein.class);
        assertEquals("one protein in bla view", 1, prots.size());
    }
}
