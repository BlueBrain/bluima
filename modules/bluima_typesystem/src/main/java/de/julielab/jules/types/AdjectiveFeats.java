

/* First created by JCasGen Sat Mar 07 22:05:57 CET 2015 */
package de.julielab.jules.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** Describes a word structure, default grammatical features of a adjective
 * Updated by JCasGen Sat Mar 07 22:05:57 CET 2015
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/target/jcasgen/typesystem.xml
 * @generated */
public class AdjectiveFeats extends GrammaticalFeats {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(AdjectiveFeats.class);
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
  protected AdjectiveFeats() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public AdjectiveFeats(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public AdjectiveFeats(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public AdjectiveFeats(JCas jcas, int begin, int end) {
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
  //* Feature: degree

  /** getter for degree - gets Degree
   * @generated
   * @return value of the feature 
   */
  public String getDegree() {
    if (AdjectiveFeats_Type.featOkTst && ((AdjectiveFeats_Type)jcasType).casFeat_degree == null)
      jcasType.jcas.throwFeatMissing("degree", "de.julielab.jules.types.AdjectiveFeats");
    return jcasType.ll_cas.ll_getStringValue(addr, ((AdjectiveFeats_Type)jcasType).casFeatCode_degree);}
    
  /** setter for degree - sets Degree 
   * @generated
   * @param v value to set into the feature 
   */
  public void setDegree(String v) {
    if (AdjectiveFeats_Type.featOkTst && ((AdjectiveFeats_Type)jcasType).casFeat_degree == null)
      jcasType.jcas.throwFeatMissing("degree", "de.julielab.jules.types.AdjectiveFeats");
    jcasType.ll_cas.ll_setStringValue(addr, ((AdjectiveFeats_Type)jcasType).casFeatCode_degree, v);}    
  }

    