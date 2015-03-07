

/* First created by JCasGen Sat Mar 07 22:05:56 CET 2015 */
package ch.epfl.bbp.uima.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** 
 * Updated by JCasGen Sat Mar 07 22:05:56 CET 2015
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/target/jcasgen/typesystem.xml
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
  /** @generated
   * @return index of the type  
   */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected DocumentBlock() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public DocumentBlock(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public DocumentBlock(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
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

    