

/* First created by JCasGen Fri Sep 14 13:58:44 CEST 2012 */
package ch.epfl.bbp.uima.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** 
 * Updated by JCasGen Fri Sep 20 01:02:03 CEST 2013
 * XML source: /Users/richarde/dev/bluebrain/svn_nlp/UIMA/blue_uima/trunk/modules/julielab_typesystem-2.6.8/src/main/resources/typeSystem/bbp-basic-types.xml
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

  /** <!-- begin-user-doc -->
    * Write your own initialization here
    * <!-- end-user-doc -->
  @generated modifiable */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: hasBold

  /** getter for hasBold - gets 
   * @generated */
  public boolean getHasBold() {
    if (DocumentBlock_Type.featOkTst && ((DocumentBlock_Type)jcasType).casFeat_hasBold == null)
      jcasType.jcas.throwFeatMissing("hasBold", "ch.epfl.bbp.uima.types.DocumentBlock");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((DocumentBlock_Type)jcasType).casFeatCode_hasBold);}
    
  /** setter for hasBold - sets  
   * @generated */
  public void setHasBold(boolean v) {
    if (DocumentBlock_Type.featOkTst && ((DocumentBlock_Type)jcasType).casFeat_hasBold == null)
      jcasType.jcas.throwFeatMissing("hasBold", "ch.epfl.bbp.uima.types.DocumentBlock");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((DocumentBlock_Type)jcasType).casFeatCode_hasBold, v);}    
   
    
  //*--------------*
  //* Feature: hasManyFontsizes

  /** getter for hasManyFontsizes - gets 
   * @generated */
  public boolean getHasManyFontsizes() {
    if (DocumentBlock_Type.featOkTst && ((DocumentBlock_Type)jcasType).casFeat_hasManyFontsizes == null)
      jcasType.jcas.throwFeatMissing("hasManyFontsizes", "ch.epfl.bbp.uima.types.DocumentBlock");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((DocumentBlock_Type)jcasType).casFeatCode_hasManyFontsizes);}
    
  /** setter for hasManyFontsizes - sets  
   * @generated */
  public void setHasManyFontsizes(boolean v) {
    if (DocumentBlock_Type.featOkTst && ((DocumentBlock_Type)jcasType).casFeat_hasManyFontsizes == null)
      jcasType.jcas.throwFeatMissing("hasManyFontsizes", "ch.epfl.bbp.uima.types.DocumentBlock");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((DocumentBlock_Type)jcasType).casFeatCode_hasManyFontsizes, v);}    
  }

    