

/* First created by JCasGen Sat Mar 07 22:05:57 CET 2015 */
package de.julielab.jules.types.ace;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.FSArray;


/** 
 * Updated by JCasGen Sat Mar 07 22:05:57 CET 2015
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/target/jcasgen/typesystem.xml
 * @generated */
public class SourceFile extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(SourceFile.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated
   * @return index of the type  
   */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected SourceFile() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public SourceFile(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public SourceFile(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public SourceFile(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** 
   * <!-- begin-user-doc -->
   * Write your own initialization here
   * <!-- end-user-doc -->
   *
   * @generated modifiable 
   */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: author

  /** getter for author - gets 
   * @generated
   * @return value of the feature 
   */
  public String getAuthor() {
    if (SourceFile_Type.featOkTst && ((SourceFile_Type)jcasType).casFeat_author == null)
      jcasType.jcas.throwFeatMissing("author", "de.julielab.jules.types.ace.SourceFile");
    return jcasType.ll_cas.ll_getStringValue(addr, ((SourceFile_Type)jcasType).casFeatCode_author);}
    
  /** setter for author - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setAuthor(String v) {
    if (SourceFile_Type.featOkTst && ((SourceFile_Type)jcasType).casFeat_author == null)
      jcasType.jcas.throwFeatMissing("author", "de.julielab.jules.types.ace.SourceFile");
    jcasType.ll_cas.ll_setStringValue(addr, ((SourceFile_Type)jcasType).casFeatCode_author, v);}    
   
    
  //*--------------*
  //* Feature: uri

  /** getter for uri - gets 
   * @generated
   * @return value of the feature 
   */
  public String getUri() {
    if (SourceFile_Type.featOkTst && ((SourceFile_Type)jcasType).casFeat_uri == null)
      jcasType.jcas.throwFeatMissing("uri", "de.julielab.jules.types.ace.SourceFile");
    return jcasType.ll_cas.ll_getStringValue(addr, ((SourceFile_Type)jcasType).casFeatCode_uri);}
    
  /** setter for uri - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setUri(String v) {
    if (SourceFile_Type.featOkTst && ((SourceFile_Type)jcasType).casFeat_uri == null)
      jcasType.jcas.throwFeatMissing("uri", "de.julielab.jules.types.ace.SourceFile");
    jcasType.ll_cas.ll_setStringValue(addr, ((SourceFile_Type)jcasType).casFeatCode_uri, v);}    
   
    
  //*--------------*
  //* Feature: encoding

  /** getter for encoding - gets 
   * @generated
   * @return value of the feature 
   */
  public String getEncoding() {
    if (SourceFile_Type.featOkTst && ((SourceFile_Type)jcasType).casFeat_encoding == null)
      jcasType.jcas.throwFeatMissing("encoding", "de.julielab.jules.types.ace.SourceFile");
    return jcasType.ll_cas.ll_getStringValue(addr, ((SourceFile_Type)jcasType).casFeatCode_encoding);}
    
  /** setter for encoding - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setEncoding(String v) {
    if (SourceFile_Type.featOkTst && ((SourceFile_Type)jcasType).casFeat_encoding == null)
      jcasType.jcas.throwFeatMissing("encoding", "de.julielab.jules.types.ace.SourceFile");
    jcasType.ll_cas.ll_setStringValue(addr, ((SourceFile_Type)jcasType).casFeatCode_encoding, v);}    
   
    
  //*--------------*
  //* Feature: source

  /** getter for source - gets 
   * @generated
   * @return value of the feature 
   */
  public String getSource() {
    if (SourceFile_Type.featOkTst && ((SourceFile_Type)jcasType).casFeat_source == null)
      jcasType.jcas.throwFeatMissing("source", "de.julielab.jules.types.ace.SourceFile");
    return jcasType.ll_cas.ll_getStringValue(addr, ((SourceFile_Type)jcasType).casFeatCode_source);}
    
  /** setter for source - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setSource(String v) {
    if (SourceFile_Type.featOkTst && ((SourceFile_Type)jcasType).casFeat_source == null)
      jcasType.jcas.throwFeatMissing("source", "de.julielab.jules.types.ace.SourceFile");
    jcasType.ll_cas.ll_setStringValue(addr, ((SourceFile_Type)jcasType).casFeatCode_source, v);}    
   
    
  //*--------------*
  //* Feature: version

  /** getter for version - gets 
   * @generated
   * @return value of the feature 
   */
  public String getVersion() {
    if (SourceFile_Type.featOkTst && ((SourceFile_Type)jcasType).casFeat_version == null)
      jcasType.jcas.throwFeatMissing("version", "de.julielab.jules.types.ace.SourceFile");
    return jcasType.ll_cas.ll_getStringValue(addr, ((SourceFile_Type)jcasType).casFeatCode_version);}
    
  /** setter for version - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setVersion(String v) {
    if (SourceFile_Type.featOkTst && ((SourceFile_Type)jcasType).casFeat_version == null)
      jcasType.jcas.throwFeatMissing("version", "de.julielab.jules.types.ace.SourceFile");
    jcasType.ll_cas.ll_setStringValue(addr, ((SourceFile_Type)jcasType).casFeatCode_version, v);}    
   
    
  //*--------------*
  //* Feature: documents

  /** getter for documents - gets 
   * @generated
   * @return value of the feature 
   */
  public FSArray getDocuments() {
    if (SourceFile_Type.featOkTst && ((SourceFile_Type)jcasType).casFeat_documents == null)
      jcasType.jcas.throwFeatMissing("documents", "de.julielab.jules.types.ace.SourceFile");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((SourceFile_Type)jcasType).casFeatCode_documents)));}
    
  /** setter for documents - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setDocuments(FSArray v) {
    if (SourceFile_Type.featOkTst && ((SourceFile_Type)jcasType).casFeat_documents == null)
      jcasType.jcas.throwFeatMissing("documents", "de.julielab.jules.types.ace.SourceFile");
    jcasType.ll_cas.ll_setRefValue(addr, ((SourceFile_Type)jcasType).casFeatCode_documents, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for documents - gets an indexed value - 
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public Document getDocuments(int i) {
    if (SourceFile_Type.featOkTst && ((SourceFile_Type)jcasType).casFeat_documents == null)
      jcasType.jcas.throwFeatMissing("documents", "de.julielab.jules.types.ace.SourceFile");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((SourceFile_Type)jcasType).casFeatCode_documents), i);
    return (Document)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((SourceFile_Type)jcasType).casFeatCode_documents), i)));}

  /** indexed setter for documents - sets an indexed value - 
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setDocuments(int i, Document v) { 
    if (SourceFile_Type.featOkTst && ((SourceFile_Type)jcasType).casFeat_documents == null)
      jcasType.jcas.throwFeatMissing("documents", "de.julielab.jules.types.ace.SourceFile");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((SourceFile_Type)jcasType).casFeatCode_documents), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((SourceFile_Type)jcasType).casFeatCode_documents), i, jcasType.ll_cas.ll_getFSRef(v));}
   
    
  //*--------------*
  //* Feature: ace_type

  /** getter for ace_type - gets 
   * @generated
   * @return value of the feature 
   */
  public String getAce_type() {
    if (SourceFile_Type.featOkTst && ((SourceFile_Type)jcasType).casFeat_ace_type == null)
      jcasType.jcas.throwFeatMissing("ace_type", "de.julielab.jules.types.ace.SourceFile");
    return jcasType.ll_cas.ll_getStringValue(addr, ((SourceFile_Type)jcasType).casFeatCode_ace_type);}
    
  /** setter for ace_type - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setAce_type(String v) {
    if (SourceFile_Type.featOkTst && ((SourceFile_Type)jcasType).casFeat_ace_type == null)
      jcasType.jcas.throwFeatMissing("ace_type", "de.julielab.jules.types.ace.SourceFile");
    jcasType.ll_cas.ll_setStringValue(addr, ((SourceFile_Type)jcasType).casFeatCode_ace_type, v);}    
  }

    