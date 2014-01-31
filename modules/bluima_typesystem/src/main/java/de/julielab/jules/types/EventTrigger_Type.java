
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

/** 
 * Updated by JCasGen Mon Oct 21 13:03:29 CEST 2013
 * @generated */
public class EventTrigger_Type extends ConceptMention_Type {
  /** @generated */
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (EventTrigger_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = EventTrigger_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new EventTrigger(addr, EventTrigger_Type.this);
  			   EventTrigger_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new EventTrigger(addr, EventTrigger_Type.this);
  	  }
    };
  /** @generated */
  public final static int typeIndexID = EventTrigger.typeIndexID;
  /** @generated 
     @modifiable */
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.julielab.jules.types.EventTrigger");
 
  /** @generated */
  final Feature casFeat_probability;
  /** @generated */
  final int     casFeatCode_probability;
  /** @generated */ 
  public String getProbability(int addr) {
        if (featOkTst && casFeat_probability == null)
      jcas.throwFeatMissing("probability", "de.julielab.jules.types.EventTrigger");
    return ll_cas.ll_getStringValue(addr, casFeatCode_probability);
  }
  /** @generated */    
  public void setProbability(int addr, String v) {
        if (featOkTst && casFeat_probability == null)
      jcas.throwFeatMissing("probability", "de.julielab.jules.types.EventTrigger");
    ll_cas.ll_setStringValue(addr, casFeatCode_probability, v);}
    
  
 
  /** @generated */
  final Feature casFeat_specifity;
  /** @generated */
  final int     casFeatCode_specifity;
  /** @generated */ 
  public String getSpecifity(int addr) {
        if (featOkTst && casFeat_specifity == null)
      jcas.throwFeatMissing("specifity", "de.julielab.jules.types.EventTrigger");
    return ll_cas.ll_getStringValue(addr, casFeatCode_specifity);
  }
  /** @generated */    
  public void setSpecifity(int addr, String v) {
        if (featOkTst && casFeat_specifity == null)
      jcas.throwFeatMissing("specifity", "de.julielab.jules.types.EventTrigger");
    ll_cas.ll_setStringValue(addr, casFeatCode_specifity, v);}
    
  
 
  /** @generated */
  final Feature casFeat_importance;
  /** @generated */
  final int     casFeatCode_importance;
  /** @generated */ 
  public String getImportance(int addr) {
        if (featOkTst && casFeat_importance == null)
      jcas.throwFeatMissing("importance", "de.julielab.jules.types.EventTrigger");
    return ll_cas.ll_getStringValue(addr, casFeatCode_importance);
  }
  /** @generated */    
  public void setImportance(int addr, String v) {
        if (featOkTst && casFeat_importance == null)
      jcas.throwFeatMissing("importance", "de.julielab.jules.types.EventTrigger");
    ll_cas.ll_setStringValue(addr, casFeatCode_importance, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public EventTrigger_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_probability = jcas.getRequiredFeatureDE(casType, "probability", "uima.cas.String", featOkTst);
    casFeatCode_probability  = (null == casFeat_probability) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_probability).getCode();

 
    casFeat_specifity = jcas.getRequiredFeatureDE(casType, "specifity", "uima.cas.String", featOkTst);
    casFeatCode_specifity  = (null == casFeat_specifity) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_specifity).getCode();

 
    casFeat_importance = jcas.getRequiredFeatureDE(casType, "importance", "uima.cas.String", featOkTst);
    casFeatCode_importance  = (null == casFeat_importance) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_importance).getCode();

  }
}



    