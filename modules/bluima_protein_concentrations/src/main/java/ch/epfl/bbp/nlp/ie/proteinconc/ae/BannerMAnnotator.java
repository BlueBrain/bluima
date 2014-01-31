package ch.epfl.bbp.nlp.ie.proteinconc.ae;

import ch.epfl.bbp.uima.ae.BannerHelper;
import ch.epfl.bbp.uima.ae.CopyAnnotationsAnnotator;
import ch.epfl.bbp.uima.types.Measure;
import ch.epfl.bbp.uima.types.Protein;
import de.julielab.jules.types.Sentence;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CASException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import static ch.epfl.bbp.uima.BlueUima.PARAM_VIEW;
import static ch.epfl.bbp.uima.ae.CopyAnnotationsAnnotator.DELETE_FROM;
import static ch.epfl.bbp.uima.ae.CopyAnnotationsAnnotator.FROM_ANNOTATION;
import static ch.epfl.bbp.uima.ae.CopyAnnotationsAnnotator.TO_ANNOTATION;
import static ch.epfl.bbp.uima.ae.CopyAnnotationsAnnotator.TO_VIEW;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.MEASURE;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.PROTEIN;
import static java.lang.Math.abs;
import static java.lang.Math.min;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.util.JCasUtil.select;


/**
 * BannerM is a wrapper for BANNER(1). Allows the user
 * to filter the protein on the length of their name
 * and improves the tokenization of the name of proteins.
 *
 * (1) note that it implies that Banner is not needed
 * below BannerM in the pipeline as BannerM contains
 * BANNER. In addition, including BANNER before BannerM
 * can lead to unpredictable results like two proteins
 * entity mentions overlapping each other.
 *
 * @author Philémon Favrod  (philemon.favrod@epfl.ch)
 */
@TypeCapability(inputs={MEASURE}, outputs = {PROTEIN})
public class BannerMAnnotator extends JCasAnnotator_ImplBase {

    public static final String PARAM_MAX_LENGTH = "max_length";

    private static final String VIEW_ID = "BANNER";
    private static final String REPLACEMENT_CHAR = " ";
    private AnalysisEngine bannerEngine;
    private AnalysisEngine sentenceAnnotationCopyEngine;

    @ConfigurationParameter(name = PARAM_MAX_LENGTH, defaultValue = "-1", mandatory = false,//
            description = "the maximum allowed length of a protein (filtering criteria)")
    private int maxLength;

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);
        bannerEngine = createEngine(BannerHelper.getBanner(), PARAM_VIEW,
                VIEW_ID);
        sentenceAnnotationCopyEngine = createEngine(
                CopyAnnotationsAnnotator.class,//
                FROM_ANNOTATION, Sentence.class.getName(),//
                TO_ANNOTATION, Sentence.class.getName(),//
                TO_VIEW, VIEW_ID, DELETE_FROM, false);
    }

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {

        JCas view;
        try {
            view = jCas.createView(VIEW_ID);
        } catch (CASException e) {
            throw new AnalysisEngineProcessException(e);
        }

        /*
         * One can observe that Banner does not react well when
         * proteins co-occurs closely with measures. It results
         * in wrong tokenization of protein entity mentions
         * like 'ml leupeptin'. The approach used below is to
         * replace the already-annotated measure entity mentions
         * by spaces before feeding Banner with the text.
         */
        String newDocumentText = jCas.getDocumentText();
        for (Measure measure : select(jCas, Measure.class)) {


            if ((min(measure.getBegin(), measure.getEnd()) > 0)
                    && (measure.getBegin() < newDocumentText.length())
                    && (measure.getEnd() < newDocumentText.length())) {
                String beforeText = newDocumentText.substring(0,
                        measure.getBegin());
                String afterText = newDocumentText.substring(measure.getEnd());

                StringBuilder sb = new StringBuilder(beforeText);
                for (int i = 0; i < abs(measure.getEnd() - measure.getBegin()); i++) {
                    sb.append(REPLACEMENT_CHAR);
                }
                sb.append(afterText);
                newDocumentText = sb.toString();
            }
        }

        view.setDocumentText(newDocumentText);

        sentenceAnnotationCopyEngine.process(jCas);
        bannerEngine.process(jCas);

        for (Protein protein : select(view, Protein.class)) {

            //If a max length is set we check that the text covering the entity mention
            //is not too big.
            if ((maxLength == -1) ||
                    (protein.getEnd() - protein.getBegin() <= maxLength)) {
                Protein proteinCopy = new Protein(jCas, protein.getBegin(),
                        protein.getEnd());
                proteinCopy.setName(protein.getName());
                proteinCopy.addToIndexes();
            }
        }
    }
}
