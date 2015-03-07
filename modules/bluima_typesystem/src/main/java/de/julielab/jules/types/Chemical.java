

/* First created by JCasGen Sat Mar 07 22:05:57 CET 2015 */
package de.julielab.jules.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** A chemical type
 * Updated by JCasGen Sat Mar 07 22:05:57 CET 2015
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/target/jcasgen/typesystem.xml
 * @generated */
public class Chemical extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Chemical.class);
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
  protected Chemical() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Chemical(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Chemical(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Chemical(JCas jcas, int begin, int end) {
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
  //* Feature: registryNumber

  /** getter for registryNumber - gets A unique 5 to 9 digit number in hyphenated format assigned by the Chemical Abstract Service to specify chemical substances, a zero is a valid number when an actual number cannot be located or is not yet available., C
   * @generated
   * @return value of the feature 
   */
  public String getRegistryNumber() {
    if (Chemical_Type.featOkTst && ((Chemical_Type)jcasType).casFeat_registryNumber == null)
      jcasType.jcas.throwFeatMissing("registryNumber", "de.julielab.jules.types.Chemical");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Chemical_Type)jcasType).casFeatCode_registryNumber);}
    
  /** setter for registryNumber - sets A unique 5 to 9 digit number in hyphenated format assigned by the Chemical Abstract Service to specify chemical substances, a zero is a valid number when an actual number cannot be located or is not yet available., C 
   * @generated
   * @param v value to set into the feature 
   */
  public void setRegistryNumber(String v) {
    if (Chemical_Type.featOkTst && ((Chemical_Type)jcasType).casFeat_registryNumber == null)
      jcasType.jcas.throwFeatMissing("registryNumber", "de.julielab.jules.types.Chemical");
    jcasType.ll_cas.ll_setStringValue(addr, ((Chemical_Type)jcasType).casFeatCode_registryNumber, v);}    
   
    
  //*--------------*
  //* Feature: nameOfSubstance

  /** getter for nameOfSubstance - gets The name of the substance that the registry number or the E.C. number identifies, C
   * @generated
   * @return value of the feature 
   */
  public String getNameOfSubstance() {
    if (Chemical_Type.featOkTst && ((Chemical_Type)jcasType).casFeat_nameOfSubstance == null)
      jcasType.jcas.throwFeatMissing("nameOfSubstance", "de.julielab.jules.types.Chemical");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Chemical_Type)jcasType).casFeatCode_nameOfSubstance);}
    
  /** setter for nameOfSubstance - sets The name of the substance that the registry number or the E.C. number identifies, C 
   * @generated
   * @param v value to set into the feature 
   */
  public void setNameOfSubstance(String v) {
    if (Chemical_Type.featOkTst && ((Chemical_Type)jcasType).casFeat_nameOfSubstance == null)
      jcasType.jcas.throwFeatMissing("nameOfSubstance", "de.julielab.jules.types.Chemical");
    jcasType.ll_cas.ll_setStringValue(addr, ((Chemical_Type)jcasType).casFeatCode_nameOfSubstance, v);}    
  }

    