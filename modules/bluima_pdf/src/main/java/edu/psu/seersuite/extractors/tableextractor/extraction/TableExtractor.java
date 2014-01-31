/**
 * This package defines the core table extraction classes
 */
package edu.psu.seersuite.extractors.tableextractor.extraction;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Vector;

import org.jdom2.Document;
import org.jdom2.Element;

import edu.psu.seersuite.extractors.tableextractor.Config;
import edu.psu.seersuite.extractors.tableextractor.Debug;
import edu.psu.seersuite.extractors.tableextractor.model.DocInfo;
import edu.psu.seersuite.extractors.tableextractor.model.Table;
import edu.psu.seersuite.extractors.tableextractor.model.TableCandidate;
import edu.psu.seersuite.extractors.tableextractor.model.TableRow;
import edu.psu.seersuite.extractors.tableextractor.model.TextPiece;

/**
 * Table extractor: this extraction class provides a set of APIs to extract a
 * set of tables from an input PDF file.
 * 
 * Here is an example of how to extract tables from a PDF file:
 * <p>
 * TableExtractor extractor = new TableExtractor(); // create a TableExtractor
 * object
 * <p>
 * extractor.setParser(parser); //set the PDF parser, here parser has two
 * options now: PDFBox or TET. Other new parsers can be added easily.
 * <p>
 * ArrayList
 * <Table>
 * extractedTableSet = extractor.extract(pdfFile, outputDirPath);
 * <p>
 * //Parameter pdfFile is the whole path of a PDF file. Parameter outputDirPath
 * is the output path for debug information. Unless otherwise noted. Passing a
 * null argument to the second parameter in this method will cause a IOException
 * to be thrown. A set of detected tables returned in the ArrayList
 * extractedTableSet
 * <p>
 * Another detailed example can be found in BatchExtractor.java, which extracts
 * tables from all PDF files within a directory.
 * 
 * <p>
 * This class implements the core steps to extract tables from documents. It
 * mainly includes the following steps:
 * <p>
 * 1. Extracting texts from the documents; 2. Checking the coding schemes of the
 * PDF source files and filtering out those PDFs in unknown codes and large
 * PDFs. 3. Combining texts from the word-similar level to the line level. 4.
 * For each page, detecting possible table captions; 5. Analyzing the position
 * of the table captions: top or below; 6. Table boundary detection with the
 * information of table captions. 7. Table structure decomposition: the
 * row/column structure, the column headings, the row headings. etc. Including
 * the table footnote analysis and the table reference text analysis; 8. Table
 * metadata file generation;
 * 
 * @author Ying, Shuyi
 */

public class TableExtractor {
    private IPdfParser m_parser;
    private DocInfo m_docInfo; // current doc information
    private Element xml_root; // <Tables></Tables>
    private Document xml_doc;
    private int unknownCodedPDF = 0; // some PDF documents are encoded in
				     // unknown codes, which can not be decoded
				     // by PDF text extraction tools
    private int HTMLCodePDF = 0;
    private String meta; // for XML generator version only
    private int largeDocNum = 0; // The number of large PDF documents. In order
				 // to avoid spending too much time on a single
				 // file, we filter out the PDF files with a
				 // very large file size.
    private int noTextDocNum = 0; // To count the number of documents that PDF
				  // text extractors can not extract any text.
    private ArrayList<Table> tables; // the data structures to store the
				     // detected tables.

    /**
     * Gets the basic information of the PDF document
     * 
     * @return the document Info
     */
    public DocInfo getDocInfo() {
	return m_docInfo;
    }

    /**
     * Gets the XML-based table metadata file
     * 
     * @return the XML-based table metadata file
     */
    public Document getXMLDoc() {
	return xml_doc;
    }

    /**
     * Extracts tables from a PDF document
     * 
     * @param pdfFile
     *            PDF file to process
     * @param outputDirPath
     *            the directory path to save the extracted tables, ONLY IN DEBUG
     * @return extracted table set / null (if error happens)
     */
    public ArrayList<Table> extract(File pdfFile, String outputDirPath) {
	m_docInfo = new DocInfo();
	xml_root = new Element("document");// create the root element:<document
					   // id="x.x.x.x.pdf"></document>
	xml_root.setAttribute("id",
		pdfFile.getName().substring(0, pdfFile.getName().length() - 4));
	Element xml_tables = new Element("tables");
	xml_root.addContent(xml_tables);
	xml_doc = new Document(xml_root); // prepare an empty XML doc with root
					  // <Tables>

	// System.out.printf("extracting tables from %s...\n",
	// pdfFile.getName());

	if (m_parser == null) {
	    System.out.printf("[Error] no parser is set\n");
	    return null;
	} else {
	    /*
	     * step 1: extracts words from the PDF document
	     */
	    ArrayList<ArrayList<TextPiece>> wordsByPage = m_parser
		    .getTextPiecesByPage(pdfFile);
	    tables = new ArrayList<Table>();

	    if (wordsByPage != null && wordsByPage.size() != 0) { // in order to
								  // keep the
								  // fast speed
								  // for large
								  // repositories,
								  // we do not
								  // process PDF
								  // files with
								  // 150+ pages
		int pageNum = wordsByPage.size();
		if (pageNum <= 150) {
		    /*
		     * Judges whether the file is in HTML code or other unknown
		     * codes
		     */
		    boolean unknownCodedDoc = unknownByPDFExtractor(wordsByPage);
		    if (unknownCodedDoc) {
			unknownCodedPDF++;
			m_docInfo
				.setErrorMsg("Sorry, this PDF document is coded with the special coders, which PDFBOX can not process successfully.");
			// System.out.println(unknownCodedPDF +
			// " PDF files are in an unknown code, which can not be processed by PDFBOX");
		    }
		    boolean isHTMLCode = true;
		    isHTMLCode = judgeHTMLCode(wordsByPage);
		    if (isHTMLCode) {
			HTMLCodePDF++;
			m_docInfo
				.setErrorMsg("Sorry, this PDF document is coded in HTML code. TableSeer does not process such files now.");
			// convertHTMLCode(wordsByPage);
			// System.out.println(HTMLCodePDF +
			// " PDF files are in HTML code, which can not be processed by PDFBOX");
		    }

		    /*
		     * currently, we only process those PDF documents that the
		     * PDF text extraction tools can process
		     */
		    // if (unknownCodedDoc==false && isHTMLCode==false) { //too
		    // much PDF files will be filtered out using this condition
		    if (isHTMLCode == false) {

			m_docInfo = computeParametersForDoc(wordsByPage);

			/*
			 * step 2: combines words into Lines, and calculates the
			 * averageLineWidth of docInfo
			 */
			ArrayList<ArrayList<TextPiece>> linesByPage = new ArrayList<ArrayList<TextPiece>>();
			float aveLineWidth = 0.0f;
			int ii = 0;
			for (ArrayList<TextPiece> wordsOfAPage : wordsByPage) {
			    ArrayList<TextPiece> linesOfAPage = combineLines(wordsOfAPage);

			    linesOfAPage = dataCleaning(linesOfAPage); // there
								       // are
								       // some
								       // noisy
								       // empty
								       // lines,
								       // we
								       // should
								       // clean
								       // them
								       // out

			    linesByPage.add(linesOfAPage);

			    for (int i = 0; i < linesOfAPage.size(); i++) {
				if (Math.max(linesOfAPage.get(i).getFontSize(),
					linesOfAPage.get(i).getXScale()) == m_docInfo
					.getBodyTextScale()) {
				    aveLineWidth = aveLineWidth
					    + linesOfAPage.get(i).getWidth();
				    ii++;
				}
			    }
			}
			aveLineWidth = aveLineWidth / (float) ii;
			if (aveLineWidth < 50.0f)
			    aveLineWidth = 175.0f; // TODO: manually fix the
						   // parameters here, we should
						   // fix it permanently
			m_docInfo.setAverageLineWidth(aveLineWidth);

			for (ArrayList<TextPiece> linesOfAPage : linesByPage) {
			    for (int i = 1; i < linesOfAPage.size() - 1; i++) {
				if ((linesOfAPage.get(i).getWidth() >= m_docInfo
					.getAverageLineWidth())
					&& (Math.abs(linesOfAPage.get(i).getY()
						- linesOfAPage.get(i - 1)
							.getY()) > 3.0)
					&& (Math.abs(linesOfAPage.get(i + 1)
						.getY()
						- linesOfAPage.get(i).getY()) > 3.0))
				    linesOfAPage.get(i).setSparseLine(false);
			    }
			}

			String meta = "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>\n";
			meta = meta + "<Tables>\n";
			meta = meta + "<FileName>" + pdfFile.getName()
				+ "</FileName>\n";
			meta = meta + "<pageNum>" + pageNum + "</pageNum>\n";

			xml_root.addContent(new Element("FileName")
				.addContent(pdfFile.getName()));
			xml_root.addContent(new Element("pageNum")
				.addContent(Integer.toString(pageNum)));
			m_docInfo.setDocMeta(xml_doc);
			m_docInfo.setDocMeta(meta);
			m_docInfo.setTableMetadata(meta);
			if (Config.DEBUG_MODE)
			    Debug.printMiddleResults(wordsByPage, linesByPage,
				    outputDirPath, pdfFile);

			/*
			 * step 3: identifies tables, Loop over pages
			 */
			int i = 0;
			for (ArrayList<TextPiece> linesOfAPage : linesByPage) {
			    // System.out.println("Page: " + (i+1));
			    ArrayList<TextPiece> wordsOfAPage = wordsByPage
				    .get(i);
			    extractTablesFromAPage(linesOfAPage, tables,
				    wordsOfAPage, i, pdfFile, linesByPage);

			    // extractFiguresFromAPage(linesOfAPage,
			    // wordsOfAPage, i, pdfFile, linesByPage);
			    i++;
			}
			meta = m_docInfo.getTableMetaInThisDoc();
			meta = meta + "<errorMsg>" + m_docInfo.getErrorMsg()
				+ "</errorMsg>\n";
			meta = meta + "</Tables>\n";
			// System.out.println(meta);

			/*
			 * TODO [improve]: generates XML from tables here
			 */
			if (tables.size() != 0) {
			    for (int k = 0; k < tables.size(); k++) {
				Table eTable = tables.get(k);
				Element tabRec = new Element("tableRecord")
					.setAttribute("id",
						(Integer.toString(k)));
				Element table = new Element("table");
				if (eTable.getHeight() != 0
					&& eTable.getWidth() != 0) {
				    table.setAttribute("height", Integer
					    .toString((int) eTable.getHeight()));
				    table.setAttribute("width", Integer
					    .toString((int) eTable.getWidth()));
				} else {
				    table.setAttribute("height",
					    Integer.toString(300));
				    table.setAttribute("width",
					    Integer.toString(400));
				}
				table.setAttribute("border",
					Integer.toString(1));
				tabRec.addContent(new Element("pageNumInDoc")
					.addContent(Integer.toString(eTable
						.getPageNumber())));
				tabRec.addContent(new Element("caption")
					.addContent(eTable.getCaption()));

				// table column headings
				Element tHead = new Element("thead");
				Element tRc = new Element("tr");
				tHead.addContent(tRc);
				ArrayList<String> colHeading = eTable
					.getHeading();
				for (int cloop = 0; cloop < colHeading.size(); cloop++) {
				    String head = colHeading.get(cloop);
				    tRc.addContent(new Element("th")
					    .addContent(head));
				}

				// table row contents
				Element tBody = new Element("tbody");

				ArrayList<String> rows = eTable.getTableBody();
				for (int rloop = 0; rloop < rows.size(); rloop++) {
				    Element tRr = new Element("tr");
				    String[] cells = rows.get(rloop).split(";");
				    for (int inner = 0; inner < cells.length; inner++) {
					Element tD = new Element("td")
						.addContent(cells[inner]);
					tRr.addContent(tD);
				    }
				    tBody.addContent(tRr);
				}

				table.addContent(tHead);
				table.addContent(tBody);
				tabRec.addContent(table);
				tabRec.addContent(new Element("footnote")
					.addContent(eTable.getFootNote()));
				tabRec.addContent(new Element("referenceText")
					.addContent(eTable.getRefTextList()));
				xml_tables.addContent(tabRec);
			    }
			}
			xml_root.addContent(new Element("errorMsg")
				.addContent(m_docInfo.getErrorMsg()));

			/*
			 * standard XML finish generating here, ready to output
			 */
			m_docInfo.setTableMetadata(meta);
			// System.out.println(meta);
			if (Config.DEBUG_MODE) { // print table metadata ordered
						 // by documents
			    Debug.printTableMeta(outputDirPath, pdfFile, meta);
			    Debug.printStatInfo(outputDirPath, pdfFile,
				    pageNum, m_docInfo.getTableNum());
			}
		    }// end if( not unknowCode or HTML code)
		    return tables;

		}// end if (page Num<=150)
		else {
		    largeDocNum++;
		    // System.out.println("Sorry, for the scalability and speed reasons, we do not process the PDF documents that are TOO LARGE! "
		    // + largeDocNum);
		    m_docInfo
			    .setErrorMsg("Sorry, for the scalability and speed reasons, we do not process the PDF documents that are TOO LARGE!");
		    return null;
		}
	    } else {
		noTextDocNum++;
		// System.out.println("Sorry, PDFBOX can not extract any text from this PDF document! "
		// + noTextDocNum);
		m_docInfo
			.setErrorMsg("Sorry, PDFBOX can not extract any text from this PDF document!");
		return null;
	    }
	}// end else()
    }// extract()

    /**
     * Detects PDF documents encoded in unknown codes
     * 
     * All the constant values are set based on extensive observation and
     * experiment. Of course, these values still need improvement for better
     * results. Machine learning methods can be used to tune these values.
     * 
     * @param wordsByPage
     *            the extracted texts in the word level in a document
     * @return A boolean value: true/false
     */
    private boolean unknownByPDFExtractor(
	    ArrayList<ArrayList<TextPiece>> wordsByPage) {
	ArrayList<TextPiece> wordsOfPageOne = wordsByPage.get(0);
	boolean isUnknownCode = false;
	int i = 0;
	float codeSize = 0.0f;
	while (i < wordsOfPageOne.size()) {
	    codeSize = codeSize
		    + Math.max(wordsOfPageOne.get(i).getXScale(),
			    wordsOfPageOne.get(i).getFontSize());
	    i++;
	}
	codeSize = codeSize / (float) wordsOfPageOne.size();
	if (codeSize <= 1.2)
	    isUnknownCode = true;
	return (isUnknownCode);
    }

    /**
     * Detects documents encoded in HTML code
     * 
     * @param wordsByPage
     *            the extracted texts in the word level in a document
     * @return A boolean value: true/false
     */
    private boolean judgeHTMLCode(ArrayList<ArrayList<TextPiece>> wordsByPage) {
	ArrayList<TextPiece> wordsOfPageOne = wordsByPage.get(0);
	boolean isHTMLCode = true;
	int i = 0;
	/*
	 * do not have to check all words, Checking 10 words is enough
	 */
	while (i < Math.min(20, wordsOfPageOne.size())) {
	    TextPiece currentWord = wordsOfPageOne.get(i);
	    String thisText = currentWord.getText();
	    if ((thisText.length() % 2 != 0)
		    && (thisText.matches("([A-Z]([A-Z]|[0-9]))*") == false)) {
		isHTMLCode = false;
		break;
	    }
	    i++;
	}
	// if (isHTMLCode)
	// System.out.println("This document is coded in HTML code!");
	return isHTMLCode;
    }

    /**
     * TODO: NOT finished yet Converts HTML code back to the real characters;
     * 
     * @param wordsByPage
     *            the extracted texts in the word level in a document
     */
    private void convertHTMLCode(ArrayList<ArrayList<TextPiece>> wordsByPage) {
	DocInfo docInfo = new DocInfo();
	String[] html2Char = docInfo.getHtml2CharMapping(); // Only define this
							    // mapping string
							    // when we detect
							    // the files in HTML
							    // codes
	int pageNum = 0;

	for (ArrayList<TextPiece> wordsOfAPage : wordsByPage) {
	    pageNum++;
	    for (int i = 0; i < wordsOfAPage.size(); i++) {
		TextPiece currentWord = wordsOfAPage.get(i);
		String realText = "";
		String textinHTMLCode = currentWord.getText();

	    }
	}
    }

    /**
     * Calculates the document-level parameters before the document is further
     * processed
     * 
     * @param wordsByPage
     *            the extracted texts in the word-level in a document
     * @return an object of DocInfo
     */
    private DocInfo computeParametersForDoc(
	    ArrayList<ArrayList<TextPiece>> wordsByPage) {
	DocInfo docInfo = new DocInfo();

	float docTitleFont = 0;
	float[][] scaleListsWithTextAmount = new float[5000][2];
	int scaleNum = 0;

	int pageNum = 0;
	for (ArrayList<TextPiece> wordsOfAPage : wordsByPage) {
	    pageNum++;
	    // ArrayList<TextPiece> linesOfAPage = new ArrayList<TextPiece>();
	    // TextPiece line = null;
	    for (int i = 0; i < wordsOfAPage.size(); i++) {
		// TextPiece prevWord = (i == 0) ? null : wordsOfAPage.get(i -
		// // 1);
		TextPiece currentWord = wordsOfAPage.get(i);
		// get the document title font size
		if ((pageNum == 1) && (currentWord.getY() < 400)) {
		    if (docTitleFont < currentWord.getFontSize())
			docTitleFont = currentWord.getFontSize();
		}
		// get the document body text font
		if (scaleNum == 0) {
		    scaleListsWithTextAmount[0][0] = Math.max(
			    currentWord.getXScale(), currentWord.getFontSize());
		    scaleListsWithTextAmount[0][1] = currentWord.getWidth();
		    scaleNum++;
		} else {
		    boolean notExist = true;
		    for (int m = 0; m < scaleNum; m++) {
			if (scaleListsWithTextAmount[m][0] == Math.max(
				currentWord.getXScale(),
				currentWord.getFontSize())) {
			    scaleListsWithTextAmount[m][1] = scaleListsWithTextAmount[m][1]
				    + currentWord.getWidth(); // calculate the
							      // text width for
							      // each scale is
							      // better than
							      // just counting
							      // the line number
			    notExist = false;
			}
		    }
		    if (notExist == true) {
			scaleListsWithTextAmount[scaleNum][0] = Math.max(
				currentWord.getXScale(),
				currentWord.getFontSize());
			scaleListsWithTextAmount[scaleNum][1] = currentWord
				.getWidth();
			scaleNum++;
		    }
		}// end else
	    }// end for()
	}// for()
	float maxLineNum = 0;
	int bodyTextScaleIndex = 0;
	for (int j = 0; j < scaleNum; j++) {
	    if (scaleListsWithTextAmount[j][1] > maxLineNum) {
		maxLineNum = scaleListsWithTextAmount[j][1];
		bodyTextScaleIndex = j;
	    }
	}

	/*
	 * Calculates other background characters----------
	 */
	float ave_LineGap = (float) 0.1; // this is ave_LineGap_thisPage in old
					 // version
	float ave_X_Gap_inLine = (float) 0.1;
	int lineNumInBodyFont = 1;
	int sameLineObj = 1;
	float minX = 1000;
	float maxX = 0;
	float minY = 1000;
	float maxY = 0;
	float aveCharWidthInDoc = (float) 0.0000001;
	int charNumInWholeDoc = 1;

	for (ArrayList<TextPiece> wordsOfAPage : wordsByPage) {
	    for (int i = 1; i < wordsOfAPage.size(); i++) {
		/*
		 * Calculates ave_LineGap
		 */
		TextPiece currentWord = wordsOfAPage.get(i);
		TextPiece prevWord = wordsOfAPage.get(i - 1);
		if (Math.max(currentWord.getXScale(), currentWord.getFontSize()) == scaleListsWithTextAmount[bodyTextScaleIndex][0]) { // only
																       // consider
																       // the
																       // text
																       // pieces
																       // that
																       // in
																       // the
																       // body
																       // text
																       // font
		    if ((currentWord.getY() - prevWord.getY()
			    - prevWord.getHeight() > (float) 0.0)
			    && (currentWord.getY() - prevWord.getY()
				    - prevWord.getHeight() < prevWord
					.getHeight())) {// reduce the big gap
							// noise
			ave_LineGap = ave_LineGap
				+ (currentWord.getY() - prevWord.getY());
			lineNumInBodyFont++;
		    }

		    /*
		     * Gets the boundary of the doc page-
		     */
		    if ((currentWord.getX() < minX) && (currentWord.getX() > 0))
			minX = currentWord.getX();
		    if ((currentWord.getX() + currentWord.getWidth() + currentWord
			    .getWidthOfSpace()) > maxX)
			maxX = currentWord.getX() + currentWord.getWidth()
				+ currentWord.getWidthOfSpace();
		    if ((currentWord.getY() < minY) && (currentWord.getY() > 0))
			minY = currentWord.getY();
		    if (currentWord.getY() > maxY)
			maxY = currentWord.getY();

		    /*
		     * Calculates ave_X_Gap_inLine
		     */
		    if ((currentWord.getY() == prevWord.getY())
			    && (currentWord.getX() - prevWord.getX()
				    - prevWord.getWidth() > 0)) {
			ave_X_Gap_inLine = ave_X_Gap_inLine
				+ currentWord.getX() - prevWord.getX()
				- prevWord.getWidth();
			sameLineObj++;
		    }

		    /*
		     * calculates the average width of each character in the
		     * whole document
		     */
		    aveCharWidthInDoc = aveCharWidthInDoc
			    + currentWord.getWidth();
		    charNumInWholeDoc = charNumInWholeDoc
			    + currentWord.getText().length();
		}// end if(lines in body text font)
	    }// end (for a page)
	}// end for(all pages)
	ave_LineGap = ave_LineGap / (float) lineNumInBodyFont;
	ave_X_Gap_inLine = ave_X_Gap_inLine / (float) sameLineObj;
	aveCharWidthInDoc = aveCharWidthInDoc / (float) charNumInWholeDoc;
	docInfo.setTitleFont(docTitleFont);
	docInfo.setBodyTextScale(scaleListsWithTextAmount[bodyTextScaleIndex][0]);
	docInfo.setAverageLineGap(ave_LineGap);
	docInfo.setMinX(minX);
	docInfo.setMaxX(maxX);
	docInfo.setMinY(minY);
	docInfo.setMaxY(maxY);
	docInfo.setAverageXGap(ave_X_Gap_inLine);
	docInfo.setAverageCharWidth(aveCharWidthInDoc);
	// System.out.println("The text body font size is: " +
	// docInfo.getBodyTextScale());
	// System.out.println("ave_LineGap: " + ave_LineGap);
	// System.out.println("ave_X_Gap_inLine: " + ave_X_Gap_inLine);
	// System.out.println("aveCharWidthInDoc: " + aveCharWidthInDoc);
	// System.out.println("(MinX, maxX) (MinY, MaxY): (" + minX + "," + maxX
	// + "); (" + minY + "," + maxY +").");
	// System.out.println();

	/*
	 * TODO! Unfinished Judges whether this doc only has one column
	 */
	float middleX = docInfo.getMinX()
		+ (docInfo.getMaxX() - docInfo.getMinX()) / 2;
	docInfo.setMiddleX(middleX);
	docInfo.setMiddleArea_X(middleX - 3);
	docInfo.setMiddleArea_EndX(middleX + 3);
	int lineNum_blockMiddle = 0;
	for (ArrayList<TextPiece> wordsOfAPage : wordsByPage) {
	    for (int i = 1; i < wordsOfAPage.size(); i++) {
		TextPiece currentWord = wordsOfAPage.get(i);
		TextPiece prevWord = wordsOfAPage.get(i - 1);

		// todo
	    }
	}
	return docInfo;
    }

    /**
     * Combines words into lines in a document page except the table area
     * 
     * @param wordsOfAPage
     *            a list of words of one page
     * @return a list of lines of one page
     */
    private ArrayList<TextPiece> combineLines(ArrayList<TextPiece> wordsOfAPage) {
	ArrayList<TextPiece> linesOfAPage = new ArrayList<TextPiece>();

	/*
	 * loops over all words
	 */
	TextPiece line = null;
	for (int i = 0; i < wordsOfAPage.size(); i++) {
	    TextPiece prevWord = (i == 0) ? null : wordsOfAPage.get(i - 1);
	    TextPiece currentWord = wordsOfAPage.get(i);
	    TextPiece nextWord = (i == wordsOfAPage.size() - 1) ? null
		    : wordsOfAPage.get(i + 1);

	    float gapToPrevWord = (prevWord == null) ? Integer.MAX_VALUE
		    : currentWord.getX() - prevWord.getX()
			    - prevWord.getWidth();
	    float gapToNextWord = (nextWord == null) ? Integer.MAX_VALUE
		    : nextWord.getX() - currentWord.getX()
			    - currentWord.getWidth();

	    /*
	     * Decides whether this word is the beginning of a new line
	     */
	    boolean isNewLine;
	    if (prevWord != null
		    && (Math.abs(currentWord.getY() - prevWord.getY()) <= Math
			    .max(prevWord.getYScale(), prevWord.getFontSize()))
		    && (currentWord.getX() > prevWord.getX())
		    && ((gapToPrevWord < m_docInfo.getAverageCharWidth() * 1.5) || (gapToPrevWord < 5.0f))) {
		isNewLine = false;
	    } else
		isNewLine = true;

	    boolean isSpaceOrEmpty = currentWord.getText() == " "
		    || currentWord.getText() == "";
	    if (isNewLine || line == null) {
		if (isSpaceOrEmpty) {
		} // ignore empty new line
		else {
		    /*
		     * Creates a new line and add it to collection
		     */
		    line = new TextPiece();
		    linesOfAPage.add(line);
		    /*
		     * Gets info of this line
		     */
		    line.setX(currentWord.getX());
		    line.setY(currentWord.getY());
		    line.setEndX(currentWord.getX() + currentWord.getWidth());
		    line.setEndY(currentWord.getY()
			    + Math.max(currentWord.getYScale(),
				    currentWord.getFontSize()));
		    line.setXScale(currentWord.getXScale());
		    line.setWidth(currentWord.getWidth());
		    line.setHeight(Math.max(currentWord.getFontSize(),
			    currentWord.getYScale()));
		    line.setText(currentWord.getText());
		    line.setFontSize(currentWord.getFontSize());
		}
	    } else {
		/*
		 * This word is still in the same line: combines it line will
		 * not be null here, otherwise it's a bug
		 */
		if (line.getText().length() == 1
			&& currentWord.getXScale() > prevWord.getXScale()
			&& currentWord.getX() > prevWord.getX()
			&& currentWord.getY() > prevWord.getY()
			&& prevWord.getY() + prevWord.getXScale() > currentWord
				.getY()) {
		    line.setSuperScriptBeginning(true);
		}
		if (currentWord.getX() - line.getEndX() > 1.5) { // try to fix
								 // the space
								 // problem
								 // inherited
								 // from PDFBOX
		    line.setText(line.getText() + " " + currentWord.getText());
		} else
		    line.setText(line.getText() + currentWord.getText());
		line.setWidth(line.getWidth() + currentWord.getWidth());

		/*
		 * Updates the end-x
		 */
		line.setEndX(currentWord.getX() + currentWord.getWidth());

		if (currentWord.getXScale() > line.getXScale())
		    line.setXScale(currentWord.getXScale());

		line.setY(currentWord.getY());
		line.setEndY(currentWord.getY()
			+ Math.max(currentWord.getYScale(),
				currentWord.getFontSize()));
	    }
	}

	return linesOfAPage;
    }

    /**
     * Cleans up the noising lines, e.g., the continuing blank lines
     * 
     * @param linesOfAPage
     *            a list of lines of one page
     * @return a list of words of one page
     */

    private ArrayList<TextPiece> dataCleaning(ArrayList<TextPiece> linesOfAPage) {
	TextPiece line = null;
	TextPiece preLine = null;
	TextPiece nextLine = null;
	for (int i = 0; i < linesOfAPage.size(); i++) {
	    line = linesOfAPage.get(i);
	    preLine = (i == 0) ? null : linesOfAPage.get(i - 1);
	    nextLine = (i == linesOfAPage.size() - 1) ? null : linesOfAPage
		    .get(i + 1);
	    if ((line.getText().replaceAll(" ", "").length() == 0)
		    && (preLine != null) && (nextLine != null)
		    && (preLine.getEndY() < line.getY())
		    && (line.getEndY() < nextLine.getY())) {
		linesOfAPage.remove(i);
		i--;
		// System.out.println("Removed the next line of: " +
		// preLine.getText());
	    }
	}
	return linesOfAPage;
    }

    /**
     * Extracts tables from one page
     * 
     * @param linesOfAPage
     *            the list of lines of one page
     * @param tables
     *            extracted tables
     * @param wordsOfAPage
     *            the list of words of one page
     * @param i
     *            the current page id
     * @param linesByPage
     *            the list of lines of the document
     * 
     */
    private void extractTablesFromAPage(ArrayList<TextPiece> linesOfAPage,
	    ArrayList<Table> tables, ArrayList<TextPiece> wordsOfAPage, int i,
	    File pdfFile, ArrayList<ArrayList<TextPiece>> linesByPage) {
	if (linesOfAPage.size() > 30) {
	    /*
	     * Checks for table keywords
	     */
	    boolean tableKwdExist = false;
	    int prevTableEndIndex = 0;
	    for (TextPiece t : linesOfAPage) {
		String textWithoutSpace = t.getText().replace(" ", "");
		if (getMatchedTabledKeyword(textWithoutSpace) != null) {
		    tableKwdExist = true;
		    break;
		}
	    }

	    /*
	     * TODO [improve]: keywords are searched twice, no need to do the
	     * first search in the future
	     */
	    if (tableKwdExist == true) {
		for (int k = 0; k < linesOfAPage.size();) {
		    TextPiece line = linesOfAPage.get(k);
		    TextPiece prevLine = (k == 0) ? null : linesOfAPage
			    .get(k - 1);
		    String textWithoutSpace = line.getText().replace(" ", "");
		    String keyword = getMatchedTabledKeyword(textWithoutSpace);

		    if (keyword != null
			    && (textWithoutSpace.startsWith("tableofcontent") == false)
			    && (textWithoutSpace.startsWith("TABLEOFCONTENT") == false)
			    && (textWithoutSpace.startsWith("TableofContent") == false)) {
			char lastCharInKwd = keyword
				.charAt(keyword.length() - 1);
			String oldKwd = line.getText().substring(0,
				line.getText().indexOf(lastCharInKwd) + 1);
			String newLine = "Table"
				+ line.getText()
					.substring(
						line.getText().indexOf(
							lastCharInKwd) + 1,
						line.getText().length());
			// linesOfAPage.get(k).setText(newLine);

			if (prevLine != null && line.getY() == prevLine.getY())
			    k++;
			else {
			    /*
			     * try to extract this table
			     */
			    TableCandidate tc = new TableCandidate();
			    tc.setKeyword(keyword);
			    // tc.setXMLDoc(xml_doc); // to add all table
			    // metadata elements in TableCandidate

			    k = extractOneTable(k, linesOfAPage, keyword, tc,
				    wordsOfAPage, i, prevTableEndIndex,
				    linesByPage);
			    if (tc.isValid()) {
				if (tc.isTopCaption())
				    prevTableEndIndex = tc.getBodyEndLine();
				else
				    prevTableEndIndex = tc.getCaptionEndLine();
				if (Config.DEBUG_MODE)
				    tc.print();
				String metadata_StructureLevel = tc
					.getMetadata_StructureLevel();
				String tableMeta_thisDoc = m_docInfo
					.getTableMetaInThisDoc();
				String tableMetaHighLevel = tc
					.getMetadata_HighLevel();
				String latestMetaData = tableMeta_thisDoc
					+ tableMetaHighLevel
					+ metadata_StructureLevel;
				m_docInfo.setTableMetadata(latestMetaData);

				/*
				 * Generates the extracted table list, then XML
				 * output from each table
				 */
				// System.out.println(Integer.toString(i)+" Adding");
				Table oneTable = new Table();
				oneTable.setValidTable(tc);
				tables.add(oneTable);

				oneTable.setPageNumber(i + 1);
			    }
			}
		    } else
			k++;
		}
	    } else {// TODO: log
	    }
	} else {
	    /*
	     * Impossible to contain tables because of few texts in the page
	     */
	}
    }

    /**
     * Extracts figures from ONE page
     * 
     * @param linesOfAPage
     *            the list of lines of one page
     * @param wordsOfAPage
     *            the list of words of one page
     * @param i
     *            the current page id
     * @param linesByPage
     *            the list of lines of the document
     **/
    private void extractFiguresFromAPage(ArrayList<TextPiece> linesOfAPage,
	    ArrayList<TextPiece> wordsOfAPage, int i, File pdfFile,
	    ArrayList<ArrayList<TextPiece>> linesByPage) {
	if (linesOfAPage.size() > 30) { // if this page is very short, it
					// usually does not contain tables
	    /*
	     * Checks for table keyword
	     */
	    boolean figureKwdExist = false;
	    int prevTableEndIndex = 0;
	    int k = -1;
	    for (TextPiece t : linesOfAPage) {
		k++;
		String textWithoutSpace = t.getText().replace(" ", "");
		TextPiece prevLine = (k == 0) ? null : linesOfAPage.get(k - 1);
		if (getMatchedFigureKeyword(textWithoutSpace) != null) {
		    figureKwdExist = true;
		    String figKwd = getMatchedFigureKeyword(textWithoutSpace);
		    System.out.println("In page " + (i + 1) + ": "
			    + textWithoutSpace);
		}
	    }
	} else {
	    /*
	     * Impossible to contain tables because of few texts in the page
	     */
	}
    }

    /**
     * Extracts a table if detecting a potential table caption
     * 
     * @param linesOfAPage
     *            the list of lines of one page
     * @param tc
     *            the object of the table candidates
     * @param wordsOfAPage
     *            the list of words of one page
     * @param i
     *            the current page id
     * @param prevTableEndIndex
     *            the line id of the ending place of the previous table
     * @param linesByPage
     *            the list of lines of the document page
     * @return next line number after caption area
     */
    private int extractOneTable(int lineNumber,
	    ArrayList<TextPiece> linesOfAPage, String keyword,
	    TableCandidate tc, ArrayList<TextPiece> wordsOfAPage, int i,
	    int prevTableEndIndex, ArrayList<ArrayList<TextPiece>> linesByPage) {
	TextPiece line = linesOfAPage.get(lineNumber);
	if (line.getX() < 0 && line.getWidth() == 0.0) {
	    System.out
		    .println("\n This is a reversed table, currently we do not support such tables!");
	    lineNumber++;
	    tc.setValid(false);
	} else {
	    lineNumber = findCaptionBoundary(lineNumber, linesOfAPage, tc);

	    if (tc.isValid()) {
		tc.setPageId_thisTable(i);
		// getReferenceTableTexts(tc, linesByPage);
		isWideTable(tc);
		Vector distinctY = new Vector();
		Vector piecesEachY = new Vector();
		distinctY = getDistinctSortedY(distinctY, piecesEachY,
			wordsOfAPage, tc);

		boolean aboveCaption = judgeCaptionLocation(linesOfAPage, tc,
			distinctY, wordsOfAPage);
		lineNumber = findBodyBoundary(lineNumber, linesOfAPage, tc,
			aboveCaption, prevTableEndIndex);
		getReferenceTableTexts(tc, linesByPage);
		// System.out.println(tc.getRefText());
	    }
	    tc.setHighLevelMeta(i);

	    if (tc.isValid()) {
		organizeCells(linesOfAPage, tc);
	    }
	    if (tc.isValid()) {
		findFootnoteBeginRow(tc);
	    }
	    if (tc.isValid()) {
		determineFootnoteHeading(tc);
		getColumnBoundaryWithoutHeading(tc);
	    }

	    int cc = tc.getColumnNum_thisTable();
	    if (cc > 20) {
		tc.setValid(false);
	    } else {
		// if((cc>=2) && (tc.getMaxColumnNumber()<cc*2) ) { //if we
		// treat each line as the possible table beginning without
		// considering the table keyword, we need use this
		if (cc >= 2) {
		    getTableStructure(cc, tc, wordsOfAPage);
		    // if (tc.isValid()) parameters.tableNum++;
		} else {
		    tc.setEmptyMetadataStructureLevel(tc.getRows().size(), cc,
			    wordsOfAPage, m_docInfo);
		}

	    }

	}
	return lineNumber;
    }

    /**
     * Judges whether a table is a wide table or not by considering the width of
     * the table
     * 
     * @param tc
     *            the object of the table candidates
     */
    private void isWideTable(TableCandidate tc) {
	float docWidth = m_docInfo.getMaxX() - m_docInfo.getMinX();
	if ((((tc.getCaptionX() > ((docWidth - 30) / (float) 4.0)
		+ m_docInfo.getMinX())) && (tc.getCaptionX() < docWidth / 2.5))
		|| (tc.getCaptonEndX() - tc.getCaptionX() > docWidth
			/ (float) 1.85)) {
	    tc.setWideTable(true);
	    // System.out.println();
	    // System.out.println("This is a wide table? " + tc.isWideTable());
	} else {
	    // System.out.println();
	    // System.out.println("This is a single-doc-column table!");
	}
    }

    /**
     * By default, the location of the table caption is above the table data
     * area. However, for those below-caption tables, we should detect the
     * correct table boundary If the caption position is close to the bottom of
     * the document page, we know this is a bottom-caption table If the caption
     * position is close to the top of the document page, we know this is a
     * top-caption table otherwise, we can judge the table caption position by
     * comparing the text density in the both sides of the caption
     * 
     * @param linesOfAPage
     *            the list of lines of this document page
     * @param tc
     *            the object of the table candidates
     * @param distinctY
     *            the vector of the sorted in-duplicated Y coordinates of all
     *            the text pieces in this tc
     * @param wordsOfAPage
     *            the list of words of this document page
     * @return A boolean value
     */
    private boolean judgeCaptionLocation(ArrayList<TextPiece> linesOfAPage,
	    TableCandidate tc, Vector distinctY,
	    ArrayList<TextPiece> wordsOfAPage) {
	/*
	 * by default, the caption position is above the table data area
	 */
	boolean aboveCaption = true;
	Config config = new Config();
	float captionY = linesOfAPage.get(tc.getCaptionStartLine()).getY();
	float captionEndY = linesOfAPage.get(tc.getCaptionEndLine()).getEndY();
	int yId_distinctY = distinctY.indexOf(captionY);
	if (yId_distinctY < 0) {
	    int i = 1;
	    while (i < distinctY.size()) {
		if (captionY > Float.valueOf(
			distinctY.elementAt(i - 1).toString().trim())
			.floatValue()
			&& captionY < Float.valueOf(
				distinctY.elementAt(i).toString().trim())
				.floatValue()) {
		    yId_distinctY = i;
		    break;
		}
		i++;
	    }
	}
	int endYId_distinctY = yId_distinctY + tc.captionLineCount() - 1;
	if ((distinctY.size() - endYId_distinctY < 4) || (captionEndY > 720.0))
	    aboveCaption = false;
	/*
	 * Method 1: compares the text density, the areas in both directions are
	 * defined as six lines
	 */
	if ((distinctY.size() - endYId_distinctY > 6) && (yId_distinctY > 6)
		&& (aboveCaption == true)) {
	    float captionX = tc.getCaptionX();
	    float captionEndX = tc.getCaptonEndX();
	    float testAreaX = captionX;
	    float testAreaEndX = captionEndX;
	    /*
	     * Manually keep the area wide enough, for those short table caption
	     */
	    if (testAreaEndX - captionX < 40.0f)
		testAreaEndX = testAreaX + 60.0f;
	    int txtDensity_Top = 0;
	    int txtDensity_Below = 0;
	    int lineNumThisPage = linesOfAPage.size();
	    /*
	     * compare the text density in both directions in LINE level for
	     * (int i=0; i<lineNumThisPage; i++) { TextPiece thisLine =
	     * linesOfAPage.get(i); //---Calculate the density in the top area
	     * if ( (thisLine.getY()>=
	     * Float.valueOf(distinctY.get(yId_distinctY-
	     * 5).toString().trim()).floatValue()) && (thisLine.getEndY()<
	     * Float.
	     * valueOf(distinctY.get(yId_distinctY).toString().trim()).floatValue
	     * () ) )// && //(thisLine.getX()>=captionX) &&
	     * //(thisLine.getEndX()<=captionEndX)) txtDensity_Top =
	     * txtDensity_Top + thisLine.getText().trim().length();
	     * //---Calculate the density in the below area else { if (
	     * (thisLine.getEndY()<=
	     * Float.valueOf(distinctY.get(endYId_distinctY
	     * +5).toString().trim()).floatValue()) && (thisLine.getY()>
	     * Float.valueOf
	     * (distinctY.get(endYId_distinctY).toString().trim()).floatValue()
	     * ) )// && //(thisLine.getX()>=captionX) &&
	     * //(thisLine.getEndX()<=captionEndX)) txtDensity_Below =
	     * txtDensity_Below + thisLine.getText().trim().length(); } }
	     */
	    // compare the text density in both directions in WORD level
	    int wordNumThisPage = wordsOfAPage.size();
	    String txtInTopArea = "";
	    String txtInBelowArea = "";
	    boolean foundFigureCaption = false;
	    /*
	     * Judges whether there are figure caption above, if yes, do not
	     * have to check the text density
	     */
	    for (int i = 0; i < linesOfAPage.size(); i++) {
		TextPiece thisLine = linesOfAPage.get(i);
		if ((thisLine.getY() >= Float.valueOf(
			distinctY.get(yId_distinctY - 5).toString().trim())
			.floatValue())
			&& (thisLine.getEndY() < Float.valueOf(
				distinctY.get(yId_distinctY).toString().trim())
				.floatValue())
			&& (thisLine.getText().replaceAll(" ", "")
				.startsWith("Figure") == true || thisLine
				.getText().replaceAll(" ", "")
				.startsWith("FIGURE") == true)) {
		    aboveCaption = true;
		    foundFigureCaption = true;
		}
	    }
	    /*
	     * If no figure caption found above, we have to compare the density
	     * in both directions
	     */
	    if (foundFigureCaption == false) {
		for (int i = 0; i < wordNumThisPage; i++) {
		    TextPiece thisWord = wordsOfAPage.get(i);
		    /*
		     * ---Calculate the density in the top area
		     */
		    if ((thisWord.getY() >= Float.valueOf(
			    distinctY.get(yId_distinctY - 5).toString().trim())
			    .floatValue())
			    && (thisWord.getEndY() < Float.valueOf(
				    distinctY.get(yId_distinctY).toString()
					    .trim()).floatValue())
			    && (thisWord.getX() >= testAreaX)
			    && (thisWord.getEndX() <= testAreaEndX)) {
			txtDensity_Top = txtDensity_Top
				+ thisWord.getText().trim().length();
			txtInTopArea = txtInTopArea + thisWord.getText();
		    }
		    /*
		     * Calculates the density in the below area
		     */
		    else {
			if ((thisWord.getEndY() <= Float.valueOf(
				distinctY.get(endYId_distinctY + 6).toString()
					.trim()).floatValue())
				&& (thisWord.getY() > Float.valueOf(
					distinctY.get(endYId_distinctY)
						.toString().trim())
					.floatValue())
				&& (thisWord.getX() >= captionX)
				&& (thisWord.getEndX() <= captionEndX)) {
			    txtDensity_Below = txtDensity_Below
				    + thisWord.getText().trim().length();
			    txtInBelowArea = txtInBelowArea
				    + thisWord.getText();
			}
		    }
		}
		// System.out.println("txt in Top: " + txtInTopArea);
		// System.out.println("txt in Below: " + txtInBelowArea);
		if (txtDensity_Top < txtDensity_Below)
		    aboveCaption = false;
	    }
	}
	/*
	 * TODO! method 2: the areas to compare the text density in both
	 * directions are defined with Height=55.0
	 * 
	 * if ( (captionY-docInfo.getMinY()>config.DENSITY_AREA_HEIGHT) &&
	 * (docInfo.getMaxY()-captionEndY>config.DENSITY_AREA_HEIGHT) &&
	 * (aboveCaption==true) ) {
	 * 
	 * 
	 * }
	 */
	// if (aboveCaption==true)
	// System.out.println("The caption location is: TOP!");
	// else System.out.println("The caption location is: BELOW!");
	tc.setTopCaption(aboveCaption);
	return aboveCaption;
    }

    /**
     * We do not sort the text pieces according to Y values in the WHOLE page,
     * because if a document contains more than one column and the table is
     * within one document column, this action will bring the text pieces in
     * other document columns as noise texts. By default, we sort Y coordinate
     * values based on the text pieces in the left part of the page If we find a
     * table is not a wide table and located in the right part, we only process
     * the right half of the page
     * 
     * @param distinctY
     *            the vector to store the non-duplicated Y coordinates
     * @param wordsOfAPage
     *            the list of words of this document page
     * @param tc
     *            the object of the table candidates
     * @return the updated vector to store the non-duplicated Y coordinates
     */

    private Vector getDistinctSortedY(Vector distinctY, Vector piecesEachY,
	    ArrayList<TextPiece> wordsOfAPage, TableCandidate tc) {
	int pieceNumThisPage = wordsOfAPage.size();
	float x_columnToGetYs = m_docInfo.getMinX();
	float endX_columnToGetYs = m_docInfo.getMiddleX();
	if ((tc.isWideTable() == false)
		&& (tc.getCaptionX() >= m_docInfo.getMiddleX())) {
	    x_columnToGetYs = m_docInfo.getMiddleX();
	    endX_columnToGetYs = m_docInfo.getMaxX();
	}
	float[] sortY = new float[pieceNumThisPage];
	for (int bb = 0; bb < pieceNumThisPage; bb++) {
	    if ((wordsOfAPage.get(bb).getX() >= x_columnToGetYs)
		    && (wordsOfAPage.get(bb).getEndX() <= endX_columnToGetYs)) {
		sortY[bb] = wordsOfAPage.get(bb).getY();
	    }
	}
	Arrays.sort(sortY);

	int bb = 0;
	float lastY = 0.0f;
	while (bb < pieceNumThisPage) {
	    if (distinctY.size() > 0)
		lastY = ((Float) distinctY.lastElement()).floatValue();
	    if ((sortY[bb] - lastY) >= m_docInfo.getAverageLineGap() / 2.0)
		distinctY.addElement(new Float(sortY[bb]));
	    bb++;
	}
	return distinctY;
    }

    /**
     * Gets the table caption boundary in a document page
     * 
     * @param lineNumber
     *            the line ID of the first caption line in the whole page
     * @param linesOfAPage
     *            the list of lines of this document page
     * @param tc
     *            the object of the table candidates
     * @return int value: the line ID of the next line after the table caption
     *         area
     */
    private int findCaptionBoundary(int lineNumber,
	    ArrayList<TextPiece> linesOfAPage, TableCandidate tc) {
	TextPiece currentLine = linesOfAPage.get(lineNumber);
	TextPiece prevLine = null;
	String caption = currentLine.getText() + " ";

	tc.setCaptionStartLine(lineNumber);
	tc.setCaptionEndLine(lineNumber);
	tc.setCaptionX(currentLine.getX());
	tc.setCaptonEndX(currentLine.getEndX());

	lineNumber++;

	while (lineNumber < linesOfAPage.size()) {
	    currentLine = linesOfAPage.get(lineNumber);
	    prevLine = linesOfAPage.get(lineNumber - 1);

	    float yDiff = currentLine.getY() - prevLine.getY();

	    /*
	     * All the constant values are set based on extensive observation
	     * and experiment. Of course, these values still need improvement
	     * for better results. Machine learning methods can be used to tune
	     * these values.
	     */
	    if (yDiff == 0.0 // Case 1: same line text pieces
		    || (yDiff <= m_docInfo.getAverageLineGap() * 1.35 // case 2:
								      // the
								      // next
								      // long
								      // caption
								      // line
			    && yDiff > 0.0
			    && !currentLine.getText().startsWith(
				    tc.getKeyword())
			    && (currentLine.getX() < prevLine.getEndX()) && (currentLine
			    .getEndX() - currentLine.getX()) >= (m_docInfo
			    .getMaxX() - m_docInfo.getMinX()) / 4.0)
		    || Math.abs(yDiff) < m_docInfo.getAverageLineGap() / 2.0 // case
									     // 3:
		    && ((currentLine.getX() - prevLine.getEndX()) <= m_docInfo
			    .getAverageXGap() * 3.0)
		    || (currentLine.getX() < prevLine.getEndX() // case 4: the
								// last short
								// caption line
			    && currentLine.getWidth() < (m_docInfo.getMaxX() - m_docInfo
				    .getMinX()) / 4.0
			    && yDiff > 0
			    && yDiff <= m_docInfo.getAverageLineGap() * 1.35 && prevLine
			    .getWidth() > (m_docInfo.getMaxX() - m_docInfo
			    .getMinX()) / 4.0)) {
		/*
		 * this is still a caption line
		 */
		caption += currentLine.getText() + " ";

		/*
		 * Updates caption-x
		 */
		if (currentLine.getX() < tc.getCaptionX()) {
		    tc.setCaptionX(currentLine.getX());
		}

		/*
		 * Updates caption-end-x
		 */
		if (currentLine.getEndX() > tc.getCaptonEndX()) {
		    tc.setCaptonEndX(currentLine.getEndX());
		}

		lineNumber++;
	    } else {
		/*
		 * if the caption is: "Table 1", usually the caption body is in
		 * the next line
		 */
		if (caption.replaceAll(" ", "").length() < 10) {
		    caption += currentLine.getText() + " ";
		    if (currentLine.getX() < tc.getCaptionX()) {
			tc.setCaptionX(currentLine.getX());
		    }

		    /*
		     * Updates caption-end-x
		     */
		    if (currentLine.getEndX() > tc.getCaptonEndX()) {
			tc.setCaptonEndX(currentLine.getEndX());
		    }
		    lineNumber++;
		} else
		    break;
	    }
	}

	// System.out.println(caption);
	tc.setCaption(caption);
	tc.setCaptionEndLine(lineNumber - 1);

	/*
	 * not a valid table if there are too many caption lines
	 */
	if (tc.captionLineCount() > 15)
	    tc.setValid(false);
	if (tc.getCaption().compareToIgnoreCase("Table of Content") == 1)
	    tc.setValid(false);
	if (tc.isValid()) {
	    float captionX = 1000;
	    float captionEndX = 0;
	    for (int i = tc.getCaptionStartLine(); i <= tc.getCaptionEndLine(); i++) {
		currentLine = linesOfAPage.get(i);
		if (currentLine.getX() < captionX)
		    captionX = currentLine.getX();
		if (currentLine.getEndX() > captionEndX)
		    captionEndX = currentLine.getEndX();
	    }
	    tc.setCaptionX(captionX);
	    tc.setCaptonEndX(captionEndX);
	}
	return lineNumber;
    }

    /**
     * Gets the table body boundary in a document page
     * 
     * @param lineNumber
     *            the line ID of the first line in the table boundary
     * @param linesOfAPage
     *            the list of lines of this document page
     * @param tc
     *            the object of the table candidates
     * @return int value: the line ID of the next line after the table body area
     */
    private int findBodyBoundary(int lineNumber,
	    ArrayList<TextPiece> linesOfAPage, TableCandidate tc,
	    boolean aboveCaption, int prevTableEndIndex) {
	float maxCellWidth;
	String thisTableData = "";
	String classificationData = "";
	if (tc.getCaptionX() > 150 && tc.getCaptionX() < 250
		|| tc.captionWidth() > 350)
	    maxCellWidth = m_docInfo.getAverageLineWidth() / (float) 1.4;
	else
	    maxCellWidth = m_docInfo.getAverageLineWidth() / (float) 1.65;

	tc.setBodyEndX(1000);
	tc.setBodyX(0);

	if (aboveCaption == true) {
	    tc.setBodyStartLine(lineNumber);
	    tc.setValid(true);
	    while (lineNumber < linesOfAPage.size()) {
		assert lineNumber > 0;
		TextPiece currentLine = linesOfAPage.get(lineNumber);
		TextPiece prevLine = linesOfAPage.get(lineNumber - 1);
		float yDiff = currentLine.getY() - prevLine.getY();
		String text = currentLine.getText();
		String textWithoutSpace = text.replace(" ", "");

		if (currentLine.getY() >= linesOfAPage.get(
			tc.getCaptionEndLine()).getY()
			&& (textWithoutSpace.startsWith(tc.getKeyword()) == false)
			&& (textWithoutSpace.startsWith("FIGURE") == false)
			&& (textWithoutSpace.startsWith("Figure") == false)
			&& (textWithoutSpace.startsWith("Fig.") == false)) {

		    if ((currentLine.getWidth() < maxCellWidth)
			    || ((currentLine.getWidth() >= maxCellWidth) && (currentLine
				    .isSparseLine() == true)) // && yDiff <
							      // m_docInfo.getBodyTextScale()
							      // * 20 && yDiff
							      // >=
							      // -(m_docInfo.getBodyTextScale()*3.0)))
							      // {
			    || text.startsWith("*") || text.startsWith("$")
			    || text.startsWith("t ") || text.startsWith("a ")
			    || text.startsWith("1 ") || text.startsWith("?")
			    || text.startsWith("''")
			    || text.startsWith("Note:")
			    || linesOfAPage.size() - lineNumber < 2
			    || currentLine.isSuperScriptBeginning()) {
			// this is a cell, update body-x
			if (currentLine.getX() < tc.getBodyX()) {
			    tc.setBodyX(currentLine.getX());
			}
			if (currentLine.getEndX() > tc.getBodyEndX()) {
			    tc.setBodyEndX(currentLine.getEndX());
			}
			thisTableData = thisTableData + text + " ";
			lineNumber++;
		    } else
			break;
		} else
		    break;
	    }
	    tc.setBodyEndLine(lineNumber - 1);
	    if (tc.cellCount() < 4 || tc.getCaptonEndX() < tc.getBodyX()
		    || tc.getBodyEndX() < tc.getCaptionX()) {
		tc.setValid(false);
		lineNumber = tc.getCaptionEndLine() + 1;
	    }
	}

	else {
	    tc.setBodyEndLine(tc.getCaptionStartLine() - 1);
	    lineNumber = tc.getCaptionStartLine() - 1;
	    tc.setValid(true);
	    while (lineNumber > prevTableEndIndex) {
		assert lineNumber > 0;
		TextPiece currentLine = linesOfAPage.get(lineNumber);
		TextPiece prevLine = linesOfAPage.get(lineNumber - 1);
		float yDiff = currentLine.getY() - prevLine.getY();
		String text = currentLine.getText();
		String textWithoutSpace = text.replace(" ", "");

		if (currentLine.getEndY() <= linesOfAPage.get(
			tc.getCaptionStartLine()).getEndY()
			&& (textWithoutSpace.startsWith(tc.getKeyword()) == false)
			&& (textWithoutSpace.startsWith("FIGURE") == false)
			&& (textWithoutSpace.startsWith("Figure") == false)
			&& (textWithoutSpace.startsWith("Fig.") == false)) {
		    if (text.startsWith("*")
			    || text.startsWith("$")
			    || text.startsWith("t ")
			    || text.startsWith("a ")
			    || text.startsWith("1 ")
			    || text.startsWith("?")
			    || text.startsWith("''")
			    || text.startsWith("Note:")
			    || currentLine.isSuperScriptBeginning()
			    || linesOfAPage.size() - lineNumber < 2
			    || (currentLine.getWidth() < maxCellWidth)
			    || ((currentLine.getWidth() >= maxCellWidth) && (currentLine
				    .isSparseLine() == true))) {
			// && !textWithoutSpace.startsWith(tc.getKeyword()) &&
			// yDiff < m_docInfo.getBodyTextScale() * 20 && yDiff >=
			// -(m_docInfo.getBodyTextScale()*3.0))) {
			/*
			 * this is a cell, update body-x
			 */
			if (currentLine.getX() < tc.getBodyX()) {
			    tc.setBodyX(currentLine.getX());
			}
			if (currentLine.getEndX() > tc.getBodyEndX()) {
			    tc.setBodyEndX(currentLine.getEndX());
			}
			thisTableData = thisTableData + text + " ";
			lineNumber--;
		    } else
			break;
		} else
		    break;
	    }
	    /*
	     * To include the first cell of the table with below caption in the
	     * top of the page
	     */
	    if (lineNumber == 0) {
		TextPiece currentLine = linesOfAPage.get(lineNumber);
		if (currentLine.getX() < tc.getBodyX()) {
		    tc.setBodyX(currentLine.getX());
		}
		if (currentLine.getEndX() > tc.getBodyEndX()) {
		    tc.setBodyEndX(currentLine.getEndX());
		}
		thisTableData = thisTableData + currentLine.getText() + " ";
		lineNumber--;
	    }
	    tc.setBodyStartLine(lineNumber + 1);
	    lineNumber = tc.getCaptionEndLine() + 1;

	    if (tc.cellCount() < 4 || tc.getCaptonEndX() < tc.getBodyX()
		    || tc.getBodyEndX() < tc.getCaptionX()) {
		tc.setValid(false);
		lineNumber = tc.getCaptionEndLine() + 1;
	    }
	}
	// System.out.println(thisTableData);
	tc.setMetadataHighLevel(thisTableData);
	return lineNumber;
    }

    /**
     * Checks and returns the table caption keyword at the beginning of a give
     * line
     * 
     * @param line
     *            input line
     * @return matched keyword if found / null if not found
     */
    private String getMatchedTabledKeyword(String line) {
	for (String keyword : Config.TABLE_KEYWORDS) {
	    if (line.startsWith(keyword)) {
		return keyword;
	    }
	}
	return null;
    }

    /**
     * Checks and returns the figure caption keyword at the beginning of a give
     * line
     * 
     * @param line
     *            input line
     * @return matched keyword if found / null if not found
     */
    private String getMatchedFigureKeyword(String line) {
	for (String keyword : Config.FIGURE_KEYWORDS) {
	    if (line.startsWith(keyword)) {
		return keyword;
	    }
	}
	return null;
    }

    /**
     * Returns the current PDF parser
     * 
     * @return the parser
     */
    public IPdfParser getParser() {
	return m_parser;
    }

    /**
     * Sets a PDF parser
     * 
     * @param parser
     *            the PDF parser to set. //int cellsThisLine =
     *            tc.getRows().get(pp).getCells().size();
     */
    public void setParser(IPdfParser parser) {
	this.m_parser = parser;
    }

    /**
     * Organizes cells into rows based on the coordinate information
     * 
     * @param linesOfAPage
     *            the list of lines of this document page
     * @param tc
     *            the object of the table candidate
     */
    private void organizeCells(ArrayList<TextPiece> linesOfAPage,
	    TableCandidate tc) {
	float captionY = linesOfAPage.get(tc.getCaptionStartLine()).getY();
	float captionEndY = linesOfAPage.get(tc.getCaptionEndLine()).getEndY();
	ArrayList<TextPiece> rawCells = new ArrayList<TextPiece>();
	/*
	 * Copy & sort
	 */
	for (int i = tc.getBodyStartLine(); i <= tc.getBodyEndLine(); i++) {
	    rawCells.add(linesOfAPage.get(i));
	}

	Collections.sort(rawCells);
	assert rawCells.size() >= 4; // due to previous check
	TableRow currentRow = null; // creates first row

	for (TextPiece cell : rawCells) {
	    /*
	     * Skips lines beyond caption //TODO: remove the far Y
	     */
	    if (tc.isTopCaption() && (cell.getY() < captionY))
		continue;
	    if (!tc.isTopCaption() && (cell.getEndY() > captionEndY))
		continue;

	    /*
	     * Lines too close to each other are treated as a single line
	     */
	    if (currentRow != null
		    && cell.getY() - currentRow.getY() < m_docInfo
			    .getAverageLineGap() / 2.0)
		currentRow.addCell(cell);
	    else {
		/*
		 * Creates a new row
		 */
		currentRow = new TableRow();
		currentRow.setY(cell.getY());
		if (cell.isSuperScriptBeginning())
		    currentRow.setSuperScriptRow(true);
		currentRow.addCell(cell);
		tc.addRow(currentRow);
	    }
	}
	if (tc.getRows().size() < 2)
	    tc.setValid(false);
    }

    /**
     * Finds the first line of the potential table footnote
     * 
     * @param tc
     *            the object of the table candidate
     */
    private void findFootnoteBeginRow(TableCandidate tc) {
	for (int i = 0; i < tc.getRows().size(); i++) {
	    TableRow row = tc.getRows().get(i);
	    TextPiece firstCell = row.getCells().get(0);
	    String text = firstCell.getText();
	    if (text.startsWith("*")
		    || text.startsWith("t ")
		    || text.startsWith("$")
		    || text.startsWith("?")
		    || text.startsWith("''")
		    || text.startsWith("Note:")
		    || (text.startsWith("a") && i >= tc.getRows().size() - 2 && row
			    .isSuperScriptRow())) {
		tc.setFootnoteBeginRow(i);
		break;
	    }
	}
	if (tc.getFootnoteBeginRow() == -1)
	    tc.setFootnoteBeginRow(tc.getRows().size());
    }

    /**
     * Adjusts the table heading rows and footnote rows
     * 
     * @param tc
     *            the object of the table candidate
     */
    private void determineFootnoteHeading(TableCandidate tc) {
	/*
	 * Gets the maximal column number based on all table rows before
	 * footnote
	 */
	int maxColumnNum = 0;
	for (int i = 0; i < tc.getFootnoteBeginRow(); i++) {
	    TableRow row = tc.getRows().get(i);
	    if (row.getCells().size() > maxColumnNum) {
		maxColumnNum = row.getCells().size();
	    }
	}
	tc.setMaxColumnNumber(maxColumnNum);

	/*
	 * Counts the number of table column heading lines TODO: this heading
	 * finding algorithm should be improved
	 */
	int headingLineNumber = 0;
	for (int i = 0; i < tc.getFootnoteBeginRow(); i++) {
	    TableRow row = tc.getRows().get(i);
	    if (row.getCells().size() < maxColumnNum) {
		headingLineNumber++;
	    } else
		break;
	}
	/*
	 * Based on observation, usually we have missing cells, especially
	 * happen in the first column
	 */
	if (headingLineNumber > 0)
	    headingLineNumber--;
	tc.setHeadingLineNumber(Math.max(0, headingLineNumber));
    }

    /**
     * Analyzes the table column information without considering the table
     * heading rows
     * 
     * @param tc
     *            the object of the table candidate
     */

    private void getColumnBoundaryWithoutHeading(TableCandidate tc) {
	int heads = tc.getHeadingLineNumber() + 1;
	int MAXcolumnNum = tc.getMaxColumnNumber();
	int footnoteLineIndex = tc.getFootnoteBeginRow();
	TableRow row;
	if (MAXcolumnNum == 0)
	    return;

	float[] leftX_tableColumns = new float[MAXcolumnNum * 4];
	float[] rightX_tableColumns = new float[MAXcolumnNum * 4];
	for (int qq = 0; qq < MAXcolumnNum * 4; qq++) {
	    leftX_tableColumns[qq] = 1000.0f;
	    rightX_tableColumns[qq] = 0.0f;
	}
	int cc = 1;

	boolean fullRowExists = false;
	int maxTextPieceInTableContent = 0;
	/*
	 * Checks whether there is a data line that has the max number of the
	 * text pieces
	 */
	for (int pp = heads; pp < footnoteLineIndex; pp++) {
	    row = tc.getRows().get(pp);
	    if (maxTextPieceInTableContent < row.getCells().size())
		maxTextPieceInTableContent = row.getCells().size();
	}
	if (maxTextPieceInTableContent == MAXcolumnNum)
	    fullRowExists = true;
	/*
	 * Adjusts leftX_tableColumns and rightX_tableColumns based on the cells
	 */
	for (int pp = heads; pp < footnoteLineIndex; pp++) {
	    row = tc.getRows().get(pp);
	    if (pp == heads) {
		leftX_tableColumns[0] = row.getCells().get(0).getX(); // pmoj.cmdX_thisTable[heads][0];
		rightX_tableColumns[0] = row.getCells().get(0).getEndX(); // rightX_tableColumns[0]=pmoj.cmdEndX_thisTable[heads][0];
	    }

	    if (((fullRowExists == true) && (row.getCells().size() == MAXcolumnNum))
		    || (fullRowExists == false)) {
		/*
		 * Each text piece/cell in a row
		 */
		for (int qq = 0; qq < row.getCells().size(); qq++) {
		    /*
		     * Gets a new leftmost column
		     */
		    if (((leftX_tableColumns[0] - row.getCells().get(qq)
			    .getEndX()) > m_docInfo.getAverageXGap() * 2.0)
			    && (row.getCells().get(qq).getEndX() > 0.0f)) {
			for (int ii = cc; ii > 0; ii--) {
			    leftX_tableColumns[ii] = leftX_tableColumns[ii - 1];
			    rightX_tableColumns[ii] = rightX_tableColumns[ii - 1];
			}
			leftX_tableColumns[0] = row.getCells().get(qq).getX();
			rightX_tableColumns[0] = row.getCells().get(qq)
				.getEndX();
			cc++;
		    } else {
			/*
			 * Gets a new rightmost column
			 */
			if (((row.getCells().get(qq).getX() - rightX_tableColumns[cc - 1]) > m_docInfo
				.getAverageXGap() * 2.0)
				&& (row.getCells().get(qq).getX() < 1000.0f)) {
			    leftX_tableColumns[cc] = row.getCells().get(qq)
				    .getX();
			    rightX_tableColumns[cc] = row.getCells().get(qq)
				    .getEndX();
			    cc++;
			}
			/*
			 * A new column
			 */
			else {
			    int thisC = 1;
			    boolean newMidColumn = false;
			    while (thisC < cc) {
				if (((row.getCells().get(qq).getX() - rightX_tableColumns[thisC - 1]) > m_docInfo
					.getAverageXGap() * 2.0)
					&& ((leftX_tableColumns[thisC] - row
						.getCells().get(qq).getEndX()) > m_docInfo
						.getAverageXGap() * 2.0)
					&& (row.getCells().get(qq).getX() < 1000.0f)
					&& (row.getCells().get(qq).getEndX() > 0.0f)) {
				    for (int ii = cc; ii > thisC; ii--) {
					leftX_tableColumns[ii] = leftX_tableColumns[ii - 1];
					rightX_tableColumns[ii] = rightX_tableColumns[ii - 1];
				    }
				    cc++;
				    leftX_tableColumns[thisC] = row.getCells()
					    .get(qq).getX();
				    rightX_tableColumns[thisC] = row.getCells()
					    .get(qq).getEndX();
				    newMidColumn = true;
				    break;
				}
				thisC++;
			    }
			    if (newMidColumn == false) {
				thisC = 0;
				cc = adjustAColumn(cc, row.getCells().get(qq)
					.getX(), row.getCells().get(qq)
					.getEndX(), leftX_tableColumns,
					rightX_tableColumns);
				tc.setColumnNum(cc);
			    }
			}// end{middleColumn}
		    }// end {else}
		}// end{for all pieces in a line}
	    }// end if()
	}// end for()

	/*
	 * Adjusts columns with the information of heading rows
	 */
	for (int pp = 0; pp < heads; pp++) {
	    int cellsThisLine = tc.getRows().get(pp).getCells().size();
	    for (int dd = 0; dd < cellsThisLine; dd++) {
		float b = tc.getRows().get(pp).getCells().get(dd).getX();
		float e = tc.getRows().get(pp).getCells().get(dd).getEndX();
		cc = adjustAColumn(cc, b, e, leftX_tableColumns,
			rightX_tableColumns);
	    }// end(for)
	}// end(for)
	tc.setColumnNum(cc);
	tc.setLeftX_tableColumns(leftX_tableColumns);
	tc.setRightX_tableColumns(rightX_tableColumns);
    }

    /**
     * Adjusts the table column information by analyzing the coordinate
     * information of each text pieces in the table bounday area
     * 
     * @param cc
     *            the number of the table column
     * @param b
     *            the X-axis of the left-end of the current table piece
     * @param e
     *            the X-axis of the right end of the current table piece
     * @param leftX_tableColumns
     *            the array to store the left-end X axes for all the table
     *            columns
     * @param rightX_tableColumns
     *            the array to store the right-end X axes for all the table
     *            columns
     * @return the updated table column information (int value)
     */
    public int adjustAColumn(int cc, float b, float e,
	    float[] leftX_tableColumns, float[] rightX_tableColumns) {
	for (int bb = 0; bb < cc; bb++) {
	    float c = leftX_tableColumns[bb];
	    float d = rightX_tableColumns[bb];
	    if ((bb > 0) && (bb < (cc - 1))) {
		float a = rightX_tableColumns[bb - 1];
		float f = leftX_tableColumns[bb + 1];

		if ((a < b) && (b <= c) && (d <= e) && (e < f)) { // case 1:
								  // both ends
								  // of the new
								  // cell exceed
								  // the
								  // boundary of
								  // current
								  // column, but
								  // not overlap
								  // with the
								  // 2nd column.
								  // We should
								  // update the
								  // boundary of
								  // the current
								  // column in
								  // both ends
		    leftX_tableColumns[bb] = b;
		    rightX_tableColumns[bb] = e;
		}
		if ((a < b) && (b < c) && (c < e) && (e < d)) // case 2: only
							      // the left end of
							      // the new cell
							      // exceed the
							      // boundary of the
							      // current column,
							      // only need to
							      // update the left
							      // side
		    leftX_tableColumns[bb] = b;
		if ((c < b) && (b < d) && (d < e) && (e < f)) // case 3: only
							      // the right end
							      // of the new cell
							      // exceed the
							      // boundary of the
							      // current column,
							      // only need to
							      // update the
							      // right side
		    rightX_tableColumns[bb] = e;
	    }
	    if (bb == 0) {
		float f = leftX_tableColumns[bb + 1];
		if ((b <= c) && (d <= e) && (e < f)) { // case 4: both ends of
						       // the new cell exceed
						       // the boundary of first
						       // column, but not
						       // overlap with the 2nd
						       // column. We should
						       // update the boundary of
						       // the 1st column in both
						       // ends
		    leftX_tableColumns[0] = b;
		    rightX_tableColumns[0] = e;
		}
		if ((b < c) && (c < e) && (e < d)) // case 5: only the left end
						   // of the new cell exceed the
						   // boundary of the 1st
						   // column, only need to
						   // update the left side
		    leftX_tableColumns[0] = b;
		if ((c < b) && (b < d) && (d < e) && (e < f)) // case 6: only
							      // the right end
							      // of the new cell
							      // exceed the
							      // boundary of the
							      // 1st column,
							      // only need to
							      // update the
							      // right side
		    rightX_tableColumns[0] = e;
		if ((c - e) > m_docInfo.getAverageLineGap() * 2.0) { // new
								     // left-most
								     // column
		    cc++;
		    for (int t = (cc - 1); t > 0; t--) {
			leftX_tableColumns[t] = leftX_tableColumns[t - 1];
			rightX_tableColumns[t] = rightX_tableColumns[t - 1];
		    }
		    leftX_tableColumns[0] = b;
		    rightX_tableColumns[0] = e;
		}
	    }
	    if (bb == (cc - 1) && (bb > 0)) {
		float a = rightX_tableColumns[bb - 1];
		if ((a < b) && (b <= c) && (d <= e)) { // case 7
		    leftX_tableColumns[bb] = b;
		    rightX_tableColumns[bb] = e;
		}
		if ((a < b) && (b < c) && (c < e) && (e < d)) // case 8
		    leftX_tableColumns[bb] = b;
		if ((c < b) && (b < d) && (d < e)) // case 9
		    rightX_tableColumns[bb] = e;
		if ((b - d) > m_docInfo.getAverageLineGap() * 2.0) { // new
								     // right-most
								     // column
		    cc++;
		    leftX_tableColumns[cc - 1] = b;
		    rightX_tableColumns[cc - 1] = e;
		}
	    }
	}// end(for bb<cc)
	return (cc);
    }

    /**
     * Implements the table structure decomposition step
     * 
     * @param cc
     *            the number of the table columns
     * @param tc
     *            the object of the table candidate
     * @param wordsOfAPage
     *            the list of all the words in a document page
     */
    public void getTableStructure(int cc, TableCandidate tc,
	    ArrayList<TextPiece> wordsOfAPage) {
	float minGapBtwColumns = 1000.0f;
	float[] leftX_tableColumns = tc.getLeftX_tableColumns();
	float[] rightX_tableColumns = tc.getRightX_tableColumns();
	int YNum = tc.getRows().size();

	for (int zz = 1; zz < cc; zz++) {
	    float thisColumnGap = leftX_tableColumns[zz]
		    - rightX_tableColumns[zz - 1];
	    if (thisColumnGap < minGapBtwColumns)
		minGapBtwColumns = thisColumnGap;
	}
	double columnGapThreshold = 0.0;
	// if (withoutKwd==true) columnGapThreshold =
	// parameters.ave_X_Gap_inLine*1.6;
	if (minGapBtwColumns > columnGapThreshold) {
	    getEachCellContent(YNum, cc, tc);
	    getRealHeadingBasedOnCells(YNum, cc, tc);
	    getColumnHeading(tc);
	    getRowHeadingBasedOnCells(tc);
	    tc.setMetadataStructureLevel(YNum, cc, wordsOfAPage, m_docInfo);
	    // tc.setMetaStructureLevelx(YNum, cc, wordsOfAPage, m_docInfo);

	    if ((tc.getFootnoteBeginRow() > 1) & (tc.getMaxColumnNumber() > 1)) {
	    } else
		tc.setValid(false);
	} else {
	    m_docInfo
		    .setErrorMsg("Although we detected some tabular structures in page "
			    + (tc.getPageId_thisTable() + 1)
			    + ", we do not treat them as tables because the space gaps between columns are not large enough.");
	}
    }

    /**
     * Gets the content of each table cell
     * 
     * @param YNum
     *            The number of non-duplicated Y axes in the table area
     * @param cc
     *            the number of the table columns
     * @param tc
     *            the object of the table candidate
     */
    public void getEachCellContent(int YNum, int cc, TableCandidate tc) {
	int footnoteLineIndex = tc.getFootnoteBeginRow();
	String[] contentsInRows = new String[footnoteLineIndex];
	float[] leftX_tableColumns = tc.getLeftX_tableColumns();
	float[] rightX_tableColumns = tc.getRightX_tableColumns();
	String[][] cells = new String[YNum][cc];
	boolean[][] crossCells = new boolean[YNum][cc];
	for (int tt = 0; tt < tc.getRows().size(); tt++) {
	    for (int qq = 0; qq < cc; qq++) {
		cells[tt][qq] = "";
		crossCells[tt][qq] = false;
	    }
	}
	for (int tt = 0; tt < footnoteLineIndex; tt++) {
	    String cellsThisLine = "";
	    TableRow row = tc.getRows().get(tt);
	    int cellThisLine = row.getCells().size(); // pmoj.objectNum_eachY[tt];
	    for (int zz = 0; zz < cellThisLine; zz++) {
		for (int qq = 0; qq < cc; qq++) {
		    if ((row.getCells().get(zz).getX() >= leftX_tableColumns[qq])
			    && (row.getCells().get(zz).getEndX() <= rightX_tableColumns[qq]))
			cells[tt][qq] = cells[tt][qq]
				+ row.getCells().get(zz).getText() + " ";
		    if ((row.getCells().get(zz).getX() < leftX_tableColumns[qq])
			    && (leftX_tableColumns[qq] <= row.getCells()
				    .get(zz).getEndX()))
			cells[tt][qq] = cells[tt][qq]
				+ row.getCells().get(zz).getText() + " ";
		    if ((leftX_tableColumns[qq] >= row.getCells().get(zz)
			    .getX())
			    && (row.getCells().get(zz).getX() >= rightX_tableColumns[qq])
			    && (rightX_tableColumns[qq] < row.getCells()
				    .get(zz).getEndX()))
			cells[tt][qq] = cells[tt][qq]
				+ row.getCells().get(zz).getText() + " ";
		}
	    }
	    for (int zz = 0; zz < cellThisLine; zz++) {
		int crossCellBegin = 1000;
		int crossCellEnd = 0;
		for (int qq = 0; qq < cc; qq++) {
		    if ((qq > 0)
			    && (cells[tt][qq - 1].length() == 0)
			    && (row.getCells().get(zz).getX() <= rightX_tableColumns[qq - 1])
			    && (row.getCells().get(zz).getX() < leftX_tableColumns[qq])
			    && (leftX_tableColumns[qq] <= row.getCells()
				    .get(zz).getEndX())) {
			crossCells[tt][qq] = true;
			crossCells[tt][qq - 1] = true;
			cells[tt][qq - 1] = row.getCells().get(zz).getText();
			crossCellBegin = qq - 1;
		    }
		    if ((qq < (cc - 1))
			    && (cells[tt][qq + 1].length() == 0)
			    && (row.getCells().get(zz).getEndX() >= leftX_tableColumns[qq + 1])
			    && (row.getCells().get(zz).getX() <= rightX_tableColumns[qq])
			    && (rightX_tableColumns[qq] < row.getCells()
				    .get(zz).getEndX())) {
			crossCells[tt][qq] = true;
			crossCells[tt][qq + 1] = true;
			cells[tt][qq + 1] = row.getCells().get(zz).getText();
			crossCellEnd = qq + 1;
		    }
		}
	    }
	    for (int qq = 0; qq < cc; qq++) {
		cellsThisLine = cellsThisLine + cells[tt][qq] + "; ";
	    }
	    contentsInRows[tt] = cellsThisLine;
	}
	tc.setCells(cells);
	tc.setCrossCells(crossCells);
    }

    /**
     * Adjusts the real table heading rows based on the cell information
     * 
     * @param YNum
     *            The number of non-duplicated Y axes in the table area
     * @param cc
     *            the number of the table columns
     * @param tc
     *            the object of the table candidate
     */
    public void getRealHeadingBasedOnCells(int YNum, int cc, TableCandidate tc) {
	int footnoteLineIndex = tc.getFootnoteBeginRow();
	String[][] cells = tc.getCells();
	int i = 0;
	while (i < footnoteLineIndex) {
	    int j = 0, nonNullCellNum = 0;
	    while (j < cc) {
		if (cells[i][j] == "") {
		    break;
		}
		nonNullCellNum++;
		j++;
	    }
	    if (nonNullCellNum == cc)
		break;
	    else
		i++;
	}
	/*
	 * if the next row contain unit symbols, this row has a very large
	 * possibility to be a table column heading row
	 */
	if ((i + 1) < footnoteLineIndex) {
	    boolean hasUnitSymbols = false;
	    int j = 0;
	    while (j < cc) {
		if ((cells[i + 1][j].indexOf("/yg rnI--") > 0)
			|| (cells[i + 1][j].indexOf("(%)") > 0)
			|| (cells[i + 1][j].indexOf("w-'") > 0)
			|| (cells[i + 1][j].indexOf("CLg g-'") > 0)
			|| (cells[i + 1][j].indexOf("pg ml-1") >= 0)) {
		    i++;
		    break;
		}
		j++;
	    }
	}
	/*
	 * Based on observation, the column heading rows always contain missing
	 * cells. But the first data row always does not contain missing cell
	 */
	if (i == YNum)
	    i = 0;
	if (i > 0)
	    i--;
	tc.setHeadingLineNumber(i);
    }

    /**
     * Gets the table column headings
     * 
     * @param tc
     *            the object of the table candidate
     */
    public void getColumnHeading(TableCandidate tc) {
	int headings = tc.getHeadingLineNumber();
	String[][] cells = tc.getCells();
	String columnHeadings = "";
	for (int i = 0; i <= headings; i++) {
	    int j = cells[i].length;
	    for (int k = 0; k < j; k++) {
		cells[i][k] = tc.replaceAllSpecialChracters(cells[i][k]);
		columnHeadings = columnHeadings + cells[i][k] + "; ";
	    }
	    columnHeadings = columnHeadings + "\n";
	}
	tc.setColumnHeadings(columnHeadings);
    }

    /**
     * Get the table row headings based on the cell information
     * 
     * @param tc
     *            the object of the table candidate
     */
    public void getRowHeadingBasedOnCells(TableCandidate tc) {
	String rowHeadingThisTable = "";
	int footnoteLineIndex = tc.getFootnoteBeginRow();
	String[][] cells = tc.getCells();
	// if (cells[0][0].length()==0 ) {
	for (int j = 0; j < footnoteLineIndex; j++) {
	    if (cells[j][0].length() > 0)
		rowHeadingThisTable = rowHeadingThisTable + cells[j][0] + "; ";
	}
	// }
	rowHeadingThisTable = tc
		.replaceAllSpecialChracters(rowHeadingThisTable);
	tc.setHeadingRows(rowHeadingThisTable);
    }

    /**
     * Gets the reference text of this table in the whole document
     * 
     * @param tc
     *            the object of the table candidate
     * @param linesByPage
     *            the list of all lines in a document page
     */
    public void getReferenceTableTexts(TableCandidate tc,
	    ArrayList<ArrayList<TextPiece>> linesByPage) {
	/*
	 * Gets the table caption beginning part, e.g., "Table 4"
	 */
	String thisCaption = tc.getCaption().replace(" ", "");
	char lastCharInThisKwd = tc.getKeyword().charAt(
		tc.getKeyword().length() - 1);
	int indexAfterKwd = thisCaption.indexOf(lastCharInThisKwd);
	int ii = indexAfterKwd + 1;
	int captionIndex_start = ii;
	int captionIndex_end = ii + 1;
	while ((ii < thisCaption.length() - 1)
		&& (captionIndex_end - captionIndex_start < 2)
		&& (captionIndex_end >= captionIndex_start)) { // for: Table 12
							       // 4 datasets.
							       // for such case,
							       // we may get
							       // "Tabl124" to
							       // start the
							       // reference text
							       // search,
							       // without the
							       // 2nd condition.
	    String nextChar = thisCaption.substring(ii, ii + 1);
	    if ((nextChar.compareToIgnoreCase(".") != 0)
		    && (nextChar.matches("[0-9]") == true)
		    && (nextChar.compareToIgnoreCase(":") != 0)
		    && (nextChar.compareToIgnoreCase(",") != 0))
		ii++;
	    else {
		captionIndex_end = ii;
		break;
	    }
	}
	String thisTableCaptionBeginning = thisCaption.substring(0, ii);
	/*
	 * Gets each character in the table caption beginning part
	 */
	int cationBeginningLength = thisTableCaptionBeginning.length();
	char[] charsInCaptionBeginning = new char[cationBeginningLength];
	for (int i = 0; i < cationBeginningLength; i++) {
	    charsInCaptionBeginning[i] = thisTableCaptionBeginning.charAt(i);
	}

	if (linesByPage != null && linesByPage.size() != 0
		&& thisTableCaptionBeginning.length() > 5) {
	    for (int k = 0; k < linesByPage.size(); k++) {
		ArrayList<TextPiece> linesThisPage = linesByPage.get(k);
		for (int j = 0; j < linesThisPage.size(); j++) {
		    String thisLine = linesThisPage.get(j).getText()
			    .replace(" ", "");
		    if ((k == tc.getPageId_thisTable())
			    && (j >= tc.getCaptionStartLine())
			    && (j <= tc.getCaptionEndLine())) {
		    }// filter out the table caption itself
		    else {
			if ((thisLine).contains(thisTableCaptionBeginning) == true) {
			    /*
			     * Finds the Index of the Table Caption beginning
			     * part in the matched document line. E.g., the "2"
			     * in "Table 2."
			     */
			    String thisLine_Orig = linesThisPage.get(j)
				    .getText();
			    int kk = thisLine
				    .indexOf(thisTableCaptionBeginning);

			    int beginIndex = thisLine_Orig.indexOf(
				    charsInCaptionBeginning[0], kk);
			    int endIndex = thisLine_Orig
				    .indexOf(
					    charsInCaptionBeginning[cationBeginningLength - 1],
					    beginIndex) + 1;
			    String thisPiece = "";
			    while (beginIndex < thisLine_Orig.length()
				    && endIndex < thisLine_Orig.length()
				    && (endIndex - beginIndex) > 5
				    && beginIndex >= 0 && endIndex > 0) {
				thisPiece = thisLine_Orig.substring(beginIndex,
					endIndex).replace(" ", "");
				/*
				 * for "Table 11", "thisPiece" can be "Table1"
				 */
				if (thisTableCaptionBeginning
					.startsWith(thisPiece)) {
				    while ((thisPiece.length() < thisTableCaptionBeginning
					    .length())
					    && (endIndex < thisLine_Orig
						    .length() - 1)) {
					endIndex++;
					thisPiece = thisLine_Orig.substring(
						beginIndex, endIndex).replace(
						" ", "");
				    }
				    break;
				} else {
				    beginIndex = thisLine_Orig.indexOf(
					    charsInCaptionBeginning[0],
					    endIndex);
				    endIndex = thisLine_Orig
					    .indexOf(
						    charsInCaptionBeginning[cationBeginningLength - 1],
						    beginIndex);
				}
			    }

			    if ((endIndex < thisLine_Orig.length() - 3)
				    && (endIndex > 0)
				    && (beginIndex >= 0)
				    && (thisLine_Orig.substring(endIndex,
					    endIndex + 2).matches("[ ][0-9]") == false)
				    && (thisLine_Orig.substring(endIndex,
					    endIndex + 2).matches("[0-9][.]") == false)
				    && (thisLine_Orig.substring(endIndex,
					    endIndex + 2).matches("[0-9][ ]") == false)
				    && (thisLine_Orig.substring(endIndex,
					    endIndex + 2).matches("[.][0-9]") == false)) {
				String firstHalfLine = thisLine_Orig.substring(
					0, beginIndex);
				String secondHalfLine = thisLine_Orig
					.substring(endIndex);

				/*
				 * Looks up all text of the reference
				 */
				int totalPeriodNum = 0;
				int periodNum_thisLine = 0;
				String curRef = "";
				int nextIndex = j;
				String line = secondHalfLine;
				while (nextIndex < linesThisPage.size() - 1) {
				    if ((k == tc.getPageId_thisTable())
					    && nextIndex <= tc.getBodyEndLine()
					    && nextIndex >= tc
						    .getBodyStartLine()) {
					break;
				    }
				    /*
				     * Gets the number of periods in the line.
				     * If the total period number < 2, get the
				     * whole line, otherwise, cut and break
				     */
				    for (int qq = 0; qq < line.length(); qq++) {
					if (line.substring(qq, qq + 1)
						.contentEquals(".") == true)
					    periodNum_thisLine++;
				    }
				    totalPeriodNum = totalPeriodNum
					    + periodNum_thisLine;
				    if (totalPeriodNum < 2) {
					curRef = curRef + " " + line;
					nextIndex++;
					line = linesThisPage.get(nextIndex)
						.getText();
					periodNum_thisLine = 0;
				    } else {
					if (totalPeriodNum == 2) {
					    if (periodNum_thisLine == 1) {
						String lines = line.substring(
							0,
							line.indexOf(".") + 1);
						curRef = curRef + " " + lines;
						break;
					    }
					    if (periodNum_thisLine == 2) {
						String lines = line
							.substring(
								0,
								line.lastIndexOf(".") + 1);
						curRef = curRef + " " + lines;
						break;
					    }
					}
					/*
					 * totalPeriodNum>2. In this case,
					 * periodNum_thisLine will >=2
					 */
					else {
					    int firstDot = line.indexOf(".");
					    int secondDot = line.indexOf(".",
						    firstDot);
					    String lines = line.substring(0,
						    secondDot + 1);
					    curRef = curRef + " " + lines;
					    break;
					}
				    }
				}

				/*
				 * Looks up the previous period "."
				 */
				totalPeriodNum = 0;
				periodNum_thisLine = 0;
				String preRef = "";
				int preIndex = j;
				line = firstHalfLine;
				while (preIndex > 0) {
				    if ((k == tc.getPageId_thisTable())
					    && preIndex <= tc.getBodyEndLine()
					    && preIndex >= tc
						    .getBodyStartLine()) {
					break;
				    }
				    /*
				     * Gets the period number in line, if total
				     * period number < 2, get the whole line,
				     * otherwise, cut and break
				     */
				    for (int qq = 0; qq < line.length(); qq++) {
					if (line.substring(qq, qq + 1)
						.contentEquals(".") == true)
					    periodNum_thisLine++;
				    }
				    totalPeriodNum = totalPeriodNum
					    + periodNum_thisLine;
				    if (totalPeriodNum < 2) {
					preRef = line + " " + preRef;
					preIndex--;
					line = linesThisPage.get(preIndex)
						.getText();
					periodNum_thisLine = 0;
				    } else {
					/*
					 * No matter this line has 1 or 2 ".",
					 * we get the part after the first "."
					 */
					if (totalPeriodNum == 2) {
					    String lines = line.substring(
						    line.indexOf(".") + 1,
						    line.length());
					    preRef = lines + " " + preRef;
					    break;
					}
					/*
					 * totalPeriodNum>2. In this case,
					 * periodNum_thisLine will >=2
					 */
					else {
					    int lastDot = line.lastIndexOf(".");
					    int secondLastDot = line.substring(
						    lastDot).lastIndexOf(".");
					    String lines = line.substring(
						    secondLastDot + 1,
						    line.length());
					    preRef = lines + " " + preRef;
					    break;
					}
				    }
				}

				String thisReference = preRef
					+ thisTableCaptionBeginning + curRef;
				// thisReference =
				// tc.replaceAllSpecialChracters(thisReference);

				String referenceText = tc.getRefText()
					+ "\n In PAGE " + (k + 1) + ": ..."
					+ thisReference + "...";
				tc.setRefText(referenceText);
			    }
			}
		    }
		}// end for
	    }
	} else {
	    tc.setRefText("");
	}

    }

}
