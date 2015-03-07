

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
public class Document extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Document.class);
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
  protected Document() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Document(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Document(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Document(JCas jcas, int begin, int end) {
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
  //* Feature: docid

  /** getter for docid - gets 
   * @generated
   * @return value of the feature 
   */
  public String getDocid() {
    if (Document_Type.featOkTst && ((Document_Type)jcasType).casFeat_docid == null)
      jcasType.jcas.throwFeatMissing("docid", "de.julielab.jules.types.ace.Document");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Document_Type)jcasType).casFeatCode_docid);}
    
  /** setter for docid - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setDocid(String v) {
    if (Document_Type.featOkTst && ((Document_Type)jcasType).casFeat_docid == null)
      jcasType.jcas.throwFeatMissing("docid", "de.julielab.jules.types.ace.Document");
    jcasType.ll_cas.ll_setStringValue(addr, ((Document_Type)jcasType).casFeatCode_docid, v);}    
   
    
  //*--------------*
  //* Feature: entities

  /** getter for entities - gets 
   * @generated
   * @return value of the feature 
   */
  public FSArray getEntities() {
    if (Document_Type.featOkTst && ((Document_Type)jcasType).casFeat_entities == null)
      jcasType.jcas.throwFeatMissing("entities", "de.julielab.jules.types.ace.Document");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Document_Type)jcasType).casFeatCode_entities)));}
    
  /** setter for entities - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setEntities(FSArray v) {
    if (Document_Type.featOkTst && ((Document_Type)jcasType).casFeat_entities == null)
      jcasType.jcas.throwFeatMissing("entities", "de.julielab.jules.types.ace.Document");
    jcasType.ll_cas.ll_setRefValue(addr, ((Document_Type)jcasType).casFeatCode_entities, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for entities - gets an indexed value - 
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public Entity getEntities(int i) {
    if (Document_Type.featOkTst && ((Document_Type)jcasType).casFeat_entities == null)
      jcasType.jcas.throwFeatMissing("entities", "de.julielab.jules.types.ace.Document");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Document_Type)jcasType).casFeatCode_entities), i);
    return (Entity)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Document_Type)jcasType).casFeatCode_entities), i)));}

  /** indexed setter for entities - sets an indexed value - 
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setEntities(int i, Entity v) { 
    if (Document_Type.featOkTst && ((Document_Type)jcasType).casFeat_entities == null)
      jcasType.jcas.throwFeatMissing("entities", "de.julielab.jules.types.ace.Document");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Document_Type)jcasType).casFeatCode_entities), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Document_Type)jcasType).casFeatCode_entities), i, jcasType.ll_cas.ll_getFSRef(v));}
   
    
  //*--------------*
  //* Feature: values

  /** getter for values - gets 
   * @generated
   * @return value of the feature 
   */
  public FSArray getValues() {
    if (Document_Type.featOkTst && ((Document_Type)jcasType).casFeat_values == null)
      jcasType.jcas.throwFeatMissing("values", "de.julielab.jules.types.ace.Document");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Document_Type)jcasType).casFeatCode_values)));}
    
  /** setter for values - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setValues(FSArray v) {
    if (Document_Type.featOkTst && ((Document_Type)jcasType).casFeat_values == null)
      jcasType.jcas.throwFeatMissing("values", "de.julielab.jules.types.ace.Document");
    jcasType.ll_cas.ll_setRefValue(addr, ((Document_Type)jcasType).casFeatCode_values, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for values - gets an indexed value - 
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public Entity getValues(int i) {
    if (Document_Type.featOkTst && ((Document_Type)jcasType).casFeat_values == null)
      jcasType.jcas.throwFeatMissing("values", "de.julielab.jules.types.ace.Document");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Document_Type)jcasType).casFeatCode_values), i);
    return (Entity)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Document_Type)jcasType).casFeatCode_values), i)));}

  /** indexed setter for values - sets an indexed value - 
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setValues(int i, Entity v) { 
    if (Document_Type.featOkTst && ((Document_Type)jcasType).casFeat_values == null)
      jcasType.jcas.throwFeatMissing("values", "de.julielab.jules.types.ace.Document");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Document_Type)jcasType).casFeatCode_values), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Document_Type)jcasType).casFeatCode_values), i, jcasType.ll_cas.ll_getFSRef(v));}
   
    
  //*--------------*
  //* Feature: timex2

  /** getter for timex2 - gets 
   * @generated
   * @return value of the feature 
   */
  public FSArray getTimex2() {
    if (Document_Type.featOkTst && ((Document_Type)jcasType).casFeat_timex2 == null)
      jcasType.jcas.throwFeatMissing("timex2", "de.julielab.jules.types.ace.Document");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Document_Type)jcasType).casFeatCode_timex2)));}
    
  /** setter for timex2 - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setTimex2(FSArray v) {
    if (Document_Type.featOkTst && ((Document_Type)jcasType).casFeat_timex2 == null)
      jcasType.jcas.throwFeatMissing("timex2", "de.julielab.jules.types.ace.Document");
    jcasType.ll_cas.ll_setRefValue(addr, ((Document_Type)jcasType).casFeatCode_timex2, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for timex2 - gets an indexed value - 
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public Timex2 getTimex2(int i) {
    if (Document_Type.featOkTst && ((Document_Type)jcasType).casFeat_timex2 == null)
      jcasType.jcas.throwFeatMissing("timex2", "de.julielab.jules.types.ace.Document");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Document_Type)jcasType).casFeatCode_timex2), i);
    return (Timex2)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Document_Type)jcasType).casFeatCode_timex2), i)));}

  /** indexed setter for timex2 - sets an indexed value - 
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setTimex2(int i, Timex2 v) { 
    if (Document_Type.featOkTst && ((Document_Type)jcasType).casFeat_timex2 == null)
      jcasType.jcas.throwFeatMissing("timex2", "de.julielab.jules.types.ace.Document");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Document_Type)jcasType).casFeatCode_timex2), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Document_Type)jcasType).casFeatCode_timex2), i, jcasType.ll_cas.ll_getFSRef(v));}
   
    
  //*--------------*
  //* Feature: relations

  /** getter for relations - gets 
   * @generated
   * @return value of the feature 
   */
  public FSArray getRelations() {
    if (Document_Type.featOkTst && ((Document_Type)jcasType).casFeat_relations == null)
      jcasType.jcas.throwFeatMissing("relations", "de.julielab.jules.types.ace.Document");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Document_Type)jcasType).casFeatCode_relations)));}
    
  /** setter for relations - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setRelations(FSArray v) {
    if (Document_Type.featOkTst && ((Document_Type)jcasType).casFeat_relations == null)
      jcasType.jcas.throwFeatMissing("relations", "de.julielab.jules.types.ace.Document");
    jcasType.ll_cas.ll_setRefValue(addr, ((Document_Type)jcasType).casFeatCode_relations, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for relations - gets an indexed value - 
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public Relation getRelations(int i) {
    if (Document_Type.featOkTst && ((Document_Type)jcasType).casFeat_relations == null)
      jcasType.jcas.throwFeatMissing("relations", "de.julielab.jules.types.ace.Document");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Document_Type)jcasType).casFeatCode_relations), i);
    return (Relation)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Document_Type)jcasType).casFeatCode_relations), i)));}

  /** indexed setter for relations - sets an indexed value - 
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setRelations(int i, Relation v) { 
    if (Document_Type.featOkTst && ((Document_Type)jcasType).casFeat_relations == null)
      jcasType.jcas.throwFeatMissing("relations", "de.julielab.jules.types.ace.Document");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Document_Type)jcasType).casFeatCode_relations), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Document_Type)jcasType).casFeatCode_relations), i, jcasType.ll_cas.ll_getFSRef(v));}
   
    
  //*--------------*
  //* Feature: events

  /** getter for events - gets 
   * @generated
   * @return value of the feature 
   */
  public FSArray getEvents() {
    if (Document_Type.featOkTst && ((Document_Type)jcasType).casFeat_events == null)
      jcasType.jcas.throwFeatMissing("events", "de.julielab.jules.types.ace.Document");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Document_Type)jcasType).casFeatCode_events)));}
    
  /** setter for events - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setEvents(FSArray v) {
    if (Document_Type.featOkTst && ((Document_Type)jcasType).casFeat_events == null)
      jcasType.jcas.throwFeatMissing("events", "de.julielab.jules.types.ace.Document");
    jcasType.ll_cas.ll_setRefValue(addr, ((Document_Type)jcasType).casFeatCode_events, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for events - gets an indexed value - 
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public Event getEvents(int i) {
    if (Document_Type.featOkTst && ((Document_Type)jcasType).casFeat_events == null)
      jcasType.jcas.throwFeatMissing("events", "de.julielab.jules.types.ace.Document");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Document_Type)jcasType).casFeatCode_events), i);
    return (Event)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Document_Type)jcasType).casFeatCode_events), i)));}

  /** indexed setter for events - sets an indexed value - 
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setEvents(int i, Event v) { 
    if (Document_Type.featOkTst && ((Document_Type)jcasType).casFeat_events == null)
      jcasType.jcas.throwFeatMissing("events", "de.julielab.jules.types.ace.Document");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Document_Type)jcasType).casFeatCode_events), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Document_Type)jcasType).casFeatCode_events), i, jcasType.ll_cas.ll_getFSRef(v));}
  }

    