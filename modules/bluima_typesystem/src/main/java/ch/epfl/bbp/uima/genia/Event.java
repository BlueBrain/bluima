

/* First created by JCasGen Fri Dec 23 13:41:29 CET 2011 */
package ch.epfl.bbp.uima.genia;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.FSArray;
import org.apache.uima.jcas.cas.FSList;
import org.apache.uima.jcas.tcas.Annotation;
import ch.epfl.bbp.uima.types.Protein;


/** See
        http://2011.bionlp-st.org/home/genia-event-extraction-genia
 * Updated by JCasGen Mon Feb 17 10:49:39 CET 2014
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/src/main/resources/typeSystem/bbp-types.xml
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
  /** @generated  */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected Event() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public Event(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public Event(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public Event(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** <!-- begin-user-doc -->
    * Write your own initialization here
    * <!-- end-user-doc -->
  @generated modifiable */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: event_type

  /** getter for event_type - gets 
   * @generated */
  public String getEvent_type() {
    if (Event_Type.featOkTst && ((Event_Type)jcasType).casFeat_event_type == null)
      jcasType.jcas.throwFeatMissing("event_type", "ch.epfl.bbp.uima.genia.Event");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Event_Type)jcasType).casFeatCode_event_type);}
    
  /** setter for event_type - sets  
   * @generated */
  public void setEvent_type(String v) {
    if (Event_Type.featOkTst && ((Event_Type)jcasType).casFeat_event_type == null)
      jcasType.jcas.throwFeatMissing("event_type", "ch.epfl.bbp.uima.genia.Event");
    jcasType.ll_cas.ll_setStringValue(addr, ((Event_Type)jcasType).casFeatCode_event_type, v);}    
   
    
  //*--------------*
  //* Feature: themes_protein

  /** getter for themes_protein - gets 
   * @generated */
  public FSArray getThemes_protein() {
    if (Event_Type.featOkTst && ((Event_Type)jcasType).casFeat_themes_protein == null)
      jcasType.jcas.throwFeatMissing("themes_protein", "ch.epfl.bbp.uima.genia.Event");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Event_Type)jcasType).casFeatCode_themes_protein)));}
    
  /** setter for themes_protein - sets  
   * @generated */
  public void setThemes_protein(FSArray v) {
    if (Event_Type.featOkTst && ((Event_Type)jcasType).casFeat_themes_protein == null)
      jcasType.jcas.throwFeatMissing("themes_protein", "ch.epfl.bbp.uima.genia.Event");
    jcasType.ll_cas.ll_setRefValue(addr, ((Event_Type)jcasType).casFeatCode_themes_protein, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for themes_protein - gets an indexed value - 
   * @generated */
  public Protein getThemes_protein(int i) {
    if (Event_Type.featOkTst && ((Event_Type)jcasType).casFeat_themes_protein == null)
      jcasType.jcas.throwFeatMissing("themes_protein", "ch.epfl.bbp.uima.genia.Event");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Event_Type)jcasType).casFeatCode_themes_protein), i);
    return (Protein)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Event_Type)jcasType).casFeatCode_themes_protein), i)));}

  /** indexed setter for themes_protein - sets an indexed value - 
   * @generated */
  public void setThemes_protein(int i, Protein v) { 
    if (Event_Type.featOkTst && ((Event_Type)jcasType).casFeat_themes_protein == null)
      jcasType.jcas.throwFeatMissing("themes_protein", "ch.epfl.bbp.uima.genia.Event");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Event_Type)jcasType).casFeatCode_themes_protein), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Event_Type)jcasType).casFeatCode_themes_protein), i, jcasType.ll_cas.ll_getFSRef(v));}
   
    
  //*--------------*
  //* Feature: causes_protein

  /** getter for causes_protein - gets 
   * @generated */
  public FSArray getCauses_protein() {
    if (Event_Type.featOkTst && ((Event_Type)jcasType).casFeat_causes_protein == null)
      jcasType.jcas.throwFeatMissing("causes_protein", "ch.epfl.bbp.uima.genia.Event");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Event_Type)jcasType).casFeatCode_causes_protein)));}
    
  /** setter for causes_protein - sets  
   * @generated */
  public void setCauses_protein(FSArray v) {
    if (Event_Type.featOkTst && ((Event_Type)jcasType).casFeat_causes_protein == null)
      jcasType.jcas.throwFeatMissing("causes_protein", "ch.epfl.bbp.uima.genia.Event");
    jcasType.ll_cas.ll_setRefValue(addr, ((Event_Type)jcasType).casFeatCode_causes_protein, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for causes_protein - gets an indexed value - 
   * @generated */
  public Protein getCauses_protein(int i) {
    if (Event_Type.featOkTst && ((Event_Type)jcasType).casFeat_causes_protein == null)
      jcasType.jcas.throwFeatMissing("causes_protein", "ch.epfl.bbp.uima.genia.Event");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Event_Type)jcasType).casFeatCode_causes_protein), i);
    return (Protein)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Event_Type)jcasType).casFeatCode_causes_protein), i)));}

  /** indexed setter for causes_protein - sets an indexed value - 
   * @generated */
  public void setCauses_protein(int i, Protein v) { 
    if (Event_Type.featOkTst && ((Event_Type)jcasType).casFeat_causes_protein == null)
      jcasType.jcas.throwFeatMissing("causes_protein", "ch.epfl.bbp.uima.genia.Event");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Event_Type)jcasType).casFeatCode_causes_protein), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Event_Type)jcasType).casFeatCode_causes_protein), i, jcasType.ll_cas.ll_getFSRef(v));}
   
    
  //*--------------*
  //* Feature: site

  /** getter for site - gets 
   * @generated */
  public String getSite() {
    if (Event_Type.featOkTst && ((Event_Type)jcasType).casFeat_site == null)
      jcasType.jcas.throwFeatMissing("site", "ch.epfl.bbp.uima.genia.Event");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Event_Type)jcasType).casFeatCode_site);}
    
  /** setter for site - sets  
   * @generated */
  public void setSite(String v) {
    if (Event_Type.featOkTst && ((Event_Type)jcasType).casFeat_site == null)
      jcasType.jcas.throwFeatMissing("site", "ch.epfl.bbp.uima.genia.Event");
    jcasType.ll_cas.ll_setStringValue(addr, ((Event_Type)jcasType).casFeatCode_site, v);}    
   
    
  //*--------------*
  //* Feature: id

  /** getter for id - gets 
   * @generated */
  public String getId() {
    if (Event_Type.featOkTst && ((Event_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "ch.epfl.bbp.uima.genia.Event");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Event_Type)jcasType).casFeatCode_id);}
    
  /** setter for id - sets  
   * @generated */
  public void setId(String v) {
    if (Event_Type.featOkTst && ((Event_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "ch.epfl.bbp.uima.genia.Event");
    jcasType.ll_cas.ll_setStringValue(addr, ((Event_Type)jcasType).casFeatCode_id, v);}    
   
    
  //*--------------*
  //* Feature: themes_event

  /** getter for themes_event - gets 
   * @generated */
  public FSArray getThemes_event() {
    if (Event_Type.featOkTst && ((Event_Type)jcasType).casFeat_themes_event == null)
      jcasType.jcas.throwFeatMissing("themes_event", "ch.epfl.bbp.uima.genia.Event");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Event_Type)jcasType).casFeatCode_themes_event)));}
    
  /** setter for themes_event - sets  
   * @generated */
  public void setThemes_event(FSArray v) {
    if (Event_Type.featOkTst && ((Event_Type)jcasType).casFeat_themes_event == null)
      jcasType.jcas.throwFeatMissing("themes_event", "ch.epfl.bbp.uima.genia.Event");
    jcasType.ll_cas.ll_setRefValue(addr, ((Event_Type)jcasType).casFeatCode_themes_event, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for themes_event - gets an indexed value - 
   * @generated */
  public Event getThemes_event(int i) {
    if (Event_Type.featOkTst && ((Event_Type)jcasType).casFeat_themes_event == null)
      jcasType.jcas.throwFeatMissing("themes_event", "ch.epfl.bbp.uima.genia.Event");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Event_Type)jcasType).casFeatCode_themes_event), i);
    return (Event)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Event_Type)jcasType).casFeatCode_themes_event), i)));}

  /** indexed setter for themes_event - sets an indexed value - 
   * @generated */
  public void setThemes_event(int i, Event v) { 
    if (Event_Type.featOkTst && ((Event_Type)jcasType).casFeat_themes_event == null)
      jcasType.jcas.throwFeatMissing("themes_event", "ch.epfl.bbp.uima.genia.Event");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Event_Type)jcasType).casFeatCode_themes_event), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Event_Type)jcasType).casFeatCode_themes_event), i, jcasType.ll_cas.ll_getFSRef(v));}
   
    
  //*--------------*
  //* Feature: causes_event

  /** getter for causes_event - gets 
   * @generated */
  public FSArray getCauses_event() {
    if (Event_Type.featOkTst && ((Event_Type)jcasType).casFeat_causes_event == null)
      jcasType.jcas.throwFeatMissing("causes_event", "ch.epfl.bbp.uima.genia.Event");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Event_Type)jcasType).casFeatCode_causes_event)));}
    
  /** setter for causes_event - sets  
   * @generated */
  public void setCauses_event(FSArray v) {
    if (Event_Type.featOkTst && ((Event_Type)jcasType).casFeat_causes_event == null)
      jcasType.jcas.throwFeatMissing("causes_event", "ch.epfl.bbp.uima.genia.Event");
    jcasType.ll_cas.ll_setRefValue(addr, ((Event_Type)jcasType).casFeatCode_causes_event, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for causes_event - gets an indexed value - 
   * @generated */
  public Event getCauses_event(int i) {
    if (Event_Type.featOkTst && ((Event_Type)jcasType).casFeat_causes_event == null)
      jcasType.jcas.throwFeatMissing("causes_event", "ch.epfl.bbp.uima.genia.Event");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Event_Type)jcasType).casFeatCode_causes_event), i);
    return (Event)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Event_Type)jcasType).casFeatCode_causes_event), i)));}

  /** indexed setter for causes_event - sets an indexed value - 
   * @generated */
  public void setCauses_event(int i, Event v) { 
    if (Event_Type.featOkTst && ((Event_Type)jcasType).casFeat_causes_event == null)
      jcasType.jcas.throwFeatMissing("causes_event", "ch.epfl.bbp.uima.genia.Event");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Event_Type)jcasType).casFeatCode_causes_event), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Event_Type)jcasType).casFeatCode_causes_event), i, jcasType.ll_cas.ll_getFSRef(v));}
  }

    