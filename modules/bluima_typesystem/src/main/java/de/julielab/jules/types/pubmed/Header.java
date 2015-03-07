

/* First created by JCasGen Sat Mar 07 22:05:57 CET 2015 */
package de.julielab.jules.types.pubmed;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** The special Header for PubMed  (http://www.pubmed.org) documents
 * Updated by JCasGen Sat Mar 07 22:05:57 CET 2015
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/target/jcasgen/typesystem.xml
 * @generated */
public class Header extends de.julielab.jules.types.Header {
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
  //* Feature: citationStatus

  /** getter for citationStatus - gets Indicates the status of citation of a PubMed document, O
   * @generated
   * @return value of the feature 
   */
  public String getCitationStatus() {
    if (Header_Type.featOkTst && ((Header_Type)jcasType).casFeat_citationStatus == null)
      jcasType.jcas.throwFeatMissing("citationStatus", "de.julielab.jules.types.pubmed.Header");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Header_Type)jcasType).casFeatCode_citationStatus);}
    
  /** setter for citationStatus - sets Indicates the status of citation of a PubMed document, O 
   * @generated
   * @param v value to set into the feature 
   */
  public void setCitationStatus(String v) {
    if (Header_Type.featOkTst && ((Header_Type)jcasType).casFeat_citationStatus == null)
      jcasType.jcas.throwFeatMissing("citationStatus", "de.julielab.jules.types.pubmed.Header");
    jcasType.ll_cas.ll_setStringValue(addr, ((Header_Type)jcasType).casFeatCode_citationStatus, v);}    
  }

    