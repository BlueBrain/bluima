package ch.epfl.bbp.uima.topicmodels.projects

import java.io.File
import java.io.FileInputStream
import scala.collection.JavaConversions._
import org.apache.commons.io.FileUtils
import org.apache.uima.resource.metadata.TypeSystemDescription
import org.apache.uima.fit.factory.AnalysisEngineFactory
import org.apache.uima.fit.factory.CollectionReaderFactory
import ch.epfl.bbp.io.LineReader
import ch.epfl.bbp.io.TextFileWriter
import ch.epfl.bbp.uima.topicmodels.annotators.DCATopicModelsAnnotator
import ch.epfl.bbp.uima.topicmodels.preprocessing.PreprocessingEngine
import ch.epfl.bbp.uima.topicmodels.preprocessing.PubMedConf
import ch.epfl.bbp.uima.topicmodels.writers.exploitation.AnnotateTokensWithTopicWriter
import ch.epfl.bbp.uima.topicmodels.writers.exploitation.TopicDistributionWriter
import ch.epfl.bbp.uima.typesystem.TypeSystem
import ch.epfl.bbp.uima.uimafit.CpeBuilder
import ch.epfl.bbp.uima.BlueUima
import ch.epfl.bbp.uima.TopicModelsHelper
import org.apache.commons.lang.StringUtils
import ch.epfl.bbp.uima.ae.GarbageCollectorAnnotator
import ch.epfl.bbp.uima.topicmodels.dca.DCAFiles
import ch.epfl.bbp.uima.cr.TextArrayReader

/**
 * Annotates tokens in documents with its most likely topic.
 * Also generates a plot of topic distributions for each document.
 *
 * Note: Observed some memory leak (i.e. memory grows large with many document). Not further investigated.
 */
object MyConf {

  val compiledModelDir = "/Volumes/HDD2/ren_data/dev_hdd/bluebrain/9_lda/dca/20130129_replicate_marcs/20130127_dca-pubmed_abstracts_trained_model"

  val base = "/Users/richarde/Desktop/BBP_experiments/18_txt2PmId/people_stats/"
  val users = List("dan")//"shruti", "srikanth", "toypaper_zotero", "eilif", "martin_zotero", "henry")

  //  val base = "/Users/richarde/Desktop/BBP_experiments/18_txt2PmId/epfl_profs_stats/"
  //  val users = List(
  ////
  ////    "20130309_aebischer", //
  ////    "20130309_barrandon", //
  ////    "20130309_blanke", //
  ////    "20130309_courtine", //
  ////    "20130309_gastpar", //
  ////    "20130309_gerstner", //
  ////    "20130309_gruetter", //
  ////    "20130309_hadjikhani", //
  //  //  "20130309_herzog", //
  ////    "20130309_ijspeert", //
  ////    "20130309_lashuel", //
  ////    "20130309_magistretti", //
  ////    "20130309_markram", //
  ////    "20130309_micera", //
  ////    "20130309_petersen", //
  //    "20130309_rainer", //
  //    "20130309_sandi", //
  //    "20130309_schneggenburger", //
  //    "20130309_schorderet", //
  //    "20130309_vandeville")
}

object TopicsForLabPeoplePdfs extends App {

  for (user <- MyConf.users) {

    GarbageCollectorAnnotator.runGC

    val pmIds = LineReader.intsFrom(new FileInputStream(MyConf.base + user + "_pmids.txt")) //+ ".txt")) 
    val outputDir = MyConf.base  + user + "/"; new File(outputDir).mkdirs()

//   TODO  val reader = CollectionReaderFactory.createReaderDescription(classOf[PubmedFromListDatabaseCR],
//      TypeSystem.JULIE_TSD: TypeSystemDescription,
//      BlueUima.PARAM_DB_CONNECTION, Array("localhost", "bb_pubmed", "root", ""),
//      "ids", pmIds)
      val reader = CollectionReaderFactory.createReaderDescription(classOf[TextArrayReader],
              TypeSystem.JULIE_TSD: TypeSystemDescription)

    val pre = PreprocessingEngine.getTwentyNewsgroupsPreprocessing(PubMedConf.stopwords)

    val dcaAnnotator = AnalysisEngineFactory.createEngineDescription(classOf[DCATopicModelsAnnotator], TypeSystem.JULIE_TSD,
      DCATopicModelsAnnotator.DCADirectoryPath, MyConf.compiledModelDir,
      DCATopicModelsAnnotator.DCAStem, "dca",
      DCATopicModelsAnnotator.InferenceBurnin, "20",
      DCATopicModelsAnnotator.InferenceIterations, "100")

    val writer = AnalysisEngineFactory.createEngineDescription(classOf[AnnotateTokensWithTopicWriter], TypeSystem.JULIE_TSD,
      AnnotateTokensWithTopicWriter.LatexOutput, "true",
      AnnotateTokensWithTopicWriter.OutputDir, outputDir)

    val rcommand = "R --vanilla --slave -f " + TopicModelsHelper.TOPIC_MODELS_SCRIPTS + "plot_doc_topic_distribution.R --args {input} {output}"
    val plots = AnalysisEngineFactory.createEngineDescription(classOf[TopicDistributionWriter], TypeSystem.JULIE_TSD,
      TopicDistributionWriter.OutputDir, outputDir,
      TopicDistributionWriter.Command, rcommand)

    val annotators = pre ::: (dcaAnnotator :: writer :: plots :: Nil)
    val pipelineBuilder = new CpeBuilder(1, reader)
    annotators.foreach(a => pipelineBuilder.add(a))

    pipelineBuilder.process

  }
}

object MergeDistributions extends App {

  val users = List("dan")//"shruti", "srikanth", "toypaper_zotero", "eilif", "martin_zotero", "henry")
  for (user <- users) {
    val userDistDir = new File(MyConf.base + "/20130310_" + user + "_ns2/")
//    val userDistDir = new File(MyConf.base +  user + "/")

    val writer = new TextFileWriter(MyConf.base + "/" + user + ".dist_ns2_all.txt")
//    val writer = new TextFileWriter(MyConf.base + "/" + user + ".dist_all.txt")

    val files = FileUtils.listFiles(userDistDir, Array("dist"), false).foreach { f =>
      val topicDist = LineReader.doublesFrom(new FileInputStream(f.asInstanceOf[File]))
      writer.addLine(f.asInstanceOf[File].getName.replaceFirst(".dist", "") + "\t" + StringUtils.join(topicDist.iterator, "\t"))
    }
    writer.close()
  }
}

object PrintWordsPerTopics extends App {

  //val inferencer = new DCAInference(PubMedConf.compiledModelDir, PubMedConf.stem, false)
  val termTopicProbability = DCAFiles.getTermTopicProbabilityMatrix(MyConf.compiledModelDir, PubMedConf.stem, false)
  val tokenDict = DCAFiles.getTokenDict(MyConf.compiledModelDir, PubMedConf.stem)
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
