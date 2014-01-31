package ch.epfl.bbp.uima.topicmodels.preprocessing
//import org.apache.uima.fit.factory.AnalysisEngineFactory
//import ch.epfl.bbp.uima.uimafit.CpeBuilder
//import org.apache.uima.fit.factory.CollectionReaderFactory
//import ch.epfl.bbp.uima.topicmodels.writers.TokenFrequencyCounterWriter
//import ch.epfl.bbp.uima.topicmodels.readers.SingleFileCollectionReader
//import ch.epfl.bbp.uima.typesystem.TypeSystem
//import org.apache.uima.resource.metadata.TypeSystemDescription
//import ch.epfl.bbp.uima.mongo.MongoCollectionReader
//import ch.epfl.bbp.uima.BlueUima

/**
 * Counts the number of occurrences of tokens the pubmed corpus.
 * 
 * The results are split over several files, since the list of unique tokens might be potentially
 * very large.
 * These files can be merged to one using ch.epfl.bbp.uima.topicmodels.utils.MergeMaps
 *
 */
object PubmedFtTokenFrequencyCounter extends App {
// TODO val outDir = "/home/mazimmer/private/corpora/pubmed_ns"
//  val MONGO_FT_CONNECTION = Array("128.178.187.248", "pubmed_uima", "pm_ft", "", "")
//  val query = "{ftr.ns:1}"
//
//  val reader = CollectionReaderFactory.createReaderDescription(classOf[MongoCollectionReader],
//    TypeSystem.JULIE_TSD: TypeSystemDescription, BlueUima.PARAM_DB_CONNECTION, MONGO_FT_CONNECTION, BlueUima.PARAM_QUERY, query)
//
//  val pre = PreprocessingEngine.getPubmedRawPreprocessing()
//
//  val post = AnalysisEngineFactory.createEngineDescription(classOf[TokenFrequencyCounterWriter], TypeSystem.JULIE_TSD,
//    TokenFrequencyCounterWriter.FilePath, outDir + "/token_frequency") :: Nil
//
//  val annotators = (pre ::: post)
//  val pipelineBuilder = new CpeBuilder(1, reader)
//  annotators.foreach(a => pipelineBuilder.add(a))
//
//  pipelineBuilder.process()
}