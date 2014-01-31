/**
 * This package defines the core table extraction classes
 */

package edu.psu.seersuite.extractors.tableextractor.extraction;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.pdfbox.cos.COSStream;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.pdfbox.util.TextPosition;

import edu.psu.seersuite.extractors.tableextractor.model.TextPiece;

/**
 * This class is designed for a specific PDF Parser with PDFBox library
 * 
 * @author Ying, Shuyi
 * 
 */
public class PdfBoxParser extends PDFTextStripper implements IPdfParser {
    private ArrayList<ArrayList<TextPiece>> m_wordsByPage;
    private int m_currentPageNo = 0;

    /**
     * Constructor
     * 
     * @throws IOException
     */
    public PdfBoxParser() throws IOException {
        super();
    }

    /**
     * Get text pieces from a PDF document
     * 
     * @param pdfFile
     *            input PDF file
     * @return lists of text pieces (one list per page)
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    public ArrayList<ArrayList<TextPiece>> getTextPiecesByPage(File pdfFile) {
        assert (pdfFile.exists()) : "pdfFile does not exist";
        m_wordsByPage = new ArrayList<ArrayList<TextPiece>>();

        PDDocument document = null;
        try {
            document = PDDocument.load(pdfFile);

            resetEngine();
            m_currentPageNo = 0;

            // No support for encrypted file in the current version
            if (document.isEncrypted()) {
                // TODO: log
            } else {
                ArrayList<PDPage> pages = (ArrayList<PDPage>) document
                        .getDocumentCatalog().getAllPages();

                for (PDPage page : pages) {
                    m_currentPageNo++;
                    PDStream contentStream = page.getContents();
                    if (contentStream != null) {
                        COSStream contents = contentStream.getStream();
                        processPage(page, contents);
                    }
                }

            }
        } catch (Exception e) {
            System.out.printf("[Error] Fail to extract words\n");
            m_wordsByPage = null;
        } catch (OutOfMemoryError e) {
            System.out.printf("[Error] Out Of Memory\n");
            m_wordsByPage = null;
        } finally {
            if (document != null) {
                try {
                    document.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }

        return m_wordsByPage;
    }

    /**
     * this overrides PDFTextStripper's flushText method: instead of writing to
     * output stream, we save the information in our own data structure
     * 
     * 
     * @throws IOException
     *             If there is an error processing the page.
     */
    @SuppressWarnings("unchecked")
    protected void flushText() throws IOException {
        ArrayList<TextPiece> wordsOfThisPage = new ArrayList<TextPiece>();

        for (int i = 0; i < charactersByArticle.size(); i++) {
            ArrayList<TextPosition> textList = (ArrayList<TextPosition>) charactersByArticle
                    .get(i);
            for (TextPosition t : textList) {

                // copy information

                TextPiece w = new TextPiece();
                w.setX(t.getX());
                w.setY(t.getY());

                /*
                 * 100: a simple step to fix the font size to the normal range,
                 * for those documents in unknown codes that PDFBox can not
                 * process now
                 */
                if (t.getFontSize() < 0.3 && t.getYScale() <= 1.0) {
                    w.setFontSize(t.getFontSize() * 100);
                    w.setHeight(Math.max(t.getYScale(), t.getFontSize()) * 100);
                    w.setXScale(t.getXScale());
                    w.setYScale(t.getYScale());
                } else {
                    if (t.getYScale() < 0.3 && t.getFontSize() <= 1.0) {
                        w.setYScale(t.getYScale() * 100);
                        w.setXScale(t.getXScale() * 100);
                        w.setHeight(Math.max(t.getYScale() * 100,
                                t.getFontSize()));
                    } else {
                        w.setFontSize(t.getFontSize());
                        w.setHeight(Math.max(t.getYScale(), t.getFontSize()));
                        w.setXScale(t.getXScale());
                        w.setYScale(t.getYScale());
                    }
                }

                // Width might be negative according to the PDF text extractors
                w.setWidth(Math.abs(t.getWidth()));
                w.setEndX(w.getX() + w.getWidth());

                // w.setHeight(Math.max(t.getYScale(), t.getFontSize()));
                w.setEndY(w.getY() + w.getHeight());

                w.setWidthOfSpace(t.getWidthOfSpace());
                w.setWordSpacing(t.getWordSpacing());

                w.setText(t.getCharacter());
                wordsOfThisPage.add(w);
            }
        }

        m_wordsByPage.add(wordsOfThisPage);
    }

}
