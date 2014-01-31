package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.typesystem.TypeSystem.TOKEN;

import java.util.Collection;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.fit.util.JCasUtil;

import ch.epfl.bbp.uima.BlueCasUtil;
import de.julielab.jules.types.Token;
import edu.ucdenver.ccp.nlp.biolemmatizer.BioLemmatizer;
import edu.ucdenver.ccp.nlp.biolemmatizer.LemmataEntry;
import edu.ucdenver.ccp.uima.shims.annotation.AnnotationDataExtractor;

/**
 * This annotator processes tokens in the CAS and inserts corresponding lemmas.
 * 
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability(inputs = { TOKEN }, outputs = { TOKEN + ":lemmaStr" })
public class BlueBioLemmatizer extends JCasAnnotator_ImplBase {

    /**
     * This process(JCas) method cycles through all annotations in the CAS. For
     * those that are identified as tokens by {@link AnnotationDataExtractor}
     * implementation being used, an attempt is made to extract part-of-speech
     * information. The covered text for each token is then lemmatized using the
     * {@link BioLemmatizer}, using the part-of-speech information if it was
     * available.
     */
    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {
        for (Token t : JCasUtil.select(jCas, Token.class)) {
            String pos = BlueCasUtil.getSinglePosTag(t);
            String lemma = lemmatize(t.getCoveredText(), pos);
            if (lemma != null)
                t.setLemmaStr(lemma);
        }
    }

    /**
     * This {@link BioLemmatizer} will do the bulk of the work in the
     * {@link BlueBioLemmatizer#process(JCas)} method
     */
    private static BioLemmatizer bioLemmatizer = null;

    /** Just pick the first lemma (if any) */
    public synchronized static String lemmatize(String text, String posTag) {
        LemmataEntry lemmata = getLemmatizer().lemmatizeByLexiconAndRules(text,
                posTag);

        Collection<edu.ucdenver.ccp.nlp.biolemmatizer.LemmataEntry.Lemma> lemmas = lemmata
                .getLemmas();
        if (!lemmas.isEmpty()) {
            edu.ucdenver.ccp.nlp.biolemmatizer.LemmataEntry.Lemma lemma = lemmas
                    .iterator().next();
            return lemma.getLemma();
        } else {
            return null;
        }
    }

    private static BioLemmatizer getLemmatizer() {
        if (bioLemmatizer == null)
            bioLemmatizer = new BioLemmatizer();
        return bioLemmatizer;
    }
}
