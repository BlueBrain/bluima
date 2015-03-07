

/* First created by JCasGen Sat Mar 07 22:05:57 CET 2015 */
package de.julielab.jules.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** The super-type for the constituent annotation, see subtypes
 * Updated by JCasGen Sat Mar 07 22:05:57 CET 2015
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/target/jcasgen/typesystem.xml
 * @generated */
public class Constituent extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Constituent.class);
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
  protected Constituent() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Constituent(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Constituent(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Constituent(JCas jcas, int begin, int end) {
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
  //* Feature: parent

  /** getter for parent - gets The parent in a constituency tree, C
   * @generated
   * @return value of the feature 
   */
  public Constituent getParent() {
    if (Constituent_Type.featOkTst && ((Constituent_Type)jcasType).casFeat_parent == null)
      jcasType.jcas.throwFeatMissing("parent", "de.julielab.jules.types.Constituent");
    return (Constituent)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Constituent_Type)jcasType).casFeatCode_parent)));}
    
  /** setter for parent - sets The parent in a constituency tree, C 
   * @generated
   * @param v value to set into the feature 
   */
  public void setParent(Constituent v) {
    if (Constituent_Type.featOkTst && ((Constituent_Type)jcasType).casFeat_parent == null)
      jcasType.jcas.throwFeatMissing("parent", "de.julielab.jules.types.Constituent");
    jcasType.ll_cas.ll_setRefValue(addr, ((Constituent_Type)jcasType).casFeatCode_parent, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: head

  /** getter for head - gets The head can be extracted with head rules, O
   * @generated
   * @return value of the feature 
   */
  public Token getHead() {
    if (Constituent_Type.featOkTst && ((Constituent_Type)jcasType).casFeat_head == null)
      jcasType.jcas.throwFeatMissing("head", "de.julielab.jules.types.Constituent");
    return (Token)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Constituent_Type)jcasType).casFeatCode_head)));}
    
  /** setter for head - sets The head can be extracted with head rules, O 
   * @generated
   * @param v value to set into the feature 
   */
  public void setHead(Token v) {
    if (Constituent_Type.featOkTst && ((Constituent_Type)jcasType).casFeat_head == null)
      jcasType.jcas.throwFeatMissing("head", "de.julielab.jules.types.Constituent");
    jcasType.ll_cas.ll_setRefValue(addr, ((Constituent_Type)jcasType).casFeatCode_head, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: cat

  /** getter for cat - gets The category of the constituent (e.g. NP, VP)
   * @generated
   * @return value of the feature 
   */
  public String getCat() {
    if (Constituent_Type.featOkTst && ((Constituent_Type)jcasType).casFeat_cat == null)
      jcasType.jcas.throwFeatMissing("cat", "de.julielab.jules.types.Constituent");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Constituent_Type)jcasType).casFeatCode_cat);}
    
  /** setter for cat - sets The category of the constituent (e.g. NP, VP) 
   * @generated
   * @param v value to set into the feature 
   */
  public void setCat(String v) {
    if (Constituent_Type.featOkTst && ((Constituent_Type)jcasType).casFeat_cat == null)
      jcasType.jcas.throwFeatMissing("cat", "de.julielab.jules.types.Constituent");
    jcasType.ll_cas.ll_setStringValue(addr, ((Constituent_Type)jcasType).casFeatCode_cat, v);}    
  }

    