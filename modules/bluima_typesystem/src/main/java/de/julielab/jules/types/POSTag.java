

/* First created by JCasGen Wed Oct 19 19:10:28 CEST 2011 */
package de.julielab.jules.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** POS is a linguistic category of words (tokens) that are defined by their particular syntactic/morphological behaviours (e.g. noun, verb).
 * Updated by JCasGen Mon Feb 17 22:12:57 CET 2014
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/src/main/resources/typeSystem/bbp-types.xml
 * @generated */
public class POSTag extends Annotation {
  /** @generated
   * @ordered 
   */
  public final static int typeIndexID = JCasRegistry.register(POSTag.class);
  /** @generated
   * @ordered 
   */
  public final static int type = typeIndexID;
  /** @generated  */
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected POSTag() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public POSTag(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public POSTag(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public POSTag(JCas jcas, int begin, int end) {
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
  //* Feature: tagsetId

  /** getter for tagsetId - gets Every POS tagset (see subtypes) has an identifier, C
   * @generated */
  public String getTagsetId() {
    if (POSTag_Type.featOkTst && ((POSTag_Type)jcasType).casFeat_tagsetId == null)
      jcasType.jcas.throwFeatMissing("tagsetId", "de.julielab.jules.types.POSTag");
    return jcasType.ll_cas.ll_getStringValue(addr, ((POSTag_Type)jcasType).casFeatCode_tagsetId);}
    
  /** setter for tagsetId - sets Every POS tagset (see subtypes) has an identifier, C 
   * @generated */
  public void setTagsetId(String v) {
    if (POSTag_Type.featOkTst && ((POSTag_Type)jcasType).casFeat_tagsetId == null)
      jcasType.jcas.throwFeatMissing("tagsetId", "de.julielab.jules.types.POSTag");
    jcasType.ll_cas.ll_setStringValue(addr, ((POSTag_Type)jcasType).casFeatCode_tagsetId, v);}    
   
    
  //*--------------*
  //* Feature: value

  /** getter for value - gets The value of POS (NN, JJ etc.)
   * @generated */
  public String getValue() {
    if (POSTag_Type.featOkTst && ((POSTag_Type)jcasType).casFeat_value == null)
      jcasType.jcas.throwFeatMissing("value", "de.julielab.jules.types.POSTag");
    return jcasType.ll_cas.ll_getStringValue(addr, ((POSTag_Type)jcasType).casFeatCode_value);}
    
  /** setter for value - sets The value of POS (NN, JJ etc.) 
   * @generated */
  public void setValue(String v) {
    if (POSTag_Type.featOkTst && ((POSTag_Type)jcasType).casFeat_value == null)
      jcasType.jcas.throwFeatMissing("value", "de.julielab.jules.types.POSTag");
    jcasType.ll_cas.ll_setStringValue(addr, ((POSTag_Type)jcasType).casFeatCode_value, v);}    
  }

    