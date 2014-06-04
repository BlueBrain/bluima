

/* First created by JCasGen Mon Feb 17 12:26:53 CET 2014 */
package ch.epfl.bbp.uima.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import de.julielab.jules.types.Annotation;


/** A general verb, that was not found in the biolexicon/Mancu
 * Updated by JCasGen Wed Jun 04 18:01:56 CEST 2014
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/src/main/resources/typeSystem/bbp-types.xml
 * @generated */
public class Verb extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Verb.class);
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
  protected Verb() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public Verb(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public Verb(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public Verb(JCas jcas, int begin, int end) {
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
  //* Feature: isModal

  /** getter for isModal - gets whether this verb has the POS MD (modal), e.g. should, ...
   * @generated
   * @return value of the feature 
   */
  public boolean getIsModal() {
    if (Verb_Type.featOkTst && ((Verb_Type)jcasType).casFeat_isModal == null)
      jcasType.jcas.throwFeatMissing("isModal", "ch.epfl.bbp.uima.types.Verb");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((Verb_Type)jcasType).casFeatCode_isModal);}
    
  /** setter for isModal - sets whether this verb has the POS MD (modal), e.g. should, ... 
   * @generated
   * @param v value to set into the feature 
   */
  public void setIsModal(boolean v) {
    if (Verb_Type.featOkTst && ((Verb_Type)jcasType).casFeat_isModal == null)
      jcasType.jcas.throwFeatMissing("isModal", "ch.epfl.bbp.uima.types.Verb");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((Verb_Type)jcasType).casFeatCode_isModal, v);}    
  }

    