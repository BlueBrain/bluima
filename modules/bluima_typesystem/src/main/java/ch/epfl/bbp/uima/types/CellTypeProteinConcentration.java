

/* First created by JCasGen Mon Feb 17 12:26:53 CET 2014 */
package ch.epfl.bbp.uima.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Wed Jun 04 18:01:55 CEST 2014
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/src/main/resources/typeSystem/bbp-types.xml
 * @generated */
public class CellTypeProteinConcentration extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(CellTypeProteinConcentration.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated  */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected CellTypeProteinConcentration() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public CellTypeProteinConcentration(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public CellTypeProteinConcentration(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public CellTypeProteinConcentration(JCas jcas, int begin, int end) {
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
  //* Feature: protein

  /** getter for protein - gets 
   * @generated
   * @return value of the feature 
   */
  public Protein getProtein() {
    if (CellTypeProteinConcentration_Type.featOkTst && ((CellTypeProteinConcentration_Type)jcasType).casFeat_protein == null)
      jcasType.jcas.throwFeatMissing("protein", "ch.epfl.bbp.uima.types.CellTypeProteinConcentration");
    return (Protein)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((CellTypeProteinConcentration_Type)jcasType).casFeatCode_protein)));}
    
  /** setter for protein - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setProtein(Protein v) {
    if (CellTypeProteinConcentration_Type.featOkTst && ((CellTypeProteinConcentration_Type)jcasType).casFeat_protein == null)
      jcasType.jcas.throwFeatMissing("protein", "ch.epfl.bbp.uima.types.CellTypeProteinConcentration");
    jcasType.ll_cas.ll_setRefValue(addr, ((CellTypeProteinConcentration_Type)jcasType).casFeatCode_protein, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: concentration

  /** getter for concentration - gets 
   * @generated
   * @return value of the feature 
   */
  public Concentration getConcentration() {
    if (CellTypeProteinConcentration_Type.featOkTst && ((CellTypeProteinConcentration_Type)jcasType).casFeat_concentration == null)
      jcasType.jcas.throwFeatMissing("concentration", "ch.epfl.bbp.uima.types.CellTypeProteinConcentration");
    return (Concentration)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((CellTypeProteinConcentration_Type)jcasType).casFeatCode_concentration)));}
    
  /** setter for concentration - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setConcentration(Concentration v) {
    if (CellTypeProteinConcentration_Type.featOkTst && ((CellTypeProteinConcentration_Type)jcasType).casFeat_concentration == null)
      jcasType.jcas.throwFeatMissing("concentration", "ch.epfl.bbp.uima.types.CellTypeProteinConcentration");
    jcasType.ll_cas.ll_setRefValue(addr, ((CellTypeProteinConcentration_Type)jcasType).casFeatCode_concentration, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: celltype

  /** getter for celltype - gets 
   * @generated
   * @return value of the feature 
   */
  public CellType getCelltype() {
    if (CellTypeProteinConcentration_Type.featOkTst && ((CellTypeProteinConcentration_Type)jcasType).casFeat_celltype == null)
      jcasType.jcas.throwFeatMissing("celltype", "ch.epfl.bbp.uima.types.CellTypeProteinConcentration");
    return (CellType)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((CellTypeProteinConcentration_Type)jcasType).casFeatCode_celltype)));}
    
  /** setter for celltype - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setCelltype(CellType v) {
    if (CellTypeProteinConcentration_Type.featOkTst && ((CellTypeProteinConcentration_Type)jcasType).casFeat_celltype == null)
      jcasType.jcas.throwFeatMissing("celltype", "ch.epfl.bbp.uima.types.CellTypeProteinConcentration");
    jcasType.ll_cas.ll_setRefValue(addr, ((CellTypeProteinConcentration_Type)jcasType).casFeatCode_celltype, jcasType.ll_cas.ll_getFSRef(v));}    
  }

    