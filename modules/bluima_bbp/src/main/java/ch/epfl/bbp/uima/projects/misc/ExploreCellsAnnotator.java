package ch.epfl.bbp.uima.projects.misc;

import static ch.epfl.bbp.uima.BlueCasUtil.isEmptyText;
import static java.util.regex.Pattern.compile;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;

public class ExploreCellsAnnotator extends JCasAnnotator_ImplBase {

    private final Pattern p = compile(".{0,50}(?:neuron|cell)");

    @Override
    public void process(JCas jcas) throws AnalysisEngineProcessException {
        if (!isEmptyText(jcas)) {
            Matcher m = p.matcher(jcas.getDocumentText());
            while (m.find()) {
                System.out.println(m.group());

            }
        }
    }

   
}