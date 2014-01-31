
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

/** An abstract type which should be used to store information on the publication. See subtypes Journal and an accumulative type (OtherPub)
 * Updated by JCasGen Fri Dec 09 11:59:26 CET 2011
 * @generated */
public class PubType_Type extends Annotation_Type {
  /** @generated */
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (PubType_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = PubType_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new PubType(addr, PubType_Type.this);
  			   PubType_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new PubType(addr, PubType_Type.this);
  	  }
    };
  /** @generated */
  public final static int typeIndexID = PubType.typeIndexID;
  /** @generated 
     @modifiable */
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.julielab.jules.types.PubType");
 
  /** @generated */
  final Feature casFeat_name;
  /** @generated */
  final int     casFeatCode_name;
  /** @generated */ 
  public String getName(int addr) {
        if (featOkTst && casFeat_name == null)
      jcas.throwFeatMissing("name", "de.julielab.jules.types.PubType");
    return ll_cas.ll_getStringValue(addr, casFeatCode_name);
  }
  /** @generated */    
  public void setName(int addr, String v) {
        if (featOkTst && casFeat_name == null)
      jcas.throwFeatMissing("name", "de.julielab.jules.types.PubType");
    ll_cas.ll_setStringValue(addr, casFeatCode_name, v);}
    
  
 
  /** @generated */
  final Feature casFeat_pubDate;
  /** @generated */
  final int     casFeatCode_pubDate;
  /** @generated */ 
  public int getPubDate(int addr) {
        if (featOkTst && casFeat_pubDate == null)
      jcas.throwFeatMissing("pubDate", "de.julielab.jules.types.PubType");
    return ll_cas.ll_getRefValue(addr, casFeatCode_pubDate);
  }
  /** @generated */    
  public void setPubDate(int addr, int v) {
        if (featOkTst && casFeat_pubDate == null)
      jcas.throwFeatMissing("pubDate", "de.julielab.jules.types.PubType");
    ll_cas.ll_setRefValue(addr, casFeatCode_pubDate, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public PubType_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_name = jcas.getRequiredFeatureDE(casType, "name", "uima.cas.String", featOkTst);
    casFeatCode_name  = (null == casFeat_name) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_name).getCode();

 
    casFeat_pubDate = jcas.getRequiredFeatureDE(casType, "pubDate", "de.julielab.jules.types.Date", featOkTst);
    casFeatCode_pubDate  = (null == casFeat_pubDate) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_pubDate).getCode();

  }
}



    