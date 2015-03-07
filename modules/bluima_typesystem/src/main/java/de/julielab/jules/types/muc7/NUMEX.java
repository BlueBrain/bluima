

/* First created by JCasGen Sat Mar 07 22:05:57 CET 2015 */
package de.julielab.jules.types.muc7;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import de.julielab.jules.types.EntityMention;


/** the NUMEX NE in MUC7
 * Updated by JCasGen Sat Mar 07 22:05:57 CET 2015
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/target/jcasgen/typesystem.xml
 * @generated */
public class NUMEX extends EntityMention {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(NUMEX.class);
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
  protected NUMEX() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public NUMEX(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public NUMEX(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public NUMEX(JCas jcas, int begin, int end) {
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
  //* Feature: min

  /** getter for min - gets the head (min) of the NUMED
   * @generated
   * @return value of the feature 
   */
  public String getMin() {
    if (NUMEX_Type.featOkTst && ((NUMEX_Type)jcasType).casFeat_min == null)
      jcasType.jcas.throwFeatMissing("min", "de.julielab.jules.types.muc7.NUMEX");
    return jcasType.ll_cas.ll_getStringValue(addr, ((NUMEX_Type)jcasType).casFeatCode_min);}
    
  /** setter for min - sets the head (min) of the NUMED 
   * @generated
   * @param v value to set into the feature 
   */
  public void setMin(String v) {
    if (NUMEX_Type.featOkTst && ((NUMEX_Type)jcasType).casFeat_min == null)
      jcasType.jcas.throwFeatMissing("min", "de.julielab.jules.types.muc7.NUMEX");
    jcasType.ll_cas.ll_setStringValue(addr, ((NUMEX_Type)jcasType).casFeatCode_min, v);}    
   
    
  //*--------------*
  //* Feature: typeOfNE

  /** getter for typeOfNE - gets the type of the NUMEX
   * @generated
   * @return value of the feature 
   */
  public String getTypeOfNE() {
    if (NUMEX_Type.featOkTst && ((NUMEX_Type)jcasType).casFeat_typeOfNE == null)
      jcasType.jcas.throwFeatMissing("typeOfNE", "de.julielab.jules.types.muc7.NUMEX");
    return jcasType.ll_cas.ll_getStringValue(addr, ((NUMEX_Type)jcasType).casFeatCode_typeOfNE);}
    
  /** setter for typeOfNE - sets the type of the NUMEX 
   * @generated
   * @param v value to set into the feature 
   */
  public void setTypeOfNE(String v) {
    if (NUMEX_Type.featOkTst && ((NUMEX_Type)jcasType).casFeat_typeOfNE == null)
      jcasType.jcas.throwFeatMissing("typeOfNE", "de.julielab.jules.types.muc7.NUMEX");
    jcasType.ll_cas.ll_setStringValue(addr, ((NUMEX_Type)jcasType).casFeatCode_typeOfNE, v);}    
  }

    