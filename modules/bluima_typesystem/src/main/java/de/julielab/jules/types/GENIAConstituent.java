

/* First created by JCasGen Wed Oct 19 19:10:28 CEST 2011 */
package de.julielab.jules.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** Ohta, Tomoko, Yuka Tateisi, Hideki Mima and Jun'ichi Tsujii. (2002). GENIA Corpus: an Annotated Research Abstract Corpus in Molecular Biology Domain. In the Proceedings of he Human Language Technology Conference (HLT 2002). pp73--77.
 * Updated by JCasGen Mon Oct 21 13:03:29 CEST 2013
 * XML source: /Users/richarde/dev/bluebrain/svn_nlp/UIMA/blue_uima/trunk/modules/julielab_typesystem-2.6.8/src/main/resources/typeSystem/bbp-semantics-biology-types.xml
 * @generated */
public class GENIAConstituent extends PTBConstituent {
  /** @generated
   * @ordered 
   */
  public final static int typeIndexID = JCasRegistry.register(GENIAConstituent.class);
  /** @generated
   * @ordered 
   */
  public final static int type = typeIndexID;
  /** @generated  */
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected GENIAConstituent() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public GENIAConstituent(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public GENIAConstituent(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public GENIAConstituent(JCas jcas, int begin, int end) {
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
  //* Feature: syn

  /** getter for syn - gets Marks coordinations, O
   * @generated */
  public String getSyn() {
    if (GENIAConstituent_Type.featOkTst && ((GENIAConstituent_Type)jcasType).casFeat_syn == null)
      jcasType.jcas.throwFeatMissing("syn", "de.julielab.jules.types.GENIAConstituent");
    return jcasType.ll_cas.ll_getStringValue(addr, ((GENIAConstituent_Type)jcasType).casFeatCode_syn);}
    
  /** setter for syn - sets Marks coordinations, O 
   * @generated */
  public void setSyn(String v) {
    if (GENIAConstituent_Type.featOkTst && ((GENIAConstituent_Type)jcasType).casFeat_syn == null)
      jcasType.jcas.throwFeatMissing("syn", "de.julielab.jules.types.GENIAConstituent");
    jcasType.ll_cas.ll_setStringValue(addr, ((GENIAConstituent_Type)jcasType).casFeatCode_syn, v);}    
  }

    