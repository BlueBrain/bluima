
/* First created by JCasGen Wed Oct 19 19:10:28 CEST 2011 */
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

/** Chains annotations of the same type
 * Updated by JCasGen Mon Feb 17 22:12:57 CET 2014
 * @generated */
public class DiscontinuousAnnotation_Type extends Annotation_Type {
  /** @generated */
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (DiscontinuousAnnotation_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = DiscontinuousAnnotation_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new DiscontinuousAnnotation(addr, DiscontinuousAnnotation_Type.this);
  			   DiscontinuousAnnotation_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new DiscontinuousAnnotation(addr, DiscontinuousAnnotation_Type.this);
  	  }
    };
  /** @generated */
  public final static int typeIndexID = DiscontinuousAnnotation.typeIndexID;
  /** @generated 
     @modifiable */
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.julielab.jules.types.DiscontinuousAnnotation");
 
  /** @generated */
  final Feature casFeat_value;
  /** @generated */
  final int     casFeatCode_value;
  /** @generated */ 
  public int getValue(int addr) {
        if (featOkTst && casFeat_value == null)
      jcas.throwFeatMissing("value", "de.julielab.jules.types.DiscontinuousAnnotation");
    return ll_cas.ll_getRefValue(addr, casFeatCode_value);
  }
  /** @generated */    
  public void setValue(int addr, int v) {
        if (featOkTst && casFeat_value == null)
      jcas.throwFeatMissing("value", "de.julielab.jules.types.DiscontinuousAnnotation");
    ll_cas.ll_setRefValue(addr, casFeatCode_value, v);}
    
   /** @generated */
  public int getValue(int addr, int i) {
        if (featOkTst && casFeat_value == null)
      jcas.throwFeatMissing("value", "de.julielab.jules.types.DiscontinuousAnnotation");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_value), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_value), i);
  return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_value), i);
  }
   
  /** @generated */ 
  public void setValue(int addr, int i, int v) {
        if (featOkTst && casFeat_value == null)
      jcas.throwFeatMissing("value", "de.julielab.jules.types.DiscontinuousAnnotation");
    if (lowLevelTypeChecks)
      ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_value), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_value), i);
    ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_value), i, v);
  }
 



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public DiscontinuousAnnotation_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_value = jcas.getRequiredFeatureDE(casType, "value", "uima.cas.FSArray", featOkTst);
    casFeatCode_value  = (null == casFeat_value) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_value).getCode();

  }
}



    