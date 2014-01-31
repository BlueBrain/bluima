/**
 * This package defines configurations and the debug class.
 */
package edu.psu.seersuite.extractors.tableextractor;



/**
 * This class defines the public parameters, which will be used in the whole package.
 * 
 * This class also defines the triggers for debugging and testing. 
 * 
 * @author Ying, Shuyi
 */

public class Config
{
    
	/**
	 * TABLE_KEYWORDS: lists the possible beginning keywords of table captions, 
	 * which are extracted by PDF text extractors. These keywords are very helpful for table caption detection and the table boundary detection. 
	 */
	public static String[] TABLE_KEYWORDS = { "Table", "TABLE", "lable",
	    "Tble", "Tabl", "(Table"};
    
    /**
     * FIGURE_KEYWORDS: lists the possible beginning words of figure captions, 
     * which are extracted by PDF text extractors. 
	 */
	public static String[] FIGURE_KEYWORDS = {"Figure", "FIGURE", "Fig."};
   

	/**
     * DEBUG_MODE: this parameter is used for debugging purpose. The default value is "false".
     * If we want to see the debug results, it should be set as "true". The middle-stage results will be printed in the screen and saved locally.   
	 * Once it is set as "true", the system will call "Debug.java".
	 */
    public static boolean DEBUG_MODE = false;
    
    /**
     *  SMALL_TEST: this parameter is used for small-scale testing.   
	 */
    public static boolean SMALL_TEST = false;
    
   
    //public static float DENSITY_AREA_HEIGHT = 55.0f;
    
}
