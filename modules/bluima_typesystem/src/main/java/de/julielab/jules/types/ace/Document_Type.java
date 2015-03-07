
/* First created by JCasGen Sat Mar 07 22:05:57 CET 2015 */
package de.julielab.jules.types.ace;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;

/** 
 * Updated by JCasGen Sat Mar 07 22:05:57 CET 2015
 * @generated */
public class Document_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Document_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Document_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Document(addr, Document_Type.this);
  			   Document_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Document(addr, Document_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Document.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.julielab.jules.types.ace.Document");
 
  /** @generated */
  final Feature casFeat_docid;
  /** @generated */
  final int     casFeatCode_docid;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getDocid(int addr) {
        if (featOkTst && casFeat_docid == null)
      jcas.throwFeatMissing("docid", "de.julielab.jules.types.ace.Document");
    return ll_cas.ll_getStringValue(addr, casFeatCode_docid);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setDocid(int addr, String v) {
        if (featOkTst && casFeat_docid == null)
      jcas.throwFeatMissing("docid", "de.julielab.jules.types.ace.Document");
    ll_cas.ll_setStringValue(addr, casFeatCode_docid, v);}
    
  
 
  /** @generated */
  final Feature casFeat_entities;
  /** @generated */
  final int     casFeatCode_entities;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getEntities(int addr) {
        if (featOkTst && casFeat_entities == null)
      jcas.throwFeatMissing("entities", "de.julielab.jules.types.ace.Document");
    return ll_cas.ll_getRefValue(addr, casFeatCode_entities);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setEntities(int addr, int v) {
        if (featOkTst && casFeat_entities == null)
      jcas.throwFeatMissing("entities", "de.julielab.jules.types.ace.Document");
    ll_cas.ll_setRefValue(addr, casFeatCode_entities, v);}
    
   /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @return value at index i in the array 
   */
  public int getEntities(int addr, int i) {
        if (featOkTst && casFeat_entities == null)
      jcas.throwFeatMissing("entities", "de.julielab.jules.types.ace.Document");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_entities), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_entities), i);
	return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_entities), i);
  }
   
  /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @param v value to set
   */ 
  public void setEntities(int addr, int i, int v) {
        if (featOkTst && casFeat_entities == null)
      jcas.throwFeatMissing("entities", "de.julielab.jules.types.ace.Document");
    if (lowLevelTypeChecks)
      ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_entities), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_entities), i);
    ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_entities), i, v);
  }
 
 
  /** @generated */
  final Feature casFeat_values;
  /** @generated */
  final int     casFeatCode_values;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getValues(int addr) {
        if (featOkTst && casFeat_values == null)
      jcas.throwFeatMissing("values", "de.julielab.jules.types.ace.Document");
    return ll_cas.ll_getRefValue(addr, casFeatCode_values);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setValues(int addr, int v) {
        if (featOkTst && casFeat_values == null)
      jcas.throwFeatMissing("values", "de.julielab.jules.types.ace.Document");
    ll_cas.ll_setRefValue(addr, casFeatCode_values, v);}
    
   /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @return value at index i in the array 
   */
  public int getValues(int addr, int i) {
        if (featOkTst && casFeat_values == null)
      jcas.throwFeatMissing("values", "de.julielab.jules.types.ace.Document");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_values), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_values), i);
	return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_values), i);
  }
   
  /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @param v value to set
   */ 
  public void setValues(int addr, int i, int v) {
        if (featOkTst && casFeat_values == null)
      jcas.throwFeatMissing("values", "de.julielab.jules.types.ace.Document");
    if (lowLevelTypeChecks)
      ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_values), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_values), i);
    ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_values), i, v);
  }
 
 
  /** @generated */
  final Feature casFeat_timex2;
  /** @generated */
  final int     casFeatCode_timex2;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getTimex2(int addr) {
        if (featOkTst && casFeat_timex2 == null)
      jcas.throwFeatMissing("timex2", "de.julielab.jules.types.ace.Document");
    return ll_cas.ll_getRefValue(addr, casFeatCode_timex2);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setTimex2(int addr, int v) {
        if (featOkTst && casFeat_timex2 == null)
      jcas.throwFeatMissing("timex2", "de.julielab.jules.types.ace.Document");
    ll_cas.ll_setRefValue(addr, casFeatCode_timex2, v);}
    
   /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @return value at index i in the array 
   */
  public int getTimex2(int addr, int i) {
        if (featOkTst && casFeat_timex2 == null)
      jcas.throwFeatMissing("timex2", "de.julielab.jules.types.ace.Document");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_timex2), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_timex2), i);
	return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_timex2), i);
  }
   
  /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @param v value to set
   */ 
  public void setTimex2(int addr, int i, int v) {
        if (featOkTst && casFeat_timex2 == null)
      jcas.throwFeatMissing("timex2", "de.julielab.jules.types.ace.Document");
    if (lowLevelTypeChecks)
      ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_timex2), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_timex2), i);
    ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_timex2), i, v);
  }
 
 
  /** @generated */
  final Feature casFeat_relations;
  /** @generated */
  final int     casFeatCode_relations;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getRelations(int addr) {
        if (featOkTst && casFeat_relations == null)
      jcas.throwFeatMissing("relations", "de.julielab.jules.types.ace.Document");
    return ll_cas.ll_getRefValue(addr, casFeatCode_relations);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setRelations(int addr, int v) {
        if (featOkTst && casFeat_relations == null)
      jcas.throwFeatMissing("relations", "de.julielab.jules.types.ace.Document");
    ll_cas.ll_setRefValue(addr, casFeatCode_relations, v);}
    
   /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @return value at index i in the array 
   */
  public int getRelations(int addr, int i) {
        if (featOkTst && casFeat_relations == null)
      jcas.throwFeatMissing("relations", "de.julielab.jules.types.ace.Document");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_relations), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_relations), i);
	return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_relations), i);
  }
   
  /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @param v value to set
   */ 
  public void setRelations(int addr, int i, int v) {
        if (featOkTst && casFeat_relations == null)
      jcas.throwFeatMissing("relations", "de.julielab.jules.types.ace.Document");
    if (lowLevelTypeChecks)
      ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_relations), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_relations), i);
    ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_relations), i, v);
  }
 
 
  /** @generated */
  final Feature casFeat_events;
  /** @generated */
  final int     casFeatCode_events;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getEvents(int addr) {
        if (featOkTst && casFeat_events == null)
      jcas.throwFeatMissing("events", "de.julielab.jules.types.ace.Document");
    return ll_cas.ll_getRefValue(addr, casFeatCode_events);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setEvents(int addr, int v) {
        if (featOkTst && casFeat_events == null)
      jcas.throwFeatMissing("events", "de.julielab.jules.types.ace.Document");
    ll_cas.ll_setRefValue(addr, casFeatCode_events, v);}
    
   /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @return value at index i in the array 
   */
  public int getEvents(int addr, int i) {
        if (featOkTst && casFeat_events == null)
      jcas.throwFeatMissing("events", "de.julielab.jules.types.ace.Document");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_events), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_events), i);
	return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_events), i);
  }
   
  /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @param v value to set
   */ 
  public void setEvents(int addr, int i, int v) {
        if (featOkTst && casFeat_events == null)
      jcas.throwFeatMissing("events", "de.julielab.jules.types.ace.Document");
    if (lowLevelTypeChecks)
      ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_events), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_events), i);
    ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_events), i, v);
  }
 



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public Document_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_docid = jcas.getRequiredFeatureDE(casType, "docid", "uima.cas.String", featOkTst);
    casFeatCode_docid  = (null == casFeat_docid) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_docid).getCode();

 
    casFeat_entities = jcas.getRequiredFeatureDE(casType, "entities", "uima.cas.FSArray", featOkTst);
    casFeatCode_entities  = (null == casFeat_entities) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_entities).getCode();

 
    casFeat_values = jcas.getRequiredFeatureDE(casType, "values", "uima.cas.FSArray", featOkTst);
    casFeatCode_values  = (null == casFeat_values) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_values).getCode();

 
    casFeat_timex2 = jcas.getRequiredFeatureDE(casType, "timex2", "uima.cas.FSArray", featOkTst);
    casFeatCode_timex2  = (null == casFeat_timex2) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_timex2).getCode();

 
    casFeat_relations = jcas.getRequiredFeatureDE(casType, "relations", "uima.cas.FSArray", featOkTst);
    casFeatCode_relations  = (null == casFeat_relations) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_relations).getCode();

 
    casFeat_events = jcas.getRequiredFeatureDE(casType, "events", "uima.cas.FSArray", featOkTst);
    casFeatCode_events  = (null == casFeat_events) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_events).getCode();

  }
}



    