

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
public class RelationMention extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(RelationMention.class);
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
  protected RelationMention() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public RelationMention(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public RelationMention(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
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
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: lexical_condition

  /** getter for lexical_condition - gets 
   * @generated
   * @return value of the feature 
   */
  public String getLexical_condition() {
    if (RelationMention_Type.featOkTst && ((RelationMention_Type)jcasType).casFeat_lexical_condition == null)
      jcasType.jcas.throwFeatMissing("lexical_condition", "de.julielab.jules.types.ace.RelationMention");
    return jcasType.ll_cas.ll_getStringValue(addr, ((RelationMention_Type)jcasType).casFeatCode_lexical_condition);}
    
  /** setter for lexical_condition - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setLexical_condition(String v) {
    if (RelationMention_Type.featOkTst && ((RelationMention_Type)jcasType).casFeat_lexical_condition == null)
      jcasType.jcas.throwFeatMissing("lexical_condition", "de.julielab.jules.types.ace.RelationMention");
    jcasType.ll_cas.ll_setStringValue(addr, ((RelationMention_Type)jcasType).casFeatCode_lexical_condition, v);}    
   
    
  //*--------------*
  //* Feature: arguments

  /** getter for arguments - gets 
   * @generated
   * @return value of the feature 
   */
  public FSArray getArguments() {
    if (RelationMention_Type.featOkTst && ((RelationMention_Type)jcasType).casFeat_arguments == null)
      jcasType.jcas.throwFeatMissing("arguments", "de.julielab.jules.types.ace.RelationMention");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((RelationMention_Type)jcasType).casFeatCode_arguments)));}
    
  /** setter for arguments - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setArguments(FSArray v) {
    if (RelationMention_Type.featOkTst && ((RelationMention_Type)jcasType).casFeat_arguments == null)
      jcasType.jcas.throwFeatMissing("arguments", "de.julielab.jules.types.ace.RelationMention");
    jcasType.ll_cas.ll_setRefValue(addr, ((RelationMention_Type)jcasType).casFeatCode_arguments, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for arguments - gets an indexed value - 
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public RelationMentionArgument getArguments(int i) {
    if (RelationMention_Type.featOkTst && ((RelationMention_Type)jcasType).casFeat_arguments == null)
      jcasType.jcas.throwFeatMissing("arguments", "de.julielab.jules.types.ace.RelationMention");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((RelationMention_Type)jcasType).casFeatCode_arguments), i);
    return (RelationMentionArgument)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((RelationMention_Type)jcasType).casFeatCode_arguments), i)));}

  /** indexed setter for arguments - sets an indexed value - 
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setArguments(int i, RelationMentionArgument v) { 
    if (RelationMention_Type.featOkTst && ((RelationMention_Type)jcasType).casFeat_arguments == null)
      jcasType.jcas.throwFeatMissing("arguments", "de.julielab.jules.types.ace.RelationMention");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((RelationMention_Type)jcasType).casFeatCode_arguments), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((RelationMention_Type)jcasType).casFeatCode_arguments), i, jcasType.ll_cas.ll_getFSRef(v));}
   
    
  //*--------------*
  //* Feature: relation_ref

  /** getter for relation_ref - gets 
   * @generated
   * @return value of the feature 
   */
  public Relation getRelation_ref() {
    if (RelationMention_Type.featOkTst && ((RelationMention_Type)jcasType).casFeat_relation_ref == null)
      jcasType.jcas.throwFeatMissing("relation_ref", "de.julielab.jules.types.ace.RelationMention");
    return (Relation)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((RelationMention_Type)jcasType).casFeatCode_relation_ref)));}
    
  /** setter for relation_ref - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setRelation_ref(Relation v) {
    if (RelationMention_Type.featOkTst && ((RelationMention_Type)jcasType).casFeat_relation_ref == null)
      jcasType.jcas.throwFeatMissing("relation_ref", "de.julielab.jules.types.ace.RelationMention");
    jcasType.ll_cas.ll_setRefValue(addr, ((RelationMention_Type)jcasType).casFeatCode_relation_ref, jcasType.ll_cas.ll_getFSRef(v));}    
  }

    