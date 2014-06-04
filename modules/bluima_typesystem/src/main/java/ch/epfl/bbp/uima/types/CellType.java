

/* First created by JCasGen Mon Feb 17 12:26:53 CET 2014 */
package ch.epfl.bbp.uima.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Wed Jun 04 18:01:55 CEST 2014
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/src/main/resources/typeSystem/bbp-types.xml
 * @generated */
public class CellType extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(CellType.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated  */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected CellType() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public CellType(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public CellType(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public CellType(JCas jcas, int begin, int end) {
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

  /** getter for name - gets 
   * @generated
   * @return value of the feature 
   */
  public String getName() {
    if (CellType_Type.featOkTst && ((CellType_Type)jcasType).casFeat_name == null)
      jcasType.jcas.throwFeatMissing("name", "ch.epfl.bbp.uima.types.CellType");
    return jcasType.ll_cas.ll_getStringValue(addr, ((CellType_Type)jcasType).casFeatCode_name);}
    
  /** setter for name - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setName(String v) {
    if (CellType_Type.featOkTst && ((CellType_Type)jcasType).casFeat_name == null)
      jcasType.jcas.throwFeatMissing("name", "ch.epfl.bbp.uima.types.CellType");
    jcasType.ll_cas.ll_setStringValue(addr, ((CellType_Type)jcasType).casFeatCode_name, v);}    
   
    
  //*--------------*
  //* Feature: id

  /** getter for id - gets 
   * @generated
   * @return value of the feature 
   */
  public String getId() {
    if (CellType_Type.featOkTst && ((CellType_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "ch.epfl.bbp.uima.types.CellType");
    return jcasType.ll_cas.ll_getStringValue(addr, ((CellType_Type)jcasType).casFeatCode_id);}
    
  /** setter for id - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setId(String v) {
    if (CellType_Type.featOkTst && ((CellType_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "ch.epfl.bbp.uima.types.CellType");
    jcasType.ll_cas.ll_setStringValue(addr, ((CellType_Type)jcasType).casFeatCode_id, v);}    
  }

    