
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
public class CoordinationElement_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (CoordinationElement_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = CoordinationElement_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new CoordinationElement(addr, CoordinationElement_Type.this);
  			   CoordinationElement_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new CoordinationElement(addr, CoordinationElement_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = CoordinationElement.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.julielab.jules.types.CoordinationElement");
 
  /** @generated */
  final Feature casFeat_cat;
  /** @generated */
  final int     casFeatCode_cat;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getCat(int addr) {
        if (featOkTst && casFeat_cat == null)
      jcas.throwFeatMissing("cat", "de.julielab.jules.types.CoordinationElement");
    return ll_cas.ll_getStringValue(addr, casFeatCode_cat);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setCat(int addr, String v) {
        if (featOkTst && casFeat_cat == null)
      jcas.throwFeatMissing("cat", "de.julielab.jules.types.CoordinationElement");
    ll_cas.ll_setStringValue(addr, casFeatCode_cat, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public CoordinationElement_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_cat = jcas.getRequiredFeatureDE(casType, "cat", "de.julielab.jules.types.CoordinationElement_Value", featOkTst);
    casFeatCode_cat  = (null == casFeat_cat) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_cat).getCode();

  }
}



    