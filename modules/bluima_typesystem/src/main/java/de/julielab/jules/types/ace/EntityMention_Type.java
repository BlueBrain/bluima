
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
public class EntityMention_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (EntityMention_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = EntityMention_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new EntityMention(addr, EntityMention_Type.this);
  			   EntityMention_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new EntityMention(addr, EntityMention_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = EntityMention.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.julielab.jules.types.ace.EntityMention");
 
  /** @generated */
  final Feature casFeat_head;
  /** @generated */
  final int     casFeatCode_head;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getHead(int addr) {
        if (featOkTst && casFeat_head == null)
      jcas.throwFeatMissing("head", "de.julielab.jules.types.ace.EntityMention");
    return ll_cas.ll_getRefValue(addr, casFeatCode_head);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setHead(int addr, int v) {
        if (featOkTst && casFeat_head == null)
      jcas.throwFeatMissing("head", "de.julielab.jules.types.ace.EntityMention");
    ll_cas.ll_setRefValue(addr, casFeatCode_head, v);}
    
  
 
  /** @generated */
  final Feature casFeat_ldcatr;
  /** @generated */
  final int     casFeatCode_ldcatr;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getLdcatr(int addr) {
        if (featOkTst && casFeat_ldcatr == null)
      jcas.throwFeatMissing("ldcatr", "de.julielab.jules.types.ace.EntityMention");
    return ll_cas.ll_getStringValue(addr, casFeatCode_ldcatr);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setLdcatr(int addr, String v) {
        if (featOkTst && casFeat_ldcatr == null)
      jcas.throwFeatMissing("ldcatr", "de.julielab.jules.types.ace.EntityMention");
    ll_cas.ll_setStringValue(addr, casFeatCode_ldcatr, v);}
    
  
 
  /** @generated */
  final Feature casFeat_role;
  /** @generated */
  final int     casFeatCode_role;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getRole(int addr) {
        if (featOkTst && casFeat_role == null)
      jcas.throwFeatMissing("role", "de.julielab.jules.types.ace.EntityMention");
    return ll_cas.ll_getStringValue(addr, casFeatCode_role);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setRole(int addr, String v) {
        if (featOkTst && casFeat_role == null)
      jcas.throwFeatMissing("role", "de.julielab.jules.types.ace.EntityMention");
    ll_cas.ll_setStringValue(addr, casFeatCode_role, v);}
    
  
 
  /** @generated */
  final Feature casFeat_metonymy_mention;
  /** @generated */
  final int     casFeatCode_metonymy_mention;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getMetonymy_mention(int addr) {
        if (featOkTst && casFeat_metonymy_mention == null)
      jcas.throwFeatMissing("metonymy_mention", "de.julielab.jules.types.ace.EntityMention");
    return ll_cas.ll_getStringValue(addr, casFeatCode_metonymy_mention);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setMetonymy_mention(int addr, String v) {
        if (featOkTst && casFeat_metonymy_mention == null)
      jcas.throwFeatMissing("metonymy_mention", "de.julielab.jules.types.ace.EntityMention");
    ll_cas.ll_setStringValue(addr, casFeatCode_metonymy_mention, v);}
    
  
 
  /** @generated */
  final Feature casFeat_mention_type;
  /** @generated */
  final int     casFeatCode_mention_type;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getMention_type(int addr) {
        if (featOkTst && casFeat_mention_type == null)
      jcas.throwFeatMissing("mention_type", "de.julielab.jules.types.ace.EntityMention");
    return ll_cas.ll_getStringValue(addr, casFeatCode_mention_type);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setMention_type(int addr, String v) {
        if (featOkTst && casFeat_mention_type == null)
      jcas.throwFeatMissing("mention_type", "de.julielab.jules.types.ace.EntityMention");
    ll_cas.ll_setStringValue(addr, casFeatCode_mention_type, v);}
    
  
 
  /** @generated */
  final Feature casFeat_mention_ldctype;
  /** @generated */
  final int     casFeatCode_mention_ldctype;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getMention_ldctype(int addr) {
        if (featOkTst && casFeat_mention_ldctype == null)
      jcas.throwFeatMissing("mention_ldctype", "de.julielab.jules.types.ace.EntityMention");
    return ll_cas.ll_getStringValue(addr, casFeatCode_mention_ldctype);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setMention_ldctype(int addr, String v) {
        if (featOkTst && casFeat_mention_ldctype == null)
      jcas.throwFeatMissing("mention_ldctype", "de.julielab.jules.types.ace.EntityMention");
    ll_cas.ll_setStringValue(addr, casFeatCode_mention_ldctype, v);}
    
  
 
  /** @generated */
  final Feature casFeat_entity_ref;
  /** @generated */
  final int     casFeatCode_entity_ref;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getEntity_ref(int addr) {
        if (featOkTst && casFeat_entity_ref == null)
      jcas.throwFeatMissing("entity_ref", "de.julielab.jules.types.ace.EntityMention");
    return ll_cas.ll_getRefValue(addr, casFeatCode_entity_ref);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setEntity_ref(int addr, int v) {
        if (featOkTst && casFeat_entity_ref == null)
      jcas.throwFeatMissing("entity_ref", "de.julielab.jules.types.ace.EntityMention");
    ll_cas.ll_setRefValue(addr, casFeatCode_entity_ref, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public EntityMention_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_head = jcas.getRequiredFeatureDE(casType, "head", "de.julielab.jules.types.ace.Head", featOkTst);
    casFeatCode_head  = (null == casFeat_head) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_head).getCode();

 
    casFeat_ldcatr = jcas.getRequiredFeatureDE(casType, "ldcatr", "uima.cas.String", featOkTst);
    casFeatCode_ldcatr  = (null == casFeat_ldcatr) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_ldcatr).getCode();

 
    casFeat_role = jcas.getRequiredFeatureDE(casType, "role", "uima.cas.String", featOkTst);
    casFeatCode_role  = (null == casFeat_role) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_role).getCode();

 
    casFeat_metonymy_mention = jcas.getRequiredFeatureDE(casType, "metonymy_mention", "uima.cas.String", featOkTst);
    casFeatCode_metonymy_mention  = (null == casFeat_metonymy_mention) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_metonymy_mention).getCode();

 
    casFeat_mention_type = jcas.getRequiredFeatureDE(casType, "mention_type", "uima.cas.String", featOkTst);
    casFeatCode_mention_type  = (null == casFeat_mention_type) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_mention_type).getCode();

 
    casFeat_mention_ldctype = jcas.getRequiredFeatureDE(casType, "mention_ldctype", "uima.cas.String", featOkTst);
    casFeatCode_mention_ldctype  = (null == casFeat_mention_ldctype) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_mention_ldctype).getCode();

 
    casFeat_entity_ref = jcas.getRequiredFeatureDE(casType, "entity_ref", "de.julielab.jules.types.ace.Entity", featOkTst);
    casFeatCode_entity_ref  = (null == casFeat_entity_ref) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_entity_ref).getCode();

  }
}



    