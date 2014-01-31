
/* First created by JCasGen Fri Dec 14 11:41:22 CET 2012 */
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

/** a list of strings that represent the cleaned-up (pre-processed) tokens for use with LDA libs or others
 * Updated by JCasGen Fri Sep 20 01:02:03 CEST 2013
 * @generated */
public class FeatureTokens_Type extends Annotation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (FeatureTokens_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = FeatureTokens_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new FeatureTokens(addr, FeatureTokens_Type.this);
  			   FeatureTokens_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new FeatureTokens(addr, FeatureTokens_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = FeatureTokens.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("ch.epfl.bbp.uima.types.FeatureTokens");
 
  /** @generated */
  final Feature casFeat_tokens;
  /** @generated */
  final int     casFeatCode_tokens;
  /** @generated */ 
  public int getTokens(int addr) {
        if (featOkTst && casFeat_tokens == null)
      jcas.throwFeatMissing("tokens", "ch.epfl.bbp.uima.types.FeatureTokens");
    return ll_cas.ll_getRefValue(addr, casFeatCode_tokens);
  }
  /** @generated */    
  public void setTokens(int addr, int v) {
        if (featOkTst && casFeat_tokens == null)
      jcas.throwFeatMissing("tokens", "ch.epfl.bbp.uima.types.FeatureTokens");
    ll_cas.ll_setRefValue(addr, casFeatCode_tokens, v);}
    
   /** @generated */
  public String getTokens(int addr, int i) {
        if (featOkTst && casFeat_tokens == null)
      jcas.throwFeatMissing("tokens", "ch.epfl.bbp.uima.types.FeatureTokens");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_tokens), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_tokens), i);
  return ll_cas.ll_getStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_tokens), i);
  }
   
  /** @generated */ 
  public void setTokens(int addr, int i, String v) {
        if (featOkTst && casFeat_tokens == null)
      jcas.throwFeatMissing("tokens", "ch.epfl.bbp.uima.types.FeatureTokens");
    if (lowLevelTypeChecks)
      ll_cas.ll_setStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_tokens), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_tokens), i);
    ll_cas.ll_setStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_tokens), i, v);
  }
 
 
  /** @generated */
  final Feature casFeat_beginnings;
  /** @generated */
  final int     casFeatCode_beginnings;
  /** @generated */ 
  public int getBeginnings(int addr) {
        if (featOkTst && casFeat_beginnings == null)
      jcas.throwFeatMissing("beginnings", "ch.epfl.bbp.uima.types.FeatureTokens");
    return ll_cas.ll_getRefValue(addr, casFeatCode_beginnings);
  }
  /** @generated */    
  public void setBeginnings(int addr, int v) {
        if (featOkTst && casFeat_beginnings == null)
      jcas.throwFeatMissing("beginnings", "ch.epfl.bbp.uima.types.FeatureTokens");
    ll_cas.ll_setRefValue(addr, casFeatCode_beginnings, v);}
    
   /** @generated */
  public int getBeginnings(int addr, int i) {
        if (featOkTst && casFeat_beginnings == null)
      jcas.throwFeatMissing("beginnings", "ch.epfl.bbp.uima.types.FeatureTokens");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getIntArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_beginnings), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_beginnings), i);
  return ll_cas.ll_getIntArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_beginnings), i);
  }
   
  /** @generated */ 
  public void setBeginnings(int addr, int i, int v) {
        if (featOkTst && casFeat_beginnings == null)
      jcas.throwFeatMissing("beginnings", "ch.epfl.bbp.uima.types.FeatureTokens");
    if (lowLevelTypeChecks)
      ll_cas.ll_setIntArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_beginnings), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_beginnings), i);
    ll_cas.ll_setIntArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_beginnings), i, v);
  }
 
 
  /** @generated */
  final Feature casFeat_endings;
  /** @generated */
  final int     casFeatCode_endings;
  /** @generated */ 
  public int getEndings(int addr) {
        if (featOkTst && casFeat_endings == null)
      jcas.throwFeatMissing("endings", "ch.epfl.bbp.uima.types.FeatureTokens");
    return ll_cas.ll_getRefValue(addr, casFeatCode_endings);
  }
  /** @generated */    
  public void setEndings(int addr, int v) {
        if (featOkTst && casFeat_endings == null)
      jcas.throwFeatMissing("endings", "ch.epfl.bbp.uima.types.FeatureTokens");
    ll_cas.ll_setRefValue(addr, casFeatCode_endings, v);}
    
   /** @generated */
  public int getEndings(int addr, int i) {
        if (featOkTst && casFeat_endings == null)
      jcas.throwFeatMissing("endings", "ch.epfl.bbp.uima.types.FeatureTokens");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getIntArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_endings), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_endings), i);
  return ll_cas.ll_getIntArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_endings), i);
  }
   
  /** @generated */ 
  public void setEndings(int addr, int i, int v) {
        if (featOkTst && casFeat_endings == null)
      jcas.throwFeatMissing("endings", "ch.epfl.bbp.uima.types.FeatureTokens");
    if (lowLevelTypeChecks)
      ll_cas.ll_setIntArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_endings), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_endings), i);
    ll_cas.ll_setIntArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_endings), i, v);
  }
 



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public FeatureTokens_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_tokens = jcas.getRequiredFeatureDE(casType, "tokens", "uima.cas.StringArray", featOkTst);
    casFeatCode_tokens  = (null == casFeat_tokens) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_tokens).getCode();

 
    casFeat_beginnings = jcas.getRequiredFeatureDE(casType, "beginnings", "uima.cas.IntegerArray", featOkTst);
    casFeatCode_beginnings  = (null == casFeat_beginnings) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_beginnings).getCode();

 
    casFeat_endings = jcas.getRequiredFeatureDE(casType, "endings", "uima.cas.IntegerArray", featOkTst);
    casFeatCode_endings  = (null == casFeat_endings) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_endings).getCode();

  }
}



    