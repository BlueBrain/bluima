

/* First created by JCasGen Sat Mar 07 22:05:57 CET 2015 */
package de.julielab.jules.types.mmax;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** Annotation segment of an MMAX annotation. Helps to represent discontinuous annotations.
 * Updated by JCasGen Sat Mar 07 22:05:57 CET 2015
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/target/jcasgen/typesystem.xml
 * @generated */
public class AnnotationSegment extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(AnnotationSegment.class);
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
  protected AnnotationSegment() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public AnnotationSegment(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public AnnotationSegment(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public AnnotationSegment(JCas jcas, int begin, int end) {
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

  /** getter for id - gets Number of the segment. First segment has number 1.
   * @generated
   * @return value of the feature 
   */
  public int getId() {
    if (AnnotationSegment_Type.featOkTst && ((AnnotationSegment_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "de.julielab.jules.types.mmax.AnnotationSegment");
    return jcasType.ll_cas.ll_getIntValue(addr, ((AnnotationSegment_Type)jcasType).casFeatCode_id);}
    
  /** setter for id - sets Number of the segment. First segment has number 1. 
   * @generated
   * @param v value to set into the feature 
   */
  public void setId(int v) {
    if (AnnotationSegment_Type.featOkTst && ((AnnotationSegment_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "de.julielab.jules.types.mmax.AnnotationSegment");
    jcasType.ll_cas.ll_setIntValue(addr, ((AnnotationSegment_Type)jcasType).casFeatCode_id, v);}    
  }

    