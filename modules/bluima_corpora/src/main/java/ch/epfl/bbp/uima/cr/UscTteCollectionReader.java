package ch.epfl.bbp.uima.cr;

import static ch.epfl.bbp.uima.CorporaHelper.CORPORA_BASE;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.BRAIN_REGION;
import static com.google.common.base.Preconditions.checkArgument;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.apache.uima.UimaContext;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Text;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.epfl.bbp.uima.io.DirectoryIterator;
import ch.epfl.bbp.uima.types.BrainRegion;
import de.julielab.jules.types.Header;

/**
 * Collection Reader for the University of Southern California (USC)
 * Tract-tracing experiments (TTE) corpus.
 * 
 * @author renaud.richardet@epfl.ch
 */
// LATER add other entities
// LATER make PARAM_INPUT_DIRECTORY not mandatory (inherited from
// AbstractFileReader)
@TypeCapability(outputs = BRAIN_REGION)
public class UscTteCollectionReader extends AbstractFileReader {

    private SAXBuilder builder;
    private XMLOutputter xo;
    private XPathExpression<Object> sentenceXPath;

    private int docCnt;

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        inputDir = CORPORA_BASE + "USC_TTE_corpus";
        // inputDir = CORPORA_HOME + "src/test/resources/corpus/USC_TTE_corpus";
        docCnt = 1;
        super.initialize(context);
        try {
            File corpusDir = new File(inputDir);
            checkArgument(corpusDir.exists());
            // duplicating code from AbstractFileReader to add "xml" filtering
            fileIterator = DirectoryIterator.get(directoryIterator, corpusDir,
                    "xml", false);
            builder = new SAXBuilder();
            xo = new XMLOutputter();
            xo.setFormat(Format.getRawFormat());
            sentenceXPath = XPathFactory.instance().compile("//S");
        } catch (Exception e) {
            throw new ResourceInitializationException(
                    ResourceInitializationException.NO_RESOURCE_FOR_PARAMETERS,
                    new Object[] { inputDir });
        }
    }

    public void getNext(JCas jcas) throws IOException, CollectionException {

        File file = fileIterator.next();
        try {
            // LOG.debug("reading {}", file.getName());
            Document doc = builder.build(new FileInputStream(file));
            Element rootNode = doc.getRootElement();

            String title = xo.outputString(rootNode.getChild("Title")
                    .getContent());
            Header h = new Header(jcas);
            h.setSource(file.getName());
            h.setTitle(title);
            h.setDocId(docCnt++ + "");
            h.addToIndexes();

            StringBuilder sb = new StringBuilder();
            int textIndex = 0;

            for (Object sentence : sentenceXPath.evaluate(rootNode)) {
                Element sentenceE = (Element) sentence;

                // skip some classes (titles and bibliography)
                String classStr = sentenceE.getAttribute("class").getValue();
                if (!classStr.matches("(references\\.body|.*heading)")) {

                    textIndex = addAnnotations(jcas, sentenceE.getContent(),
                            sb, textIndex, xo);
                    // space btw sentences
                    textIndex++;
                    sb.append(" ");

                }
            }

            jcas.setDocumentText(sb.toString().trim()); // trim last space

        } catch (JDOMException e) {
            throw new CollectionException(e);
        }
    }

    private static int addAnnotations(JCas jCas, List<?> children,
            StringBuilder sb, Integer txtIndex, XMLOutputter _xo) {

        for (Object child : children) {

            if (child instanceof Element) {
                Element childE = (Element) child;

                // brain region elements
                if (childE.getName().equals("neuroanatomicalLocation")) {
                    String text = childE.getText().replaceAll("[\r\n]", " ")
                            .replaceAll(" +", " ");

                    new BrainRegion(jCas, txtIndex, txtIndex + text.length())
                            .addToIndexes();

                    // LOG.debug("add BR '{}' at {}", text, txtIndex);
                    txtIndex += text.length();
                    sb.append(text);

                } else { // recurse into other elements
                    // LOG.debug("recurse into {}", childE.getName());
                    txtIndex = addAnnotations(jCas, childE.getContent(), sb,
                            txtIndex, _xo);
                }

            } else if (child instanceof Text) {
                Text textE = (Text) child;
                String text = _xo.outputString(textE).replaceAll("[\r\n]", " ")
                        .replaceAll(" +", " ");
                // LOG.debug("add text '{}' at {}", text, txtIndex);
                txtIndex += text.length();
                sb.append(text);
            }
        }
        return txtIndex;
    }
}
