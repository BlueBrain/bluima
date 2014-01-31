package ch.epfl.bbp.uima.cr;

import static ch.epfl.bbp.uima.BlueUima.PARAM_INPUT;
import static ch.epfl.bbp.uima.BlueUima.PARAM_INPUT_DIRECTORY;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.JULIE_TSD;
import static org.apache.uima.util.CasCreationUtils.createCas;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReader;

import java.io.File;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.cas.CAS;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.jcas.JCas;
import org.junit.BeforeClass;
import org.junit.Test;
import org.apache.uima.fit.component.xwriter.HeaderDocIdFileNamer;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;

import ch.epfl.bbp.uima.BlueUima;
import ch.epfl.bbp.uima.ae.output.ZipXWriter;
import ch.epfl.bbp.uima.cr.TextArrayReader;
import ch.epfl.bbp.uima.cr.ZipXmiCollectionReader;
import ch.epfl.bbp.uima.testutils.UimaTests;
import ch.epfl.bbp.uima.typesystem.TypeSystem;
import ch.epfl.bbp.uima.utils.StructuredDirectory;
import de.julielab.jules.types.Header;

/**
 * @author renaud.richardet@epfl.ch
 */
public class ZipXmiCollectionReaderTest {

    private static String testDir;

    @BeforeClass
    public static void before() {
        testDir = new File("target/zipXmiCollectionReaderTest_"
                + System.currentTimeMillis()).getAbsolutePath();
    }

    @Test
    public void testSerializeDeserializeXmi() throws Exception {

        CollectionReader cr = createReader(TextArrayReader.class,
                JULIE_TSD, PARAM_INPUT, new String[] { "this is a test" });
        AnalysisEngine serializer = createEngine(ZipXWriter.class,
                BlueUima.PARAM_OUTPUT_DIR, testDir + "/serdeser");
        SimplePipeline.runPipeline(cr, serializer);

        cr = createReader(ZipXmiCollectionReader.class, JULIE_TSD,
                PARAM_INPUT_DIRECTORY, testDir + "/serdeser");
        CAS cas = createCas(cr.getProcessingResourceMetaData());
        cr.getNext(cas);
        assertEquals("this is a test", cas.getDocumentText());
    }

    @Test
    public void testStructured() throws Exception {

        for (int docId : new int[] { 12345678, 1234, 12345 }) {

            JCas jCas = UimaTests.getTestCas("bla blahh " + docId);
            Header header = new Header(jCas);
            header.setDocId(docId + "");
            header.addToIndexes();

            AnalysisEngine serializer = createEngine(ZipXWriter.class,
                    BlueUima.PARAM_OUTPUT_DIR, testDir,
                    ZipXWriter.PARAM_FILE_NAMER_CLASS_NAME,
                    HeaderDocIdFileNamer.class.getName(),
                    ZipXWriter.PARAM_STRUCTURE_DIR, true);
            SimplePipeline.runPipeline(jCas, serializer);

            File newFile = new File(testDir, StructuredDirectory.getFilePath(
                    docId, "xmi.zip"));
            assertTrue(newFile.exists());

            JCas newJCas = JCasFactory.createJCas(TypeSystem.JULIE_TSD);
            ZipXmiCollectionReader.deserialize(newFile, newJCas, ZipXmiCollectionReader.XMI);
            assertEquals("contents should match", "bla blahh " + docId,
                    newJCas.getDocumentText());
        }
    }

}
