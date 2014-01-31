package ch.epfl.bbp.uima.pdf.tables;

import java.io.File;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snowtide.pdf.OutputHandler;
import com.snowtide.pdf.PDFTextStream;
import com.snowtide.pdf.Page;
import com.snowtide.pdf.layout.TextUnit;
import com.snowtide.pdf.layout.TextUnitImpl;

import edu.psu.seersuite.extractors.tableextractor.model.TextPiece;

public class TablesOutputHandler extends OutputHandler {
    Logger LOG = LoggerFactory.getLogger(TablesOutputHandler.class);

    private int currentPageNr = 1;

    ArrayList<ArrayList<TextPiece>> allTextPieces = new ArrayList<ArrayList<TextPiece>>();
    ArrayList<TextPiece> currentPageTextPieces = new ArrayList<TextPiece>();

    @Override
    public void startPDF(String arg0, File arg1) {
	super.startPDF(arg0, arg1);
	// LOG.debug("start pdf {}", arg0);
    }

    @Override
    public void endPDF(String arg0, File arg1) {
	super.endPDF(arg0, arg1);
	// LOG.debug("end pdf {}", arg0);
    }

    @Override
    public void startPage(Page page) {

	super.startPage(page);
	currentPageNr = page.getPageNumber();
	currentPageTextPieces = new ArrayList<TextPiece>();

	// LOG.debug("\tstart page {}", page.getPageNumber());
    }

    @Override
    public void endPage(Page page) {
	super.endPage(page);
	allTextPieces.add(currentPageTextPieces);
	currentPageTextPieces = null;
	// LOG.debug("\tend page {}", page.getPageNumber());
    }

    @Override
    public void textUnit(TextUnit t) {
	super.textUnit(t);

	TextPiece p = new TextPiece();
	p.setX(t.xpos());
	p.setY(t.ypos());
	p.setEndX(t.endxpos());
	p.setEndY(t.endypos());
	p.setFontName(t.getFont().getFontName());
	p.setHeight(t.height());
	p.setWidth(t.width());

	if (t instanceof TextUnitImpl) {
	    TextUnitImpl ti = (TextUnitImpl) t;
	    p.setFontSize(ti.getFontSize());
	}

	String txt = t.getCharacterSequence() == null ? Character
		.toString((char) t.getCharCode()) : new String(
		t.getCharacterSequence());
	p.setText(txt);

	currentPageTextPieces.add(p);
    }

    @Override
    public void spaces(int length) {
	super.spaces(length);
    }

    @Override
    public void linebreaks(int lenght) {
	super.linebreaks(lenght);
    }

    public static void main(String[] args) throws Exception {
	File pdf = new File(
		"/Users/richarde/data/_papers_etc/pubmed/sample_pdfs/pdfs/21840999.pdf");
	// "/Users/richarde/data/_papers_etc/pubmed/sample_pdfs/pdfs/21832999.pdf");
	PDFTextStream pdfStream = new PDFTextStream(pdf);
	TablesOutputHandler blueOutputHandler = new TablesOutputHandler();
	pdfStream.pipe(blueOutputHandler);

    }

    public ArrayList<ArrayList<TextPiece>> getWordsByPage() {
	// TODO Auto-generated method stub
	return null;
    }

}
