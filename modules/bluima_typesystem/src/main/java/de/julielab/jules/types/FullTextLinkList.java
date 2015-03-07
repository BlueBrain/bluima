

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
public class FullTextLinkList extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(FullTextLinkList.class);
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
  protected FullTextLinkList() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public FullTextLinkList(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public FullTextLinkList(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public FullTextLinkList(JCas jcas, int begin, int end) {
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
  //* Feature: fullTextLinks

  /** getter for fullTextLinks - gets 
   * @generated
   * @return value of the feature 
   */
  public FSArray getFullTextLinks() {
    if (FullTextLinkList_Type.featOkTst && ((FullTextLinkList_Type)jcasType).casFeat_fullTextLinks == null)
      jcasType.jcas.throwFeatMissing("fullTextLinks", "de.julielab.jules.types.FullTextLinkList");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((FullTextLinkList_Type)jcasType).casFeatCode_fullTextLinks)));}
    
  /** setter for fullTextLinks - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setFullTextLinks(FSArray v) {
    if (FullTextLinkList_Type.featOkTst && ((FullTextLinkList_Type)jcasType).casFeat_fullTextLinks == null)
      jcasType.jcas.throwFeatMissing("fullTextLinks", "de.julielab.jules.types.FullTextLinkList");
    jcasType.ll_cas.ll_setRefValue(addr, ((FullTextLinkList_Type)jcasType).casFeatCode_fullTextLinks, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for fullTextLinks - gets an indexed value - 
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public FullTextLink getFullTextLinks(int i) {
    if (FullTextLinkList_Type.featOkTst && ((FullTextLinkList_Type)jcasType).casFeat_fullTextLinks == null)
      jcasType.jcas.throwFeatMissing("fullTextLinks", "de.julielab.jules.types.FullTextLinkList");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((FullTextLinkList_Type)jcasType).casFeatCode_fullTextLinks), i);
    return (FullTextLink)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((FullTextLinkList_Type)jcasType).casFeatCode_fullTextLinks), i)));}

  /** indexed setter for fullTextLinks - sets an indexed value - 
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setFullTextLinks(int i, FullTextLink v) { 
    if (FullTextLinkList_Type.featOkTst && ((FullTextLinkList_Type)jcasType).casFeat_fullTextLinks == null)
      jcasType.jcas.throwFeatMissing("fullTextLinks", "de.julielab.jules.types.FullTextLinkList");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((FullTextLinkList_Type)jcasType).casFeatCode_fullTextLinks), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((FullTextLinkList_Type)jcasType).casFeatCode_fullTextLinks), i, jcasType.ll_cas.ll_getFSRef(v));}
  }

    