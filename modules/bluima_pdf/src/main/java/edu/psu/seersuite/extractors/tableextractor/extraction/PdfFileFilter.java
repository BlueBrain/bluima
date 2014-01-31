/**
 * This package defines the core table extraction classes
 */
package edu.psu.seersuite.extractors.tableextractor.extraction;

import java.io.File;

/**
 * File filter for PDF documents. This class will detect the PDF documents by checking the file extensions.
 * 
 * @author Ying, Shuyi
 * 
 */
public class PdfFileFilter implements java.io.FileFilter
{
    /**
     * Judges whether a file is a PDF document or not
     * 
     * @param f
     *            file to check
     * @return a boolean value: yes/no
     */
    public boolean accept(File f)
    {
	if (f.isDirectory())
	{
	    return false;
	}
	String name = f.getName().toLowerCase();
	return name.endsWith(".pdf");
    }
}
