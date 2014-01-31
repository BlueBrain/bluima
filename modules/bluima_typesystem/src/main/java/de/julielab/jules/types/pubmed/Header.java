

/* First created by JCasGen Wed Oct 19 19:10:41 CEST 2011 */
package de.julielab.jules.types.pubmed;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** The special Header for PubMed  (http://www.pubmed.org) documents
 * Updated by JCasGen Fri Dec 09 11:59:26 CET 2011
 * XML source: /Users/richarde/dev/bluebrain/svn_nlp/UIMA/blue_uima/trunk/julielab_typesystem-2.6.8/src/main/resources/typeSystem/julie-document-meta-types.xml
 * @generated */
public class Header extends de.julielab.jules.types.Header {
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
  //* Feature: citationStatus

  /** getter for citationStatus - gets Indicates the status of citation of a PubMed document, O
   * @generated */
  public String getCitationStatus() {
    if (Header_Type.featOkTst && ((Header_Type)jcasType).casFeat_citationStatus == null)
      jcasType.jcas.throwFeatMissing("citationStatus", "de.julielab.jules.types.pubmed.Header");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Header_Type)jcasType).casFeatCode_citationStatus);}
    
  /** setter for citationStatus - sets Indicates the status of citation of a PubMed document, O 
   * @generated */
  public void setCitationStatus(String v) {
    if (Header_Type.featOkTst && ((Header_Type)jcasType).casFeat_citationStatus == null)
      jcasType.jcas.throwFeatMissing("citationStatus", "de.julielab.jules.types.pubmed.Header");
    jcasType.ll_cas.ll_setStringValue(addr, ((Header_Type)jcasType).casFeatCode_citationStatus, v);}    
  }

    