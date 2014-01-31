package ch.epfl.bbp.uima.filter;

import static ch.epfl.bbp.uima.BlueUima.PARAM_CASE_SENSITIVE;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.KEEP;
import static org.apache.uima.fit.util.JCasUtil.select;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.TypeCapability;

import ch.epfl.bbp.uima.types.Keep;
import de.julielab.jules.types.Token;

/**
 * Lemmatizes every {@link Keep} annotation and sets its
 * {@link Keep#setNormalizedText()}, by (just) trimming the annotation's text.
 * 
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability(inputs = { KEEP }, outputs = { KEEP })
public class SimpleNormalizerAnnotator extends JCasAnnotator_ImplBase {

    @ConfigurationParameter(name = PARAM_CASE_SENSITIVE, defaultValue = "false",//
    description = "If true, tokens are not normalized to lowercase before string comparisions")
    private boolean caseSensitive;

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {

        for (Keep k : select(jCas, Keep.class)) {

            Annotation a = k.getEnclosedAnnot();
            String normalized = null;

            // sometimes, Tokens already have a lemma form, use this one.
            if (a instanceof Token) {
                normalized = ((Token) a).getLemmaStr();
            }

            // Simple normalizer
            if (normalized == null)
                normalized = a.getCoveredText().trim();

            if (!caseSensitive)
                normalized = normalized.toLowerCase();

            k.setNormalizedText(normalized);
        }
    }
}
