
/* First created by JCasGen Wed Oct 19 19:10:41 CEST 2011 */
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

/** Medical Subject Headings, see NLM's MeSH for a detailed description.
 * Updated by JCasGen Fri Dec 09 11:59:26 CET 2011
 * @generated */
public class MeshHeading_Type extends Annotation_Type {
  /** @generated */
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (MeshHeading_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = MeshHeading_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new MeshHeading(addr, MeshHeading_Type.this);
  			   MeshHeading_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new MeshHeading(addr, MeshHeading_Type.this);
  	  }
    };
  /** @generated */
  public final static int typeIndexID = MeshHeading.typeIndexID;
  /** @generated 
     @modifiable */
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.julielab.jules.types.MeshHeading");
 
  /** @generated */
  final Feature casFeat_descriptorName;
  /** @generated */
  final int     casFeatCode_descriptorName;
  /** @generated */ 
  public String getDescriptorName(int addr) {
        if (featOkTst && casFeat_descriptorName == null)
      jcas.throwFeatMissing("descriptorName", "de.julielab.jules.types.MeshHeading");
    return ll_cas.ll_getStringValue(addr, casFeatCode_descriptorName);
  }
  /** @generated */    
  public void setDescriptorName(int addr, String v) {
        if (featOkTst && casFeat_descriptorName == null)
      jcas.throwFeatMissing("descriptorName", "de.julielab.jules.types.MeshHeading");
    ll_cas.ll_setStringValue(addr, casFeatCode_descriptorName, v);}
    
  
 
  /** @generated */
  final Feature casFeat_qualifierName;
  /** @generated */
  final int     casFeatCode_qualifierName;
  /** @generated */ 
  public String getQualifierName(int addr) {
        if (featOkTst && casFeat_qualifierName == null)
      jcas.throwFeatMissing("qualifierName", "de.julielab.jules.types.MeshHeading");
    return ll_cas.ll_getStringValue(addr, casFeatCode_qualifierName);
  }
  /** @generated */    
  public void setQualifierName(int addr, String v) {
        if (featOkTst && casFeat_qualifierName == null)
      jcas.throwFeatMissing("qualifierName", "de.julielab.jules.types.MeshHeading");
    ll_cas.ll_setStringValue(addr, casFeatCode_qualifierName, v);}
    
  
 
  /** @generated */
  final Feature casFeat_descriptorNameMajorTopic;
  /** @generated */
  final int     casFeatCode_descriptorNameMajorTopic;
  /** @generated */ 
  public boolean getDescriptorNameMajorTopic(int addr) {
        if (featOkTst && casFeat_descriptorNameMajorTopic == null)
      jcas.throwFeatMissing("descriptorNameMajorTopic", "de.julielab.jules.types.MeshHeading");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_descriptorNameMajorTopic);
  }
  /** @generated */    
  public void setDescriptorNameMajorTopic(int addr, boolean v) {
        if (featOkTst && casFeat_descriptorNameMajorTopic == null)
      jcas.throwFeatMissing("descriptorNameMajorTopic", "de.julielab.jules.types.MeshHeading");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_descriptorNameMajorTopic, v);}
    
  
 
  /** @generated */
  final Feature casFeat_qualifierNameMajorTopic;
  /** @generated */
  final int     casFeatCode_qualifierNameMajorTopic;
  /** @generated */ 
  public boolean getQualifierNameMajorTopic(int addr) {
        if (featOkTst && casFeat_qualifierNameMajorTopic == null)
      jcas.throwFeatMissing("qualifierNameMajorTopic", "de.julielab.jules.types.MeshHeading");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_qualifierNameMajorTopic);
  }
  /** @generated */    
  public void setQualifierNameMajorTopic(int addr, boolean v) {
        if (featOkTst && casFeat_qualifierNameMajorTopic == null)
      jcas.throwFeatMissing("qualifierNameMajorTopic", "de.julielab.jules.types.MeshHeading");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_qualifierNameMajorTopic, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public MeshHeading_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_descriptorName = jcas.getRequiredFeatureDE(casType, "descriptorName", "uima.cas.String", featOkTst);
    casFeatCode_descriptorName  = (null == casFeat_descriptorName) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_descriptorName).getCode();

 
    casFeat_qualifierName = jcas.getRequiredFeatureDE(casType, "qualifierName", "uima.cas.String", featOkTst);
    casFeatCode_qualifierName  = (null == casFeat_qualifierName) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_qualifierName).getCode();

 
    casFeat_descriptorNameMajorTopic = jcas.getRequiredFeatureDE(casType, "descriptorNameMajorTopic", "uima.cas.Boolean", featOkTst);
    casFeatCode_descriptorNameMajorTopic  = (null == casFeat_descriptorNameMajorTopic) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_descriptorNameMajorTopic).getCode();

 
    casFeat_qualifierNameMajorTopic = jcas.getRequiredFeatureDE(casType, "qualifierNameMajorTopic", "uima.cas.Boolean", featOkTst);
    casFeatCode_qualifierNameMajorTopic  = (null == casFeat_qualifierNameMajorTopic) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_qualifierNameMajorTopic).getCode();

  }
}



    