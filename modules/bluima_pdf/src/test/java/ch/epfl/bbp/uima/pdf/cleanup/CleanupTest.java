package ch.epfl.bbp.uima.pdf.cleanup;

import static ch.epfl.bbp.uima.BlueCasUtil.asList;
import static ch.epfl.bbp.uima.BlueUima.PARAM_INPUT_DIRECTORY;
import static ch.epfl.bbp.uima.PdfHelper.PDF_TEST_RESOURCES;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.JULIE_TSD;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReader;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.uima.jcas.JCas;
import org.junit.Test;
import org.xmlcml.pdf2svg.GlyphCorrector;

import ch.epfl.bbp.uima.BlueCasUtil;
import ch.epfl.bbp.uima.pdf.cr.PdfCollectionReader;

/**
 * An integration test for {@link GlyphCorrector}
 * 
 * @author renaud.richardet@epfl.ch
 */
public class CleanupTest {

    @Test
    public void testCleanup() throws Exception {

        List<JCas> pdfs = asList(createReader(PdfCollectionReader.class,
                JULIE_TSD, PARAM_INPUT_DIRECTORY, PDF_TEST_RESOURCES + "pdf/"));
        // PARAM_INPUT_DIRECTORY, PDF_TEST_RESOURCES + "pdf_Orianne/"))

        JCas pdf1 = pdfs.get(0);
        System.out.println("SSource " + BlueCasUtil.getHeaderSource(pdf1));
        String txt = pdf1.getDocumentText();
        // System.out.println(txt);

        // lambda glyph corrected
        int titleStart = txt.indexOf("Factors influencing");
        assertTrue(titleStart > 0);
        assertEquals(
                "lambda glyph corrected",
                "Factors influencing lysis time stochasticity in bacteriophage λ",
                txt.substring(titleStart, titleStart + 63));

        // hyphenation
        // was: [14-16], Bacillus sub-\ntilis [17,18]
        int hyphenStart = txt.indexOf("[14-16], Bacillus sub");
        assertTrue("hyphen sample found", hyphenStart > 0);
        assertEquals("hyphenation",//
                "[14-16], Bacillus subtilis [17,18]",//
                txt.substring(hyphenStart, hyphenStart + 34));

        // text normalization
        assertEquals("found 2 Delbrück with correct umlaut",//
                2, txt.split("Delbrück").length - 1);

        //
        // testing for Ca2+ (can't be copy pasted from Skim)
        JCas pdf3 = pdfs.get(2);
        assertEquals(3, BlueCasUtil.getHeaderIntDocId(pdf3));
        txt = pdf3.getDocumentText();
        assertTrue(txt.indexOf("Ca2+") > 1);

        // TODO Ligatures
    }

}
