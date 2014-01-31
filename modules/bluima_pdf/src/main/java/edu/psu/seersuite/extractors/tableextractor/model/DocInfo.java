/**
 * This package defines all the models
 */
package edu.psu.seersuite.extractors.tableextractor.model;

import org.jdom2.Document;

/**
 * This class defines the document-related parameters and methods. 
 * 
 * @author Ying, Shuyi
 * 
 */
public class DocInfo
{
    /**
     * The average width of the characters in a document
     */
	private float m_averageCharWidth = -1;   
	
	/**
	 * The average width of the lines in a document
	 */
    private float m_averageLineWidth = -1;          
	
	/*
	 * the text scale of the document body contents
	 */
    private float m_bodyTextScale = -1;             
    
    /**
	 * the font size of the document title
	 */
    private float m_titleFont = -1;                
    
    /**
     * the average gap between two consecutive lines in a document
     */
    private float m_averageLineGap = 10;           
    
    /**
     * the average gap between the left-ends of two text pieces in a document
     */
    private float m_averageXGap = 10;               
    
    /**
     * the minimum X axis in this document
     */
    private float m_minX = 1000;                    
    
    /**
     * the maximum X axis in this document
     */
    private float m_maxX = 0;     
    
    /**
     * The minimum Y axis in this document
     */
    private float m_minY = 1000;  
    
    /**
     * the maximum Y axis in this document
     */
    private float m_maxY = 0;             
    
    /**
     * the X axis of the middle point of this document
     */
    private float m_middleX = 0;                    
    
    /**
     * the left-side X axis of the middle area of this document
     */
    private float m_middleArea_X = 0;               
    
    /**
     * the right-side X axis of the middle area of this document
     */
    private float m_middleArea_EndX = 0;            
    
    /**
     * the Html codes from &#32 to &#127 
     */
    private String[] m_Html2Char = new String[96]; 
    
    /**
     * The string to store the table metadata in this document
     */
    private String m_tableMetadata = "";
    
    /**
     * The string to store the document metadata
     */
    private String m_docMeta = "";
    
    /**
     * The possible error message of this document after processing
     */
    private String m_errorMsg= "";
   
    /**
     * The total number of pages in this document
     */
    private int m_pageNum = 0;
    
    /**
     * The total number of tables in this document
     */
    private int m_tableNum = 0;
    
    /**
     * The document metadata in XML format 
     */
    private Document m_xdocMeta; //for standard XML 
    
    	
    
    
    
    /**
     * Gets the table metadata in this document
     * 
     * @return the XML-based table metadata
     */
    public String getTableMetaInThisDoc() {
    	return m_tableMetadata;
    }
    
    /**
     * Gets the basic metadata of this document
     * 
     * @return the document metadata
     */
    public String getDocMeta() {
    	return m_docMeta;
    }
    
    /**
     * @return m_xdocMeta
     *     the XML Document
     * 
    public Document getXDocMeta(){
    	return m_xdocMeta;
    }
    */

    /**
     * Gets the error message of this document, if applicable
     * 
     * @return the error message
     */
    
    public String getErrorMsg() {
    	return m_errorMsg;
    }
    
    /**
     * Gets the total number of pages in this document
     * 
     * @return the total number of pages 
     */
    public int getPageNum() {
    	return m_pageNum;
    }
    
    /**
     * Gets the total number of tables in this document
     * 
     * @return the total number of tables
     */
    public int getTableNum() {
    	return m_tableNum;
    }
    
    
    /**
     * Gets the average width of characters in a document
     * 
     * @return the average Char width
     */
    public float getAverageCharWidth()
    {
	return m_averageCharWidth;
    }

    
    /**
     * Gets the average gaps between consecutive lines in a document
     * 
     * @return the average gaps between lines
     */
    public float getAverageLineGap()
    {
	return m_averageLineGap;
    }

    
    /**
     * Gets the average width of lines in a document
     * 
     * @return the average line width in a document -- averageLineWidth
     */
    public float getAverageLineWidth()
    {
	return m_averageLineWidth;
    }

    
    /**
     * Gets the average X-axis gaps between two neighbor text pieces in a document
     * 
     * @return the average X gap
     */
    public float getAverageXGap()
    {
	return m_averageXGap;
    }

    
    /**
     * Gets the text scale information of the body content area in a document
     * 
     * @return the body Text Scale information
     */
    public float getBodyTextScale()
    {
	return m_bodyTextScale;
    }

    /**
     * Gets the font information of the document title
     * 
     * @return the font information of the document title
     */
    public float getTitleFont()
    {
	return m_titleFont;
    }

    /**
     * Gets the minimum X-axis in a document
     * @return the minimum X-axis
     */
    public float getMinX()
    {
    	return m_minX;
    }
    
    /**
     *Gets the maximum X-axis in a document
     * @return the max X-axis
     */
    public float getMaxX()
    {
    	return m_maxX;
    }
    
    /**
     * Gets the minimum Y-axis in a document
     * @return the minimum Y-axis
     */
    public float getMinY()
    {
    	return m_minY;
    }
    
    /**
     * Gets the maximum Y-axis in a document
     * @return the max Y-axis
     */
    public float getMaxY()
    {
    	return m_maxY;
    }
    
    /**
     * Gets the middle X-axis in a document
     * @return the middle X-axis
     */
    public float getMiddleX()
    {
    	return m_middleX;
    }
    
    /**
     * Gets the left-end X axis of the middle area in a document
     * @return the left-end x-axis of the middle area
     */
    public float getMiddleArea_X()
    {
    	return m_middleArea_X;
    }
    
    /**
     * Gets the right-end X-axis of the middle area in a document
     * @return the right-end x-axis of the middle area
     */
    public float getMiddleArea_EndX()
    {
    	return m_middleArea_EndX;
    }
    
    /**
     * Sets the average character width in a document
     * @param averageCharWidth
     *            the average Char Width to set
     */
    public void setAverageCharWidth(float averageCharWidth)
    {
    	m_averageCharWidth = averageCharWidth;
    }

    /**
     * Sets the average gaps between the consecutive lines in a document
     * @param averageLineGap
     *            the average Line Gap to set
     */
    public void setAverageLineGap(float averageLineGap)
    {
    	m_averageLineGap = averageLineGap;
    }

    /**
     * Sets the average line width in a document
     * @param averageLineWidth
     *            the average Line Width to set
     */
    public void setAverageLineWidth(float averageLineWidth)
    {
    	m_averageLineWidth = averageLineWidth;
    }

    /**
     * Sets the average gap between the left ends of two neighbor text pieces in a document
     * @param averageXGap
     *            the average Gap between X-axes to set
     */
    public void setAverageXGap(float averageXGap)
    {
    	m_averageXGap = averageXGap;
    }

    /**
     * Sets the text scale information of the document body content areas in a document
     * @param bodyTextScale
     *            the text scale information of the document body content areas to set
     */
    public void setBodyTextScale(float bodyTextScale)
    {
    	m_bodyTextScale = bodyTextScale;
    }

    /**
     * Sets the font information of the document title 
     * @param titleFont
     *            the Font of the title to set
     */
    public void setTitleFont(float titleFont)
    {
    	m_titleFont = titleFont;
    }
    
    /**
     * Sets the minimum X-axis in a document
     * @param minX
     */
    public void setMinX(float minX)
    {
    	m_minX = minX;
    }
    /**
     * Sets the maximum X-axis in a document
     * @param maxX
     */
    public void setMaxX(float maxX)
    {
    	m_maxX = maxX;
    }
    
    /**
     * Sets the minimum Y-axis in a document
     * @param minY
     */
    public void setMinY(float minY)
    {
    	m_minY = minY;
    }
    
    /**
     * Sets the maximum Y-axis in a document
     * @param maxY
     */
    public void setMaxY(float maxY)
    {
    	m_maxY = maxY;
    }
    
    /**
     * Sets the middle X-axis in a document
     * @param middleX
     */
    public void setMiddleX(float middleX)
    {
    	m_middleX = middleX;
    }
    
    /**
     * Sets the left-end X-axis of the middle area in a document
     * @param middleArea_X
     *         the left-end X-axis of the middle area in a document
     */
    public void setMiddleArea_X(float middleArea_X)
    {
    	m_middleArea_X = middleArea_X;
    }
    
    /**
     * Sets the middle X-axis of the middle area in a document 
     * @param middleArea_EndX
     *        the middle X-axis of the middle area in a document 
     */
    public void setMiddleArea_EndX(float middleArea_EndX)
    {
    	m_middleArea_EndX = middleArea_EndX;
	}
    
    /**
     * Sets the table metadata in a document 
     * @param tableMetadata
     *         the extracted table metadata
     */
    public void setTableMetadata(String tableMetadata) {
    	m_tableMetadata = tableMetadata;
    }
    
    /**
     * Sets the document metadata      
     * @param docMeta
     *         the extracted document metadata
     */
    public void setDocMeta(String docMeta){
    	m_docMeta = docMeta;
    }
    
    /**
     * Sets the document metadata in the XML-format
     * @param xdocMeta
     * 		the document medata in the XML-format
     * 
     * */
    public void setDocMeta(Document xdocMeta){
    	
    	m_xdocMeta = xdocMeta;
    }
    
    
    /**
     * Sets the error message of this document
     * @param errorMsg
     *         the error message to set
     */
    public void setErrorMsg(String errorMsg) {
    	m_errorMsg = errorMsg;
    }
    
    /**
     * Sets the total number of pages in a document
     * @param pageNum
     *         the total number of pages
     */
    public void setPageNum (int pageNum) {
    	m_pageNum = pageNum;
    }
    
    /**
     * Sets the total number of the extracted tables in a document
     * @param tableNum
     *        the total number of tables
     */
    public void setTableNum (int tableNum) {
    	m_tableNum = tableNum;
    }
    
    
    /**
     * Gets the mapping table of the HTML code
     * @return the string array recording the decode mapping table
     */
    public String[] getHtml2CharMapping () {
    	m_Html2Char[0] = " ";	//&#32
    	m_Html2Char[1] = "!";
    	m_Html2Char[2] = "\"";
    	m_Html2Char[3] = "#";	
    	m_Html2Char[4] = "$";	//36
    	m_Html2Char[5] = "%";
    	m_Html2Char[6] = "&";
    	m_Html2Char[7] = "'";
    	m_Html2Char[8] = "(";
    	m_Html2Char[9] = ")";	//41
    	m_Html2Char[10] = "*";
    	m_Html2Char[11] = "+";
    	m_Html2Char[12] = ",";
    	m_Html2Char[13] = "-";
    	m_Html2Char[14] = ".";	//46
    	m_Html2Char[15] = "/";	
    	m_Html2Char[16] = "0";
    	m_Html2Char[17] = "1";
    	m_Html2Char[18] = "2";
    	m_Html2Char[19] = "3";	//51
    	m_Html2Char[20] = "4";
    	m_Html2Char[21] = "5";
    	m_Html2Char[22] = "6";
    	m_Html2Char[23] = "7";
    	m_Html2Char[24] = "8";	//56
    	m_Html2Char[25] = "9";
    	m_Html2Char[26] = ":";
    	m_Html2Char[27] = ";";
    	m_Html2Char[28] = "<";
    	m_Html2Char[29] = "=";	//61
    	m_Html2Char[30] = ">";
    	m_Html2Char[31] = "?";
    	m_Html2Char[32] = "@";
    	m_Html2Char[33] = "A";
    	m_Html2Char[34] = "B";	//66
    	m_Html2Char[35] = "C";
    	m_Html2Char[36] = "D";
    	m_Html2Char[37] = "E";
    	m_Html2Char[38] = "F";
    	m_Html2Char[39] = "G";	//71
    	m_Html2Char[40] = "H";
    	m_Html2Char[41] = "I";
    	m_Html2Char[42] = "J";
    	m_Html2Char[43] = "K";
    	m_Html2Char[44] = "L";	//76
    	m_Html2Char[45] = "M";
    	m_Html2Char[46] = "N";
    	m_Html2Char[47] = "O";
    	m_Html2Char[48] = "P";
    	m_Html2Char[49] = "Q";	//81
    	m_Html2Char[50] = "R";
    	m_Html2Char[51] = "S";
    	m_Html2Char[52] = "T";
    	m_Html2Char[53] = "U";
    	m_Html2Char[54] = "V";	//86
    	m_Html2Char[55] = "W";
    	m_Html2Char[56] = "X";
    	m_Html2Char[57] = "Y";
    	m_Html2Char[58] = "Z";
    	m_Html2Char[59] = "[";	//91
    	m_Html2Char[60] = "\\";
    	m_Html2Char[61] = "]";
    	m_Html2Char[62] = "^";
    	m_Html2Char[63] = "_";
    	m_Html2Char[64] = "`";	//96
    	m_Html2Char[65] = "a";		
    	m_Html2Char[66] = "b";
    	m_Html2Char[67] = "c";
    	m_Html2Char[68] = "d";
    	m_Html2Char[69] = "e";	//101
    	m_Html2Char[70] = "f";
    	m_Html2Char[71] = "g";
    	m_Html2Char[72] = "h";
    	m_Html2Char[73] = "i";
    	m_Html2Char[74] = "j";	//106
    	m_Html2Char[75] = "k";
    	m_Html2Char[76] = "l";
    	m_Html2Char[77] = "m";
    	m_Html2Char[78] = "n";
    	m_Html2Char[79] = "o";	//111
    	m_Html2Char[80] = "p";
    	m_Html2Char[81] = "q";
    	m_Html2Char[82] = "r";
    	m_Html2Char[83] = "s";
    	m_Html2Char[84] = "t";	//116
    	m_Html2Char[85] = "u";
    	m_Html2Char[86] = "v";
    	m_Html2Char[87] = "w";
    	m_Html2Char[88] = "x";
    	m_Html2Char[89] = "y";	//121
    	m_Html2Char[90] = "z";
    	m_Html2Char[91] = "{";
    	m_Html2Char[92] = "|";
    	m_Html2Char[93] = "}";
    	m_Html2Char[94] = "~";	//126
    	
    	
    	return m_Html2Char;
    }
}
