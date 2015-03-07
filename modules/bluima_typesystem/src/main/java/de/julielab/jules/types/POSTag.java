

/* First created by JCasGen Sat Mar 07 22:05:57 CET 2015 */
package de.julielab.jules.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** POS is a linguistic category of words (tokens) that are defined by their particular syntactic/morphological behaviours (e.g. noun, verb).
 * Updated by JCasGen Sat Mar 07 22:05:57 CET 2015
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/target/jcasgen/typesystem.xml
 * @generated */
public class POSTag extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(POSTag.class);
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
  protected POSTag() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public POSTag(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public POSTag(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public POSTag(JCas jcas, int begin, int end) {
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
  //* Feature: tagsetId

  /** getter for tagsetId - gets Every POS tagset (see subtypes) has an identifier, C
   * @generated
   * @return value of the feature 
   */
  public String getTagsetId() {
    if (POSTag_Type.featOkTst && ((POSTag_Type)jcasType).casFeat_tagsetId == null)
      jcasType.jcas.throwFeatMissing("tagsetId", "de.julielab.jules.types.POSTag");
    return jcasType.ll_cas.ll_getStringValue(addr, ((POSTag_Type)jcasType).casFeatCode_tagsetId);}
    
  /** setter for tagsetId - sets Every POS tagset (see subtypes) has an identifier, C 
   * @generated
   * @param v value to set into the feature 
   */
  public void setTagsetId(String v) {
    if (POSTag_Type.featOkTst && ((POSTag_Type)jcasType).casFeat_tagsetId == null)
      jcasType.jcas.throwFeatMissing("tagsetId", "de.julielab.jules.types.POSTag");
    jcasType.ll_cas.ll_setStringValue(addr, ((POSTag_Type)jcasType).casFeatCode_tagsetId, v);}    
   
    
  //*--------------*
  //* Feature: value

  /** getter for value - gets The value of POS (NN, JJ etc.)
   * @generated
   * @return value of the feature 
   */
  public String getValue() {
    if (POSTag_Type.featOkTst && ((POSTag_Type)jcasType).casFeat_value == null)
      jcasType.jcas.throwFeatMissing("value", "de.julielab.jules.types.POSTag");
    return jcasType.ll_cas.ll_getStringValue(addr, ((POSTag_Type)jcasType).casFeatCode_value);}
    
  /** setter for value - sets The value of POS (NN, JJ etc.) 
   * @generated
   * @param v value to set into the feature 
   */
  public void setValue(String v) {
    if (POSTag_Type.featOkTst && ((POSTag_Type)jcasType).casFeat_value == null)
      jcasType.jcas.throwFeatMissing("value", "de.julielab.jules.types.POSTag");
    jcasType.ll_cas.ll_setStringValue(addr, ((POSTag_Type)jcasType).casFeatCode_value, v);}    
  }

    