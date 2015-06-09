package ch.epfl.bbp.uima.banner;

import static ch.epfl.bbp.uima.BlueUima.PARAM_VIEW;
import static ch.epfl.bbp.uima.testutils.UimaTests.TEST_SENTENCE;
import static ch.epfl.bbp.uima.testutils.UimaTests.createAnnot;
import static ch.epfl.bbp.uima.testutils.UimaTests.getTestCas;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;
import static org.apache.uima.fit.util.JCasUtil.select;
import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.jcas.JCas;
import org.junit.Test;

import ch.epfl.bbp.uima.ae.BannerAnnotator;
import ch.epfl.bbp.uima.ae.NaiveSentenceSplitterAnnotator;
import ch.epfl.bbp.uima.types.Protein;
import ch.epfl.bbp.uima.typesystem.Prin;
import de.julielab.jules.types.Sentence;

/**
 * @author renaud.richardet@epfl.ch
 */
public class BannerAETest {

    @Test
    public void testText() throws Exception {

        JCas jCas = getTestCas();

        AnalysisEngine ss = createEngine(NaiveSentenceSplitterAnnotator.class);
        AnalysisEngine banner = createEngine(BannerAnnotator.class);

        runPipeline(jCas, ss, banner);

        Collection<Protein> prots = select(jCas, Protein.class);
        assertEquals(1, prots.size());

        Protein prot = prots.iterator().next();
        // TODO calbindin-D28k
        assertEquals("D28k", prot.getCoveredText());
        Prin.t(prots);

        // pmid 8786446
        jCas = getTestCas("Physiological and morphological characteristics of GABAergic nonpyramidal cells in frontal cortex of young rats identified immunohistochemically as containing somatostatin or vasoactive intestinal polypeptide (VIP) were studied in vitro by whole-cell recording and biocytin injection. We have found that most somatostatin- or VIP-containing neurons were different from two other types of GABAergic cells, the parvalbumin-containing fast-spiking cells and the late-spiking cells (neurogliaform cells). In response to injected currents, somatostatin- or VIP-containing nonpyramidal cells showed either bursts of a few spikes on a slow-depolarizing hump, burst-spiking nonpyramidal cells, or single spikes only on depolarization, regular-spiking nonpyramidal cells. Morphologically, both somatostatin- and VIP-containing cells had vertical axonal arbors terminating in symmetrical synapses that were immunoreactive for GABA in electron micrographs. Somatostatin cells included neurons with main ascending axons sending collaterals into layer I (Martinotti cells in deep layers). Some of the Martinotti cells in layer V also contained calbindin D 28k. VIP cells included neurons the main descending axons of which had more descending than ascending collaterals (bipolar cells and double bouquet cells). Two other morphological forms of the VIP cells were those with short descending axons with collaterals bearing multiple boutons on other cell bodies (small basket cells) or with short ascending main axons with collaterals forming arcades (arcade cells). Some of these neurons also contained calretinin. From these results, it appears that the GABAergic neurons controlling circuits in the neocortical layers may be characterized further based on whether they contain somatostatin or VIP.");
        runPipeline(jCas, ss, banner);
        Prin.t(select(jCas, Protein.class));
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
