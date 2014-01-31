

/* First created by JCasGen Fri Sep 14 13:58:35 CEST 2012 */
package ch.epfl.bbp.uima.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Mon Oct 21 13:03:29 CEST 2013
 * XML source: /Users/richarde/dev/bluebrain/svn_nlp/UIMA/blue_uima/trunk/modules/julielab_typesystem-2.6.8/src/main/resources/typeSystem/bbp-semantics-biology-types.xml
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

  /** <!-- begin-user-doc -->
    * Write your own initialization here
    * <!-- end-user-doc -->
  @generated modifiable */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: protein

  /** getter for protein - gets 
   * @generated */
  public Protein getProtein() {
    if (CellTypeProteinConcentration_Type.featOkTst && ((CellTypeProteinConcentration_Type)jcasType).casFeat_protein == null)
      jcasType.jcas.throwFeatMissing("protein", "ch.epfl.bbp.uima.types.CellTypeProteinConcentration");
    return (Protein)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((CellTypeProteinConcentration_Type)jcasType).casFeatCode_protein)));}
    
  /** setter for protein - sets  
   * @generated */
  public void setProtein(Protein v) {
    if (CellTypeProteinConcentration_Type.featOkTst && ((CellTypeProteinConcentration_Type)jcasType).casFeat_protein == null)
      jcasType.jcas.throwFeatMissing("protein", "ch.epfl.bbp.uima.types.CellTypeProteinConcentration");
    jcasType.ll_cas.ll_setRefValue(addr, ((CellTypeProteinConcentration_Type)jcasType).casFeatCode_protein, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: concentration

  /** getter for concentration - gets 
   * @generated */
  public Concentration getConcentration() {
    if (CellTypeProteinConcentration_Type.featOkTst && ((CellTypeProteinConcentration_Type)jcasType).casFeat_concentration == null)
      jcasType.jcas.throwFeatMissing("concentration", "ch.epfl.bbp.uima.types.CellTypeProteinConcentration");
    return (Concentration)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((CellTypeProteinConcentration_Type)jcasType).casFeatCode_concentration)));}
    
  /** setter for concentration - sets  
   * @generated */
  public void setConcentration(Concentration v) {
    if (CellTypeProteinConcentration_Type.featOkTst && ((CellTypeProteinConcentration_Type)jcasType).casFeat_concentration == null)
      jcasType.jcas.throwFeatMissing("concentration", "ch.epfl.bbp.uima.types.CellTypeProteinConcentration");
    jcasType.ll_cas.ll_setRefValue(addr, ((CellTypeProteinConcentration_Type)jcasType).casFeatCode_concentration, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: celltype

  /** getter for celltype - gets 
   * @generated */
  public CellType getCelltype() {
    if (CellTypeProteinConcentration_Type.featOkTst && ((CellTypeProteinConcentration_Type)jcasType).casFeat_celltype == null)
      jcasType.jcas.throwFeatMissing("celltype", "ch.epfl.bbp.uima.types.CellTypeProteinConcentration");
    return (CellType)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((CellTypeProteinConcentration_Type)jcasType).casFeatCode_celltype)));}
    
  /** setter for celltype - sets  
   * @generated */
  public void setCelltype(CellType v) {
    if (CellTypeProteinConcentration_Type.featOkTst && ((CellTypeProteinConcentration_Type)jcasType).casFeat_celltype == null)
      jcasType.jcas.throwFeatMissing("celltype", "ch.epfl.bbp.uima.types.CellTypeProteinConcentration");
    jcasType.ll_cas.ll_setRefValue(addr, ((CellTypeProteinConcentration_Type)jcasType).casFeatCode_celltype, jcasType.ll_cas.ll_getFSRef(v));}    
  }

    