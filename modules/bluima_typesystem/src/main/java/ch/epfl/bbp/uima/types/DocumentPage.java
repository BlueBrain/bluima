

/* First created by JCasGen Sat Mar 07 22:05:56 CET 2015 */
package ch.epfl.bbp.uima.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** Represents a page in a document, e.g. a page in a PDF article
 * Updated by JCasGen Sat Mar 07 22:05:56 CET 2015
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/target/jcasgen/typesystem.xml
 * @generated */
public class DocumentPage extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(DocumentPage.class);
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
  protected DocumentPage() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public DocumentPage(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public DocumentPage(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public DocumentPage(JCas jcas, int begin, int end) {
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
  //* Feature: pageId

  /** getter for pageId - gets The id of the page, 0-indexed
   * @generated
   * @return value of the feature 
   */
  public int getPageId() {
    if (DocumentPage_Type.featOkTst && ((DocumentPage_Type)jcasType).casFeat_pageId == null)
      jcasType.jcas.throwFeatMissing("pageId", "ch.epfl.bbp.uima.types.DocumentPage");
    return jcasType.ll_cas.ll_getIntValue(addr, ((DocumentPage_Type)jcasType).casFeatCode_pageId);}
    
  /** setter for pageId - sets The id of the page, 0-indexed 
   * @generated
   * @param v value to set into the feature 
   */
  public void setPageId(int v) {
    if (DocumentPage_Type.featOkTst && ((DocumentPage_Type)jcasType).casFeat_pageId == null)
      jcasType.jcas.throwFeatMissing("pageId", "ch.epfl.bbp.uima.types.DocumentPage");
    jcasType.ll_cas.ll_setIntValue(addr, ((DocumentPage_Type)jcasType).casFeatCode_pageId, v);}    
  }

    