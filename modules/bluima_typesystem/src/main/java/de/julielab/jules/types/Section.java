

/* First created by JCasGen Sat Mar 07 22:05:57 CET 2015 */
package de.julielab.jules.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.FSArray;


/** a section is a part of the text that often has a heading, an id, a section type, figures, tables, citations and footnotes that occur in this section
 * Updated by JCasGen Sat Mar 07 22:05:57 CET 2015
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/target/jcasgen/typesystem.xml
 * @generated */
public class Section extends Zone {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Section.class);
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
  protected Section() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Section(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Section(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Section(JCas jcas, int begin, int end) {
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
  //* Feature: sectionHeading

  /** getter for sectionHeading - gets the title of the section
   * @generated
   * @return value of the feature 
   */
  public Title getSectionHeading() {
    if (Section_Type.featOkTst && ((Section_Type)jcasType).casFeat_sectionHeading == null)
      jcasType.jcas.throwFeatMissing("sectionHeading", "de.julielab.jules.types.Section");
    return (Title)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Section_Type)jcasType).casFeatCode_sectionHeading)));}
    
  /** setter for sectionHeading - sets the title of the section 
   * @generated
   * @param v value to set into the feature 
   */
  public void setSectionHeading(Title v) {
    if (Section_Type.featOkTst && ((Section_Type)jcasType).casFeat_sectionHeading == null)
      jcasType.jcas.throwFeatMissing("sectionHeading", "de.julielab.jules.types.Section");
    jcasType.ll_cas.ll_setRefValue(addr, ((Section_Type)jcasType).casFeatCode_sectionHeading, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: sectionType

  /** getter for sectionType - gets the type of the section (e.g. results)
   * @generated
   * @return value of the feature 
   */
  public String getSectionType() {
    if (Section_Type.featOkTst && ((Section_Type)jcasType).casFeat_sectionType == null)
      jcasType.jcas.throwFeatMissing("sectionType", "de.julielab.jules.types.Section");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Section_Type)jcasType).casFeatCode_sectionType);}
    
  /** setter for sectionType - sets the type of the section (e.g. results) 
   * @generated
   * @param v value to set into the feature 
   */
  public void setSectionType(String v) {
    if (Section_Type.featOkTst && ((Section_Type)jcasType).casFeat_sectionType == null)
      jcasType.jcas.throwFeatMissing("sectionType", "de.julielab.jules.types.Section");
    jcasType.ll_cas.ll_setStringValue(addr, ((Section_Type)jcasType).casFeatCode_sectionType, v);}    
   
    
  //*--------------*
  //* Feature: textObjects

  /** getter for textObjects - gets the text objects (figure, table, boxed text etc.) that are associated with a particular section
   * @generated
   * @return value of the feature 
   */
  public FSArray getTextObjects() {
    if (Section_Type.featOkTst && ((Section_Type)jcasType).casFeat_textObjects == null)
      jcasType.jcas.throwFeatMissing("textObjects", "de.julielab.jules.types.Section");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Section_Type)jcasType).casFeatCode_textObjects)));}
    
  /** setter for textObjects - sets the text objects (figure, table, boxed text etc.) that are associated with a particular section 
   * @generated
   * @param v value to set into the feature 
   */
  public void setTextObjects(FSArray v) {
    if (Section_Type.featOkTst && ((Section_Type)jcasType).casFeat_textObjects == null)
      jcasType.jcas.throwFeatMissing("textObjects", "de.julielab.jules.types.Section");
    jcasType.ll_cas.ll_setRefValue(addr, ((Section_Type)jcasType).casFeatCode_textObjects, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for textObjects - gets an indexed value - the text objects (figure, table, boxed text etc.) that are associated with a particular section
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public TextObject getTextObjects(int i) {
    if (Section_Type.featOkTst && ((Section_Type)jcasType).casFeat_textObjects == null)
      jcasType.jcas.throwFeatMissing("textObjects", "de.julielab.jules.types.Section");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Section_Type)jcasType).casFeatCode_textObjects), i);
    return (TextObject)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Section_Type)jcasType).casFeatCode_textObjects), i)));}

  /** indexed setter for textObjects - sets an indexed value - the text objects (figure, table, boxed text etc.) that are associated with a particular section
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setTextObjects(int i, TextObject v) { 
    if (Section_Type.featOkTst && ((Section_Type)jcasType).casFeat_textObjects == null)
      jcasType.jcas.throwFeatMissing("textObjects", "de.julielab.jules.types.Section");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Section_Type)jcasType).casFeatCode_textObjects), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Section_Type)jcasType).casFeatCode_textObjects), i, jcasType.ll_cas.ll_getFSRef(v));}
   
    
  //*--------------*
  //* Feature: sectionId

  /** getter for sectionId - gets the id of the section, for example as mentioned in the original file, or level of the section
   * @generated
   * @return value of the feature 
   */
  public String getSectionId() {
    if (Section_Type.featOkTst && ((Section_Type)jcasType).casFeat_sectionId == null)
      jcasType.jcas.throwFeatMissing("sectionId", "de.julielab.jules.types.Section");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Section_Type)jcasType).casFeatCode_sectionId);}
    
  /** setter for sectionId - sets the id of the section, for example as mentioned in the original file, or level of the section 
   * @generated
   * @param v value to set into the feature 
   */
  public void setSectionId(String v) {
    if (Section_Type.featOkTst && ((Section_Type)jcasType).casFeat_sectionId == null)
      jcasType.jcas.throwFeatMissing("sectionId", "de.julielab.jules.types.Section");
    jcasType.ll_cas.ll_setStringValue(addr, ((Section_Type)jcasType).casFeatCode_sectionId, v);}    
  }

    