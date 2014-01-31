package ch.epfl.bbp.nlp.ie.proteinconc.ae;

import static java.util.regex.Pattern.compile;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import ch.epfl.bbp.uima.types.Figure;

/**
 * A simple rule-based annotator which detect figure references like 'Fig. 3.2',
 * 'Figure 4.5.3.5', etc.
 *
 * @author Philémon Favrod (philemon.favrod@epfl.ch)
 */
public class FigureTitleAnnotator extends JCasAnnotator_ImplBase{

	private List<Pattern> patterns;
	
	public void initialize(UimaContext context) 
			throws ResourceInitializationException 
	{
		patterns = Arrays.asList(
					compile("([Ff]ig\\.?(?: (?:\\d+\\.?)+))"),
					compile("([Ff]igure(?: (?:\\d+\\.?)+))")
				);
	};
	
	@Override
	public void process(JCas cas) throws AnalysisEngineProcessException {
		for (Pattern pattern : patterns) {
			Matcher matcher = pattern.matcher(cas.getDocumentText());
			if (matcher.find()) {
				
				int matchesCount = matcher.groupCount();
				
				for (int i = 0; i < matchesCount; i++) {
					int begin = matcher.start(i);
					int end = matcher.end(i);
					new Figure(cas, begin, end).addToIndexes();
				}
			}
		}
	}

}
