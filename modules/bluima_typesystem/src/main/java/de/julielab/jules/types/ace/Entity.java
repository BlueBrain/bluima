

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
public class Entity extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Entity.class);
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
  protected Entity() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Entity(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Entity(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Entity(JCas jcas, int begin, int end) {
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
  //* Feature: ace_type

  /** getter for ace_type - gets 
   * @generated
   * @return value of the feature 
   */
  public String getAce_type() {
    if (Entity_Type.featOkTst && ((Entity_Type)jcasType).casFeat_ace_type == null)
      jcasType.jcas.throwFeatMissing("ace_type", "de.julielab.jules.types.ace.Entity");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Entity_Type)jcasType).casFeatCode_ace_type);}
    
  /** setter for ace_type - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setAce_type(String v) {
    if (Entity_Type.featOkTst && ((Entity_Type)jcasType).casFeat_ace_type == null)
      jcasType.jcas.throwFeatMissing("ace_type", "de.julielab.jules.types.ace.Entity");
    jcasType.ll_cas.ll_setStringValue(addr, ((Entity_Type)jcasType).casFeatCode_ace_type, v);}    
   
    
  //*--------------*
  //* Feature: ace_subtype

  /** getter for ace_subtype - gets 
   * @generated
   * @return value of the feature 
   */
  public String getAce_subtype() {
    if (Entity_Type.featOkTst && ((Entity_Type)jcasType).casFeat_ace_subtype == null)
      jcasType.jcas.throwFeatMissing("ace_subtype", "de.julielab.jules.types.ace.Entity");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Entity_Type)jcasType).casFeatCode_ace_subtype);}
    
  /** setter for ace_subtype - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setAce_subtype(String v) {
    if (Entity_Type.featOkTst && ((Entity_Type)jcasType).casFeat_ace_subtype == null)
      jcasType.jcas.throwFeatMissing("ace_subtype", "de.julielab.jules.types.ace.Entity");
    jcasType.ll_cas.ll_setStringValue(addr, ((Entity_Type)jcasType).casFeatCode_ace_subtype, v);}    
   
    
  //*--------------*
  //* Feature: ace_class

  /** getter for ace_class - gets 
   * @generated
   * @return value of the feature 
   */
  public String getAce_class() {
    if (Entity_Type.featOkTst && ((Entity_Type)jcasType).casFeat_ace_class == null)
      jcasType.jcas.throwFeatMissing("ace_class", "de.julielab.jules.types.ace.Entity");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Entity_Type)jcasType).casFeatCode_ace_class);}
    
  /** setter for ace_class - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setAce_class(String v) {
    if (Entity_Type.featOkTst && ((Entity_Type)jcasType).casFeat_ace_class == null)
      jcasType.jcas.throwFeatMissing("ace_class", "de.julielab.jules.types.ace.Entity");
    jcasType.ll_cas.ll_setStringValue(addr, ((Entity_Type)jcasType).casFeatCode_ace_class, v);}    
   
    
  //*--------------*
  //* Feature: entity_mentions

  /** getter for entity_mentions - gets 
   * @generated
   * @return value of the feature 
   */
  public FSArray getEntity_mentions() {
    if (Entity_Type.featOkTst && ((Entity_Type)jcasType).casFeat_entity_mentions == null)
      jcasType.jcas.throwFeatMissing("entity_mentions", "de.julielab.jules.types.ace.Entity");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Entity_Type)jcasType).casFeatCode_entity_mentions)));}
    
  /** setter for entity_mentions - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setEntity_mentions(FSArray v) {
    if (Entity_Type.featOkTst && ((Entity_Type)jcasType).casFeat_entity_mentions == null)
      jcasType.jcas.throwFeatMissing("entity_mentions", "de.julielab.jules.types.ace.Entity");
    jcasType.ll_cas.ll_setRefValue(addr, ((Entity_Type)jcasType).casFeatCode_entity_mentions, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for entity_mentions - gets an indexed value - 
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public EntityMention getEntity_mentions(int i) {
    if (Entity_Type.featOkTst && ((Entity_Type)jcasType).casFeat_entity_mentions == null)
      jcasType.jcas.throwFeatMissing("entity_mentions", "de.julielab.jules.types.ace.Entity");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Entity_Type)jcasType).casFeatCode_entity_mentions), i);
    return (EntityMention)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Entity_Type)jcasType).casFeatCode_entity_mentions), i)));}

  /** indexed setter for entity_mentions - sets an indexed value - 
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setEntity_mentions(int i, EntityMention v) { 
    if (Entity_Type.featOkTst && ((Entity_Type)jcasType).casFeat_entity_mentions == null)
      jcasType.jcas.throwFeatMissing("entity_mentions", "de.julielab.jules.types.ace.Entity");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Entity_Type)jcasType).casFeatCode_entity_mentions), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Entity_Type)jcasType).casFeatCode_entity_mentions), i, jcasType.ll_cas.ll_getFSRef(v));}
   
    
  //*--------------*
  //* Feature: entity_attributes

  /** getter for entity_attributes - gets 
   * @generated
   * @return value of the feature 
   */
  public FSArray getEntity_attributes() {
    if (Entity_Type.featOkTst && ((Entity_Type)jcasType).casFeat_entity_attributes == null)
      jcasType.jcas.throwFeatMissing("entity_attributes", "de.julielab.jules.types.ace.Entity");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Entity_Type)jcasType).casFeatCode_entity_attributes)));}
    
  /** setter for entity_attributes - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setEntity_attributes(FSArray v) {
    if (Entity_Type.featOkTst && ((Entity_Type)jcasType).casFeat_entity_attributes == null)
      jcasType.jcas.throwFeatMissing("entity_attributes", "de.julielab.jules.types.ace.Entity");
    jcasType.ll_cas.ll_setRefValue(addr, ((Entity_Type)jcasType).casFeatCode_entity_attributes, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for entity_attributes - gets an indexed value - 
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public EntityAttribute getEntity_attributes(int i) {
    if (Entity_Type.featOkTst && ((Entity_Type)jcasType).casFeat_entity_attributes == null)
      jcasType.jcas.throwFeatMissing("entity_attributes", "de.julielab.jules.types.ace.Entity");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Entity_Type)jcasType).casFeatCode_entity_attributes), i);
    return (EntityAttribute)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Entity_Type)jcasType).casFeatCode_entity_attributes), i)));}

  /** indexed setter for entity_attributes - sets an indexed value - 
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setEntity_attributes(int i, EntityAttribute v) { 
    if (Entity_Type.featOkTst && ((Entity_Type)jcasType).casFeat_entity_attributes == null)
      jcasType.jcas.throwFeatMissing("entity_attributes", "de.julielab.jules.types.ace.Entity");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Entity_Type)jcasType).casFeatCode_entity_attributes), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Entity_Type)jcasType).casFeatCode_entity_attributes), i, jcasType.ll_cas.ll_getFSRef(v));}
  }

    