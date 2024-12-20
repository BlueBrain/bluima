//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.12.14 at 03:30:44 PM CET 
//


package ch.epfl.bbp.uima.xml.archivearticle3;

import java.util.ArrayList;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlMixed;
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
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element ref="{}email"/>
 *         &lt;element ref="{}ext-link"/>
 *         &lt;element ref="{}uri"/>
 *         &lt;element ref="{}inline-supplementary-material"/>
 *         &lt;element ref="{}related-article"/>
 *         &lt;element ref="{}related-object"/>
 *         &lt;element ref="{}hr"/>
 *         &lt;element ref="{}bold"/>
 *         &lt;element ref="{}italic"/>
 *         &lt;element ref="{}monospace"/>
 *         &lt;element ref="{}overline"/>
 *         &lt;element ref="{}overline-start"/>
 *         &lt;element ref="{}overline-end"/>
 *         &lt;element ref="{}roman"/>
 *         &lt;element ref="{}sans-serif"/>
 *         &lt;element ref="{}sc"/>
 *         &lt;element ref="{}strike"/>
 *         &lt;element ref="{}underline"/>
 *         &lt;element ref="{}underline-start"/>
 *         &lt;element ref="{}underline-end"/>
 *         &lt;element ref="{}alternatives"/>
 *         &lt;element ref="{}inline-graphic"/>
 *         &lt;element ref="{}private-char"/>
 *         &lt;element ref="{}chem-struct"/>
 *         &lt;element ref="{}inline-formula"/>
 *         &lt;element ref="{}tex-math"/>
 *         &lt;element ref="{http://www.w3.org/1998/Math/MathML}math"/>
 *         &lt;element ref="{}abbrev"/>
 *         &lt;element ref="{}milestone-end"/>
 *         &lt;element ref="{}milestone-start"/>
 *         &lt;element ref="{}named-content"/>
 *         &lt;element ref="{}styled-content"/>
 *         &lt;element ref="{}fn"/>
 *         &lt;element ref="{}target"/>
 *         &lt;element ref="{}xref"/>
 *         &lt;element ref="{}sub"/>
 *         &lt;element ref="{}sup"/>
 *         &lt;element ref="{}x"/>
 *         &lt;element ref="{}address"/>
 *         &lt;element ref="{}array"/>
 *         &lt;element ref="{}boxed-text"/>
 *         &lt;element ref="{}chem-struct-wrap"/>
 *         &lt;element ref="{}fig"/>
 *         &lt;element ref="{}fig-group"/>
 *         &lt;element ref="{}graphic"/>
 *         &lt;element ref="{}media"/>
 *         &lt;element ref="{}preformat"/>
 *         &lt;element ref="{}supplementary-material"/>
 *         &lt;element ref="{}table-wrap"/>
 *         &lt;element ref="{}table-wrap-group"/>
 *         &lt;element ref="{}disp-formula"/>
 *         &lt;element ref="{}disp-formula-group"/>
 *         &lt;element ref="{}def-list"/>
 *         &lt;element ref="{}list"/>
 *         &lt;element ref="{}ack"/>
 *         &lt;element ref="{}disp-quote"/>
 *         &lt;element ref="{}speech"/>
 *         &lt;element ref="{}statement"/>
 *         &lt;element ref="{}verse-group"/>
 *       &lt;/choice>
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *       &lt;attribute name="content-type" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="specific-use" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute ref="{http://www.w3.org/1999/xlink}type"/>
 *       &lt;attribute ref="{http://www.w3.org/1999/xlink}href"/>
 *       &lt;attribute ref="{http://www.w3.org/1999/xlink}role"/>
 *       &lt;attribute ref="{http://www.w3.org/1999/xlink}title"/>
 *       &lt;attribute ref="{http://www.w3.org/1999/xlink}show"/>
 *       &lt;attribute ref="{http://www.w3.org/1999/xlink}actuate"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "content"
})
@XmlRootElement(name = "named-content")
public class NamedContent {

    @XmlElementRefs({
        @XmlElementRef(name = "sans-serif", type = SansSerif.class),
        @XmlElementRef(name = "graphic", type = Graphic.class),
        @XmlElementRef(name = "ext-link", type = ExtLink.class),
        @XmlElementRef(name = "roman", type = Roman.class),
        @XmlElementRef(name = "boxed-text", type = BoxedText.class),
        @XmlElementRef(name = "address", type = Address.class),
        @XmlElementRef(name = "underline-end", type = UnderlineEnd.class),
        @XmlElementRef(name = "table-wrap-group", type = TableWrapGroup.class),
        @XmlElementRef(name = "bold", type = Bold.class),
        @XmlElementRef(name = "private-char", type = PrivateChar.class),
        @XmlElementRef(name = "inline-formula", type = InlineFormula.class),
        @XmlElementRef(name = "alternatives", type = Alternatives.class),
        @XmlElementRef(name = "overline-start", type = OverlineStart.class),
        @XmlElementRef(name = "underline-start", type = UnderlineStart.class),
        @XmlElementRef(name = "milestone-end", type = MilestoneEnd.class),
        @XmlElementRef(name = "inline-graphic", type = InlineGraphic.class),
        @XmlElementRef(name = "hr", type = Hr.class),
        @XmlElementRef(name = "chem-struct", type = ChemStruct.class),
        @XmlElementRef(name = "overline-end", type = OverlineEnd.class),
        @XmlElementRef(name = "table-wrap", type = TableWrap.class),
        @XmlElementRef(name = "email", type = Email.class),
        @XmlElementRef(name = "abbrev", type = Abbrev.class),
        @XmlElementRef(name = "ack", type = Ack.class),
        @XmlElementRef(name = "disp-formula", type = DispFormula.class),
        @XmlElementRef(name = "math", namespace = "http://www.w3.org/1998/Math/MathML", type = JAXBElement.class),
        @XmlElementRef(name = "statement", type = Statement.class),
        @XmlElementRef(name = "underline", type = Underline.class),
        @XmlElementRef(name = "monospace", type = Monospace.class),
        @XmlElementRef(name = "array", type = Array.class),
        @XmlElementRef(name = "fig-group", type = FigGroup.class),
        @XmlElementRef(name = "media", type = Media.class),
        @XmlElementRef(name = "milestone-start", type = MilestoneStart.class),
        @XmlElementRef(name = "xref", type = Xref.class),
        @XmlElementRef(name = "x", type = X.class),
        @XmlElementRef(name = "fig", type = Fig.class),
        @XmlElementRef(name = "sup", type = Sup.class),
        @XmlElementRef(name = "italic", type = Italic.class),
        @XmlElementRef(name = "sc", type = Sc.class),
        @XmlElementRef(name = "supplementary-material", type = SupplementaryMaterial.class),
        @XmlElementRef(name = "related-object", type = RelatedObject.class),
        @XmlElementRef(name = "disp-formula-group", type = DispFormulaGroup.class),
        @XmlElementRef(name = "target", type = Target.class),
        @XmlElementRef(name = "disp-quote", type = DispQuote.class),
        @XmlElementRef(name = "list", type = ch.epfl.bbp.uima.xml.archivearticle3.List.class),
        @XmlElementRef(name = "chem-struct-wrap", type = ChemStructWrap.class),
        @XmlElementRef(name = "related-article", type = RelatedArticle.class),
        @XmlElementRef(name = "styled-content", type = StyledContent.class),
        @XmlElementRef(name = "strike", type = Strike.class),
        @XmlElementRef(name = "fn", type = Fn.class),
        @XmlElementRef(name = "overline", type = Overline.class),
        @XmlElementRef(name = "sub", type = Sub.class),
        @XmlElementRef(name = "speech", type = Speech.class),
        @XmlElementRef(name = "preformat", type = Preformat.class),
        @XmlElementRef(name = "verse-group", type = VerseGroup.class),
        @XmlElementRef(name = "named-content", type = NamedContent.class),
        @XmlElementRef(name = "tex-math", type = TexMath.class),
        @XmlElementRef(name = "inline-supplementary-material", type = InlineSupplementaryMaterial.class),
        @XmlElementRef(name = "def-list", type = DefList.class),
        @XmlElementRef(name = "uri", type = Uri.class)
    })
    @XmlMixed
    protected java.util.List<Object> content;
    @XmlAttribute(name = "id")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;
    @XmlAttribute(name = "content-type", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String contentType;
    @XmlAttribute(name = "specific-use")
    @XmlSchemaType(name = "anySimpleType")
    protected String specificUse;
    @XmlAttribute(name = "type", namespace = "http://www.w3.org/1999/xlink")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String type;
    @XmlAttribute(name = "href", namespace = "http://www.w3.org/1999/xlink")
    @XmlSchemaType(name = "anySimpleType")
    protected String href;
    @XmlAttribute(name = "role", namespace = "http://www.w3.org/1999/xlink")
    @XmlSchemaType(name = "anySimpleType")
    protected String role;
    @XmlAttribute(name = "title", namespace = "http://www.w3.org/1999/xlink")
    @XmlSchemaType(name = "anySimpleType")
    protected String title;
    @XmlAttribute(name = "show", namespace = "http://www.w3.org/1999/xlink")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String show;
    @XmlAttribute(name = "actuate", namespace = "http://www.w3.org/1999/xlink")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String actuate;

    /**
     * Gets the value of the content property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the content property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SansSerif }
     * {@link Graphic }
     * {@link ExtLink }
     * {@link Roman }
     * {@link BoxedText }
     * {@link Address }
     * {@link UnderlineEnd }
     * {@link TableWrapGroup }
     * {@link Bold }
     * {@link PrivateChar }
     * {@link InlineFormula }
     * {@link Alternatives }
     * {@link OverlineStart }
     * {@link UnderlineStart }
     * {@link MilestoneEnd }
     * {@link InlineGraphic }
     * {@link Hr }
     * {@link ChemStruct }
     * {@link OverlineEnd }
     * {@link TableWrap }
     * {@link Email }
     * {@link Abbrev }
     * {@link Ack }
     * {@link DispFormula }
     * {@link JAXBElement }{@code <}{@link MathType }{@code >}
     * {@link Statement }
     * {@link Underline }
     * {@link Monospace }
     * {@link Array }
     * {@link FigGroup }
     * {@link Media }
     * {@link MilestoneStart }
     * {@link Xref }
     * {@link X }
     * {@link Fig }
     * {@link Sup }
     * {@link Italic }
     * {@link Sc }
     * {@link SupplementaryMaterial }
     * {@link RelatedObject }
     * {@link DispFormulaGroup }
     * {@link Target }
     * {@link DispQuote }
     * {@link ch.epfl.bbp.uima.xml.archivearticle3.List }
     * {@link ChemStructWrap }
     * {@link RelatedArticle }
     * {@link StyledContent }
     * {@link Strike }
     * {@link Fn }
     * {@link Sub }
     * {@link Overline }
     * {@link Speech }
     * {@link Preformat }
     * {@link NamedContent }
     * {@link VerseGroup }
     * {@link TexMath }
     * {@link String }
     * {@link InlineSupplementaryMaterial }
     * {@link DefList }
     * {@link Uri }
     * 
     * 
     */
    public java.util.List<Object> getContent() {
        if (content == null) {
            content = new ArrayList<Object>();
        }
        return this.content;
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

    /**
     * Gets the value of the specificUse property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpecificUse() {
        return specificUse;
    }

    /**
     * Sets the value of the specificUse property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpecificUse(String value) {
        this.specificUse = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Gets the value of the href property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHref() {
        return href;
    }

    /**
     * Sets the value of the href property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHref(String value) {
        this.href = value;
    }

    /**
     * Gets the value of the role property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the value of the role property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRole(String value) {
        this.role = value;
    }

    /**
     * Gets the value of the title property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitle(String value) {
        this.title = value;
    }

    /**
     * Gets the value of the show property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShow() {
        return show;
    }

    /**
     * Sets the value of the show property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShow(String value) {
        this.show = value;
    }

    /**
     * Gets the value of the actuate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActuate() {
        return actuate;
    }

    /**
     * Sets the value of the actuate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActuate(String value) {
        this.actuate = value;
    }

}
