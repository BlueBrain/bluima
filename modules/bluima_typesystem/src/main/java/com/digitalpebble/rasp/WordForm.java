

/* First created by JCasGen Sun May 27 17:27:29 CEST 2012 */
package com.digitalpebble.rasp;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** A WordForm consists of a POS tag, a lemma and possibly a probability. There is one or more WordForm per Token (as in the MAF ISO Norm)
 * Updated by JCasGen Sun May 27 17:27:29 CEST 2012
 * XML source: /Users/richarde/dev/bluebrain/svn_nlp/UIMA/blue_uima/trunk/modules/julielab_typesystem-2.6.8/src/main/resources/typeSystem/RASPTypes.xml
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
  /** @generated  */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected WordForm() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public WordForm(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public WordForm(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public WordForm(JCas jcas, int begin, int end) {
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
  //* Feature: lemma

  /** getter for lemma - gets lemma of the Form
   * @generated */
  public String getLemma() {
    if (WordForm_Type.featOkTst && ((WordForm_Type)jcasType).casFeat_lemma == null)
      jcasType.jcas.throwFeatMissing("lemma", "com.digitalpebble.rasp.WordForm");
    return jcasType.ll_cas.ll_getStringValue(addr, ((WordForm_Type)jcasType).casFeatCode_lemma);}
    
  /** setter for lemma - sets lemma of the Form 
   * @generated */
  public void setLemma(String v) {
    if (WordForm_Type.featOkTst && ((WordForm_Type)jcasType).casFeat_lemma == null)
      jcasType.jcas.throwFeatMissing("lemma", "com.digitalpebble.rasp.WordForm");
    jcasType.ll_cas.ll_setStringValue(addr, ((WordForm_Type)jcasType).casFeatCode_lemma, v);}    
   
    
  //*--------------*
  //* Feature: POS

  /** getter for POS - gets POS tag for a given form
   * @generated */
  public String getPOS() {
    if (WordForm_Type.featOkTst && ((WordForm_Type)jcasType).casFeat_POS == null)
      jcasType.jcas.throwFeatMissing("POS", "com.digitalpebble.rasp.WordForm");
    return jcasType.ll_cas.ll_getStringValue(addr, ((WordForm_Type)jcasType).casFeatCode_POS);}
    
  /** setter for POS - sets POS tag for a given form 
   * @generated */
  public void setPOS(String v) {
    if (WordForm_Type.featOkTst && ((WordForm_Type)jcasType).casFeat_POS == null)
      jcasType.jcas.throwFeatMissing("POS", "com.digitalpebble.rasp.WordForm");
    jcasType.ll_cas.ll_setStringValue(addr, ((WordForm_Type)jcasType).casFeatCode_POS, v);}    
   
    
  //*--------------*
  //* Feature: probability

  /** getter for probability - gets 
   * @generated */
  public double getProbability() {
    if (WordForm_Type.featOkTst && ((WordForm_Type)jcasType).casFeat_probability == null)
      jcasType.jcas.throwFeatMissing("probability", "com.digitalpebble.rasp.WordForm");
    return jcasType.ll_cas.ll_getDoubleValue(addr, ((WordForm_Type)jcasType).casFeatCode_probability);}
    
  /** setter for probability - sets  
   * @generated */
  public void setProbability(double v) {
    if (WordForm_Type.featOkTst && ((WordForm_Type)jcasType).casFeat_probability == null)
      jcasType.jcas.throwFeatMissing("probability", "com.digitalpebble.rasp.WordForm");
    jcasType.ll_cas.ll_setDoubleValue(addr, ((WordForm_Type)jcasType).casFeatCode_probability, v);}    
   
    
  //*--------------*
  //* Feature: suffix

  /** getter for suffix - gets 
   * @generated */
  public String getSuffix() {
    if (WordForm_Type.featOkTst && ((WordForm_Type)jcasType).casFeat_suffix == null)
      jcasType.jcas.throwFeatMissing("suffix", "com.digitalpebble.rasp.WordForm");
    return jcasType.ll_cas.ll_getStringValue(addr, ((WordForm_Type)jcasType).casFeatCode_suffix);}
    
  /** setter for suffix - sets  
   * @generated */
  public void setSuffix(String v) {
    if (WordForm_Type.featOkTst && ((WordForm_Type)jcasType).casFeat_suffix == null)
      jcasType.jcas.throwFeatMissing("suffix", "com.digitalpebble.rasp.WordForm");
    jcasType.ll_cas.ll_setStringValue(addr, ((WordForm_Type)jcasType).casFeatCode_suffix, v);}    
  }

    