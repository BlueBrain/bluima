
/* First created by JCasGen Mon Feb 17 12:26:53 CET 2014 */
package ch.epfl.bbp.uima.types;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** a measure, e.g. 40g
 * Updated by JCasGen Wed Jun 04 18:01:56 CEST 2014
 * @generated */
public class Measure_Type extends Annotation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Measure_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Measure_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Measure(addr, Measure_Type.this);
  			   Measure_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Measure(addr, Measure_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Measure.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("ch.epfl.bbp.uima.types.Measure");
 
  /** @generated */
  final Feature casFeat_textValue;
  /** @generated */
  final int     casFeatCode_textValue;
  /** @generated */ 
  public String getTextValue(int addr) {
        if (featOkTst && casFeat_textValue == null)
      jcas.throwFeatMissing("textValue", "ch.epfl.bbp.uima.types.Measure");
    return ll_cas.ll_getStringValue(addr, casFeatCode_textValue);
  }
  /** @generated */    
  public void setTextValue(int addr, String v) {
        if (featOkTst && casFeat_textValue == null)
      jcas.throwFeatMissing("textValue", "ch.epfl.bbp.uima.types.Measure");
    ll_cas.ll_setStringValue(addr, casFeatCode_textValue, v);}
    
  
 
  /** @generated */
  final Feature casFeat_value;
  /** @generated */
  final int     casFeatCode_value;
  /** @generated */ 
  public float getValue(int addr) {
        if (featOkTst && casFeat_value == null)
      jcas.throwFeatMissing("value", "ch.epfl.bbp.uima.types.Measure");
    return ll_cas.ll_getFloatValue(addr, casFeatCode_value);
  }
  /** @generated */    
  public void setValue(int addr, float v) {
        if (featOkTst && casFeat_value == null)
      jcas.throwFeatMissing("value", "ch.epfl.bbp.uima.types.Measure");
    ll_cas.ll_setFloatValue(addr, casFeatCode_value, v);}
    
  
 
  /** @generated */
  final Feature casFeat_normalizedValue;
  /** @generated */
  final int     casFeatCode_normalizedValue;
  /** @generated */ 
  public float getNormalizedValue(int addr) {
        if (featOkTst && casFeat_normalizedValue == null)
      jcas.throwFeatMissing("normalizedValue", "ch.epfl.bbp.uima.types.Measure");
    return ll_cas.ll_getFloatValue(addr, casFeatCode_normalizedValue);
  }
  /** @generated */    
  public void setNormalizedValue(int addr, float v) {
        if (featOkTst && casFeat_normalizedValue == null)
      jcas.throwFeatMissing("normalizedValue", "ch.epfl.bbp.uima.types.Measure");
    ll_cas.ll_setFloatValue(addr, casFeatCode_normalizedValue, v);}
    
  
 
  /** @generated */
  final Feature casFeat_rangeStartValue;
  /** @generated */
  final int     casFeatCode_rangeStartValue;
  /** @generated */ 
  public float getRangeStartValue(int addr) {
        if (featOkTst && casFeat_rangeStartValue == null)
      jcas.throwFeatMissing("rangeStartValue", "ch.epfl.bbp.uima.types.Measure");
    return ll_cas.ll_getFloatValue(addr, casFeatCode_rangeStartValue);
  }
  /** @generated */    
  public void setRangeStartValue(int addr, float v) {
        if (featOkTst && casFeat_rangeStartValue == null)
      jcas.throwFeatMissing("rangeStartValue", "ch.epfl.bbp.uima.types.Measure");
    ll_cas.ll_setFloatValue(addr, casFeatCode_rangeStartValue, v);}
    
  
 
  /** @generated */
  final Feature casFeat_rangeEndValue;
  /** @generated */
  final int     casFeatCode_rangeEndValue;
  /** @generated */ 
  public float getRangeEndValue(int addr) {
        if (featOkTst && casFeat_rangeEndValue == null)
      jcas.throwFeatMissing("rangeEndValue", "ch.epfl.bbp.uima.types.Measure");
    return ll_cas.ll_getFloatValue(addr, casFeatCode_rangeEndValue);
  }
  /** @generated */    
  public void setRangeEndValue(int addr, float v) {
        if (featOkTst && casFeat_rangeEndValue == null)
      jcas.throwFeatMissing("rangeEndValue", "ch.epfl.bbp.uima.types.Measure");
    ll_cas.ll_setFloatValue(addr, casFeatCode_rangeEndValue, v);}
    
  
 
  /** @generated */
  final Feature casFeat_meanValue;
  /** @generated */
  final int     casFeatCode_meanValue;
  /** @generated */ 
  public float getMeanValue(int addr) {
        if (featOkTst && casFeat_meanValue == null)
      jcas.throwFeatMissing("meanValue", "ch.epfl.bbp.uima.types.Measure");
    return ll_cas.ll_getFloatValue(addr, casFeatCode_meanValue);
  }
  /** @generated */    
  public void setMeanValue(int addr, float v) {
        if (featOkTst && casFeat_meanValue == null)
      jcas.throwFeatMissing("meanValue", "ch.epfl.bbp.uima.types.Measure");
    ll_cas.ll_setFloatValue(addr, casFeatCode_meanValue, v);}
    
  
 
  /** @generated */
  final Feature casFeat_standardErrorValue;
  /** @generated */
  final int     casFeatCode_standardErrorValue;
  /** @generated */ 
  public float getStandardErrorValue(int addr) {
        if (featOkTst && casFeat_standardErrorValue == null)
      jcas.throwFeatMissing("standardErrorValue", "ch.epfl.bbp.uima.types.Measure");
    return ll_cas.ll_getFloatValue(addr, casFeatCode_standardErrorValue);
  }
  /** @generated */    
  public void setStandardErrorValue(int addr, float v) {
        if (featOkTst && casFeat_standardErrorValue == null)
      jcas.throwFeatMissing("standardErrorValue", "ch.epfl.bbp.uima.types.Measure");
    ll_cas.ll_setFloatValue(addr, casFeatCode_standardErrorValue, v);}
    
  
 
  /** @generated */
  final Feature casFeat_accuracy;
  /** @generated */
  final int     casFeatCode_accuracy;
  /** @generated */ 
  public String getAccuracy(int addr) {
        if (featOkTst && casFeat_accuracy == null)
      jcas.throwFeatMissing("accuracy", "ch.epfl.bbp.uima.types.Measure");
    return ll_cas.ll_getStringValue(addr, casFeatCode_accuracy);
  }
  /** @generated */    
  public void setAccuracy(int addr, String v) {
        if (featOkTst && casFeat_accuracy == null)
      jcas.throwFeatMissing("accuracy", "ch.epfl.bbp.uima.types.Measure");
    ll_cas.ll_setStringValue(addr, casFeatCode_accuracy, v);}
    
  
 
  /** @generated */
  final Feature casFeat_unit;
  /** @generated */
  final int     casFeatCode_unit;
  /** @generated */ 
  public String getUnit(int addr) {
        if (featOkTst && casFeat_unit == null)
      jcas.throwFeatMissing("unit", "ch.epfl.bbp.uima.types.Measure");
    return ll_cas.ll_getStringValue(addr, casFeatCode_unit);
  }
  /** @generated */    
  public void setUnit(int addr, String v) {
        if (featOkTst && casFeat_unit == null)
      jcas.throwFeatMissing("unit", "ch.epfl.bbp.uima.types.Measure");
    ll_cas.ll_setStringValue(addr, casFeatCode_unit, v);}
    
  
 
  /** @generated */
  final Feature casFeat_normalizedUnit;
  /** @generated */
  final int     casFeatCode_normalizedUnit;
  /** @generated */ 
  public String getNormalizedUnit(int addr) {
        if (featOkTst && casFeat_normalizedUnit == null)
      jcas.throwFeatMissing("normalizedUnit", "ch.epfl.bbp.uima.types.Measure");
    return ll_cas.ll_getStringValue(addr, casFeatCode_normalizedUnit);
  }
  /** @generated */    
  public void setNormalizedUnit(int addr, String v) {
        if (featOkTst && casFeat_normalizedUnit == null)
      jcas.throwFeatMissing("normalizedUnit", "ch.epfl.bbp.uima.types.Measure");
    ll_cas.ll_setStringValue(addr, casFeatCode_normalizedUnit, v);}
    
  
 
  /** @generated */
  final Feature casFeat_confidence;
  /** @generated */
  final int     casFeatCode_confidence;
  /** @generated */ 
  public float getConfidence(int addr) {
        if (featOkTst && casFeat_confidence == null)
      jcas.throwFeatMissing("confidence", "ch.epfl.bbp.uima.types.Measure");
    return ll_cas.ll_getFloatValue(addr, casFeatCode_confidence);
  }
  /** @generated */    
  public void setConfidence(int addr, float v) {
        if (featOkTst && casFeat_confidence == null)
      jcas.throwFeatMissing("confidence", "ch.epfl.bbp.uima.types.Measure");
    ll_cas.ll_setFloatValue(addr, casFeatCode_confidence, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public Measure_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_textValue = jcas.getRequiredFeatureDE(casType, "textValue", "uima.cas.String", featOkTst);
    casFeatCode_textValue  = (null == casFeat_textValue) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_textValue).getCode();

 
    casFeat_value = jcas.getRequiredFeatureDE(casType, "value", "uima.cas.Float", featOkTst);
    casFeatCode_value  = (null == casFeat_value) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_value).getCode();

 
    casFeat_normalizedValue = jcas.getRequiredFeatureDE(casType, "normalizedValue", "uima.cas.Float", featOkTst);
    casFeatCode_normalizedValue  = (null == casFeat_normalizedValue) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_normalizedValue).getCode();

 
    casFeat_rangeStartValue = jcas.getRequiredFeatureDE(casType, "rangeStartValue", "uima.cas.Float", featOkTst);
    casFeatCode_rangeStartValue  = (null == casFeat_rangeStartValue) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_rangeStartValue).getCode();

 
    casFeat_rangeEndValue = jcas.getRequiredFeatureDE(casType, "rangeEndValue", "uima.cas.Float", featOkTst);
    casFeatCode_rangeEndValue  = (null == casFeat_rangeEndValue) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_rangeEndValue).getCode();

 
    casFeat_meanValue = jcas.getRequiredFeatureDE(casType, "meanValue", "uima.cas.Float", featOkTst);
    casFeatCode_meanValue  = (null == casFeat_meanValue) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_meanValue).getCode();

 
    casFeat_standardErrorValue = jcas.getRequiredFeatureDE(casType, "standardErrorValue", "uima.cas.Float", featOkTst);
    casFeatCode_standardErrorValue  = (null == casFeat_standardErrorValue) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_standardErrorValue).getCode();

 
    casFeat_accuracy = jcas.getRequiredFeatureDE(casType, "accuracy", "uima.cas.String", featOkTst);
    casFeatCode_accuracy  = (null == casFeat_accuracy) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_accuracy).getCode();

 
    casFeat_unit = jcas.getRequiredFeatureDE(casType, "unit", "uima.cas.String", featOkTst);
    casFeatCode_unit  = (null == casFeat_unit) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_unit).getCode();

 
    casFeat_normalizedUnit = jcas.getRequiredFeatureDE(casType, "normalizedUnit", "uima.cas.String", featOkTst);
    casFeatCode_normalizedUnit  = (null == casFeat_normalizedUnit) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_normalizedUnit).getCode();

 
    casFeat_confidence = jcas.getRequiredFeatureDE(casType, "confidence", "uima.cas.Float", featOkTst);
    casFeatCode_confidence  = (null == casFeat_confidence) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_confidence).getCode();

  }
}



    