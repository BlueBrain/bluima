

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
public class EventMention extends Annotation {
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
  //* Feature: anchor

  /** getter for anchor - gets 
   * @generated
   * @return value of the feature 
   */
  public Anchor getAnchor() {
    if (EventMention_Type.featOkTst && ((EventMention_Type)jcasType).casFeat_anchor == null)
      jcasType.jcas.throwFeatMissing("anchor", "de.julielab.jules.types.ace.EventMention");
    return (Anchor)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((EventMention_Type)jcasType).casFeatCode_anchor)));}
    
  /** setter for anchor - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setAnchor(Anchor v) {
    if (EventMention_Type.featOkTst && ((EventMention_Type)jcasType).casFeat_anchor == null)
      jcasType.jcas.throwFeatMissing("anchor", "de.julielab.jules.types.ace.EventMention");
    jcasType.ll_cas.ll_setRefValue(addr, ((EventMention_Type)jcasType).casFeatCode_anchor, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: level

  /** getter for level - gets 
   * @generated
   * @return value of the feature 
   */
  public String getLevel() {
    if (EventMention_Type.featOkTst && ((EventMention_Type)jcasType).casFeat_level == null)
      jcasType.jcas.throwFeatMissing("level", "de.julielab.jules.types.ace.EventMention");
    return jcasType.ll_cas.ll_getStringValue(addr, ((EventMention_Type)jcasType).casFeatCode_level);}
    
  /** setter for level - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setLevel(String v) {
    if (EventMention_Type.featOkTst && ((EventMention_Type)jcasType).casFeat_level == null)
      jcasType.jcas.throwFeatMissing("level", "de.julielab.jules.types.ace.EventMention");
    jcasType.ll_cas.ll_setStringValue(addr, ((EventMention_Type)jcasType).casFeatCode_level, v);}    
   
    
  //*--------------*
  //* Feature: arguments

  /** getter for arguments - gets 
   * @generated
   * @return value of the feature 
   */
  public FSArray getArguments() {
    if (EventMention_Type.featOkTst && ((EventMention_Type)jcasType).casFeat_arguments == null)
      jcasType.jcas.throwFeatMissing("arguments", "de.julielab.jules.types.ace.EventMention");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((EventMention_Type)jcasType).casFeatCode_arguments)));}
    
  /** setter for arguments - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setArguments(FSArray v) {
    if (EventMention_Type.featOkTst && ((EventMention_Type)jcasType).casFeat_arguments == null)
      jcasType.jcas.throwFeatMissing("arguments", "de.julielab.jules.types.ace.EventMention");
    jcasType.ll_cas.ll_setRefValue(addr, ((EventMention_Type)jcasType).casFeatCode_arguments, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for arguments - gets an indexed value - 
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public EventMentionArgument getArguments(int i) {
    if (EventMention_Type.featOkTst && ((EventMention_Type)jcasType).casFeat_arguments == null)
      jcasType.jcas.throwFeatMissing("arguments", "de.julielab.jules.types.ace.EventMention");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((EventMention_Type)jcasType).casFeatCode_arguments), i);
    return (EventMentionArgument)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((EventMention_Type)jcasType).casFeatCode_arguments), i)));}

  /** indexed setter for arguments - sets an indexed value - 
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setArguments(int i, EventMentionArgument v) { 
    if (EventMention_Type.featOkTst && ((EventMention_Type)jcasType).casFeat_arguments == null)
      jcasType.jcas.throwFeatMissing("arguments", "de.julielab.jules.types.ace.EventMention");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((EventMention_Type)jcasType).casFeatCode_arguments), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((EventMention_Type)jcasType).casFeatCode_arguments), i, jcasType.ll_cas.ll_getFSRef(v));}
   
    
  //*--------------*
  //* Feature: ldc_scope

  /** getter for ldc_scope - gets 
   * @generated
   * @return value of the feature 
   */
  public LDC_Scope getLdc_scope() {
    if (EventMention_Type.featOkTst && ((EventMention_Type)jcasType).casFeat_ldc_scope == null)
      jcasType.jcas.throwFeatMissing("ldc_scope", "de.julielab.jules.types.ace.EventMention");
    return (LDC_Scope)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((EventMention_Type)jcasType).casFeatCode_ldc_scope)));}
    
  /** setter for ldc_scope - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setLdc_scope(LDC_Scope v) {
    if (EventMention_Type.featOkTst && ((EventMention_Type)jcasType).casFeat_ldc_scope == null)
      jcasType.jcas.throwFeatMissing("ldc_scope", "de.julielab.jules.types.ace.EventMention");
    jcasType.ll_cas.ll_setRefValue(addr, ((EventMention_Type)jcasType).casFeatCode_ldc_scope, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: event_ref

  /** getter for event_ref - gets 
   * @generated
   * @return value of the feature 
   */
  public Event getEvent_ref() {
    if (EventMention_Type.featOkTst && ((EventMention_Type)jcasType).casFeat_event_ref == null)
      jcasType.jcas.throwFeatMissing("event_ref", "de.julielab.jules.types.ace.EventMention");
    return (Event)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((EventMention_Type)jcasType).casFeatCode_event_ref)));}
    
  /** setter for event_ref - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setEvent_ref(Event v) {
    if (EventMention_Type.featOkTst && ((EventMention_Type)jcasType).casFeat_event_ref == null)
      jcasType.jcas.throwFeatMissing("event_ref", "de.julielab.jules.types.ace.EventMention");
    jcasType.ll_cas.ll_setRefValue(addr, ((EventMention_Type)jcasType).casFeatCode_event_ref, jcasType.ll_cas.ll_getFSRef(v));}    
  }

    