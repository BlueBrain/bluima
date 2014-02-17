

/* First created by JCasGen Mon Feb 17 12:26:53 CET 2014 */
package ch.epfl.bbp.uima.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** 
 * Updated by JCasGen Mon Feb 17 22:12:56 CET 2014
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/src/main/resources/typeSystem/bbp-types.xml
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
  /** @generated  */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected Protein() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public Protein(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public Protein(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public Protein(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** <!-- begin-user-doc -->
    * Write your own initialization here
    * <!-- end-user-doc -->
  @generated modifiable */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: id

  /** getter for id - gets 
   * @generated */
  public String getId() {
    if (Protein_Type.featOkTst && ((Protein_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "ch.epfl.bbp.uima.types.Protein");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Protein_Type)jcasType).casFeatCode_id);}
    
  /** setter for id - sets  
   * @generated */
  public void setId(String v) {
    if (Protein_Type.featOkTst && ((Protein_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "ch.epfl.bbp.uima.types.Protein");
    jcasType.ll_cas.ll_setStringValue(addr, ((Protein_Type)jcasType).casFeatCode_id, v);}    
   
    
  //*--------------*
  //* Feature: name

  /** getter for name - gets 
   * @generated */
  public String getName() {
    if (Protein_Type.featOkTst && ((Protein_Type)jcasType).casFeat_name == null)
      jcasType.jcas.throwFeatMissing("name", "ch.epfl.bbp.uima.types.Protein");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Protein_Type)jcasType).casFeatCode_name);}
    
  /** setter for name - sets  
   * @generated */
  public void setName(String v) {
    if (Protein_Type.featOkTst && ((Protein_Type)jcasType).casFeat_name == null)
      jcasType.jcas.throwFeatMissing("name", "ch.epfl.bbp.uima.types.Protein");
    jcasType.ll_cas.ll_setStringValue(addr, ((Protein_Type)jcasType).casFeatCode_name, v);}    
  }

    