

/* First created by JCasGen Wed Oct 19 19:10:28 CEST 2011 */
package de.julielab.jules.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** Lemma is the canonical form of a lexeme. Lemma can be retrieved from lexicon or be produced by lemmatizer.
 * Updated by JCasGen Wed Jun 04 18:01:58 CEST 2014
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/src/main/resources/typeSystem/bbp-types.xml
 * @generated */
public class Lemma extends Annotation {
  /** @generated
   * @ordered 
   */
  public final static int typeIndexID = JCasRegistry.register(Lemma.class);
  /** @generated
   * @ordered 
   */
  public final static int type = typeIndexID;
  /** @generated  */
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected Lemma() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public Lemma(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public Lemma(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public Lemma(JCas jcas, int begin, int end) {
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
  private void readObject() {}
     
 
    
  //*--------------*
  //* Feature: value

  /** getter for value - gets The value of lemma,C
   * @generated
   * @return value of the feature 
   */
  public String getValue() {
    if (Lemma_Type.featOkTst && ((Lemma_Type)jcasType).casFeat_value == null)
      jcasType.jcas.throwFeatMissing("value", "de.julielab.jules.types.Lemma");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Lemma_Type)jcasType).casFeatCode_value);}
    
  /** setter for value - sets The value of lemma,C 
   * @generated
   * @param v value to set into the feature 
   */
  public void setValue(String v) {
    if (Lemma_Type.featOkTst && ((Lemma_Type)jcasType).casFeat_value == null)
      jcasType.jcas.throwFeatMissing("value", "de.julielab.jules.types.Lemma");
    jcasType.ll_cas.ll_setStringValue(addr, ((Lemma_Type)jcasType).casFeatCode_value, v);}    
  }

    