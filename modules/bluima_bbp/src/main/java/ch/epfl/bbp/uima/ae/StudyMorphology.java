package ch.epfl.bbp.uima.ae;

import static org.apache.uima.fit.util.JCasUtil.select;
import static org.apache.uima.fit.util.JCasUtil.selectCovered;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;

import ch.epfl.bbp.uima.types.BrainRegionDictTerm;
import de.julielab.jules.types.Token;

/**
 * after extracting BR with NName lex-ner or BrainNER, print each BR token with
 * its POS
 * 
 * @author renaud.richardet@epfl.ch
 */
public class StudyMorphology extends JCasAnnotator_ImplBase {

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {

        for (BrainRegionDictTerm br : select(jCas, BrainRegionDictTerm.class)) {

            for (Token t : selectCovered(Token.class, br)) {
                System.out.println(t.getCoveredText() + "\t" + t.getPos());
            }
        }
    }
}