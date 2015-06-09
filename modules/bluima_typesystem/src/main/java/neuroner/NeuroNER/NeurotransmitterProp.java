

/* First created by JCasGen Tue May 13 16:25:02 CEST 2014 */
package neuroner.NeuroNER;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** Type defined in neuroner.NeuroNER
 * Updated by JCasGen Tue Jun 09 19:55:09 CEST 2015
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/src/main/resources/typeSystem/ruta/neuroNER/NeuroNERTypeSystem.xml
 * @generated */
public class NeurotransmitterProp extends NeuronProperty {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(NeurotransmitterProp.class);
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
  protected NeurotransmitterProp() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public NeurotransmitterProp(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public NeurotransmitterProp(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public NeurotransmitterProp(JCas jcas, int begin, int end) {
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
  //* Feature: name

  /** getter for name - gets name
   * @generated
   * @return value of the feature 
   */
  public String getName() {
    if (NeurotransmitterProp_Type.featOkTst && ((NeurotransmitterProp_Type)jcasType).casFeat_name == null)
      jcasType.jcas.throwFeatMissing("name", "neuroner.NeuroNER.NeurotransmitterProp");
    return jcasType.ll_cas.ll_getStringValue(addr, ((NeurotransmitterProp_Type)jcasType).casFeatCode_name);}
    
  /** setter for name - sets name 
   * @generated
   * @param v value to set into the feature 
   */
  public void setName(String v) {
    if (NeurotransmitterProp_Type.featOkTst && ((NeurotransmitterProp_Type)jcasType).casFeat_name == null)
      jcasType.jcas.throwFeatMissing("name", "neuroner.NeuroNER.NeurotransmitterProp");
    jcasType.ll_cas.ll_setStringValue(addr, ((NeurotransmitterProp_Type)jcasType).casFeatCode_name, v);}    
   
    
  //*--------------*
  //* Feature: ontologyId

  /** getter for ontologyId - gets ontologyId
   * @generated
   * @return value of the feature 
   */
  public String getOntologyId() {
    if (NeurotransmitterProp_Type.featOkTst && ((NeurotransmitterProp_Type)jcasType).casFeat_ontologyId == null)
      jcasType.jcas.throwFeatMissing("ontologyId", "neuroner.NeuroNER.NeurotransmitterProp");
    return jcasType.ll_cas.ll_getStringValue(addr, ((NeurotransmitterProp_Type)jcasType).casFeatCode_ontologyId);}
    
  /** setter for ontologyId - sets ontologyId 
   * @generated
   * @param v value to set into the feature 
   */
  public void setOntologyId(String v) {
    if (NeurotransmitterProp_Type.featOkTst && ((NeurotransmitterProp_Type)jcasType).casFeat_ontologyId == null)
      jcasType.jcas.throwFeatMissing("ontologyId", "neuroner.NeuroNER.NeurotransmitterProp");
    jcasType.ll_cas.ll_setStringValue(addr, ((NeurotransmitterProp_Type)jcasType).casFeatCode_ontologyId, v);}    
  }

    