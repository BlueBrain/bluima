

/* First created by JCasGen Sat Mar 07 22:05:57 CET 2015 */
package de.julielab.jules.types.ace;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.FSArray;


/** 
 * Updated by JCasGen Sat Mar 07 22:05:57 CET 2015
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/target/jcasgen/typesystem.xml
 * @generated */
public class Timex2 extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Timex2.class);
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
  protected Timex2() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Timex2(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Timex2(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Timex2(JCas jcas, int begin, int end) {
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
  //* Feature: mod

  /** getter for mod - gets 
   * @generated
   * @return value of the feature 
   */
  public String getMod() {
    if (Timex2_Type.featOkTst && ((Timex2_Type)jcasType).casFeat_mod == null)
      jcasType.jcas.throwFeatMissing("mod", "de.julielab.jules.types.ace.Timex2");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Timex2_Type)jcasType).casFeatCode_mod);}
    
  /** setter for mod - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setMod(String v) {
    if (Timex2_Type.featOkTst && ((Timex2_Type)jcasType).casFeat_mod == null)
      jcasType.jcas.throwFeatMissing("mod", "de.julielab.jules.types.ace.Timex2");
    jcasType.ll_cas.ll_setStringValue(addr, ((Timex2_Type)jcasType).casFeatCode_mod, v);}    
   
    
  //*--------------*
  //* Feature: comment

  /** getter for comment - gets 
   * @generated
   * @return value of the feature 
   */
  public String getComment() {
    if (Timex2_Type.featOkTst && ((Timex2_Type)jcasType).casFeat_comment == null)
      jcasType.jcas.throwFeatMissing("comment", "de.julielab.jules.types.ace.Timex2");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Timex2_Type)jcasType).casFeatCode_comment);}
    
  /** setter for comment - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setComment(String v) {
    if (Timex2_Type.featOkTst && ((Timex2_Type)jcasType).casFeat_comment == null)
      jcasType.jcas.throwFeatMissing("comment", "de.julielab.jules.types.ace.Timex2");
    jcasType.ll_cas.ll_setStringValue(addr, ((Timex2_Type)jcasType).casFeatCode_comment, v);}    
   
    
  //*--------------*
  //* Feature: anchor_val

  /** getter for anchor_val - gets 
   * @generated
   * @return value of the feature 
   */
  public String getAnchor_val() {
    if (Timex2_Type.featOkTst && ((Timex2_Type)jcasType).casFeat_anchor_val == null)
      jcasType.jcas.throwFeatMissing("anchor_val", "de.julielab.jules.types.ace.Timex2");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Timex2_Type)jcasType).casFeatCode_anchor_val);}
    
  /** setter for anchor_val - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setAnchor_val(String v) {
    if (Timex2_Type.featOkTst && ((Timex2_Type)jcasType).casFeat_anchor_val == null)
      jcasType.jcas.throwFeatMissing("anchor_val", "de.julielab.jules.types.ace.Timex2");
    jcasType.ll_cas.ll_setStringValue(addr, ((Timex2_Type)jcasType).casFeatCode_anchor_val, v);}    
   
    
  //*--------------*
  //* Feature: val

  /** getter for val - gets 
   * @generated
   * @return value of the feature 
   */
  public String getVal() {
    if (Timex2_Type.featOkTst && ((Timex2_Type)jcasType).casFeat_val == null)
      jcasType.jcas.throwFeatMissing("val", "de.julielab.jules.types.ace.Timex2");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Timex2_Type)jcasType).casFeatCode_val);}
    
  /** setter for val - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setVal(String v) {
    if (Timex2_Type.featOkTst && ((Timex2_Type)jcasType).casFeat_val == null)
      jcasType.jcas.throwFeatMissing("val", "de.julielab.jules.types.ace.Timex2");
    jcasType.ll_cas.ll_setStringValue(addr, ((Timex2_Type)jcasType).casFeatCode_val, v);}    
   
    
  //*--------------*
  //* Feature: set

  /** getter for set - gets 
   * @generated
   * @return value of the feature 
   */
  public String getSet() {
    if (Timex2_Type.featOkTst && ((Timex2_Type)jcasType).casFeat_set == null)
      jcasType.jcas.throwFeatMissing("set", "de.julielab.jules.types.ace.Timex2");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Timex2_Type)jcasType).casFeatCode_set);}
    
  /** setter for set - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setSet(String v) {
    if (Timex2_Type.featOkTst && ((Timex2_Type)jcasType).casFeat_set == null)
      jcasType.jcas.throwFeatMissing("set", "de.julielab.jules.types.ace.Timex2");
    jcasType.ll_cas.ll_setStringValue(addr, ((Timex2_Type)jcasType).casFeatCode_set, v);}    
   
    
  //*--------------*
  //* Feature: non_specific

  /** getter for non_specific - gets 
   * @generated
   * @return value of the feature 
   */
  public String getNon_specific() {
    if (Timex2_Type.featOkTst && ((Timex2_Type)jcasType).casFeat_non_specific == null)
      jcasType.jcas.throwFeatMissing("non_specific", "de.julielab.jules.types.ace.Timex2");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Timex2_Type)jcasType).casFeatCode_non_specific);}
    
  /** setter for non_specific - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setNon_specific(String v) {
    if (Timex2_Type.featOkTst && ((Timex2_Type)jcasType).casFeat_non_specific == null)
      jcasType.jcas.throwFeatMissing("non_specific", "de.julielab.jules.types.ace.Timex2");
    jcasType.ll_cas.ll_setStringValue(addr, ((Timex2_Type)jcasType).casFeatCode_non_specific, v);}    
   
    
  //*--------------*
  //* Feature: anchor_dir

  /** getter for anchor_dir - gets 
   * @generated
   * @return value of the feature 
   */
  public String getAnchor_dir() {
    if (Timex2_Type.featOkTst && ((Timex2_Type)jcasType).casFeat_anchor_dir == null)
      jcasType.jcas.throwFeatMissing("anchor_dir", "de.julielab.jules.types.ace.Timex2");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Timex2_Type)jcasType).casFeatCode_anchor_dir);}
    
  /** setter for anchor_dir - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setAnchor_dir(String v) {
    if (Timex2_Type.featOkTst && ((Timex2_Type)jcasType).casFeat_anchor_dir == null)
      jcasType.jcas.throwFeatMissing("anchor_dir", "de.julielab.jules.types.ace.Timex2");
    jcasType.ll_cas.ll_setStringValue(addr, ((Timex2_Type)jcasType).casFeatCode_anchor_dir, v);}    
   
    
  //*--------------*
  //* Feature: mentions

  /** getter for mentions - gets 
   * @generated
   * @return value of the feature 
   */
  public FSArray getMentions() {
    if (Timex2_Type.featOkTst && ((Timex2_Type)jcasType).casFeat_mentions == null)
      jcasType.jcas.throwFeatMissing("mentions", "de.julielab.jules.types.ace.Timex2");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Timex2_Type)jcasType).casFeatCode_mentions)));}
    
  /** setter for mentions - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setMentions(FSArray v) {
    if (Timex2_Type.featOkTst && ((Timex2_Type)jcasType).casFeat_mentions == null)
      jcasType.jcas.throwFeatMissing("mentions", "de.julielab.jules.types.ace.Timex2");
    jcasType.ll_cas.ll_setRefValue(addr, ((Timex2_Type)jcasType).casFeatCode_mentions, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for mentions - gets an indexed value - 
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public Timex2Mention getMentions(int i) {
    if (Timex2_Type.featOkTst && ((Timex2_Type)jcasType).casFeat_mentions == null)
      jcasType.jcas.throwFeatMissing("mentions", "de.julielab.jules.types.ace.Timex2");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Timex2_Type)jcasType).casFeatCode_mentions), i);
    return (Timex2Mention)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Timex2_Type)jcasType).casFeatCode_mentions), i)));}

  /** indexed setter for mentions - sets an indexed value - 
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setMentions(int i, Timex2Mention v) { 
    if (Timex2_Type.featOkTst && ((Timex2_Type)jcasType).casFeat_mentions == null)
      jcasType.jcas.throwFeatMissing("mentions", "de.julielab.jules.types.ace.Timex2");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Timex2_Type)jcasType).casFeatCode_mentions), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Timex2_Type)jcasType).casFeatCode_mentions), i, jcasType.ll_cas.ll_getFSRef(v));}
  }

    