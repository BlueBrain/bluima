

/* First created by JCasGen Sat Mar 07 22:05:57 CET 2015 */
package de.julielab.jules.types.ace;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.FSArray;


/** 
 * Updated by JCasGen Sat Mar 07 22:05:57 CET 2015
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/target/jcasgen/typesystem.xml
 * @generated */
public class EntityAttribute extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(EntityAttribute.class);
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
  protected EntityAttribute() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public EntityAttribute(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public EntityAttribute(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public EntityAttribute(JCas jcas, int begin, int end) {
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
  //* Feature: names

  /** getter for names - gets 
   * @generated
   * @return value of the feature 
   */
  public FSArray getNames() {
    if (EntityAttribute_Type.featOkTst && ((EntityAttribute_Type)jcasType).casFeat_names == null)
      jcasType.jcas.throwFeatMissing("names", "de.julielab.jules.types.ace.EntityAttribute");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((EntityAttribute_Type)jcasType).casFeatCode_names)));}
    
  /** setter for names - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setNames(FSArray v) {
    if (EntityAttribute_Type.featOkTst && ((EntityAttribute_Type)jcasType).casFeat_names == null)
      jcasType.jcas.throwFeatMissing("names", "de.julielab.jules.types.ace.EntityAttribute");
    jcasType.ll_cas.ll_setRefValue(addr, ((EntityAttribute_Type)jcasType).casFeatCode_names, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for names - gets an indexed value - 
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public Name getNames(int i) {
    if (EntityAttribute_Type.featOkTst && ((EntityAttribute_Type)jcasType).casFeat_names == null)
      jcasType.jcas.throwFeatMissing("names", "de.julielab.jules.types.ace.EntityAttribute");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((EntityAttribute_Type)jcasType).casFeatCode_names), i);
    return (Name)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((EntityAttribute_Type)jcasType).casFeatCode_names), i)));}

  /** indexed setter for names - sets an indexed value - 
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setNames(int i, Name v) { 
    if (EntityAttribute_Type.featOkTst && ((EntityAttribute_Type)jcasType).casFeat_names == null)
      jcasType.jcas.throwFeatMissing("names", "de.julielab.jules.types.ace.EntityAttribute");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((EntityAttribute_Type)jcasType).casFeatCode_names), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((EntityAttribute_Type)jcasType).casFeatCode_names), i, jcasType.ll_cas.ll_getFSRef(v));}
  }

    