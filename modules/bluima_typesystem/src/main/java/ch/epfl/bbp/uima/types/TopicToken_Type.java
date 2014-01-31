
/* First created by JCasGen Tue Jun 11 11:40:34 CEST 2013 */
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
public class TopicToken_Type extends Annotation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (TopicToken_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = TopicToken_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new TopicToken(addr, TopicToken_Type.this);
  			   TopicToken_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new TopicToken(addr, TopicToken_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = TopicToken.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("ch.epfl.bbp.uima.types.TopicToken");
 
  /** @generated */
  final Feature casFeat_enclosingAnnot;
  /** @generated */
  final int     casFeatCode_enclosingAnnot;
  /** @generated */ 
  public int getEnclosingAnnot(int addr) {
        if (featOkTst && casFeat_enclosingAnnot == null)
      jcas.throwFeatMissing("enclosingAnnot", "ch.epfl.bbp.uima.types.TopicToken");
    return ll_cas.ll_getRefValue(addr, casFeatCode_enclosingAnnot);
  }
  /** @generated */    
  public void setEnclosingAnnot(int addr, int v) {
        if (featOkTst && casFeat_enclosingAnnot == null)
      jcas.throwFeatMissing("enclosingAnnot", "ch.epfl.bbp.uima.types.TopicToken");
    ll_cas.ll_setRefValue(addr, casFeatCode_enclosingAnnot, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public TopicToken_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_enclosingAnnot = jcas.getRequiredFeatureDE(casType, "enclosingAnnot", "uima.tcas.Annotation", featOkTst);
    casFeatCode_enclosingAnnot  = (null == casFeat_enclosingAnnot) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_enclosingAnnot).getCode();

  }
}



    