

/* First created by JCasGen Wed Oct 19 19:10:28 CEST 2011 */
package de.julielab.jules.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.FSArray;
import org.apache.uima.jcas.cas.TOP;


/** 
 * Updated by JCasGen Wed Jun 04 18:01:57 CEST 2014
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/src/main/resources/typeSystem/bbp-types.xml
 * @generated */
public class ConceptMention extends Annotation {
  /** @generated
   * @ordered 
   */
  public final static int typeIndexID = JCasRegistry.register(ConceptMention.class);
  /** @generated
   * @ordered 
   */
  public final static int type = typeIndexID;
  /** @generated  */
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected ConceptMention() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public ConceptMention(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public ConceptMention(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public ConceptMention(JCas jcas, int begin, int end) {
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
  //* Feature: specificType

  /** getter for specificType - gets 
   * @generated
   * @return value of the feature 
   */
  public String getSpecificType() {
    if (ConceptMention_Type.featOkTst && ((ConceptMention_Type)jcasType).casFeat_specificType == null)
      jcasType.jcas.throwFeatMissing("specificType", "de.julielab.jules.types.ConceptMention");
    return jcasType.ll_cas.ll_getStringValue(addr, ((ConceptMention_Type)jcasType).casFeatCode_specificType);}
    
  /** setter for specificType - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setSpecificType(String v) {
    if (ConceptMention_Type.featOkTst && ((ConceptMention_Type)jcasType).casFeat_specificType == null)
      jcasType.jcas.throwFeatMissing("specificType", "de.julielab.jules.types.ConceptMention");
    jcasType.ll_cas.ll_setStringValue(addr, ((ConceptMention_Type)jcasType).casFeatCode_specificType, v);}    
   
    
  //*--------------*
  //* Feature: ref

  /** getter for ref - gets The reference to the Concept, we use here the super type TOP in order to avoid the recursive dependencies between type systems
   * @generated
   * @return value of the feature 
   */
  public TOP getRef() {
    if (ConceptMention_Type.featOkTst && ((ConceptMention_Type)jcasType).casFeat_ref == null)
      jcasType.jcas.throwFeatMissing("ref", "de.julielab.jules.types.ConceptMention");
    return (TOP)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((ConceptMention_Type)jcasType).casFeatCode_ref)));}
    
  /** setter for ref - sets The reference to the Concept, we use here the super type TOP in order to avoid the recursive dependencies between type systems 
   * @generated
   * @param v value to set into the feature 
   */
  public void setRef(TOP v) {
    if (ConceptMention_Type.featOkTst && ((ConceptMention_Type)jcasType).casFeat_ref == null)
      jcasType.jcas.throwFeatMissing("ref", "de.julielab.jules.types.ConceptMention");
    jcasType.ll_cas.ll_setRefValue(addr, ((ConceptMention_Type)jcasType).casFeatCode_ref, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: resourceEntryList

  /** getter for resourceEntryList - gets 
   * @generated
   * @return value of the feature 
   */
  public FSArray getResourceEntryList() {
    if (ConceptMention_Type.featOkTst && ((ConceptMention_Type)jcasType).casFeat_resourceEntryList == null)
      jcasType.jcas.throwFeatMissing("resourceEntryList", "de.julielab.jules.types.ConceptMention");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((ConceptMention_Type)jcasType).casFeatCode_resourceEntryList)));}
    
  /** setter for resourceEntryList - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setResourceEntryList(FSArray v) {
    if (ConceptMention_Type.featOkTst && ((ConceptMention_Type)jcasType).casFeat_resourceEntryList == null)
      jcasType.jcas.throwFeatMissing("resourceEntryList", "de.julielab.jules.types.ConceptMention");
    jcasType.ll_cas.ll_setRefValue(addr, ((ConceptMention_Type)jcasType).casFeatCode_resourceEntryList, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for resourceEntryList - gets an indexed value - 
   * @generated */
  public ResourceEntry getResourceEntryList(int i) {
    if (ConceptMention_Type.featOkTst && ((ConceptMention_Type)jcasType).casFeat_resourceEntryList == null)
      jcasType.jcas.throwFeatMissing("resourceEntryList", "de.julielab.jules.types.ConceptMention");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((ConceptMention_Type)jcasType).casFeatCode_resourceEntryList), i);
    return (ResourceEntry)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((ConceptMention_Type)jcasType).casFeatCode_resourceEntryList), i)));}

  /** indexed setter for resourceEntryList - sets an indexed value - 
   * @generated */
  public void setResourceEntryList(int i, ResourceEntry v) { 
    if (ConceptMention_Type.featOkTst && ((ConceptMention_Type)jcasType).casFeat_resourceEntryList == null)
      jcasType.jcas.throwFeatMissing("resourceEntryList", "de.julielab.jules.types.ConceptMention");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((ConceptMention_Type)jcasType).casFeatCode_resourceEntryList), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((ConceptMention_Type)jcasType).casFeatCode_resourceEntryList), i, jcasType.ll_cas.ll_getFSRef(v));}
   
    
  //*--------------*
  //* Feature: textualRepresentation

  /** getter for textualRepresentation - gets This feature provides the text of the annotated concept mention (e.g., entity mention). important for easily representing discontinious mentions such as 'T cell'  in th expression 'T and B cell'
   * @generated
   * @return value of the feature 
   */
  public String getTextualRepresentation() {
    if (ConceptMention_Type.featOkTst && ((ConceptMention_Type)jcasType).casFeat_textualRepresentation == null)
      jcasType.jcas.throwFeatMissing("textualRepresentation", "de.julielab.jules.types.ConceptMention");
    return jcasType.ll_cas.ll_getStringValue(addr, ((ConceptMention_Type)jcasType).casFeatCode_textualRepresentation);}
    
  /** setter for textualRepresentation - sets This feature provides the text of the annotated concept mention (e.g., entity mention). important for easily representing discontinious mentions such as 'T cell'  in th expression 'T and B cell' 
   * @generated
   * @param v value to set into the feature 
   */
  public void setTextualRepresentation(String v) {
    if (ConceptMention_Type.featOkTst && ((ConceptMention_Type)jcasType).casFeat_textualRepresentation == null)
      jcasType.jcas.throwFeatMissing("textualRepresentation", "de.julielab.jules.types.ConceptMention");
    jcasType.ll_cas.ll_setStringValue(addr, ((ConceptMention_Type)jcasType).casFeatCode_textualRepresentation, v);}    
  }

    