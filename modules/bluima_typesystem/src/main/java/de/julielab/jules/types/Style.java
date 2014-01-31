

/* First created by JCasGen Wed Oct 19 19:11:10 CEST 2011 */
package de.julielab.jules.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** Text-markup information (italic, bold etc.) on any (e.g. character) level. Allows to keep the original style markup of the text, several style types might be set to same (or overlapping) range, when different styles are set to the same text region.
 * Updated by JCasGen Fri Oct 21 11:02:43 CEST 2011
 * XML source: /Users/ren/dev/bluebrain/svn_nlp/UIMA/julielab/trunk/julielab_typesystem-2.6.8/src/main/resources/typeSystem/julie-document-structure-types.xml
 * @generated */
public class Style extends Annotation {
  /** @generated
   * @ordered 
   */
  public final static int typeIndexID = JCasRegistry.register(Style.class);
  /** @generated
   * @ordered 
   */
  public final static int type = typeIndexID;
  /** @generated  */
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected Style() {}
    
  /** Internal - constructor used by generator 
   * @generated */
  public Style(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public Style(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public Style(JCas jcas, int begin, int end) {
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
  //* Feature: styleName

  /** getter for styleName - gets the name of the style used.
   * @generated */
  public String getStyleName() {
    if (Style_Type.featOkTst && ((Style_Type)jcasType).casFeat_styleName == null)
      jcasType.jcas.throwFeatMissing("styleName", "de.julielab.jules.types.Style");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Style_Type)jcasType).casFeatCode_styleName);}
    
  /** setter for styleName - sets the name of the style used. 
   * @generated */
  public void setStyleName(String v) {
    if (Style_Type.featOkTst && ((Style_Type)jcasType).casFeat_styleName == null)
      jcasType.jcas.throwFeatMissing("styleName", "de.julielab.jules.types.Style");
    jcasType.ll_cas.ll_setStringValue(addr, ((Style_Type)jcasType).casFeatCode_styleName, v);}    
   
    
  //*--------------*
  //* Feature: encoding

  /** getter for encoding - gets the encoding used.
   * @generated */
  public String getEncoding() {
    if (Style_Type.featOkTst && ((Style_Type)jcasType).casFeat_encoding == null)
      jcasType.jcas.throwFeatMissing("encoding", "de.julielab.jules.types.Style");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Style_Type)jcasType).casFeatCode_encoding);}
    
  /** setter for encoding - sets the encoding used. 
   * @generated */
  public void setEncoding(String v) {
    if (Style_Type.featOkTst && ((Style_Type)jcasType).casFeat_encoding == null)
      jcasType.jcas.throwFeatMissing("encoding", "de.julielab.jules.types.Style");
    jcasType.ll_cas.ll_setStringValue(addr, ((Style_Type)jcasType).casFeatCode_encoding, v);}    
  }

    