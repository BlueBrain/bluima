

/* First created by JCasGen Sat Mar 07 22:05:56 CET 2015 */
package ch.epfl.bbp.uima.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.jcas.cas.StringArray;


/** A table that contains data, extracted from a pdf article
 * Updated by JCasGen Sat Mar 07 22:05:56 CET 2015
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/target/jcasgen/typesystem.xml
 * @generated */
public class DataTable extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(DataTable.class);
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
  protected DataTable() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public DataTable(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public DataTable(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public DataTable(JCas jcas, int begin, int end) {
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
  //* Feature: tableId

  /** getter for tableId - gets the id of this table in the document
   * @generated
   * @return value of the feature 
   */
  public int getTableId() {
    if (DataTable_Type.featOkTst && ((DataTable_Type)jcasType).casFeat_tableId == null)
      jcasType.jcas.throwFeatMissing("tableId", "ch.epfl.bbp.uima.types.DataTable");
    return jcasType.ll_cas.ll_getIntValue(addr, ((DataTable_Type)jcasType).casFeatCode_tableId);}
    
  /** setter for tableId - sets the id of this table in the document 
   * @generated
   * @param v value to set into the feature 
   */
  public void setTableId(int v) {
    if (DataTable_Type.featOkTst && ((DataTable_Type)jcasType).casFeat_tableId == null)
      jcasType.jcas.throwFeatMissing("tableId", "ch.epfl.bbp.uima.types.DataTable");
    jcasType.ll_cas.ll_setIntValue(addr, ((DataTable_Type)jcasType).casFeatCode_tableId, v);}    
   
    
  //*--------------*
  //* Feature: rowCount

  /** getter for rowCount - gets the nr of rows
   * @generated
   * @return value of the feature 
   */
  public int getRowCount() {
    if (DataTable_Type.featOkTst && ((DataTable_Type)jcasType).casFeat_rowCount == null)
      jcasType.jcas.throwFeatMissing("rowCount", "ch.epfl.bbp.uima.types.DataTable");
    return jcasType.ll_cas.ll_getIntValue(addr, ((DataTable_Type)jcasType).casFeatCode_rowCount);}
    
  /** setter for rowCount - sets the nr of rows 
   * @generated
   * @param v value to set into the feature 
   */
  public void setRowCount(int v) {
    if (DataTable_Type.featOkTst && ((DataTable_Type)jcasType).casFeat_rowCount == null)
      jcasType.jcas.throwFeatMissing("rowCount", "ch.epfl.bbp.uima.types.DataTable");
    jcasType.ll_cas.ll_setIntValue(addr, ((DataTable_Type)jcasType).casFeatCode_rowCount, v);}    
   
    
  //*--------------*
  //* Feature: columnCount

  /** getter for columnCount - gets 
   * @generated
   * @return value of the feature 
   */
  public int getColumnCount() {
    if (DataTable_Type.featOkTst && ((DataTable_Type)jcasType).casFeat_columnCount == null)
      jcasType.jcas.throwFeatMissing("columnCount", "ch.epfl.bbp.uima.types.DataTable");
    return jcasType.ll_cas.ll_getIntValue(addr, ((DataTable_Type)jcasType).casFeatCode_columnCount);}
    
  /** setter for columnCount - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setColumnCount(int v) {
    if (DataTable_Type.featOkTst && ((DataTable_Type)jcasType).casFeat_columnCount == null)
      jcasType.jcas.throwFeatMissing("columnCount", "ch.epfl.bbp.uima.types.DataTable");
    jcasType.ll_cas.ll_setIntValue(addr, ((DataTable_Type)jcasType).casFeatCode_columnCount, v);}    
   
    
  //*--------------*
  //* Feature: caption

  /** getter for caption - gets 
   * @generated
   * @return value of the feature 
   */
  public String getCaption() {
    if (DataTable_Type.featOkTst && ((DataTable_Type)jcasType).casFeat_caption == null)
      jcasType.jcas.throwFeatMissing("caption", "ch.epfl.bbp.uima.types.DataTable");
    return jcasType.ll_cas.ll_getStringValue(addr, ((DataTable_Type)jcasType).casFeatCode_caption);}
    
  /** setter for caption - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setCaption(String v) {
    if (DataTable_Type.featOkTst && ((DataTable_Type)jcasType).casFeat_caption == null)
      jcasType.jcas.throwFeatMissing("caption", "ch.epfl.bbp.uima.types.DataTable");
    jcasType.ll_cas.ll_setStringValue(addr, ((DataTable_Type)jcasType).casFeatCode_caption, v);}    
   
    
  //*--------------*
  //* Feature: headings

  /** getter for headings - gets 
   * @generated
   * @return value of the feature 
   */
  public StringArray getHeadings() {
    if (DataTable_Type.featOkTst && ((DataTable_Type)jcasType).casFeat_headings == null)
      jcasType.jcas.throwFeatMissing("headings", "ch.epfl.bbp.uima.types.DataTable");
    return (StringArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((DataTable_Type)jcasType).casFeatCode_headings)));}
    
  /** setter for headings - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setHeadings(StringArray v) {
    if (DataTable_Type.featOkTst && ((DataTable_Type)jcasType).casFeat_headings == null)
      jcasType.jcas.throwFeatMissing("headings", "ch.epfl.bbp.uima.types.DataTable");
    jcasType.ll_cas.ll_setRefValue(addr, ((DataTable_Type)jcasType).casFeatCode_headings, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for headings - gets an indexed value - 
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public String getHeadings(int i) {
    if (DataTable_Type.featOkTst && ((DataTable_Type)jcasType).casFeat_headings == null)
      jcasType.jcas.throwFeatMissing("headings", "ch.epfl.bbp.uima.types.DataTable");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((DataTable_Type)jcasType).casFeatCode_headings), i);
    return jcasType.ll_cas.ll_getStringArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((DataTable_Type)jcasType).casFeatCode_headings), i);}

  /** indexed setter for headings - sets an indexed value - 
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setHeadings(int i, String v) { 
    if (DataTable_Type.featOkTst && ((DataTable_Type)jcasType).casFeat_headings == null)
      jcasType.jcas.throwFeatMissing("headings", "ch.epfl.bbp.uima.types.DataTable");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((DataTable_Type)jcasType).casFeatCode_headings), i);
    jcasType.ll_cas.ll_setStringArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((DataTable_Type)jcasType).casFeatCode_headings), i, v);}
   
    
  //*--------------*
  //* Feature: body

  /** getter for body - gets the body of the table that contains data
   * @generated
   * @return value of the feature 
   */
  public StringArray getBody() {
    if (DataTable_Type.featOkTst && ((DataTable_Type)jcasType).casFeat_body == null)
      jcasType.jcas.throwFeatMissing("body", "ch.epfl.bbp.uima.types.DataTable");
    return (StringArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((DataTable_Type)jcasType).casFeatCode_body)));}
    
  /** setter for body - sets the body of the table that contains data 
   * @generated
   * @param v value to set into the feature 
   */
  public void setBody(StringArray v) {
    if (DataTable_Type.featOkTst && ((DataTable_Type)jcasType).casFeat_body == null)
      jcasType.jcas.throwFeatMissing("body", "ch.epfl.bbp.uima.types.DataTable");
    jcasType.ll_cas.ll_setRefValue(addr, ((DataTable_Type)jcasType).casFeatCode_body, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for body - gets an indexed value - the body of the table that contains data
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public String getBody(int i) {
    if (DataTable_Type.featOkTst && ((DataTable_Type)jcasType).casFeat_body == null)
      jcasType.jcas.throwFeatMissing("body", "ch.epfl.bbp.uima.types.DataTable");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((DataTable_Type)jcasType).casFeatCode_body), i);
    return jcasType.ll_cas.ll_getStringArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((DataTable_Type)jcasType).casFeatCode_body), i);}

  /** indexed setter for body - sets an indexed value - the body of the table that contains data
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setBody(int i, String v) { 
    if (DataTable_Type.featOkTst && ((DataTable_Type)jcasType).casFeat_body == null)
      jcasType.jcas.throwFeatMissing("body", "ch.epfl.bbp.uima.types.DataTable");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((DataTable_Type)jcasType).casFeatCode_body), i);
    jcasType.ll_cas.ll_setStringArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((DataTable_Type)jcasType).casFeatCode_body), i, v);}
   
    
  //*--------------*
  //* Feature: referenceText

  /** getter for referenceText - gets 
   * @generated
   * @return value of the feature 
   */
  public String getReferenceText() {
    if (DataTable_Type.featOkTst && ((DataTable_Type)jcasType).casFeat_referenceText == null)
      jcasType.jcas.throwFeatMissing("referenceText", "ch.epfl.bbp.uima.types.DataTable");
    return jcasType.ll_cas.ll_getStringValue(addr, ((DataTable_Type)jcasType).casFeatCode_referenceText);}
    
  /** setter for referenceText - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setReferenceText(String v) {
    if (DataTable_Type.featOkTst && ((DataTable_Type)jcasType).casFeat_referenceText == null)
      jcasType.jcas.throwFeatMissing("referenceText", "ch.epfl.bbp.uima.types.DataTable");
    jcasType.ll_cas.ll_setStringValue(addr, ((DataTable_Type)jcasType).casFeatCode_referenceText, v);}    
   
    
  //*--------------*
  //* Feature: pageNumber

  /** getter for pageNumber - gets the number of the page where the table is located in
   * @generated
   * @return value of the feature 
   */
  public int getPageNumber() {
    if (DataTable_Type.featOkTst && ((DataTable_Type)jcasType).casFeat_pageNumber == null)
      jcasType.jcas.throwFeatMissing("pageNumber", "ch.epfl.bbp.uima.types.DataTable");
    return jcasType.ll_cas.ll_getIntValue(addr, ((DataTable_Type)jcasType).casFeatCode_pageNumber);}
    
  /** setter for pageNumber - sets the number of the page where the table is located in 
   * @generated
   * @param v value to set into the feature 
   */
  public void setPageNumber(int v) {
    if (DataTable_Type.featOkTst && ((DataTable_Type)jcasType).casFeat_pageNumber == null)
      jcasType.jcas.throwFeatMissing("pageNumber", "ch.epfl.bbp.uima.types.DataTable");
    jcasType.ll_cas.ll_setIntValue(addr, ((DataTable_Type)jcasType).casFeatCode_pageNumber, v);}    
  }

    