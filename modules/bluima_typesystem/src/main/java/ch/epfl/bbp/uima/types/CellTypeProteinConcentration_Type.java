
/* First created by JCasGen Fri Sep 14 13:58:35 CEST 2012 */
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

/** 
 * Updated by JCasGen Mon Oct 21 13:03:29 CEST 2013
 * @generated */
public class CellTypeProteinConcentration_Type extends Annotation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (CellTypeProteinConcentration_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = CellTypeProteinConcentration_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new CellTypeProteinConcentration(addr, CellTypeProteinConcentration_Type.this);
  			   CellTypeProteinConcentration_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new CellTypeProteinConcentration(addr, CellTypeProteinConcentration_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = CellTypeProteinConcentration.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("ch.epfl.bbp.uima.types.CellTypeProteinConcentration");
 
  /** @generated */
  final Feature casFeat_protein;
  /** @generated */
  final int     casFeatCode_protein;
  /** @generated */ 
  public int getProtein(int addr) {
        if (featOkTst && casFeat_protein == null)
      jcas.throwFeatMissing("protein", "ch.epfl.bbp.uima.types.CellTypeProteinConcentration");
    return ll_cas.ll_getRefValue(addr, casFeatCode_protein);
  }
  /** @generated */    
  public void setProtein(int addr, int v) {
        if (featOkTst && casFeat_protein == null)
      jcas.throwFeatMissing("protein", "ch.epfl.bbp.uima.types.CellTypeProteinConcentration");
    ll_cas.ll_setRefValue(addr, casFeatCode_protein, v);}
    
  
 
  /** @generated */
  final Feature casFeat_concentration;
  /** @generated */
  final int     casFeatCode_concentration;
  /** @generated */ 
  public int getConcentration(int addr) {
        if (featOkTst && casFeat_concentration == null)
      jcas.throwFeatMissing("concentration", "ch.epfl.bbp.uima.types.CellTypeProteinConcentration");
    return ll_cas.ll_getRefValue(addr, casFeatCode_concentration);
  }
  /** @generated */    
  public void setConcentration(int addr, int v) {
        if (featOkTst && casFeat_concentration == null)
      jcas.throwFeatMissing("concentration", "ch.epfl.bbp.uima.types.CellTypeProteinConcentration");
    ll_cas.ll_setRefValue(addr, casFeatCode_concentration, v);}
    
  
 
  /** @generated */
  final Feature casFeat_celltype;
  /** @generated */
  final int     casFeatCode_celltype;
  /** @generated */ 
  public int getCelltype(int addr) {
        if (featOkTst && casFeat_celltype == null)
      jcas.throwFeatMissing("celltype", "ch.epfl.bbp.uima.types.CellTypeProteinConcentration");
    return ll_cas.ll_getRefValue(addr, casFeatCode_celltype);
  }
  /** @generated */    
  public void setCelltype(int addr, int v) {
        if (featOkTst && casFeat_celltype == null)
      jcas.throwFeatMissing("celltype", "ch.epfl.bbp.uima.types.CellTypeProteinConcentration");
    ll_cas.ll_setRefValue(addr, casFeatCode_celltype, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public CellTypeProteinConcentration_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_protein = jcas.getRequiredFeatureDE(casType, "protein", "ch.epfl.bbp.uima.types.Protein", featOkTst);
    casFeatCode_protein  = (null == casFeat_protein) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_protein).getCode();

 
    casFeat_concentration = jcas.getRequiredFeatureDE(casType, "concentration", "ch.epfl.bbp.uima.types.Concentration", featOkTst);
    casFeatCode_concentration  = (null == casFeat_concentration) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_concentration).getCode();

 
    casFeat_celltype = jcas.getRequiredFeatureDE(casType, "celltype", "ch.epfl.bbp.uima.types.CellType", featOkTst);
    casFeatCode_celltype  = (null == casFeat_celltype) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_celltype).getCode();

  }
}



    