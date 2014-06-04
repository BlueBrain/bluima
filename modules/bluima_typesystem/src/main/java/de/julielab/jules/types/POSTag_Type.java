
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

/** POS is a linguistic category of words (tokens) that are defined by their particular syntactic/morphological behaviours (e.g. noun, verb).
 * Updated by JCasGen Wed Jun 04 18:01:58 CEST 2014
 * @generated */
public class POSTag_Type extends Annotation_Type {
  /** @generated */
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (POSTag_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = POSTag_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new POSTag(addr, POSTag_Type.this);
  			   POSTag_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new POSTag(addr, POSTag_Type.this);
  	  }
    };
  /** @generated */
  public final static int typeIndexID = POSTag.typeIndexID;
  /** @generated 
     @modifiable */
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.julielab.jules.types.POSTag");
 
  /** @generated */
  final Feature casFeat_tagsetId;
  /** @generated */
  final int     casFeatCode_tagsetId;
  /** @generated */ 
  public String getTagsetId(int addr) {
        if (featOkTst && casFeat_tagsetId == null)
      jcas.throwFeatMissing("tagsetId", "de.julielab.jules.types.POSTag");
    return ll_cas.ll_getStringValue(addr, casFeatCode_tagsetId);
  }
  /** @generated */    
  public void setTagsetId(int addr, String v) {
        if (featOkTst && casFeat_tagsetId == null)
      jcas.throwFeatMissing("tagsetId", "de.julielab.jules.types.POSTag");
    ll_cas.ll_setStringValue(addr, casFeatCode_tagsetId, v);}
    
  
 
  /** @generated */
  final Feature casFeat_value;
  /** @generated */
  final int     casFeatCode_value;
  /** @generated */ 
  public String getValue(int addr) {
        if (featOkTst && casFeat_value == null)
      jcas.throwFeatMissing("value", "de.julielab.jules.types.POSTag");
    return ll_cas.ll_getStringValue(addr, casFeatCode_value);
  }
  /** @generated */    
  public void setValue(int addr, String v) {
        if (featOkTst && casFeat_value == null)
      jcas.throwFeatMissing("value", "de.julielab.jules.types.POSTag");
    ll_cas.ll_setStringValue(addr, casFeatCode_value, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public POSTag_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_tagsetId = jcas.getRequiredFeatureDE(casType, "tagsetId", "uima.cas.String", featOkTst);
    casFeatCode_tagsetId  = (null == casFeat_tagsetId) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_tagsetId).getCode();

 
    casFeat_value = jcas.getRequiredFeatureDE(casType, "value", "uima.cas.String", featOkTst);
    casFeatCode_value  = (null == casFeat_value) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_value).getCode();

  }
}



    