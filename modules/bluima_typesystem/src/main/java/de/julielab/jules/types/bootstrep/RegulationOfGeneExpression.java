

/* First created by JCasGen Sat Mar 07 22:05:57 CET 2015 */
package de.julielab.jules.types.bootstrep;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import de.julielab.jules.types.BioRelationMention;


/** specificType: positive, negative, unspecified
contains regulation of gene transcription as well
 * Updated by JCasGen Sat Mar 07 22:05:57 CET 2015
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/target/jcasgen/typesystem.xml
 * @generated */
public class RegulationOfGeneExpression extends BioRelationMention {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(RegulationOfGeneExpression.class);
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
  protected RegulationOfGeneExpression() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public RegulationOfGeneExpression(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public RegulationOfGeneExpression(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public RegulationOfGeneExpression(JCas jcas, int begin, int end) {
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
  //* Feature: physicalContact

  /** getter for physicalContact - gets yes, no, unknown
   * @generated
   * @return value of the feature 
   */
  public String getPhysicalContact() {
    if (RegulationOfGeneExpression_Type.featOkTst && ((RegulationOfGeneExpression_Type)jcasType).casFeat_physicalContact == null)
      jcasType.jcas.throwFeatMissing("physicalContact", "de.julielab.jules.types.bootstrep.RegulationOfGeneExpression");
    return jcasType.ll_cas.ll_getStringValue(addr, ((RegulationOfGeneExpression_Type)jcasType).casFeatCode_physicalContact);}
    
  /** setter for physicalContact - sets yes, no, unknown 
   * @generated
   * @param v value to set into the feature 
   */
  public void setPhysicalContact(String v) {
    if (RegulationOfGeneExpression_Type.featOkTst && ((RegulationOfGeneExpression_Type)jcasType).casFeat_physicalContact == null)
      jcasType.jcas.throwFeatMissing("physicalContact", "de.julielab.jules.types.bootstrep.RegulationOfGeneExpression");
    jcasType.ll_cas.ll_setStringValue(addr, ((RegulationOfGeneExpression_Type)jcasType).casFeatCode_physicalContact, v);}    
  }

    