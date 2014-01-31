
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

/** References to other databases, e.g. SwissProt, INTERPRO e.g.
 * Updated by JCasGen Fri Dec 09 11:59:26 CET 2011
 * @generated */
public class DBInfo_Type extends Annotation_Type {
  /** @generated */
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (DBInfo_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = DBInfo_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new DBInfo(addr, DBInfo_Type.this);
  			   DBInfo_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new DBInfo(addr, DBInfo_Type.this);
  	  }
    };
  /** @generated */
  public final static int typeIndexID = DBInfo.typeIndexID;
  /** @generated 
     @modifiable */
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.julielab.jules.types.DBInfo");
 
  /** @generated */
  final Feature casFeat_name;
  /** @generated */
  final int     casFeatCode_name;
  /** @generated */ 
  public String getName(int addr) {
        if (featOkTst && casFeat_name == null)
      jcas.throwFeatMissing("name", "de.julielab.jules.types.DBInfo");
    return ll_cas.ll_getStringValue(addr, casFeatCode_name);
  }
  /** @generated */    
  public void setName(int addr, String v) {
        if (featOkTst && casFeat_name == null)
      jcas.throwFeatMissing("name", "de.julielab.jules.types.DBInfo");
    ll_cas.ll_setStringValue(addr, casFeatCode_name, v);}
    
  
 
  /** @generated */
  final Feature casFeat_acList;
  /** @generated */
  final int     casFeatCode_acList;
  /** @generated */ 
  public int getAcList(int addr) {
        if (featOkTst && casFeat_acList == null)
      jcas.throwFeatMissing("acList", "de.julielab.jules.types.DBInfo");
    return ll_cas.ll_getRefValue(addr, casFeatCode_acList);
  }
  /** @generated */    
  public void setAcList(int addr, int v) {
        if (featOkTst && casFeat_acList == null)
      jcas.throwFeatMissing("acList", "de.julielab.jules.types.DBInfo");
    ll_cas.ll_setRefValue(addr, casFeatCode_acList, v);}
    
   /** @generated */
  public String getAcList(int addr, int i) {
        if (featOkTst && casFeat_acList == null)
      jcas.throwFeatMissing("acList", "de.julielab.jules.types.DBInfo");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_acList), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_acList), i);
  return ll_cas.ll_getStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_acList), i);
  }
   
  /** @generated */ 
  public void setAcList(int addr, int i, String v) {
        if (featOkTst && casFeat_acList == null)
      jcas.throwFeatMissing("acList", "de.julielab.jules.types.DBInfo");
    if (lowLevelTypeChecks)
      ll_cas.ll_setStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_acList), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_acList), i);
    ll_cas.ll_setStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_acList), i, v);
  }
 



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public DBInfo_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_name = jcas.getRequiredFeatureDE(casType, "name", "uima.cas.String", featOkTst);
    casFeatCode_name  = (null == casFeat_name) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_name).getCode();

 
    casFeat_acList = jcas.getRequiredFeatureDE(casType, "acList", "uima.cas.StringArray", featOkTst);
    casFeatCode_acList  = (null == casFeat_acList) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_acList).getCode();

  }
}



    