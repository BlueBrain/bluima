
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
public class EventMention_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (EventMention_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = EventMention_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new EventMention(addr, EventMention_Type.this);
  			   EventMention_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new EventMention(addr, EventMention_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = EventMention.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.julielab.jules.types.ace.EventMention");
 
  /** @generated */
  final Feature casFeat_anchor;
  /** @generated */
  final int     casFeatCode_anchor;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getAnchor(int addr) {
        if (featOkTst && casFeat_anchor == null)
      jcas.throwFeatMissing("anchor", "de.julielab.jules.types.ace.EventMention");
    return ll_cas.ll_getRefValue(addr, casFeatCode_anchor);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setAnchor(int addr, int v) {
        if (featOkTst && casFeat_anchor == null)
      jcas.throwFeatMissing("anchor", "de.julielab.jules.types.ace.EventMention");
    ll_cas.ll_setRefValue(addr, casFeatCode_anchor, v);}
    
  
 
  /** @generated */
  final Feature casFeat_level;
  /** @generated */
  final int     casFeatCode_level;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getLevel(int addr) {
        if (featOkTst && casFeat_level == null)
      jcas.throwFeatMissing("level", "de.julielab.jules.types.ace.EventMention");
    return ll_cas.ll_getStringValue(addr, casFeatCode_level);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setLevel(int addr, String v) {
        if (featOkTst && casFeat_level == null)
      jcas.throwFeatMissing("level", "de.julielab.jules.types.ace.EventMention");
    ll_cas.ll_setStringValue(addr, casFeatCode_level, v);}
    
  
 
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
      jcas.throwFeatMissing("arguments", "de.julielab.jules.types.ace.EventMention");
    return ll_cas.ll_getRefValue(addr, casFeatCode_arguments);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setArguments(int addr, int v) {
        if (featOkTst && casFeat_arguments == null)
      jcas.throwFeatMissing("arguments", "de.julielab.jules.types.ace.EventMention");
    ll_cas.ll_setRefValue(addr, casFeatCode_arguments, v);}
    
   /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @return value at index i in the array 
   */
  public int getArguments(int addr, int i) {
        if (featOkTst && casFeat_arguments == null)
      jcas.throwFeatMissing("arguments", "de.julielab.jules.types.ace.EventMention");
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
      jcas.throwFeatMissing("arguments", "de.julielab.jules.types.ace.EventMention");
    if (lowLevelTypeChecks)
      ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_arguments), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_arguments), i);
    ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_arguments), i, v);
  }
 
 
  /** @generated */
  final Feature casFeat_ldc_scope;
  /** @generated */
  final int     casFeatCode_ldc_scope;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getLdc_scope(int addr) {
        if (featOkTst && casFeat_ldc_scope == null)
      jcas.throwFeatMissing("ldc_scope", "de.julielab.jules.types.ace.EventMention");
    return ll_cas.ll_getRefValue(addr, casFeatCode_ldc_scope);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setLdc_scope(int addr, int v) {
        if (featOkTst && casFeat_ldc_scope == null)
      jcas.throwFeatMissing("ldc_scope", "de.julielab.jules.types.ace.EventMention");
    ll_cas.ll_setRefValue(addr, casFeatCode_ldc_scope, v);}
    
  
 
  /** @generated */
  final Feature casFeat_event_ref;
  /** @generated */
  final int     casFeatCode_event_ref;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getEvent_ref(int addr) {
        if (featOkTst && casFeat_event_ref == null)
      jcas.throwFeatMissing("event_ref", "de.julielab.jules.types.ace.EventMention");
    return ll_cas.ll_getRefValue(addr, casFeatCode_event_ref);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setEvent_ref(int addr, int v) {
        if (featOkTst && casFeat_event_ref == null)
      jcas.throwFeatMissing("event_ref", "de.julielab.jules.types.ace.EventMention");
    ll_cas.ll_setRefValue(addr, casFeatCode_event_ref, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public EventMention_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_anchor = jcas.getRequiredFeatureDE(casType, "anchor", "de.julielab.jules.types.ace.Anchor", featOkTst);
    casFeatCode_anchor  = (null == casFeat_anchor) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_anchor).getCode();

 
    casFeat_level = jcas.getRequiredFeatureDE(casType, "level", "uima.cas.String", featOkTst);
    casFeatCode_level  = (null == casFeat_level) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_level).getCode();

 
    casFeat_arguments = jcas.getRequiredFeatureDE(casType, "arguments", "uima.cas.FSArray", featOkTst);
    casFeatCode_arguments  = (null == casFeat_arguments) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_arguments).getCode();

 
    casFeat_ldc_scope = jcas.getRequiredFeatureDE(casType, "ldc_scope", "de.julielab.jules.types.ace.LDC_Scope", featOkTst);
    casFeatCode_ldc_scope  = (null == casFeat_ldc_scope) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_ldc_scope).getCode();

 
    casFeat_event_ref = jcas.getRequiredFeatureDE(casType, "event_ref", "de.julielab.jules.types.ace.Event", featOkTst);
    casFeatCode_event_ref  = (null == casFeat_event_ref) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_event_ref).getCode();

  }
}



    