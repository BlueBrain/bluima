

/* First created by JCasGen Sat Mar 07 22:05:57 CET 2015 */
package com.digitalpebble.rasp;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.FSArray;
import org.apache.uima.jcas.tcas.Annotation;


/** A token for Rasp
 * Updated by JCasGen Sat Mar 07 22:05:57 CET 2015
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/target/jcasgen/typesystem.xml
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
  /** @generated
   * @return index of the type  
   */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected Token() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Token(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Token(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Token(JCas jcas, int begin, int end) {
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
  //* Feature: wordForms

  /** getter for wordForms - gets A Token is related to one or more WordForm
   * @generated
   * @return value of the feature 
   */
  public FSArray getWordForms() {
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_wordForms == null)
      jcasType.jcas.throwFeatMissing("wordForms", "com.digitalpebble.rasp.Token");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Token_Type)jcasType).casFeatCode_wordForms)));}
    
  /** setter for wordForms - sets A Token is related to one or more WordForm 
   * @generated
   * @param v value to set into the feature 
   */
  public void setWordForms(FSArray v) {
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_wordForms == null)
      jcasType.jcas.throwFeatMissing("wordForms", "com.digitalpebble.rasp.Token");
    jcasType.ll_cas.ll_setRefValue(addr, ((Token_Type)jcasType).casFeatCode_wordForms, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for wordForms - gets an indexed value - A Token is related to one or more WordForm
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public WordForm getWordForms(int i) {
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_wordForms == null)
      jcasType.jcas.throwFeatMissing("wordForms", "com.digitalpebble.rasp.Token");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Token_Type)jcasType).casFeatCode_wordForms), i);
    return (WordForm)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Token_Type)jcasType).casFeatCode_wordForms), i)));}

  /** indexed setter for wordForms - sets an indexed value - A Token is related to one or more WordForm
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setWordForms(int i, WordForm v) { 
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_wordForms == null)
      jcasType.jcas.throwFeatMissing("wordForms", "com.digitalpebble.rasp.Token");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Token_Type)jcasType).casFeatCode_wordForms), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Token_Type)jcasType).casFeatCode_wordForms), i, jcasType.ll_cas.ll_getFSRef(v));}
  }

    