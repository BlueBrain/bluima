

/* First created by JCasGen Sat Mar 07 22:05:57 CET 2015 */
package de.julielab.jules.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** Medical Subject Headings, see NLM's MeSH for a detailed description.
 * Updated by JCasGen Sat Mar 07 22:05:57 CET 2015
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/target/jcasgen/typesystem.xml
 * @generated */
public class MeshHeading extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(MeshHeading.class);
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
  protected MeshHeading() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public MeshHeading(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public MeshHeading(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public MeshHeading(JCas jcas, int begin, int end) {
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
  //* Feature: descriptorName

  /** getter for descriptorName - gets see MeSH
   * @generated
   * @return value of the feature 
   */
  public String getDescriptorName() {
    if (MeshHeading_Type.featOkTst && ((MeshHeading_Type)jcasType).casFeat_descriptorName == null)
      jcasType.jcas.throwFeatMissing("descriptorName", "de.julielab.jules.types.MeshHeading");
    return jcasType.ll_cas.ll_getStringValue(addr, ((MeshHeading_Type)jcasType).casFeatCode_descriptorName);}
    
  /** setter for descriptorName - sets see MeSH 
   * @generated
   * @param v value to set into the feature 
   */
  public void setDescriptorName(String v) {
    if (MeshHeading_Type.featOkTst && ((MeshHeading_Type)jcasType).casFeat_descriptorName == null)
      jcasType.jcas.throwFeatMissing("descriptorName", "de.julielab.jules.types.MeshHeading");
    jcasType.ll_cas.ll_setStringValue(addr, ((MeshHeading_Type)jcasType).casFeatCode_descriptorName, v);}    
   
    
  //*--------------*
  //* Feature: qualifierName

  /** getter for qualifierName - gets see MeSH
   * @generated
   * @return value of the feature 
   */
  public String getQualifierName() {
    if (MeshHeading_Type.featOkTst && ((MeshHeading_Type)jcasType).casFeat_qualifierName == null)
      jcasType.jcas.throwFeatMissing("qualifierName", "de.julielab.jules.types.MeshHeading");
    return jcasType.ll_cas.ll_getStringValue(addr, ((MeshHeading_Type)jcasType).casFeatCode_qualifierName);}
    
  /** setter for qualifierName - sets see MeSH 
   * @generated
   * @param v value to set into the feature 
   */
  public void setQualifierName(String v) {
    if (MeshHeading_Type.featOkTst && ((MeshHeading_Type)jcasType).casFeat_qualifierName == null)
      jcasType.jcas.throwFeatMissing("qualifierName", "de.julielab.jules.types.MeshHeading");
    jcasType.ll_cas.ll_setStringValue(addr, ((MeshHeading_Type)jcasType).casFeatCode_qualifierName, v);}    
   
    
  //*--------------*
  //* Feature: descriptorNameMajorTopic

  /** getter for descriptorNameMajorTopic - gets see MeSH
   * @generated
   * @return value of the feature 
   */
  public boolean getDescriptorNameMajorTopic() {
    if (MeshHeading_Type.featOkTst && ((MeshHeading_Type)jcasType).casFeat_descriptorNameMajorTopic == null)
      jcasType.jcas.throwFeatMissing("descriptorNameMajorTopic", "de.julielab.jules.types.MeshHeading");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((MeshHeading_Type)jcasType).casFeatCode_descriptorNameMajorTopic);}
    
  /** setter for descriptorNameMajorTopic - sets see MeSH 
   * @generated
   * @param v value to set into the feature 
   */
  public void setDescriptorNameMajorTopic(boolean v) {
    if (MeshHeading_Type.featOkTst && ((MeshHeading_Type)jcasType).casFeat_descriptorNameMajorTopic == null)
      jcasType.jcas.throwFeatMissing("descriptorNameMajorTopic", "de.julielab.jules.types.MeshHeading");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((MeshHeading_Type)jcasType).casFeatCode_descriptorNameMajorTopic, v);}    
   
    
  //*--------------*
  //* Feature: qualifierNameMajorTopic

  /** getter for qualifierNameMajorTopic - gets see MeSH
   * @generated
   * @return value of the feature 
   */
  public boolean getQualifierNameMajorTopic() {
    if (MeshHeading_Type.featOkTst && ((MeshHeading_Type)jcasType).casFeat_qualifierNameMajorTopic == null)
      jcasType.jcas.throwFeatMissing("qualifierNameMajorTopic", "de.julielab.jules.types.MeshHeading");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((MeshHeading_Type)jcasType).casFeatCode_qualifierNameMajorTopic);}
    
  /** setter for qualifierNameMajorTopic - sets see MeSH 
   * @generated
   * @param v value to set into the feature 
   */
  public void setQualifierNameMajorTopic(boolean v) {
    if (MeshHeading_Type.featOkTst && ((MeshHeading_Type)jcasType).casFeat_qualifierNameMajorTopic == null)
      jcasType.jcas.throwFeatMissing("qualifierNameMajorTopic", "de.julielab.jules.types.MeshHeading");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((MeshHeading_Type)jcasType).casFeatCode_qualifierNameMajorTopic, v);}    
  }

    