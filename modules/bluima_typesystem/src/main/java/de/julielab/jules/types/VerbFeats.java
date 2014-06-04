

/* First created by JCasGen Wed Oct 19 19:10:28 CEST 2011 */
package de.julielab.jules.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** Describes a word structure, default grammatical features of a verb
 * Updated by JCasGen Wed Jun 04 18:01:59 CEST 2014
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/src/main/resources/typeSystem/bbp-types.xml
 * @generated */
public class VerbFeats extends GrammaticalFeats {
  /** @generated
   * @ordered 
   */
  public final static int typeIndexID = JCasRegistry.register(VerbFeats.class);
  /** @generated
   * @ordered 
   */
  public final static int type = typeIndexID;
  /** @generated  */
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected VerbFeats() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public VerbFeats(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public VerbFeats(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public VerbFeats(JCas jcas, int begin, int end) {
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

  /** getter for tense - gets Tense
   * @generated
   * @return value of the feature 
   */
  public String getTense() {
    if (VerbFeats_Type.featOkTst && ((VerbFeats_Type)jcasType).casFeat_tense == null)
      jcasType.jcas.throwFeatMissing("tense", "de.julielab.jules.types.VerbFeats");
    return jcasType.ll_cas.ll_getStringValue(addr, ((VerbFeats_Type)jcasType).casFeatCode_tense);}
    
  /** setter for tense - sets Tense 
   * @generated
   * @param v value to set into the feature 
   */
  public void setTense(String v) {
    if (VerbFeats_Type.featOkTst && ((VerbFeats_Type)jcasType).casFeat_tense == null)
      jcasType.jcas.throwFeatMissing("tense", "de.julielab.jules.types.VerbFeats");
    jcasType.ll_cas.ll_setStringValue(addr, ((VerbFeats_Type)jcasType).casFeatCode_tense, v);}    
   
    
  //*--------------*
  //* Feature: person

  /** getter for person - gets Person
   * @generated
   * @return value of the feature 
   */
  public String getPerson() {
    if (VerbFeats_Type.featOkTst && ((VerbFeats_Type)jcasType).casFeat_person == null)
      jcasType.jcas.throwFeatMissing("person", "de.julielab.jules.types.VerbFeats");
    return jcasType.ll_cas.ll_getStringValue(addr, ((VerbFeats_Type)jcasType).casFeatCode_person);}
    
  /** setter for person - sets Person 
   * @generated
   * @param v value to set into the feature 
   */
  public void setPerson(String v) {
    if (VerbFeats_Type.featOkTst && ((VerbFeats_Type)jcasType).casFeat_person == null)
      jcasType.jcas.throwFeatMissing("person", "de.julielab.jules.types.VerbFeats");
    jcasType.ll_cas.ll_setStringValue(addr, ((VerbFeats_Type)jcasType).casFeatCode_person, v);}    
   
    
  //*--------------*
  //* Feature: number

  /** getter for number - gets Number
   * @generated
   * @return value of the feature 
   */
  public String getNumber() {
    if (VerbFeats_Type.featOkTst && ((VerbFeats_Type)jcasType).casFeat_number == null)
      jcasType.jcas.throwFeatMissing("number", "de.julielab.jules.types.VerbFeats");
    return jcasType.ll_cas.ll_getStringValue(addr, ((VerbFeats_Type)jcasType).casFeatCode_number);}
    
  /** setter for number - sets Number 
   * @generated
   * @param v value to set into the feature 
   */
  public void setNumber(String v) {
    if (VerbFeats_Type.featOkTst && ((VerbFeats_Type)jcasType).casFeat_number == null)
      jcasType.jcas.throwFeatMissing("number", "de.julielab.jules.types.VerbFeats");
    jcasType.ll_cas.ll_setStringValue(addr, ((VerbFeats_Type)jcasType).casFeatCode_number, v);}    
   
    
  //*--------------*
  //* Feature: voice

  /** getter for voice - gets Voice
   * @generated
   * @return value of the feature 
   */
  public String getVoice() {
    if (VerbFeats_Type.featOkTst && ((VerbFeats_Type)jcasType).casFeat_voice == null)
      jcasType.jcas.throwFeatMissing("voice", "de.julielab.jules.types.VerbFeats");
    return jcasType.ll_cas.ll_getStringValue(addr, ((VerbFeats_Type)jcasType).casFeatCode_voice);}
    
  /** setter for voice - sets Voice 
   * @generated
   * @param v value to set into the feature 
   */
  public void setVoice(String v) {
    if (VerbFeats_Type.featOkTst && ((VerbFeats_Type)jcasType).casFeat_voice == null)
      jcasType.jcas.throwFeatMissing("voice", "de.julielab.jules.types.VerbFeats");
    jcasType.ll_cas.ll_setStringValue(addr, ((VerbFeats_Type)jcasType).casFeatCode_voice, v);}    
   
    
  //*--------------*
  //* Feature: aspect

  /** getter for aspect - gets Aspect
   * @generated
   * @return value of the feature 
   */
  public String getAspect() {
    if (VerbFeats_Type.featOkTst && ((VerbFeats_Type)jcasType).casFeat_aspect == null)
      jcasType.jcas.throwFeatMissing("aspect", "de.julielab.jules.types.VerbFeats");
    return jcasType.ll_cas.ll_getStringValue(addr, ((VerbFeats_Type)jcasType).casFeatCode_aspect);}
    
  /** setter for aspect - sets Aspect 
   * @generated
   * @param v value to set into the feature 
   */
  public void setAspect(String v) {
    if (VerbFeats_Type.featOkTst && ((VerbFeats_Type)jcasType).casFeat_aspect == null)
      jcasType.jcas.throwFeatMissing("aspect", "de.julielab.jules.types.VerbFeats");
    jcasType.ll_cas.ll_setStringValue(addr, ((VerbFeats_Type)jcasType).casFeatCode_aspect, v);}    
  }

    