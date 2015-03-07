
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
public class RelatedArticle_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (RelatedArticle_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = RelatedArticle_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new RelatedArticle(addr, RelatedArticle_Type.this);
  			   RelatedArticle_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new RelatedArticle(addr, RelatedArticle_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = RelatedArticle.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.julielab.jules.types.RelatedArticle");
 
  /** @generated */
  final Feature casFeat_relatedArticle;
  /** @generated */
  final int     casFeatCode_relatedArticle;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getRelatedArticle(int addr) {
        if (featOkTst && casFeat_relatedArticle == null)
      jcas.throwFeatMissing("relatedArticle", "de.julielab.jules.types.RelatedArticle");
    return ll_cas.ll_getStringValue(addr, casFeatCode_relatedArticle);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setRelatedArticle(int addr, String v) {
        if (featOkTst && casFeat_relatedArticle == null)
      jcas.throwFeatMissing("relatedArticle", "de.julielab.jules.types.RelatedArticle");
    ll_cas.ll_setStringValue(addr, casFeatCode_relatedArticle, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public RelatedArticle_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_relatedArticle = jcas.getRequiredFeatureDE(casType, "relatedArticle", "uima.cas.String", featOkTst);
    casFeatCode_relatedArticle  = (null == casFeat_relatedArticle) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_relatedArticle).getCode();

  }
}



    