
/* First created by JCasGen Sat Mar 07 22:05:57 CET 2015 */
package de.julielab.jules.types.muc7;

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
 * Updated by JCasGen Sat Mar 07 22:05:57 CET 2015
 * @generated */
public class Coref_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Coref_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Coref_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Coref(addr, Coref_Type.this);
  			   Coref_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Coref(addr, Coref_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Coref.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.julielab.jules.types.muc7.Coref");
 
  /** @generated */
  final Feature casFeat_id;
  /** @generated */
  final int     casFeatCode_id;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getId(int addr) {
        if (featOkTst && casFeat_id == null)
      jcas.throwFeatMissing("id", "de.julielab.jules.types.muc7.Coref");
    return ll_cas.ll_getIntValue(addr, casFeatCode_id);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setId(int addr, int v) {
        if (featOkTst && casFeat_id == null)
      jcas.throwFeatMissing("id", "de.julielab.jules.types.muc7.Coref");
    ll_cas.ll_setIntValue(addr, casFeatCode_id, v);}
    
  
 
  /** @generated */
  final Feature casFeat_corefType;
  /** @generated */
  final int     casFeatCode_corefType;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getCorefType(int addr) {
        if (featOkTst && casFeat_corefType == null)
      jcas.throwFeatMissing("corefType", "de.julielab.jules.types.muc7.Coref");
    return ll_cas.ll_getStringValue(addr, casFeatCode_corefType);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setCorefType(int addr, String v) {
        if (featOkTst && casFeat_corefType == null)
      jcas.throwFeatMissing("corefType", "de.julielab.jules.types.muc7.Coref");
    ll_cas.ll_setStringValue(addr, casFeatCode_corefType, v);}
    
  
 
  /** @generated */
  final Feature casFeat_ref;
  /** @generated */
  final int     casFeatCode_ref;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getRef(int addr) {
        if (featOkTst && casFeat_ref == null)
      jcas.throwFeatMissing("ref", "de.julielab.jules.types.muc7.Coref");
    return ll_cas.ll_getRefValue(addr, casFeatCode_ref);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setRef(int addr, int v) {
        if (featOkTst && casFeat_ref == null)
      jcas.throwFeatMissing("ref", "de.julielab.jules.types.muc7.Coref");
    ll_cas.ll_setRefValue(addr, casFeatCode_ref, v);}
    
  
 
  /** @generated */
  final Feature casFeat_min;
  /** @generated */
  final int     casFeatCode_min;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getMin(int addr) {
        if (featOkTst && casFeat_min == null)
      jcas.throwFeatMissing("min", "de.julielab.jules.types.muc7.Coref");
    return ll_cas.ll_getStringValue(addr, casFeatCode_min);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setMin(int addr, String v) {
        if (featOkTst && casFeat_min == null)
      jcas.throwFeatMissing("min", "de.julielab.jules.types.muc7.Coref");
    ll_cas.ll_setStringValue(addr, casFeatCode_min, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public Coref_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_id = jcas.getRequiredFeatureDE(casType, "id", "uima.cas.Integer", featOkTst);
    casFeatCode_id  = (null == casFeat_id) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_id).getCode();

 
    casFeat_corefType = jcas.getRequiredFeatureDE(casType, "corefType", "uima.cas.String", featOkTst);
    casFeatCode_corefType  = (null == casFeat_corefType) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_corefType).getCode();

 
    casFeat_ref = jcas.getRequiredFeatureDE(casType, "ref", "de.julielab.jules.types.muc7.Coref", featOkTst);
    casFeatCode_ref  = (null == casFeat_ref) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_ref).getCode();

 
    casFeat_min = jcas.getRequiredFeatureDE(casType, "min", "uima.cas.String", featOkTst);
    casFeatCode_min  = (null == casFeat_min) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_min).getCode();

  }
}



    