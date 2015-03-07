

/* First created by JCasGen Sat Mar 07 22:05:57 CET 2015 */
package de.julielab.jules.types.pubmed;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.FSArray;
import de.julielab.jules.types.Keyword;
import de.julielab.jules.types.MeshHeading;
import de.julielab.jules.types.DBInfo;
import de.julielab.jules.types.Chemical;
import org.apache.uima.jcas.cas.StringArray;


/** The special type for PubMed documents
 * Updated by JCasGen Sat Mar 07 22:05:57 CET 2015
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/target/jcasgen/typesystem.xml
 * @generated */
public class ManualDescriptor extends de.julielab.jules.types.ManualDescriptor {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(ManualDescriptor.class);
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
  protected ManualDescriptor() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public ManualDescriptor(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public ManualDescriptor(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public ManualDescriptor(JCas jcas, int begin, int end) {
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
  //* Feature: meSHList

  /** getter for meSHList - gets A collection of objects of type uima.julielab.uima.MeSHHeading, O
   * @generated
   * @return value of the feature 
   */
  public FSArray getMeSHList() {
    if (ManualDescriptor_Type.featOkTst && ((ManualDescriptor_Type)jcasType).casFeat_meSHList == null)
      jcasType.jcas.throwFeatMissing("meSHList", "de.julielab.jules.types.pubmed.ManualDescriptor");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((ManualDescriptor_Type)jcasType).casFeatCode_meSHList)));}
    
  /** setter for meSHList - sets A collection of objects of type uima.julielab.uima.MeSHHeading, O 
   * @generated
   * @param v value to set into the feature 
   */
  public void setMeSHList(FSArray v) {
    if (ManualDescriptor_Type.featOkTst && ((ManualDescriptor_Type)jcasType).casFeat_meSHList == null)
      jcasType.jcas.throwFeatMissing("meSHList", "de.julielab.jules.types.pubmed.ManualDescriptor");
    jcasType.ll_cas.ll_setRefValue(addr, ((ManualDescriptor_Type)jcasType).casFeatCode_meSHList, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for meSHList - gets an indexed value - A collection of objects of type uima.julielab.uima.MeSHHeading, O
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public MeshHeading getMeSHList(int i) {
    if (ManualDescriptor_Type.featOkTst && ((ManualDescriptor_Type)jcasType).casFeat_meSHList == null)
      jcasType.jcas.throwFeatMissing("meSHList", "de.julielab.jules.types.pubmed.ManualDescriptor");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((ManualDescriptor_Type)jcasType).casFeatCode_meSHList), i);
    return (MeshHeading)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((ManualDescriptor_Type)jcasType).casFeatCode_meSHList), i)));}

  /** indexed setter for meSHList - sets an indexed value - A collection of objects of type uima.julielab.uima.MeSHHeading, O
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setMeSHList(int i, MeshHeading v) { 
    if (ManualDescriptor_Type.featOkTst && ((ManualDescriptor_Type)jcasType).casFeat_meSHList == null)
      jcasType.jcas.throwFeatMissing("meSHList", "de.julielab.jules.types.pubmed.ManualDescriptor");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((ManualDescriptor_Type)jcasType).casFeatCode_meSHList), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((ManualDescriptor_Type)jcasType).casFeatCode_meSHList), i, jcasType.ll_cas.ll_getFSRef(v));}
   
    
  //*--------------*
  //* Feature: chemicalList

  /** getter for chemicalList - gets A collection of objects of type uima.julielab.uima.Chemical, O
   * @generated
   * @return value of the feature 
   */
  public FSArray getChemicalList() {
    if (ManualDescriptor_Type.featOkTst && ((ManualDescriptor_Type)jcasType).casFeat_chemicalList == null)
      jcasType.jcas.throwFeatMissing("chemicalList", "de.julielab.jules.types.pubmed.ManualDescriptor");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((ManualDescriptor_Type)jcasType).casFeatCode_chemicalList)));}
    
  /** setter for chemicalList - sets A collection of objects of type uima.julielab.uima.Chemical, O 
   * @generated
   * @param v value to set into the feature 
   */
  public void setChemicalList(FSArray v) {
    if (ManualDescriptor_Type.featOkTst && ((ManualDescriptor_Type)jcasType).casFeat_chemicalList == null)
      jcasType.jcas.throwFeatMissing("chemicalList", "de.julielab.jules.types.pubmed.ManualDescriptor");
    jcasType.ll_cas.ll_setRefValue(addr, ((ManualDescriptor_Type)jcasType).casFeatCode_chemicalList, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for chemicalList - gets an indexed value - A collection of objects of type uima.julielab.uima.Chemical, O
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public Chemical getChemicalList(int i) {
    if (ManualDescriptor_Type.featOkTst && ((ManualDescriptor_Type)jcasType).casFeat_chemicalList == null)
      jcasType.jcas.throwFeatMissing("chemicalList", "de.julielab.jules.types.pubmed.ManualDescriptor");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((ManualDescriptor_Type)jcasType).casFeatCode_chemicalList), i);
    return (Chemical)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((ManualDescriptor_Type)jcasType).casFeatCode_chemicalList), i)));}

  /** indexed setter for chemicalList - sets an indexed value - A collection of objects of type uima.julielab.uima.Chemical, O
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setChemicalList(int i, Chemical v) { 
    if (ManualDescriptor_Type.featOkTst && ((ManualDescriptor_Type)jcasType).casFeat_chemicalList == null)
      jcasType.jcas.throwFeatMissing("chemicalList", "de.julielab.jules.types.pubmed.ManualDescriptor");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((ManualDescriptor_Type)jcasType).casFeatCode_chemicalList), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((ManualDescriptor_Type)jcasType).casFeatCode_chemicalList), i, jcasType.ll_cas.ll_getFSRef(v));}
   
    
  //*--------------*
  //* Feature: dBInfoList

  /** getter for dBInfoList - gets A collection of objects of type uima.julielab.uima.DBInfo, O
   * @generated
   * @return value of the feature 
   */
  public FSArray getDBInfoList() {
    if (ManualDescriptor_Type.featOkTst && ((ManualDescriptor_Type)jcasType).casFeat_dBInfoList == null)
      jcasType.jcas.throwFeatMissing("dBInfoList", "de.julielab.jules.types.pubmed.ManualDescriptor");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((ManualDescriptor_Type)jcasType).casFeatCode_dBInfoList)));}
    
  /** setter for dBInfoList - sets A collection of objects of type uima.julielab.uima.DBInfo, O 
   * @generated
   * @param v value to set into the feature 
   */
  public void setDBInfoList(FSArray v) {
    if (ManualDescriptor_Type.featOkTst && ((ManualDescriptor_Type)jcasType).casFeat_dBInfoList == null)
      jcasType.jcas.throwFeatMissing("dBInfoList", "de.julielab.jules.types.pubmed.ManualDescriptor");
    jcasType.ll_cas.ll_setRefValue(addr, ((ManualDescriptor_Type)jcasType).casFeatCode_dBInfoList, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for dBInfoList - gets an indexed value - A collection of objects of type uima.julielab.uima.DBInfo, O
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public DBInfo getDBInfoList(int i) {
    if (ManualDescriptor_Type.featOkTst && ((ManualDescriptor_Type)jcasType).casFeat_dBInfoList == null)
      jcasType.jcas.throwFeatMissing("dBInfoList", "de.julielab.jules.types.pubmed.ManualDescriptor");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((ManualDescriptor_Type)jcasType).casFeatCode_dBInfoList), i);
    return (DBInfo)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((ManualDescriptor_Type)jcasType).casFeatCode_dBInfoList), i)));}

  /** indexed setter for dBInfoList - sets an indexed value - A collection of objects of type uima.julielab.uima.DBInfo, O
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setDBInfoList(int i, DBInfo v) { 
    if (ManualDescriptor_Type.featOkTst && ((ManualDescriptor_Type)jcasType).casFeat_dBInfoList == null)
      jcasType.jcas.throwFeatMissing("dBInfoList", "de.julielab.jules.types.pubmed.ManualDescriptor");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((ManualDescriptor_Type)jcasType).casFeatCode_dBInfoList), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((ManualDescriptor_Type)jcasType).casFeatCode_dBInfoList), i, jcasType.ll_cas.ll_getFSRef(v));}
   
    
  //*--------------*
  //* Feature: keywordList

  /** getter for keywordList - gets A collection of objects of type uima.julielab.uima.Keyword, O
   * @generated
   * @return value of the feature 
   */
  public FSArray getKeywordList() {
    if (ManualDescriptor_Type.featOkTst && ((ManualDescriptor_Type)jcasType).casFeat_keywordList == null)
      jcasType.jcas.throwFeatMissing("keywordList", "de.julielab.jules.types.pubmed.ManualDescriptor");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((ManualDescriptor_Type)jcasType).casFeatCode_keywordList)));}
    
  /** setter for keywordList - sets A collection of objects of type uima.julielab.uima.Keyword, O 
   * @generated
   * @param v value to set into the feature 
   */
  public void setKeywordList(FSArray v) {
    if (ManualDescriptor_Type.featOkTst && ((ManualDescriptor_Type)jcasType).casFeat_keywordList == null)
      jcasType.jcas.throwFeatMissing("keywordList", "de.julielab.jules.types.pubmed.ManualDescriptor");
    jcasType.ll_cas.ll_setRefValue(addr, ((ManualDescriptor_Type)jcasType).casFeatCode_keywordList, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for keywordList - gets an indexed value - A collection of objects of type uima.julielab.uima.Keyword, O
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public Keyword getKeywordList(int i) {
    if (ManualDescriptor_Type.featOkTst && ((ManualDescriptor_Type)jcasType).casFeat_keywordList == null)
      jcasType.jcas.throwFeatMissing("keywordList", "de.julielab.jules.types.pubmed.ManualDescriptor");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((ManualDescriptor_Type)jcasType).casFeatCode_keywordList), i);
    return (Keyword)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((ManualDescriptor_Type)jcasType).casFeatCode_keywordList), i)));}

  /** indexed setter for keywordList - sets an indexed value - A collection of objects of type uima.julielab.uima.Keyword, O
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setKeywordList(int i, Keyword v) { 
    if (ManualDescriptor_Type.featOkTst && ((ManualDescriptor_Type)jcasType).casFeat_keywordList == null)
      jcasType.jcas.throwFeatMissing("keywordList", "de.julielab.jules.types.pubmed.ManualDescriptor");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((ManualDescriptor_Type)jcasType).casFeatCode_keywordList), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((ManualDescriptor_Type)jcasType).casFeatCode_keywordList), i, jcasType.ll_cas.ll_getFSRef(v));}
   
    
  //*--------------*
  //* Feature: geneSymbolList

  /** getter for geneSymbolList - gets GeneSymbolList in PubMed
   * @generated
   * @return value of the feature 
   */
  public StringArray getGeneSymbolList() {
    if (ManualDescriptor_Type.featOkTst && ((ManualDescriptor_Type)jcasType).casFeat_geneSymbolList == null)
      jcasType.jcas.throwFeatMissing("geneSymbolList", "de.julielab.jules.types.pubmed.ManualDescriptor");
    return (StringArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((ManualDescriptor_Type)jcasType).casFeatCode_geneSymbolList)));}
    
  /** setter for geneSymbolList - sets GeneSymbolList in PubMed 
   * @generated
   * @param v value to set into the feature 
   */
  public void setGeneSymbolList(StringArray v) {
    if (ManualDescriptor_Type.featOkTst && ((ManualDescriptor_Type)jcasType).casFeat_geneSymbolList == null)
      jcasType.jcas.throwFeatMissing("geneSymbolList", "de.julielab.jules.types.pubmed.ManualDescriptor");
    jcasType.ll_cas.ll_setRefValue(addr, ((ManualDescriptor_Type)jcasType).casFeatCode_geneSymbolList, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for geneSymbolList - gets an indexed value - GeneSymbolList in PubMed
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public String getGeneSymbolList(int i) {
    if (ManualDescriptor_Type.featOkTst && ((ManualDescriptor_Type)jcasType).casFeat_geneSymbolList == null)
      jcasType.jcas.throwFeatMissing("geneSymbolList", "de.julielab.jules.types.pubmed.ManualDescriptor");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((ManualDescriptor_Type)jcasType).casFeatCode_geneSymbolList), i);
    return jcasType.ll_cas.ll_getStringArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((ManualDescriptor_Type)jcasType).casFeatCode_geneSymbolList), i);}

  /** indexed setter for geneSymbolList - sets an indexed value - GeneSymbolList in PubMed
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setGeneSymbolList(int i, String v) { 
    if (ManualDescriptor_Type.featOkTst && ((ManualDescriptor_Type)jcasType).casFeat_geneSymbolList == null)
      jcasType.jcas.throwFeatMissing("geneSymbolList", "de.julielab.jules.types.pubmed.ManualDescriptor");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((ManualDescriptor_Type)jcasType).casFeatCode_geneSymbolList), i);
    jcasType.ll_cas.ll_setStringArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((ManualDescriptor_Type)jcasType).casFeatCode_geneSymbolList), i, v);}
  }

    