//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.12.14 at 03:30:44 PM CET 
//


package ch.epfl.bbp.uima.xml.archivearticle3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlMixed;
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
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
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
 *         &lt;element ref="{}inline-graphic"/>
 *         &lt;element ref="{}private-char"/>
 *         &lt;element ref="{}label"/>
 *         &lt;element ref="{}tex-math"/>
 *         &lt;element ref="{http://www.w3.org/1998/Math/MathML}math"/>
 *         &lt;element ref="{}named-content"/>
 *         &lt;element ref="{}styled-content"/>
 *         &lt;element ref="{}sub"/>
 *         &lt;element ref="{}sup"/>
 *       &lt;/choice>
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
@XmlRootElement(name = "textual-form")
public class TextualForm {

    @XmlElementRefs({
        @XmlElementRef(name = "sub", type = Sub.class),
        @XmlElementRef(name = "overline", type = Overline.class),
        @XmlElementRef(name = "sans-serif", type = SansSerif.class),
        @XmlElementRef(name = "monospace", type = Monospace.class),
        @XmlElementRef(name = "roman", type = Roman.class),
        @XmlElementRef(name = "sup", type = Sup.class),
        @XmlElementRef(name = "italic", type = Italic.class),
        @XmlElementRef(name = "sc", type = Sc.class),
        @XmlElementRef(name = "underline-end", type = UnderlineEnd.class),
        @XmlElementRef(name = "named-content", type = NamedContent.class),
        @XmlElementRef(name = "label", type = Label.class),
        @XmlElementRef(name = "inline-graphic", type = InlineGraphic.class),
        @XmlElementRef(name = "tex-math", type = TexMath.class),
        @XmlElementRef(name = "bold", type = Bold.class),
        @XmlElementRef(name = "private-char", type = PrivateChar.class),
        @XmlElementRef(name = "overline-end", type = OverlineEnd.class),
        @XmlElementRef(name = "overline-start", type = OverlineStart.class),
        @XmlElementRef(name = "math", namespace = "http://www.w3.org/1998/Math/MathML", type = JAXBElement.class),
        @XmlElementRef(name = "styled-content", type = StyledContent.class),
        @XmlElementRef(name = "strike", type = Strike.class),
        @XmlElementRef(name = "underline", type = Underline.class),
        @XmlElementRef(name = "underline-start", type = UnderlineStart.class)
    })
    @XmlMixed
    protected List<Object> content;

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
     * {@link Overline }
     * {@link Sub }
     * {@link Monospace }
     * {@link Roman }
     * {@link Sup }
     * {@link Italic }
     * {@link UnderlineEnd }
     * {@link Sc }
     * {@link NamedContent }
     * {@link Label }
     * {@link InlineGraphic }
     * {@link TexMath }
     * {@link Bold }
     * {@link PrivateChar }
     * {@link String }
     * {@link OverlineEnd }
     * {@link OverlineStart }
     * {@link JAXBElement }{@code <}{@link MathType }{@code >}
     * {@link StyledContent }
     * {@link Strike }
     * {@link Underline }
     * {@link UnderlineStart }
     * 
     * 
     */
    public List<Object> getContent() {
        if (content == null) {
            content = new ArrayList<Object>();
        }
        return this.content;
    }

}