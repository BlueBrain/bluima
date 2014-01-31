/**
 * 
 */
package edu.psu.seersuite.extractors.tableextractor.model;

import java.util.ArrayList;
import java.util.List;

//XML library jdom.org

/*
 import org.jdom2.Document;
 import org.jdom2.Element;
 import org.jdom2.JDOMException;
 import org.jdom2.input.SAXBuilder;
 import org.jdom2.output.DOMOutputter;
 import org.jdom2.output.XMLOutputter;
 */

/**
 * This class defines the internal data structure for a table candidate.
 * 
 * @author Ying, Shuyi
 * 
 */
public class TableCandidate {
    private boolean m_valid = true; // whether this table candidate is a valid
				    // table or not
    private boolean m_WideTable = false; // whether this table is a wide table
					 // or not
    private boolean m_topCaptionLocation = true; // whether the caption of the
						 // table is on the top of the
						 // table body or not

    private String m_caption; // the caption of this table candidate
    private String m_keyword; // the table keyword of this table candidate
    private String m_metadata_HighLevel = ""; // the metadata of this table in
					      // the high-level (without the
					      // table structure decomposition
					      // result)
    private String m_metadata_StructureLevel = ""; // the metadata of this table
						   // in the structure-level
						   // (with the table structure
						   // decomposition result)
    private String m_rowHeadings = ""; // the heading rows of a table candidate
    private String m_columnHeadings = ""; // the heading columns of a table
					  // candidate

    private String m_classficationData; // the classification data for further
					// table understanding
    private String m_referenceText = ""; // the reference text in the document
					 // of a table candidate
    private String m_footnoteText = ""; // the table footnote
    private String m_tablecontent = ""; // the table content

    private int m_pageId = 0; // the page Id where the table is located in
    private int m_captionStartLine; // the line ID of the first table caption
				    // line in the document page
    private int m_captionEndLine; // the line ID of the last table caption line
				  // in the document page
    private int m_bodyStartLine; // the line ID of the first table body line in
				 // the document page
    private int m_bodyEndLine; // the line ID of the last table body line in the
			       // document page
    private int m_footnoteBeginRow = -1; // the line ID of the first table
					 // footnote line in the document page,
					 // if appliable
    private int m_headingLineNumber = 0; // the number of combined lines in the
					 // heading rows
    private int m_maxColumnNumber = 0; // the maximum number of table columns
    private int m_columnNum = 0; // the real number of table columns
    private int m_rowNum = 0; // the number of table rows

    private float m_captionX; // the left-end X axis of the table caption
    private float m_captonEndX; // the right-end X axis of the table caption
    private float m_bodyX; // the left-end X axis of the table body
    private float m_bodyEndX; // the right-end X axis of the table body
    private float m_minGapBtwColumns = 1000.0f; // the minimum gaps between
						// every two neighbor table
						// columns
    private float m_height = 0.0f; // the table height
    private float m_width = 0.0f; // the table width

    public String[][] m_cells; // the table cells
    public boolean[][] m_crossCells; // the cross row/column table cells
				     // (nesting cells)
    private float[] m_leftX_tableColumns; // the left-end X axis of each table
					  // column
    private float[] m_rightX_tableColumns; // the right-end X axis of each table
					   // column

    private ArrayList<TableRow> m_rows = new ArrayList<TableRow>();

    // private Document xml_doc;

    /**
     * Gets the left-end X-axis of each table column
     * 
     * @return the left-end X-axis of each table column
     */
    public float[] getLeftX_tableColumns() {
	return m_leftX_tableColumns;
    }

    /**
     * Gets the right-end X-axis of each table column
     * 
     * @return the right-end X-axis of each table column
     */
    public float[] getRightX_tableColumns() {
	return m_rightX_tableColumns;
    }

    /**
     * Gets the table cells
     * 
     * @return all the cells of a table
     */
    public String[][] getCells() {
	return m_cells;
    }

    /**
     * Gets all the nesting cells
     * 
     * @return all the nesting cells
     */
    public boolean[][] getCrossCells() {
	return m_crossCells;
    }

    /**
     * Adds a newly detected row into a table
     * 
     * @param row
     *            the row to add
     */
    public void addRow(TableRow row) {
	m_rows.add(row);
    }

    /**
     * Gets the number of columns of this table
     * 
     * @return the number of columns of this table
     */
    public int getColumnNum_thisTable() {
	return m_columnNum;
    }

    /**
     * Gets the page number where the table is located
     * 
     * @return m_pageId the page number
     */
    public int getPageId_thisTable() {
	return m_pageId;
    }

    /**
     * Gets the table height
     * 
     * @return m_height the table height
     */
    public float getHeight() {
	return this.m_height;
    }

    /**
     * Gets the table width
     * 
     * @return m_width the table width
     */
    public float getWidth() {
	return this.m_width;
    }

    /**
     * Gets the total number of table caption lines
     * 
     * @return the number of lines in table caption
     */
    public int captionLineCount() {
	return m_captionEndLine - m_captionStartLine + 1;
    }

    /**
     * Gets the width of the table caption area
     * 
     * @return the caption width
     */
    public float captionWidth() {
	return m_captonEndX - m_captionX;
    }

    /**
     * Gets the number of cells in a table
     * 
     * @return the number of cells
     */
    public int cellCount() {
	return m_bodyEndLine - m_bodyStartLine + 1;
    }

    /**
     * Gets the last line of the table body
     * 
     * @return the last line of the table body
     */
    public int getBodyEndLine() {
	return m_bodyEndLine;
    }

    /**
     * Gets the right-end X-axis of a table body
     * 
     * @return the right-end X-axis of a table body
     */
    public float getBodyEndX() {
	return m_bodyEndX;
    }

    /**
     * Gets the first line of a table body
     * 
     * @return the first line of a table body
     */
    public int getBodyStartLine() {
	return m_bodyStartLine;
    }

    /**
     * Gets the left-end X-axis of a table body
     * 
     * @return left-end X-axis of a table body
     */
    public float getBodyX() {
	return m_bodyX;
    }

    /**
     * Gets the minimum gap between table columns
     * 
     * @return the minimum gap between table columns
     */
    public float getMinGapBtwColumns() {
	return m_minGapBtwColumns;
    }

    /**
     * Gets the table caption
     * 
     * @return the table caption
     */
    public String getCaption() {
	return m_caption;
    }

    /**
     * Gets the table reference text
     * 
     * @return The table reference text
     */
    public String getRefText() {
	return m_referenceText;
    }

    /**
     * Gets the table footnote
     * 
     * @return the table footnote
     */
    public String getFootnoteText() {
	return m_footnoteText;
    }

    /**
     * Gets the table metadata in high level (without the table structure
     * decomposition)
     * 
     * @return the high-level table metadata
     */
    public String getMetadata_HighLevel() {
	return m_metadata_HighLevel;
    }

    /**
     * Gets the table metadata with table structure decomposition information
     * 
     * @return the table metadata with table structure decomposition information
     */
    public String getMetadata_StructureLevel() {
	return m_metadata_StructureLevel;
    }

    /**
     * Gets the heading rows of a table
     * 
     * @return the row headings
     */
    public String getRowHeadings() {
	return m_rowHeadings;
    }

    /**
     * Gets the heading columns of a table
     * 
     * @return the column headings
     */
    public String getColumnHeadings() {
	return m_columnHeadings;
    }

    /**
     * Gets the table content/data cells
     * 
     * @return the table content
     */
    public String getTableContent() {
	return this.m_tablecontent;
    }

    /**
     * Gets the table classification data
     * 
     * @return the table classification data
     */
    public String getClassificationData() {
	return m_classficationData;
    }

    /**
     * Gets the ending line of the table caption
     * 
     * @return the the ending line of the table caption --- captionEndLine
     */
    public int getCaptionEndLine() {
	return m_captionEndLine;
    }

    /**
     * Gets the starting line of the table caption
     * 
     * @return the starting line of a table caption -- captionStartLine
     */
    public int getCaptionStartLine() {
	return m_captionStartLine;
    }

    /**
     * Gets the starting X-axis of the table caption
     * 
     * @return the starting X-axis of the table caption -- captionX
     */
    public float getCaptionX() {
	return m_captionX;
    }

    /**
     * Gets the ending X-axis of the table caption
     * 
     * @return the ending X-axis of the table caption -- captonEndX
     */
    public float getCaptonEndX() {
	return m_captonEndX;
    }

    /**
     * Gets the beginning line of the table footnote
     * 
     * @return the beginning line of the table footnote -- footnoteBeginRow
     */
    public int getFootnoteBeginRow() {
	return m_footnoteBeginRow;
    }

    /**
     * Get the number of table heading lines
     * 
     * @return the number of table heading lines -- headingLineNumber
     */
    public int getHeadingLineNumber() {
	return m_headingLineNumber;
    }

    /**
     * Gets the keyword of a table caption
     * 
     * @return the keyword of a table caption
     */
    public String getKeyword() {
	return m_keyword;
    }

    /**
     * Gets the maximum number of columns
     * 
     * @return the maximum number of columns -- maxColumnNumber
     */
    public int getMaxColumnNumber() {
	return m_maxColumnNumber;
    }

    /**
     * Gets the table rows
     * 
     * @return the table rows
     */
    public ArrayList<TableRow> getRows() {
	return m_rows;
    }

    /**
     * Judges whether the table candidate is a valid table
     * 
     * @return the validity of the table
     */
    public boolean isValid() {
	return m_valid;
    }

    /**
     * Judges whether it is a wide table or not
     * 
     * @return whether it is a wide table or not
     */
    public boolean isWideTable() {
	return m_WideTable;
    }

    /**
     * Judges the table caption location -- top caption or below caption
     * 
     * @return the table caption location
     */
    public boolean isTopCaption() {
	return m_topCaptionLocation;
    }

    /**
     * Sets the high-level table metadata
     * 
     * @param i
     *            the total number of pages of the document
     */
    public void setHighLevelMeta(int i) {
	String meta = "<Table>\n";
	meta = meta + "<pageNumInDoc>" + (i + 1) + "</pageNumInDoc>\n";
	m_caption = replaceAllSpecialChracters(m_caption);
	meta = meta + "<TableCaption>" + m_caption + "</TableCaption>\n";
	String location = "";
	if (m_topCaptionLocation)
	    location = "top";
	else
	    location = "below";
	location = replaceAllSpecialChracters(location);
	meta = meta + "<CaptionLocation>" + location + "</CaptionLocation>\n";
	m_metadata_HighLevel = replaceAllSpecialChracters(m_metadata_HighLevel);
	meta = meta + "<dataHighLevel>" + m_metadata_HighLevel
		+ "</dataHighLevel>\n";
	this.setMetadataHighLevel(meta);
    }

    /*
     * public void setHighLevelMetaX(int i){ Document xmldoc = this.xml_doc;
     * Element root = xmldoc.getRootElement();
     * 
     * Element subroot = new Element("Table"); subroot.addContent(new
     * Element("pageNumInDoc").addContent(Integer.toString(i+1)));
     * subroot.addContent(new Element("TableCaption").addContent(m_caption)); if
     * (m_topCaptionLocation) subroot.addContent(new
     * Element("CaptionLocation").addContent("top")); else
     * subroot.addContent(new Element("CaptionLocation").addContent("below"));
     * subroot.addContent(new
     * Element("dataHighLevel").addContent(m_metadata_HighLevel));
     * 
     * root.addContent(subroot);
     * 
     * }
     */
    /*
     * public void setMetaStructureLevelX(int YNum, int cc, ArrayList<TextPiece>
     * wordsOfAPage, DocInfo m_docInfo){ String rowHeading =
     * this.getRowHeadings(); int heads = this.getHeadingLineNumber(); int
     * footnoteLineIndex = this.getFootnoteBeginRow(); int MAXcolumnNum =
     * this.getMaxColumnNumber(); float[] leftX_tableColumns =
     * this.getLeftX_tableColumns(); float[] rightX_tableColumns =
     * this.getRightX_tableColumns(); String[][] cells = this.getCells(); String
     * detailedTableMeta = "";
     * 
     * Document xmldoc = this.xml_doc; Element root = xmldoc.getRootElement();
     * Element subroot = root.getChild("Table");
     * 
     * }
     */

    /**
     * Sets the table metadata with the detailed structure decomposition
     * information
     * 
     * @param YNum
     *            the total number of table lines including footnote
     * @param cc
     *            the total number of table columns
     * @param wordsOfAPage
     *            the list of words in a document page
     * @param m_docInfo
     *            an object of DocInfo
     */
    public void setMetadataStructureLevel(int YNum, int cc,
	    List<TextPiece> wordsOfAPage, DocInfo m_docInfo) {
	String rowHeading = this.getRowHeadings();
	int heads = this.getHeadingLineNumber();
	int footnoteLineIndex = this.getFootnoteBeginRow();
	int MAXcolumnNum = this.getMaxColumnNumber();
	float[] leftX_tableColumns = this.getLeftX_tableColumns();
	float[] rightX_tableColumns = this.getRightX_tableColumns();
	String[][] cells = this.getCells();
	String detailedTableMeta = "";
	detailedTableMeta = "<TableColumnHeading>\n" + m_columnHeadings
		+ "</TableColumnHeading>" + "\n";

	detailedTableMeta = detailedTableMeta + "<TableContent>\n";
	for (int tt = (heads + 1); tt < footnoteLineIndex; tt++) {
	    int cellNumThisRow = cells[tt].length;
	    String contentThisRow = "";
	    for (int i = 0; i < cellNumThisRow; i++) {
		contentThisRow = contentThisRow + cells[tt][i] + ";";
	    }
	    contentThisRow = replaceAllSpecialChracters(contentThisRow);
	    this.m_tablecontent += contentThisRow + "\n"; // for standard XML
							  // only
	    detailedTableMeta = detailedTableMeta + contentThisRow + "\n";
	}
	detailedTableMeta = detailedTableMeta + "</TableContent>" + "\n";
	rowHeading = replaceAllSpecialChracters(rowHeading);
	detailedTableMeta = detailedTableMeta + "<TableRowHeading>"
		+ m_rowHeadings + "</TableRowHeading>" + "\n";

	detailedTableMeta = detailedTableMeta + "<TableFootnote>";
	int footnote = footnoteLineIndex;
	while (footnote < YNum) {
	    int cellNumThisRow = this.getRows().get(footnote).getCells().size();
	    String contentThisRow = "";
	    for (int i = 0; i < cellNumThisRow; i++) {
		contentThisRow = contentThisRow
			+ this.getRows().get(footnote).getCells().get(i)
				.getText() + "\t";
	    }
	    contentThisRow = replaceAllSpecialChracters(contentThisRow);
	    m_footnoteText = m_footnoteText + contentThisRow + "\n";
	    footnote++;
	}
	detailedTableMeta = detailedTableMeta + m_footnoteText
		+ "... </TableFootnote>" + "\n";

	if (MAXcolumnNum < cc)
	    MAXcolumnNum = cc;
	detailedTableMeta = detailedTableMeta + "<ColumnNum>" + cc
		+ "</ColumnNum>" + "\n";

	detailedTableMeta = detailedTableMeta + "<RowNum>" + footnoteLineIndex
		+ "</RowNum>" + "\n";

	String columnCoordinates = "";
	for (int qq = 0; qq < cc; qq++) {
	    columnCoordinates = columnCoordinates + "("
		    + leftX_tableColumns[qq] + "," + rightX_tableColumns[qq]
		    + "); ";
	}
	columnCoordinates = replaceAllSpecialChracters(columnCoordinates);
	detailedTableMeta = detailedTableMeta + "<ColumnCoordinates>"
		+ columnCoordinates + "</ColumnCoordinates>" + "\n";

	this.m_height = wordsOfAPage.get(this.getBodyEndLine()).getEndY()
		- wordsOfAPage.get(this.getBodyStartLine()).getY();
	detailedTableMeta = detailedTableMeta + "<TableHeight>" + this.m_height
		+ "</TableHeight>" + "\n";

	this.m_width = rightX_tableColumns[cc - 1] - leftX_tableColumns[0];
	detailedTableMeta = detailedTableMeta + "<TableWidth>" + this.m_width
		+ "</TableWidth>" + "\n";
	if (m_WideTable == false) { // fix the wideTableOrNot based on the width
	    if (rightX_tableColumns[cc - 1] > (m_docInfo.getMiddleX())
		    && leftX_tableColumns[0] < m_docInfo.getMiddleX())
		m_WideTable = true;
	}
	detailedTableMeta = detailedTableMeta + "<isWideTable>" + m_WideTable
		+ "</isWideTable>\n";
	m_referenceText = replaceAllSpecialChracters(m_referenceText);
	detailedTableMeta = detailedTableMeta + "<TableReferenceText>"
		+ m_referenceText + "</TableReferenceText>" + "\n";
	detailedTableMeta = detailedTableMeta + "</Table>" + "\n";

	setMetadataStructureLevel(detailedTableMeta);
	int tableNum = m_docInfo.getTableNum();
	m_docInfo.setTableNum(tableNum + 1);
    }

    /**
     * Sets an empty metadata file
     * 
     * @param YNum
     *            the total number of table lines including footnote
     * @param cc
     *            the total number of table columns
     * @param wordsOfAPage
     *            the list of words in a document page
     * @param m_docInfo
     *            an object of DocInfo
     */
    public void setEmptyMetadataStructureLevel(int YNum, int cc,
	    List<TextPiece> wordsOfAPage, DocInfo m_docInfo) {
	
	String meta = "";
	meta = "<TableColumnHeading>\n" + "</TableColumnHeading>"
		+ "\n";

	meta = meta + "<TableContent>\n";
	meta = meta + "</TableContent>" + "\n";
	meta = meta + "<TableRowHeading>"
		+ "</TableRowHeading>" + "\n";

	meta = meta + "<TableFootnote>";
	meta = meta + "... </TableFootnote>" + "\n";
	meta = meta + "<ColumnNum>" + "</ColumnNum>"
		+ "\n";
	meta = meta + "<RowNum>" + "</RowNum>" + "\n";
	meta = meta + "<ColumnCoordinates>"
		+ "</ColumnCoordinates>" + "\n";

	meta = meta + "<TableHeight>"
		+ "</TableHeight>" + "\n";

	meta = meta + "<TableWidth>"
		+ "</TableWidth>" + "\n";
	meta = meta + "<isWideTable>" + m_WideTable
		+ "</isWideTable>\n";
	m_referenceText = replaceAllSpecialChracters(m_referenceText);
	meta = meta + "<TableReferenceText>"
		+ m_referenceText + "</TableReferenceText>" + "\n";
	meta = meta + "</Table>" + "\n";

	setMetadataStructureLevel(meta);
	int tableNum = m_docInfo.getTableNum();
    }

    /**
     * Prints this table candidate to a standard output
     */
    public void print() {
	// System.out.printf("-------------------------\n");
	// System.out.printf("candidate info:\n");
	// System.out.printf("valid=%s\n", this.isValid());
	// System.out.printf("caption: %s\n", this.getCaption());
	/*
	 * System.out.printf( "caption\t[line#%d, line#%d]\t[x%f, x%f]\n",
	 * this.getCaptionStartLine(), this.getCaptionEndLine(),
	 * this.getCaptionX(), this.getCaptonEndX()); System.out.printf(
	 * "body\t[line#%d, line#%d]\t[x%f, x%f]\n", this.getBodyStartLine(),
	 * this.getBodyEndLine(), this.getBodyX(), this.getBodyEndX());
	 * System.out.printf("footnote from row#%d\n", m_footnoteBeginRow);
	 * System.out.printf("max column #: %d\n", m_maxColumnNumber);
	 * System.out.printf("heading line #: %d\n", m_headingLineNumber);
	 * 
	 * System.out.printf("rows:\n"); for (TableRow row : this.getRows()) {
	 * System.out.printf( "[Y=%f, %d cells] ", row.getY(),
	 * row.getCells().size()); System.out.printf("\n"); }
	 */
	// System.out.println(m_metadata_HighLevel + m_metadata_StructureLevel);
    }

    /**
     * Replaces all special characters in a given string
     * 
     * @param toBeReplaced
     *            The string to be replaced
     * @return the string after the replace
     */
    public static String replaceAllSpecialChracters(String toBeReplaced) {
	toBeReplaced = toBeReplaced.replaceAll("&", "&amp;");
	toBeReplaced = toBeReplaced.replaceAll("<", "&lt;");
	toBeReplaced = toBeReplaced.replaceAll(">", "&gt;");
	toBeReplaced = toBeReplaced.replaceAll("\"", "&quot;");
	toBeReplaced = toBeReplaced.replaceAll("'", "&apos;");
	// toBeReplaced = toBeReplaced.replace("^", "&circ");
	// toBeReplaced = toBeReplaced.replaceAll("0xb", "");
	int len = toBeReplaced.length();
	char[] temp = toBeReplaced.toCharArray();
	for (int i = 0; i < len; i++) {
	    char xCode = temp[i];
	    if ((xCode > '\u0000' && xCode <= '\u0020') || (xCode == '\u0026')
		    || (xCode == '\u0022') || (xCode == '\u02C6')
		    || (xCode == '\u201C') || (xCode == '\u201D')
		    || (xCode == '\u201E')) {
		String sub1 = toBeReplaced.substring(0, i);
		String sub2 = toBeReplaced.substring(i + 1, len);

		String replacement = "";
		if ((xCode > '\u0000' && xCode <= '\u0020'))
		    replacement = " ";
		if (xCode == '\u0026')
		    replacement = " ";
		if (xCode == '\u0022')
		    replacement = " ";
		if (xCode == '\u02C6')
		    replacement = " ";
		if (xCode == '\u201C')
		    replacement = " ";
		if (xCode == '\u201D')
		    replacement = " ";
		if (xCode == '\u201E')
		    replacement = " ";

		toBeReplaced = sub1 + replacement + sub2;
	    }
	    len = toBeReplaced.length();
	    temp = toBeReplaced.toCharArray();
	}

	return (toBeReplaced);
    }

    /**
     * Sets the table cells
     * 
     * @param cells
     *            the table cells to be set
     */
    public void setCells(String[][] cells) {
	m_cells = cells;
    }

    /**
     * Sets the cross-column cells
     * 
     * @param crossCells
     *            the cross-column cells to be set
     */
    public void setCrossCells(boolean[][] crossCells) {
	m_crossCells = crossCells;
    }

    /**
     * Sets the left-end X-axis of table columns
     * 
     * @param leftX_tableColumns
     *            the left-end X-axis of table columns to be set
     */
    public void setLeftX_tableColumns(float[] leftX_tableColumns) {
	m_leftX_tableColumns = leftX_tableColumns;
    }

    /**
     * Sets the right-end X-axis of table columns
     * 
     * @param rightX_tableColumns
     *            the right-end X-axis of table columns to be set
     */
    public void setRightX_tableColumns(float[] rightX_tableColumns) {
	m_rightX_tableColumns = rightX_tableColumns;
    }

    /**
     * Sets the last line of the table body
     * 
     * @param bodyEndLine
     *            the bodyEndLine to set
     */
    public void setBodyEndLine(int bodyEndLine) {
	m_bodyEndLine = bodyEndLine;
    }

    /**
     * Sets the number of table columns
     * 
     * @param columnNum
     *            the number of columns
     */
    public void setColumnNum(int columnNum) {
	m_columnNum = columnNum;
    }

    /**
     * Sets the right-end X-axis of the table body
     * 
     * @param bodyEndX
     *            the right-end X-axis of the table body to be set
     */
    public void setBodyEndX(float bodyEndX) {
	m_bodyEndX = bodyEndX;
    }

    /**
     * Sets the starting line of the table body
     * 
     * @param bodyStartLine
     *            the bodyStartLine to set
     */
    public void setBodyStartLine(int bodyStartLine) {
	m_bodyStartLine = bodyStartLine;
    }

    /**
     * Sets the left-end X-axis of the table body
     * 
     * @param bodyX
     *            the bodyX to set
     */
    public void setBodyX(float bodyX) {
	m_bodyX = bodyX;
    }

    /**
     * Sets the minimum gap between table columns
     * 
     * @param minGapBtwColumns
     *            the minimum gap between table columns to be set
     */
    public void setMinGapBtwColumns(float minGapBtwColumns) {
	m_minGapBtwColumns = minGapBtwColumns;
    }

    /**
     * Sets the table caption
     * 
     * @param caption
     *            the table caption to set
     */
    public void setCaption(String caption) {
	m_caption = caption;
    }

    /**
     * Sets the table reference text in the document
     * 
     * @param referenceText
     *            the reference text of a table
     */
    public void setRefText(String referenceText) {
	m_referenceText = referenceText;
    }

    /**
     * Sets the table footnote
     * 
     * @param footnoteText
     *            the table footnote to be set
     */
    public void setFootnoteText(String footnoteText) {
	m_footnoteText = footnoteText;
    }

    /**
     * Sets the high-level table metadata
     * 
     * @param metadata_HighLevel
     *            the high-level table metadata to be set
     */
    public void setMetadataHighLevel(String metadata_HighLevel) {
	m_metadata_HighLevel = metadata_HighLevel;
    }

    /**
     * Sets the table metadata with the detailed structure decomposition
     * information
     * 
     * @param metadata_StructureLevel
     *            the table metadata with the structure decomposition
     *            information
     */
    public void setMetadataStructureLevel(String metadata_StructureLevel) {
	m_metadata_StructureLevel = metadata_StructureLevel;
    }

    /**
     * Sets the table row headings
     * 
     * @param headingRows
     *            the row headings to be set
     */
    public void setHeadingRows(String headingRows) {
	m_rowHeadings = headingRows;
    }

    /**
     * Sets the column headings
     * 
     * @param columnHeadings
     *            the column headings to be set
     */
    public void setColumnHeadings(String columnHeadings) {
	m_columnHeadings = columnHeadings;
    }

    /**
     * Sets the data for table classification
     * 
     * @param classficationData
     *            the table classification data
     */
    public void setClassificationData(String classficationData) {
	m_classficationData = classficationData;
    }

    /**
     * Sets the page number where the table is located
     * 
     * @param pageId
     *            the page number where the table is located
     */
    public void setPageId_thisTable(int pageId) {
	m_pageId = pageId;
    }

    /**
     * Sets the last line of the table caption
     * 
     * @param captionEndLine
     *            the captionEndLine to set
     */
    public void setCaptionEndLine(int captionEndLine) {
	m_captionEndLine = captionEndLine;
    }

    /**
     * Sets the first line of the table caption
     * 
     * @param captionStartLine
     *            the captionStartLine to set
     */
    public void setCaptionStartLine(int captionStartLine) {
	m_captionStartLine = captionStartLine;
    }

    /**
     * Sets the left-end X-axis of the table caption
     * 
     * @param captionX
     *            the captionX to set
     */
    public void setCaptionX(float captionX) {
	m_captionX = captionX;
    }

    /**
     * Sets the right-end X-axis of the table caption
     * 
     * @param captonEndX
     *            the captonEndX to set
     */
    public void setCaptonEndX(float captonEndX) {
	m_captonEndX = captonEndX;
    }

    /**
     * Sets the beginning line of the table footnote
     * 
     * @param footnoteBeginRow
     *            the footnoteBeginRow to set
     */
    public void setFootnoteBeginRow(int footnoteBeginRow) {
	m_footnoteBeginRow = footnoteBeginRow;
    }

    /**
     * Sets the total number of the column heading lines
     * 
     * @param headingLineNumber
     *            the headingLineNumber to set
     */
    public void setHeadingLineNumber(int headingLineNumber) {
	m_headingLineNumber = headingLineNumber;
    }

    /**
     * Sets the table caption keywords
     * 
     * @param keyword
     *            the keyword to set
     */
    public void setKeyword(String keyword) {
	m_keyword = keyword;
    }

    /**
     * @param xml
     *            the Document to set
     * 
     *            public void setXMLDoc(Document xml){ this.xml_doc = xml; }
     */

    /**
     * Sets the maximum number of table columns
     * 
     * @param maxColumnNumber
     *            the maxColumnNumber to set
     */
    public void setMaxColumnNumber(int maxColumnNumber) {
	m_maxColumnNumber = maxColumnNumber;
    }

    /**
     * Sets the validility of the table
     * 
     * @param valid
     *            the valid to set
     */
    public void setValid(boolean valid) {
	m_valid = valid;
    }

    /**
     * Sets the side (wide or not) of a table
     * 
     * @param wideTable
     *            whether the table is wide or not
     */
    public void setWideTable(boolean wideTable) {
	m_WideTable = wideTable;
    }

    /**
     * Sets the location of the table caption -- top or below
     * 
     * @param topCaptionLocation
     *            the table caption location to be set
     */
    public void setTopCaption(boolean topCaptionLocation) {
	m_topCaptionLocation = topCaptionLocation;
    }
}
