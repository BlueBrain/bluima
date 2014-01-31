/**
 * 
 */
package edu.psu.seersuite.extractors.tableextractor.model;

import java.io.File;
import java.util.ArrayList;

/**
 * This class defines the table-related parameters and methods.
 * 
 * @author  Ying, Shuyi
 * 
 */
public class Table
{
    /**
     * The PDF file where the table is located in
     */
	private String m_sourcePdfFile; 

	/**
	 * The id of the document page where the table is located in
	 */
    private int m_pageNumber;

    /**
     * The table id in the document
     */
    private int m_order;

    /**
     * The number of rows in this table 
     */
    private int m_rowNumber;

    /**
     * The number of columns in this table
     */
    private int m_columnNumber;

    /**
     * The width of the table
     */
    private float m_width;

    /**
     * THe height of the table
     */
    private float m_height;

    /**
     * The caption of the table
     */
    private String m_caption;

    /**
     * The heading rows of the table
     */
    private ArrayList<String> m_xheading;

    /**
     * THe body/data rows of the table
     */
    private ArrayList<String> m_xbody;
    
    /**
     * THe footnote of the table
     */
    private String m_footNote;

    /**
     * The reference text of the table in the document
     */
    private String m_refTextList;

    /**
     * The coordinate information of each table column
     */
    private ArrayList<Float> m_columnCoordinates;

    
    public Table(){
    	this.m_xheading = new ArrayList<String>();
    	this.m_xbody = new ArrayList<String>();
    	
    }
    /**
     *Gets the body cells of a table
     * @return the body content of a table
     */
    public ArrayList<String> getTableBody()
    {
	return m_xbody;
    }

    /**
     * Gets the caption of a table
     * @return the caption
     */
    public String getCaption()
    {
	return m_caption;
    }

    /**
     * Gets the coordinate information of the table columns
     * @return the Coordinates of table columns
     */
    public ArrayList<Float> getColumnCoordinates()
    {
	return m_columnCoordinates;
    }

    /**
     * Gets the number of columns in a table
     * @return the number of the columns in a table
     */
    public int getColumnNumber()
    {
	return m_columnNumber;
    }

    /**
     * Gets the footnote of a table
     * @return the footnote of a table
     */
    public String getFootNote()
    {
	return m_footNote;
    }

    /**
     * Gets the heading rows of a table 
     * @return the heading of a table
     */
    public ArrayList<String> getHeading()
    {
	return m_xheading;
    }

    /**
     * Gets the height of a table
     * @return the height of a table
     */
    public float getHeight()
    {
	return m_height;
    }

    /**
     * @return the order id of a table in the document
     */
    public int getOrder()
    {
	return m_order;
    }

    /**
     * Gets the number of the page where the table is located in
     * @return the page number of a table
     */
    public int getPageNumber()
    {
	return m_pageNumber;
    }

    /**
     * Gets the reference text of a table in the document
     * @return the reference text of a table 
     */
    public String getRefTextList()
    {
	return m_refTextList;
    }

    /**
     * Gets the number of rows in a table
     * @return the number of rows
     */
    public int getRowNumber()
    {
	return m_rowNumber;
    }

    /**
     * Gets the PDF file where the table is located in
     * @return the source PDF file
     */
    public String getSourcePdfFile()
    {
	return m_sourcePdfFile;
    }

    /**
     * Gets the width of a table
     * @return the width of a table
     */
    public float getWidth()
    {
	return m_width;
    }

    /**
     * Loads table-set from a file
     * 
     * @param f
     *         the input file
     */
    public void load(File f)
    {

    }

    /**
     * Saves table to a file
     * 
     * @param f
     *         the output file
     */
    public void save(File f)
    {

    }

    /**
     * Sets the table body cells into an array
     * @param body
     *            the table body content to set
     */
    public void setBody(String body)
    {
    	String xbody = body;
    	String [] row = xbody.split("\n");
    	for(int i=0; i<row.length; i++){
    		this.m_xbody.add(row[i]);
    	}
    }

    /**
     * Sets the table caption
     * @param caption
     *            the caption to set
     */
    public void setCaption(String caption)
    {
	m_caption = caption;
    }

    /**
     * Sets the coordinates of table columns
     * @param columnCoordinates
     *            the columnCoordinates to set
     */
    public void setColumnCoordinates(ArrayList<Float> columnCoordinates)
    {
	m_columnCoordinates = columnCoordinates;
    }

    /**
     * Sets the number of table columns 
     * @param columnNumber
     *            the columnNumber to set
     */
    public void setColumnNumber(int columnNumber)
    {
	m_columnNumber = columnNumber;
    }

    /**
     * Sets the table footnote
     * @param footNote
     *            the footNode to set
     */
    public void setFootNote(String footNote)
    {
	m_footNote = footNote;
    }

    /**
     * Sets the table heading rows
     * @param heading
     *            the heading to set
     */
    public void setHeading(String heading)
    {
	    
    	String m_heading = heading;
	    
    	String [] preCol = m_heading.split("\n");
    	String [][] cells = new String[preCol.length][this.m_columnNumber];
    	
    	
    	for(int i=0; i<preCol.length; i++)
    		cells[i] = preCol[i].split(";", this.m_columnNumber);
    	
    	for(int i=0; i<cells[0].length; i++)
    	{
    		String xhead = "";
    		for(int j=0; j<preCol.length; j++){
    			//if(cells[j][i]!=null)
    			xhead += (cells[j][i]+ " ");
    		}
    		this.m_xheading.add(xhead);
    	}
    	
    }

    /**
     * Sets the table height
     * @param height
     *            the height to set
     */
    public void setHeight(float height)
    {
	m_height = height;
    }

    /**
     * Sets the order ID of a table
     * @param order
     *            the order to set
     */
    public void setOrder(int order)
    {
	m_order = order;
    }

    /**
     * Sets the page number where the table is located in
     * @param pageNumber
     *            the pageNumber to set
     */
    public void setPageNumber(int pageNumber)
    {
	m_pageNumber = pageNumber;
    }

    /**
     * Sets the reference text of a table in a document
     * @param refTextList
     *            the refTextList to set
     */
    public void setRefTextList(String refTextList)
    {
	m_refTextList = refTextList;
    }

    /**
     * Sets the number of table rows 
     * @param rowNumber
     *            the rowNumber to set
     */
    public void setRowNumber(int rowNumber)
    {
	m_rowNumber = rowNumber;
    }

    /**
     * Sets the source PDF file
     * @param sourcePdfFile
     *            the sourcePdfFile to set
     */
    public void setSourcePdfFile(String sourcePdfFile)
    {
	m_sourcePdfFile = sourcePdfFile;
    }

    /**
     * Sets the width of a table
     * @param width
     *            the width to set
     */
    public void setWidth(float width)
    {
	m_width = width;
    }
    
    /**
     * Sets the metadata of a valid table
     * @param tc
     *      TableCandidate
     * */
    public void setValidTable(TableCandidate tc)
    {
    	/**
    	 * TODO [improve]: Table/TableCandidate not match
    	 * */
    	this.setHeight(tc.getHeight());
    	this.setWidth(tc.getWidth());
    	this.setCaption(tc.getCaption());
    	this.setRowNumber(tc.getRows().size());
    	this.setColumnNumber(tc.getColumnNum_thisTable());
    	this.setRefTextList(tc.getRefText());
    	this.setFootNote(tc.getFootnoteText());
    	this.setHeading(tc.getColumnHeadings());
    	this.setBody(tc.getTableContent());
    	
    }
}
