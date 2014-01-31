package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.BlueCasUtil.getHeaderIntDocId;
import static ch.epfl.bbp.uima.BlueUima.PARAM_VIEW;
import static ch.epfl.bbp.uima.BlueUima.VIEW_SYSTEM;
import static ch.epfl.bbp.uima.ae.BannerHelper.BANNER_ROOT;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.PROTEIN;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.SENTENCE;
import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CASException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import banner.BannerProperties;
import banner.Sentence;
import banner.processing.PostProcessor;
import banner.tagging.CRFTagger;
import banner.tagging.Mention;
import banner.tokenization.Tokenizer;
import ch.epfl.bbp.StringUtils;
import ch.epfl.bbp.uima.types.Protein;

/**
 * Protein NER that uses BANNER (http://cbioc.eas.asu.edu/banner/). BANNER is a
 * named entity recognition system, primarily intended for biomedical text. It
 * is a machine-learning system based on conditional random fields and contains
 * a wide survey of the best features in recent literature on biomedical named
 * entity recognition (NER). It relies on a CRF model (Mallet). The list of
 * features can be found in {@link CRFTagger} <br>
 * BANNER achieves a F1 of 84.92 on BioCreative2<br>
 * It is the subject of the following paper: Leaman, R. & Gonzalez G. (2008)
 * BANNER: An executable survey of advances in biomedical named entity
 * recognition. Pacific Symposium on Biocomputing 13:652-663(2008)
 * 
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability(inputs = { SENTENCE }, outputs = { PROTEIN })
public class BannerAnnotator extends JCasAnnotator_ImplBase {
    Logger LOG = LoggerFactory.getLogger(BannerAnnotator.class);

    // TODO Phil document why we do this
    @ConfigurationParameter(name = PARAM_VIEW,//
    defaultValue = VIEW_SYSTEM, description = "on which view to process")
    private static String view;

    // LATER configure
    private static final String root = BANNER_ROOT
            + "src/main/resources/pear_resources/";
    private static final String modelFile = root + "models/gene_model_v02.bin";
    private static final String nlpData = root + "nlpdata";
    private static final String bannerProps = root + "banner.properties";

    private File model;
    private BannerProperties properties;
    private Tokenizer bannerTokenizer;
    private CRFTagger crfTagger;
    private PostProcessor postProcessor;

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);

        try {
            model = new File(modelFile);
            checkArgument(model.exists(), "no model found at " + modelFile);

            File props = new File(bannerProps);
            checkArgument(props.exists(), "no properties found at "
                    + bannerProps);

            File root = new File(nlpData);
            checkArgument(root.exists(), "no nlpData found at " + nlpData);

            properties = BannerProperties.load(props.getAbsolutePath(),
                    root.getAbsolutePath() + "/");
            bannerTokenizer = properties.getTokenizer();
            loadTagger();
            crfTagger = CRFTagger.load(model, properties.getLemmatiser(),
                    properties.getPosTagger());
            postProcessor = properties.getPostProcessor();

        } catch (Exception e) {
            throw new ResourceInitializationException(e);
        }
    }

    private void loadTagger() throws IOException {
    }

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {

        // to allow working on a different view
        JCas viewJCas = jCas;
        if (!view.equals(VIEW_SYSTEM)) {
            try {
                viewJCas = jCas.getView(view);
            } catch (CASException e) {// just rethrow
                throw new AnalysisEngineProcessException(e);
            }
        }

        Collection<de.julielab.jules.types.Sentence> sentences = select(
                viewJCas, de.julielab.jules.types.Sentence.class);

        for (de.julielab.jules.types.Sentence julesSentence : sentences) {
            int sentence_start = julesSentence.getBegin();

            if (julesSentence.getCoveredText().length() > 0) {

                try {
                    Sentence sentence = new Sentence(
                            julesSentence.getCoveredText());
                    bannerTokenizer.tokenize(sentence);
                    crfTagger.tag(sentence);// error
                    if (postProcessor != null)
                        postProcessor.postProcess(sentence);

                    for (Mention mention : sentence.getMentions()) {

                        int startChar = mention.getStartChar() + sentence_start;
                        int endChar = mention.getEndChar() + sentence_start;
                        // LOG.debug("found NE:" + mention.getText() + " " +
                        // startChar
                        // + ":" + endChar);
                        Protein prot = new Protein(viewJCas, startChar, endChar);
                        prot.setName(mention.getText());
                        prot.setTextualRepresentation("⊂PROT⊃");
                        prot.addToIndexes();
                    }

                } catch (Throwable t) {
                    // not sure why, but this happens sometimes after some time
                    int docId = getHeaderIntDocId(viewJCas);
                    LOG.warn("Banner exception at docId {}, skipping. {}",
                            docId, StringUtils.print(t));
                    try {
                        GarbageCollectorAnnotator.runGC();
                        loadTagger(); // reload
                    } catch (Exception e) {
                        throw new AnalysisEngineProcessException(e);
                    }
                }
            }
        }
    }
}
