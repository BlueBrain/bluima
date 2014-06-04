

/* First created by JCasGen Mon Feb 17 12:26:53 CET 2014 */
package ch.epfl.bbp.uima.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** 
 * Updated by JCasGen Wed Jun 04 18:01:55 CEST 2014
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/src/main/resources/typeSystem/bbp-types.xml
 * @generated */
public class DocumentBlock extends DocumentElement {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(DocumentBlock.class);
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
  protected DocumentBlock() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public DocumentBlock(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public DocumentBlock(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public DocumentBlock(JCas jcas, int begin, int end) {
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
  //* Feature: hasBold

  /** getter for hasBold - gets 
   * @generated
   * @return value of the feature 
   */
  public boolean getHasBold() {
    if (DocumentBlock_Type.featOkTst && ((DocumentBlock_Type)jcasType).casFeat_hasBold == null)
      jcasType.jcas.throwFeatMissing("hasBold", "ch.epfl.bbp.uima.types.DocumentBlock");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((DocumentBlock_Type)jcasType).casFeatCode_hasBold);}
    
  /** setter for hasBold - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setHasBold(boolean v) {
    if (DocumentBlock_Type.featOkTst && ((DocumentBlock_Type)jcasType).casFeat_hasBold == null)
      jcasType.jcas.throwFeatMissing("hasBold", "ch.epfl.bbp.uima.types.DocumentBlock");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((DocumentBlock_Type)jcasType).casFeatCode_hasBold, v);}    
   
    
  //*--------------*
  //* Feature: hasManyFontsizes

  /** getter for hasManyFontsizes - gets 
   * @generated
   * @return value of the feature 
   */
  public boolean getHasManyFontsizes() {
    if (DocumentBlock_Type.featOkTst && ((DocumentBlock_Type)jcasType).casFeat_hasManyFontsizes == null)
      jcasType.jcas.throwFeatMissing("hasManyFontsizes", "ch.epfl.bbp.uima.types.DocumentBlock");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((DocumentBlock_Type)jcasType).casFeatCode_hasManyFontsizes);}
    
  /** setter for hasManyFontsizes - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setHasManyFontsizes(boolean v) {
    if (DocumentBlock_Type.featOkTst && ((DocumentBlock_Type)jcasType).casFeat_hasManyFontsizes == null)
      jcasType.jcas.throwFeatMissing("hasManyFontsizes", "ch.epfl.bbp.uima.types.DocumentBlock");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((DocumentBlock_Type)jcasType).casFeatCode_hasManyFontsizes, v);}    
  }

    