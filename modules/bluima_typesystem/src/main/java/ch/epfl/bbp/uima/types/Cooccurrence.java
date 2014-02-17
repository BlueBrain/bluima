

/* First created by JCasGen Mon Sep 30 16:58:18 CEST 2013 */
package ch.epfl.bbp.uima.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


import org.apache.uima.jcas.cas.StringArray;


/** A coocurrence btw two annotations.
 * Updated by JCasGen Mon Feb 17 10:49:39 CET 2014
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/src/main/resources/typeSystem/bbp-types.xml
 * @generated */
public class Cooccurrence extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Cooccurrence.class);
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
  protected Cooccurrence() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public Cooccurrence(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public Cooccurrence(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public Cooccurrence(JCas jcas, int begin, int end) {
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
  //* Feature: firstEntity

  /** getter for firstEntity - gets 
   * @generated */
  public Annotation getFirstEntity() {
    if (Cooccurrence_Type.featOkTst && ((Cooccurrence_Type)jcasType).casFeat_firstEntity == null)
      jcasType.jcas.throwFeatMissing("firstEntity", "ch.epfl.bbp.uima.types.Cooccurrence");
    return (Annotation)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Cooccurrence_Type)jcasType).casFeatCode_firstEntity)));}
    
  /** setter for firstEntity - sets  
   * @generated */
  public void setFirstEntity(Annotation v) {
    if (Cooccurrence_Type.featOkTst && ((Cooccurrence_Type)jcasType).casFeat_firstEntity == null)
      jcasType.jcas.throwFeatMissing("firstEntity", "ch.epfl.bbp.uima.types.Cooccurrence");
    jcasType.ll_cas.ll_setRefValue(addr, ((Cooccurrence_Type)jcasType).casFeatCode_firstEntity, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: secondEntity

  /** getter for secondEntity - gets 
   * @generated */
  public Annotation getSecondEntity() {
    if (Cooccurrence_Type.featOkTst && ((Cooccurrence_Type)jcasType).casFeat_secondEntity == null)
      jcasType.jcas.throwFeatMissing("secondEntity", "ch.epfl.bbp.uima.types.Cooccurrence");
    return (Annotation)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Cooccurrence_Type)jcasType).casFeatCode_secondEntity)));}
    
  /** setter for secondEntity - sets  
   * @generated */
  public void setSecondEntity(Annotation v) {
    if (Cooccurrence_Type.featOkTst && ((Cooccurrence_Type)jcasType).casFeat_secondEntity == null)
      jcasType.jcas.throwFeatMissing("secondEntity", "ch.epfl.bbp.uima.types.Cooccurrence");
    jcasType.ll_cas.ll_setRefValue(addr, ((Cooccurrence_Type)jcasType).casFeatCode_secondEntity, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: snippetBegin

  /** getter for snippetBegin - gets 
   * @generated */
  public int getSnippetBegin() {
    if (Cooccurrence_Type.featOkTst && ((Cooccurrence_Type)jcasType).casFeat_snippetBegin == null)
      jcasType.jcas.throwFeatMissing("snippetBegin", "ch.epfl.bbp.uima.types.Cooccurrence");
    return jcasType.ll_cas.ll_getIntValue(addr, ((Cooccurrence_Type)jcasType).casFeatCode_snippetBegin);}
    
  /** setter for snippetBegin - sets  
   * @generated */
  public void setSnippetBegin(int v) {
    if (Cooccurrence_Type.featOkTst && ((Cooccurrence_Type)jcasType).casFeat_snippetBegin == null)
      jcasType.jcas.throwFeatMissing("snippetBegin", "ch.epfl.bbp.uima.types.Cooccurrence");
    jcasType.ll_cas.ll_setIntValue(addr, ((Cooccurrence_Type)jcasType).casFeatCode_snippetBegin, v);}    
   
    
  //*--------------*
  //* Feature: snippetEnd

  /** getter for snippetEnd - gets 
   * @generated */
  public int getSnippetEnd() {
    if (Cooccurrence_Type.featOkTst && ((Cooccurrence_Type)jcasType).casFeat_snippetEnd == null)
      jcasType.jcas.throwFeatMissing("snippetEnd", "ch.epfl.bbp.uima.types.Cooccurrence");
    return jcasType.ll_cas.ll_getIntValue(addr, ((Cooccurrence_Type)jcasType).casFeatCode_snippetEnd);}
    
  /** setter for snippetEnd - sets  
   * @generated */
  public void setSnippetEnd(int v) {
    if (Cooccurrence_Type.featOkTst && ((Cooccurrence_Type)jcasType).casFeat_snippetEnd == null)
      jcasType.jcas.throwFeatMissing("snippetEnd", "ch.epfl.bbp.uima.types.Cooccurrence");
    jcasType.ll_cas.ll_setIntValue(addr, ((Cooccurrence_Type)jcasType).casFeatCode_snippetEnd, v);}    
   
    
  //*--------------*
  //* Feature: firstIds

  /** getter for firstIds - gets A list of string ids to identify the first occurrence
   * @generated */
  public StringArray getFirstIds() {
    if (Cooccurrence_Type.featOkTst && ((Cooccurrence_Type)jcasType).casFeat_firstIds == null)
      jcasType.jcas.throwFeatMissing("firstIds", "ch.epfl.bbp.uima.types.Cooccurrence");
    return (StringArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Cooccurrence_Type)jcasType).casFeatCode_firstIds)));}
    
  /** setter for firstIds - sets A list of string ids to identify the first occurrence 
   * @generated */
  public void setFirstIds(StringArray v) {
    if (Cooccurrence_Type.featOkTst && ((Cooccurrence_Type)jcasType).casFeat_firstIds == null)
      jcasType.jcas.throwFeatMissing("firstIds", "ch.epfl.bbp.uima.types.Cooccurrence");
    jcasType.ll_cas.ll_setRefValue(addr, ((Cooccurrence_Type)jcasType).casFeatCode_firstIds, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for firstIds - gets an indexed value - A list of string ids to identify the first occurrence
   * @generated */
  public String getFirstIds(int i) {
    if (Cooccurrence_Type.featOkTst && ((Cooccurrence_Type)jcasType).casFeat_firstIds == null)
      jcasType.jcas.throwFeatMissing("firstIds", "ch.epfl.bbp.uima.types.Cooccurrence");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Cooccurrence_Type)jcasType).casFeatCode_firstIds), i);
    return jcasType.ll_cas.ll_getStringArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Cooccurrence_Type)jcasType).casFeatCode_firstIds), i);}

  /** indexed setter for firstIds - sets an indexed value - A list of string ids to identify the first occurrence
   * @generated */
  public void setFirstIds(int i, String v) { 
    if (Cooccurrence_Type.featOkTst && ((Cooccurrence_Type)jcasType).casFeat_firstIds == null)
      jcasType.jcas.throwFeatMissing("firstIds", "ch.epfl.bbp.uima.types.Cooccurrence");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Cooccurrence_Type)jcasType).casFeatCode_firstIds), i);
    jcasType.ll_cas.ll_setStringArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Cooccurrence_Type)jcasType).casFeatCode_firstIds), i, v);}
   
    
  //*--------------*
  //* Feature: secondIds

  /** getter for secondIds - gets a list of string ids to identify the second occurrence
   * @generated */
  public StringArray getSecondIds() {
    if (Cooccurrence_Type.featOkTst && ((Cooccurrence_Type)jcasType).casFeat_secondIds == null)
      jcasType.jcas.throwFeatMissing("secondIds", "ch.epfl.bbp.uima.types.Cooccurrence");
    return (StringArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Cooccurrence_Type)jcasType).casFeatCode_secondIds)));}
    
  /** setter for secondIds - sets a list of string ids to identify the second occurrence 
   * @generated */
  public void setSecondIds(StringArray v) {
    if (Cooccurrence_Type.featOkTst && ((Cooccurrence_Type)jcasType).casFeat_secondIds == null)
      jcasType.jcas.throwFeatMissing("secondIds", "ch.epfl.bbp.uima.types.Cooccurrence");
    jcasType.ll_cas.ll_setRefValue(addr, ((Cooccurrence_Type)jcasType).casFeatCode_secondIds, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for secondIds - gets an indexed value - a list of string ids to identify the second occurrence
   * @generated */
  public String getSecondIds(int i) {
    if (Cooccurrence_Type.featOkTst && ((Cooccurrence_Type)jcasType).casFeat_secondIds == null)
      jcasType.jcas.throwFeatMissing("secondIds", "ch.epfl.bbp.uima.types.Cooccurrence");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Cooccurrence_Type)jcasType).casFeatCode_secondIds), i);
    return jcasType.ll_cas.ll_getStringArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Cooccurrence_Type)jcasType).casFeatCode_secondIds), i);}

  /** indexed setter for secondIds - sets an indexed value - a list of string ids to identify the second occurrence
   * @generated */
  public void setSecondIds(int i, String v) { 
    if (Cooccurrence_Type.featOkTst && ((Cooccurrence_Type)jcasType).casFeat_secondIds == null)
      jcasType.jcas.throwFeatMissing("secondIds", "ch.epfl.bbp.uima.types.Cooccurrence");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Cooccurrence_Type)jcasType).casFeatCode_secondIds), i);
    jcasType.ll_cas.ll_setStringArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Cooccurrence_Type)jcasType).casFeatCode_secondIds), i, v);}
   
    
  //*--------------*
  //* Feature: cooccurrenceType

  /** getter for cooccurrenceType - gets 
   * @generated */
  public String getCooccurrenceType() {
    if (Cooccurrence_Type.featOkTst && ((Cooccurrence_Type)jcasType).casFeat_cooccurrenceType == null)
      jcasType.jcas.throwFeatMissing("cooccurrenceType", "ch.epfl.bbp.uima.types.Cooccurrence");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Cooccurrence_Type)jcasType).casFeatCode_cooccurrenceType);}
    
  /** setter for cooccurrenceType - sets  
   * @generated */
  public void setCooccurrenceType(String v) {
    if (Cooccurrence_Type.featOkTst && ((Cooccurrence_Type)jcasType).casFeat_cooccurrenceType == null)
      jcasType.jcas.throwFeatMissing("cooccurrenceType", "ch.epfl.bbp.uima.types.Cooccurrence");
    jcasType.ll_cas.ll_setStringValue(addr, ((Cooccurrence_Type)jcasType).casFeatCode_cooccurrenceType, v);}    
   
    
  //*--------------*
  //* Feature: confidence

  /** getter for confidence - gets To which degree we are confident about this being a true co-occurrence
   * @generated */
  public float getConfidence() {
    if (Cooccurrence_Type.featOkTst && ((Cooccurrence_Type)jcasType).casFeat_confidence == null)
      jcasType.jcas.throwFeatMissing("confidence", "ch.epfl.bbp.uima.types.Cooccurrence");
    return jcasType.ll_cas.ll_getFloatValue(addr, ((Cooccurrence_Type)jcasType).casFeatCode_confidence);}
    
  /** setter for confidence - sets To which degree we are confident about this being a true co-occurrence 
   * @generated */
  public void setConfidence(float v) {
    if (Cooccurrence_Type.featOkTst && ((Cooccurrence_Type)jcasType).casFeat_confidence == null)
      jcasType.jcas.throwFeatMissing("confidence", "ch.epfl.bbp.uima.types.Cooccurrence");
    jcasType.ll_cas.ll_setFloatValue(addr, ((Cooccurrence_Type)jcasType).casFeatCode_confidence, v);}    
   
    
  //*--------------*
  //* Feature: hasInteraction

  /** getter for hasInteraction - gets whether this cooccurrence signals an interaction between the two entities. This is relevant for the Whitetext corpus in particular.
   * @generated */
  public boolean getHasInteraction() {
    if (Cooccurrence_Type.featOkTst && ((Cooccurrence_Type)jcasType).casFeat_hasInteraction == null)
      jcasType.jcas.throwFeatMissing("hasInteraction", "ch.epfl.bbp.uima.types.Cooccurrence");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((Cooccurrence_Type)jcasType).casFeatCode_hasInteraction);}
    
  /** setter for hasInteraction - sets whether this cooccurrence signals an interaction between the two entities. This is relevant for the Whitetext corpus in particular. 
   * @generated */
  public void setHasInteraction(boolean v) {
    if (Cooccurrence_Type.featOkTst && ((Cooccurrence_Type)jcasType).casFeat_hasInteraction == null)
      jcasType.jcas.throwFeatMissing("hasInteraction", "ch.epfl.bbp.uima.types.Cooccurrence");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((Cooccurrence_Type)jcasType).casFeatCode_hasInteraction, v);}    
  }

    