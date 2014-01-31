/**
 * 
 */
package edu.psu.seersuite.extractors.tableextractor.model;

import java.util.ArrayList;

/**
 * This class defines the internal data structure for a table row.
 * 
 * @author Ying, Shuyi
 * 
 */
public class TableRow
{
    private float m_y;
    private boolean m_superScriptRow = false;

    private ArrayList<TextPiece> m_cells = new ArrayList<TextPiece>();

    /**
     * Adds a table cell into a table row
     * @param cell
     *            the cell to add
     */
    public void addCell(TextPiece cell)
    {
	m_cells.add(cell);
    }

    /**
     * Gets the cells of a table row 
     * @return the table cells
     */
    public ArrayList<TextPiece> getCells()
    {
	return m_cells;
    }

    /**
     * Gets the Y-axis of a table row
     * @return the Y-axis of a table
     */
    public float getY()
    {
	return m_y;
    }

    /**
     * Judges whether a table row contains superscripts or not
     * @return where a table row has superscript
     */
    public boolean isSuperScriptRow()
    {
	return m_superScriptRow;
    }

    /**
     * Sets a table row as the row with superscript
     * @param superScriptRow
     *            the superScriptRow to set
     */
    public void setSuperScriptRow(boolean superScriptRow)
    {
	m_superScriptRow = superScriptRow;
    }

    /**
     * Sets the Y-axis of a table row
     * @param y
     *            the y to set
     */
    public void setY(float y)
    {
	m_y = y;
    }
}
