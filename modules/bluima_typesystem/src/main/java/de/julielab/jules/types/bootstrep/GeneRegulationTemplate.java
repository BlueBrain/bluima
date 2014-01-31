

/* First created by JCasGen Wed Oct 19 19:12:42 CEST 2011 */
package de.julielab.jules.types.bootstrep;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import de.julielab.jules.types.Chemicals;
import de.julielab.jules.types.Annotation;


/** Gene Regulation Template for BootStrep
 * Updated by JCasGen Wed Oct 19 19:12:42 CEST 2011
 * XML source: /Users/ren/dev/bluebrain/svn_nlp/UIMA/julielab/trunk/julielab_typesystem-2.6.8/src/main/resources/typeSystem/julie-semantics-bootstrep-types.xml
 * @generated */
public class GeneRegulationTemplate extends Annotation {
  /** @generated
   * @ordered 
   */
  public final static int typeIndexID = JCasRegistry.register(GeneRegulationTemplate.class);
  /** @generated
   * @ordered 
   */
  public final static int type = typeIndexID;
  /** @generated  */
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected GeneRegulationTemplate() {}
    
  /** Internal - constructor used by generator 
   * @generated */
  public GeneRegulationTemplate(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public GeneRegulationTemplate(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public GeneRegulationTemplate(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** <!-- begin-user-doc -->
    * Write your own initialization here
    * <!-- end-user-doc -->
  @generated modifiable */
  private void readObject() {}
     
 
    
  //*--------------*
  //* Feature: relation

  /** getter for relation - gets mention of gene regulation relation
   * @generated */
  public RegulationOfGeneExpression getRelation() {
    if (GeneRegulationTemplate_Type.featOkTst && ((GeneRegulationTemplate_Type)jcasType).casFeat_relation == null)
      jcasType.jcas.throwFeatMissing("relation", "de.julielab.jules.types.bootstrep.GeneRegulationTemplate");
    return (RegulationOfGeneExpression)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((GeneRegulationTemplate_Type)jcasType).casFeatCode_relation)));}
    
  /** setter for relation - sets mention of gene regulation relation 
   * @generated */
  public void setRelation(RegulationOfGeneExpression v) {
    if (GeneRegulationTemplate_Type.featOkTst && ((GeneRegulationTemplate_Type)jcasType).casFeat_relation == null)
      jcasType.jcas.throwFeatMissing("relation", "de.julielab.jules.types.bootstrep.GeneRegulationTemplate");
    jcasType.ll_cas.ll_setRefValue(addr, ((GeneRegulationTemplate_Type)jcasType).casFeatCode_relation, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: ligand

  /** getter for ligand - gets chemical particpating in the gene regulation process
   * @generated */
  public Chemicals getLigand() {
    if (GeneRegulationTemplate_Type.featOkTst && ((GeneRegulationTemplate_Type)jcasType).casFeat_ligand == null)
      jcasType.jcas.throwFeatMissing("ligand", "de.julielab.jules.types.bootstrep.GeneRegulationTemplate");
    return (Chemicals)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((GeneRegulationTemplate_Type)jcasType).casFeatCode_ligand)));}
    
  /** setter for ligand - sets chemical particpating in the gene regulation process 
   * @generated */
  public void setLigand(Chemicals v) {
    if (GeneRegulationTemplate_Type.featOkTst && ((GeneRegulationTemplate_Type)jcasType).casFeat_ligand == null)
      jcasType.jcas.throwFeatMissing("ligand", "de.julielab.jules.types.bootstrep.GeneRegulationTemplate");
    jcasType.ll_cas.ll_setRefValue(addr, ((GeneRegulationTemplate_Type)jcasType).casFeatCode_ligand, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: cellGrowthCondition

  /** getter for cellGrowthCondition - gets cell growth condition
   * @generated */
  public CellGrowthCondition getCellGrowthCondition() {
    if (GeneRegulationTemplate_Type.featOkTst && ((GeneRegulationTemplate_Type)jcasType).casFeat_cellGrowthCondition == null)
      jcasType.jcas.throwFeatMissing("cellGrowthCondition", "de.julielab.jules.types.bootstrep.GeneRegulationTemplate");
    return (CellGrowthCondition)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((GeneRegulationTemplate_Type)jcasType).casFeatCode_cellGrowthCondition)));}
    
  /** setter for cellGrowthCondition - sets cell growth condition 
   * @generated */
  public void setCellGrowthCondition(CellGrowthCondition v) {
    if (GeneRegulationTemplate_Type.featOkTst && ((GeneRegulationTemplate_Type)jcasType).casFeat_cellGrowthCondition == null)
      jcasType.jcas.throwFeatMissing("cellGrowthCondition", "de.julielab.jules.types.bootstrep.GeneRegulationTemplate");
    jcasType.ll_cas.ll_setRefValue(addr, ((GeneRegulationTemplate_Type)jcasType).casFeatCode_cellGrowthCondition, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: investigationTechnique

  /** getter for investigationTechnique - gets investigation technique for the detection of the gene regulation relation
   * @generated */
  public InvestigationTechnique getInvestigationTechnique() {
    if (GeneRegulationTemplate_Type.featOkTst && ((GeneRegulationTemplate_Type)jcasType).casFeat_investigationTechnique == null)
      jcasType.jcas.throwFeatMissing("investigationTechnique", "de.julielab.jules.types.bootstrep.GeneRegulationTemplate");
    return (InvestigationTechnique)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((GeneRegulationTemplate_Type)jcasType).casFeatCode_investigationTechnique)));}
    
  /** setter for investigationTechnique - sets investigation technique for the detection of the gene regulation relation 
   * @generated */
  public void setInvestigationTechnique(InvestigationTechnique v) {
    if (GeneRegulationTemplate_Type.featOkTst && ((GeneRegulationTemplate_Type)jcasType).casFeat_investigationTechnique == null)
      jcasType.jcas.throwFeatMissing("investigationTechnique", "de.julielab.jules.types.bootstrep.GeneRegulationTemplate");
    jcasType.ll_cas.ll_setRefValue(addr, ((GeneRegulationTemplate_Type)jcasType).casFeatCode_investigationTechnique, jcasType.ll_cas.ll_getFSRef(v));}    
  }

    