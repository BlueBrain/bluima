
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
import org.apache.uima.jcas.tcas.Annotation_Type;

/** The distr of topic from a LDA analysis on that document
 * Updated by JCasGen Wed Jun 04 18:01:56 CEST 2014
 * @generated */
public class TopicDistribution_Type extends Annotation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (TopicDistribution_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = TopicDistribution_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new TopicDistribution(addr, TopicDistribution_Type.this);
  			   TopicDistribution_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new TopicDistribution(addr, TopicDistribution_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = TopicDistribution.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("ch.epfl.bbp.uima.types.TopicDistribution");
 
  /** @generated */
  final Feature casFeat_probability;
  /** @generated */
  final int     casFeatCode_probability;
  /** @generated */ 
  public int getProbability(int addr) {
        if (featOkTst && casFeat_probability == null)
      jcas.throwFeatMissing("probability", "ch.epfl.bbp.uima.types.TopicDistribution");
    return ll_cas.ll_getRefValue(addr, casFeatCode_probability);
  }
  /** @generated */    
  public void setProbability(int addr, int v) {
        if (featOkTst && casFeat_probability == null)
      jcas.throwFeatMissing("probability", "ch.epfl.bbp.uima.types.TopicDistribution");
    ll_cas.ll_setRefValue(addr, casFeatCode_probability, v);}
    
   /** @generated */
  public double getProbability(int addr, int i) {
        if (featOkTst && casFeat_probability == null)
      jcas.throwFeatMissing("probability", "ch.epfl.bbp.uima.types.TopicDistribution");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getDoubleArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_probability), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_probability), i);
  return ll_cas.ll_getDoubleArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_probability), i);
  }
   
  /** @generated */ 
  public void setProbability(int addr, int i, double v) {
        if (featOkTst && casFeat_probability == null)
      jcas.throwFeatMissing("probability", "ch.epfl.bbp.uima.types.TopicDistribution");
    if (lowLevelTypeChecks)
      ll_cas.ll_setDoubleArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_probability), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_probability), i);
    ll_cas.ll_setDoubleArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_probability), i, v);
  }
 



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public TopicDistribution_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_probability = jcas.getRequiredFeatureDE(casType, "probability", "uima.cas.DoubleArray", featOkTst);
    casFeatCode_probability  = (null == casFeat_probability) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_probability).getCode();

  }
}



    