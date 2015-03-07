
/* First created by JCasGen Sat Mar 07 22:05:57 CET 2015 */
package de.julielab.jules.types.wikipedia;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;

/** Descriptor for Wikipedia pages. It covers a list of Wikipedia categories associated with the page, a list of incoming and outgoing links, and a list of associated redirects.
 * Updated by JCasGen Sat Mar 07 22:05:57 CET 2015
 * @generated */
public class Descriptor_Type extends de.julielab.jules.types.Descriptor_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Descriptor_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Descriptor_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Descriptor(addr, Descriptor_Type.this);
  			   Descriptor_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Descriptor(addr, Descriptor_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Descriptor.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.julielab.jules.types.wikipedia.Descriptor");
 
  /** @generated */
  final Feature casFeat_categories;
  /** @generated */
  final int     casFeatCode_categories;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getCategories(int addr) {
        if (featOkTst && casFeat_categories == null)
      jcas.throwFeatMissing("categories", "de.julielab.jules.types.wikipedia.Descriptor");
    return ll_cas.ll_getRefValue(addr, casFeatCode_categories);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setCategories(int addr, int v) {
        if (featOkTst && casFeat_categories == null)
      jcas.throwFeatMissing("categories", "de.julielab.jules.types.wikipedia.Descriptor");
    ll_cas.ll_setRefValue(addr, casFeatCode_categories, v);}
    
   /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @return value at index i in the array 
   */
  public int getCategories(int addr, int i) {
        if (featOkTst && casFeat_categories == null)
      jcas.throwFeatMissing("categories", "de.julielab.jules.types.wikipedia.Descriptor");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_categories), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_categories), i);
	return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_categories), i);
  }
   
  /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @param v value to set
   */ 
  public void setCategories(int addr, int i, int v) {
        if (featOkTst && casFeat_categories == null)
      jcas.throwFeatMissing("categories", "de.julielab.jules.types.wikipedia.Descriptor");
    if (lowLevelTypeChecks)
      ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_categories), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_categories), i);
    ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_categories), i, v);
  }
 
 
  /** @generated */
  final Feature casFeat_incomingLinks;
  /** @generated */
  final int     casFeatCode_incomingLinks;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getIncomingLinks(int addr) {
        if (featOkTst && casFeat_incomingLinks == null)
      jcas.throwFeatMissing("incomingLinks", "de.julielab.jules.types.wikipedia.Descriptor");
    return ll_cas.ll_getRefValue(addr, casFeatCode_incomingLinks);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setIncomingLinks(int addr, int v) {
        if (featOkTst && casFeat_incomingLinks == null)
      jcas.throwFeatMissing("incomingLinks", "de.julielab.jules.types.wikipedia.Descriptor");
    ll_cas.ll_setRefValue(addr, casFeatCode_incomingLinks, v);}
    
   /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @return value at index i in the array 
   */
  public int getIncomingLinks(int addr, int i) {
        if (featOkTst && casFeat_incomingLinks == null)
      jcas.throwFeatMissing("incomingLinks", "de.julielab.jules.types.wikipedia.Descriptor");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_incomingLinks), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_incomingLinks), i);
	return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_incomingLinks), i);
  }
   
  /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @param v value to set
   */ 
  public void setIncomingLinks(int addr, int i, int v) {
        if (featOkTst && casFeat_incomingLinks == null)
      jcas.throwFeatMissing("incomingLinks", "de.julielab.jules.types.wikipedia.Descriptor");
    if (lowLevelTypeChecks)
      ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_incomingLinks), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_incomingLinks), i);
    ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_incomingLinks), i, v);
  }
 
 
  /** @generated */
  final Feature casFeat_outgoingLinks;
  /** @generated */
  final int     casFeatCode_outgoingLinks;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getOutgoingLinks(int addr) {
        if (featOkTst && casFeat_outgoingLinks == null)
      jcas.throwFeatMissing("outgoingLinks", "de.julielab.jules.types.wikipedia.Descriptor");
    return ll_cas.ll_getRefValue(addr, casFeatCode_outgoingLinks);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setOutgoingLinks(int addr, int v) {
        if (featOkTst && casFeat_outgoingLinks == null)
      jcas.throwFeatMissing("outgoingLinks", "de.julielab.jules.types.wikipedia.Descriptor");
    ll_cas.ll_setRefValue(addr, casFeatCode_outgoingLinks, v);}
    
   /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @return value at index i in the array 
   */
  public int getOutgoingLinks(int addr, int i) {
        if (featOkTst && casFeat_outgoingLinks == null)
      jcas.throwFeatMissing("outgoingLinks", "de.julielab.jules.types.wikipedia.Descriptor");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_outgoingLinks), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_outgoingLinks), i);
	return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_outgoingLinks), i);
  }
   
  /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @param v value to set
   */ 
  public void setOutgoingLinks(int addr, int i, int v) {
        if (featOkTst && casFeat_outgoingLinks == null)
      jcas.throwFeatMissing("outgoingLinks", "de.julielab.jules.types.wikipedia.Descriptor");
    if (lowLevelTypeChecks)
      ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_outgoingLinks), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_outgoingLinks), i);
    ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_outgoingLinks), i, v);
  }
 
 
  /** @generated */
  final Feature casFeat_redirects;
  /** @generated */
  final int     casFeatCode_redirects;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getRedirects(int addr) {
        if (featOkTst && casFeat_redirects == null)
      jcas.throwFeatMissing("redirects", "de.julielab.jules.types.wikipedia.Descriptor");
    return ll_cas.ll_getRefValue(addr, casFeatCode_redirects);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setRedirects(int addr, int v) {
        if (featOkTst && casFeat_redirects == null)
      jcas.throwFeatMissing("redirects", "de.julielab.jules.types.wikipedia.Descriptor");
    ll_cas.ll_setRefValue(addr, casFeatCode_redirects, v);}
    
   /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @return value at index i in the array 
   */
  public String getRedirects(int addr, int i) {
        if (featOkTst && casFeat_redirects == null)
      jcas.throwFeatMissing("redirects", "de.julielab.jules.types.wikipedia.Descriptor");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_redirects), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_redirects), i);
	return ll_cas.ll_getStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_redirects), i);
  }
   
  /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @param v value to set
   */ 
  public void setRedirects(int addr, int i, String v) {
        if (featOkTst && casFeat_redirects == null)
      jcas.throwFeatMissing("redirects", "de.julielab.jules.types.wikipedia.Descriptor");
    if (lowLevelTypeChecks)
      ll_cas.ll_setStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_redirects), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_redirects), i);
    ll_cas.ll_setStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_redirects), i, v);
  }
 



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public Descriptor_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_categories = jcas.getRequiredFeatureDE(casType, "categories", "uima.cas.FSArray", featOkTst);
    casFeatCode_categories  = (null == casFeat_categories) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_categories).getCode();

 
    casFeat_incomingLinks = jcas.getRequiredFeatureDE(casType, "incomingLinks", "uima.cas.FSArray", featOkTst);
    casFeatCode_incomingLinks  = (null == casFeat_incomingLinks) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_incomingLinks).getCode();

 
    casFeat_outgoingLinks = jcas.getRequiredFeatureDE(casType, "outgoingLinks", "uima.cas.FSArray", featOkTst);
    casFeatCode_outgoingLinks  = (null == casFeat_outgoingLinks) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_outgoingLinks).getCode();

 
    casFeat_redirects = jcas.getRequiredFeatureDE(casType, "redirects", "uima.cas.StringArray", featOkTst);
    casFeatCode_redirects  = (null == casFeat_redirects) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_redirects).getCode();

  }
}



    