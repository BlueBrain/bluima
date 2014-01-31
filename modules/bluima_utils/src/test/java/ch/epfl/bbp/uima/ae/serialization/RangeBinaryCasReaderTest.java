package ch.epfl.bbp.uima.ae.serialization;

import static ch.epfl.bbp.uima.BlueCasUtil.asList;
import static ch.epfl.bbp.uima.BlueCasUtil.setDocId;
import static ch.epfl.bbp.uima.BlueUima.PARAM_BETWEEN;
import static ch.epfl.bbp.uima.BlueUima.PARAM_INPUT_DIRECTORY;
import static ch.epfl.bbp.uima.ae.StatsAnnotatorPlus.PARAM_PRINT_EVERY;
import static ch.epfl.bbp.uima.testutils.UimaTests.getTestCas;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.JULIE_TSD;
import static java.lang.System.currentTimeMillis;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReader;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReaderDescription;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.apache.uima.jcas.JCas;
import org.junit.Test;

import ch.epfl.bbp.uima.BlueUima;
import ch.epfl.bbp.uima.ae.StatsAnnotatorPlus;
import ch.epfl.bbp.uima.uimafit.CpeBuilder;
import ch.epfl.bbp.uima.uimafit.JcasPipelineBuilder;

public class RangeBinaryCasReaderTest {

    @Test
    public void test() throws Exception {
        String out = "target/BinaryCasReaderTest_" + currentTimeMillis() + "/";

        // WRITING
        JcasPipelineBuilder p = new JcasPipelineBuilder();
        p.add(BinaryCasWriter.class, BlueUima.PARAM_OUTPUT_DIR, out);

        JCas testCas1 = getTestCas("test1");
        setDocId(testCas1, 1);
        p.process(testCas1);
        assertTrue(new File(out + "0/0/1.gz").exists());

        JCas testCas4 = getTestCas("test4");
        setDocId(testCas4, 4);
        p.process(testCas4);

        JCas testCas4000 = getTestCas("test4000");
        setDocId(testCas4000, 4000);
        p.process(testCas4000);
        assertTrue(new File(out + "0/4/0.gz").exists());

        // READING
        List<JCas> res = asList(createReader(RangeBinaryCasReader.class,
                JULIE_TSD, PARAM_INPUT_DIRECTORY, out, PARAM_BETWEEN,
                new int[] { 1, 5000 }));
        assertEquals("with upper bound 5000, all 3 test cases are returned", 3,
                res.size());

        res = asList(createReader(RangeBinaryCasReader.class, JULIE_TSD,
                PARAM_INPUT_DIRECTORY, out, PARAM_BETWEEN, new int[] { 1, 500 }));
        assertEquals(
                "with upper bound 500, only first 2 test cases are returned",
                2, res.size());

        res = asList(createReader(RangeBinaryCasReader.class, JULIE_TSD,
                PARAM_INPUT_DIRECTORY, out, PARAM_BETWEEN, new int[] { 10000,
                        10500 }));
        assertEquals("with upper bound too high, NO docs are returned", 0,
                res.size());
    }

    public static void main(String[] args) throws Exception {
        String out = "/Volumes/simulation/nlp/data/20131120_preprocessed/";

        CpeBuilder.runPipeline(
                createReaderDescription(RangeBinaryCasReader.class, JULIE_TSD,
                        PARAM_INPUT_DIRECTORY, out, PARAM_BETWEEN, new int[] {
                                15000000, 15005000 }),
                createEngineDescription(StatsAnnotatorPlus.class,
                        PARAM_PRINT_EVERY, 10));
    }
}
