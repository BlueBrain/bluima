

/* First created by JCasGen Sat Mar 07 22:05:57 CET 2015 */
package de.julielab.jules.types.ace;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** 
 * Updated by JCasGen Sat Mar 07 22:05:57 CET 2015
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/target/jcasgen/typesystem.xml
 * @generated */
public class EntityMention extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(EntityMention.class);
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
  protected EntityMention() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public EntityMention(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public EntityMention(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public EntityMention(JCas jcas, int begin, int end) {
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
  //* Feature: head

  /** getter for head - gets 
   * @generated
   * @return value of the feature 
   */
  public Head getHead() {
    if (EntityMention_Type.featOkTst && ((EntityMention_Type)jcasType).casFeat_head == null)
      jcasType.jcas.throwFeatMissing("head", "de.julielab.jules.types.ace.EntityMention");
    return (Head)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((EntityMention_Type)jcasType).casFeatCode_head)));}
    
  /** setter for head - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setHead(Head v) {
    if (EntityMention_Type.featOkTst && ((EntityMention_Type)jcasType).casFeat_head == null)
      jcasType.jcas.throwFeatMissing("head", "de.julielab.jules.types.ace.EntityMention");
    jcasType.ll_cas.ll_setRefValue(addr, ((EntityMention_Type)jcasType).casFeatCode_head, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: ldcatr

  /** getter for ldcatr - gets 
   * @generated
   * @return value of the feature 
   */
  public String getLdcatr() {
    if (EntityMention_Type.featOkTst && ((EntityMention_Type)jcasType).casFeat_ldcatr == null)
      jcasType.jcas.throwFeatMissing("ldcatr", "de.julielab.jules.types.ace.EntityMention");
    return jcasType.ll_cas.ll_getStringValue(addr, ((EntityMention_Type)jcasType).casFeatCode_ldcatr);}
    
  /** setter for ldcatr - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setLdcatr(String v) {
    if (EntityMention_Type.featOkTst && ((EntityMention_Type)jcasType).casFeat_ldcatr == null)
      jcasType.jcas.throwFeatMissing("ldcatr", "de.julielab.jules.types.ace.EntityMention");
    jcasType.ll_cas.ll_setStringValue(addr, ((EntityMention_Type)jcasType).casFeatCode_ldcatr, v);}    
   
    
  //*--------------*
  //* Feature: role

  /** getter for role - gets 
   * @generated
   * @return value of the feature 
   */
  public String getRole() {
    if (EntityMention_Type.featOkTst && ((EntityMention_Type)jcasType).casFeat_role == null)
      jcasType.jcas.throwFeatMissing("role", "de.julielab.jules.types.ace.EntityMention");
    return jcasType.ll_cas.ll_getStringValue(addr, ((EntityMention_Type)jcasType).casFeatCode_role);}
    
  /** setter for role - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setRole(String v) {
    if (EntityMention_Type.featOkTst && ((EntityMention_Type)jcasType).casFeat_role == null)
      jcasType.jcas.throwFeatMissing("role", "de.julielab.jules.types.ace.EntityMention");
    jcasType.ll_cas.ll_setStringValue(addr, ((EntityMention_Type)jcasType).casFeatCode_role, v);}    
   
    
  //*--------------*
  //* Feature: metonymy_mention

  /** getter for metonymy_mention - gets 
   * @generated
   * @return value of the feature 
   */
  public String getMetonymy_mention() {
    if (EntityMention_Type.featOkTst && ((EntityMention_Type)jcasType).casFeat_metonymy_mention == null)
      jcasType.jcas.throwFeatMissing("metonymy_mention", "de.julielab.jules.types.ace.EntityMention");
    return jcasType.ll_cas.ll_getStringValue(addr, ((EntityMention_Type)jcasType).casFeatCode_metonymy_mention);}
    
  /** setter for metonymy_mention - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setMetonymy_mention(String v) {
    if (EntityMention_Type.featOkTst && ((EntityMention_Type)jcasType).casFeat_metonymy_mention == null)
      jcasType.jcas.throwFeatMissing("metonymy_mention", "de.julielab.jules.types.ace.EntityMention");
    jcasType.ll_cas.ll_setStringValue(addr, ((EntityMention_Type)jcasType).casFeatCode_metonymy_mention, v);}    
   
    
  //*--------------*
  //* Feature: mention_type

  /** getter for mention_type - gets 
   * @generated
   * @return value of the feature 
   */
  public String getMention_type() {
    if (EntityMention_Type.featOkTst && ((EntityMention_Type)jcasType).casFeat_mention_type == null)
      jcasType.jcas.throwFeatMissing("mention_type", "de.julielab.jules.types.ace.EntityMention");
    return jcasType.ll_cas.ll_getStringValue(addr, ((EntityMention_Type)jcasType).casFeatCode_mention_type);}
    
  /** setter for mention_type - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setMention_type(String v) {
    if (EntityMention_Type.featOkTst && ((EntityMention_Type)jcasType).casFeat_mention_type == null)
      jcasType.jcas.throwFeatMissing("mention_type", "de.julielab.jules.types.ace.EntityMention");
    jcasType.ll_cas.ll_setStringValue(addr, ((EntityMention_Type)jcasType).casFeatCode_mention_type, v);}    
   
    
  //*--------------*
  //* Feature: mention_ldctype

  /** getter for mention_ldctype - gets 
   * @generated
   * @return value of the feature 
   */
  public String getMention_ldctype() {
    if (EntityMention_Type.featOkTst && ((EntityMention_Type)jcasType).casFeat_mention_ldctype == null)
      jcasType.jcas.throwFeatMissing("mention_ldctype", "de.julielab.jules.types.ace.EntityMention");
    return jcasType.ll_cas.ll_getStringValue(addr, ((EntityMention_Type)jcasType).casFeatCode_mention_ldctype);}
    
  /** setter for mention_ldctype - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setMention_ldctype(String v) {
    if (EntityMention_Type.featOkTst && ((EntityMention_Type)jcasType).casFeat_mention_ldctype == null)
      jcasType.jcas.throwFeatMissing("mention_ldctype", "de.julielab.jules.types.ace.EntityMention");
    jcasType.ll_cas.ll_setStringValue(addr, ((EntityMention_Type)jcasType).casFeatCode_mention_ldctype, v);}    
   
    
  //*--------------*
  //* Feature: entity_ref

  /** getter for entity_ref - gets 
   * @generated
   * @return value of the feature 
   */
  public Entity getEntity_ref() {
    if (EntityMention_Type.featOkTst && ((EntityMention_Type)jcasType).casFeat_entity_ref == null)
      jcasType.jcas.throwFeatMissing("entity_ref", "de.julielab.jules.types.ace.EntityMention");
    return (Entity)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((EntityMention_Type)jcasType).casFeatCode_entity_ref)));}
    
  /** setter for entity_ref - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setEntity_ref(Entity v) {
    if (EntityMention_Type.featOkTst && ((EntityMention_Type)jcasType).casFeat_entity_ref == null)
      jcasType.jcas.throwFeatMissing("entity_ref", "de.julielab.jules.types.ace.EntityMention");
    jcasType.ll_cas.ll_setRefValue(addr, ((EntityMention_Type)jcasType).casFeatCode_entity_ref, jcasType.ll_cas.ll_getFSRef(v));}    
  }

    