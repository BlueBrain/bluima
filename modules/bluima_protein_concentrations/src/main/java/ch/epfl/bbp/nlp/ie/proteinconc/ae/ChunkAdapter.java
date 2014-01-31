package ch.epfl.bbp.nlp.ie.proteinconc.ae;

import ch.epfl.bbp.uima.types.Measure;
import ch.epfl.bbp.uima.types.Protein;
import de.julielab.jules.types.Chunk;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static ch.epfl.bbp.uima.typesystem.TypeSystem.MEASURE;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.PROTEIN;
import static org.apache.uima.fit.util.JCasUtil.select;
import static org.apache.uima.fit.util.JCasUtil.selectCovered;
import static org.apache.uima.fit.util.JCasUtil.selectCovering;

/**
 * Redefines the {@link Chunk} entity appropriately for
 * this project. Namely, it uses chunks annotated by OpenNLP.
 * Then, it cuts those who contains commas in two such that no chunk
 * contains commas.
 *
 * Forces pattern of
 * the form '[protein] ([measure]) to be in the same chunk. 
 *
 * @author Philémon Favrod (philemon.favrod@epfl.ch)
 */
@TypeCapability(inputs={ PROTEIN, MEASURE})
public class ChunkAdapter extends JCasAnnotator_ImplBase {

    @Override
    public void process(JCas cas) throws AnalysisEngineProcessException {
        int casEnd = cas.getDocumentText().length() - 1;
        final List<Chunk> oldChunksToDelete = new LinkedList<Chunk>();
        for (Chunk chunk : select(cas, Chunk.class)) {

            //OpenNLP sometimes set 0 to the end of a chunk (for the last chunk of a Cas).
            // That is, the following logic should correct that.
            final String coveredText =
                    (chunk.getEnd() > 0) ? chunk.getCoveredText() : cas.getDocumentText().substring(chunk.getBegin(), casEnd);


            if (coveredText.contains(",")) {

                oldChunksToDelete.add(chunk);
                final int originalBegin = chunk.getBegin();

                int begin = originalBegin;
                int end = originalBegin + coveredText.indexOf(",", begin - originalBegin);

                String[] innterChunksStr = coveredText.split(",");
                for (String innerChunkStr : innterChunksStr) {
                    //creation of the new chunk
                    Chunk innerChunk = new Chunk(cas);
                    innerChunk.setBegin(begin);
                    innerChunk.setEnd(end);
                    innerChunk.addToIndexes();

                    begin = end + 1;
                    end = originalBegin + coveredText.indexOf(",", begin - originalBegin);
                    if (end == originalBegin-1 || end > chunk.getEnd()) { //meaning that there is no more commas in the chunk

                        end = chunk.getEnd();
                    }

                }
            }
        }

        removeChunks(oldChunksToDelete);

        for (Protein protein : select(cas, Protein.class)) {
            final String documentText = cas.getDocumentText();
            final int proteinEnd = protein.getEnd();
            int nextParenthesisPosition = documentText.indexOf("(", proteinEnd);

            if ((nextParenthesisPosition != -1) &&
                    (documentText.substring(proteinEnd, nextParenthesisPosition).matches("\\s*"))) {
                //if we are here, it means we have a pattern of the form "[PROTEIN] ("
                //we need to make sure that what is in the parenthesis after the protein NE is a concentration

                int nextClosedParenthesisPosition = documentText.indexOf(")", nextParenthesisPosition);
                if (nextClosedParenthesisPosition != -1) {

                    Annotation innerParenthesisDelimiter =
                            new Annotation(cas, nextParenthesisPosition, nextClosedParenthesisPosition + 1);
                    Collection<Measure> measures = selectCovered(cas, Measure.class, innerParenthesisDelimiter);

                    if (measures.size() > 0) { // if there is one measure NE in the parenthesis following the protein NE

                        //we try to find the start and the end of the chunk covering both the protein and the measure
                        Collection<Chunk> chunksCoveringProtein = selectCovering(cas, Chunk.class, protein);
                        Collection<Chunk> chunksCoveredByParenthesis = selectCovered(cas, Chunk.class, innerParenthesisDelimiter);

                        int newChunkBegin = Integer.MAX_VALUE;
                        int newChunkEnd = 0;

                        for (Chunk chunkCoveringProtein: chunksCoveringProtein) {
                            int begin = chunkCoveringProtein.getBegin();
                            if (begin < newChunkBegin) {
                                newChunkBegin = begin;
                            }
                            oldChunksToDelete.add(chunkCoveringProtein);
                        }

                        for (Chunk chunkCoveredByParenthesis: chunksCoveredByParenthesis) {
                            int end = chunkCoveredByParenthesis.getEnd();
                            if (end > newChunkEnd) {
                                newChunkEnd = end;
                            }
                            oldChunksToDelete.add(chunkCoveredByParenthesis);
                        }

                        Chunk chunkCoveringProteinAndMeasure = new Chunk(cas, newChunkBegin, newChunkEnd);
                        chunkCoveringProteinAndMeasure.addToIndexes();
                    }
                }

            }
        }


        removeChunks(oldChunksToDelete);
    }

    private static void removeChunks(List<Chunk> oldChunks) {
        for (Chunk oldChunk : oldChunks) {
            oldChunk.removeFromIndexes();
        }
        oldChunks.clear();
    }
}
