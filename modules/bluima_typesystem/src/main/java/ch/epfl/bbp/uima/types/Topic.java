

/* First created by JCasGen Sat Mar 07 22:05:56 CET 2015 */
package ch.epfl.bbp.uima.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import de.julielab.jules.types.Annotation;
import org.apache.uima.jcas.cas.DoubleArray;


/** The scores of topics from a LDA analysis on a token
 * Updated by JCasGen Sat Mar 07 22:05:56 CET 2015
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/target/jcasgen/typesystem.xml
 * @generated */
public class Topic extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Topic.class);
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
  protected Topic() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Topic(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Topic(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Topic(JCas jcas, int begin, int end) {
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
  //* Feature: scores

  /** getter for scores - gets 
   * @generated
   * @return value of the feature 
   */
  public DoubleArray getScores() {
    if (Topic_Type.featOkTst && ((Topic_Type)jcasType).casFeat_scores == null)
      jcasType.jcas.throwFeatMissing("scores", "ch.epfl.bbp.uima.types.Topic");
    return (DoubleArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Topic_Type)jcasType).casFeatCode_scores)));}
    
  /** setter for scores - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setScores(DoubleArray v) {
    if (Topic_Type.featOkTst && ((Topic_Type)jcasType).casFeat_scores == null)
      jcasType.jcas.throwFeatMissing("scores", "ch.epfl.bbp.uima.types.Topic");
    jcasType.ll_cas.ll_setRefValue(addr, ((Topic_Type)jcasType).casFeatCode_scores, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for scores - gets an indexed value - 
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public double getScores(int i) {
    if (Topic_Type.featOkTst && ((Topic_Type)jcasType).casFeat_scores == null)
      jcasType.jcas.throwFeatMissing("scores", "ch.epfl.bbp.uima.types.Topic");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Topic_Type)jcasType).casFeatCode_scores), i);
    return jcasType.ll_cas.ll_getDoubleArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Topic_Type)jcasType).casFeatCode_scores), i);}

  /** indexed setter for scores - sets an indexed value - 
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setScores(int i, double v) { 
    if (Topic_Type.featOkTst && ((Topic_Type)jcasType).casFeat_scores == null)
      jcasType.jcas.throwFeatMissing("scores", "ch.epfl.bbp.uima.types.Topic");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Topic_Type)jcasType).casFeatCode_scores), i);
    jcasType.ll_cas.ll_setDoubleArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Topic_Type)jcasType).casFeatCode_scores), i, v);}
   
    
  //*--------------*
  //* Feature: mostLikelyTopic

  /** getter for mostLikelyTopic - gets The most likely topic id for this topic
   * @generated
   * @return value of the feature 
   */
  public int getMostLikelyTopic() {
    if (Topic_Type.featOkTst && ((Topic_Type)jcasType).casFeat_mostLikelyTopic == null)
      jcasType.jcas.throwFeatMissing("mostLikelyTopic", "ch.epfl.bbp.uima.types.Topic");
    return jcasType.ll_cas.ll_getIntValue(addr, ((Topic_Type)jcasType).casFeatCode_mostLikelyTopic);}
    
  /** setter for mostLikelyTopic - sets The most likely topic id for this topic 
   * @generated
   * @param v value to set into the feature 
   */
  public void setMostLikelyTopic(int v) {
    if (Topic_Type.featOkTst && ((Topic_Type)jcasType).casFeat_mostLikelyTopic == null)
      jcasType.jcas.throwFeatMissing("mostLikelyTopic", "ch.epfl.bbp.uima.types.Topic");
    jcasType.ll_cas.ll_setIntValue(addr, ((Topic_Type)jcasType).casFeatCode_mostLikelyTopic, v);}    
  }

    