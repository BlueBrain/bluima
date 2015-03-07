
/* First created by JCasGen Sat Mar 07 22:05:56 CET 2015 */
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

/** A clause as returned by the RASP analyser. It can contain one or more word forms or clauses
 * Updated by JCasGen Sat Mar 07 22:05:56 CET 2015
 * @generated */
public class Clause_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Clause_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Clause_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Clause(addr, Clause_Type.this);
  			   Clause_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Clause(addr, Clause_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Clause.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("com.digitalpebble.rasp.Clause");
 
  /** @generated */
  final Feature casFeat_rule;
  /** @generated */
  final int     casFeatCode_rule;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getRule(int addr) {
        if (featOkTst && casFeat_rule == null)
      jcas.throwFeatMissing("rule", "com.digitalpebble.rasp.Clause");
    return ll_cas.ll_getStringValue(addr, casFeatCode_rule);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setRule(int addr, String v) {
        if (featOkTst && casFeat_rule == null)
      jcas.throwFeatMissing("rule", "com.digitalpebble.rasp.Clause");
    ll_cas.ll_setStringValue(addr, casFeatCode_rule, v);}
    
  
 
  /** @generated */
  final Feature casFeat_subclauses;
  /** @generated */
  final int     casFeatCode_subclauses;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getSubclauses(int addr) {
        if (featOkTst && casFeat_subclauses == null)
      jcas.throwFeatMissing("subclauses", "com.digitalpebble.rasp.Clause");
    return ll_cas.ll_getRefValue(addr, casFeatCode_subclauses);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setSubclauses(int addr, int v) {
        if (featOkTst && casFeat_subclauses == null)
      jcas.throwFeatMissing("subclauses", "com.digitalpebble.rasp.Clause");
    ll_cas.ll_setRefValue(addr, casFeatCode_subclauses, v);}
    
   /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @return value at index i in the array 
   */
  public int getSubclauses(int addr, int i) {
        if (featOkTst && casFeat_subclauses == null)
      jcas.throwFeatMissing("subclauses", "com.digitalpebble.rasp.Clause");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_subclauses), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_subclauses), i);
	return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_subclauses), i);
  }
   
  /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @param v value to set
   */ 
  public void setSubclauses(int addr, int i, int v) {
        if (featOkTst && casFeat_subclauses == null)
      jcas.throwFeatMissing("subclauses", "com.digitalpebble.rasp.Clause");
    if (lowLevelTypeChecks)
      ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_subclauses), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_subclauses), i);
    ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_subclauses), i, v);
  }
 



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public Clause_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_rule = jcas.getRequiredFeatureDE(casType, "rule", "uima.cas.String", featOkTst);
    casFeatCode_rule  = (null == casFeat_rule) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_rule).getCode();

 
    casFeat_subclauses = jcas.getRequiredFeatureDE(casType, "subclauses", "uima.cas.FSArray", featOkTst);
    casFeatCode_subclauses  = (null == casFeat_subclauses) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_subclauses).getCode();

  }
}



    