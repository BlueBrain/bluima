

/* First created by JCasGen Wed Oct 19 19:10:28 CEST 2011 */
package de.julielab.jules.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.FSArray;


/** Chains annotations of the same type
 * Updated by JCasGen Mon Feb 17 10:49:41 CET 2014
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/src/main/resources/typeSystem/bbp-types.xml
 * @generated */
public class DiscontinuousAnnotation extends Annotation {
  /** @generated
   * @ordered 
   */
  public final static int typeIndexID = JCasRegistry.register(DiscontinuousAnnotation.class);
  /** @generated
   * @ordered 
   */
  public final static int type = typeIndexID;
  /** @generated  */
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected DiscontinuousAnnotation() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public DiscontinuousAnnotation(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public DiscontinuousAnnotation(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public DiscontinuousAnnotation(JCas jcas, int begin, int end) {
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

  /** getter for value - gets Annotations to be chained.
   * @generated */
  public FSArray getValue() {
    if (DiscontinuousAnnotation_Type.featOkTst && ((DiscontinuousAnnotation_Type)jcasType).casFeat_value == null)
      jcasType.jcas.throwFeatMissing("value", "de.julielab.jules.types.DiscontinuousAnnotation");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((DiscontinuousAnnotation_Type)jcasType).casFeatCode_value)));}
    
  /** setter for value - sets Annotations to be chained. 
   * @generated */
  public void setValue(FSArray v) {
    if (DiscontinuousAnnotation_Type.featOkTst && ((DiscontinuousAnnotation_Type)jcasType).casFeat_value == null)
      jcasType.jcas.throwFeatMissing("value", "de.julielab.jules.types.DiscontinuousAnnotation");
    jcasType.ll_cas.ll_setRefValue(addr, ((DiscontinuousAnnotation_Type)jcasType).casFeatCode_value, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for value - gets an indexed value - Annotations to be chained.
   * @generated */
  public Annotation getValue(int i) {
    if (DiscontinuousAnnotation_Type.featOkTst && ((DiscontinuousAnnotation_Type)jcasType).casFeat_value == null)
      jcasType.jcas.throwFeatMissing("value", "de.julielab.jules.types.DiscontinuousAnnotation");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((DiscontinuousAnnotation_Type)jcasType).casFeatCode_value), i);
    return (Annotation)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((DiscontinuousAnnotation_Type)jcasType).casFeatCode_value), i)));}

  /** indexed setter for value - sets an indexed value - Annotations to be chained.
   * @generated */
  public void setValue(int i, Annotation v) { 
    if (DiscontinuousAnnotation_Type.featOkTst && ((DiscontinuousAnnotation_Type)jcasType).casFeat_value == null)
      jcasType.jcas.throwFeatMissing("value", "de.julielab.jules.types.DiscontinuousAnnotation");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((DiscontinuousAnnotation_Type)jcasType).casFeatCode_value), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((DiscontinuousAnnotation_Type)jcasType).casFeatCode_value), i, jcasType.ll_cas.ll_getFSRef(v));}
  }

    