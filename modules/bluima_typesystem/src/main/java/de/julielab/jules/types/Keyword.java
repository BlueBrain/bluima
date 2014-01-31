

/* First created by JCasGen Wed Oct 19 19:10:41 CEST 2011 */
package de.julielab.jules.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** A term of a controlled keyword list to describe the content of the publication.
 * Updated by JCasGen Fri Dec 09 11:59:26 CET 2011
 * XML source: /Users/richarde/dev/bluebrain/svn_nlp/UIMA/blue_uima/trunk/julielab_typesystem-2.6.8/src/main/resources/typeSystem/julie-document-meta-types.xml
 * @generated */
public class Keyword extends Annotation {
  /** @generated
   * @ordered 
   */
  public final static int typeIndexID = JCasRegistry.register(Keyword.class);
  /** @generated
   * @ordered 
   */
  public final static int type = typeIndexID;
  /** @generated  */
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected Keyword() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public Keyword(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public Keyword(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public Keyword(JCas jcas, int begin, int end) {
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

  /** getter for name - gets The name of the keyword, C
   * @generated */
  public String getName() {
    if (Keyword_Type.featOkTst && ((Keyword_Type)jcasType).casFeat_name == null)
      jcasType.jcas.throwFeatMissing("name", "de.julielab.jules.types.Keyword");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Keyword_Type)jcasType).casFeatCode_name);}
    
  /** setter for name - sets The name of the keyword, C 
   * @generated */
  public void setName(String v) {
    if (Keyword_Type.featOkTst && ((Keyword_Type)jcasType).casFeat_name == null)
      jcasType.jcas.throwFeatMissing("name", "de.julielab.jules.types.Keyword");
    jcasType.ll_cas.ll_setStringValue(addr, ((Keyword_Type)jcasType).casFeatCode_name, v);}    
   
    
  //*--------------*
  //* Feature: source

  /** getter for source - gets Tthe keyword source (terminology), O
   * @generated */
  public String getSource() {
    if (Keyword_Type.featOkTst && ((Keyword_Type)jcasType).casFeat_source == null)
      jcasType.jcas.throwFeatMissing("source", "de.julielab.jules.types.Keyword");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Keyword_Type)jcasType).casFeatCode_source);}
    
  /** setter for source - sets Tthe keyword source (terminology), O 
   * @generated */
  public void setSource(String v) {
    if (Keyword_Type.featOkTst && ((Keyword_Type)jcasType).casFeat_source == null)
      jcasType.jcas.throwFeatMissing("source", "de.julielab.jules.types.Keyword");
    jcasType.ll_cas.ll_setStringValue(addr, ((Keyword_Type)jcasType).casFeatCode_source, v);}    
  }

    