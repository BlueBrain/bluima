

/* First created by JCasGen Sat Mar 07 22:05:57 CET 2015 */
package com.digitalpebble.rasp;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** A WordForm consists of a POS tag, a lemma and possibly a probability. There is one or more WordForm per Token (as in the MAF ISO Norm)
 * Updated by JCasGen Sat Mar 07 22:05:57 CET 2015
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/target/jcasgen/typesystem.xml
 * @generated */
public class WordForm extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(WordForm.class);
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
  protected WordForm() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public WordForm(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public WordForm(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public WordForm(JCas jcas, int begin, int end) {
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
  //* Feature: lemma

  /** getter for lemma - gets lemma of the Form
   * @generated
   * @return value of the feature 
   */
  public String getLemma() {
    if (WordForm_Type.featOkTst && ((WordForm_Type)jcasType).casFeat_lemma == null)
      jcasType.jcas.throwFeatMissing("lemma", "com.digitalpebble.rasp.WordForm");
    return jcasType.ll_cas.ll_getStringValue(addr, ((WordForm_Type)jcasType).casFeatCode_lemma);}
    
  /** setter for lemma - sets lemma of the Form 
   * @generated
   * @param v value to set into the feature 
   */
  public void setLemma(String v) {
    if (WordForm_Type.featOkTst && ((WordForm_Type)jcasType).casFeat_lemma == null)
      jcasType.jcas.throwFeatMissing("lemma", "com.digitalpebble.rasp.WordForm");
    jcasType.ll_cas.ll_setStringValue(addr, ((WordForm_Type)jcasType).casFeatCode_lemma, v);}    
   
    
  //*--------------*
  //* Feature: POS

  /** getter for POS - gets POS tag for a given form
   * @generated
   * @return value of the feature 
   */
  public String getPOS() {
    if (WordForm_Type.featOkTst && ((WordForm_Type)jcasType).casFeat_POS == null)
      jcasType.jcas.throwFeatMissing("POS", "com.digitalpebble.rasp.WordForm");
    return jcasType.ll_cas.ll_getStringValue(addr, ((WordForm_Type)jcasType).casFeatCode_POS);}
    
  /** setter for POS - sets POS tag for a given form 
   * @generated
   * @param v value to set into the feature 
   */
  public void setPOS(String v) {
    if (WordForm_Type.featOkTst && ((WordForm_Type)jcasType).casFeat_POS == null)
      jcasType.jcas.throwFeatMissing("POS", "com.digitalpebble.rasp.WordForm");
    jcasType.ll_cas.ll_setStringValue(addr, ((WordForm_Type)jcasType).casFeatCode_POS, v);}    
   
    
  //*--------------*
  //* Feature: probability

  /** getter for probability - gets 
   * @generated
   * @return value of the feature 
   */
  public double getProbability() {
    if (WordForm_Type.featOkTst && ((WordForm_Type)jcasType).casFeat_probability == null)
      jcasType.jcas.throwFeatMissing("probability", "com.digitalpebble.rasp.WordForm");
    return jcasType.ll_cas.ll_getDoubleValue(addr, ((WordForm_Type)jcasType).casFeatCode_probability);}
    
  /** setter for probability - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setProbability(double v) {
    if (WordForm_Type.featOkTst && ((WordForm_Type)jcasType).casFeat_probability == null)
      jcasType.jcas.throwFeatMissing("probability", "com.digitalpebble.rasp.WordForm");
    jcasType.ll_cas.ll_setDoubleValue(addr, ((WordForm_Type)jcasType).casFeatCode_probability, v);}    
   
    
  //*--------------*
  //* Feature: suffix

  /** getter for suffix - gets 
   * @generated
   * @return value of the feature 
   */
  public String getSuffix() {
    if (WordForm_Type.featOkTst && ((WordForm_Type)jcasType).casFeat_suffix == null)
      jcasType.jcas.throwFeatMissing("suffix", "com.digitalpebble.rasp.WordForm");
    return jcasType.ll_cas.ll_getStringValue(addr, ((WordForm_Type)jcasType).casFeatCode_suffix);}
    
  /** setter for suffix - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setSuffix(String v) {
    if (WordForm_Type.featOkTst && ((WordForm_Type)jcasType).casFeat_suffix == null)
      jcasType.jcas.throwFeatMissing("suffix", "com.digitalpebble.rasp.WordForm");
    jcasType.ll_cas.ll_setStringValue(addr, ((WordForm_Type)jcasType).casFeatCode_suffix, v);}    
  }

    