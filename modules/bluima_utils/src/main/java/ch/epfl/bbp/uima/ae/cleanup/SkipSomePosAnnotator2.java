package ch.epfl.bbp.uima.ae.cleanup;

import static ch.epfl.bbp.uima.ae.cleanup.DisambiguatorAnnotator.BIO_ANNOTATIONS;
import static com.google.common.collect.Lists.newArrayList;
import static org.apache.uima.fit.util.JCasUtil.select;
import static org.apache.uima.fit.util.JCasUtil.selectCovered;

import java.util.List;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;

import ch.epfl.bbp.uima.BlueCasUtil;
import de.julielab.jules.types.Token;

/**
 * Bluntly removes annotations that cover a word with an uninteresting POS, like
 * DT or TO, IN, ...
 * 
 * @author renaud.richardet@epfl.ch
 */
public class SkipSomePosAnnotator2 extends JCasAnnotator_ImplBase {

	public static final List<String> SKIP_POS = newArrayList("CC", "IN", "PRP",
	        "PRP$", "PDT", "POS", "DT", "WDT", "WP", "WP$", "WRB", "TO", "EX",
	        "LS", "RP");

	@Override
	public void process(JCas jCas) throws AnalysisEngineProcessException {

		for (Token t : select(jCas, Token.class)) {

			// filter by POS
			String pos = t.getPos();
			if (pos == null)
				pos = "UNKNOWN";
			if (SKIP_POS.contains(pos)) {
				for (Annotation a : selectCovered(jCas, Annotation.class,
				        t.getBegin(), t.getEnd())) {
					if (BlueCasUtil.haveSameBeginEnd(t, a)
					        && BIO_ANNOTATIONS.contains(a.getClass()))
						a.removeFromIndexes(jCas);
				}
			}
		}
	}
}