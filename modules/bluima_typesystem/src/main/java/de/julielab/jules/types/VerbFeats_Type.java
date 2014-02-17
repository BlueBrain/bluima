
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

/** Describes a word structure, default grammatical features of a verb
 * Updated by JCasGen Mon Feb 17 22:12:57 CET 2014
 * @generated */
public class VerbFeats_Type extends GrammaticalFeats_Type {
  /** @generated */
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (VerbFeats_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = VerbFeats_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new VerbFeats(addr, VerbFeats_Type.this);
  			   VerbFeats_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new VerbFeats(addr, VerbFeats_Type.this);
  	  }
    };
  /** @generated */
  public final static int typeIndexID = VerbFeats.typeIndexID;
  /** @generated 
     @modifiable */
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.julielab.jules.types.VerbFeats");
 
  /** @generated */
  final Feature casFeat_tense;
  /** @generated */
  final int     casFeatCode_tense;
  /** @generated */ 
  public String getTense(int addr) {
        if (featOkTst && casFeat_tense == null)
      jcas.throwFeatMissing("tense", "de.julielab.jules.types.VerbFeats");
    return ll_cas.ll_getStringValue(addr, casFeatCode_tense);
  }
  /** @generated */    
  public void setTense(int addr, String v) {
        if (featOkTst && casFeat_tense == null)
      jcas.throwFeatMissing("tense", "de.julielab.jules.types.VerbFeats");
    ll_cas.ll_setStringValue(addr, casFeatCode_tense, v);}
    
  
 
  /** @generated */
  final Feature casFeat_person;
  /** @generated */
  final int     casFeatCode_person;
  /** @generated */ 
  public String getPerson(int addr) {
        if (featOkTst && casFeat_person == null)
      jcas.throwFeatMissing("person", "de.julielab.jules.types.VerbFeats");
    return ll_cas.ll_getStringValue(addr, casFeatCode_person);
  }
  /** @generated */    
  public void setPerson(int addr, String v) {
        if (featOkTst && casFeat_person == null)
      jcas.throwFeatMissing("person", "de.julielab.jules.types.VerbFeats");
    ll_cas.ll_setStringValue(addr, casFeatCode_person, v);}
    
  
 
  /** @generated */
  final Feature casFeat_number;
  /** @generated */
  final int     casFeatCode_number;
  /** @generated */ 
  public String getNumber(int addr) {
        if (featOkTst && casFeat_number == null)
      jcas.throwFeatMissing("number", "de.julielab.jules.types.VerbFeats");
    return ll_cas.ll_getStringValue(addr, casFeatCode_number);
  }
  /** @generated */    
  public void setNumber(int addr, String v) {
        if (featOkTst && casFeat_number == null)
      jcas.throwFeatMissing("number", "de.julielab.jules.types.VerbFeats");
    ll_cas.ll_setStringValue(addr, casFeatCode_number, v);}
    
  
 
  /** @generated */
  final Feature casFeat_voice;
  /** @generated */
  final int     casFeatCode_voice;
  /** @generated */ 
  public String getVoice(int addr) {
        if (featOkTst && casFeat_voice == null)
      jcas.throwFeatMissing("voice", "de.julielab.jules.types.VerbFeats");
    return ll_cas.ll_getStringValue(addr, casFeatCode_voice);
  }
  /** @generated */    
  public void setVoice(int addr, String v) {
        if (featOkTst && casFeat_voice == null)
      jcas.throwFeatMissing("voice", "de.julielab.jules.types.VerbFeats");
    ll_cas.ll_setStringValue(addr, casFeatCode_voice, v);}
    
  
 
  /** @generated */
  final Feature casFeat_aspect;
  /** @generated */
  final int     casFeatCode_aspect;
  /** @generated */ 
  public String getAspect(int addr) {
        if (featOkTst && casFeat_aspect == null)
      jcas.throwFeatMissing("aspect", "de.julielab.jules.types.VerbFeats");
    return ll_cas.ll_getStringValue(addr, casFeatCode_aspect);
  }
  /** @generated */    
  public void setAspect(int addr, String v) {
        if (featOkTst && casFeat_aspect == null)
      jcas.throwFeatMissing("aspect", "de.julielab.jules.types.VerbFeats");
    ll_cas.ll_setStringValue(addr, casFeatCode_aspect, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public VerbFeats_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_tense = jcas.getRequiredFeatureDE(casType, "tense", "de.julielab.jules.types.Tense", featOkTst);
    casFeatCode_tense  = (null == casFeat_tense) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_tense).getCode();

 
    casFeat_person = jcas.getRequiredFeatureDE(casType, "person", "de.julielab.jules.types.Person", featOkTst);
    casFeatCode_person  = (null == casFeat_person) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_person).getCode();

 
    casFeat_number = jcas.getRequiredFeatureDE(casType, "number", "de.julielab.jules.types.Number", featOkTst);
    casFeatCode_number  = (null == casFeat_number) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_number).getCode();

 
    casFeat_voice = jcas.getRequiredFeatureDE(casType, "voice", "de.julielab.jules.types.Voice", featOkTst);
    casFeatCode_voice  = (null == casFeat_voice) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_voice).getCode();

 
    casFeat_aspect = jcas.getRequiredFeatureDE(casType, "aspect", "de.julielab.jules.types.Aspect", featOkTst);
    casFeatCode_aspect  = (null == casFeat_aspect) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_aspect).getCode();

  }
}



    