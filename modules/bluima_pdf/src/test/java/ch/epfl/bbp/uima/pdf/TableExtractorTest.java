package ch.epfl.bbp.uima.pdf;

import java.io.File;
import java.util.ArrayList;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.epfl.bbp.uima.pdf.tables.PdfTextStreamParser;
import ch.epfl.bbp.uima.utils.Resources;
import edu.psu.seersuite.extractors.tableextractor.extraction.IPdfParser;
import edu.psu.seersuite.extractors.tableextractor.extraction.PdfBoxParser;
import edu.psu.seersuite.extractors.tableextractor.extraction.TableExtractor;
import edu.psu.seersuite.extractors.tableextractor.model.Table;

/**
 * @author renaud.richardet@epfl.ch
 */
@Ignore
// FIXME not working!?
public class TableExtractorTest {
    Logger LOG = LoggerFactory.getLogger(TableExtractorTest.class);

    @Test
    public void test() throws Exception {

        TableExtractor extractor = new TableExtractor();
        IPdfParser parser = new PdfBoxParser();
        extractor.setParser(parser);

        File pdfFile = Resources.getFile("pdf/2.pdf");

        ArrayList<Table> extractedTableSet = extractor.extract(pdfFile,
                "target/");

        for (Table table : extractedTableSet) {
            LOG.debug(table.toString());
        }
    }

    @Test
    @Ignore
    // not implemented yet
    public void testPdfTextStream() throws Exception {

        TableExtractor extractor = new TableExtractor();

        IPdfParser parser = new PdfTextStreamParser();
        extractor.setParser(parser);

        File pdfFile = Resources.getFile("pdf/1.pdf");

        ArrayList<Table> extractedTableSet = extractor.extract(pdfFile,
                "target/");

        for (Table table : extractedTableSet) {
            LOG.debug(table.toString());
        }
    }
}
