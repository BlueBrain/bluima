package ch.epfl.bbp.uima.cr;

import static ch.epfl.bbp.uima.BlueCasUtil.asList;
import static ch.epfl.bbp.uima.BlueUima.PARAM_INPUT;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReader;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.apache.uima.jcas.JCas;
import org.junit.Test;

public class TextArrayReaderTest {

    @Test
    public void test() throws Exception {

        String[] testText = { "This is doc1.", "This is doc2." };

        List<JCas> docs = asList(createReader(TextArrayReader.class,
                PARAM_INPUT, testText));

        assertEquals(2, docs.size());
        assertEquals(testText[0], docs.get(0).getDocumentText());
        assertEquals(testText[1], docs.get(1).getDocumentText());
    }
}
