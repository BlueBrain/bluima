package ch.epfl.bbp.uima.ae.output;

import static ch.epfl.bbp.MissingUtils.format;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.epfl.bbp.uima.BlueCasUtil;

/**
 * A consumer that writes out specified Annotations and features to a specified
 * text file, prepending each line with pmId and begin-end. Example:
 * 
 * <pre>
 * 123   0   5   hello
 * 123   6   11  brave
 * 123   12  17  world
 * </pre>
 * 
 * @author renaud.richardet@epfl.ch
 */
public class AnnotationTypeWriter2 extends AnnotationTypeWriter {
    private static Logger LOG = LoggerFactory
            .getLogger(AnnotationTypeWriter2.class);

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {
        String pmId = BlueCasUtil.getHeaderDocId(jCas);
        try {
            for (Annotation a : jCas.getAnnotationIndex()) {
                if (a.getClass().getName().equals(annotationClass)) {
                    String featureStr;
                    if (featureName != null) {
                        featureStr = a.getFeatureValueAsString(a.getType()
                                .getFeatureByBaseName(featureName));
                    } else {
                        featureStr = a.getCoveredText();
                    }

                    if (!(filterFeaturesWithValue != null && featureStr
                            .equals(filterFeaturesWithValue))) {

                        writer.append(format("{}\t{}\t{}\t{}\n",//
                                pmId, a.getBegin(), a.getEnd(), featureStr));
                    }
                }
            }
            writer.flush();

        } catch (Exception e) {
            LOG.warn("could not process " + pmId, e);
        }
    }
}
