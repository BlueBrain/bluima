package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.testutils.UimaTests.getTestCas;
import static org.junit.Assert.assertEquals;
import static org.slf4j.LoggerFactory.getLogger;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.util.Collection;

import org.apache.uima.jcas.JCas;
import org.junit.Test;
import org.slf4j.Logger;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.fit.util.JCasUtil;

import ch.epfl.bbp.uima.ae.OscarAnnotator;
import ch.epfl.bbp.uima.testutils.UimaTests;
import ch.epfl.bbp.uima.typesystem.To;
import de.julielab.jules.types.Chemical;

public class OscarAnnotatorTest {
    private static Logger LOG = getLogger(OscarAnnotatorTest.class);

    @Test
    public void test() throws Exception {

        JCas jCas = getTestCas("Synthesis of the brown dropwise Hyperbranched Macroligands via Michael Addition of Butyl or Ethyl Acrylate with HPEI. The synthetic procedure for partially EA- or BA-modified HPEI is exemplified for HPEI25K-EA0.79: 1.00 g of HPEI25K (Mn = 2.50 x 104, 23.3 mmol of amine groups) was dissolved in 5.00 mL of THF, and then 2.52 mL (23.3 mmol) of EA was added. The mixture was stirred at room temperature for 24 h and subsequently at 50 °C for another 24 h. 6 wt. % at 333 K.");

        runPipeline(jCas, OscarAnnotator.getAED());

        Collection<Chemical> chemicals = select(jCas, Chemical.class);
        LOG.debug(To.string("matched chemicals", chemicals));
        assertEquals(8, chemicals.size());
        // UimaTests.assertResultsContains(chemicals, "id", 1);
    }

    @Test
    public void testDihydrotestosterone() throws Exception {

        //JCas jCas = getTestCas("Synthesis of 5 alpha-DHT is difficult to realise in laboratory.");
        JCas jCas = getTestCas("Synthesis of 5 α-DHT is difficult to realise in laboratory.");

        runPipeline(jCas, OscarAnnotator.getAED());

        Collection<Chemical> chemicals = select(jCas, Chemical.class);
        LOG.debug(To.string("matched chemicals", chemicals));
        assertEquals(1, chemicals.size());
    }
}
