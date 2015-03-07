

/* First created by JCasGen Sat Mar 07 22:05:57 CET 2015 */
package de.julielab.jules.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.FSArray;


/** Token annotation marks the span of a token and takes all additional annotations that are on the token level, including Part-of-Speech information, lemma, stemmed form,  grammatical features such as gender, number and orthographical information; furthemore, Token includes the information about dependency relations to other tokens (see correspondent annotation types for further infromation).
 * Updated by JCasGen Sat Mar 07 22:05:57 CET 2015
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/target/jcasgen/typesystem.xml
 * @generated */
public class Token extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Token.class);
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
  protected Token() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Token(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Token(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Token(JCas jcas, int begin, int end) {
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
  //* Feature: lemma

  /** getter for lemma - gets the lemma information, O
   * @generated
   * @return value of the feature 
   */
  public Lemma getLemma() {
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_lemma == null)
      jcasType.jcas.throwFeatMissing("lemma", "de.julielab.jules.types.Token");
    return (Lemma)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Token_Type)jcasType).casFeatCode_lemma)));}
    
  /** setter for lemma - sets the lemma information, O 
   * @generated
   * @param v value to set into the feature 
   */
  public void setLemma(Lemma v) {
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_lemma == null)
      jcasType.jcas.throwFeatMissing("lemma", "de.julielab.jules.types.Token");
    jcasType.ll_cas.ll_setRefValue(addr, ((Token_Type)jcasType).casFeatCode_lemma, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: posTag

  /** getter for posTag - gets List contains part-of-speech tags of different part-of-speech tagsets (see also POSTag and subtypes), O
   * @generated
   * @return value of the feature 
   */
  public FSArray getPosTag() {
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_posTag == null)
      jcasType.jcas.throwFeatMissing("posTag", "de.julielab.jules.types.Token");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Token_Type)jcasType).casFeatCode_posTag)));}
    
  /** setter for posTag - sets List contains part-of-speech tags of different part-of-speech tagsets (see also POSTag and subtypes), O 
   * @generated
   * @param v value to set into the feature 
   */
  public void setPosTag(FSArray v) {
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_posTag == null)
      jcasType.jcas.throwFeatMissing("posTag", "de.julielab.jules.types.Token");
    jcasType.ll_cas.ll_setRefValue(addr, ((Token_Type)jcasType).casFeatCode_posTag, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for posTag - gets an indexed value - List contains part-of-speech tags of different part-of-speech tagsets (see also POSTag and subtypes), O
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public POSTag getPosTag(int i) {
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_posTag == null)
      jcasType.jcas.throwFeatMissing("posTag", "de.julielab.jules.types.Token");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Token_Type)jcasType).casFeatCode_posTag), i);
    return (POSTag)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Token_Type)jcasType).casFeatCode_posTag), i)));}

  /** indexed setter for posTag - sets an indexed value - List contains part-of-speech tags of different part-of-speech tagsets (see also POSTag and subtypes), O
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setPosTag(int i, POSTag v) { 
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_posTag == null)
      jcasType.jcas.throwFeatMissing("posTag", "de.julielab.jules.types.Token");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Token_Type)jcasType).casFeatCode_posTag), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Token_Type)jcasType).casFeatCode_posTag), i, jcasType.ll_cas.ll_getFSRef(v));}
   
    
  //*--------------*
  //* Feature: stemmedForm

  /** getter for stemmedForm - gets Contains the stemmed form of token (see StemmedForm), O
   * @generated
   * @return value of the feature 
   */
  public StemmedForm getStemmedForm() {
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_stemmedForm == null)
      jcasType.jcas.throwFeatMissing("stemmedForm", "de.julielab.jules.types.Token");
    return (StemmedForm)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Token_Type)jcasType).casFeatCode_stemmedForm)));}
    
  /** setter for stemmedForm - sets Contains the stemmed form of token (see StemmedForm), O 
   * @generated
   * @param v value to set into the feature 
   */
  public void setStemmedForm(StemmedForm v) {
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_stemmedForm == null)
      jcasType.jcas.throwFeatMissing("stemmedForm", "de.julielab.jules.types.Token");
    jcasType.ll_cas.ll_setRefValue(addr, ((Token_Type)jcasType).casFeatCode_stemmedForm, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: feats

  /** getter for feats - gets Contains grammatical features correspondent to the part-of-speech tag of current token (see GrammaticalFeats and subtypes), O
   * @generated
   * @return value of the feature 
   */
  public GrammaticalFeats getFeats() {
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_feats == null)
      jcasType.jcas.throwFeatMissing("feats", "de.julielab.jules.types.Token");
    return (GrammaticalFeats)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Token_Type)jcasType).casFeatCode_feats)));}
    
  /** setter for feats - sets Contains grammatical features correspondent to the part-of-speech tag of current token (see GrammaticalFeats and subtypes), O 
   * @generated
   * @param v value to set into the feature 
   */
  public void setFeats(GrammaticalFeats v) {
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_feats == null)
      jcasType.jcas.throwFeatMissing("feats", "de.julielab.jules.types.Token");
    jcasType.ll_cas.ll_setRefValue(addr, ((Token_Type)jcasType).casFeatCode_feats, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: orthogr

  /** getter for orthogr - gets see de.julielab.jules.types.Orthogrpahy
   * @generated
   * @return value of the feature 
   */
  public String getOrthogr() {
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_orthogr == null)
      jcasType.jcas.throwFeatMissing("orthogr", "de.julielab.jules.types.Token");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Token_Type)jcasType).casFeatCode_orthogr);}
    
  /** setter for orthogr - sets see de.julielab.jules.types.Orthogrpahy 
   * @generated
   * @param v value to set into the feature 
   */
  public void setOrthogr(String v) {
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_orthogr == null)
      jcasType.jcas.throwFeatMissing("orthogr", "de.julielab.jules.types.Token");
    jcasType.ll_cas.ll_setStringValue(addr, ((Token_Type)jcasType).casFeatCode_orthogr, v);}    
   
    
  //*--------------*
  //* Feature: depRel

  /** getter for depRel - gets Contains a list of syntactical dependencies, see DependencyRelation, O
   * @generated
   * @return value of the feature 
   */
  public FSArray getDepRel() {
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_depRel == null)
      jcasType.jcas.throwFeatMissing("depRel", "de.julielab.jules.types.Token");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Token_Type)jcasType).casFeatCode_depRel)));}
    
  /** setter for depRel - sets Contains a list of syntactical dependencies, see DependencyRelation, O 
   * @generated
   * @param v value to set into the feature 
   */
  public void setDepRel(FSArray v) {
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_depRel == null)
      jcasType.jcas.throwFeatMissing("depRel", "de.julielab.jules.types.Token");
    jcasType.ll_cas.ll_setRefValue(addr, ((Token_Type)jcasType).casFeatCode_depRel, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for depRel - gets an indexed value - Contains a list of syntactical dependencies, see DependencyRelation, O
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public DependencyRelation getDepRel(int i) {
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_depRel == null)
      jcasType.jcas.throwFeatMissing("depRel", "de.julielab.jules.types.Token");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Token_Type)jcasType).casFeatCode_depRel), i);
    return (DependencyRelation)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Token_Type)jcasType).casFeatCode_depRel), i)));}

  /** indexed setter for depRel - sets an indexed value - Contains a list of syntactical dependencies, see DependencyRelation, O
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setDepRel(int i, DependencyRelation v) { 
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_depRel == null)
      jcasType.jcas.throwFeatMissing("depRel", "de.julielab.jules.types.Token");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Token_Type)jcasType).casFeatCode_depRel), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Token_Type)jcasType).casFeatCode_depRel), i, jcasType.ll_cas.ll_getFSRef(v));}
   
    
  //*--------------*
  //* Feature: pos

  /** getter for pos - gets 
   * @generated
   * @return value of the feature 
   */
  public String getPos() {
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_pos == null)
      jcasType.jcas.throwFeatMissing("pos", "de.julielab.jules.types.Token");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Token_Type)jcasType).casFeatCode_pos);}
    
  /** setter for pos - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setPos(String v) {
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_pos == null)
      jcasType.jcas.throwFeatMissing("pos", "de.julielab.jules.types.Token");
    jcasType.ll_cas.ll_setStringValue(addr, ((Token_Type)jcasType).casFeatCode_pos, v);}    
   
    
  //*--------------*
  //* Feature: lemmaStr

  /** getter for lemmaStr - gets 
   * @generated
   * @return value of the feature 
   */
  public String getLemmaStr() {
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_lemmaStr == null)
      jcasType.jcas.throwFeatMissing("lemmaStr", "de.julielab.jules.types.Token");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Token_Type)jcasType).casFeatCode_lemmaStr);}
    
  /** setter for lemmaStr - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setLemmaStr(String v) {
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_lemmaStr == null)
      jcasType.jcas.throwFeatMissing("lemmaStr", "de.julielab.jules.types.Token");
    jcasType.ll_cas.ll_setStringValue(addr, ((Token_Type)jcasType).casFeatCode_lemmaStr, v);}    
   
    
  //*--------------*
  //* Feature: topicIds

  /** getter for topicIds - gets topic model ids, separated by spaces
   * @generated
   * @return value of the feature 
   */
  public String getTopicIds() {
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_topicIds == null)
      jcasType.jcas.throwFeatMissing("topicIds", "de.julielab.jules.types.Token");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Token_Type)jcasType).casFeatCode_topicIds);}
    
  /** setter for topicIds - sets topic model ids, separated by spaces 
   * @generated
   * @param v value to set into the feature 
   */
  public void setTopicIds(String v) {
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_topicIds == null)
      jcasType.jcas.throwFeatMissing("topicIds", "de.julielab.jules.types.Token");
    jcasType.ll_cas.ll_setStringValue(addr, ((Token_Type)jcasType).casFeatCode_topicIds, v);}    
  }

    