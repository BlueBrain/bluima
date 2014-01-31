
package ch.epfl.bbp.uima.pdf.tables;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snowtide.pdf.PDFTextStream;

import edu.psu.seersuite.extractors.tableextractor.extraction.IPdfParser;
import edu.psu.seersuite.extractors.tableextractor.model.TextPiece;

public class PdfTextStreamParser implements IPdfParser {
    Logger LOG = LoggerFactory.getLogger(PdfTextStreamParser.class);

    public ArrayList<ArrayList<TextPiece>> getTextPiecesByPage(File pdfFile) {
	assert (pdfFile.exists()) : "pdfFile does not exist";

	try {
	    PDFTextStream pdf = new PDFTextStream(pdfFile);

	    TablesOutputHandler tableHandler = new TablesOutputHandler();
	    pdf.pipe(tableHandler);
	    
	    return tableHandler.getWordsByPage();

	} catch (IOException e) {
	    LOG.error("could not extract {}, {}", pdfFile.getAbsoluteFile(), e);
	}
	return new ArrayList<ArrayList<TextPiece>>();
    }
    
    
}
