
/* First created by JCasGen Sat Mar 07 22:05:57 CET 2015 */
package de.julielab.jules.types;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;

/** Ohta, Tomoko, Yuka Tateisi, Hideki Mima and Jun'ichi Tsujii. (2002). GENIA Corpus: an Annotated Research Abstract Corpus in Molecular Biology Domain. In the Proceedings of he Human Language Technology Conference (HLT 2002). pp73--77.
 * Updated by JCasGen Sat Mar 07 22:05:57 CET 2015
 * @generated */
public class GENIAConstituent_Type extends PTBConstituent_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (GENIAConstituent_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = GENIAConstituent_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new GENIAConstituent(addr, GENIAConstituent_Type.this);
  			   GENIAConstituent_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new GENIAConstituent(addr, GENIAConstituent_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = GENIAConstituent.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.julielab.jules.types.GENIAConstituent");
 
  /** @generated */
  final Feature casFeat_syn;
  /** @generated */
  final int     casFeatCode_syn;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getSyn(int addr) {
        if (featOkTst && casFeat_syn == null)
      jcas.throwFeatMissing("syn", "de.julielab.jules.types.GENIAConstituent");
    return ll_cas.ll_getStringValue(addr, casFeatCode_syn);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setSyn(int addr, String v) {
        if (featOkTst && casFeat_syn == null)
      jcas.throwFeatMissing("syn", "de.julielab.jules.types.GENIAConstituent");
    ll_cas.ll_setStringValue(addr, casFeatCode_syn, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public GENIAConstituent_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_syn = jcas.getRequiredFeatureDE(casType, "syn", "uima.cas.String", featOkTst);
    casFeatCode_syn  = (null == casFeat_syn) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_syn).getCode();

  }
}



    