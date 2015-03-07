
/* First created by JCasGen Sat Mar 07 22:05:57 CET 2015 */
package de.julielab.jules.types.mmax;

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

/** Type to represent MMAX annotation.
 * Updated by JCasGen Sat Mar 07 22:05:57 CET 2015
 * @generated */
public class MMAXAnnotation_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (MMAXAnnotation_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = MMAXAnnotation_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new MMAXAnnotation(addr, MMAXAnnotation_Type.this);
  			   MMAXAnnotation_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new MMAXAnnotation(addr, MMAXAnnotation_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = MMAXAnnotation.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.julielab.jules.types.mmax.MMAXAnnotation");
 
  /** @generated */
  final Feature casFeat_annotationLevel;
  /** @generated */
  final int     casFeatCode_annotationLevel;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getAnnotationLevel(int addr) {
        if (featOkTst && casFeat_annotationLevel == null)
      jcas.throwFeatMissing("annotationLevel", "de.julielab.jules.types.mmax.MMAXAnnotation");
    return ll_cas.ll_getStringValue(addr, casFeatCode_annotationLevel);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setAnnotationLevel(int addr, String v) {
        if (featOkTst && casFeat_annotationLevel == null)
      jcas.throwFeatMissing("annotationLevel", "de.julielab.jules.types.mmax.MMAXAnnotation");
    ll_cas.ll_setStringValue(addr, casFeatCode_annotationLevel, v);}
    
  
 
  /** @generated */
  final Feature casFeat_isContinuous;
  /** @generated */
  final int     casFeatCode_isContinuous;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public boolean getIsContinuous(int addr) {
        if (featOkTst && casFeat_isContinuous == null)
      jcas.throwFeatMissing("isContinuous", "de.julielab.jules.types.mmax.MMAXAnnotation");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_isContinuous);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setIsContinuous(int addr, boolean v) {
        if (featOkTst && casFeat_isContinuous == null)
      jcas.throwFeatMissing("isContinuous", "de.julielab.jules.types.mmax.MMAXAnnotation");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_isContinuous, v);}
    
  
 
  /** @generated */
  final Feature casFeat_segmentList;
  /** @generated */
  final int     casFeatCode_segmentList;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getSegmentList(int addr) {
        if (featOkTst && casFeat_segmentList == null)
      jcas.throwFeatMissing("segmentList", "de.julielab.jules.types.mmax.MMAXAnnotation");
    return ll_cas.ll_getRefValue(addr, casFeatCode_segmentList);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setSegmentList(int addr, int v) {
        if (featOkTst && casFeat_segmentList == null)
      jcas.throwFeatMissing("segmentList", "de.julielab.jules.types.mmax.MMAXAnnotation");
    ll_cas.ll_setRefValue(addr, casFeatCode_segmentList, v);}
    
   /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @return value at index i in the array 
   */
  public int getSegmentList(int addr, int i) {
        if (featOkTst && casFeat_segmentList == null)
      jcas.throwFeatMissing("segmentList", "de.julielab.jules.types.mmax.MMAXAnnotation");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_segmentList), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_segmentList), i);
	return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_segmentList), i);
  }
   
  /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @param v value to set
   */ 
  public void setSegmentList(int addr, int i, int v) {
        if (featOkTst && casFeat_segmentList == null)
      jcas.throwFeatMissing("segmentList", "de.julielab.jules.types.mmax.MMAXAnnotation");
    if (lowLevelTypeChecks)
      ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_segmentList), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_segmentList), i);
    ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_segmentList), i, v);
  }
 
 
  /** @generated */
  final Feature casFeat_attributeList;
  /** @generated */
  final int     casFeatCode_attributeList;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getAttributeList(int addr) {
        if (featOkTst && casFeat_attributeList == null)
      jcas.throwFeatMissing("attributeList", "de.julielab.jules.types.mmax.MMAXAnnotation");
    return ll_cas.ll_getRefValue(addr, casFeatCode_attributeList);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setAttributeList(int addr, int v) {
        if (featOkTst && casFeat_attributeList == null)
      jcas.throwFeatMissing("attributeList", "de.julielab.jules.types.mmax.MMAXAnnotation");
    ll_cas.ll_setRefValue(addr, casFeatCode_attributeList, v);}
    
   /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @return value at index i in the array 
   */
  public int getAttributeList(int addr, int i) {
        if (featOkTst && casFeat_attributeList == null)
      jcas.throwFeatMissing("attributeList", "de.julielab.jules.types.mmax.MMAXAnnotation");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_attributeList), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_attributeList), i);
	return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_attributeList), i);
  }
   
  /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @param v value to set
   */ 
  public void setAttributeList(int addr, int i, int v) {
        if (featOkTst && casFeat_attributeList == null)
      jcas.throwFeatMissing("attributeList", "de.julielab.jules.types.mmax.MMAXAnnotation");
    if (lowLevelTypeChecks)
      ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_attributeList), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_attributeList), i);
    ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_attributeList), i, v);
  }
 
 
  /** @generated */
  final Feature casFeat_pointerList;
  /** @generated */
  final int     casFeatCode_pointerList;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getPointerList(int addr) {
        if (featOkTst && casFeat_pointerList == null)
      jcas.throwFeatMissing("pointerList", "de.julielab.jules.types.mmax.MMAXAnnotation");
    return ll_cas.ll_getRefValue(addr, casFeatCode_pointerList);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setPointerList(int addr, int v) {
        if (featOkTst && casFeat_pointerList == null)
      jcas.throwFeatMissing("pointerList", "de.julielab.jules.types.mmax.MMAXAnnotation");
    ll_cas.ll_setRefValue(addr, casFeatCode_pointerList, v);}
    
   /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @return value at index i in the array 
   */
  public int getPointerList(int addr, int i) {
        if (featOkTst && casFeat_pointerList == null)
      jcas.throwFeatMissing("pointerList", "de.julielab.jules.types.mmax.MMAXAnnotation");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_pointerList), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_pointerList), i);
	return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_pointerList), i);
  }
   
  /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @param v value to set
   */ 
  public void setPointerList(int addr, int i, int v) {
        if (featOkTst && casFeat_pointerList == null)
      jcas.throwFeatMissing("pointerList", "de.julielab.jules.types.mmax.MMAXAnnotation");
    if (lowLevelTypeChecks)
      ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_pointerList), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_pointerList), i);
    ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_pointerList), i, v);
  }
 
 
  /** @generated */
  final Feature casFeat_adaptedCoveredText;
  /** @generated */
  final int     casFeatCode_adaptedCoveredText;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getAdaptedCoveredText(int addr) {
        if (featOkTst && casFeat_adaptedCoveredText == null)
      jcas.throwFeatMissing("adaptedCoveredText", "de.julielab.jules.types.mmax.MMAXAnnotation");
    return ll_cas.ll_getStringValue(addr, casFeatCode_adaptedCoveredText);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setAdaptedCoveredText(int addr, String v) {
        if (featOkTst && casFeat_adaptedCoveredText == null)
      jcas.throwFeatMissing("adaptedCoveredText", "de.julielab.jules.types.mmax.MMAXAnnotation");
    ll_cas.ll_setStringValue(addr, casFeatCode_adaptedCoveredText, v);}
    
  
 
  /** @generated */
  final Feature casFeat_id;
  /** @generated */
  final int     casFeatCode_id;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getId(int addr) {
        if (featOkTst && casFeat_id == null)
      jcas.throwFeatMissing("id", "de.julielab.jules.types.mmax.MMAXAnnotation");
    return ll_cas.ll_getStringValue(addr, casFeatCode_id);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setId(int addr, String v) {
        if (featOkTst && casFeat_id == null)
      jcas.throwFeatMissing("id", "de.julielab.jules.types.mmax.MMAXAnnotation");
    ll_cas.ll_setStringValue(addr, casFeatCode_id, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public MMAXAnnotation_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_annotationLevel = jcas.getRequiredFeatureDE(casType, "annotationLevel", "uima.cas.String", featOkTst);
    casFeatCode_annotationLevel  = (null == casFeat_annotationLevel) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_annotationLevel).getCode();

 
    casFeat_isContinuous = jcas.getRequiredFeatureDE(casType, "isContinuous", "uima.cas.Boolean", featOkTst);
    casFeatCode_isContinuous  = (null == casFeat_isContinuous) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_isContinuous).getCode();

 
    casFeat_segmentList = jcas.getRequiredFeatureDE(casType, "segmentList", "uima.cas.FSArray", featOkTst);
    casFeatCode_segmentList  = (null == casFeat_segmentList) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_segmentList).getCode();

 
    casFeat_attributeList = jcas.getRequiredFeatureDE(casType, "attributeList", "uima.cas.FSArray", featOkTst);
    casFeatCode_attributeList  = (null == casFeat_attributeList) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_attributeList).getCode();

 
    casFeat_pointerList = jcas.getRequiredFeatureDE(casType, "pointerList", "uima.cas.FSArray", featOkTst);
    casFeatCode_pointerList  = (null == casFeat_pointerList) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_pointerList).getCode();

 
    casFeat_adaptedCoveredText = jcas.getRequiredFeatureDE(casType, "adaptedCoveredText", "uima.cas.String", featOkTst);
    casFeatCode_adaptedCoveredText  = (null == casFeat_adaptedCoveredText) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_adaptedCoveredText).getCode();

 
    casFeat_id = jcas.getRequiredFeatureDE(casType, "id", "uima.cas.String", featOkTst);
    casFeatCode_id  = (null == casFeat_id) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_id).getCode();

  }
}



    