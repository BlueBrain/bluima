
/* First created by JCasGen Sun May 27 17:27:29 CEST 2012 */
package com.digitalpebble.rasp;

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

/** A WordForm consists of a POS tag, a lemma and possibly a probability. There is one or more WordForm per Token (as in the MAF ISO Norm)
 * Updated by JCasGen Sun May 27 17:27:29 CEST 2012
 * @generated */
public class WordForm_Type extends Annotation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (WordForm_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = WordForm_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new WordForm(addr, WordForm_Type.this);
  			   WordForm_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new WordForm(addr, WordForm_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = WordForm.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("com.digitalpebble.rasp.WordForm");
 
  /** @generated */
  final Feature casFeat_lemma;
  /** @generated */
  final int     casFeatCode_lemma;
  /** @generated */ 
  public String getLemma(int addr) {
        if (featOkTst && casFeat_lemma == null)
      jcas.throwFeatMissing("lemma", "com.digitalpebble.rasp.WordForm");
    return ll_cas.ll_getStringValue(addr, casFeatCode_lemma);
  }
  /** @generated */    
  public void setLemma(int addr, String v) {
        if (featOkTst && casFeat_lemma == null)
      jcas.throwFeatMissing("lemma", "com.digitalpebble.rasp.WordForm");
    ll_cas.ll_setStringValue(addr, casFeatCode_lemma, v);}
    
  
 
  /** @generated */
  final Feature casFeat_POS;
  /** @generated */
  final int     casFeatCode_POS;
  /** @generated */ 
  public String getPOS(int addr) {
        if (featOkTst && casFeat_POS == null)
      jcas.throwFeatMissing("POS", "com.digitalpebble.rasp.WordForm");
    return ll_cas.ll_getStringValue(addr, casFeatCode_POS);
  }
  /** @generated */    
  public void setPOS(int addr, String v) {
        if (featOkTst && casFeat_POS == null)
      jcas.throwFeatMissing("POS", "com.digitalpebble.rasp.WordForm");
    ll_cas.ll_setStringValue(addr, casFeatCode_POS, v);}
    
  
 
  /** @generated */
  final Feature casFeat_probability;
  /** @generated */
  final int     casFeatCode_probability;
  /** @generated */ 
  public double getProbability(int addr) {
        if (featOkTst && casFeat_probability == null)
      jcas.throwFeatMissing("probability", "com.digitalpebble.rasp.WordForm");
    return ll_cas.ll_getDoubleValue(addr, casFeatCode_probability);
  }
  /** @generated */    
  public void setProbability(int addr, double v) {
        if (featOkTst && casFeat_probability == null)
      jcas.throwFeatMissing("probability", "com.digitalpebble.rasp.WordForm");
    ll_cas.ll_setDoubleValue(addr, casFeatCode_probability, v);}
    
  
 
  /** @generated */
  final Feature casFeat_suffix;
  /** @generated */
  final int     casFeatCode_suffix;
  /** @generated */ 
  public String getSuffix(int addr) {
        if (featOkTst && casFeat_suffix == null)
      jcas.throwFeatMissing("suffix", "com.digitalpebble.rasp.WordForm");
    return ll_cas.ll_getStringValue(addr, casFeatCode_suffix);
  }
  /** @generated */    
  public void setSuffix(int addr, String v) {
        if (featOkTst && casFeat_suffix == null)
      jcas.throwFeatMissing("suffix", "com.digitalpebble.rasp.WordForm");
    ll_cas.ll_setStringValue(addr, casFeatCode_suffix, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public WordForm_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_lemma = jcas.getRequiredFeatureDE(casType, "lemma", "uima.cas.String", featOkTst);
    casFeatCode_lemma  = (null == casFeat_lemma) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_lemma).getCode();

 
    casFeat_POS = jcas.getRequiredFeatureDE(casType, "POS", "uima.cas.String", featOkTst);
    casFeatCode_POS  = (null == casFeat_POS) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_POS).getCode();

 
    casFeat_probability = jcas.getRequiredFeatureDE(casType, "probability", "uima.cas.Double", featOkTst);
    casFeatCode_probability  = (null == casFeat_probability) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_probability).getCode();

 
    casFeat_suffix = jcas.getRequiredFeatureDE(casType, "suffix", "uima.cas.String", featOkTst);
    casFeatCode_suffix  = (null == casFeat_suffix) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_suffix).getCode();

  }
}



    