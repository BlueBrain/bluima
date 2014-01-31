package ch.epfl.bbp.uima.ae.cleanup;

import static ch.epfl.bbp.uima.BlueCasUtil.getHeaderDocId;
import static java.lang.Math.max;
import static org.apache.uima.fit.util.JCasUtil.select;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;

import ch.epfl.bbp.uima.types.DocumentBlock;
import ch.epfl.bbp.uima.types.TooFewTokensPerPage;
import de.julielab.jules.types.Token;

/**
 * Flags a document that has too few tokens per page (< 50 on average)
 * 
 * @author renaud.richardet@epfl.ch
 */
public class TooFewTokensFilterAnnotator extends JCasAnnotator_ImplBase {
    private static Logger LOG = LoggerFactory
            .getLogger(TooFewTokensFilterAnnotator.class);

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {

        try {
            int tokens = select(jCas, Token.class).size();

            int pages = 0;
            for (DocumentBlock block : select(jCas, DocumentBlock.class)) {
                pages = max(pages, block.getPageId());
            }
            pages++;// 0-indexed

            if (tokens < pages * 50) {
                new TooFewTokensPerPage(jCas).addToIndexes();
            }
            
        } catch (Exception e) {
            LOG.error("could not compute token/page ratio on pmid "
                    + getHeaderDocId(jCas), e);
        }
    }
}