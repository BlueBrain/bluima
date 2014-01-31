

/* First created by JCasGen Wed Dec 18 15:26:51 CET 2013 */
package ch.epfl.bbp.uima.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** the most probable species of a document
 * Updated by JCasGen Wed Dec 18 15:26:51 CET 2013
 * XML source: /Users/richarde/dev/bluebrain/svn_nlp/UIMA/blue_uima/trunk/modules/julielab_typesystem-2.6.8/src/main/resources/typeSystem/LinnaeusAnnotationTypes.xml
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
  /** @generated  */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected DocumentSpecies() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public DocumentSpecies(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public DocumentSpecies(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public DocumentSpecies(JCas jcas, int begin, int end) {
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
  //* Feature: familyName

  /** getter for familyName - gets e.g. Homo_sapiens, or None
   * @generated */
  public String getFamilyName() {
    if (DocumentSpecies_Type.featOkTst && ((DocumentSpecies_Type)jcasType).casFeat_familyName == null)
      jcasType.jcas.throwFeatMissing("familyName", "ch.epfl.bbp.uima.types.DocumentSpecies");
    return jcasType.ll_cas.ll_getStringValue(addr, ((DocumentSpecies_Type)jcasType).casFeatCode_familyName);}
    
  /** setter for familyName - sets e.g. Homo_sapiens, or None 
   * @generated */
  public void setFamilyName(String v) {
    if (DocumentSpecies_Type.featOkTst && ((DocumentSpecies_Type)jcasType).casFeat_familyName == null)
      jcasType.jcas.throwFeatMissing("familyName", "ch.epfl.bbp.uima.types.DocumentSpecies");
    jcasType.ll_cas.ll_setStringValue(addr, ((DocumentSpecies_Type)jcasType).casFeatCode_familyName, v);}    
  }

    