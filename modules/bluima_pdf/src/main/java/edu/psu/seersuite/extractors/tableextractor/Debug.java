/**
 * This package defines configurations and the debug class.
 */
package edu.psu.seersuite.extractors.tableextractor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import edu.psu.seersuite.extractors.tableextractor.model.TextPiece;

/**
 * This class is designed for testing purpose. 
 * The function is to validate the performance of the PDF text extractors.
 * The combineLines results will be printed into files in middleResults_new directory.
 * 
 * To call this class, the parameter "DEBUG_MODE" in "Config.java" should be set as "true". 
 * 
 * @author Ying, Shuyi
 */


public class Debug {
    
	/**
	 * For testing purpose. The function of this method is to display the middle-stage text combination results.
     * E.g., combined texts in the Word or Line level. The results will be printed into files "pdfFile" 
     * in path "outputDirPath" in the directory of the PDF documents
     * 
     *  @param wordsByPage 
     *             the word list of a PDF document page
     *  @param linesByPage
     *             the line list of a PDF document page
     *  @param outputDirPath 
     *             the directory path where the middle-stage results will go to
     *  @param pdfFile        
     *             the PDF file being processed   
     *  @throws IOException            
	 */
	public static void printMiddleResults(ArrayList<ArrayList<TextPiece>> wordsByPage,
	ArrayList<ArrayList<TextPiece>> linesByPage, String outputDirPath, File pdfFile) {    	    	   
    	
    	try {
    		/**
    		 * Creates the middle-result directory if it does not exist
    		 */
    		File middleDir = new File(outputDirPath, "middleResults-Seersuite"); //"middleResults-Seersuite"
    		if (!middleDir.exists()) {
    			middleDir.mkdirs();
    		}

    		/**
    		 * Generates the names with the directory path of files to store the middle-stage results 
    		 */
    		File pieceResultFile = new File(middleDir, pdfFile.getName() + ".piece");
    		File lineResultFile = new File(middleDir, pdfFile.getName() + ".line");
    		BufferedWriter bw0 = new BufferedWriter(new FileWriter(pieceResultFile));
    		BufferedWriter bw = new BufferedWriter(new FileWriter(lineResultFile));

    		/**
    		 * Loops over PDF document pages. 
    		 */
    		for (int i = 0; i < linesByPage.size(); i++) {
    			bw0.write("************ PAGE " + i + "***************\n");
    			bw.write("************ PAGE " + i + "***************\n");

    			ArrayList<TextPiece> wordsOfAPage = wordsByPage.get(i);
    			ArrayList<TextPiece> linesOfAPage = linesByPage.get(i);

    			/**
    			 * Loops over text pieces within a page
    			 */
    			for (int j = 0; j < wordsOfAPage.size(); j++) {
    				TextPiece word = wordsOfAPage.get(j);
    				String wordStr = String.format("WORD %d %s\n", j, word.toString());
    				bw0.write(wordStr);
    			}
		
    			/**
    			 * Loops over lines within a page
    			 */
    			for (int j = 0; j < linesOfAPage.size(); j++) {
    				TextPiece line = linesOfAPage.get(j);
    				String lineStr = String.format(
    						"LINE %d %s\n",
    						j,
    						line.toString());
    				bw.write(lineStr);
    			}
    			bw0.write("\n");
    			bw.write("\n");
    		}
    		bw0.close();
    		bw.close();
    	}
    	catch (IOException e){
    		System.out.printf("[Debug Error] IOException\n");
    	}
    }
    
    
    
    /**
     * After detecting and extracting a table, this method enables us to save the table metadata file locally for later performance evaluation. 
     * 
     *  @param outputDirPath 
     *             the directory path where the middle-stage results will go to
     *  @param pdfFile        
     *             the PDF file being processed 
     *  @param meta
     *             the table metadata to be printed   
     *  @throws IOException                
     **/
    public static void printTableMeta(String outputDirPath, File pdfFile, String meta) {
    	try {
    		File middleDir = new File(outputDirPath, "metadata");
    		if (!middleDir.exists()) {
    			middleDir.mkdirs();
    		}
    		File tableMetaFile = new File(middleDir, pdfFile.getName() + ".metadata");
    		BufferedWriter bw0 = new BufferedWriter(new FileWriter(tableMetaFile));
    		//System.out.println(meta);
    		bw0.write(meta);
    		bw0.close();
    	}
    	catch (IOException e){
    		System.out.printf("[Debug Error] IOException\n");
    	}
    }
    
    
    /**
     * After processing all the PDF documents, this method helps us to get the statistic information of all the PDF documents
     * such as the total number of pages, tables, etc. 
     * 
     * @param outputDirPath 
     *             the directory path where the middle-stage results will go to
     * @param pdfFile        
     *             the PDF file being processed 
     * @param pageNum
     *             the total number of pages in the PDF directory
     * @param tableNum
     *             the total number of detected tables in the PDF directory
     * @throws IOException                        
     **/
   public static void printStatInfo(String outputDirPath, File pdfFile, int pageNum, int tableNum) {
    	try {
    		File classificationData = new File(outputDirPath, "statInfo");
    		if (!classificationData.exists()) {
    			classificationData.mkdirs();
    		}
    		File fileName = new File(classificationData, "tableNumStatInfo.txt");
    		BufferedWriter bw0 = new BufferedWriter(new FileWriter(fileName, true));
    		bw0.append(outputDirPath+pdfFile.getName() + "\t\t" + pageNum + " PAGES\t\t" +  tableNum + " TABLES\n");
    		bw0.close();
    	}
    	catch (IOException e){
    		System.out.printf("[Debug Error] IOException\n");
    	}
    }
}
