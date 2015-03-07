

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
public class Value extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Value.class);
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
  protected Value() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Value(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Value(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Value(JCas jcas, int begin, int end) {
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
  //* Feature: mentions

  /** getter for mentions - gets 
   * @generated
   * @return value of the feature 
   */
  public FSArray getMentions() {
    if (Value_Type.featOkTst && ((Value_Type)jcasType).casFeat_mentions == null)
      jcasType.jcas.throwFeatMissing("mentions", "de.julielab.jules.types.ace.Value");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Value_Type)jcasType).casFeatCode_mentions)));}
    
  /** setter for mentions - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setMentions(FSArray v) {
    if (Value_Type.featOkTst && ((Value_Type)jcasType).casFeat_mentions == null)
      jcasType.jcas.throwFeatMissing("mentions", "de.julielab.jules.types.ace.Value");
    jcasType.ll_cas.ll_setRefValue(addr, ((Value_Type)jcasType).casFeatCode_mentions, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for mentions - gets an indexed value - 
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public ValueMention getMentions(int i) {
    if (Value_Type.featOkTst && ((Value_Type)jcasType).casFeat_mentions == null)
      jcasType.jcas.throwFeatMissing("mentions", "de.julielab.jules.types.ace.Value");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Value_Type)jcasType).casFeatCode_mentions), i);
    return (ValueMention)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Value_Type)jcasType).casFeatCode_mentions), i)));}

  /** indexed setter for mentions - sets an indexed value - 
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setMentions(int i, ValueMention v) { 
    if (Value_Type.featOkTst && ((Value_Type)jcasType).casFeat_mentions == null)
      jcasType.jcas.throwFeatMissing("mentions", "de.julielab.jules.types.ace.Value");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Value_Type)jcasType).casFeatCode_mentions), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Value_Type)jcasType).casFeatCode_mentions), i, jcasType.ll_cas.ll_getFSRef(v));}
   
    
  //*--------------*
  //* Feature: ace_type

  /** getter for ace_type - gets 
   * @generated
   * @return value of the feature 
   */
  public String getAce_type() {
    if (Value_Type.featOkTst && ((Value_Type)jcasType).casFeat_ace_type == null)
      jcasType.jcas.throwFeatMissing("ace_type", "de.julielab.jules.types.ace.Value");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Value_Type)jcasType).casFeatCode_ace_type);}
    
  /** setter for ace_type - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setAce_type(String v) {
    if (Value_Type.featOkTst && ((Value_Type)jcasType).casFeat_ace_type == null)
      jcasType.jcas.throwFeatMissing("ace_type", "de.julielab.jules.types.ace.Value");
    jcasType.ll_cas.ll_setStringValue(addr, ((Value_Type)jcasType).casFeatCode_ace_type, v);}    
   
    
  //*--------------*
  //* Feature: ace_subtype

  /** getter for ace_subtype - gets 
   * @generated
   * @return value of the feature 
   */
  public String getAce_subtype() {
    if (Value_Type.featOkTst && ((Value_Type)jcasType).casFeat_ace_subtype == null)
      jcasType.jcas.throwFeatMissing("ace_subtype", "de.julielab.jules.types.ace.Value");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Value_Type)jcasType).casFeatCode_ace_subtype);}
    
  /** setter for ace_subtype - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setAce_subtype(String v) {
    if (Value_Type.featOkTst && ((Value_Type)jcasType).casFeat_ace_subtype == null)
      jcasType.jcas.throwFeatMissing("ace_subtype", "de.julielab.jules.types.ace.Value");
    jcasType.ll_cas.ll_setStringValue(addr, ((Value_Type)jcasType).casFeatCode_ace_subtype, v);}    
  }

    