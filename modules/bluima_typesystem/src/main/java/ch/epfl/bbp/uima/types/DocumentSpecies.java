

/* First created by JCasGen Sat Mar 07 22:05:56 CET 2015 */
package ch.epfl.bbp.uima.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** the most probable species of a document
 * Updated by JCasGen Sat Mar 07 22:05:56 CET 2015
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/target/jcasgen/typesystem.xml
 * @generated */
public class DocumentSpecies extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(DocumentSpecies.class);
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
  protected DocumentSpecies() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public DocumentSpecies(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public DocumentSpecies(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public DocumentSpecies(JCas jcas, int begin, int end) {
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
  //* Feature: familyName

  /** getter for familyName - gets e.g. Homo_sapiens, or None
   * @generated
   * @return value of the feature 
   */
  public String getFamilyName() {
    if (DocumentSpecies_Type.featOkTst && ((DocumentSpecies_Type)jcasType).casFeat_familyName == null)
      jcasType.jcas.throwFeatMissing("familyName", "ch.epfl.bbp.uima.types.DocumentSpecies");
    return jcasType.ll_cas.ll_getStringValue(addr, ((DocumentSpecies_Type)jcasType).casFeatCode_familyName);}
    
  /** setter for familyName - sets e.g. Homo_sapiens, or None 
   * @generated
   * @param v value to set into the feature 
   */
  public void setFamilyName(String v) {
    if (DocumentSpecies_Type.featOkTst && ((DocumentSpecies_Type)jcasType).casFeat_familyName == null)
      jcasType.jcas.throwFeatMissing("familyName", "ch.epfl.bbp.uima.types.DocumentSpecies");
    jcasType.ll_cas.ll_setStringValue(addr, ((DocumentSpecies_Type)jcasType).casFeatCode_familyName, v);}    
  }

    