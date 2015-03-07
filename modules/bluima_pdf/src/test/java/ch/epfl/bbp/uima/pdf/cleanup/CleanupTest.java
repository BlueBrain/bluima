package ch.epfl.bbp.uima.pdf.cleanup;

import static ch.epfl.bbp.uima.BlueCasUtil.asList;
import static ch.epfl.bbp.uima.BlueCasUtil.getHeaderSource;
import static ch.epfl.bbp.uima.BlueUima.PARAM_INPUT_DIRECTORY;
import static ch.epfl.bbp.uima.PdfHelper.PDF_TEST_RESOURCES;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReader;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

        for (JCas pdf : asList(createReader(PdfCollectionReader.class,
                PARAM_INPUT_DIRECTORY, PDF_TEST_RESOURCES + "pdf/"))) {

            if (getHeaderSource(pdf).endsWith("resources/pdf/1.pdf")) {

                String txt = pdf.getDocumentText();

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

            } else if (getHeaderSource(pdf).endsWith("resources/pdf/3.pdf")) {

                // testing for Ca2+ (can't be copy pasted from Skim)
                assertEquals(3, BlueCasUtil.getHeaderIntDocId(pdf));
                String txt = pdf.getDocumentText();
                assertTrue(txt.indexOf("Ca2+") > 1);

                // TODO Ligatures
            }
        }
    }

}
