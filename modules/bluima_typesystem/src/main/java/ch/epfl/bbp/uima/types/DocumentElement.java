

/* First created by JCasGen Mon Feb 17 12:26:53 CET 2014 */
package ch.epfl.bbp.uima.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Mon Feb 17 22:12:56 CET 2014
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/src/main/resources/typeSystem/bbp-types.xml
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
  /** @generated  */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected DocumentElement() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public DocumentElement(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public DocumentElement(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public DocumentElement(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** <!-- begin-user-doc -->
    * Write your own initialization here
    * <!-- end-user-doc -->
  @generated modifiable */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: ElementId

  /** getter for ElementId - gets 
   * @generated */
  public int getElementId() {
    if (DocumentElement_Type.featOkTst && ((DocumentElement_Type)jcasType).casFeat_ElementId == null)
      jcasType.jcas.throwFeatMissing("ElementId", "ch.epfl.bbp.uima.types.DocumentElement");
    return jcasType.ll_cas.ll_getIntValue(addr, ((DocumentElement_Type)jcasType).casFeatCode_ElementId);}
    
  /** setter for ElementId - sets  
   * @generated */
  public void setElementId(int v) {
    if (DocumentElement_Type.featOkTst && ((DocumentElement_Type)jcasType).casFeat_ElementId == null)
      jcasType.jcas.throwFeatMissing("ElementId", "ch.epfl.bbp.uima.types.DocumentElement");
    jcasType.ll_cas.ll_setIntValue(addr, ((DocumentElement_Type)jcasType).casFeatCode_ElementId, v);}    
   
    
  //*--------------*
  //* Feature: isBold

  /** getter for isBold - gets 
   * @generated */
  public boolean getIsBold() {
    if (DocumentElement_Type.featOkTst && ((DocumentElement_Type)jcasType).casFeat_isBold == null)
      jcasType.jcas.throwFeatMissing("isBold", "ch.epfl.bbp.uima.types.DocumentElement");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((DocumentElement_Type)jcasType).casFeatCode_isBold);}
    
  /** setter for isBold - sets  
   * @generated */
  public void setIsBold(boolean v) {
    if (DocumentElement_Type.featOkTst && ((DocumentElement_Type)jcasType).casFeat_isBold == null)
      jcasType.jcas.throwFeatMissing("isBold", "ch.epfl.bbp.uima.types.DocumentElement");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((DocumentElement_Type)jcasType).casFeatCode_isBold, v);}    
   
    
  //*--------------*
  //* Feature: height

  /** getter for height - gets 
   * @generated */
  public float getHeight() {
    if (DocumentElement_Type.featOkTst && ((DocumentElement_Type)jcasType).casFeat_height == null)
      jcasType.jcas.throwFeatMissing("height", "ch.epfl.bbp.uima.types.DocumentElement");
    return jcasType.ll_cas.ll_getFloatValue(addr, ((DocumentElement_Type)jcasType).casFeatCode_height);}
    
  /** setter for height - sets  
   * @generated */
  public void setHeight(float v) {
    if (DocumentElement_Type.featOkTst && ((DocumentElement_Type)jcasType).casFeat_height == null)
      jcasType.jcas.throwFeatMissing("height", "ch.epfl.bbp.uima.types.DocumentElement");
    jcasType.ll_cas.ll_setFloatValue(addr, ((DocumentElement_Type)jcasType).casFeatCode_height, v);}    
   
    
  //*--------------*
  //* Feature: width

  /** getter for width - gets 
   * @generated */
  public float getWidth() {
    if (DocumentElement_Type.featOkTst && ((DocumentElement_Type)jcasType).casFeat_width == null)
      jcasType.jcas.throwFeatMissing("width", "ch.epfl.bbp.uima.types.DocumentElement");
    return jcasType.ll_cas.ll_getFloatValue(addr, ((DocumentElement_Type)jcasType).casFeatCode_width);}
    
  /** setter for width - sets  
   * @generated */
  public void setWidth(float v) {
    if (DocumentElement_Type.featOkTst && ((DocumentElement_Type)jcasType).casFeat_width == null)
      jcasType.jcas.throwFeatMissing("width", "ch.epfl.bbp.uima.types.DocumentElement");
    jcasType.ll_cas.ll_setFloatValue(addr, ((DocumentElement_Type)jcasType).casFeatCode_width, v);}    
   
    
  //*--------------*
  //* Feature: x

  /** getter for x - gets 
   * @generated */
  public float getX() {
    if (DocumentElement_Type.featOkTst && ((DocumentElement_Type)jcasType).casFeat_x == null)
      jcasType.jcas.throwFeatMissing("x", "ch.epfl.bbp.uima.types.DocumentElement");
    return jcasType.ll_cas.ll_getFloatValue(addr, ((DocumentElement_Type)jcasType).casFeatCode_x);}
    
  /** setter for x - sets  
   * @generated */
  public void setX(float v) {
    if (DocumentElement_Type.featOkTst && ((DocumentElement_Type)jcasType).casFeat_x == null)
      jcasType.jcas.throwFeatMissing("x", "ch.epfl.bbp.uima.types.DocumentElement");
    jcasType.ll_cas.ll_setFloatValue(addr, ((DocumentElement_Type)jcasType).casFeatCode_x, v);}    
   
    
  //*--------------*
  //* Feature: y

  /** getter for y - gets 
   * @generated */
  public float getY() {
    if (DocumentElement_Type.featOkTst && ((DocumentElement_Type)jcasType).casFeat_y == null)
      jcasType.jcas.throwFeatMissing("y", "ch.epfl.bbp.uima.types.DocumentElement");
    return jcasType.ll_cas.ll_getFloatValue(addr, ((DocumentElement_Type)jcasType).casFeatCode_y);}
    
  /** setter for y - sets  
   * @generated */
  public void setY(float v) {
    if (DocumentElement_Type.featOkTst && ((DocumentElement_Type)jcasType).casFeat_y == null)
      jcasType.jcas.throwFeatMissing("y", "ch.epfl.bbp.uima.types.DocumentElement");
    jcasType.ll_cas.ll_setFloatValue(addr, ((DocumentElement_Type)jcasType).casFeatCode_y, v);}    
   
    
  //*--------------*
  //* Feature: pageId

  /** getter for pageId - gets 
   * @generated */
  public int getPageId() {
    if (DocumentElement_Type.featOkTst && ((DocumentElement_Type)jcasType).casFeat_pageId == null)
      jcasType.jcas.throwFeatMissing("pageId", "ch.epfl.bbp.uima.types.DocumentElement");
    return jcasType.ll_cas.ll_getIntValue(addr, ((DocumentElement_Type)jcasType).casFeatCode_pageId);}
    
  /** setter for pageId - sets  
   * @generated */
  public void setPageId(int v) {
    if (DocumentElement_Type.featOkTst && ((DocumentElement_Type)jcasType).casFeat_pageId == null)
      jcasType.jcas.throwFeatMissing("pageId", "ch.epfl.bbp.uima.types.DocumentElement");
    jcasType.ll_cas.ll_setIntValue(addr, ((DocumentElement_Type)jcasType).casFeatCode_pageId, v);}    
   
    
  //*--------------*
  //* Feature: label

  /** getter for label - gets 
   * @generated */
  public String getLabel() {
    if (DocumentElement_Type.featOkTst && ((DocumentElement_Type)jcasType).casFeat_label == null)
      jcasType.jcas.throwFeatMissing("label", "ch.epfl.bbp.uima.types.DocumentElement");
    return jcasType.ll_cas.ll_getStringValue(addr, ((DocumentElement_Type)jcasType).casFeatCode_label);}
    
  /** setter for label - sets  
   * @generated */
  public void setLabel(String v) {
    if (DocumentElement_Type.featOkTst && ((DocumentElement_Type)jcasType).casFeat_label == null)
      jcasType.jcas.throwFeatMissing("label", "ch.epfl.bbp.uima.types.DocumentElement");
    jcasType.ll_cas.ll_setStringValue(addr, ((DocumentElement_Type)jcasType).casFeatCode_label, v);}    
   
    
  //*--------------*
  //* Feature: medianFontsize

  /** getter for medianFontsize - gets 
   * @generated */
  public double getMedianFontsize() {
    if (DocumentElement_Type.featOkTst && ((DocumentElement_Type)jcasType).casFeat_medianFontsize == null)
      jcasType.jcas.throwFeatMissing("medianFontsize", "ch.epfl.bbp.uima.types.DocumentElement");
    return jcasType.ll_cas.ll_getDoubleValue(addr, ((DocumentElement_Type)jcasType).casFeatCode_medianFontsize);}
    
  /** setter for medianFontsize - sets  
   * @generated */
  public void setMedianFontsize(double v) {
    if (DocumentElement_Type.featOkTst && ((DocumentElement_Type)jcasType).casFeat_medianFontsize == null)
      jcasType.jcas.throwFeatMissing("medianFontsize", "ch.epfl.bbp.uima.types.DocumentElement");
    jcasType.ll_cas.ll_setDoubleValue(addr, ((DocumentElement_Type)jcasType).casFeatCode_medianFontsize, v);}    
  }

    