package ch.epfl.bbp.uima.cr;

import static ch.epfl.bbp.uima.BlueCasUtil.asList;
import static ch.epfl.bbp.uima.BlueUima.PARAM_INPUT;
import static ch.epfl.bbp.uima.BlueUima.PARAM_INPUT_DIRECTORY;
import static ch.epfl.bbp.uima.BlueUima.PARAM_OUTPUT_DIR;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.JULIE_TSD;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReader;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.jcas.JCas;
import org.junit.BeforeClass;
import org.junit.Test;

import ch.epfl.bbp.uima.ae.output.XWriter;

/**
 * @author renaud.richardet@epfl.ch
 */
public class XmiCollectionReaderTest {

    private static String testDir;

    @BeforeClass
    public static void before() {
        testDir = new File("target/xmiCollectionReaderTest_"
                + System.currentTimeMillis()).getAbsolutePath();
    }

    @Test
    public void testSerializeDeserializeXmi() throws Exception {

        // serialize
        CollectionReader cr = createReader(TextArrayReader.class,
                JULIE_TSD, PARAM_INPUT, new String[] { "this is a test" });
        AnalysisEngine serializer = createEngine(XWriter.class,
                PARAM_OUTPUT_DIR, testDir + "/serdeser");
        runPipeline(cr, serializer);

        // deserialize
        cr = createReader(XCollectionReader.class, JULIE_TSD,
                PARAM_INPUT_DIRECTORY, testDir + "/serdeser");
        List<JCas> deserializedCases = asList(cr);
        assertEquals(1, deserializedCases.size());

        assertEquals("this is a test", deserializedCases.get(0)
                .getDocumentText());
    }
}
