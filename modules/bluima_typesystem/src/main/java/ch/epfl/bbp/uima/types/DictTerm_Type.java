
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

/** 
 * Updated by JCasGen Mon Feb 17 22:12:56 CET 2014
 * @generated */
public class DictTerm_Type extends org.apache.uima.conceptMapper.DictTerm_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (DictTerm_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = DictTerm_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new DictTerm(addr, DictTerm_Type.this);
  			   DictTerm_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new DictTerm(addr, DictTerm_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = DictTerm.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("ch.epfl.bbp.uima.types.DictTerm");
 
  /** @generated */
  final Feature casFeat_entityId;
  /** @generated */
  final int     casFeatCode_entityId;
  /** @generated */ 
  public String getEntityId(int addr) {
        if (featOkTst && casFeat_entityId == null)
      jcas.throwFeatMissing("entityId", "ch.epfl.bbp.uima.types.DictTerm");
    return ll_cas.ll_getStringValue(addr, casFeatCode_entityId);
  }
  /** @generated */    
  public void setEntityId(int addr, String v) {
        if (featOkTst && casFeat_entityId == null)
      jcas.throwFeatMissing("entityId", "ch.epfl.bbp.uima.types.DictTerm");
    ll_cas.ll_setStringValue(addr, casFeatCode_entityId, v);}
    
  
 
  /** @generated */
  final Feature casFeat_annotType;
  /** @generated */
  final int     casFeatCode_annotType;
  /** @generated */ 
  public String getAnnotType(int addr) {
        if (featOkTst && casFeat_annotType == null)
      jcas.throwFeatMissing("annotType", "ch.epfl.bbp.uima.types.DictTerm");
    return ll_cas.ll_getStringValue(addr, casFeatCode_annotType);
  }
  /** @generated */    
  public void setAnnotType(int addr, String v) {
        if (featOkTst && casFeat_annotType == null)
      jcas.throwFeatMissing("annotType", "ch.epfl.bbp.uima.types.DictTerm");
    ll_cas.ll_setStringValue(addr, casFeatCode_annotType, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public DictTerm_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_entityId = jcas.getRequiredFeatureDE(casType, "entityId", "uima.cas.String", featOkTst);
    casFeatCode_entityId  = (null == casFeat_entityId) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_entityId).getCode();

 
    casFeat_annotType = jcas.getRequiredFeatureDE(casType, "annotType", "uima.cas.String", featOkTst);
    casFeatCode_annotType  = (null == casFeat_annotType) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_annotType).getCode();

  }
}



    