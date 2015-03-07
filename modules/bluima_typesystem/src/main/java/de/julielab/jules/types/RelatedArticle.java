

/* First created by JCasGen Sat Mar 07 22:05:57 CET 2015 */
package de.julielab.jules.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** 
 * Updated by JCasGen Sat Mar 07 22:05:57 CET 2015
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/target/jcasgen/typesystem.xml
 * @generated */
public class RelatedArticle extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(RelatedArticle.class);
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
  protected RelatedArticle() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public RelatedArticle(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public RelatedArticle(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public RelatedArticle(JCas jcas, int begin, int end) {
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
  //* Feature: relatedArticle

  /** getter for relatedArticle - gets 
   * @generated
   * @return value of the feature 
   */
  public String getRelatedArticle() {
    if (RelatedArticle_Type.featOkTst && ((RelatedArticle_Type)jcasType).casFeat_relatedArticle == null)
      jcasType.jcas.throwFeatMissing("relatedArticle", "de.julielab.jules.types.RelatedArticle");
    return jcasType.ll_cas.ll_getStringValue(addr, ((RelatedArticle_Type)jcasType).casFeatCode_relatedArticle);}
    
  /** setter for relatedArticle - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setRelatedArticle(String v) {
    if (RelatedArticle_Type.featOkTst && ((RelatedArticle_Type)jcasType).casFeat_relatedArticle == null)
      jcasType.jcas.throwFeatMissing("relatedArticle", "de.julielab.jules.types.RelatedArticle");
    jcasType.ll_cas.ll_setStringValue(addr, ((RelatedArticle_Type)jcasType).casFeatCode_relatedArticle, v);}    
  }

    