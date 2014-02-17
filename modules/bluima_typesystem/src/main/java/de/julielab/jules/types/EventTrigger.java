

/* First created by JCasGen Wed Oct 19 19:10:28 CEST 2011 */
package de.julielab.jules.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** 
 * Updated by JCasGen Mon Feb 17 22:12:57 CET 2014
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/src/main/resources/typeSystem/bbp-types.xml
 * @generated */
public class EventTrigger extends ConceptMention {
  /** @generated
   * @ordered 
   */
  public final static int typeIndexID = JCasRegistry.register(EventTrigger.class);
  /** @generated
   * @ordered 
   */
  public final static int type = typeIndexID;
  /** @generated  */
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected EventTrigger() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public EventTrigger(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public EventTrigger(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public EventTrigger(JCas jcas, int begin, int end) {
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
  //* Feature: probability

  /** getter for probability - gets probability of this trigger to be an event or relation trigger
   * @generated */
  public String getProbability() {
    if (EventTrigger_Type.featOkTst && ((EventTrigger_Type)jcasType).casFeat_probability == null)
      jcasType.jcas.throwFeatMissing("probability", "de.julielab.jules.types.EventTrigger");
    return jcasType.ll_cas.ll_getStringValue(addr, ((EventTrigger_Type)jcasType).casFeatCode_probability);}
    
  /** setter for probability - sets probability of this trigger to be an event or relation trigger 
   * @generated */
  public void setProbability(String v) {
    if (EventTrigger_Type.featOkTst && ((EventTrigger_Type)jcasType).casFeat_probability == null)
      jcasType.jcas.throwFeatMissing("probability", "de.julielab.jules.types.EventTrigger");
    jcasType.ll_cas.ll_setStringValue(addr, ((EventTrigger_Type)jcasType).casFeatCode_probability, v);}    
   
    
  //*--------------*
  //* Feature: specifity

  /** getter for specifity - gets specifity to be an event trigger, compare with using in other context
   * @generated */
  public String getSpecifity() {
    if (EventTrigger_Type.featOkTst && ((EventTrigger_Type)jcasType).casFeat_specifity == null)
      jcasType.jcas.throwFeatMissing("specifity", "de.julielab.jules.types.EventTrigger");
    return jcasType.ll_cas.ll_getStringValue(addr, ((EventTrigger_Type)jcasType).casFeatCode_specifity);}
    
  /** setter for specifity - sets specifity to be an event trigger, compare with using in other context 
   * @generated */
  public void setSpecifity(String v) {
    if (EventTrigger_Type.featOkTst && ((EventTrigger_Type)jcasType).casFeat_specifity == null)
      jcasType.jcas.throwFeatMissing("specifity", "de.julielab.jules.types.EventTrigger");
    jcasType.ll_cas.ll_setStringValue(addr, ((EventTrigger_Type)jcasType).casFeatCode_specifity, v);}    
   
    
  //*--------------*
  //* Feature: importance

  /** getter for importance - gets how important is this trigger for the event
   * @generated */
  public String getImportance() {
    if (EventTrigger_Type.featOkTst && ((EventTrigger_Type)jcasType).casFeat_importance == null)
      jcasType.jcas.throwFeatMissing("importance", "de.julielab.jules.types.EventTrigger");
    return jcasType.ll_cas.ll_getStringValue(addr, ((EventTrigger_Type)jcasType).casFeatCode_importance);}
    
  /** setter for importance - sets how important is this trigger for the event 
   * @generated */
  public void setImportance(String v) {
    if (EventTrigger_Type.featOkTst && ((EventTrigger_Type)jcasType).casFeat_importance == null)
      jcasType.jcas.throwFeatMissing("importance", "de.julielab.jules.types.EventTrigger");
    jcasType.ll_cas.ll_setStringValue(addr, ((EventTrigger_Type)jcasType).casFeatCode_importance, v);}    
  }

    