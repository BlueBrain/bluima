

/* First created by JCasGen Sat Mar 07 22:05:57 CET 2015 */
package de.julielab.jules.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** 
 * Updated by JCasGen Sat Mar 07 22:05:57 CET 2015
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/target/jcasgen/typesystem.xml
 * @generated */
public class Coordination extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Coordination.class);
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
  protected Coordination() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Coordination(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Coordination(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Coordination(JCas jcas, int begin, int end) {
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
  //* Feature: resolved

  /** getter for resolved - gets 
   * @generated
   * @return value of the feature 
   */
  public String getResolved() {
    if (Coordination_Type.featOkTst && ((Coordination_Type)jcasType).casFeat_resolved == null)
      jcasType.jcas.throwFeatMissing("resolved", "de.julielab.jules.types.Coordination");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Coordination_Type)jcasType).casFeatCode_resolved);}
    
  /** setter for resolved - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setResolved(String v) {
    if (Coordination_Type.featOkTst && ((Coordination_Type)jcasType).casFeat_resolved == null)
      jcasType.jcas.throwFeatMissing("resolved", "de.julielab.jules.types.Coordination");
    jcasType.ll_cas.ll_setStringValue(addr, ((Coordination_Type)jcasType).casFeatCode_resolved, v);}    
   
    
  //*--------------*
  //* Feature: elliptical

  /** getter for elliptical - gets 
   * @generated
   * @return value of the feature 
   */
  public boolean getElliptical() {
    if (Coordination_Type.featOkTst && ((Coordination_Type)jcasType).casFeat_elliptical == null)
      jcasType.jcas.throwFeatMissing("elliptical", "de.julielab.jules.types.Coordination");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((Coordination_Type)jcasType).casFeatCode_elliptical);}
    
  /** setter for elliptical - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setElliptical(boolean v) {
    if (Coordination_Type.featOkTst && ((Coordination_Type)jcasType).casFeat_elliptical == null)
      jcasType.jcas.throwFeatMissing("elliptical", "de.julielab.jules.types.Coordination");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((Coordination_Type)jcasType).casFeatCode_elliptical, v);}    
  }

    