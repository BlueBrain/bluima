
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
public class BioVerb_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (BioVerb_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = BioVerb_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new BioVerb(addr, BioVerb_Type.this);
  			   BioVerb_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new BioVerb(addr, BioVerb_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = BioVerb.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("ch.epfl.bbp.uima.types.BioVerb");
 
  /** @generated */
  final Feature casFeat_ref_id;
  /** @generated */
  final int     casFeatCode_ref_id;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getRef_id(int addr) {
        if (featOkTst && casFeat_ref_id == null)
      jcas.throwFeatMissing("ref_id", "ch.epfl.bbp.uima.types.BioVerb");
    return ll_cas.ll_getStringValue(addr, casFeatCode_ref_id);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setRef_id(int addr, String v) {
        if (featOkTst && casFeat_ref_id == null)
      jcas.throwFeatMissing("ref_id", "ch.epfl.bbp.uima.types.BioVerb");
    ll_cas.ll_setStringValue(addr, casFeatCode_ref_id, v);}
    
  
 
  /** @generated */
  final Feature casFeat_canonical;
  /** @generated */
  final int     casFeatCode_canonical;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getCanonical(int addr) {
        if (featOkTst && casFeat_canonical == null)
      jcas.throwFeatMissing("canonical", "ch.epfl.bbp.uima.types.BioVerb");
    return ll_cas.ll_getStringValue(addr, casFeatCode_canonical);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setCanonical(int addr, String v) {
        if (featOkTst && casFeat_canonical == null)
      jcas.throwFeatMissing("canonical", "ch.epfl.bbp.uima.types.BioVerb");
    ll_cas.ll_setStringValue(addr, casFeatCode_canonical, v);}
    
  
 
  /** @generated */
  final Feature casFeat_enclosingSpan;
  /** @generated */
  final int     casFeatCode_enclosingSpan;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getEnclosingSpan(int addr) {
        if (featOkTst && casFeat_enclosingSpan == null)
      jcas.throwFeatMissing("enclosingSpan", "ch.epfl.bbp.uima.types.BioVerb");
    return ll_cas.ll_getRefValue(addr, casFeatCode_enclosingSpan);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setEnclosingSpan(int addr, int v) {
        if (featOkTst && casFeat_enclosingSpan == null)
      jcas.throwFeatMissing("enclosingSpan", "ch.epfl.bbp.uima.types.BioVerb");
    ll_cas.ll_setRefValue(addr, casFeatCode_enclosingSpan, v);}
    
  
 
  /** @generated */
  final Feature casFeat_matchedText;
  /** @generated */
  final int     casFeatCode_matchedText;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getMatchedText(int addr) {
        if (featOkTst && casFeat_matchedText == null)
      jcas.throwFeatMissing("matchedText", "ch.epfl.bbp.uima.types.BioVerb");
    return ll_cas.ll_getStringValue(addr, casFeatCode_matchedText);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setMatchedText(int addr, String v) {
        if (featOkTst && casFeat_matchedText == null)
      jcas.throwFeatMissing("matchedText", "ch.epfl.bbp.uima.types.BioVerb");
    ll_cas.ll_setStringValue(addr, casFeatCode_matchedText, v);}
    
  
 
  /** @generated */
  final Feature casFeat_matchedTokens;
  /** @generated */
  final int     casFeatCode_matchedTokens;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getMatchedTokens(int addr) {
        if (featOkTst && casFeat_matchedTokens == null)
      jcas.throwFeatMissing("matchedTokens", "ch.epfl.bbp.uima.types.BioVerb");
    return ll_cas.ll_getRefValue(addr, casFeatCode_matchedTokens);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setMatchedTokens(int addr, int v) {
        if (featOkTst && casFeat_matchedTokens == null)
      jcas.throwFeatMissing("matchedTokens", "ch.epfl.bbp.uima.types.BioVerb");
    ll_cas.ll_setRefValue(addr, casFeatCode_matchedTokens, v);}
    
   /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @return value at index i in the array 
   */
  public int getMatchedTokens(int addr, int i) {
        if (featOkTst && casFeat_matchedTokens == null)
      jcas.throwFeatMissing("matchedTokens", "ch.epfl.bbp.uima.types.BioVerb");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_matchedTokens), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_matchedTokens), i);
	return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_matchedTokens), i);
  }
   
  /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @param v value to set
   */ 
  public void setMatchedTokens(int addr, int i, int v) {
        if (featOkTst && casFeat_matchedTokens == null)
      jcas.throwFeatMissing("matchedTokens", "ch.epfl.bbp.uima.types.BioVerb");
    if (lowLevelTypeChecks)
      ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_matchedTokens), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_matchedTokens), i);
    ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_matchedTokens), i, v);
  }
 



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public BioVerb_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_ref_id = jcas.getRequiredFeatureDE(casType, "ref_id", "uima.cas.String", featOkTst);
    casFeatCode_ref_id  = (null == casFeat_ref_id) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_ref_id).getCode();

 
    casFeat_canonical = jcas.getRequiredFeatureDE(casType, "canonical", "uima.cas.String", featOkTst);
    casFeatCode_canonical  = (null == casFeat_canonical) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_canonical).getCode();

 
    casFeat_enclosingSpan = jcas.getRequiredFeatureDE(casType, "enclosingSpan", "uima.tcas.Annotation", featOkTst);
    casFeatCode_enclosingSpan  = (null == casFeat_enclosingSpan) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_enclosingSpan).getCode();

 
    casFeat_matchedText = jcas.getRequiredFeatureDE(casType, "matchedText", "uima.cas.String", featOkTst);
    casFeatCode_matchedText  = (null == casFeat_matchedText) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_matchedText).getCode();

 
    casFeat_matchedTokens = jcas.getRequiredFeatureDE(casType, "matchedTokens", "uima.cas.FSArray", featOkTst);
    casFeatCode_matchedTokens  = (null == casFeat_matchedTokens) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_matchedTokens).getCode();

  }
}



    