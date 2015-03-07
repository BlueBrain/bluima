

/* First created by JCasGen Sat Mar 07 22:05:57 CET 2015 */
package de.julielab.jules.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.FSArray;


/** used for annotation of lists
 * Updated by JCasGen Sat Mar 07 22:05:57 CET 2015
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/target/jcasgen/typesystem.xml
 * @generated */
public class List extends Zone {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(List.class);
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
  protected List() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public List(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public List(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public List(JCas jcas, int begin, int end) {
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
  //* Feature: itemList

  /** getter for itemList - gets contains items of the level 1. The items of the level 1 could contain further items of next level and so on in order to represent an iterative structure of list items.
   * @generated
   * @return value of the feature 
   */
  public FSArray getItemList() {
    if (List_Type.featOkTst && ((List_Type)jcasType).casFeat_itemList == null)
      jcasType.jcas.throwFeatMissing("itemList", "de.julielab.jules.types.List");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((List_Type)jcasType).casFeatCode_itemList)));}
    
  /** setter for itemList - sets contains items of the level 1. The items of the level 1 could contain further items of next level and so on in order to represent an iterative structure of list items. 
   * @generated
   * @param v value to set into the feature 
   */
  public void setItemList(FSArray v) {
    if (List_Type.featOkTst && ((List_Type)jcasType).casFeat_itemList == null)
      jcasType.jcas.throwFeatMissing("itemList", "de.julielab.jules.types.List");
    jcasType.ll_cas.ll_setRefValue(addr, ((List_Type)jcasType).casFeatCode_itemList, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for itemList - gets an indexed value - contains items of the level 1. The items of the level 1 could contain further items of next level and so on in order to represent an iterative structure of list items.
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public ListItem getItemList(int i) {
    if (List_Type.featOkTst && ((List_Type)jcasType).casFeat_itemList == null)
      jcasType.jcas.throwFeatMissing("itemList", "de.julielab.jules.types.List");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((List_Type)jcasType).casFeatCode_itemList), i);
    return (ListItem)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((List_Type)jcasType).casFeatCode_itemList), i)));}

  /** indexed setter for itemList - sets an indexed value - contains items of the level 1. The items of the level 1 could contain further items of next level and so on in order to represent an iterative structure of list items.
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setItemList(int i, ListItem v) { 
    if (List_Type.featOkTst && ((List_Type)jcasType).casFeat_itemList == null)
      jcasType.jcas.throwFeatMissing("itemList", "de.julielab.jules.types.List");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((List_Type)jcasType).casFeatCode_itemList), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((List_Type)jcasType).casFeatCode_itemList), i, jcasType.ll_cas.ll_getFSRef(v));}
  }

    