package ch.epfl.bbp.uima.topicmodels.preprocessing

import scala.collection.JavaConversions.seqAsJavaList
import org.apache.uima.resource.metadata.TypeSystemDescription
import org.apache.uima.fit.factory.AnalysisEngineFactory
import org.apache.uima.fit.factory.CollectionReaderFactory
import ch.epfl.bbp.uima.ae.StatsAnnotatorPlus
import ch.epfl.bbp.uima.topicmodels.annotators.DCATopicModelsAnnotator
import ch.epfl.bbp.uima.topicmodels.dca.DCAFiles
import ch.epfl.bbp.uima.topicmodels.utils.MergeMaps
import ch.epfl.bbp.uima.topicmodels.writers.exploitation.AnnotateTokensWithTopicWriter
import ch.epfl.bbp.uima.topicmodels.writers.exploitation.TopicDistributionWriter
import ch.epfl.bbp.uima.topicmodels.writers.DCAWriter
import ch.epfl.bbp.uima.topicmodels.writers.JCasWriterConsumer
import ch.epfl.bbp.uima.topicmodels.writers.TokenFrequencyCounterWriter
import ch.epfl.bbp.uima.topicmodels.writers.WriterConfig
import ch.epfl.bbp.uima.typesystem.TypeSystem
import ch.epfl.bbp.uima.uimafit.CpeBuilder
import ch.epfl.bbp.uima.BlueUima
import ch.epfl.bbp.uima.TopicModelsHelper
import java.io.File
import scala.io.Source
import scala.collection.mutable.HashMap
import ch.epfl.bbp.uima.cr.TextArrayReader
//import scala.collection.JavaConversions._

object PubMedConf {
  val pubmed_root = "/Volumes/HDD2/ren_data/dev_hdd/bluebrain/9_lda/pubmed_preproc/"
  val freqs = pubmed_root + "token_frequency"
  val stopwords = TopicModelsHelper.TOPIC_MODELS_ROOT + "src/main/resources/stoplists/mallet_stopwords_en.txt"
  val compiledModelDir = "/Volumes/HDD2/ren_data/dev_hdd/bluebrain/9_lda/dca/20130129_replicate_marcs/20130127_dca-pubmed_abstracts_trained_model"
//  val compiledModelDir = "/Volumes/simulation/nlp/data/lda/dca-pubmed_abstracts"
  val stem = "dca"

    //FIXME
  val reader = CollectionReaderFactory.createReaderDescription(classOf[TextArrayReader],
    TypeSystem.JULIE_TSD: TypeSystemDescription)
//  val reader = CollectionReaderFactory.createReaderDescription(classOf[PubmedWholeDatabaseCR],
//    TypeSystem.JULIE_TSD: TypeSystemDescription,
//    BlueUima.PARAM_DB_CONNECTION, Array("localhost", "bb_pubmed", "root", ""))
//  val reader_ = CollectionReaderFactory.createReaderDescription(classOf[PubmedFromListDatabaseCR],
//    TypeSystem.JULIE_TSD: TypeSystemDescription,
//    BlueUima.PARAM_DB_CONNECTION, Array("localhost", "bb_pubmed", "root", ""),
//    "ids", Array(128))
//  val reader3 = CollectionReaderFactory.createReaderDescription(classOf[PubmedCentralCollectionReader], TypeSystem.JULIE_TSD: TypeSystemDescription,
//    BlueUima.PARAM_INPUT_DIRECTORY, "/Volumes/HDD2/ren_data/data_hdd/_papers_etc/pmc/pmc_sample/d6")

}

object PubMedTokenFrequencyCounter extends App {

  val pre = PreprocessingEngine.getPubmedRawPreprocessing()

  val post = AnalysisEngineFactory.createEngineDescription(classOf[TokenFrequencyCounterWriter], TypeSystem.JULIE_TSD,
    TokenFrequencyCounterWriter.FilePath, PubMedConf.freqs) :: Nil

  val annotators = (pre ::: post)
  val pipelineBuilder = new CpeBuilder(1, PubMedConf.reader)
  annotators.foreach(a => pipelineBuilder.add(a))

  pipelineBuilder.add(classOf[StatsAnnotatorPlus])
  pipelineBuilder.process()
}

// WARN: DO NOT sort -r tokenfiles!! (has been fixed by Marc)
object PubMedMergeMaps extends App {
  MergeMaps.mergeFiles(PubMedConf.freqs, 15)
}

object PubMedPubmedPreprocessor extends App {

  val outDir = PubMedConf.pubmed_root + ""
  val minFreq = 100
  val maxFreq = Int.MaxValue
  val testsetfraction = "0.15" // split training.txt / test.txt
  val folds: Integer = 1 //10 for 10-fold cross validation (test3.txt, test4.txt,...)
  val writeTestFolds = false
  val seed = 1352130938

  val pre = PreprocessingEngine.getPubmedPreprocessing(PubMedConf.stopwords, PubMedConf.freqs, minFreq, maxFreq)

  val writers =
    (new DCAWriter(outDir + "/dca/dca.txtbag", outDir + "/dca/dca.tokens", outDir + "/dca", folds, writeTestFolds)) :: Nil

  val writerKey = "pubmed"
  WriterConfig.addConfig(writerKey, writers)

  val post = AnalysisEngineFactory.createEngineDescription(classOf[JCasWriterConsumer], TypeSystem.JULIE_TSD,
    JCasWriterConsumer.Seed, seed.toString(),
    JCasWriterConsumer.Writers, writerKey,
    JCasWriterConsumer.TestSetFraction, testsetfraction,
    JCasWriterConsumer.OutputDirectory, outDir,
    JCasWriterConsumer.CrossvalidationFolds, folds) :: Nil

  val annotators = (pre ::: post)
  val pipelineBuilder = new CpeBuilder(1, PubMedConf.reader)
  annotators.foreach(a => pipelineBuilder.add(a))

  pipelineBuilder.add(classOf[StatsAnnotatorPlus])
  pipelineBuilder.process()
}

/**
 * Annotates tokens in documents with its most likely topic.
 * Also generates a plot of topic distributions for each document.
 *
 * Note: Observed some memory leak (i.e. memory grows large with many document). Not further investigated.
 */
object ExploitPubMedAbstracts extends App {

  val pre = PreprocessingEngine.getTwentyNewsgroupsPreprocessing(PubMedConf.stopwords)

  val dcaAnnotator = AnalysisEngineFactory.createEngineDescription(classOf[DCATopicModelsAnnotator], TypeSystem.JULIE_TSD,
    DCATopicModelsAnnotator.DCADirectoryPath, PubMedConf.compiledModelDir,
    DCATopicModelsAnnotator.DCAStem, "dca",
    DCATopicModelsAnnotator.InferenceBurnin, "20",
    DCATopicModelsAnnotator.InferenceIterations, "100")

  val writer = AnalysisEngineFactory.createEngineDescription(classOf[AnnotateTokensWithTopicWriter], TypeSystem.JULIE_TSD,
    AnnotateTokensWithTopicWriter.LatexOutput, "true",
    AnnotateTokensWithTopicWriter.OutputDir, PubMedConf.pubmed_root + "/expl")

  val rcommand = "R --vanilla --slave -f " + TopicModelsHelper.TOPIC_MODELS_SCRIPTS + "plot_doc_topic_distribution.R --args {input} {output}"
  val plots = AnalysisEngineFactory.createEngineDescription(classOf[TopicDistributionWriter], TypeSystem.JULIE_TSD,
    TopicDistributionWriter.OutputDir, PubMedConf.pubmed_root + "/expl",
    TopicDistributionWriter.Command, rcommand)

  val annotators = pre ::: (dcaAnnotator :: writer :: plots :: Nil)
  val pipelineBuilder = new CpeBuilder(1, PubMedConf.reader)
  annotators.foreach(a => pipelineBuilder.add(a))

  pipelineBuilder.process
}

object PrintWordsPerTopics extends App {

  //val inferencer = new DCAInference(PubMedConf.compiledModelDir, PubMedConf.stem, false)
  val termTopicProbability = DCAFiles.getTermTopicProbabilityMatrix(PubMedConf.compiledModelDir, PubMedConf.stem, false)
  val tokenDict = DCAFiles.getTokenDict(PubMedConf.compiledModelDir, PubMedConf.stem)
  val tokenIds = tokenDict.foldLeft(Map[Int, String]())((out, in) => out + (in._2 -> in._1))

  for (topicId <- 0 to 199) {
    println("topic " + topicId)
    // array[(w, P(z|w)]
    val a = termTopicProbability.zipWithIndex.map {
      case (topicProbs, termId) =>
        (termId, topicProbs(topicId))
    }
    val topWs = a.sortBy(-_._2).take(20).map { k => k._1 }
    topWs.foreach { w => println(" " + tokenIds.get(w).get) }
  }
}

object MergePreProc {
  def main(args: Array[String]) {
    merge(args(0))
    //merge("/Volumes/HDD2/ren_data/dev_hdd/bluebrain/9_lda/dca/20130214_preprocess_pubmed3.pipeline");
    //merge(TopicModelsHelper.TOPIC_MODELS_TEST_ROOT + "MergePreProc/");
  }

  def merge(dir: String) {

    println("merging " + dir)

    def it = new File(dir) // def to get it mult times. an iterator over all outputs of preprocessing
      .listFiles.toIterator
      .filter(_.getName.startsWith("dca"))

    //create & write new tokens file
    val newTokenFile = new File(dir + "/merged/dca.tokens")
    if (!newTokenFile.exists) {
      newTokenFile.getParentFile.mkdirs
      newTokenFile.createNewFile
    }
    val newTokenSeq = it
      .flatMap(file => Source fromFile new File(file.getAbsolutePath + "/dca/dca.tokens") getLines)
      .toSet.toSeq
    printToFile(newTokenFile)(p => {
      newTokenSeq.foreach(p.println)
    })
    val newTokenIndex = newTokenSeq.zipWithIndex.toMap

    // merge all txtbag files
    val writer = new java.io.PrintWriter(new File(dir + "/merged/dca.txtbag"))
    it.foreach { f =>
      println("merge txtbag for: " + f.getName)
      val tokenLines = Source fromFile new File(f.getAbsolutePath + "/dca/dca.tokens") getLines;
      val currTokenIndex = tokenLines.zipWithIndex.map { case (v, i) => (i, v) } //inverse key-value
        .toMap
      val txtbagLines = Source fromFile new File(f.getAbsolutePath() + "/dca/dca.txtbag") getLines;
      txtbagLines.filter(_.contains(" ")) foreach { l => // filter: to skip first lines
        // format {#token} ({tokenId} {#occurences})+    // see dcaguide-0202A.pdf
        val split = l.split(" ")
        // println("line " + l)
        writer.append(split(0)) // 1st is {#token}
        for (i <- 1.to(split.length - 1) by 2) { // skip 1st
          val currTokenId = split(i).toInt
          val currString = currTokenIndex.get(currTokenId).get
          val newTokenId: Int = newTokenIndex.get(currString).get
          writer.append(" " + newTokenId + " " + split(i + 1))
        }
        writer.append("\n")
      }
    }
    writer.close
  }
  def printToFile(f: java.io.File)(op: java.io.PrintWriter => Unit) {
    val p = new java.io.PrintWriter(f)
    try { op(p) } finally { p.close() }
  }
}