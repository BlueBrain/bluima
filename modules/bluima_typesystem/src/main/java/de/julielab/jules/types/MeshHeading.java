

/* First created by JCasGen Wed Oct 19 19:10:41 CEST 2011 */
package de.julielab.jules.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** Medical Subject Headings, see NLM's MeSH for a detailed description.
 * Updated by JCasGen Fri Dec 09 11:59:26 CET 2011
 * XML source: /Users/richarde/dev/bluebrain/svn_nlp/UIMA/blue_uima/trunk/julielab_typesystem-2.6.8/src/main/resources/typeSystem/julie-document-meta-types.xml
 * @generated */
public class MeshHeading extends Annotation {
  /** @generated
   * @ordered 
   */
  public final static int typeIndexID = JCasRegistry.register(MeshHeading.class);
  /** @generated
   * @ordered 
   */
  public final static int type = typeIndexID;
  /** @generated  */
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected MeshHeading() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public MeshHeading(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public MeshHeading(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public MeshHeading(JCas jcas, int begin, int end) {
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
  //* Feature: descriptorName

  /** getter for descriptorName - gets see MeSH
   * @generated */
  public String getDescriptorName() {
    if (MeshHeading_Type.featOkTst && ((MeshHeading_Type)jcasType).casFeat_descriptorName == null)
      jcasType.jcas.throwFeatMissing("descriptorName", "de.julielab.jules.types.MeshHeading");
    return jcasType.ll_cas.ll_getStringValue(addr, ((MeshHeading_Type)jcasType).casFeatCode_descriptorName);}
    
  /** setter for descriptorName - sets see MeSH 
   * @generated */
  public void setDescriptorName(String v) {
    if (MeshHeading_Type.featOkTst && ((MeshHeading_Type)jcasType).casFeat_descriptorName == null)
      jcasType.jcas.throwFeatMissing("descriptorName", "de.julielab.jules.types.MeshHeading");
    jcasType.ll_cas.ll_setStringValue(addr, ((MeshHeading_Type)jcasType).casFeatCode_descriptorName, v);}    
   
    
  //*--------------*
  //* Feature: qualifierName

  /** getter for qualifierName - gets see MeSH
   * @generated */
  public String getQualifierName() {
    if (MeshHeading_Type.featOkTst && ((MeshHeading_Type)jcasType).casFeat_qualifierName == null)
      jcasType.jcas.throwFeatMissing("qualifierName", "de.julielab.jules.types.MeshHeading");
    return jcasType.ll_cas.ll_getStringValue(addr, ((MeshHeading_Type)jcasType).casFeatCode_qualifierName);}
    
  /** setter for qualifierName - sets see MeSH 
   * @generated */
  public void setQualifierName(String v) {
    if (MeshHeading_Type.featOkTst && ((MeshHeading_Type)jcasType).casFeat_qualifierName == null)
      jcasType.jcas.throwFeatMissing("qualifierName", "de.julielab.jules.types.MeshHeading");
    jcasType.ll_cas.ll_setStringValue(addr, ((MeshHeading_Type)jcasType).casFeatCode_qualifierName, v);}    
   
    
  //*--------------*
  //* Feature: descriptorNameMajorTopic

  /** getter for descriptorNameMajorTopic - gets see MeSH
   * @generated */
  public boolean getDescriptorNameMajorTopic() {
    if (MeshHeading_Type.featOkTst && ((MeshHeading_Type)jcasType).casFeat_descriptorNameMajorTopic == null)
      jcasType.jcas.throwFeatMissing("descriptorNameMajorTopic", "de.julielab.jules.types.MeshHeading");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((MeshHeading_Type)jcasType).casFeatCode_descriptorNameMajorTopic);}
    
  /** setter for descriptorNameMajorTopic - sets see MeSH 
   * @generated */
  public void setDescriptorNameMajorTopic(boolean v) {
    if (MeshHeading_Type.featOkTst && ((MeshHeading_Type)jcasType).casFeat_descriptorNameMajorTopic == null)
      jcasType.jcas.throwFeatMissing("descriptorNameMajorTopic", "de.julielab.jules.types.MeshHeading");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((MeshHeading_Type)jcasType).casFeatCode_descriptorNameMajorTopic, v);}    
   
    
  //*--------------*
  //* Feature: qualifierNameMajorTopic

  /** getter for qualifierNameMajorTopic - gets see MeSH
   * @generated */
  public boolean getQualifierNameMajorTopic() {
    if (MeshHeading_Type.featOkTst && ((MeshHeading_Type)jcasType).casFeat_qualifierNameMajorTopic == null)
      jcasType.jcas.throwFeatMissing("qualifierNameMajorTopic", "de.julielab.jules.types.MeshHeading");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((MeshHeading_Type)jcasType).casFeatCode_qualifierNameMajorTopic);}
    
  /** setter for qualifierNameMajorTopic - sets see MeSH 
   * @generated */
  public void setQualifierNameMajorTopic(boolean v) {
    if (MeshHeading_Type.featOkTst && ((MeshHeading_Type)jcasType).casFeat_qualifierNameMajorTopic == null)
      jcasType.jcas.throwFeatMissing("qualifierNameMajorTopic", "de.julielab.jules.types.MeshHeading");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((MeshHeading_Type)jcasType).casFeatCode_qualifierNameMajorTopic, v);}    
  }

    