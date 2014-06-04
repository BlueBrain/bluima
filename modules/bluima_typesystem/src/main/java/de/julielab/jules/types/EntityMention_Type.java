
/* First created by JCasGen Wed Oct 19 19:10:28 CEST 2011 */
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

/** (Named) EntityMention (i.e. An entity is an object or set of objects in the world. Entitiy mentions may be refrenced in a text by their name, indicated by a common noun or noun phrase, or represented by a pronoun) annotation
 * Updated by JCasGen Wed Jun 04 18:01:57 CEST 2014
 * @generated */
public class EntityMention_Type extends ConceptMention_Type {
  /** @generated */
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (EntityMention_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = EntityMention_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new EntityMention(addr, EntityMention_Type.this);
  			   EntityMention_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new EntityMention(addr, EntityMention_Type.this);
  	  }
    };
  /** @generated */
  public final static int typeIndexID = EntityMention.typeIndexID;
  /** @generated 
     @modifiable */
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.julielab.jules.types.EntityMention");
 
  /** @generated */
  final Feature casFeat_head;
  /** @generated */
  final int     casFeatCode_head;
  /** @generated */ 
  public int getHead(int addr) {
        if (featOkTst && casFeat_head == null)
      jcas.throwFeatMissing("head", "de.julielab.jules.types.EntityMention");
    return ll_cas.ll_getRefValue(addr, casFeatCode_head);
  }
  /** @generated */    
  public void setHead(int addr, int v) {
        if (featOkTst && casFeat_head == null)
      jcas.throwFeatMissing("head", "de.julielab.jules.types.EntityMention");
    ll_cas.ll_setRefValue(addr, casFeatCode_head, v);}
    
  
 
  /** @generated */
  final Feature casFeat_mentionLevel;
  /** @generated */
  final int     casFeatCode_mentionLevel;
  /** @generated */ 
  public String getMentionLevel(int addr) {
        if (featOkTst && casFeat_mentionLevel == null)
      jcas.throwFeatMissing("mentionLevel", "de.julielab.jules.types.EntityMention");
    return ll_cas.ll_getStringValue(addr, casFeatCode_mentionLevel);
  }
  /** @generated */    
  public void setMentionLevel(int addr, String v) {
        if (featOkTst && casFeat_mentionLevel == null)
      jcas.throwFeatMissing("mentionLevel", "de.julielab.jules.types.EntityMention");
    ll_cas.ll_setStringValue(addr, casFeatCode_mentionLevel, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public EntityMention_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_head = jcas.getRequiredFeatureDE(casType, "head", "de.julielab.jules.types.Annotation", featOkTst);
    casFeatCode_head  = (null == casFeat_head) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_head).getCode();

 
    casFeat_mentionLevel = jcas.getRequiredFeatureDE(casType, "mentionLevel", "uima.cas.String", featOkTst);
    casFeatCode_mentionLevel  = (null == casFeat_mentionLevel) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_mentionLevel).getCode();

  }
}



    