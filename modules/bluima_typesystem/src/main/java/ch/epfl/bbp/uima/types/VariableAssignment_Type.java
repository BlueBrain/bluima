
/* First created by JCasGen Mon Feb 17 12:26:53 CET 2014 */
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

/** e.g. x =3, p < 0.05
 * Updated by JCasGen Mon Feb 17 22:12:56 CET 2014
 * @generated */
public class VariableAssignment_Type extends Measure_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (VariableAssignment_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = VariableAssignment_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new VariableAssignment(addr, VariableAssignment_Type.this);
  			   VariableAssignment_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new VariableAssignment(addr, VariableAssignment_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = VariableAssignment.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("ch.epfl.bbp.uima.types.VariableAssignment");
 
  /** @generated */
  final Feature casFeat_operator;
  /** @generated */
  final int     casFeatCode_operator;
  /** @generated */ 
  public String getOperator(int addr) {
        if (featOkTst && casFeat_operator == null)
      jcas.throwFeatMissing("operator", "ch.epfl.bbp.uima.types.VariableAssignment");
    return ll_cas.ll_getStringValue(addr, casFeatCode_operator);
  }
  /** @generated */    
  public void setOperator(int addr, String v) {
        if (featOkTst && casFeat_operator == null)
      jcas.throwFeatMissing("operator", "ch.epfl.bbp.uima.types.VariableAssignment");
    ll_cas.ll_setStringValue(addr, casFeatCode_operator, v);}
    
  
 
  /** @generated */
  final Feature casFeat_value;
  /** @generated */
  final int     casFeatCode_value;
  /** @generated */ 
  public float getValue(int addr) {
        if (featOkTst && casFeat_value == null)
      jcas.throwFeatMissing("value", "ch.epfl.bbp.uima.types.VariableAssignment");
    return ll_cas.ll_getFloatValue(addr, casFeatCode_value);
  }
  /** @generated */    
  public void setValue(int addr, float v) {
        if (featOkTst && casFeat_value == null)
      jcas.throwFeatMissing("value", "ch.epfl.bbp.uima.types.VariableAssignment");
    ll_cas.ll_setFloatValue(addr, casFeatCode_value, v);}
    
  
 
  /** @generated */
  final Feature casFeat_variableName;
  /** @generated */
  final int     casFeatCode_variableName;
  /** @generated */ 
  public String getVariableName(int addr) {
        if (featOkTst && casFeat_variableName == null)
      jcas.throwFeatMissing("variableName", "ch.epfl.bbp.uima.types.VariableAssignment");
    return ll_cas.ll_getStringValue(addr, casFeatCode_variableName);
  }
  /** @generated */    
  public void setVariableName(int addr, String v) {
        if (featOkTst && casFeat_variableName == null)
      jcas.throwFeatMissing("variableName", "ch.epfl.bbp.uima.types.VariableAssignment");
    ll_cas.ll_setStringValue(addr, casFeatCode_variableName, v);}
    
  
 
  /** @generated */
  final Feature casFeat_textValue;
  /** @generated */
  final int     casFeatCode_textValue;
  /** @generated */ 
  public String getTextValue(int addr) {
        if (featOkTst && casFeat_textValue == null)
      jcas.throwFeatMissing("textValue", "ch.epfl.bbp.uima.types.VariableAssignment");
    return ll_cas.ll_getStringValue(addr, casFeatCode_textValue);
  }
  /** @generated */    
  public void setTextValue(int addr, String v) {
        if (featOkTst && casFeat_textValue == null)
      jcas.throwFeatMissing("textValue", "ch.epfl.bbp.uima.types.VariableAssignment");
    ll_cas.ll_setStringValue(addr, casFeatCode_textValue, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public VariableAssignment_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_operator = jcas.getRequiredFeatureDE(casType, "operator", "uima.cas.String", featOkTst);
    casFeatCode_operator  = (null == casFeat_operator) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_operator).getCode();

 
    casFeat_value = jcas.getRequiredFeatureDE(casType, "value", "uima.cas.Float", featOkTst);
    casFeatCode_value  = (null == casFeat_value) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_value).getCode();

 
    casFeat_variableName = jcas.getRequiredFeatureDE(casType, "variableName", "uima.cas.String", featOkTst);
    casFeatCode_variableName  = (null == casFeat_variableName) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_variableName).getCode();

 
    casFeat_textValue = jcas.getRequiredFeatureDE(casType, "textValue", "uima.cas.String", featOkTst);
    casFeatCode_textValue  = (null == casFeat_textValue) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_textValue).getCode();

  }
}



    