
/* First created by JCasGen Sat Mar 07 22:05:57 CET 2015 */
package de.julielab.jules.types.ace;

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
 * Updated by JCasGen Sat Mar 07 22:05:57 CET 2015
 * @generated */
public class SourceFile_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (SourceFile_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = SourceFile_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new SourceFile(addr, SourceFile_Type.this);
  			   SourceFile_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new SourceFile(addr, SourceFile_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = SourceFile.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.julielab.jules.types.ace.SourceFile");
 
  /** @generated */
  final Feature casFeat_author;
  /** @generated */
  final int     casFeatCode_author;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getAuthor(int addr) {
        if (featOkTst && casFeat_author == null)
      jcas.throwFeatMissing("author", "de.julielab.jules.types.ace.SourceFile");
    return ll_cas.ll_getStringValue(addr, casFeatCode_author);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setAuthor(int addr, String v) {
        if (featOkTst && casFeat_author == null)
      jcas.throwFeatMissing("author", "de.julielab.jules.types.ace.SourceFile");
    ll_cas.ll_setStringValue(addr, casFeatCode_author, v);}
    
  
 
  /** @generated */
  final Feature casFeat_uri;
  /** @generated */
  final int     casFeatCode_uri;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getUri(int addr) {
        if (featOkTst && casFeat_uri == null)
      jcas.throwFeatMissing("uri", "de.julielab.jules.types.ace.SourceFile");
    return ll_cas.ll_getStringValue(addr, casFeatCode_uri);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setUri(int addr, String v) {
        if (featOkTst && casFeat_uri == null)
      jcas.throwFeatMissing("uri", "de.julielab.jules.types.ace.SourceFile");
    ll_cas.ll_setStringValue(addr, casFeatCode_uri, v);}
    
  
 
  /** @generated */
  final Feature casFeat_encoding;
  /** @generated */
  final int     casFeatCode_encoding;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getEncoding(int addr) {
        if (featOkTst && casFeat_encoding == null)
      jcas.throwFeatMissing("encoding", "de.julielab.jules.types.ace.SourceFile");
    return ll_cas.ll_getStringValue(addr, casFeatCode_encoding);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setEncoding(int addr, String v) {
        if (featOkTst && casFeat_encoding == null)
      jcas.throwFeatMissing("encoding", "de.julielab.jules.types.ace.SourceFile");
    ll_cas.ll_setStringValue(addr, casFeatCode_encoding, v);}
    
  
 
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
      jcas.throwFeatMissing("source", "de.julielab.jules.types.ace.SourceFile");
    return ll_cas.ll_getStringValue(addr, casFeatCode_source);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setSource(int addr, String v) {
        if (featOkTst && casFeat_source == null)
      jcas.throwFeatMissing("source", "de.julielab.jules.types.ace.SourceFile");
    ll_cas.ll_setStringValue(addr, casFeatCode_source, v);}
    
  
 
  /** @generated */
  final Feature casFeat_version;
  /** @generated */
  final int     casFeatCode_version;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getVersion(int addr) {
        if (featOkTst && casFeat_version == null)
      jcas.throwFeatMissing("version", "de.julielab.jules.types.ace.SourceFile");
    return ll_cas.ll_getStringValue(addr, casFeatCode_version);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setVersion(int addr, String v) {
        if (featOkTst && casFeat_version == null)
      jcas.throwFeatMissing("version", "de.julielab.jules.types.ace.SourceFile");
    ll_cas.ll_setStringValue(addr, casFeatCode_version, v);}
    
  
 
  /** @generated */
  final Feature casFeat_documents;
  /** @generated */
  final int     casFeatCode_documents;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getDocuments(int addr) {
        if (featOkTst && casFeat_documents == null)
      jcas.throwFeatMissing("documents", "de.julielab.jules.types.ace.SourceFile");
    return ll_cas.ll_getRefValue(addr, casFeatCode_documents);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setDocuments(int addr, int v) {
        if (featOkTst && casFeat_documents == null)
      jcas.throwFeatMissing("documents", "de.julielab.jules.types.ace.SourceFile");
    ll_cas.ll_setRefValue(addr, casFeatCode_documents, v);}
    
   /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @return value at index i in the array 
   */
  public int getDocuments(int addr, int i) {
        if (featOkTst && casFeat_documents == null)
      jcas.throwFeatMissing("documents", "de.julielab.jules.types.ace.SourceFile");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_documents), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_documents), i);
	return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_documents), i);
  }
   
  /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @param v value to set
   */ 
  public void setDocuments(int addr, int i, int v) {
        if (featOkTst && casFeat_documents == null)
      jcas.throwFeatMissing("documents", "de.julielab.jules.types.ace.SourceFile");
    if (lowLevelTypeChecks)
      ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_documents), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_documents), i);
    ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_documents), i, v);
  }
 
 
  /** @generated */
  final Feature casFeat_ace_type;
  /** @generated */
  final int     casFeatCode_ace_type;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getAce_type(int addr) {
        if (featOkTst && casFeat_ace_type == null)
      jcas.throwFeatMissing("ace_type", "de.julielab.jules.types.ace.SourceFile");
    return ll_cas.ll_getStringValue(addr, casFeatCode_ace_type);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setAce_type(int addr, String v) {
        if (featOkTst && casFeat_ace_type == null)
      jcas.throwFeatMissing("ace_type", "de.julielab.jules.types.ace.SourceFile");
    ll_cas.ll_setStringValue(addr, casFeatCode_ace_type, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public SourceFile_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_author = jcas.getRequiredFeatureDE(casType, "author", "uima.cas.String", featOkTst);
    casFeatCode_author  = (null == casFeat_author) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_author).getCode();

 
    casFeat_uri = jcas.getRequiredFeatureDE(casType, "uri", "uima.cas.String", featOkTst);
    casFeatCode_uri  = (null == casFeat_uri) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_uri).getCode();

 
    casFeat_encoding = jcas.getRequiredFeatureDE(casType, "encoding", "uima.cas.String", featOkTst);
    casFeatCode_encoding  = (null == casFeat_encoding) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_encoding).getCode();

 
    casFeat_source = jcas.getRequiredFeatureDE(casType, "source", "uima.cas.String", featOkTst);
    casFeatCode_source  = (null == casFeat_source) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_source).getCode();

 
    casFeat_version = jcas.getRequiredFeatureDE(casType, "version", "uima.cas.String", featOkTst);
    casFeatCode_version  = (null == casFeat_version) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_version).getCode();

 
    casFeat_documents = jcas.getRequiredFeatureDE(casType, "documents", "uima.cas.FSArray", featOkTst);
    casFeatCode_documents  = (null == casFeat_documents) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_documents).getCode();

 
    casFeat_ace_type = jcas.getRequiredFeatureDE(casType, "ace_type", "uima.cas.String", featOkTst);
    casFeatCode_ace_type  = (null == casFeat_ace_type) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_ace_type).getCode();

  }
}



    