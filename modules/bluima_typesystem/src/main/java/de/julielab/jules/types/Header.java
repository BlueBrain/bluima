

/* First created by JCasGen Wed Oct 19 19:10:41 CEST 2011 */
package de.julielab.jules.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.FSArray;


/** The Header type stores the bibliographical document information.
 * Updated by JCasGen Fri Dec 09 11:59:26 CET 2011
 * XML source: /Users/richarde/dev/bluebrain/svn_nlp/UIMA/blue_uima/trunk/julielab_typesystem-2.6.8/src/main/resources/typeSystem/julie-document-meta-types.xml
 * @generated */
public class Header extends DocumentAnnotation {
  /** @generated
   * @ordered 
   */
  public final static int typeIndexID = JCasRegistry.register(Header.class);
  /** @generated
   * @ordered 
   */
  public final static int type = typeIndexID;
  /** @generated  */
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected Header() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public Header(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public Header(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public Header(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** <!-- begin-user-doc -->
    * Write your own initialization here
    * <!-- end-user-doc -->
  @generated modifiable */
  private void readObject() {}
     
 
    
  //*--------------*
  //* Feature: docType

  /** getter for docType - gets The type of the document (e.g. abstract, fulltext, randomized), C
   * @generated */
  public String getDocType() {
    if (Header_Type.featOkTst && ((Header_Type)jcasType).casFeat_docType == null)
      jcasType.jcas.throwFeatMissing("docType", "de.julielab.jules.types.Header");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Header_Type)jcasType).casFeatCode_docType);}
    
  /** setter for docType - sets The type of the document (e.g. abstract, fulltext, randomized), C 
   * @generated */
  public void setDocType(String v) {
    if (Header_Type.featOkTst && ((Header_Type)jcasType).casFeat_docType == null)
      jcasType.jcas.throwFeatMissing("docType", "de.julielab.jules.types.Header");
    jcasType.ll_cas.ll_setStringValue(addr, ((Header_Type)jcasType).casFeatCode_docType, v);}    
   
    
  //*--------------*
  //* Feature: source

  /** getter for source - gets The source of the document (e.g. WWW, Database), C
   * @generated */
  public String getSource() {
    if (Header_Type.featOkTst && ((Header_Type)jcasType).casFeat_source == null)
      jcasType.jcas.throwFeatMissing("source", "de.julielab.jules.types.Header");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Header_Type)jcasType).casFeatCode_source);}
    
  /** setter for source - sets The source of the document (e.g. WWW, Database), C 
   * @generated */
  public void setSource(String v) {
    if (Header_Type.featOkTst && ((Header_Type)jcasType).casFeat_source == null)
      jcasType.jcas.throwFeatMissing("source", "de.julielab.jules.types.Header");
    jcasType.ll_cas.ll_setStringValue(addr, ((Header_Type)jcasType).casFeatCode_source, v);}    
   
    
  //*--------------*
  //* Feature: docId

  /** getter for docId - gets The identifier of the document with respect to its source. E.g.: PMID in PubMed. In combination with the source, this is a unique identifier for a document, C
   * @generated */
  public String getDocId() {
    if (Header_Type.featOkTst && ((Header_Type)jcasType).casFeat_docId == null)
      jcasType.jcas.throwFeatMissing("docId", "de.julielab.jules.types.Header");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Header_Type)jcasType).casFeatCode_docId);}
    
  /** setter for docId - sets The identifier of the document with respect to its source. E.g.: PMID in PubMed. In combination with the source, this is a unique identifier for a document, C 
   * @generated */
  public void setDocId(String v) {
    if (Header_Type.featOkTst && ((Header_Type)jcasType).casFeat_docId == null)
      jcasType.jcas.throwFeatMissing("docId", "de.julielab.jules.types.Header");
    jcasType.ll_cas.ll_setStringValue(addr, ((Header_Type)jcasType).casFeatCode_docId, v);}    
   
    
  //*--------------*
  //* Feature: copyright

  /** getter for copyright - gets Copyright information, C
   * @generated */
  public String getCopyright() {
    if (Header_Type.featOkTst && ((Header_Type)jcasType).casFeat_copyright == null)
      jcasType.jcas.throwFeatMissing("copyright", "de.julielab.jules.types.Header");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Header_Type)jcasType).casFeatCode_copyright);}
    
  /** setter for copyright - sets Copyright information, C 
   * @generated */
  public void setCopyright(String v) {
    if (Header_Type.featOkTst && ((Header_Type)jcasType).casFeat_copyright == null)
      jcasType.jcas.throwFeatMissing("copyright", "de.julielab.jules.types.Header");
    jcasType.ll_cas.ll_setStringValue(addr, ((Header_Type)jcasType).casFeatCode_copyright, v);}    
   
    
  //*--------------*
  //* Feature: truncated

  /** getter for truncated - gets Indicates whether the document is truncated, C
   * @generated */
  public boolean getTruncated() {
    if (Header_Type.featOkTst && ((Header_Type)jcasType).casFeat_truncated == null)
      jcasType.jcas.throwFeatMissing("truncated", "de.julielab.jules.types.Header");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((Header_Type)jcasType).casFeatCode_truncated);}
    
  /** setter for truncated - sets Indicates whether the document is truncated, C 
   * @generated */
  public void setTruncated(boolean v) {
    if (Header_Type.featOkTst && ((Header_Type)jcasType).casFeat_truncated == null)
      jcasType.jcas.throwFeatMissing("truncated", "de.julielab.jules.types.Header");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((Header_Type)jcasType).casFeatCode_truncated, v);}    
   
    
  //*--------------*
  //* Feature: authors

  /** getter for authors - gets The authors of the document, O
   * @generated */
  public FSArray getAuthors() {
    if (Header_Type.featOkTst && ((Header_Type)jcasType).casFeat_authors == null)
      jcasType.jcas.throwFeatMissing("authors", "de.julielab.jules.types.Header");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Header_Type)jcasType).casFeatCode_authors)));}
    
  /** setter for authors - sets The authors of the document, O 
   * @generated */
  public void setAuthors(FSArray v) {
    if (Header_Type.featOkTst && ((Header_Type)jcasType).casFeat_authors == null)
      jcasType.jcas.throwFeatMissing("authors", "de.julielab.jules.types.Header");
    jcasType.ll_cas.ll_setRefValue(addr, ((Header_Type)jcasType).casFeatCode_authors, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for authors - gets an indexed value - The authors of the document, O
   * @generated */
  public AuthorInfo getAuthors(int i) {
    if (Header_Type.featOkTst && ((Header_Type)jcasType).casFeat_authors == null)
      jcasType.jcas.throwFeatMissing("authors", "de.julielab.jules.types.Header");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Header_Type)jcasType).casFeatCode_authors), i);
    return (AuthorInfo)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Header_Type)jcasType).casFeatCode_authors), i)));}

  /** indexed setter for authors - sets an indexed value - The authors of the document, O
   * @generated */
  public void setAuthors(int i, AuthorInfo v) { 
    if (Header_Type.featOkTst && ((Header_Type)jcasType).casFeat_authors == null)
      jcasType.jcas.throwFeatMissing("authors", "de.julielab.jules.types.Header");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Header_Type)jcasType).casFeatCode_authors), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Header_Type)jcasType).casFeatCode_authors), i, jcasType.ll_cas.ll_getFSRef(v));}
   
    
  //*--------------*
  //* Feature: title

  /** getter for title - gets The title of the document, C
   * @generated */
  public String getTitle() {
    if (Header_Type.featOkTst && ((Header_Type)jcasType).casFeat_title == null)
      jcasType.jcas.throwFeatMissing("title", "de.julielab.jules.types.Header");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Header_Type)jcasType).casFeatCode_title);}
    
  /** setter for title - sets The title of the document, C 
   * @generated */
  public void setTitle(String v) {
    if (Header_Type.featOkTst && ((Header_Type)jcasType).casFeat_title == null)
      jcasType.jcas.throwFeatMissing("title", "de.julielab.jules.types.Header");
    jcasType.ll_cas.ll_setStringValue(addr, ((Header_Type)jcasType).casFeatCode_title, v);}    
   
    
  //*--------------*
  //* Feature: pubTypeList

  /** getter for pubTypeList - gets The list of the publication types, O
   * @generated */
  public FSArray getPubTypeList() {
    if (Header_Type.featOkTst && ((Header_Type)jcasType).casFeat_pubTypeList == null)
      jcasType.jcas.throwFeatMissing("pubTypeList", "de.julielab.jules.types.Header");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Header_Type)jcasType).casFeatCode_pubTypeList)));}
    
  /** setter for pubTypeList - sets The list of the publication types, O 
   * @generated */
  public void setPubTypeList(FSArray v) {
    if (Header_Type.featOkTst && ((Header_Type)jcasType).casFeat_pubTypeList == null)
      jcasType.jcas.throwFeatMissing("pubTypeList", "de.julielab.jules.types.Header");
    jcasType.ll_cas.ll_setRefValue(addr, ((Header_Type)jcasType).casFeatCode_pubTypeList, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for pubTypeList - gets an indexed value - The list of the publication types, O
   * @generated */
  public PubType getPubTypeList(int i) {
    if (Header_Type.featOkTst && ((Header_Type)jcasType).casFeat_pubTypeList == null)
      jcasType.jcas.throwFeatMissing("pubTypeList", "de.julielab.jules.types.Header");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Header_Type)jcasType).casFeatCode_pubTypeList), i);
    return (PubType)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Header_Type)jcasType).casFeatCode_pubTypeList), i)));}

  /** indexed setter for pubTypeList - sets an indexed value - The list of the publication types, O
   * @generated */
  public void setPubTypeList(int i, PubType v) { 
    if (Header_Type.featOkTst && ((Header_Type)jcasType).casFeat_pubTypeList == null)
      jcasType.jcas.throwFeatMissing("pubTypeList", "de.julielab.jules.types.Header");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Header_Type)jcasType).casFeatCode_pubTypeList), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Header_Type)jcasType).casFeatCode_pubTypeList), i, jcasType.ll_cas.ll_getFSRef(v));}
   
    
  //*--------------*
  //* Feature: language

  /** getter for language - gets The language of the document, O
   * @generated */
  public String getLanguage() {
    if (Header_Type.featOkTst && ((Header_Type)jcasType).casFeat_language == null)
      jcasType.jcas.throwFeatMissing("language", "de.julielab.jules.types.Header");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Header_Type)jcasType).casFeatCode_language);}
    
  /** setter for language - sets The language of the document, O 
   * @generated */
  public void setLanguage(String v) {
    if (Header_Type.featOkTst && ((Header_Type)jcasType).casFeat_language == null)
      jcasType.jcas.throwFeatMissing("language", "de.julielab.jules.types.Header");
    jcasType.ll_cas.ll_setStringValue(addr, ((Header_Type)jcasType).casFeatCode_language, v);}    
   
    
  //*--------------*
  //* Feature: doi

  /** getter for doi - gets document object identifier
   * @generated */
  public String getDoi() {
    if (Header_Type.featOkTst && ((Header_Type)jcasType).casFeat_doi == null)
      jcasType.jcas.throwFeatMissing("doi", "de.julielab.jules.types.Header");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Header_Type)jcasType).casFeatCode_doi);}
    
  /** setter for doi - sets document object identifier 
   * @generated */
  public void setDoi(String v) {
    if (Header_Type.featOkTst && ((Header_Type)jcasType).casFeat_doi == null)
      jcasType.jcas.throwFeatMissing("doi", "de.julielab.jules.types.Header");
    jcasType.ll_cas.ll_setStringValue(addr, ((Header_Type)jcasType).casFeatCode_doi, v);}    
  }

    