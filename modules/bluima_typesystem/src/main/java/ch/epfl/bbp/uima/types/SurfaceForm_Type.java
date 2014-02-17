
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
import org.apache.uima.jcas.tcas.Annotation_Type;

/** Surface form from BioLexicon's FORMS table
 * Updated by JCasGen Mon Feb 17 22:12:56 CET 2014
 * @generated */
public class SurfaceForm_Type extends Annotation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (SurfaceForm_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = SurfaceForm_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new SurfaceForm(addr, SurfaceForm_Type.this);
  			   SurfaceForm_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new SurfaceForm(addr, SurfaceForm_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = SurfaceForm.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("ch.epfl.bbp.uima.types.SurfaceForm");
 
  /** @generated */
  final Feature casFeat_id;
  /** @generated */
  final int     casFeatCode_id;
  /** @generated */ 
  public String getId(int addr) {
        if (featOkTst && casFeat_id == null)
      jcas.throwFeatMissing("id", "ch.epfl.bbp.uima.types.SurfaceForm");
    return ll_cas.ll_getStringValue(addr, casFeatCode_id);
  }
  /** @generated */    
  public void setId(int addr, String v) {
        if (featOkTst && casFeat_id == null)
      jcas.throwFeatMissing("id", "ch.epfl.bbp.uima.types.SurfaceForm");
    ll_cas.ll_setStringValue(addr, casFeatCode_id, v);}
    
  
 
  /** @generated */
  final Feature casFeat_pos;
  /** @generated */
  final int     casFeatCode_pos;
  /** @generated */ 
  public String getPos(int addr) {
        if (featOkTst && casFeat_pos == null)
      jcas.throwFeatMissing("pos", "ch.epfl.bbp.uima.types.SurfaceForm");
    return ll_cas.ll_getStringValue(addr, casFeatCode_pos);
  }
  /** @generated */    
  public void setPos(int addr, String v) {
        if (featOkTst && casFeat_pos == null)
      jcas.throwFeatMissing("pos", "ch.epfl.bbp.uima.types.SurfaceForm");
    ll_cas.ll_setStringValue(addr, casFeatCode_pos, v);}
    
  
 
  /** @generated */
  final Feature casFeat_semtype;
  /** @generated */
  final int     casFeatCode_semtype;
  /** @generated */ 
  public String getSemtype(int addr) {
        if (featOkTst && casFeat_semtype == null)
      jcas.throwFeatMissing("semtype", "ch.epfl.bbp.uima.types.SurfaceForm");
    return ll_cas.ll_getStringValue(addr, casFeatCode_semtype);
  }
  /** @generated */    
  public void setSemtype(int addr, String v) {
        if (featOkTst && casFeat_semtype == null)
      jcas.throwFeatMissing("semtype", "ch.epfl.bbp.uima.types.SurfaceForm");
    ll_cas.ll_setStringValue(addr, casFeatCode_semtype, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public SurfaceForm_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_id = jcas.getRequiredFeatureDE(casType, "id", "uima.cas.String", featOkTst);
    casFeatCode_id  = (null == casFeat_id) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_id).getCode();

 
    casFeat_pos = jcas.getRequiredFeatureDE(casType, "pos", "uima.cas.String", featOkTst);
    casFeatCode_pos  = (null == casFeat_pos) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_pos).getCode();

 
    casFeat_semtype = jcas.getRequiredFeatureDE(casType, "semtype", "uima.cas.String", featOkTst);
    casFeatCode_semtype  = (null == casFeat_semtype) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_semtype).getCode();

  }
}



    