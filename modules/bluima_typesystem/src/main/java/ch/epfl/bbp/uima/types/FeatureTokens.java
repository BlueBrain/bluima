

/* First created by JCasGen Sat Mar 07 22:05:56 CET 2015 */
package ch.epfl.bbp.uima.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.IntegerArray;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.jcas.cas.StringArray;


/** a list of strings that represent the cleaned-up (pre-processed) tokens for use with LDA libs or others
 * Updated by JCasGen Sat Mar 07 22:05:56 CET 2015
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/target/jcasgen/typesystem.xml
 * @generated */
public class FeatureTokens extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(FeatureTokens.class);
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
  protected FeatureTokens() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public FeatureTokens(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public FeatureTokens(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public FeatureTokens(JCas jcas, int begin, int end) {
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
  //* Feature: tokens

  /** getter for tokens - gets 
   * @generated
   * @return value of the feature 
   */
  public StringArray getTokens() {
    if (FeatureTokens_Type.featOkTst && ((FeatureTokens_Type)jcasType).casFeat_tokens == null)
      jcasType.jcas.throwFeatMissing("tokens", "ch.epfl.bbp.uima.types.FeatureTokens");
    return (StringArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((FeatureTokens_Type)jcasType).casFeatCode_tokens)));}
    
  /** setter for tokens - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setTokens(StringArray v) {
    if (FeatureTokens_Type.featOkTst && ((FeatureTokens_Type)jcasType).casFeat_tokens == null)
      jcasType.jcas.throwFeatMissing("tokens", "ch.epfl.bbp.uima.types.FeatureTokens");
    jcasType.ll_cas.ll_setRefValue(addr, ((FeatureTokens_Type)jcasType).casFeatCode_tokens, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for tokens - gets an indexed value - 
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public String getTokens(int i) {
    if (FeatureTokens_Type.featOkTst && ((FeatureTokens_Type)jcasType).casFeat_tokens == null)
      jcasType.jcas.throwFeatMissing("tokens", "ch.epfl.bbp.uima.types.FeatureTokens");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((FeatureTokens_Type)jcasType).casFeatCode_tokens), i);
    return jcasType.ll_cas.ll_getStringArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((FeatureTokens_Type)jcasType).casFeatCode_tokens), i);}

  /** indexed setter for tokens - sets an indexed value - 
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setTokens(int i, String v) { 
    if (FeatureTokens_Type.featOkTst && ((FeatureTokens_Type)jcasType).casFeat_tokens == null)
      jcasType.jcas.throwFeatMissing("tokens", "ch.epfl.bbp.uima.types.FeatureTokens");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((FeatureTokens_Type)jcasType).casFeatCode_tokens), i);
    jcasType.ll_cas.ll_setStringArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((FeatureTokens_Type)jcasType).casFeatCode_tokens), i, v);}
   
    
  //*--------------*
  //* Feature: beginnings

  /** getter for beginnings - gets 
   * @generated
   * @return value of the feature 
   */
  public IntegerArray getBeginnings() {
    if (FeatureTokens_Type.featOkTst && ((FeatureTokens_Type)jcasType).casFeat_beginnings == null)
      jcasType.jcas.throwFeatMissing("beginnings", "ch.epfl.bbp.uima.types.FeatureTokens");
    return (IntegerArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((FeatureTokens_Type)jcasType).casFeatCode_beginnings)));}
    
  /** setter for beginnings - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setBeginnings(IntegerArray v) {
    if (FeatureTokens_Type.featOkTst && ((FeatureTokens_Type)jcasType).casFeat_beginnings == null)
      jcasType.jcas.throwFeatMissing("beginnings", "ch.epfl.bbp.uima.types.FeatureTokens");
    jcasType.ll_cas.ll_setRefValue(addr, ((FeatureTokens_Type)jcasType).casFeatCode_beginnings, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for beginnings - gets an indexed value - 
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public int getBeginnings(int i) {
    if (FeatureTokens_Type.featOkTst && ((FeatureTokens_Type)jcasType).casFeat_beginnings == null)
      jcasType.jcas.throwFeatMissing("beginnings", "ch.epfl.bbp.uima.types.FeatureTokens");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((FeatureTokens_Type)jcasType).casFeatCode_beginnings), i);
    return jcasType.ll_cas.ll_getIntArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((FeatureTokens_Type)jcasType).casFeatCode_beginnings), i);}

  /** indexed setter for beginnings - sets an indexed value - 
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setBeginnings(int i, int v) { 
    if (FeatureTokens_Type.featOkTst && ((FeatureTokens_Type)jcasType).casFeat_beginnings == null)
      jcasType.jcas.throwFeatMissing("beginnings", "ch.epfl.bbp.uima.types.FeatureTokens");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((FeatureTokens_Type)jcasType).casFeatCode_beginnings), i);
    jcasType.ll_cas.ll_setIntArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((FeatureTokens_Type)jcasType).casFeatCode_beginnings), i, v);}
   
    
  //*--------------*
  //* Feature: endings

  /** getter for endings - gets 
   * @generated
   * @return value of the feature 
   */
  public IntegerArray getEndings() {
    if (FeatureTokens_Type.featOkTst && ((FeatureTokens_Type)jcasType).casFeat_endings == null)
      jcasType.jcas.throwFeatMissing("endings", "ch.epfl.bbp.uima.types.FeatureTokens");
    return (IntegerArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((FeatureTokens_Type)jcasType).casFeatCode_endings)));}
    
  /** setter for endings - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setEndings(IntegerArray v) {
    if (FeatureTokens_Type.featOkTst && ((FeatureTokens_Type)jcasType).casFeat_endings == null)
      jcasType.jcas.throwFeatMissing("endings", "ch.epfl.bbp.uima.types.FeatureTokens");
    jcasType.ll_cas.ll_setRefValue(addr, ((FeatureTokens_Type)jcasType).casFeatCode_endings, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for endings - gets an indexed value - 
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public int getEndings(int i) {
    if (FeatureTokens_Type.featOkTst && ((FeatureTokens_Type)jcasType).casFeat_endings == null)
      jcasType.jcas.throwFeatMissing("endings", "ch.epfl.bbp.uima.types.FeatureTokens");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((FeatureTokens_Type)jcasType).casFeatCode_endings), i);
    return jcasType.ll_cas.ll_getIntArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((FeatureTokens_Type)jcasType).casFeatCode_endings), i);}

  /** indexed setter for endings - sets an indexed value - 
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setEndings(int i, int v) { 
    if (FeatureTokens_Type.featOkTst && ((FeatureTokens_Type)jcasType).casFeat_endings == null)
      jcasType.jcas.throwFeatMissing("endings", "ch.epfl.bbp.uima.types.FeatureTokens");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((FeatureTokens_Type)jcasType).casFeatCode_endings), i);
    jcasType.ll_cas.ll_setIntArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((FeatureTokens_Type)jcasType).casFeatCode_endings), i, v);}
  }

    