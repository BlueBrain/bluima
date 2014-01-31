package ch.epfl.bbp.uima.topicmodels.preprocessing
import scala.collection.JavaConversions._
import scala.collection.JavaConversions.seqAsJavaList
import org.apache.uima.resource.metadata.TypeSystemDescription
import org.apache.uima.resource.metadata.TypeSystemDescription
import org.apache.uima.fit.factory.CollectionReaderFactory
import org.apache.uima.fit.factory.CollectionReaderFactory
import ch.epfl.bbp.uima.topicmodels.readers.SingleFileCollectionReader
import ch.epfl.bbp.uima.topicmodels.writers.DCAWriter
import ch.epfl.bbp.uima.topicmodels.writers.DCAWriter
import ch.epfl.bbp.uima.topicmodels.writers.JCasWriterConsumer
import ch.epfl.bbp.uima.topicmodels.writers.JCasWriterConsumer
import ch.epfl.bbp.uima.topicmodels.writers.PLDAWriter
import ch.epfl.bbp.uima.topicmodels.writers.PLDAWriter
import ch.epfl.bbp.uima.topicmodels.writers.VowpalWabbitWriter
import ch.epfl.bbp.uima.topicmodels.writers.VowpalWabbitWriter
import ch.epfl.bbp.uima.topicmodels.writers.WriterConfig
import ch.epfl.bbp.uima.topicmodels.writers.WriterConfig
import ch.epfl.bbp.uima.typesystem.TypeSystem
import ch.epfl.bbp.uima.typesystem.TypeSystem
import ch.epfl.bbp.uima.uimafit.CpeBuilder
import ch.epfl.bbp.uima.uimafit.CpeBuilder
import org.apache.uima.fit.factory.AnalysisEngineFactory

/**
 * Preprocess Pubmed abstracts
 */
object PubmedPreprocessor extends App {
  
  val root = "/home/mazimmer/private"
  val outDir = root + "/corpora/pubmed_complete"
  val tokenFrequencies = root + "/corpora/pubmed_complete/token_frequency"
  val minFreq = 100
  val maxFreq = Int.MaxValue
  val testsetfraction = "0.15"
  val folds: Integer = 1
  val writeTestFolds = false
  val seed = 1352130938
    
  val stopwords = GenericConf.pubmedStopwords
  val rawCorpusPath = GenericConf.pubmedAbstractsRawCorpusDir

  val reader = CollectionReaderFactory.createReaderDescription(classOf[SingleFileCollectionReader],
    TypeSystem.JULIE_TSD: TypeSystemDescription, SingleFileCollectionReader.FilePath, rawCorpusPath)
  //, SingleFileCollectionReader.ProportionToRead, "0.04")) // for testing
  //SingleFileCollectionReader.MaxDocsToRead, "1000"))

  val pre = PreprocessingEngine.getPubmedPreprocessing(stopwords, tokenFrequencies, minFreq, maxFreq)

  val writers = Nil
  //(new DCAWriter(outDir + "/dca/dca.txtbag", outDir + "/dca/dca.tokens", outDir + "/dca", folds, writeTestFolds)) :: Nil
  // 	(new PLDAWriter(outDir + "/plda/plda_train", outDir + "/plda/plda_test", folds, writeTestFolds)) ::
  // 	(new VowpalWabbitWriter(outDir + "/vw/vw_train", outDir + "/vw/vw_test", folds, writeTestFolds)) :: Nil 

  val writerKey = "pubmed"
  WriterConfig.addConfig(writerKey, writers)

  val post = AnalysisEngineFactory.createEngineDescription(classOf[JCasWriterConsumer], TypeSystem.JULIE_TSD,
    JCasWriterConsumer.Seed, seed.toString(),
    JCasWriterConsumer.Writers, writerKey,
    JCasWriterConsumer.TestSetFraction, testsetfraction,
    JCasWriterConsumer.OutputDirectory, outDir,
    JCasWriterConsumer.CrossvalidationFolds, folds) :: Nil

  val annotators = (pre ::: post)
  val pipelineBuilder = new CpeBuilder(1, reader)
  annotators.foreach(a => pipelineBuilder.add(a))

  pipelineBuilder.process()
}