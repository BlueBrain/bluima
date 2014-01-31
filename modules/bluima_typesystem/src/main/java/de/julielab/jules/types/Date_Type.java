
/* First created by JCasGen Wed Oct 19 19:10:41 CEST 2011 */
package de.julielab.jules.types;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;

/** Type to store dates
 * Updated by JCasGen Fri Dec 09 11:59:26 CET 2011
 * @generated */
public class Date_Type extends Annotation_Type {
  /** @generated */
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Date_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Date_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Date(addr, Date_Type.this);
  			   Date_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Date(addr, Date_Type.this);
  	  }
    };
  /** @generated */
  public final static int typeIndexID = Date.typeIndexID;
  /** @generated 
     @modifiable */
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.julielab.jules.types.Date");
 
  /** @generated */
  final Feature casFeat_day;
  /** @generated */
  final int     casFeatCode_day;
  /** @generated */ 
  public int getDay(int addr) {
        if (featOkTst && casFeat_day == null)
      jcas.throwFeatMissing("day", "de.julielab.jules.types.Date");
    return ll_cas.ll_getIntValue(addr, casFeatCode_day);
  }
  /** @generated */    
  public void setDay(int addr, int v) {
        if (featOkTst && casFeat_day == null)
      jcas.throwFeatMissing("day", "de.julielab.jules.types.Date");
    ll_cas.ll_setIntValue(addr, casFeatCode_day, v);}
    
  
 
  /** @generated */
  final Feature casFeat_month;
  /** @generated */
  final int     casFeatCode_month;
  /** @generated */ 
  public int getMonth(int addr) {
        if (featOkTst && casFeat_month == null)
      jcas.throwFeatMissing("month", "de.julielab.jules.types.Date");
    return ll_cas.ll_getIntValue(addr, casFeatCode_month);
  }
  /** @generated */    
  public void setMonth(int addr, int v) {
        if (featOkTst && casFeat_month == null)
      jcas.throwFeatMissing("month", "de.julielab.jules.types.Date");
    ll_cas.ll_setIntValue(addr, casFeatCode_month, v);}
    
  
 
  /** @generated */
  final Feature casFeat_year;
  /** @generated */
  final int     casFeatCode_year;
  /** @generated */ 
  public int getYear(int addr) {
        if (featOkTst && casFeat_year == null)
      jcas.throwFeatMissing("year", "de.julielab.jules.types.Date");
    return ll_cas.ll_getIntValue(addr, casFeatCode_year);
  }
  /** @generated */    
  public void setYear(int addr, int v) {
        if (featOkTst && casFeat_year == null)
      jcas.throwFeatMissing("year", "de.julielab.jules.types.Date");
    ll_cas.ll_setIntValue(addr, casFeatCode_year, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public Date_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_day = jcas.getRequiredFeatureDE(casType, "day", "uima.cas.Integer", featOkTst);
    casFeatCode_day  = (null == casFeat_day) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_day).getCode();

 
    casFeat_month = jcas.getRequiredFeatureDE(casType, "month", "uima.cas.Integer", featOkTst);
    casFeatCode_month  = (null == casFeat_month) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_month).getCode();

 
    casFeat_year = jcas.getRequiredFeatureDE(casType, "year", "uima.cas.Integer", featOkTst);
    casFeatCode_year  = (null == casFeat_year) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_year).getCode();

  }
}



    