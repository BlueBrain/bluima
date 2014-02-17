

/* First created by JCasGen Wed Oct 19 19:10:28 CEST 2011 */
package de.julielab.jules.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.FSArray;


/** 
 * Updated by JCasGen Mon Feb 17 22:12:57 CET 2014
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/src/main/resources/typeSystem/bbp-types.xml
 * @generated */
public class EventMention extends ConceptMention {
  /** @generated
   * @ordered 
   */
  public final static int typeIndexID = JCasRegistry.register(EventMention.class);
  /** @generated
   * @ordered 
   */
  public final static int type = typeIndexID;
  /** @generated  */
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected EventMention() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public EventMention(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public EventMention(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public EventMention(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** <!-- begin-user-doc -->
    * Write your own initialization here
    * <!-- end-user-doc -->
  @generated modifiable */
  private void readObject() {}
     
 
    
  //*--------------*
  //* Feature: arguments

  /** getter for arguments - gets 
   * @generated */
  public FSArray getArguments() {
    if (EventMention_Type.featOkTst && ((EventMention_Type)jcasType).casFeat_arguments == null)
      jcasType.jcas.throwFeatMissing("arguments", "de.julielab.jules.types.EventMention");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((EventMention_Type)jcasType).casFeatCode_arguments)));}
    
  /** setter for arguments - sets  
   * @generated */
  public void setArguments(FSArray v) {
    if (EventMention_Type.featOkTst && ((EventMention_Type)jcasType).casFeat_arguments == null)
      jcasType.jcas.throwFeatMissing("arguments", "de.julielab.jules.types.EventMention");
    jcasType.ll_cas.ll_setRefValue(addr, ((EventMention_Type)jcasType).casFeatCode_arguments, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for arguments - gets an indexed value - 
   * @generated */
  public ArgumentMention getArguments(int i) {
    if (EventMention_Type.featOkTst && ((EventMention_Type)jcasType).casFeat_arguments == null)
      jcasType.jcas.throwFeatMissing("arguments", "de.julielab.jules.types.EventMention");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((EventMention_Type)jcasType).casFeatCode_arguments), i);
    return (ArgumentMention)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((EventMention_Type)jcasType).casFeatCode_arguments), i)));}

  /** indexed setter for arguments - sets an indexed value - 
   * @generated */
  public void setArguments(int i, ArgumentMention v) { 
    if (EventMention_Type.featOkTst && ((EventMention_Type)jcasType).casFeat_arguments == null)
      jcasType.jcas.throwFeatMissing("arguments", "de.julielab.jules.types.EventMention");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((EventMention_Type)jcasType).casFeatCode_arguments), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((EventMention_Type)jcasType).casFeatCode_arguments), i, jcasType.ll_cas.ll_getFSRef(v));}
   
    
  //*--------------*
  //* Feature: polarity

  /** getter for polarity - gets 
   * @generated */
  public String getPolarity() {
    if (EventMention_Type.featOkTst && ((EventMention_Type)jcasType).casFeat_polarity == null)
      jcasType.jcas.throwFeatMissing("polarity", "de.julielab.jules.types.EventMention");
    return jcasType.ll_cas.ll_getStringValue(addr, ((EventMention_Type)jcasType).casFeatCode_polarity);}
    
  /** setter for polarity - sets  
   * @generated */
  public void setPolarity(String v) {
    if (EventMention_Type.featOkTst && ((EventMention_Type)jcasType).casFeat_polarity == null)
      jcasType.jcas.throwFeatMissing("polarity", "de.julielab.jules.types.EventMention");
    jcasType.ll_cas.ll_setStringValue(addr, ((EventMention_Type)jcasType).casFeatCode_polarity, v);}    
   
    
  //*--------------*
  //* Feature: modality

  /** getter for modality - gets 
   * @generated */
  public String getModality() {
    if (EventMention_Type.featOkTst && ((EventMention_Type)jcasType).casFeat_modality == null)
      jcasType.jcas.throwFeatMissing("modality", "de.julielab.jules.types.EventMention");
    return jcasType.ll_cas.ll_getStringValue(addr, ((EventMention_Type)jcasType).casFeatCode_modality);}
    
  /** setter for modality - sets  
   * @generated */
  public void setModality(String v) {
    if (EventMention_Type.featOkTst && ((EventMention_Type)jcasType).casFeat_modality == null)
      jcasType.jcas.throwFeatMissing("modality", "de.julielab.jules.types.EventMention");
    jcasType.ll_cas.ll_setStringValue(addr, ((EventMention_Type)jcasType).casFeatCode_modality, v);}    
   
    
  //*--------------*
  //* Feature: tense

  /** getter for tense - gets 
   * @generated */
  public String getTense() {
    if (EventMention_Type.featOkTst && ((EventMention_Type)jcasType).casFeat_tense == null)
      jcasType.jcas.throwFeatMissing("tense", "de.julielab.jules.types.EventMention");
    return jcasType.ll_cas.ll_getStringValue(addr, ((EventMention_Type)jcasType).casFeatCode_tense);}
    
  /** setter for tense - sets  
   * @generated */
  public void setTense(String v) {
    if (EventMention_Type.featOkTst && ((EventMention_Type)jcasType).casFeat_tense == null)
      jcasType.jcas.throwFeatMissing("tense", "de.julielab.jules.types.EventMention");
    jcasType.ll_cas.ll_setStringValue(addr, ((EventMention_Type)jcasType).casFeatCode_tense, v);}    
   
    
  //*--------------*
  //* Feature: genericity

  /** getter for genericity - gets 
   * @generated */
  public String getGenericity() {
    if (EventMention_Type.featOkTst && ((EventMention_Type)jcasType).casFeat_genericity == null)
      jcasType.jcas.throwFeatMissing("genericity", "de.julielab.jules.types.EventMention");
    return jcasType.ll_cas.ll_getStringValue(addr, ((EventMention_Type)jcasType).casFeatCode_genericity);}
    
  /** setter for genericity - sets  
   * @generated */
  public void setGenericity(String v) {
    if (EventMention_Type.featOkTst && ((EventMention_Type)jcasType).casFeat_genericity == null)
      jcasType.jcas.throwFeatMissing("genericity", "de.julielab.jules.types.EventMention");
    jcasType.ll_cas.ll_setStringValue(addr, ((EventMention_Type)jcasType).casFeatCode_genericity, v);}    
   
    
  //*--------------*
  //* Feature: trigger

  /** getter for trigger - gets 
   * @generated */
  public EventTrigger getTrigger() {
    if (EventMention_Type.featOkTst && ((EventMention_Type)jcasType).casFeat_trigger == null)
      jcasType.jcas.throwFeatMissing("trigger", "de.julielab.jules.types.EventMention");
    return (EventTrigger)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((EventMention_Type)jcasType).casFeatCode_trigger)));}
    
  /** setter for trigger - sets  
   * @generated */
  public void setTrigger(EventTrigger v) {
    if (EventMention_Type.featOkTst && ((EventMention_Type)jcasType).casFeat_trigger == null)
      jcasType.jcas.throwFeatMissing("trigger", "de.julielab.jules.types.EventMention");
    jcasType.ll_cas.ll_setRefValue(addr, ((EventMention_Type)jcasType).casFeatCode_trigger, jcasType.ll_cas.ll_getFSRef(v));}    
  }

    