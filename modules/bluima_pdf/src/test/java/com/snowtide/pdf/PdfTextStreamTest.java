package com.snowtide.pdf;

import static org.slf4j.LoggerFactory.getLogger;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;

/**
 * Prints out the text of a pdf, using the raw PdfTextStream API. Useful to
 * diagnose PdfTextStream problems.
 * 
 * @author renaud.richardet@epfl.ch
 */
@Ignore
public class PdfTextStreamTest {
    Logger LOG = getLogger(PdfTextStreamTest.class);

    @Test
    public void test12834440() throws Exception {

        // File pdfFilePath = new File(
        // "/Users/richarde/Desktop/BBP_experiments/9_LDA/20130608_fix_preprocessing/TooMuchOOV_check_files/NaN/Adv Second Messenger Phosphoprotein Res 1995 Rosen.pdf");

        final String root = "/Volumes/HDD2/ren_data/data_hdd/_papers_etc/bbp_people/people/";

        // harrison_pres people/emuller/gpu/harrison_pres.pdf 0.298404754
        // 87009.49953
        // File pdfFilePath = new File(
        // "/Volumes/HDD2/ren_data/data_hdd/_papers_etc/bbp_people/people/emuller/gpu/harrison_pres.pdf");

        // File pdfFilePath = new File(
        // "/Volumes/HDD2/ren_data/data_hdd/_papers_etc/bbp_people/people/martin_zotero/G42AAH3A/Proc Natl Acad Sci USA 1989 Tamura.pdf");
        // File pdfFilePath = new File(
        // "/Volumes/HDD2/ren_data/data_hdd/_papers_etc/bbp_people/people/martin_zotero/3QAEDGS4/Endocrinology 1980 Parsons.pdf");
        // File pdfFilePath = new File(
        // "/Volumes/HDD2/ren_data/data_hdd/_papers_etc/bbp_people/people/joe/Kolta 1997.pdf");

        File pdfFilePath = new File(root
                + "shruti/Misc/Sakmann and Stuart 94.pdf");

        pdfFilePath = new File(
                "/Users/richarde/dev/bluebrain/svn_nlp/UIMA/blue_uima/trunk/modules/blue_uima_pdf/src/test/resources/pdf_fail/46.pdf");
        pdfFilePath = new File(
                "/Volumes/HDD2/ren_data/data_hdd/ontologies/BAMS/pdfs/crawled_bams_pdfs/10720617.pdf");
        pdfFilePath = new File(
                "/Volumes/HDD2/ren_data/data_hdd/ontologies/BAMS/pdfs/crawled_bams_pdfs/11069955.pdf");
        pdfFilePath = new File("/Users/richarde/Desktop/4.pdf");
        pdfFilePath = new File(
                "/Users/richarde/dev/bluebrain/svn_nlp/UIMA/blue_uima/trunk/modules/blue_uima_pdf/src/test/resources/pdf_fail/30.pdf");
        // File pdfFilePath = new File(root+ "srikanth/Stuart94.pdf");
        // File pdfFilePath = new File(root+
        // "wilhelm/Unknown/Unknown_No Title(3).pdf");
        // File pdfFilePath = new File(root+
        // "yihwa/Unknown/Unknown/Untitled-8469.pdf");

        // File pdfFilePath = new File("/Users/richarde/Desktop/12834440.pdf");

        pdfFilePath = new File(
                "/Volumes/HDD2/ren_data/data_hdd/ontologies/BAMS/pdfs/crawled_bams_pdfs/1279669.pdf");

        PDFTextStream pdfts = new PDFTextStream(pdfFilePath);
        StringBuilder text = new StringBuilder(1024);
        pdfts.pipe(new OutputTarget(text));
        pdfts.close();
        System.out.printf("The text extracted from %s is:", pdfFilePath);
        System.out.println(text);
    }

    @Test
    public void testSubscripts() throws Exception {

        final Pattern SUBSCRIPTS = Pattern.compile("^[ \\d]{10,1000}$");

        File ROOT = new File(
        // "/Volumes/HDD2/ren_data/data_hdd/ontologies/BAMS/pdfs/crawled_bams_pdfs/");
                "/Volumes/scratch/richarde/pdfs/201307/");
        // "/Volumes/scratch/richarde/pdfs/ic_channelpedia/");
        // "/Users/richarde/dev/bluebrain/svn_nlp/UIMA/blue_uima/trunk/modules/blue_uima_pdf/src/test/resources/pdf_fail");
        for (File pdf : ROOT.listFiles()) {
            if (pdf.getName().endsWith(".pdf")) {
                try {

                    // System.out.println("reading " + pdf.getName());
                    PDFTextStream pdfts = new PDFTextStream(pdf);
                    StringBuilder text = new StringBuilder(1024);
                    pdfts.pipe(new OutputTarget(text));
                    pdfts.close();
                    String previous = "";
                    for (String line : text.toString().split("\n")) {

                        Matcher m = SUBSCRIPTS.matcher(line);
                        while (m.find()) {
                            System.out
                                    .println(pdf.getName()
                                            + "--------------------------------------\n"
                                            + previous + "\n" + line + "\n");
                        }

                        previous = line;
                    }
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
        }
    }
}
