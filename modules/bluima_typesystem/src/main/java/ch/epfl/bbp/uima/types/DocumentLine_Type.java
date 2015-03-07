
/* First created by JCasGen Sat Mar 07 22:05:56 CET 2015 */
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
 * Updated by JCasGen Sat Mar 07 22:05:56 CET 2015
 * @generated */
public class DocumentLine_Type extends DocumentElement_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (DocumentLine_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = DocumentLine_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new DocumentLine(addr, DocumentLine_Type.this);
  			   DocumentLine_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new DocumentLine(addr, DocumentLine_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = DocumentLine.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("ch.epfl.bbp.uima.types.DocumentLine");
 
  /** @generated */
  final Feature casFeat_block;
  /** @generated */
  final int     casFeatCode_block;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getBlock(int addr) {
        if (featOkTst && casFeat_block == null)
      jcas.throwFeatMissing("block", "ch.epfl.bbp.uima.types.DocumentLine");
    return ll_cas.ll_getIntValue(addr, casFeatCode_block);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setBlock(int addr, int v) {
        if (featOkTst && casFeat_block == null)
      jcas.throwFeatMissing("block", "ch.epfl.bbp.uima.types.DocumentLine");
    ll_cas.ll_setIntValue(addr, casFeatCode_block, v);}
    
  
 
  /** @generated */
  final Feature casFeat_lineText;
  /** @generated */
  final int     casFeatCode_lineText;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getLineText(int addr) {
        if (featOkTst && casFeat_lineText == null)
      jcas.throwFeatMissing("lineText", "ch.epfl.bbp.uima.types.DocumentLine");
    return ll_cas.ll_getStringValue(addr, casFeatCode_lineText);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setLineText(int addr, String v) {
        if (featOkTst && casFeat_lineText == null)
      jcas.throwFeatMissing("lineText", "ch.epfl.bbp.uima.types.DocumentLine");
    ll_cas.ll_setStringValue(addr, casFeatCode_lineText, v);}
    
  
 
  /** @generated */
  final Feature casFeat_beginnings;
  /** @generated */
  final int     casFeatCode_beginnings;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getBeginnings(int addr) {
        if (featOkTst && casFeat_beginnings == null)
      jcas.throwFeatMissing("beginnings", "ch.epfl.bbp.uima.types.DocumentLine");
    return ll_cas.ll_getRefValue(addr, casFeatCode_beginnings);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setBeginnings(int addr, int v) {
        if (featOkTst && casFeat_beginnings == null)
      jcas.throwFeatMissing("beginnings", "ch.epfl.bbp.uima.types.DocumentLine");
    ll_cas.ll_setRefValue(addr, casFeatCode_beginnings, v);}
    
   /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @return value at index i in the array 
   */
  public float getBeginnings(int addr, int i) {
        if (featOkTst && casFeat_beginnings == null)
      jcas.throwFeatMissing("beginnings", "ch.epfl.bbp.uima.types.DocumentLine");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getFloatArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_beginnings), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_beginnings), i);
	return ll_cas.ll_getFloatArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_beginnings), i);
  }
   
  /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @param v value to set
   */ 
  public void setBeginnings(int addr, int i, float v) {
        if (featOkTst && casFeat_beginnings == null)
      jcas.throwFeatMissing("beginnings", "ch.epfl.bbp.uima.types.DocumentLine");
    if (lowLevelTypeChecks)
      ll_cas.ll_setFloatArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_beginnings), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_beginnings), i);
    ll_cas.ll_setFloatArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_beginnings), i, v);
  }
 
 
  /** @generated */
  final Feature casFeat_endings;
  /** @generated */
  final int     casFeatCode_endings;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getEndings(int addr) {
        if (featOkTst && casFeat_endings == null)
      jcas.throwFeatMissing("endings", "ch.epfl.bbp.uima.types.DocumentLine");
    return ll_cas.ll_getRefValue(addr, casFeatCode_endings);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setEndings(int addr, int v) {
        if (featOkTst && casFeat_endings == null)
      jcas.throwFeatMissing("endings", "ch.epfl.bbp.uima.types.DocumentLine");
    ll_cas.ll_setRefValue(addr, casFeatCode_endings, v);}
    
   /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @return value at index i in the array 
   */
  public float getEndings(int addr, int i) {
        if (featOkTst && casFeat_endings == null)
      jcas.throwFeatMissing("endings", "ch.epfl.bbp.uima.types.DocumentLine");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getFloatArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_endings), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_endings), i);
	return ll_cas.ll_getFloatArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_endings), i);
  }
   
  /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @param v value to set
   */ 
  public void setEndings(int addr, int i, float v) {
        if (featOkTst && casFeat_endings == null)
      jcas.throwFeatMissing("endings", "ch.epfl.bbp.uima.types.DocumentLine");
    if (lowLevelTypeChecks)
      ll_cas.ll_setFloatArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_endings), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_endings), i);
    ll_cas.ll_setFloatArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_endings), i, v);
  }
 



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public DocumentLine_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_block = jcas.getRequiredFeatureDE(casType, "block", "uima.cas.Integer", featOkTst);
    casFeatCode_block  = (null == casFeat_block) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_block).getCode();

 
    casFeat_lineText = jcas.getRequiredFeatureDE(casType, "lineText", "uima.cas.String", featOkTst);
    casFeatCode_lineText  = (null == casFeat_lineText) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_lineText).getCode();

 
    casFeat_beginnings = jcas.getRequiredFeatureDE(casType, "beginnings", "uima.cas.FloatArray", featOkTst);
    casFeatCode_beginnings  = (null == casFeat_beginnings) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_beginnings).getCode();

 
    casFeat_endings = jcas.getRequiredFeatureDE(casType, "endings", "uima.cas.FloatArray", featOkTst);
    casFeatCode_endings  = (null == casFeat_endings) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_endings).getCode();

  }
}



    