package ch.epfl.bbp.nlp.ie.proteinconc.ae;


import static ch.epfl.bbp.uima.ae.MeasureRegexAnnotators.getMeasuresAED;
import static ch.epfl.bbp.uima.testutils.UimaTests.getTokenizedTestCas;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;
import static org.apache.uima.fit.util.JCasUtil.select;
import static org.junit.Assert.assertEquals;

import org.apache.uima.jcas.JCas;
import org.junit.Test;

import ch.epfl.bbp.uima.ae.BannerHelper;
import ch.epfl.bbp.uima.types.Protein;
import ch.epfl.bbp.uima.typesystem.To;

public class BannerMTest {
    //TODO Write regression test to compare with Banner

    @Test
    public void testBANNERTokenizationImprovement() throws Exception {
        String sentence = "To demonstrate that indeed the shift in molecular mass is due to N-linked glycosylation, oocytes injected with Kv4.2 and HA/DPP10 were injected and incubated with 2.5 mg/ml tunicamycin to prevent glycosylation.Cells were lysed in a buffer containing 50 mM Tris at pH 7.5, 1% Triton X-100, 150 mM NaCl, 5 mM EDTA, 1 µg/ml pepstatin A, 1 µg/ml  leupeptin, 2 µg/ml aprotinin, 1 mM PMSF, 0.1 mg/ml benz- 21.";
        JCas cas = getTokenizedTestCas(sentence);

        String BANNERCoveredText[] = {"HA", "DPP10", "pepstatin A, 1 µg", "ml  leupeptin"};

        runPipeline(cas, BannerHelper.getBanner());

        int i = 0;
        for (Protein p : select(cas, Protein.class)) {
            System.out.println(To.string(p));
            assertEquals(BANNERCoveredText[i], p.getCoveredText());
            ++i;
        }

        JCas cas2 = getTokenizedTestCas(sentence);

        runPipeline(cas2,
                getMeasuresAED(),
                createEngineDescription(BannerMAnnotator.class));

        i = 0;

        //note that it still weird that 'd ' is detected as a protein entity mention
        String BannerMCoveredText[] = {"d ", "A/DPP", "pepstatin A", "leupeptin", "aprotinin", "PMSF"};
        for (Protein p : select(cas2, Protein.class)) {
            System.out.println(To.string(p));
            assertEquals(BannerMCoveredText[i], p.getCoveredText());
            ++i;
        }


    }
}
