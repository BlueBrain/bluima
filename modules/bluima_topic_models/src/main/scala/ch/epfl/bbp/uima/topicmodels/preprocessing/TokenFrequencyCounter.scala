package ch.epfl.bbp.uima.topicmodels.preprocessing
import org.apache.uima.fit.factory.AnalysisEngineFactory
import ch.epfl.bbp.uima.uimafit.CpeBuilder
import org.apache.uima.fit.factory.CollectionReaderFactory
import ch.epfl.bbp.uima.topicmodels.writers.TokenFrequencyCounterWriter
import ch.epfl.bbp.uima.topicmodels.readers.SingleFileCollectionReader
import ch.epfl.bbp.uima.typesystem.TypeSystem
import org.apache.uima.resource.metadata.TypeSystemDescription

/**
 * Counts the number of occurrences of tokens in a corpus
 *
 * The results are split over several files, since the list of unique tokens might be potentially
 * very large.
 * These files can be merged to one using ch.epfl.bbp.uima.topicmodels.utils.MergeMaps
 */
object TokenFrequencyCounter extends App {
  val outDir = "/home/mazimmer/private/corpora/pubmed_complete"
  val rawCorpusPath = GenericConf.pubmedAbstractsRawCorpusDir

  val reader = CollectionReaderFactory.createReaderDescription(classOf[SingleFileCollectionReader],
    SingleFileCollectionReader.FilePath, rawCorpusPath)

  val pre = PreprocessingEngine.getPubmedRawPreprocessing()

  val post = AnalysisEngineFactory.createEngineDescription(classOf[TokenFrequencyCounterWriter],
    TokenFrequencyCounterWriter.FilePath, outDir + "/token_frequency") :: Nil

  val annotators = (pre ::: post)
  val pipelineBuilder = new CpeBuilder(1, reader)
  annotators.foreach(a => pipelineBuilder.add(a))

  pipelineBuilder.process()
}
