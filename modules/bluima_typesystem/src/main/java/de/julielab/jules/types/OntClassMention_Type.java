
/* First created by JCasGen Sat Mar 07 22:05:57 CET 2015 */
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

/** ontology class mention
 * Updated by JCasGen Sat Mar 07 22:05:57 CET 2015
 * @generated */
public class OntClassMention_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (OntClassMention_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = OntClassMention_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new OntClassMention(addr, OntClassMention_Type.this);
  			   OntClassMention_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new OntClassMention(addr, OntClassMention_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = OntClassMention.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.julielab.jules.types.OntClassMention");
 
  /** @generated */
  final Feature casFeat_ontClassId;
  /** @generated */
  final int     casFeatCode_ontClassId;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getOntClassId(int addr) {
        if (featOkTst && casFeat_ontClassId == null)
      jcas.throwFeatMissing("ontClassId", "de.julielab.jules.types.OntClassMention");
    return ll_cas.ll_getStringValue(addr, casFeatCode_ontClassId);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setOntClassId(int addr, String v) {
        if (featOkTst && casFeat_ontClassId == null)
      jcas.throwFeatMissing("ontClassId", "de.julielab.jules.types.OntClassMention");
    ll_cas.ll_setStringValue(addr, casFeatCode_ontClassId, v);}
    
  
 
  /** @generated */
  final Feature casFeat_sourceOntology;
  /** @generated */
  final int     casFeatCode_sourceOntology;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getSourceOntology(int addr) {
        if (featOkTst && casFeat_sourceOntology == null)
      jcas.throwFeatMissing("sourceOntology", "de.julielab.jules.types.OntClassMention");
    return ll_cas.ll_getStringValue(addr, casFeatCode_sourceOntology);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setSourceOntology(int addr, String v) {
        if (featOkTst && casFeat_sourceOntology == null)
      jcas.throwFeatMissing("sourceOntology", "de.julielab.jules.types.OntClassMention");
    ll_cas.ll_setStringValue(addr, casFeatCode_sourceOntology, v);}
    
  
 
  /** @generated */
  final Feature casFeat_textualRepresentation;
  /** @generated */
  final int     casFeatCode_textualRepresentation;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getTextualRepresentation(int addr) {
        if (featOkTst && casFeat_textualRepresentation == null)
      jcas.throwFeatMissing("textualRepresentation", "de.julielab.jules.types.OntClassMention");
    return ll_cas.ll_getStringValue(addr, casFeatCode_textualRepresentation);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setTextualRepresentation(int addr, String v) {
        if (featOkTst && casFeat_textualRepresentation == null)
      jcas.throwFeatMissing("textualRepresentation", "de.julielab.jules.types.OntClassMention");
    ll_cas.ll_setStringValue(addr, casFeatCode_textualRepresentation, v);}
    
  
 
  /** @generated */
  final Feature casFeat_semanticTypes;
  /** @generated */
  final int     casFeatCode_semanticTypes;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getSemanticTypes(int addr) {
        if (featOkTst && casFeat_semanticTypes == null)
      jcas.throwFeatMissing("semanticTypes", "de.julielab.jules.types.OntClassMention");
    return ll_cas.ll_getRefValue(addr, casFeatCode_semanticTypes);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setSemanticTypes(int addr, int v) {
        if (featOkTst && casFeat_semanticTypes == null)
      jcas.throwFeatMissing("semanticTypes", "de.julielab.jules.types.OntClassMention");
    ll_cas.ll_setRefValue(addr, casFeatCode_semanticTypes, v);}
    
   /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @return value at index i in the array 
   */
  public String getSemanticTypes(int addr, int i) {
        if (featOkTst && casFeat_semanticTypes == null)
      jcas.throwFeatMissing("semanticTypes", "de.julielab.jules.types.OntClassMention");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_semanticTypes), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_semanticTypes), i);
	return ll_cas.ll_getStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_semanticTypes), i);
  }
   
  /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @param v value to set
   */ 
  public void setSemanticTypes(int addr, int i, String v) {
        if (featOkTst && casFeat_semanticTypes == null)
      jcas.throwFeatMissing("semanticTypes", "de.julielab.jules.types.OntClassMention");
    if (lowLevelTypeChecks)
      ll_cas.ll_setStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_semanticTypes), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_semanticTypes), i);
    ll_cas.ll_setStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_semanticTypes), i, v);
  }
 
 
  /** @generated */
  final Feature casFeat_preferredTerm;
  /** @generated */
  final int     casFeatCode_preferredTerm;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getPreferredTerm(int addr) {
        if (featOkTst && casFeat_preferredTerm == null)
      jcas.throwFeatMissing("preferredTerm", "de.julielab.jules.types.OntClassMention");
    return ll_cas.ll_getStringValue(addr, casFeatCode_preferredTerm);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setPreferredTerm(int addr, String v) {
        if (featOkTst && casFeat_preferredTerm == null)
      jcas.throwFeatMissing("preferredTerm", "de.julielab.jules.types.OntClassMention");
    ll_cas.ll_setStringValue(addr, casFeatCode_preferredTerm, v);}
    
  
 
  /** @generated */
  final Feature casFeat_matchedTokens;
  /** @generated */
  final int     casFeatCode_matchedTokens;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getMatchedTokens(int addr) {
        if (featOkTst && casFeat_matchedTokens == null)
      jcas.throwFeatMissing("matchedTokens", "de.julielab.jules.types.OntClassMention");
    return ll_cas.ll_getRefValue(addr, casFeatCode_matchedTokens);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setMatchedTokens(int addr, int v) {
        if (featOkTst && casFeat_matchedTokens == null)
      jcas.throwFeatMissing("matchedTokens", "de.julielab.jules.types.OntClassMention");
    ll_cas.ll_setRefValue(addr, casFeatCode_matchedTokens, v);}
    
   /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @return value at index i in the array 
   */
  public int getMatchedTokens(int addr, int i) {
        if (featOkTst && casFeat_matchedTokens == null)
      jcas.throwFeatMissing("matchedTokens", "de.julielab.jules.types.OntClassMention");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_matchedTokens), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_matchedTokens), i);
	return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_matchedTokens), i);
  }
   
  /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @param v value to set
   */ 
  public void setMatchedTokens(int addr, int i, int v) {
        if (featOkTst && casFeat_matchedTokens == null)
      jcas.throwFeatMissing("matchedTokens", "de.julielab.jules.types.OntClassMention");
    if (lowLevelTypeChecks)
      ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_matchedTokens), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_matchedTokens), i);
    ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_matchedTokens), i, v);
  }
 
 
  /** @generated */
  final Feature casFeat_matchedSourceTerm;
  /** @generated */
  final int     casFeatCode_matchedSourceTerm;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getMatchedSourceTerm(int addr) {
        if (featOkTst && casFeat_matchedSourceTerm == null)
      jcas.throwFeatMissing("matchedSourceTerm", "de.julielab.jules.types.OntClassMention");
    return ll_cas.ll_getStringValue(addr, casFeatCode_matchedSourceTerm);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setMatchedSourceTerm(int addr, String v) {
        if (featOkTst && casFeat_matchedSourceTerm == null)
      jcas.throwFeatMissing("matchedSourceTerm", "de.julielab.jules.types.OntClassMention");
    ll_cas.ll_setStringValue(addr, casFeatCode_matchedSourceTerm, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public OntClassMention_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_ontClassId = jcas.getRequiredFeatureDE(casType, "ontClassId", "uima.cas.String", featOkTst);
    casFeatCode_ontClassId  = (null == casFeat_ontClassId) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_ontClassId).getCode();

 
    casFeat_sourceOntology = jcas.getRequiredFeatureDE(casType, "sourceOntology", "uima.cas.String", featOkTst);
    casFeatCode_sourceOntology  = (null == casFeat_sourceOntology) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_sourceOntology).getCode();

 
    casFeat_textualRepresentation = jcas.getRequiredFeatureDE(casType, "textualRepresentation", "uima.cas.String", featOkTst);
    casFeatCode_textualRepresentation  = (null == casFeat_textualRepresentation) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_textualRepresentation).getCode();

 
    casFeat_semanticTypes = jcas.getRequiredFeatureDE(casType, "semanticTypes", "uima.cas.StringArray", featOkTst);
    casFeatCode_semanticTypes  = (null == casFeat_semanticTypes) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_semanticTypes).getCode();

 
    casFeat_preferredTerm = jcas.getRequiredFeatureDE(casType, "preferredTerm", "uima.cas.String", featOkTst);
    casFeatCode_preferredTerm  = (null == casFeat_preferredTerm) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_preferredTerm).getCode();

 
    casFeat_matchedTokens = jcas.getRequiredFeatureDE(casType, "matchedTokens", "uima.cas.FSArray", featOkTst);
    casFeatCode_matchedTokens  = (null == casFeat_matchedTokens) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_matchedTokens).getCode();

 
    casFeat_matchedSourceTerm = jcas.getRequiredFeatureDE(casType, "matchedSourceTerm", "uima.cas.String", featOkTst);
    casFeatCode_matchedSourceTerm  = (null == casFeat_matchedSourceTerm) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_matchedSourceTerm).getCode();

  }
}



    