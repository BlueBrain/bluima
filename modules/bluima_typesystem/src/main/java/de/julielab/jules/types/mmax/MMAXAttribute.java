

/* First created by JCasGen Sat Mar 07 22:05:57 CET 2015 */
package de.julielab.jules.types.mmax;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** MMAX annotation attribute.
 * Updated by JCasGen Sat Mar 07 22:05:57 CET 2015
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/target/jcasgen/typesystem.xml
 * @generated */
public class MMAXAttribute extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(MMAXAttribute.class);
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
  protected MMAXAttribute() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public MMAXAttribute(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public MMAXAttribute(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public MMAXAttribute(JCas jcas, int begin, int end) {
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

  /** getter for name - gets Name of the MMAX attribute.
   * @generated
   * @return value of the feature 
   */
  public String getName() {
    if (MMAXAttribute_Type.featOkTst && ((MMAXAttribute_Type)jcasType).casFeat_name == null)
      jcasType.jcas.throwFeatMissing("name", "de.julielab.jules.types.mmax.MMAXAttribute");
    return jcasType.ll_cas.ll_getStringValue(addr, ((MMAXAttribute_Type)jcasType).casFeatCode_name);}
    
  /** setter for name - sets Name of the MMAX attribute. 
   * @generated
   * @param v value to set into the feature 
   */
  public void setName(String v) {
    if (MMAXAttribute_Type.featOkTst && ((MMAXAttribute_Type)jcasType).casFeat_name == null)
      jcasType.jcas.throwFeatMissing("name", "de.julielab.jules.types.mmax.MMAXAttribute");
    jcasType.ll_cas.ll_setStringValue(addr, ((MMAXAttribute_Type)jcasType).casFeatCode_name, v);}    
   
    
  //*--------------*
  //* Feature: value

  /** getter for value - gets Value of the MMAX attribute
   * @generated
   * @return value of the feature 
   */
  public String getValue() {
    if (MMAXAttribute_Type.featOkTst && ((MMAXAttribute_Type)jcasType).casFeat_value == null)
      jcasType.jcas.throwFeatMissing("value", "de.julielab.jules.types.mmax.MMAXAttribute");
    return jcasType.ll_cas.ll_getStringValue(addr, ((MMAXAttribute_Type)jcasType).casFeatCode_value);}
    
  /** setter for value - sets Value of the MMAX attribute 
   * @generated
   * @param v value to set into the feature 
   */
  public void setValue(String v) {
    if (MMAXAttribute_Type.featOkTst && ((MMAXAttribute_Type)jcasType).casFeat_value == null)
      jcasType.jcas.throwFeatMissing("value", "de.julielab.jules.types.mmax.MMAXAttribute");
    jcasType.ll_cas.ll_setStringValue(addr, ((MMAXAttribute_Type)jcasType).casFeatCode_value, v);}    
  }

    