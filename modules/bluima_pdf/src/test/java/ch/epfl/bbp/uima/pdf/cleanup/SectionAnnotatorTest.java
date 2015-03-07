package ch.epfl.bbp.uima.pdf.cleanup;

import static ch.epfl.bbp.uima.BlueUima.PARAM_INPUT_DIRECTORY;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReader;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;
import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;

import ch.epfl.bbp.uima.filter.SectionAnnotator;
import ch.epfl.bbp.uima.filter.SectionRegexAnnotator;
import ch.epfl.bbp.uima.pdf.cr.PdfCollectionReader;

public class SectionAnnotatorTest {

    @Test
    @Ignore
    public void test() throws Exception {

        //String pdfCorpus = "/Volumes/HDD2/ren_data/data_hdd/ontologies/BAMS/pdfs/crawled_bams_pdfs";
        //String pdfCorpus = "/Volumes/scratch/richarde/pdfs/ic_channelpedia";
        String pdfCorpus = "/Users/richarde/Desktop/docs2";

        runPipeline(
                createReader(PdfCollectionReader.class, 
                        PARAM_INPUT_DIRECTORY, pdfCorpus),
                createEngine(SectionAnnotator.class),
                createEngine(SectionRegexAnnotator.class));
    }

    @Test
    @Ignore
    public void testOnePdf() throws Exception {

        String pdfFile = "/Users/richarde/dev/bluebrain/svn_nlp/UIMA/blue_uima/trunk/modules/bluima_pdf/src/test/resources/pdf_section/matmet";

        runPipeline(
                createReader(PdfCollectionReader.class, 
                        PARAM_INPUT_DIRECTORY, pdfFile),
                createEngine(SectionAnnotator.class));
    }

    @Test
    public void testCleanBlockTest() throws Exception {
        assertEquals("uppercase", "Hello World",
                SectionAnnotator.cleanBlockText("Hello World"));
        assertEquals("too short", null,
                SectionAnnotator.cleanBlockText("Hello"));
    }
}
