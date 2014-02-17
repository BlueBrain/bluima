

/* First created by JCasGen Wed Oct 19 19:10:28 CEST 2011 */
package de.julielab.jules.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** 
 * Updated by JCasGen Mon Feb 17 22:12:57 CET 2014
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/src/main/resources/typeSystem/bbp-types.xml
 * @generated */
public class Coordination extends Annotation {
  /** @generated
   * @ordered 
   */
  public final static int typeIndexID = JCasRegistry.register(Coordination.class);
  /** @generated
   * @ordered 
   */
  public final static int type = typeIndexID;
  /** @generated  */
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected Coordination() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public Coordination(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public Coordination(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public Coordination(JCas jcas, int begin, int end) {
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
  //* Feature: resolved

  /** getter for resolved - gets 
   * @generated */
  public String getResolved() {
    if (Coordination_Type.featOkTst && ((Coordination_Type)jcasType).casFeat_resolved == null)
      jcasType.jcas.throwFeatMissing("resolved", "de.julielab.jules.types.Coordination");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Coordination_Type)jcasType).casFeatCode_resolved);}
    
  /** setter for resolved - sets  
   * @generated */
  public void setResolved(String v) {
    if (Coordination_Type.featOkTst && ((Coordination_Type)jcasType).casFeat_resolved == null)
      jcasType.jcas.throwFeatMissing("resolved", "de.julielab.jules.types.Coordination");
    jcasType.ll_cas.ll_setStringValue(addr, ((Coordination_Type)jcasType).casFeatCode_resolved, v);}    
   
    
  //*--------------*
  //* Feature: elliptical

  /** getter for elliptical - gets 
   * @generated */
  public boolean getElliptical() {
    if (Coordination_Type.featOkTst && ((Coordination_Type)jcasType).casFeat_elliptical == null)
      jcasType.jcas.throwFeatMissing("elliptical", "de.julielab.jules.types.Coordination");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((Coordination_Type)jcasType).casFeatCode_elliptical);}
    
  /** setter for elliptical - sets  
   * @generated */
  public void setElliptical(boolean v) {
    if (Coordination_Type.featOkTst && ((Coordination_Type)jcasType).casFeat_elliptical == null)
      jcasType.jcas.throwFeatMissing("elliptical", "de.julielab.jules.types.Coordination");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((Coordination_Type)jcasType).casFeatCode_elliptical, v);}    
  }

    