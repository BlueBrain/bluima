
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
public class ArgumentMention_Type extends Annotation_Type {
  /** @generated */
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (ArgumentMention_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = ArgumentMention_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new ArgumentMention(addr, ArgumentMention_Type.this);
  			   ArgumentMention_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new ArgumentMention(addr, ArgumentMention_Type.this);
  	  }
    };
  /** @generated */
  public final static int typeIndexID = ArgumentMention.typeIndexID;
  /** @generated 
     @modifiable */
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.julielab.jules.types.ArgumentMention");
 
  /** @generated */
  final Feature casFeat_ref;
  /** @generated */
  final int     casFeatCode_ref;
  /** @generated */ 
  public int getRef(int addr) {
        if (featOkTst && casFeat_ref == null)
      jcas.throwFeatMissing("ref", "de.julielab.jules.types.ArgumentMention");
    return ll_cas.ll_getRefValue(addr, casFeatCode_ref);
  }
  /** @generated */    
  public void setRef(int addr, int v) {
        if (featOkTst && casFeat_ref == null)
      jcas.throwFeatMissing("ref", "de.julielab.jules.types.ArgumentMention");
    ll_cas.ll_setRefValue(addr, casFeatCode_ref, v);}
    
  
 
  /** @generated */
  final Feature casFeat_role;
  /** @generated */
  final int     casFeatCode_role;
  /** @generated */ 
  public String getRole(int addr) {
        if (featOkTst && casFeat_role == null)
      jcas.throwFeatMissing("role", "de.julielab.jules.types.ArgumentMention");
    return ll_cas.ll_getStringValue(addr, casFeatCode_role);
  }
  /** @generated */    
  public void setRole(int addr, String v) {
        if (featOkTst && casFeat_role == null)
      jcas.throwFeatMissing("role", "de.julielab.jules.types.ArgumentMention");
    ll_cas.ll_setStringValue(addr, casFeatCode_role, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public ArgumentMention_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_ref = jcas.getRequiredFeatureDE(casType, "ref", "de.julielab.jules.types.Annotation", featOkTst);
    casFeatCode_ref  = (null == casFeat_ref) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_ref).getCode();

 
    casFeat_role = jcas.getRequiredFeatureDE(casType, "role", "uima.cas.String", featOkTst);
    casFeatCode_role  = (null == casFeat_role) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_role).getCode();

  }
}



    