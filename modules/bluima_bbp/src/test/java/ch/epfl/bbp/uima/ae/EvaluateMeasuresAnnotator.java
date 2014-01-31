package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.BlueCasUtil.inspect;
import static java.util.regex.Pattern.compile;
import static org.apache.uima.fit.util.JCasUtil.select;
import static org.apache.uima.fit.util.JCasUtil.selectCovering;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import ch.epfl.bbp.uima.types.DictTerm;
import ch.epfl.bbp.uima.types.Measure;
import ch.epfl.bbp.uima.types.Protein;
import de.julielab.jules.types.Chemical;
import de.julielab.jules.types.Header;

/**
 * Extracts all \d from text.
 * 
 * @see Evaluate2_Numbers
 * @author renaud.richardet@epfl.ch
 */
public class EvaluateMeasuresAnnotator extends JCasAnnotator_ImplBase {

    final static Pattern digits = compile("-?(?:\\d+[\\.,]\\d+|\\d+\\.|\\.\\d+|\\d+)(?:(?:[eE][+-]?\\d+)|(?:x10\\(.{0,4}\\)))?");

    int measuresCnt = 0;

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {
        System.err.println("file: "
                + JCasUtil.selectSingle(jCas, Header.class).getSource());

        measuresCnt += select(jCas, Measure.class).size();

        Matcher matcher = digits.matcher(jCas.getDocumentText());

        while (matcher.find()) {
            // String match = matcher.group();

            if (selectCovering(jCas, Protein.class, matcher.start(),
                    matcher.end()).isEmpty()) {
                if (selectCovering(jCas, Chemical.class, matcher.start(),
                        matcher.end()).isEmpty()) {
                    if (selectCovering(jCas, DictTerm.class, matcher.start(),
                            matcher.end()).isEmpty()) {

                        List<Measure> covered = selectCovering(jCas,
                                Measure.class, matcher.start(), matcher.end());

                        if (covered.isEmpty()) {
                            System.out.print("⨀-Measure: ");
                            System.out.println(inspect(jCas, matcher.start(),
                                    matcher.end()));
                        } else {
                            System.out.println("⨀+Measure:"
                                    + covered.iterator().next()
                                            .getCoveredText());
                        }
                    }
                }
            }
        }
    }

    @Override
    public void collectionProcessComplete()
            throws AnalysisEngineProcessException {
        System.out.println("Total measures: " + measuresCnt);
    }
}