
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

/** A dependency between two word forms
 * Updated by JCasGen Sat Mar 07 22:05:56 CET 2015
 * @generated */
public class Dependency_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Dependency_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Dependency_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Dependency(addr, Dependency_Type.this);
  			   Dependency_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Dependency(addr, Dependency_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Dependency.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("com.digitalpebble.rasp.Dependency");
 
  /** @generated */
  final Feature casFeat_deptype;
  /** @generated */
  final int     casFeatCode_deptype;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getDeptype(int addr) {
        if (featOkTst && casFeat_deptype == null)
      jcas.throwFeatMissing("deptype", "com.digitalpebble.rasp.Dependency");
    return ll_cas.ll_getStringValue(addr, casFeatCode_deptype);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setDeptype(int addr, String v) {
        if (featOkTst && casFeat_deptype == null)
      jcas.throwFeatMissing("deptype", "com.digitalpebble.rasp.Dependency");
    ll_cas.ll_setStringValue(addr, casFeatCode_deptype, v);}
    
  
 
  /** @generated */
  final Feature casFeat_subtype;
  /** @generated */
  final int     casFeatCode_subtype;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getSubtype(int addr) {
        if (featOkTst && casFeat_subtype == null)
      jcas.throwFeatMissing("subtype", "com.digitalpebble.rasp.Dependency");
    return ll_cas.ll_getStringValue(addr, casFeatCode_subtype);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setSubtype(int addr, String v) {
        if (featOkTst && casFeat_subtype == null)
      jcas.throwFeatMissing("subtype", "com.digitalpebble.rasp.Dependency");
    ll_cas.ll_setStringValue(addr, casFeatCode_subtype, v);}
    
  
 
  /** @generated */
  final Feature casFeat_head;
  /** @generated */
  final int     casFeatCode_head;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getHead(int addr) {
        if (featOkTst && casFeat_head == null)
      jcas.throwFeatMissing("head", "com.digitalpebble.rasp.Dependency");
    return ll_cas.ll_getRefValue(addr, casFeatCode_head);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setHead(int addr, int v) {
        if (featOkTst && casFeat_head == null)
      jcas.throwFeatMissing("head", "com.digitalpebble.rasp.Dependency");
    ll_cas.ll_setRefValue(addr, casFeatCode_head, v);}
    
  
 
  /** @generated */
  final Feature casFeat_dep;
  /** @generated */
  final int     casFeatCode_dep;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getDep(int addr) {
        if (featOkTst && casFeat_dep == null)
      jcas.throwFeatMissing("dep", "com.digitalpebble.rasp.Dependency");
    return ll_cas.ll_getRefValue(addr, casFeatCode_dep);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setDep(int addr, int v) {
        if (featOkTst && casFeat_dep == null)
      jcas.throwFeatMissing("dep", "com.digitalpebble.rasp.Dependency");
    ll_cas.ll_setRefValue(addr, casFeatCode_dep, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public Dependency_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_deptype = jcas.getRequiredFeatureDE(casType, "deptype", "uima.cas.String", featOkTst);
    casFeatCode_deptype  = (null == casFeat_deptype) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_deptype).getCode();

 
    casFeat_subtype = jcas.getRequiredFeatureDE(casType, "subtype", "uima.cas.String", featOkTst);
    casFeatCode_subtype  = (null == casFeat_subtype) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_subtype).getCode();

 
    casFeat_head = jcas.getRequiredFeatureDE(casType, "head", "com.digitalpebble.rasp.WordForm", featOkTst);
    casFeatCode_head  = (null == casFeat_head) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_head).getCode();

 
    casFeat_dep = jcas.getRequiredFeatureDE(casType, "dep", "com.digitalpebble.rasp.WordForm", featOkTst);
    casFeatCode_dep  = (null == casFeat_dep) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_dep).getCode();

  }
}



    