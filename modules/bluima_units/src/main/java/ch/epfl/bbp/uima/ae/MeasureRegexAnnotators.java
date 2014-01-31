package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.BlueUima.PARAM_ANNOTATION_CLASS;
import static org.apache.uima.annotator.regex.impl.RegExAnnotator.REGEX_CONCEPTS_FILES;

import java.io.IOException;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.metadata.CpeDescriptorException;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.InvalidXMLException;
import org.xml.sax.SAXException;

import ch.epfl.bbp.uima.BlueUima;
import ch.epfl.bbp.uima.types.Measure;
import ch.epfl.bbp.uima.uimafit.PipelineBuilder;

public class MeasureRegexAnnotators {

    public static final String BLUE_UIMA_MEASURES = BlueUima.BLUE_UIMA_ROOT
            + "modules/bluima_units/";
    public static final String CONCEPTS_CONCENTRATION = BLUE_UIMA_MEASURES
            + "src/main/resources/pear_resources/regex_concepts/concentrations.xml";
    public static final String CONCEPTS_MEASURE = BLUE_UIMA_MEASURES
            + "src/main/resources/pear_resources/regex_concepts/measures.xml";

    public static void addMeasureAnnotators(PipelineBuilder cpeBuilder)
            throws InvalidXMLException, ResourceInitializationException,
            IOException, SAXException, CpeDescriptorException {
        cpeBuilder.add(getAllAED());
        cpeBuilder.add(KeepLargestAnnotationAnnotator.class,
                PARAM_ANNOTATION_CLASS, Measure.class.getName());
    }

    public static AnalysisEngineDescription getAllAED()
            throws ResourceInitializationException {
        return AnalysisEngineFactory.createEngineDescription(
                RegExAnnotator.class, REGEX_CONCEPTS_FILES, new String[] {
                        CONCEPTS_CONCENTRATION, CONCEPTS_MEASURE });
    }

    public static AnalysisEngineDescription getMeasuresAED()
            throws ResourceInitializationException {
        return AnalysisEngineFactory.createEngineDescription(
                RegExAnnotator.class, REGEX_CONCEPTS_FILES,
                new String[] { CONCEPTS_MEASURE });
    }

    public static AnalysisEngineDescription getConcentrationsAED()
            throws ResourceInitializationException {
        return AnalysisEngineFactory.createEngineDescription(
                RegExAnnotator.class, REGEX_CONCEPTS_FILES,
                new String[] { CONCEPTS_CONCENTRATION });
    }

    public static AnalysisEngine getMeasuresAE()
            throws ResourceInitializationException {
        return AnalysisEngineFactory.createEngine(RegExAnnotator.class,
                REGEX_CONCEPTS_FILES, new String[] { CONCEPTS_MEASURE });
    }

    public static AnalysisEngine getConcentrationsAE()
            throws ResourceInitializationException {
        return AnalysisEngineFactory.createEngine(RegExAnnotator.class,
                REGEX_CONCEPTS_FILES, new String[] { CONCEPTS_CONCENTRATION });
    }
}
