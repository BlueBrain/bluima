
/* First created by JCasGen Sat Mar 07 22:05:57 CET 2015 */
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

/** AuthorInfo Type annotates the text segments containing the information about an author and his/her affiliation information.
 * Updated by JCasGen Sat Mar 07 22:05:57 CET 2015
 * @generated */
public class AuthorInfo_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (AuthorInfo_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = AuthorInfo_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new AuthorInfo(addr, AuthorInfo_Type.this);
  			   AuthorInfo_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new AuthorInfo(addr, AuthorInfo_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = AuthorInfo.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.julielab.jules.types.AuthorInfo");
 
  /** @generated */
  final Feature casFeat_foreName;
  /** @generated */
  final int     casFeatCode_foreName;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getForeName(int addr) {
        if (featOkTst && casFeat_foreName == null)
      jcas.throwFeatMissing("foreName", "de.julielab.jules.types.AuthorInfo");
    return ll_cas.ll_getStringValue(addr, casFeatCode_foreName);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setForeName(int addr, String v) {
        if (featOkTst && casFeat_foreName == null)
      jcas.throwFeatMissing("foreName", "de.julielab.jules.types.AuthorInfo");
    ll_cas.ll_setStringValue(addr, casFeatCode_foreName, v);}
    
  
 
  /** @generated */
  final Feature casFeat_affiliation;
  /** @generated */
  final int     casFeatCode_affiliation;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getAffiliation(int addr) {
        if (featOkTst && casFeat_affiliation == null)
      jcas.throwFeatMissing("affiliation", "de.julielab.jules.types.AuthorInfo");
    return ll_cas.ll_getStringValue(addr, casFeatCode_affiliation);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setAffiliation(int addr, String v) {
        if (featOkTst && casFeat_affiliation == null)
      jcas.throwFeatMissing("affiliation", "de.julielab.jules.types.AuthorInfo");
    ll_cas.ll_setStringValue(addr, casFeatCode_affiliation, v);}
    
  
 
  /** @generated */
  final Feature casFeat_contact;
  /** @generated */
  final int     casFeatCode_contact;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getContact(int addr) {
        if (featOkTst && casFeat_contact == null)
      jcas.throwFeatMissing("contact", "de.julielab.jules.types.AuthorInfo");
    return ll_cas.ll_getStringValue(addr, casFeatCode_contact);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setContact(int addr, String v) {
        if (featOkTst && casFeat_contact == null)
      jcas.throwFeatMissing("contact", "de.julielab.jules.types.AuthorInfo");
    ll_cas.ll_setStringValue(addr, casFeatCode_contact, v);}
    
  
 
  /** @generated */
  final Feature casFeat_lastName;
  /** @generated */
  final int     casFeatCode_lastName;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getLastName(int addr) {
        if (featOkTst && casFeat_lastName == null)
      jcas.throwFeatMissing("lastName", "de.julielab.jules.types.AuthorInfo");
    return ll_cas.ll_getStringValue(addr, casFeatCode_lastName);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setLastName(int addr, String v) {
        if (featOkTst && casFeat_lastName == null)
      jcas.throwFeatMissing("lastName", "de.julielab.jules.types.AuthorInfo");
    ll_cas.ll_setStringValue(addr, casFeatCode_lastName, v);}
    
  
 
  /** @generated */
  final Feature casFeat_initials;
  /** @generated */
  final int     casFeatCode_initials;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getInitials(int addr) {
        if (featOkTst && casFeat_initials == null)
      jcas.throwFeatMissing("initials", "de.julielab.jules.types.AuthorInfo");
    return ll_cas.ll_getStringValue(addr, casFeatCode_initials);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setInitials(int addr, String v) {
        if (featOkTst && casFeat_initials == null)
      jcas.throwFeatMissing("initials", "de.julielab.jules.types.AuthorInfo");
    ll_cas.ll_setStringValue(addr, casFeatCode_initials, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public AuthorInfo_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_foreName = jcas.getRequiredFeatureDE(casType, "foreName", "uima.cas.String", featOkTst);
    casFeatCode_foreName  = (null == casFeat_foreName) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_foreName).getCode();

 
    casFeat_affiliation = jcas.getRequiredFeatureDE(casType, "affiliation", "uima.cas.String", featOkTst);
    casFeatCode_affiliation  = (null == casFeat_affiliation) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_affiliation).getCode();

 
    casFeat_contact = jcas.getRequiredFeatureDE(casType, "contact", "uima.cas.String", featOkTst);
    casFeatCode_contact  = (null == casFeat_contact) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_contact).getCode();

 
    casFeat_lastName = jcas.getRequiredFeatureDE(casType, "lastName", "uima.cas.String", featOkTst);
    casFeatCode_lastName  = (null == casFeat_lastName) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_lastName).getCode();

 
    casFeat_initials = jcas.getRequiredFeatureDE(casType, "initials", "uima.cas.String", featOkTst);
    casFeatCode_initials  = (null == casFeat_initials) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_initials).getCode();

  }
}



    