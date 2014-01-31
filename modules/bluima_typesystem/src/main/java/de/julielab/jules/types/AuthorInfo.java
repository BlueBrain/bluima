

/* First created by JCasGen Fri Dec 09 11:59:24 CET 2011 */
package de.julielab.jules.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** AuthorInfo Type annotates the text segments containing the information about an author and his/her affiliation information.
 * Updated by JCasGen Fri Dec 09 11:59:26 CET 2011
 * XML source: /Users/richarde/dev/bluebrain/svn_nlp/UIMA/blue_uima/trunk/julielab_typesystem-2.6.8/src/main/resources/typeSystem/julie-document-meta-types.xml
 * @generated */
public class AuthorInfo extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(AuthorInfo.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated  */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected AuthorInfo() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public AuthorInfo(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public AuthorInfo(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public AuthorInfo(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** <!-- begin-user-doc -->
    * Write your own initialization here
    * <!-- end-user-doc -->
  @generated modifiable */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: foreName

  /** getter for foreName - gets An authors forename
   * @generated */
  public String getForeName() {
    if (AuthorInfo_Type.featOkTst && ((AuthorInfo_Type)jcasType).casFeat_foreName == null)
      jcasType.jcas.throwFeatMissing("foreName", "de.julielab.jules.types.AuthorInfo");
    return jcasType.ll_cas.ll_getStringValue(addr, ((AuthorInfo_Type)jcasType).casFeatCode_foreName);}
    
  /** setter for foreName - sets An authors forename 
   * @generated */
  public void setForeName(String v) {
    if (AuthorInfo_Type.featOkTst && ((AuthorInfo_Type)jcasType).casFeat_foreName == null)
      jcasType.jcas.throwFeatMissing("foreName", "de.julielab.jules.types.AuthorInfo");
    jcasType.ll_cas.ll_setStringValue(addr, ((AuthorInfo_Type)jcasType).casFeatCode_foreName, v);}    
   
    
  //*--------------*
  //* Feature: affiliation

  /** getter for affiliation - gets Affiliation of the author
   * @generated */
  public String getAffiliation() {
    if (AuthorInfo_Type.featOkTst && ((AuthorInfo_Type)jcasType).casFeat_affiliation == null)
      jcasType.jcas.throwFeatMissing("affiliation", "de.julielab.jules.types.AuthorInfo");
    return jcasType.ll_cas.ll_getStringValue(addr, ((AuthorInfo_Type)jcasType).casFeatCode_affiliation);}
    
  /** setter for affiliation - sets Affiliation of the author 
   * @generated */
  public void setAffiliation(String v) {
    if (AuthorInfo_Type.featOkTst && ((AuthorInfo_Type)jcasType).casFeat_affiliation == null)
      jcasType.jcas.throwFeatMissing("affiliation", "de.julielab.jules.types.AuthorInfo");
    jcasType.ll_cas.ll_setStringValue(addr, ((AuthorInfo_Type)jcasType).casFeatCode_affiliation, v);}    
   
    
  //*--------------*
  //* Feature: contact

  /** getter for contact - gets Contact information (emails, phones, etc.), O
   * @generated */
  public String getContact() {
    if (AuthorInfo_Type.featOkTst && ((AuthorInfo_Type)jcasType).casFeat_contact == null)
      jcasType.jcas.throwFeatMissing("contact", "de.julielab.jules.types.AuthorInfo");
    return jcasType.ll_cas.ll_getStringValue(addr, ((AuthorInfo_Type)jcasType).casFeatCode_contact);}
    
  /** setter for contact - sets Contact information (emails, phones, etc.), O 
   * @generated */
  public void setContact(String v) {
    if (AuthorInfo_Type.featOkTst && ((AuthorInfo_Type)jcasType).casFeat_contact == null)
      jcasType.jcas.throwFeatMissing("contact", "de.julielab.jules.types.AuthorInfo");
    jcasType.ll_cas.ll_setStringValue(addr, ((AuthorInfo_Type)jcasType).casFeatCode_contact, v);}    
   
    
  //*--------------*
  //* Feature: lastName

  /** getter for lastName - gets The last name of the author.
   * @generated */
  public String getLastName() {
    if (AuthorInfo_Type.featOkTst && ((AuthorInfo_Type)jcasType).casFeat_lastName == null)
      jcasType.jcas.throwFeatMissing("lastName", "de.julielab.jules.types.AuthorInfo");
    return jcasType.ll_cas.ll_getStringValue(addr, ((AuthorInfo_Type)jcasType).casFeatCode_lastName);}
    
  /** setter for lastName - sets The last name of the author. 
   * @generated */
  public void setLastName(String v) {
    if (AuthorInfo_Type.featOkTst && ((AuthorInfo_Type)jcasType).casFeat_lastName == null)
      jcasType.jcas.throwFeatMissing("lastName", "de.julielab.jules.types.AuthorInfo");
    jcasType.ll_cas.ll_setStringValue(addr, ((AuthorInfo_Type)jcasType).casFeatCode_lastName, v);}    
   
    
  //*--------------*
  //* Feature: initials

  /** getter for initials - gets Initials
   * @generated */
  public String getInitials() {
    if (AuthorInfo_Type.featOkTst && ((AuthorInfo_Type)jcasType).casFeat_initials == null)
      jcasType.jcas.throwFeatMissing("initials", "de.julielab.jules.types.AuthorInfo");
    return jcasType.ll_cas.ll_getStringValue(addr, ((AuthorInfo_Type)jcasType).casFeatCode_initials);}
    
  /** setter for initials - sets Initials 
   * @generated */
  public void setInitials(String v) {
    if (AuthorInfo_Type.featOkTst && ((AuthorInfo_Type)jcasType).casFeat_initials == null)
      jcasType.jcas.throwFeatMissing("initials", "de.julielab.jules.types.AuthorInfo");
    jcasType.ll_cas.ll_setStringValue(addr, ((AuthorInfo_Type)jcasType).casFeatCode_initials, v);}    
  }

    