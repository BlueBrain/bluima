
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
import org.apache.uima.jcas.tcas.Annotation_Type;

/** Represents a page in a document, e.g. a page in a PDF article
 * Updated by JCasGen Sat Mar 07 22:05:56 CET 2015
 * @generated */
public class DocumentPage_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (DocumentPage_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = DocumentPage_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new DocumentPage(addr, DocumentPage_Type.this);
  			   DocumentPage_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new DocumentPage(addr, DocumentPage_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = DocumentPage.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("ch.epfl.bbp.uima.types.DocumentPage");
 
  /** @generated */
  final Feature casFeat_pageId;
  /** @generated */
  final int     casFeatCode_pageId;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getPageId(int addr) {
        if (featOkTst && casFeat_pageId == null)
      jcas.throwFeatMissing("pageId", "ch.epfl.bbp.uima.types.DocumentPage");
    return ll_cas.ll_getIntValue(addr, casFeatCode_pageId);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setPageId(int addr, int v) {
        if (featOkTst && casFeat_pageId == null)
      jcas.throwFeatMissing("pageId", "ch.epfl.bbp.uima.types.DocumentPage");
    ll_cas.ll_setIntValue(addr, casFeatCode_pageId, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public DocumentPage_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_pageId = jcas.getRequiredFeatureDE(casType, "pageId", "uima.cas.Integer", featOkTst);
    casFeatCode_pageId  = (null == casFeat_pageId) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_pageId).getCode();

  }
}



    