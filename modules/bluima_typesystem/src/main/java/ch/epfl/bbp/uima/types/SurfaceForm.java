

/* First created by JCasGen Fri Sep 14 13:58:44 CEST 2012 */
package ch.epfl.bbp.uima.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** Surface form from BioLexicon's FORMS table
 * Updated by JCasGen Fri Sep 20 01:02:04 CEST 2013
 * XML source: /Users/richarde/dev/bluebrain/svn_nlp/UIMA/blue_uima/trunk/modules/julielab_typesystem-2.6.8/src/main/resources/typeSystem/bbp-basic-types.xml
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
  /** @generated  */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected SurfaceForm() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public SurfaceForm(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public SurfaceForm(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public SurfaceForm(JCas jcas, int begin, int end) {
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
  //* Feature: id

  /** getter for id - gets 
   * @generated */
  public String getId() {
    if (SurfaceForm_Type.featOkTst && ((SurfaceForm_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "ch.epfl.bbp.uima.types.SurfaceForm");
    return jcasType.ll_cas.ll_getStringValue(addr, ((SurfaceForm_Type)jcasType).casFeatCode_id);}
    
  /** setter for id - sets  
   * @generated */
  public void setId(String v) {
    if (SurfaceForm_Type.featOkTst && ((SurfaceForm_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "ch.epfl.bbp.uima.types.SurfaceForm");
    jcasType.ll_cas.ll_setStringValue(addr, ((SurfaceForm_Type)jcasType).casFeatCode_id, v);}    
   
    
  //*--------------*
  //* Feature: pos

  /** getter for pos - gets 
   * @generated */
  public String getPos() {
    if (SurfaceForm_Type.featOkTst && ((SurfaceForm_Type)jcasType).casFeat_pos == null)
      jcasType.jcas.throwFeatMissing("pos", "ch.epfl.bbp.uima.types.SurfaceForm");
    return jcasType.ll_cas.ll_getStringValue(addr, ((SurfaceForm_Type)jcasType).casFeatCode_pos);}
    
  /** setter for pos - sets  
   * @generated */
  public void setPos(String v) {
    if (SurfaceForm_Type.featOkTst && ((SurfaceForm_Type)jcasType).casFeat_pos == null)
      jcasType.jcas.throwFeatMissing("pos", "ch.epfl.bbp.uima.types.SurfaceForm");
    jcasType.ll_cas.ll_setStringValue(addr, ((SurfaceForm_Type)jcasType).casFeatCode_pos, v);}    
   
    
  //*--------------*
  //* Feature: semtype

  /** getter for semtype - gets 
   * @generated */
  public String getSemtype() {
    if (SurfaceForm_Type.featOkTst && ((SurfaceForm_Type)jcasType).casFeat_semtype == null)
      jcasType.jcas.throwFeatMissing("semtype", "ch.epfl.bbp.uima.types.SurfaceForm");
    return jcasType.ll_cas.ll_getStringValue(addr, ((SurfaceForm_Type)jcasType).casFeatCode_semtype);}
    
  /** setter for semtype - sets  
   * @generated */
  public void setSemtype(String v) {
    if (SurfaceForm_Type.featOkTst && ((SurfaceForm_Type)jcasType).casFeat_semtype == null)
      jcasType.jcas.throwFeatMissing("semtype", "ch.epfl.bbp.uima.types.SurfaceForm");
    jcasType.ll_cas.ll_setStringValue(addr, ((SurfaceForm_Type)jcasType).casFeatCode_semtype, v);}    
  }

    