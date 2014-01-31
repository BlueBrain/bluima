

/* First created by JCasGen Wed Oct 19 19:10:41 CEST 2011 */
package de.julielab.jules.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.FSArray;


/** 
 * Updated by JCasGen Fri Dec 09 11:59:26 CET 2011
 * XML source: /Users/richarde/dev/bluebrain/svn_nlp/UIMA/blue_uima/trunk/julielab_typesystem-2.6.8/src/main/resources/typeSystem/julie-document-meta-types.xml
 * @generated */
public class RelatedArticleList extends Annotation {
  /** @generated
   * @ordered 
   */
  public final static int typeIndexID = JCasRegistry.register(RelatedArticleList.class);
  /** @generated
   * @ordered 
   */
  public final static int type = typeIndexID;
  /** @generated  */
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected RelatedArticleList() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public RelatedArticleList(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public RelatedArticleList(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public RelatedArticleList(JCas jcas, int begin, int end) {
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
  //* Feature: relatedArticles

  /** getter for relatedArticles - gets 
   * @generated */
  public FSArray getRelatedArticles() {
    if (RelatedArticleList_Type.featOkTst && ((RelatedArticleList_Type)jcasType).casFeat_relatedArticles == null)
      jcasType.jcas.throwFeatMissing("relatedArticles", "de.julielab.jules.types.RelatedArticleList");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((RelatedArticleList_Type)jcasType).casFeatCode_relatedArticles)));}
    
  /** setter for relatedArticles - sets  
   * @generated */
  public void setRelatedArticles(FSArray v) {
    if (RelatedArticleList_Type.featOkTst && ((RelatedArticleList_Type)jcasType).casFeat_relatedArticles == null)
      jcasType.jcas.throwFeatMissing("relatedArticles", "de.julielab.jules.types.RelatedArticleList");
    jcasType.ll_cas.ll_setRefValue(addr, ((RelatedArticleList_Type)jcasType).casFeatCode_relatedArticles, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for relatedArticles - gets an indexed value - 
   * @generated */
  public RelatedArticle getRelatedArticles(int i) {
    if (RelatedArticleList_Type.featOkTst && ((RelatedArticleList_Type)jcasType).casFeat_relatedArticles == null)
      jcasType.jcas.throwFeatMissing("relatedArticles", "de.julielab.jules.types.RelatedArticleList");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((RelatedArticleList_Type)jcasType).casFeatCode_relatedArticles), i);
    return (RelatedArticle)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((RelatedArticleList_Type)jcasType).casFeatCode_relatedArticles), i)));}

  /** indexed setter for relatedArticles - sets an indexed value - 
   * @generated */
  public void setRelatedArticles(int i, RelatedArticle v) { 
    if (RelatedArticleList_Type.featOkTst && ((RelatedArticleList_Type)jcasType).casFeat_relatedArticles == null)
      jcasType.jcas.throwFeatMissing("relatedArticles", "de.julielab.jules.types.RelatedArticleList");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((RelatedArticleList_Type)jcasType).casFeatCode_relatedArticles), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((RelatedArticleList_Type)jcasType).casFeatCode_relatedArticles), i, jcasType.ll_cas.ll_getFSRef(v));}
  }

    