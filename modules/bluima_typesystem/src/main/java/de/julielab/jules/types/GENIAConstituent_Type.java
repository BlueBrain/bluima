
/* First created by JCasGen Wed Oct 19 19:10:28 CEST 2011 */
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
 * Updated by JCasGen Wed Jun 04 18:01:58 CEST 2014
 * @generated */
public class GENIAConstituent_Type extends PTBConstituent_Type {
  /** @generated */
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
  public final static int typeIndexID = GENIAConstituent.typeIndexID;
  /** @generated 
     @modifiable */
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.julielab.jules.types.GENIAConstituent");
 
  /** @generated */
  final Feature casFeat_syn;
  /** @generated */
  final int     casFeatCode_syn;
  /** @generated */ 
  public String getSyn(int addr) {
        if (featOkTst && casFeat_syn == null)
      jcas.throwFeatMissing("syn", "de.julielab.jules.types.GENIAConstituent");
    return ll_cas.ll_getStringValue(addr, casFeatCode_syn);
  }
  /** @generated */    
  public void setSyn(int addr, String v) {
        if (featOkTst && casFeat_syn == null)
      jcas.throwFeatMissing("syn", "de.julielab.jules.types.GENIAConstituent");
    ll_cas.ll_setStringValue(addr, casFeatCode_syn, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public GENIAConstituent_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_syn = jcas.getRequiredFeatureDE(casType, "syn", "uima.cas.String", featOkTst);
    casFeatCode_syn  = (null == casFeat_syn) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_syn).getCode();

  }
}



    