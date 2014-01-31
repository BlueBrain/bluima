

/* First created by JCasGen Wed Oct 19 19:11:10 CEST 2011 */
package de.julielab.jules.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.FSArray;


/** a section is a part of the text that often has a heading, an id, a section type, figures, tables, citations and footnotes that occur in this section
 * Updated by JCasGen Fri Oct 21 11:02:43 CEST 2011
 * XML source: /Users/ren/dev/bluebrain/svn_nlp/UIMA/julielab/trunk/julielab_typesystem-2.6.8/src/main/resources/typeSystem/julie-document-structure-types.xml
 * @generated */
public class Section extends Zone {
  /** @generated
   * @ordered 
   */
  public final static int typeIndexID = JCasRegistry.register(Section.class);
  /** @generated
   * @ordered 
   */
  public final static int type = typeIndexID;
  /** @generated  */
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected Section() {}
    
  /** Internal - constructor used by generator 
   * @generated */
  public Section(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public Section(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public Section(JCas jcas, int begin, int end) {
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
  //* Feature: sectionHeading

  /** getter for sectionHeading - gets the title of the section
   * @generated */
  public Title getSectionHeading() {
    if (Section_Type.featOkTst && ((Section_Type)jcasType).casFeat_sectionHeading == null)
      jcasType.jcas.throwFeatMissing("sectionHeading", "de.julielab.jules.types.Section");
    return (Title)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Section_Type)jcasType).casFeatCode_sectionHeading)));}
    
  /** setter for sectionHeading - sets the title of the section 
   * @generated */
  public void setSectionHeading(Title v) {
    if (Section_Type.featOkTst && ((Section_Type)jcasType).casFeat_sectionHeading == null)
      jcasType.jcas.throwFeatMissing("sectionHeading", "de.julielab.jules.types.Section");
    jcasType.ll_cas.ll_setRefValue(addr, ((Section_Type)jcasType).casFeatCode_sectionHeading, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: sectionType

  /** getter for sectionType - gets the type of the section (e.g. results)
   * @generated */
  public String getSectionType() {
    if (Section_Type.featOkTst && ((Section_Type)jcasType).casFeat_sectionType == null)
      jcasType.jcas.throwFeatMissing("sectionType", "de.julielab.jules.types.Section");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Section_Type)jcasType).casFeatCode_sectionType);}
    
  /** setter for sectionType - sets the type of the section (e.g. results) 
   * @generated */
  public void setSectionType(String v) {
    if (Section_Type.featOkTst && ((Section_Type)jcasType).casFeat_sectionType == null)
      jcasType.jcas.throwFeatMissing("sectionType", "de.julielab.jules.types.Section");
    jcasType.ll_cas.ll_setStringValue(addr, ((Section_Type)jcasType).casFeatCode_sectionType, v);}    
   
    
  //*--------------*
  //* Feature: textObjects

  /** getter for textObjects - gets the text objects (figure, table, boxed text etc.) that are associated with a particular section
   * @generated */
  public FSArray getTextObjects() {
    if (Section_Type.featOkTst && ((Section_Type)jcasType).casFeat_textObjects == null)
      jcasType.jcas.throwFeatMissing("textObjects", "de.julielab.jules.types.Section");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Section_Type)jcasType).casFeatCode_textObjects)));}
    
  /** setter for textObjects - sets the text objects (figure, table, boxed text etc.) that are associated with a particular section 
   * @generated */
  public void setTextObjects(FSArray v) {
    if (Section_Type.featOkTst && ((Section_Type)jcasType).casFeat_textObjects == null)
      jcasType.jcas.throwFeatMissing("textObjects", "de.julielab.jules.types.Section");
    jcasType.ll_cas.ll_setRefValue(addr, ((Section_Type)jcasType).casFeatCode_textObjects, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for textObjects - gets an indexed value - the text objects (figure, table, boxed text etc.) that are associated with a particular section
   * @generated */
  public TextObject getTextObjects(int i) {
    if (Section_Type.featOkTst && ((Section_Type)jcasType).casFeat_textObjects == null)
      jcasType.jcas.throwFeatMissing("textObjects", "de.julielab.jules.types.Section");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Section_Type)jcasType).casFeatCode_textObjects), i);
    return (TextObject)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Section_Type)jcasType).casFeatCode_textObjects), i)));}

  /** indexed setter for textObjects - sets an indexed value - the text objects (figure, table, boxed text etc.) that are associated with a particular section
   * @generated */
  public void setTextObjects(int i, TextObject v) { 
    if (Section_Type.featOkTst && ((Section_Type)jcasType).casFeat_textObjects == null)
      jcasType.jcas.throwFeatMissing("textObjects", "de.julielab.jules.types.Section");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Section_Type)jcasType).casFeatCode_textObjects), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Section_Type)jcasType).casFeatCode_textObjects), i, jcasType.ll_cas.ll_getFSRef(v));}
   
    
  //*--------------*
  //* Feature: sectionId

  /** getter for sectionId - gets the id of the section, for example as mentioned in the original file, or level of the section
   * @generated */
  public String getSectionId() {
    if (Section_Type.featOkTst && ((Section_Type)jcasType).casFeat_sectionId == null)
      jcasType.jcas.throwFeatMissing("sectionId", "de.julielab.jules.types.Section");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Section_Type)jcasType).casFeatCode_sectionId);}
    
  /** setter for sectionId - sets the id of the section, for example as mentioned in the original file, or level of the section 
   * @generated */
  public void setSectionId(String v) {
    if (Section_Type.featOkTst && ((Section_Type)jcasType).casFeat_sectionId == null)
      jcasType.jcas.throwFeatMissing("sectionId", "de.julielab.jules.types.Section");
    jcasType.ll_cas.ll_setStringValue(addr, ((Section_Type)jcasType).casFeatCode_sectionId, v);}    
  }

    