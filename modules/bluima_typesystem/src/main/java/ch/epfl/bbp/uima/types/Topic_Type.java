
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
import de.julielab.jules.types.Annotation_Type;

/** The scores of topics from a LDA analysis on a token
 * Updated by JCasGen Mon Feb 17 22:12:56 CET 2014
 * @generated */
public class Topic_Type extends Annotation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Topic_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Topic_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Topic(addr, Topic_Type.this);
  			   Topic_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Topic(addr, Topic_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Topic.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("ch.epfl.bbp.uima.types.Topic");
 
  /** @generated */
  final Feature casFeat_scores;
  /** @generated */
  final int     casFeatCode_scores;
  /** @generated */ 
  public int getScores(int addr) {
        if (featOkTst && casFeat_scores == null)
      jcas.throwFeatMissing("scores", "ch.epfl.bbp.uima.types.Topic");
    return ll_cas.ll_getRefValue(addr, casFeatCode_scores);
  }
  /** @generated */    
  public void setScores(int addr, int v) {
        if (featOkTst && casFeat_scores == null)
      jcas.throwFeatMissing("scores", "ch.epfl.bbp.uima.types.Topic");
    ll_cas.ll_setRefValue(addr, casFeatCode_scores, v);}
    
   /** @generated */
  public double getScores(int addr, int i) {
        if (featOkTst && casFeat_scores == null)
      jcas.throwFeatMissing("scores", "ch.epfl.bbp.uima.types.Topic");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getDoubleArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_scores), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_scores), i);
  return ll_cas.ll_getDoubleArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_scores), i);
  }
   
  /** @generated */ 
  public void setScores(int addr, int i, double v) {
        if (featOkTst && casFeat_scores == null)
      jcas.throwFeatMissing("scores", "ch.epfl.bbp.uima.types.Topic");
    if (lowLevelTypeChecks)
      ll_cas.ll_setDoubleArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_scores), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_scores), i);
    ll_cas.ll_setDoubleArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_scores), i, v);
  }
 
 
  /** @generated */
  final Feature casFeat_mostLikelyTopic;
  /** @generated */
  final int     casFeatCode_mostLikelyTopic;
  /** @generated */ 
  public int getMostLikelyTopic(int addr) {
        if (featOkTst && casFeat_mostLikelyTopic == null)
      jcas.throwFeatMissing("mostLikelyTopic", "ch.epfl.bbp.uima.types.Topic");
    return ll_cas.ll_getIntValue(addr, casFeatCode_mostLikelyTopic);
  }
  /** @generated */    
  public void setMostLikelyTopic(int addr, int v) {
        if (featOkTst && casFeat_mostLikelyTopic == null)
      jcas.throwFeatMissing("mostLikelyTopic", "ch.epfl.bbp.uima.types.Topic");
    ll_cas.ll_setIntValue(addr, casFeatCode_mostLikelyTopic, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public Topic_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_scores = jcas.getRequiredFeatureDE(casType, "scores", "uima.cas.DoubleArray", featOkTst);
    casFeatCode_scores  = (null == casFeat_scores) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_scores).getCode();

 
    casFeat_mostLikelyTopic = jcas.getRequiredFeatureDE(casType, "mostLikelyTopic", "uima.cas.Integer", featOkTst);
    casFeatCode_mostLikelyTopic  = (null == casFeat_mostLikelyTopic) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_mostLikelyTopic).getCode();

  }
}



    