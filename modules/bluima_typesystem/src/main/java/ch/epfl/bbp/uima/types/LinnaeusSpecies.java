

/* First created by JCasGen Sat Mar 07 22:05:56 CET 2015 */
package ch.epfl.bbp.uima.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** Species annotation type holds information for Linnaeus species tagger's annotation.
 * Updated by JCasGen Sat Mar 07 22:05:56 CET 2015
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/target/jcasgen/typesystem.xml
 * @generated */
public class LinnaeusSpecies extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(LinnaeusSpecies.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated
   * @return index of the type  
   */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected LinnaeusSpecies() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public LinnaeusSpecies(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public LinnaeusSpecies(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public LinnaeusSpecies(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** 
   * <!-- begin-user-doc -->
   * Write your own initialization here
   * <!-- end-user-doc -->
   *
   * @generated modifiable 
   */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: mostProbableSpeciesId

  /** getter for mostProbableSpeciesId - gets This feature contains the value of the most probable NCBI Taxonomy Id for the annotated species occurence.
   * @generated
   * @return value of the feature 
   */
  public String getMostProbableSpeciesId() {
    if (LinnaeusSpecies_Type.featOkTst && ((LinnaeusSpecies_Type)jcasType).casFeat_mostProbableSpeciesId == null)
      jcasType.jcas.throwFeatMissing("mostProbableSpeciesId", "ch.epfl.bbp.uima.types.LinnaeusSpecies");
    return jcasType.ll_cas.ll_getStringValue(addr, ((LinnaeusSpecies_Type)jcasType).casFeatCode_mostProbableSpeciesId);}
    
  /** setter for mostProbableSpeciesId - sets This feature contains the value of the most probable NCBI Taxonomy Id for the annotated species occurence. 
   * @generated
   * @param v value to set into the feature 
   */
  public void setMostProbableSpeciesId(String v) {
    if (LinnaeusSpecies_Type.featOkTst && ((LinnaeusSpecies_Type)jcasType).casFeat_mostProbableSpeciesId == null)
      jcasType.jcas.throwFeatMissing("mostProbableSpeciesId", "ch.epfl.bbp.uima.types.LinnaeusSpecies");
    jcasType.ll_cas.ll_setStringValue(addr, ((LinnaeusSpecies_Type)jcasType).casFeatCode_mostProbableSpeciesId, v);}    
   
    
  //*--------------*
  //* Feature: allIdsString

  /** getter for allIdsString - gets This feature contains all possible NCBI Taxonomy IDs.
   * @generated
   * @return value of the feature 
   */
  public String getAllIdsString() {
    if (LinnaeusSpecies_Type.featOkTst && ((LinnaeusSpecies_Type)jcasType).casFeat_allIdsString == null)
      jcasType.jcas.throwFeatMissing("allIdsString", "ch.epfl.bbp.uima.types.LinnaeusSpecies");
    return jcasType.ll_cas.ll_getStringValue(addr, ((LinnaeusSpecies_Type)jcasType).casFeatCode_allIdsString);}
    
  /** setter for allIdsString - sets This feature contains all possible NCBI Taxonomy IDs. 
   * @generated
   * @param v value to set into the feature 
   */
  public void setAllIdsString(String v) {
    if (LinnaeusSpecies_Type.featOkTst && ((LinnaeusSpecies_Type)jcasType).casFeat_allIdsString == null)
      jcasType.jcas.throwFeatMissing("allIdsString", "ch.epfl.bbp.uima.types.LinnaeusSpecies");
    jcasType.ll_cas.ll_setStringValue(addr, ((LinnaeusSpecies_Type)jcasType).casFeatCode_allIdsString, v);}    
   
    
  //*--------------*
  //* Feature: ambigous

  /** getter for ambigous - gets True if the species tagging is ambigous.
   * @generated
   * @return value of the feature 
   */
  public boolean getAmbigous() {
    if (LinnaeusSpecies_Type.featOkTst && ((LinnaeusSpecies_Type)jcasType).casFeat_ambigous == null)
      jcasType.jcas.throwFeatMissing("ambigous", "ch.epfl.bbp.uima.types.LinnaeusSpecies");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((LinnaeusSpecies_Type)jcasType).casFeatCode_ambigous);}
    
  /** setter for ambigous - sets True if the species tagging is ambigous. 
   * @generated
   * @param v value to set into the feature 
   */
  public void setAmbigous(boolean v) {
    if (LinnaeusSpecies_Type.featOkTst && ((LinnaeusSpecies_Type)jcasType).casFeat_ambigous == null)
      jcasType.jcas.throwFeatMissing("ambigous", "ch.epfl.bbp.uima.types.LinnaeusSpecies");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((LinnaeusSpecies_Type)jcasType).casFeatCode_ambigous, v);}    
  }

    