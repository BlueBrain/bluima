

/* First created by JCasGen Sat Mar 07 22:05:57 CET 2015 */
package de.julielab.jules.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** Type to store dates
 * Updated by JCasGen Sat Mar 07 22:05:57 CET 2015
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/target/jcasgen/typesystem.xml
 * @generated */
public class Date extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Date.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated
   * @return index of the type  
   */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected Date() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Date(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Date(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Date(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** 
   * <!-- begin-user-doc -->
   * Write your own initialization here
   * <!-- end-user-doc -->
   *
   * @generated modifiable 
   */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: day

  /** getter for day - gets day of the month, C
   * @generated
   * @return value of the feature 
   */
  public int getDay() {
    if (Date_Type.featOkTst && ((Date_Type)jcasType).casFeat_day == null)
      jcasType.jcas.throwFeatMissing("day", "de.julielab.jules.types.Date");
    return jcasType.ll_cas.ll_getIntValue(addr, ((Date_Type)jcasType).casFeatCode_day);}
    
  /** setter for day - sets day of the month, C 
   * @generated
   * @param v value to set into the feature 
   */
  public void setDay(int v) {
    if (Date_Type.featOkTst && ((Date_Type)jcasType).casFeat_day == null)
      jcasType.jcas.throwFeatMissing("day", "de.julielab.jules.types.Date");
    jcasType.ll_cas.ll_setIntValue(addr, ((Date_Type)jcasType).casFeatCode_day, v);}    
   
    
  //*--------------*
  //* Feature: month

  /** getter for month - gets month of the year, C
   * @generated
   * @return value of the feature 
   */
  public int getMonth() {
    if (Date_Type.featOkTst && ((Date_Type)jcasType).casFeat_month == null)
      jcasType.jcas.throwFeatMissing("month", "de.julielab.jules.types.Date");
    return jcasType.ll_cas.ll_getIntValue(addr, ((Date_Type)jcasType).casFeatCode_month);}
    
  /** setter for month - sets month of the year, C 
   * @generated
   * @param v value to set into the feature 
   */
  public void setMonth(int v) {
    if (Date_Type.featOkTst && ((Date_Type)jcasType).casFeat_month == null)
      jcasType.jcas.throwFeatMissing("month", "de.julielab.jules.types.Date");
    jcasType.ll_cas.ll_setIntValue(addr, ((Date_Type)jcasType).casFeatCode_month, v);}    
   
    
  //*--------------*
  //* Feature: year

  /** getter for year - gets full year (e.g. 2006 and NOT 06), C
   * @generated
   * @return value of the feature 
   */
  public int getYear() {
    if (Date_Type.featOkTst && ((Date_Type)jcasType).casFeat_year == null)
      jcasType.jcas.throwFeatMissing("year", "de.julielab.jules.types.Date");
    return jcasType.ll_cas.ll_getIntValue(addr, ((Date_Type)jcasType).casFeatCode_year);}
    
  /** setter for year - sets full year (e.g. 2006 and NOT 06), C 
   * @generated
   * @param v value to set into the feature 
   */
  public void setYear(int v) {
    if (Date_Type.featOkTst && ((Date_Type)jcasType).casFeat_year == null)
      jcasType.jcas.throwFeatMissing("year", "de.julielab.jules.types.Date");
    jcasType.ll_cas.ll_setIntValue(addr, ((Date_Type)jcasType).casFeatCode_year, v);}    
  }

    