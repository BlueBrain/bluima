

/* First created by JCasGen Wed Oct 19 19:10:41 CEST 2011 */
package de.julielab.jules.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** 
 * Updated by JCasGen Fri Dec 09 11:59:26 CET 2011
 * XML source: /Users/richarde/dev/bluebrain/svn_nlp/UIMA/blue_uima/trunk/julielab_typesystem-2.6.8/src/main/resources/typeSystem/julie-document-meta-types.xml
 * @generated */
public class RelatedArticle extends Annotation {
  /** @generated
   * @ordered 
   */
  public final static int typeIndexID = JCasRegistry.register(RelatedArticle.class);
  /** @generated
   * @ordered 
   */
  public final static int type = typeIndexID;
  /** @generated  */
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected RelatedArticle() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public RelatedArticle(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public RelatedArticle(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public RelatedArticle(JCas jcas, int begin, int end) {
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
  //* Feature: relatedArticle

  /** getter for relatedArticle - gets 
   * @generated */
  public String getRelatedArticle() {
    if (RelatedArticle_Type.featOkTst && ((RelatedArticle_Type)jcasType).casFeat_relatedArticle == null)
      jcasType.jcas.throwFeatMissing("relatedArticle", "de.julielab.jules.types.RelatedArticle");
    return jcasType.ll_cas.ll_getStringValue(addr, ((RelatedArticle_Type)jcasType).casFeatCode_relatedArticle);}
    
  /** setter for relatedArticle - sets  
   * @generated */
  public void setRelatedArticle(String v) {
    if (RelatedArticle_Type.featOkTst && ((RelatedArticle_Type)jcasType).casFeat_relatedArticle == null)
      jcasType.jcas.throwFeatMissing("relatedArticle", "de.julielab.jules.types.RelatedArticle");
    jcasType.ll_cas.ll_setStringValue(addr, ((RelatedArticle_Type)jcasType).casFeatCode_relatedArticle, v);}    
  }

    