

/* First created by JCasGen Wed Oct 19 19:10:41 CEST 2011 */
package de.julielab.jules.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.StringArray;


/** References to other databases, e.g. SwissProt, INTERPRO e.g.
 * Updated by JCasGen Fri Dec 09 11:59:26 CET 2011
 * XML source: /Users/richarde/dev/bluebrain/svn_nlp/UIMA/blue_uima/trunk/julielab_typesystem-2.6.8/src/main/resources/typeSystem/julie-document-meta-types.xml
 * @generated */
public class DBInfo extends Annotation {
  /** @generated
   * @ordered 
   */
  public final static int typeIndexID = JCasRegistry.register(DBInfo.class);
  /** @generated
   * @ordered 
   */
  public final static int type = typeIndexID;
  /** @generated  */
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected DBInfo() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public DBInfo(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public DBInfo(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public DBInfo(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** <!-- begin-user-doc -->
    * Write your own initialization here
    * <!-- end-user-doc -->
  @generated modifiable */
  private void readObject() {}
     
 
    
  //*--------------*
  //* Feature: name

  /** getter for name - gets The name of the DB referred to
   * @generated */
  public String getName() {
    if (DBInfo_Type.featOkTst && ((DBInfo_Type)jcasType).casFeat_name == null)
      jcasType.jcas.throwFeatMissing("name", "de.julielab.jules.types.DBInfo");
    return jcasType.ll_cas.ll_getStringValue(addr, ((DBInfo_Type)jcasType).casFeatCode_name);}
    
  /** setter for name - sets The name of the DB referred to 
   * @generated */
  public void setName(String v) {
    if (DBInfo_Type.featOkTst && ((DBInfo_Type)jcasType).casFeat_name == null)
      jcasType.jcas.throwFeatMissing("name", "de.julielab.jules.types.DBInfo");
    jcasType.ll_cas.ll_setStringValue(addr, ((DBInfo_Type)jcasType).casFeatCode_name, v);}    
   
    
  //*--------------*
  //* Feature: acList

  /** getter for acList - gets A list of accession numbers for this DB, C
   * @generated */
  public StringArray getAcList() {
    if (DBInfo_Type.featOkTst && ((DBInfo_Type)jcasType).casFeat_acList == null)
      jcasType.jcas.throwFeatMissing("acList", "de.julielab.jules.types.DBInfo");
    return (StringArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((DBInfo_Type)jcasType).casFeatCode_acList)));}
    
  /** setter for acList - sets A list of accession numbers for this DB, C 
   * @generated */
  public void setAcList(StringArray v) {
    if (DBInfo_Type.featOkTst && ((DBInfo_Type)jcasType).casFeat_acList == null)
      jcasType.jcas.throwFeatMissing("acList", "de.julielab.jules.types.DBInfo");
    jcasType.ll_cas.ll_setRefValue(addr, ((DBInfo_Type)jcasType).casFeatCode_acList, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for acList - gets an indexed value - A list of accession numbers for this DB, C
   * @generated */
  public String getAcList(int i) {
    if (DBInfo_Type.featOkTst && ((DBInfo_Type)jcasType).casFeat_acList == null)
      jcasType.jcas.throwFeatMissing("acList", "de.julielab.jules.types.DBInfo");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((DBInfo_Type)jcasType).casFeatCode_acList), i);
    return jcasType.ll_cas.ll_getStringArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((DBInfo_Type)jcasType).casFeatCode_acList), i);}

  /** indexed setter for acList - sets an indexed value - A list of accession numbers for this DB, C
   * @generated */
  public void setAcList(int i, String v) { 
    if (DBInfo_Type.featOkTst && ((DBInfo_Type)jcasType).casFeat_acList == null)
      jcasType.jcas.throwFeatMissing("acList", "de.julielab.jules.types.DBInfo");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((DBInfo_Type)jcasType).casFeatCode_acList), i);
    jcasType.ll_cas.ll_setStringArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((DBInfo_Type)jcasType).casFeatCode_acList), i, v);}
  }

    