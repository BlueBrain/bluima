
/* First created by JCasGen Sun Mar 11 02:53:34 CET 2012 */
package org.apache.uima.conceptMapper;

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

/** Annotation for dictionary lookup matches
 * Updated by JCasGen Mon Oct 21 13:03:30 CEST 2013
 * @generated */
public class DictTerm_Type extends Annotation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (DictTerm_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = DictTerm_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new DictTerm(addr, DictTerm_Type.this);
  			   DictTerm_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new DictTerm(addr, DictTerm_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = DictTerm.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("org.apache.uima.conceptMapper.DictTerm");
 
  /** @generated */
  final Feature casFeat_DictCanon;
  /** @generated */
  final int     casFeatCode_DictCanon;
  /** @generated */ 
  public String getDictCanon(int addr) {
        if (featOkTst && casFeat_DictCanon == null)
      jcas.throwFeatMissing("DictCanon", "org.apache.uima.conceptMapper.DictTerm");
    return ll_cas.ll_getStringValue(addr, casFeatCode_DictCanon);
  }
  /** @generated */    
  public void setDictCanon(int addr, String v) {
        if (featOkTst && casFeat_DictCanon == null)
      jcas.throwFeatMissing("DictCanon", "org.apache.uima.conceptMapper.DictTerm");
    ll_cas.ll_setStringValue(addr, casFeatCode_DictCanon, v);}
    
  
 
  /** @generated */
  final Feature casFeat_enclosingSpan;
  /** @generated */
  final int     casFeatCode_enclosingSpan;
  /** @generated */ 
  public int getEnclosingSpan(int addr) {
        if (featOkTst && casFeat_enclosingSpan == null)
      jcas.throwFeatMissing("enclosingSpan", "org.apache.uima.conceptMapper.DictTerm");
    return ll_cas.ll_getRefValue(addr, casFeatCode_enclosingSpan);
  }
  /** @generated */    
  public void setEnclosingSpan(int addr, int v) {
        if (featOkTst && casFeat_enclosingSpan == null)
      jcas.throwFeatMissing("enclosingSpan", "org.apache.uima.conceptMapper.DictTerm");
    ll_cas.ll_setRefValue(addr, casFeatCode_enclosingSpan, v);}
    
  
 
  /** @generated */
  final Feature casFeat_matchedText;
  /** @generated */
  final int     casFeatCode_matchedText;
  /** @generated */ 
  public String getMatchedText(int addr) {
        if (featOkTst && casFeat_matchedText == null)
      jcas.throwFeatMissing("matchedText", "org.apache.uima.conceptMapper.DictTerm");
    return ll_cas.ll_getStringValue(addr, casFeatCode_matchedText);
  }
  /** @generated */    
  public void setMatchedText(int addr, String v) {
        if (featOkTst && casFeat_matchedText == null)
      jcas.throwFeatMissing("matchedText", "org.apache.uima.conceptMapper.DictTerm");
    ll_cas.ll_setStringValue(addr, casFeatCode_matchedText, v);}
    
  
 
  /** @generated */
  final Feature casFeat_matchedTokens;
  /** @generated */
  final int     casFeatCode_matchedTokens;
  /** @generated */ 
  public int getMatchedTokens(int addr) {
        if (featOkTst && casFeat_matchedTokens == null)
      jcas.throwFeatMissing("matchedTokens", "org.apache.uima.conceptMapper.DictTerm");
    return ll_cas.ll_getRefValue(addr, casFeatCode_matchedTokens);
  }
  /** @generated */    
  public void setMatchedTokens(int addr, int v) {
        if (featOkTst && casFeat_matchedTokens == null)
      jcas.throwFeatMissing("matchedTokens", "org.apache.uima.conceptMapper.DictTerm");
    ll_cas.ll_setRefValue(addr, casFeatCode_matchedTokens, v);}
    
   /** @generated */
  public int getMatchedTokens(int addr, int i) {
        if (featOkTst && casFeat_matchedTokens == null)
      jcas.throwFeatMissing("matchedTokens", "org.apache.uima.conceptMapper.DictTerm");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_matchedTokens), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_matchedTokens), i);
  return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_matchedTokens), i);
  }
   
  /** @generated */ 
  public void setMatchedTokens(int addr, int i, int v) {
        if (featOkTst && casFeat_matchedTokens == null)
      jcas.throwFeatMissing("matchedTokens", "org.apache.uima.conceptMapper.DictTerm");
    if (lowLevelTypeChecks)
      ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_matchedTokens), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_matchedTokens), i);
    ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_matchedTokens), i, v);
  }
 



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public DictTerm_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_DictCanon = jcas.getRequiredFeatureDE(casType, "DictCanon", "uima.cas.String", featOkTst);
    casFeatCode_DictCanon  = (null == casFeat_DictCanon) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_DictCanon).getCode();

 
    casFeat_enclosingSpan = jcas.getRequiredFeatureDE(casType, "enclosingSpan", "uima.tcas.Annotation", featOkTst);
    casFeatCode_enclosingSpan  = (null == casFeat_enclosingSpan) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_enclosingSpan).getCode();

 
    casFeat_matchedText = jcas.getRequiredFeatureDE(casType, "matchedText", "uima.cas.String", featOkTst);
    casFeatCode_matchedText  = (null == casFeat_matchedText) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_matchedText).getCode();

 
    casFeat_matchedTokens = jcas.getRequiredFeatureDE(casType, "matchedTokens", "uima.cas.FSArray", featOkTst);
    casFeatCode_matchedTokens  = (null == casFeat_matchedTokens) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_matchedTokens).getCode();

  }
}



    