package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.BlueUima.PARAM_CASE_SENSITIVE;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.KEEP;
import static org.apache.uima.fit.util.JCasUtil.select;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.TypeCapability;

import ch.epfl.bbp.uima.types.Keep;
import de.julielab.jules.types.Token;

/**
 * Sets {@link Keep#setNormalizedText()}  for every {@link Keep} annotation 
 * to the lemmatization of its covered text.
 * Expects that {@link Token} themselves have already been lemmatized (with
 * {@link Token#setLemmaStr()})
 * 
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability(inputs = { KEEP }, outputs = { KEEP })
public class BioLemmatizerNormalizerAnnotator extends JCasAnnotator_ImplBase {
    private static Logger LOG = LoggerFactory
            .getLogger(BioLemmatizerNormalizerAnnotator.class);

    @ConfigurationParameter(name = PARAM_CASE_SENSITIVE, defaultValue = "false",//
    description = "If true, tokens are not normalized to lowercase before string comparisions")
    private boolean caseSensitive;
    
    @ConfigurationParameter(name = "onlyTokens", defaultValue = "false",//
    description = "Only lemmatize the Keeps that are Tokens, rest are left unchanged.")
    private boolean onlyTokens;

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);
        // warmup lemmatizer
        BlueBioLemmatizer.lemmatize("warmup", "");
    }

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {

        for (Keep k : select(jCas, Keep.class)) {

            Annotation a = k.getEnclosedAnnot();   
            String normalized = null;
            
            // sometimes, Tokens already have a lemma form, use this one.
            if (a instanceof Token) {
                normalized = ((Token) a).getLemmaStr();
                if (!caseSensitive){
                	normalized = normalized.toLowerCase();
                }
                k.setNormalizedText(normalized);
            }
            else if(!onlyTokens) {
	            // Biolemmatizer
	            try {
	            	normalized = BlueBioLemmatizer.lemmatize(a.getCoveredText(), "");               
	            } catch (Exception e) {
	            	LOG.warn("Failed to lemmatize '{}'", a.getCoveredText());
	                normalized = a.getCoveredText().trim();
	            }
	            finally{
	                if (!caseSensitive){
	                	normalized = normalized.toLowerCase();
	                }
	                
	                k.setNormalizedText(normalized);
                }
            }      
        }
    }
}
