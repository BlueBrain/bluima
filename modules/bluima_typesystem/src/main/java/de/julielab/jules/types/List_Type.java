
/* First created by JCasGen Wed Oct 19 19:11:10 CEST 2011 */
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

/** used for annotation of lists
 * Updated by JCasGen Fri Oct 21 11:02:43 CEST 2011
 * @generated */
public class List_Type extends Zone_Type {
  /** @generated */
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (List_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = List_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new List(addr, List_Type.this);
  			   List_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new List(addr, List_Type.this);
  	  }
    };
  /** @generated */
  public final static int typeIndexID = List.typeIndexID;
  /** @generated 
     @modifiable */
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.julielab.jules.types.List");
 
  /** @generated */
  final Feature casFeat_itemList;
  /** @generated */
  final int     casFeatCode_itemList;
  /** @generated */ 
  public int getItemList(int addr) {
        if (featOkTst && casFeat_itemList == null)
      jcas.throwFeatMissing("itemList", "de.julielab.jules.types.List");
    return ll_cas.ll_getRefValue(addr, casFeatCode_itemList);
  }
  /** @generated */    
  public void setItemList(int addr, int v) {
        if (featOkTst && casFeat_itemList == null)
      jcas.throwFeatMissing("itemList", "de.julielab.jules.types.List");
    ll_cas.ll_setRefValue(addr, casFeatCode_itemList, v);}
    
   /** @generated */
  public int getItemList(int addr, int i) {
        if (featOkTst && casFeat_itemList == null)
      jcas.throwFeatMissing("itemList", "de.julielab.jules.types.List");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_itemList), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_itemList), i);
  return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_itemList), i);
  }
   
  /** @generated */ 
  public void setItemList(int addr, int i, int v) {
        if (featOkTst && casFeat_itemList == null)
      jcas.throwFeatMissing("itemList", "de.julielab.jules.types.List");
    if (lowLevelTypeChecks)
      ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_itemList), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_itemList), i);
    ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_itemList), i, v);
  }
 



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public List_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_itemList = jcas.getRequiredFeatureDE(casType, "itemList", "uima.cas.FSArray", featOkTst);
    casFeatCode_itemList  = (null == casFeat_itemList) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_itemList).getCode();

  }
}



    