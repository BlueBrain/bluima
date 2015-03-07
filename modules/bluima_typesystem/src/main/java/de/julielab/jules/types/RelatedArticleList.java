

/* First created by JCasGen Sat Mar 07 22:05:57 CET 2015 */
package de.julielab.jules.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.FSArray;


/** 
 * Updated by JCasGen Sat Mar 07 22:05:57 CET 2015
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/target/jcasgen/typesystem.xml
 * @generated */
public class RelatedArticleList extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(RelatedArticleList.class);
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
  protected RelatedArticleList() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public RelatedArticleList(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public RelatedArticleList(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public RelatedArticleList(JCas jcas, int begin, int end) {
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
  //* Feature: relatedArticles

  /** getter for relatedArticles - gets 
   * @generated
   * @return value of the feature 
   */
  public FSArray getRelatedArticles() {
    if (RelatedArticleList_Type.featOkTst && ((RelatedArticleList_Type)jcasType).casFeat_relatedArticles == null)
      jcasType.jcas.throwFeatMissing("relatedArticles", "de.julielab.jules.types.RelatedArticleList");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((RelatedArticleList_Type)jcasType).casFeatCode_relatedArticles)));}
    
  /** setter for relatedArticles - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setRelatedArticles(FSArray v) {
    if (RelatedArticleList_Type.featOkTst && ((RelatedArticleList_Type)jcasType).casFeat_relatedArticles == null)
      jcasType.jcas.throwFeatMissing("relatedArticles", "de.julielab.jules.types.RelatedArticleList");
    jcasType.ll_cas.ll_setRefValue(addr, ((RelatedArticleList_Type)jcasType).casFeatCode_relatedArticles, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for relatedArticles - gets an indexed value - 
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public RelatedArticle getRelatedArticles(int i) {
    if (RelatedArticleList_Type.featOkTst && ((RelatedArticleList_Type)jcasType).casFeat_relatedArticles == null)
      jcasType.jcas.throwFeatMissing("relatedArticles", "de.julielab.jules.types.RelatedArticleList");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((RelatedArticleList_Type)jcasType).casFeatCode_relatedArticles), i);
    return (RelatedArticle)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((RelatedArticleList_Type)jcasType).casFeatCode_relatedArticles), i)));}

  /** indexed setter for relatedArticles - sets an indexed value - 
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setRelatedArticles(int i, RelatedArticle v) { 
    if (RelatedArticleList_Type.featOkTst && ((RelatedArticleList_Type)jcasType).casFeat_relatedArticles == null)
      jcasType.jcas.throwFeatMissing("relatedArticles", "de.julielab.jules.types.RelatedArticleList");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((RelatedArticleList_Type)jcasType).casFeatCode_relatedArticles), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((RelatedArticleList_Type)jcasType).casFeatCode_relatedArticles), i, jcasType.ll_cas.ll_getFSRef(v));}
  }

    