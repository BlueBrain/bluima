
/* First created by JCasGen Wed Oct 19 19:10:28 CEST 2011 */
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
 * Updated by JCasGen Mon Feb 17 22:12:57 CET 2014
 * @generated */
public class GOMention_Type extends ConceptMention_Type {
  /** @generated */
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
  public final static int typeIndexID = GOMention.typeIndexID;
  /** @generated 
     @modifiable */
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.julielab.jules.types.GOMention");
 
  /** @generated */
  final Feature casFeat_categories;
  /** @generated */
  final int     casFeatCode_categories;
  /** @generated */ 
  public int getCategories(int addr) {
        if (featOkTst && casFeat_categories == null)
      jcas.throwFeatMissing("categories", "de.julielab.jules.types.GOMention");
    return ll_cas.ll_getRefValue(addr, casFeatCode_categories);
  }
  /** @generated */    
  public void setCategories(int addr, int v) {
        if (featOkTst && casFeat_categories == null)
      jcas.throwFeatMissing("categories", "de.julielab.jules.types.GOMention");
    ll_cas.ll_setRefValue(addr, casFeatCode_categories, v);}
    
   /** @generated */
  public String getCategories(int addr, int i) {
        if (featOkTst && casFeat_categories == null)
      jcas.throwFeatMissing("categories", "de.julielab.jules.types.GOMention");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_categories), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_categories), i);
  return ll_cas.ll_getStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_categories), i);
  }
   
  /** @generated */ 
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
  /** @generated */ 
  public String getGoID(int addr) {
        if (featOkTst && casFeat_goID == null)
      jcas.throwFeatMissing("goID", "de.julielab.jules.types.GOMention");
    return ll_cas.ll_getStringValue(addr, casFeatCode_goID);
  }
  /** @generated */    
  public void setGoID(int addr, String v) {
        if (featOkTst && casFeat_goID == null)
      jcas.throwFeatMissing("goID", "de.julielab.jules.types.GOMention");
    ll_cas.ll_setStringValue(addr, casFeatCode_goID, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public GOMention_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_categories = jcas.getRequiredFeatureDE(casType, "categories", "uima.cas.StringArray", featOkTst);
    casFeatCode_categories  = (null == casFeat_categories) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_categories).getCode();

 
    casFeat_goID = jcas.getRequiredFeatureDE(casType, "goID", "uima.cas.String", featOkTst);
    casFeatCode_goID  = (null == casFeat_goID) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_goID).getCode();

  }
}



    