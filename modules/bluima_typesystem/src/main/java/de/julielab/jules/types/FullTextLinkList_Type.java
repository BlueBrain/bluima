
/* First created by JCasGen Wed Oct 19 19:10:41 CEST 2011 */
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
 * Updated by JCasGen Fri Dec 09 11:59:26 CET 2011
 * @generated */
public class FullTextLinkList_Type extends Annotation_Type {
  /** @generated */
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
  public final static int typeIndexID = FullTextLinkList.typeIndexID;
  /** @generated 
     @modifiable */
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.julielab.jules.types.FullTextLinkList");
 
  /** @generated */
  final Feature casFeat_fullTextLinks;
  /** @generated */
  final int     casFeatCode_fullTextLinks;
  /** @generated */ 
  public int getFullTextLinks(int addr) {
        if (featOkTst && casFeat_fullTextLinks == null)
      jcas.throwFeatMissing("fullTextLinks", "de.julielab.jules.types.FullTextLinkList");
    return ll_cas.ll_getRefValue(addr, casFeatCode_fullTextLinks);
  }
  /** @generated */    
  public void setFullTextLinks(int addr, int v) {
        if (featOkTst && casFeat_fullTextLinks == null)
      jcas.throwFeatMissing("fullTextLinks", "de.julielab.jules.types.FullTextLinkList");
    ll_cas.ll_setRefValue(addr, casFeatCode_fullTextLinks, v);}
    
   /** @generated */
  public int getFullTextLinks(int addr, int i) {
        if (featOkTst && casFeat_fullTextLinks == null)
      jcas.throwFeatMissing("fullTextLinks", "de.julielab.jules.types.FullTextLinkList");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_fullTextLinks), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_fullTextLinks), i);
  return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_fullTextLinks), i);
  }
   
  /** @generated */ 
  public void setFullTextLinks(int addr, int i, int v) {
        if (featOkTst && casFeat_fullTextLinks == null)
      jcas.throwFeatMissing("fullTextLinks", "de.julielab.jules.types.FullTextLinkList");
    if (lowLevelTypeChecks)
      ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_fullTextLinks), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_fullTextLinks), i);
    ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_fullTextLinks), i, v);
  }
 



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public FullTextLinkList_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_fullTextLinks = jcas.getRequiredFeatureDE(casType, "fullTextLinks", "uima.cas.FSArray", featOkTst);
    casFeatCode_fullTextLinks  = (null == casFeat_fullTextLinks) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_fullTextLinks).getCode();

  }
}



    