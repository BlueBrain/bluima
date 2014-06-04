
/* First created by JCasGen Wed Oct 19 19:10:28 CEST 2011 */
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
 * Updated by JCasGen Wed Jun 04 18:01:57 CEST 2014
 * @generated */
public class Coordination_Type extends Annotation_Type {
  /** @generated */
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Coordination_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Coordination_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Coordination(addr, Coordination_Type.this);
  			   Coordination_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Coordination(addr, Coordination_Type.this);
  	  }
    };
  /** @generated */
  public final static int typeIndexID = Coordination.typeIndexID;
  /** @generated 
     @modifiable */
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.julielab.jules.types.Coordination");
 
  /** @generated */
  final Feature casFeat_resolved;
  /** @generated */
  final int     casFeatCode_resolved;
  /** @generated */ 
  public String getResolved(int addr) {
        if (featOkTst && casFeat_resolved == null)
      jcas.throwFeatMissing("resolved", "de.julielab.jules.types.Coordination");
    return ll_cas.ll_getStringValue(addr, casFeatCode_resolved);
  }
  /** @generated */    
  public void setResolved(int addr, String v) {
        if (featOkTst && casFeat_resolved == null)
      jcas.throwFeatMissing("resolved", "de.julielab.jules.types.Coordination");
    ll_cas.ll_setStringValue(addr, casFeatCode_resolved, v);}
    
  
 
  /** @generated */
  final Feature casFeat_elliptical;
  /** @generated */
  final int     casFeatCode_elliptical;
  /** @generated */ 
  public boolean getElliptical(int addr) {
        if (featOkTst && casFeat_elliptical == null)
      jcas.throwFeatMissing("elliptical", "de.julielab.jules.types.Coordination");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_elliptical);
  }
  /** @generated */    
  public void setElliptical(int addr, boolean v) {
        if (featOkTst && casFeat_elliptical == null)
      jcas.throwFeatMissing("elliptical", "de.julielab.jules.types.Coordination");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_elliptical, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public Coordination_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_resolved = jcas.getRequiredFeatureDE(casType, "resolved", "uima.cas.String", featOkTst);
    casFeatCode_resolved  = (null == casFeat_resolved) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_resolved).getCode();

 
    casFeat_elliptical = jcas.getRequiredFeatureDE(casType, "elliptical", "uima.cas.Boolean", featOkTst);
    casFeatCode_elliptical  = (null == casFeat_elliptical) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_elliptical).getCode();

  }
}



    