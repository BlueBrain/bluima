

/* First created by JCasGen Sat Mar 07 22:05:57 CET 2015 */
package de.julielab.jules.types.mmax;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.FSArray;
import org.apache.uima.jcas.tcas.Annotation;


/** MMAX pointers specify a relation between two MMAX annotations.
 * Updated by JCasGen Sat Mar 07 22:05:57 CET 2015
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/target/jcasgen/typesystem.xml
 * @generated */
public class MMAXPointer extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(MMAXPointer.class);
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
  protected MMAXPointer() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public MMAXPointer(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public MMAXPointer(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public MMAXPointer(JCas jcas, int begin, int end) {
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
  //* Feature: name

  /** getter for name - gets Name of the MMAX pointer.
   * @generated
   * @return value of the feature 
   */
  public String getName() {
    if (MMAXPointer_Type.featOkTst && ((MMAXPointer_Type)jcasType).casFeat_name == null)
      jcasType.jcas.throwFeatMissing("name", "de.julielab.jules.types.mmax.MMAXPointer");
    return jcasType.ll_cas.ll_getStringValue(addr, ((MMAXPointer_Type)jcasType).casFeatCode_name);}
    
  /** setter for name - sets Name of the MMAX pointer. 
   * @generated
   * @param v value to set into the feature 
   */
  public void setName(String v) {
    if (MMAXPointer_Type.featOkTst && ((MMAXPointer_Type)jcasType).casFeat_name == null)
      jcasType.jcas.throwFeatMissing("name", "de.julielab.jules.types.mmax.MMAXPointer");
    jcasType.ll_cas.ll_setStringValue(addr, ((MMAXPointer_Type)jcasType).casFeatCode_name, v);}    
   
    
  //*--------------*
  //* Feature: targetList

  /** getter for targetList - gets The MMAX annotations the pointer points at.
   * @generated
   * @return value of the feature 
   */
  public FSArray getTargetList() {
    if (MMAXPointer_Type.featOkTst && ((MMAXPointer_Type)jcasType).casFeat_targetList == null)
      jcasType.jcas.throwFeatMissing("targetList", "de.julielab.jules.types.mmax.MMAXPointer");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((MMAXPointer_Type)jcasType).casFeatCode_targetList)));}
    
  /** setter for targetList - sets The MMAX annotations the pointer points at. 
   * @generated
   * @param v value to set into the feature 
   */
  public void setTargetList(FSArray v) {
    if (MMAXPointer_Type.featOkTst && ((MMAXPointer_Type)jcasType).casFeat_targetList == null)
      jcasType.jcas.throwFeatMissing("targetList", "de.julielab.jules.types.mmax.MMAXPointer");
    jcasType.ll_cas.ll_setRefValue(addr, ((MMAXPointer_Type)jcasType).casFeatCode_targetList, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for targetList - gets an indexed value - The MMAX annotations the pointer points at.
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public MMAXAnnotation getTargetList(int i) {
    if (MMAXPointer_Type.featOkTst && ((MMAXPointer_Type)jcasType).casFeat_targetList == null)
      jcasType.jcas.throwFeatMissing("targetList", "de.julielab.jules.types.mmax.MMAXPointer");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((MMAXPointer_Type)jcasType).casFeatCode_targetList), i);
    return (MMAXAnnotation)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((MMAXPointer_Type)jcasType).casFeatCode_targetList), i)));}

  /** indexed setter for targetList - sets an indexed value - The MMAX annotations the pointer points at.
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setTargetList(int i, MMAXAnnotation v) { 
    if (MMAXPointer_Type.featOkTst && ((MMAXPointer_Type)jcasType).casFeat_targetList == null)
      jcasType.jcas.throwFeatMissing("targetList", "de.julielab.jules.types.mmax.MMAXPointer");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((MMAXPointer_Type)jcasType).casFeatCode_targetList), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((MMAXPointer_Type)jcasType).casFeatCode_targetList), i, jcasType.ll_cas.ll_getFSRef(v));}
  }

    