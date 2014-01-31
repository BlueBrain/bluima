

/* First created by JCasGen Wed Oct 19 19:12:42 CEST 2011 */
package de.julielab.jules.types.bootstrep;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import de.julielab.jules.types.BioRelationMention;


/** specificType: positive, negative, unspecified
contains regulation of gene transcription as well
 * Updated by JCasGen Wed Oct 19 19:12:42 CEST 2011
 * XML source: /Users/ren/dev/bluebrain/svn_nlp/UIMA/julielab/trunk/julielab_typesystem-2.6.8/src/main/resources/typeSystem/julie-semantics-bootstrep-types.xml
 * @generated */
public class RegulationOfGeneExpression extends BioRelationMention {
  /** @generated
   * @ordered 
   */
  public final static int typeIndexID = JCasRegistry.register(RegulationOfGeneExpression.class);
  /** @generated
   * @ordered 
   */
  public final static int type = typeIndexID;
  /** @generated  */
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected RegulationOfGeneExpression() {}
    
  /** Internal - constructor used by generator 
   * @generated */
  public RegulationOfGeneExpression(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public RegulationOfGeneExpression(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public RegulationOfGeneExpression(JCas jcas, int begin, int end) {
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
  //* Feature: physicalContact

  /** getter for physicalContact - gets yes, no, unknown
   * @generated */
  public String getPhysicalContact() {
    if (RegulationOfGeneExpression_Type.featOkTst && ((RegulationOfGeneExpression_Type)jcasType).casFeat_physicalContact == null)
      jcasType.jcas.throwFeatMissing("physicalContact", "de.julielab.jules.types.bootstrep.RegulationOfGeneExpression");
    return jcasType.ll_cas.ll_getStringValue(addr, ((RegulationOfGeneExpression_Type)jcasType).casFeatCode_physicalContact);}
    
  /** setter for physicalContact - sets yes, no, unknown 
   * @generated */
  public void setPhysicalContact(String v) {
    if (RegulationOfGeneExpression_Type.featOkTst && ((RegulationOfGeneExpression_Type)jcasType).casFeat_physicalContact == null)
      jcasType.jcas.throwFeatMissing("physicalContact", "de.julielab.jules.types.bootstrep.RegulationOfGeneExpression");
    jcasType.ll_cas.ll_setStringValue(addr, ((RegulationOfGeneExpression_Type)jcasType).casFeatCode_physicalContact, v);}    
  }

    