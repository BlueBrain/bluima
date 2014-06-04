
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

/** Describes a word structure, default grammatical features of a noun
 * Updated by JCasGen Wed Jun 04 18:01:58 CEST 2014
 * @generated */
public class NounFeats_Type extends GrammaticalFeats_Type {
  /** @generated */
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (NounFeats_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = NounFeats_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new NounFeats(addr, NounFeats_Type.this);
  			   NounFeats_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new NounFeats(addr, NounFeats_Type.this);
  	  }
    };
  /** @generated */
  public final static int typeIndexID = NounFeats.typeIndexID;
  /** @generated 
     @modifiable */
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.julielab.jules.types.NounFeats");
 
  /** @generated */
  final Feature casFeat_gender;
  /** @generated */
  final int     casFeatCode_gender;
  /** @generated */ 
  public String getGender(int addr) {
        if (featOkTst && casFeat_gender == null)
      jcas.throwFeatMissing("gender", "de.julielab.jules.types.NounFeats");
    return ll_cas.ll_getStringValue(addr, casFeatCode_gender);
  }
  /** @generated */    
  public void setGender(int addr, String v) {
        if (featOkTst && casFeat_gender == null)
      jcas.throwFeatMissing("gender", "de.julielab.jules.types.NounFeats");
    ll_cas.ll_setStringValue(addr, casFeatCode_gender, v);}
    
  
 
  /** @generated */
  final Feature casFeat_case;
  /** @generated */
  final int     casFeatCode_case;
  /** @generated */ 
  public String getCase(int addr) {
        if (featOkTst && casFeat_case == null)
      jcas.throwFeatMissing("case", "de.julielab.jules.types.NounFeats");
    return ll_cas.ll_getStringValue(addr, casFeatCode_case);
  }
  /** @generated */    
  public void setCase(int addr, String v) {
        if (featOkTst && casFeat_case == null)
      jcas.throwFeatMissing("case", "de.julielab.jules.types.NounFeats");
    ll_cas.ll_setStringValue(addr, casFeatCode_case, v);}
    
  
 
  /** @generated */
  final Feature casFeat_number;
  /** @generated */
  final int     casFeatCode_number;
  /** @generated */ 
  public String getNumber(int addr) {
        if (featOkTst && casFeat_number == null)
      jcas.throwFeatMissing("number", "de.julielab.jules.types.NounFeats");
    return ll_cas.ll_getStringValue(addr, casFeatCode_number);
  }
  /** @generated */    
  public void setNumber(int addr, String v) {
        if (featOkTst && casFeat_number == null)
      jcas.throwFeatMissing("number", "de.julielab.jules.types.NounFeats");
    ll_cas.ll_setStringValue(addr, casFeatCode_number, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public NounFeats_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_gender = jcas.getRequiredFeatureDE(casType, "gender", "de.julielab.jules.types.Gender", featOkTst);
    casFeatCode_gender  = (null == casFeat_gender) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_gender).getCode();

 
    casFeat_case = jcas.getRequiredFeatureDE(casType, "case", "de.julielab.jules.types.Case", featOkTst);
    casFeatCode_case  = (null == casFeat_case) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_case).getCode();

 
    casFeat_number = jcas.getRequiredFeatureDE(casType, "number", "de.julielab.jules.types.Number", featOkTst);
    casFeatCode_number  = (null == casFeat_number) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_number).getCode();

  }
}



    