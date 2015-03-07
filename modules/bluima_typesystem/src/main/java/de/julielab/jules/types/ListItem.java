

/* First created by JCasGen Sat Mar 07 22:05:57 CET 2015 */
package de.julielab.jules.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.FSArray;


/** item of a list
 * Updated by JCasGen Sat Mar 07 22:05:57 CET 2015
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/target/jcasgen/typesystem.xml
 * @generated */
public class ListItem extends Zone {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(ListItem.class);
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
  protected ListItem() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public ListItem(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public ListItem(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public ListItem(JCas jcas, int begin, int end) {
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

  /** getter for itemList - gets items of the next level (sub-items)
   * @generated
   * @return value of the feature 
   */
  public FSArray getItemList() {
    if (ListItem_Type.featOkTst && ((ListItem_Type)jcasType).casFeat_itemList == null)
      jcasType.jcas.throwFeatMissing("itemList", "de.julielab.jules.types.ListItem");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((ListItem_Type)jcasType).casFeatCode_itemList)));}
    
  /** setter for itemList - sets items of the next level (sub-items) 
   * @generated
   * @param v value to set into the feature 
   */
  public void setItemList(FSArray v) {
    if (ListItem_Type.featOkTst && ((ListItem_Type)jcasType).casFeat_itemList == null)
      jcasType.jcas.throwFeatMissing("itemList", "de.julielab.jules.types.ListItem");
    jcasType.ll_cas.ll_setRefValue(addr, ((ListItem_Type)jcasType).casFeatCode_itemList, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for itemList - gets an indexed value - items of the next level (sub-items)
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public ListItem getItemList(int i) {
    if (ListItem_Type.featOkTst && ((ListItem_Type)jcasType).casFeat_itemList == null)
      jcasType.jcas.throwFeatMissing("itemList", "de.julielab.jules.types.ListItem");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((ListItem_Type)jcasType).casFeatCode_itemList), i);
    return (ListItem)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((ListItem_Type)jcasType).casFeatCode_itemList), i)));}

  /** indexed setter for itemList - sets an indexed value - items of the next level (sub-items)
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setItemList(int i, ListItem v) { 
    if (ListItem_Type.featOkTst && ((ListItem_Type)jcasType).casFeat_itemList == null)
      jcasType.jcas.throwFeatMissing("itemList", "de.julielab.jules.types.ListItem");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((ListItem_Type)jcasType).casFeatCode_itemList), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((ListItem_Type)jcasType).casFeatCode_itemList), i, jcasType.ll_cas.ll_getFSRef(v));}
   
    
  //*--------------*
  //* Feature: level

  /** getter for level - gets Level of indentation of the list item.
   * @generated
   * @return value of the feature 
   */
  public int getLevel() {
    if (ListItem_Type.featOkTst && ((ListItem_Type)jcasType).casFeat_level == null)
      jcasType.jcas.throwFeatMissing("level", "de.julielab.jules.types.ListItem");
    return jcasType.ll_cas.ll_getIntValue(addr, ((ListItem_Type)jcasType).casFeatCode_level);}
    
  /** setter for level - sets Level of indentation of the list item. 
   * @generated
   * @param v value to set into the feature 
   */
  public void setLevel(int v) {
    if (ListItem_Type.featOkTst && ((ListItem_Type)jcasType).casFeat_level == null)
      jcasType.jcas.throwFeatMissing("level", "de.julielab.jules.types.ListItem");
    jcasType.ll_cas.ll_setIntValue(addr, ((ListItem_Type)jcasType).casFeatCode_level, v);}    
  }

    