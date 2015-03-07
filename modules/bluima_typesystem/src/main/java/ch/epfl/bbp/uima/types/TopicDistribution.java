

/* First created by JCasGen Sat Mar 07 22:05:56 CET 2015 */
package ch.epfl.bbp.uima.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.jcas.cas.DoubleArray;


/** The distr of topic from a LDA analysis on that document
 * Updated by JCasGen Sat Mar 07 22:05:56 CET 2015
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/target/jcasgen/typesystem.xml
 * @generated */
public class TopicDistribution extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(TopicDistribution.class);
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
  protected TopicDistribution() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public TopicDistribution(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public TopicDistribution(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public TopicDistribution(JCas jcas, int begin, int end) {
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
  //* Feature: probability

  /** getter for probability - gets 
   * @generated
   * @return value of the feature 
   */
  public DoubleArray getProbability() {
    if (TopicDistribution_Type.featOkTst && ((TopicDistribution_Type)jcasType).casFeat_probability == null)
      jcasType.jcas.throwFeatMissing("probability", "ch.epfl.bbp.uima.types.TopicDistribution");
    return (DoubleArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((TopicDistribution_Type)jcasType).casFeatCode_probability)));}
    
  /** setter for probability - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setProbability(DoubleArray v) {
    if (TopicDistribution_Type.featOkTst && ((TopicDistribution_Type)jcasType).casFeat_probability == null)
      jcasType.jcas.throwFeatMissing("probability", "ch.epfl.bbp.uima.types.TopicDistribution");
    jcasType.ll_cas.ll_setRefValue(addr, ((TopicDistribution_Type)jcasType).casFeatCode_probability, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for probability - gets an indexed value - 
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public double getProbability(int i) {
    if (TopicDistribution_Type.featOkTst && ((TopicDistribution_Type)jcasType).casFeat_probability == null)
      jcasType.jcas.throwFeatMissing("probability", "ch.epfl.bbp.uima.types.TopicDistribution");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((TopicDistribution_Type)jcasType).casFeatCode_probability), i);
    return jcasType.ll_cas.ll_getDoubleArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((TopicDistribution_Type)jcasType).casFeatCode_probability), i);}

  /** indexed setter for probability - sets an indexed value - 
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setProbability(int i, double v) { 
    if (TopicDistribution_Type.featOkTst && ((TopicDistribution_Type)jcasType).casFeat_probability == null)
      jcasType.jcas.throwFeatMissing("probability", "ch.epfl.bbp.uima.types.TopicDistribution");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((TopicDistribution_Type)jcasType).casFeatCode_probability), i);
    jcasType.ll_cas.ll_setDoubleArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((TopicDistribution_Type)jcasType).casFeatCode_probability), i, v);}
  }

    