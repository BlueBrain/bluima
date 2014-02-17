

/* First created by JCasGen Wed Oct 19 19:10:28 CEST 2011 */
package de.julielab.jules.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** The super-type for the constituent annotation, see subtypes
 * Updated by JCasGen Mon Feb 17 22:12:57 CET 2014
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/src/main/resources/typeSystem/bbp-types.xml
 * @generated */
public class Constituent extends Annotation {
  /** @generated
   * @ordered 
   */
  public final static int typeIndexID = JCasRegistry.register(Constituent.class);
  /** @generated
   * @ordered 
   */
  public final static int type = typeIndexID;
  /** @generated  */
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected Constituent() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public Constituent(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public Constituent(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public Constituent(JCas jcas, int begin, int end) {
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
  //* Feature: parent

  /** getter for parent - gets The parent in a constituency tree, C
   * @generated */
  public Constituent getParent() {
    if (Constituent_Type.featOkTst && ((Constituent_Type)jcasType).casFeat_parent == null)
      jcasType.jcas.throwFeatMissing("parent", "de.julielab.jules.types.Constituent");
    return (Constituent)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Constituent_Type)jcasType).casFeatCode_parent)));}
    
  /** setter for parent - sets The parent in a constituency tree, C 
   * @generated */
  public void setParent(Constituent v) {
    if (Constituent_Type.featOkTst && ((Constituent_Type)jcasType).casFeat_parent == null)
      jcasType.jcas.throwFeatMissing("parent", "de.julielab.jules.types.Constituent");
    jcasType.ll_cas.ll_setRefValue(addr, ((Constituent_Type)jcasType).casFeatCode_parent, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: head

  /** getter for head - gets The head can be extracted with head rules, O
   * @generated */
  public Token getHead() {
    if (Constituent_Type.featOkTst && ((Constituent_Type)jcasType).casFeat_head == null)
      jcasType.jcas.throwFeatMissing("head", "de.julielab.jules.types.Constituent");
    return (Token)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Constituent_Type)jcasType).casFeatCode_head)));}
    
  /** setter for head - sets The head can be extracted with head rules, O 
   * @generated */
  public void setHead(Token v) {
    if (Constituent_Type.featOkTst && ((Constituent_Type)jcasType).casFeat_head == null)
      jcasType.jcas.throwFeatMissing("head", "de.julielab.jules.types.Constituent");
    jcasType.ll_cas.ll_setRefValue(addr, ((Constituent_Type)jcasType).casFeatCode_head, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: cat

  /** getter for cat - gets The category of the constituent (e.g. NP, VP)
   * @generated */
  public String getCat() {
    if (Constituent_Type.featOkTst && ((Constituent_Type)jcasType).casFeat_cat == null)
      jcasType.jcas.throwFeatMissing("cat", "de.julielab.jules.types.Constituent");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Constituent_Type)jcasType).casFeatCode_cat);}
    
  /** setter for cat - sets The category of the constituent (e.g. NP, VP) 
   * @generated */
  public void setCat(String v) {
    if (Constituent_Type.featOkTst && ((Constituent_Type)jcasType).casFeat_cat == null)
      jcasType.jcas.throwFeatMissing("cat", "de.julielab.jules.types.Constituent");
    jcasType.ll_cas.ll_setStringValue(addr, ((Constituent_Type)jcasType).casFeatCode_cat, v);}    
  }

    