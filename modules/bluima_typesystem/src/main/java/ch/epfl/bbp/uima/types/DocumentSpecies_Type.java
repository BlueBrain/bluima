
/* First created by JCasGen Mon Feb 17 12:27:27 CET 2014 */
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

/** the most probable species of a document
 * Updated by JCasGen Mon Feb 17 12:27:27 CET 2014
 * @generated */
public class DocumentSpecies_Type extends Annotation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (DocumentSpecies_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = DocumentSpecies_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new DocumentSpecies(addr, DocumentSpecies_Type.this);
  			   DocumentSpecies_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new DocumentSpecies(addr, DocumentSpecies_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = DocumentSpecies.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("ch.epfl.bbp.uima.types.DocumentSpecies");
 
  /** @generated */
  final Feature casFeat_familyName;
  /** @generated */
  final int     casFeatCode_familyName;
  /** @generated */ 
  public String getFamilyName(int addr) {
        if (featOkTst && casFeat_familyName == null)
      jcas.throwFeatMissing("familyName", "ch.epfl.bbp.uima.types.DocumentSpecies");
    return ll_cas.ll_getStringValue(addr, casFeatCode_familyName);
  }
  /** @generated */    
  public void setFamilyName(int addr, String v) {
        if (featOkTst && casFeat_familyName == null)
      jcas.throwFeatMissing("familyName", "ch.epfl.bbp.uima.types.DocumentSpecies");
    ll_cas.ll_setStringValue(addr, casFeatCode_familyName, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public DocumentSpecies_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_familyName = jcas.getRequiredFeatureDE(casType, "familyName", "uima.cas.String", featOkTst);
    casFeatCode_familyName  = (null == casFeat_familyName) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_familyName).getCode();

  }
}



    