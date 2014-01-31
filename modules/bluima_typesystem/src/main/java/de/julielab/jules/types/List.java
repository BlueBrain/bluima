

/* First created by JCasGen Wed Oct 19 19:11:10 CEST 2011 */
package de.julielab.jules.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.FSArray;


/** used for annotation of lists
 * Updated by JCasGen Fri Oct 21 11:02:43 CEST 2011
 * XML source: /Users/ren/dev/bluebrain/svn_nlp/UIMA/julielab/trunk/julielab_typesystem-2.6.8/src/main/resources/typeSystem/julie-document-structure-types.xml
 * @generated */
public class List extends Zone {
  /** @generated
   * @ordered 
   */
  public final static int typeIndexID = JCasRegistry.register(List.class);
  /** @generated
   * @ordered 
   */
  public final static int type = typeIndexID;
  /** @generated  */
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected List() {}
    
  /** Internal - constructor used by generator 
   * @generated */
  public List(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public List(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public List(JCas jcas, int begin, int end) {
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
  //* Feature: itemList

  /** getter for itemList - gets contains items of the level 1. The items of the level 1 could contain further items of next level and so on in order to represent an iterative structure of list items.
   * @generated */
  public FSArray getItemList() {
    if (List_Type.featOkTst && ((List_Type)jcasType).casFeat_itemList == null)
      jcasType.jcas.throwFeatMissing("itemList", "de.julielab.jules.types.List");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((List_Type)jcasType).casFeatCode_itemList)));}
    
  /** setter for itemList - sets contains items of the level 1. The items of the level 1 could contain further items of next level and so on in order to represent an iterative structure of list items. 
   * @generated */
  public void setItemList(FSArray v) {
    if (List_Type.featOkTst && ((List_Type)jcasType).casFeat_itemList == null)
      jcasType.jcas.throwFeatMissing("itemList", "de.julielab.jules.types.List");
    jcasType.ll_cas.ll_setRefValue(addr, ((List_Type)jcasType).casFeatCode_itemList, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for itemList - gets an indexed value - contains items of the level 1. The items of the level 1 could contain further items of next level and so on in order to represent an iterative structure of list items.
   * @generated */
  public ListItem getItemList(int i) {
    if (List_Type.featOkTst && ((List_Type)jcasType).casFeat_itemList == null)
      jcasType.jcas.throwFeatMissing("itemList", "de.julielab.jules.types.List");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((List_Type)jcasType).casFeatCode_itemList), i);
    return (ListItem)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((List_Type)jcasType).casFeatCode_itemList), i)));}

  /** indexed setter for itemList - sets an indexed value - contains items of the level 1. The items of the level 1 could contain further items of next level and so on in order to represent an iterative structure of list items.
   * @generated */
  public void setItemList(int i, ListItem v) { 
    if (List_Type.featOkTst && ((List_Type)jcasType).casFeat_itemList == null)
      jcasType.jcas.throwFeatMissing("itemList", "de.julielab.jules.types.List");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((List_Type)jcasType).casFeatCode_itemList), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((List_Type)jcasType).casFeatCode_itemList), i, jcasType.ll_cas.ll_getFSRef(v));}
  }

    