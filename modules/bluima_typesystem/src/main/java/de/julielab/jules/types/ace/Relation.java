

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
public class Relation extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Relation.class);
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
  protected Relation() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Relation(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Relation(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Relation(JCas jcas, int begin, int end) {
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
    if (Relation_Type.featOkTst && ((Relation_Type)jcasType).casFeat_tense == null)
      jcasType.jcas.throwFeatMissing("tense", "de.julielab.jules.types.ace.Relation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Relation_Type)jcasType).casFeatCode_tense);}
    
  /** setter for tense - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setTense(String v) {
    if (Relation_Type.featOkTst && ((Relation_Type)jcasType).casFeat_tense == null)
      jcasType.jcas.throwFeatMissing("tense", "de.julielab.jules.types.ace.Relation");
    jcasType.ll_cas.ll_setStringValue(addr, ((Relation_Type)jcasType).casFeatCode_tense, v);}    
   
    
  //*--------------*
  //* Feature: ace_subtype

  /** getter for ace_subtype - gets 
   * @generated
   * @return value of the feature 
   */
  public String getAce_subtype() {
    if (Relation_Type.featOkTst && ((Relation_Type)jcasType).casFeat_ace_subtype == null)
      jcasType.jcas.throwFeatMissing("ace_subtype", "de.julielab.jules.types.ace.Relation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Relation_Type)jcasType).casFeatCode_ace_subtype);}
    
  /** setter for ace_subtype - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setAce_subtype(String v) {
    if (Relation_Type.featOkTst && ((Relation_Type)jcasType).casFeat_ace_subtype == null)
      jcasType.jcas.throwFeatMissing("ace_subtype", "de.julielab.jules.types.ace.Relation");
    jcasType.ll_cas.ll_setStringValue(addr, ((Relation_Type)jcasType).casFeatCode_ace_subtype, v);}    
   
    
  //*--------------*
  //* Feature: modality

  /** getter for modality - gets 
   * @generated
   * @return value of the feature 
   */
  public String getModality() {
    if (Relation_Type.featOkTst && ((Relation_Type)jcasType).casFeat_modality == null)
      jcasType.jcas.throwFeatMissing("modality", "de.julielab.jules.types.ace.Relation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Relation_Type)jcasType).casFeatCode_modality);}
    
  /** setter for modality - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setModality(String v) {
    if (Relation_Type.featOkTst && ((Relation_Type)jcasType).casFeat_modality == null)
      jcasType.jcas.throwFeatMissing("modality", "de.julielab.jules.types.ace.Relation");
    jcasType.ll_cas.ll_setStringValue(addr, ((Relation_Type)jcasType).casFeatCode_modality, v);}    
   
    
  //*--------------*
  //* Feature: ace_type

  /** getter for ace_type - gets 
   * @generated
   * @return value of the feature 
   */
  public String getAce_type() {
    if (Relation_Type.featOkTst && ((Relation_Type)jcasType).casFeat_ace_type == null)
      jcasType.jcas.throwFeatMissing("ace_type", "de.julielab.jules.types.ace.Relation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Relation_Type)jcasType).casFeatCode_ace_type);}
    
  /** setter for ace_type - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setAce_type(String v) {
    if (Relation_Type.featOkTst && ((Relation_Type)jcasType).casFeat_ace_type == null)
      jcasType.jcas.throwFeatMissing("ace_type", "de.julielab.jules.types.ace.Relation");
    jcasType.ll_cas.ll_setStringValue(addr, ((Relation_Type)jcasType).casFeatCode_ace_type, v);}    
   
    
  //*--------------*
  //* Feature: mentions

  /** getter for mentions - gets 
   * @generated
   * @return value of the feature 
   */
  public FSArray getMentions() {
    if (Relation_Type.featOkTst && ((Relation_Type)jcasType).casFeat_mentions == null)
      jcasType.jcas.throwFeatMissing("mentions", "de.julielab.jules.types.ace.Relation");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Relation_Type)jcasType).casFeatCode_mentions)));}
    
  /** setter for mentions - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setMentions(FSArray v) {
    if (Relation_Type.featOkTst && ((Relation_Type)jcasType).casFeat_mentions == null)
      jcasType.jcas.throwFeatMissing("mentions", "de.julielab.jules.types.ace.Relation");
    jcasType.ll_cas.ll_setRefValue(addr, ((Relation_Type)jcasType).casFeatCode_mentions, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for mentions - gets an indexed value - 
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public RelationMention getMentions(int i) {
    if (Relation_Type.featOkTst && ((Relation_Type)jcasType).casFeat_mentions == null)
      jcasType.jcas.throwFeatMissing("mentions", "de.julielab.jules.types.ace.Relation");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Relation_Type)jcasType).casFeatCode_mentions), i);
    return (RelationMention)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Relation_Type)jcasType).casFeatCode_mentions), i)));}

  /** indexed setter for mentions - sets an indexed value - 
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setMentions(int i, RelationMention v) { 
    if (Relation_Type.featOkTst && ((Relation_Type)jcasType).casFeat_mentions == null)
      jcasType.jcas.throwFeatMissing("mentions", "de.julielab.jules.types.ace.Relation");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Relation_Type)jcasType).casFeatCode_mentions), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Relation_Type)jcasType).casFeatCode_mentions), i, jcasType.ll_cas.ll_getFSRef(v));}
   
    
  //*--------------*
  //* Feature: arguments

  /** getter for arguments - gets 
   * @generated
   * @return value of the feature 
   */
  public FSArray getArguments() {
    if (Relation_Type.featOkTst && ((Relation_Type)jcasType).casFeat_arguments == null)
      jcasType.jcas.throwFeatMissing("arguments", "de.julielab.jules.types.ace.Relation");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Relation_Type)jcasType).casFeatCode_arguments)));}
    
  /** setter for arguments - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setArguments(FSArray v) {
    if (Relation_Type.featOkTst && ((Relation_Type)jcasType).casFeat_arguments == null)
      jcasType.jcas.throwFeatMissing("arguments", "de.julielab.jules.types.ace.Relation");
    jcasType.ll_cas.ll_setRefValue(addr, ((Relation_Type)jcasType).casFeatCode_arguments, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for arguments - gets an indexed value - 
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public RelationArgument getArguments(int i) {
    if (Relation_Type.featOkTst && ((Relation_Type)jcasType).casFeat_arguments == null)
      jcasType.jcas.throwFeatMissing("arguments", "de.julielab.jules.types.ace.Relation");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Relation_Type)jcasType).casFeatCode_arguments), i);
    return (RelationArgument)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Relation_Type)jcasType).casFeatCode_arguments), i)));}

  /** indexed setter for arguments - sets an indexed value - 
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setArguments(int i, RelationArgument v) { 
    if (Relation_Type.featOkTst && ((Relation_Type)jcasType).casFeat_arguments == null)
      jcasType.jcas.throwFeatMissing("arguments", "de.julielab.jules.types.ace.Relation");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Relation_Type)jcasType).casFeatCode_arguments), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Relation_Type)jcasType).casFeatCode_arguments), i, jcasType.ll_cas.ll_getFSRef(v));}
  }

    