

/* First created by JCasGen Wed Oct 19 19:10:28 CEST 2011 */
package de.julielab.jules.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** Describes a word structure, default grammatical features of a noun
 * Updated by JCasGen Wed Jun 04 18:01:58 CEST 2014
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/src/main/resources/typeSystem/bbp-types.xml
 * @generated */
public class NounFeats extends GrammaticalFeats {
  /** @generated
   * @ordered 
   */
  public final static int typeIndexID = JCasRegistry.register(NounFeats.class);
  /** @generated
   * @ordered 
   */
  public final static int type = typeIndexID;
  /** @generated  */
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected NounFeats() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public NounFeats(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public NounFeats(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public NounFeats(JCas jcas, int begin, int end) {
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

  /** getter for gender - gets Gender, C
   * @generated
   * @return value of the feature 
   */
  public String getGender() {
    if (NounFeats_Type.featOkTst && ((NounFeats_Type)jcasType).casFeat_gender == null)
      jcasType.jcas.throwFeatMissing("gender", "de.julielab.jules.types.NounFeats");
    return jcasType.ll_cas.ll_getStringValue(addr, ((NounFeats_Type)jcasType).casFeatCode_gender);}
    
  /** setter for gender - sets Gender, C 
   * @generated
   * @param v value to set into the feature 
   */
  public void setGender(String v) {
    if (NounFeats_Type.featOkTst && ((NounFeats_Type)jcasType).casFeat_gender == null)
      jcasType.jcas.throwFeatMissing("gender", "de.julielab.jules.types.NounFeats");
    jcasType.ll_cas.ll_setStringValue(addr, ((NounFeats_Type)jcasType).casFeatCode_gender, v);}    
   
    
  //*--------------*
  //* Feature: case

  /** getter for case - gets Case. C
   * @generated
   * @return value of the feature 
   */
  public String getCase() {
    if (NounFeats_Type.featOkTst && ((NounFeats_Type)jcasType).casFeat_case == null)
      jcasType.jcas.throwFeatMissing("case", "de.julielab.jules.types.NounFeats");
    return jcasType.ll_cas.ll_getStringValue(addr, ((NounFeats_Type)jcasType).casFeatCode_case);}
    
  /** setter for case - sets Case. C 
   * @generated
   * @param v value to set into the feature 
   */
  public void setCase(String v) {
    if (NounFeats_Type.featOkTst && ((NounFeats_Type)jcasType).casFeat_case == null)
      jcasType.jcas.throwFeatMissing("case", "de.julielab.jules.types.NounFeats");
    jcasType.ll_cas.ll_setStringValue(addr, ((NounFeats_Type)jcasType).casFeatCode_case, v);}    
   
    
  //*--------------*
  //* Feature: number

  /** getter for number - gets Number, C
   * @generated
   * @return value of the feature 
   */
  public String getNumber() {
    if (NounFeats_Type.featOkTst && ((NounFeats_Type)jcasType).casFeat_number == null)
      jcasType.jcas.throwFeatMissing("number", "de.julielab.jules.types.NounFeats");
    return jcasType.ll_cas.ll_getStringValue(addr, ((NounFeats_Type)jcasType).casFeatCode_number);}
    
  /** setter for number - sets Number, C 
   * @generated
   * @param v value to set into the feature 
   */
  public void setNumber(String v) {
    if (NounFeats_Type.featOkTst && ((NounFeats_Type)jcasType).casFeat_number == null)
      jcasType.jcas.throwFeatMissing("number", "de.julielab.jules.types.NounFeats");
    jcasType.ll_cas.ll_setStringValue(addr, ((NounFeats_Type)jcasType).casFeatCode_number, v);}    
  }

    