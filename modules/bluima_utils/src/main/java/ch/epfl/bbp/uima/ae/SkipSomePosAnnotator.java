package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.typesystem.TypeSystem.POS_TAG;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.POS_VERB;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.TOKEN;
import static com.google.common.collect.Sets.newHashSet;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.util.Set;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.jcas.JCas;

import ch.epfl.bbp.uima.types.POSSkip;
import ch.epfl.bbp.uima.types.POSVerb;
import ch.epfl.bbp.uima.types.POSWh;
import de.julielab.jules.types.Token;

/**
 * Annotate (flags) tokens that have an "uninteresting" POS, like V.., W.., DT
 * or TO, IN, ...
 * 
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability(inputs = TOKEN, outputs = { POS_VERB, POS_TAG })
public class SkipSomePosAnnotator extends JCasAnnotator_ImplBase {

    public static final Set<String> POS_SKIP = newHashSet("CC", "DT", "EX", "IN", 
    		"LS", "MD", "PDT", "POS", "PRP", "PRP$", "RP",  "TO");

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {

        for (Token t : select(jCas, Token.class)) {

            // filter by POS
            String pos = t.getPos();
            if (pos == null)
                pos = "UNKNOWN";

            // switch
            if (pos.startsWith("V")) { // verb
                (new POSVerb(jCas, t.getBegin(), t.getEnd())).addToIndexes();

            } else if (pos.startsWith("W")) { // Wh-thing
                (new POSWh(jCas, t.getBegin(), t.getEnd())).addToIndexes();
            
//            } else if (pos.startsWith("R")) { // Wh-thing
//               (new POSAdverb(jCas, t.getBegin(), t.getEnd())).addToIndexes();

            } else if (POS_SKIP.contains(pos)) { // to be skipped
                (new POSSkip(jCas, t.getBegin(), t.getEnd())).addToIndexes();
            }
        }
    }
}