

/* First created by JCasGen Sat Mar 07 22:05:57 CET 2015 */
package de.julielab.jules.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.StringArray;


/** References to other databases, e.g. SwissProt, INTERPRO e.g.
 * Updated by JCasGen Sat Mar 07 22:05:57 CET 2015
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/target/jcasgen/typesystem.xml
 * @generated */
public class DBInfo extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(DBInfo.class);
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
  protected DBInfo() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public DBInfo(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public DBInfo(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public DBInfo(JCas jcas, int begin, int end) {
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
  //* Feature: name

  /** getter for name - gets The name of the DB referred to
   * @generated
   * @return value of the feature 
   */
  public String getName() {
    if (DBInfo_Type.featOkTst && ((DBInfo_Type)jcasType).casFeat_name == null)
      jcasType.jcas.throwFeatMissing("name", "de.julielab.jules.types.DBInfo");
    return jcasType.ll_cas.ll_getStringValue(addr, ((DBInfo_Type)jcasType).casFeatCode_name);}
    
  /** setter for name - sets The name of the DB referred to 
   * @generated
   * @param v value to set into the feature 
   */
  public void setName(String v) {
    if (DBInfo_Type.featOkTst && ((DBInfo_Type)jcasType).casFeat_name == null)
      jcasType.jcas.throwFeatMissing("name", "de.julielab.jules.types.DBInfo");
    jcasType.ll_cas.ll_setStringValue(addr, ((DBInfo_Type)jcasType).casFeatCode_name, v);}    
   
    
  //*--------------*
  //* Feature: acList

  /** getter for acList - gets A list of accession numbers for this DB, C
   * @generated
   * @return value of the feature 
   */
  public StringArray getAcList() {
    if (DBInfo_Type.featOkTst && ((DBInfo_Type)jcasType).casFeat_acList == null)
      jcasType.jcas.throwFeatMissing("acList", "de.julielab.jules.types.DBInfo");
    return (StringArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((DBInfo_Type)jcasType).casFeatCode_acList)));}
    
  /** setter for acList - sets A list of accession numbers for this DB, C 
   * @generated
   * @param v value to set into the feature 
   */
  public void setAcList(StringArray v) {
    if (DBInfo_Type.featOkTst && ((DBInfo_Type)jcasType).casFeat_acList == null)
      jcasType.jcas.throwFeatMissing("acList", "de.julielab.jules.types.DBInfo");
    jcasType.ll_cas.ll_setRefValue(addr, ((DBInfo_Type)jcasType).casFeatCode_acList, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for acList - gets an indexed value - A list of accession numbers for this DB, C
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public String getAcList(int i) {
    if (DBInfo_Type.featOkTst && ((DBInfo_Type)jcasType).casFeat_acList == null)
      jcasType.jcas.throwFeatMissing("acList", "de.julielab.jules.types.DBInfo");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((DBInfo_Type)jcasType).casFeatCode_acList), i);
    return jcasType.ll_cas.ll_getStringArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((DBInfo_Type)jcasType).casFeatCode_acList), i);}

  /** indexed setter for acList - sets an indexed value - A list of accession numbers for this DB, C
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setAcList(int i, String v) { 
    if (DBInfo_Type.featOkTst && ((DBInfo_Type)jcasType).casFeat_acList == null)
      jcasType.jcas.throwFeatMissing("acList", "de.julielab.jules.types.DBInfo");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((DBInfo_Type)jcasType).casFeatCode_acList), i);
    jcasType.ll_cas.ll_setStringArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((DBInfo_Type)jcasType).casFeatCode_acList), i, v);}
  }

    