package ch.epfl.bbp.uima.ae;

import java.util.Collection;
import java.util.Map;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;

import com.wcohen.ss.abbvGapsHmm.Acronym;

import de.julielab.jules.types.Abbreviation;

/**
 * Finds abbreviations in text, using a HMM model from SecondString, trained on
 * biomedical text. Compared to {@link AbbreviationsAnnotator}, <strong>Only
 * annotates the first occurrence of the abbreviation</strong>.
 * 
 * @see https://github.com/TeamCohen/secondstring
 * @author renaud.richardet@epfl.ch
 */
public class AbbreviationsConservativeAnnotator extends AbbreviationsAnnotator {

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {
        String txt = jCas.getDocumentText();

        Collection<Acronym> all_predictions = model.predict(txt);
        Map<String, Acronym> final_predictions = model
                .acronymsArrayToMap(all_predictions);

        for (String k : final_predictions.keySet()) {
            Acronym acro = final_predictions.get(k);
            LOG.debug("Acronym: {} start:{}", acro._shortForm + " :: "
                    + acro._longForm, acro._start);

            int occurence = acro._start;
            Abbreviation abrev = new Abbreviation(jCas, occurence, occurence
                    + acro._shortForm.length());
            abrev.setExpan(acro._longForm);
            abrev.setDefinedHere(true);
            abrev.addToIndexes();
        }
    }
}
