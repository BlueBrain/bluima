package ch.epfl.bbp.uima.pdf.cr;

import static ch.epfl.bbp.uima.BlueCasUtil.asList;
import static ch.epfl.bbp.uima.BlueCasUtil.getHeaderDocId;
import static ch.epfl.bbp.uima.BlueCasUtil.getHeaderIntDocId;
import static ch.epfl.bbp.uima.BlueUima.PARAM_DIRECTORY_ITERATOR;
import static ch.epfl.bbp.uima.BlueUima.PARAM_INPUT_DIRECTORY;
import static ch.epfl.bbp.uima.BlueUima.PARAM_INPUT_FILE;
import static ch.epfl.bbp.uima.BlueUima.PARAM_OUTPUT_DIR;
import static ch.epfl.bbp.uima.io.DirectoryIterator.ZIP;
import static ch.epfl.bbp.uima.pdf.cr.PdfCollectionReader.PARAM_EXPAND_ABBREVIATIONS;
import static ch.epfl.bbp.uima.pdf.cr.PdfCollectionReader.PARAM_EXTRACT_TABLES;
import static ch.epfl.bbp.uima.pdf.cr.PdfCollectionReader.extractReferencesNaively;
import static ch.epfl.bbp.uima.testutils.UimaTests.getTestCas;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.JULIE_TSD;
import static ch.epfl.bbp.uima.utils.DataTableUtils.toHtml;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReader;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReaderDescription;
import static org.apache.uima.fit.pipeline.SimplePipeline.iteratePipeline;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;
import static org.apache.uima.fit.util.JCasUtil.select;
import static org.apache.uima.fit.util.JCasUtil.selectSingle;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.slf4j.LoggerFactory.getLogger;

import java.io.IOException;
import java.util.Collection;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.pipeline.JCasIterator;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.jcas.JCas;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;

import ch.epfl.bbp.StringUtils;
import ch.epfl.bbp.io.TextFileWriter;
import ch.epfl.bbp.uima.ae.output.DocumentTextWriter;
import ch.epfl.bbp.uima.cr.SingleFileReader;
import ch.epfl.bbp.uima.types.DataTable;
import de.julielab.jules.types.Header;

/**
 * @author renaud.richardet@epfl.ch
 */
public class PdfCollectionReaderTest {
    Logger LOG = getLogger(PdfCollectionReaderTest.class);

    @Test
    public void test() throws Exception {

        String start[] = { "R E S E A R C H", "BMC Oral Health" };
        for (JCas pdf : asList(createReader(PdfCollectionReader.class,
                JULIE_TSD, PARAM_INPUT_DIRECTORY, "pdf", PARAM_EXTRACT_TABLES,
                true))) {

            int id = getHeaderIntDocId(pdf);
            LOG.debug("docid:{}, text:{}", id,
                    StringUtils.snippetize(pdf.getDocumentText(), 200));

            if (id == 1 || id == 2) {
                assertEquals(start[id - 1],
                        pdf.getDocumentText().substring(0, 15));

            }
            // check for tables FIXME
            // Collection<DataTable> tables = select(cas.getJCas(),
            // DataTable.class);
            // assertTrue(tables.size() > 0);
            // for (DataTable table : tables) {
            // LOG.debug(DataTableUtils.toHtml(table));
            // }
        }
    }

    @Test
    public void testAbbrevs() throws Exception {

        final String abbrevs[][] = { { "PMF", "MLT" }, {}, { "ICC", "HVA" } };

        for (JCas pdf : asList(createReader(PdfCollectionReader.class,
                JULIE_TSD, PARAM_INPUT_DIRECTORY, "pdf", PARAM_EXTRACT_TABLES,
                true, PARAM_EXPAND_ABBREVIATIONS, true))) {

            int id = getHeaderIntDocId(pdf) - 1;
            String pdfText = pdf.getDocumentText();
            System.out.println(pdfText);
            for (String abbrev : abbrevs[id]) {
                assertTrue("all abbreviations '" + abbrev
                        + "' should be removed in:: " + pdfText,
                        pdfText.indexOf(abbrev) == -1);
            }
        }
    }

    @Test
    public void testZips() throws Exception {

        JCasIterator it = iteratePipeline(
                createReaderDescription(PdfCollectionReader.class, JULIE_TSD,
                        PARAM_INPUT_DIRECTORY, "pdf/zipPdfs.zip",
                        PARAM_DIRECTORY_ITERATOR, ZIP)).iterator();
        int i = 0;
        while (it.hasNext()) {
            JCas cas = it.next();

            // check file name
            Header header = selectSingle(cas, Header.class);
            LOG.debug("docid:{}, text:{}", header.getDocId(),
                    cas.getDocumentText());
            assertEquals((i + 1) + "", header.getDocId());
        }
    }

    @Test
    @Ignore
    // LATER
    public void testFails() throws Exception {

        CollectionReader cr = createReader(PdfCollectionReader.class,
                JULIE_TSD, PARAM_INPUT_DIRECTORY, "pdf_fail");

        for (JCas jCas : asList(cr)) {
            LOG.warn("docid:{}, text:{}", getHeaderDocId(jCas),
                    jCas.getDocumentText());
        }
        cr.close();
    }

    @Test
    @Ignore
    // For debugging
    public void testSinglePdfForGlyphMapping() throws Exception {

        runPipeline(
                createReaderDescription(SingleFileReader.class, JULIE_TSD,
                        PARAM_INPUT_FILE, "src/test/resources/pdf_fail/30.pdf"), //
                createEngineDescription(PdfCollectionAnnotator.class));
    }

    @Test
    @Ignore
    public void testExtractTablesOnSample() throws Exception {

        CollectionReader cr = createReader(PdfCollectionReader.class,
                JULIE_TSD, PARAM_INPUT_DIRECTORY,
                "/Users/richarde/data/_papers_etc/pmc_pdfs_sample");

        AnalysisEngine dumper = createEngine(TableWriter.class);

        SimplePipeline.runPipeline(cr, dumper);
    }

    public static class TableWriter extends JCasAnnotator_ImplBase {

        @Override
        public void process(JCas jCas) throws AnalysisEngineProcessException {

            String docId = getHeaderDocId(jCas);

            Collection<DataTable> tables = select(jCas, DataTable.class);
            if (!tables.isEmpty()) {
                try {
                    TextFileWriter writer = new TextFileWriter(
                            "/Users/richarde/data/_papers_etc/pmc_pdfs_sample/"
                                    + docId + ".html");
                    for (DataTable table : tables) {
                        writer.addLine(toHtml(table));
                    }
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    @Ignore
    public void testOnSampleForExtractionQuality() throws Exception {

        CollectionReader cr = createReader(
                PdfCollectionReader.class,
                JULIE_TSD,
                PARAM_INPUT_DIRECTORY,
                "/Users/richarde/data_hdd/_papers_etc/pubmed/sample_pdfs_68/pdfs",
                PARAM_EXPAND_ABBREVIATIONS, true);

        AnalysisEngine dumper = createEngine(DocumentTextWriter.class,
                PARAM_OUTPUT_DIR, "/Users/richarde/Desktop/");

        runPipeline(cr, dumper);
    }

    @Test
    @Ignore
    public void testOnSampleForSrikanth() throws Exception {

        CollectionReader cr = createReader(PdfCollectionReader.class,
                JULIE_TSD, PARAM_INPUT_DIRECTORY, "pdf_srikanth");

        AnalysisEngine dumper = createEngine(DocumentTextWriter.class,
                PARAM_OUTPUT_DIR, "/Users/richarde/Desktop/");

        SimplePipeline.runPipeline(cr, dumper);
    }

    @Test
    @Ignore
    // TODO fails
    public void tesTextractReferencesNaively() throws Exception {
        assertFalse(extractReferencesNaively(getTestCas("no\nrefs\nhere")));
        assertTrue(extractReferencesNaively(getTestCas("some\nReferences\nhere")));
    }
}
