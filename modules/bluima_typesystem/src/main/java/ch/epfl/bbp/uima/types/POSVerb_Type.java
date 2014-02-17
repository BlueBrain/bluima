
/* First created by JCasGen Mon Feb 17 12:26:53 CET 2014 */
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
import de.julielab.jules.types.POSTag_Type;

/** 
 * Updated by JCasGen Mon Feb 17 22:12:56 CET 2014
 * @generated */
public class POSVerb_Type extends POSTag_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (POSVerb_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = POSVerb_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new POSVerb(addr, POSVerb_Type.this);
  			   POSVerb_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new POSVerb(addr, POSVerb_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = POSVerb.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("ch.epfl.bbp.uima.types.POSVerb");
 
  /** @generated */
  final Feature casFeat_biolexicon_id;
  /** @generated */
  final int     casFeatCode_biolexicon_id;
  /** @generated */ 
  public String getBiolexicon_id(int addr) {
        if (featOkTst && casFeat_biolexicon_id == null)
      jcas.throwFeatMissing("biolexicon_id", "ch.epfl.bbp.uima.types.POSVerb");
    return ll_cas.ll_getStringValue(addr, casFeatCode_biolexicon_id);
  }
  /** @generated */    
  public void setBiolexicon_id(int addr, String v) {
        if (featOkTst && casFeat_biolexicon_id == null)
      jcas.throwFeatMissing("biolexicon_id", "ch.epfl.bbp.uima.types.POSVerb");
    ll_cas.ll_setStringValue(addr, casFeatCode_biolexicon_id, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public POSVerb_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_biolexicon_id = jcas.getRequiredFeatureDE(casType, "biolexicon_id", "uima.cas.String", featOkTst);
    casFeatCode_biolexicon_id  = (null == casFeat_biolexicon_id) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_biolexicon_id).getCode();

  }
}



    