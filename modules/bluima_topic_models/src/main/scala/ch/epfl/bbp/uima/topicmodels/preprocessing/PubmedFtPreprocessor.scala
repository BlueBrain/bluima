package ch.epfl.bbp.uima.topicmodels.preprocessing
//import org.apache.uima.fit.factory.AnalysisEngineFactory
//import ch.epfl.bbp.uima.uimafit.CpeBuilder
//import org.apache.uima.fit.factory.CollectionReaderFactory
//import ch.epfl.bbp.uima.topicmodels.writers.TokenFrequencyCounterWriter
//import ch.epfl.bbp.uima.topicmodels.readers.SingleFileCollectionReader
//import ch.epfl.bbp.uima.typesystem.TypeSystem
//import org.apache.uima.resource.metadata.TypeSystemDescription
//import ch.epfl.bbp.uima.BlueUima
//import ch.epfl.bbp.uima.topicmodels.writers.DCAWriter
//import ch.epfl.bbp.uima.topicmodels.writers.JCasWriterConsumer
//import ch.epfl.bbp.uima.typesystem.TypeSystem
//import ch.epfl.bbp.uima.uimafit.CpeBuilder
//import ch.epfl.bbp.uima.topicmodels.writers.WriterConfig
//import scala.collection.JavaConversions._
//import scala.util.Properties
//import scala.util.Properties
//import scala.util.Properties
//import java.util.Properties

/** 
 * Preprocess Pubmed full text cropus
 */
object PubmedFtPreprocessor extends App {
  //TODO
//  val outDir = "/home/mazimmer/private/corpora/pubmed_ns_sv"   
//  val MONGO_FT_CONNECTION = Array("128.178.187.248", "pubmed_uima", "pm_ft", "", "" )	
//  val query = "{ftr.ns:1}" // do only neuroscience fulltexts
//  val tokenFrequencies = "/home/mazimmer/private/corpora/pubmed_ns/token_frequency"
//  val testsetfraction = "0.15"
//  val seed = 1352130938
//  
//  val stopwords = GenericConf.pubmedStopwords
//  
//  val reader = CollectionReaderFactory.createReaderDescription(classOf[MongoCollectionReader], 
//    TypeSystem.JULIE_TSD: TypeSystemDescription, BlueUima.PARAM_DB_CONNECTION, MONGO_FT_CONNECTION, BlueUima.PARAM_QUERY, query)
//  
//  val pre = PreprocessingEngine.getPubmedPreprocessing(stopwords, tokenFrequencies, 100, Int.MaxValue)
//
//  val folds : Integer = 1
//  val writeTestFolds = false
//  val writers = 
// 	(new DCAWriter(outDir + "/dca/dca.txtbag", outDir + "/dca/dca.tokens", outDir + "/dca", folds, writeTestFolds)) :: Nil
//// 	(new PLDAWriter(outDir + "/plda/plda_train", outDir + "/plda/plda_test", folds, writeTestFolds)) ::
//// 	(new VowpalWabbitWriter(outDir + "/vw/vw_train", outDir + "/vw/vw_test", folds, writeTestFolds)) :: Nil 
//  val writerKey = "pubmed"
//  WriterConfig.addConfig(writerKey, writers)
//  
//  val post =  AnalysisEngineFactory.createEngineDescription(classOf[JCasWriterConsumer], TypeSystem.JULIE_TSD, 
//	  JCasWriterConsumer.Seed, seed.toString(),
//      JCasWriterConsumer.Writers, writerKey, 
//      JCasWriterConsumer.TestSetFraction, testsetfraction,
//      JCasWriterConsumer.OutputDirectory, outDir,
//      JCasWriterConsumer.CrossvalidationFolds, folds) :: Nil
//      
//  val annotators = (pre ::: post)
//  val pipelineBuilder = new CpeBuilder(1, reader)
//   annotators.foreach(a => pipelineBuilder.add(a))
//   
//   pipelineBuilder.process()  
}