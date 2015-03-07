

/* First created by JCasGen Sat Mar 07 22:05:57 CET 2015 */
package de.julielab.jules.types.ace;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** 
 * Updated by JCasGen Sat Mar 07 22:05:57 CET 2015
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/target/jcasgen/typesystem.xml
 * @generated */
public class RelationArgument extends Argument {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(RelationArgument.class);
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
  protected RelationArgument() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public RelationArgument(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public RelationArgument(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public RelationArgument(JCas jcas, int begin, int end) {
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
  //* Feature: refid

  /** getter for refid - gets 
   * @generated
   * @return value of the feature 
   */
  public String getRefid() {
    if (RelationArgument_Type.featOkTst && ((RelationArgument_Type)jcasType).casFeat_refid == null)
      jcasType.jcas.throwFeatMissing("refid", "de.julielab.jules.types.ace.RelationArgument");
    return jcasType.ll_cas.ll_getStringValue(addr, ((RelationArgument_Type)jcasType).casFeatCode_refid);}
    
  /** setter for refid - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setRefid(String v) {
    if (RelationArgument_Type.featOkTst && ((RelationArgument_Type)jcasType).casFeat_refid == null)
      jcasType.jcas.throwFeatMissing("refid", "de.julielab.jules.types.ace.RelationArgument");
    jcasType.ll_cas.ll_setStringValue(addr, ((RelationArgument_Type)jcasType).casFeatCode_refid, v);}    
  }

    