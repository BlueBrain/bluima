

/* First created by JCasGen Sat Mar 07 22:05:56 CET 2015 */
package ch.epfl.bbp.uima.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import de.julielab.jules.types.Annotation;


/** A general verb, that was not found in the biolexicon/Mancu
 * Updated by JCasGen Sat Mar 07 22:05:56 CET 2015
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/target/jcasgen/typesystem.xml
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
  /** @generated
   * @return index of the type  
   */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected Verb() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Verb(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Verb(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
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

    