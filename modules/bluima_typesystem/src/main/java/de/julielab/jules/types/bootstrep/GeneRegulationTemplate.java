

/* First created by JCasGen Sat Mar 07 22:05:57 CET 2015 */
package de.julielab.jules.types.bootstrep;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import de.julielab.jules.types.Chemicals;
import de.julielab.jules.types.Annotation;


/** Gene Regulation Template for BootStrep
 * Updated by JCasGen Sat Mar 07 22:05:57 CET 2015
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/target/jcasgen/typesystem.xml
 * @generated */
public class GeneRegulationTemplate extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(GeneRegulationTemplate.class);
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
  protected GeneRegulationTemplate() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public GeneRegulationTemplate(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public GeneRegulationTemplate(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public GeneRegulationTemplate(JCas jcas, int begin, int end) {
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
  //* Feature: relation

  /** getter for relation - gets mention of gene regulation relation
   * @generated
   * @return value of the feature 
   */
  public RegulationOfGeneExpression getRelation() {
    if (GeneRegulationTemplate_Type.featOkTst && ((GeneRegulationTemplate_Type)jcasType).casFeat_relation == null)
      jcasType.jcas.throwFeatMissing("relation", "de.julielab.jules.types.bootstrep.GeneRegulationTemplate");
    return (RegulationOfGeneExpression)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((GeneRegulationTemplate_Type)jcasType).casFeatCode_relation)));}
    
  /** setter for relation - sets mention of gene regulation relation 
   * @generated
   * @param v value to set into the feature 
   */
  public void setRelation(RegulationOfGeneExpression v) {
    if (GeneRegulationTemplate_Type.featOkTst && ((GeneRegulationTemplate_Type)jcasType).casFeat_relation == null)
      jcasType.jcas.throwFeatMissing("relation", "de.julielab.jules.types.bootstrep.GeneRegulationTemplate");
    jcasType.ll_cas.ll_setRefValue(addr, ((GeneRegulationTemplate_Type)jcasType).casFeatCode_relation, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: ligand

  /** getter for ligand - gets chemical particpating in the gene regulation process
   * @generated
   * @return value of the feature 
   */
  public Chemicals getLigand() {
    if (GeneRegulationTemplate_Type.featOkTst && ((GeneRegulationTemplate_Type)jcasType).casFeat_ligand == null)
      jcasType.jcas.throwFeatMissing("ligand", "de.julielab.jules.types.bootstrep.GeneRegulationTemplate");
    return (Chemicals)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((GeneRegulationTemplate_Type)jcasType).casFeatCode_ligand)));}
    
  /** setter for ligand - sets chemical particpating in the gene regulation process 
   * @generated
   * @param v value to set into the feature 
   */
  public void setLigand(Chemicals v) {
    if (GeneRegulationTemplate_Type.featOkTst && ((GeneRegulationTemplate_Type)jcasType).casFeat_ligand == null)
      jcasType.jcas.throwFeatMissing("ligand", "de.julielab.jules.types.bootstrep.GeneRegulationTemplate");
    jcasType.ll_cas.ll_setRefValue(addr, ((GeneRegulationTemplate_Type)jcasType).casFeatCode_ligand, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: cellGrowthCondition

  /** getter for cellGrowthCondition - gets cell growth condition
   * @generated
   * @return value of the feature 
   */
  public CellGrowthCondition getCellGrowthCondition() {
    if (GeneRegulationTemplate_Type.featOkTst && ((GeneRegulationTemplate_Type)jcasType).casFeat_cellGrowthCondition == null)
      jcasType.jcas.throwFeatMissing("cellGrowthCondition", "de.julielab.jules.types.bootstrep.GeneRegulationTemplate");
    return (CellGrowthCondition)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((GeneRegulationTemplate_Type)jcasType).casFeatCode_cellGrowthCondition)));}
    
  /** setter for cellGrowthCondition - sets cell growth condition 
   * @generated
   * @param v value to set into the feature 
   */
  public void setCellGrowthCondition(CellGrowthCondition v) {
    if (GeneRegulationTemplate_Type.featOkTst && ((GeneRegulationTemplate_Type)jcasType).casFeat_cellGrowthCondition == null)
      jcasType.jcas.throwFeatMissing("cellGrowthCondition", "de.julielab.jules.types.bootstrep.GeneRegulationTemplate");
    jcasType.ll_cas.ll_setRefValue(addr, ((GeneRegulationTemplate_Type)jcasType).casFeatCode_cellGrowthCondition, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: investigationTechnique

  /** getter for investigationTechnique - gets investigation technique for the detection of the gene regulation relation
   * @generated
   * @return value of the feature 
   */
  public InvestigationTechnique getInvestigationTechnique() {
    if (GeneRegulationTemplate_Type.featOkTst && ((GeneRegulationTemplate_Type)jcasType).casFeat_investigationTechnique == null)
      jcasType.jcas.throwFeatMissing("investigationTechnique", "de.julielab.jules.types.bootstrep.GeneRegulationTemplate");
    return (InvestigationTechnique)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((GeneRegulationTemplate_Type)jcasType).casFeatCode_investigationTechnique)));}
    
  /** setter for investigationTechnique - sets investigation technique for the detection of the gene regulation relation 
   * @generated
   * @param v value to set into the feature 
   */
  public void setInvestigationTechnique(InvestigationTechnique v) {
    if (GeneRegulationTemplate_Type.featOkTst && ((GeneRegulationTemplate_Type)jcasType).casFeat_investigationTechnique == null)
      jcasType.jcas.throwFeatMissing("investigationTechnique", "de.julielab.jules.types.bootstrep.GeneRegulationTemplate");
    jcasType.ll_cas.ll_setRefValue(addr, ((GeneRegulationTemplate_Type)jcasType).casFeatCode_investigationTechnique, jcasType.ll_cas.ll_getFSRef(v));}    
  }

    