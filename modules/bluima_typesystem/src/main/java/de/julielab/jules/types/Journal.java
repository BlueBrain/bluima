

/* First created by JCasGen Wed Oct 19 19:10:41 CEST 2011 */
package de.julielab.jules.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** This type contains attributes to describe a journal publication.
 * Updated by JCasGen Fri Dec 09 11:59:26 CET 2011
 * XML source: /Users/richarde/dev/bluebrain/svn_nlp/UIMA/blue_uima/trunk/julielab_typesystem-2.6.8/src/main/resources/typeSystem/julie-document-meta-types.xml
 * @generated */
public class Journal extends PubType {
  /** @generated
   * @ordered 
   */
  public final static int typeIndexID = JCasRegistry.register(Journal.class);
  /** @generated
   * @ordered 
   */
  public final static int type = typeIndexID;
  /** @generated  */
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected Journal() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public Journal(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public Journal(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public Journal(JCas jcas, int begin, int end) {
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
  //* Feature: ISSN

  /** getter for ISSN - gets The international standard serial number, O
   * @generated */
  public String getISSN() {
    if (Journal_Type.featOkTst && ((Journal_Type)jcasType).casFeat_ISSN == null)
      jcasType.jcas.throwFeatMissing("ISSN", "de.julielab.jules.types.Journal");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Journal_Type)jcasType).casFeatCode_ISSN);}
    
  /** setter for ISSN - sets The international standard serial number, O 
   * @generated */
  public void setISSN(String v) {
    if (Journal_Type.featOkTst && ((Journal_Type)jcasType).casFeat_ISSN == null)
      jcasType.jcas.throwFeatMissing("ISSN", "de.julielab.jules.types.Journal");
    jcasType.ll_cas.ll_setStringValue(addr, ((Journal_Type)jcasType).casFeatCode_ISSN, v);}    
   
    
  //*--------------*
  //* Feature: volume

  /** getter for volume - gets The volume number of the journal in which the article was published, O
   * @generated */
  public String getVolume() {
    if (Journal_Type.featOkTst && ((Journal_Type)jcasType).casFeat_volume == null)
      jcasType.jcas.throwFeatMissing("volume", "de.julielab.jules.types.Journal");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Journal_Type)jcasType).casFeatCode_volume);}
    
  /** setter for volume - sets The volume number of the journal in which the article was published, O 
   * @generated */
  public void setVolume(String v) {
    if (Journal_Type.featOkTst && ((Journal_Type)jcasType).casFeat_volume == null)
      jcasType.jcas.throwFeatMissing("volume", "de.julielab.jules.types.Journal");
    jcasType.ll_cas.ll_setStringValue(addr, ((Journal_Type)jcasType).casFeatCode_volume, v);}    
   
    
  //*--------------*
  //* Feature: title

  /** getter for title - gets Full journal title, C
   * @generated */
  public String getTitle() {
    if (Journal_Type.featOkTst && ((Journal_Type)jcasType).casFeat_title == null)
      jcasType.jcas.throwFeatMissing("title", "de.julielab.jules.types.Journal");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Journal_Type)jcasType).casFeatCode_title);}
    
  /** setter for title - sets Full journal title, C 
   * @generated */
  public void setTitle(String v) {
    if (Journal_Type.featOkTst && ((Journal_Type)jcasType).casFeat_title == null)
      jcasType.jcas.throwFeatMissing("title", "de.julielab.jules.types.Journal");
    jcasType.ll_cas.ll_setStringValue(addr, ((Journal_Type)jcasType).casFeatCode_title, v);}    
   
    
  //*--------------*
  //* Feature: impactFactor

  /** getter for impactFactor - gets The impact factor of the journal at the time of publication, O
   * @generated */
  public String getImpactFactor() {
    if (Journal_Type.featOkTst && ((Journal_Type)jcasType).casFeat_impactFactor == null)
      jcasType.jcas.throwFeatMissing("impactFactor", "de.julielab.jules.types.Journal");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Journal_Type)jcasType).casFeatCode_impactFactor);}
    
  /** setter for impactFactor - sets The impact factor of the journal at the time of publication, O 
   * @generated */
  public void setImpactFactor(String v) {
    if (Journal_Type.featOkTst && ((Journal_Type)jcasType).casFeat_impactFactor == null)
      jcasType.jcas.throwFeatMissing("impactFactor", "de.julielab.jules.types.Journal");
    jcasType.ll_cas.ll_setStringValue(addr, ((Journal_Type)jcasType).casFeatCode_impactFactor, v);}    
   
    
  //*--------------*
  //* Feature: shortTitle

  /** getter for shortTitle - gets the short title of the Journal (e.g. "Nicotine Tob Res" for "Nicotine & tobacco research : official journal of the Society for Research on Nicotine and Tobacco")
   * @generated */
  public String getShortTitle() {
    if (Journal_Type.featOkTst && ((Journal_Type)jcasType).casFeat_shortTitle == null)
      jcasType.jcas.throwFeatMissing("shortTitle", "de.julielab.jules.types.Journal");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Journal_Type)jcasType).casFeatCode_shortTitle);}
    
  /** setter for shortTitle - sets the short title of the Journal (e.g. "Nicotine Tob Res" for "Nicotine & tobacco research : official journal of the Society for Research on Nicotine and Tobacco") 
   * @generated */
  public void setShortTitle(String v) {
    if (Journal_Type.featOkTst && ((Journal_Type)jcasType).casFeat_shortTitle == null)
      jcasType.jcas.throwFeatMissing("shortTitle", "de.julielab.jules.types.Journal");
    jcasType.ll_cas.ll_setStringValue(addr, ((Journal_Type)jcasType).casFeatCode_shortTitle, v);}    
   
    
  //*--------------*
  //* Feature: issue

  /** getter for issue - gets Issue of Journal
   * @generated */
  public String getIssue() {
    if (Journal_Type.featOkTst && ((Journal_Type)jcasType).casFeat_issue == null)
      jcasType.jcas.throwFeatMissing("issue", "de.julielab.jules.types.Journal");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Journal_Type)jcasType).casFeatCode_issue);}
    
  /** setter for issue - sets Issue of Journal 
   * @generated */
  public void setIssue(String v) {
    if (Journal_Type.featOkTst && ((Journal_Type)jcasType).casFeat_issue == null)
      jcasType.jcas.throwFeatMissing("issue", "de.julielab.jules.types.Journal");
    jcasType.ll_cas.ll_setStringValue(addr, ((Journal_Type)jcasType).casFeatCode_issue, v);}    
   
    
  //*--------------*
  //* Feature: pages

  /** getter for pages - gets Pages of Journal
   * @generated */
  public String getPages() {
    if (Journal_Type.featOkTst && ((Journal_Type)jcasType).casFeat_pages == null)
      jcasType.jcas.throwFeatMissing("pages", "de.julielab.jules.types.Journal");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Journal_Type)jcasType).casFeatCode_pages);}
    
  /** setter for pages - sets Pages of Journal 
   * @generated */
  public void setPages(String v) {
    if (Journal_Type.featOkTst && ((Journal_Type)jcasType).casFeat_pages == null)
      jcasType.jcas.throwFeatMissing("pages", "de.julielab.jules.types.Journal");
    jcasType.ll_cas.ll_setStringValue(addr, ((Journal_Type)jcasType).casFeatCode_pages, v);}    
  }

    