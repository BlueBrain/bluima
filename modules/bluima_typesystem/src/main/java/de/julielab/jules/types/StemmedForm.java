

/* First created by JCasGen Sat Mar 07 22:05:57 CET 2015 */
package de.julielab.jules.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** Represents a grammatical morpheme, identifies the morpheme's base form for the current token, activat- for activation. Different forms can be produced by stemmers.
 * Updated by JCasGen Sat Mar 07 22:05:57 CET 2015
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/target/jcasgen/typesystem.xml
 * @generated */
public class StemmedForm extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(StemmedForm.class);
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
  protected StemmedForm() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public StemmedForm(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public StemmedForm(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public StemmedForm(JCas jcas, int begin, int end) {
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
  //* Feature: value

  /** getter for value - gets The value of the stemmed form, C
   * @generated
   * @return value of the feature 
   */
  public String getValue() {
    if (StemmedForm_Type.featOkTst && ((StemmedForm_Type)jcasType).casFeat_value == null)
      jcasType.jcas.throwFeatMissing("value", "de.julielab.jules.types.StemmedForm");
    return jcasType.ll_cas.ll_getStringValue(addr, ((StemmedForm_Type)jcasType).casFeatCode_value);}
    
  /** setter for value - sets The value of the stemmed form, C 
   * @generated
   * @param v value to set into the feature 
   */
  public void setValue(String v) {
    if (StemmedForm_Type.featOkTst && ((StemmedForm_Type)jcasType).casFeat_value == null)
      jcasType.jcas.throwFeatMissing("value", "de.julielab.jules.types.StemmedForm");
    jcasType.ll_cas.ll_setStringValue(addr, ((StemmedForm_Type)jcasType).casFeatCode_value, v);}    
  }

    