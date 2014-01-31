

/* First created by JCasGen Fri Sep 14 13:58:35 CEST 2012 */
package ch.epfl.bbp.uima.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Mon Oct 21 13:03:29 CEST 2013
 * XML source: /Users/richarde/dev/bluebrain/svn_nlp/UIMA/blue_uima/trunk/modules/julielab_typesystem-2.6.8/src/main/resources/typeSystem/bbp-semantics-biology-types.xml
 * @generated */
public class CellType extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(CellType.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated  */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected CellType() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public CellType(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public CellType(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public CellType(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** <!-- begin-user-doc -->
    * Write your own initialization here
    * <!-- end-user-doc -->
  @generated modifiable */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: name

  /** getter for name - gets 
   * @generated */
  public String getName() {
    if (CellType_Type.featOkTst && ((CellType_Type)jcasType).casFeat_name == null)
      jcasType.jcas.throwFeatMissing("name", "ch.epfl.bbp.uima.types.CellType");
    return jcasType.ll_cas.ll_getStringValue(addr, ((CellType_Type)jcasType).casFeatCode_name);}
    
  /** setter for name - sets  
   * @generated */
  public void setName(String v) {
    if (CellType_Type.featOkTst && ((CellType_Type)jcasType).casFeat_name == null)
      jcasType.jcas.throwFeatMissing("name", "ch.epfl.bbp.uima.types.CellType");
    jcasType.ll_cas.ll_setStringValue(addr, ((CellType_Type)jcasType).casFeatCode_name, v);}    
   
    
  //*--------------*
  //* Feature: id

  /** getter for id - gets 
   * @generated */
  public String getId() {
    if (CellType_Type.featOkTst && ((CellType_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "ch.epfl.bbp.uima.types.CellType");
    return jcasType.ll_cas.ll_getStringValue(addr, ((CellType_Type)jcasType).casFeatCode_id);}
    
  /** setter for id - sets  
   * @generated */
  public void setId(String v) {
    if (CellType_Type.featOkTst && ((CellType_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "ch.epfl.bbp.uima.types.CellType");
    jcasType.ll_cas.ll_setStringValue(addr, ((CellType_Type)jcasType).casFeatCode_id, v);}    
  }

    