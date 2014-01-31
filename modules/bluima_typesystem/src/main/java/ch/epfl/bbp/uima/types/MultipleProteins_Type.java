
/* First created by JCasGen Wed Oct 24 10:17:31 CEST 2012 */
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
 * Updated by JCasGen Mon Oct 21 13:03:29 CEST 2013
 * @generated */
public class MultipleProteins_Type extends Annotation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (MultipleProteins_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = MultipleProteins_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new MultipleProteins(addr, MultipleProteins_Type.this);
  			   MultipleProteins_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new MultipleProteins(addr, MultipleProteins_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = MultipleProteins.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("ch.epfl.bbp.uima.types.MultipleProteins");
 
  /** @generated */
  final Feature casFeat_present;
  /** @generated */
  final int     casFeatCode_present;
  /** @generated */ 
  public int getPresent(int addr) {
        if (featOkTst && casFeat_present == null)
      jcas.throwFeatMissing("present", "ch.epfl.bbp.uima.types.MultipleProteins");
    return ll_cas.ll_getIntValue(addr, casFeatCode_present);
  }
  /** @generated */    
  public void setPresent(int addr, int v) {
        if (featOkTst && casFeat_present == null)
      jcas.throwFeatMissing("present", "ch.epfl.bbp.uima.types.MultipleProteins");
    ll_cas.ll_setIntValue(addr, casFeatCode_present, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public MultipleProteins_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_present = jcas.getRequiredFeatureDE(casType, "present", "uima.cas.Integer", featOkTst);
    casFeatCode_present  = (null == casFeat_present) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_present).getCode();

  }
}



    