
/* First created by JCasGen Wed May 29 20:18:14 CEST 2013 */
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
import org.apache.uima.jcas.tcas.Annotation_Type;

/** The gold annotation, coming from a corpus
 * Updated by JCasGen Fri Sep 20 01:02:03 CEST 2013
 * @generated */
public class Gold_Type extends Annotation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Gold_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Gold_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Gold(addr, Gold_Type.this);
  			   Gold_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Gold(addr, Gold_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Gold.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("ch.epfl.bbp.uima.types.Gold");
 
  /** @generated */
  final Feature casFeat_typeS;
  /** @generated */
  final int     casFeatCode_typeS;
  /** @generated */ 
  public String getTypeS(int addr) {
        if (featOkTst && casFeat_typeS == null)
      jcas.throwFeatMissing("typeS", "ch.epfl.bbp.uima.types.Gold");
    return ll_cas.ll_getStringValue(addr, casFeatCode_typeS);
  }
  /** @generated */    
  public void setTypeS(int addr, String v) {
        if (featOkTst && casFeat_typeS == null)
      jcas.throwFeatMissing("typeS", "ch.epfl.bbp.uima.types.Gold");
    ll_cas.ll_setStringValue(addr, casFeatCode_typeS, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public Gold_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_typeS = jcas.getRequiredFeatureDE(casType, "typeS", "uima.cas.String", featOkTst);
    casFeatCode_typeS  = (null == casFeat_typeS) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_typeS).getCode();

  }
}



    