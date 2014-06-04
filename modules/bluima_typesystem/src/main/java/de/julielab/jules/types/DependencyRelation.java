

/* First created by JCasGen Wed Oct 19 19:10:28 CEST 2011 */
package de.julielab.jules.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** Syntactic dependency relation annotation, see subtypes
 * Updated by JCasGen Wed Jun 04 18:01:57 CEST 2014
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/src/main/resources/typeSystem/bbp-types.xml
 * @generated */
public class DependencyRelation extends Annotation {
  /** @generated
   * @ordered 
   */
  public final static int typeIndexID = JCasRegistry.register(DependencyRelation.class);
  /** @generated
   * @ordered 
   */
  public final static int type = typeIndexID;
  /** @generated  */
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected DependencyRelation() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public DependencyRelation(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public DependencyRelation(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public DependencyRelation(JCas jcas, int begin, int end) {
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
  private void readObject() {}
     
 
    
  //*--------------*
  //* Feature: head

  /** getter for head - gets Head (Token) of the analyzed dependency relation, C
   * @generated
   * @return value of the feature 
   */
  public Token getHead() {
    if (DependencyRelation_Type.featOkTst && ((DependencyRelation_Type)jcasType).casFeat_head == null)
      jcasType.jcas.throwFeatMissing("head", "de.julielab.jules.types.DependencyRelation");
    return (Token)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((DependencyRelation_Type)jcasType).casFeatCode_head)));}
    
  /** setter for head - sets Head (Token) of the analyzed dependency relation, C 
   * @generated
   * @param v value to set into the feature 
   */
  public void setHead(Token v) {
    if (DependencyRelation_Type.featOkTst && ((DependencyRelation_Type)jcasType).casFeat_head == null)
      jcasType.jcas.throwFeatMissing("head", "de.julielab.jules.types.DependencyRelation");
    jcasType.ll_cas.ll_setRefValue(addr, ((DependencyRelation_Type)jcasType).casFeatCode_head, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: projective

  /** getter for projective - gets The dependency relations can be projective or not, C
   * @generated
   * @return value of the feature 
   */
  public boolean getProjective() {
    if (DependencyRelation_Type.featOkTst && ((DependencyRelation_Type)jcasType).casFeat_projective == null)
      jcasType.jcas.throwFeatMissing("projective", "de.julielab.jules.types.DependencyRelation");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((DependencyRelation_Type)jcasType).casFeatCode_projective);}
    
  /** setter for projective - sets The dependency relations can be projective or not, C 
   * @generated
   * @param v value to set into the feature 
   */
  public void setProjective(boolean v) {
    if (DependencyRelation_Type.featOkTst && ((DependencyRelation_Type)jcasType).casFeat_projective == null)
      jcasType.jcas.throwFeatMissing("projective", "de.julielab.jules.types.DependencyRelation");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((DependencyRelation_Type)jcasType).casFeatCode_projective, v);}    
   
    
  //*--------------*
  //* Feature: label

  /** getter for label - gets The label of the relation (e.g. SBJ, OBJ, DEP)
   * @generated
   * @return value of the feature 
   */
  public String getLabel() {
    if (DependencyRelation_Type.featOkTst && ((DependencyRelation_Type)jcasType).casFeat_label == null)
      jcasType.jcas.throwFeatMissing("label", "de.julielab.jules.types.DependencyRelation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((DependencyRelation_Type)jcasType).casFeatCode_label);}
    
  /** setter for label - sets The label of the relation (e.g. SBJ, OBJ, DEP) 
   * @generated
   * @param v value to set into the feature 
   */
  public void setLabel(String v) {
    if (DependencyRelation_Type.featOkTst && ((DependencyRelation_Type)jcasType).casFeat_label == null)
      jcasType.jcas.throwFeatMissing("label", "de.julielab.jules.types.DependencyRelation");
    jcasType.ll_cas.ll_setStringValue(addr, ((DependencyRelation_Type)jcasType).casFeatCode_label, v);}    
  }

    