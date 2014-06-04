

/* First created by JCasGen Wed Oct 19 19:10:28 CEST 2011 */
package de.julielab.jules.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** The reference to an external resource
 * Updated by JCasGen Wed Jun 04 18:01:58 CEST 2014
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/src/main/resources/typeSystem/bbp-types.xml
 * @generated */
public class ResourceEntry extends Annotation {
  /** @generated
   * @ordered 
   */
  public final static int typeIndexID = JCasRegistry.register(ResourceEntry.class);
  /** @generated
   * @ordered 
   */
  public final static int type = typeIndexID;
  /** @generated  */
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected ResourceEntry() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public ResourceEntry(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public ResourceEntry(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public ResourceEntry(JCas jcas, int begin, int end) {
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
  //* Feature: source

  /** getter for source - gets The name of the resource, C
   * @generated
   * @return value of the feature 
   */
  public String getSource() {
    if (ResourceEntry_Type.featOkTst && ((ResourceEntry_Type)jcasType).casFeat_source == null)
      jcasType.jcas.throwFeatMissing("source", "de.julielab.jules.types.ResourceEntry");
    return jcasType.ll_cas.ll_getStringValue(addr, ((ResourceEntry_Type)jcasType).casFeatCode_source);}
    
  /** setter for source - sets The name of the resource, C 
   * @generated
   * @param v value to set into the feature 
   */
  public void setSource(String v) {
    if (ResourceEntry_Type.featOkTst && ((ResourceEntry_Type)jcasType).casFeat_source == null)
      jcasType.jcas.throwFeatMissing("source", "de.julielab.jules.types.ResourceEntry");
    jcasType.ll_cas.ll_setStringValue(addr, ((ResourceEntry_Type)jcasType).casFeatCode_source, v);}    
   
    
  //*--------------*
  //* Feature: entryId

  /** getter for entryId - gets The identifier of the entry, C
   * @generated
   * @return value of the feature 
   */
  public String getEntryId() {
    if (ResourceEntry_Type.featOkTst && ((ResourceEntry_Type)jcasType).casFeat_entryId == null)
      jcasType.jcas.throwFeatMissing("entryId", "de.julielab.jules.types.ResourceEntry");
    return jcasType.ll_cas.ll_getStringValue(addr, ((ResourceEntry_Type)jcasType).casFeatCode_entryId);}
    
  /** setter for entryId - sets The identifier of the entry, C 
   * @generated
   * @param v value to set into the feature 
   */
  public void setEntryId(String v) {
    if (ResourceEntry_Type.featOkTst && ((ResourceEntry_Type)jcasType).casFeat_entryId == null)
      jcasType.jcas.throwFeatMissing("entryId", "de.julielab.jules.types.ResourceEntry");
    jcasType.ll_cas.ll_setStringValue(addr, ((ResourceEntry_Type)jcasType).casFeatCode_entryId, v);}    
   
    
  //*--------------*
  //* Feature: version

  /** getter for version - gets The version of the resource, C
   * @generated
   * @return value of the feature 
   */
  public String getVersion() {
    if (ResourceEntry_Type.featOkTst && ((ResourceEntry_Type)jcasType).casFeat_version == null)
      jcasType.jcas.throwFeatMissing("version", "de.julielab.jules.types.ResourceEntry");
    return jcasType.ll_cas.ll_getStringValue(addr, ((ResourceEntry_Type)jcasType).casFeatCode_version);}
    
  /** setter for version - sets The version of the resource, C 
   * @generated
   * @param v value to set into the feature 
   */
  public void setVersion(String v) {
    if (ResourceEntry_Type.featOkTst && ((ResourceEntry_Type)jcasType).casFeat_version == null)
      jcasType.jcas.throwFeatMissing("version", "de.julielab.jules.types.ResourceEntry");
    jcasType.ll_cas.ll_setStringValue(addr, ((ResourceEntry_Type)jcasType).casFeatCode_version, v);}    
  }

    