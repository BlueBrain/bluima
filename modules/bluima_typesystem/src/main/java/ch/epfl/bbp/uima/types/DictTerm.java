

/* First created by JCasGen Sat Mar 07 22:05:56 CET 2015 */
package ch.epfl.bbp.uima.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** 
 * Updated by JCasGen Sat Mar 07 22:05:56 CET 2015
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/target/jcasgen/typesystem.xml
 * @generated */
public class DictTerm extends org.apache.uima.conceptMapper.DictTerm {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(DictTerm.class);
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
  protected DictTerm() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public DictTerm(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public DictTerm(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public DictTerm(JCas jcas, int begin, int end) {
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
  //* Feature: entityId

  /** getter for entityId - gets 
   * @generated
   * @return value of the feature 
   */
  public String getEntityId() {
    if (DictTerm_Type.featOkTst && ((DictTerm_Type)jcasType).casFeat_entityId == null)
      jcasType.jcas.throwFeatMissing("entityId", "ch.epfl.bbp.uima.types.DictTerm");
    return jcasType.ll_cas.ll_getStringValue(addr, ((DictTerm_Type)jcasType).casFeatCode_entityId);}
    
  /** setter for entityId - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setEntityId(String v) {
    if (DictTerm_Type.featOkTst && ((DictTerm_Type)jcasType).casFeat_entityId == null)
      jcasType.jcas.throwFeatMissing("entityId", "ch.epfl.bbp.uima.types.DictTerm");
    jcasType.ll_cas.ll_setStringValue(addr, ((DictTerm_Type)jcasType).casFeatCode_entityId, v);}    
   
    
  //*--------------*
  //* Feature: annotType

  /** getter for annotType - gets 
   * @generated
   * @return value of the feature 
   */
  public String getAnnotType() {
    if (DictTerm_Type.featOkTst && ((DictTerm_Type)jcasType).casFeat_annotType == null)
      jcasType.jcas.throwFeatMissing("annotType", "ch.epfl.bbp.uima.types.DictTerm");
    return jcasType.ll_cas.ll_getStringValue(addr, ((DictTerm_Type)jcasType).casFeatCode_annotType);}
    
  /** setter for annotType - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setAnnotType(String v) {
    if (DictTerm_Type.featOkTst && ((DictTerm_Type)jcasType).casFeat_annotType == null)
      jcasType.jcas.throwFeatMissing("annotType", "ch.epfl.bbp.uima.types.DictTerm");
    jcasType.ll_cas.ll_setStringValue(addr, ((DictTerm_Type)jcasType).casFeatCode_annotType, v);}    
  }

    