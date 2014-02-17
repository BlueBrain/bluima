

/* First created by JCasGen Mon Feb 17 12:26:53 CET 2014 */
package ch.epfl.bbp.uima.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** Represents a page in a document, e.g. a page in a PDF article
 * Updated by JCasGen Mon Feb 17 22:12:56 CET 2014
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/src/main/resources/typeSystem/bbp-types.xml
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
  /** @generated  */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected DocumentPage() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public DocumentPage(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public DocumentPage(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public DocumentPage(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** <!-- begin-user-doc -->
    * Write your own initialization here
    * <!-- end-user-doc -->
  @generated modifiable */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: pageId

  /** getter for pageId - gets The id of the page, 0-indexed
   * @generated */
  public int getPageId() {
    if (DocumentPage_Type.featOkTst && ((DocumentPage_Type)jcasType).casFeat_pageId == null)
      jcasType.jcas.throwFeatMissing("pageId", "ch.epfl.bbp.uima.types.DocumentPage");
    return jcasType.ll_cas.ll_getIntValue(addr, ((DocumentPage_Type)jcasType).casFeatCode_pageId);}
    
  /** setter for pageId - sets The id of the page, 0-indexed 
   * @generated */
  public void setPageId(int v) {
    if (DocumentPage_Type.featOkTst && ((DocumentPage_Type)jcasType).casFeat_pageId == null)
      jcasType.jcas.throwFeatMissing("pageId", "ch.epfl.bbp.uima.types.DocumentPage");
    jcasType.ll_cas.ll_setIntValue(addr, ((DocumentPage_Type)jcasType).casFeatCode_pageId, v);}    
  }

    