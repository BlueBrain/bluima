

/* First created by JCasGen Fri Sep 14 13:58:44 CEST 2012 */
package ch.epfl.bbp.uima.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import de.julielab.jules.types.POSTag;


/** 
 * Updated by JCasGen Mon Feb 17 10:49:40 CET 2014
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/src/main/resources/typeSystem/bbp-types.xml
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
  /** @generated  */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected POSVerb() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public POSVerb(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public POSVerb(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public POSVerb(JCas jcas, int begin, int end) {
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
  //* Feature: biolexicon_id

  /** getter for biolexicon_id - gets 
   * @generated */
  public String getBiolexicon_id() {
    if (POSVerb_Type.featOkTst && ((POSVerb_Type)jcasType).casFeat_biolexicon_id == null)
      jcasType.jcas.throwFeatMissing("biolexicon_id", "ch.epfl.bbp.uima.types.POSVerb");
    return jcasType.ll_cas.ll_getStringValue(addr, ((POSVerb_Type)jcasType).casFeatCode_biolexicon_id);}
    
  /** setter for biolexicon_id - sets  
   * @generated */
  public void setBiolexicon_id(String v) {
    if (POSVerb_Type.featOkTst && ((POSVerb_Type)jcasType).casFeat_biolexicon_id == null)
      jcasType.jcas.throwFeatMissing("biolexicon_id", "ch.epfl.bbp.uima.types.POSVerb");
    jcasType.ll_cas.ll_setStringValue(addr, ((POSVerb_Type)jcasType).casFeatCode_biolexicon_id, v);}    
  }

    