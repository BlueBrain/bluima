

/* First created by JCasGen Mon Feb 17 12:26:53 CET 2014 */
package ch.epfl.bbp.uima.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Wed Jun 04 18:01:56 CEST 2014
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/src/main/resources/typeSystem/bbp-types.xml
 * @generated */
public class TopicToken extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(TopicToken.class);
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
  protected TopicToken() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public TopicToken(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public TopicToken(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public TopicToken(JCas jcas, int begin, int end) {
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
  //* Feature: enclosingAnnot

  /** getter for enclosingAnnot - gets 
   * @generated
   * @return value of the feature 
   */
  public Annotation getEnclosingAnnot() {
    if (TopicToken_Type.featOkTst && ((TopicToken_Type)jcasType).casFeat_enclosingAnnot == null)
      jcasType.jcas.throwFeatMissing("enclosingAnnot", "ch.epfl.bbp.uima.types.TopicToken");
    return (Annotation)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((TopicToken_Type)jcasType).casFeatCode_enclosingAnnot)));}
    
  /** setter for enclosingAnnot - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setEnclosingAnnot(Annotation v) {
    if (TopicToken_Type.featOkTst && ((TopicToken_Type)jcasType).casFeat_enclosingAnnot == null)
      jcasType.jcas.throwFeatMissing("enclosingAnnot", "ch.epfl.bbp.uima.types.TopicToken");
    jcasType.ll_cas.ll_setRefValue(addr, ((TopicToken_Type)jcasType).casFeatCode_enclosingAnnot, jcasType.ll_cas.ll_getFSRef(v));}    
  }

    