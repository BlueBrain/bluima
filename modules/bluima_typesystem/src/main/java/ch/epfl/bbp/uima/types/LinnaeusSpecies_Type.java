
/* First created by JCasGen Mon Feb 17 12:27:27 CET 2014 */
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

/** Species annotation type holds information for Linnaeus species tagger's annotation.
 * Updated by JCasGen Mon Feb 17 12:27:27 CET 2014
 * @generated */
public class LinnaeusSpecies_Type extends Annotation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (LinnaeusSpecies_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = LinnaeusSpecies_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new LinnaeusSpecies(addr, LinnaeusSpecies_Type.this);
  			   LinnaeusSpecies_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new LinnaeusSpecies(addr, LinnaeusSpecies_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = LinnaeusSpecies.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("ch.epfl.bbp.uima.types.LinnaeusSpecies");
 
  /** @generated */
  final Feature casFeat_mostProbableSpeciesId;
  /** @generated */
  final int     casFeatCode_mostProbableSpeciesId;
  /** @generated */ 
  public String getMostProbableSpeciesId(int addr) {
        if (featOkTst && casFeat_mostProbableSpeciesId == null)
      jcas.throwFeatMissing("mostProbableSpeciesId", "ch.epfl.bbp.uima.types.LinnaeusSpecies");
    return ll_cas.ll_getStringValue(addr, casFeatCode_mostProbableSpeciesId);
  }
  /** @generated */    
  public void setMostProbableSpeciesId(int addr, String v) {
        if (featOkTst && casFeat_mostProbableSpeciesId == null)
      jcas.throwFeatMissing("mostProbableSpeciesId", "ch.epfl.bbp.uima.types.LinnaeusSpecies");
    ll_cas.ll_setStringValue(addr, casFeatCode_mostProbableSpeciesId, v);}
    
  
 
  /** @generated */
  final Feature casFeat_allIdsString;
  /** @generated */
  final int     casFeatCode_allIdsString;
  /** @generated */ 
  public String getAllIdsString(int addr) {
        if (featOkTst && casFeat_allIdsString == null)
      jcas.throwFeatMissing("allIdsString", "ch.epfl.bbp.uima.types.LinnaeusSpecies");
    return ll_cas.ll_getStringValue(addr, casFeatCode_allIdsString);
  }
  /** @generated */    
  public void setAllIdsString(int addr, String v) {
        if (featOkTst && casFeat_allIdsString == null)
      jcas.throwFeatMissing("allIdsString", "ch.epfl.bbp.uima.types.LinnaeusSpecies");
    ll_cas.ll_setStringValue(addr, casFeatCode_allIdsString, v);}
    
  
 
  /** @generated */
  final Feature casFeat_ambigous;
  /** @generated */
  final int     casFeatCode_ambigous;
  /** @generated */ 
  public boolean getAmbigous(int addr) {
        if (featOkTst && casFeat_ambigous == null)
      jcas.throwFeatMissing("ambigous", "ch.epfl.bbp.uima.types.LinnaeusSpecies");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_ambigous);
  }
  /** @generated */    
  public void setAmbigous(int addr, boolean v) {
        if (featOkTst && casFeat_ambigous == null)
      jcas.throwFeatMissing("ambigous", "ch.epfl.bbp.uima.types.LinnaeusSpecies");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_ambigous, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public LinnaeusSpecies_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_mostProbableSpeciesId = jcas.getRequiredFeatureDE(casType, "mostProbableSpeciesId", "uima.cas.String", featOkTst);
    casFeatCode_mostProbableSpeciesId  = (null == casFeat_mostProbableSpeciesId) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_mostProbableSpeciesId).getCode();

 
    casFeat_allIdsString = jcas.getRequiredFeatureDE(casType, "allIdsString", "uima.cas.String", featOkTst);
    casFeatCode_allIdsString  = (null == casFeat_allIdsString) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_allIdsString).getCode();

 
    casFeat_ambigous = jcas.getRequiredFeatureDE(casType, "ambigous", "uima.cas.Boolean", featOkTst);
    casFeatCode_ambigous  = (null == casFeat_ambigous) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_ambigous).getCode();

  }
}



    