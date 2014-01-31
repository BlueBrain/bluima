
/* First created by JCasGen Wed Oct 19 19:10:41 CEST 2011 */
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

/** A chemical type
 * Updated by JCasGen Fri Dec 09 11:59:26 CET 2011
 * @generated */
public class Chemical_Type extends Annotation_Type {
  /** @generated */
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Chemical_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Chemical_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Chemical(addr, Chemical_Type.this);
  			   Chemical_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Chemical(addr, Chemical_Type.this);
  	  }
    };
  /** @generated */
  public final static int typeIndexID = Chemical.typeIndexID;
  /** @generated 
     @modifiable */
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.julielab.jules.types.Chemical");
 
  /** @generated */
  final Feature casFeat_registryNumber;
  /** @generated */
  final int     casFeatCode_registryNumber;
  /** @generated */ 
  public String getRegistryNumber(int addr) {
        if (featOkTst && casFeat_registryNumber == null)
      jcas.throwFeatMissing("registryNumber", "de.julielab.jules.types.Chemical");
    return ll_cas.ll_getStringValue(addr, casFeatCode_registryNumber);
  }
  /** @generated */    
  public void setRegistryNumber(int addr, String v) {
        if (featOkTst && casFeat_registryNumber == null)
      jcas.throwFeatMissing("registryNumber", "de.julielab.jules.types.Chemical");
    ll_cas.ll_setStringValue(addr, casFeatCode_registryNumber, v);}
    
  
 
  /** @generated */
  final Feature casFeat_nameOfSubstance;
  /** @generated */
  final int     casFeatCode_nameOfSubstance;
  /** @generated */ 
  public String getNameOfSubstance(int addr) {
        if (featOkTst && casFeat_nameOfSubstance == null)
      jcas.throwFeatMissing("nameOfSubstance", "de.julielab.jules.types.Chemical");
    return ll_cas.ll_getStringValue(addr, casFeatCode_nameOfSubstance);
  }
  /** @generated */    
  public void setNameOfSubstance(int addr, String v) {
        if (featOkTst && casFeat_nameOfSubstance == null)
      jcas.throwFeatMissing("nameOfSubstance", "de.julielab.jules.types.Chemical");
    ll_cas.ll_setStringValue(addr, casFeatCode_nameOfSubstance, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public Chemical_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_registryNumber = jcas.getRequiredFeatureDE(casType, "registryNumber", "uima.cas.String", featOkTst);
    casFeatCode_registryNumber  = (null == casFeat_registryNumber) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_registryNumber).getCode();

 
    casFeat_nameOfSubstance = jcas.getRequiredFeatureDE(casType, "nameOfSubstance", "uima.cas.String", featOkTst);
    casFeatCode_nameOfSubstance  = (null == casFeat_nameOfSubstance) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_nameOfSubstance).getCode();

  }
}



    