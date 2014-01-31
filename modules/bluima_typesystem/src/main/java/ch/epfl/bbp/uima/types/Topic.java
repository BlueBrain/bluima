

/* First created by JCasGen Mon Dec 10 13:40:31 CET 2012 */
package ch.epfl.bbp.uima.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import de.julielab.jules.types.Annotation;
import org.apache.uima.jcas.cas.DoubleArray;


/** The scores of topics from a LDA analysis on a token
 * Updated by JCasGen Mon Oct 21 13:03:29 CEST 2013
 * XML source: /Users/richarde/dev/bluebrain/svn_nlp/UIMA/blue_uima/trunk/modules/julielab_typesystem-2.6.8/src/main/resources/typeSystem/bbp-semantics-biology-types.xml
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
  /** @generated  */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected Topic() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public Topic(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public Topic(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public Topic(JCas jcas, int begin, int end) {
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
  //* Feature: scores

  /** getter for scores - gets 
   * @generated */
  public DoubleArray getScores() {
    if (Topic_Type.featOkTst && ((Topic_Type)jcasType).casFeat_scores == null)
      jcasType.jcas.throwFeatMissing("scores", "ch.epfl.bbp.uima.types.Topic");
    return (DoubleArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Topic_Type)jcasType).casFeatCode_scores)));}
    
  /** setter for scores - sets  
   * @generated */
  public void setScores(DoubleArray v) {
    if (Topic_Type.featOkTst && ((Topic_Type)jcasType).casFeat_scores == null)
      jcasType.jcas.throwFeatMissing("scores", "ch.epfl.bbp.uima.types.Topic");
    jcasType.ll_cas.ll_setRefValue(addr, ((Topic_Type)jcasType).casFeatCode_scores, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for scores - gets an indexed value - 
   * @generated */
  public double getScores(int i) {
    if (Topic_Type.featOkTst && ((Topic_Type)jcasType).casFeat_scores == null)
      jcasType.jcas.throwFeatMissing("scores", "ch.epfl.bbp.uima.types.Topic");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Topic_Type)jcasType).casFeatCode_scores), i);
    return jcasType.ll_cas.ll_getDoubleArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Topic_Type)jcasType).casFeatCode_scores), i);}

  /** indexed setter for scores - sets an indexed value - 
   * @generated */
  public void setScores(int i, double v) { 
    if (Topic_Type.featOkTst && ((Topic_Type)jcasType).casFeat_scores == null)
      jcasType.jcas.throwFeatMissing("scores", "ch.epfl.bbp.uima.types.Topic");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Topic_Type)jcasType).casFeatCode_scores), i);
    jcasType.ll_cas.ll_setDoubleArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Topic_Type)jcasType).casFeatCode_scores), i, v);}
   
    
  //*--------------*
  //* Feature: mostLikelyTopic

  /** getter for mostLikelyTopic - gets 
   * @generated */
  public int getMostLikelyTopic() {
    if (Topic_Type.featOkTst && ((Topic_Type)jcasType).casFeat_mostLikelyTopic == null)
      jcasType.jcas.throwFeatMissing("mostLikelyTopic", "ch.epfl.bbp.uima.types.Topic");
    return jcasType.ll_cas.ll_getIntValue(addr, ((Topic_Type)jcasType).casFeatCode_mostLikelyTopic);}
    
  /** setter for mostLikelyTopic - sets  
   * @generated */
  public void setMostLikelyTopic(int v) {
    if (Topic_Type.featOkTst && ((Topic_Type)jcasType).casFeat_mostLikelyTopic == null)
      jcasType.jcas.throwFeatMissing("mostLikelyTopic", "ch.epfl.bbp.uima.types.Topic");
    jcasType.ll_cas.ll_setIntValue(addr, ((Topic_Type)jcasType).casFeatCode_mostLikelyTopic, v);}    
  }

    