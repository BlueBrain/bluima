package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.BlueCasUtil.getHeaderDocId;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.TOKEN;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.WORDNET;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.slf4j.LoggerFactory;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.fit.util.JCasUtil;

import ch.epfl.bbp.uima.BlueUima;
import ch.epfl.bbp.uima.CommonAnnotatorsHelper;
import ch.epfl.bbp.uima.types.WordnetDictTerm;
import de.julielab.jules.types.Token;
import edu.mit.jwi.RAMDictionary;
import edu.mit.jwi.data.ILoadPolicy;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.POS;
import edu.mit.jwi.morph.WordnetStemmer;

/**
 * Wordnet Uima wrapper, using JWI (the MIT Java Wordnet Interface). ATM, find
 * the first match from the lemmatizer. <strong>Needs tokens with POS as
 * input.</strong>
 * 
 * @see http://projects.csail.mit.edu/jwi/
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability(inputs = { TOKEN }, outputs = { WORDNET })
public class WordnetAnnotator extends JCasAnnotator_ImplBase {
    private static org.slf4j.Logger LOG = LoggerFactory
            .getLogger(WordnetAnnotator.class);

    @ConfigurationParameter(name = BlueUima.PARAM_MODEL_FILE, //
    description = "dict file from Wordnet", mandatory = false)
    private String dictFile;

    private RAMDictionary dict;
    private WordnetStemmer stemmer;

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);
        try {
            if (dictFile == null) // use default
                dictFile = CommonAnnotatorsHelper.COMMON_ANNOTATORS_ROOT
                        + "src/main/resources/wordnet-3.1/dict";
            dict = new RAMDictionary(new File(dictFile),
                    ILoadPolicy.BACKGROUND_LOAD);
            dict.open();
            stemmer = new WordnetStemmer(dict);
        } catch (IOException e) {
            throw new ResourceInitializationException(e);
        }
    }

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {

        for (Token t : JCasUtil.select(jCas, Token.class)) {
            try {

                if (t.getPos() != null
                        && POS.getPartOfSpeech(t.getPos().charAt(0)) != null) {
                    POS pos = POS.getPartOfSpeech(t.getPos().charAt(0));
                    List<String> stems = stemmer.findStems(t.getCoveredText(),
                            pos);
                    if (!stems.isEmpty()) {
                        IIndexWord wnWord = dict
                                .getIndexWord(stems.get(0), pos);
                        if (wnWord != null) {
                            WordnetDictTerm wdt = new WordnetDictTerm(jCas,
                                    t.getBegin(), t.getEnd());
                            wdt.setDictCanon(stems.get(0));
                            wdt.setEntityId(wnWord.getID().toString());
                            wdt.addToIndexes();
                        }
                    }
                } else { // indep. of POS (tries them all)
                    for (POS pos : POS.values()) {
                        List<String> stems = stemmer.findStems(
                                t.getCoveredText(), pos);
                        if (!stems.isEmpty()) {
                            IIndexWord wnWord = dict.getIndexWord(stems.get(0),
                                    pos);
                            if (wnWord != null) {
                                WordnetDictTerm wdt = new WordnetDictTerm(jCas,
                                        t.getBegin(), t.getEnd());
                                wdt.setDictCanon(stems.get(0));
                                wdt.setEntityId(wnWord.getID().toString());
                                wdt.addToIndexes();
                            }
                        }
                    }
                }

            } catch (Throwable tr) {
                LOG.debug("Wordnet exception while processing >"
                        + t.getCoveredText() + "< [" + t.getBegin() + ":"
                        + t.getEnd() + "] from doc " + getHeaderDocId(jCas));
            }
        }
    }

    @Override
    public void collectionProcessComplete()
            throws AnalysisEngineProcessException {
        super.collectionProcessComplete();
        dict.close();
    }
}
