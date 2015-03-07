
/* First created by JCasGen Sat Mar 07 22:05:56 CET 2015 */
package ch.epfl.bbp.uima.genia;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** See
        http://2011.bionlp-st.org/home/genia-event-extraction-genia
 * Updated by JCasGen Sat Mar 07 22:05:56 CET 2015
 * @generated */
public class Event_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Event_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Event_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Event(addr, Event_Type.this);
  			   Event_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Event(addr, Event_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Event.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("ch.epfl.bbp.uima.genia.Event");
 
  /** @generated */
  final Feature casFeat_event_type;
  /** @generated */
  final int     casFeatCode_event_type;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getEvent_type(int addr) {
        if (featOkTst && casFeat_event_type == null)
      jcas.throwFeatMissing("event_type", "ch.epfl.bbp.uima.genia.Event");
    return ll_cas.ll_getStringValue(addr, casFeatCode_event_type);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setEvent_type(int addr, String v) {
        if (featOkTst && casFeat_event_type == null)
      jcas.throwFeatMissing("event_type", "ch.epfl.bbp.uima.genia.Event");
    ll_cas.ll_setStringValue(addr, casFeatCode_event_type, v);}
    
  
 
  /** @generated */
  final Feature casFeat_themes_protein;
  /** @generated */
  final int     casFeatCode_themes_protein;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getThemes_protein(int addr) {
        if (featOkTst && casFeat_themes_protein == null)
      jcas.throwFeatMissing("themes_protein", "ch.epfl.bbp.uima.genia.Event");
    return ll_cas.ll_getRefValue(addr, casFeatCode_themes_protein);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setThemes_protein(int addr, int v) {
        if (featOkTst && casFeat_themes_protein == null)
      jcas.throwFeatMissing("themes_protein", "ch.epfl.bbp.uima.genia.Event");
    ll_cas.ll_setRefValue(addr, casFeatCode_themes_protein, v);}
    
   /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @return value at index i in the array 
   */
  public int getThemes_protein(int addr, int i) {
        if (featOkTst && casFeat_themes_protein == null)
      jcas.throwFeatMissing("themes_protein", "ch.epfl.bbp.uima.genia.Event");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_themes_protein), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_themes_protein), i);
	return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_themes_protein), i);
  }
   
  /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @param v value to set
   */ 
  public void setThemes_protein(int addr, int i, int v) {
        if (featOkTst && casFeat_themes_protein == null)
      jcas.throwFeatMissing("themes_protein", "ch.epfl.bbp.uima.genia.Event");
    if (lowLevelTypeChecks)
      ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_themes_protein), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_themes_protein), i);
    ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_themes_protein), i, v);
  }
 
 
  /** @generated */
  final Feature casFeat_causes_protein;
  /** @generated */
  final int     casFeatCode_causes_protein;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getCauses_protein(int addr) {
        if (featOkTst && casFeat_causes_protein == null)
      jcas.throwFeatMissing("causes_protein", "ch.epfl.bbp.uima.genia.Event");
    return ll_cas.ll_getRefValue(addr, casFeatCode_causes_protein);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setCauses_protein(int addr, int v) {
        if (featOkTst && casFeat_causes_protein == null)
      jcas.throwFeatMissing("causes_protein", "ch.epfl.bbp.uima.genia.Event");
    ll_cas.ll_setRefValue(addr, casFeatCode_causes_protein, v);}
    
   /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @return value at index i in the array 
   */
  public int getCauses_protein(int addr, int i) {
        if (featOkTst && casFeat_causes_protein == null)
      jcas.throwFeatMissing("causes_protein", "ch.epfl.bbp.uima.genia.Event");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_causes_protein), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_causes_protein), i);
	return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_causes_protein), i);
  }
   
  /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @param v value to set
   */ 
  public void setCauses_protein(int addr, int i, int v) {
        if (featOkTst && casFeat_causes_protein == null)
      jcas.throwFeatMissing("causes_protein", "ch.epfl.bbp.uima.genia.Event");
    if (lowLevelTypeChecks)
      ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_causes_protein), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_causes_protein), i);
    ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_causes_protein), i, v);
  }
 
 
  /** @generated */
  final Feature casFeat_site;
  /** @generated */
  final int     casFeatCode_site;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getSite(int addr) {
        if (featOkTst && casFeat_site == null)
      jcas.throwFeatMissing("site", "ch.epfl.bbp.uima.genia.Event");
    return ll_cas.ll_getStringValue(addr, casFeatCode_site);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setSite(int addr, String v) {
        if (featOkTst && casFeat_site == null)
      jcas.throwFeatMissing("site", "ch.epfl.bbp.uima.genia.Event");
    ll_cas.ll_setStringValue(addr, casFeatCode_site, v);}
    
  
 
  /** @generated */
  final Feature casFeat_id;
  /** @generated */
  final int     casFeatCode_id;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getId(int addr) {
        if (featOkTst && casFeat_id == null)
      jcas.throwFeatMissing("id", "ch.epfl.bbp.uima.genia.Event");
    return ll_cas.ll_getStringValue(addr, casFeatCode_id);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setId(int addr, String v) {
        if (featOkTst && casFeat_id == null)
      jcas.throwFeatMissing("id", "ch.epfl.bbp.uima.genia.Event");
    ll_cas.ll_setStringValue(addr, casFeatCode_id, v);}
    
  
 
  /** @generated */
  final Feature casFeat_themes_event;
  /** @generated */
  final int     casFeatCode_themes_event;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getThemes_event(int addr) {
        if (featOkTst && casFeat_themes_event == null)
      jcas.throwFeatMissing("themes_event", "ch.epfl.bbp.uima.genia.Event");
    return ll_cas.ll_getRefValue(addr, casFeatCode_themes_event);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setThemes_event(int addr, int v) {
        if (featOkTst && casFeat_themes_event == null)
      jcas.throwFeatMissing("themes_event", "ch.epfl.bbp.uima.genia.Event");
    ll_cas.ll_setRefValue(addr, casFeatCode_themes_event, v);}
    
   /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @return value at index i in the array 
   */
  public int getThemes_event(int addr, int i) {
        if (featOkTst && casFeat_themes_event == null)
      jcas.throwFeatMissing("themes_event", "ch.epfl.bbp.uima.genia.Event");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_themes_event), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_themes_event), i);
	return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_themes_event), i);
  }
   
  /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @param v value to set
   */ 
  public void setThemes_event(int addr, int i, int v) {
        if (featOkTst && casFeat_themes_event == null)
      jcas.throwFeatMissing("themes_event", "ch.epfl.bbp.uima.genia.Event");
    if (lowLevelTypeChecks)
      ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_themes_event), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_themes_event), i);
    ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_themes_event), i, v);
  }
 
 
  /** @generated */
  final Feature casFeat_causes_event;
  /** @generated */
  final int     casFeatCode_causes_event;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getCauses_event(int addr) {
        if (featOkTst && casFeat_causes_event == null)
      jcas.throwFeatMissing("causes_event", "ch.epfl.bbp.uima.genia.Event");
    return ll_cas.ll_getRefValue(addr, casFeatCode_causes_event);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setCauses_event(int addr, int v) {
        if (featOkTst && casFeat_causes_event == null)
      jcas.throwFeatMissing("causes_event", "ch.epfl.bbp.uima.genia.Event");
    ll_cas.ll_setRefValue(addr, casFeatCode_causes_event, v);}
    
   /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @return value at index i in the array 
   */
  public int getCauses_event(int addr, int i) {
        if (featOkTst && casFeat_causes_event == null)
      jcas.throwFeatMissing("causes_event", "ch.epfl.bbp.uima.genia.Event");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_causes_event), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_causes_event), i);
	return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_causes_event), i);
  }
   
  /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @param v value to set
   */ 
  public void setCauses_event(int addr, int i, int v) {
        if (featOkTst && casFeat_causes_event == null)
      jcas.throwFeatMissing("causes_event", "ch.epfl.bbp.uima.genia.Event");
    if (lowLevelTypeChecks)
      ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_causes_event), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_causes_event), i);
    ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_causes_event), i, v);
  }
 



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public Event_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_event_type = jcas.getRequiredFeatureDE(casType, "event_type", "uima.cas.String", featOkTst);
    casFeatCode_event_type  = (null == casFeat_event_type) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_event_type).getCode();

 
    casFeat_themes_protein = jcas.getRequiredFeatureDE(casType, "themes_protein", "uima.cas.FSArray", featOkTst);
    casFeatCode_themes_protein  = (null == casFeat_themes_protein) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_themes_protein).getCode();

 
    casFeat_causes_protein = jcas.getRequiredFeatureDE(casType, "causes_protein", "uima.cas.FSArray", featOkTst);
    casFeatCode_causes_protein  = (null == casFeat_causes_protein) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_causes_protein).getCode();

 
    casFeat_site = jcas.getRequiredFeatureDE(casType, "site", "uima.cas.String", featOkTst);
    casFeatCode_site  = (null == casFeat_site) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_site).getCode();

 
    casFeat_id = jcas.getRequiredFeatureDE(casType, "id", "uima.cas.String", featOkTst);
    casFeatCode_id  = (null == casFeat_id) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_id).getCode();

 
    casFeat_themes_event = jcas.getRequiredFeatureDE(casType, "themes_event", "uima.cas.FSArray", featOkTst);
    casFeatCode_themes_event  = (null == casFeat_themes_event) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_themes_event).getCode();

 
    casFeat_causes_event = jcas.getRequiredFeatureDE(casType, "causes_event", "uima.cas.FSArray", featOkTst);
    casFeatCode_causes_event  = (null == casFeat_causes_event) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_causes_event).getCode();

  }
}



    