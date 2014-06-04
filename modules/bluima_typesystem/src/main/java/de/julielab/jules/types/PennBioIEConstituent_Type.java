
/* First created by JCasGen Wed Oct 19 19:10:28 CEST 2011 */
package de.julielab.jules.types;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;

/** Mark A. Mandel. Integrated Annotation of Biomedical Text: Creating the PennBioIE Corpus. March 2006. Text Mining, Ontologies and Natural Language Processing in Biomedicine.
 * Updated by JCasGen Wed Jun 04 18:01:58 CEST 2014
 * @generated */
public class PennBioIEConstituent_Type extends PTBConstituent_Type {
  /** @generated */
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (PennBioIEConstituent_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = PennBioIEConstituent_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new PennBioIEConstituent(addr, PennBioIEConstituent_Type.this);
  			   PennBioIEConstituent_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new PennBioIEConstituent(addr, PennBioIEConstituent_Type.this);
  	  }
    };
  /** @generated */
  public final static int typeIndexID = PennBioIEConstituent.typeIndexID;
  /** @generated 
     @modifiable */
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.julielab.jules.types.PennBioIEConstituent");



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public PennBioIEConstituent_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

  }
}



    