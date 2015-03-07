

/* First created by JCasGen Sat Mar 07 22:05:57 CET 2015 */
package de.julielab.jules.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.FSArray;


/** The Header type stores the bibliographical document information.
 * Updated by JCasGen Sat Mar 07 22:05:57 CET 2015
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/target/jcasgen/typesystem.xml
 * @generated */
public class Header extends DocumentAnnotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Header.class);
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
  protected Header() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Header(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Header(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Header(JCas jcas, int begin, int end) {
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
  //* Feature: docType

  /** getter for docType - gets The type of the document (e.g. abstract, fulltext, randomized), C
   * @generated
   * @return value of the feature 
   */
  public String getDocType() {
    if (Header_Type.featOkTst && ((Header_Type)jcasType).casFeat_docType == null)
      jcasType.jcas.throwFeatMissing("docType", "de.julielab.jules.types.Header");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Header_Type)jcasType).casFeatCode_docType);}
    
  /** setter for docType - sets The type of the document (e.g. abstract, fulltext, randomized), C 
   * @generated
   * @param v value to set into the feature 
   */
  public void setDocType(String v) {
    if (Header_Type.featOkTst && ((Header_Type)jcasType).casFeat_docType == null)
      jcasType.jcas.throwFeatMissing("docType", "de.julielab.jules.types.Header");
    jcasType.ll_cas.ll_setStringValue(addr, ((Header_Type)jcasType).casFeatCode_docType, v);}    
   
    
  //*--------------*
  //* Feature: source

  /** getter for source - gets The source of the document (e.g. WWW, Database), C
   * @generated
   * @return value of the feature 
   */
  public String getSource() {
    if (Header_Type.featOkTst && ((Header_Type)jcasType).casFeat_source == null)
      jcasType.jcas.throwFeatMissing("source", "de.julielab.jules.types.Header");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Header_Type)jcasType).casFeatCode_source);}
    
  /** setter for source - sets The source of the document (e.g. WWW, Database), C 
   * @generated
   * @param v value to set into the feature 
   */
  public void setSource(String v) {
    if (Header_Type.featOkTst && ((Header_Type)jcasType).casFeat_source == null)
      jcasType.jcas.throwFeatMissing("source", "de.julielab.jules.types.Header");
    jcasType.ll_cas.ll_setStringValue(addr, ((Header_Type)jcasType).casFeatCode_source, v);}    
   
    
  //*--------------*
  //* Feature: docId

  /** getter for docId - gets The identifier of the document with respect to its source. E.g.: PMID in PubMed. In combination with the source, this is a unique identifier for a document, C
   * @generated
   * @return value of the feature 
   */
  public String getDocId() {
    if (Header_Type.featOkTst && ((Header_Type)jcasType).casFeat_docId == null)
      jcasType.jcas.throwFeatMissing("docId", "de.julielab.jules.types.Header");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Header_Type)jcasType).casFeatCode_docId);}
    
  /** setter for docId - sets The identifier of the document with respect to its source. E.g.: PMID in PubMed. In combination with the source, this is a unique identifier for a document, C 
   * @generated
   * @param v value to set into the feature 
   */
  public void setDocId(String v) {
    if (Header_Type.featOkTst && ((Header_Type)jcasType).casFeat_docId == null)
      jcasType.jcas.throwFeatMissing("docId", "de.julielab.jules.types.Header");
    jcasType.ll_cas.ll_setStringValue(addr, ((Header_Type)jcasType).casFeatCode_docId, v);}    
   
    
  //*--------------*
  //* Feature: copyright

  /** getter for copyright - gets Copyright information, C
   * @generated
   * @return value of the feature 
   */
  public String getCopyright() {
    if (Header_Type.featOkTst && ((Header_Type)jcasType).casFeat_copyright == null)
      jcasType.jcas.throwFeatMissing("copyright", "de.julielab.jules.types.Header");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Header_Type)jcasType).casFeatCode_copyright);}
    
  /** setter for copyright - sets Copyright information, C 
   * @generated
   * @param v value to set into the feature 
   */
  public void setCopyright(String v) {
    if (Header_Type.featOkTst && ((Header_Type)jcasType).casFeat_copyright == null)
      jcasType.jcas.throwFeatMissing("copyright", "de.julielab.jules.types.Header");
    jcasType.ll_cas.ll_setStringValue(addr, ((Header_Type)jcasType).casFeatCode_copyright, v);}    
   
    
  //*--------------*
  //* Feature: truncated

  /** getter for truncated - gets Indicates whether the document is truncated, C
   * @generated
   * @return value of the feature 
   */
  public boolean getTruncated() {
    if (Header_Type.featOkTst && ((Header_Type)jcasType).casFeat_truncated == null)
      jcasType.jcas.throwFeatMissing("truncated", "de.julielab.jules.types.Header");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((Header_Type)jcasType).casFeatCode_truncated);}
    
  /** setter for truncated - sets Indicates whether the document is truncated, C 
   * @generated
   * @param v value to set into the feature 
   */
  public void setTruncated(boolean v) {
    if (Header_Type.featOkTst && ((Header_Type)jcasType).casFeat_truncated == null)
      jcasType.jcas.throwFeatMissing("truncated", "de.julielab.jules.types.Header");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((Header_Type)jcasType).casFeatCode_truncated, v);}    
   
    
  //*--------------*
  //* Feature: authors

  /** getter for authors - gets The authors of the document, O
   * @generated
   * @return value of the feature 
   */
  public FSArray getAuthors() {
    if (Header_Type.featOkTst && ((Header_Type)jcasType).casFeat_authors == null)
      jcasType.jcas.throwFeatMissing("authors", "de.julielab.jules.types.Header");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Header_Type)jcasType).casFeatCode_authors)));}
    
  /** setter for authors - sets The authors of the document, O 
   * @generated
   * @param v value to set into the feature 
   */
  public void setAuthors(FSArray v) {
    if (Header_Type.featOkTst && ((Header_Type)jcasType).casFeat_authors == null)
      jcasType.jcas.throwFeatMissing("authors", "de.julielab.jules.types.Header");
    jcasType.ll_cas.ll_setRefValue(addr, ((Header_Type)jcasType).casFeatCode_authors, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for authors - gets an indexed value - The authors of the document, O
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public AuthorInfo getAuthors(int i) {
    if (Header_Type.featOkTst && ((Header_Type)jcasType).casFeat_authors == null)
      jcasType.jcas.throwFeatMissing("authors", "de.julielab.jules.types.Header");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Header_Type)jcasType).casFeatCode_authors), i);
    return (AuthorInfo)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Header_Type)jcasType).casFeatCode_authors), i)));}

  /** indexed setter for authors - sets an indexed value - The authors of the document, O
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setAuthors(int i, AuthorInfo v) { 
    if (Header_Type.featOkTst && ((Header_Type)jcasType).casFeat_authors == null)
      jcasType.jcas.throwFeatMissing("authors", "de.julielab.jules.types.Header");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Header_Type)jcasType).casFeatCode_authors), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Header_Type)jcasType).casFeatCode_authors), i, jcasType.ll_cas.ll_getFSRef(v));}
   
    
  //*--------------*
  //* Feature: title

  /** getter for title - gets The title of the document, C
   * @generated
   * @return value of the feature 
   */
  public String getTitle() {
    if (Header_Type.featOkTst && ((Header_Type)jcasType).casFeat_title == null)
      jcasType.jcas.throwFeatMissing("title", "de.julielab.jules.types.Header");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Header_Type)jcasType).casFeatCode_title);}
    
  /** setter for title - sets The title of the document, C 
   * @generated
   * @param v value to set into the feature 
   */
  public void setTitle(String v) {
    if (Header_Type.featOkTst && ((Header_Type)jcasType).casFeat_title == null)
      jcasType.jcas.throwFeatMissing("title", "de.julielab.jules.types.Header");
    jcasType.ll_cas.ll_setStringValue(addr, ((Header_Type)jcasType).casFeatCode_title, v);}    
   
    
  //*--------------*
  //* Feature: pubTypeList

  /** getter for pubTypeList - gets The list of the publication types, O
   * @generated
   * @return value of the feature 
   */
  public FSArray getPubTypeList() {
    if (Header_Type.featOkTst && ((Header_Type)jcasType).casFeat_pubTypeList == null)
      jcasType.jcas.throwFeatMissing("pubTypeList", "de.julielab.jules.types.Header");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Header_Type)jcasType).casFeatCode_pubTypeList)));}
    
  /** setter for pubTypeList - sets The list of the publication types, O 
   * @generated
   * @param v value to set into the feature 
   */
  public void setPubTypeList(FSArray v) {
    if (Header_Type.featOkTst && ((Header_Type)jcasType).casFeat_pubTypeList == null)
      jcasType.jcas.throwFeatMissing("pubTypeList", "de.julielab.jules.types.Header");
    jcasType.ll_cas.ll_setRefValue(addr, ((Header_Type)jcasType).casFeatCode_pubTypeList, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for pubTypeList - gets an indexed value - The list of the publication types, O
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public PubType getPubTypeList(int i) {
    if (Header_Type.featOkTst && ((Header_Type)jcasType).casFeat_pubTypeList == null)
      jcasType.jcas.throwFeatMissing("pubTypeList", "de.julielab.jules.types.Header");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Header_Type)jcasType).casFeatCode_pubTypeList), i);
    return (PubType)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Header_Type)jcasType).casFeatCode_pubTypeList), i)));}

  /** indexed setter for pubTypeList - sets an indexed value - The list of the publication types, O
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setPubTypeList(int i, PubType v) { 
    if (Header_Type.featOkTst && ((Header_Type)jcasType).casFeat_pubTypeList == null)
      jcasType.jcas.throwFeatMissing("pubTypeList", "de.julielab.jules.types.Header");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Header_Type)jcasType).casFeatCode_pubTypeList), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Header_Type)jcasType).casFeatCode_pubTypeList), i, jcasType.ll_cas.ll_getFSRef(v));}
   
    
  //*--------------*
  //* Feature: language

  /** getter for language - gets The language of the document, O
   * @generated
   * @return value of the feature 
   */
  public String getLanguage() {
    if (Header_Type.featOkTst && ((Header_Type)jcasType).casFeat_language == null)
      jcasType.jcas.throwFeatMissing("language", "de.julielab.jules.types.Header");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Header_Type)jcasType).casFeatCode_language);}
    
  /** setter for language - sets The language of the document, O 
   * @generated
   * @param v value to set into the feature 
   */
  public void setLanguage(String v) {
    if (Header_Type.featOkTst && ((Header_Type)jcasType).casFeat_language == null)
      jcasType.jcas.throwFeatMissing("language", "de.julielab.jules.types.Header");
    jcasType.ll_cas.ll_setStringValue(addr, ((Header_Type)jcasType).casFeatCode_language, v);}    
   
    
  //*--------------*
  //* Feature: doi

  /** getter for doi - gets document object identifier
   * @generated
   * @return value of the feature 
   */
  public String getDoi() {
    if (Header_Type.featOkTst && ((Header_Type)jcasType).casFeat_doi == null)
      jcasType.jcas.throwFeatMissing("doi", "de.julielab.jules.types.Header");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Header_Type)jcasType).casFeatCode_doi);}
    
  /** setter for doi - sets document object identifier 
   * @generated
   * @param v value to set into the feature 
   */
  public void setDoi(String v) {
    if (Header_Type.featOkTst && ((Header_Type)jcasType).casFeat_doi == null)
      jcasType.jcas.throwFeatMissing("doi", "de.julielab.jules.types.Header");
    jcasType.ll_cas.ll_setStringValue(addr, ((Header_Type)jcasType).casFeatCode_doi, v);}    
  }

    