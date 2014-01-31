

/* First created by JCasGen Wed Oct 19 19:10:28 CEST 2011 */
package de.julielab.jules.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** An abbreviation is a letter or group of letters, taken from a word or words. For example, the word "abbreviation" can be abbreviated as "abbr." or "abbrev."
 * Updated by JCasGen Mon Oct 21 13:03:29 CEST 2013
 * XML source: /Users/richarde/dev/bluebrain/svn_nlp/UIMA/blue_uima/trunk/modules/julielab_typesystem-2.6.8/src/main/resources/typeSystem/bbp-semantics-biology-types.xml
 * @generated */
public class Abbreviation extends Annotation {
  /** @generated
   * @ordered 
   */
  public final static int typeIndexID = JCasRegistry.register(Abbreviation.class);
  /** @generated
   * @ordered 
   */
  public final static int type = typeIndexID;
  /** @generated  */
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected Abbreviation() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public Abbreviation(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public Abbreviation(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public Abbreviation(JCas jcas, int begin, int end) {
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
  //* Feature: expan

  /** getter for expan - gets The full form of an abbreviation. For example he fullform, for example for HLA (human leukocyte antigen), O
   * @generated */
  public String getExpan() {
    if (Abbreviation_Type.featOkTst && ((Abbreviation_Type)jcasType).casFeat_expan == null)
      jcasType.jcas.throwFeatMissing("expan", "de.julielab.jules.types.Abbreviation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Abbreviation_Type)jcasType).casFeatCode_expan);}
    
  /** setter for expan - sets The full form of an abbreviation. For example he fullform, for example for HLA (human leukocyte antigen), O 
   * @generated */
  public void setExpan(String v) {
    if (Abbreviation_Type.featOkTst && ((Abbreviation_Type)jcasType).casFeat_expan == null)
      jcasType.jcas.throwFeatMissing("expan", "de.julielab.jules.types.Abbreviation");
    jcasType.ll_cas.ll_setStringValue(addr, ((Abbreviation_Type)jcasType).casFeatCode_expan, v);}    
   
    
  //*--------------*
  //* Feature: textReference

  /** getter for textReference - gets Reference to the text span that contains the  full form of the abbreviation/acronym
   * @generated */
  public Annotation getTextReference() {
    if (Abbreviation_Type.featOkTst && ((Abbreviation_Type)jcasType).casFeat_textReference == null)
      jcasType.jcas.throwFeatMissing("textReference", "de.julielab.jules.types.Abbreviation");
    return (Annotation)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Abbreviation_Type)jcasType).casFeatCode_textReference)));}
    
  /** setter for textReference - sets Reference to the text span that contains the  full form of the abbreviation/acronym 
   * @generated */
  public void setTextReference(Annotation v) {
    if (Abbreviation_Type.featOkTst && ((Abbreviation_Type)jcasType).casFeat_textReference == null)
      jcasType.jcas.throwFeatMissing("textReference", "de.julielab.jules.types.Abbreviation");
    jcasType.ll_cas.ll_setRefValue(addr, ((Abbreviation_Type)jcasType).casFeatCode_textReference, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: definedHere

  /** getter for definedHere - gets The definedHere is true if the abbreviation/acronym is defined for the first time in the text, e.g. in "interleukin 2 (Il-2) receptor", it can be true only for locally introduced abbreviations/acronyms.
   * @generated */
  public boolean getDefinedHere() {
    if (Abbreviation_Type.featOkTst && ((Abbreviation_Type)jcasType).casFeat_definedHere == null)
      jcasType.jcas.throwFeatMissing("definedHere", "de.julielab.jules.types.Abbreviation");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((Abbreviation_Type)jcasType).casFeatCode_definedHere);}
    
  /** setter for definedHere - sets The definedHere is true if the abbreviation/acronym is defined for the first time in the text, e.g. in "interleukin 2 (Il-2) receptor", it can be true only for locally introduced abbreviations/acronyms. 
   * @generated */
  public void setDefinedHere(boolean v) {
    if (Abbreviation_Type.featOkTst && ((Abbreviation_Type)jcasType).casFeat_definedHere == null)
      jcasType.jcas.throwFeatMissing("definedHere", "de.julielab.jules.types.Abbreviation");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((Abbreviation_Type)jcasType).casFeatCode_definedHere, v);}    
  }

    