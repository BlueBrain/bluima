package ch.epfl.bbp.nlp.ie.proteinconc.ae;

import ch.epfl.bbp.uima.types.Protein;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.jcas.JCas;

import java.util.LinkedList;
import java.util.List;

import static ch.epfl.bbp.uima.typesystem.TypeSystem.PROTEIN;
import static org.apache.uima.fit.util.JCasUtil.select;

/**
 * The distinction between proteins and some molecules in BANNER
 * is confusing. That is, the annotator below intend to supress
 * the most frequent molecule annotated as Protein by BANNER.
 */
@TypeCapability(inputs={ PROTEIN})
public class MoleculeFilter extends JCasAnnotator_ImplBase {

    //if a protein matches one of those regexp, it is removed from indexes
    private final static String[] filteringRegexes =
            {
                    "^NaHCO$",
                    "^CaC[1IlL]2?$",
                    "^(?:Mg)?C[1IlL]2?$",
                    "^KC[1IlL]$",
                    "^NaC[1IlL]$",
                    "^NaH[1-9]?\\s?[CP]O[1-9]?$",
                    "^Na3VO4$",
                    "^MgSO[1-9]*$",
                    "^C5$",
                    "^KHPO4?$",
                    "^Na4P2O7$"
            };

    @Override
    public void process(JCas cas) throws AnalysisEngineProcessException {
        List<Protein> proteinsToRemove = new LinkedList<Protein>();

        for (Protein protein : select(cas, Protein.class)) {
            for (String filteringRegex : filteringRegexes) {

                if (protein.getCoveredText().trim().matches(filteringRegex)) {
                    proteinsToRemove.add(protein);
                }

            }
        }

        for (Protein proteinToRemove : proteinsToRemove) {
            proteinToRemove.removeFromIndexes();
        }
    }
}
