
/* First created by JCasGen Mon Feb 17 12:26:53 CET 2014 */
package ch.epfl.bbp.uima.types;

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
 * Updated by JCasGen Mon Feb 17 22:12:56 CET 2014
 * @generated */
public class DocumentBlock_Type extends DocumentElement_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (DocumentBlock_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = DocumentBlock_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new DocumentBlock(addr, DocumentBlock_Type.this);
  			   DocumentBlock_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new DocumentBlock(addr, DocumentBlock_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = DocumentBlock.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("ch.epfl.bbp.uima.types.DocumentBlock");
 
  /** @generated */
  final Feature casFeat_hasBold;
  /** @generated */
  final int     casFeatCode_hasBold;
  /** @generated */ 
  public boolean getHasBold(int addr) {
        if (featOkTst && casFeat_hasBold == null)
      jcas.throwFeatMissing("hasBold", "ch.epfl.bbp.uima.types.DocumentBlock");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_hasBold);
  }
  /** @generated */    
  public void setHasBold(int addr, boolean v) {
        if (featOkTst && casFeat_hasBold == null)
      jcas.throwFeatMissing("hasBold", "ch.epfl.bbp.uima.types.DocumentBlock");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_hasBold, v);}
    
  
 
  /** @generated */
  final Feature casFeat_hasManyFontsizes;
  /** @generated */
  final int     casFeatCode_hasManyFontsizes;
  /** @generated */ 
  public boolean getHasManyFontsizes(int addr) {
        if (featOkTst && casFeat_hasManyFontsizes == null)
      jcas.throwFeatMissing("hasManyFontsizes", "ch.epfl.bbp.uima.types.DocumentBlock");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_hasManyFontsizes);
  }
  /** @generated */    
  public void setHasManyFontsizes(int addr, boolean v) {
        if (featOkTst && casFeat_hasManyFontsizes == null)
      jcas.throwFeatMissing("hasManyFontsizes", "ch.epfl.bbp.uima.types.DocumentBlock");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_hasManyFontsizes, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public DocumentBlock_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_hasBold = jcas.getRequiredFeatureDE(casType, "hasBold", "uima.cas.Boolean", featOkTst);
    casFeatCode_hasBold  = (null == casFeat_hasBold) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_hasBold).getCode();

 
    casFeat_hasManyFontsizes = jcas.getRequiredFeatureDE(casType, "hasManyFontsizes", "uima.cas.Boolean", featOkTst);
    casFeatCode_hasManyFontsizes  = (null == casFeat_hasManyFontsizes) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_hasManyFontsizes).getCode();

  }
}



    