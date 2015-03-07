

/* First created by JCasGen Sat Mar 07 22:05:57 CET 2015 */
package de.julielab.jules.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.FSArray;


/** Chains annotations of the same type
 * Updated by JCasGen Sat Mar 07 22:05:57 CET 2015
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/target/jcasgen/typesystem.xml
 * @generated */
public class DiscontinuousAnnotation extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(DiscontinuousAnnotation.class);
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
  protected DiscontinuousAnnotation() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public DiscontinuousAnnotation(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public DiscontinuousAnnotation(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public DiscontinuousAnnotation(JCas jcas, int begin, int end) {
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

  /** getter for value - gets Annotations to be chained.
   * @generated
   * @return value of the feature 
   */
  public FSArray getValue() {
    if (DiscontinuousAnnotation_Type.featOkTst && ((DiscontinuousAnnotation_Type)jcasType).casFeat_value == null)
      jcasType.jcas.throwFeatMissing("value", "de.julielab.jules.types.DiscontinuousAnnotation");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((DiscontinuousAnnotation_Type)jcasType).casFeatCode_value)));}
    
  /** setter for value - sets Annotations to be chained. 
   * @generated
   * @param v value to set into the feature 
   */
  public void setValue(FSArray v) {
    if (DiscontinuousAnnotation_Type.featOkTst && ((DiscontinuousAnnotation_Type)jcasType).casFeat_value == null)
      jcasType.jcas.throwFeatMissing("value", "de.julielab.jules.types.DiscontinuousAnnotation");
    jcasType.ll_cas.ll_setRefValue(addr, ((DiscontinuousAnnotation_Type)jcasType).casFeatCode_value, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for value - gets an indexed value - Annotations to be chained.
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public Annotation getValue(int i) {
    if (DiscontinuousAnnotation_Type.featOkTst && ((DiscontinuousAnnotation_Type)jcasType).casFeat_value == null)
      jcasType.jcas.throwFeatMissing("value", "de.julielab.jules.types.DiscontinuousAnnotation");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((DiscontinuousAnnotation_Type)jcasType).casFeatCode_value), i);
    return (Annotation)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((DiscontinuousAnnotation_Type)jcasType).casFeatCode_value), i)));}

  /** indexed setter for value - sets an indexed value - Annotations to be chained.
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setValue(int i, Annotation v) { 
    if (DiscontinuousAnnotation_Type.featOkTst && ((DiscontinuousAnnotation_Type)jcasType).casFeat_value == null)
      jcasType.jcas.throwFeatMissing("value", "de.julielab.jules.types.DiscontinuousAnnotation");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((DiscontinuousAnnotation_Type)jcasType).casFeatCode_value), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((DiscontinuousAnnotation_Type)jcasType).casFeatCode_value), i, jcasType.ll_cas.ll_getFSRef(v));}
  }

    