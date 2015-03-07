package ch.epfl.bbp.uima.cr;

import static ch.epfl.bbp.uima.BlueCasUtil.asList;
import static ch.epfl.bbp.uima.BlueCasUtil.getHeaderIntDocId;
import static ch.epfl.bbp.uima.BlueUima.BLUE_UTILS_TEST_BASE;
import static ch.epfl.bbp.uima.BlueUima.PARAM_INPUT_FILE;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReader;
import static org.apache.uima.fit.util.JCasUtil.selectSingle;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;

import org.apache.uima.jcas.JCas;
import org.junit.Test;

import ch.epfl.bbp.io.TextFileWriter;
import ch.epfl.bbp.uima.BlueUima;
import de.julielab.jules.types.Header;

public class FromFilelistReaderTest {

    @Test
    public void test() throws Exception {

        File tmp = File.createTempFile("FromFilelistReaderTest", null);
        TextFileWriter writer = new TextFileWriter(tmp);
        writer.addLine("1\t" + BLUE_UTILS_TEST_BASE + "testData/1.pdf");
        writer.addLine("seminars\t" + BLUE_UTILS_TEST_BASE
                + "testData/seminars.txt");
        writer.close();

        List<JCas> jCases = asList(createReader(FromFilelistReader.class,
                PARAM_INPUT_FILE, tmp.getAbsolutePath()));
        assertEquals(2, jCases.size());
        assertEquals(1, getHeaderIntDocId(jCases.get(0)));
    }

    @Test
    public void testFormat() throws Exception {

        File tmp = File.createTempFile("FromFilelistReaderTest2", null);
        TextFileWriter writer = new TextFileWriter(tmp);
        writer.addLine(BLUE_UTILS_TEST_BASE + "testData/1.pdf");

        writer.close();

        List<JCas> jCases = asList(createReader(FromFilelistReader.class,
                PARAM_INPUT_FILE, tmp.getAbsolutePath(), BlueUima.PARAM_FORMAT,
                true));
        assertEquals(1, jCases.size());
        assertEquals(1, getHeaderIntDocId(jCases.get(0)));

        Header header = selectSingle(jCases.get(0), Header.class);
        assertEquals(BLUE_UTILS_TEST_BASE + "testData/1.pdf",
                header.getSource());
    }
}
