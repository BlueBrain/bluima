
/* First created by JCasGen Wed Oct 19 19:12:42 CEST 2011 */
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
import de.julielab.jules.types.BioRelationMention_Type;

/** specificType: positive, negative, unspecified
contains regulation of gene transcription as well
 * Updated by JCasGen Wed Oct 19 19:12:42 CEST 2011
 * @generated */
public class RegulationOfGeneExpression_Type extends BioRelationMention_Type {
  /** @generated */
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (RegulationOfGeneExpression_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = RegulationOfGeneExpression_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new RegulationOfGeneExpression(addr, RegulationOfGeneExpression_Type.this);
  			   RegulationOfGeneExpression_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new RegulationOfGeneExpression(addr, RegulationOfGeneExpression_Type.this);
  	  }
    };
  /** @generated */
  public final static int typeIndexID = RegulationOfGeneExpression.typeIndexID;
  /** @generated 
     @modifiable */
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.julielab.jules.types.bootstrep.RegulationOfGeneExpression");
 
  /** @generated */
  final Feature casFeat_physicalContact;
  /** @generated */
  final int     casFeatCode_physicalContact;
  /** @generated */ 
  public String getPhysicalContact(int addr) {
        if (featOkTst && casFeat_physicalContact == null)
      jcas.throwFeatMissing("physicalContact", "de.julielab.jules.types.bootstrep.RegulationOfGeneExpression");
    return ll_cas.ll_getStringValue(addr, casFeatCode_physicalContact);
  }
  /** @generated */    
  public void setPhysicalContact(int addr, String v) {
        if (featOkTst && casFeat_physicalContact == null)
      jcas.throwFeatMissing("physicalContact", "de.julielab.jules.types.bootstrep.RegulationOfGeneExpression");
    ll_cas.ll_setStringValue(addr, casFeatCode_physicalContact, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public RegulationOfGeneExpression_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_physicalContact = jcas.getRequiredFeatureDE(casType, "physicalContact", "uima.cas.String", featOkTst);
    casFeatCode_physicalContact  = (null == casFeat_physicalContact) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_physicalContact).getCode();

  }
}



    