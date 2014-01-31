
/* First created by JCasGen Fri Sep 14 13:58:44 CEST 2012 */
package ch.epfl.bbp.uima.types;

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

/** A table that contains data, extracted from a pdf article
 * Updated by JCasGen Fri Sep 20 01:02:03 CEST 2013
 * @generated */
public class DataTable_Type extends Annotation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (DataTable_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = DataTable_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new DataTable(addr, DataTable_Type.this);
  			   DataTable_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new DataTable(addr, DataTable_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = DataTable.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("ch.epfl.bbp.uima.types.DataTable");
 
  /** @generated */
  final Feature casFeat_tableId;
  /** @generated */
  final int     casFeatCode_tableId;
  /** @generated */ 
  public int getTableId(int addr) {
        if (featOkTst && casFeat_tableId == null)
      jcas.throwFeatMissing("tableId", "ch.epfl.bbp.uima.types.DataTable");
    return ll_cas.ll_getIntValue(addr, casFeatCode_tableId);
  }
  /** @generated */    
  public void setTableId(int addr, int v) {
        if (featOkTst && casFeat_tableId == null)
      jcas.throwFeatMissing("tableId", "ch.epfl.bbp.uima.types.DataTable");
    ll_cas.ll_setIntValue(addr, casFeatCode_tableId, v);}
    
  
 
  /** @generated */
  final Feature casFeat_rowCount;
  /** @generated */
  final int     casFeatCode_rowCount;
  /** @generated */ 
  public int getRowCount(int addr) {
        if (featOkTst && casFeat_rowCount == null)
      jcas.throwFeatMissing("rowCount", "ch.epfl.bbp.uima.types.DataTable");
    return ll_cas.ll_getIntValue(addr, casFeatCode_rowCount);
  }
  /** @generated */    
  public void setRowCount(int addr, int v) {
        if (featOkTst && casFeat_rowCount == null)
      jcas.throwFeatMissing("rowCount", "ch.epfl.bbp.uima.types.DataTable");
    ll_cas.ll_setIntValue(addr, casFeatCode_rowCount, v);}
    
  
 
  /** @generated */
  final Feature casFeat_columnCount;
  /** @generated */
  final int     casFeatCode_columnCount;
  /** @generated */ 
  public int getColumnCount(int addr) {
        if (featOkTst && casFeat_columnCount == null)
      jcas.throwFeatMissing("columnCount", "ch.epfl.bbp.uima.types.DataTable");
    return ll_cas.ll_getIntValue(addr, casFeatCode_columnCount);
  }
  /** @generated */    
  public void setColumnCount(int addr, int v) {
        if (featOkTst && casFeat_columnCount == null)
      jcas.throwFeatMissing("columnCount", "ch.epfl.bbp.uima.types.DataTable");
    ll_cas.ll_setIntValue(addr, casFeatCode_columnCount, v);}
    
  
 
  /** @generated */
  final Feature casFeat_caption;
  /** @generated */
  final int     casFeatCode_caption;
  /** @generated */ 
  public String getCaption(int addr) {
        if (featOkTst && casFeat_caption == null)
      jcas.throwFeatMissing("caption", "ch.epfl.bbp.uima.types.DataTable");
    return ll_cas.ll_getStringValue(addr, casFeatCode_caption);
  }
  /** @generated */    
  public void setCaption(int addr, String v) {
        if (featOkTst && casFeat_caption == null)
      jcas.throwFeatMissing("caption", "ch.epfl.bbp.uima.types.DataTable");
    ll_cas.ll_setStringValue(addr, casFeatCode_caption, v);}
    
  
 
  /** @generated */
  final Feature casFeat_headings;
  /** @generated */
  final int     casFeatCode_headings;
  /** @generated */ 
  public int getHeadings(int addr) {
        if (featOkTst && casFeat_headings == null)
      jcas.throwFeatMissing("headings", "ch.epfl.bbp.uima.types.DataTable");
    return ll_cas.ll_getRefValue(addr, casFeatCode_headings);
  }
  /** @generated */    
  public void setHeadings(int addr, int v) {
        if (featOkTst && casFeat_headings == null)
      jcas.throwFeatMissing("headings", "ch.epfl.bbp.uima.types.DataTable");
    ll_cas.ll_setRefValue(addr, casFeatCode_headings, v);}
    
   /** @generated */
  public String getHeadings(int addr, int i) {
        if (featOkTst && casFeat_headings == null)
      jcas.throwFeatMissing("headings", "ch.epfl.bbp.uima.types.DataTable");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_headings), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_headings), i);
  return ll_cas.ll_getStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_headings), i);
  }
   
  /** @generated */ 
  public void setHeadings(int addr, int i, String v) {
        if (featOkTst && casFeat_headings == null)
      jcas.throwFeatMissing("headings", "ch.epfl.bbp.uima.types.DataTable");
    if (lowLevelTypeChecks)
      ll_cas.ll_setStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_headings), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_headings), i);
    ll_cas.ll_setStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_headings), i, v);
  }
 
 
  /** @generated */
  final Feature casFeat_body;
  /** @generated */
  final int     casFeatCode_body;
  /** @generated */ 
  public int getBody(int addr) {
        if (featOkTst && casFeat_body == null)
      jcas.throwFeatMissing("body", "ch.epfl.bbp.uima.types.DataTable");
    return ll_cas.ll_getRefValue(addr, casFeatCode_body);
  }
  /** @generated */    
  public void setBody(int addr, int v) {
        if (featOkTst && casFeat_body == null)
      jcas.throwFeatMissing("body", "ch.epfl.bbp.uima.types.DataTable");
    ll_cas.ll_setRefValue(addr, casFeatCode_body, v);}
    
   /** @generated */
  public String getBody(int addr, int i) {
        if (featOkTst && casFeat_body == null)
      jcas.throwFeatMissing("body", "ch.epfl.bbp.uima.types.DataTable");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_body), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_body), i);
  return ll_cas.ll_getStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_body), i);
  }
   
  /** @generated */ 
  public void setBody(int addr, int i, String v) {
        if (featOkTst && casFeat_body == null)
      jcas.throwFeatMissing("body", "ch.epfl.bbp.uima.types.DataTable");
    if (lowLevelTypeChecks)
      ll_cas.ll_setStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_body), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_body), i);
    ll_cas.ll_setStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_body), i, v);
  }
 
 
  /** @generated */
  final Feature casFeat_referenceText;
  /** @generated */
  final int     casFeatCode_referenceText;
  /** @generated */ 
  public String getReferenceText(int addr) {
        if (featOkTst && casFeat_referenceText == null)
      jcas.throwFeatMissing("referenceText", "ch.epfl.bbp.uima.types.DataTable");
    return ll_cas.ll_getStringValue(addr, casFeatCode_referenceText);
  }
  /** @generated */    
  public void setReferenceText(int addr, String v) {
        if (featOkTst && casFeat_referenceText == null)
      jcas.throwFeatMissing("referenceText", "ch.epfl.bbp.uima.types.DataTable");
    ll_cas.ll_setStringValue(addr, casFeatCode_referenceText, v);}
    
  
 
  /** @generated */
  final Feature casFeat_pageNumber;
  /** @generated */
  final int     casFeatCode_pageNumber;
  /** @generated */ 
  public int getPageNumber(int addr) {
        if (featOkTst && casFeat_pageNumber == null)
      jcas.throwFeatMissing("pageNumber", "ch.epfl.bbp.uima.types.DataTable");
    return ll_cas.ll_getIntValue(addr, casFeatCode_pageNumber);
  }
  /** @generated */    
  public void setPageNumber(int addr, int v) {
        if (featOkTst && casFeat_pageNumber == null)
      jcas.throwFeatMissing("pageNumber", "ch.epfl.bbp.uima.types.DataTable");
    ll_cas.ll_setIntValue(addr, casFeatCode_pageNumber, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public DataTable_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_tableId = jcas.getRequiredFeatureDE(casType, "tableId", "uima.cas.Integer", featOkTst);
    casFeatCode_tableId  = (null == casFeat_tableId) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_tableId).getCode();

 
    casFeat_rowCount = jcas.getRequiredFeatureDE(casType, "rowCount", "uima.cas.Integer", featOkTst);
    casFeatCode_rowCount  = (null == casFeat_rowCount) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_rowCount).getCode();

 
    casFeat_columnCount = jcas.getRequiredFeatureDE(casType, "columnCount", "uima.cas.Integer", featOkTst);
    casFeatCode_columnCount  = (null == casFeat_columnCount) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_columnCount).getCode();

 
    casFeat_caption = jcas.getRequiredFeatureDE(casType, "caption", "uima.cas.String", featOkTst);
    casFeatCode_caption  = (null == casFeat_caption) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_caption).getCode();

 
    casFeat_headings = jcas.getRequiredFeatureDE(casType, "headings", "uima.cas.StringArray", featOkTst);
    casFeatCode_headings  = (null == casFeat_headings) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_headings).getCode();

 
    casFeat_body = jcas.getRequiredFeatureDE(casType, "body", "uima.cas.StringArray", featOkTst);
    casFeatCode_body  = (null == casFeat_body) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_body).getCode();

 
    casFeat_referenceText = jcas.getRequiredFeatureDE(casType, "referenceText", "uima.cas.String", featOkTst);
    casFeatCode_referenceText  = (null == casFeat_referenceText) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_referenceText).getCode();

 
    casFeat_pageNumber = jcas.getRequiredFeatureDE(casType, "pageNumber", "uima.cas.Integer", featOkTst);
    casFeatCode_pageNumber  = (null == casFeat_pageNumber) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_pageNumber).getCode();

  }
}



    