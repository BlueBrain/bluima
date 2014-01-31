

/* First created by JCasGen Wed Oct 19 19:10:41 CEST 2011 */
package de.julielab.jules.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** An abstract type which should be used to store information on the publication. See subtypes Journal and an accumulative type (OtherPub)
 * Updated by JCasGen Fri Dec 09 11:59:26 CET 2011
 * XML source: /Users/richarde/dev/bluebrain/svn_nlp/UIMA/blue_uima/trunk/julielab_typesystem-2.6.8/src/main/resources/typeSystem/julie-document-meta-types.xml
 * @generated */
public class PubType extends Annotation {
  /** @generated
   * @ordered 
   */
  public final static int typeIndexID = JCasRegistry.register(PubType.class);
  /** @generated
   * @ordered 
   */
  public final static int type = typeIndexID;
  /** @generated  */
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected PubType() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public PubType(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public PubType(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public PubType(JCas jcas, int begin, int end) {
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
  //* Feature: name

  /** getter for name - gets The name of the publication type (e.g. journal, technical report, book), C
   * @generated */
  public String getName() {
    if (PubType_Type.featOkTst && ((PubType_Type)jcasType).casFeat_name == null)
      jcasType.jcas.throwFeatMissing("name", "de.julielab.jules.types.PubType");
    return jcasType.ll_cas.ll_getStringValue(addr, ((PubType_Type)jcasType).casFeatCode_name);}
    
  /** setter for name - sets The name of the publication type (e.g. journal, technical report, book), C 
   * @generated */
  public void setName(String v) {
    if (PubType_Type.featOkTst && ((PubType_Type)jcasType).casFeat_name == null)
      jcasType.jcas.throwFeatMissing("name", "de.julielab.jules.types.PubType");
    jcasType.ll_cas.ll_setStringValue(addr, ((PubType_Type)jcasType).casFeatCode_name, v);}    
   
    
  //*--------------*
  //* Feature: pubDate

  /** getter for pubDate - gets The date on which the document was published, O
   * @generated */
  public Date getPubDate() {
    if (PubType_Type.featOkTst && ((PubType_Type)jcasType).casFeat_pubDate == null)
      jcasType.jcas.throwFeatMissing("pubDate", "de.julielab.jules.types.PubType");
    return (Date)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((PubType_Type)jcasType).casFeatCode_pubDate)));}
    
  /** setter for pubDate - sets The date on which the document was published, O 
   * @generated */
  public void setPubDate(Date v) {
    if (PubType_Type.featOkTst && ((PubType_Type)jcasType).casFeat_pubDate == null)
      jcasType.jcas.throwFeatMissing("pubDate", "de.julielab.jules.types.PubType");
    jcasType.ll_cas.ll_setRefValue(addr, ((PubType_Type)jcasType).casFeatCode_pubDate, jcasType.ll_cas.ll_getFSRef(v));}    
  }

    