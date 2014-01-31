

/* First created by JCasGen Sat Dec 08 23:30:55 CET 2012 */
package ch.epfl.bbp.uima.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import de.julielab.jules.types.Annotation;


/** A literature citation found in the body of the text, not the reference section
 * Updated by JCasGen Fri Sep 20 01:02:03 CEST 2013
 * XML source: /Users/richarde/dev/bluebrain/svn_nlp/UIMA/blue_uima/trunk/modules/julielab_typesystem-2.6.8/src/main/resources/typeSystem/bbp-basic-types.xml
 * @generated */
public class BodyCitation extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(BodyCitation.class);
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
  protected BodyCitation() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public BodyCitation(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public BodyCitation(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public BodyCitation(JCas jcas, int begin, int end) {
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
  //* Feature: textValue

  /** getter for textValue - gets 
   * @generated */
  public String getTextValue() {
    if (BodyCitation_Type.featOkTst && ((BodyCitation_Type)jcasType).casFeat_textValue == null)
      jcasType.jcas.throwFeatMissing("textValue", "ch.epfl.bbp.uima.types.BodyCitation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((BodyCitation_Type)jcasType).casFeatCode_textValue);}
    
  /** setter for textValue - sets  
   * @generated */
  public void setTextValue(String v) {
    if (BodyCitation_Type.featOkTst && ((BodyCitation_Type)jcasType).casFeat_textValue == null)
      jcasType.jcas.throwFeatMissing("textValue", "ch.epfl.bbp.uima.types.BodyCitation");
    jcasType.ll_cas.ll_setStringValue(addr, ((BodyCitation_Type)jcasType).casFeatCode_textValue, v);}    
  }

    