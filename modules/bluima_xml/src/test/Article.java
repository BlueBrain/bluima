//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.09.09 at 01:22:27 PM CEST 
//


package test;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
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
 *       &lt;sequence>
 *         &lt;element ref="{}front"/>
 *         &lt;element ref="{}body" minOccurs="0"/>
 *         &lt;element ref="{}back" minOccurs="0"/>
 *         &lt;element ref="{}floats-group" minOccurs="0"/>
 *         &lt;choice>
 *           &lt;element ref="{}sub-article" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element ref="{}response" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attribute name="article-type" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="dtd-version" default="3.0">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="3.0"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute ref="{http://www.w3.org/XML/1998/namespace}lang default="en""/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "front",
    "body",
    "back",
    "floatsGroup",
    "subArticle",
    "response"
})
@XmlRootElement(name = "article")
public class Article {

    @XmlElement(required = true)
    protected Front front;
    protected Body body;
    protected Back back;
    @XmlElement(name = "floats-group")
    protected FloatsGroup floatsGroup;
    @XmlElement(name = "sub-article")
    protected List<SubArticle> subArticle;
    protected List<Response> response;
    @XmlAttribute(name = "article-type")
    @XmlSchemaType(name = "anySimpleType")
    protected String articleType;
    @XmlAttribute(name = "dtd-version")
    protected String dtdVersion;
    @XmlAttribute(namespace = "http://www.w3.org/XML/1998/namespace")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NMTOKEN")
    protected String lang;

    /**
     * Gets the value of the front property.
     * 
     * @return
     *     possible object is
     *     {@link Front }
     *     
     */
    public Front getFront() {
        return front;
    }

    /**
     * Sets the value of the front property.
     * 
     * @param value
     *     allowed object is
     *     {@link Front }
     *     
     */
    public void setFront(Front value) {
        this.front = value;
    }

    /**
     * Gets the value of the body property.
     * 
     * @return
     *     possible object is
     *     {@link Body }
     *     
     */
    public Body getBody() {
        return body;
    }

    /**
     * Sets the value of the body property.
     * 
     * @param value
     *     allowed object is
     *     {@link Body }
     *     
     */
    public void setBody(Body value) {
        this.body = value;
    }

    /**
     * Gets the value of the back property.
     * 
     * @return
     *     possible object is
     *     {@link Back }
     *     
     */
    public Back getBack() {
        return back;
    }

    /**
     * Sets the value of the back property.
     * 
     * @param value
     *     allowed object is
     *     {@link Back }
     *     
     */
    public void setBack(Back value) {
        this.back = value;
    }

    /**
     * Gets the value of the floatsGroup property.
     * 
     * @return
     *     possible object is
     *     {@link FloatsGroup }
     *     
     */
    public FloatsGroup getFloatsGroup() {
        return floatsGroup;
    }

    /**
     * Sets the value of the floatsGroup property.
     * 
     * @param value
     *     allowed object is
     *     {@link FloatsGroup }
     *     
     */
    public void setFloatsGroup(FloatsGroup value) {
        this.floatsGroup = value;
    }

    /**
     * Gets the value of the subArticle property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the subArticle property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSubArticle().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SubArticle }
     * 
     * 
     */
    public List<SubArticle> getSubArticle() {
        if (subArticle == null) {
            subArticle = new ArrayList<SubArticle>();
        }
        return this.subArticle;
    }

    /**
     * Gets the value of the response property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the response property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getResponse().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Response }
     * 
     * 
     */
    public List<Response> getResponse() {
        if (response == null) {
            response = new ArrayList<Response>();
        }
        return this.response;
    }

    /**
     * Gets the value of the articleType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArticleType() {
        return articleType;
    }

    /**
     * Sets the value of the articleType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArticleType(String value) {
        this.articleType = value;
    }

    /**
     * Gets the value of the dtdVersion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDtdVersion() {
        if (dtdVersion == null) {
            return "3.0";
        } else {
            return dtdVersion;
        }
    }

    /**
     * Sets the value of the dtdVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDtdVersion(String value) {
        this.dtdVersion = value;
    }

    /**
     * Gets the value of the lang property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLang() {
        if (lang == null) {
            return "en";
        } else {
            return lang;
        }
    }

    /**
     * Sets the value of the lang property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLang(String value) {
        this.lang = value;
    }

}
