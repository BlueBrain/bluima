

/* First created by JCasGen Sat Mar 07 22:05:57 CET 2015 */
package de.julielab.jules.types.ace;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** 
 * Updated by JCasGen Sat Mar 07 22:05:57 CET 2015
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/target/jcasgen/typesystem.xml
 * @generated */
public class EventArgument extends Argument {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(EventArgument.class);
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
  protected EventArgument() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public EventArgument(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public EventArgument(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public EventArgument(JCas jcas, int begin, int end) {
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
  //* Feature: ace_type

  /** getter for ace_type - gets 
   * @generated
   * @return value of the feature 
   */
  public String getAce_type() {
    if (EventArgument_Type.featOkTst && ((EventArgument_Type)jcasType).casFeat_ace_type == null)
      jcasType.jcas.throwFeatMissing("ace_type", "de.julielab.jules.types.ace.EventArgument");
    return jcasType.ll_cas.ll_getStringValue(addr, ((EventArgument_Type)jcasType).casFeatCode_ace_type);}
    
  /** setter for ace_type - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setAce_type(String v) {
    if (EventArgument_Type.featOkTst && ((EventArgument_Type)jcasType).casFeat_ace_type == null)
      jcasType.jcas.throwFeatMissing("ace_type", "de.julielab.jules.types.ace.EventArgument");
    jcasType.ll_cas.ll_setStringValue(addr, ((EventArgument_Type)jcasType).casFeatCode_ace_type, v);}    
   
    
  //*--------------*
  //* Feature: refid

  /** getter for refid - gets 
   * @generated
   * @return value of the feature 
   */
  public String getRefid() {
    if (EventArgument_Type.featOkTst && ((EventArgument_Type)jcasType).casFeat_refid == null)
      jcasType.jcas.throwFeatMissing("refid", "de.julielab.jules.types.ace.EventArgument");
    return jcasType.ll_cas.ll_getStringValue(addr, ((EventArgument_Type)jcasType).casFeatCode_refid);}
    
  /** setter for refid - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setRefid(String v) {
    if (EventArgument_Type.featOkTst && ((EventArgument_Type)jcasType).casFeat_refid == null)
      jcasType.jcas.throwFeatMissing("refid", "de.julielab.jules.types.ace.EventArgument");
    jcasType.ll_cas.ll_setStringValue(addr, ((EventArgument_Type)jcasType).casFeatCode_refid, v);}    
  }

    