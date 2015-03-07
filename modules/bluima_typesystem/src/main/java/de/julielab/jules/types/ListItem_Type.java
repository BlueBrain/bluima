
/* First created by JCasGen Sat Mar 07 22:05:57 CET 2015 */
package de.julielab.jules.types;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;

/** item of a list
 * Updated by JCasGen Sat Mar 07 22:05:57 CET 2015
 * @generated */
public class ListItem_Type extends Zone_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (ListItem_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = ListItem_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new ListItem(addr, ListItem_Type.this);
  			   ListItem_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new ListItem(addr, ListItem_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = ListItem.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.julielab.jules.types.ListItem");
 
  /** @generated */
  final Feature casFeat_itemList;
  /** @generated */
  final int     casFeatCode_itemList;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getItemList(int addr) {
        if (featOkTst && casFeat_itemList == null)
      jcas.throwFeatMissing("itemList", "de.julielab.jules.types.ListItem");
    return ll_cas.ll_getRefValue(addr, casFeatCode_itemList);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setItemList(int addr, int v) {
        if (featOkTst && casFeat_itemList == null)
      jcas.throwFeatMissing("itemList", "de.julielab.jules.types.ListItem");
    ll_cas.ll_setRefValue(addr, casFeatCode_itemList, v);}
    
   /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @return value at index i in the array 
   */
  public int getItemList(int addr, int i) {
        if (featOkTst && casFeat_itemList == null)
      jcas.throwFeatMissing("itemList", "de.julielab.jules.types.ListItem");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_itemList), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_itemList), i);
	return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_itemList), i);
  }
   
  /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @param v value to set
   */ 
  public void setItemList(int addr, int i, int v) {
        if (featOkTst && casFeat_itemList == null)
      jcas.throwFeatMissing("itemList", "de.julielab.jules.types.ListItem");
    if (lowLevelTypeChecks)
      ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_itemList), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_itemList), i);
    ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_itemList), i, v);
  }
 
 
  /** @generated */
  final Feature casFeat_level;
  /** @generated */
  final int     casFeatCode_level;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getLevel(int addr) {
        if (featOkTst && casFeat_level == null)
      jcas.throwFeatMissing("level", "de.julielab.jules.types.ListItem");
    return ll_cas.ll_getIntValue(addr, casFeatCode_level);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setLevel(int addr, int v) {
        if (featOkTst && casFeat_level == null)
      jcas.throwFeatMissing("level", "de.julielab.jules.types.ListItem");
    ll_cas.ll_setIntValue(addr, casFeatCode_level, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public ListItem_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_itemList = jcas.getRequiredFeatureDE(casType, "itemList", "uima.cas.FSArray", featOkTst);
    casFeatCode_itemList  = (null == casFeat_itemList) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_itemList).getCode();

 
    casFeat_level = jcas.getRequiredFeatureDE(casType, "level", "uima.cas.Integer", featOkTst);
    casFeatCode_level  = (null == casFeat_level) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_level).getCode();

  }
}



    