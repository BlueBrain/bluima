//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.01.04 at 08:09:15 PM CET 
//


package ch.epfl.bbp.uima.xml.mesh;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "semanticTypeUI",
    "semanticTypeName"
})
@XmlRootElement(name = "SemanticType")
public class SemanticType {

    @XmlElement(name = "SemanticTypeUI", required = true)
    protected String semanticTypeUI;
    @XmlElement(name = "SemanticTypeName", required = true)
    protected String semanticTypeName;

    /**
     * Gets the value of the semanticTypeUI property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSemanticTypeUI() {
        return semanticTypeUI;
    }

    /**
     * Sets the value of the semanticTypeUI property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSemanticTypeUI(String value) {
        this.semanticTypeUI = value;
    }

    /**
     * Gets the value of the semanticTypeName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSemanticTypeName() {
        return semanticTypeName;
    }

    /**
     * Sets the value of the semanticTypeName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSemanticTypeName(String value) {
        this.semanticTypeName = value;
    }

}
