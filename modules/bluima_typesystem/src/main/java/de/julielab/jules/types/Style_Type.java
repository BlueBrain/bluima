
/* First created by JCasGen Wed Oct 19 19:11:10 CEST 2011 */
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

/** Text-markup information (italic, bold etc.) on any (e.g. character) level. Allows to keep the original style markup of the text, several style types might be set to same (or overlapping) range, when different styles are set to the same text region.
 * Updated by JCasGen Fri Oct 21 11:02:43 CEST 2011
 * @generated */
public class Style_Type extends Annotation_Type {
  /** @generated */
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Style_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Style_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Style(addr, Style_Type.this);
  			   Style_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Style(addr, Style_Type.this);
  	  }
    };
  /** @generated */
  public final static int typeIndexID = Style.typeIndexID;
  /** @generated 
     @modifiable */
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.julielab.jules.types.Style");
 
  /** @generated */
  final Feature casFeat_styleName;
  /** @generated */
  final int     casFeatCode_styleName;
  /** @generated */ 
  public String getStyleName(int addr) {
        if (featOkTst && casFeat_styleName == null)
      jcas.throwFeatMissing("styleName", "de.julielab.jules.types.Style");
    return ll_cas.ll_getStringValue(addr, casFeatCode_styleName);
  }
  /** @generated */    
  public void setStyleName(int addr, String v) {
        if (featOkTst && casFeat_styleName == null)
      jcas.throwFeatMissing("styleName", "de.julielab.jules.types.Style");
    ll_cas.ll_setStringValue(addr, casFeatCode_styleName, v);}
    
  
 
  /** @generated */
  final Feature casFeat_encoding;
  /** @generated */
  final int     casFeatCode_encoding;
  /** @generated */ 
  public String getEncoding(int addr) {
        if (featOkTst && casFeat_encoding == null)
      jcas.throwFeatMissing("encoding", "de.julielab.jules.types.Style");
    return ll_cas.ll_getStringValue(addr, casFeatCode_encoding);
  }
  /** @generated */    
  public void setEncoding(int addr, String v) {
        if (featOkTst && casFeat_encoding == null)
      jcas.throwFeatMissing("encoding", "de.julielab.jules.types.Style");
    ll_cas.ll_setStringValue(addr, casFeatCode_encoding, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public Style_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_styleName = jcas.getRequiredFeatureDE(casType, "styleName", "de.julielab.jules.types.StyleName", featOkTst);
    casFeatCode_styleName  = (null == casFeat_styleName) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_styleName).getCode();

 
    casFeat_encoding = jcas.getRequiredFeatureDE(casType, "encoding", "de.julielab.jules.types.Encoding", featOkTst);
    casFeatCode_encoding  = (null == casFeat_encoding) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_encoding).getCode();

  }
}



    