
/* First created by JCasGen Sat Mar 07 22:05:57 CET 2015 */
package de.julielab.jules.types.muc7;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import de.julielab.jules.types.EntityMention_Type;

/** the NUMEX NE in MUC7
 * Updated by JCasGen Sat Mar 07 22:05:57 CET 2015
 * @generated */
public class NUMEX_Type extends EntityMention_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (NUMEX_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = NUMEX_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new NUMEX(addr, NUMEX_Type.this);
  			   NUMEX_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new NUMEX(addr, NUMEX_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = NUMEX.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.julielab.jules.types.muc7.NUMEX");
 
  /** @generated */
  final Feature casFeat_min;
  /** @generated */
  final int     casFeatCode_min;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getMin(int addr) {
        if (featOkTst && casFeat_min == null)
      jcas.throwFeatMissing("min", "de.julielab.jules.types.muc7.NUMEX");
    return ll_cas.ll_getStringValue(addr, casFeatCode_min);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setMin(int addr, String v) {
        if (featOkTst && casFeat_min == null)
      jcas.throwFeatMissing("min", "de.julielab.jules.types.muc7.NUMEX");
    ll_cas.ll_setStringValue(addr, casFeatCode_min, v);}
    
  
 
  /** @generated */
  final Feature casFeat_typeOfNE;
  /** @generated */
  final int     casFeatCode_typeOfNE;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getTypeOfNE(int addr) {
        if (featOkTst && casFeat_typeOfNE == null)
      jcas.throwFeatMissing("typeOfNE", "de.julielab.jules.types.muc7.NUMEX");
    return ll_cas.ll_getStringValue(addr, casFeatCode_typeOfNE);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setTypeOfNE(int addr, String v) {
        if (featOkTst && casFeat_typeOfNE == null)
      jcas.throwFeatMissing("typeOfNE", "de.julielab.jules.types.muc7.NUMEX");
    ll_cas.ll_setStringValue(addr, casFeatCode_typeOfNE, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public NUMEX_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_min = jcas.getRequiredFeatureDE(casType, "min", "uima.cas.String", featOkTst);
    casFeatCode_min  = (null == casFeat_min) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_min).getCode();

 
    casFeat_typeOfNE = jcas.getRequiredFeatureDE(casType, "typeOfNE", "uima.cas.String", featOkTst);
    casFeatCode_typeOfNE  = (null == casFeat_typeOfNE) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_typeOfNE).getCode();

  }
}



    