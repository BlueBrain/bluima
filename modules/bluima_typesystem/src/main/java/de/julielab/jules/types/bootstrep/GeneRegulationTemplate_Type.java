
/* First created by JCasGen Sat Mar 07 22:05:57 CET 2015 */
package de.julielab.jules.types.bootstrep;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import de.julielab.jules.types.Annotation_Type;

/** Gene Regulation Template for BootStrep
 * Updated by JCasGen Sat Mar 07 22:05:57 CET 2015
 * @generated */
public class GeneRegulationTemplate_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (GeneRegulationTemplate_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = GeneRegulationTemplate_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new GeneRegulationTemplate(addr, GeneRegulationTemplate_Type.this);
  			   GeneRegulationTemplate_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new GeneRegulationTemplate(addr, GeneRegulationTemplate_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = GeneRegulationTemplate.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.julielab.jules.types.bootstrep.GeneRegulationTemplate");
 
  /** @generated */
  final Feature casFeat_relation;
  /** @generated */
  final int     casFeatCode_relation;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getRelation(int addr) {
        if (featOkTst && casFeat_relation == null)
      jcas.throwFeatMissing("relation", "de.julielab.jules.types.bootstrep.GeneRegulationTemplate");
    return ll_cas.ll_getRefValue(addr, casFeatCode_relation);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setRelation(int addr, int v) {
        if (featOkTst && casFeat_relation == null)
      jcas.throwFeatMissing("relation", "de.julielab.jules.types.bootstrep.GeneRegulationTemplate");
    ll_cas.ll_setRefValue(addr, casFeatCode_relation, v);}
    
  
 
  /** @generated */
  final Feature casFeat_ligand;
  /** @generated */
  final int     casFeatCode_ligand;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getLigand(int addr) {
        if (featOkTst && casFeat_ligand == null)
      jcas.throwFeatMissing("ligand", "de.julielab.jules.types.bootstrep.GeneRegulationTemplate");
    return ll_cas.ll_getRefValue(addr, casFeatCode_ligand);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setLigand(int addr, int v) {
        if (featOkTst && casFeat_ligand == null)
      jcas.throwFeatMissing("ligand", "de.julielab.jules.types.bootstrep.GeneRegulationTemplate");
    ll_cas.ll_setRefValue(addr, casFeatCode_ligand, v);}
    
  
 
  /** @generated */
  final Feature casFeat_cellGrowthCondition;
  /** @generated */
  final int     casFeatCode_cellGrowthCondition;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getCellGrowthCondition(int addr) {
        if (featOkTst && casFeat_cellGrowthCondition == null)
      jcas.throwFeatMissing("cellGrowthCondition", "de.julielab.jules.types.bootstrep.GeneRegulationTemplate");
    return ll_cas.ll_getRefValue(addr, casFeatCode_cellGrowthCondition);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setCellGrowthCondition(int addr, int v) {
        if (featOkTst && casFeat_cellGrowthCondition == null)
      jcas.throwFeatMissing("cellGrowthCondition", "de.julielab.jules.types.bootstrep.GeneRegulationTemplate");
    ll_cas.ll_setRefValue(addr, casFeatCode_cellGrowthCondition, v);}
    
  
 
  /** @generated */
  final Feature casFeat_investigationTechnique;
  /** @generated */
  final int     casFeatCode_investigationTechnique;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getInvestigationTechnique(int addr) {
        if (featOkTst && casFeat_investigationTechnique == null)
      jcas.throwFeatMissing("investigationTechnique", "de.julielab.jules.types.bootstrep.GeneRegulationTemplate");
    return ll_cas.ll_getRefValue(addr, casFeatCode_investigationTechnique);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setInvestigationTechnique(int addr, int v) {
        if (featOkTst && casFeat_investigationTechnique == null)
      jcas.throwFeatMissing("investigationTechnique", "de.julielab.jules.types.bootstrep.GeneRegulationTemplate");
    ll_cas.ll_setRefValue(addr, casFeatCode_investigationTechnique, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public GeneRegulationTemplate_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_relation = jcas.getRequiredFeatureDE(casType, "relation", "de.julielab.jules.types.bootstrep.RegulationOfGeneExpression", featOkTst);
    casFeatCode_relation  = (null == casFeat_relation) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_relation).getCode();

 
    casFeat_ligand = jcas.getRequiredFeatureDE(casType, "ligand", "de.julielab.jules.types.Chemicals", featOkTst);
    casFeatCode_ligand  = (null == casFeat_ligand) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_ligand).getCode();

 
    casFeat_cellGrowthCondition = jcas.getRequiredFeatureDE(casType, "cellGrowthCondition", "de.julielab.jules.types.bootstrep.CellGrowthCondition", featOkTst);
    casFeatCode_cellGrowthCondition  = (null == casFeat_cellGrowthCondition) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_cellGrowthCondition).getCode();

 
    casFeat_investigationTechnique = jcas.getRequiredFeatureDE(casType, "investigationTechnique", "de.julielab.jules.types.bootstrep.InvestigationTechnique", featOkTst);
    casFeatCode_investigationTechnique  = (null == casFeat_investigationTechnique) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_investigationTechnique).getCode();

  }
}



    