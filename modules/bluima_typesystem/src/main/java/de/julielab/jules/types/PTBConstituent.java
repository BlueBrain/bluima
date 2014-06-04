

/* First created by JCasGen Wed Oct 19 19:10:28 CEST 2011 */
package de.julielab.jules.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** Penn Treebank constituent annotation (see Penn Treebank)
 * Updated by JCasGen Wed Jun 04 18:01:58 CEST 2014
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/src/main/resources/typeSystem/bbp-types.xml
 * @generated */
public class PTBConstituent extends Constituent {
  /** @generated
   * @ordered 
   */
  public final static int typeIndexID = JCasRegistry.register(PTBConstituent.class);
  /** @generated
   * @ordered 
   */
  public final static int type = typeIndexID;
  /** @generated  */
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected PTBConstituent() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public PTBConstituent(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public PTBConstituent(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public PTBConstituent(JCas jcas, int begin, int end) {
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
  //* Feature: formFuncDisc

  /** getter for formFuncDisc - gets Form/function discrepancies, O
   * @generated
   * @return value of the feature 
   */
  public String getFormFuncDisc() {
    if (PTBConstituent_Type.featOkTst && ((PTBConstituent_Type)jcasType).casFeat_formFuncDisc == null)
      jcasType.jcas.throwFeatMissing("formFuncDisc", "de.julielab.jules.types.PTBConstituent");
    return jcasType.ll_cas.ll_getStringValue(addr, ((PTBConstituent_Type)jcasType).casFeatCode_formFuncDisc);}
    
  /** setter for formFuncDisc - sets Form/function discrepancies, O 
   * @generated
   * @param v value to set into the feature 
   */
  public void setFormFuncDisc(String v) {
    if (PTBConstituent_Type.featOkTst && ((PTBConstituent_Type)jcasType).casFeat_formFuncDisc == null)
      jcasType.jcas.throwFeatMissing("formFuncDisc", "de.julielab.jules.types.PTBConstituent");
    jcasType.ll_cas.ll_setStringValue(addr, ((PTBConstituent_Type)jcasType).casFeatCode_formFuncDisc, v);}    
   
    
  //*--------------*
  //* Feature: gramRole

  /** getter for gramRole - gets Grammatical role, O
   * @generated
   * @return value of the feature 
   */
  public String getGramRole() {
    if (PTBConstituent_Type.featOkTst && ((PTBConstituent_Type)jcasType).casFeat_gramRole == null)
      jcasType.jcas.throwFeatMissing("gramRole", "de.julielab.jules.types.PTBConstituent");
    return jcasType.ll_cas.ll_getStringValue(addr, ((PTBConstituent_Type)jcasType).casFeatCode_gramRole);}
    
  /** setter for gramRole - sets Grammatical role, O 
   * @generated
   * @param v value to set into the feature 
   */
  public void setGramRole(String v) {
    if (PTBConstituent_Type.featOkTst && ((PTBConstituent_Type)jcasType).casFeat_gramRole == null)
      jcasType.jcas.throwFeatMissing("gramRole", "de.julielab.jules.types.PTBConstituent");
    jcasType.ll_cas.ll_setStringValue(addr, ((PTBConstituent_Type)jcasType).casFeatCode_gramRole, v);}    
   
    
  //*--------------*
  //* Feature: adv

  /** getter for adv - gets Adverbials are generally VP adjuncts.
   * @generated
   * @return value of the feature 
   */
  public String getAdv() {
    if (PTBConstituent_Type.featOkTst && ((PTBConstituent_Type)jcasType).casFeat_adv == null)
      jcasType.jcas.throwFeatMissing("adv", "de.julielab.jules.types.PTBConstituent");
    return jcasType.ll_cas.ll_getStringValue(addr, ((PTBConstituent_Type)jcasType).casFeatCode_adv);}
    
  /** setter for adv - sets Adverbials are generally VP adjuncts. 
   * @generated
   * @param v value to set into the feature 
   */
  public void setAdv(String v) {
    if (PTBConstituent_Type.featOkTst && ((PTBConstituent_Type)jcasType).casFeat_adv == null)
      jcasType.jcas.throwFeatMissing("adv", "de.julielab.jules.types.PTBConstituent");
    jcasType.ll_cas.ll_setStringValue(addr, ((PTBConstituent_Type)jcasType).casFeatCode_adv, v);}    
   
    
  //*--------------*
  //* Feature: misc

  /** getter for misc - gets Miscellaneous
   * @generated
   * @return value of the feature 
   */
  public String getMisc() {
    if (PTBConstituent_Type.featOkTst && ((PTBConstituent_Type)jcasType).casFeat_misc == null)
      jcasType.jcas.throwFeatMissing("misc", "de.julielab.jules.types.PTBConstituent");
    return jcasType.ll_cas.ll_getStringValue(addr, ((PTBConstituent_Type)jcasType).casFeatCode_misc);}
    
  /** setter for misc - sets Miscellaneous 
   * @generated
   * @param v value to set into the feature 
   */
  public void setMisc(String v) {
    if (PTBConstituent_Type.featOkTst && ((PTBConstituent_Type)jcasType).casFeat_misc == null)
      jcasType.jcas.throwFeatMissing("misc", "de.julielab.jules.types.PTBConstituent");
    jcasType.ll_cas.ll_setStringValue(addr, ((PTBConstituent_Type)jcasType).casFeatCode_misc, v);}    
   
    
  //*--------------*
  //* Feature: nullElement

  /** getter for nullElement - gets 
   * @generated
   * @return value of the feature 
   */
  public String getNullElement() {
    if (PTBConstituent_Type.featOkTst && ((PTBConstituent_Type)jcasType).casFeat_nullElement == null)
      jcasType.jcas.throwFeatMissing("nullElement", "de.julielab.jules.types.PTBConstituent");
    return jcasType.ll_cas.ll_getStringValue(addr, ((PTBConstituent_Type)jcasType).casFeatCode_nullElement);}
    
  /** setter for nullElement - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setNullElement(String v) {
    if (PTBConstituent_Type.featOkTst && ((PTBConstituent_Type)jcasType).casFeat_nullElement == null)
      jcasType.jcas.throwFeatMissing("nullElement", "de.julielab.jules.types.PTBConstituent");
    jcasType.ll_cas.ll_setStringValue(addr, ((PTBConstituent_Type)jcasType).casFeatCode_nullElement, v);}    
   
    
  //*--------------*
  //* Feature: ref

  /** getter for ref - gets Th reference from the null constituent to the corresponding lexicalized constituent, O
   * @generated
   * @return value of the feature 
   */
  public Constituent getRef() {
    if (PTBConstituent_Type.featOkTst && ((PTBConstituent_Type)jcasType).casFeat_ref == null)
      jcasType.jcas.throwFeatMissing("ref", "de.julielab.jules.types.PTBConstituent");
    return (Constituent)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((PTBConstituent_Type)jcasType).casFeatCode_ref)));}
    
  /** setter for ref - sets Th reference from the null constituent to the corresponding lexicalized constituent, O 
   * @generated
   * @param v value to set into the feature 
   */
  public void setRef(Constituent v) {
    if (PTBConstituent_Type.featOkTst && ((PTBConstituent_Type)jcasType).casFeat_ref == null)
      jcasType.jcas.throwFeatMissing("ref", "de.julielab.jules.types.PTBConstituent");
    jcasType.ll_cas.ll_setRefValue(addr, ((PTBConstituent_Type)jcasType).casFeatCode_ref, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: map

  /** getter for map - gets In the case of gapping the reference to the corresponding constituent, O
   * @generated
   * @return value of the feature 
   */
  public Constituent getMap() {
    if (PTBConstituent_Type.featOkTst && ((PTBConstituent_Type)jcasType).casFeat_map == null)
      jcasType.jcas.throwFeatMissing("map", "de.julielab.jules.types.PTBConstituent");
    return (Constituent)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((PTBConstituent_Type)jcasType).casFeatCode_map)));}
    
  /** setter for map - sets In the case of gapping the reference to the corresponding constituent, O 
   * @generated
   * @param v value to set into the feature 
   */
  public void setMap(Constituent v) {
    if (PTBConstituent_Type.featOkTst && ((PTBConstituent_Type)jcasType).casFeat_map == null)
      jcasType.jcas.throwFeatMissing("map", "de.julielab.jules.types.PTBConstituent");
    jcasType.ll_cas.ll_setRefValue(addr, ((PTBConstituent_Type)jcasType).casFeatCode_map, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: tpc

  /** getter for tpc - gets Marks elements that appear before the subject in a declarative sentence, O
   * @generated
   * @return value of the feature 
   */
  public boolean getTpc() {
    if (PTBConstituent_Type.featOkTst && ((PTBConstituent_Type)jcasType).casFeat_tpc == null)
      jcasType.jcas.throwFeatMissing("tpc", "de.julielab.jules.types.PTBConstituent");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((PTBConstituent_Type)jcasType).casFeatCode_tpc);}
    
  /** setter for tpc - sets Marks elements that appear before the subject in a declarative sentence, O 
   * @generated
   * @param v value to set into the feature 
   */
  public void setTpc(boolean v) {
    if (PTBConstituent_Type.featOkTst && ((PTBConstituent_Type)jcasType).casFeat_tpc == null)
      jcasType.jcas.throwFeatMissing("tpc", "de.julielab.jules.types.PTBConstituent");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((PTBConstituent_Type)jcasType).casFeatCode_tpc, v);}    
  }

    