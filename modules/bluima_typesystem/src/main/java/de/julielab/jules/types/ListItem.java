

/* First created by JCasGen Wed Oct 19 19:11:10 CEST 2011 */
package de.julielab.jules.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.FSArray;


/** item of a list
 * Updated by JCasGen Fri Oct 21 11:02:43 CEST 2011
 * XML source: /Users/ren/dev/bluebrain/svn_nlp/UIMA/julielab/trunk/julielab_typesystem-2.6.8/src/main/resources/typeSystem/julie-document-structure-types.xml
 * @generated */
public class ListItem extends Zone {
  /** @generated
   * @ordered 
   */
  public final static int typeIndexID = JCasRegistry.register(ListItem.class);
  /** @generated
   * @ordered 
   */
  public final static int type = typeIndexID;
  /** @generated  */
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected ListItem() {}
    
  /** Internal - constructor used by generator 
   * @generated */
  public ListItem(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public ListItem(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public ListItem(JCas jcas, int begin, int end) {
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

  /** getter for itemList - gets items of the next level (sub-items)
   * @generated */
  public FSArray getItemList() {
    if (ListItem_Type.featOkTst && ((ListItem_Type)jcasType).casFeat_itemList == null)
      jcasType.jcas.throwFeatMissing("itemList", "de.julielab.jules.types.ListItem");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((ListItem_Type)jcasType).casFeatCode_itemList)));}
    
  /** setter for itemList - sets items of the next level (sub-items) 
   * @generated */
  public void setItemList(FSArray v) {
    if (ListItem_Type.featOkTst && ((ListItem_Type)jcasType).casFeat_itemList == null)
      jcasType.jcas.throwFeatMissing("itemList", "de.julielab.jules.types.ListItem");
    jcasType.ll_cas.ll_setRefValue(addr, ((ListItem_Type)jcasType).casFeatCode_itemList, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for itemList - gets an indexed value - items of the next level (sub-items)
   * @generated */
  public ListItem getItemList(int i) {
    if (ListItem_Type.featOkTst && ((ListItem_Type)jcasType).casFeat_itemList == null)
      jcasType.jcas.throwFeatMissing("itemList", "de.julielab.jules.types.ListItem");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((ListItem_Type)jcasType).casFeatCode_itemList), i);
    return (ListItem)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((ListItem_Type)jcasType).casFeatCode_itemList), i)));}

  /** indexed setter for itemList - sets an indexed value - items of the next level (sub-items)
   * @generated */
  public void setItemList(int i, ListItem v) { 
    if (ListItem_Type.featOkTst && ((ListItem_Type)jcasType).casFeat_itemList == null)
      jcasType.jcas.throwFeatMissing("itemList", "de.julielab.jules.types.ListItem");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((ListItem_Type)jcasType).casFeatCode_itemList), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((ListItem_Type)jcasType).casFeatCode_itemList), i, jcasType.ll_cas.ll_getFSRef(v));}
   
    
  //*--------------*
  //* Feature: level

  /** getter for level - gets Level of indentation of the list item.
   * @generated */
  public int getLevel() {
    if (ListItem_Type.featOkTst && ((ListItem_Type)jcasType).casFeat_level == null)
      jcasType.jcas.throwFeatMissing("level", "de.julielab.jules.types.ListItem");
    return jcasType.ll_cas.ll_getIntValue(addr, ((ListItem_Type)jcasType).casFeatCode_level);}
    
  /** setter for level - sets Level of indentation of the list item. 
   * @generated */
  public void setLevel(int v) {
    if (ListItem_Type.featOkTst && ((ListItem_Type)jcasType).casFeat_level == null)
      jcasType.jcas.throwFeatMissing("level", "de.julielab.jules.types.ListItem");
    jcasType.ll_cas.ll_setIntValue(addr, ((ListItem_Type)jcasType).casFeatCode_level, v);}    
  }

    