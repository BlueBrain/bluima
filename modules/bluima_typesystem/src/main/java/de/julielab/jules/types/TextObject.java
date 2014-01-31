

/* First created by JCasGen Wed Oct 19 19:11:10 CEST 2011 */
package de.julielab.jules.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** Object, on our case, are annotations such as figures, tables, boxed text etc.
 * Updated by JCasGen Fri Oct 21 11:02:43 CEST 2011
 * XML source: /Users/ren/dev/bluebrain/svn_nlp/UIMA/julielab/trunk/julielab_typesystem-2.6.8/src/main/resources/typeSystem/julie-document-structure-types.xml
 * @generated */
public class TextObject extends Zone {
  /** @generated
   * @ordered 
   */
  public final static int typeIndexID = JCasRegistry.register(TextObject.class);
  /** @generated
   * @ordered 
   */
  public final static int type = typeIndexID;
  /** @generated  */
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected TextObject() {}
    
  /** Internal - constructor used by generator 
   * @generated */
  public TextObject(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public TextObject(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public TextObject(JCas jcas, int begin, int end) {
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
  //* Feature: objectType

  /** getter for objectType - gets such as figure, table, boxed-text etc.
   * @generated */
  public String getObjectType() {
    if (TextObject_Type.featOkTst && ((TextObject_Type)jcasType).casFeat_objectType == null)
      jcasType.jcas.throwFeatMissing("objectType", "de.julielab.jules.types.TextObject");
    return jcasType.ll_cas.ll_getStringValue(addr, ((TextObject_Type)jcasType).casFeatCode_objectType);}
    
  /** setter for objectType - sets such as figure, table, boxed-text etc. 
   * @generated */
  public void setObjectType(String v) {
    if (TextObject_Type.featOkTst && ((TextObject_Type)jcasType).casFeat_objectType == null)
      jcasType.jcas.throwFeatMissing("objectType", "de.julielab.jules.types.TextObject");
    jcasType.ll_cas.ll_setStringValue(addr, ((TextObject_Type)jcasType).casFeatCode_objectType, v);}    
   
    
  //*--------------*
  //* Feature: objectId

  /** getter for objectId - gets the id of the object as found in the text
   * @generated */
  public String getObjectId() {
    if (TextObject_Type.featOkTst && ((TextObject_Type)jcasType).casFeat_objectId == null)
      jcasType.jcas.throwFeatMissing("objectId", "de.julielab.jules.types.TextObject");
    return jcasType.ll_cas.ll_getStringValue(addr, ((TextObject_Type)jcasType).casFeatCode_objectId);}
    
  /** setter for objectId - sets the id of the object as found in the text 
   * @generated */
  public void setObjectId(String v) {
    if (TextObject_Type.featOkTst && ((TextObject_Type)jcasType).casFeat_objectId == null)
      jcasType.jcas.throwFeatMissing("objectId", "de.julielab.jules.types.TextObject");
    jcasType.ll_cas.ll_setStringValue(addr, ((TextObject_Type)jcasType).casFeatCode_objectId, v);}    
   
    
  //*--------------*
  //* Feature: objectLabel

  /** getter for objectLabel - gets the label of an object
   * @generated */
  public String getObjectLabel() {
    if (TextObject_Type.featOkTst && ((TextObject_Type)jcasType).casFeat_objectLabel == null)
      jcasType.jcas.throwFeatMissing("objectLabel", "de.julielab.jules.types.TextObject");
    return jcasType.ll_cas.ll_getStringValue(addr, ((TextObject_Type)jcasType).casFeatCode_objectLabel);}
    
  /** setter for objectLabel - sets the label of an object 
   * @generated */
  public void setObjectLabel(String v) {
    if (TextObject_Type.featOkTst && ((TextObject_Type)jcasType).casFeat_objectLabel == null)
      jcasType.jcas.throwFeatMissing("objectLabel", "de.julielab.jules.types.TextObject");
    jcasType.ll_cas.ll_setStringValue(addr, ((TextObject_Type)jcasType).casFeatCode_objectLabel, v);}    
   
    
  //*--------------*
  //* Feature: objectCaption

  /** getter for objectCaption - gets the caption that comes with the object
   * @generated */
  public Caption getObjectCaption() {
    if (TextObject_Type.featOkTst && ((TextObject_Type)jcasType).casFeat_objectCaption == null)
      jcasType.jcas.throwFeatMissing("objectCaption", "de.julielab.jules.types.TextObject");
    return (Caption)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((TextObject_Type)jcasType).casFeatCode_objectCaption)));}
    
  /** setter for objectCaption - sets the caption that comes with the object 
   * @generated */
  public void setObjectCaption(Caption v) {
    if (TextObject_Type.featOkTst && ((TextObject_Type)jcasType).casFeat_objectCaption == null)
      jcasType.jcas.throwFeatMissing("objectCaption", "de.julielab.jules.types.TextObject");
    jcasType.ll_cas.ll_setRefValue(addr, ((TextObject_Type)jcasType).casFeatCode_objectCaption, jcasType.ll_cas.ll_getFSRef(v));}    
  }

    