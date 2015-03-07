
/* First created by JCasGen Sat Mar 07 22:05:57 CET 2015 */
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

/** A token for Rasp
 * Updated by JCasGen Sat Mar 07 22:05:57 CET 2015
 * @generated */
public class Token_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Token_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Token_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Token(addr, Token_Type.this);
  			   Token_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Token(addr, Token_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Token.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("com.digitalpebble.rasp.Token");
 
  /** @generated */
  final Feature casFeat_wordForms;
  /** @generated */
  final int     casFeatCode_wordForms;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getWordForms(int addr) {
        if (featOkTst && casFeat_wordForms == null)
      jcas.throwFeatMissing("wordForms", "com.digitalpebble.rasp.Token");
    return ll_cas.ll_getRefValue(addr, casFeatCode_wordForms);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setWordForms(int addr, int v) {
        if (featOkTst && casFeat_wordForms == null)
      jcas.throwFeatMissing("wordForms", "com.digitalpebble.rasp.Token");
    ll_cas.ll_setRefValue(addr, casFeatCode_wordForms, v);}
    
   /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @return value at index i in the array 
   */
  public int getWordForms(int addr, int i) {
        if (featOkTst && casFeat_wordForms == null)
      jcas.throwFeatMissing("wordForms", "com.digitalpebble.rasp.Token");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_wordForms), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_wordForms), i);
	return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_wordForms), i);
  }
   
  /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @param v value to set
   */ 
  public void setWordForms(int addr, int i, int v) {
        if (featOkTst && casFeat_wordForms == null)
      jcas.throwFeatMissing("wordForms", "com.digitalpebble.rasp.Token");
    if (lowLevelTypeChecks)
      ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_wordForms), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_wordForms), i);
    ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_wordForms), i, v);
  }
 



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public Token_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_wordForms = jcas.getRequiredFeatureDE(casType, "wordForms", "uima.cas.FSArray", featOkTst);
    casFeatCode_wordForms  = (null == casFeat_wordForms) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_wordForms).getCode();

  }
}



    