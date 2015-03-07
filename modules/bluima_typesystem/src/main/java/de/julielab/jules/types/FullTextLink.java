

/* First created by JCasGen Sat Mar 07 22:05:57 CET 2015 */
package de.julielab.jules.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** 
 * Updated by JCasGen Sat Mar 07 22:05:57 CET 2015
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/target/jcasgen/typesystem.xml
 * @generated */
public class FullTextLink extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(FullTextLink.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated
   * @return index of the type  
   */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected FullTextLink() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public FullTextLink(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public FullTextLink(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public FullTextLink(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** 
   * <!-- begin-user-doc -->
   * Write your own initialization here
   * <!-- end-user-doc -->
   *
   * @generated modifiable 
   */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: url

  /** getter for url - gets 
   * @generated
   * @return value of the feature 
   */
  public String getUrl() {
    if (FullTextLink_Type.featOkTst && ((FullTextLink_Type)jcasType).casFeat_url == null)
      jcasType.jcas.throwFeatMissing("url", "de.julielab.jules.types.FullTextLink");
    return jcasType.ll_cas.ll_getStringValue(addr, ((FullTextLink_Type)jcasType).casFeatCode_url);}
    
  /** setter for url - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setUrl(String v) {
    if (FullTextLink_Type.featOkTst && ((FullTextLink_Type)jcasType).casFeat_url == null)
      jcasType.jcas.throwFeatMissing("url", "de.julielab.jules.types.FullTextLink");
    jcasType.ll_cas.ll_setStringValue(addr, ((FullTextLink_Type)jcasType).casFeatCode_url, v);}    
   
    
  //*--------------*
  //* Feature: iconUrl

  /** getter for iconUrl - gets 
   * @generated
   * @return value of the feature 
   */
  public String getIconUrl() {
    if (FullTextLink_Type.featOkTst && ((FullTextLink_Type)jcasType).casFeat_iconUrl == null)
      jcasType.jcas.throwFeatMissing("iconUrl", "de.julielab.jules.types.FullTextLink");
    return jcasType.ll_cas.ll_getStringValue(addr, ((FullTextLink_Type)jcasType).casFeatCode_iconUrl);}
    
  /** setter for iconUrl - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setIconUrl(String v) {
    if (FullTextLink_Type.featOkTst && ((FullTextLink_Type)jcasType).casFeat_iconUrl == null)
      jcasType.jcas.throwFeatMissing("iconUrl", "de.julielab.jules.types.FullTextLink");
    jcasType.ll_cas.ll_setStringValue(addr, ((FullTextLink_Type)jcasType).casFeatCode_iconUrl, v);}    
  }

    