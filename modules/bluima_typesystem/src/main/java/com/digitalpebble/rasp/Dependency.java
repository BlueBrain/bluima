

/* First created by JCasGen Sun May 27 17:27:29 CEST 2012 */
package com.digitalpebble.rasp;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** A dependency between two word forms
 * Updated by JCasGen Sun May 27 17:27:29 CEST 2012
 * XML source: /Users/richarde/dev/bluebrain/svn_nlp/UIMA/blue_uima/trunk/modules/julielab_typesystem-2.6.8/src/main/resources/typeSystem/RASPTypes.xml
 * @generated */
public class Dependency extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Dependency.class);
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
  protected Dependency() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public Dependency(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public Dependency(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public Dependency(JCas jcas, int begin, int end) {
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
  //* Feature: deptype

  /** getter for deptype - gets 
   * @generated */
  public String getDeptype() {
    if (Dependency_Type.featOkTst && ((Dependency_Type)jcasType).casFeat_deptype == null)
      jcasType.jcas.throwFeatMissing("deptype", "com.digitalpebble.rasp.Dependency");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Dependency_Type)jcasType).casFeatCode_deptype);}
    
  /** setter for deptype - sets  
   * @generated */
  public void setDeptype(String v) {
    if (Dependency_Type.featOkTst && ((Dependency_Type)jcasType).casFeat_deptype == null)
      jcasType.jcas.throwFeatMissing("deptype", "com.digitalpebble.rasp.Dependency");
    jcasType.ll_cas.ll_setStringValue(addr, ((Dependency_Type)jcasType).casFeatCode_deptype, v);}    
   
    
  //*--------------*
  //* Feature: subtype

  /** getter for subtype - gets 
   * @generated */
  public String getSubtype() {
    if (Dependency_Type.featOkTst && ((Dependency_Type)jcasType).casFeat_subtype == null)
      jcasType.jcas.throwFeatMissing("subtype", "com.digitalpebble.rasp.Dependency");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Dependency_Type)jcasType).casFeatCode_subtype);}
    
  /** setter for subtype - sets  
   * @generated */
  public void setSubtype(String v) {
    if (Dependency_Type.featOkTst && ((Dependency_Type)jcasType).casFeat_subtype == null)
      jcasType.jcas.throwFeatMissing("subtype", "com.digitalpebble.rasp.Dependency");
    jcasType.ll_cas.ll_setStringValue(addr, ((Dependency_Type)jcasType).casFeatCode_subtype, v);}    
   
    
  //*--------------*
  //* Feature: head

  /** getter for head - gets 
   * @generated */
  public WordForm getHead() {
    if (Dependency_Type.featOkTst && ((Dependency_Type)jcasType).casFeat_head == null)
      jcasType.jcas.throwFeatMissing("head", "com.digitalpebble.rasp.Dependency");
    return (WordForm)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Dependency_Type)jcasType).casFeatCode_head)));}
    
  /** setter for head - sets  
   * @generated */
  public void setHead(WordForm v) {
    if (Dependency_Type.featOkTst && ((Dependency_Type)jcasType).casFeat_head == null)
      jcasType.jcas.throwFeatMissing("head", "com.digitalpebble.rasp.Dependency");
    jcasType.ll_cas.ll_setRefValue(addr, ((Dependency_Type)jcasType).casFeatCode_head, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: dep

  /** getter for dep - gets 
   * @generated */
  public WordForm getDep() {
    if (Dependency_Type.featOkTst && ((Dependency_Type)jcasType).casFeat_dep == null)
      jcasType.jcas.throwFeatMissing("dep", "com.digitalpebble.rasp.Dependency");
    return (WordForm)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Dependency_Type)jcasType).casFeatCode_dep)));}
    
  /** setter for dep - sets  
   * @generated */
  public void setDep(WordForm v) {
    if (Dependency_Type.featOkTst && ((Dependency_Type)jcasType).casFeat_dep == null)
      jcasType.jcas.throwFeatMissing("dep", "com.digitalpebble.rasp.Dependency");
    jcasType.ll_cas.ll_setRefValue(addr, ((Dependency_Type)jcasType).casFeatCode_dep, jcasType.ll_cas.ll_getFSRef(v));}    
  }

    