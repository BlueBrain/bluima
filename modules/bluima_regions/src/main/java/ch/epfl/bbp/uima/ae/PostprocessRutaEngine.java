package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.utils.Preconditions.checkEquals;
import static com.google.common.collect.Lists.newArrayList;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

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
			// Prin.t(cooc.getKey()); Prin.t(cooc.getValue());

			List<BrainRegionChunk> chunks = newArrayList(cooc.getValue());
			checkEquals(2, chunks.size(), "BRCoocs should "
					+ "have exactly 2 chunks, but found " + chunks.size());

			// all BRs from first and second chunks
			Collection<BrainRegionDictTerm> brs1 = chunksIdx.get(chunks.get(0));
			Collection<BrainRegionDictTerm> brs2 = chunksIdx.get(chunks.get(1));

			// create Cooccurrence with all permutations
			for (BrainRegionDictTerm br1 : brs1) {
				for (BrainRegionDictTerm br2 : brs2) {
					Cooccurrence newC = new Cooccurrence(jCas, cooc.getKey()
							.getBegin(), cooc.getKey().getEnd());
					newC.setFirstEntity(br1);
					newC.setSecondEntity(br2);
					newC.addToIndexes();
				}
			}
		}
	}
}