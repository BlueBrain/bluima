

/* First created by JCasGen Wed Oct 19 19:10:28 CEST 2011 */
package de.julielab.jules.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** 
 * Updated by JCasGen Wed Jun 04 18:01:57 CEST 2014
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/src/main/resources/typeSystem/bbp-types.xml
 * @generated */
public class CoordinationElement extends Annotation {
  /** @generated
   * @ordered 
   */
  public final static int typeIndexID = JCasRegistry.register(CoordinationElement.class);
  /** @generated
   * @ordered 
   */
  public final static int type = typeIndexID;
  /** @generated  */
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected CoordinationElement() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public CoordinationElement(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public CoordinationElement(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public CoordinationElement(JCas jcas, int begin, int end) {
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
  //* Feature: cat

  /** getter for cat - gets 
   * @generated
   * @return value of the feature 
   */
  public String getCat() {
    if (CoordinationElement_Type.featOkTst && ((CoordinationElement_Type)jcasType).casFeat_cat == null)
      jcasType.jcas.throwFeatMissing("cat", "de.julielab.jules.types.CoordinationElement");
    return jcasType.ll_cas.ll_getStringValue(addr, ((CoordinationElement_Type)jcasType).casFeatCode_cat);}
    
  /** setter for cat - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setCat(String v) {
    if (CoordinationElement_Type.featOkTst && ((CoordinationElement_Type)jcasType).casFeat_cat == null)
      jcasType.jcas.throwFeatMissing("cat", "de.julielab.jules.types.CoordinationElement");
    jcasType.ll_cas.ll_setStringValue(addr, ((CoordinationElement_Type)jcasType).casFeatCode_cat, v);}    
  }

    