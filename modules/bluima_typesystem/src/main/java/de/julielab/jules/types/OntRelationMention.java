

/* First created by JCasGen Sat Mar 07 22:05:57 CET 2015 */
package de.julielab.jules.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.FSArray;


/** 
 * Updated by JCasGen Sat Mar 07 22:05:57 CET 2015
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/target/jcasgen/typesystem.xml
 * @generated */
public class OntRelationMention extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(OntRelationMention.class);
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
  protected OntRelationMention() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public OntRelationMention(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public OntRelationMention(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public OntRelationMention(JCas jcas, int begin, int end) {
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
  //* Feature: domain

  /** getter for domain - gets 
   * @generated
   * @return value of the feature 
   */
  public Annotation getDomain() {
    if (OntRelationMention_Type.featOkTst && ((OntRelationMention_Type)jcasType).casFeat_domain == null)
      jcasType.jcas.throwFeatMissing("domain", "de.julielab.jules.types.OntRelationMention");
    return (Annotation)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((OntRelationMention_Type)jcasType).casFeatCode_domain)));}
    
  /** setter for domain - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setDomain(Annotation v) {
    if (OntRelationMention_Type.featOkTst && ((OntRelationMention_Type)jcasType).casFeat_domain == null)
      jcasType.jcas.throwFeatMissing("domain", "de.julielab.jules.types.OntRelationMention");
    jcasType.ll_cas.ll_setRefValue(addr, ((OntRelationMention_Type)jcasType).casFeatCode_domain, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: range

  /** getter for range - gets 
   * @generated
   * @return value of the feature 
   */
  public Annotation getRange() {
    if (OntRelationMention_Type.featOkTst && ((OntRelationMention_Type)jcasType).casFeat_range == null)
      jcasType.jcas.throwFeatMissing("range", "de.julielab.jules.types.OntRelationMention");
    return (Annotation)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((OntRelationMention_Type)jcasType).casFeatCode_range)));}
    
  /** setter for range - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setRange(Annotation v) {
    if (OntRelationMention_Type.featOkTst && ((OntRelationMention_Type)jcasType).casFeat_range == null)
      jcasType.jcas.throwFeatMissing("range", "de.julielab.jules.types.OntRelationMention");
    jcasType.ll_cas.ll_setRefValue(addr, ((OntRelationMention_Type)jcasType).casFeatCode_range, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: domainList

  /** getter for domainList - gets 
   * @generated
   * @return value of the feature 
   */
  public FSArray getDomainList() {
    if (OntRelationMention_Type.featOkTst && ((OntRelationMention_Type)jcasType).casFeat_domainList == null)
      jcasType.jcas.throwFeatMissing("domainList", "de.julielab.jules.types.OntRelationMention");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((OntRelationMention_Type)jcasType).casFeatCode_domainList)));}
    
  /** setter for domainList - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setDomainList(FSArray v) {
    if (OntRelationMention_Type.featOkTst && ((OntRelationMention_Type)jcasType).casFeat_domainList == null)
      jcasType.jcas.throwFeatMissing("domainList", "de.julielab.jules.types.OntRelationMention");
    jcasType.ll_cas.ll_setRefValue(addr, ((OntRelationMention_Type)jcasType).casFeatCode_domainList, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for domainList - gets an indexed value - 
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public Annotation getDomainList(int i) {
    if (OntRelationMention_Type.featOkTst && ((OntRelationMention_Type)jcasType).casFeat_domainList == null)
      jcasType.jcas.throwFeatMissing("domainList", "de.julielab.jules.types.OntRelationMention");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((OntRelationMention_Type)jcasType).casFeatCode_domainList), i);
    return (Annotation)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((OntRelationMention_Type)jcasType).casFeatCode_domainList), i)));}

  /** indexed setter for domainList - sets an indexed value - 
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setDomainList(int i, Annotation v) { 
    if (OntRelationMention_Type.featOkTst && ((OntRelationMention_Type)jcasType).casFeat_domainList == null)
      jcasType.jcas.throwFeatMissing("domainList", "de.julielab.jules.types.OntRelationMention");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((OntRelationMention_Type)jcasType).casFeatCode_domainList), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((OntRelationMention_Type)jcasType).casFeatCode_domainList), i, jcasType.ll_cas.ll_getFSRef(v));}
   
    
  //*--------------*
  //* Feature: rangeList

  /** getter for rangeList - gets 
   * @generated
   * @return value of the feature 
   */
  public FSArray getRangeList() {
    if (OntRelationMention_Type.featOkTst && ((OntRelationMention_Type)jcasType).casFeat_rangeList == null)
      jcasType.jcas.throwFeatMissing("rangeList", "de.julielab.jules.types.OntRelationMention");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((OntRelationMention_Type)jcasType).casFeatCode_rangeList)));}
    
  /** setter for rangeList - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setRangeList(FSArray v) {
    if (OntRelationMention_Type.featOkTst && ((OntRelationMention_Type)jcasType).casFeat_rangeList == null)
      jcasType.jcas.throwFeatMissing("rangeList", "de.julielab.jules.types.OntRelationMention");
    jcasType.ll_cas.ll_setRefValue(addr, ((OntRelationMention_Type)jcasType).casFeatCode_rangeList, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for rangeList - gets an indexed value - 
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public Annotation getRangeList(int i) {
    if (OntRelationMention_Type.featOkTst && ((OntRelationMention_Type)jcasType).casFeat_rangeList == null)
      jcasType.jcas.throwFeatMissing("rangeList", "de.julielab.jules.types.OntRelationMention");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((OntRelationMention_Type)jcasType).casFeatCode_rangeList), i);
    return (Annotation)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((OntRelationMention_Type)jcasType).casFeatCode_rangeList), i)));}

  /** indexed setter for rangeList - sets an indexed value - 
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setRangeList(int i, Annotation v) { 
    if (OntRelationMention_Type.featOkTst && ((OntRelationMention_Type)jcasType).casFeat_rangeList == null)
      jcasType.jcas.throwFeatMissing("rangeList", "de.julielab.jules.types.OntRelationMention");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((OntRelationMention_Type)jcasType).casFeatCode_rangeList), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((OntRelationMention_Type)jcasType).casFeatCode_rangeList), i, jcasType.ll_cas.ll_getFSRef(v));}
   
    
  //*--------------*
  //* Feature: name

  /** getter for name - gets 
   * @generated
   * @return value of the feature 
   */
  public String getName() {
    if (OntRelationMention_Type.featOkTst && ((OntRelationMention_Type)jcasType).casFeat_name == null)
      jcasType.jcas.throwFeatMissing("name", "de.julielab.jules.types.OntRelationMention");
    return jcasType.ll_cas.ll_getStringValue(addr, ((OntRelationMention_Type)jcasType).casFeatCode_name);}
    
  /** setter for name - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setName(String v) {
    if (OntRelationMention_Type.featOkTst && ((OntRelationMention_Type)jcasType).casFeat_name == null)
      jcasType.jcas.throwFeatMissing("name", "de.julielab.jules.types.OntRelationMention");
    jcasType.ll_cas.ll_setStringValue(addr, ((OntRelationMention_Type)jcasType).casFeatCode_name, v);}    
   
    
  //*--------------*
  //* Feature: ontRelationId

  /** getter for ontRelationId - gets ID of the ontology relation the relation mention refers to.
   * @generated
   * @return value of the feature 
   */
  public String getOntRelationId() {
    if (OntRelationMention_Type.featOkTst && ((OntRelationMention_Type)jcasType).casFeat_ontRelationId == null)
      jcasType.jcas.throwFeatMissing("ontRelationId", "de.julielab.jules.types.OntRelationMention");
    return jcasType.ll_cas.ll_getStringValue(addr, ((OntRelationMention_Type)jcasType).casFeatCode_ontRelationId);}
    
  /** setter for ontRelationId - sets ID of the ontology relation the relation mention refers to. 
   * @generated
   * @param v value to set into the feature 
   */
  public void setOntRelationId(String v) {
    if (OntRelationMention_Type.featOkTst && ((OntRelationMention_Type)jcasType).casFeat_ontRelationId == null)
      jcasType.jcas.throwFeatMissing("ontRelationId", "de.julielab.jules.types.OntRelationMention");
    jcasType.ll_cas.ll_setStringValue(addr, ((OntRelationMention_Type)jcasType).casFeatCode_ontRelationId, v);}    
   
    
  //*--------------*
  //* Feature: sourceOntology

  /** getter for sourceOntology - gets Ontology defining the relation to which the relation mention refers to.
   * @generated
   * @return value of the feature 
   */
  public String getSourceOntology() {
    if (OntRelationMention_Type.featOkTst && ((OntRelationMention_Type)jcasType).casFeat_sourceOntology == null)
      jcasType.jcas.throwFeatMissing("sourceOntology", "de.julielab.jules.types.OntRelationMention");
    return jcasType.ll_cas.ll_getStringValue(addr, ((OntRelationMention_Type)jcasType).casFeatCode_sourceOntology);}
    
  /** setter for sourceOntology - sets Ontology defining the relation to which the relation mention refers to. 
   * @generated
   * @param v value to set into the feature 
   */
  public void setSourceOntology(String v) {
    if (OntRelationMention_Type.featOkTst && ((OntRelationMention_Type)jcasType).casFeat_sourceOntology == null)
      jcasType.jcas.throwFeatMissing("sourceOntology", "de.julielab.jules.types.OntRelationMention");
    jcasType.ll_cas.ll_setStringValue(addr, ((OntRelationMention_Type)jcasType).casFeatCode_sourceOntology, v);}    
   
    
  //*--------------*
  //* Feature: textualRepresentation

  /** getter for textualRepresentation - gets Textual representation of the ontology relation. Could be redundant to the covered text of the relation mention annotation.
   * @generated
   * @return value of the feature 
   */
  public String getTextualRepresentation() {
    if (OntRelationMention_Type.featOkTst && ((OntRelationMention_Type)jcasType).casFeat_textualRepresentation == null)
      jcasType.jcas.throwFeatMissing("textualRepresentation", "de.julielab.jules.types.OntRelationMention");
    return jcasType.ll_cas.ll_getStringValue(addr, ((OntRelationMention_Type)jcasType).casFeatCode_textualRepresentation);}
    
  /** setter for textualRepresentation - sets Textual representation of the ontology relation. Could be redundant to the covered text of the relation mention annotation. 
   * @generated
   * @param v value to set into the feature 
   */
  public void setTextualRepresentation(String v) {
    if (OntRelationMention_Type.featOkTst && ((OntRelationMention_Type)jcasType).casFeat_textualRepresentation == null)
      jcasType.jcas.throwFeatMissing("textualRepresentation", "de.julielab.jules.types.OntRelationMention");
    jcasType.ll_cas.ll_setStringValue(addr, ((OntRelationMention_Type)jcasType).casFeatCode_textualRepresentation, v);}    
  }

    