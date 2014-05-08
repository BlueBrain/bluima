package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.BlueUima.BLUE_UIMA_ROOT;
import static ch.epfl.bbp.uima.BlueUima.RESOURCES_PATH;
import static java.lang.Character.isLetter;
import static java.lang.Math.max;
import static java.lang.System.currentTimeMillis;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.epfl.bbp.uima.typesystem.TypeSystem;

import com.wcohen.ss.abbvGapsHmm.Acronym;
import com.wcohen.ss.abbvGapsHmm.AlignmentPredictionModel;

import de.julielab.jules.types.Abbreviation;

/**
 * Finds abbreviations in text, using a HMM model from SecondString, trained on
 * biomedical text.<br/>
 * Paper: Alignment-HMM-based extraction of abbreviations from biomedical text
 * (http://dl.acm.org/citation.cfm?id=2391130). We report 98% precision and 93%
 * recall on a standard data set, and 95% precision and 91% recall on an
 * additional test set
 * 
 * @see https://github.com/TeamCohen/secondstring
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability(inputs = {}, outputs = { TypeSystem.ABBREVIATION })
public class AbbreviationsAnnotator extends JCasAnnotator_ImplBase {
    protected static final Logger LOG = LoggerFactory
            .getLogger(AbbreviationsAnnotator.class);

    @ConfigurationParameter(name = "retrain", //
    defaultValue = "false", description = "whether to retrain the model")
    private static boolean retrain;

    public static final String ABREVIATIONS_HOME = BLUE_UIMA_ROOT
            + "modules/bluima_abbreviations/";

    protected AlignmentPredictionModel model;

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);
        try {
            long start = currentTimeMillis();
            model = new AlignmentPredictionModel();
            if (retrain)
                model.setTrainingDataDir(ABREVIATIONS_HOME + RESOURCES_PATH
                        + "model_train/");
            // where the model is saved/loaded from
            model.setModelParamsFile(ABREVIATIONS_HOME + RESOURCES_PATH
                    + "model_trained");
            model.trainIfNeeded();
            LOG.debug("Abbrev model trained in {}ms", currentTimeMillis()
                    - start);
        } catch (IOException e) {
            throw new ResourceInitializationException(e);
        }
    }

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {
        final String txt = jCas.getDocumentText();

        Collection<Acronym> all_predictions = model.predict(txt);
        // for(Acronym a : all_predictions){
        // System.out.println(a);
        // }
        Map<String, Acronym> final_predictions = model
                .acronymsArrayToMap(all_predictions);

        for (String k : final_predictions.keySet()) {
            Acronym acro = final_predictions.get(k);
            LOG.debug("Acronym: {} start:{}", acro._shortForm + " :: "
                    + acro._longForm, acro._start);

            int beg = max(acro._start, 0);
            Abbreviation reference = null; // = first annot
            while (true) {

                int occurence = txt.indexOf(acro._shortForm, beg);
                if (occurence > -1) {

                    // Check that abrev has NON-letter on the right AND left
                    char before = txt.charAt(occurence - 1);
                    char after = txt.charAt(occurence
                            + acro._shortForm.length());

                    if (!isLetter(before) && !isLetter(after)) {
                        LOG.debug("\toccurence: {}", occurence);
                        Abbreviation abbrev = new Abbreviation(jCas, occurence,
                                occurence + acro._shortForm.length());
                        abbrev.setExpan(acro._longForm);

                        if (reference == null) {// first annot
                            reference = abbrev;
                            abbrev.setDefinedHere(true);
                        } else {
                            abbrev.setTextReference(reference);
                            abbrev.setDefinedHere(false);
                        }
                        abbrev.addToIndexes();
                    }
                    beg = occurence + 1;
                } else
                    break;
            }
        }
    }
}
