

/* First created by JCasGen Sat Mar 07 22:05:57 CET 2015 */
package de.julielab.jules.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.FSArray;


/** 
 * Updated by JCasGen Sat Mar 07 22:05:57 CET 2015
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/target/jcasgen/typesystem.xml
 * @generated */
public class EventMention extends ConceptMention {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(EventMention.class);
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
  protected EventMention() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public EventMention(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public EventMention(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public EventMention(JCas jcas, int begin, int end) {
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
  //* Feature: arguments

  /** getter for arguments - gets 
   * @generated
   * @return value of the feature 
   */
  public FSArray getArguments() {
    if (EventMention_Type.featOkTst && ((EventMention_Type)jcasType).casFeat_arguments == null)
      jcasType.jcas.throwFeatMissing("arguments", "de.julielab.jules.types.EventMention");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((EventMention_Type)jcasType).casFeatCode_arguments)));}
    
  /** setter for arguments - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setArguments(FSArray v) {
    if (EventMention_Type.featOkTst && ((EventMention_Type)jcasType).casFeat_arguments == null)
      jcasType.jcas.throwFeatMissing("arguments", "de.julielab.jules.types.EventMention");
    jcasType.ll_cas.ll_setRefValue(addr, ((EventMention_Type)jcasType).casFeatCode_arguments, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for arguments - gets an indexed value - 
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public ArgumentMention getArguments(int i) {
    if (EventMention_Type.featOkTst && ((EventMention_Type)jcasType).casFeat_arguments == null)
      jcasType.jcas.throwFeatMissing("arguments", "de.julielab.jules.types.EventMention");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((EventMention_Type)jcasType).casFeatCode_arguments), i);
    return (ArgumentMention)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((EventMention_Type)jcasType).casFeatCode_arguments), i)));}

  /** indexed setter for arguments - sets an indexed value - 
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setArguments(int i, ArgumentMention v) { 
    if (EventMention_Type.featOkTst && ((EventMention_Type)jcasType).casFeat_arguments == null)
      jcasType.jcas.throwFeatMissing("arguments", "de.julielab.jules.types.EventMention");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((EventMention_Type)jcasType).casFeatCode_arguments), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((EventMention_Type)jcasType).casFeatCode_arguments), i, jcasType.ll_cas.ll_getFSRef(v));}
   
    
  //*--------------*
  //* Feature: polarity

  /** getter for polarity - gets 
   * @generated
   * @return value of the feature 
   */
  public String getPolarity() {
    if (EventMention_Type.featOkTst && ((EventMention_Type)jcasType).casFeat_polarity == null)
      jcasType.jcas.throwFeatMissing("polarity", "de.julielab.jules.types.EventMention");
    return jcasType.ll_cas.ll_getStringValue(addr, ((EventMention_Type)jcasType).casFeatCode_polarity);}
    
  /** setter for polarity - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setPolarity(String v) {
    if (EventMention_Type.featOkTst && ((EventMention_Type)jcasType).casFeat_polarity == null)
      jcasType.jcas.throwFeatMissing("polarity", "de.julielab.jules.types.EventMention");
    jcasType.ll_cas.ll_setStringValue(addr, ((EventMention_Type)jcasType).casFeatCode_polarity, v);}    
   
    
  //*--------------*
  //* Feature: modality

  /** getter for modality - gets 
   * @generated
   * @return value of the feature 
   */
  public String getModality() {
    if (EventMention_Type.featOkTst && ((EventMention_Type)jcasType).casFeat_modality == null)
      jcasType.jcas.throwFeatMissing("modality", "de.julielab.jules.types.EventMention");
    return jcasType.ll_cas.ll_getStringValue(addr, ((EventMention_Type)jcasType).casFeatCode_modality);}
    
  /** setter for modality - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setModality(String v) {
    if (EventMention_Type.featOkTst && ((EventMention_Type)jcasType).casFeat_modality == null)
      jcasType.jcas.throwFeatMissing("modality", "de.julielab.jules.types.EventMention");
    jcasType.ll_cas.ll_setStringValue(addr, ((EventMention_Type)jcasType).casFeatCode_modality, v);}    
   
    
  //*--------------*
  //* Feature: tense

  /** getter for tense - gets 
   * @generated
   * @return value of the feature 
   */
  public String getTense() {
    if (EventMention_Type.featOkTst && ((EventMention_Type)jcasType).casFeat_tense == null)
      jcasType.jcas.throwFeatMissing("tense", "de.julielab.jules.types.EventMention");
    return jcasType.ll_cas.ll_getStringValue(addr, ((EventMention_Type)jcasType).casFeatCode_tense);}
    
  /** setter for tense - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setTense(String v) {
    if (EventMention_Type.featOkTst && ((EventMention_Type)jcasType).casFeat_tense == null)
      jcasType.jcas.throwFeatMissing("tense", "de.julielab.jules.types.EventMention");
    jcasType.ll_cas.ll_setStringValue(addr, ((EventMention_Type)jcasType).casFeatCode_tense, v);}    
   
    
  //*--------------*
  //* Feature: genericity

  /** getter for genericity - gets 
   * @generated
   * @return value of the feature 
   */
  public String getGenericity() {
    if (EventMention_Type.featOkTst && ((EventMention_Type)jcasType).casFeat_genericity == null)
      jcasType.jcas.throwFeatMissing("genericity", "de.julielab.jules.types.EventMention");
    return jcasType.ll_cas.ll_getStringValue(addr, ((EventMention_Type)jcasType).casFeatCode_genericity);}
    
  /** setter for genericity - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setGenericity(String v) {
    if (EventMention_Type.featOkTst && ((EventMention_Type)jcasType).casFeat_genericity == null)
      jcasType.jcas.throwFeatMissing("genericity", "de.julielab.jules.types.EventMention");
    jcasType.ll_cas.ll_setStringValue(addr, ((EventMention_Type)jcasType).casFeatCode_genericity, v);}    
   
    
  //*--------------*
  //* Feature: trigger

  /** getter for trigger - gets 
   * @generated
   * @return value of the feature 
   */
  public EventTrigger getTrigger() {
    if (EventMention_Type.featOkTst && ((EventMention_Type)jcasType).casFeat_trigger == null)
      jcasType.jcas.throwFeatMissing("trigger", "de.julielab.jules.types.EventMention");
    return (EventTrigger)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((EventMention_Type)jcasType).casFeatCode_trigger)));}
    
  /** setter for trigger - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setTrigger(EventTrigger v) {
    if (EventMention_Type.featOkTst && ((EventMention_Type)jcasType).casFeat_trigger == null)
      jcasType.jcas.throwFeatMissing("trigger", "de.julielab.jules.types.EventMention");
    jcasType.ll_cas.ll_setRefValue(addr, ((EventMention_Type)jcasType).casFeatCode_trigger, jcasType.ll_cas.ll_getFSRef(v));}    
  }

    