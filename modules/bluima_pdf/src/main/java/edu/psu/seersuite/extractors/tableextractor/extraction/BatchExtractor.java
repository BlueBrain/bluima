/**
 * This package defines the core table extraction classes
 */
package edu.psu.seersuite.extractors.tableextractor.extraction;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import edu.psu.seersuite.extractors.tableextractor.Config;
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

public class BatchExtractor
{

    /**
     * Main entry
     * 
     * @param args
     *            command line parameters: the path of the PDF directory, the path of the output, the PDF parser types, the debug mode, and the test mode.
     * @throws IOException
     */
    public static void main(String[] args) throws IOException
    {
	if (args.length == 3)
	{
	    String pdfDirPath = args[0];
	    String outputDirPath = args[1];
	    String parserType = args[2];
	    extractTables(pdfDirPath, outputDirPath, parserType);
	}
	else if (args.length == 4)
	{
	    String pdfDirPath = args[0];
	    String outputDirPath = args[1];
	    String parserType = args[2];

	    if (args[3].compareTo("--debug") == 0)
	    {
		Config.DEBUG_MODE = true;
	    }
	    else if (args[3].compareTo("--small") == 0)
	    {
		Config.SMALL_TEST = true;
	    }

	    extractTables(pdfDirPath, outputDirPath, parserType);
	}
	else
	{
	    showUsage();
	}
    }

    /**
     * Extracts tables from all PDFs in a directory
     * 
     * @param pdfDirPath
     *            the directory path of the PDF documents to be processed
     * @param outputDirPath
     *            output directory path where the extracted metadata and other result files will be saved into
     * @param parserType
     * 			   the name of the PDF text extraction tools.
     * @throws FileNotFoundException
     * @throws IOException
     **/
    @SuppressWarnings("unchecked")
    private static void extractTables(
	    String pdfDirPath,
	    String outputDirPath,
	    String parserType) throws IOException
    {
	/*
	 *  directory existence check
	 */
	File pdfDir = new File(pdfDirPath);
	if (!pdfDir.exists())
	{
	    System.out.printf("[Error] %s does not exist\n", pdfDirPath);
	    return;
	}

	/*
	 *  must be a directory
	 */
	if (!pdfDir.isDirectory())
	{
	    System.out.printf("[Error] %s is not a directory\n", pdfDirPath);
	    return;
	}

	/*
	 *  Creates the output directory if not exist
	 */
	File outputDir = new File(outputDirPath);
	if (!outputDir.exists())
	{
	    outputDir.mkdirs();
	    System.out.printf("[Info] output dir %s created\n", outputDirPath);
	}

	/*
	 *  to output the XML files
	 */
	File xmlDir = new File(outputDirPath, "xml");
	if (!xmlDir.exists())
	{
	    xmlDir.mkdirs();
	    System.out.printf("[Info] xml dir %s created\n", xmlDir);
	}

	TableExtractor extractor = new TableExtractor();

	/*
	 *  Sets PDF parser. Now we have two parsers: PDFBOX or TET. Other parsers can be easily added here. 
	 */
	IPdfParser parser = null;

	if (parserType.compareToIgnoreCase("pdfbox") == 0)
	{
	    try
	    {
		parser = new PdfBoxParser();
	    }
	    catch (IOException e)
	    {
		System.out.printf("[Error] PDFBox parser cannot be created\n");
		return;
	    }
	    extractor.setParser(parser);
	}
	else
	{
	    System.out.printf(
		    "[Error] %s is not a correct parser name\n",
		    parserType);
	    return;
	}

	PdfFileFilter filter = new PdfFileFilter();
	File[] pdfFiles = pdfDir.listFiles(filter);

	System.out.printf("[Info] %d PDF documents found\n", pdfFiles.length);

	/*
	 *  Main loop over files
	 */
	int success = 0;
	int fail = 0;
	int tableCount = 0;

	XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
	XMLOutputter rawOutputter = new XMLOutputter(Format.getCompactFormat());

	File mysqlInFile = new File(outputDir, "mysql-infile");
	PrintWriter mysqlInFileWriter = new PrintWriter(new FileOutputStream(
		mysqlInFile));

	int i = 0;
	for (File pdfFile : pdfFiles)
	{
	    /*
	     *  handling one file each time
	     */
	    try
	    {
		ArrayList<Table> extractedTableSet = extractor.extract(
			pdfFile,
			outputDirPath);

		if (extractedTableSet != null)
		{
		    /*
		     * Writes new standard XML
		     */
		    String xmlFileName = pdfFile.getName().replaceFirst(
			    ".pdf",
			    ".xml");
		    File xmlFile = new File(xmlDir, xmlFileName);
		    PrintWriter xmlWriter = new PrintWriter(
			    new FileOutputStream(xmlFile));

		    Document xmlDoc = extractor.getXMLDoc();

		    outputter.output(xmlDoc, xmlWriter);

		    xmlWriter.close();

		    tableCount += extractor.getDocInfo().getTableNum();
		    success++;

		    Element xRoot = xmlDoc.getRootElement();
		    Element xTables = xRoot.getChild("tables");

		    String paperid = xRoot.getAttributeValue("id");

		    if (xTables != null)
		    {
		    	List<Element> tableRecords = xTables.getChildren();
		    	for (Element tableRecord : tableRecords)
		    	{
		    		String id = paperid + "-"
				    + tableRecord.getAttributeValue("id");
		    		String caption = "";
		    		String content = "";
		    		String footNote = "";
		    		String refText = "";
		    		String pageNumInDoc = "\\N";

		    		Element xTable = tableRecord.getChild("table");
		    		if (xTable != null)
		    		{
		    			content = cleanString(rawOutputter.outputString(xTable));
		    		}

		    		Element xCaption = tableRecord.getChild("caption");
		    		if (xCaption != null)
		    		{
		    			caption = cleanString(xCaption.getText());
		    		}

		    		Element xFootNote = tableRecord.getChild("footnote");
		    		if (xFootNote != null)
		    		{
		    			footNote = cleanString(xFootNote.getText());
		    		}

		    		Element xRefText = tableRecord.getChild("referenceText");
		    		if (xRefText != null)
		    		{
		    			refText = cleanString(xRefText.getText());
		    		}

		    		Element xPageNumInDoc = tableRecord.getChild("pageNumInDoc");
		    		if (xPageNumInDoc != null)
		    		{
		    			pageNumInDoc = xPageNumInDoc.getText();
		    		}

		    		String line =  id + "\t" + caption + "\t"
		    		+ content + "\t" + footNote + "\t"
		    		+ refText + "\t" + paperid + "\t"
		    		+ pageNumInDoc + "\n";
		    		mysqlInFileWriter.write(line);
		    	}	
		    }
		}
		else
		{
		    fail++;
		}
	    }
	    catch (Exception e)
	    {
		System.out.printf(
			"[Error] unhandled exception of %s\n",
			pdfFile.getName());

		/*
		 *  Print to standard error
		 */
		System.err.printf("----- %s -----\n", pdfFile.getName());
		System.err.printf("%s\n", e.getMessage());
		e.printStackTrace();

		fail++;
	    }

	    i++;

	    if (Config.SMALL_TEST && i >= 10)
	    {
		break;
	    }
	}

	mysqlInFileWriter.close();

	/*
	 * Prints the summary information after processing all the PDF documents. 
	 */
	File summaryFile = new File(outputDir, "summary");
	PrintWriter summaryWriter = null;
	try
	{
	    summaryWriter = new PrintWriter(new FileOutputStream(summaryFile));
	    summaryWriter.write("total=" + pdfFiles.length + "\n");
	    summaryWriter.write("success=" + success + "\n");
	    summaryWriter.write("fail=" + fail + "\n");
	    summaryWriter.write("table=" + tableCount + "\n");
	}
	catch (FileNotFoundException e)
	{
	    // ignore
	}
	finally
	{
	    if (summaryWriter != null)
	    {
		summaryWriter.close();
	    }
	}

	System.out.printf("--------------------\n");
	System.out.printf("success=%d\n", success);
	System.out.printf("fail=%d\n", fail);
	System.out.printf("--------------------\n");
    }

    /**
     * Shows usages by displaying the information on the screen, such as the PDF path, output path, the PDF parser, process mode, etc.  
     */
    private static void showUsage()
    {
	System.out.println("BatchExtractor <pdf-dir-path> <output-dir-path> <parser>");
	System.out.println("BatchExtractor <pdf-dir-path> <output-dir-path> <parser> --debug");
	System.out.println("BatchExtractor <pdf-dir-path> <output-dir-path> <parser> --small");
	System.out.println("\tparser= pdfbox | tet");
    }

    /**
     * Cleans the special symbols (e.g., "\t", "\n") in a string
     * @param str
     *        the string to be checked
     * @return the cleaned string
     */
    private static String cleanString(String str)
    {
	String result = str;
	result = result.replace("\r", "");
	result = result.replace("\n", "");
	result = result.replace("\t", "");

	return result;
    }
}
