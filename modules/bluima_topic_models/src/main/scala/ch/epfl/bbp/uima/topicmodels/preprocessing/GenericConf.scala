package ch.epfl.bbp.uima.topicmodels.preprocessing
import java.util.Properties
import java.io.FileInputStream

// FIXME Find better configuration mechanism

/**
 * Hols some configuration parameters often used. Configured over property files.
 */
object GenericConf {
  private val properties = new Properties();
  init

  private def init() {
    properties.load(new FileInputStream("/home/mz/Documents/epfl/projet/blue_uima_trunk/projects/blue_uima_topic_models/src/main/resources/general.properties"))
    println(properties.getProperty("malletStopwords"))
  }

  def malletStopwords = properties.getProperty("malletStopwords")
  def pubmedStopwords = properties.getProperty("pubmedStopwords")
  def twentyNewsgroupsRawCorpusDir = properties.getProperty("twentyNewsgroupsRawDir")
  def pubmedAbstractsRawCorpusDir = properties.getProperty("pubmedAbstrRawCorpus")
}