package ch.epfl.bbp.uima.pdf.extraction;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;
import org.junit.Test;

import ch.epfl.bbp.uima.PdfHelper;

public class ExtractImages {

    @Test
    public void testPdfBox() throws IOException {

        File pdfFile = new File(PdfHelper.PDF_TEST_RESOURCES + "pdf/1.pdf");
        File outDir = new File("target");
        
        PDDocument document = PDDocument.load(pdfFile);
        @SuppressWarnings("unchecked")
        List<PDPage> pages = document.getDocumentCatalog().getAllPages();
        int imageId = 0;
        for (PDPage page : pages) {
            for (PDXObjectImage img : page.getResources().getImages().values()) {
                
                int height = img.getHeight();
                int width = img.getWidth();
                
                System.out.println(img.getCOSStream().toString());
                
                img.write2file(new File(outDir, imageId++ + "."
                        + img.getSuffix()));
            }
        }
    }
}
