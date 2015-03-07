

/* First created by JCasGen Sat Mar 07 22:05:56 CET 2015 */
package ch.epfl.bbp.uima.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** Surface form from BioLexicon's FORMS table
 * Updated by JCasGen Sat Mar 07 22:05:56 CET 2015
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/target/jcasgen/typesystem.xml
 * @generated */
public class SurfaceForm extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(SurfaceForm.class);
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
  protected SurfaceForm() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public SurfaceForm(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public SurfaceForm(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public SurfaceForm(JCas jcas, int begin, int end) {
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
  //* Feature: id

  /** getter for id - gets 
   * @generated
   * @return value of the feature 
   */
  public String getId() {
    if (SurfaceForm_Type.featOkTst && ((SurfaceForm_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "ch.epfl.bbp.uima.types.SurfaceForm");
    return jcasType.ll_cas.ll_getStringValue(addr, ((SurfaceForm_Type)jcasType).casFeatCode_id);}
    
  /** setter for id - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setId(String v) {
    if (SurfaceForm_Type.featOkTst && ((SurfaceForm_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "ch.epfl.bbp.uima.types.SurfaceForm");
    jcasType.ll_cas.ll_setStringValue(addr, ((SurfaceForm_Type)jcasType).casFeatCode_id, v);}    
   
    
  //*--------------*
  //* Feature: pos

  /** getter for pos - gets 
   * @generated
   * @return value of the feature 
   */
  public String getPos() {
    if (SurfaceForm_Type.featOkTst && ((SurfaceForm_Type)jcasType).casFeat_pos == null)
      jcasType.jcas.throwFeatMissing("pos", "ch.epfl.bbp.uima.types.SurfaceForm");
    return jcasType.ll_cas.ll_getStringValue(addr, ((SurfaceForm_Type)jcasType).casFeatCode_pos);}
    
  /** setter for pos - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setPos(String v) {
    if (SurfaceForm_Type.featOkTst && ((SurfaceForm_Type)jcasType).casFeat_pos == null)
      jcasType.jcas.throwFeatMissing("pos", "ch.epfl.bbp.uima.types.SurfaceForm");
    jcasType.ll_cas.ll_setStringValue(addr, ((SurfaceForm_Type)jcasType).casFeatCode_pos, v);}    
   
    
  //*--------------*
  //* Feature: semtype

  /** getter for semtype - gets 
   * @generated
   * @return value of the feature 
   */
  public String getSemtype() {
    if (SurfaceForm_Type.featOkTst && ((SurfaceForm_Type)jcasType).casFeat_semtype == null)
      jcasType.jcas.throwFeatMissing("semtype", "ch.epfl.bbp.uima.types.SurfaceForm");
    return jcasType.ll_cas.ll_getStringValue(addr, ((SurfaceForm_Type)jcasType).casFeatCode_semtype);}
    
  /** setter for semtype - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setSemtype(String v) {
    if (SurfaceForm_Type.featOkTst && ((SurfaceForm_Type)jcasType).casFeat_semtype == null)
      jcasType.jcas.throwFeatMissing("semtype", "ch.epfl.bbp.uima.types.SurfaceForm");
    jcasType.ll_cas.ll_setStringValue(addr, ((SurfaceForm_Type)jcasType).casFeatCode_semtype, v);}    
  }

    