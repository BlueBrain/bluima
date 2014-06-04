

/* First created by JCasGen Wed Oct 19 19:10:28 CEST 2011 */
package de.julielab.jules.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.StringArray;


/** 
 * Updated by JCasGen Wed Jun 04 18:01:58 CEST 2014
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/src/main/resources/typeSystem/bbp-types.xml
 * @generated */
public class GOMention extends ConceptMention {
  /** @generated
   * @ordered 
   */
  public final static int typeIndexID = JCasRegistry.register(GOMention.class);
  /** @generated
   * @ordered 
   */
  public final static int type = typeIndexID;
  /** @generated  */
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected GOMention() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public GOMention(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public GOMention(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public GOMention(JCas jcas, int begin, int end) {
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
  private void readObject() {}
     
 
    
  //*--------------*
  //* Feature: categories

  /** getter for categories - gets created for the shared task, here we add the group
   * @generated
   * @return value of the feature 
   */
  public StringArray getCategories() {
    if (GOMention_Type.featOkTst && ((GOMention_Type)jcasType).casFeat_categories == null)
      jcasType.jcas.throwFeatMissing("categories", "de.julielab.jules.types.GOMention");
    return (StringArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((GOMention_Type)jcasType).casFeatCode_categories)));}
    
  /** setter for categories - sets created for the shared task, here we add the group 
   * @generated
   * @param v value to set into the feature 
   */
  public void setCategories(StringArray v) {
    if (GOMention_Type.featOkTst && ((GOMention_Type)jcasType).casFeat_categories == null)
      jcasType.jcas.throwFeatMissing("categories", "de.julielab.jules.types.GOMention");
    jcasType.ll_cas.ll_setRefValue(addr, ((GOMention_Type)jcasType).casFeatCode_categories, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for categories - gets an indexed value - created for the shared task, here we add the group
   * @generated */
  public String getCategories(int i) {
    if (GOMention_Type.featOkTst && ((GOMention_Type)jcasType).casFeat_categories == null)
      jcasType.jcas.throwFeatMissing("categories", "de.julielab.jules.types.GOMention");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((GOMention_Type)jcasType).casFeatCode_categories), i);
    return jcasType.ll_cas.ll_getStringArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((GOMention_Type)jcasType).casFeatCode_categories), i);}

  /** indexed setter for categories - sets an indexed value - created for the shared task, here we add the group
   * @generated */
  public void setCategories(int i, String v) { 
    if (GOMention_Type.featOkTst && ((GOMention_Type)jcasType).casFeat_categories == null)
      jcasType.jcas.throwFeatMissing("categories", "de.julielab.jules.types.GOMention");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((GOMention_Type)jcasType).casFeatCode_categories), i);
    jcasType.ll_cas.ll_setStringArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((GOMention_Type)jcasType).casFeatCode_categories), i, v);}
   
    
  //*--------------*
  //* Feature: goID

  /** getter for goID - gets 
   * @generated
   * @return value of the feature 
   */
  public String getGoID() {
    if (GOMention_Type.featOkTst && ((GOMention_Type)jcasType).casFeat_goID == null)
      jcasType.jcas.throwFeatMissing("goID", "de.julielab.jules.types.GOMention");
    return jcasType.ll_cas.ll_getStringValue(addr, ((GOMention_Type)jcasType).casFeatCode_goID);}
    
  /** setter for goID - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setGoID(String v) {
    if (GOMention_Type.featOkTst && ((GOMention_Type)jcasType).casFeat_goID == null)
      jcasType.jcas.throwFeatMissing("goID", "de.julielab.jules.types.GOMention");
    jcasType.ll_cas.ll_setStringValue(addr, ((GOMention_Type)jcasType).casFeatCode_goID, v);}    
  }

    