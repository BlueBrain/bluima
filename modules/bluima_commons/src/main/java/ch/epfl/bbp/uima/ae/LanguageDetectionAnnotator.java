package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.BlueCasUtil.getHeaderDocId;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.TypeCapability;

import com.cybozu.labs.langdetect.Detector;
import com.cybozu.labs.langdetect.DetectorFactory;
import com.cybozu.labs.langdetect.LangDetectException;

/**
 * Language detection
 * 
 * @see http://code.google.com/p/language-detection
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability()
public class LanguageDetectionAnnotator extends JCasAnnotator_ImplBase {
    private static Logger LOG = LoggerFactory
            .getLogger(LanguageDetectionAnnotator.class);

    static {
        try {
            DetectorFactory.loadProfiles("af", "am", "ar", "az", "be", "bg",
                    "bn", "bo", "ca", "cs", "cy", "da", "de", "dv", "el", "en",
                    "es", "et", "eu", "fa", "fi", "fo", "fr", "ga", "gn", "gu",
                    "he", "hi", "hr", "hu", "hy", "id", "is", "it", "ja", "jv",
                    "ka", "kk", "km", "kn", "ko", "ky", "lb", "lij", "ln",
                    "lt", "lv", "mi", "mk", "ml", "mn", "mr", "mt", "my", "ne",
                    "nl", "no", "os", "pa", "pl", "pnb", "pt", "qu", "ro",
                    "ru", "si", "sk", "so", "sq", "sr", "sv", "sw", "ta", "te",
                    "th", "tk", "tl", "tr", "tt", "ug", "uk", "ur", "uz", "vi",
                    "yi", "yo", "zh-cn", "zh-tw");
        } catch (LangDetectException e) {
            LOG.warn("could not init lang detect");
        }
    }

    // for testing
    public static final String MIN_TEXT_LENGTH = "min_text_length";
    @ConfigurationParameter(name = MIN_TEXT_LENGTH, defaultValue = "200")
    private int minTextLenght;

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {
        String text = jCas.getDocumentText();

        // only detect if text is long enough
        if (text != null && text.length() > minTextLenght) {

            // TODO maybe cut if text too long --> slower
            try {

                jCas.setDocumentLanguage(detect(text));

            } catch (LangDetectException e) {
                LOG.warn("error detecting language for {}, {}",
                        getHeaderDocId(jCas), e);
            }
        }
    }

    public static String detect(String text) throws LangDetectException {

        Detector detector = DetectorFactory.create(0.5);
        detector.append(text);

        return detector.detect();
    }
}
