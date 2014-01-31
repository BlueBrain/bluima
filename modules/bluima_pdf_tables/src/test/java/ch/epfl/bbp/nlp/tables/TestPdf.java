package ch.epfl.bbp.nlp.tables;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.junit.Test;
import org.xml.sax.DocumentHandler;

import com.snowtide.pdf.Page;
import com.snowtide.pdf.layout.Block;
import com.snowtide.pdf.layout.Line;

/**
 * Creates PDF documents to perform tests
 * 
 * @author renaud.richardet@epfl.ch
 */
public class TestPdf {

    // http://pdfbox.apache.org/cookbook/documentcreation.html
    public static void createPdf(File f, List<String> lines)
            throws COSVisitorException, FileNotFoundException, IOException {

        // Create a document and add a page to it
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        // Create a new font object selecting one of the PDF base fonts
        PDFont font = PDType1Font.HELVETICA_BOLD;

        // Start a new content stream which will "hold" the to be created
        // content
        PDPageContentStream contentStream = new PDPageContentStream(document,
                page);

        // Define a text content stream using the selected font, moving the
        // cursor and drawing the text "Hello World"
        int y = 700;
        for (String line : lines) {

            contentStream.beginText();
            contentStream.setFont(font, 12);
            contentStream.moveTextPositionByAmount(100, y);
            contentStream.drawString(line);
            contentStream.endText();
            y -= 30;
        }

        contentStream.close();
        document.save(new FileOutputStream(f));
        document.close();
    }

//    /**
//     * @param lines
//     *            , the lines of the pdf we want to create
//     * @return a test {@link PdfDocument}
//     */
//    public static PdfDocument getPdfDocument(List<String> lines)
//            throws COSVisitorException, FileNotFoundException, IOException {
//
//        // create tmp pdf
//        File f = new File("target/testPdf" + System.currentTimeMillis());
//        createPdf(f, lines);
//
//        // convert it
//        return DocumentHandler.handle(f);
//    }
//
//    @Test
//    public void test() throws Exception {
//
//        PdfDocument doc = getPdfDocument(newArrayList("hello", "world"));
//
//        // tests
//        List<Entry<Page, List<Block>>> pages = newArrayList(doc.iterator());
//        assertEquals(1, pages.size());
//        List<Block> blocks = newArrayList(pages.get(0).getValue());
//        assertEquals(1, blocks.size());
//        @SuppressWarnings("unchecked")
//        List<Line> lines = newArrayList(blocks.get(0).getLines());
//        assertEquals(2, lines.size());
//        assertEquals("hello", getText(lines.get(0)));
//    }

}
