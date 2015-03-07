package ch.epfl.bbp.uima.cr;

import static ch.epfl.bbp.uima.BlueCasUtil.asList;
import static ch.epfl.bbp.uima.BlueCasUtil.getHeaderIntDocId;
import static ch.epfl.bbp.uima.BlueUima.BLUE_UTILS_TEST_BASE;
import static ch.epfl.bbp.uima.BlueUima.PARAM_BETWEEN;
import static ch.epfl.bbp.uima.BlueUima.PARAM_INPUT_DIRECTORY;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReader;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.uima.jcas.JCas;
import org.junit.Test;

public class OneDocPerLineReader3Test {

    @Test
    public void test() throws Exception {
        List<JCas> jCases = asList(createReader(OneDocPerLineReader3.class,
                 PARAM_INPUT_DIRECTORY, BLUE_UTILS_TEST_BASE
                        + "OneDocPerLineReader3", PARAM_BETWEEN, new int[] { 1,
                        300 }));
        assertEquals(2, jCases.size());
        assertEquals(1, getHeaderIntDocId(jCases.get(0)));
        String txt = jCases.get(1).getDocumentText().trim();
        assertTrue(txt.startsWith("These results"));
        assertTrue(txt.endsWith("afferent inputs."));

        jCases = asList(createReader(OneDocPerLineReader3.class,
                PARAM_INPUT_DIRECTORY, BLUE_UTILS_TEST_BASE
                        + "OneDocPerLineReader3", PARAM_BETWEEN, new int[] { 1,
                        302 }));
        assertEquals(3, jCases.size());

    }
}
