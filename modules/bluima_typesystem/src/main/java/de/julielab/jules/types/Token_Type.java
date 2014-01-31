
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

/** Token annotation marks the span of a token and takes all additional annotations that are on the token level, including Part-of-Speech information, lemma, stemmed form,  grammatical features such as gender, number and orthographical information; furthemore, Token includes the information about dependency relations to other tokens (see correspondent annotation types for further infromation).
 * Updated by JCasGen Mon Oct 21 13:03:30 CEST 2013
 * @generated */
public class Token_Type extends Annotation_Type {
  /** @generated */
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Token_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Token_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Token(addr, Token_Type.this);
  			   Token_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Token(addr, Token_Type.this);
  	  }
    };
  /** @generated */
  public final static int typeIndexID = Token.typeIndexID;
  /** @generated 
     @modifiable */
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.julielab.jules.types.Token");
 
  /** @generated */
  final Feature casFeat_lemma;
  /** @generated */
  final int     casFeatCode_lemma;
  /** @generated */ 
  public int getLemma(int addr) {
        if (featOkTst && casFeat_lemma == null)
      jcas.throwFeatMissing("lemma", "de.julielab.jules.types.Token");
    return ll_cas.ll_getRefValue(addr, casFeatCode_lemma);
  }
  /** @generated */    
  public void setLemma(int addr, int v) {
        if (featOkTst && casFeat_lemma == null)
      jcas.throwFeatMissing("lemma", "de.julielab.jules.types.Token");
    ll_cas.ll_setRefValue(addr, casFeatCode_lemma, v);}
    
  
 
  /** @generated */
  final Feature casFeat_posTag;
  /** @generated */
  final int     casFeatCode_posTag;
  /** @generated */ 
  public int getPosTag(int addr) {
        if (featOkTst && casFeat_posTag == null)
      jcas.throwFeatMissing("posTag", "de.julielab.jules.types.Token");
    return ll_cas.ll_getRefValue(addr, casFeatCode_posTag);
  }
  /** @generated */    
  public void setPosTag(int addr, int v) {
        if (featOkTst && casFeat_posTag == null)
      jcas.throwFeatMissing("posTag", "de.julielab.jules.types.Token");
    ll_cas.ll_setRefValue(addr, casFeatCode_posTag, v);}
    
   /** @generated */
  public int getPosTag(int addr, int i) {
        if (featOkTst && casFeat_posTag == null)
      jcas.throwFeatMissing("posTag", "de.julielab.jules.types.Token");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_posTag), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_posTag), i);
  return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_posTag), i);
  }
   
  /** @generated */ 
  public void setPosTag(int addr, int i, int v) {
        if (featOkTst && casFeat_posTag == null)
      jcas.throwFeatMissing("posTag", "de.julielab.jules.types.Token");
    if (lowLevelTypeChecks)
      ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_posTag), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_posTag), i);
    ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_posTag), i, v);
  }
 
 
  /** @generated */
  final Feature casFeat_stemmedForm;
  /** @generated */
  final int     casFeatCode_stemmedForm;
  /** @generated */ 
  public int getStemmedForm(int addr) {
        if (featOkTst && casFeat_stemmedForm == null)
      jcas.throwFeatMissing("stemmedForm", "de.julielab.jules.types.Token");
    return ll_cas.ll_getRefValue(addr, casFeatCode_stemmedForm);
  }
  /** @generated */    
  public void setStemmedForm(int addr, int v) {
        if (featOkTst && casFeat_stemmedForm == null)
      jcas.throwFeatMissing("stemmedForm", "de.julielab.jules.types.Token");
    ll_cas.ll_setRefValue(addr, casFeatCode_stemmedForm, v);}
    
  
 
  /** @generated */
  final Feature casFeat_feats;
  /** @generated */
  final int     casFeatCode_feats;
  /** @generated */ 
  public int getFeats(int addr) {
        if (featOkTst && casFeat_feats == null)
      jcas.throwFeatMissing("feats", "de.julielab.jules.types.Token");
    return ll_cas.ll_getRefValue(addr, casFeatCode_feats);
  }
  /** @generated */    
  public void setFeats(int addr, int v) {
        if (featOkTst && casFeat_feats == null)
      jcas.throwFeatMissing("feats", "de.julielab.jules.types.Token");
    ll_cas.ll_setRefValue(addr, casFeatCode_feats, v);}
    
  
 
  /** @generated */
  final Feature casFeat_orthogr;
  /** @generated */
  final int     casFeatCode_orthogr;
  /** @generated */ 
  public String getOrthogr(int addr) {
        if (featOkTst && casFeat_orthogr == null)
      jcas.throwFeatMissing("orthogr", "de.julielab.jules.types.Token");
    return ll_cas.ll_getStringValue(addr, casFeatCode_orthogr);
  }
  /** @generated */    
  public void setOrthogr(int addr, String v) {
        if (featOkTst && casFeat_orthogr == null)
      jcas.throwFeatMissing("orthogr", "de.julielab.jules.types.Token");
    ll_cas.ll_setStringValue(addr, casFeatCode_orthogr, v);}
    
  
 
  /** @generated */
  final Feature casFeat_depRel;
  /** @generated */
  final int     casFeatCode_depRel;
  /** @generated */ 
  public int getDepRel(int addr) {
        if (featOkTst && casFeat_depRel == null)
      jcas.throwFeatMissing("depRel", "de.julielab.jules.types.Token");
    return ll_cas.ll_getRefValue(addr, casFeatCode_depRel);
  }
  /** @generated */    
  public void setDepRel(int addr, int v) {
        if (featOkTst && casFeat_depRel == null)
      jcas.throwFeatMissing("depRel", "de.julielab.jules.types.Token");
    ll_cas.ll_setRefValue(addr, casFeatCode_depRel, v);}
    
   /** @generated */
  public int getDepRel(int addr, int i) {
        if (featOkTst && casFeat_depRel == null)
      jcas.throwFeatMissing("depRel", "de.julielab.jules.types.Token");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_depRel), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_depRel), i);
  return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_depRel), i);
  }
   
  /** @generated */ 
  public void setDepRel(int addr, int i, int v) {
        if (featOkTst && casFeat_depRel == null)
      jcas.throwFeatMissing("depRel", "de.julielab.jules.types.Token");
    if (lowLevelTypeChecks)
      ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_depRel), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_depRel), i);
    ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_depRel), i, v);
  }
 
 
  /** @generated */
  final Feature casFeat_pos;
  /** @generated */
  final int     casFeatCode_pos;
  /** @generated */ 
  public String getPos(int addr) {
        if (featOkTst && casFeat_pos == null)
      jcas.throwFeatMissing("pos", "de.julielab.jules.types.Token");
    return ll_cas.ll_getStringValue(addr, casFeatCode_pos);
  }
  /** @generated */    
  public void setPos(int addr, String v) {
        if (featOkTst && casFeat_pos == null)
      jcas.throwFeatMissing("pos", "de.julielab.jules.types.Token");
    ll_cas.ll_setStringValue(addr, casFeatCode_pos, v);}
    
  
 
  /** @generated */
  final Feature casFeat_lemmaStr;
  /** @generated */
  final int     casFeatCode_lemmaStr;
  /** @generated */ 
  public String getLemmaStr(int addr) {
        if (featOkTst && casFeat_lemmaStr == null)
      jcas.throwFeatMissing("lemmaStr", "de.julielab.jules.types.Token");
    return ll_cas.ll_getStringValue(addr, casFeatCode_lemmaStr);
  }
  /** @generated */    
  public void setLemmaStr(int addr, String v) {
        if (featOkTst && casFeat_lemmaStr == null)
      jcas.throwFeatMissing("lemmaStr", "de.julielab.jules.types.Token");
    ll_cas.ll_setStringValue(addr, casFeatCode_lemmaStr, v);}
    
  
 
  /** @generated */
  final Feature casFeat_topicIds;
  /** @generated */
  final int     casFeatCode_topicIds;
  /** @generated */ 
  public String getTopicIds(int addr) {
        if (featOkTst && casFeat_topicIds == null)
      jcas.throwFeatMissing("topicIds", "de.julielab.jules.types.Token");
    return ll_cas.ll_getStringValue(addr, casFeatCode_topicIds);
  }
  /** @generated */    
  public void setTopicIds(int addr, String v) {
        if (featOkTst && casFeat_topicIds == null)
      jcas.throwFeatMissing("topicIds", "de.julielab.jules.types.Token");
    ll_cas.ll_setStringValue(addr, casFeatCode_topicIds, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public Token_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_lemma = jcas.getRequiredFeatureDE(casType, "lemma", "de.julielab.jules.types.Lemma", featOkTst);
    casFeatCode_lemma  = (null == casFeat_lemma) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_lemma).getCode();

 
    casFeat_posTag = jcas.getRequiredFeatureDE(casType, "posTag", "uima.cas.FSArray", featOkTst);
    casFeatCode_posTag  = (null == casFeat_posTag) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_posTag).getCode();

 
    casFeat_stemmedForm = jcas.getRequiredFeatureDE(casType, "stemmedForm", "de.julielab.jules.types.StemmedForm", featOkTst);
    casFeatCode_stemmedForm  = (null == casFeat_stemmedForm) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_stemmedForm).getCode();

 
    casFeat_feats = jcas.getRequiredFeatureDE(casType, "feats", "de.julielab.jules.types.GrammaticalFeats", featOkTst);
    casFeatCode_feats  = (null == casFeat_feats) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_feats).getCode();

 
    casFeat_orthogr = jcas.getRequiredFeatureDE(casType, "orthogr", "de.julielab.jules.types.Orthography", featOkTst);
    casFeatCode_orthogr  = (null == casFeat_orthogr) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_orthogr).getCode();

 
    casFeat_depRel = jcas.getRequiredFeatureDE(casType, "depRel", "uima.cas.FSArray", featOkTst);
    casFeatCode_depRel  = (null == casFeat_depRel) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_depRel).getCode();

 
    casFeat_pos = jcas.getRequiredFeatureDE(casType, "pos", "uima.cas.String", featOkTst);
    casFeatCode_pos  = (null == casFeat_pos) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_pos).getCode();

 
    casFeat_lemmaStr = jcas.getRequiredFeatureDE(casType, "lemmaStr", "uima.cas.String", featOkTst);
    casFeatCode_lemmaStr  = (null == casFeat_lemmaStr) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_lemmaStr).getCode();

 
    casFeat_topicIds = jcas.getRequiredFeatureDE(casType, "topicIds", "uima.cas.String", featOkTst);
    casFeatCode_topicIds  = (null == casFeat_topicIds) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_topicIds).getCode();

  }
}



    