

/* First created by JCasGen Sat Mar 07 22:05:57 CET 2015 */
package de.julielab.jules.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** (Named) EntityMention (i.e. An entity is an object or set of objects in the world. Entitiy mentions may be refrenced in a text by their name, indicated by a common noun or noun phrase, or represented by a pronoun) annotation
 * Updated by JCasGen Sat Mar 07 22:05:57 CET 2015
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/target/jcasgen/typesystem.xml
 * @generated */
public class EntityMention extends ConceptMention {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(EntityMention.class);
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
  protected EntityMention() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public EntityMention(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public EntityMention(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public EntityMention(JCas jcas, int begin, int end) {
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
  //* Feature: head

  /** getter for head - gets Head of an entity mention such as Northwest in the Northwest
   * @generated
   * @return value of the feature 
   */
  public Annotation getHead() {
    if (EntityMention_Type.featOkTst && ((EntityMention_Type)jcasType).casFeat_head == null)
      jcasType.jcas.throwFeatMissing("head", "de.julielab.jules.types.EntityMention");
    return (Annotation)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((EntityMention_Type)jcasType).casFeatCode_head)));}
    
  /** setter for head - sets Head of an entity mention such as Northwest in the Northwest 
   * @generated
   * @param v value to set into the feature 
   */
  public void setHead(Annotation v) {
    if (EntityMention_Type.featOkTst && ((EntityMention_Type)jcasType).casFeat_head == null)
      jcasType.jcas.throwFeatMissing("head", "de.julielab.jules.types.EntityMention");
    jcasType.ll_cas.ll_setRefValue(addr, ((EntityMention_Type)jcasType).casFeatCode_head, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: mentionLevel

  /** getter for mentionLevel - gets mention level such as PRO (many), NOM, NAM (see ACE)
   * @generated
   * @return value of the feature 
   */
  public String getMentionLevel() {
    if (EntityMention_Type.featOkTst && ((EntityMention_Type)jcasType).casFeat_mentionLevel == null)
      jcasType.jcas.throwFeatMissing("mentionLevel", "de.julielab.jules.types.EntityMention");
    return jcasType.ll_cas.ll_getStringValue(addr, ((EntityMention_Type)jcasType).casFeatCode_mentionLevel);}
    
  /** setter for mentionLevel - sets mention level such as PRO (many), NOM, NAM (see ACE) 
   * @generated
   * @param v value to set into the feature 
   */
  public void setMentionLevel(String v) {
    if (EntityMention_Type.featOkTst && ((EntityMention_Type)jcasType).casFeat_mentionLevel == null)
      jcasType.jcas.throwFeatMissing("mentionLevel", "de.julielab.jules.types.EntityMention");
    jcasType.ll_cas.ll_setStringValue(addr, ((EntityMention_Type)jcasType).casFeatCode_mentionLevel, v);}    
  }

    