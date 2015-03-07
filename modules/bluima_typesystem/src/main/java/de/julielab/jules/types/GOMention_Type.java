
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
public class GOMention_Type extends ConceptMention_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (GOMention_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = GOMention_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new GOMention(addr, GOMention_Type.this);
  			   GOMention_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new GOMention(addr, GOMention_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = GOMention.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.julielab.jules.types.GOMention");
 
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
      jcas.throwFeatMissing("categories", "de.julielab.jules.types.GOMention");
    return ll_cas.ll_getRefValue(addr, casFeatCode_categories);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setCategories(int addr, int v) {
        if (featOkTst && casFeat_categories == null)
      jcas.throwFeatMissing("categories", "de.julielab.jules.types.GOMention");
    ll_cas.ll_setRefValue(addr, casFeatCode_categories, v);}
    
   /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @return value at index i in the array 
   */
  public String getCategories(int addr, int i) {
        if (featOkTst && casFeat_categories == null)
      jcas.throwFeatMissing("categories", "de.julielab.jules.types.GOMention");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_categories), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_categories), i);
	return ll_cas.ll_getStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_categories), i);
  }
   
  /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @param v value to set
   */ 
  public void setCategories(int addr, int i, String v) {
        if (featOkTst && casFeat_categories == null)
      jcas.throwFeatMissing("categories", "de.julielab.jules.types.GOMention");
    if (lowLevelTypeChecks)
      ll_cas.ll_setStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_categories), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_categories), i);
    ll_cas.ll_setStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_categories), i, v);
  }
 
 
  /** @generated */
  final Feature casFeat_goID;
  /** @generated */
  final int     casFeatCode_goID;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getGoID(int addr) {
        if (featOkTst && casFeat_goID == null)
      jcas.throwFeatMissing("goID", "de.julielab.jules.types.GOMention");
    return ll_cas.ll_getStringValue(addr, casFeatCode_goID);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setGoID(int addr, String v) {
        if (featOkTst && casFeat_goID == null)
      jcas.throwFeatMissing("goID", "de.julielab.jules.types.GOMention");
    ll_cas.ll_setStringValue(addr, casFeatCode_goID, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public GOMention_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_categories = jcas.getRequiredFeatureDE(casType, "categories", "uima.cas.StringArray", featOkTst);
    casFeatCode_categories  = (null == casFeat_categories) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_categories).getCode();

 
    casFeat_goID = jcas.getRequiredFeatureDE(casType, "goID", "uima.cas.String", featOkTst);
    casFeatCode_goID  = (null == casFeat_goID) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_goID).getCode();

  }
}



    