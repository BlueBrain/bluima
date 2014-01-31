

/* First created by JCasGen Fri Sep 14 13:58:35 CEST 2012 */
package ch.epfl.bbp.uima.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** 
 * Updated by JCasGen Mon Oct 21 13:03:29 CEST 2013
 * XML source: /Users/richarde/dev/bluebrain/svn_nlp/UIMA/blue_uima/trunk/modules/julielab_typesystem-2.6.8/src/main/resources/typeSystem/bbp-semantics-biology-types.xml
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
  /** @generated  */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected DictTerm() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public DictTerm(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public DictTerm(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public DictTerm(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** <!-- begin-user-doc -->
    * Write your own initialization here
    * <!-- end-user-doc -->
  @generated modifiable */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: entityId

  /** getter for entityId - gets 
   * @generated */
  public String getEntityId() {
    if (DictTerm_Type.featOkTst && ((DictTerm_Type)jcasType).casFeat_entityId == null)
      jcasType.jcas.throwFeatMissing("entityId", "ch.epfl.bbp.uima.types.DictTerm");
    return jcasType.ll_cas.ll_getStringValue(addr, ((DictTerm_Type)jcasType).casFeatCode_entityId);}
    
  /** setter for entityId - sets  
   * @generated */
  public void setEntityId(String v) {
    if (DictTerm_Type.featOkTst && ((DictTerm_Type)jcasType).casFeat_entityId == null)
      jcasType.jcas.throwFeatMissing("entityId", "ch.epfl.bbp.uima.types.DictTerm");
    jcasType.ll_cas.ll_setStringValue(addr, ((DictTerm_Type)jcasType).casFeatCode_entityId, v);}    
   
    
  //*--------------*
  //* Feature: annotType

  /** getter for annotType - gets 
   * @generated */
  public String getAnnotType() {
    if (DictTerm_Type.featOkTst && ((DictTerm_Type)jcasType).casFeat_annotType == null)
      jcasType.jcas.throwFeatMissing("annotType", "ch.epfl.bbp.uima.types.DictTerm");
    return jcasType.ll_cas.ll_getStringValue(addr, ((DictTerm_Type)jcasType).casFeatCode_annotType);}
    
  /** setter for annotType - sets  
   * @generated */
  public void setAnnotType(String v) {
    if (DictTerm_Type.featOkTst && ((DictTerm_Type)jcasType).casFeat_annotType == null)
      jcasType.jcas.throwFeatMissing("annotType", "ch.epfl.bbp.uima.types.DictTerm");
    jcasType.ll_cas.ll_setStringValue(addr, ((DictTerm_Type)jcasType).casFeatCode_annotType, v);}    
  }

    