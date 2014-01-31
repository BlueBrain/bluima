
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

/** The reference to an external resource
 * Updated by JCasGen Mon Oct 21 13:03:30 CEST 2013
 * @generated */
public class ResourceEntry_Type extends Annotation_Type {
  /** @generated */
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (ResourceEntry_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = ResourceEntry_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new ResourceEntry(addr, ResourceEntry_Type.this);
  			   ResourceEntry_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new ResourceEntry(addr, ResourceEntry_Type.this);
  	  }
    };
  /** @generated */
  public final static int typeIndexID = ResourceEntry.typeIndexID;
  /** @generated 
     @modifiable */
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.julielab.jules.types.ResourceEntry");
 
  /** @generated */
  final Feature casFeat_source;
  /** @generated */
  final int     casFeatCode_source;
  /** @generated */ 
  public String getSource(int addr) {
        if (featOkTst && casFeat_source == null)
      jcas.throwFeatMissing("source", "de.julielab.jules.types.ResourceEntry");
    return ll_cas.ll_getStringValue(addr, casFeatCode_source);
  }
  /** @generated */    
  public void setSource(int addr, String v) {
        if (featOkTst && casFeat_source == null)
      jcas.throwFeatMissing("source", "de.julielab.jules.types.ResourceEntry");
    ll_cas.ll_setStringValue(addr, casFeatCode_source, v);}
    
  
 
  /** @generated */
  final Feature casFeat_entryId;
  /** @generated */
  final int     casFeatCode_entryId;
  /** @generated */ 
  public String getEntryId(int addr) {
        if (featOkTst && casFeat_entryId == null)
      jcas.throwFeatMissing("entryId", "de.julielab.jules.types.ResourceEntry");
    return ll_cas.ll_getStringValue(addr, casFeatCode_entryId);
  }
  /** @generated */    
  public void setEntryId(int addr, String v) {
        if (featOkTst && casFeat_entryId == null)
      jcas.throwFeatMissing("entryId", "de.julielab.jules.types.ResourceEntry");
    ll_cas.ll_setStringValue(addr, casFeatCode_entryId, v);}
    
  
 
  /** @generated */
  final Feature casFeat_version;
  /** @generated */
  final int     casFeatCode_version;
  /** @generated */ 
  public String getVersion(int addr) {
        if (featOkTst && casFeat_version == null)
      jcas.throwFeatMissing("version", "de.julielab.jules.types.ResourceEntry");
    return ll_cas.ll_getStringValue(addr, casFeatCode_version);
  }
  /** @generated */    
  public void setVersion(int addr, String v) {
        if (featOkTst && casFeat_version == null)
      jcas.throwFeatMissing("version", "de.julielab.jules.types.ResourceEntry");
    ll_cas.ll_setStringValue(addr, casFeatCode_version, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public ResourceEntry_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_source = jcas.getRequiredFeatureDE(casType, "source", "uima.cas.String", featOkTst);
    casFeatCode_source  = (null == casFeat_source) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_source).getCode();

 
    casFeat_entryId = jcas.getRequiredFeatureDE(casType, "entryId", "uima.cas.String", featOkTst);
    casFeatCode_entryId  = (null == casFeat_entryId) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_entryId).getCode();

 
    casFeat_version = jcas.getRequiredFeatureDE(casType, "version", "uima.cas.String", featOkTst);
    casFeatCode_version  = (null == casFeat_version) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_version).getCode();

  }
}



    