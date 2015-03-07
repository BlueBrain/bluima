

/* First created by JCasGen Sat Mar 07 22:05:56 CET 2015 */
package com.digitalpebble.rasp;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** A dependency between two word forms
 * Updated by JCasGen Sat Mar 07 22:05:56 CET 2015
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/target/jcasgen/typesystem.xml
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
  /** @generated
   * @return index of the type  
   */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected Dependency() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Dependency(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Dependency(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Dependency(JCas jcas, int begin, int end) {
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
  //* Feature: deptype

  /** getter for deptype - gets 
   * @generated
   * @return value of the feature 
   */
  public String getDeptype() {
    if (Dependency_Type.featOkTst && ((Dependency_Type)jcasType).casFeat_deptype == null)
      jcasType.jcas.throwFeatMissing("deptype", "com.digitalpebble.rasp.Dependency");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Dependency_Type)jcasType).casFeatCode_deptype);}
    
  /** setter for deptype - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setDeptype(String v) {
    if (Dependency_Type.featOkTst && ((Dependency_Type)jcasType).casFeat_deptype == null)
      jcasType.jcas.throwFeatMissing("deptype", "com.digitalpebble.rasp.Dependency");
    jcasType.ll_cas.ll_setStringValue(addr, ((Dependency_Type)jcasType).casFeatCode_deptype, v);}    
   
    
  //*--------------*
  //* Feature: subtype

  /** getter for subtype - gets 
   * @generated
   * @return value of the feature 
   */
  public String getSubtype() {
    if (Dependency_Type.featOkTst && ((Dependency_Type)jcasType).casFeat_subtype == null)
      jcasType.jcas.throwFeatMissing("subtype", "com.digitalpebble.rasp.Dependency");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Dependency_Type)jcasType).casFeatCode_subtype);}
    
  /** setter for subtype - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setSubtype(String v) {
    if (Dependency_Type.featOkTst && ((Dependency_Type)jcasType).casFeat_subtype == null)
      jcasType.jcas.throwFeatMissing("subtype", "com.digitalpebble.rasp.Dependency");
    jcasType.ll_cas.ll_setStringValue(addr, ((Dependency_Type)jcasType).casFeatCode_subtype, v);}    
   
    
  //*--------------*
  //* Feature: head

  /** getter for head - gets 
   * @generated
   * @return value of the feature 
   */
  public WordForm getHead() {
    if (Dependency_Type.featOkTst && ((Dependency_Type)jcasType).casFeat_head == null)
      jcasType.jcas.throwFeatMissing("head", "com.digitalpebble.rasp.Dependency");
    return (WordForm)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Dependency_Type)jcasType).casFeatCode_head)));}
    
  /** setter for head - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setHead(WordForm v) {
    if (Dependency_Type.featOkTst && ((Dependency_Type)jcasType).casFeat_head == null)
      jcasType.jcas.throwFeatMissing("head", "com.digitalpebble.rasp.Dependency");
    jcasType.ll_cas.ll_setRefValue(addr, ((Dependency_Type)jcasType).casFeatCode_head, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: dep

  /** getter for dep - gets 
   * @generated
   * @return value of the feature 
   */
  public WordForm getDep() {
    if (Dependency_Type.featOkTst && ((Dependency_Type)jcasType).casFeat_dep == null)
      jcasType.jcas.throwFeatMissing("dep", "com.digitalpebble.rasp.Dependency");
    return (WordForm)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Dependency_Type)jcasType).casFeatCode_dep)));}
    
  /** setter for dep - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setDep(WordForm v) {
    if (Dependency_Type.featOkTst && ((Dependency_Type)jcasType).casFeat_dep == null)
      jcasType.jcas.throwFeatMissing("dep", "com.digitalpebble.rasp.Dependency");
    jcasType.ll_cas.ll_setRefValue(addr, ((Dependency_Type)jcasType).casFeatCode_dep, jcasType.ll_cas.ll_getFSRef(v));}    
  }

    