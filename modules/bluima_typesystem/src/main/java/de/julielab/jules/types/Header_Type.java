
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

/** The Header type stores the bibliographical document information.
 * Updated by JCasGen Sat Mar 07 22:05:57 CET 2015
 * @generated */
public class Header_Type extends DocumentAnnotation_Type {
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
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.julielab.jules.types.Header");
 
  /** @generated */
  final Feature casFeat_docType;
  /** @generated */
  final int     casFeatCode_docType;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getDocType(int addr) {
        if (featOkTst && casFeat_docType == null)
      jcas.throwFeatMissing("docType", "de.julielab.jules.types.Header");
    return ll_cas.ll_getStringValue(addr, casFeatCode_docType);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setDocType(int addr, String v) {
        if (featOkTst && casFeat_docType == null)
      jcas.throwFeatMissing("docType", "de.julielab.jules.types.Header");
    ll_cas.ll_setStringValue(addr, casFeatCode_docType, v);}
    
  
 
  /** @generated */
  final Feature casFeat_source;
  /** @generated */
  final int     casFeatCode_source;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getSource(int addr) {
        if (featOkTst && casFeat_source == null)
      jcas.throwFeatMissing("source", "de.julielab.jules.types.Header");
    return ll_cas.ll_getStringValue(addr, casFeatCode_source);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setSource(int addr, String v) {
        if (featOkTst && casFeat_source == null)
      jcas.throwFeatMissing("source", "de.julielab.jules.types.Header");
    ll_cas.ll_setStringValue(addr, casFeatCode_source, v);}
    
  
 
  /** @generated */
  final Feature casFeat_docId;
  /** @generated */
  final int     casFeatCode_docId;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getDocId(int addr) {
        if (featOkTst && casFeat_docId == null)
      jcas.throwFeatMissing("docId", "de.julielab.jules.types.Header");
    return ll_cas.ll_getStringValue(addr, casFeatCode_docId);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setDocId(int addr, String v) {
        if (featOkTst && casFeat_docId == null)
      jcas.throwFeatMissing("docId", "de.julielab.jules.types.Header");
    ll_cas.ll_setStringValue(addr, casFeatCode_docId, v);}
    
  
 
  /** @generated */
  final Feature casFeat_copyright;
  /** @generated */
  final int     casFeatCode_copyright;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getCopyright(int addr) {
        if (featOkTst && casFeat_copyright == null)
      jcas.throwFeatMissing("copyright", "de.julielab.jules.types.Header");
    return ll_cas.ll_getStringValue(addr, casFeatCode_copyright);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setCopyright(int addr, String v) {
        if (featOkTst && casFeat_copyright == null)
      jcas.throwFeatMissing("copyright", "de.julielab.jules.types.Header");
    ll_cas.ll_setStringValue(addr, casFeatCode_copyright, v);}
    
  
 
  /** @generated */
  final Feature casFeat_truncated;
  /** @generated */
  final int     casFeatCode_truncated;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public boolean getTruncated(int addr) {
        if (featOkTst && casFeat_truncated == null)
      jcas.throwFeatMissing("truncated", "de.julielab.jules.types.Header");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_truncated);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setTruncated(int addr, boolean v) {
        if (featOkTst && casFeat_truncated == null)
      jcas.throwFeatMissing("truncated", "de.julielab.jules.types.Header");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_truncated, v);}
    
  
 
  /** @generated */
  final Feature casFeat_authors;
  /** @generated */
  final int     casFeatCode_authors;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getAuthors(int addr) {
        if (featOkTst && casFeat_authors == null)
      jcas.throwFeatMissing("authors", "de.julielab.jules.types.Header");
    return ll_cas.ll_getRefValue(addr, casFeatCode_authors);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setAuthors(int addr, int v) {
        if (featOkTst && casFeat_authors == null)
      jcas.throwFeatMissing("authors", "de.julielab.jules.types.Header");
    ll_cas.ll_setRefValue(addr, casFeatCode_authors, v);}
    
   /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @return value at index i in the array 
   */
  public int getAuthors(int addr, int i) {
        if (featOkTst && casFeat_authors == null)
      jcas.throwFeatMissing("authors", "de.julielab.jules.types.Header");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_authors), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_authors), i);
	return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_authors), i);
  }
   
  /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @param v value to set
   */ 
  public void setAuthors(int addr, int i, int v) {
        if (featOkTst && casFeat_authors == null)
      jcas.throwFeatMissing("authors", "de.julielab.jules.types.Header");
    if (lowLevelTypeChecks)
      ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_authors), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_authors), i);
    ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_authors), i, v);
  }
 
 
  /** @generated */
  final Feature casFeat_title;
  /** @generated */
  final int     casFeatCode_title;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getTitle(int addr) {
        if (featOkTst && casFeat_title == null)
      jcas.throwFeatMissing("title", "de.julielab.jules.types.Header");
    return ll_cas.ll_getStringValue(addr, casFeatCode_title);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setTitle(int addr, String v) {
        if (featOkTst && casFeat_title == null)
      jcas.throwFeatMissing("title", "de.julielab.jules.types.Header");
    ll_cas.ll_setStringValue(addr, casFeatCode_title, v);}
    
  
 
  /** @generated */
  final Feature casFeat_pubTypeList;
  /** @generated */
  final int     casFeatCode_pubTypeList;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getPubTypeList(int addr) {
        if (featOkTst && casFeat_pubTypeList == null)
      jcas.throwFeatMissing("pubTypeList", "de.julielab.jules.types.Header");
    return ll_cas.ll_getRefValue(addr, casFeatCode_pubTypeList);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setPubTypeList(int addr, int v) {
        if (featOkTst && casFeat_pubTypeList == null)
      jcas.throwFeatMissing("pubTypeList", "de.julielab.jules.types.Header");
    ll_cas.ll_setRefValue(addr, casFeatCode_pubTypeList, v);}
    
   /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @return value at index i in the array 
   */
  public int getPubTypeList(int addr, int i) {
        if (featOkTst && casFeat_pubTypeList == null)
      jcas.throwFeatMissing("pubTypeList", "de.julielab.jules.types.Header");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_pubTypeList), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_pubTypeList), i);
	return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_pubTypeList), i);
  }
   
  /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @param v value to set
   */ 
  public void setPubTypeList(int addr, int i, int v) {
        if (featOkTst && casFeat_pubTypeList == null)
      jcas.throwFeatMissing("pubTypeList", "de.julielab.jules.types.Header");
    if (lowLevelTypeChecks)
      ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_pubTypeList), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_pubTypeList), i);
    ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_pubTypeList), i, v);
  }
 
 
  /** @generated */
  final Feature casFeat_language;
  /** @generated */
  final int     casFeatCode_language;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getLanguage(int addr) {
        if (featOkTst && casFeat_language == null)
      jcas.throwFeatMissing("language", "de.julielab.jules.types.Header");
    return ll_cas.ll_getStringValue(addr, casFeatCode_language);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setLanguage(int addr, String v) {
        if (featOkTst && casFeat_language == null)
      jcas.throwFeatMissing("language", "de.julielab.jules.types.Header");
    ll_cas.ll_setStringValue(addr, casFeatCode_language, v);}
    
  
 
  /** @generated */
  final Feature casFeat_doi;
  /** @generated */
  final int     casFeatCode_doi;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getDoi(int addr) {
        if (featOkTst && casFeat_doi == null)
      jcas.throwFeatMissing("doi", "de.julielab.jules.types.Header");
    return ll_cas.ll_getStringValue(addr, casFeatCode_doi);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setDoi(int addr, String v) {
        if (featOkTst && casFeat_doi == null)
      jcas.throwFeatMissing("doi", "de.julielab.jules.types.Header");
    ll_cas.ll_setStringValue(addr, casFeatCode_doi, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public Header_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_docType = jcas.getRequiredFeatureDE(casType, "docType", "de.julielab.jules.types.DocType", featOkTst);
    casFeatCode_docType  = (null == casFeat_docType) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_docType).getCode();

 
    casFeat_source = jcas.getRequiredFeatureDE(casType, "source", "uima.cas.String", featOkTst);
    casFeatCode_source  = (null == casFeat_source) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_source).getCode();

 
    casFeat_docId = jcas.getRequiredFeatureDE(casType, "docId", "uima.cas.String", featOkTst);
    casFeatCode_docId  = (null == casFeat_docId) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_docId).getCode();

 
    casFeat_copyright = jcas.getRequiredFeatureDE(casType, "copyright", "uima.cas.String", featOkTst);
    casFeatCode_copyright  = (null == casFeat_copyright) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_copyright).getCode();

 
    casFeat_truncated = jcas.getRequiredFeatureDE(casType, "truncated", "uima.cas.Boolean", featOkTst);
    casFeatCode_truncated  = (null == casFeat_truncated) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_truncated).getCode();

 
    casFeat_authors = jcas.getRequiredFeatureDE(casType, "authors", "uima.cas.FSArray", featOkTst);
    casFeatCode_authors  = (null == casFeat_authors) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_authors).getCode();

 
    casFeat_title = jcas.getRequiredFeatureDE(casType, "title", "uima.cas.String", featOkTst);
    casFeatCode_title  = (null == casFeat_title) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_title).getCode();

 
    casFeat_pubTypeList = jcas.getRequiredFeatureDE(casType, "pubTypeList", "uima.cas.FSArray", featOkTst);
    casFeatCode_pubTypeList  = (null == casFeat_pubTypeList) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_pubTypeList).getCode();

 
    casFeat_language = jcas.getRequiredFeatureDE(casType, "language", "de.julielab.jules.types.Language", featOkTst);
    casFeatCode_language  = (null == casFeat_language) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_language).getCode();

 
    casFeat_doi = jcas.getRequiredFeatureDE(casType, "doi", "uima.cas.String", featOkTst);
    casFeatCode_doi  = (null == casFeat_doi) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_doi).getCode();

  }
}



    