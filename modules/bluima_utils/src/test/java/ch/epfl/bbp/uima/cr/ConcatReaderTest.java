package ch.epfl.bbp.uima.cr;

import static ch.epfl.bbp.uima.BlueCasUtil.asList;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReader;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.apache.uima.jcas.JCas;
import org.junit.Test;

public class ConcatReaderTest {

    @Test
    public void test() throws Exception {

        List<JCas> jCases = asList(createReader(ConcatReader.class));
        assertEquals(2, jCases.size());

    }

}
