

/* First created by JCasGen Wed Oct 19 19:10:28 CEST 2011 */
package de.julielab.jules.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** Describes a word structure, default grammatical features of  pronouns
 * Updated by JCasGen Wed Jun 04 18:01:58 CEST 2014
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/src/main/resources/typeSystem/bbp-types.xml
 * @generated */
public class PronounFeats extends GrammaticalFeats {
  /** @generated
   * @ordered 
   */
  public final static int typeIndexID = JCasRegistry.register(PronounFeats.class);
  /** @generated
   * @ordered 
   */
  public final static int type = typeIndexID;
  /** @generated  */
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected PronounFeats() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public PronounFeats(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public PronounFeats(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public PronounFeats(JCas jcas, int begin, int end) {
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
  //* Feature: gender

  /** getter for gender - gets Gender
   * @generated
   * @return value of the feature 
   */
  public String getGender() {
    if (PronounFeats_Type.featOkTst && ((PronounFeats_Type)jcasType).casFeat_gender == null)
      jcasType.jcas.throwFeatMissing("gender", "de.julielab.jules.types.PronounFeats");
    return jcasType.ll_cas.ll_getStringValue(addr, ((PronounFeats_Type)jcasType).casFeatCode_gender);}
    
  /** setter for gender - sets Gender 
   * @generated
   * @param v value to set into the feature 
   */
  public void setGender(String v) {
    if (PronounFeats_Type.featOkTst && ((PronounFeats_Type)jcasType).casFeat_gender == null)
      jcasType.jcas.throwFeatMissing("gender", "de.julielab.jules.types.PronounFeats");
    jcasType.ll_cas.ll_setStringValue(addr, ((PronounFeats_Type)jcasType).casFeatCode_gender, v);}    
   
    
  //*--------------*
  //* Feature: case

  /** getter for case - gets Case
   * @generated
   * @return value of the feature 
   */
  public String getCase() {
    if (PronounFeats_Type.featOkTst && ((PronounFeats_Type)jcasType).casFeat_case == null)
      jcasType.jcas.throwFeatMissing("case", "de.julielab.jules.types.PronounFeats");
    return jcasType.ll_cas.ll_getStringValue(addr, ((PronounFeats_Type)jcasType).casFeatCode_case);}
    
  /** setter for case - sets Case 
   * @generated
   * @param v value to set into the feature 
   */
  public void setCase(String v) {
    if (PronounFeats_Type.featOkTst && ((PronounFeats_Type)jcasType).casFeat_case == null)
      jcasType.jcas.throwFeatMissing("case", "de.julielab.jules.types.PronounFeats");
    jcasType.ll_cas.ll_setStringValue(addr, ((PronounFeats_Type)jcasType).casFeatCode_case, v);}    
   
    
  //*--------------*
  //* Feature: number

  /** getter for number - gets Number
   * @generated
   * @return value of the feature 
   */
  public String getNumber() {
    if (PronounFeats_Type.featOkTst && ((PronounFeats_Type)jcasType).casFeat_number == null)
      jcasType.jcas.throwFeatMissing("number", "de.julielab.jules.types.PronounFeats");
    return jcasType.ll_cas.ll_getStringValue(addr, ((PronounFeats_Type)jcasType).casFeatCode_number);}
    
  /** setter for number - sets Number 
   * @generated
   * @param v value to set into the feature 
   */
  public void setNumber(String v) {
    if (PronounFeats_Type.featOkTst && ((PronounFeats_Type)jcasType).casFeat_number == null)
      jcasType.jcas.throwFeatMissing("number", "de.julielab.jules.types.PronounFeats");
    jcasType.ll_cas.ll_setStringValue(addr, ((PronounFeats_Type)jcasType).casFeatCode_number, v);}    
   
    
  //*--------------*
  //* Feature: person

  /** getter for person - gets Person
   * @generated
   * @return value of the feature 
   */
  public String getPerson() {
    if (PronounFeats_Type.featOkTst && ((PronounFeats_Type)jcasType).casFeat_person == null)
      jcasType.jcas.throwFeatMissing("person", "de.julielab.jules.types.PronounFeats");
    return jcasType.ll_cas.ll_getStringValue(addr, ((PronounFeats_Type)jcasType).casFeatCode_person);}
    
  /** setter for person - sets Person 
   * @generated
   * @param v value to set into the feature 
   */
  public void setPerson(String v) {
    if (PronounFeats_Type.featOkTst && ((PronounFeats_Type)jcasType).casFeat_person == null)
      jcasType.jcas.throwFeatMissing("person", "de.julielab.jules.types.PronounFeats");
    jcasType.ll_cas.ll_setStringValue(addr, ((PronounFeats_Type)jcasType).casFeatCode_person, v);}    
  }

    