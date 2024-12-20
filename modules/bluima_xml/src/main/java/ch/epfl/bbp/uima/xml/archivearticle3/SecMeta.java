//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.12.14 at 03:30:44 PM CET 
//


package ch.epfl.bbp.uima.xml.archivearticle3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}contrib-group" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}kwd-group" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}permissions" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "contribGroup",
    "kwdGroup",
    "permissions"
})
@XmlRootElement(name = "sec-meta")
public class SecMeta {

    @XmlElement(name = "contrib-group")
    protected List<ContribGroup> contribGroup;
    @XmlElement(name = "kwd-group")
    protected List<KwdGroup> kwdGroup;
    protected Permissions permissions;

    /**
     * Gets the value of the contribGroup property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the contribGroup property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContribGroup().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ContribGroup }
     * 
     * 
     */
    public List<ContribGroup> getContribGroup() {
        if (contribGroup == null) {
            contribGroup = new ArrayList<ContribGroup>();
        }
        return this.contribGroup;
    }

    /**
     * Gets the value of the kwdGroup property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the kwdGroup property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getKwdGroup().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link KwdGroup }
     * 
     * 
     */
    public List<KwdGroup> getKwdGroup() {
        if (kwdGroup == null) {
            kwdGroup = new ArrayList<KwdGroup>();
        }
        return this.kwdGroup;
    }

    /**
     * Gets the value of the permissions property.
     * 
     * @return
     *     possible object is
     *     {@link Permissions }
     *     
     */
    public Permissions getPermissions() {
        return permissions;
    }

    /**
     * Sets the value of the permissions property.
     * 
     * @param value
     *     allowed object is
     *     {@link Permissions }
     *     
     */
    public void setPermissions(Permissions value) {
        this.permissions = value;
    }

}
