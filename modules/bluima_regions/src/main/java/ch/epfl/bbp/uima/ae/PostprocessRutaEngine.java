package ch.epfl.bbp.uima.ae;

import static com.google.common.collect.Lists.newArrayList;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.epfl.bbp.uima.types.BRCooc;
import ch.epfl.bbp.uima.types.BrainRegionChunk;
import ch.epfl.bbp.uima.types.BrainRegionDictTerm;
import ch.epfl.bbp.uima.types.Cooccurrence;

/**
 * Post-processes the {@link BRCooc}s into {@link Cooccurrence}s.
 * 
 * @author renaud.richardet@epfl.ch
 */
public class PostprocessRutaEngine extends JCasAnnotator_ImplBase {
	private static Logger LOG = LoggerFactory
			.getLogger(PostprocessRutaEngine.class);

	@Override
	public void process(JCas jCas) throws AnalysisEngineProcessException {

		Map<BrainRegionChunk, Collection<BrainRegionDictTerm>> chunksIdx = JCasUtil
				.indexCovered(jCas, BrainRegionChunk.class,
						BrainRegionDictTerm.class);
		Map<BRCooc, Collection<BrainRegionChunk>> coocsIdx = JCasUtil
				.indexCovered(jCas, BRCooc.class, BrainRegionChunk.class);

		// all BRCoocs, and the enclosed two BrainRegionChunks
		for (Entry<BRCooc, Collection<BrainRegionChunk>> cooc : coocsIdx
				.entrySet()) {
			// Prin.t(cooc.getKey());Prin.t(cooc.getValue());

			List<BrainRegionChunk> chunks = newArrayList(cooc.getValue());
			if (chunks.size() != 2) {// ignore, then
				LOG.warn("BRCoocs should have exactly 2 chunks, but found "
						+ chunks.size() + " chunks, ignoring");

			} else {
				// all BRs from first and second chunks
				Collection<BrainRegionDictTerm> brs1 = chunksIdx.get(chunks
						.get(0));
				Collection<BrainRegionDictTerm> brs2 = chunksIdx.get(chunks
						.get(1));

				// create Cooccurrence with all permutations
				for (BrainRegionDictTerm br1 : brs1) {
					// TODO better
					if (br1.getCoveredText().startsWith("the ")
							|| br1.getCoveredText().startsWith("The ")) {
						br1.setBegin(br1.getBegin() + 4);
					}
					for (BrainRegionDictTerm br2 : brs2) {
						// TODO better
						if (br2.getCoveredText().startsWith("the ")
								|| br2.getCoveredText().startsWith("The ")) {
							br2.setBegin(br2.getBegin() + 4);
						}
						Cooccurrence newC = new Cooccurrence(jCas, cooc
								.getKey().getBegin(), cooc.getKey().getEnd());
						newC.setFirstEntity(br1);
						newC.setSecondEntity(br2);
						newC.addToIndexes();
					}
				}
			}
		}
	}
}