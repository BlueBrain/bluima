

/* First created by JCasGen Mon Feb 17 12:26:53 CET 2014 */
package ch.epfl.bbp.uima.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Mon Feb 17 22:12:56 CET 2014
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/src/main/resources/typeSystem/bbp-types.xml
 * @generated */
public class DocumentTextHolder extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(DocumentTextHolder.class);
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
  protected DocumentTextHolder() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public DocumentTextHolder(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public DocumentTextHolder(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public DocumentTextHolder(JCas jcas, int begin, int end) {
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
  //* Feature: text

  /** getter for text - gets 
   * @generated */
  public String getText() {
    if (DocumentTextHolder_Type.featOkTst && ((DocumentTextHolder_Type)jcasType).casFeat_text == null)
      jcasType.jcas.throwFeatMissing("text", "ch.epfl.bbp.uima.types.DocumentTextHolder");
    return jcasType.ll_cas.ll_getStringValue(addr, ((DocumentTextHolder_Type)jcasType).casFeatCode_text);}
    
  /** setter for text - sets  
   * @generated */
  public void setText(String v) {
    if (DocumentTextHolder_Type.featOkTst && ((DocumentTextHolder_Type)jcasType).casFeat_text == null)
      jcasType.jcas.throwFeatMissing("text", "ch.epfl.bbp.uima.types.DocumentTextHolder");
    jcasType.ll_cas.ll_setStringValue(addr, ((DocumentTextHolder_Type)jcasType).casFeatCode_text, v);}    
  }

    