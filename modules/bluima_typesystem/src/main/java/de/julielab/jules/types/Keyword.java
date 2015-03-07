

/* First created by JCasGen Sat Mar 07 22:05:57 CET 2015 */
package de.julielab.jules.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** A term of a controlled keyword list to describe the content of the publication.
 * Updated by JCasGen Sat Mar 07 22:05:57 CET 2015
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/target/jcasgen/typesystem.xml
 * @generated */
public class Keyword extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Keyword.class);
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
  protected Keyword() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Keyword(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Keyword(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Keyword(JCas jcas, int begin, int end) {
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
  //* Feature: name

  /** getter for name - gets The name of the keyword, C
   * @generated
   * @return value of the feature 
   */
  public String getName() {
    if (Keyword_Type.featOkTst && ((Keyword_Type)jcasType).casFeat_name == null)
      jcasType.jcas.throwFeatMissing("name", "de.julielab.jules.types.Keyword");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Keyword_Type)jcasType).casFeatCode_name);}
    
  /** setter for name - sets The name of the keyword, C 
   * @generated
   * @param v value to set into the feature 
   */
  public void setName(String v) {
    if (Keyword_Type.featOkTst && ((Keyword_Type)jcasType).casFeat_name == null)
      jcasType.jcas.throwFeatMissing("name", "de.julielab.jules.types.Keyword");
    jcasType.ll_cas.ll_setStringValue(addr, ((Keyword_Type)jcasType).casFeatCode_name, v);}    
   
    
  //*--------------*
  //* Feature: source

  /** getter for source - gets Tthe keyword source (terminology), O
   * @generated
   * @return value of the feature 
   */
  public String getSource() {
    if (Keyword_Type.featOkTst && ((Keyword_Type)jcasType).casFeat_source == null)
      jcasType.jcas.throwFeatMissing("source", "de.julielab.jules.types.Keyword");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Keyword_Type)jcasType).casFeatCode_source);}
    
  /** setter for source - sets Tthe keyword source (terminology), O 
   * @generated
   * @param v value to set into the feature 
   */
  public void setSource(String v) {
    if (Keyword_Type.featOkTst && ((Keyword_Type)jcasType).casFeat_source == null)
      jcasType.jcas.throwFeatMissing("source", "de.julielab.jules.types.Keyword");
    jcasType.ll_cas.ll_setStringValue(addr, ((Keyword_Type)jcasType).casFeatCode_source, v);}    
  }

    