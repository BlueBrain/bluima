/**
 * This package defines the core table extraction classes
 */
package edu.psu.seersuite.extractors.tableextractor.extraction;

import java.io.File;
import java.util.ArrayList;

import edu.psu.seersuite.extractors.tableextractor.model.TextPiece;

/**
 * Interface of PDF Parser
 * 
 * @author Shuyi, Ying
 * 
 */
public interface IPdfParser
{

    /**
     * Gets text pieces from a PDF document. The input is a PDF file and the output is an ArrayList.
     * 
     * @param pdfFile
     *            input PDF file
     * @return lists of text pieces (one list per page)
     */
    public ArrayList<ArrayList<TextPiece>> getTextPiecesByPage(File pdfFile);
}
