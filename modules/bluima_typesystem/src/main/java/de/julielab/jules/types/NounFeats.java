

/* First created by JCasGen Sat Mar 07 22:05:57 CET 2015 */
package de.julielab.jules.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** Describes a word structure, default grammatical features of a noun
 * Updated by JCasGen Sat Mar 07 22:05:57 CET 2015
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/target/jcasgen/typesystem.xml
 * @generated */
public class NounFeats extends GrammaticalFeats {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(NounFeats.class);
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
  protected NounFeats() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public NounFeats(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public NounFeats(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
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
  private void readObject() {/*default - does nothing empty block */}
     
 
    
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

    