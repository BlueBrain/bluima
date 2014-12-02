package ch.epfl.bbp.uima.ae;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.Integer.parseInt;
import static org.apache.commons.lang.StringUtils.isNumeric;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tests the number of annotations on each CAS. Probably only useful when
 * testing a pipeline that processes a few documents.
 * 
 * @author renaud.richardet@epfl.ch
 */
public class TestAnnotator extends JCasAnnotator_ImplBase {
    private static Logger LOG = LoggerFactory.getLogger(TestAnnotator.class);

    @ConfigurationParameter(name = "expects", mandatory = true,//
    description = "pour le premier document: [{annot} {expected_count}, ]* , e.g. de.julielab.jules.types.Token 10, ch.epfl.bbp.uima.types.GeneralEnglish 4")
    private String expects;

    @ConfigurationParameter(name = "expects2", mandatory = false,//
    description = "pour le second document: [{annot} {expected_count}, ]* , e.g. de.julielab.jules.types.Token 10, ch.epfl.bbp.uima.types.GeneralEnglish 4")
    private String expects2;

    @ConfigurationParameter(name = "expects3", mandatory = false,//
    description = "pour le 3eme document: [{annot} {expected_count}, ]* , e.g. de.julielab.jules.types.Token 10, ch.epfl.bbp.uima.types.GeneralEnglish 4")
    private String expects3;

    @ConfigurationParameter(name = "expects4", mandatory = false,//
    description = "pour le 4eme document: [{annot} {expected_count}, ]* , e.g. de.julielab.jules.types.Token 10, ch.epfl.bbp.uima.types.GeneralEnglish 4")
    private String expects4;

    @ConfigurationParameter(name = "expects5", mandatory = false,//
    description = "pour le 5eme document: [{annot} {expected_count}, ]* , e.g. de.julielab.jules.types.Token 10, ch.epfl.bbp.uima.types.GeneralEnglish 4")
    private String expects5;

    int doc = 0;

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {
        String[] expectationsArr = { expects, expects2, expects3, expects4,
                expects5 };
        String documentExpect = expectationsArr[doc];
        doc++;

        // checkArgument(documentExpect != null && documentExpect.length() >
        // 0);
        if (documentExpect != null) {
            String[] expectations = documentExpect.split(", ");
            for (String expectation : expectations) {
                checkArgument(expectation.indexOf(" ") > -1,
                        "format is [{annot} {expected_count}, ]*, but it was "
                                + expectation);
                String[] e = expectation.split(" ");
                checkArgument(e.length == 2,
                        "format is [{annot} {expected_count}, ]*, but it was "
                                + expectation);
                String annot = e[0];
                checkArgument(isNumeric(e[1]),
                        "expected_count shoud be numeric, but it was " + e[1]);
                int expected = parseInt(e[1]);
                LOG.debug(
                        "TEST let's see if i can find {} annotation(s) of type {} here?",
                        expected, annot);
                try {
                    @SuppressWarnings("unchecked")
                    Class<? extends Annotation> annotClass = (Class<? extends Annotation>) Class
                            .forName(annot);
                    int actual = JCasUtil.select(jCas, annotClass).size();
                    if (actual != expected) {
                        String found = "";
                        for (Annotation a : JCasUtil.select(jCas, annotClass)) {
                            found += a.getCoveredText() + "\n";
                        }
                        System.err
                                .println("FATAL ERROR [TestAnnotator] assertion failed: found "
                                        + actual
                                        + " "
                                        + annot
                                        + " instead of "
                                        + expected + ", found:\n" + found);
                        System.exit(1);
                    }
                } catch (ClassNotFoundException e1) {
                    throw new RuntimeException("could not load class " + annot);
                }
            }
        }
    }
}
