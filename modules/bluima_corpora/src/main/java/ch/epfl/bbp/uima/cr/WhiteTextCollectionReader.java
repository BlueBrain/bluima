package ch.epfl.bbp.uima.cr;

import static ch.epfl.bbp.uima.typesystem.TypeSystem.BRAIN_REGION;
import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.Integer.MAX_VALUE;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.uima.UimaContext;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.fit.component.JCasCollectionReader_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceConfigurationException;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Progress;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Text;
import org.jdom2.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.epfl.bbp.uima.BlueUima;
import ch.epfl.bbp.uima.CorporaHelper;
import ch.epfl.bbp.uima.types.BrainRegion;
import de.julielab.jules.types.Header;

/**
 * Collection Reader for the WhiteText brain regions corpus
 * (http://www.chibi.ubc.ca/WhiteText/). This corpus has 17,585 brain region
 * mentions.<br>
 * Paper: French L, Lane S, Xu L and Pavlidis P (2009) Automated recognition of
 * brain region mentions in neuroscience literature. Front. Neuroinform. 3:29.
 * doi:10.3389/neuro.11.029.2009
 * 
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability(outputs = BRAIN_REGION)
public class WhiteTextCollectionReader extends JCasCollectionReader_ImplBase {
    Logger LOG = LoggerFactory.getLogger(WhiteTextCollectionReader.class);

    @ConfigurationParameter(name = BlueUima.PARAM_CORPUS_PATH, mandatory = false)
    private String corpus; // not needed...

    @ConfigurationParameter(name = BlueUima.PARAM_MAX_NR_RESULTS, //
    defaultValue = MAX_VALUE + "", mandatory = false)
    private int maxNrDocs;

    private int currentNrDocs = 0;
    private Iterator<Element> articleIt;

    @SuppressWarnings("unchecked")
    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {

        try {
            if (corpus == null) {
                corpus = CorporaHelper.CORPORA_HOME
                        + "/src/main/resources/pear_resources/whitetext/WhiteText.1.3.xml";
            }
            checkArgument(new File(corpus).exists());
            InputStream corpusIs = new FileInputStream(corpus);

            SAXBuilder builder = new SAXBuilder();
            Document doc = builder.build(corpusIs);
            Element rootNode = doc.getRootElement();

            articleIt = rootNode.getChildren("PubmedArticle").iterator();

        } catch (Exception e) {
            throw new ResourceInitializationException(
                    ResourceConfigurationException.NONEXISTENT_PARAMETER,
                    new Object[] { corpus });
        }
    }

    public void getNext(JCas jcas) throws IOException, CollectionException {

        Element articleE = articleIt.next();
        String pmid = articleE.getChildText("PMID");
        LOG.trace("processing pmId {}", pmid);
        currentNrDocs++;

        StringBuilder sb = new StringBuilder();

        int i = addAnnotations(jcas, articleE.getChild("ArticleTitle")
                .getContent(), sb, 0);

        sb.append(" ");// add "space"
        i++;

        addAnnotations(jcas, articleE.getChild("AbstractText").getContent(),
                sb, i);

        jcas.setDocumentText(sb.toString());

        Header h = new Header(jcas);
        h.setDocId(pmid);
        h.addToIndexes();
    }

    private Integer addAnnotations(JCas jcas, List<?> children,
            StringBuilder sb, Integer i) {
        for (Object child : children) {

            if (child instanceof Element) {
                Element childE = (Element) child;
                checkArgument(childE.getName().equals("BrainRegion"));
                String text = childE.getText();

                BrainRegion br = new BrainRegion(jcas, i, i + text.length());
                br.addToIndexes();

                i += text.length();
                sb.append(text);
                //System.err.print("[" + text + "]");

            } else if (child instanceof Text) {
                Text text = (Text) child;
                i += text.getText().length();
                sb.append(text.getText());
                //System.err.print("[" + text.getText() + "]");

            } else {
                throw new RuntimeException("no match wit Element " + child);
            }
        }
        return i;
    }

    public boolean hasNext() throws IOException, CollectionException {
        return articleIt.hasNext() && (currentNrDocs < maxNrDocs);
    }

    public Progress[] getProgress() {// nope
        return null;
    }
}
