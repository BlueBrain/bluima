

/* First created by JCasGen Sat Mar 07 22:05:57 CET 2015 */
package de.julielab.jules.types.wikipedia;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import de.julielab.jules.types.Annotation;


/** Combined full form and short form of the title of a Wikipedia page (the short form does not include disambiguation strings such as the expression in brackets in 'Ontologies (Informatics)').
 * Updated by JCasGen Sat Mar 07 22:05:57 CET 2015
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/target/jcasgen/typesystem.xml
 * @generated */
public class Title extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Title.class);
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
  protected Title() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Title(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Title(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Title(JCas jcas, int begin, int end) {
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

  /** getter for name - gets Title of a Wikipedia page excluding disambiguation strings (such as the expression in brackets in 'Ontologies (Informatics)').
   * @generated
   * @return value of the feature 
   */
  public String getName() {
    if (Title_Type.featOkTst && ((Title_Type)jcasType).casFeat_name == null)
      jcasType.jcas.throwFeatMissing("name", "de.julielab.jules.types.wikipedia.Title");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Title_Type)jcasType).casFeatCode_name);}
    
  /** setter for name - sets Title of a Wikipedia page excluding disambiguation strings (such as the expression in brackets in 'Ontologies (Informatics)'). 
   * @generated
   * @param v value to set into the feature 
   */
  public void setName(String v) {
    if (Title_Type.featOkTst && ((Title_Type)jcasType).casFeat_name == null)
      jcasType.jcas.throwFeatMissing("name", "de.julielab.jules.types.wikipedia.Title");
    jcasType.ll_cas.ll_setStringValue(addr, ((Title_Type)jcasType).casFeatCode_name, v);}    
   
    
  //*--------------*
  //* Feature: fullname

  /** getter for fullname - gets Full title of a Wikipedia page including disambiguation strings (such as the expression in brackets in 'Ontologies (Informatics)').
   * @generated
   * @return value of the feature 
   */
  public String getFullname() {
    if (Title_Type.featOkTst && ((Title_Type)jcasType).casFeat_fullname == null)
      jcasType.jcas.throwFeatMissing("fullname", "de.julielab.jules.types.wikipedia.Title");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Title_Type)jcasType).casFeatCode_fullname);}
    
  /** setter for fullname - sets Full title of a Wikipedia page including disambiguation strings (such as the expression in brackets in 'Ontologies (Informatics)'). 
   * @generated
   * @param v value to set into the feature 
   */
  public void setFullname(String v) {
    if (Title_Type.featOkTst && ((Title_Type)jcasType).casFeat_fullname == null)
      jcasType.jcas.throwFeatMissing("fullname", "de.julielab.jules.types.wikipedia.Title");
    jcasType.ll_cas.ll_setStringValue(addr, ((Title_Type)jcasType).casFeatCode_fullname, v);}    
  }

    