

/* First created by JCasGen Wed Oct 19 19:10:41 CEST 2011 */
package de.julielab.jules.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** 
 * Updated by JCasGen Fri Dec 09 11:59:26 CET 2011
 * XML source: /Users/richarde/dev/bluebrain/svn_nlp/UIMA/blue_uima/trunk/julielab_typesystem-2.6.8/src/main/resources/typeSystem/julie-document-meta-types.xml
 * @generated */
public class FullTextLink extends Annotation {
  /** @generated
   * @ordered 
   */
  public final static int typeIndexID = JCasRegistry.register(FullTextLink.class);
  /** @generated
   * @ordered 
   */
  public final static int type = typeIndexID;
  /** @generated  */
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected FullTextLink() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public FullTextLink(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public FullTextLink(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public FullTextLink(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** <!-- begin-user-doc -->
    * Write your own initialization here
    * <!-- end-user-doc -->
  @generated modifiable */
  private void readObject() {}
     
 
    
  //*--------------*
  //* Feature: url

  /** getter for url - gets 
   * @generated */
  public String getUrl() {
    if (FullTextLink_Type.featOkTst && ((FullTextLink_Type)jcasType).casFeat_url == null)
      jcasType.jcas.throwFeatMissing("url", "de.julielab.jules.types.FullTextLink");
    return jcasType.ll_cas.ll_getStringValue(addr, ((FullTextLink_Type)jcasType).casFeatCode_url);}
    
  /** setter for url - sets  
   * @generated */
  public void setUrl(String v) {
    if (FullTextLink_Type.featOkTst && ((FullTextLink_Type)jcasType).casFeat_url == null)
      jcasType.jcas.throwFeatMissing("url", "de.julielab.jules.types.FullTextLink");
    jcasType.ll_cas.ll_setStringValue(addr, ((FullTextLink_Type)jcasType).casFeatCode_url, v);}    
   
    
  //*--------------*
  //* Feature: iconUrl

  /** getter for iconUrl - gets 
   * @generated */
  public String getIconUrl() {
    if (FullTextLink_Type.featOkTst && ((FullTextLink_Type)jcasType).casFeat_iconUrl == null)
      jcasType.jcas.throwFeatMissing("iconUrl", "de.julielab.jules.types.FullTextLink");
    return jcasType.ll_cas.ll_getStringValue(addr, ((FullTextLink_Type)jcasType).casFeatCode_iconUrl);}
    
  /** setter for iconUrl - sets  
   * @generated */
  public void setIconUrl(String v) {
    if (FullTextLink_Type.featOkTst && ((FullTextLink_Type)jcasType).casFeat_iconUrl == null)
      jcasType.jcas.throwFeatMissing("iconUrl", "de.julielab.jules.types.FullTextLink");
    jcasType.ll_cas.ll_setStringValue(addr, ((FullTextLink_Type)jcasType).casFeatCode_iconUrl, v);}    
  }

    