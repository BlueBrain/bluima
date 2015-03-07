
/* First created by JCasGen Sat Mar 07 22:05:56 CET 2015 */
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

/** 
 * Updated by JCasGen Sat Mar 07 22:05:56 CET 2015
 * @generated */
public class DocumentElement_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (DocumentElement_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = DocumentElement_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new DocumentElement(addr, DocumentElement_Type.this);
  			   DocumentElement_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new DocumentElement(addr, DocumentElement_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = DocumentElement.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("ch.epfl.bbp.uima.types.DocumentElement");
 
  /** @generated */
  final Feature casFeat_ElementId;
  /** @generated */
  final int     casFeatCode_ElementId;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getElementId(int addr) {
        if (featOkTst && casFeat_ElementId == null)
      jcas.throwFeatMissing("ElementId", "ch.epfl.bbp.uima.types.DocumentElement");
    return ll_cas.ll_getIntValue(addr, casFeatCode_ElementId);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setElementId(int addr, int v) {
        if (featOkTst && casFeat_ElementId == null)
      jcas.throwFeatMissing("ElementId", "ch.epfl.bbp.uima.types.DocumentElement");
    ll_cas.ll_setIntValue(addr, casFeatCode_ElementId, v);}
    
  
 
  /** @generated */
  final Feature casFeat_isBold;
  /** @generated */
  final int     casFeatCode_isBold;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public boolean getIsBold(int addr) {
        if (featOkTst && casFeat_isBold == null)
      jcas.throwFeatMissing("isBold", "ch.epfl.bbp.uima.types.DocumentElement");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_isBold);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setIsBold(int addr, boolean v) {
        if (featOkTst && casFeat_isBold == null)
      jcas.throwFeatMissing("isBold", "ch.epfl.bbp.uima.types.DocumentElement");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_isBold, v);}
    
  
 
  /** @generated */
  final Feature casFeat_height;
  /** @generated */
  final int     casFeatCode_height;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public float getHeight(int addr) {
        if (featOkTst && casFeat_height == null)
      jcas.throwFeatMissing("height", "ch.epfl.bbp.uima.types.DocumentElement");
    return ll_cas.ll_getFloatValue(addr, casFeatCode_height);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setHeight(int addr, float v) {
        if (featOkTst && casFeat_height == null)
      jcas.throwFeatMissing("height", "ch.epfl.bbp.uima.types.DocumentElement");
    ll_cas.ll_setFloatValue(addr, casFeatCode_height, v);}
    
  
 
  /** @generated */
  final Feature casFeat_width;
  /** @generated */
  final int     casFeatCode_width;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public float getWidth(int addr) {
        if (featOkTst && casFeat_width == null)
      jcas.throwFeatMissing("width", "ch.epfl.bbp.uima.types.DocumentElement");
    return ll_cas.ll_getFloatValue(addr, casFeatCode_width);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setWidth(int addr, float v) {
        if (featOkTst && casFeat_width == null)
      jcas.throwFeatMissing("width", "ch.epfl.bbp.uima.types.DocumentElement");
    ll_cas.ll_setFloatValue(addr, casFeatCode_width, v);}
    
  
 
  /** @generated */
  final Feature casFeat_x;
  /** @generated */
  final int     casFeatCode_x;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public float getX(int addr) {
        if (featOkTst && casFeat_x == null)
      jcas.throwFeatMissing("x", "ch.epfl.bbp.uima.types.DocumentElement");
    return ll_cas.ll_getFloatValue(addr, casFeatCode_x);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setX(int addr, float v) {
        if (featOkTst && casFeat_x == null)
      jcas.throwFeatMissing("x", "ch.epfl.bbp.uima.types.DocumentElement");
    ll_cas.ll_setFloatValue(addr, casFeatCode_x, v);}
    
  
 
  /** @generated */
  final Feature casFeat_y;
  /** @generated */
  final int     casFeatCode_y;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public float getY(int addr) {
        if (featOkTst && casFeat_y == null)
      jcas.throwFeatMissing("y", "ch.epfl.bbp.uima.types.DocumentElement");
    return ll_cas.ll_getFloatValue(addr, casFeatCode_y);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setY(int addr, float v) {
        if (featOkTst && casFeat_y == null)
      jcas.throwFeatMissing("y", "ch.epfl.bbp.uima.types.DocumentElement");
    ll_cas.ll_setFloatValue(addr, casFeatCode_y, v);}
    
  
 
  /** @generated */
  final Feature casFeat_pageId;
  /** @generated */
  final int     casFeatCode_pageId;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getPageId(int addr) {
        if (featOkTst && casFeat_pageId == null)
      jcas.throwFeatMissing("pageId", "ch.epfl.bbp.uima.types.DocumentElement");
    return ll_cas.ll_getIntValue(addr, casFeatCode_pageId);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setPageId(int addr, int v) {
        if (featOkTst && casFeat_pageId == null)
      jcas.throwFeatMissing("pageId", "ch.epfl.bbp.uima.types.DocumentElement");
    ll_cas.ll_setIntValue(addr, casFeatCode_pageId, v);}
    
  
 
  /** @generated */
  final Feature casFeat_label;
  /** @generated */
  final int     casFeatCode_label;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getLabel(int addr) {
        if (featOkTst && casFeat_label == null)
      jcas.throwFeatMissing("label", "ch.epfl.bbp.uima.types.DocumentElement");
    return ll_cas.ll_getStringValue(addr, casFeatCode_label);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setLabel(int addr, String v) {
        if (featOkTst && casFeat_label == null)
      jcas.throwFeatMissing("label", "ch.epfl.bbp.uima.types.DocumentElement");
    ll_cas.ll_setStringValue(addr, casFeatCode_label, v);}
    
  
 
  /** @generated */
  final Feature casFeat_medianFontsize;
  /** @generated */
  final int     casFeatCode_medianFontsize;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public double getMedianFontsize(int addr) {
        if (featOkTst && casFeat_medianFontsize == null)
      jcas.throwFeatMissing("medianFontsize", "ch.epfl.bbp.uima.types.DocumentElement");
    return ll_cas.ll_getDoubleValue(addr, casFeatCode_medianFontsize);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setMedianFontsize(int addr, double v) {
        if (featOkTst && casFeat_medianFontsize == null)
      jcas.throwFeatMissing("medianFontsize", "ch.epfl.bbp.uima.types.DocumentElement");
    ll_cas.ll_setDoubleValue(addr, casFeatCode_medianFontsize, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public DocumentElement_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_ElementId = jcas.getRequiredFeatureDE(casType, "ElementId", "uima.cas.Integer", featOkTst);
    casFeatCode_ElementId  = (null == casFeat_ElementId) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_ElementId).getCode();

 
    casFeat_isBold = jcas.getRequiredFeatureDE(casType, "isBold", "uima.cas.Boolean", featOkTst);
    casFeatCode_isBold  = (null == casFeat_isBold) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_isBold).getCode();

 
    casFeat_height = jcas.getRequiredFeatureDE(casType, "height", "uima.cas.Float", featOkTst);
    casFeatCode_height  = (null == casFeat_height) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_height).getCode();

 
    casFeat_width = jcas.getRequiredFeatureDE(casType, "width", "uima.cas.Float", featOkTst);
    casFeatCode_width  = (null == casFeat_width) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_width).getCode();

 
    casFeat_x = jcas.getRequiredFeatureDE(casType, "x", "uima.cas.Float", featOkTst);
    casFeatCode_x  = (null == casFeat_x) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_x).getCode();

 
    casFeat_y = jcas.getRequiredFeatureDE(casType, "y", "uima.cas.Float", featOkTst);
    casFeatCode_y  = (null == casFeat_y) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_y).getCode();

 
    casFeat_pageId = jcas.getRequiredFeatureDE(casType, "pageId", "uima.cas.Integer", featOkTst);
    casFeatCode_pageId  = (null == casFeat_pageId) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_pageId).getCode();

 
    casFeat_label = jcas.getRequiredFeatureDE(casType, "label", "uima.cas.String", featOkTst);
    casFeatCode_label  = (null == casFeat_label) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_label).getCode();

 
    casFeat_medianFontsize = jcas.getRequiredFeatureDE(casType, "medianFontsize", "uima.cas.Double", featOkTst);
    casFeatCode_medianFontsize  = (null == casFeat_medianFontsize) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_medianFontsize).getCode();

  }
}



    