
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
import de.julielab.jules.types.Annotation_Type;

/** A literature citation found in the body of the text, not the reference section
 * Updated by JCasGen Mon Feb 17 22:12:56 CET 2014
 * @generated */
public class BodyCitation_Type extends Annotation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (BodyCitation_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = BodyCitation_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new BodyCitation(addr, BodyCitation_Type.this);
  			   BodyCitation_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new BodyCitation(addr, BodyCitation_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = BodyCitation.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("ch.epfl.bbp.uima.types.BodyCitation");
 
  /** @generated */
  final Feature casFeat_textValue;
  /** @generated */
  final int     casFeatCode_textValue;
  /** @generated */ 
  public String getTextValue(int addr) {
        if (featOkTst && casFeat_textValue == null)
      jcas.throwFeatMissing("textValue", "ch.epfl.bbp.uima.types.BodyCitation");
    return ll_cas.ll_getStringValue(addr, casFeatCode_textValue);
  }
  /** @generated */    
  public void setTextValue(int addr, String v) {
        if (featOkTst && casFeat_textValue == null)
      jcas.throwFeatMissing("textValue", "ch.epfl.bbp.uima.types.BodyCitation");
    ll_cas.ll_setStringValue(addr, casFeatCode_textValue, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public BodyCitation_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_textValue = jcas.getRequiredFeatureDE(casType, "textValue", "uima.cas.String", featOkTst);
    casFeatCode_textValue  = (null == casFeat_textValue) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_textValue).getCode();

  }
}



    