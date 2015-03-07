package ch.epfl.bbp.uima.topicmodels.preprocessing
import scala.collection.JavaConversions.seqAsJavaList
import org.apache.uima.resource.metadata.TypeSystemDescription
import org.apache.uima.fit.factory.AnalysisEngineFactory
import org.apache.uima.fit.factory.CollectionReaderFactory
import ch.epfl.bbp.uima.topicmodels.readers.TwentyNewsgroupsCollectionReader
import ch.epfl.bbp.uima.topicmodels.writers.DCAWriter
import ch.epfl.bbp.uima.topicmodels.writers.JCasWriterConsumer
import ch.epfl.bbp.uima.topicmodels.writers.PLDAWriter
import ch.epfl.bbp.uima.topicmodels.writers.VowpalWabbitWriter
import ch.epfl.bbp.uima.topicmodels.writers.WriterConfig
import ch.epfl.bbp.uima.typesystem.TypeSystem
import ch.epfl.bbp.uima.uimafit.CpeBuilder
import ch.epfl.bbp.uima.topicmodels.readers.SingleFileCollectionReader
import ch.epfl.bbp.uima.ae.RegexTokenizerAnnotator
import ch.epfl.bbp.uima.topicmodels.writers.TokenFrequencyCounterWriter

/**
 * Prepare 20 newsgroups corpus to read in other applications.
 *
 * Reads 20 newsgroups corpus, preprocesses it and write files to _one_ directory
 */
object TwentyNewsgroupsPreprocessor extends App {
  val outDir = "/home/mz/Documents/epfl/projet/corpora/twenty_newsgroups"
  val malletStopwords = GenericConf.malletStopwords
  val testsetfraction = "0.15"
  val seed = 1352130938

  val start = System.currentTimeMillis()

  val reader = CollectionReaderFactory.createReaderDescription(classOf[TwentyNewsgroupsCollectionReader],
    TwentyNewsgroupsCollectionReader.CorpusDirPath, GenericConf.twentyNewsgroupsRawCorpusDir)

  val pre = AnalysisEngineFactory.createEngineDescription(classOf[RegexTokenizerAnnotator]) :: Nil //PreprocessingEngine.getTwentyNewsgroupsPreprocessing(malletStopwords)

  val nfolds: Integer = 1 // generates files for n-fold crossvalidation
  val writeTestFolds = false // also write out text files

  // write in different formats
  val writers =
    (new DCAWriter(outDir + "/dca/dca.txtbag", outDir + "/dca/dca.tokens", outDir + "/dca", nfolds, writeTestFolds)) ::
      (new PLDAWriter(outDir + "/plda/plda_train", outDir + "/plda/plda_test", nfolds, writeTestFolds)) ::
      (new VowpalWabbitWriter(outDir + "/vw/vw_train", outDir + "/vw/vw_test", nfolds, writeTestFolds)) :: Nil
  val writerKey = "20news"
  WriterConfig.addConfig(writerKey, writers)

  val post = AnalysisEngineFactory.createEngineDescription(classOf[JCasWriterConsumer],
    JCasWriterConsumer.Seed, seed.toString(),
    JCasWriterConsumer.Writers, writerKey,
    JCasWriterConsumer.TestSetFraction, testsetfraction,
    JCasWriterConsumer.OutputDirectory, outDir,
    JCasWriterConsumer.CrossvalidationFolds, nfolds) :: Nil

  // construct pipeline
  val annotators = (pre ::: post)
  val pipelineBuilder = new CpeBuilder(1, reader)
  annotators.foreach(a => pipelineBuilder.add(a))

  pipelineBuilder.process()
}
