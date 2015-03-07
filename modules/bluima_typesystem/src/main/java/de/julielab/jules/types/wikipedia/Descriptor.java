

/* First created by JCasGen Sat Mar 07 22:05:57 CET 2015 */
package de.julielab.jules.types.wikipedia;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.FSArray;
import org.apache.uima.jcas.cas.StringArray;


/** Descriptor for Wikipedia pages. It covers a list of Wikipedia categories associated with the page, a list of incoming and outgoing links, and a list of associated redirects.
 * Updated by JCasGen Sat Mar 07 22:05:57 CET 2015
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/target/jcasgen/typesystem.xml
 * @generated */
public class Descriptor extends de.julielab.jules.types.Descriptor {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Descriptor.class);
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
  protected Descriptor() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Descriptor(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Descriptor(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Descriptor(JCas jcas, int begin, int end) {
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
  //* Feature: categories

  /** getter for categories - gets List of Wikipedia categories associated with a Wikipedia page.
   * @generated
   * @return value of the feature 
   */
  public FSArray getCategories() {
    if (Descriptor_Type.featOkTst && ((Descriptor_Type)jcasType).casFeat_categories == null)
      jcasType.jcas.throwFeatMissing("categories", "de.julielab.jules.types.wikipedia.Descriptor");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Descriptor_Type)jcasType).casFeatCode_categories)));}
    
  /** setter for categories - sets List of Wikipedia categories associated with a Wikipedia page. 
   * @generated
   * @param v value to set into the feature 
   */
  public void setCategories(FSArray v) {
    if (Descriptor_Type.featOkTst && ((Descriptor_Type)jcasType).casFeat_categories == null)
      jcasType.jcas.throwFeatMissing("categories", "de.julielab.jules.types.wikipedia.Descriptor");
    jcasType.ll_cas.ll_setRefValue(addr, ((Descriptor_Type)jcasType).casFeatCode_categories, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for categories - gets an indexed value - List of Wikipedia categories associated with a Wikipedia page.
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public Title getCategories(int i) {
    if (Descriptor_Type.featOkTst && ((Descriptor_Type)jcasType).casFeat_categories == null)
      jcasType.jcas.throwFeatMissing("categories", "de.julielab.jules.types.wikipedia.Descriptor");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Descriptor_Type)jcasType).casFeatCode_categories), i);
    return (Title)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Descriptor_Type)jcasType).casFeatCode_categories), i)));}

  /** indexed setter for categories - sets an indexed value - List of Wikipedia categories associated with a Wikipedia page.
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setCategories(int i, Title v) { 
    if (Descriptor_Type.featOkTst && ((Descriptor_Type)jcasType).casFeat_categories == null)
      jcasType.jcas.throwFeatMissing("categories", "de.julielab.jules.types.wikipedia.Descriptor");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Descriptor_Type)jcasType).casFeatCode_categories), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Descriptor_Type)jcasType).casFeatCode_categories), i, jcasType.ll_cas.ll_getFSRef(v));}
   
    
  //*--------------*
  //* Feature: incomingLinks

  /** getter for incomingLinks - gets List of incoming links (from other Wikipedia pages) associated with a Wikipedia page.
   * @generated
   * @return value of the feature 
   */
  public FSArray getIncomingLinks() {
    if (Descriptor_Type.featOkTst && ((Descriptor_Type)jcasType).casFeat_incomingLinks == null)
      jcasType.jcas.throwFeatMissing("incomingLinks", "de.julielab.jules.types.wikipedia.Descriptor");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Descriptor_Type)jcasType).casFeatCode_incomingLinks)));}
    
  /** setter for incomingLinks - sets List of incoming links (from other Wikipedia pages) associated with a Wikipedia page. 
   * @generated
   * @param v value to set into the feature 
   */
  public void setIncomingLinks(FSArray v) {
    if (Descriptor_Type.featOkTst && ((Descriptor_Type)jcasType).casFeat_incomingLinks == null)
      jcasType.jcas.throwFeatMissing("incomingLinks", "de.julielab.jules.types.wikipedia.Descriptor");
    jcasType.ll_cas.ll_setRefValue(addr, ((Descriptor_Type)jcasType).casFeatCode_incomingLinks, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for incomingLinks - gets an indexed value - List of incoming links (from other Wikipedia pages) associated with a Wikipedia page.
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public Title getIncomingLinks(int i) {
    if (Descriptor_Type.featOkTst && ((Descriptor_Type)jcasType).casFeat_incomingLinks == null)
      jcasType.jcas.throwFeatMissing("incomingLinks", "de.julielab.jules.types.wikipedia.Descriptor");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Descriptor_Type)jcasType).casFeatCode_incomingLinks), i);
    return (Title)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Descriptor_Type)jcasType).casFeatCode_incomingLinks), i)));}

  /** indexed setter for incomingLinks - sets an indexed value - List of incoming links (from other Wikipedia pages) associated with a Wikipedia page.
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setIncomingLinks(int i, Title v) { 
    if (Descriptor_Type.featOkTst && ((Descriptor_Type)jcasType).casFeat_incomingLinks == null)
      jcasType.jcas.throwFeatMissing("incomingLinks", "de.julielab.jules.types.wikipedia.Descriptor");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Descriptor_Type)jcasType).casFeatCode_incomingLinks), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Descriptor_Type)jcasType).casFeatCode_incomingLinks), i, jcasType.ll_cas.ll_getFSRef(v));}
   
    
  //*--------------*
  //* Feature: outgoingLinks

  /** getter for outgoingLinks - gets List of outgoing links pointing to other Wikipedia pages starting at a Wikipedia page.
   * @generated
   * @return value of the feature 
   */
  public FSArray getOutgoingLinks() {
    if (Descriptor_Type.featOkTst && ((Descriptor_Type)jcasType).casFeat_outgoingLinks == null)
      jcasType.jcas.throwFeatMissing("outgoingLinks", "de.julielab.jules.types.wikipedia.Descriptor");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Descriptor_Type)jcasType).casFeatCode_outgoingLinks)));}
    
  /** setter for outgoingLinks - sets List of outgoing links pointing to other Wikipedia pages starting at a Wikipedia page. 
   * @generated
   * @param v value to set into the feature 
   */
  public void setOutgoingLinks(FSArray v) {
    if (Descriptor_Type.featOkTst && ((Descriptor_Type)jcasType).casFeat_outgoingLinks == null)
      jcasType.jcas.throwFeatMissing("outgoingLinks", "de.julielab.jules.types.wikipedia.Descriptor");
    jcasType.ll_cas.ll_setRefValue(addr, ((Descriptor_Type)jcasType).casFeatCode_outgoingLinks, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for outgoingLinks - gets an indexed value - List of outgoing links pointing to other Wikipedia pages starting at a Wikipedia page.
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public Title getOutgoingLinks(int i) {
    if (Descriptor_Type.featOkTst && ((Descriptor_Type)jcasType).casFeat_outgoingLinks == null)
      jcasType.jcas.throwFeatMissing("outgoingLinks", "de.julielab.jules.types.wikipedia.Descriptor");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Descriptor_Type)jcasType).casFeatCode_outgoingLinks), i);
    return (Title)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Descriptor_Type)jcasType).casFeatCode_outgoingLinks), i)));}

  /** indexed setter for outgoingLinks - sets an indexed value - List of outgoing links pointing to other Wikipedia pages starting at a Wikipedia page.
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setOutgoingLinks(int i, Title v) { 
    if (Descriptor_Type.featOkTst && ((Descriptor_Type)jcasType).casFeat_outgoingLinks == null)
      jcasType.jcas.throwFeatMissing("outgoingLinks", "de.julielab.jules.types.wikipedia.Descriptor");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Descriptor_Type)jcasType).casFeatCode_outgoingLinks), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Descriptor_Type)jcasType).casFeatCode_outgoingLinks), i, jcasType.ll_cas.ll_getFSRef(v));}
   
    
  //*--------------*
  //* Feature: redirects

  /** getter for redirects - gets List of redirects pointing to a Wikipedia page.
   * @generated
   * @return value of the feature 
   */
  public StringArray getRedirects() {
    if (Descriptor_Type.featOkTst && ((Descriptor_Type)jcasType).casFeat_redirects == null)
      jcasType.jcas.throwFeatMissing("redirects", "de.julielab.jules.types.wikipedia.Descriptor");
    return (StringArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Descriptor_Type)jcasType).casFeatCode_redirects)));}
    
  /** setter for redirects - sets List of redirects pointing to a Wikipedia page. 
   * @generated
   * @param v value to set into the feature 
   */
  public void setRedirects(StringArray v) {
    if (Descriptor_Type.featOkTst && ((Descriptor_Type)jcasType).casFeat_redirects == null)
      jcasType.jcas.throwFeatMissing("redirects", "de.julielab.jules.types.wikipedia.Descriptor");
    jcasType.ll_cas.ll_setRefValue(addr, ((Descriptor_Type)jcasType).casFeatCode_redirects, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for redirects - gets an indexed value - List of redirects pointing to a Wikipedia page.
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public String getRedirects(int i) {
    if (Descriptor_Type.featOkTst && ((Descriptor_Type)jcasType).casFeat_redirects == null)
      jcasType.jcas.throwFeatMissing("redirects", "de.julielab.jules.types.wikipedia.Descriptor");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Descriptor_Type)jcasType).casFeatCode_redirects), i);
    return jcasType.ll_cas.ll_getStringArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Descriptor_Type)jcasType).casFeatCode_redirects), i);}

  /** indexed setter for redirects - sets an indexed value - List of redirects pointing to a Wikipedia page.
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setRedirects(int i, String v) { 
    if (Descriptor_Type.featOkTst && ((Descriptor_Type)jcasType).casFeat_redirects == null)
      jcasType.jcas.throwFeatMissing("redirects", "de.julielab.jules.types.wikipedia.Descriptor");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Descriptor_Type)jcasType).casFeatCode_redirects), i);
    jcasType.ll_cas.ll_setStringArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Descriptor_Type)jcasType).casFeatCode_redirects), i, v);}
  }

    