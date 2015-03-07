
/* First created by JCasGen Sat Mar 07 22:05:57 CET 2015 */
package de.julielab.jules.types.ace;

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
 * Updated by JCasGen Sat Mar 07 22:05:57 CET 2015
 * @generated */
public class Event_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Event_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Event_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Event(addr, Event_Type.this);
  			   Event_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Event(addr, Event_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Event.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.julielab.jules.types.ace.Event");
 
  /** @generated */
  final Feature casFeat_tense;
  /** @generated */
  final int     casFeatCode_tense;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getTense(int addr) {
        if (featOkTst && casFeat_tense == null)
      jcas.throwFeatMissing("tense", "de.julielab.jules.types.ace.Event");
    return ll_cas.ll_getStringValue(addr, casFeatCode_tense);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setTense(int addr, String v) {
        if (featOkTst && casFeat_tense == null)
      jcas.throwFeatMissing("tense", "de.julielab.jules.types.ace.Event");
    ll_cas.ll_setStringValue(addr, casFeatCode_tense, v);}
    
  
 
  /** @generated */
  final Feature casFeat_polarity;
  /** @generated */
  final int     casFeatCode_polarity;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getPolarity(int addr) {
        if (featOkTst && casFeat_polarity == null)
      jcas.throwFeatMissing("polarity", "de.julielab.jules.types.ace.Event");
    return ll_cas.ll_getStringValue(addr, casFeatCode_polarity);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setPolarity(int addr, String v) {
        if (featOkTst && casFeat_polarity == null)
      jcas.throwFeatMissing("polarity", "de.julielab.jules.types.ace.Event");
    ll_cas.ll_setStringValue(addr, casFeatCode_polarity, v);}
    
  
 
  /** @generated */
  final Feature casFeat_ace_subtype;
  /** @generated */
  final int     casFeatCode_ace_subtype;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getAce_subtype(int addr) {
        if (featOkTst && casFeat_ace_subtype == null)
      jcas.throwFeatMissing("ace_subtype", "de.julielab.jules.types.ace.Event");
    return ll_cas.ll_getStringValue(addr, casFeatCode_ace_subtype);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setAce_subtype(int addr, String v) {
        if (featOkTst && casFeat_ace_subtype == null)
      jcas.throwFeatMissing("ace_subtype", "de.julielab.jules.types.ace.Event");
    ll_cas.ll_setStringValue(addr, casFeatCode_ace_subtype, v);}
    
  
 
  /** @generated */
  final Feature casFeat_genericity;
  /** @generated */
  final int     casFeatCode_genericity;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getGenericity(int addr) {
        if (featOkTst && casFeat_genericity == null)
      jcas.throwFeatMissing("genericity", "de.julielab.jules.types.ace.Event");
    return ll_cas.ll_getStringValue(addr, casFeatCode_genericity);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setGenericity(int addr, String v) {
        if (featOkTst && casFeat_genericity == null)
      jcas.throwFeatMissing("genericity", "de.julielab.jules.types.ace.Event");
    ll_cas.ll_setStringValue(addr, casFeatCode_genericity, v);}
    
  
 
  /** @generated */
  final Feature casFeat_modality;
  /** @generated */
  final int     casFeatCode_modality;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getModality(int addr) {
        if (featOkTst && casFeat_modality == null)
      jcas.throwFeatMissing("modality", "de.julielab.jules.types.ace.Event");
    return ll_cas.ll_getStringValue(addr, casFeatCode_modality);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setModality(int addr, String v) {
        if (featOkTst && casFeat_modality == null)
      jcas.throwFeatMissing("modality", "de.julielab.jules.types.ace.Event");
    ll_cas.ll_setStringValue(addr, casFeatCode_modality, v);}
    
  
 
  /** @generated */
  final Feature casFeat_ace_type;
  /** @generated */
  final int     casFeatCode_ace_type;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getAce_type(int addr) {
        if (featOkTst && casFeat_ace_type == null)
      jcas.throwFeatMissing("ace_type", "de.julielab.jules.types.ace.Event");
    return ll_cas.ll_getStringValue(addr, casFeatCode_ace_type);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setAce_type(int addr, String v) {
        if (featOkTst && casFeat_ace_type == null)
      jcas.throwFeatMissing("ace_type", "de.julielab.jules.types.ace.Event");
    ll_cas.ll_setStringValue(addr, casFeatCode_ace_type, v);}
    
  
 
  /** @generated */
  final Feature casFeat_arguments;
  /** @generated */
  final int     casFeatCode_arguments;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getArguments(int addr) {
        if (featOkTst && casFeat_arguments == null)
      jcas.throwFeatMissing("arguments", "de.julielab.jules.types.ace.Event");
    return ll_cas.ll_getRefValue(addr, casFeatCode_arguments);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setArguments(int addr, int v) {
        if (featOkTst && casFeat_arguments == null)
      jcas.throwFeatMissing("arguments", "de.julielab.jules.types.ace.Event");
    ll_cas.ll_setRefValue(addr, casFeatCode_arguments, v);}
    
   /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @return value at index i in the array 
   */
  public int getArguments(int addr, int i) {
        if (featOkTst && casFeat_arguments == null)
      jcas.throwFeatMissing("arguments", "de.julielab.jules.types.ace.Event");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_arguments), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_arguments), i);
	return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_arguments), i);
  }
   
  /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @param v value to set
   */ 
  public void setArguments(int addr, int i, int v) {
        if (featOkTst && casFeat_arguments == null)
      jcas.throwFeatMissing("arguments", "de.julielab.jules.types.ace.Event");
    if (lowLevelTypeChecks)
      ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_arguments), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_arguments), i);
    ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_arguments), i, v);
  }
 
 
  /** @generated */
  final Feature casFeat_mentions;
  /** @generated */
  final int     casFeatCode_mentions;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getMentions(int addr) {
        if (featOkTst && casFeat_mentions == null)
      jcas.throwFeatMissing("mentions", "de.julielab.jules.types.ace.Event");
    return ll_cas.ll_getRefValue(addr, casFeatCode_mentions);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setMentions(int addr, int v) {
        if (featOkTst && casFeat_mentions == null)
      jcas.throwFeatMissing("mentions", "de.julielab.jules.types.ace.Event");
    ll_cas.ll_setRefValue(addr, casFeatCode_mentions, v);}
    
   /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @return value at index i in the array 
   */
  public int getMentions(int addr, int i) {
        if (featOkTst && casFeat_mentions == null)
      jcas.throwFeatMissing("mentions", "de.julielab.jules.types.ace.Event");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_mentions), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_mentions), i);
	return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_mentions), i);
  }
   
  /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @param v value to set
   */ 
  public void setMentions(int addr, int i, int v) {
        if (featOkTst && casFeat_mentions == null)
      jcas.throwFeatMissing("mentions", "de.julielab.jules.types.ace.Event");
    if (lowLevelTypeChecks)
      ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_mentions), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_mentions), i);
    ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_mentions), i, v);
  }
 



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public Event_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_tense = jcas.getRequiredFeatureDE(casType, "tense", "uima.cas.String", featOkTst);
    casFeatCode_tense  = (null == casFeat_tense) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_tense).getCode();

 
    casFeat_polarity = jcas.getRequiredFeatureDE(casType, "polarity", "uima.cas.String", featOkTst);
    casFeatCode_polarity  = (null == casFeat_polarity) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_polarity).getCode();

 
    casFeat_ace_subtype = jcas.getRequiredFeatureDE(casType, "ace_subtype", "uima.cas.String", featOkTst);
    casFeatCode_ace_subtype  = (null == casFeat_ace_subtype) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_ace_subtype).getCode();

 
    casFeat_genericity = jcas.getRequiredFeatureDE(casType, "genericity", "uima.cas.String", featOkTst);
    casFeatCode_genericity  = (null == casFeat_genericity) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_genericity).getCode();

 
    casFeat_modality = jcas.getRequiredFeatureDE(casType, "modality", "uima.cas.String", featOkTst);
    casFeatCode_modality  = (null == casFeat_modality) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_modality).getCode();

 
    casFeat_ace_type = jcas.getRequiredFeatureDE(casType, "ace_type", "uima.cas.String", featOkTst);
    casFeatCode_ace_type  = (null == casFeat_ace_type) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_ace_type).getCode();

 
    casFeat_arguments = jcas.getRequiredFeatureDE(casType, "arguments", "uima.cas.FSArray", featOkTst);
    casFeatCode_arguments  = (null == casFeat_arguments) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_arguments).getCode();

 
    casFeat_mentions = jcas.getRequiredFeatureDE(casType, "mentions", "uima.cas.FSArray", featOkTst);
    casFeatCode_mentions  = (null == casFeat_mentions) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_mentions).getCode();

  }
}



    