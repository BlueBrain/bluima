
/* First created by JCasGen Sat Mar 07 22:05:57 CET 2015 */
package de.julielab.jules.types.pubmed;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;

/** The special Header for PubMed  (http://www.pubmed.org) documents
 * Updated by JCasGen Sat Mar 07 22:05:57 CET 2015
 * @generated */
public class Header_Type extends de.julielab.jules.types.Header_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Header_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Header_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Header(addr, Header_Type.this);
  			   Header_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Header(addr, Header_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Header.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.julielab.jules.types.pubmed.Header");
 
  /** @generated */
  final Feature casFeat_citationStatus;
  /** @generated */
  final int     casFeatCode_citationStatus;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getCitationStatus(int addr) {
        if (featOkTst && casFeat_citationStatus == null)
      jcas.throwFeatMissing("citationStatus", "de.julielab.jules.types.pubmed.Header");
    return ll_cas.ll_getStringValue(addr, casFeatCode_citationStatus);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setCitationStatus(int addr, String v) {
        if (featOkTst && casFeat_citationStatus == null)
      jcas.throwFeatMissing("citationStatus", "de.julielab.jules.types.pubmed.Header");
    ll_cas.ll_setStringValue(addr, casFeatCode_citationStatus, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public Header_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_citationStatus = jcas.getRequiredFeatureDE(casType, "citationStatus", "de.julielab.jules.types.CitationStatus", featOkTst);
    casFeatCode_citationStatus  = (null == casFeat_citationStatus) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_citationStatus).getCode();

  }
}



    