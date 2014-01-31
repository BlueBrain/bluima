
/* First created by JCasGen Mon Sep 30 16:58:18 CEST 2013 */
package ch.epfl.bbp.uima.types;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** A coocurence btw two annotations.
 * Updated by JCasGen Mon Sep 30 21:29:11 CEST 2013
 * @generated */
public class Cooccurence_Type extends Annotation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Cooccurence_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Cooccurence_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Cooccurrence(addr, Cooccurence_Type.this);
  			   Cooccurence_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Cooccurrence(addr, Cooccurence_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Cooccurrence.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("ch.epfl.bbp.uima.types.Cooccurence");
 
  /** @generated */
  final Feature casFeat_firstEntity;
  /** @generated */
  final int     casFeatCode_firstEntity;
  /** @generated */ 
  public int getFirstEntity(int addr) {
        if (featOkTst && casFeat_firstEntity == null)
      jcas.throwFeatMissing("firstEntity", "ch.epfl.bbp.uima.types.Cooccurence");
    return ll_cas.ll_getRefValue(addr, casFeatCode_firstEntity);
  }
  /** @generated */    
  public void setFirstEntity(int addr, int v) {
        if (featOkTst && casFeat_firstEntity == null)
      jcas.throwFeatMissing("firstEntity", "ch.epfl.bbp.uima.types.Cooccurence");
    ll_cas.ll_setRefValue(addr, casFeatCode_firstEntity, v);}
    
  
 
  /** @generated */
  final Feature casFeat_secondEntity;
  /** @generated */
  final int     casFeatCode_secondEntity;
  /** @generated */ 
  public int getSecondEntity(int addr) {
        if (featOkTst && casFeat_secondEntity == null)
      jcas.throwFeatMissing("secondEntity", "ch.epfl.bbp.uima.types.Cooccurence");
    return ll_cas.ll_getRefValue(addr, casFeatCode_secondEntity);
  }
  /** @generated */    
  public void setSecondEntity(int addr, int v) {
        if (featOkTst && casFeat_secondEntity == null)
      jcas.throwFeatMissing("secondEntity", "ch.epfl.bbp.uima.types.Cooccurence");
    ll_cas.ll_setRefValue(addr, casFeatCode_secondEntity, v);}
    
  
 
  /** @generated */
  final Feature casFeat_snippetBegin;
  /** @generated */
  final int     casFeatCode_snippetBegin;
  /** @generated */ 
  public int getSnippetBegin(int addr) {
        if (featOkTst && casFeat_snippetBegin == null)
      jcas.throwFeatMissing("snippetBegin", "ch.epfl.bbp.uima.types.Cooccurence");
    return ll_cas.ll_getIntValue(addr, casFeatCode_snippetBegin);
  }
  /** @generated */    
  public void setSnippetBegin(int addr, int v) {
        if (featOkTst && casFeat_snippetBegin == null)
      jcas.throwFeatMissing("snippetBegin", "ch.epfl.bbp.uima.types.Cooccurence");
    ll_cas.ll_setIntValue(addr, casFeatCode_snippetBegin, v);}
    
  
 
  /** @generated */
  final Feature casFeat_snippetEnd;
  /** @generated */
  final int     casFeatCode_snippetEnd;
  /** @generated */ 
  public int getSnippetEnd(int addr) {
        if (featOkTst && casFeat_snippetEnd == null)
      jcas.throwFeatMissing("snippetEnd", "ch.epfl.bbp.uima.types.Cooccurence");
    return ll_cas.ll_getIntValue(addr, casFeatCode_snippetEnd);
  }
  /** @generated */    
  public void setSnippetEnd(int addr, int v) {
        if (featOkTst && casFeat_snippetEnd == null)
      jcas.throwFeatMissing("snippetEnd", "ch.epfl.bbp.uima.types.Cooccurence");
    ll_cas.ll_setIntValue(addr, casFeatCode_snippetEnd, v);}
    
  
 
  /** @generated */
  final Feature casFeat_firstId;
  /** @generated */
  final int     casFeatCode_firstId;
  /** @generated */ 
  public String getFirstId(int addr) {
        if (featOkTst && casFeat_firstId == null)
      jcas.throwFeatMissing("firstId", "ch.epfl.bbp.uima.types.Cooccurence");
    return ll_cas.ll_getStringValue(addr, casFeatCode_firstId);
  }
  /** @generated */    
  public void setFirstId(int addr, String v) {
        if (featOkTst && casFeat_firstId == null)
      jcas.throwFeatMissing("firstId", "ch.epfl.bbp.uima.types.Cooccurence");
    ll_cas.ll_setStringValue(addr, casFeatCode_firstId, v);}
    
  
 
  /** @generated */
  final Feature casFeat_secondId;
  /** @generated */
  final int     casFeatCode_secondId;
  /** @generated */ 
  public String getSecondId(int addr) {
        if (featOkTst && casFeat_secondId == null)
      jcas.throwFeatMissing("secondId", "ch.epfl.bbp.uima.types.Cooccurence");
    return ll_cas.ll_getStringValue(addr, casFeatCode_secondId);
  }
  /** @generated */    
  public void setSecondId(int addr, String v) {
        if (featOkTst && casFeat_secondId == null)
      jcas.throwFeatMissing("secondId", "ch.epfl.bbp.uima.types.Cooccurence");
    ll_cas.ll_setStringValue(addr, casFeatCode_secondId, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public Cooccurence_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_firstEntity = jcas.getRequiredFeatureDE(casType, "firstEntity", "uima.tcas.Annotation", featOkTst);
    casFeatCode_firstEntity  = (null == casFeat_firstEntity) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_firstEntity).getCode();

 
    casFeat_secondEntity = jcas.getRequiredFeatureDE(casType, "secondEntity", "uima.tcas.Annotation", featOkTst);
    casFeatCode_secondEntity  = (null == casFeat_secondEntity) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_secondEntity).getCode();

 
    casFeat_snippetBegin = jcas.getRequiredFeatureDE(casType, "snippetBegin", "uima.cas.Integer", featOkTst);
    casFeatCode_snippetBegin  = (null == casFeat_snippetBegin) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_snippetBegin).getCode();

 
    casFeat_snippetEnd = jcas.getRequiredFeatureDE(casType, "snippetEnd", "uima.cas.Integer", featOkTst);
    casFeatCode_snippetEnd  = (null == casFeat_snippetEnd) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_snippetEnd).getCode();

 
    casFeat_firstId = jcas.getRequiredFeatureDE(casType, "firstId", "uima.cas.String", featOkTst);
    casFeatCode_firstId  = (null == casFeat_firstId) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_firstId).getCode();

 
    casFeat_secondId = jcas.getRequiredFeatureDE(casType, "secondId", "uima.cas.String", featOkTst);
    casFeatCode_secondId  = (null == casFeat_secondId) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_secondId).getCode();

  }
}



    