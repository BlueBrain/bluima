//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.03.06 at 04:19:48 PM CET 
//


package ch.epfl.bbp.uima.xml.testcase;

import java.math.BigInteger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ch.epfl.bbp.uima.xml.testcase package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _RawContent_QNAME = new QName("", "rawContent");
    private final static QName _ProteinName_QNAME = new QName("", "proteinName");
    private final static QName _SentenceNo_QNAME = new QName("", "sentenceNo");
    private final static QName _Value_QNAME = new QName("", "value");
    private final static QName _CellTypeName_QNAME = new QName("", "cellTypeName");
    private final static QName _ParagraphId_QNAME = new QName("", "paragraphId");
    private final static QName _DocumentId_QNAME = new QName("", "documentId");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ch.epfl.bbp.uima.xml.testcase
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link TestSuiteCorpus }
     * 
     */
    public TestSuiteCorpus createTestSuiteCorpus() {
        return new TestSuiteCorpus();
    }

    /**
     * Create an instance of {@link Input }
     * 
     */
    public Input createInput() {
        return new Input();
    }

    /**
     * Create an instance of {@link Protein }
     * 
     */
    public Protein createProtein() {
        return new Protein();
    }

    /**
     * Create an instance of {@link Output }
     * 
     */
    public Output createOutput() {
        return new Output();
    }

    /**
     * Create an instance of {@link Tokens }
     * 
     */
    public Tokens createTokens() {
        return new Tokens();
    }

    /**
     * Create an instance of {@link SentencesId }
     * 
     */
    public SentencesId createSentencesId() {
        return new SentencesId();
    }

    /**
     * Create an instance of {@link Concentration }
     * 
     */
    public Concentration createConcentration() {
        return new Concentration();
    }

    /**
     * Create an instance of {@link CellType }
     * 
     */
    public CellType createCellType() {
        return new CellType();
    }

    /**
     * Create an instance of {@link Token }
     * 
     */
    public Token createToken() {
        return new Token();
    }

    /**
     * Create an instance of {@link Result }
     * 
     */
    public Result createResult() {
        return new Result();
    }

    /**
     * Create an instance of {@link Tokenization }
     * 
     */
    public Tokenization createTokenization() {
        return new Tokenization();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "rawContent")
    public JAXBElement<String> createRawContent(String value) {
        return new JAXBElement<String>(_RawContent_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "proteinName")
    public JAXBElement<String> createProteinName(String value) {
        return new JAXBElement<String>(_ProteinName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "sentenceNo")
    public JAXBElement<BigInteger> createSentenceNo(BigInteger value) {
        return new JAXBElement<BigInteger>(_SentenceNo_QNAME, BigInteger.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "value")
    public JAXBElement<String> createValue(String value) {
        return new JAXBElement<String>(_Value_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "cellTypeName")
    public JAXBElement<String> createCellTypeName(String value) {
        return new JAXBElement<String>(_CellTypeName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "paragraphId")
    public JAXBElement<String> createParagraphId(String value) {
        return new JAXBElement<String>(_ParagraphId_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "documentId")
    public JAXBElement<String> createDocumentId(String value) {
        return new JAXBElement<String>(_DocumentId_QNAME, String.class, null, value);
    }

}
