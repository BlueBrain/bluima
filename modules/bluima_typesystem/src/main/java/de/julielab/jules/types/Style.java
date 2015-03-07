

/* First created by JCasGen Sat Mar 07 22:05:57 CET 2015 */
package de.julielab.jules.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** Text-markup information (italic, bold etc.) on any (e.g. character) level. Allows to keep the original style markup of the text, several style types might be set to same (or overlapping) range, when different styles are set to the same text region.
 * Updated by JCasGen Sat Mar 07 22:05:57 CET 2015
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/target/jcasgen/typesystem.xml
 * @generated */
public class Style extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Style.class);
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
  protected Style() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Style(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Style(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Style(JCas jcas, int begin, int end) {
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
  //* Feature: styleName

  /** getter for styleName - gets the name of the style used.
   * @generated
   * @return value of the feature 
   */
  public String getStyleName() {
    if (Style_Type.featOkTst && ((Style_Type)jcasType).casFeat_styleName == null)
      jcasType.jcas.throwFeatMissing("styleName", "de.julielab.jules.types.Style");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Style_Type)jcasType).casFeatCode_styleName);}
    
  /** setter for styleName - sets the name of the style used. 
   * @generated
   * @param v value to set into the feature 
   */
  public void setStyleName(String v) {
    if (Style_Type.featOkTst && ((Style_Type)jcasType).casFeat_styleName == null)
      jcasType.jcas.throwFeatMissing("styleName", "de.julielab.jules.types.Style");
    jcasType.ll_cas.ll_setStringValue(addr, ((Style_Type)jcasType).casFeatCode_styleName, v);}    
   
    
  //*--------------*
  //* Feature: encoding

  /** getter for encoding - gets the encoding used.
   * @generated
   * @return value of the feature 
   */
  public String getEncoding() {
    if (Style_Type.featOkTst && ((Style_Type)jcasType).casFeat_encoding == null)
      jcasType.jcas.throwFeatMissing("encoding", "de.julielab.jules.types.Style");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Style_Type)jcasType).casFeatCode_encoding);}
    
  /** setter for encoding - sets the encoding used. 
   * @generated
   * @param v value to set into the feature 
   */
  public void setEncoding(String v) {
    if (Style_Type.featOkTst && ((Style_Type)jcasType).casFeat_encoding == null)
      jcasType.jcas.throwFeatMissing("encoding", "de.julielab.jules.types.Style");
    jcasType.ll_cas.ll_setStringValue(addr, ((Style_Type)jcasType).casFeatCode_encoding, v);}    
  }

    