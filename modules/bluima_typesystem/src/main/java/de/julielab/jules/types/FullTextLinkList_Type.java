
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

/** 
 * Updated by JCasGen Sat Mar 07 22:05:57 CET 2015
 * @generated */
public class FullTextLinkList_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (FullTextLinkList_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = FullTextLinkList_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new FullTextLinkList(addr, FullTextLinkList_Type.this);
  			   FullTextLinkList_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new FullTextLinkList(addr, FullTextLinkList_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = FullTextLinkList.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.julielab.jules.types.FullTextLinkList");
 
  /** @generated */
  final Feature casFeat_fullTextLinks;
  /** @generated */
  final int     casFeatCode_fullTextLinks;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getFullTextLinks(int addr) {
        if (featOkTst && casFeat_fullTextLinks == null)
      jcas.throwFeatMissing("fullTextLinks", "de.julielab.jules.types.FullTextLinkList");
    return ll_cas.ll_getRefValue(addr, casFeatCode_fullTextLinks);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setFullTextLinks(int addr, int v) {
        if (featOkTst && casFeat_fullTextLinks == null)
      jcas.throwFeatMissing("fullTextLinks", "de.julielab.jules.types.FullTextLinkList");
    ll_cas.ll_setRefValue(addr, casFeatCode_fullTextLinks, v);}
    
   /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @return value at index i in the array 
   */
  public int getFullTextLinks(int addr, int i) {
        if (featOkTst && casFeat_fullTextLinks == null)
      jcas.throwFeatMissing("fullTextLinks", "de.julielab.jules.types.FullTextLinkList");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_fullTextLinks), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_fullTextLinks), i);
	return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_fullTextLinks), i);
  }
   
  /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @param v value to set
   */ 
  public void setFullTextLinks(int addr, int i, int v) {
        if (featOkTst && casFeat_fullTextLinks == null)
      jcas.throwFeatMissing("fullTextLinks", "de.julielab.jules.types.FullTextLinkList");
    if (lowLevelTypeChecks)
      ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_fullTextLinks), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_fullTextLinks), i);
    ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_fullTextLinks), i, v);
  }
 



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public FullTextLinkList_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_fullTextLinks = jcas.getRequiredFeatureDE(casType, "fullTextLinks", "uima.cas.FSArray", featOkTst);
    casFeatCode_fullTextLinks  = (null == casFeat_fullTextLinks) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_fullTextLinks).getCode();

  }
}



    