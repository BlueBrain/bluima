package ch.epfl.bbp.uima.references;

import static ch.epfl.bbp.uima.BlueUima.PARAM_MODEL;
import static ch.epfl.bbp.uima.references.ReferencesClassifierAnnotator.DEFAULT_MODEL_NAME;
import static ch.epfl.bbp.uima.references.ReferencesClassifierAnnotator.LABEL_INSIDE;
import static ch.epfl.bbp.uima.references.ReferencesClassifierAnnotator.LABEL_OUTSIDE;
import static ch.epfl.bbp.uima.references.ReferencesClassifierTrainer.CORPUS;
import static ch.epfl.bbp.uima.testutils.UimaTests.getTestCas;
import static ch.epfl.bbp.uima.typesystem.ContentSection.SECTION_REFERENCES_ENTRY;
import static org.apache.uima.fit.util.JCasUtil.selectSingle;

import java.io.File;
import java.io.FileInputStream;

import org.apache.uima.jcas.JCas;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.epfl.bbp.io.LineReader;
import ch.epfl.bbp.uima.types.DocumentBlock;
import ch.epfl.bbp.uima.uimafit.JcasPipelineBuilder;

/**
 * Applies the trained classifier on 20 (well, 19) documents and sets high
 * expectations (yes we can).
 * 
 * @author renaud.richardet@epfl.ch
 */
public class ReferencesClassifierAnnotatorTest {
    private static Logger LOG = LoggerFactory
            .getLogger(ReferencesClassifierAnnotatorTest.class);

    @Test
    public void test() throws Exception {

        JcasPipelineBuilder builder = new JcasPipelineBuilder();
        builder.add(ReferencesClassifierAnnotator.class,//
                PARAM_MODEL, DEFAULT_MODEL_NAME);

        // test
        for (String goldLabel : new String[] { LABEL_INSIDE, LABEL_OUTSIDE }) {

            double tp = 0, total = 0, filesToCheck = 20;

            for (File f : new File(CORPUS, goldLabel).listFiles()) {

                if (filesToCheck-- >= 0 && f.getName().endsWith(".txt")) {

                    @SuppressWarnings("resource")
                    String txt = new LineReader(new FileInputStream(f))
                            .getText("\n");
                    JCas testCas = getTestCas(txt);
                    new DocumentBlock(testCas, 0, txt.length()).addToIndexes();

                    builder.process(testCas);
                    String system = selectSingle(testCas, DocumentBlock.class)
                            .getLabel();

                    if ((system == null && goldLabel.equals(LABEL_OUTSIDE))
                            || (system != null
                                    && system.equals(SECTION_REFERENCES_ENTRY) && goldLabel
                                        .equals(LABEL_INSIDE))) {
                        tp++;
                    }
                    total++;
                }
            }

            double ratio = tp / total;
            if (!(ratio > 0.85 && ratio <= 1)) {
                throw new Exception("tp ratio " + ratio + " too low: " + tp
                        + " out of total " + total);
            } else {
                LOG.debug("ratio: {}", ratio);
            }
        }
    }
}
