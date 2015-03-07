

/* First created by JCasGen Sat Mar 07 22:05:57 CET 2015 */
package de.julielab.jules.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** Ohta, Tomoko, Yuka Tateisi, Hideki Mima and Jun'ichi Tsujii. (2002). GENIA Corpus: an Annotated Research Abstract Corpus in Molecular Biology Domain. In the Proceedings of he Human Language Technology Conference (HLT 2002). pp73--77.
 * Updated by JCasGen Sat Mar 07 22:05:57 CET 2015
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/target/jcasgen/typesystem.xml
 * @generated */
public class GENIAConstituent extends PTBConstituent {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(GENIAConstituent.class);
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
  protected GENIAConstituent() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public GENIAConstituent(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public GENIAConstituent(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public GENIAConstituent(JCas jcas, int begin, int end) {
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
  //* Feature: syn

  /** getter for syn - gets Marks coordinations, O
   * @generated
   * @return value of the feature 
   */
  public String getSyn() {
    if (GENIAConstituent_Type.featOkTst && ((GENIAConstituent_Type)jcasType).casFeat_syn == null)
      jcasType.jcas.throwFeatMissing("syn", "de.julielab.jules.types.GENIAConstituent");
    return jcasType.ll_cas.ll_getStringValue(addr, ((GENIAConstituent_Type)jcasType).casFeatCode_syn);}
    
  /** setter for syn - sets Marks coordinations, O 
   * @generated
   * @param v value to set into the feature 
   */
  public void setSyn(String v) {
    if (GENIAConstituent_Type.featOkTst && ((GENIAConstituent_Type)jcasType).casFeat_syn == null)
      jcasType.jcas.throwFeatMissing("syn", "de.julielab.jules.types.GENIAConstituent");
    jcasType.ll_cas.ll_setStringValue(addr, ((GENIAConstituent_Type)jcasType).casFeatCode_syn, v);}    
  }

    