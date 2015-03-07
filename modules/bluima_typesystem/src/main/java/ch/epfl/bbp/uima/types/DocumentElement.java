

/* First created by JCasGen Sat Mar 07 22:05:56 CET 2015 */
package ch.epfl.bbp.uima.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Sat Mar 07 22:05:56 CET 2015
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/target/jcasgen/typesystem.xml
 * @generated */
public class DocumentElement extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(DocumentElement.class);
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
  protected DocumentElement() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public DocumentElement(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public DocumentElement(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public DocumentElement(JCas jcas, int begin, int end) {
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
  //* Feature: ElementId

  /** getter for ElementId - gets 
   * @generated
   * @return value of the feature 
   */
  public int getElementId() {
    if (DocumentElement_Type.featOkTst && ((DocumentElement_Type)jcasType).casFeat_ElementId == null)
      jcasType.jcas.throwFeatMissing("ElementId", "ch.epfl.bbp.uima.types.DocumentElement");
    return jcasType.ll_cas.ll_getIntValue(addr, ((DocumentElement_Type)jcasType).casFeatCode_ElementId);}
    
  /** setter for ElementId - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setElementId(int v) {
    if (DocumentElement_Type.featOkTst && ((DocumentElement_Type)jcasType).casFeat_ElementId == null)
      jcasType.jcas.throwFeatMissing("ElementId", "ch.epfl.bbp.uima.types.DocumentElement");
    jcasType.ll_cas.ll_setIntValue(addr, ((DocumentElement_Type)jcasType).casFeatCode_ElementId, v);}    
   
    
  //*--------------*
  //* Feature: isBold

  /** getter for isBold - gets 
   * @generated
   * @return value of the feature 
   */
  public boolean getIsBold() {
    if (DocumentElement_Type.featOkTst && ((DocumentElement_Type)jcasType).casFeat_isBold == null)
      jcasType.jcas.throwFeatMissing("isBold", "ch.epfl.bbp.uima.types.DocumentElement");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((DocumentElement_Type)jcasType).casFeatCode_isBold);}
    
  /** setter for isBold - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setIsBold(boolean v) {
    if (DocumentElement_Type.featOkTst && ((DocumentElement_Type)jcasType).casFeat_isBold == null)
      jcasType.jcas.throwFeatMissing("isBold", "ch.epfl.bbp.uima.types.DocumentElement");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((DocumentElement_Type)jcasType).casFeatCode_isBold, v);}    
   
    
  //*--------------*
  //* Feature: height

  /** getter for height - gets 
   * @generated
   * @return value of the feature 
   */
  public float getHeight() {
    if (DocumentElement_Type.featOkTst && ((DocumentElement_Type)jcasType).casFeat_height == null)
      jcasType.jcas.throwFeatMissing("height", "ch.epfl.bbp.uima.types.DocumentElement");
    return jcasType.ll_cas.ll_getFloatValue(addr, ((DocumentElement_Type)jcasType).casFeatCode_height);}
    
  /** setter for height - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setHeight(float v) {
    if (DocumentElement_Type.featOkTst && ((DocumentElement_Type)jcasType).casFeat_height == null)
      jcasType.jcas.throwFeatMissing("height", "ch.epfl.bbp.uima.types.DocumentElement");
    jcasType.ll_cas.ll_setFloatValue(addr, ((DocumentElement_Type)jcasType).casFeatCode_height, v);}    
   
    
  //*--------------*
  //* Feature: width

  /** getter for width - gets 
   * @generated
   * @return value of the feature 
   */
  public float getWidth() {
    if (DocumentElement_Type.featOkTst && ((DocumentElement_Type)jcasType).casFeat_width == null)
      jcasType.jcas.throwFeatMissing("width", "ch.epfl.bbp.uima.types.DocumentElement");
    return jcasType.ll_cas.ll_getFloatValue(addr, ((DocumentElement_Type)jcasType).casFeatCode_width);}
    
  /** setter for width - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setWidth(float v) {
    if (DocumentElement_Type.featOkTst && ((DocumentElement_Type)jcasType).casFeat_width == null)
      jcasType.jcas.throwFeatMissing("width", "ch.epfl.bbp.uima.types.DocumentElement");
    jcasType.ll_cas.ll_setFloatValue(addr, ((DocumentElement_Type)jcasType).casFeatCode_width, v);}    
   
    
  //*--------------*
  //* Feature: x

  /** getter for x - gets 
   * @generated
   * @return value of the feature 
   */
  public float getX() {
    if (DocumentElement_Type.featOkTst && ((DocumentElement_Type)jcasType).casFeat_x == null)
      jcasType.jcas.throwFeatMissing("x", "ch.epfl.bbp.uima.types.DocumentElement");
    return jcasType.ll_cas.ll_getFloatValue(addr, ((DocumentElement_Type)jcasType).casFeatCode_x);}
    
  /** setter for x - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setX(float v) {
    if (DocumentElement_Type.featOkTst && ((DocumentElement_Type)jcasType).casFeat_x == null)
      jcasType.jcas.throwFeatMissing("x", "ch.epfl.bbp.uima.types.DocumentElement");
    jcasType.ll_cas.ll_setFloatValue(addr, ((DocumentElement_Type)jcasType).casFeatCode_x, v);}    
   
    
  //*--------------*
  //* Feature: y

  /** getter for y - gets 
   * @generated
   * @return value of the feature 
   */
  public float getY() {
    if (DocumentElement_Type.featOkTst && ((DocumentElement_Type)jcasType).casFeat_y == null)
      jcasType.jcas.throwFeatMissing("y", "ch.epfl.bbp.uima.types.DocumentElement");
    return jcasType.ll_cas.ll_getFloatValue(addr, ((DocumentElement_Type)jcasType).casFeatCode_y);}
    
  /** setter for y - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setY(float v) {
    if (DocumentElement_Type.featOkTst && ((DocumentElement_Type)jcasType).casFeat_y == null)
      jcasType.jcas.throwFeatMissing("y", "ch.epfl.bbp.uima.types.DocumentElement");
    jcasType.ll_cas.ll_setFloatValue(addr, ((DocumentElement_Type)jcasType).casFeatCode_y, v);}    
   
    
  //*--------------*
  //* Feature: pageId

  /** getter for pageId - gets 
   * @generated
   * @return value of the feature 
   */
  public int getPageId() {
    if (DocumentElement_Type.featOkTst && ((DocumentElement_Type)jcasType).casFeat_pageId == null)
      jcasType.jcas.throwFeatMissing("pageId", "ch.epfl.bbp.uima.types.DocumentElement");
    return jcasType.ll_cas.ll_getIntValue(addr, ((DocumentElement_Type)jcasType).casFeatCode_pageId);}
    
  /** setter for pageId - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setPageId(int v) {
    if (DocumentElement_Type.featOkTst && ((DocumentElement_Type)jcasType).casFeat_pageId == null)
      jcasType.jcas.throwFeatMissing("pageId", "ch.epfl.bbp.uima.types.DocumentElement");
    jcasType.ll_cas.ll_setIntValue(addr, ((DocumentElement_Type)jcasType).casFeatCode_pageId, v);}    
   
    
  //*--------------*
  //* Feature: label

  /** getter for label - gets 
   * @generated
   * @return value of the feature 
   */
  public String getLabel() {
    if (DocumentElement_Type.featOkTst && ((DocumentElement_Type)jcasType).casFeat_label == null)
      jcasType.jcas.throwFeatMissing("label", "ch.epfl.bbp.uima.types.DocumentElement");
    return jcasType.ll_cas.ll_getStringValue(addr, ((DocumentElement_Type)jcasType).casFeatCode_label);}
    
  /** setter for label - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setLabel(String v) {
    if (DocumentElement_Type.featOkTst && ((DocumentElement_Type)jcasType).casFeat_label == null)
      jcasType.jcas.throwFeatMissing("label", "ch.epfl.bbp.uima.types.DocumentElement");
    jcasType.ll_cas.ll_setStringValue(addr, ((DocumentElement_Type)jcasType).casFeatCode_label, v);}    
   
    
  //*--------------*
  //* Feature: medianFontsize

  /** getter for medianFontsize - gets 
   * @generated
   * @return value of the feature 
   */
  public double getMedianFontsize() {
    if (DocumentElement_Type.featOkTst && ((DocumentElement_Type)jcasType).casFeat_medianFontsize == null)
      jcasType.jcas.throwFeatMissing("medianFontsize", "ch.epfl.bbp.uima.types.DocumentElement");
    return jcasType.ll_cas.ll_getDoubleValue(addr, ((DocumentElement_Type)jcasType).casFeatCode_medianFontsize);}
    
  /** setter for medianFontsize - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setMedianFontsize(double v) {
    if (DocumentElement_Type.featOkTst && ((DocumentElement_Type)jcasType).casFeat_medianFontsize == null)
      jcasType.jcas.throwFeatMissing("medianFontsize", "ch.epfl.bbp.uima.types.DocumentElement");
    jcasType.ll_cas.ll_setDoubleValue(addr, ((DocumentElement_Type)jcasType).casFeatCode_medianFontsize, v);}    
  }

    