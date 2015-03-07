

/* First created by JCasGen Sat Mar 07 22:05:56 CET 2015 */
package com.digitalpebble.rasp;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.FSArray;
import org.apache.uima.jcas.tcas.Annotation;


/** A clause as returned by the RASP analyser. It can contain one or more word forms or clauses
 * Updated by JCasGen Sat Mar 07 22:05:56 CET 2015
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/target/jcasgen/typesystem.xml
 * @generated */
public class Clause extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Clause.class);
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
  protected Clause() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Clause(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Clause(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Clause(JCas jcas, int begin, int end) {
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
  //* Feature: rule

  /** getter for rule - gets 
   * @generated
   * @return value of the feature 
   */
  public String getRule() {
    if (Clause_Type.featOkTst && ((Clause_Type)jcasType).casFeat_rule == null)
      jcasType.jcas.throwFeatMissing("rule", "com.digitalpebble.rasp.Clause");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Clause_Type)jcasType).casFeatCode_rule);}
    
  /** setter for rule - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setRule(String v) {
    if (Clause_Type.featOkTst && ((Clause_Type)jcasType).casFeat_rule == null)
      jcasType.jcas.throwFeatMissing("rule", "com.digitalpebble.rasp.Clause");
    jcasType.ll_cas.ll_setStringValue(addr, ((Clause_Type)jcasType).casFeatCode_rule, v);}    
   
    
  //*--------------*
  //* Feature: subclauses

  /** getter for subclauses - gets array of subelements. contains WordForms or Clauses
   * @generated
   * @return value of the feature 
   */
  public FSArray getSubclauses() {
    if (Clause_Type.featOkTst && ((Clause_Type)jcasType).casFeat_subclauses == null)
      jcasType.jcas.throwFeatMissing("subclauses", "com.digitalpebble.rasp.Clause");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Clause_Type)jcasType).casFeatCode_subclauses)));}
    
  /** setter for subclauses - sets array of subelements. contains WordForms or Clauses 
   * @generated
   * @param v value to set into the feature 
   */
  public void setSubclauses(FSArray v) {
    if (Clause_Type.featOkTst && ((Clause_Type)jcasType).casFeat_subclauses == null)
      jcasType.jcas.throwFeatMissing("subclauses", "com.digitalpebble.rasp.Clause");
    jcasType.ll_cas.ll_setRefValue(addr, ((Clause_Type)jcasType).casFeatCode_subclauses, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for subclauses - gets an indexed value - array of subelements. contains WordForms or Clauses
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public Annotation getSubclauses(int i) {
    if (Clause_Type.featOkTst && ((Clause_Type)jcasType).casFeat_subclauses == null)
      jcasType.jcas.throwFeatMissing("subclauses", "com.digitalpebble.rasp.Clause");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Clause_Type)jcasType).casFeatCode_subclauses), i);
    return (Annotation)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Clause_Type)jcasType).casFeatCode_subclauses), i)));}

  /** indexed setter for subclauses - sets an indexed value - array of subelements. contains WordForms or Clauses
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setSubclauses(int i, Annotation v) { 
    if (Clause_Type.featOkTst && ((Clause_Type)jcasType).casFeat_subclauses == null)
      jcasType.jcas.throwFeatMissing("subclauses", "com.digitalpebble.rasp.Clause");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Clause_Type)jcasType).casFeatCode_subclauses), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Clause_Type)jcasType).casFeatCode_subclauses), i, jcasType.ll_cas.ll_getFSRef(v));}
  }

    