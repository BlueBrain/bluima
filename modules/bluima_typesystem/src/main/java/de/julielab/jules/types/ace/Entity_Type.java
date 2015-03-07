
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
public class Entity_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Entity_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Entity_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Entity(addr, Entity_Type.this);
  			   Entity_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Entity(addr, Entity_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Entity.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.julielab.jules.types.ace.Entity");
 
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
      jcas.throwFeatMissing("ace_type", "de.julielab.jules.types.ace.Entity");
    return ll_cas.ll_getStringValue(addr, casFeatCode_ace_type);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setAce_type(int addr, String v) {
        if (featOkTst && casFeat_ace_type == null)
      jcas.throwFeatMissing("ace_type", "de.julielab.jules.types.ace.Entity");
    ll_cas.ll_setStringValue(addr, casFeatCode_ace_type, v);}
    
  
 
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
      jcas.throwFeatMissing("ace_subtype", "de.julielab.jules.types.ace.Entity");
    return ll_cas.ll_getStringValue(addr, casFeatCode_ace_subtype);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setAce_subtype(int addr, String v) {
        if (featOkTst && casFeat_ace_subtype == null)
      jcas.throwFeatMissing("ace_subtype", "de.julielab.jules.types.ace.Entity");
    ll_cas.ll_setStringValue(addr, casFeatCode_ace_subtype, v);}
    
  
 
  /** @generated */
  final Feature casFeat_ace_class;
  /** @generated */
  final int     casFeatCode_ace_class;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getAce_class(int addr) {
        if (featOkTst && casFeat_ace_class == null)
      jcas.throwFeatMissing("ace_class", "de.julielab.jules.types.ace.Entity");
    return ll_cas.ll_getStringValue(addr, casFeatCode_ace_class);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setAce_class(int addr, String v) {
        if (featOkTst && casFeat_ace_class == null)
      jcas.throwFeatMissing("ace_class", "de.julielab.jules.types.ace.Entity");
    ll_cas.ll_setStringValue(addr, casFeatCode_ace_class, v);}
    
  
 
  /** @generated */
  final Feature casFeat_entity_mentions;
  /** @generated */
  final int     casFeatCode_entity_mentions;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getEntity_mentions(int addr) {
        if (featOkTst && casFeat_entity_mentions == null)
      jcas.throwFeatMissing("entity_mentions", "de.julielab.jules.types.ace.Entity");
    return ll_cas.ll_getRefValue(addr, casFeatCode_entity_mentions);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setEntity_mentions(int addr, int v) {
        if (featOkTst && casFeat_entity_mentions == null)
      jcas.throwFeatMissing("entity_mentions", "de.julielab.jules.types.ace.Entity");
    ll_cas.ll_setRefValue(addr, casFeatCode_entity_mentions, v);}
    
   /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @return value at index i in the array 
   */
  public int getEntity_mentions(int addr, int i) {
        if (featOkTst && casFeat_entity_mentions == null)
      jcas.throwFeatMissing("entity_mentions", "de.julielab.jules.types.ace.Entity");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_entity_mentions), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_entity_mentions), i);
	return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_entity_mentions), i);
  }
   
  /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @param v value to set
   */ 
  public void setEntity_mentions(int addr, int i, int v) {
        if (featOkTst && casFeat_entity_mentions == null)
      jcas.throwFeatMissing("entity_mentions", "de.julielab.jules.types.ace.Entity");
    if (lowLevelTypeChecks)
      ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_entity_mentions), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_entity_mentions), i);
    ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_entity_mentions), i, v);
  }
 
 
  /** @generated */
  final Feature casFeat_entity_attributes;
  /** @generated */
  final int     casFeatCode_entity_attributes;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getEntity_attributes(int addr) {
        if (featOkTst && casFeat_entity_attributes == null)
      jcas.throwFeatMissing("entity_attributes", "de.julielab.jules.types.ace.Entity");
    return ll_cas.ll_getRefValue(addr, casFeatCode_entity_attributes);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setEntity_attributes(int addr, int v) {
        if (featOkTst && casFeat_entity_attributes == null)
      jcas.throwFeatMissing("entity_attributes", "de.julielab.jules.types.ace.Entity");
    ll_cas.ll_setRefValue(addr, casFeatCode_entity_attributes, v);}
    
   /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @return value at index i in the array 
   */
  public int getEntity_attributes(int addr, int i) {
        if (featOkTst && casFeat_entity_attributes == null)
      jcas.throwFeatMissing("entity_attributes", "de.julielab.jules.types.ace.Entity");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_entity_attributes), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_entity_attributes), i);
	return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_entity_attributes), i);
  }
   
  /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @param v value to set
   */ 
  public void setEntity_attributes(int addr, int i, int v) {
        if (featOkTst && casFeat_entity_attributes == null)
      jcas.throwFeatMissing("entity_attributes", "de.julielab.jules.types.ace.Entity");
    if (lowLevelTypeChecks)
      ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_entity_attributes), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_entity_attributes), i);
    ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_entity_attributes), i, v);
  }
 



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public Entity_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_ace_type = jcas.getRequiredFeatureDE(casType, "ace_type", "uima.cas.String", featOkTst);
    casFeatCode_ace_type  = (null == casFeat_ace_type) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_ace_type).getCode();

 
    casFeat_ace_subtype = jcas.getRequiredFeatureDE(casType, "ace_subtype", "uima.cas.String", featOkTst);
    casFeatCode_ace_subtype  = (null == casFeat_ace_subtype) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_ace_subtype).getCode();

 
    casFeat_ace_class = jcas.getRequiredFeatureDE(casType, "ace_class", "uima.cas.String", featOkTst);
    casFeatCode_ace_class  = (null == casFeat_ace_class) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_ace_class).getCode();

 
    casFeat_entity_mentions = jcas.getRequiredFeatureDE(casType, "entity_mentions", "uima.cas.FSArray", featOkTst);
    casFeatCode_entity_mentions  = (null == casFeat_entity_mentions) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_entity_mentions).getCode();

 
    casFeat_entity_attributes = jcas.getRequiredFeatureDE(casType, "entity_attributes", "uima.cas.FSArray", featOkTst);
    casFeatCode_entity_attributes  = (null == casFeat_entity_attributes) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_entity_attributes).getCode();

  }
}



    