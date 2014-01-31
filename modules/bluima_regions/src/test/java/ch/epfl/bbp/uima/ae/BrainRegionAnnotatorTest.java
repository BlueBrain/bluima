package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.BlueUima.PARAM_MODEL_FILE;
import static ch.epfl.bbp.uima.BrainRegionsHelper.TEST_BASE;
import static ch.epfl.bbp.uima.laucher.PipelineScriptParser.parse;
import static ch.epfl.bbp.uima.testutils.UimaTests.assertResultsContainsText;
import static ch.epfl.bbp.uima.testutils.UimaTests.getTestCas;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.apache.uima.fit.util.JCasUtil.select;
import static org.junit.Assert.assertEquals;
import static org.slf4j.LoggerFactory.getLogger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Collection;

import org.apache.uima.UIMAException;
import org.apache.uima.jcas.JCas;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;

import cc.mallet.fst.CRF;
import ch.epfl.bbp.uima.BrainRegionsHelper;
import ch.epfl.bbp.uima.laucher.Pipeline;
import ch.epfl.bbp.uima.types.BrainRegion;
import ch.epfl.bbp.uima.typesystem.To;

/**
 * @author renaud.richardet@epfl.ch
 */
public class BrainRegionAnnotatorTest {
    private static Logger LOG = getLogger(BrainRegionAnnotatorTest.class);

    private static final String MODEL_FILE = BrainRegionsHelper.BRAIN_REGIONS_HOME
            + "resources/models/20131116_BrainRegion.model";

    @Test
    public void testInfer() throws Exception {

        infer("Sex differences in the gross size of the rat neocortex.", 1,
                "neocortex");// 1506479

        infer("Corticopontine remodelling after cortical and/or cerebellar lesions in newborn rats.",
                2, "cortical", "cerebellar");// 6619329

        infer("We examined the organization of descending projections from auditory and adjacent cortical areas to the inferior colliculus (IC) in the rat by using the retrograde and anterograde transport of wheat germ agglutinin-horseradish peroxidase.",//
                2, "auditory and adjacent cortical areas",
                "inferior colliculus");

        // should not find BR in these
        infer("To each well, 100 l of low-potassium buffer (135 mM NaCl, 4.6 mM KCl, 1 mM CaCl2, 2 mM MgCl2, 0.2% bovine serum albumin, and 10 mM HEPES, pH 7.4, with NaOH) with or without test samples was added, and incubation continued for 30 min at 37°C.",//
                0);
        infer("Patch–clamp pipettes (2.5–3.5 MX) were filled with (in mM): KGluconate 142, NaGluconate 4, Ca1/2Gluconate 0.8 (free Ca2): 95 nM), Mg1/2Gluconate 2 (free Mg2): 535 nM), EGTA 1, ATPK2 3, and Hepes 5 (pH 7.2).",
                0);
        infer("Quantification of the responses of Kv3.3 currents to 10 nM Bryostatin-1 (B, n 7) or 5 M PseudoRack1 (D, n inactivation (p 0.006 and p 4) or 5 M PKC-(19 –31) (F, n 9) and the response of Kv3.3 currents to these PKC activators in the presence of PKC pseudosubstrate PKC-(19 –31) (n 4 and 6 for Bryostatin-1 and PseudoRack1, respectively).",
                0);
        infer("Error bars are mean 7 s.e.m.Table 1 Effects of apamin on synaptically evoked EPSPsCondition (n) EPSP (mV) Slope (V/s) HW (ms) Rise time (ms)Control 3.3 7 0.4 0.84 7 0.13 35 7 4 4.5 7 0.2 Apamin 5.6 7 0.5 (173 7 16%)a 1.14 7 0.09 (141 7 12%)a 40 7 3 5.0 7 0.1 Apamin + AP5 (5) 2.7 7 0.6 (79 7 13%) 0.70 7 0.10 (84 7 8%) 33 7 5 4.9 7 0.3Control 3.4 7 0.5 0.72 7 0.11 44 7 9 6.6 7 1.0 dTC 7.0 7 1.2 (216 7 53%)a 0.96 7 0.09 (146 7 23%)a 47 7 6 8.7 7 0.9 Washout (6) 5.1 7 0.8 (154 7 21%) 0.90 7 0.10 (131 7 11%)a 41 7 10 6.1 7 0.9Control 4.1 7 0.3 0.96 7 0.07 40 7 8 4.6 7 0.6 AP5 3.2 7 0.3 (78 7 5%)a 0.76 7 0.05 (80 7 4%)a 44 7 8 4.8 7 0.5 AP5 + Apamin (7) 3.1 7 0.3 (96 7 3%) 0.71 7 0.06 (93 7 5%) 48 7 8 5.9 7 1.1Control (0.2 mM Mg2+) 1.7 7 0.2 0.62 7 0.07 56 7 7 7.4 7 1.4 Apamin (6) 4.3 7 0.2 (266 7 29%)a,b 1.00 7 0.08 (167 7 10%)a 43 7 3 6.1 7 1.1Control (CNQX) 1.3 7 0.3 0.5 7 0.2 82 7 22 10.8 7 3.2 Apamin (9) 4.1 7 1.0 (320 7 52%)a 0.7 7 0.2 (171 7 20%)a 105 7 26 16.1 7 4.5 AP5 (5) 0.3 7 0.1 (12 7 18%) nd Nd ndControl (5 mM BAPTA) 2.5 7 0.6 1.55 7 0.61 35 7 4 5.0 7 0.5 Apamin (6) 2.1 7 0.5 (89 7 3%) 1.47 7 0.57 (104 7 12%) 35 7 4 4.8 7 0.8Control (5 mM EGTA) 3.5 7 0.7 0.86 7 0.12 40 7 5 4.4 7 0.7 Apamin (8) 6.7 7 0.9 (214 7 32%)a 1.41 7 0.17 (170 7 15%)a 35 7 5 4.4 7 0.3Control (1 mM BAPTA) 5.8 7 0.8 1.16 7 0.12 27 7 8 4.6 7 0.5 Apamin (5) 7.8 7 0.7 (140 7 11%)a 1.66 7 0.13 (147 7 15%)a 27 7 7 4.5 7 0.5Properties of synaptically evoked responses.",
                0);

        infer("For example, 500 nM BDS-II inhibited Kv3.1a by 76.1 5.9% (n 9) at 10 mV but by only 18.3 5.6% (n 8) at 70 mV.",
                0);
        infer("Bath application of 100 nM ShK had no effect on resting membrane potential (Vm; 73.0 5.5 and 75.0 5.2 mV in control and after addition of ShK, respectively; n 4) but produced a small increase in membrane resistance (Rm) from 112 52 M under control conditions to 139 61 M after ShK application (an increase of 26 20% over control; n 4).",
                0);

        /*-
        Persechini and Cronk (1999) estimated that, although under normal conditions high-affinity (KD 10 nM) CaM binding proteins are able to bind CaM, low-affinity (KD 100 nM) CaM binding proteins are able to bind CaM only if the local concentration of free CaM can be increased significantly.
        Table 2 Parameters for the fast inactivation and slow inactivation of wild-type and mutant channelsParameters of the channels indicated were determined in the absence and presence of 500 nM BmK I.Fast inactivation Slow inactivationControl BmK I at 500 nM Control BmK I at 500 nMChannel V f (mV) k f (mV) V f (mV) k f (mV) V s (mV) ks (mV) V s (mV) ks (mV) nrNav1.2α/β1
        Mouse tails were incubated for 20 min at 95◦C in a dissociation solution consisting of 25 mM NaOH and 0.2 mM EDTA; the reaction was then stopped with 40 mM Tris-HCl.
        Spinal cords were minced and homogenized in ice-cold homogenization buffer (0.32 M sucrose, 5 mM TrisHCl, pH 7.4, 2 mM EDTA and 1× protease inhibitor mixture, consisting of 0.5 mM APMSF with leupeptin, aprotinin, and pepstatin A at 1 μg/ml each).
        A significant contribution of GABA acting on ionotropic GABA or G protein–coupled GABA AB receptors could be excluded, because all experiments were performed in the continuous presence of the GABA receptor A blocker bicuculline (10 M) and additional application of the GABA receptor antag- B onist CGP55845 (100 M) did not prevent inhibition ( NMDA-EPSC: 37.2 10.2%, n 8, P 0.01) (Fig. 1B).
        BmK I pro-solution (1 mM BmK I plus 4 mM BSA/L ddH2O) was diluted into the designed concentrations with ND96 medium (in mM: 96 NaCl, 2 KCl, 1.8 CaCl2, 1 MgCl2, and 5 HEPES, pH 7.5, supplemented with 5 mM pyruvate and 0.1 mg/ml gentamicin).

        Hearts were then perfused with 50 ml of Buffer A containing 0.2-0.8 mg/ml collagenase B (Boehringer Mannheim Biochemicals, Indianapolis, IN) and 10 ~M CaCI2, and the flow rate was monitored continuously; the temperatures of the tissue and the perfusate were maintained at 34-35~ The enzyme solution was filtered (at 5 p~m)and recirculated through the heart until the flow rate doubled from its initial value (12-20 min); the lower two-thirds of the left and right ventricles were then removed and minced in the enzyme-containing Buffer A.

        By comparison, 10 M RTG alone increases current at 50 mV by 1.5 0.1 fold, accelerates channel activation and delayed deactivation, and left shifts the activation curve dramatically (V1/2 of 37 mV) (Fig. 5 A and B and SI Fig.

        For studying K+ currents the pipette solution contained (in mM): 97.5 potassium gluconate, 32.5 KCl, 10 Hepes, 5 EGTA (or 10 BAPTA) 0.01 ZD 7288 (to block I h) and 1 MgCl2 (pH 7.2 with KOH) and had series resistances of 4–12 M . The perfusing aCSF contained 1 μM tetrodotoxin (TTX) to block I Na, and usually contained 3 mM TEA and 10 nM dendrotoxin-I (DTx-I) unless otherwise stated.
        Synaptic currents wereControlControl DTx-I 10nM TEA 3mM23 mV3 mV-17 mVDTx-I 10nM, TEA 3mM2 nA 50 msconstructed using a reversal potential of 0 mV and were of the form:I = B × G a × (v - 0) + C × G b × (v - 0) (3)where v was voltage, G was conductance and B (and C) were given by,X = z × (exp(-t/0.75)
         */
    }

    private static void infer(String txt, int expectedBrCnt,
            String... expectedBrs) throws UIMAException, IOException,
            ParseException {

        JCas jCas = getTestCas(txt);
        Pipeline pipeline = parse(new File(TEST_BASE
                + "pipelines/preprocess.incl"));
        pipeline.addAe(createEngineDescription(BrainRegionAnnotator.class,
                PARAM_MODEL_FILE, MODEL_FILE));
        pipeline.run(jCas);

        Collection<BrainRegion> brs = select(jCas, BrainRegion.class);
        for (BrainRegion br : brs) {
            LOG.debug(To.string(br));
        }
        assertEquals("wrong number of brain regions found", expectedBrCnt,
                brs.size());
        if (expectedBrCnt > 0 && expectedBrs.length > 0)
            assertResultsContainsText(brs, expectedBrs);
    }

    @Test
    @Ignore
    public void testPrintFactors() throws Exception {
        ObjectInputStream s = new ObjectInputStream(new FileInputStream(
                MODEL_FILE));
        CRF trainedCrf = (CRF) s.readObject();
        s.close();
        // Factors factors = trainedCrf.getParameters();

        PrintWriter out = new PrintWriter("target/crf.print.txt");
        trainedCrf.print(out);
        out.close();
    }

    @Test
    @Ignore
    public void testMalletEval() throws Exception {
        parse(new File(TEST_BASE + "pipelines/testMalletEval.pipeline")).run();
    }

    @Test
    @Ignore
    public void testUimaEval() throws Exception {
        parse(new File(TEST_BASE + "pipelines/testUimaEval.pipeline")).run();
    }

    @Test
    @Ignore
    public void testUscTteEval() throws Exception {
        parse(new File(TEST_BASE + "pipelines/testUscTteEval.pipeline")).run();
    }

    @Test
    @Ignore
    public void testTrain() throws Exception {
        parse(new File(TEST_BASE + "pipelines/testTrain.pipeline")).run();
    }

    public static void main(String[] args) throws Exception {
        new BrainRegionAnnotatorTest().testTrain();
    }
}
