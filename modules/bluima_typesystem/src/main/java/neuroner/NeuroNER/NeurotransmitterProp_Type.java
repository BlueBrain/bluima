
/* First created by JCasGen Tue May 13 16:25:02 CEST 2014 */
package neuroner.NeuroNER;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;

import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;

/** Type defined in neuroner.NeuroNER
 * Updated by JCasGen Tue Jun 09 19:55:09 CEST 2015
 * @generated */
public class NeurotransmitterProp_Type extends NeuronProperty_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (NeurotransmitterProp_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = NeurotransmitterProp_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new NeurotransmitterProp(addr, NeurotransmitterProp_Type.this);
  			   NeurotransmitterProp_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new NeurotransmitterProp(addr, NeurotransmitterProp_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = NeurotransmitterProp.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("neuroner.NeuroNER.NeurotransmitterProp");



  /** @generated */
  final Feature casFeat_name;
  /** @generated */
  final int     casFeatCode_name;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getName(int addr) {
        if (featOkTst && casFeat_name == null)
      jcas.throwFeatMissing("name", "neuroner.NeuroNER.NeurotransmitterProp");
    return ll_cas.ll_getStringValue(addr, casFeatCode_name);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setName(int addr, String v) {
        if (featOkTst && casFeat_name == null)
      jcas.throwFeatMissing("name", "neuroner.NeuroNER.NeurotransmitterProp");
    ll_cas.ll_setStringValue(addr, casFeatCode_name, v);}
    
  
 
  /** @generated */
  final Feature casFeat_ontologyId;
  /** @generated */
  final int     casFeatCode_ontologyId;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getOntologyId(int addr) {
        if (featOkTst && casFeat_ontologyId == null)
      jcas.throwFeatMissing("ontologyId", "neuroner.NeuroNER.NeurotransmitterProp");
    return ll_cas.ll_getStringValue(addr, casFeatCode_ontologyId);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setOntologyId(int addr, String v) {
        if (featOkTst && casFeat_ontologyId == null)
      jcas.throwFeatMissing("ontologyId", "neuroner.NeuroNER.NeurotransmitterProp");
    ll_cas.ll_setStringValue(addr, casFeatCode_ontologyId, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public NeurotransmitterProp_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_name = jcas.getRequiredFeatureDE(casType, "name", "uima.cas.String", featOkTst);
    casFeatCode_name  = (null == casFeat_name) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_name).getCode();

 
    casFeat_ontologyId = jcas.getRequiredFeatureDE(casType, "ontologyId", "uima.cas.String", featOkTst);
    casFeatCode_ontologyId  = (null == casFeat_ontologyId) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_ontologyId).getCode();

  }
}



    