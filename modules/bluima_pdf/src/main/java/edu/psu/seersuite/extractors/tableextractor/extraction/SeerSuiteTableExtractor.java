/**
 * This package defines the core table extraction classes
 */
package edu.psu.seersuite.extractors.tableextractor.extraction;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.jdom2.Document;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import edu.psu.seersuite.extractors.tableextractor.model.Table;

/**
 * This class extracts tables in the batch mode. The input should be a directory instead of a single PDF file. 
 * The directory can contain multiple/one PDF files.
 * <p>
 * The BatchExtractor class represents a batch mode for extracting tables from PDF files. 
 * This class leverages the table extraction APIs provided in the class TableExtractor to extract tables 
 * through a command line shown as below.
 * <p>
 **
 * Usage: BatchExtractor [pdf-dir-path] [output-dir-path] [parser] <p>
 *         [pdf-dir-path]: the directory contains all pdf files. <p>    
 *         [output-dir-path]: the directory stored all extracted table in XML format. <p>
 *         [parser]: the PDF parser options: PDFBox or TET. <p>
 * <p> 
 * This class provides the capability to extract the tables from massive PDF achieves. The output directory
 * stores all extracted tables in the XML format for display purpose. 
 * <p>
 * A user can also define and store the tables in a customized format.
 * <p>
 * @author Shuyi, Ying
 * 
 */

public class SeerSuiteTableExtractor {

	static class PDFFilter implements FileFilter {
		public boolean accept(File pathname) {
			if(pathname.canRead() && pathname.getName().endsWith(".pdf"))
				return true;
			return false;
		}
	}
	
    public static void main(String[] arguments) throws IOException {
	
    if(arguments[0].equals("batch")) {
    		String pdfFileDir = arguments[1];
    		File pdfDir = new File(pdfFileDir);
    		int counter = 1;
    		if(!pdfDir.isDirectory()) {
    		    throw new IOException("Need directory in this mode");
    		}
    		for (File fid: pdfDir.listFiles(new PDFFilter())) {
    			extractTable(pdfDir.getPath()+"/"+fid.getName(),Integer.toString(counter));
    			counter++;
    		}
    	}
    else if(arguments.length == 2) {
		String pdfFilePath = arguments[0];
		String id = arguments[1];
		extractTable(pdfFilePath,id);
	}
 	else {
	    showUsage();
	}
    }

    private static void showUsage() {
		System.out.println("SeerSuiteTableExtractor <PDF_FILE> <ID>");

    }

    private static String getMetFileName(String fileName) {
		String metFile = fileName+".met";
		File mF = new File(metFile);
		try {
		    mF.createNewFile();
		} catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		if(mF.exists()) {
			return metFile;
		}
		else {
			metFile = fileName.replaceAll("\\.pdf$",".met");
			mF = new File(metFile);
			if(mF.exists()) {
				return metFile;
			}
			else {
				return null;
			}
		}
		
    }

    private static String getTableFile(String fileName, String id) {
	if(id != null) {
		int i = fileName.lastIndexOf(File.separator);
		String filePath = (i > -1) ? fileName.substring(0,i): fileName;
		if(filePath.equals(fileName)) {
			// return the file name
			return id+".tbl";
		}
		else { // got the file path
			return filePath+File.separator+id+".tbl";
		}
	}
	return fileName.replaceAll("\\.pdf$",".tbl");
    }

    private static void extractTable(String filePath, String id) throws IOException
    {
    	File pdfFile = new File(filePath);
    	//System.out.println(pdfFile.getName());
    	BoxTableExtractor extractor = new BoxTableExtractor();
    	String metid = null;
    	if(!pdfFile.exists()) {
    		System.out.println(filePath+" does not exist");
    	}
    	else {
//    		String metFile = getMetFileName(filePath);
//    		try {
//    			SAXBuilder builder = new SAXBuilder();
//    			Document metRecord = builder.build(new File(metFile));
//    			Element root = metRecord.getRootElement();
//    			Element metsha1 = root.getChild("SHA1");
//    			metid = metsha1.getText();
//    		} catch(JDOMException e) {
//    			System.out.println("Met File Error Can't open met file "+metFile);
//			e.printStackTrace();
//    			return;
//    		} catch(NullPointerException e) {
//    			System.out.println("Met File Error Can't find field "+metFile);
//    			return;
//    		} catch(IOException e) {
//    			System.out.println("Can't find Met File "+metFile);
//    			return;
//    		}
    	    metid="blah";
			
		// Code should change to use TET - for now use PDFBox
    		IPdfParser parser = new PdfBoxParser();
    		extractor.setParser(parser);
		
    		try {
    			//System.out.println(pdfFile+" PDF FILE");
    			ArrayList<Table> extractedTableSet = extractor.extract(pdfFile,id, metid);
    			XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
    			if(extractedTableSet != null) {
    				String tblFileName = getTableFile(filePath, id);
    				File tblFile = new File (tblFileName);
    				PrintWriter xmlWriter = new PrintWriter(new FileOutputStream(tblFile));
    				Document xmlDoc = extractor.getXMLDoc();
    				outputter.output(xmlDoc, xmlWriter);
    				xmlWriter.close();	 
    			}
    		}
    		catch(Exception e) {
    			e.printStackTrace();
			return;
    		}
		
	}
	
	
    }
    
}
