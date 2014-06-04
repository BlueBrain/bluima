

/* First created by JCasGen Wed Oct 19 19:10:28 CEST 2011 */
package de.julielab.jules.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** Represents a morpheme of a compound.
 * Updated by JCasGen Wed Jun 04 18:01:58 CEST 2014
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/src/main/resources/typeSystem/bbp-types.xml
 * @generated */
public class Simplex extends Annotation {
  /** @generated
   * @ordered 
   */
  public final static int typeIndexID = JCasRegistry.register(Simplex.class);
  /** @generated
   * @ordered 
   */
  public final static int type = typeIndexID;
  /** @generated  */
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected Simplex() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public Simplex(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public Simplex(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public Simplex(JCas jcas, int begin, int end) {
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
  //* Feature: lemma

  /** getter for lemma - gets The lemma of the simplex, C
   * @generated
   * @return value of the feature 
   */
  public Lemma getLemma() {
    if (Simplex_Type.featOkTst && ((Simplex_Type)jcasType).casFeat_lemma == null)
      jcasType.jcas.throwFeatMissing("lemma", "de.julielab.jules.types.Simplex");
    return (Lemma)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Simplex_Type)jcasType).casFeatCode_lemma)));}
    
  /** setter for lemma - sets The lemma of the simplex, C 
   * @generated
   * @param v value to set into the feature 
   */
  public void setLemma(Lemma v) {
    if (Simplex_Type.featOkTst && ((Simplex_Type)jcasType).casFeat_lemma == null)
      jcasType.jcas.throwFeatMissing("lemma", "de.julielab.jules.types.Simplex");
    jcasType.ll_cas.ll_setRefValue(addr, ((Simplex_Type)jcasType).casFeatCode_lemma, jcasType.ll_cas.ll_getFSRef(v));}    
  }

    