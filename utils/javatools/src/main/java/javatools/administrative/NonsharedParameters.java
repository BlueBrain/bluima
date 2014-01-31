package javatools.administrative;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javatools.administrative.Announce;
import javatools.administrative.D;
import javatools.database.Database;
import javatools.database.MySQLDatabase;
import javatools.database.OracleDatabase;
import javatools.database.PostgresDatabase;
import javatools.datatypes.FinalSet;
import javatools.filehandlers.FileLines;



/** 
  This class is part of the Java Tools (see http://mpii.de/yago-naga/javatools).
  It is licensed under the Creative Commons Attribution License 
  (see http://creativecommons.org/licenses/by/3.0) by 
  the YAGO-NAGA team (see http://mpii.de/yago-naga).
    
  
   This is a nonshared, i.e. instantiable variation of the Parameters class. 
   It allows to have different parameter settings handled simultaneously, 
   i.e. each component (or each component instance) using NonsharedParameters can have
   its own parameters allowing, for instance, that two components running at the same time
   work on different databases, both obtained through their own NonsharedParameters instance.  
   
   While the old 'Parameters' class is more convenient to use (less objects passed around),
   this version makes it easier to integrate your components with other components that also 
   use the Parameters/NonsharedParameters to load (and maintain) their settings.
   
   Therefore please consider using the NonsharedParameters instead of the Parameters. 
  
  Provides an interface for an ini-File. The ini-File may contain parameters of the form
  <PRE>
  parameterName = value
  ...
  </PRE>
  It may also contain comments or section headers (i.e. anything that does not match the
  above pattern). Parameter names are not case sensitive. Initial and terminal spaces
  are trimmed for both parameter names and values. Boolean parameters accept multiple
  ways of expressing "true" (namely "on", "true", "yes" and "active").<P>
  
  This class does function as an object! Example:
  <PRE>
    // Read data from my.ini
    NonsharedParameters params = new NonsharedParameters("my.ini");
    // Abort with error message if the following parameters are not specified
    params.ensureParameters(
       "firstPar - some help text for the first parameter",
       "secondPar - some help text for the secondparameter"
    );
    // Retrieve the value of a parameter
    String p=params.get("firstPar");
  </PRE>
  You can load parameters from multiple files. These will overlay.
  You can reference a .ini file in another .ini file using the 'include' parameter, 
  included files will be loaded at the point of the 'include' statements, 
  later parameter settings (whether directly in the file or by another include)
  overwrite parameter settings of this included ini file.   
  Example:
   Content of main.ini:
   <PRE>
     //load database configuration
     include = db_myserver.ini
     // overwrite or add parameters
     databasePort = 5555
     myOtherParameter = 4
   </PRE>
   Content of db_myserver.ini:
   <PRE>
     databaseSystem = postgres
     databaseDatabase = example
     databaseUser = albert
     databaseHost = localhost
     databasePort = 5432
   </PRE>
   If main.ini is loaded, the port parameter will have value '5555' in the resulting NonsharedParameters instance.
   Include is recursive, make sure you do not generate a cycle!
     
*/
public class NonsharedParameters implements Cloneable{
  /** Thrown for an undefined Parameter */
  public static class UndefinedParameterException extends RuntimeException {    
    
    private static final long serialVersionUID = -7648653481162390257L;
    
    public UndefinedParameterException(String s, File f) {
      super("The parameter "+s+" is undefined in "+f);
    }
  }
  
 
   
  /** Holds the filename of the ini-file */
  public File iniFile=null;
  
  /** Holds the path that should be assumed to be the base path to the current directory for all local path values */ 
  public String basePath=null;
  
  /** Contains the values for the parameters*/
  public Map<String,String> values=null;

  /** Holds the pattern used for ini-file-entries */
  public static Pattern INIPATTERN=Pattern.compile(" *(\\w+) *= *(.*) *");

  /** Holds words that count as "no" for boolean parameters */
  public static FinalSet<String> no=new FinalSet<String>(new String [] {
        "inactive",
        "off",
        "false",
        "no",
        "none"
  });
  
  /** Constructors*/
  public NonsharedParameters(){};
  public NonsharedParameters(File iniFile)throws IOException{    
	  init(iniFile);
  };
  public NonsharedParameters(String iniFile)throws IOException{
	  init(iniFile);
  };
  
  public NonsharedParameters(File iniFile, String localPath)throws IOException{    
    if(localPath!=null)
    	basePath=localPath.endsWith("/")?localPath:localPath+"/";    		
    init(iniFile);
  };
  public NonsharedParameters(String iniFile, String localPath)throws IOException{
	if(localPath!=null)
	   	basePath=localPath.endsWith("/")?localPath:localPath+"/";
    init(iniFile);
  };  

  
  /** Returns a value for a date parameter;  */
  public Timestamp getTimestamp(String s) throws UndefinedParameterException {
    return Timestamp.valueOf(get(s));
  }

  /** Returns a value for a date parameter, returning the default value if undefined; */
  public Timestamp getTimestamp(String s, Timestamp defaultValue) throws UndefinedParameterException {
    return(isDefined(s)?getTimestamp(s):defaultValue);
  }

  /** Returns a value for a file or folder parameter; same as getFile but returns the path as String 
   *  also adjusts local paths such that a global path is returned (if a base path is set)*/
  public String getPath(String s) throws UndefinedParameterException {
    if(basePath==null)
      return get(s);
    else{
      String path=get(s);
      if(path.startsWith("./"))
        return basePath+path.substring(2);
//      else if(path.startsWith("../"))
//          return basePath+path;
      else if(path.startsWith("/"))
        return path;
      else 
        return basePath+path;
    }
  }

  /** Returns a value for a file or folder parameter, returning the default value if undefined; same as getFile but returns the path as String*/
  public String getPath(String s, String defaultValue) throws UndefinedParameterException {
    return(isDefined(s)?getPath(s):defaultValue);
  }
  
  
  /** Returns a value for a file or folder parameter; same as getPath but returns an actual File instance */
  public File getFile(String s) throws UndefinedParameterException {
    return(new File(getPath(s)));
  }

  /** Returns a value for a file or folder parameter, returning the default value if undefined; same as getPath but returns an actual File instance */
  public File getFile(String s, File defaultValue) throws UndefinedParameterException {
    return(isDefined(s)?new File(getPath(s)):defaultValue);
  }

  /** Returns a value for an integer parameter as Integer object*/
  public Integer getInteger(String s) throws UndefinedParameterException {
    return(Integer.parseInt(get(s)));
  }
  
  /** Returns a value for an integer parameter returning the default value if undefined*/
  public Integer getInteger(String s, Integer defaultValue) throws UndefinedParameterException {
    return(isDefined(s)?Integer.parseInt(get(s)):defaultValue);
  }
  
  /** Returns a value for an integer parameter*/
  public int getInt(String s) throws UndefinedParameterException {
    return(Integer.parseInt(get(s)));
  }

  /** Returns a value for an integer parameter returning the default value if undefined*/
  public int getInt(String s, int defaultValue) throws UndefinedParameterException {
    return(isDefined(s)?Integer.parseInt(get(s)):defaultValue);
  }
  
  /** Returns a value for an integer parameter*/
  public Float getFloatObject(String s) throws UndefinedParameterException {
    return(Float.parseFloat(get(s)));
  }
  
  /** Returns a value for an integer parameter returning the default value if undefined*/
  public Float getFloatObject(String s, Float defaultValue) throws UndefinedParameterException {
    return(isDefined(s)?Float.parseFloat(get(s)):defaultValue);
  }

  /** Returns a value for an integer parameter*/
  public float getFloat(String s) throws UndefinedParameterException {
    return(Float.parseFloat(get(s)));
  }

  /** Returns a value for an integer parameter returning the default value if undefined*/
  public float getFloat(String s, float defaultValue) throws UndefinedParameterException {
    return(isDefined(s)?Float.parseFloat(get(s)):defaultValue);
  }
  
  /** Returns a value for an integer parameter*/
  public double getDouble(String s) throws UndefinedParameterException {
    return(Double.parseDouble(get(s)));
  }

  /** Returns a value for an integer parameter returning the default value if undefined*/
  public Double getDouble(String s, Double defaultValue) throws UndefinedParameterException {
    return(isDefined(s)?Double.parseDouble(get(s)):defaultValue);
  }
  
  /** Returns a value for a boolean parameter */
  public Boolean getBooleanObject(String s) throws UndefinedParameterException  {
    String v=get(s);
    return(!no.contains(v.toLowerCase()));
  }
  
  /** Returns a value for a boolean parameter, returning a default value by default */
  public Boolean getBooleanObject(String s, Boolean defaultValue) {
    String v=get(s,defaultValue?"yes":"no");
    return(!no.contains(v.toLowerCase()));
  }

  /** Returns a value for a boolean parameter */
  public boolean getBoolean(String s) throws UndefinedParameterException  {
    String v=get(s);
    return(!no.contains(v.toLowerCase()));
  }

  /** Returns a value for a boolean parameter, returning a default value by default */
  public boolean getBoolean(String s, boolean defaultValue) {
    String v=get(s,defaultValue?"yes":"no");
    return(!no.contains(v.toLowerCase()));
  }

  /** Returns a value for a list parameter */
  public List<String> getList(String s) throws UndefinedParameterException  {
    if(!isDefined(s)) return(null);
    return(Arrays.asList(get(s).split("\\s*,\\s*")));
  }


 
  /** Returns a value for a parameter*/
  public String get(String s) throws UndefinedParameterException  {
    if(values==null) throw new RuntimeException("Call init() before get()!");
    String pname=s.indexOf(' ')==-1?s:s.substring(0,s.indexOf(' '));
    String v=values.get(pname.toLowerCase());
    if(v==null) throw new UndefinedParameterException(s,iniFile);
    return(v);
  }

  /** Returns a value for a parameter, returning a default value by default */
  public String get(String s, String defaultValue)  {
    if(values==null) throw new RuntimeException("Call init() before get()!");
    String pname=s.indexOf(' ')==-1?s:s.substring(0,s.indexOf(' '));
    String v=values.get(pname.toLowerCase());
    if(v==null) return(defaultValue);
    return(v);
  }
  
  /** Returns a value for a parameter. If not present, asks the user for it */
  public String getOrRequest(String s, String description) {
    if (values == null)
      throw new RuntimeException("Call init() before get()!");
    String v = values.get(s.toLowerCase());
    if (v == null) {
      D.println(description);
      v = D.read();
    }
    return (v);
  }

  /**
   * Returns a value for a parameter. If not present, asks the user for it and
   * adds it
   */
  public String getOrRequestAndAdd(String s, String description)
      throws IOException {
    String v = getOrRequest(s, description);
    add(s, v);
    return (v);
  }

  /**
   * Returns a value for a parameter. If not present, asks the user for it and
   * adds it
   */
  public boolean getOrRequestAndAddBoolean(String s, String description)
      throws IOException {
    boolean v = getOrRequestBoolean(s, description);
    add(s, v ? "yes" : "no");
    return (v);
  }

  /** Returns a value for a parameter. If not present, asks the user for it */
  public File getOrRequestFileParameter(String s, String description) {
    while (true) {
      String fn = getOrRequest(s, description);
      File f = new File(fn);
      if (f.exists())
        return (f);
      D.println("File not found",fn);
      remove(s);
    }
  }
  
  /** Returns a value for a parameter. If not present, asks the user for it */
  public boolean getOrRequestBoolean(String s, String description) {
    while (true) {
      String fn = getOrRequest(s, description).toLowerCase();
      if (fn.equals("true") || fn.equals("yes"))
        return (true);
      if (fn.equals("false") || fn.equals("no"))
        return (false);
    }
  }

  /** Returns a value for a parameter. If not present, asks the user for it */
  public int getOrRequestInteger(String s, String description) {
    while (true) {
      String fn = getOrRequest(s, description);
      try {
        return(Integer.parseInt(fn));
      } catch(Exception e) {}
      remove(s);
    }
  }
  
  /** Adds a value to the map and to the ini file, if not yet there */
  public void add(String key, String value) throws IOException {
    if (values == null || iniFile == null)
      throw new RuntimeException("Call init() before get()!");
    if (values.containsKey(key.toLowerCase()))
      return;
    values.put(key.toLowerCase(), value);
    Writer w = new FileWriter(iniFile, true);
    w.write(key + " = " + value + "\n");
    w.close();
  }
  
  /** Removes a value from the mapping (NOT: from the file) */
  public String remove(String parameter) {
    return(values.remove(parameter.toLowerCase()));
  }

  /** sets a value for a parameter */
  public void set(String param, String value){
	  if(values==null) throw new RuntimeException("Call init() before get()!");
	  values.put(param.toLowerCase(), value);
  }
  
  /** Initializes the parameters from a file*/
  public void init(File f) throws IOException{
    init(f,true);
  }
  public void init(File f, boolean mainIni) throws IOException {
    if(f.equals(iniFile)) return;    
    if(mainIni){
      values=new TreeMap<String,String>();    
      iniFile=f;
    }
            
    if (!iniFile.exists()) {
      Announce.error("The initialisation file",
          iniFile.getCanonicalPath(),
          "was not found.");
    }
    for(String l : new FileLines(f)) {
      Matcher m=INIPATTERN.matcher(l);
      if(!m.matches()) continue;
      String s=m.group(2).trim();
      if(s.startsWith("\"")) s=s.substring(1);
      if(s.endsWith("\"")) s=s.substring(0,s.length()-1);      
      if(m.group(1).toLowerCase().equals("include")){
        if(s.startsWith("/"))
          init(s,false);
        else
          init((f.getParent()!=null?f.getParent()+"/":"")+s,false);        
      }
      else
        values.put(m.group(1).toLowerCase(),s);        
    }
  }
  
  /** Seeks the file in all given folders*/  
  public void init(String filename, File... folders) throws IOException {
    boolean found=false;
    for(File folder : folders) {
      if(new File(folder, filename).exists()) {
        if(found) 
          throw new IOException("INI-file "+filename+"occurs twice in given folders");
        init(new File(folder, filename));
        found = true;
      }
    }
  }

  
  /** Initializes the parameters from a file*/
  public void init(String filename)throws IOException{
    init(filename,true);
  }
  public void init(String file, boolean mainIni) throws IOException {
    Announce.message("Loading ini file '"+file+"'");
    init(new File(file),mainIni);    
  }
  
  
  /** Tells whether a parameter is defined */
  public boolean isDefined(String s) {
    if (values == null)
      throw new RuntimeException("Call init() before get()!");
    String pname = s.indexOf(' ') == -1 ? s : s
        .substring(0, s.indexOf(' '));
    return (values.containsKey(pname.toLowerCase()));
  }
  
  /** Reports an error message and aborts if the parameters are undefined.
   * p may contain strings of the form "parametername explanation"*/
  public void ensureParameters(String... p) {
    if (values == null)
      throw new RuntimeException("Call init() before ensureParameters()!");
    boolean OK = true;
    StringBuilder b = new StringBuilder(
        "The following parameters are undefined in ").append(iniFile);
    for (String s : p) {
      if (!isDefined(s)) {
        b.append("\n       ").append(s);
        OK = false;
      }
    }
    if (OK)
      return;
    Announce.error(b.toString());
  }

  /** Parses the arguments of the main method and tells whether a parameter is on or off */
  public boolean getBooleanArgument(String[] args,String... argnames) {
    String arg = " ";
    for (String s : args)
      arg += s + ' ';
    String p = "\\W(";
    for (String s : argnames)
      p += s + '|';
    if (p.endsWith("|"))
      p = p.substring(0, p.length() - 1);
    p += ")\\W";
    Matcher m = Pattern.compile(p).matcher(arg);
    if (!m.find())
      return (false);
    String next = arg.substring(m.end()).toLowerCase();
    if (next.indexOf(' ') != -1)
      next = next.substring(0, next.indexOf(' '));
    if (next.equals("off"))
      return (false);
    if (next.equals("0"))
      return (false);
    if (next.equals("false"))
      return (false);
    String previous = arg.substring(0, m.start()).toLowerCase();
    if (previous.indexOf(' ') != -1)
      previous = previous.substring(previous.lastIndexOf(' ') + 1);
    if (previous.equals("no"))
      return (false);
    return (true);
  }
  
  /** Deletes all current values*/
  public void reset() {
    iniFile=null;
    values=null;
  }
  
  /** Returns the database defined in this ini-file*/
  public Database getDatabase() throws Exception {
    ensureParameters("databaseSystem - either Oracle, Postgres or MySQL",
        "databaseUser - the user name for the database (also: databaseDatabase, databaseInst,databasePort,databaseHost,databaseSchema)",
        "databasePassword - the password for the database"
    );
        
    // Retrieve the obligatory parameters
    String system=this.get("databaseSystem").toUpperCase();
    String user=this.get("databaseUser");    
    String password=this.get("databasePassword");
    String host=null;
    String schema=null;
    String inst=null;
    String port=null;
    String database=null;

    
    // Retrieve the optional parameters
    try {
      host=this.get("databaseHost");
    } catch(Exception e){Announce.debug("Warning: "+e);};
    try {
      schema=this.get("databaseSchema");
    } catch(Exception e){Announce.debug("Warning: "+e);};
    try {
      port=this.get("databasePort");    
    } catch(Exception e){Announce.debug("Warning: "+e);};
    if(system.equals("ORACLE")){
      try {
        inst=this.get("databaseSID");
      } catch(UndefinedParameterException e) {Announce.debug("Warning: "+e);}
    }
    try {
      database=this.get("databaseDatabase");
    } catch(UndefinedParameterException e) {Announce.debug("Warning: "+e);}    
    
    // Initialize the database
    // ------ ORACLE ----------
    if(system.equals("ORACLE")) {
      return(new OracleDatabase(user,password,host,port,inst));
    }
    //  ------ MySQL----------
    if(system.equals("MYSQL")) {
      return(new MySQLDatabase(user,password,database,host,port));
    }
    //  ------ Postgres----------
    if(system.equals("POSTGRES")) {
      return(new PostgresDatabase(user,password,database,host,port,schema));
    }
    throw new RuntimeException("Unsupported database system "+system);        
  }
  
  
  /** Matches all parameters against a provided class attribute 
   * If a parameter name, transformed to upper-case, matches the attribute 
   * the value assigned to the parameter is returned as an object   
   * @param field  the attribute against which to match the parameters
   * @return  an object representing the value of a parameter that matches the given attribute
   *          iff there is a match, otherwise null
   */
  public Object matchObjectAttribut(Field field) throws IllegalAccessException{   
      String parameterName = field.getName();
      if (parameterName.equals(parameterName.toUpperCase()) && isDefined(parameterName)) {
        if (field.getType() == Integer.class || field.getType() == int.class) return getInteger(parameterName);
        else if (field.getType() == Boolean.class || field.getType() == boolean.class) return new Boolean(getBoolean(parameterName));
        else if (field.getType() == Float.class || field.getType() == float.class) return new Float(getFloat(parameterName));
        else if (D.indexOf(List.class, (Object[]) field.getType().getInterfaces()) != -1) return getList(parameterName);
        else if (D.indexOf(List.class, field.getType()) != -1) return getList(parameterName);
        else return get(parameterName);
      }
      return null;
  }  
  
  /** Returns all defined parameters*/
  public Set<String> parameters() {
    return(values.keySet());
  }
  
  /** Stores the parameters in a given file */
  public void saveAs(String file) throws IOException {
    Writer w = new FileWriter(file, false);
    for(Map.Entry<String, String> entry:values.entrySet())
      w.write(entry.getKey() + " = " + entry.getValue() + "\n");
    w.close();
  }
  
  /** Cloning implementation */
  @Override
  public NonsharedParameters clone(){
    try {
      NonsharedParameters other=(NonsharedParameters) super.clone();
      other.values=new TreeMap<String,String>();
      for (Map.Entry<String,String> entry:values.entrySet())
        other.values.put(entry.getKey(),entry.getValue());            
      return other;
    } catch (CloneNotSupportedException e) {
        throw new Error("Is too",e);
    }
  }

  
  
  /** Test routine */  
  public static void main(String[] args) throws Exception {    
    NonsharedParameters params= new NonsharedParameters();
    params.init("testdata/testconfig.ini");
    D.p(params.values);
    params.set("someSetting", "1");
    NonsharedParameters params2=params.clone();
    params2.set("someSetting", "2");
    D.p(params.values);
    D.p(params2.values);    
  }
}
