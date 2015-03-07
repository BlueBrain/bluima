

/* First created by JCasGen Sat Mar 07 22:05:56 CET 2015 */
package ch.epfl.bbp.uima.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Sat Mar 07 22:05:56 CET 2015
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/target/jcasgen/typesystem.xml
 * @generated */
public class MultipleProteins extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(MultipleProteins.class);
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
  protected MultipleProteins() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public MultipleProteins(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public MultipleProteins(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public MultipleProteins(JCas jcas, int begin, int end) {
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
  //* Feature: present

  /** getter for present - gets 1 if present
   * @generated
   * @return value of the feature 
   */
  public int getPresent() {
    if (MultipleProteins_Type.featOkTst && ((MultipleProteins_Type)jcasType).casFeat_present == null)
      jcasType.jcas.throwFeatMissing("present", "ch.epfl.bbp.uima.types.MultipleProteins");
    return jcasType.ll_cas.ll_getIntValue(addr, ((MultipleProteins_Type)jcasType).casFeatCode_present);}
    
  /** setter for present - sets 1 if present 
   * @generated
   * @param v value to set into the feature 
   */
  public void setPresent(int v) {
    if (MultipleProteins_Type.featOkTst && ((MultipleProteins_Type)jcasType).casFeat_present == null)
      jcasType.jcas.throwFeatMissing("present", "ch.epfl.bbp.uima.types.MultipleProteins");
    jcasType.ll_cas.ll_setIntValue(addr, ((MultipleProteins_Type)jcasType).casFeatCode_present, v);}    
  }

    