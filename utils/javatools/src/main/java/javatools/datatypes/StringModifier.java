package javatools.datatypes;

import java.util.Collection;
import java.util.Iterator;

import javatools.database.Database;

/** 
This class is part of the Java Tools (see http://mpii.de/yago-naga/javatools).
It is licensed under the Creative Commons Attribution License 
(see http://creativecommons.org/licenses/by/3.0) by 
the YAGO-NAGA team (see http://mpii.de/yago-naga).
  

  
  This class is a collection of additional string modification methods.
   
  For instance, it provides methods to generate a string from an array, 
  separating array elements by a given deleminator; 
  
  String array[]={"cat", "mouse", "cheese"}; 
  String imploded=StringModifier.implode(array," eats ");
  System.out.println(imploded);  
  -> cat eats mouse eats cheese;
  
  imploded=StringModifier.implode(array,",");
  System.out.println(imploded);
  -> cat,mouse,cheese;
  
  This is helpfull for instance to generate a list of values 
  for database insertion; note that there is also a version
  of that functionality that makes sure the array strings 
  are all formated for database insertion/queries (e.g. put in
  quotations).
   
   
 */
public abstract class StringModifier {
  


  /* Concatenates the Strings contained in an array to a combined string, 
   * separating each two array Strings with the given delimeter */
  public static String implode(String[] array, String delim){
    if (array.length==0) {
      return "";
    } else {
      StringBuffer sb = new StringBuffer();
      sb.append(array[0]);
      for (int i=1;i<array.length;i++) {
        sb.append(delim);
        sb.append(array[i]);
      }
      return sb.toString();
    }
  }

  /* Concatenates the Strings contained in a collection to a combined string, 
   * separating each two array Strings with the given delimeter */
  public static String implode(Collection<?> col, String delim){
    if(col==null)
      return "";
    Iterator<?> it=col.iterator();
    if(!it.hasNext())
      return "";
    else{
      StringBuffer sb = new StringBuffer();
      sb.append(it.next());
      while (it.hasNext()){
        sb.append(delim);
        sb.append(it.next().toString());              
      }
      return sb.toString();
    }
  }  
  
  /* Concatenates the Strings contained in a collection to a combined string, 
   * separating each two array Strings with the given delimeter 
   * while applying the database.format function on each array string piece */
  public static String implodeForDB(Collection<?> col, String delim, Database database ){    
    return implodeForDB(col.iterator(),delim, database);
  }
  
  /* Concatenates the String pieces produced by an iterator to a combined string, 
   * separating each two array Strings with the given delimeter 
   * while applying the database.format function to each array string piece */
  public static String implodeForDB(Iterator<?> it, String delim, Database database ){
    if(it==null)
      return "";
    if(!it.hasNext())
      return "";
    else{
      StringBuffer sb = new StringBuffer();
      sb.append(database.format(it.next()));
      while (it.hasNext()){
        sb.append(delim);
        sb.append(database.format(it.next()));              
      }
      return sb.toString();
    }
  }
  
  /* Concatenates the String pieces contained in an array to a combined string, 
   * separating each two array values with the given delimeter 
   * while applying the database.format function to each array entry */
  public static <T> String implodeForDB(T[] col, String delim, Database database ){        
    if(col.length<=0)
      return "";
    else{
      StringBuffer sb = new StringBuffer();
      sb.append(database.format(col[0]));
      for (int i=1;i<col.length;i++){
        sb.append(delim);
        sb.append(database.format(col[i]));              
      }
      return sb.toString();
    }
  }

  
  
  /** limits the length of a String to the given size
   * ie applies s.substring(0,length) for given length iff
   * length<s.length() */
  public static String limitLength(String s, int length){
    if(s.length()>length)
      return s.substring(0,length);
    else return s;
  }
  
  /** Test method */
  public static void main(String[] argv) throws Exception {
    String array[]={"cat", "mouse", "cheese"}; 
    String imploded=StringModifier.implode(array," eats ");
    System.out.println(imploded);    
           
    imploded=StringModifier.implode(array,",");
    System.out.println(imploded);    
  }
     
  

}
