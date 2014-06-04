

/* First created by JCasGen Wed Oct 19 19:10:28 CEST 2011 */
package de.julielab.jules.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** Abbreviation includes acronyms written as the initial letter or letters of words, and pronounced on the basis of this abbreviated written form.
 * Updated by JCasGen Wed Jun 04 18:01:57 CEST 2014
 * XML source: /Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_typesystem/src/main/resources/typeSystem/bbp-types.xml
 * @generated */
public class Acronym extends Abbreviation {
  /** @generated
   * @ordered 
   */
  public final static int typeIndexID = JCasRegistry.register(Acronym.class);
  /** @generated
   * @ordered 
   */
  public final static int type = typeIndexID;
  /** @generated  */
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected Acronym() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public Acronym(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public Acronym(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public Acronym(JCas jcas, int begin, int end) {
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
  private void readObject() {}
     
}

    