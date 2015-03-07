

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
public class Event extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Event.class);
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
  protected Event() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Event(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Event(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Event(JCas jcas, int begin, int end) {
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
  //* Feature: tense

  /** getter for tense - gets 
   * @generated
   * @return value of the feature 
   */
  public String getTense() {
    if (Event_Type.featOkTst && ((Event_Type)jcasType).casFeat_tense == null)
      jcasType.jcas.throwFeatMissing("tense", "de.julielab.jules.types.ace.Event");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Event_Type)jcasType).casFeatCode_tense);}
    
  /** setter for tense - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setTense(String v) {
    if (Event_Type.featOkTst && ((Event_Type)jcasType).casFeat_tense == null)
      jcasType.jcas.throwFeatMissing("tense", "de.julielab.jules.types.ace.Event");
    jcasType.ll_cas.ll_setStringValue(addr, ((Event_Type)jcasType).casFeatCode_tense, v);}    
   
    
  //*--------------*
  //* Feature: polarity

  /** getter for polarity - gets 
   * @generated
   * @return value of the feature 
   */
  public String getPolarity() {
    if (Event_Type.featOkTst && ((Event_Type)jcasType).casFeat_polarity == null)
      jcasType.jcas.throwFeatMissing("polarity", "de.julielab.jules.types.ace.Event");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Event_Type)jcasType).casFeatCode_polarity);}
    
  /** setter for polarity - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setPolarity(String v) {
    if (Event_Type.featOkTst && ((Event_Type)jcasType).casFeat_polarity == null)
      jcasType.jcas.throwFeatMissing("polarity", "de.julielab.jules.types.ace.Event");
    jcasType.ll_cas.ll_setStringValue(addr, ((Event_Type)jcasType).casFeatCode_polarity, v);}    
   
    
  //*--------------*
  //* Feature: ace_subtype

  /** getter for ace_subtype - gets 
   * @generated
   * @return value of the feature 
   */
  public String getAce_subtype() {
    if (Event_Type.featOkTst && ((Event_Type)jcasType).casFeat_ace_subtype == null)
      jcasType.jcas.throwFeatMissing("ace_subtype", "de.julielab.jules.types.ace.Event");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Event_Type)jcasType).casFeatCode_ace_subtype);}
    
  /** setter for ace_subtype - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setAce_subtype(String v) {
    if (Event_Type.featOkTst && ((Event_Type)jcasType).casFeat_ace_subtype == null)
      jcasType.jcas.throwFeatMissing("ace_subtype", "de.julielab.jules.types.ace.Event");
    jcasType.ll_cas.ll_setStringValue(addr, ((Event_Type)jcasType).casFeatCode_ace_subtype, v);}    
   
    
  //*--------------*
  //* Feature: genericity

  /** getter for genericity - gets 
   * @generated
   * @return value of the feature 
   */
  public String getGenericity() {
    if (Event_Type.featOkTst && ((Event_Type)jcasType).casFeat_genericity == null)
      jcasType.jcas.throwFeatMissing("genericity", "de.julielab.jules.types.ace.Event");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Event_Type)jcasType).casFeatCode_genericity);}
    
  /** setter for genericity - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setGenericity(String v) {
    if (Event_Type.featOkTst && ((Event_Type)jcasType).casFeat_genericity == null)
      jcasType.jcas.throwFeatMissing("genericity", "de.julielab.jules.types.ace.Event");
    jcasType.ll_cas.ll_setStringValue(addr, ((Event_Type)jcasType).casFeatCode_genericity, v);}    
   
    
  //*--------------*
  //* Feature: modality

  /** getter for modality - gets 
   * @generated
   * @return value of the feature 
   */
  public String getModality() {
    if (Event_Type.featOkTst && ((Event_Type)jcasType).casFeat_modality == null)
      jcasType.jcas.throwFeatMissing("modality", "de.julielab.jules.types.ace.Event");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Event_Type)jcasType).casFeatCode_modality);}
    
  /** setter for modality - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setModality(String v) {
    if (Event_Type.featOkTst && ((Event_Type)jcasType).casFeat_modality == null)
      jcasType.jcas.throwFeatMissing("modality", "de.julielab.jules.types.ace.Event");
    jcasType.ll_cas.ll_setStringValue(addr, ((Event_Type)jcasType).casFeatCode_modality, v);}    
   
    
  //*--------------*
  //* Feature: ace_type

  /** getter for ace_type - gets 
   * @generated
   * @return value of the feature 
   */
  public String getAce_type() {
    if (Event_Type.featOkTst && ((Event_Type)jcasType).casFeat_ace_type == null)
      jcasType.jcas.throwFeatMissing("ace_type", "de.julielab.jules.types.ace.Event");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Event_Type)jcasType).casFeatCode_ace_type);}
    
  /** setter for ace_type - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setAce_type(String v) {
    if (Event_Type.featOkTst && ((Event_Type)jcasType).casFeat_ace_type == null)
      jcasType.jcas.throwFeatMissing("ace_type", "de.julielab.jules.types.ace.Event");
    jcasType.ll_cas.ll_setStringValue(addr, ((Event_Type)jcasType).casFeatCode_ace_type, v);}    
   
    
  //*--------------*
  //* Feature: arguments

  /** getter for arguments - gets 
   * @generated
   * @return value of the feature 
   */
  public FSArray getArguments() {
    if (Event_Type.featOkTst && ((Event_Type)jcasType).casFeat_arguments == null)
      jcasType.jcas.throwFeatMissing("arguments", "de.julielab.jules.types.ace.Event");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Event_Type)jcasType).casFeatCode_arguments)));}
    
  /** setter for arguments - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setArguments(FSArray v) {
    if (Event_Type.featOkTst && ((Event_Type)jcasType).casFeat_arguments == null)
      jcasType.jcas.throwFeatMissing("arguments", "de.julielab.jules.types.ace.Event");
    jcasType.ll_cas.ll_setRefValue(addr, ((Event_Type)jcasType).casFeatCode_arguments, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for arguments - gets an indexed value - 
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public EventArgument getArguments(int i) {
    if (Event_Type.featOkTst && ((Event_Type)jcasType).casFeat_arguments == null)
      jcasType.jcas.throwFeatMissing("arguments", "de.julielab.jules.types.ace.Event");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Event_Type)jcasType).casFeatCode_arguments), i);
    return (EventArgument)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Event_Type)jcasType).casFeatCode_arguments), i)));}

  /** indexed setter for arguments - sets an indexed value - 
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setArguments(int i, EventArgument v) { 
    if (Event_Type.featOkTst && ((Event_Type)jcasType).casFeat_arguments == null)
      jcasType.jcas.throwFeatMissing("arguments", "de.julielab.jules.types.ace.Event");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Event_Type)jcasType).casFeatCode_arguments), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Event_Type)jcasType).casFeatCode_arguments), i, jcasType.ll_cas.ll_getFSRef(v));}
   
    
  //*--------------*
  //* Feature: mentions

  /** getter for mentions - gets 
   * @generated
   * @return value of the feature 
   */
  public FSArray getMentions() {
    if (Event_Type.featOkTst && ((Event_Type)jcasType).casFeat_mentions == null)
      jcasType.jcas.throwFeatMissing("mentions", "de.julielab.jules.types.ace.Event");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Event_Type)jcasType).casFeatCode_mentions)));}
    
  /** setter for mentions - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setMentions(FSArray v) {
    if (Event_Type.featOkTst && ((Event_Type)jcasType).casFeat_mentions == null)
      jcasType.jcas.throwFeatMissing("mentions", "de.julielab.jules.types.ace.Event");
    jcasType.ll_cas.ll_setRefValue(addr, ((Event_Type)jcasType).casFeatCode_mentions, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for mentions - gets an indexed value - 
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public EventMention getMentions(int i) {
    if (Event_Type.featOkTst && ((Event_Type)jcasType).casFeat_mentions == null)
      jcasType.jcas.throwFeatMissing("mentions", "de.julielab.jules.types.ace.Event");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Event_Type)jcasType).casFeatCode_mentions), i);
    return (EventMention)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Event_Type)jcasType).casFeatCode_mentions), i)));}

  /** indexed setter for mentions - sets an indexed value - 
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setMentions(int i, EventMention v) { 
    if (Event_Type.featOkTst && ((Event_Type)jcasType).casFeat_mentions == null)
      jcasType.jcas.throwFeatMissing("mentions", "de.julielab.jules.types.ace.Event");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Event_Type)jcasType).casFeatCode_mentions), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Event_Type)jcasType).casFeatCode_mentions), i, jcasType.ll_cas.ll_getFSRef(v));}
  }

    