

/* First created by JCasGen Wed Oct 19 19:10:28 CEST 2011 */
package de.julielab.jules.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.FSArray;
import org.apache.uima.jcas.cas.StringArray;


/** ontology class mention
 * Updated by JCasGen Wed Jun 04 18:01:58 CEST 2014
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/src/main/resources/typeSystem/bbp-types.xml
 * @generated */
public class OntClassMention extends Annotation {
  /** @generated
   * @ordered 
   */
  public final static int typeIndexID = JCasRegistry.register(OntClassMention.class);
  /** @generated
   * @ordered 
   */
  public final static int type = typeIndexID;
  /** @generated  */
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected OntClassMention() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public OntClassMention(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public OntClassMention(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public OntClassMention(JCas jcas, int begin, int end) {
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
  //* Feature: ontClassId

  /** getter for ontClassId - gets ID of the corresponding ontology class
   * @generated
   * @return value of the feature 
   */
  public String getOntClassId() {
    if (OntClassMention_Type.featOkTst && ((OntClassMention_Type)jcasType).casFeat_ontClassId == null)
      jcasType.jcas.throwFeatMissing("ontClassId", "de.julielab.jules.types.OntClassMention");
    return jcasType.ll_cas.ll_getStringValue(addr, ((OntClassMention_Type)jcasType).casFeatCode_ontClassId);}
    
  /** setter for ontClassId - sets ID of the corresponding ontology class 
   * @generated
   * @param v value to set into the feature 
   */
  public void setOntClassId(String v) {
    if (OntClassMention_Type.featOkTst && ((OntClassMention_Type)jcasType).casFeat_ontClassId == null)
      jcasType.jcas.throwFeatMissing("ontClassId", "de.julielab.jules.types.OntClassMention");
    jcasType.ll_cas.ll_setStringValue(addr, ((OntClassMention_Type)jcasType).casFeatCode_ontClassId, v);}    
   
    
  //*--------------*
  //* Feature: sourceOntology

  /** getter for sourceOntology - gets name or ID of the ontology the class comes from
   * @generated
   * @return value of the feature 
   */
  public String getSourceOntology() {
    if (OntClassMention_Type.featOkTst && ((OntClassMention_Type)jcasType).casFeat_sourceOntology == null)
      jcasType.jcas.throwFeatMissing("sourceOntology", "de.julielab.jules.types.OntClassMention");
    return jcasType.ll_cas.ll_getStringValue(addr, ((OntClassMention_Type)jcasType).casFeatCode_sourceOntology);}
    
  /** setter for sourceOntology - sets name or ID of the ontology the class comes from 
   * @generated
   * @param v value to set into the feature 
   */
  public void setSourceOntology(String v) {
    if (OntClassMention_Type.featOkTst && ((OntClassMention_Type)jcasType).casFeat_sourceOntology == null)
      jcasType.jcas.throwFeatMissing("sourceOntology", "de.julielab.jules.types.OntClassMention");
    jcasType.ll_cas.ll_setStringValue(addr, ((OntClassMention_Type)jcasType).casFeatCode_sourceOntology, v);}    
   
    
  //*--------------*
  //* Feature: textualRepresentation

  /** getter for textualRepresentation - gets Text of the annotated concept mention. Important for easily representing discontinuous mentions such as 'T cell'  in 'T and B cell'
   * @generated
   * @return value of the feature 
   */
  public String getTextualRepresentation() {
    if (OntClassMention_Type.featOkTst && ((OntClassMention_Type)jcasType).casFeat_textualRepresentation == null)
      jcasType.jcas.throwFeatMissing("textualRepresentation", "de.julielab.jules.types.OntClassMention");
    return jcasType.ll_cas.ll_getStringValue(addr, ((OntClassMention_Type)jcasType).casFeatCode_textualRepresentation);}
    
  /** setter for textualRepresentation - sets Text of the annotated concept mention. Important for easily representing discontinuous mentions such as 'T cell'  in 'T and B cell' 
   * @generated
   * @param v value to set into the feature 
   */
  public void setTextualRepresentation(String v) {
    if (OntClassMention_Type.featOkTst && ((OntClassMention_Type)jcasType).casFeat_textualRepresentation == null)
      jcasType.jcas.throwFeatMissing("textualRepresentation", "de.julielab.jules.types.OntClassMention");
    jcasType.ll_cas.ll_setStringValue(addr, ((OntClassMention_Type)jcasType).casFeatCode_textualRepresentation, v);}    
   
    
  //*--------------*
  //* Feature: semanticTypes

  /** getter for semanticTypes - gets Names or IDs of associated semantic types.
   * @generated
   * @return value of the feature 
   */
  public StringArray getSemanticTypes() {
    if (OntClassMention_Type.featOkTst && ((OntClassMention_Type)jcasType).casFeat_semanticTypes == null)
      jcasType.jcas.throwFeatMissing("semanticTypes", "de.julielab.jules.types.OntClassMention");
    return (StringArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((OntClassMention_Type)jcasType).casFeatCode_semanticTypes)));}
    
  /** setter for semanticTypes - sets Names or IDs of associated semantic types. 
   * @generated
   * @param v value to set into the feature 
   */
  public void setSemanticTypes(StringArray v) {
    if (OntClassMention_Type.featOkTst && ((OntClassMention_Type)jcasType).casFeat_semanticTypes == null)
      jcasType.jcas.throwFeatMissing("semanticTypes", "de.julielab.jules.types.OntClassMention");
    jcasType.ll_cas.ll_setRefValue(addr, ((OntClassMention_Type)jcasType).casFeatCode_semanticTypes, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for semanticTypes - gets an indexed value - Names or IDs of associated semantic types.
   * @generated */
  public String getSemanticTypes(int i) {
    if (OntClassMention_Type.featOkTst && ((OntClassMention_Type)jcasType).casFeat_semanticTypes == null)
      jcasType.jcas.throwFeatMissing("semanticTypes", "de.julielab.jules.types.OntClassMention");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((OntClassMention_Type)jcasType).casFeatCode_semanticTypes), i);
    return jcasType.ll_cas.ll_getStringArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((OntClassMention_Type)jcasType).casFeatCode_semanticTypes), i);}

  /** indexed setter for semanticTypes - sets an indexed value - Names or IDs of associated semantic types.
   * @generated */
  public void setSemanticTypes(int i, String v) { 
    if (OntClassMention_Type.featOkTst && ((OntClassMention_Type)jcasType).casFeat_semanticTypes == null)
      jcasType.jcas.throwFeatMissing("semanticTypes", "de.julielab.jules.types.OntClassMention");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((OntClassMention_Type)jcasType).casFeatCode_semanticTypes), i);
    jcasType.ll_cas.ll_setStringArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((OntClassMention_Type)jcasType).casFeatCode_semanticTypes), i, v);}
   
    
  //*--------------*
  //* Feature: preferredTerm

  /** getter for preferredTerm - gets The preferred term associated with the corresponding ontology class.
   * @generated
   * @return value of the feature 
   */
  public String getPreferredTerm() {
    if (OntClassMention_Type.featOkTst && ((OntClassMention_Type)jcasType).casFeat_preferredTerm == null)
      jcasType.jcas.throwFeatMissing("preferredTerm", "de.julielab.jules.types.OntClassMention");
    return jcasType.ll_cas.ll_getStringValue(addr, ((OntClassMention_Type)jcasType).casFeatCode_preferredTerm);}
    
  /** setter for preferredTerm - sets The preferred term associated with the corresponding ontology class. 
   * @generated
   * @param v value to set into the feature 
   */
  public void setPreferredTerm(String v) {
    if (OntClassMention_Type.featOkTst && ((OntClassMention_Type)jcasType).casFeat_preferredTerm == null)
      jcasType.jcas.throwFeatMissing("preferredTerm", "de.julielab.jules.types.OntClassMention");
    jcasType.ll_cas.ll_setStringValue(addr, ((OntClassMention_Type)jcasType).casFeatCode_preferredTerm, v);}    
   
    
  //*--------------*
  //* Feature: matchedTokens

  /** getter for matchedTokens - gets List of tokens the ontology class mention is comprised of.
   * @generated
   * @return value of the feature 
   */
  public FSArray getMatchedTokens() {
    if (OntClassMention_Type.featOkTst && ((OntClassMention_Type)jcasType).casFeat_matchedTokens == null)
      jcasType.jcas.throwFeatMissing("matchedTokens", "de.julielab.jules.types.OntClassMention");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((OntClassMention_Type)jcasType).casFeatCode_matchedTokens)));}
    
  /** setter for matchedTokens - sets List of tokens the ontology class mention is comprised of. 
   * @generated
   * @param v value to set into the feature 
   */
  public void setMatchedTokens(FSArray v) {
    if (OntClassMention_Type.featOkTst && ((OntClassMention_Type)jcasType).casFeat_matchedTokens == null)
      jcasType.jcas.throwFeatMissing("matchedTokens", "de.julielab.jules.types.OntClassMention");
    jcasType.ll_cas.ll_setRefValue(addr, ((OntClassMention_Type)jcasType).casFeatCode_matchedTokens, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for matchedTokens - gets an indexed value - List of tokens the ontology class mention is comprised of.
   * @generated */
  public Token getMatchedTokens(int i) {
    if (OntClassMention_Type.featOkTst && ((OntClassMention_Type)jcasType).casFeat_matchedTokens == null)
      jcasType.jcas.throwFeatMissing("matchedTokens", "de.julielab.jules.types.OntClassMention");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((OntClassMention_Type)jcasType).casFeatCode_matchedTokens), i);
    return (Token)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((OntClassMention_Type)jcasType).casFeatCode_matchedTokens), i)));}

  /** indexed setter for matchedTokens - sets an indexed value - List of tokens the ontology class mention is comprised of.
   * @generated */
  public void setMatchedTokens(int i, Token v) { 
    if (OntClassMention_Type.featOkTst && ((OntClassMention_Type)jcasType).casFeat_matchedTokens == null)
      jcasType.jcas.throwFeatMissing("matchedTokens", "de.julielab.jules.types.OntClassMention");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((OntClassMention_Type)jcasType).casFeatCode_matchedTokens), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((OntClassMention_Type)jcasType).casFeatCode_matchedTokens), i, jcasType.ll_cas.ll_getFSRef(v));}
   
    
  //*--------------*
  //* Feature: matchedSourceTerm

  /** getter for matchedSourceTerm - gets E.g. an ontology class label that was found to correspond to the mention (e.g. as result of a dictionary lookup).
   * @generated
   * @return value of the feature 
   */
  public String getMatchedSourceTerm() {
    if (OntClassMention_Type.featOkTst && ((OntClassMention_Type)jcasType).casFeat_matchedSourceTerm == null)
      jcasType.jcas.throwFeatMissing("matchedSourceTerm", "de.julielab.jules.types.OntClassMention");
    return jcasType.ll_cas.ll_getStringValue(addr, ((OntClassMention_Type)jcasType).casFeatCode_matchedSourceTerm);}
    
  /** setter for matchedSourceTerm - sets E.g. an ontology class label that was found to correspond to the mention (e.g. as result of a dictionary lookup). 
   * @generated
   * @param v value to set into the feature 
   */
  public void setMatchedSourceTerm(String v) {
    if (OntClassMention_Type.featOkTst && ((OntClassMention_Type)jcasType).casFeat_matchedSourceTerm == null)
      jcasType.jcas.throwFeatMissing("matchedSourceTerm", "de.julielab.jules.types.OntClassMention");
    jcasType.ll_cas.ll_setStringValue(addr, ((OntClassMention_Type)jcasType).casFeatCode_matchedSourceTerm, v);}    
  }

    