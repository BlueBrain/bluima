package ch.epfl.bbp.uima.cr;

import static ch.epfl.bbp.uima.BlueCasUtil.asList;
import static ch.epfl.bbp.uima.BlueUima.BLUE_UTILS_TEST_BASE;
import static ch.epfl.bbp.uima.BlueUima.PARAM_INPUT_DIRECTORY;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReader;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.uima.jcas.JCas;
import org.junit.Test;

public class TextFileReaderTest {

    @Test
    public void test() throws Exception {

        List<JCas> jCases = asList(createReader(TextFileReader.class,
                PARAM_INPUT_DIRECTORY, BLUE_UTILS_TEST_BASE + "testData"));
        assertEquals(1, jCases.size());
        assertTrue(jCases.get(0).getDocumentText().startsWith("April 7"));
    }
}
