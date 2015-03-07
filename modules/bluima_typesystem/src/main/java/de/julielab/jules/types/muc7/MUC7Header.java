

/* First created by JCasGen Sat Mar 07 22:05:57 CET 2015 */
package de.julielab.jules.types.muc7;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import de.julielab.jules.types.Header;


/** the MUC7Header has the storyID as additional type
 * Updated by JCasGen Sat Mar 07 22:05:57 CET 2015
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/target/jcasgen/typesystem.xml
 * @generated */
public class MUC7Header extends Header {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(MUC7Header.class);
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
  protected MUC7Header() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public MUC7Header(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public MUC7Header(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public MUC7Header(JCas jcas, int begin, int end) {
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
  //* Feature: storyID

  /** getter for storyID - gets the storyID of the MUC7 documents
   * @generated
   * @return value of the feature 
   */
  public String getStoryID() {
    if (MUC7Header_Type.featOkTst && ((MUC7Header_Type)jcasType).casFeat_storyID == null)
      jcasType.jcas.throwFeatMissing("storyID", "de.julielab.jules.types.muc7.MUC7Header");
    return jcasType.ll_cas.ll_getStringValue(addr, ((MUC7Header_Type)jcasType).casFeatCode_storyID);}
    
  /** setter for storyID - sets the storyID of the MUC7 documents 
   * @generated
   * @param v value to set into the feature 
   */
  public void setStoryID(String v) {
    if (MUC7Header_Type.featOkTst && ((MUC7Header_Type)jcasType).casFeat_storyID == null)
      jcasType.jcas.throwFeatMissing("storyID", "de.julielab.jules.types.muc7.MUC7Header");
    jcasType.ll_cas.ll_setStringValue(addr, ((MUC7Header_Type)jcasType).casFeatCode_storyID, v);}    
  }

    