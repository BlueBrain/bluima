

/* First created by JCasGen Wed Oct 19 19:10:28 CEST 2011 */
package de.julielab.jules.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** Represents a grammatical morpheme, identifies the morpheme's base form for the current token, activat- for activation. Different forms can be produced by stemmers.
 * Updated by JCasGen Mon Oct 21 13:03:30 CEST 2013
 * XML source: /Users/richarde/dev/bluebrain/svn_nlp/UIMA/blue_uima/trunk/modules/julielab_typesystem-2.6.8/src/main/resources/typeSystem/bbp-semantics-biology-types.xml
 * @generated */
public class StemmedForm extends Annotation {
  /** @generated
   * @ordered 
   */
  public final static int typeIndexID = JCasRegistry.register(StemmedForm.class);
  /** @generated
   * @ordered 
   */
  public final static int type = typeIndexID;
  /** @generated  */
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected StemmedForm() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public StemmedForm(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public StemmedForm(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public StemmedForm(JCas jcas, int begin, int end) {
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
  //* Feature: value

  /** getter for value - gets The value of the stemmed form, C
   * @generated */
  public String getValue() {
    if (StemmedForm_Type.featOkTst && ((StemmedForm_Type)jcasType).casFeat_value == null)
      jcasType.jcas.throwFeatMissing("value", "de.julielab.jules.types.StemmedForm");
    return jcasType.ll_cas.ll_getStringValue(addr, ((StemmedForm_Type)jcasType).casFeatCode_value);}
    
  /** setter for value - sets The value of the stemmed form, C 
   * @generated */
  public void setValue(String v) {
    if (StemmedForm_Type.featOkTst && ((StemmedForm_Type)jcasType).casFeat_value == null)
      jcasType.jcas.throwFeatMissing("value", "de.julielab.jules.types.StemmedForm");
    jcasType.ll_cas.ll_setStringValue(addr, ((StemmedForm_Type)jcasType).casFeatCode_value, v);}    
  }

    