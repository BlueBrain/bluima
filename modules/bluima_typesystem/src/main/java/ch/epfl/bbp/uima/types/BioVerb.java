

/* First created by JCasGen Sat Mar 07 22:05:56 CET 2015 */
package ch.epfl.bbp.uima.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.FSArray;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.jcas.cas.TOP;


/** 
 * Updated by JCasGen Sat Mar 07 22:05:56 CET 2015
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/target/jcasgen/typesystem.xml
 * @generated */
public class BioVerb extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(BioVerb.class);
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
  protected BioVerb() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public BioVerb(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public BioVerb(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public BioVerb(JCas jcas, int begin, int end) {
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
  //* Feature: ref_id

  /** getter for ref_id - gets 
   * @generated
   * @return value of the feature 
   */
  public String getRef_id() {
    if (BioVerb_Type.featOkTst && ((BioVerb_Type)jcasType).casFeat_ref_id == null)
      jcasType.jcas.throwFeatMissing("ref_id", "ch.epfl.bbp.uima.types.BioVerb");
    return jcasType.ll_cas.ll_getStringValue(addr, ((BioVerb_Type)jcasType).casFeatCode_ref_id);}
    
  /** setter for ref_id - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setRef_id(String v) {
    if (BioVerb_Type.featOkTst && ((BioVerb_Type)jcasType).casFeat_ref_id == null)
      jcasType.jcas.throwFeatMissing("ref_id", "ch.epfl.bbp.uima.types.BioVerb");
    jcasType.ll_cas.ll_setStringValue(addr, ((BioVerb_Type)jcasType).casFeatCode_ref_id, v);}    
   
    
  //*--------------*
  //* Feature: canonical

  /** getter for canonical - gets 
   * @generated
   * @return value of the feature 
   */
  public String getCanonical() {
    if (BioVerb_Type.featOkTst && ((BioVerb_Type)jcasType).casFeat_canonical == null)
      jcasType.jcas.throwFeatMissing("canonical", "ch.epfl.bbp.uima.types.BioVerb");
    return jcasType.ll_cas.ll_getStringValue(addr, ((BioVerb_Type)jcasType).casFeatCode_canonical);}
    
  /** setter for canonical - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setCanonical(String v) {
    if (BioVerb_Type.featOkTst && ((BioVerb_Type)jcasType).casFeat_canonical == null)
      jcasType.jcas.throwFeatMissing("canonical", "ch.epfl.bbp.uima.types.BioVerb");
    jcasType.ll_cas.ll_setStringValue(addr, ((BioVerb_Type)jcasType).casFeatCode_canonical, v);}    
   
    
  //*--------------*
  //* Feature: enclosingSpan

  /** getter for enclosingSpan - gets span that this NoTerm is contained within (i.e.
            its sentence)
   * @generated
   * @return value of the feature 
   */
  public Annotation getEnclosingSpan() {
    if (BioVerb_Type.featOkTst && ((BioVerb_Type)jcasType).casFeat_enclosingSpan == null)
      jcasType.jcas.throwFeatMissing("enclosingSpan", "ch.epfl.bbp.uima.types.BioVerb");
    return (Annotation)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((BioVerb_Type)jcasType).casFeatCode_enclosingSpan)));}
    
  /** setter for enclosingSpan - sets span that this NoTerm is contained within (i.e.
            its sentence) 
   * @generated
   * @param v value to set into the feature 
   */
  public void setEnclosingSpan(Annotation v) {
    if (BioVerb_Type.featOkTst && ((BioVerb_Type)jcasType).casFeat_enclosingSpan == null)
      jcasType.jcas.throwFeatMissing("enclosingSpan", "ch.epfl.bbp.uima.types.BioVerb");
    jcasType.ll_cas.ll_setRefValue(addr, ((BioVerb_Type)jcasType).casFeatCode_enclosingSpan, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: matchedText

  /** getter for matchedText - gets 
   * @generated
   * @return value of the feature 
   */
  public String getMatchedText() {
    if (BioVerb_Type.featOkTst && ((BioVerb_Type)jcasType).casFeat_matchedText == null)
      jcasType.jcas.throwFeatMissing("matchedText", "ch.epfl.bbp.uima.types.BioVerb");
    return jcasType.ll_cas.ll_getStringValue(addr, ((BioVerb_Type)jcasType).casFeatCode_matchedText);}
    
  /** setter for matchedText - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setMatchedText(String v) {
    if (BioVerb_Type.featOkTst && ((BioVerb_Type)jcasType).casFeat_matchedText == null)
      jcasType.jcas.throwFeatMissing("matchedText", "ch.epfl.bbp.uima.types.BioVerb");
    jcasType.ll_cas.ll_setStringValue(addr, ((BioVerb_Type)jcasType).casFeatCode_matchedText, v);}    
   
    
  //*--------------*
  //* Feature: matchedTokens

  /** getter for matchedTokens - gets 
   * @generated
   * @return value of the feature 
   */
  public FSArray getMatchedTokens() {
    if (BioVerb_Type.featOkTst && ((BioVerb_Type)jcasType).casFeat_matchedTokens == null)
      jcasType.jcas.throwFeatMissing("matchedTokens", "ch.epfl.bbp.uima.types.BioVerb");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((BioVerb_Type)jcasType).casFeatCode_matchedTokens)));}
    
  /** setter for matchedTokens - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setMatchedTokens(FSArray v) {
    if (BioVerb_Type.featOkTst && ((BioVerb_Type)jcasType).casFeat_matchedTokens == null)
      jcasType.jcas.throwFeatMissing("matchedTokens", "ch.epfl.bbp.uima.types.BioVerb");
    jcasType.ll_cas.ll_setRefValue(addr, ((BioVerb_Type)jcasType).casFeatCode_matchedTokens, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for matchedTokens - gets an indexed value - 
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public TOP getMatchedTokens(int i) {
    if (BioVerb_Type.featOkTst && ((BioVerb_Type)jcasType).casFeat_matchedTokens == null)
      jcasType.jcas.throwFeatMissing("matchedTokens", "ch.epfl.bbp.uima.types.BioVerb");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((BioVerb_Type)jcasType).casFeatCode_matchedTokens), i);
    return (TOP)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((BioVerb_Type)jcasType).casFeatCode_matchedTokens), i)));}

  /** indexed setter for matchedTokens - sets an indexed value - 
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setMatchedTokens(int i, TOP v) { 
    if (BioVerb_Type.featOkTst && ((BioVerb_Type)jcasType).casFeat_matchedTokens == null)
      jcasType.jcas.throwFeatMissing("matchedTokens", "ch.epfl.bbp.uima.types.BioVerb");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((BioVerb_Type)jcasType).casFeatCode_matchedTokens), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((BioVerb_Type)jcasType).casFeatCode_matchedTokens), i, jcasType.ll_cas.ll_getFSRef(v));}
  }

    