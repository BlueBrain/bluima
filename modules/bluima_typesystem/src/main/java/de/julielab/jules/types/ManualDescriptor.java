

/* First created by JCasGen Wed Oct 19 19:10:41 CEST 2011 */
package de.julielab.jules.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** The descriptor type for the manually added information.
 * Updated by JCasGen Fri Dec 09 11:59:26 CET 2011
 * XML source: /Users/richarde/dev/bluebrain/svn_nlp/UIMA/blue_uima/trunk/julielab_typesystem-2.6.8/src/main/resources/typeSystem/julie-document-meta-types.xml
 * @generated */
public class ManualDescriptor extends Descriptor {
  /** @generated
   * @ordered 
   */
  public final static int typeIndexID = JCasRegistry.register(ManualDescriptor.class);
  /** @generated
   * @ordered 
   */
  public final static int type = typeIndexID;
  /** @generated  */
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected ManualDescriptor() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public ManualDescriptor(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public ManualDescriptor(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public ManualDescriptor(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** <!-- begin-user-doc -->
    * Write your own initialization here
    * <!-- end-user-doc -->
  @generated modifiable */
  private void readObject() {}
     
}

    