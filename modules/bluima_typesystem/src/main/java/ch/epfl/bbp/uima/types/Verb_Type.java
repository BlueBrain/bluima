
/* First created by JCasGen Wed Oct 31 21:02:40 CET 2012 */
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
import de.julielab.jules.types.Annotation_Type;

/** A general verb, that was not found in the biolexicon/Mancu
 * Updated by JCasGen Fri Sep 20 01:02:04 CEST 2013
 * @generated */
public class Verb_Type extends Annotation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Verb_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Verb_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Verb(addr, Verb_Type.this);
  			   Verb_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Verb(addr, Verb_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Verb.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("ch.epfl.bbp.uima.types.Verb");



  /** @generated */
  final Feature casFeat_isModal;
  /** @generated */
  final int     casFeatCode_isModal;
  /** @generated */ 
  public boolean getIsModal(int addr) {
        if (featOkTst && casFeat_isModal == null)
      jcas.throwFeatMissing("isModal", "ch.epfl.bbp.uima.types.Verb");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_isModal);
  }
  /** @generated */    
  public void setIsModal(int addr, boolean v) {
        if (featOkTst && casFeat_isModal == null)
      jcas.throwFeatMissing("isModal", "ch.epfl.bbp.uima.types.Verb");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_isModal, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public Verb_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_isModal = jcas.getRequiredFeatureDE(casType, "isModal", "uima.cas.Boolean", featOkTst);
    casFeatCode_isModal  = (null == casFeat_isModal) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_isModal).getCode();

  }
}



    