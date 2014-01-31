/**
 * 
 */
package edu.psu.seersuite.extractors.tableextractor.model;

/**
 * A "TextPiece" is the smallest unit extracted from PDF documents. It is
 * supposed to be a complete word. However, due to the quality of the PDF converters,
 * they are often a sub-string of complete word.
 * 
 * This class defines the internal data structure of a TextPiece as well as the parameters and methods.
 * 
 * @author Ying, Shuyi
 * 
 */
public final class TextPiece implements Comparable<TextPiece>
{
    float m_x;                       //the left-end X axis of the text piece
    float m_y;                       //the top-end Y axis of the text piece
    float m_endX;                    //the right-end X axis of the text piece
    float m_endY;                    //the bottom-end Y axis of the text piece
    float m_xScale;                  //the X-scale of the text piece 
    float m_yScale;                  //the Y-scale of the text piece 

    boolean m_superScriptBeginning = false;      //whether this text piece begins with a superscript
    boolean m_sparseLine = true;                 //whether this text piece is a sparse line or not
    boolean m_onlyPiece_PhysicalLine = true;     //whether this text piece is the only piece in the physical line in document. We need this attribute to label table caption lines....

    float m_width;                               //the width of this text piece
    float m_height;                              //the height of this text piece
    float m_widthOfSpace;                        //the width space in this text piece
    float m_wordSpacing;                         //the space between words in this text piece
    float m_fontSize;                            //the font size of this text piece

    String m_fontName;                           //the font name of this text piece. Added by Ying for future implementation
    String m_text;                               //the text content of this text piece

    /**
     * Gets the right-end X-axis of a text piece
     * @return the endX of a text piece
     */
    public float getEndX()
    {
	return m_endX;
    }

    /**
     * Gets the ending (below) Y axis of a text piece
     * @return the ending Y axis
     */
    public float getEndY()
    {
	return m_endY;
    }

    /**
     * Gets the font name of a text piece
     * @return the font name
     * */
    public String getFontName()
    {
	return m_fontName;
    }

    /**
     * Gets the font size of a text piece
     * @return the font size
     */
    public float getFontSize()
    {
	return m_fontSize;
    }

    /**
     * Gets the content of a text piece
     * @return the text itself
     */
    public String getText()
    {
	return m_text;
    }

    /**
     * Gets the width of a text piece
     * @return the width of a text piece
     */
    public float getWidth()
    {
	return m_width;
    }
    
    /**
     * Gets the height of a text piece
     * @return the height of a text piece
     */
    public float getHeight()
    {
	return m_height;
    }

    /**
     * Gets the space width between characters in a text piece
     * @return the space width
     */
    public float getWidthOfSpace()
    {
	return m_widthOfSpace;
    }

    /**
     * Gets the space size between words
     * @return the space size between words 
     */
    public float getWordSpacing()
    {
	return m_wordSpacing;
    }

    /**
     * Gets the left-side X axis of a text piece
     * @return the left-side X axis
     */
    public float getX()
    {
	return m_x;
    }

    /**
     * Gets the X scale of a text piece
     * @return the X-Scale
     */
    public float getXScale()
    {
	return m_xScale;
    }

    /**
     * Gets the beginning Y axis of a text piece
     * @return the beginning Y axis
     */
    public float getY()
    {
	return m_y;
    }

    /**
     * Gets the Y scale of a text piece
     * @return the Y-Scale
     */
    public float getYScale()
    {
	return m_yScale;
    }

    /**
     * Judges whether the first character of a text piece starting is a superscript
     * @return the superScriptBeginning
     */
    public boolean isSuperScriptBeginning()
    {
	return m_superScriptBeginning;
    }

    /**
     * Judges whether a text line is a sparse line or not
     * @return whether it is a sparse line or not
     */
    public boolean isSparseLine() {
    	return m_sparseLine;
    }
    
    /**
     * Sets the right-end X-axis of a text piece
     * @param endX
     *        the right-end X axis to set
     */
    public void setEndX(float endX)
    {
	m_endX = endX;
    }

    /**
     * Sets the ending Y axis of a text piece
     * @param endY
     *            the ending Y to set
     */
    public void setEndY(float endY)
    {
	m_endY = endY;
    }

    /**
     * Sets the font name of a text piece
     * @param fontName
     *           the font name to be set
     * */
    public void setFontName(String fontName)
    {
	m_fontName = fontName;
    }

    /**
     * Sets the font size of a text piece
     * @param fontSize
     *            the font size to set
     */
    public void setFontSize(float fontSize)
    {
	m_fontSize = fontSize;
    }

    /**
     * Sets a text piece starting with a superscript 
     * @param superScriptBeginning
     *            the superScriptBeginning to set
     */
    public void setSuperScriptBeginning(boolean superScriptBeginning)
    {
	m_superScriptBeginning = superScriptBeginning;
    }

    /**
     * Sets a text piece as a sparse line
     * @param sparseLine
     *         a boolean value (whether it is a sparse line or not) 
     */
    public void setSparseLine(boolean sparseLine)
    {
	m_sparseLine = sparseLine;
    }
    
    /**
     * Sets the text content of a text piece
     * @param text
     *            the text to set
     */
    public void setText(String text)
    {
	m_text = text;
    }

    /**
     * Sets the width of a text piece
     * @param width
     *            the width to set
     */
    public void setWidth(float width)
    {
	m_width = width;
    }

    /**
     * Sets the height of a text piece
     * @param height
     *          the height to set
     */
    public void setHeight(float height)
    {
	m_height = height;
    }
    
    /**
     * Sets the width of the space between characters in a text piece
     * @param widthOfSpace
     *            the widthOfSpace to set
     */
    public void setWidthOfSpace(float widthOfSpace)
    {
	m_widthOfSpace = widthOfSpace;
    }

    /**
     * Sets the space between words
     * @param wordSpacing
     *            the wordSpacing to set
     */
    public void setWordSpacing(float wordSpacing)
    {
	m_wordSpacing = wordSpacing;
    }

    /**
     * Sets the left-side X-axis of a text piece
     * @param x
     *            the left-side X axis to set
     */
    public void setX(float x)
    {
	m_x = x;
    }

    /**
     * Sets the XScale of a text piece 
     * @param scale
     *            the xScale of a text piece to set
     */
    public void setXScale(float scale)
    {
	m_xScale = scale;
    }

    /**
     * Sets the starting Y axis of a text piece
     * @param y
     *            the starting Y axis to set
     */
    public void setY(float y)
    {
	m_y = y;
    }

    /**
     * Sets the YScale of a text piece
     * @param scale
     *            the yScale to set
     */
    public void setYScale(float scale)
    {
	m_yScale = scale;
    }

    /**
     * For some PDF files, PDFBOX extracted the texts as HTML codes.
     * We have to detect such files and convert the HTML numbers back to real text for later procession  
     * @param text 
     *         the string to be checked
     * @return 
     *         the boolean result after checking         
     */
    public boolean isHTMLCode (String text) {
    	boolean isHTMLCode =text.startsWith("c");
    	return isHTMLCode;
    	
    }
    
    
    /**
     * Collects all the information of a text piece and stores them in a string. The string will be printed into a middle-result file locally for testing and debugging purpose
     * @return 
     *       the generated information string for the text piece
     */
    public String toString()
    {
	String format = "X=[%f, %f] Y=[%f, %f] Width=[%f] Scale=[%f] Font=[%f] Sparse=[%b] Text=[%s]";
	return String.format(
		format,
		this.getX(),
		this.getEndX(),
		this.getY(),
		this.getEndY(),
		this.getWidth(),
		this.getXScale(),
		this.getFontSize(),
		this.isSparseLine(),
		this.getText());
    }

  
    
    @Override
    /**
     * Sorts text pieces by position by comparing the location of this text piece
     *  with a given text piece t
     * @param t
     *       the text piece to compare with
     * @return 
     *       the comparison result
     *            -1: this text piece is located in the left-side or the top of another text piece t
     *             1: this text piece is located in the right-side or the below of another text piece t
     *             0: this text piece is located in the same location as another text piece t
     */
    public int compareTo(TextPiece t)
    {
	float x1 = this.getX();
	float y1 = this.getY();

	float x2 = t.getX();
	float y2 = t.getY();

	if (y1 < y2)
	{
	    return -1;
	}
	else if (y1 > y2)
	{
	    return 1;
	}
	else
	{
	    if (x1 < x2)
	    {
		return -1;
	    }
	    else if (x1 > x2)
	    {
		return 1;
	    }
	    else
	    {
		return 0;
	    }
	}
    }
}
