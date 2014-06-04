

/* First created by JCasGen Mon Feb 17 12:26:53 CET 2014 */
package ch.epfl.bbp.uima.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** a measure, e.g. 40g
 * Updated by JCasGen Wed Jun 04 18:01:56 CEST 2014
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/src/main/resources/typeSystem/bbp-types.xml
 * @generated */
public class Measure extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Measure.class);
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
  protected Measure() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public Measure(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public Measure(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public Measure(JCas jcas, int begin, int end) {
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
  //* Feature: textValue

  /** getter for textValue - gets the complete surface form of this measure
   * @generated
   * @return value of the feature 
   */
  public String getTextValue() {
    if (Measure_Type.featOkTst && ((Measure_Type)jcasType).casFeat_textValue == null)
      jcasType.jcas.throwFeatMissing("textValue", "ch.epfl.bbp.uima.types.Measure");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Measure_Type)jcasType).casFeatCode_textValue);}
    
  /** setter for textValue - sets the complete surface form of this measure 
   * @generated
   * @param v value to set into the feature 
   */
  public void setTextValue(String v) {
    if (Measure_Type.featOkTst && ((Measure_Type)jcasType).casFeat_textValue == null)
      jcasType.jcas.throwFeatMissing("textValue", "ch.epfl.bbp.uima.types.Measure");
    jcasType.ll_cas.ll_setStringValue(addr, ((Measure_Type)jcasType).casFeatCode_textValue, v);}    
   
    
  //*--------------*
  //* Feature: value

  /** getter for value - gets 
   * @generated
   * @return value of the feature 
   */
  public float getValue() {
    if (Measure_Type.featOkTst && ((Measure_Type)jcasType).casFeat_value == null)
      jcasType.jcas.throwFeatMissing("value", "ch.epfl.bbp.uima.types.Measure");
    return jcasType.ll_cas.ll_getFloatValue(addr, ((Measure_Type)jcasType).casFeatCode_value);}
    
  /** setter for value - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setValue(float v) {
    if (Measure_Type.featOkTst && ((Measure_Type)jcasType).casFeat_value == null)
      jcasType.jcas.throwFeatMissing("value", "ch.epfl.bbp.uima.types.Measure");
    jcasType.ll_cas.ll_setFloatValue(addr, ((Measure_Type)jcasType).casFeatCode_value, v);}    
   
    
  //*--------------*
  //* Feature: normalizedValue

  /** getter for normalizedValue - gets 
   * @generated
   * @return value of the feature 
   */
  public float getNormalizedValue() {
    if (Measure_Type.featOkTst && ((Measure_Type)jcasType).casFeat_normalizedValue == null)
      jcasType.jcas.throwFeatMissing("normalizedValue", "ch.epfl.bbp.uima.types.Measure");
    return jcasType.ll_cas.ll_getFloatValue(addr, ((Measure_Type)jcasType).casFeatCode_normalizedValue);}
    
  /** setter for normalizedValue - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setNormalizedValue(float v) {
    if (Measure_Type.featOkTst && ((Measure_Type)jcasType).casFeat_normalizedValue == null)
      jcasType.jcas.throwFeatMissing("normalizedValue", "ch.epfl.bbp.uima.types.Measure");
    jcasType.ll_cas.ll_setFloatValue(addr, ((Measure_Type)jcasType).casFeatCode_normalizedValue, v);}    
   
    
  //*--------------*
  //* Feature: rangeStartValue

  /** getter for rangeStartValue - gets 
   * @generated
   * @return value of the feature 
   */
  public float getRangeStartValue() {
    if (Measure_Type.featOkTst && ((Measure_Type)jcasType).casFeat_rangeStartValue == null)
      jcasType.jcas.throwFeatMissing("rangeStartValue", "ch.epfl.bbp.uima.types.Measure");
    return jcasType.ll_cas.ll_getFloatValue(addr, ((Measure_Type)jcasType).casFeatCode_rangeStartValue);}
    
  /** setter for rangeStartValue - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setRangeStartValue(float v) {
    if (Measure_Type.featOkTst && ((Measure_Type)jcasType).casFeat_rangeStartValue == null)
      jcasType.jcas.throwFeatMissing("rangeStartValue", "ch.epfl.bbp.uima.types.Measure");
    jcasType.ll_cas.ll_setFloatValue(addr, ((Measure_Type)jcasType).casFeatCode_rangeStartValue, v);}    
   
    
  //*--------------*
  //* Feature: rangeEndValue

  /** getter for rangeEndValue - gets 
   * @generated
   * @return value of the feature 
   */
  public float getRangeEndValue() {
    if (Measure_Type.featOkTst && ((Measure_Type)jcasType).casFeat_rangeEndValue == null)
      jcasType.jcas.throwFeatMissing("rangeEndValue", "ch.epfl.bbp.uima.types.Measure");
    return jcasType.ll_cas.ll_getFloatValue(addr, ((Measure_Type)jcasType).casFeatCode_rangeEndValue);}
    
  /** setter for rangeEndValue - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setRangeEndValue(float v) {
    if (Measure_Type.featOkTst && ((Measure_Type)jcasType).casFeat_rangeEndValue == null)
      jcasType.jcas.throwFeatMissing("rangeEndValue", "ch.epfl.bbp.uima.types.Measure");
    jcasType.ll_cas.ll_setFloatValue(addr, ((Measure_Type)jcasType).casFeatCode_rangeEndValue, v);}    
   
    
  //*--------------*
  //* Feature: meanValue

  /** getter for meanValue - gets 
   * @generated
   * @return value of the feature 
   */
  public float getMeanValue() {
    if (Measure_Type.featOkTst && ((Measure_Type)jcasType).casFeat_meanValue == null)
      jcasType.jcas.throwFeatMissing("meanValue", "ch.epfl.bbp.uima.types.Measure");
    return jcasType.ll_cas.ll_getFloatValue(addr, ((Measure_Type)jcasType).casFeatCode_meanValue);}
    
  /** setter for meanValue - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setMeanValue(float v) {
    if (Measure_Type.featOkTst && ((Measure_Type)jcasType).casFeat_meanValue == null)
      jcasType.jcas.throwFeatMissing("meanValue", "ch.epfl.bbp.uima.types.Measure");
    jcasType.ll_cas.ll_setFloatValue(addr, ((Measure_Type)jcasType).casFeatCode_meanValue, v);}    
   
    
  //*--------------*
  //* Feature: standardErrorValue

  /** getter for standardErrorValue - gets 
   * @generated
   * @return value of the feature 
   */
  public float getStandardErrorValue() {
    if (Measure_Type.featOkTst && ((Measure_Type)jcasType).casFeat_standardErrorValue == null)
      jcasType.jcas.throwFeatMissing("standardErrorValue", "ch.epfl.bbp.uima.types.Measure");
    return jcasType.ll_cas.ll_getFloatValue(addr, ((Measure_Type)jcasType).casFeatCode_standardErrorValue);}
    
  /** setter for standardErrorValue - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setStandardErrorValue(float v) {
    if (Measure_Type.featOkTst && ((Measure_Type)jcasType).casFeat_standardErrorValue == null)
      jcasType.jcas.throwFeatMissing("standardErrorValue", "ch.epfl.bbp.uima.types.Measure");
    jcasType.ll_cas.ll_setFloatValue(addr, ((Measure_Type)jcasType).casFeatCode_standardErrorValue, v);}    
   
    
  //*--------------*
  //* Feature: accuracy

  /** getter for accuracy - gets allows to store additional information about this
            measure, e.g. modifiers like '~', '+/-', 'approximately'
   * @generated
   * @return value of the feature 
   */
  public String getAccuracy() {
    if (Measure_Type.featOkTst && ((Measure_Type)jcasType).casFeat_accuracy == null)
      jcasType.jcas.throwFeatMissing("accuracy", "ch.epfl.bbp.uima.types.Measure");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Measure_Type)jcasType).casFeatCode_accuracy);}
    
  /** setter for accuracy - sets allows to store additional information about this
            measure, e.g. modifiers like '~', '+/-', 'approximately' 
   * @generated
   * @param v value to set into the feature 
   */
  public void setAccuracy(String v) {
    if (Measure_Type.featOkTst && ((Measure_Type)jcasType).casFeat_accuracy == null)
      jcasType.jcas.throwFeatMissing("accuracy", "ch.epfl.bbp.uima.types.Measure");
    jcasType.ll_cas.ll_setStringValue(addr, ((Measure_Type)jcasType).casFeatCode_accuracy, v);}    
   
    
  //*--------------*
  //* Feature: unit

  /** getter for unit - gets iso
   * @generated
   * @return value of the feature 
   */
  public String getUnit() {
    if (Measure_Type.featOkTst && ((Measure_Type)jcasType).casFeat_unit == null)
      jcasType.jcas.throwFeatMissing("unit", "ch.epfl.bbp.uima.types.Measure");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Measure_Type)jcasType).casFeatCode_unit);}
    
  /** setter for unit - sets iso 
   * @generated
   * @param v value to set into the feature 
   */
  public void setUnit(String v) {
    if (Measure_Type.featOkTst && ((Measure_Type)jcasType).casFeat_unit == null)
      jcasType.jcas.throwFeatMissing("unit", "ch.epfl.bbp.uima.types.Measure");
    jcasType.ll_cas.ll_setStringValue(addr, ((Measure_Type)jcasType).casFeatCode_unit, v);}    
   
    
  //*--------------*
  //* Feature: normalizedUnit

  /** getter for normalizedUnit - gets iso
   * @generated
   * @return value of the feature 
   */
  public String getNormalizedUnit() {
    if (Measure_Type.featOkTst && ((Measure_Type)jcasType).casFeat_normalizedUnit == null)
      jcasType.jcas.throwFeatMissing("normalizedUnit", "ch.epfl.bbp.uima.types.Measure");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Measure_Type)jcasType).casFeatCode_normalizedUnit);}
    
  /** setter for normalizedUnit - sets iso 
   * @generated
   * @param v value to set into the feature 
   */
  public void setNormalizedUnit(String v) {
    if (Measure_Type.featOkTst && ((Measure_Type)jcasType).casFeat_normalizedUnit == null)
      jcasType.jcas.throwFeatMissing("normalizedUnit", "ch.epfl.bbp.uima.types.Measure");
    jcasType.ll_cas.ll_setStringValue(addr, ((Measure_Type)jcasType).casFeatCode_normalizedUnit, v);}    
   
    
  //*--------------*
  //* Feature: confidence

  /** getter for confidence - gets characterizes the confidence of the extractor
            that this is indeed a measure. default should be 1f
   * @generated
   * @return value of the feature 
   */
  public float getConfidence() {
    if (Measure_Type.featOkTst && ((Measure_Type)jcasType).casFeat_confidence == null)
      jcasType.jcas.throwFeatMissing("confidence", "ch.epfl.bbp.uima.types.Measure");
    return jcasType.ll_cas.ll_getFloatValue(addr, ((Measure_Type)jcasType).casFeatCode_confidence);}
    
  /** setter for confidence - sets characterizes the confidence of the extractor
            that this is indeed a measure. default should be 1f 
   * @generated
   * @param v value to set into the feature 
   */
  public void setConfidence(float v) {
    if (Measure_Type.featOkTst && ((Measure_Type)jcasType).casFeat_confidence == null)
      jcasType.jcas.throwFeatMissing("confidence", "ch.epfl.bbp.uima.types.Measure");
    jcasType.ll_cas.ll_setFloatValue(addr, ((Measure_Type)jcasType).casFeatCode_confidence, v);}    
  }

    