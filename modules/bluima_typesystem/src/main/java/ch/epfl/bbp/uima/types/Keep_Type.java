
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
public class Keep_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Keep_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Keep_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Keep(addr, Keep_Type.this);
  			   Keep_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Keep(addr, Keep_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Keep.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("ch.epfl.bbp.uima.types.Keep");
 
  /** @generated */
  final Feature casFeat_enclosedAnnot;
  /** @generated */
  final int     casFeatCode_enclosedAnnot;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getEnclosedAnnot(int addr) {
        if (featOkTst && casFeat_enclosedAnnot == null)
      jcas.throwFeatMissing("enclosedAnnot", "ch.epfl.bbp.uima.types.Keep");
    return ll_cas.ll_getRefValue(addr, casFeatCode_enclosedAnnot);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setEnclosedAnnot(int addr, int v) {
        if (featOkTst && casFeat_enclosedAnnot == null)
      jcas.throwFeatMissing("enclosedAnnot", "ch.epfl.bbp.uima.types.Keep");
    ll_cas.ll_setRefValue(addr, casFeatCode_enclosedAnnot, v);}
    
  
 
  /** @generated */
  final Feature casFeat_normalizedText;
  /** @generated */
  final int     casFeatCode_normalizedText;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getNormalizedText(int addr) {
        if (featOkTst && casFeat_normalizedText == null)
      jcas.throwFeatMissing("normalizedText", "ch.epfl.bbp.uima.types.Keep");
    return ll_cas.ll_getStringValue(addr, casFeatCode_normalizedText);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setNormalizedText(int addr, String v) {
        if (featOkTst && casFeat_normalizedText == null)
      jcas.throwFeatMissing("normalizedText", "ch.epfl.bbp.uima.types.Keep");
    ll_cas.ll_setStringValue(addr, casFeatCode_normalizedText, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public Keep_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_enclosedAnnot = jcas.getRequiredFeatureDE(casType, "enclosedAnnot", "uima.tcas.Annotation", featOkTst);
    casFeatCode_enclosedAnnot  = (null == casFeat_enclosedAnnot) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_enclosedAnnot).getCode();

 
    casFeat_normalizedText = jcas.getRequiredFeatureDE(casType, "normalizedText", "uima.cas.String", featOkTst);
    casFeatCode_normalizedText  = (null == casFeat_normalizedText) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_normalizedText).getCode();

  }
}



    