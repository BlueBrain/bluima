package ch.epfl.bbp.uima;

import static ch.epfl.bbp.uima.BlueUima.PARAM_ANNOTATION_CLASSES;
import static ch.epfl.bbp.uima.testutils.UimaTests.getTestCas;
import static ch.epfl.bbp.uima.testutils.UimaTests.getTokenizedTestCas;
import static com.google.common.collect.Lists.newArrayList;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;
import static org.apache.uima.fit.util.JCasUtil.exists;
import static org.apache.uima.fit.util.JCasUtil.select;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.slf4j.LoggerFactory.getLogger;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Collection;

import org.apache.uima.jcas.JCas;
import org.junit.Test;
import org.slf4j.Logger;

import ch.epfl.bbp.uima.ae.DeduplicatorAnnotator;
import ch.epfl.bbp.uima.ae.NaiveSentenceSplitterAnnotator;
import ch.epfl.bbp.uima.ae.RegexTokenizerAnnotator;
import ch.epfl.bbp.uima.types.AgeDictTerm;
import ch.epfl.bbp.uima.types.BrainRegionDictTerm;
import ch.epfl.bbp.uima.types.DictTerm;
import ch.epfl.bbp.uima.types.DiseaseDictTerm;
import ch.epfl.bbp.uima.types.IonchannelDictTerm;
import ch.epfl.bbp.uima.types.NavchannelDictTerm;
import ch.epfl.bbp.uima.types.NeuronDictTerm;
import ch.epfl.bbp.uima.types.NifTerm;
import ch.epfl.bbp.uima.types.ProteinDictTerm;
import ch.epfl.bbp.uima.types.SubcellDictTerm;
import ch.epfl.bbp.uima.typesystem.To;
import ch.epfl.bbp.uima.uimafit.JcasPipelineBuilder;

/**
 * @author renaud.richardet@epfl.ch
 */
public class LexicaHelperTest {
    private static Logger LOG = getLogger(LexicaHelperTest.class);

    /** Tests selected lexica with selected entries */
    @Test
    public void test() throws Exception {

        testWith("brainregions/aba", BrainRegionDictTerm.class, "Visual areas",
                "Visual area", "Pontine gray");

        testWith("brainregions/aba-syn", BrainRegionDictTerm.class,
                "Pontine gray", "Nucleus pontis");

        testWith("blueonto1/age", AgeDictTerm.class, "p18");

        testWith("onto_baseline/protein", ProteinDictTerm.class,
                "bovine serum albumin");

        testWith("go/subcell", SubcellDictTerm.class, "axonal membrane");

        testWith("blueonto1/navchannel", NavchannelDictTerm.class, "Scn8a",
                "sodium channel protein type X subunit alpha",
                "HVA sodium channel");

        testWith("blueonto1/ionchannel", IonchannelDictTerm.class, "Cacng1");

        testWith("blueonto1/neuron", NeuronDictTerm.class,
                "narrow pyramidal neuron projecting to thalamus");

        testWith("nif/nif", NifTerm.class, "IPSP", "calbindin 1");
    }

    @Test
    public void testAlternateTokenization() throws Exception {

        JcasPipelineBuilder pipeline = new JcasPipelineBuilder();
        pipeline.add(LexicaHelper.getConceptMapper("blueonto1/disease"));

        // String test = "Taybi-Linder";
        String test = "Cayler cardiofacial syndrome";
        JCas jCas = getTokenizedTestCas("test with " + test + " test");
        pipeline.process(jCas);
        assertTrue("should contain '" + test + "'",
                exists(jCas, DiseaseDictTerm.class));

        test = "Taybi";
        jCas = getTokenizedTestCas("test with " + test + " test");
        pipeline.process(jCas);
        assertFalse("should NOT contain '" + test + "'",
                exists(jCas, DiseaseDictTerm.class));

        // with regex tokenization
        pipeline = new JcasPipelineBuilder();
        pipeline.add(createEngineDescription(NaiveSentenceSplitterAnnotator.class));
        pipeline.add(createEngineDescription(RegexTokenizerAnnotator.class,
                RegexTokenizerAnnotator.PARAM_TOKENIZATION_PATTERN,
                RegexTokenizerAnnotator.patterPunctuation));
        pipeline.add(LexicaHelper.getConceptMapper(
                "blueonto1/disease",
                createEngineDescription(RegexTokenizerAnnotator.class,
                        RegexTokenizerAnnotator.PARAM_TOKENIZATION_PATTERN,
                        RegexTokenizerAnnotator.patterPunctuation)));
        
        // test = "Taybi";
        test = "Cayler cardiofacial syndrome";
        // test = "Taybi-Linder syndrome";
        jCas = getTestCas("test with " + test + " test");
        pipeline.process(jCas);
        assertTrue("should contain '" + test + "'",
                exists(jCas, DiseaseDictTerm.class));
    }

    /**
     * @param tests
     *            a list of strings that should be found in the given
     * @param lexica
     */
    private void testWith(String lexica,
            Class<? extends DictTerm> annotationClass, String... tests)
            throws Exception {

        JcasPipelineBuilder pipeline = new JcasPipelineBuilder();
        pipeline.add(LexicaHelper.getConceptMapper(lexica));

        for (String test : tests) {
            JCas jCas = getTokenizedTestCas("test with " + test + " test");
            pipeline.process(jCas);
            assertTrue("lexicon " + lexica + " should contain '" + test + "'",
                    exists(jCas, annotationClass));
        }
    }

    @Test
    public void testBrainregions() throws Exception {

        String[] brs = { "bams2004", "bams2013", "dong", "hof", "paxinos",
                "swanson", "neuronames" };
        for (String br : brs) {

            JCas jCas = getTokenizedTestCas("with claustrum and");
            runPipeline(
                    jCas,
                    LexicaHelper.getConceptMapper("brainregions/" + br),
                    createEngineDescription(
                            DeduplicatorAnnotator.class,
                            PARAM_ANNOTATION_CLASSES,
                            new String[] { "ch.epfl.bbp.uima.types.BrainRegionDictTerm" }));
            Collection<BrainRegionDictTerm> b = select(jCas,
                    BrainRegionDictTerm.class);
            LOG.debug(To.string(b));
            assertEquals("no brain matched with " + br, 1, b.size());
        }
    }

    @Test
    public void testAll() throws Exception {

        FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File arg0, String name) {
                if (newArrayList(".svn", "templates", ".DS_Store").contains(
                        name))
                    return false;
                return true;
            }
        };

        for (File dir : new File("desc").listFiles(filter)) {

            LOG.debug("dir {}", dir.getAbsolutePath());

            for (File descr : dir.listFiles(filter)) {
                LOG.debug("descr {}", descr.getAbsolutePath());
                JCas jCas = getTokenizedTestCas("test with p18 test");
                runPipeline(
                        jCas,
                        LexicaHelper.getConceptMapper(dir.getName()
                                + "/"
                                + descr.getName().replaceAll(
                                        "ConceptMapper\\.xml", "")));
            }
        }
    }
}
