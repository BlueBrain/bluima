

/* First created by JCasGen Sat Mar 07 22:05:57 CET 2015 */
package de.julielab.jules.types.mmax;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.FSArray;
import org.apache.uima.jcas.tcas.Annotation;


/** Type to represent MMAX annotation.
 * Updated by JCasGen Sat Mar 07 22:05:57 CET 2015
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/target/jcasgen/typesystem.xml
 * @generated */
public class MMAXAnnotation extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(MMAXAnnotation.class);
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
  protected MMAXAnnotation() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public MMAXAnnotation(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public MMAXAnnotation(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public MMAXAnnotation(JCas jcas, int begin, int end) {
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
  //* Feature: annotationLevel

  /** getter for annotationLevel - gets The MMAX annotation level.
   * @generated
   * @return value of the feature 
   */
  public String getAnnotationLevel() {
    if (MMAXAnnotation_Type.featOkTst && ((MMAXAnnotation_Type)jcasType).casFeat_annotationLevel == null)
      jcasType.jcas.throwFeatMissing("annotationLevel", "de.julielab.jules.types.mmax.MMAXAnnotation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((MMAXAnnotation_Type)jcasType).casFeatCode_annotationLevel);}
    
  /** setter for annotationLevel - sets The MMAX annotation level. 
   * @generated
   * @param v value to set into the feature 
   */
  public void setAnnotationLevel(String v) {
    if (MMAXAnnotation_Type.featOkTst && ((MMAXAnnotation_Type)jcasType).casFeat_annotationLevel == null)
      jcasType.jcas.throwFeatMissing("annotationLevel", "de.julielab.jules.types.mmax.MMAXAnnotation");
    jcasType.ll_cas.ll_setStringValue(addr, ((MMAXAnnotation_Type)jcasType).casFeatCode_annotationLevel, v);}    
   
    
  //*--------------*
  //* Feature: isContinuous

  /** getter for isContinuous - gets Tells if the MMAX annotation is continuous (true) or discontinuous (false).
Could be also detected by checking if segmentList has length = 1.
   * @generated
   * @return value of the feature 
   */
  public boolean getIsContinuous() {
    if (MMAXAnnotation_Type.featOkTst && ((MMAXAnnotation_Type)jcasType).casFeat_isContinuous == null)
      jcasType.jcas.throwFeatMissing("isContinuous", "de.julielab.jules.types.mmax.MMAXAnnotation");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((MMAXAnnotation_Type)jcasType).casFeatCode_isContinuous);}
    
  /** setter for isContinuous - sets Tells if the MMAX annotation is continuous (true) or discontinuous (false).
Could be also detected by checking if segmentList has length = 1. 
   * @generated
   * @param v value to set into the feature 
   */
  public void setIsContinuous(boolean v) {
    if (MMAXAnnotation_Type.featOkTst && ((MMAXAnnotation_Type)jcasType).casFeat_isContinuous == null)
      jcasType.jcas.throwFeatMissing("isContinuous", "de.julielab.jules.types.mmax.MMAXAnnotation");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((MMAXAnnotation_Type)jcasType).casFeatCode_isContinuous, v);}    
   
    
  //*--------------*
  //* Feature: segmentList

  /** getter for segmentList - gets List of MMAX annotation segements that make up the MMAX annotation.
   * @generated
   * @return value of the feature 
   */
  public FSArray getSegmentList() {
    if (MMAXAnnotation_Type.featOkTst && ((MMAXAnnotation_Type)jcasType).casFeat_segmentList == null)
      jcasType.jcas.throwFeatMissing("segmentList", "de.julielab.jules.types.mmax.MMAXAnnotation");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((MMAXAnnotation_Type)jcasType).casFeatCode_segmentList)));}
    
  /** setter for segmentList - sets List of MMAX annotation segements that make up the MMAX annotation. 
   * @generated
   * @param v value to set into the feature 
   */
  public void setSegmentList(FSArray v) {
    if (MMAXAnnotation_Type.featOkTst && ((MMAXAnnotation_Type)jcasType).casFeat_segmentList == null)
      jcasType.jcas.throwFeatMissing("segmentList", "de.julielab.jules.types.mmax.MMAXAnnotation");
    jcasType.ll_cas.ll_setRefValue(addr, ((MMAXAnnotation_Type)jcasType).casFeatCode_segmentList, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for segmentList - gets an indexed value - List of MMAX annotation segements that make up the MMAX annotation.
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public AnnotationSegment getSegmentList(int i) {
    if (MMAXAnnotation_Type.featOkTst && ((MMAXAnnotation_Type)jcasType).casFeat_segmentList == null)
      jcasType.jcas.throwFeatMissing("segmentList", "de.julielab.jules.types.mmax.MMAXAnnotation");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((MMAXAnnotation_Type)jcasType).casFeatCode_segmentList), i);
    return (AnnotationSegment)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((MMAXAnnotation_Type)jcasType).casFeatCode_segmentList), i)));}

  /** indexed setter for segmentList - sets an indexed value - List of MMAX annotation segements that make up the MMAX annotation.
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setSegmentList(int i, AnnotationSegment v) { 
    if (MMAXAnnotation_Type.featOkTst && ((MMAXAnnotation_Type)jcasType).casFeat_segmentList == null)
      jcasType.jcas.throwFeatMissing("segmentList", "de.julielab.jules.types.mmax.MMAXAnnotation");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((MMAXAnnotation_Type)jcasType).casFeatCode_segmentList), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((MMAXAnnotation_Type)jcasType).casFeatCode_segmentList), i, jcasType.ll_cas.ll_getFSRef(v));}
   
    
  //*--------------*
  //* Feature: attributeList

  /** getter for attributeList - gets List of attributes of the MMAX annotation.
   * @generated
   * @return value of the feature 
   */
  public FSArray getAttributeList() {
    if (MMAXAnnotation_Type.featOkTst && ((MMAXAnnotation_Type)jcasType).casFeat_attributeList == null)
      jcasType.jcas.throwFeatMissing("attributeList", "de.julielab.jules.types.mmax.MMAXAnnotation");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((MMAXAnnotation_Type)jcasType).casFeatCode_attributeList)));}
    
  /** setter for attributeList - sets List of attributes of the MMAX annotation. 
   * @generated
   * @param v value to set into the feature 
   */
  public void setAttributeList(FSArray v) {
    if (MMAXAnnotation_Type.featOkTst && ((MMAXAnnotation_Type)jcasType).casFeat_attributeList == null)
      jcasType.jcas.throwFeatMissing("attributeList", "de.julielab.jules.types.mmax.MMAXAnnotation");
    jcasType.ll_cas.ll_setRefValue(addr, ((MMAXAnnotation_Type)jcasType).casFeatCode_attributeList, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for attributeList - gets an indexed value - List of attributes of the MMAX annotation.
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public MMAXAttribute getAttributeList(int i) {
    if (MMAXAnnotation_Type.featOkTst && ((MMAXAnnotation_Type)jcasType).casFeat_attributeList == null)
      jcasType.jcas.throwFeatMissing("attributeList", "de.julielab.jules.types.mmax.MMAXAnnotation");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((MMAXAnnotation_Type)jcasType).casFeatCode_attributeList), i);
    return (MMAXAttribute)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((MMAXAnnotation_Type)jcasType).casFeatCode_attributeList), i)));}

  /** indexed setter for attributeList - sets an indexed value - List of attributes of the MMAX annotation.
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setAttributeList(int i, MMAXAttribute v) { 
    if (MMAXAnnotation_Type.featOkTst && ((MMAXAnnotation_Type)jcasType).casFeat_attributeList == null)
      jcasType.jcas.throwFeatMissing("attributeList", "de.julielab.jules.types.mmax.MMAXAnnotation");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((MMAXAnnotation_Type)jcasType).casFeatCode_attributeList), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((MMAXAnnotation_Type)jcasType).casFeatCode_attributeList), i, jcasType.ll_cas.ll_getFSRef(v));}
   
    
  //*--------------*
  //* Feature: pointerList

  /** getter for pointerList - gets The list of MMAX pointers of the MMAX annotation.
   * @generated
   * @return value of the feature 
   */
  public FSArray getPointerList() {
    if (MMAXAnnotation_Type.featOkTst && ((MMAXAnnotation_Type)jcasType).casFeat_pointerList == null)
      jcasType.jcas.throwFeatMissing("pointerList", "de.julielab.jules.types.mmax.MMAXAnnotation");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((MMAXAnnotation_Type)jcasType).casFeatCode_pointerList)));}
    
  /** setter for pointerList - sets The list of MMAX pointers of the MMAX annotation. 
   * @generated
   * @param v value to set into the feature 
   */
  public void setPointerList(FSArray v) {
    if (MMAXAnnotation_Type.featOkTst && ((MMAXAnnotation_Type)jcasType).casFeat_pointerList == null)
      jcasType.jcas.throwFeatMissing("pointerList", "de.julielab.jules.types.mmax.MMAXAnnotation");
    jcasType.ll_cas.ll_setRefValue(addr, ((MMAXAnnotation_Type)jcasType).casFeatCode_pointerList, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for pointerList - gets an indexed value - The list of MMAX pointers of the MMAX annotation.
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public MMAXPointer getPointerList(int i) {
    if (MMAXAnnotation_Type.featOkTst && ((MMAXAnnotation_Type)jcasType).casFeat_pointerList == null)
      jcasType.jcas.throwFeatMissing("pointerList", "de.julielab.jules.types.mmax.MMAXAnnotation");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((MMAXAnnotation_Type)jcasType).casFeatCode_pointerList), i);
    return (MMAXPointer)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((MMAXAnnotation_Type)jcasType).casFeatCode_pointerList), i)));}

  /** indexed setter for pointerList - sets an indexed value - The list of MMAX pointers of the MMAX annotation.
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setPointerList(int i, MMAXPointer v) { 
    if (MMAXAnnotation_Type.featOkTst && ((MMAXAnnotation_Type)jcasType).casFeat_pointerList == null)
      jcasType.jcas.throwFeatMissing("pointerList", "de.julielab.jules.types.mmax.MMAXAnnotation");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((MMAXAnnotation_Type)jcasType).casFeatCode_pointerList), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((MMAXAnnotation_Type)jcasType).casFeatCode_pointerList), i, jcasType.ll_cas.ll_getFSRef(v));}
   
    
  //*--------------*
  //* Feature: adaptedCoveredText

  /** getter for adaptedCoveredText - gets Text covered by the annotation. This feature is helpful if the annotations is discontinuous...
   * @generated
   * @return value of the feature 
   */
  public String getAdaptedCoveredText() {
    if (MMAXAnnotation_Type.featOkTst && ((MMAXAnnotation_Type)jcasType).casFeat_adaptedCoveredText == null)
      jcasType.jcas.throwFeatMissing("adaptedCoveredText", "de.julielab.jules.types.mmax.MMAXAnnotation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((MMAXAnnotation_Type)jcasType).casFeatCode_adaptedCoveredText);}
    
  /** setter for adaptedCoveredText - sets Text covered by the annotation. This feature is helpful if the annotations is discontinuous... 
   * @generated
   * @param v value to set into the feature 
   */
  public void setAdaptedCoveredText(String v) {
    if (MMAXAnnotation_Type.featOkTst && ((MMAXAnnotation_Type)jcasType).casFeat_adaptedCoveredText == null)
      jcasType.jcas.throwFeatMissing("adaptedCoveredText", "de.julielab.jules.types.mmax.MMAXAnnotation");
    jcasType.ll_cas.ll_setStringValue(addr, ((MMAXAnnotation_Type)jcasType).casFeatCode_adaptedCoveredText, v);}    
   
    
  //*--------------*
  //* Feature: id

  /** getter for id - gets Original markable id from MMAX2 tool. Unique only on a given annotation level.
   * @generated
   * @return value of the feature 
   */
  public String getId() {
    if (MMAXAnnotation_Type.featOkTst && ((MMAXAnnotation_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "de.julielab.jules.types.mmax.MMAXAnnotation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((MMAXAnnotation_Type)jcasType).casFeatCode_id);}
    
  /** setter for id - sets Original markable id from MMAX2 tool. Unique only on a given annotation level. 
   * @generated
   * @param v value to set into the feature 
   */
  public void setId(String v) {
    if (MMAXAnnotation_Type.featOkTst && ((MMAXAnnotation_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "de.julielab.jules.types.mmax.MMAXAnnotation");
    jcasType.ll_cas.ll_setStringValue(addr, ((MMAXAnnotation_Type)jcasType).casFeatCode_id, v);}    
  }

    