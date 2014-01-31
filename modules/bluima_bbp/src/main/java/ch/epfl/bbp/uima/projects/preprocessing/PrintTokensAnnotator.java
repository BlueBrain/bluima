package ch.epfl.bbp.uima.projects.preprocessing;

import static ch.epfl.bbp.uima.BlueCasUtil.getHeaderIntDocId;
import static ch.epfl.bbp.uima.BlueCasUtil.getHeaderSource;
import static org.apache.uima.fit.util.JCasUtil.select;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.OperationalProperties;

import de.julielab.jules.types.Token;

/**
 * Util to inspect quality of preprocessing, and print tokens
 * 
 * @author renaud.richardet@epfl.ch
 */
@OperationalProperties(multipleDeploymentAllowed = false)
public class PrintTokensAnnotator extends JCasAnnotator_ImplBase {

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {

        int pmId = getHeaderIntDocId(jCas);
        String source = getHeaderSource(jCas);
        System.out.println("\nsource:" + source + " pmId:" + pmId);

        for (Token t : select(jCas, Token.class)) {
            String ttext = t.getCoveredText();
            System.out.print("⊣" + ttext + "⊢");
        }
    }
}