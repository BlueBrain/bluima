

/* First created by JCasGen Sat Mar 07 22:05:57 CET 2015 */
package de.julielab.jules.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** Describes a word structure, default grammatical features of a verb
 * Updated by JCasGen Sat Mar 07 22:05:57 CET 2015
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/target/jcasgen/typesystem.xml
 * @generated */
public class VerbFeats extends GrammaticalFeats {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(VerbFeats.class);
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
  protected VerbFeats() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public VerbFeats(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public VerbFeats(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
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
  private void readObject() {/*default - does nothing empty block */}
     
 
    
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

    