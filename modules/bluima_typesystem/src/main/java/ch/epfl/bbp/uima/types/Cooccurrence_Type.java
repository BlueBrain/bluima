
/* First created by JCasGen Mon Feb 17 12:26:53 CET 2014 */
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

/** A coocurrence btw two annotations.
 * Updated by JCasGen Wed Jun 04 18:01:55 CEST 2014
 * @generated */
public class Cooccurrence_Type extends Annotation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Cooccurrence_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Cooccurrence_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Cooccurrence(addr, Cooccurrence_Type.this);
  			   Cooccurrence_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Cooccurrence(addr, Cooccurrence_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Cooccurrence.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("ch.epfl.bbp.uima.types.Cooccurrence");
 
  /** @generated */
  final Feature casFeat_firstEntity;
  /** @generated */
  final int     casFeatCode_firstEntity;
  /** @generated */ 
  public int getFirstEntity(int addr) {
        if (featOkTst && casFeat_firstEntity == null)
      jcas.throwFeatMissing("firstEntity", "ch.epfl.bbp.uima.types.Cooccurrence");
    return ll_cas.ll_getRefValue(addr, casFeatCode_firstEntity);
  }
  /** @generated */    
  public void setFirstEntity(int addr, int v) {
        if (featOkTst && casFeat_firstEntity == null)
      jcas.throwFeatMissing("firstEntity", "ch.epfl.bbp.uima.types.Cooccurrence");
    ll_cas.ll_setRefValue(addr, casFeatCode_firstEntity, v);}
    
  
 
  /** @generated */
  final Feature casFeat_secondEntity;
  /** @generated */
  final int     casFeatCode_secondEntity;
  /** @generated */ 
  public int getSecondEntity(int addr) {
        if (featOkTst && casFeat_secondEntity == null)
      jcas.throwFeatMissing("secondEntity", "ch.epfl.bbp.uima.types.Cooccurrence");
    return ll_cas.ll_getRefValue(addr, casFeatCode_secondEntity);
  }
  /** @generated */    
  public void setSecondEntity(int addr, int v) {
        if (featOkTst && casFeat_secondEntity == null)
      jcas.throwFeatMissing("secondEntity", "ch.epfl.bbp.uima.types.Cooccurrence");
    ll_cas.ll_setRefValue(addr, casFeatCode_secondEntity, v);}
    
  
 
  /** @generated */
  final Feature casFeat_snippetBegin;
  /** @generated */
  final int     casFeatCode_snippetBegin;
  /** @generated */ 
  public int getSnippetBegin(int addr) {
        if (featOkTst && casFeat_snippetBegin == null)
      jcas.throwFeatMissing("snippetBegin", "ch.epfl.bbp.uima.types.Cooccurrence");
    return ll_cas.ll_getIntValue(addr, casFeatCode_snippetBegin);
  }
  /** @generated */    
  public void setSnippetBegin(int addr, int v) {
        if (featOkTst && casFeat_snippetBegin == null)
      jcas.throwFeatMissing("snippetBegin", "ch.epfl.bbp.uima.types.Cooccurrence");
    ll_cas.ll_setIntValue(addr, casFeatCode_snippetBegin, v);}
    
  
 
  /** @generated */
  final Feature casFeat_snippetEnd;
  /** @generated */
  final int     casFeatCode_snippetEnd;
  /** @generated */ 
  public int getSnippetEnd(int addr) {
        if (featOkTst && casFeat_snippetEnd == null)
      jcas.throwFeatMissing("snippetEnd", "ch.epfl.bbp.uima.types.Cooccurrence");
    return ll_cas.ll_getIntValue(addr, casFeatCode_snippetEnd);
  }
  /** @generated */    
  public void setSnippetEnd(int addr, int v) {
        if (featOkTst && casFeat_snippetEnd == null)
      jcas.throwFeatMissing("snippetEnd", "ch.epfl.bbp.uima.types.Cooccurrence");
    ll_cas.ll_setIntValue(addr, casFeatCode_snippetEnd, v);}
    
  
 
  /** @generated */
  final Feature casFeat_firstIds;
  /** @generated */
  final int     casFeatCode_firstIds;
  /** @generated */ 
  public int getFirstIds(int addr) {
        if (featOkTst && casFeat_firstIds == null)
      jcas.throwFeatMissing("firstIds", "ch.epfl.bbp.uima.types.Cooccurrence");
    return ll_cas.ll_getRefValue(addr, casFeatCode_firstIds);
  }
  /** @generated */    
  public void setFirstIds(int addr, int v) {
        if (featOkTst && casFeat_firstIds == null)
      jcas.throwFeatMissing("firstIds", "ch.epfl.bbp.uima.types.Cooccurrence");
    ll_cas.ll_setRefValue(addr, casFeatCode_firstIds, v);}
    
   /** @generated */
  public String getFirstIds(int addr, int i) {
        if (featOkTst && casFeat_firstIds == null)
      jcas.throwFeatMissing("firstIds", "ch.epfl.bbp.uima.types.Cooccurrence");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_firstIds), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_firstIds), i);
  return ll_cas.ll_getStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_firstIds), i);
  }
   
  /** @generated */ 
  public void setFirstIds(int addr, int i, String v) {
        if (featOkTst && casFeat_firstIds == null)
      jcas.throwFeatMissing("firstIds", "ch.epfl.bbp.uima.types.Cooccurrence");
    if (lowLevelTypeChecks)
      ll_cas.ll_setStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_firstIds), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_firstIds), i);
    ll_cas.ll_setStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_firstIds), i, v);
  }
 
 
  /** @generated */
  final Feature casFeat_secondIds;
  /** @generated */
  final int     casFeatCode_secondIds;
  /** @generated */ 
  public int getSecondIds(int addr) {
        if (featOkTst && casFeat_secondIds == null)
      jcas.throwFeatMissing("secondIds", "ch.epfl.bbp.uima.types.Cooccurrence");
    return ll_cas.ll_getRefValue(addr, casFeatCode_secondIds);
  }
  /** @generated */    
  public void setSecondIds(int addr, int v) {
        if (featOkTst && casFeat_secondIds == null)
      jcas.throwFeatMissing("secondIds", "ch.epfl.bbp.uima.types.Cooccurrence");
    ll_cas.ll_setRefValue(addr, casFeatCode_secondIds, v);}
    
   /** @generated */
  public String getSecondIds(int addr, int i) {
        if (featOkTst && casFeat_secondIds == null)
      jcas.throwFeatMissing("secondIds", "ch.epfl.bbp.uima.types.Cooccurrence");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_secondIds), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_secondIds), i);
  return ll_cas.ll_getStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_secondIds), i);
  }
   
  /** @generated */ 
  public void setSecondIds(int addr, int i, String v) {
        if (featOkTst && casFeat_secondIds == null)
      jcas.throwFeatMissing("secondIds", "ch.epfl.bbp.uima.types.Cooccurrence");
    if (lowLevelTypeChecks)
      ll_cas.ll_setStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_secondIds), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_secondIds), i);
    ll_cas.ll_setStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_secondIds), i, v);
  }
 
 
  /** @generated */
  final Feature casFeat_cooccurrenceType;
  /** @generated */
  final int     casFeatCode_cooccurrenceType;
  /** @generated */ 
  public String getCooccurrenceType(int addr) {
        if (featOkTst && casFeat_cooccurrenceType == null)
      jcas.throwFeatMissing("cooccurrenceType", "ch.epfl.bbp.uima.types.Cooccurrence");
    return ll_cas.ll_getStringValue(addr, casFeatCode_cooccurrenceType);
  }
  /** @generated */    
  public void setCooccurrenceType(int addr, String v) {
        if (featOkTst && casFeat_cooccurrenceType == null)
      jcas.throwFeatMissing("cooccurrenceType", "ch.epfl.bbp.uima.types.Cooccurrence");
    ll_cas.ll_setStringValue(addr, casFeatCode_cooccurrenceType, v);}
    
  
 
  /** @generated */
  final Feature casFeat_confidence;
  /** @generated */
  final int     casFeatCode_confidence;
  /** @generated */ 
  public float getConfidence(int addr) {
        if (featOkTst && casFeat_confidence == null)
      jcas.throwFeatMissing("confidence", "ch.epfl.bbp.uima.types.Cooccurrence");
    return ll_cas.ll_getFloatValue(addr, casFeatCode_confidence);
  }
  /** @generated */    
  public void setConfidence(int addr, float v) {
        if (featOkTst && casFeat_confidence == null)
      jcas.throwFeatMissing("confidence", "ch.epfl.bbp.uima.types.Cooccurrence");
    ll_cas.ll_setFloatValue(addr, casFeatCode_confidence, v);}
    
  
 
  /** @generated */
  final Feature casFeat_hasInteraction;
  /** @generated */
  final int     casFeatCode_hasInteraction;
  /** @generated */ 
  public boolean getHasInteraction(int addr) {
        if (featOkTst && casFeat_hasInteraction == null)
      jcas.throwFeatMissing("hasInteraction", "ch.epfl.bbp.uima.types.Cooccurrence");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_hasInteraction);
  }
  /** @generated */    
  public void setHasInteraction(int addr, boolean v) {
        if (featOkTst && casFeat_hasInteraction == null)
      jcas.throwFeatMissing("hasInteraction", "ch.epfl.bbp.uima.types.Cooccurrence");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_hasInteraction, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public Cooccurrence_Type(JCas jcas, Type casType) {
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

 
    casFeat_firstIds = jcas.getRequiredFeatureDE(casType, "firstIds", "uima.cas.StringArray", featOkTst);
    casFeatCode_firstIds  = (null == casFeat_firstIds) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_firstIds).getCode();

 
    casFeat_secondIds = jcas.getRequiredFeatureDE(casType, "secondIds", "uima.cas.StringArray", featOkTst);
    casFeatCode_secondIds  = (null == casFeat_secondIds) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_secondIds).getCode();

 
    casFeat_cooccurrenceType = jcas.getRequiredFeatureDE(casType, "cooccurrenceType", "uima.cas.String", featOkTst);
    casFeatCode_cooccurrenceType  = (null == casFeat_cooccurrenceType) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_cooccurrenceType).getCode();

 
    casFeat_confidence = jcas.getRequiredFeatureDE(casType, "confidence", "uima.cas.Float", featOkTst);
    casFeatCode_confidence  = (null == casFeat_confidence) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_confidence).getCode();

 
    casFeat_hasInteraction = jcas.getRequiredFeatureDE(casType, "hasInteraction", "uima.cas.Boolean", featOkTst);
    casFeatCode_hasInteraction  = (null == casFeat_hasInteraction) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_hasInteraction).getCode();

  }
}



    