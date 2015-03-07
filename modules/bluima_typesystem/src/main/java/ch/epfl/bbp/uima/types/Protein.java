

/* First created by JCasGen Sat Mar 07 22:05:56 CET 2015 */
package ch.epfl.bbp.uima.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** 
 * Updated by JCasGen Sat Mar 07 22:05:56 CET 2015
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/target/jcasgen/typesystem.xml
 * @generated */
public class Protein extends de.julielab.jules.types.Protein {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Protein.class);
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
  protected Protein() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Protein(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Protein(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Protein(JCas jcas, int begin, int end) {
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
  //* Feature: id

  /** getter for id - gets 
   * @generated
   * @return value of the feature 
   */
  public String getId() {
    if (Protein_Type.featOkTst && ((Protein_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "ch.epfl.bbp.uima.types.Protein");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Protein_Type)jcasType).casFeatCode_id);}
    
  /** setter for id - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setId(String v) {
    if (Protein_Type.featOkTst && ((Protein_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "ch.epfl.bbp.uima.types.Protein");
    jcasType.ll_cas.ll_setStringValue(addr, ((Protein_Type)jcasType).casFeatCode_id, v);}    
   
    
  //*--------------*
  //* Feature: name

  /** getter for name - gets 
   * @generated
   * @return value of the feature 
   */
  public String getName() {
    if (Protein_Type.featOkTst && ((Protein_Type)jcasType).casFeat_name == null)
      jcasType.jcas.throwFeatMissing("name", "ch.epfl.bbp.uima.types.Protein");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Protein_Type)jcasType).casFeatCode_name);}
    
  /** setter for name - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setName(String v) {
    if (Protein_Type.featOkTst && ((Protein_Type)jcasType).casFeat_name == null)
      jcasType.jcas.throwFeatMissing("name", "ch.epfl.bbp.uima.types.Protein");
    jcasType.ll_cas.ll_setStringValue(addr, ((Protein_Type)jcasType).casFeatCode_name, v);}    
  }

    