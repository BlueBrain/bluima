package ch.epfl.bbp.uima.filter;

import static ch.epfl.bbp.uima.BlueCasUtil.getHeaderDocId;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.TOKEN;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.TOO_FEW_TOKENS_PER_PAGE;
import static java.lang.Math.max;
import static org.apache.uima.fit.util.JCasUtil.select;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.TypeCapability;

import ch.epfl.bbp.uima.types.DocumentBlock;
import ch.epfl.bbp.uima.types.TooFewTokensPerPage;
import de.julielab.jules.types.Token;

/**
 * Document-level filtering.Flags a document that has too few tokens per page (<
 * 50 on average).
 * 
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability(inputs = { TOKEN }, outputs = { TOO_FEW_TOKENS_PER_PAGE })
public class TooFewTokensFilterAnnotator extends JCasAnnotator_ImplBase {
    private static Logger LOG = LoggerFactory
            .getLogger(TooFewTokensFilterAnnotator.class);

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {

        try {

            int tokenCnt = select(jCas, Token.class).size();

            int pageCnt = 0;
            for (DocumentBlock block : select(jCas, DocumentBlock.class)) {
                pageCnt = max(pageCnt, block.getPageId());
            }
            pageCnt++;// 0-indexed

            if (tokenCnt < pageCnt * 50) {
                new TooFewTokensPerPage(jCas).addToIndexes();
            }

        } catch (Exception e) {
            LOG.error("could not compute token/page ratio on pmid "
                    + getHeaderDocId(jCas), e);
        }
    }
}