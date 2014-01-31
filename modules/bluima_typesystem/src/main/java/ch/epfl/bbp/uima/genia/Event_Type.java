
/* First created by JCasGen Fri Dec 23 13:41:29 CET 2011 */
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
        http://2011.bionlp-st.org/home/genia-event-extraction-genia<br><table
        cellspacing="0" bordercolor="#888888" border="1"
        style="border-color:rgb(136,136,136);border-width:1px;border-collapse:collapse"><tbody><tr><td
        style="background-color:rgb(207,226,243);width:168px;height:17px"><b>&nbsp;Event
        Type</b><br></td><td
        style="background-color:rgb(207,226,243);width:370px;height:17px"><b>Core
        arguments </b><br></td><td
        style="background-color:rgb(207,226,243);width:214px;height:17px">&nbsp;<b>Additional
        arguments</b><br></td></tr><tr><td
        style="width:168px;height:17px">&nbsp;Gene
        expression<br></td><td
        style="width:370px;height:17px">&nbsp;Theme(Protein)<br></td><td
        style="width:214px;height:17px">&nbsp;</td></tr><tr><td
        style="width:168px;height:17px">&nbsp;Transcription</td><td
        style="width:370px;height:17px">&nbsp;Theme(Protein)<br></td><td
        style="width:214px;height:17px">&nbsp;</td></tr><tr><td
        style="width:168px;height:17px">&nbsp;Protein
        catabolism<br></td><td
        style="width:370px;height:17px">&nbsp;Theme(Protein)</td><td
        style="width:214px;height:17px">&nbsp;</td></tr><tr><td
        style="width:168px;height:17px">&nbsp;Phosphorylation
        <br></td><td
        style="width:370px;height:17px">&nbsp;Theme(Protein)</td><td
        style="width:214px;height:17px">&nbsp;Site(Entity)</td></tr><tr><td
        style="width:168px;height:17px">&nbsp;Localization</td><td
        style="width:370px;height:17px">&nbsp;Theme(Protein)<br></td><td
        style="width:214px;height:17px">&nbsp;AtLoc(Entity),
        ToLoc(Entity)<br></td></tr><tr><td
        style="width:168px;height:17px">&nbsp;Binding</td><td
        style="width:370px;height:17px">&nbsp;Theme(Protein)
        +<br></td><td
        style="width:214px;height:17px">&nbsp;Site(Entity)
        +<br></td></tr><tr><td
        style="width:168px;height:17px">&nbsp;Regulation</td><td
        style="width:370px;height:17px">&nbsp;Theme(Protein /
        Event), Cause(Protein / Event)<br></td><td
        style="width:214px;height:17px">&nbsp;Site(Entity),
        CSite(Entity)<br></td></tr><tr><td
        style="width:168px;height:17px">&nbsp;Positive
        regulation<br></td><td
        style="width:370px;height:17px">&nbsp;Theme(Protein /
        Event), Cause(Protein / Event)</td><td
        style="width:214px;height:17px">&nbsp;Site(Entity),
        CSite(Entity)</td></tr><tr><td
        style="width:168px;height:17px">&nbsp;Negative
        regulation<br></td><td
        style="width:370px;height:17px">&nbsp;Theme(Protein /
        Event), Cause(Protein / Event)</td><td
        style="width:214px;height:17px">&nbsp;Site(Entity),
        CSite(Entity)</td></tr></tbody></table>
 * Updated by JCasGen Mon Oct 21 13:03:29 CEST 2013
 * @generated */
public class Event_Type extends Annotation_Type {
  /** @generated */
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
  /** @generated */ 
  public String getEvent_type(int addr) {
        if (featOkTst && casFeat_event_type == null)
      jcas.throwFeatMissing("event_type", "ch.epfl.bbp.uima.genia.Event");
    return ll_cas.ll_getStringValue(addr, casFeatCode_event_type);
  }
  /** @generated */    
  public void setEvent_type(int addr, String v) {
        if (featOkTst && casFeat_event_type == null)
      jcas.throwFeatMissing("event_type", "ch.epfl.bbp.uima.genia.Event");
    ll_cas.ll_setStringValue(addr, casFeatCode_event_type, v);}
    
  
 
  /** @generated */
  final Feature casFeat_themes_protein;
  /** @generated */
  final int     casFeatCode_themes_protein;
  /** @generated */ 
  public int getThemes_protein(int addr) {
        if (featOkTst && casFeat_themes_protein == null)
      jcas.throwFeatMissing("themes_protein", "ch.epfl.bbp.uima.genia.Event");
    return ll_cas.ll_getRefValue(addr, casFeatCode_themes_protein);
  }
  /** @generated */    
  public void setThemes_protein(int addr, int v) {
        if (featOkTst && casFeat_themes_protein == null)
      jcas.throwFeatMissing("themes_protein", "ch.epfl.bbp.uima.genia.Event");
    ll_cas.ll_setRefValue(addr, casFeatCode_themes_protein, v);}
    
   /** @generated */
  public int getThemes_protein(int addr, int i) {
        if (featOkTst && casFeat_themes_protein == null)
      jcas.throwFeatMissing("themes_protein", "ch.epfl.bbp.uima.genia.Event");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_themes_protein), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_themes_protein), i);
  return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_themes_protein), i);
  }
   
  /** @generated */ 
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
  /** @generated */ 
  public int getCauses_protein(int addr) {
        if (featOkTst && casFeat_causes_protein == null)
      jcas.throwFeatMissing("causes_protein", "ch.epfl.bbp.uima.genia.Event");
    return ll_cas.ll_getRefValue(addr, casFeatCode_causes_protein);
  }
  /** @generated */    
  public void setCauses_protein(int addr, int v) {
        if (featOkTst && casFeat_causes_protein == null)
      jcas.throwFeatMissing("causes_protein", "ch.epfl.bbp.uima.genia.Event");
    ll_cas.ll_setRefValue(addr, casFeatCode_causes_protein, v);}
    
   /** @generated */
  public int getCauses_protein(int addr, int i) {
        if (featOkTst && casFeat_causes_protein == null)
      jcas.throwFeatMissing("causes_protein", "ch.epfl.bbp.uima.genia.Event");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_causes_protein), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_causes_protein), i);
  return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_causes_protein), i);
  }
   
  /** @generated */ 
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
  /** @generated */ 
  public String getSite(int addr) {
        if (featOkTst && casFeat_site == null)
      jcas.throwFeatMissing("site", "ch.epfl.bbp.uima.genia.Event");
    return ll_cas.ll_getStringValue(addr, casFeatCode_site);
  }
  /** @generated */    
  public void setSite(int addr, String v) {
        if (featOkTst && casFeat_site == null)
      jcas.throwFeatMissing("site", "ch.epfl.bbp.uima.genia.Event");
    ll_cas.ll_setStringValue(addr, casFeatCode_site, v);}
    
  
 
  /** @generated */
  final Feature casFeat_id;
  /** @generated */
  final int     casFeatCode_id;
  /** @generated */ 
  public String getId(int addr) {
        if (featOkTst && casFeat_id == null)
      jcas.throwFeatMissing("id", "ch.epfl.bbp.uima.genia.Event");
    return ll_cas.ll_getStringValue(addr, casFeatCode_id);
  }
  /** @generated */    
  public void setId(int addr, String v) {
        if (featOkTst && casFeat_id == null)
      jcas.throwFeatMissing("id", "ch.epfl.bbp.uima.genia.Event");
    ll_cas.ll_setStringValue(addr, casFeatCode_id, v);}
    
  
 
  /** @generated */
  final Feature casFeat_themes_event;
  /** @generated */
  final int     casFeatCode_themes_event;
  /** @generated */ 
  public int getThemes_event(int addr) {
        if (featOkTst && casFeat_themes_event == null)
      jcas.throwFeatMissing("themes_event", "ch.epfl.bbp.uima.genia.Event");
    return ll_cas.ll_getRefValue(addr, casFeatCode_themes_event);
  }
  /** @generated */    
  public void setThemes_event(int addr, int v) {
        if (featOkTst && casFeat_themes_event == null)
      jcas.throwFeatMissing("themes_event", "ch.epfl.bbp.uima.genia.Event");
    ll_cas.ll_setRefValue(addr, casFeatCode_themes_event, v);}
    
   /** @generated */
  public int getThemes_event(int addr, int i) {
        if (featOkTst && casFeat_themes_event == null)
      jcas.throwFeatMissing("themes_event", "ch.epfl.bbp.uima.genia.Event");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_themes_event), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_themes_event), i);
  return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_themes_event), i);
  }
   
  /** @generated */ 
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
  /** @generated */ 
  public int getCauses_event(int addr) {
        if (featOkTst && casFeat_causes_event == null)
      jcas.throwFeatMissing("causes_event", "ch.epfl.bbp.uima.genia.Event");
    return ll_cas.ll_getRefValue(addr, casFeatCode_causes_event);
  }
  /** @generated */    
  public void setCauses_event(int addr, int v) {
        if (featOkTst && casFeat_causes_event == null)
      jcas.throwFeatMissing("causes_event", "ch.epfl.bbp.uima.genia.Event");
    ll_cas.ll_setRefValue(addr, casFeatCode_causes_event, v);}
    
   /** @generated */
  public int getCauses_event(int addr, int i) {
        if (featOkTst && casFeat_causes_event == null)
      jcas.throwFeatMissing("causes_event", "ch.epfl.bbp.uima.genia.Event");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_causes_event), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_causes_event), i);
  return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_causes_event), i);
  }
   
  /** @generated */ 
  public void setCauses_event(int addr, int i, int v) {
        if (featOkTst && casFeat_causes_event == null)
      jcas.throwFeatMissing("causes_event", "ch.epfl.bbp.uima.genia.Event");
    if (lowLevelTypeChecks)
      ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_causes_event), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_causes_event), i);
    ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_causes_event), i, v);
  }
 



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
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



    