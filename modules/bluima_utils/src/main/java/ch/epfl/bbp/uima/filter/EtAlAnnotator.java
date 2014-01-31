package ch.epfl.bbp.uima.filter;

import static ch.epfl.bbp.uima.typesystem.TypeSystem.ET_AL_INLINE_REFERENCE;
import static java.util.regex.Pattern.compile;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.epfl.bbp.uima.types.EtAlInlineReference;

import com.google.common.collect.Lists;

/**
 * Text-level filtering. Finds occurences of et al.
 * 
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability(outputs = { ET_AL_INLINE_REFERENCE })
public class EtAlAnnotator extends JCasAnnotator_ImplBase {
    @SuppressWarnings("unused")
    private static Logger LOG = LoggerFactory.getLogger(EtAlAnnotator.class);

    private final List<Pattern> patterns = Lists.newArrayList();

    public void initialize(org.apache.uima.UimaContext context)
            throws ResourceInitializationException {

        // (Capital .... number)
        patterns.add(compile("\\([A-Z][^(]{4,500}(19[56789]|20[01])\\d[abcde]?\\)"));

        // ( ... et al .... 2009)
        patterns.add(compile("\\([^()]{0,100}(et\\.? al)([^()]+)(19[56789]|20[01])\\d[abcde]?\\)"));

        // Joachims (1999)
        // Satre et al. (2007)
        // Poirazi and Mel (2001)
        patterns.add(compile("([A-Z]\\w+ and )?[A-Z]\\w+ (et\\.? al\\.?)? ?\\((19[56789]|20[01])\\d[abcde]?\\)"));

        // ( et al )
        patterns.add(compile("\\([^(]{0,100}(et\\.? al\\.?)[^(]{0,100}\\)"));

        // Zucker et al. (1993)
        patterns.add(compile("[A-Z]\\w{2,50} (et\\.? al\\.?) ?\\((19[56789]|20[01])\\d\\)"));

        // just: et al.
        patterns.add(compile("et\\.? al\\.?"));
    };

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {

        String txt = jCas.getDocumentText();

        for (final Pattern pattern : patterns) {

            Matcher matcher = pattern.matcher(txt);
            while (matcher.find()) {
                new EtAlInlineReference(jCas, matcher.start(), matcher.end())
                        .addToIndexes();
            }
        }
    }
}
