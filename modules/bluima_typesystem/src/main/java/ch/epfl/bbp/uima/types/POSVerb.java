

/* First created by JCasGen Sat Mar 07 22:05:56 CET 2015 */
package ch.epfl.bbp.uima.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import de.julielab.jules.types.POSTag;


/** 
 * Updated by JCasGen Sat Mar 07 22:05:56 CET 2015
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/target/jcasgen/typesystem.xml
 * @generated */
public class POSVerb extends POSTag {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(POSVerb.class);
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
  protected POSVerb() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public POSVerb(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public POSVerb(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public POSVerb(JCas jcas, int begin, int end) {
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
  //* Feature: biolexicon_id

  /** getter for biolexicon_id - gets 
   * @generated
   * @return value of the feature 
   */
  public String getBiolexicon_id() {
    if (POSVerb_Type.featOkTst && ((POSVerb_Type)jcasType).casFeat_biolexicon_id == null)
      jcasType.jcas.throwFeatMissing("biolexicon_id", "ch.epfl.bbp.uima.types.POSVerb");
    return jcasType.ll_cas.ll_getStringValue(addr, ((POSVerb_Type)jcasType).casFeatCode_biolexicon_id);}
    
  /** setter for biolexicon_id - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setBiolexicon_id(String v) {
    if (POSVerb_Type.featOkTst && ((POSVerb_Type)jcasType).casFeat_biolexicon_id == null)
      jcasType.jcas.throwFeatMissing("biolexicon_id", "ch.epfl.bbp.uima.types.POSVerb");
    jcasType.ll_cas.ll_setStringValue(addr, ((POSVerb_Type)jcasType).casFeatCode_biolexicon_id, v);}    
  }

    