
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
import de.julielab.jules.types.Header_Type;

/** the MUC7Header has the storyID as additional type
 * Updated by JCasGen Sat Mar 07 22:05:57 CET 2015
 * @generated */
public class MUC7Header_Type extends Header_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (MUC7Header_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = MUC7Header_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new MUC7Header(addr, MUC7Header_Type.this);
  			   MUC7Header_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new MUC7Header(addr, MUC7Header_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = MUC7Header.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.julielab.jules.types.muc7.MUC7Header");
 
  /** @generated */
  final Feature casFeat_storyID;
  /** @generated */
  final int     casFeatCode_storyID;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getStoryID(int addr) {
        if (featOkTst && casFeat_storyID == null)
      jcas.throwFeatMissing("storyID", "de.julielab.jules.types.muc7.MUC7Header");
    return ll_cas.ll_getStringValue(addr, casFeatCode_storyID);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setStoryID(int addr, String v) {
        if (featOkTst && casFeat_storyID == null)
      jcas.throwFeatMissing("storyID", "de.julielab.jules.types.muc7.MUC7Header");
    ll_cas.ll_setStringValue(addr, casFeatCode_storyID, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public MUC7Header_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_storyID = jcas.getRequiredFeatureDE(casType, "storyID", "uima.cas.String", featOkTst);
    casFeatCode_storyID  = (null == casFeat_storyID) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_storyID).getCode();

  }
}



    