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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="unbounded">
 *         &lt;element ref="{}contrib"/>
 *         &lt;element ref="{}address"/>
 *         &lt;element ref="{}aff"/>
 *         &lt;element ref="{}author-comment"/>
 *         &lt;element ref="{}bio"/>
 *         &lt;element ref="{}email"/>
 *         &lt;element ref="{}etal"/>
 *         &lt;element ref="{}ext-link"/>
 *         &lt;element ref="{}fn"/>
 *         &lt;element ref="{}on-behalf-of"/>
 *         &lt;element ref="{}role"/>
 *         &lt;element ref="{}uri"/>
 *         &lt;element ref="{}xref"/>
 *         &lt;element ref="{}x"/>
 *       &lt;/choice>
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *       &lt;attribute name="content-type" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "contribOrAddressOrAff"
})
@XmlRootElement(name = "contrib-group")
public class ContribGroup {

    @XmlElements({
        @XmlElement(name = "contrib", type = Contrib.class),
        @XmlElement(name = "address", type = Address.class),
        @XmlElement(name = "aff", type = Aff.class),
        @XmlElement(name = "author-comment", type = AuthorComment.class),
        @XmlElement(name = "bio", type = Bio.class),
        @XmlElement(name = "email", type = Email.class),
        @XmlElement(name = "etal", type = Etal.class),
        @XmlElement(name = "ext-link", type = ExtLink.class),
        @XmlElement(name = "fn", type = Fn.class),
        @XmlElement(name = "on-behalf-of", type = OnBehalfOf.class),
        @XmlElement(name = "role", type = Role.class),
        @XmlElement(name = "uri", type = Uri.class),
        @XmlElement(name = "xref", type = Xref.class),
        @XmlElement(name = "x", type = X.class)
    })
    protected List<Object> contribOrAddressOrAff;
    @XmlAttribute(name = "id")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;
    @XmlAttribute(name = "content-type")
    @XmlSchemaType(name = "anySimpleType")
    protected String contentType;

    /**
     * Gets the value of the contribOrAddressOrAff property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the contribOrAddressOrAff property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContribOrAddressOrAff().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Contrib }
     * {@link Address }
     * {@link Aff }
     * {@link AuthorComment }
     * {@link Bio }
     * {@link Email }
     * {@link Etal }
     * {@link ExtLink }
     * {@link Fn }
     * {@link OnBehalfOf }
     * {@link Role }
     * {@link Uri }
     * {@link Xref }
     * {@link X }
     * 
     * 
     */
    public List<Object> getContribOrAddressOrAff() {
        if (contribOrAddressOrAff == null) {
            contribOrAddressOrAff = new ArrayList<Object>();
        }
        return this.contribOrAddressOrAff;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the contentType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * Sets the value of the contentType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContentType(String value) {
        this.contentType = value;
    }

}
