

/* First created by JCasGen Wed Oct 19 19:10:28 CEST 2011 */
package de.julielab.jules.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.FSArray;


/** Binary Relation between Entitiy Mentions
 * Updated by JCasGen Wed Jun 04 18:01:58 CEST 2014
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/src/main/resources/typeSystem/bbp-types.xml
 * @generated */
public class RelationMention extends ConceptMention {
  /** @generated
   * @ordered 
   */
  public final static int typeIndexID = JCasRegistry.register(RelationMention.class);
  /** @generated
   * @ordered 
   */
  public final static int type = typeIndexID;
  /** @generated  */
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected RelationMention() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public RelationMention(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public RelationMention(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public RelationMention(JCas jcas, int begin, int end) {
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
  private void readObject() {}
     
 
    
  //*--------------*
  //* Feature: tense

  /** getter for tense - gets 
   * @generated
   * @return value of the feature 
   */
  public String getTense() {
    if (RelationMention_Type.featOkTst && ((RelationMention_Type)jcasType).casFeat_tense == null)
      jcasType.jcas.throwFeatMissing("tense", "de.julielab.jules.types.RelationMention");
    return jcasType.ll_cas.ll_getStringValue(addr, ((RelationMention_Type)jcasType).casFeatCode_tense);}
    
  /** setter for tense - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setTense(String v) {
    if (RelationMention_Type.featOkTst && ((RelationMention_Type)jcasType).casFeat_tense == null)
      jcasType.jcas.throwFeatMissing("tense", "de.julielab.jules.types.RelationMention");
    jcasType.ll_cas.ll_setStringValue(addr, ((RelationMention_Type)jcasType).casFeatCode_tense, v);}    
   
    
  //*--------------*
  //* Feature: modality

  /** getter for modality - gets 
   * @generated
   * @return value of the feature 
   */
  public String getModality() {
    if (RelationMention_Type.featOkTst && ((RelationMention_Type)jcasType).casFeat_modality == null)
      jcasType.jcas.throwFeatMissing("modality", "de.julielab.jules.types.RelationMention");
    return jcasType.ll_cas.ll_getStringValue(addr, ((RelationMention_Type)jcasType).casFeatCode_modality);}
    
  /** setter for modality - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setModality(String v) {
    if (RelationMention_Type.featOkTst && ((RelationMention_Type)jcasType).casFeat_modality == null)
      jcasType.jcas.throwFeatMissing("modality", "de.julielab.jules.types.RelationMention");
    jcasType.ll_cas.ll_setStringValue(addr, ((RelationMention_Type)jcasType).casFeatCode_modality, v);}    
   
    
  //*--------------*
  //* Feature: arguments

  /** getter for arguments - gets 
   * @generated
   * @return value of the feature 
   */
  public FSArray getArguments() {
    if (RelationMention_Type.featOkTst && ((RelationMention_Type)jcasType).casFeat_arguments == null)
      jcasType.jcas.throwFeatMissing("arguments", "de.julielab.jules.types.RelationMention");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((RelationMention_Type)jcasType).casFeatCode_arguments)));}
    
  /** setter for arguments - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setArguments(FSArray v) {
    if (RelationMention_Type.featOkTst && ((RelationMention_Type)jcasType).casFeat_arguments == null)
      jcasType.jcas.throwFeatMissing("arguments", "de.julielab.jules.types.RelationMention");
    jcasType.ll_cas.ll_setRefValue(addr, ((RelationMention_Type)jcasType).casFeatCode_arguments, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for arguments - gets an indexed value - 
   * @generated */
  public ArgumentMention getArguments(int i) {
    if (RelationMention_Type.featOkTst && ((RelationMention_Type)jcasType).casFeat_arguments == null)
      jcasType.jcas.throwFeatMissing("arguments", "de.julielab.jules.types.RelationMention");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((RelationMention_Type)jcasType).casFeatCode_arguments), i);
    return (ArgumentMention)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((RelationMention_Type)jcasType).casFeatCode_arguments), i)));}

  /** indexed setter for arguments - sets an indexed value - 
   * @generated */
  public void setArguments(int i, ArgumentMention v) { 
    if (RelationMention_Type.featOkTst && ((RelationMention_Type)jcasType).casFeat_arguments == null)
      jcasType.jcas.throwFeatMissing("arguments", "de.julielab.jules.types.RelationMention");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((RelationMention_Type)jcasType).casFeatCode_arguments), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((RelationMention_Type)jcasType).casFeatCode_arguments), i, jcasType.ll_cas.ll_getFSRef(v));}
   
    
  //*--------------*
  //* Feature: polarity

  /** getter for polarity - gets 
   * @generated
   * @return value of the feature 
   */
  public String getPolarity() {
    if (RelationMention_Type.featOkTst && ((RelationMention_Type)jcasType).casFeat_polarity == null)
      jcasType.jcas.throwFeatMissing("polarity", "de.julielab.jules.types.RelationMention");
    return jcasType.ll_cas.ll_getStringValue(addr, ((RelationMention_Type)jcasType).casFeatCode_polarity);}
    
  /** setter for polarity - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setPolarity(String v) {
    if (RelationMention_Type.featOkTst && ((RelationMention_Type)jcasType).casFeat_polarity == null)
      jcasType.jcas.throwFeatMissing("polarity", "de.julielab.jules.types.RelationMention");
    jcasType.ll_cas.ll_setStringValue(addr, ((RelationMention_Type)jcasType).casFeatCode_polarity, v);}    
  }

    