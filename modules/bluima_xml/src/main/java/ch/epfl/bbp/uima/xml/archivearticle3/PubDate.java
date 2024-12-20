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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
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
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element ref="{}day"/>
 *         &lt;element ref="{}month"/>
 *         &lt;element ref="{}year"/>
 *         &lt;element ref="{}season"/>
 *         &lt;element ref="{}string-date"/>
 *         &lt;element ref="{}x"/>
 *       &lt;/choice>
 *       &lt;attribute name="pub-type" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "dayOrMonthOrYear"
})
@XmlRootElement(name = "pub-date")
public class PubDate {

    @XmlElements({
        @XmlElement(name = "day", type = Day.class),
        @XmlElement(name = "month", type = Month.class),
        @XmlElement(name = "year", type = Year.class),
        @XmlElement(name = "season", type = Season.class),
        @XmlElement(name = "string-date", type = StringDate.class),
        @XmlElement(name = "x", type = X.class)
    })
    protected List<Object> dayOrMonthOrYear;
    @XmlAttribute(name = "pub-type")
    @XmlSchemaType(name = "anySimpleType")
    protected String pubType;

    /**
     * Gets the value of the dayOrMonthOrYear property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dayOrMonthOrYear property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDayOrMonthOrYear().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Day }
     * {@link Month }
     * {@link Year }
     * {@link Season }
     * {@link StringDate }
     * {@link X }
     * 
     * 
     */
    public List<Object> getDayOrMonthOrYear() {
        if (dayOrMonthOrYear == null) {
            dayOrMonthOrYear = new ArrayList<Object>();
        }
        return this.dayOrMonthOrYear;
    }

    /**
     * Gets the value of the pubType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPubType() {
        return pubType;
    }

    /**
     * Sets the value of the pubType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPubType(String value) {
        this.pubType = value;
    }

}
