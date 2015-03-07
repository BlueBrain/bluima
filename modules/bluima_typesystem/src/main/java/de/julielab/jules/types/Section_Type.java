
/* First created by JCasGen Sat Mar 07 22:05:57 CET 2015 */
package de.julielab.jules.types;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;

/** a section is a part of the text that often has a heading, an id, a section type, figures, tables, citations and footnotes that occur in this section
 * Updated by JCasGen Sat Mar 07 22:05:57 CET 2015
 * @generated */
public class Section_Type extends Zone_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Section_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Section_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Section(addr, Section_Type.this);
  			   Section_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Section(addr, Section_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Section.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.julielab.jules.types.Section");
 
  /** @generated */
  final Feature casFeat_sectionHeading;
  /** @generated */
  final int     casFeatCode_sectionHeading;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getSectionHeading(int addr) {
        if (featOkTst && casFeat_sectionHeading == null)
      jcas.throwFeatMissing("sectionHeading", "de.julielab.jules.types.Section");
    return ll_cas.ll_getRefValue(addr, casFeatCode_sectionHeading);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setSectionHeading(int addr, int v) {
        if (featOkTst && casFeat_sectionHeading == null)
      jcas.throwFeatMissing("sectionHeading", "de.julielab.jules.types.Section");
    ll_cas.ll_setRefValue(addr, casFeatCode_sectionHeading, v);}
    
  
 
  /** @generated */
  final Feature casFeat_sectionType;
  /** @generated */
  final int     casFeatCode_sectionType;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getSectionType(int addr) {
        if (featOkTst && casFeat_sectionType == null)
      jcas.throwFeatMissing("sectionType", "de.julielab.jules.types.Section");
    return ll_cas.ll_getStringValue(addr, casFeatCode_sectionType);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setSectionType(int addr, String v) {
        if (featOkTst && casFeat_sectionType == null)
      jcas.throwFeatMissing("sectionType", "de.julielab.jules.types.Section");
    ll_cas.ll_setStringValue(addr, casFeatCode_sectionType, v);}
    
  
 
  /** @generated */
  final Feature casFeat_textObjects;
  /** @generated */
  final int     casFeatCode_textObjects;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getTextObjects(int addr) {
        if (featOkTst && casFeat_textObjects == null)
      jcas.throwFeatMissing("textObjects", "de.julielab.jules.types.Section");
    return ll_cas.ll_getRefValue(addr, casFeatCode_textObjects);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setTextObjects(int addr, int v) {
        if (featOkTst && casFeat_textObjects == null)
      jcas.throwFeatMissing("textObjects", "de.julielab.jules.types.Section");
    ll_cas.ll_setRefValue(addr, casFeatCode_textObjects, v);}
    
   /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @return value at index i in the array 
   */
  public int getTextObjects(int addr, int i) {
        if (featOkTst && casFeat_textObjects == null)
      jcas.throwFeatMissing("textObjects", "de.julielab.jules.types.Section");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_textObjects), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_textObjects), i);
	return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_textObjects), i);
  }
   
  /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @param v value to set
   */ 
  public void setTextObjects(int addr, int i, int v) {
        if (featOkTst && casFeat_textObjects == null)
      jcas.throwFeatMissing("textObjects", "de.julielab.jules.types.Section");
    if (lowLevelTypeChecks)
      ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_textObjects), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_textObjects), i);
    ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_textObjects), i, v);
  }
 
 
  /** @generated */
  final Feature casFeat_sectionId;
  /** @generated */
  final int     casFeatCode_sectionId;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getSectionId(int addr) {
        if (featOkTst && casFeat_sectionId == null)
      jcas.throwFeatMissing("sectionId", "de.julielab.jules.types.Section");
    return ll_cas.ll_getStringValue(addr, casFeatCode_sectionId);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setSectionId(int addr, String v) {
        if (featOkTst && casFeat_sectionId == null)
      jcas.throwFeatMissing("sectionId", "de.julielab.jules.types.Section");
    ll_cas.ll_setStringValue(addr, casFeatCode_sectionId, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public Section_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_sectionHeading = jcas.getRequiredFeatureDE(casType, "sectionHeading", "de.julielab.jules.types.Title", featOkTst);
    casFeatCode_sectionHeading  = (null == casFeat_sectionHeading) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_sectionHeading).getCode();

 
    casFeat_sectionType = jcas.getRequiredFeatureDE(casType, "sectionType", "uima.cas.String", featOkTst);
    casFeatCode_sectionType  = (null == casFeat_sectionType) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_sectionType).getCode();

 
    casFeat_textObjects = jcas.getRequiredFeatureDE(casType, "textObjects", "uima.cas.FSArray", featOkTst);
    casFeatCode_textObjects  = (null == casFeat_textObjects) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_textObjects).getCode();

 
    casFeat_sectionId = jcas.getRequiredFeatureDE(casType, "sectionId", "uima.cas.String", featOkTst);
    casFeatCode_sectionId  = (null == casFeat_sectionId) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_sectionId).getCode();

  }
}



    