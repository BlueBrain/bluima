
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
public class Timex2_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Timex2_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Timex2_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Timex2(addr, Timex2_Type.this);
  			   Timex2_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Timex2(addr, Timex2_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Timex2.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.julielab.jules.types.ace.Timex2");
 
  /** @generated */
  final Feature casFeat_mod;
  /** @generated */
  final int     casFeatCode_mod;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getMod(int addr) {
        if (featOkTst && casFeat_mod == null)
      jcas.throwFeatMissing("mod", "de.julielab.jules.types.ace.Timex2");
    return ll_cas.ll_getStringValue(addr, casFeatCode_mod);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setMod(int addr, String v) {
        if (featOkTst && casFeat_mod == null)
      jcas.throwFeatMissing("mod", "de.julielab.jules.types.ace.Timex2");
    ll_cas.ll_setStringValue(addr, casFeatCode_mod, v);}
    
  
 
  /** @generated */
  final Feature casFeat_comment;
  /** @generated */
  final int     casFeatCode_comment;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getComment(int addr) {
        if (featOkTst && casFeat_comment == null)
      jcas.throwFeatMissing("comment", "de.julielab.jules.types.ace.Timex2");
    return ll_cas.ll_getStringValue(addr, casFeatCode_comment);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setComment(int addr, String v) {
        if (featOkTst && casFeat_comment == null)
      jcas.throwFeatMissing("comment", "de.julielab.jules.types.ace.Timex2");
    ll_cas.ll_setStringValue(addr, casFeatCode_comment, v);}
    
  
 
  /** @generated */
  final Feature casFeat_anchor_val;
  /** @generated */
  final int     casFeatCode_anchor_val;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getAnchor_val(int addr) {
        if (featOkTst && casFeat_anchor_val == null)
      jcas.throwFeatMissing("anchor_val", "de.julielab.jules.types.ace.Timex2");
    return ll_cas.ll_getStringValue(addr, casFeatCode_anchor_val);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setAnchor_val(int addr, String v) {
        if (featOkTst && casFeat_anchor_val == null)
      jcas.throwFeatMissing("anchor_val", "de.julielab.jules.types.ace.Timex2");
    ll_cas.ll_setStringValue(addr, casFeatCode_anchor_val, v);}
    
  
 
  /** @generated */
  final Feature casFeat_val;
  /** @generated */
  final int     casFeatCode_val;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getVal(int addr) {
        if (featOkTst && casFeat_val == null)
      jcas.throwFeatMissing("val", "de.julielab.jules.types.ace.Timex2");
    return ll_cas.ll_getStringValue(addr, casFeatCode_val);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setVal(int addr, String v) {
        if (featOkTst && casFeat_val == null)
      jcas.throwFeatMissing("val", "de.julielab.jules.types.ace.Timex2");
    ll_cas.ll_setStringValue(addr, casFeatCode_val, v);}
    
  
 
  /** @generated */
  final Feature casFeat_set;
  /** @generated */
  final int     casFeatCode_set;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getSet(int addr) {
        if (featOkTst && casFeat_set == null)
      jcas.throwFeatMissing("set", "de.julielab.jules.types.ace.Timex2");
    return ll_cas.ll_getStringValue(addr, casFeatCode_set);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setSet(int addr, String v) {
        if (featOkTst && casFeat_set == null)
      jcas.throwFeatMissing("set", "de.julielab.jules.types.ace.Timex2");
    ll_cas.ll_setStringValue(addr, casFeatCode_set, v);}
    
  
 
  /** @generated */
  final Feature casFeat_non_specific;
  /** @generated */
  final int     casFeatCode_non_specific;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getNon_specific(int addr) {
        if (featOkTst && casFeat_non_specific == null)
      jcas.throwFeatMissing("non_specific", "de.julielab.jules.types.ace.Timex2");
    return ll_cas.ll_getStringValue(addr, casFeatCode_non_specific);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setNon_specific(int addr, String v) {
        if (featOkTst && casFeat_non_specific == null)
      jcas.throwFeatMissing("non_specific", "de.julielab.jules.types.ace.Timex2");
    ll_cas.ll_setStringValue(addr, casFeatCode_non_specific, v);}
    
  
 
  /** @generated */
  final Feature casFeat_anchor_dir;
  /** @generated */
  final int     casFeatCode_anchor_dir;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getAnchor_dir(int addr) {
        if (featOkTst && casFeat_anchor_dir == null)
      jcas.throwFeatMissing("anchor_dir", "de.julielab.jules.types.ace.Timex2");
    return ll_cas.ll_getStringValue(addr, casFeatCode_anchor_dir);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setAnchor_dir(int addr, String v) {
        if (featOkTst && casFeat_anchor_dir == null)
      jcas.throwFeatMissing("anchor_dir", "de.julielab.jules.types.ace.Timex2");
    ll_cas.ll_setStringValue(addr, casFeatCode_anchor_dir, v);}
    
  
 
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
      jcas.throwFeatMissing("mentions", "de.julielab.jules.types.ace.Timex2");
    return ll_cas.ll_getRefValue(addr, casFeatCode_mentions);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setMentions(int addr, int v) {
        if (featOkTst && casFeat_mentions == null)
      jcas.throwFeatMissing("mentions", "de.julielab.jules.types.ace.Timex2");
    ll_cas.ll_setRefValue(addr, casFeatCode_mentions, v);}
    
   /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @return value at index i in the array 
   */
  public int getMentions(int addr, int i) {
        if (featOkTst && casFeat_mentions == null)
      jcas.throwFeatMissing("mentions", "de.julielab.jules.types.ace.Timex2");
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
      jcas.throwFeatMissing("mentions", "de.julielab.jules.types.ace.Timex2");
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
  public Timex2_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_mod = jcas.getRequiredFeatureDE(casType, "mod", "uima.cas.String", featOkTst);
    casFeatCode_mod  = (null == casFeat_mod) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_mod).getCode();

 
    casFeat_comment = jcas.getRequiredFeatureDE(casType, "comment", "uima.cas.String", featOkTst);
    casFeatCode_comment  = (null == casFeat_comment) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_comment).getCode();

 
    casFeat_anchor_val = jcas.getRequiredFeatureDE(casType, "anchor_val", "uima.cas.String", featOkTst);
    casFeatCode_anchor_val  = (null == casFeat_anchor_val) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_anchor_val).getCode();

 
    casFeat_val = jcas.getRequiredFeatureDE(casType, "val", "uima.cas.String", featOkTst);
    casFeatCode_val  = (null == casFeat_val) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_val).getCode();

 
    casFeat_set = jcas.getRequiredFeatureDE(casType, "set", "uima.cas.String", featOkTst);
    casFeatCode_set  = (null == casFeat_set) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_set).getCode();

 
    casFeat_non_specific = jcas.getRequiredFeatureDE(casType, "non_specific", "uima.cas.String", featOkTst);
    casFeatCode_non_specific  = (null == casFeat_non_specific) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_non_specific).getCode();

 
    casFeat_anchor_dir = jcas.getRequiredFeatureDE(casType, "anchor_dir", "uima.cas.String", featOkTst);
    casFeatCode_anchor_dir  = (null == casFeat_anchor_dir) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_anchor_dir).getCode();

 
    casFeat_mentions = jcas.getRequiredFeatureDE(casType, "mentions", "uima.cas.FSArray", featOkTst);
    casFeatCode_mentions  = (null == casFeat_mentions) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_mentions).getCode();

  }
}



    