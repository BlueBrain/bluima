

/* First created by JCasGen Tue Feb 12 11:02:34 CET 2013 */
package ch.epfl.bbp.uima.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Fri Sep 20 01:02:03 CEST 2013
 * XML source: /Users/richarde/dev/bluebrain/svn_nlp/UIMA/blue_uima/trunk/modules/julielab_typesystem-2.6.8/src/main/resources/typeSystem/bbp-basic-types.xml
 * @generated */
public class Keep extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Keep.class);
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
  protected Keep() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public Keep(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public Keep(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public Keep(JCas jcas, int begin, int end) {
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
  //* Feature: enclosedAnnot

  /** getter for enclosedAnnot - gets 
   * @generated */
  public Annotation getEnclosedAnnot() {
    if (Keep_Type.featOkTst && ((Keep_Type)jcasType).casFeat_enclosedAnnot == null)
      jcasType.jcas.throwFeatMissing("enclosedAnnot", "ch.epfl.bbp.uima.types.Keep");
    return (Annotation)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Keep_Type)jcasType).casFeatCode_enclosedAnnot)));}
    
  /** setter for enclosedAnnot - sets  
   * @generated */
  public void setEnclosedAnnot(Annotation v) {
    if (Keep_Type.featOkTst && ((Keep_Type)jcasType).casFeat_enclosedAnnot == null)
      jcasType.jcas.throwFeatMissing("enclosedAnnot", "ch.epfl.bbp.uima.types.Keep");
    jcasType.ll_cas.ll_setRefValue(addr, ((Keep_Type)jcasType).casFeatCode_enclosedAnnot, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: normalizedText

  /** getter for normalizedText - gets 
   * @generated */
  public String getNormalizedText() {
    if (Keep_Type.featOkTst && ((Keep_Type)jcasType).casFeat_normalizedText == null)
      jcasType.jcas.throwFeatMissing("normalizedText", "ch.epfl.bbp.uima.types.Keep");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Keep_Type)jcasType).casFeatCode_normalizedText);}
    
  /** setter for normalizedText - sets  
   * @generated */
  public void setNormalizedText(String v) {
    if (Keep_Type.featOkTst && ((Keep_Type)jcasType).casFeat_normalizedText == null)
      jcasType.jcas.throwFeatMissing("normalizedText", "ch.epfl.bbp.uima.types.Keep");
    jcasType.ll_cas.ll_setStringValue(addr, ((Keep_Type)jcasType).casFeatCode_normalizedText, v);}    
  }

    