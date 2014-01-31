

/* First created by JCasGen Wed Oct 19 19:10:41 CEST 2011 */
package de.julielab.jules.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.FSArray;


/** 
 * Updated by JCasGen Fri Dec 09 11:59:26 CET 2011
 * XML source: /Users/richarde/dev/bluebrain/svn_nlp/UIMA/blue_uima/trunk/julielab_typesystem-2.6.8/src/main/resources/typeSystem/julie-document-meta-types.xml
 * @generated */
public class FullTextLinkList extends Annotation {
  /** @generated
   * @ordered 
   */
  public final static int typeIndexID = JCasRegistry.register(FullTextLinkList.class);
  /** @generated
   * @ordered 
   */
  public final static int type = typeIndexID;
  /** @generated  */
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected FullTextLinkList() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public FullTextLinkList(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public FullTextLinkList(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public FullTextLinkList(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** <!-- begin-user-doc -->
    * Write your own initialization here
    * <!-- end-user-doc -->
  @generated modifiable */
  private void readObject() {}
     
 
    
  //*--------------*
  //* Feature: fullTextLinks

  /** getter for fullTextLinks - gets 
   * @generated */
  public FSArray getFullTextLinks() {
    if (FullTextLinkList_Type.featOkTst && ((FullTextLinkList_Type)jcasType).casFeat_fullTextLinks == null)
      jcasType.jcas.throwFeatMissing("fullTextLinks", "de.julielab.jules.types.FullTextLinkList");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((FullTextLinkList_Type)jcasType).casFeatCode_fullTextLinks)));}
    
  /** setter for fullTextLinks - sets  
   * @generated */
  public void setFullTextLinks(FSArray v) {
    if (FullTextLinkList_Type.featOkTst && ((FullTextLinkList_Type)jcasType).casFeat_fullTextLinks == null)
      jcasType.jcas.throwFeatMissing("fullTextLinks", "de.julielab.jules.types.FullTextLinkList");
    jcasType.ll_cas.ll_setRefValue(addr, ((FullTextLinkList_Type)jcasType).casFeatCode_fullTextLinks, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for fullTextLinks - gets an indexed value - 
   * @generated */
  public FullTextLink getFullTextLinks(int i) {
    if (FullTextLinkList_Type.featOkTst && ((FullTextLinkList_Type)jcasType).casFeat_fullTextLinks == null)
      jcasType.jcas.throwFeatMissing("fullTextLinks", "de.julielab.jules.types.FullTextLinkList");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((FullTextLinkList_Type)jcasType).casFeatCode_fullTextLinks), i);
    return (FullTextLink)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((FullTextLinkList_Type)jcasType).casFeatCode_fullTextLinks), i)));}

  /** indexed setter for fullTextLinks - sets an indexed value - 
   * @generated */
  public void setFullTextLinks(int i, FullTextLink v) { 
    if (FullTextLinkList_Type.featOkTst && ((FullTextLinkList_Type)jcasType).casFeat_fullTextLinks == null)
      jcasType.jcas.throwFeatMissing("fullTextLinks", "de.julielab.jules.types.FullTextLinkList");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((FullTextLinkList_Type)jcasType).casFeatCode_fullTextLinks), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((FullTextLinkList_Type)jcasType).casFeatCode_fullTextLinks), i, jcasType.ll_cas.ll_getFSRef(v));}
  }

    