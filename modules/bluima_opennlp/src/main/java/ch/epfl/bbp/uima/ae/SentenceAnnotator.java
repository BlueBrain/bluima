package ch.epfl.bbp.uima.ae;

import static org.apache.commons.lang.StringUtils.isBlank;

import org.apache.uima.UimaContext;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.epfl.bbp.nlp.ModelProxy;
import ch.epfl.bbp.nlp.ModelStream;
import ch.epfl.bbp.shaded.opennlp.tools.lang.english.SentenceDetector;
import ch.epfl.bbp.uima.BlueUima;
import ch.epfl.bbp.uima.ScriptingShortcut;
import ch.epfl.bbp.uima.typesystem.TypeSystem;
import de.julielab.jules.types.Sentence;

/**
 * Sentence splitter, based on OpenNLP's MaxEnt {@link SentenceDetector}, and
 * trained on biomedical corpora (PennBio or Genia corpora).
 * 
 * @author buyko
 * @author renaud.richardet@epfl.ch
 */
@ScriptingShortcut(shortcut = "ch.epfl.bbp.uima.ae.OpenNlpHelper.getSentenceSplitter()")
@TypeCapability(outputs = { TypeSystem.SENTENCE })
public class SentenceAnnotator extends JCasAnnotator_ImplBase {
    private static Logger LOG = LoggerFactory
            .getLogger(SentenceAnnotator.class);

    @ConfigurationParameter(name = BlueUima.PARAM_MODEL)
    private String model;

    /** component id for CAS */
    private static final String COMPONENT_ID = "de.julielab.types.OpenNLPSentenceDetector";

    /** OpenNLP SentenceDetector instance */
    private ch.epfl.bbp.shaded.opennlp.tools.lang.english.SentenceDetector sentenceSplitter;

    @Override
    public void initialize(UimaContext aContext)
            throws ResourceInitializationException {
        super.initialize(aContext);

        // LOG.debug("initializing OpenNLP Sentence Annotator, using model at {}",
        // modelFile);

        try {
            ModelStream modelStream = ModelProxy.getStream(model);
            // TODO couldn't we use OpenNlpHelper.getSentenceDetector ?
            sentenceSplitter = new ch.epfl.bbp.shaded.opennlp.tools.lang.english.SentenceDetector(
                    modelStream);
        } catch (Exception e) {
            LOG.error("[OpenNLP Sentence Annotator] Could not load sentence splitter model: "
                    + e.getMessage());
            throw new ResourceInitializationException(e);
        }
    }

    @Override
    public void process(JCas aJCas) {
        // LOG.trace(" [OpenNLP Sentence Annotator] processing document ...");
        String text = aJCas.getDocumentText();
        if (!isBlank(text)) {
            int sentenceOffsets[] = sentenceSplitter.sentPosDetect(text);

            int begin = 0;
            int end = 0;
            for (int i = 0; i < sentenceOffsets.length; i++) {
                end = begin
                        + (text.substring(begin, sentenceOffsets[i]).trim())
                                .length();
                Sentence sentence = new Sentence(aJCas, begin, end);
                begin = sentenceOffsets[i];
                sentence.setComponentId(COMPONENT_ID);
                sentence.addToIndexes();
            }
        }
    }
}
