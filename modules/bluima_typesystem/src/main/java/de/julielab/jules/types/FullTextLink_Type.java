
/* First created by JCasGen Wed Oct 19 19:10:41 CEST 2011 */
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

/** 
 * Updated by JCasGen Fri Dec 09 11:59:26 CET 2011
 * @generated */
public class FullTextLink_Type extends Annotation_Type {
  /** @generated */
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (FullTextLink_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = FullTextLink_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new FullTextLink(addr, FullTextLink_Type.this);
  			   FullTextLink_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new FullTextLink(addr, FullTextLink_Type.this);
  	  }
    };
  /** @generated */
  public final static int typeIndexID = FullTextLink.typeIndexID;
  /** @generated 
     @modifiable */
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.julielab.jules.types.FullTextLink");
 
  /** @generated */
  final Feature casFeat_url;
  /** @generated */
  final int     casFeatCode_url;
  /** @generated */ 
  public String getUrl(int addr) {
        if (featOkTst && casFeat_url == null)
      jcas.throwFeatMissing("url", "de.julielab.jules.types.FullTextLink");
    return ll_cas.ll_getStringValue(addr, casFeatCode_url);
  }
  /** @generated */    
  public void setUrl(int addr, String v) {
        if (featOkTst && casFeat_url == null)
      jcas.throwFeatMissing("url", "de.julielab.jules.types.FullTextLink");
    ll_cas.ll_setStringValue(addr, casFeatCode_url, v);}
    
  
 
  /** @generated */
  final Feature casFeat_iconUrl;
  /** @generated */
  final int     casFeatCode_iconUrl;
  /** @generated */ 
  public String getIconUrl(int addr) {
        if (featOkTst && casFeat_iconUrl == null)
      jcas.throwFeatMissing("iconUrl", "de.julielab.jules.types.FullTextLink");
    return ll_cas.ll_getStringValue(addr, casFeatCode_iconUrl);
  }
  /** @generated */    
  public void setIconUrl(int addr, String v) {
        if (featOkTst && casFeat_iconUrl == null)
      jcas.throwFeatMissing("iconUrl", "de.julielab.jules.types.FullTextLink");
    ll_cas.ll_setStringValue(addr, casFeatCode_iconUrl, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public FullTextLink_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_url = jcas.getRequiredFeatureDE(casType, "url", "uima.cas.String", featOkTst);
    casFeatCode_url  = (null == casFeat_url) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_url).getCode();

 
    casFeat_iconUrl = jcas.getRequiredFeatureDE(casType, "iconUrl", "uima.cas.String", featOkTst);
    casFeatCode_iconUrl  = (null == casFeat_iconUrl) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_iconUrl).getCode();

  }
}



    