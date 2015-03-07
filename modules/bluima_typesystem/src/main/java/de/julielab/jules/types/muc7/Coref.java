

/* First created by JCasGen Sat Mar 07 22:05:57 CET 2015 */
package de.julielab.jules.types.muc7;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Sat Mar 07 22:05:57 CET 2015
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/target/jcasgen/typesystem.xml
 * @generated */
public class Coref extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Coref.class);
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
  protected Coref() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Coref(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Coref(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Coref(JCas jcas, int begin, int end) {
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
  //* Feature: id

  /** getter for id - gets 
   * @generated
   * @return value of the feature 
   */
  public int getId() {
    if (Coref_Type.featOkTst && ((Coref_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "de.julielab.jules.types.muc7.Coref");
    return jcasType.ll_cas.ll_getIntValue(addr, ((Coref_Type)jcasType).casFeatCode_id);}
    
  /** setter for id - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setId(int v) {
    if (Coref_Type.featOkTst && ((Coref_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "de.julielab.jules.types.muc7.Coref");
    jcasType.ll_cas.ll_setIntValue(addr, ((Coref_Type)jcasType).casFeatCode_id, v);}    
   
    
  //*--------------*
  //* Feature: corefType

  /** getter for corefType - gets 
   * @generated
   * @return value of the feature 
   */
  public String getCorefType() {
    if (Coref_Type.featOkTst && ((Coref_Type)jcasType).casFeat_corefType == null)
      jcasType.jcas.throwFeatMissing("corefType", "de.julielab.jules.types.muc7.Coref");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Coref_Type)jcasType).casFeatCode_corefType);}
    
  /** setter for corefType - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setCorefType(String v) {
    if (Coref_Type.featOkTst && ((Coref_Type)jcasType).casFeat_corefType == null)
      jcasType.jcas.throwFeatMissing("corefType", "de.julielab.jules.types.muc7.Coref");
    jcasType.ll_cas.ll_setStringValue(addr, ((Coref_Type)jcasType).casFeatCode_corefType, v);}    
   
    
  //*--------------*
  //* Feature: ref

  /** getter for ref - gets 
   * @generated
   * @return value of the feature 
   */
  public Coref getRef() {
    if (Coref_Type.featOkTst && ((Coref_Type)jcasType).casFeat_ref == null)
      jcasType.jcas.throwFeatMissing("ref", "de.julielab.jules.types.muc7.Coref");
    return (Coref)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Coref_Type)jcasType).casFeatCode_ref)));}
    
  /** setter for ref - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setRef(Coref v) {
    if (Coref_Type.featOkTst && ((Coref_Type)jcasType).casFeat_ref == null)
      jcasType.jcas.throwFeatMissing("ref", "de.julielab.jules.types.muc7.Coref");
    jcasType.ll_cas.ll_setRefValue(addr, ((Coref_Type)jcasType).casFeatCode_ref, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: min

  /** getter for min - gets 
   * @generated
   * @return value of the feature 
   */
  public String getMin() {
    if (Coref_Type.featOkTst && ((Coref_Type)jcasType).casFeat_min == null)
      jcasType.jcas.throwFeatMissing("min", "de.julielab.jules.types.muc7.Coref");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Coref_Type)jcasType).casFeatCode_min);}
    
  /** setter for min - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setMin(String v) {
    if (Coref_Type.featOkTst && ((Coref_Type)jcasType).casFeat_min == null)
      jcasType.jcas.throwFeatMissing("min", "de.julielab.jules.types.muc7.Coref");
    jcasType.ll_cas.ll_setStringValue(addr, ((Coref_Type)jcasType).casFeatCode_min, v);}    
  }

    