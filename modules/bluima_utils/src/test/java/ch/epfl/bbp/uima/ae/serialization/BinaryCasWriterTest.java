package ch.epfl.bbp.uima.ae.serialization;

import static ch.epfl.bbp.uima.BlueCasUtil.getHeaderIntDocId;
import static ch.epfl.bbp.uima.BlueUima.PARAM_INPUT_DIRECTORY;
import static ch.epfl.bbp.uima.BlueUima.PARAM_OUTPUT_DIR;
import static java.lang.System.currentTimeMillis;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReader;
import static org.apache.uima.fit.factory.TypeSystemDescriptionFactory.createTypeSystemDescription;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;
import static org.apache.uima.util.CasCreationUtils.createCas;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.cas.CAS;
import org.apache.uima.collection.CollectionReader;
import org.junit.Test;

import ch.epfl.bbp.uima.ae.NaiveSentenceSplitterAnnotator;
import ch.epfl.bbp.uima.ae.RegexTokenizerAnnotator;
import ch.epfl.bbp.uima.cr.SingleAbstractReader;

public class BinaryCasWriterTest {

    @Test
    public void test() throws Exception {
        String out = "target/BinaryCasWriterTest_" + currentTimeMillis() + "/";

        // WRITING
        CollectionReader cr = createReader(SingleAbstractReader.class);
        AnalysisEngine writer = createEngine(BinaryCasWriter.class,
                PARAM_OUTPUT_DIR, out);
        runPipeline(cr, createEngine(NaiveSentenceSplitterAnnotator.class),
                createEngine(RegexTokenizerAnnotator.class), writer);

        assertTrue(new File(out + "1/957/687.gz").exists());

        // READING
        CollectionReader reader = createReader(BinaryCasReader.class,
                PARAM_INPUT_DIRECTORY, out);
        CAS cas = createCas(createTypeSystemDescription(), null, null);
        reader.getNext(cas);

        assertEquals(SingleAbstractReader.getText(), cas.getDocumentText());
        assertEquals(SingleAbstractReader.getPmId(),
                getHeaderIntDocId(cas.getJCas()));
    }

    String out = "target/BinaryCasWriterTest_1/";

}
