
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

/** This type contains attributes to describe a journal publication.
 * Updated by JCasGen Fri Dec 09 11:59:26 CET 2011
 * @generated */
public class Journal_Type extends PubType_Type {
  /** @generated */
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Journal_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Journal_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Journal(addr, Journal_Type.this);
  			   Journal_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Journal(addr, Journal_Type.this);
  	  }
    };
  /** @generated */
  public final static int typeIndexID = Journal.typeIndexID;
  /** @generated 
     @modifiable */
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.julielab.jules.types.Journal");
 
  /** @generated */
  final Feature casFeat_ISSN;
  /** @generated */
  final int     casFeatCode_ISSN;
  /** @generated */ 
  public String getISSN(int addr) {
        if (featOkTst && casFeat_ISSN == null)
      jcas.throwFeatMissing("ISSN", "de.julielab.jules.types.Journal");
    return ll_cas.ll_getStringValue(addr, casFeatCode_ISSN);
  }
  /** @generated */    
  public void setISSN(int addr, String v) {
        if (featOkTst && casFeat_ISSN == null)
      jcas.throwFeatMissing("ISSN", "de.julielab.jules.types.Journal");
    ll_cas.ll_setStringValue(addr, casFeatCode_ISSN, v);}
    
  
 
  /** @generated */
  final Feature casFeat_volume;
  /** @generated */
  final int     casFeatCode_volume;
  /** @generated */ 
  public String getVolume(int addr) {
        if (featOkTst && casFeat_volume == null)
      jcas.throwFeatMissing("volume", "de.julielab.jules.types.Journal");
    return ll_cas.ll_getStringValue(addr, casFeatCode_volume);
  }
  /** @generated */    
  public void setVolume(int addr, String v) {
        if (featOkTst && casFeat_volume == null)
      jcas.throwFeatMissing("volume", "de.julielab.jules.types.Journal");
    ll_cas.ll_setStringValue(addr, casFeatCode_volume, v);}
    
  
 
  /** @generated */
  final Feature casFeat_title;
  /** @generated */
  final int     casFeatCode_title;
  /** @generated */ 
  public String getTitle(int addr) {
        if (featOkTst && casFeat_title == null)
      jcas.throwFeatMissing("title", "de.julielab.jules.types.Journal");
    return ll_cas.ll_getStringValue(addr, casFeatCode_title);
  }
  /** @generated */    
  public void setTitle(int addr, String v) {
        if (featOkTst && casFeat_title == null)
      jcas.throwFeatMissing("title", "de.julielab.jules.types.Journal");
    ll_cas.ll_setStringValue(addr, casFeatCode_title, v);}
    
  
 
  /** @generated */
  final Feature casFeat_impactFactor;
  /** @generated */
  final int     casFeatCode_impactFactor;
  /** @generated */ 
  public String getImpactFactor(int addr) {
        if (featOkTst && casFeat_impactFactor == null)
      jcas.throwFeatMissing("impactFactor", "de.julielab.jules.types.Journal");
    return ll_cas.ll_getStringValue(addr, casFeatCode_impactFactor);
  }
  /** @generated */    
  public void setImpactFactor(int addr, String v) {
        if (featOkTst && casFeat_impactFactor == null)
      jcas.throwFeatMissing("impactFactor", "de.julielab.jules.types.Journal");
    ll_cas.ll_setStringValue(addr, casFeatCode_impactFactor, v);}
    
  
 
  /** @generated */
  final Feature casFeat_shortTitle;
  /** @generated */
  final int     casFeatCode_shortTitle;
  /** @generated */ 
  public String getShortTitle(int addr) {
        if (featOkTst && casFeat_shortTitle == null)
      jcas.throwFeatMissing("shortTitle", "de.julielab.jules.types.Journal");
    return ll_cas.ll_getStringValue(addr, casFeatCode_shortTitle);
  }
  /** @generated */    
  public void setShortTitle(int addr, String v) {
        if (featOkTst && casFeat_shortTitle == null)
      jcas.throwFeatMissing("shortTitle", "de.julielab.jules.types.Journal");
    ll_cas.ll_setStringValue(addr, casFeatCode_shortTitle, v);}
    
  
 
  /** @generated */
  final Feature casFeat_issue;
  /** @generated */
  final int     casFeatCode_issue;
  /** @generated */ 
  public String getIssue(int addr) {
        if (featOkTst && casFeat_issue == null)
      jcas.throwFeatMissing("issue", "de.julielab.jules.types.Journal");
    return ll_cas.ll_getStringValue(addr, casFeatCode_issue);
  }
  /** @generated */    
  public void setIssue(int addr, String v) {
        if (featOkTst && casFeat_issue == null)
      jcas.throwFeatMissing("issue", "de.julielab.jules.types.Journal");
    ll_cas.ll_setStringValue(addr, casFeatCode_issue, v);}
    
  
 
  /** @generated */
  final Feature casFeat_pages;
  /** @generated */
  final int     casFeatCode_pages;
  /** @generated */ 
  public String getPages(int addr) {
        if (featOkTst && casFeat_pages == null)
      jcas.throwFeatMissing("pages", "de.julielab.jules.types.Journal");
    return ll_cas.ll_getStringValue(addr, casFeatCode_pages);
  }
  /** @generated */    
  public void setPages(int addr, String v) {
        if (featOkTst && casFeat_pages == null)
      jcas.throwFeatMissing("pages", "de.julielab.jules.types.Journal");
    ll_cas.ll_setStringValue(addr, casFeatCode_pages, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public Journal_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_ISSN = jcas.getRequiredFeatureDE(casType, "ISSN", "uima.cas.String", featOkTst);
    casFeatCode_ISSN  = (null == casFeat_ISSN) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_ISSN).getCode();

 
    casFeat_volume = jcas.getRequiredFeatureDE(casType, "volume", "uima.cas.String", featOkTst);
    casFeatCode_volume  = (null == casFeat_volume) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_volume).getCode();

 
    casFeat_title = jcas.getRequiredFeatureDE(casType, "title", "uima.cas.String", featOkTst);
    casFeatCode_title  = (null == casFeat_title) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_title).getCode();

 
    casFeat_impactFactor = jcas.getRequiredFeatureDE(casType, "impactFactor", "uima.cas.String", featOkTst);
    casFeatCode_impactFactor  = (null == casFeat_impactFactor) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_impactFactor).getCode();

 
    casFeat_shortTitle = jcas.getRequiredFeatureDE(casType, "shortTitle", "uima.cas.String", featOkTst);
    casFeatCode_shortTitle  = (null == casFeat_shortTitle) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_shortTitle).getCode();

 
    casFeat_issue = jcas.getRequiredFeatureDE(casType, "issue", "uima.cas.String", featOkTst);
    casFeatCode_issue  = (null == casFeat_issue) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_issue).getCode();

 
    casFeat_pages = jcas.getRequiredFeatureDE(casType, "pages", "uima.cas.String", featOkTst);
    casFeatCode_pages  = (null == casFeat_pages) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_pages).getCode();

  }
}



    