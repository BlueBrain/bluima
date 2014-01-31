package ch.epfl.bbp.uima.references;

import static ch.epfl.bbp.uima.BlueCasUtil.getHeaderSource;
import static ch.epfl.bbp.uima.references.ReferencesClassifierAnnotator.LABEL_INSIDE;
import static ch.epfl.bbp.uima.references.ReferencesClassifierAnnotator.LABEL_OUTSIDE;
import static ch.epfl.bbp.uima.typesystem.ContentSection.SECTION_REFERENCES_ENTRY;
import static org.apache.uima.fit.util.JCasUtil.select;

import org.apache.commons.lang.StringUtils;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;

import ch.epfl.bbp.uima.types.DocumentBlock;

/**
 * Prints OOOOIOOIIO. For debugging
 * 
 * @author renaud.richardet@epfl.ch
 */
public class PrintStates extends JCasAnnotator_ImplBase {
    private static Logger LOG = LoggerFactory.getLogger(PrintStates.class);

    public void process(JCas jCas) throws AnalysisEngineProcessException {
        for (DocumentBlock block : select(jCas, DocumentBlock.class)) {

            String label = block.getLabel();
            if (label == null)
                label = "NOSECTIONN";
            label = StringUtils.rightPad(label, 20) + " ";

            System.out.println(label
                    + block.getCoveredText().replaceAll("\n", ""));
        }
        System.out.println("\n\n\n\n");
    }

    public void process2(JCas jCas) throws AnalysisEngineProcessException {

        LOG.debug(getHeaderSource(jCas)
                + "**************************************");

        String states = "";

        for (DocumentBlock block : select(jCas, DocumentBlock.class)) {

            if (block.getLabel() == null) {
                states += LABEL_OUTSIDE;
                LOG.debug(LABEL_OUTSIDE + "\t"
                        + block.getCoveredText().replaceAll("\n", ""));
            } else if (block.getLabel().equals(SECTION_REFERENCES_ENTRY)) {
                states += LABEL_INSIDE;
                LOG.debug(LABEL_INSIDE + "\t"
                        + block.getCoveredText().replaceAll("\n", ""));
            }
        }

        LOG.debug(getHeaderSource(jCas) + " " + states);

        LOG.debug("\n\n\n");
    }
}
