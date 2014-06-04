
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

/** 
 * Updated by JCasGen Wed Jun 04 18:01:58 CEST 2014
 * @generated */
public class OntRelationMention_Type extends Annotation_Type {
  /** @generated */
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (OntRelationMention_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = OntRelationMention_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new OntRelationMention(addr, OntRelationMention_Type.this);
  			   OntRelationMention_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new OntRelationMention(addr, OntRelationMention_Type.this);
  	  }
    };
  /** @generated */
  public final static int typeIndexID = OntRelationMention.typeIndexID;
  /** @generated 
     @modifiable */
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.julielab.jules.types.OntRelationMention");
 
  /** @generated */
  final Feature casFeat_domain;
  /** @generated */
  final int     casFeatCode_domain;
  /** @generated */ 
  public int getDomain(int addr) {
        if (featOkTst && casFeat_domain == null)
      jcas.throwFeatMissing("domain", "de.julielab.jules.types.OntRelationMention");
    return ll_cas.ll_getRefValue(addr, casFeatCode_domain);
  }
  /** @generated */    
  public void setDomain(int addr, int v) {
        if (featOkTst && casFeat_domain == null)
      jcas.throwFeatMissing("domain", "de.julielab.jules.types.OntRelationMention");
    ll_cas.ll_setRefValue(addr, casFeatCode_domain, v);}
    
  
 
  /** @generated */
  final Feature casFeat_range;
  /** @generated */
  final int     casFeatCode_range;
  /** @generated */ 
  public int getRange(int addr) {
        if (featOkTst && casFeat_range == null)
      jcas.throwFeatMissing("range", "de.julielab.jules.types.OntRelationMention");
    return ll_cas.ll_getRefValue(addr, casFeatCode_range);
  }
  /** @generated */    
  public void setRange(int addr, int v) {
        if (featOkTst && casFeat_range == null)
      jcas.throwFeatMissing("range", "de.julielab.jules.types.OntRelationMention");
    ll_cas.ll_setRefValue(addr, casFeatCode_range, v);}
    
  
 
  /** @generated */
  final Feature casFeat_domainList;
  /** @generated */
  final int     casFeatCode_domainList;
  /** @generated */ 
  public int getDomainList(int addr) {
        if (featOkTst && casFeat_domainList == null)
      jcas.throwFeatMissing("domainList", "de.julielab.jules.types.OntRelationMention");
    return ll_cas.ll_getRefValue(addr, casFeatCode_domainList);
  }
  /** @generated */    
  public void setDomainList(int addr, int v) {
        if (featOkTst && casFeat_domainList == null)
      jcas.throwFeatMissing("domainList", "de.julielab.jules.types.OntRelationMention");
    ll_cas.ll_setRefValue(addr, casFeatCode_domainList, v);}
    
   /** @generated */
  public int getDomainList(int addr, int i) {
        if (featOkTst && casFeat_domainList == null)
      jcas.throwFeatMissing("domainList", "de.julielab.jules.types.OntRelationMention");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_domainList), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_domainList), i);
  return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_domainList), i);
  }
   
  /** @generated */ 
  public void setDomainList(int addr, int i, int v) {
        if (featOkTst && casFeat_domainList == null)
      jcas.throwFeatMissing("domainList", "de.julielab.jules.types.OntRelationMention");
    if (lowLevelTypeChecks)
      ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_domainList), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_domainList), i);
    ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_domainList), i, v);
  }
 
 
  /** @generated */
  final Feature casFeat_rangeList;
  /** @generated */
  final int     casFeatCode_rangeList;
  /** @generated */ 
  public int getRangeList(int addr) {
        if (featOkTst && casFeat_rangeList == null)
      jcas.throwFeatMissing("rangeList", "de.julielab.jules.types.OntRelationMention");
    return ll_cas.ll_getRefValue(addr, casFeatCode_rangeList);
  }
  /** @generated */    
  public void setRangeList(int addr, int v) {
        if (featOkTst && casFeat_rangeList == null)
      jcas.throwFeatMissing("rangeList", "de.julielab.jules.types.OntRelationMention");
    ll_cas.ll_setRefValue(addr, casFeatCode_rangeList, v);}
    
   /** @generated */
  public int getRangeList(int addr, int i) {
        if (featOkTst && casFeat_rangeList == null)
      jcas.throwFeatMissing("rangeList", "de.julielab.jules.types.OntRelationMention");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_rangeList), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_rangeList), i);
  return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_rangeList), i);
  }
   
  /** @generated */ 
  public void setRangeList(int addr, int i, int v) {
        if (featOkTst && casFeat_rangeList == null)
      jcas.throwFeatMissing("rangeList", "de.julielab.jules.types.OntRelationMention");
    if (lowLevelTypeChecks)
      ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_rangeList), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_rangeList), i);
    ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_rangeList), i, v);
  }
 
 
  /** @generated */
  final Feature casFeat_name;
  /** @generated */
  final int     casFeatCode_name;
  /** @generated */ 
  public String getName(int addr) {
        if (featOkTst && casFeat_name == null)
      jcas.throwFeatMissing("name", "de.julielab.jules.types.OntRelationMention");
    return ll_cas.ll_getStringValue(addr, casFeatCode_name);
  }
  /** @generated */    
  public void setName(int addr, String v) {
        if (featOkTst && casFeat_name == null)
      jcas.throwFeatMissing("name", "de.julielab.jules.types.OntRelationMention");
    ll_cas.ll_setStringValue(addr, casFeatCode_name, v);}
    
  
 
  /** @generated */
  final Feature casFeat_ontRelationId;
  /** @generated */
  final int     casFeatCode_ontRelationId;
  /** @generated */ 
  public String getOntRelationId(int addr) {
        if (featOkTst && casFeat_ontRelationId == null)
      jcas.throwFeatMissing("ontRelationId", "de.julielab.jules.types.OntRelationMention");
    return ll_cas.ll_getStringValue(addr, casFeatCode_ontRelationId);
  }
  /** @generated */    
  public void setOntRelationId(int addr, String v) {
        if (featOkTst && casFeat_ontRelationId == null)
      jcas.throwFeatMissing("ontRelationId", "de.julielab.jules.types.OntRelationMention");
    ll_cas.ll_setStringValue(addr, casFeatCode_ontRelationId, v);}
    
  
 
  /** @generated */
  final Feature casFeat_sourceOntology;
  /** @generated */
  final int     casFeatCode_sourceOntology;
  /** @generated */ 
  public String getSourceOntology(int addr) {
        if (featOkTst && casFeat_sourceOntology == null)
      jcas.throwFeatMissing("sourceOntology", "de.julielab.jules.types.OntRelationMention");
    return ll_cas.ll_getStringValue(addr, casFeatCode_sourceOntology);
  }
  /** @generated */    
  public void setSourceOntology(int addr, String v) {
        if (featOkTst && casFeat_sourceOntology == null)
      jcas.throwFeatMissing("sourceOntology", "de.julielab.jules.types.OntRelationMention");
    ll_cas.ll_setStringValue(addr, casFeatCode_sourceOntology, v);}
    
  
 
  /** @generated */
  final Feature casFeat_textualRepresentation;
  /** @generated */
  final int     casFeatCode_textualRepresentation;
  /** @generated */ 
  public String getTextualRepresentation(int addr) {
        if (featOkTst && casFeat_textualRepresentation == null)
      jcas.throwFeatMissing("textualRepresentation", "de.julielab.jules.types.OntRelationMention");
    return ll_cas.ll_getStringValue(addr, casFeatCode_textualRepresentation);
  }
  /** @generated */    
  public void setTextualRepresentation(int addr, String v) {
        if (featOkTst && casFeat_textualRepresentation == null)
      jcas.throwFeatMissing("textualRepresentation", "de.julielab.jules.types.OntRelationMention");
    ll_cas.ll_setStringValue(addr, casFeatCode_textualRepresentation, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public OntRelationMention_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_domain = jcas.getRequiredFeatureDE(casType, "domain", "de.julielab.jules.types.Annotation", featOkTst);
    casFeatCode_domain  = (null == casFeat_domain) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_domain).getCode();

 
    casFeat_range = jcas.getRequiredFeatureDE(casType, "range", "de.julielab.jules.types.Annotation", featOkTst);
    casFeatCode_range  = (null == casFeat_range) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_range).getCode();

 
    casFeat_domainList = jcas.getRequiredFeatureDE(casType, "domainList", "uima.cas.FSArray", featOkTst);
    casFeatCode_domainList  = (null == casFeat_domainList) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_domainList).getCode();

 
    casFeat_rangeList = jcas.getRequiredFeatureDE(casType, "rangeList", "uima.cas.FSArray", featOkTst);
    casFeatCode_rangeList  = (null == casFeat_rangeList) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_rangeList).getCode();

 
    casFeat_name = jcas.getRequiredFeatureDE(casType, "name", "uima.cas.String", featOkTst);
    casFeatCode_name  = (null == casFeat_name) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_name).getCode();

 
    casFeat_ontRelationId = jcas.getRequiredFeatureDE(casType, "ontRelationId", "uima.cas.String", featOkTst);
    casFeatCode_ontRelationId  = (null == casFeat_ontRelationId) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_ontRelationId).getCode();

 
    casFeat_sourceOntology = jcas.getRequiredFeatureDE(casType, "sourceOntology", "uima.cas.String", featOkTst);
    casFeatCode_sourceOntology  = (null == casFeat_sourceOntology) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_sourceOntology).getCode();

 
    casFeat_textualRepresentation = jcas.getRequiredFeatureDE(casType, "textualRepresentation", "uima.cas.String", featOkTst);
    casFeatCode_textualRepresentation  = (null == casFeat_textualRepresentation) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_textualRepresentation).getCode();

  }
}



    