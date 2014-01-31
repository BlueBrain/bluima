

/* First created by JCasGen Sun May 27 17:27:29 CEST 2012 */
package com.digitalpebble.rasp;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.FSArray;
import org.apache.uima.jcas.tcas.Annotation;


/** A token for Rasp
 * Updated by JCasGen Sun May 27 17:27:29 CEST 2012
 * XML source: /Users/richarde/dev/bluebrain/svn_nlp/UIMA/blue_uima/trunk/modules/julielab_typesystem-2.6.8/src/main/resources/typeSystem/RASPTypes.xml
 * @generated */
public class Token extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Token.class);
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
  protected Token() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public Token(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public Token(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public Token(JCas jcas, int begin, int end) {
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
  //* Feature: wordForms

  /** getter for wordForms - gets A Token is related to one or more WordForm
   * @generated */
  public FSArray getWordForms() {
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_wordForms == null)
      jcasType.jcas.throwFeatMissing("wordForms", "com.digitalpebble.rasp.Token");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Token_Type)jcasType).casFeatCode_wordForms)));}
    
  /** setter for wordForms - sets A Token is related to one or more WordForm 
   * @generated */
  public void setWordForms(FSArray v) {
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_wordForms == null)
      jcasType.jcas.throwFeatMissing("wordForms", "com.digitalpebble.rasp.Token");
    jcasType.ll_cas.ll_setRefValue(addr, ((Token_Type)jcasType).casFeatCode_wordForms, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for wordForms - gets an indexed value - A Token is related to one or more WordForm
   * @generated */
  public WordForm getWordForms(int i) {
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_wordForms == null)
      jcasType.jcas.throwFeatMissing("wordForms", "com.digitalpebble.rasp.Token");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Token_Type)jcasType).casFeatCode_wordForms), i);
    return (WordForm)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Token_Type)jcasType).casFeatCode_wordForms), i)));}

  /** indexed setter for wordForms - sets an indexed value - A Token is related to one or more WordForm
   * @generated */
  public void setWordForms(int i, WordForm v) { 
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_wordForms == null)
      jcasType.jcas.throwFeatMissing("wordForms", "com.digitalpebble.rasp.Token");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Token_Type)jcasType).casFeatCode_wordForms), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Token_Type)jcasType).casFeatCode_wordForms), i, jcasType.ll_cas.ll_getFSRef(v));}
  }

    