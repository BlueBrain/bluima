
/* First created by JCasGen Sat Mar 07 22:05:57 CET 2015 */
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

/** Describes a word structure, default grammatical features of a adjective
 * Updated by JCasGen Sat Mar 07 22:05:57 CET 2015
 * @generated */
public class AdjectiveFeats_Type extends GrammaticalFeats_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (AdjectiveFeats_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = AdjectiveFeats_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new AdjectiveFeats(addr, AdjectiveFeats_Type.this);
  			   AdjectiveFeats_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new AdjectiveFeats(addr, AdjectiveFeats_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = AdjectiveFeats.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.julielab.jules.types.AdjectiveFeats");
 
  /** @generated */
  final Feature casFeat_degree;
  /** @generated */
  final int     casFeatCode_degree;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getDegree(int addr) {
        if (featOkTst && casFeat_degree == null)
      jcas.throwFeatMissing("degree", "de.julielab.jules.types.AdjectiveFeats");
    return ll_cas.ll_getStringValue(addr, casFeatCode_degree);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setDegree(int addr, String v) {
        if (featOkTst && casFeat_degree == null)
      jcas.throwFeatMissing("degree", "de.julielab.jules.types.AdjectiveFeats");
    ll_cas.ll_setStringValue(addr, casFeatCode_degree, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public AdjectiveFeats_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_degree = jcas.getRequiredFeatureDE(casType, "degree", "de.julielab.jules.types.Degree", featOkTst);
    casFeatCode_degree  = (null == casFeat_degree) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_degree).getCode();

  }
}



    